package enhancedportals.block;

import enhancedportals.EnhancedPortals;
import enhancedportals.item.Items;
import enhancedportals.utility.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel
{
    public BlockBase(String name, Material material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(3);
        setResistance(100F);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 3);

        Blocks.BLOCKS.add(this);
        Items.ITEMS.add(new ItemBlock(this.setRegistryName(this.getRegistryName())));
    }

    @Override
    public void registerModels()
    {
        EnhancedPortals.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
