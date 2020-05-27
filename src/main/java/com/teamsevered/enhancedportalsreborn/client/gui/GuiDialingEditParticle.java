package com.teamsevered.enhancedportalsreborn.client.gui;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.proxy.ClientProxy;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketRequestGui;
import com.teamsevered.enhancedportalsreborn.portal.PortalTextureManager;
import com.teamsevered.enhancedportalsreborn.tile.TileDialingDevice;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.util.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class GuiDialingEditParticle extends GuiTextureParticle
{
    TileDialingDevice dial;
    boolean didSave, returnToEdit;

    public GuiDialingEditParticle(TileDialingDevice d, EntityPlayer p)
    {
        this(d, p, false);
    }

    public GuiDialingEditParticle(TileDialingDevice d, EntityPlayer p, boolean r)
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

        Color c = new Color(getPTM().getParticleColour());
        sliderR.sliderValue = c.getRed() / 255f;
        sliderG.sliderValue = c.getGreen() / 255f;
        sliderB.sliderValue = c.getBlue() / 255f;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == buttonSave.id)
        {
            getPTM().setParticleColour(Integer.parseInt(String.format("%02x%02x%02x", sliderR.getValue(), sliderG.getValue(), sliderB.getValue()), 16));
        }
        else if (button.id == buttonReset.id)
        {
            int colour = 0xffffff;
            getPTM().setParticleColour(colour);

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
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_A : GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_SAVE_A));
        }
        else if (button.id == 501)
        {
            didSave = true;
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, returnToEdit ? GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_B : GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_SAVE_B));
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
    public void particleSelected(int particle)
    {
        getPTM().setParticleType(particle);
    }

    @Override
    public PortalTextureManager getPTM()
    {
        return ClientProxy.saveTexture;
    }
}
