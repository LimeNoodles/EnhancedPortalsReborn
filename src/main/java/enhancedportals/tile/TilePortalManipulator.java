package enhancedportals.tile;

import enhancedportals.Reference;
import enhancedportals.client.PortalParticleFX;
import enhancedportals.item.ItemNanobrush;
import enhancedportals.network.GuiHandler;
import enhancedportals.utility.GeneralUtils;
import enhancedportals.utility.IPortalModule;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TilePortalManipulator extends TileFrame implements IInventory
{
    ItemStack[] inventory = new ItemStack[9];

    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        if (player.isSneaking())
        {
            return false;
        }

        TileController controller = getPortalController();

        if (stack != null && controller != null && controller.isFinalized())
        {
            if (GeneralUtils.isWrench(stack) && !player.isSneaking())
            {
                GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_MISC.MODULE_MANIPULATOR.ordinal());
                return true;
            }
            else if (stack.getItem() == ItemNanobrush.instance)
            {
                GuiHandler.openGui(player, controller, Reference.GuiEnums.GUI_TEXTURE.TEXTURE_A.ordinal());
                return true;
            }
        }

        return false;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i++)
        {
            NBTTagCompound t = new NBTTagCompound();
            ItemStack s = getStackInSlot(i);

            if (s != null)
            {
                s.writeToNBT(t);
            }

            items.appendTag(t);
        }

        tag.setTag("Modules", items);
    }

  /*  @Override
    public boolean canUpdate()
    {
        return true;
    }*/

    @Override
    public void closeInventory(EntityPlayer player)
    {

    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack stack = getStackInSlot(i);

        if (stack != null)
        {
            if (stack.getCount() <= j)
            {
                setInventorySlotContents(i, null);
            }
            else
            {
                stack = stack.splitStack(j);

                if (stack.getCount() == 0)
                {
                    setInventorySlotContents(i, null);
                }
            }
        }

        return stack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return null;
    }

    public IPortalModule[] getInstalledUpgrades()
    {
        IPortalModule[] modules = new IPortalModule[getSizeInventory()];

        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack s = getStackInSlot(i);

            if (s != null)
            {
                modules[i] = (IPortalModule) s.getItem();
            }
        }

        return modules;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    public ItemStack getModifierItem()
    {
        return inventory[9];
    }

    public ItemStack getModule(String ID)
    {
        for (ItemStack i : getModules())
        {
            if (((IPortalModule) i.getItem()).getID(i).equals(ID))
            {
                return i;
            }
        }

        return null;
    }

    public ArrayList<ItemStack> getModules()
    {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();

        for (ItemStack i : inventory)
        {
            if (i != null && i.getItem() instanceof IPortalModule)
            {
                list.add(i);
            }
        }

        return list;
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inventory[i];
    }

  /*  @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return inventory[i];
    }
    */

    public boolean hasModule(String ID)
    {
        return getModule(ID) != null;
    }

    public boolean installUpgrade(ItemStack stack)
    {
        if (stack == null || !(stack.getItem() instanceof IPortalModule))
        {
            return false;
        }

        IPortalModule pModule = (IPortalModule) stack.getItem();

        if (!hasModule(pModule.getID(stack)) && pModule.canInstallUpgrade(this, getInstalledUpgrades(), stack))
        {
            for (int i = 0; i < getSizeInventory(); i++)
            {
                if (getStackInSlot(i) == null)
                {
                    ItemStack s = stack.copy();
                    s.setCount(1);

                    setInventorySlotContents(i, s);
//                  todo   worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    final IBlockState state = getWorld().getBlockState(getPos());
                    world.notifyBlockUpdate(getPos(), state, state, 3);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof IPortalModule && !hasModule(((IPortalModule) stack.getItem()).getID(stack));
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {

    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {

    }

    public boolean isPortalInvisible()
    {
        for (ItemStack i : getModules())
        {
            if (((IPortalModule) i.getItem()).disablePortalRendering(this, i))
            {
                return true;
            }
        }

        return false;
    }

    //@Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void markDirty()
    {
        super.markDirty();
//   todo     worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        final IBlockState state = getWorld().getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {
        NBTTagList items = tag.getTagList("Modules", 10);

        for (int i = 0; i < items.tagCount(); i++)
        {
          //todo   setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(items.getCompoundTagAt(i)));
        }
    }

    public boolean onEntityTeleport(Entity entity)
    {
        for (ItemStack i : getModules())
        {
            if (((IPortalModule) i.getItem()).onEntityTeleportStart(entity, this, i))
            {
                return true;
            }
        }

        return false;
    }

    public void onEntityTeleported(Entity entity)
    {
        for (ItemStack i : getModules())
        {
            ((IPortalModule) i.getItem()).onEntityTeleportEnd(entity, this, i);
        }
    }

    @Override
    public void openInventory(EntityPlayer player)
    {

    }

    public void particleCreated(PortalParticleFX portalFX)
    {
        for (ItemStack i : getModules())
        {
            ((IPortalModule) i.getItem()).onParticleCreated(this, i, portalFX);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagList items = compound.getTagList("Modules", compound.getId());

        for (int i = 0; i < items.tagCount(); ++i)
        {
            NBTTagCompound item = items.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");
            if (slot >= 0 && slot < getSizeInventory())
            {
               //todo  setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        inventory[i] = itemstack;
    }

    public boolean shouldKeepMomentumOnTeleport()
    {
        for (ItemStack i : getModules())
        {
            if (((IPortalModule) i.getItem()).keepMomentumOnTeleport(this, i))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList items = new NBTTagList();

        // Goes through all of the module inventory.
        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack stack = getStackInSlot(i);
            if (stack != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }

        // Saves the inventory under "Modules" in NBT
        compound.setTag("Modules", items);

        return compound;
    }

    @Override
    public String getName()
    {
        return "tile.ep3.portalFrame.upgrade.name";
    }

    @Override
    public boolean hasCustomName()
    {
        return false;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return null;
    }
}
