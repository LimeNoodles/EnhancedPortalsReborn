package com.teamsevered.enhancedportalsreborn.items;

import com.mojang.realmsclient.gui.ChatFormatting;

import com.teamsevered.enhancedportalsreborn.util.Reference;
import com.teamsevered.enhancedportalsreborn.util.Localization;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemStabilizer extends ItemBlock
{
    public ItemStabilizer(Block block)
    {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setRegistryName(block.getRegistryName());
        setUnlocalizedName(getRegistryName().toString());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    //@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(Localization.get("block.multiblockStructure"));
        list.add(ChatFormatting.DARK_GRAY + Localization.get("block.dbsSize"));
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
}
