package enhancedportals.utility;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabUtil extends CreativeTabs
{
    public TabUtil(String label)
    {
        super("epreborn");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Item.getItemFromBlock(Blocks.DIRT));
    }
}
