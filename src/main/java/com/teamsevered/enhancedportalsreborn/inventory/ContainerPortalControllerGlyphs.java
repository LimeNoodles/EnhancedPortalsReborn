package com.teamsevered.enhancedportalsreborn.inventory;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.client.gui.GuiPortalControllerGlyphs;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketGuiData;
import com.teamsevered.enhancedportalsreborn.portal.GlyphIdentifier;
import com.teamsevered.enhancedportalsreborn.portal.PortalException;
import com.teamsevered.enhancedportalsreborn.tile.TileController;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class ContainerPortalControllerGlyphs extends BaseContainer
{
    TileController controller;

    public ContainerPortalControllerGlyphs(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiPortalControllerGlyphs.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        controller = c;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("uid"))
        {
            try
            {
                controller.setIdentifierUnique(new GlyphIdentifier(tag.getString("uid")));
                player.openGui(EnhancedPortalsReborn.instance, GuiEnums.GUI_CONTROLLER.CONTROLLER_A.ordinal(), controller.getWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
            }
            catch (PortalException e)
            {
                NBTTagCompound errorTag = new NBTTagCompound();
                errorTag.setInteger("error", 0);
                EnhancedPortalsReborn.packetPipeline.sendTo(new PacketGuiData(errorTag), (EntityPlayerMP) player);
            }
        }
        else if (tag.hasKey("error") && FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            ((GuiPortalControllerGlyphs) Minecraft.getMinecraft().currentScreen).setWarningMessage();
        }
    }
}
