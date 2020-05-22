package enhancedportals.utility;

import enhancedportals.EnhancedPortals;
import enhancedportals.items.ItemBase;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler
{
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, EnhancedPortals.MOD_ID);
    //public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, EnhancedPortals.MOD_ID);

    public static void init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Items

    public static final RegistryObject<Item> BLANK_PORTAL_MODULE = ITEMS.register("blank_portal_module", ItemBase::new);
    public static final RegistryObject<Item> BLANK_UPGRADE = ITEMS.register("blank_upgrade", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_FRAME = ITEMS.register("portal_frame", ItemBase::new);
    public static final RegistryObject<Item> GLASSES = ITEMS.register("glasses", ItemBase::new);
    public static final RegistryObject<Item> LOCATION_CARD = ITEMS.register("location_card", ItemBase::new);
    public static final RegistryObject<Item> MANUAL = ITEMS.register("manual", ItemBase::new);
    public static final RegistryObject<Item> NANO_BRUSH = ITEMS.register("nano_brush", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE = ITEMS.register("portal_module", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_STABILIZER = ITEMS.register("portal_stabilizer", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE = ITEMS.register("portal_upgrade", ItemBase::new);
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", ItemBase::new);
}
