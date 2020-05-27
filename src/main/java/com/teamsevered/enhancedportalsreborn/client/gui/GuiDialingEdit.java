package com.teamsevered.enhancedportalsreborn.client.gui;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.inventory.ContainerDialingEdit;
import com.teamsevered.enhancedportalsreborn.proxy.ClientProxy;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketGuiData;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketRequestGui;
import com.teamsevered.enhancedportalsreborn.portal.GlyphIdentifier;
import com.teamsevered.enhancedportalsreborn.portal.PortalTextureManager;
import com.teamsevered.enhancedportalsreborn.tile.TileDialingDevice;

import java.io.IOException;
import java.util.Arrays;

import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.util.Localization;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.input.Keyboard;

public class GuiDialingEdit extends GuiDialingAdd
{
    boolean receivedData = false;

    public GuiDialingEdit(TileDialingDevice d, EntityPlayer p)
    {
        super(new ContainerDialingEdit(d, p.inventory), CONTAINER_SIZE);
        dial = d;
        name = "gui.dialDevice";
        setHidePlayerInventory();
        allowUserInput = true;
        Keyboard.enableRepeatEvents(true);

        if (ClientProxy.saveTexture == null)
        {
            ClientProxy.saveTexture = new PortalTextureManager();
        }
    }

    @Override
    public void initGui()
    {
        if (ClientProxy.saveName == null)
        {
            ClientProxy.saveName = "";
            ClientProxy.saveGlyph = new GlyphIdentifier();
            ClientProxy.saveTexture = new PortalTextureManager();
        }
        else
        {
            receivedData = true;
        }

        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (receivedData)
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);

            if (mouseX >= guiLeft + 7 && mouseX <= guiLeft + 168 && mouseY >= guiTop + 52 && mouseY < guiTop + 70)
            {
                isEditing = true;
                EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, GuiEnums.GUI_DIAL.DIAL_E));
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException
    {
        if (receivedData)
        {
            super.keyTyped(par1, par2);
        }
        else if (par2 == 1 || par2 == mc.gameSettings.keyBindInventory.getKeyCode())
        {
            mc.player.closeScreen();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);

        if (!receivedData) // Just in case the users connection is very slow
        {
            drawRect(0, 0, xSize, ySize, 0xCC000000);
            String s = Localization.get("gui.waitingForDataFromServer");
            getFontRenderer().drawSplitString(s, xSize / 2 - getFontRenderer().getStringWidth(s) / 2, ySize / 2 - getFontRenderer().FONT_HEIGHT / 2, xSize, 0xFF0000);
        }

        if (par1 >= guiLeft + 7 && par1 <= guiLeft + 168 && par2 >= guiTop + 52 && par2 < guiTop + 70)
        {
            drawHoveringText(Arrays.asList(new String[]{Localization.get("gui.clickToModify")}), par1 - guiLeft, par2 - guiTop, getFontRenderer());
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, GuiEnums.GUI_DIAL.DIAL_A));
        }
        else if (button.id == 1) // save
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("id", ClientProxy.editingID);
            tag.setString("name", text.getText());
            tag.setString("uid", ClientProxy.saveGlyph.getGlyphString());
            ClientProxy.saveTexture.writeToNBT(tag, "texture");
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketGuiData(tag));
        }
        else if (button.id == 100)
        {
            isEditing = true;
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_A));
        }
        else if (button.id == 101)
        {
            isEditing = true;
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_B));
        }
        else if (button.id == 102)
        {
            isEditing = true;
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, GuiEnums.GUI_TEXTURE.TEXTURE_DIAL_EDIT_C));
        }
    }

    public void receivedData()
    {
        receivedData = true;
        text.setText(ClientProxy.saveName);
        display.setIdentifier(ClientProxy.saveGlyph);
    }
}
