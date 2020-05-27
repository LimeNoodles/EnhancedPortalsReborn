package enhancedportals.registration;

import enhancedportals.block.Blocks;
import enhancedportals.item.Items;
import enhancedportals.utility.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler
{
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(Items.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(Blocks.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for(Block block : Blocks.BLOCKS)
        {
            if(block instanceof IHasModel)
            {
                ((IHasModel) block).registerModels();
            }
        }

        for(Item item : Items.ITEMS)
        {
            if(item instanceof IHasModel)
            {
                ((IHasModel) item).registerModels();
            }
        }
    }
}
