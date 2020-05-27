package com.teamsevered.enhancedportalsreborn.inventory;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketGui;
import com.teamsevered.enhancedportalsreborn.network.packet.PacketTextureData;
import com.teamsevered.enhancedportalsreborn.portal.GlyphElement;
import com.teamsevered.enhancedportalsreborn.tile.TileDialingDevice;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class ContainerDialingDevice extends BaseContainer
{
    TileDialingDevice dial;
    ArrayList<String> list = new ArrayList<String>();

    public ContainerDialingDevice(TileDialingDevice d, InventoryPlayer p)
    {
        super(null, p);
        dial = d;
        hideInventorySlots();
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (list.size() != dial.glyphList.size())
        {
            updateAll();
        }
        else
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (!list.get(i).equals(dial.glyphList.get(i).name))
                {
                    updateAll();
                    break;
                }
            }
        }

        list.clear();

        for (GlyphElement e : dial.glyphList)
        {
            list.add(e.name);
        }
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("terminate"))
        {
            dial.getPortalController().connectionTerminate();
        }
        else if (tag.hasKey("dial"))
        {
            int id = tag.getInteger("dial");

            if (dial.glyphList.size() > id)
            {
                GlyphElement e = dial.glyphList.get(id);
                dial.getPortalController().connectionDial(e.identifier, e.texture, player);
            }
        }
        else if (tag.hasKey("edit"))
        {
            int id = tag.getInteger("edit");

            if (dial.glyphList.size() > id)
            {
                GlyphElement e = dial.glyphList.get(id);
                player.openGui(EnhancedPortalsReborn.instance, GuiEnums.GUI_DIAL.DIAL_D.ordinal(), dial.getWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
                EnhancedPortalsReborn.packetPipeline.sendTo(new PacketTextureData(e.name, e.identifier.getGlyphString(), e.texture), (EntityPlayerMP) player);
            }
        }
        else if (tag.hasKey("delete"))
        {
            int id = tag.getInteger("delete");

            if (dial.glyphList.size() > id)
            {
                dial.glyphList.remove(id);
            }

            EnhancedPortalsReborn.packetPipeline.sendTo(new PacketGui(dial), (EntityPlayerMP) player);
        }
    }

    void updateAll()
    {
        for (int i = 0; i < this.listeners.size(); i++)
        {
            IContainerListener iContainerListener = (IContainerListener) listeners.get(i);
            EnhancedPortalsReborn.packetPipeline.sendTo(new PacketGui(dial), (EntityPlayerMP) iContainerListener);
        }
    }
}
