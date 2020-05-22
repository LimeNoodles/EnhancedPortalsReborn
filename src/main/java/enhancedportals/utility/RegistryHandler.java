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
    public static final RegistryObject<Item> GLASSES = ITEMS.register("glasses", ItemBase::new);
    public static final RegistryObject<Item> LOCATION_CARD = ITEMS.register("location_card", ItemBase::new);
    public static final RegistryObject<Item> MANUAL = ITEMS.register("manual", ItemBase::new);
    public static final RegistryObject<Item> NANO_BRUSH = ITEMS.register("nano_brush", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_CLOAKING = ITEMS.register("pm_cloaking", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_FACING = ITEMS.register("pm_facing", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_FEATHER_FALL = ITEMS.register("pm_feather_fall", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_MOMENTUM = ITEMS.register("pm_momentum", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_PARTICLE_DESTROYER = ITEMS.register("pm_particle_destroyer", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_RAINBOW_PARTICLES = ITEMS.register("pm_particle_rainbow", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_TINT_PARTICLES = ITEMS.register("pm_particle_tint", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_MODULE_SILENCER = ITEMS.register("pm_silencer", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_STABILIZER = ITEMS.register("portal_stabilizer", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE_DIALLING_DEVICE = ITEMS.register("pu_dialling_device", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE_NETWORK_INTERFACE = ITEMS.register("pu_network_interface", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE_PORTAL_MANIPULATOR = ITEMS.register("pu_portal_manipulator", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE_PROGRAMMABLE_INTERFACE = ITEMS.register("pu_programmable_interface", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE_REDSTONE_INTERFACE = ITEMS.register("pu_redstone_interface", ItemBase::new);;
    public static final RegistryObject<Item> PORTAL_UPGRADE_ENERGY_TRANSPORTATION = ITEMS.register("pu_energy_transportation", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE_FLUID_TRANSPORTATION = ITEMS.register("pu_fluid_transportation", ItemBase::new);
    public static final RegistryObject<Item> PORTAL_UPGRADE_ITEM_TRANSPORTATION = ITEMS.register("pu_item_transportation", ItemBase::new);
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", ItemBase::new);
}
