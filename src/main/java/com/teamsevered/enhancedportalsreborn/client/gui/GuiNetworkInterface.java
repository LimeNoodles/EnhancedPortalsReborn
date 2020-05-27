package com.teamsevered.enhancedportalsreborn.client.gui;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.util.Localization;
import com.teamsevered.enhancedportalsreborn.client.gui.elements.ElementGlyphDisplay;
import com.teamsevered.enhancedportalsreborn.inventory.ContainerNetworkInterface;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketRequestGui;
import com.teamsevered.enhancedportalsreborn.tile.TileController;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.util.Localization;

import java.io.IOException;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;

public class GuiNetworkInterface extends BaseGui
{
    public static final int CONTAINER_SIZE = 68;
    TileController controller;
    ElementGlyphDisplay display;

    public GuiNetworkInterface(TileController c, EntityPlayer p)
    {
        super(new ContainerNetworkInterface(c, p.inventory), CONTAINER_SIZE);
        controller = c;
        name = "gui.networkInterface";
        setHidePlayerInventory();
    }

    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException
    {
        super.mouseClicked(x, y, button);

        if (x >= guiLeft + 7 && x <= guiLeft + 169 && y >= guiTop + 29 && y < guiTop + 47)
        {
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(controller, GuiEnums.GUI_MISC.NETWORK_INTERFACE_B));
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();
        display = new ElementGlyphDisplay(this, 7, 29, controller.getIdentifierNetwork());
        addElement(display);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        display.setIdentifier(controller.getIdentifierNetwork());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        super.drawGuiContainerForegroundLayer(x, y);
        getFontRenderer().drawString(Localization.get("gui.networkIdentifier"), 7, 19, 0x404040);
        getFontRenderer().drawString(Localization.get("gui.networkedPortals"), 7, 52, 0x404040);
        String s = controller.connectedPortals == -1 ? Localization.get("gui.notSet") : "" + controller.connectedPortals;
        getFontRenderer().drawString(s, xSize - getFontRenderer().getStringWidth(s) - 7, 52, 0x404040);

        if (x >= guiLeft + 7 && x <= guiLeft + 169 && y >= guiTop + 29 && y < guiTop + 47)
        {
            drawHoveringText(Arrays.asList(new String[]{Localization.get("gui.clickToModify")}), x - guiLeft, y - guiTop, getFontRenderer());
        }
    }
}
