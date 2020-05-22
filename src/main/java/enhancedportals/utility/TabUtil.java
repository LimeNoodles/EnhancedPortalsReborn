package enhancedportals.utility;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import enhancedportals.item.Items;

public class TabUtil extends CreativeTabs
{
    public TabUtil(String label)
    {
        super(Reference.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Items.BLANK_PORTAL_MODULE);
    }
}
