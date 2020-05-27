package com.teamsevered.enhancedportalsreborn.client.gui;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.client.gui.elements.ElementGlyphSelector;
import com.teamsevered.enhancedportalsreborn.client.gui.elements.ElementGlyphViewer;
import com.teamsevered.enhancedportalsreborn.inventory.ContainerAddressManual;
import com.teamsevered.enhancedportalsreborn.items.ItemAddressBook;
import com.teamsevered.enhancedportalsreborn.proxy.ClientProxy;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketRequestGui;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.util.Localization;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

public class GuiAddressManual extends BaseGui
{
    public static final int CONTAINER_SIZE = 163;
    ElementGlyphSelector selector;
    EntityPlayer player;
    int warningTimer;
    ItemAddressBook addressBook;

    public GuiAddressManual(ItemAddressBook b, EntityPlayer player)
    {
        super(new ContainerAddressManual(b, player.inventory), CONTAINER_SIZE);
        name = "gui.addressBook";
        setHidePlayerInventory();
        this.player = player;
        addressBook = b;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        selector = new ElementGlyphSelector(this, 7, 59);
        addElement(new ElementGlyphViewer(this, 7, 27, selector));
        addElement(selector);

        buttonList.add(new GuiButton(0, guiLeft + xSize - 87, guiTop + 136, 80, 20, Localization.get("gui.clear")));
        buttonList.add(new GuiButton(1, guiLeft + 50, guiTop + 115, 80, 20, Localization.get("gui.save")));
        buttonList.add(new GuiButton(2, guiLeft + 7, guiTop + 136, 80, 20, Localization.get("gui.cancel")));
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);

        getFontRenderer().drawString(Localization.get("gui.uniqueIdentifier"), 7, 18, 0x404040);
        getFontRenderer().drawString(Localization.get("gui.glyphs"), 7, 50, 0x404040);

        if (warningTimer > 0)
        {
            drawRect(7, 27, 7 + 162, 27 + 18, 0xAA000000);
            String s = Localization.get("gui.noUidSet");
            getFontRenderer().drawString(s, xSize / 2 - getFontRenderer().getStringWidth(s) / 2, 33, 0xff4040);
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        if (warningTimer > 0)
        {
            warningTimer--;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            selector.setIdentifierTo(null);
        }
        else if (button.id == 1) // save
        {
            if (selector.getGlyphIdentifier().size() > 0)
            {
                ClientProxy.saveGlyph = selector.getGlyphIdentifier();
                ClientProxy.saveName = "Unnamed Portal";
                EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(player, GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_C));
            }
            else
            {
                warningTimer = 50;
            }
        }
        else if (button.id == 2)
        { //cancel
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(player, GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_A));
        }
    }
}

