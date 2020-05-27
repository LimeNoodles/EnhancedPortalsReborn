package com.teamsevered.enhancedportalsreborn.tile;

import com.teamsevered.enhancedportalsreborn.items.ItemNanobrush;
import com.teamsevered.enhancedportalsreborn.network.GuiHandler;
import com.teamsevered.enhancedportalsreborn.util.GeneralUtils;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileFrameBasic extends TileFrame
{
    //todo Activate

//   @Override
    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        if (player.isSneaking())
        {
            return false;
        }

        TileController controller = getPortalController();

        if (stack != null && controller != null && controller.isFinalized())
        {
            if (GeneralUtils.isWrench(stack))
            {
                GuiHandler.openGui(player, controller, GuiEnums.GUI_CONTROLLER.CONTROLLER_A.ordinal());
                return true;
            }
            else if (stack.getItem() == ItemNanobrush.instance)
            {
                GuiHandler.openGui(player, controller, GuiEnums.GUI_TEXTURE.TEXTURE_A.ordinal());
                return true;
            }
        }

        return false;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {

    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {

    }
}
