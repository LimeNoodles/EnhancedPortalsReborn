package com.teamsevered.enhancedportalsreborn.client.gui;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.client.gui.elements.ElementGlyphDisplay;
import com.teamsevered.enhancedportalsreborn.client.gui.tabs.TabTip;
import com.teamsevered.enhancedportalsreborn.inventory.ContainerPortalController;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketGuiData;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketRequestGui;
import com.teamsevered.enhancedportalsreborn.tile.TileController;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.util.Localization;
import com.teamsevered.enhancedportalsreborn.util.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.util.Arrays;

public class GuiPortalController extends BaseGui
{
    public static final int CONTAINER_SIZE = 78;
    TileController controller;
    GuiButton buttonLock;
    ElementGlyphDisplay display;

    public GuiPortalController(TileController c, EntityPlayer p)
    {
        super(new ContainerPortalController(c, p.inventory), CONTAINER_SIZE);
        controller = c;
        name = "gui.portalController";
        setHidePlayerInventory();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonLock = new GuiButton(10, guiLeft + 7, guiTop + containerSize - 27, 162, 20, Localization.get("gui." + (controller.isPublic ? "public" : "private")));
        buttonList.add(buttonLock);
        display = new ElementGlyphDisplay(this, 7, 29, controller.getIdentifierUnique());
        addElement(display);
        addTab(new TabTip(this, "privatePublic"));
    }

    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException
    {
        super.mouseClicked(x, y, button);

        if (x >= guiLeft + 7 && x <= guiLeft + 168 && y >= guiTop + 32 && y < guiTop + 47)
        {
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(controller, GuiEnums.GUI_CONTROLLER.CONTROLLER_B));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == buttonLock.id)
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setBoolean("public", true);
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketGuiData(tag));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        super.drawGuiContainerForegroundLayer(x, y);
        getFontRenderer().drawString(Localization.get("gui.uniqueIdentifier"), 7, 19, 0x404040);

        if (x >= guiLeft + 7 && x <= guiLeft + 168 && y >= guiTop + 32 && y < guiTop + 47)
        {
            drawHoveringText(Arrays.asList(new String[]{Localization.get("gui.clickToModify")}), x - guiLeft, y - guiTop, getFontRenderer());
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        buttonLock.displayString = Localization.get("gui." + (controller.isPublic ? "public" : "private"));
        display.setIdentifier(controller.getIdentifierUnique());
    }
}
