package com.teamsevered.enhancedportalsreborn.client.gui;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.client.gui.elements.ElementScrollDiallingDevice;
import com.teamsevered.enhancedportalsreborn.client.gui.tabs.TabTip;
import com.teamsevered.enhancedportalsreborn.inventory.ContainerDialingDevice;
import com.teamsevered.enhancedportalsreborn.proxy.ClientProxy;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketGuiData;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketRequestGui;
import com.teamsevered.enhancedportalsreborn.tile.TileController;
import com.teamsevered.enhancedportalsreborn.tile.TileDialingDevice;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.util.Localization;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiDialingDevice extends BaseGui
{
    public static final int CONTAINER_SIZE = 175, CONTAINER_WIDTH = 256;
    TileDialingDevice dial;
    TileController controller;
    GuiButton buttonDial;

    public GuiDialingDevice(TileDialingDevice d, EntityPlayer p)
    {
        super(new ContainerDialingDevice(d, p.inventory), CONTAINER_SIZE);
        texture = new ResourceLocation("enhancedportals", "textures/gui/dialling_device.png");
        xSize = CONTAINER_WIDTH;
        dial = d;
        controller = dial.getPortalController();
        name = "gui.dialDevice";
        setHidePlayerInventory();
    }

    @Override
    public void initGui()
    {
        super.initGui();

        buttonDial = new GuiButton(1, guiLeft + xSize - 147, guiTop + ySize - 27, 140, 20, Localization.get("gui.terminate"));
        buttonDial.enabled = controller.isPortalActive();
        buttonList.add(new GuiButton(0, guiLeft + 7, guiTop + ySize - 27, 100, 20, Localization.get("gui.manualEntry")));
        buttonList.add(buttonDial);

        addElement(new ElementScrollDiallingDevice(this, dial, 7, 28));
        addTab(new TabTip(this, "dialling"));
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        buttonDial.enabled = controller.isPortalActive();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        getFontRenderer().drawString(Localization.get("gui.storedIdentifiers"), 7, 18, 0x404040);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0) // Manual Entry
        {
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketRequestGui(dial, GuiEnums.GUI_DIAL.DIAL_B)); // Open DialingManual

        }
        else if (button.id == 1) // Terminate
        {
            if (controller.isPortalActive())
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setBoolean("terminate", true);
                EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketGuiData(tag));
            }
        }
    }

    public void onEntrySelected(int entry)
    {
        if (!controller.isPortalActive())
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("dial", entry);
            EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketGuiData(tag));
            Minecraft.getMinecraft().player.closeScreen();
        }
    }

    public void onEntryEdited(int entry)
    {
        ClientProxy.editingID = entry;
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("edit", entry);
        EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketGuiData(tag));
    }

    public void onEntryDeleted(int entry)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("delete", entry);
        EnhancedPortalsReborn.packetPipeline.sendToServer(new PacketGuiData(tag));
    }
}
