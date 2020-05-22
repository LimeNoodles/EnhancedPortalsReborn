package enhancedportals.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import enhancedportals.utility.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

import enhancedportals.EnhancedPortals;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
    public static final int REDSTONE_FLUX_COST = 10000, REDSTONE_FLUX_TIMER = 20;
    public int gogglesRenderIndex = 0;
    public NetworkManager networkManager;
    public static boolean forceShowFrameOverlays, disableSounds, disableParticles, portalsDestroyBlocks, fasterPortalCooldown, requirePower, updateNotifier, vanillaRecipes, teRecipes;
    public static double powerMultiplier, powerStorageMultiplier;
    public static int activePortalsPerRow = 2;
    static Configuration config;
    static File craftingDir;
    public static String lateVers;

    public void waitForController(BlockPos controller, BlockPos frame)
    {

    }

    public ArrayList<BlockPos> getControllerList(BlockPos controller)
    {
        return null;
    }

    public void clearControllerList(BlockPos controller)
    {

    }

    public File getBaseDir()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getFile(".");
    }

    public File getResourcePacksDir()
    {
        return new File(getBaseDir(), "resourcepacks");
    }

    public File getWorldDir()
    {
        return new File(getBaseDir(), DimensionManager.getWorld(0).getSaveHandler().getWorldDirectory().getName());
    }

    public void registerItemRenderer(Item item, int meta, String id)
    {

    }

    public void setupConfiguration(File c)
    {
        config = new Configuration(c);
        craftingDir = new File(c.getParentFile(), "crafting");
        forceShowFrameOverlays = config.get("Misc", "ForceShowFrameOverlays", false).getBoolean(false);
        disableSounds = config.get("Overrides", "DisableSounds", false).getBoolean(false);
        disableParticles = config.get("Overrides", "DisableParticles", false).getBoolean(false);
        portalsDestroyBlocks = config.get("Portal", "PortalsDestroyBlocks", true).getBoolean(true);
        fasterPortalCooldown = config.get("Portal", "FasterPortalCooldown", false).getBoolean(false);
        requirePower = config.get("Power", "RequirePower", true).getBoolean(true);
        powerMultiplier = config.get("Power", "PowerMultiplier", 1.0).getDouble(1.0);
        powerStorageMultiplier = config.get("Power", "DBSPowerStorageMultiplier", 1.0).getDouble(1.0);
        activePortalsPerRow = config.get("Portal", "ActivePortalsPerRow", 2).getInt(2);
        updateNotifier = config.get("Misc", "NotifyOfUpdates", true).getBoolean(true);
        vanillaRecipes = config.get("Crafting", "Vanilla", true).getBoolean(true);
        teRecipes = config.get("Crafting", "ThermalExpansion", true).getBoolean(true);

        config.save();

        if (powerMultiplier < 0)
        {
            requirePower = false;
        }

        if (powerStorageMultiplier < 0.01)
        {
            powerStorageMultiplier = 0.01;
        }

        try
        {
            URL versionIn = new URL(Reference.UPDATE_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(versionIn.openStream()));
            lateVers = in.readLine();

            if (FMLCommonHandler.instance().getSide() == Side.SERVER && !lateVers.equals(Reference.VERSION))
            {
                EnhancedPortals.logger.info("You're using an outdated version (v" + Reference.VERSION + "). The newest version is: " + lateVers);
            }
        }
        catch (Exception e)
        {
            EnhancedPortals.logger.warn("Unable to get the latest version information");
            lateVers = Reference.VERSION;
        }
    }

    public static boolean Notify(EntityPlayer player, String lateVers)
    {
        if (updateNotifier == true)
        {
            player.sendMessage(new TextComponentString("Enhanced Portals has been updated to v" + lateVers + " :: You are running v" + Reference.VERSION));
            return true;
        }
        else
        {
            EnhancedPortals.logger.info("You're using an outdated version (v" + Reference.VERSION + ")");
            return false;
        }
    }
}
