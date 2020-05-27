package com.teamsevered.enhancedportalsreborn.proxy;

import com.mojang.realmsclient.gui.ChatFormatting;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.portal.NetworkManager;
import com.teamsevered.enhancedportalsreborn.util.ConfigurationHandler;
import com.teamsevered.enhancedportalsreborn.util.LogHelper;
import com.teamsevered.enhancedportalsreborn.util.Reference;
import com.teamsevered.enhancedportalsreborn.util.TabUtil;
import static com.teamsevered.enhancedportalsreborn.util.ConfigurationHandler.CONFIG_RECIPES_VANILLA;
import static com.teamsevered.enhancedportalsreborn.util.ConfigurationHandler.CONFIG_UPDATE_NOTIFIER;

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

public class CommonProxy
{
    public static final CreativeTabs NETHER_ORES_TAB = new TabUtil("Enhanced Portals: Reborn");

    public int gogglesRenderIndex = 0;
    public NetworkManager networkManager;

    public static String UPDATE_LATEST_VER;

    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Common Proxy Pre-preInit");
        //RegisterPackets.preinit();
        //RegisterPotions.preinit();
        //RegisterTiles.preinit();
        ConfigurationHandler.setupConfiguration(new File(event.getSuggestedConfigurationFile().getParentFile(), Reference.MOD_NAME + File.separator + "config.cfg"));
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

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

            player.sendMessage(new TextComponentString(ChatFormatting.GOLD + "[EnhancedPortals] " + ChatFormatting.WHITE + "has been updated to v" + lateVers + " :: You are running v" + Reference.VERSION));
            return true;
        }
        else
        {
            LogHelper.warn("You're using an outdated version (v" + Reference.VERSION + ")");
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
            URL versionIn = new URL(Reference.UPDATE_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(versionIn.openStream()));
            UPDATE_LATEST_VER = in.readLine();

            if (FMLCommonHandler.instance().getSide() == Side.SERVER && !UPDATE_LATEST_VER.equals(Reference.VERSION))
            {
                LogHelper.info("You're using an outdated version (v" + Reference.VERSION + "). The newest " + "version is: " + UPDATE_LATEST_VER);
            }

        }
        catch (Exception e)
        {
            LogHelper.warn("Unable to get the latest version information");
            UPDATE_LATEST_VER = Reference.VERSION;
        }
    }

    public void registerItemRenderer(Item item, int meta, String id)
    {

    }
}
