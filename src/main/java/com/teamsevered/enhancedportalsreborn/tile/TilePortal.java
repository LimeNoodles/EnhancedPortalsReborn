package com.teamsevered.enhancedportalsreborn.tile;


import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.items.ItemNanobrush;
import com.teamsevered.enhancedportalsreborn.network.GuiHandler;
import com.teamsevered.enhancedportalsreborn.util.GeneralUtils;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;


public class TilePortal extends TilePortalPart
{
    public boolean activate(EntityPlayer player, ItemStack stack)
    {
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
                GuiHandler.openGui(player, controller, player.isSneaking() ? GuiEnums.GUI_TEXTURE.TEXTURE_C.ordinal() : GuiEnums.GUI_TEXTURE.TEXTURE_B.ordinal());
                return true;
            }
        }

        return false;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {

    }

    public int getColour()
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            return controller.activeTextureData.getPortalColour();
        }
        else if (portalController != null)
        {
            EnhancedPortalsReborn.proxy.waitForController(new ChunkPos(portalController.x, portalController.z), getChunkPos());
        }

        return 0xFFFFFF;
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {

    }

    public void onEntityCollidedWithBlock(Entity entity)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.onEntityEnterPortal(entity, this);
        }
    }
}
