package com.teamsevered.enhancedportalsreborn.client.gui;

import com.teamsevered.enhancedportalsreborn.proxy.ClientProxy;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketRequestGui;
import com.teamsevered.enhancedportalsreborn.portal.PortalTextureManager;
import com.teamsevered.enhancedportalsreborn.tile.TileDialingDevice;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class GuiDialingEditFrame extends GuiTextureFrame
{
    TileDialingDevice dial;
    boolean didSave, returnToEdit;

    public GuiDialingEditFrame(TileDialingDevice d, EntityPlayer p)
    {
        this(d, p, false);
    }

    public GuiDialingEditFrame(TileDialingDevice d, EntityPlayer p, boolean r)
    {
        super(d.getPortalController(), p);
        dial = d;
        returnToEdit = r;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        buttonList.add(new GuiButton(1000, guiLeft + 7, guiTop + ySize + 3, xSize - 14, 20, "Save"));

        Color c = new Color(getPTM().getFrameColour());
        sliderR.sliderValue = c.getRed() / 255f;
        sliderG.sliderValue = c.getGreen() / 255f;
        sliderB.sliderValue = c.getBlue() / 255f;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == buttonSave.id)
        {
            getPTM().setFrameColour(Integer.parseInt(String.format("%02x%02x%02x", sliderR.getValue(), sliderG.getValue(), sliderB.getValue()), 16));
        }
        else if (button.id == buttonReset.id)
        {
            int colour = 0xffffff;
            getPTM().setFrameColour(colour);

            Color c = new Color(colour);
            sliderR.sliderValue = c.getRed() / 255f;
            sliderG.sliderValue = c.getGreen() / 255f;
            sliderB.sliderValue = c.getBlue() / 255f;
        }
        else if (button.id == 1000)
        {
            didSave = true;
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiEnums.GUI_DIAL.DIAL_D : GuiEnums.GUI_DIAL.DIAL_C));
        }
        else if (button.id == 500)
        {
            didSave = true;
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_B : GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_SAVE_B));
        }
        else if (button.id == 501)
        {
            didSave = true;
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_C : GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_SAVE_C));
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();

        if (!didSave)
        {
            ClientProxy.saveGlyph = null;
            ClientProxy.saveName = null;
            ClientProxy.saveTexture = null;
        }
    }

    @Override
    public void iconSelected(int icon)
    {
        getPTM().setCustomFrameTexture(icon);
    }

    @Override
    public void onItemChanged(ItemStack newItem)
    {
        getPTM().setFrameItem(newItem);
    }

    @Override
    public PortalTextureManager getPTM()
    {
        return ClientProxy.saveTexture;
    }
}
