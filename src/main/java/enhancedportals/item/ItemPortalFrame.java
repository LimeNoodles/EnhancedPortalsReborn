package enhancedportals.item;

import enhancedportals.block.BlockFrame;
import enhancedportals.utility.Localization;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemPortalFrame extends ItemBase
{
    public ItemPortalFrame(String name)
    {
        super(name);
        this.setMaxDamage(0);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    //@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        int damage = stack.getItemDamage();

        if (damage > 0)
        {
            list.add(Localization.get("block.portalFramePart"));
        }
    }

    @Override
    public int getMetadata(int metadata) {

        return metadata;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    //@Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
    {
        for (int i = 0; i < BlockFrame.FrameType.values().length; i++)
        {
            list.add(new ItemStack(par1, 1, i));
        }
    }
}
