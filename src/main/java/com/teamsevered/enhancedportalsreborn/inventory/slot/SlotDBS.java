package com.teamsevered.enhancedportalsreborn.inventory.slot;

import com.teamsevered.enhancedportalsreborn.items.ItemLocationCard;
import com.teamsevered.enhancedportalsreborn.util.GeneralUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDBS extends Slot
{
    public SlotDBS(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack s)
    {
        return false;
        //todo //s == null || GeneralUtils.isEnergyContainerItem(s) || s.getItem() == ItemLocationCard.instance;
    }
}
