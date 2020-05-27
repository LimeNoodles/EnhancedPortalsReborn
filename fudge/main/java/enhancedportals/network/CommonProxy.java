package enhancedportals.network;

import com.mojang.realmsclient.gui.ChatFormatting;

import enhancedportals.EnhancedPortals;
import enhancedportals.Reference;
import enhancedportals.portal.NetworkManager;
import enhancedportals.registration.*;
import enhancedportals.utility.ConfigurationHandler;

import enhancedportals.utility.LogHelper;
import enhancedportals.utility.TabUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import static enhancedportals.utility.ConfigurationHandler.CONFIG_RECIPES_VANILLA;
import static enhancedportals.utility.ConfigurationHandler.CONFIG_UPDATE_NOTIFIER;

public class CommonProxy
{
    public int gogglesRenderIndex = 0;
    public NetworkManager networkManager;

    public static String UPDATE_LATEST_VER;
    public static final CreativeTabs CREATIVE_TAB = new TabUtil("Enhanced Portals: Reborn");


    public void waitForController(ChunkPos controller, ChunkPos frame)
    {

    }

    public ArrayList<ChunkPos> getControllerList(ChunkPos controller)
    {
        return null;
    }

    public void clearControllerList(ChunkPos controller)
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
        return new File(getBaseDir(), DimensionManager.getWorld(0).getSaveHandler().getWorldDirectory().getAbsolutePath());
    }

    public void miscSetup()
    {
        //todo Add dungeon loot
        //ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ItemPortalModule.instance, 1, 4), 1, 1, 2));
    }

    public static boolean Notify(EntityPlayer player, String lateVers)
    {
        if (CONFIG_UPDATE_NOTIFIER)
        {
            //player.addChatMessage(new ChatComponentText(ChatFormatting.GREEN + "Your framework for tampering with subatomic particles has been rendered obsolete"));

            player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "[EnhancedPortals] " + ChatFormatting.WHITE + "has been updated to v" + lateVers + " :: You are running v" + Reference.MOD_VERSION));
            return true;
        }
        else
        {
            LogHelper.warn("You're using an outdated version (v" + Reference.MOD_VERSION + ")");
            return false;
        }
    }

    public void setupCrafting()
    {
        if (CONFIG_RECIPES_VANILLA)
        {
            //RegisterRecipes.initShaped();
            //RegisterRecipes.initShapeless();
        }
        /*if (CONFIG_RECIPES_TE && Loader.isModLoaded(Reference.Dependencies.MODID_THERMALEXPANSION))
        {
            ThermalExpansion.registerRecipes();
        }*/


        try
        {
            URL versionIn = new URL(Reference.ACCEPTED_VERSIONS);
            BufferedReader in = new BufferedReader(new InputStreamReader(versionIn.openStream()));
            UPDATE_LATEST_VER = in.readLine();

            if (FMLCommonHandler.instance().getSide() == Side.SERVER && !UPDATE_LATEST_VER.equals(Reference.MOD_VERSION))
            {
                LogHelper.info("You're using an outdated version (v" + Reference.MOD_VERSION + "). The newest " + "version is: " + UPDATE_LATEST_VER);
            }

        }
        catch (Exception e)
        {
            LogHelper.warn("Unable to get the latest version information");
            UPDATE_LATEST_VER = Reference.MOD_VERSION;
        }
    }

    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
}
