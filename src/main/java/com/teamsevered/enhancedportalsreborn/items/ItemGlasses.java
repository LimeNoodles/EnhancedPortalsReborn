package com.teamsevered.enhancedportalsreborn.items;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemGlasses extends ItemArmor
{
    public static ItemGlasses instance;


    public ItemGlasses(String n)
    {
        super(ArmorMaterial.LEATHER, EnhancedPortalsReborn.proxy.gogglesRenderIndex, EntityEquipmentSlot.HEAD);
        instance = this;
    }

    @Override
    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2)
    {
        return false;
    }
}
