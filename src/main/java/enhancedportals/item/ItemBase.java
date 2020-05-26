package enhancedportals.item;

import enhancedportals.EnhancedPortals;
import enhancedportals.network.CommonProxy;
import enhancedportals.utility.IHasModel;

import io.netty.buffer.ByteBuf;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IHasModel
{
    public ItemBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CommonProxy.CREATIVE_TAB);

        Items.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        EnhancedPortals.proxy.registerItemRenderer(this, 0, "inventory");
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
