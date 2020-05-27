package com.teamsevered.enhancedportalsreborn.items;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.init.Items;
import com.teamsevered.enhancedportalsreborn.proxy.CommonProxy;
import com.teamsevered.enhancedportalsreborn.util.IHasModel;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IHasModel
{
    public ItemBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CommonProxy.NETHER_ORES_TAB);

        Items.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        EnhancedPortalsReborn.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public void packetGuiFill(ByteBuf buffer)
    {

    }

    public void packetGuiUse(ByteBuf buffer)
    {

    }

    public void writeToNBT(ItemStack itemStack)
    {

    }

    public void readFromNBT(ItemStack itemStack)
    {

    }
}
