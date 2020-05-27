package enhancedportals.tile;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
//import enhancedportals.block.BlockContainerBase;
//import enhancedportals.block.BlockStabilizer;
import enhancedportals.item.ItemLocationCard;
import enhancedportals.network.GuiHandler;
import enhancedportals.portal.GlyphIdentifier;
import enhancedportals.portal.PortalException;
import enhancedportals.portal.PortalTextureManager;
import enhancedportals.utility.ConfigurationHandler;
import enhancedportals.utility.GeneralUtils;
import enhancedportals.utility.GuiEnums;
import enhancedportals.utility.LogHelper;

import io.netty.buffer.ByteBuf;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class TileStabilizerMain extends TileEP implements IInventory, ITickable
{
    static final int ENERGY_STORAGE_PER_ROW = ConfigurationHandler.CONFIG_REDSTONE_FLUX_COST + ConfigurationHandler.CONFIG_REDSTONE_FLUX_COST / 2;

    ArrayList<ChunkPos> blockList;

    HashMap<String, String> activeConnections;
    HashMap<String, String> activeConnectionsReverse;

    ItemStack inventory;
    int rows, tickTimer;
    EnergyStorage energyStorage;
    public int powerState, instability = 0;
    public boolean is3x3 = false;
    public ArrayList<TileController> connectedPortals;

    @SideOnly(Side.CLIENT)
    public int intActiveConnections;

    public TileStabilizerMain()
    {
        blockList = new ArrayList<ChunkPos>();
        connectedPortals = new ArrayList<TileController>();
        activeConnections = new HashMap<String, String>();
        activeConnectionsReverse = new HashMap<String, String>();
        energyStorage = new EnergyStorage(0);
    }

    /*
     * Adds the given TileController to the connectedPortals HashMap
     */
    public void addPortal(TileController portal)
    {
        connectedPortals.add(portal);
    }

    public boolean activate(EntityPlayer player)
    {
        ItemStack stack = player.inventory.getCurrentItem();

        if (stack != null && stack.getItem() instanceof ItemBlock && Block.getBlockFromItem(stack.getItem()) == Blocks.NETHERRACK)
        {
            return false;
        }

        GuiHandler.openGui(player, this, GuiEnums.GUI_MISC.DIM_BRIDGE_STABILIZER.ordinal());
        return true;
    }

    public void breakBlock()
    {
        for (int i = activeConnections.size() - 1; i > -1; i--)
        {
            try
            {
                terminateExistingConnection(new GlyphIdentifier(activeConnections.values().toArray(new String[activeConnections.size()])[i]));
            }
            catch (Exception e)
            {

            }
        }

        for (ChunkPos c : blockList)
        {
            TileEntity tile = world.getTileEntity(getPos());

            if (tile instanceof TileStabilizer)
            {
                TileStabilizer t = (TileStabilizer) tile;
                t.mainBlock = null;
//                worldObj.markBlockForUpdate(t.xCoord, t.yCoord, t.zCoord);
                final IBlockState state = getWorld().getBlockState(getPos());
                world.notifyBlockUpdate(getPos(), state, state, 3);
            }
        }
    }

    /***
     * Whether or not this stabilizer can create a new connection
     */
    public boolean canAcceptNewConnection()
    {
        return activeConnections.size() * 2 + 2 <= ConfigurationHandler.CONFIG_PORTAL_CONNECTIONS_PER_ROW * rows;
    }

    //TODO @Override
    public boolean canConnectEnergy(EnumFacing from)
    {
        return true;
    }


    public void deconstruct()
    {
        breakBlock();
//        worldObj.setBlock(xCoord, yCoord, zCoord, BlockStabilizer.instance, 0, 3);
        final IBlockState state = getWorld().getBlockState(getPos());
        world.setBlockState(getPos(), state);
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

    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate)
    {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    public int getActiveConnections()
    {
        return activeConnections != null ? activeConnections.size() : -1;
    }

    public GlyphIdentifier getConnectedPortal(GlyphIdentifier uniqueIdentifier)
    {
        if (activeConnections.containsKey(uniqueIdentifier.getGlyphString()))
        {
            return new GlyphIdentifier(activeConnections.get(uniqueIdentifier.getGlyphString()));
        }
        else if (activeConnectionsReverse.containsKey(uniqueIdentifier.getGlyphString()))
        {
            return new GlyphIdentifier(activeConnectionsReverse.get(uniqueIdentifier.getGlyphString()));
        }

        return null;
    }

    public EnergyStorage getEnergyStorage()
    {
        return energyStorage;
    }

    public int getEnergyStoragePerRow()
    {
        return (int) ((is3x3 ? ENERGY_STORAGE_PER_ROW + ENERGY_STORAGE_PER_ROW / 2 : ENERGY_STORAGE_PER_ROW) * ConfigurationHandler.CONFIG_POWER_STORAGE_MULTIPLIER);
    }

    //todo @Override
    public int getEnergyStored(EnumFacing from)
    {
        return energyStorage.getEnergyStored();
    }

   /* @Override
    public String getInventoryName()
    {
        return "tile.stabilizer.name";
    }*/

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    //TODO @Override
    public int getMaxEnergyStored(EnumFacing from)
    {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inventory;
    }

/*    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return inventory;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }*/

    /***
     * Gets whether or not this stabilizer has enough power to keep the portal open for at least one second.
     */
    public boolean hasEnoughPowerToStart()
    {
        if (!GeneralUtils.hasEnergyCost())
        {
            return true;
        }

        int powerRequirement = (int) (GeneralUtils.getPowerMultiplier() * ConfigurationHandler.CONFIG_REDSTONE_FLUX_COST);
        return extractEnergy(null, (int) (powerRequirement * 0.3), true) == (int) (powerRequirement * 0.3);
    }

    //@Override
    //public boolean isItemValidForSlot(int i, ItemStack itemstack)
   // {
        //todo return GeneralUtils.isEnergyContainerItem(itemstack);
    //}

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

    //todo @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {

    }

    @Override
    public void closeInventory(EntityPlayer player)
    {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public void packetGuiFill(ByteBuf buffer)
    {
        buffer.writeInt(activeConnections.size());
        buffer.writeInt(powerState);
        buffer.writeInt(instability);
        buffer.writeInt(energyStorage.getMaxEnergyStored());
        buffer.writeInt(energyStorage.getEnergyStored());
    }

    @Override
    public void packetGuiUse(ByteBuf buffer)
    {
        if (buffer.readableBytes() <= 1)
        {
            return; // Stops EOF exceptions from old invalid packets
        }

        intActiveConnections = buffer.readInt();
        powerState = buffer.readInt();
        instability = buffer.readInt();
        //TODO energyStorage.setCapacity(buffer.readInt());
        //TODO energyStorage.setEnergyStored(buffer.readInt());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        powerState = tag.getInteger("powerState");
        is3x3 = tag.getBoolean("is3x3");
        rows = tag.getInteger("rows");
        energyStorage = new EnergyStorage(rows * getEnergyStoragePerRow());
        blockList = GeneralUtils.loadChunkCoordList(tag, "blockList");
        //TODO energyStorage.readFromNBT(tag);

        if (tag.hasKey("activeConnections"))
        {
            NBTTagList c = tag.getTagList("activeConnections", 10);

            for (int i = 0; i < c.tagCount(); i++)
            {
                NBTTagCompound t = c.getCompoundTagAt(i);
                String A = t.getString("Key"), B = t.getString("Value");

                activeConnections.put(A, B);
                activeConnectionsReverse.put(B, A);
            }
        }

        if (tag.hasKey("inventory"))
        {
            //todo inventory = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("inventory"));
        }
    }

    //TODO @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate)
    {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    /***
     * Removes a connection from the active list.
     */
    public void removeExistingConnection(GlyphIdentifier portalA, GlyphIdentifier portalB)
    {
        activeConnections.remove(portalA.getGlyphString());
        activeConnections.remove(portalB.getGlyphString());
        activeConnectionsReverse.remove(portalA.getGlyphString());
        activeConnectionsReverse.remove(portalB.getGlyphString());

        if (activeConnections.size() == 0 && powerState == 0 && instability > 0)
        {
            setInstability(0);
        }
    }

    public void setData(ArrayList<ChunkPos> blocks, int rows2, boolean is3)
    {
        is3x3 = is3;
        rows = rows2;
        blockList = blocks;
        energyStorage = new EnergyStorage(rows * getEnergyStoragePerRow());
//   todo     worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        final IBlockState state = getWorld().getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
    }

    void setInstability(int newInstability)
    {
        if (newInstability == instability)
        {
            return;
        }

        instability = newInstability;

        for (Entry<String, String> pair : activeConnections.entrySet())
        {
            TileController controllerA = EnhancedPortals.proxy.networkManager.getPortalController(new GlyphIdentifier(pair.getKey()), getPos());
            TileController controllerB = EnhancedPortals.proxy.networkManager.getPortalController(new GlyphIdentifier(pair.getValue()), getPos());

            if (controllerA != null)
            {
                controllerA.setInstability(newInstability);
            }

            if (controllerB != null)
            {
                controllerB.setInstability(newInstability);
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        inventory = itemstack;
    }

    /***
     * Sets up a new connection between two portals.
     *
     * @return True if connection was successfully established.
     */
    public boolean setupNewConnection(GlyphIdentifier portalA, GlyphIdentifier portalB, PortalTextureManager textureManager) throws PortalException
    {
        if (activeConnections.containsKey(portalA.getGlyphString()))
        {
            throw new PortalException("diallingPortalAlreadyActive");
        }
        else if (activeConnections.containsValue(portalB.getGlyphString()))
        {
            throw new PortalException("receivingPortalAlreadyActive");
        }
        else if (!hasEnoughPowerToStart())
        {
            throw new PortalException("notEnoughPowerToStart");
        }
        else if (!canAcceptNewConnection())
        {
            throw new PortalException("maxedConnectionLimit");
        }
        else if (portalA.equals(portalB))
        {
            throw new PortalException("cannotDialDiallingPortal");
        }
        else if (!EnhancedPortals.proxy.networkManager.portalIdentifierExists(portalA))
        {
            throw new PortalException("noPortalWithThatIdentifierSending");
        }
        else if (!EnhancedPortals.proxy.networkManager.portalIdentifierExists(portalB))
        {
            throw new PortalException("noPortalWithThatIdentifierReceiving");
        }

        TileController cA = EnhancedPortals.proxy.networkManager.getPortalController(portalA, getPos()), cB = EnhancedPortals.proxy.networkManager.getPortalController(portalB, getPos());

        if (cA == null)
        {
            throw new PortalException("diallingPortalNotFound");
        }
        else if (cB == null)
        {
            throw new PortalException("receivingPortalNotFound");
        }
        else if (cA.isPortalActive())
        {
            throw new PortalException("diallingPortalAlreadyActive");
        }
        else if (cB.isPortalActive())
        {
            throw new PortalException("receivingPortalAlreadyActive");
        }
        else if (!cA.isFinalized())
        {
            throw new PortalException("sendingPortalNotInitialized");
        }
        else if (!cB.isFinalized())
        {
            throw new PortalException("receivingPortalNotInitialized");
        }
        else if (cA.getDiallingDevices().size() > 0 && cB.getNetworkInterfaces().size() > 0)
        {
            throw new PortalException("receivingPortalNoDialler");
        }
        else if (cA.getNetworkInterfaces().size() > 0 && cB.getDiallingDevices().size() > 0)
        {
            throw new PortalException("sendingPortalNoDialler"); // Should never happen but it doesn't hurt to check.
        }

        TileStabilizerMain sA = cA.getDimensionalBridgeStabilizer(), sB = cB.getDimensionalBridgeStabilizer();

        if (!sA.getDimensionCoordinates().equals(sB.getDimensionCoordinates()))
        {
            if (cB.isPublic)
            {
                cB.setupTemporaryDBS(sA);
            }
            else
            {
                throw new PortalException("notOnSameStabilizer");
            }
        }

        if (textureManager != null && !textureManager.isDefault())
        {
            cA.swapTextureData(textureManager);
            cB.swapTextureData(textureManager);
        }

        try
        {
            cA.setInstability(instability);
            cA.portalCreate();
            cA.cacheDestination(portalB, cB.getDimensionCoordinates());
        }
        catch (PortalException e)
        {
            cA.revertTextureData();
            cB.revertTextureData();
            throw new PortalException("sendingPortalFailedToCreatePortal");
        }

        try
        {
            cB.setInstability(instability);
            cB.portalCreate();
            cB.cacheDestination(portalA, cA.getDimensionCoordinates());
        }
        catch (PortalException e)
        {
            cA.portalRemove();
            cA.cacheDestination(null, null);
            cA.revertTextureData();
            cB.revertTextureData();
            throw new PortalException("receivingPortalFailedToCreatePortal");
        }

        activeConnections.put(portalA.getGlyphString(), portalB.getGlyphString());
        activeConnectionsReverse.put(portalB.getGlyphString(), portalA.getGlyphString());
        return true;
    }

    /***
     * Terminates both portals and removes them from the active connection list. Used by dialling devices when the exit location is not known by the controller.
     */
    public void terminateExistingConnection(GlyphIdentifier identifier) throws PortalException
    {
        if (identifier == null || identifier.isEmpty())
        {
            throw new PortalException("No identifier found for first portal");
        }

        GlyphIdentifier portalA = new GlyphIdentifier(identifier), portalB = null;

        if (activeConnections.containsKey(identifier.getGlyphString()))
        {
            portalB = new GlyphIdentifier(activeConnections.get(identifier.getGlyphString()));
        }
        else if (activeConnectionsReverse.containsKey(identifier.getGlyphString()))
        {
            portalB = new GlyphIdentifier(activeConnectionsReverse.get(identifier.getGlyphString()));
        }

        terminateExistingConnection(portalA, portalB);
    }

    /***
     * Terminates both portals and removes them from the active connection list.
     */
    public void terminateExistingConnection(GlyphIdentifier portalA, GlyphIdentifier portalB) throws PortalException
    {
        if (portalA == null)
        {
            throw new PortalException("No identifier found for first portal");
        }
        else if (portalB == null)
        {
            throw new PortalException("No identifier found for second portal");
        }

        TileController cA = EnhancedPortals.proxy.networkManager.getPortalController(portalA, getPos()), cB = EnhancedPortals.proxy.networkManager.getPortalController(portalB, getPos());

        if (cA == null || cB == null)
        {
            if (cA == null)
            {
                throw new PortalException("No identifier found for first portal");
            }
            else if (cB == null)
            {
                throw new PortalException("No identifier found for second portal");
            }

            if (cA != null)
            {
                cA.portalRemove();
                cA.revertTextureData();
                cA.cacheDestination(null, null);
            }

            if (cB != null)
            {
                cB.portalRemove();
                cB.revertTextureData();
                cB.cacheDestination(null, null);
            }

            removeExistingConnection(portalA, portalB);
        }
        else if (activeConnections.containsKey(portalA.getGlyphString()) && activeConnections.get(portalA.getGlyphString()).equals(portalB.getGlyphString()) || activeConnectionsReverse.containsKey(portalA.getGlyphString()) && activeConnectionsReverse.get(portalA.getGlyphString()).equals(portalB.getGlyphString()))
        {
            // Make sure we're terminating the correct connection, also don't mind that we're terminating it from the other side that we started it from
            cA.portalRemove();
            cB.portalRemove();
            cA.cacheDestination(null, null);
            cB.cacheDestination(null, null);
            cA.revertTextureData();
            cB.revertTextureData();

            removeExistingConnection(portalA, portalB);
        }
        else
        {
            throw new PortalException("Could not find both portals in the active connection list.");
        }
    }

    @Override
    public void update()
    {
        if (activeConnections.size() > 0 && GeneralUtils.hasEnergyCost() && tickTimer >= ConfigurationHandler.CONFIG_REDSTONE_FLUX_TIMER)
        {
            int powerRequirement = (int) (GeneralUtils.getPowerMultiplier() * activeConnections.size() * ConfigurationHandler.CONFIG_REDSTONE_FLUX_COST);

            if (powerState == 0 && extractEnergy(null, powerRequirement, true) == powerRequirement) // Simulate the full power requirement
            {
                extractEnergy(null, powerRequirement, false);
                setInstability(0);
            }
            else if ((powerState == 1 || powerState == 0) && extractEnergy(null, (int) (powerRequirement * 0.8), true) == (int) (powerRequirement * 0.8)) // Otherwise, try it at 80%
            {
                extractEnergy(null, (int) (powerRequirement * 0.8), false);
                setInstability(20);
            }
            else if ((powerState == 2 || powerState == 0) && extractEnergy(null, (int) (powerRequirement * 0.5), true) == (int) (powerRequirement * 0.5)) // Otherwise, try it at 50%
            {
                extractEnergy(null, (int) (powerRequirement * 0.5), false);
                setInstability(50);
            }
            else if ((powerState == 3 || powerState == 0) && extractEnergy(null, (int) (powerRequirement * 0.3), true) == (int) (powerRequirement * 0.3)) // Otherwise, try it at 30%
            {
                extractEnergy(null, (int) (powerRequirement * 0.3), false);
                setInstability(70);
            }
            else
            {
                for (int i = activeConnections.size() - 1; i > -1; i--)
                {
                    try
                    {
                        terminateExistingConnection(new GlyphIdentifier(activeConnections.values().toArray(new String[activeConnections.size()])[i]));
                    }
                    catch (PortalException e)
                    {
                        LogHelper.warn(e.getMessage());
                    }
                }

                setInstability(0);
            }

            tickTimer = -1;
        }

        if (inventory != null)
        {//todo
            /*if (inventory.getItem() instanceof IEnergyContainerItem)
            {
                int requiredEnergy = energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored();

                //todo
                // if (requiredEnergy > 0 && ((IEnergyContainerItem) inventory.getItem()).getEnergyStored(inventory) > 0)
                //{
                    //energyStorage.receiveEnergy(((IEnergyContainerItem) inventory.getItem()).extractEnergy(inventory, Math.min(requiredEnergy, 10000), false), false);
                //}
            }
            else if (inventory.getItem() == ItemLocationCard.instance)
            {
                if (!ItemLocationCard.hasDBSLocation(inventory) && !world.isRemote)
                {
                    ItemLocationCard.setDBSLocation(inventory, getDimensionCoordinates());
                }
            }*/
        }

        tickTimer++;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        //TODO energyStorage.writeToNBT(tag);
        tag.setBoolean("is3x3", is3x3);
        tag.setInteger("powerState", powerState);
        tag.setInteger("rows", rows);
        GeneralUtils.saveChunkCoordList(tag, blockList, "blockList");

        if (!activeConnections.isEmpty())
        {
            NBTTagList c = new NBTTagList();

            for (Entry<String, String> entry : activeConnections.entrySet())
            {
                NBTTagCompound t = new NBTTagCompound();
                t.setString("Key", entry.getKey());
                t.setString("Value", entry.getValue());
                c.appendTag(t);
            }

            tag.setTag("activeConnections", c);
        }

        if (inventory != null)
        {
            NBTTagCompound t = new NBTTagCompound();
            inventory.writeToNBT(t);
            tag.setTag("inventory", t);
        }

        return tag;
    }

    @Override
    public String getName()
    {
        return null;
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