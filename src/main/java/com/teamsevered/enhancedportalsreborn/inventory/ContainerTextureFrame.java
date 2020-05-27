package com.teamsevered.enhancedportalsreborn.inventory;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.client.gui.GuiTextureFrame;
import com.teamsevered.enhancedportalsreborn.tile.TileController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerTextureFrame extends BaseContainer
{
    protected TileController controller;

    public ContainerTextureFrame(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiTextureFrame.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize, 7);
        controller = c;
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("colour"))
        {
            controller.setFrameColour(tag.getInteger("colour"));
        }
        else if (tag.hasKey("custom"))
        {
            controller.setCustomFrameTexture(tag.getInteger("custom"));
        }
        else if (tag.hasKey("removeItem"))
        {
            controller.setFrameItem(null);
        }
        else if (tag.hasKey("id") && tag.hasKey("Damage"))
        {
            //todo controller.setFrameItem(ItemStack.loadItemStackFromNBT(tag));
        }
    }
}
