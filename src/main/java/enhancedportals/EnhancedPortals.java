package enhancedportals;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.LoggerConfig;

import enhancedportals.network.LogOnHandler;
import enhancedportals.proxy.CommonProxy;
import enhancedportals.utility.Reference;
import enhancedportals.utility.TabUtil;


@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class EnhancedPortals
{
    public static final Logger logger = LogManager.getLogger("EnhancedPortals");
    public static final CreativeTabs enhancedPortalsTab = new TabUtil("Enhanced Portals: Reborn");

    @Mod.Instance
    public static EnhancedPortals instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static String localize(String s)
    {
        String s2 = I18n.translateToLocal(Reference.MOD_ID + "." + s).replace("<N>", "\n").replace("<P>", "\n\n");
        return s2.contains(Reference.MOD_ID + ".") || s2.contains("item.") || s2.contains("tile.") ? I18n.translateToLocal(s2) : s2;
    }

    public static String localizeError(String s)
    {
        return TextFormatting.RED + localize("error") + TextFormatting.WHITE + localize("error." + s);
    }

    public static String localizeSuccess(String s)
    {
        return TextFormatting.GREEN + localize("success") + TextFormatting.WHITE + localize("success." + s);
    }

    public EnhancedPortals()
    {
        LoggerConfig fml = new LoggerConfig(FMLCommonHandler.instance().getFMLLogger().getName(), Level.ALL, true);
        LoggerConfig modConf = new LoggerConfig(logger.getName(), Level.ALL, true);
        modConf.setParent(fml);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (event.getSide() == Side.CLIENT)
        {
            MinecraftForge.EVENT_BUS.register(new LogOnHandler());
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.setupConfiguration(new File(event.getSuggestedConfigurationFile().getParentFile(), Reference.MOD_NAME + File.separator + "config.cfg"));
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {

    }
}
