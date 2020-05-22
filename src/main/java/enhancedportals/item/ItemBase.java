package enhancedportals.item;

import enhancedportals.EnhancedPortals;
import enhancedportals.utility.IHasModel;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
    public ItemBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(EnhancedPortals.enhancedPortalsTab);

        Items.ITEMS.add(this);
    }

    @Override
    public void registerModels()

    {
        EnhancedPortals.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
