package com.teamsevered.enhancedportalsreborn.items;

import com.teamsevered.enhancedportalsreborn.util.Reference;
import com.teamsevered.enhancedportalsreborn.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemUpgrade extends ItemBase
{
    public static ItemUpgrade instance;

    //Todo ENUM all the things

    public ItemUpgrade(String name)
    {
        super(name);
        instance = this;
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(16);
    }

    private void decrementStack(EntityPlayer player, ItemStack stack)
    {
        if (!player.capabilities.isCreativeMode)
        {
            //todo stack.get--;

            if (stack.getCount() <= 0)
            {
                stack = null;
            }
        }
    }

    //todo SetItemUseFirst

    /*@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return false;
        }

        TileEntity tile = world.getTileEntity(pos);
        int blockMeta = stack.getItemDamage() + 2;

        if (tile instanceof TileFrame)
        {
            TileFrame frame = (TileFrame) tile;
            TileController controller = frame.getPortalController(pos);

            if (controller == null)
            {
                frame = null;
                world.setBlockState(x, y, z, BlockFrame.instance, blockMeta, 2);
                decrementStack(player, stack);
                return true;
            }
            else
            {
                if (controller.getDiallingDevices().size() > 0 && blockMeta == BlockFrame.NETWORK_INTERFACE)
                {
                    player.addChatComponentMessage(new TextComponentString(Localization.getChatError("dialAndNetwork")));
                    return false;
                }
                else if (controller.getNetworkInterfaces().size() > 0 && blockMeta == BlockFrame.DIALLING_DEVICE)
                {
                    player.addChatComponentMessage(new TextComponentString(Localization.getChatError("dialAndNetwork")));
                    return false;
                }
                else if (controller.getModuleManipulator() != null && blockMeta == BlockFrame.MODULE_MANIPULATOR)
                {
                    player.addChatComponentMessage(new TextComponentString(Localization.getChatError("multipleMod")));
                    return false;
                }

                controller.removeFrame(frame.getChunkCoordinates());
                frame = null;
                world.setBlock(x, y, z, BlockFrame.instance, blockMeta, 2);
                decrementStack(player, stack);
                TilePortalPart t = (TilePortalPart) world.getTileEntity(pos);

                if (t instanceof TileRedstoneInterface)
                {
                    controller.addRedstoneInterface(t.getChunkCoordinates());
                }
                else if (t instanceof TileDialingDevice)
                {
                    controller.addDialDevice(t.getChunkCoordinates());
                }
                else if (t instanceof TileNetworkInterface)
                {
                    controller.addNetworkInterface(t.getChunkCoordinates());
                }
                else if (t instanceof TilePortalManipulator)
                {
                    controller.setModuleManipulator(t.getChunkCoordinates());
                }
                else if (t instanceof TileTransferEnergy)
                {
                    controller.addTransferEnergy(t.getChunkCoordinates());
                }
                else if (t instanceof TileTransferFluid)
                {
                    controller.addTransferFluid(t.getChunkCoordinates());
                }
                else if (t instanceof TileTransferItem)
                {
                    controller.addTransferItem(t.getChunkCoordinates());
                }

                t.setPortalController(controller.getChunkCoordinates());
                world.markBlockForUpdate(controller.xCoord, controller.yCoord, controller.zCoord);
                return true;
            }
        }

        return false;
    }
*/

}
