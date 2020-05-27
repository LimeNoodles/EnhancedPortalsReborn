package com.teamsevered.enhancedportalsreborn.inventory;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.client.gui.GuiNetworkInterfaceGlyphs;
import com.teamsevered.enhancedportalsreborn.portal.GlyphIdentifier;
import com.teamsevered.enhancedportalsreborn.tile.TileController;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerNetworkInterfaceGlyphs extends BaseContainer
{
    TileController controller;

    public ContainerNetworkInterfaceGlyphs(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiNetworkInterfaceGlyphs.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        controller = c;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("nid"))
        {
            controller.setIdentifierNetwork(new GlyphIdentifier(tag.getString("nid")));
            player.openGui(EnhancedPortalsReborn.instance, GuiEnums.GUI_MISC.NETWORK_INTERFACE_A.ordinal(), controller.getWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}
