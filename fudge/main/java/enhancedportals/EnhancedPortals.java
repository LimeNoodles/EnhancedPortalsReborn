package enhancedportals;

import enhancedportals.network.CommonProxy;
import enhancedportals.network.GuiHandler;
import enhancedportals.network.PacketPipeline;
import enhancedportals.portal.NetworkManager;
import enhancedportals.registration.RegisterPotions;
import enhancedportals.utility.LogHelper;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.LoggerConfig;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = Reference.DEPENDENCIES)
public class EnhancedPortals
{
    public static final PacketPipeline packetPipeline = new PacketPipeline();

    @Instance(Reference.MOD_ID)
    public static EnhancedPortals instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;

    public EnhancedPortals()
    {
        LoggerConfig fml = new LoggerConfig(FMLCommonHandler.instance().getFMLLogger().getName(), Level.ALL, true);
        MinecraftForge.EVENT_BUS.register(this);
    }

    //PRE INITIALIZATION

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new enhancedportals.network.LogOnHandler());

        //WormholeTunnelManual.buildManual();
       // proxy.preInit(event);
        packetPipeline.initalise();
    }

    //INITIALIZATION

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("I am tampering with subatomic particles...what could go wrong?");
        proxy.miscSetup();
        proxy.setupCrafting();
       // proxy.init(event);
//        proxy.registerModelBakery();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        packetPipeline.postInitialise();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.networkManager = new NetworkManager(event);
    }


    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        PotionEffect effect = event.getEntityLiving().getActivePotionEffect(RegisterPotions.featherfallPotion);

        if (effect != null)
        {
            event.getEntityLiving().fallDistance = 0f;

            if (event.getEntityLiving().getActivePotionEffect(RegisterPotions.featherfallPotion).getDuration() <= 0)
            {
                event.getEntityLiving().removePotionEffect(RegisterPotions.featherfallPotion);
            }
        }
    }

    @SubscribeEvent
    public void worldSave(WorldEvent.Save event)
    {
        if (!event.getWorld().isRemote)
        {
            proxy.networkManager.saveAllData();
        }
    }
}
