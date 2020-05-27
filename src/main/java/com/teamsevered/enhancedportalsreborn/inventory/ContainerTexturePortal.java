package com.teamsevered.enhancedportalsreborn.inventory;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.client.gui.GuiTexturePortal;
import com.teamsevered.enhancedportalsreborn.items.ItemAddressBook;
import com.teamsevered.enhancedportalsreborn.tile.TileController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerTexturePortal extends BaseContainer
{
    protected TileController controller;
    protected ItemAddressBook addressBook;

    public ContainerTexturePortal(TileController c, InventoryPlayer p)
    {
        super(null, p, GuiTexturePortal.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize, 7);
        controller = c;
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("colour"))
        {
            controller.setPortalColour(tag.getInteger("colour"));
        }
        else if (tag.hasKey("custom"))
        {
            controller.setCustomPortalTexture(tag.getInteger("custom"));
        }
        else if (tag.hasKey("removeItem"))
        {
            controller.setPortalItem(null);
        }
        else if (tag.hasKey("id") && tag.hasKey("Damage"))
        {
           //todo  controller.setPortalItem(ItemStack.loadItemStackFromNBT(tag));
        }
    }
}
