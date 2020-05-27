package com.teamsevered.enhancedportalsreborn.inventory;


import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.items.ItemAddressBook;
import com.teamsevered.enhancedportalsreborn.portal.GlyphElement;
import com.teamsevered.enhancedportalsreborn.portal.GlyphIdentifier;
import com.teamsevered.enhancedportalsreborn.portal.PortalTextureManager;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerAddressEdit extends BaseContainer
{
    ItemAddressBook addressBook;

    public ContainerAddressEdit(ItemAddressBook b, InventoryPlayer p)
    {
        super(null, p);
        addressBook = b;
        hideInventorySlots();
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {
        if (tag.hasKey("id") && tag.hasKey("uid") && tag.hasKey("texture") && tag.hasKey("name"))
        {
            PortalTextureManager ptm = new PortalTextureManager();
            ptm.readFromNBT(tag, "texture");
            addressBook.glyphList.set(tag.getInteger("id"), new GlyphElement(tag.getString("name"), new GlyphIdentifier(tag.getString("uid")), ptm));
            player.openGui(EnhancedPortalsReborn.instance, GuiEnums.GUI_ADDRESS_BOOK.ADDRESS_BOOK_A.ordinal(), player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }
}
