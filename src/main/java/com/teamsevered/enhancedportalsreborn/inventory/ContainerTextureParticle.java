package com.teamsevered.enhancedportalsreborn.inventory;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.client.gui.GuiTextureParticle;
import com.teamsevered.enhancedportalsreborn.tile.TileController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerTextureParticle extends BaseContainer
{
    protected TileController controller;

    public ContainerTextureParticle(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiTextureParticle.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize, 7);
        controller = c;
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("colour"))
        {
            controller.setParticleColour(tag.getInteger("colour"));
        }
        else if (tag.hasKey("type"))
        {
            controller.setParticleType(tag.getInteger("type"));
        }
    }
}
