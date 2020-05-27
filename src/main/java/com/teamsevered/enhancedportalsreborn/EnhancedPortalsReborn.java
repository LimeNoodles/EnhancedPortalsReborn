package com.teamsevered.enhancedportalsreborn;


import com.teamsevered.enhancedportalsreborn.network.GuiHandler;
import com.teamsevered.enhancedportalsreborn.network.PacketPipeline;
import com.teamsevered.enhancedportalsreborn.portal.NetworkManager;
import com.teamsevered.enhancedportalsreborn.proxy.CommonProxy;
import com.teamsevered.enhancedportalsreborn.registration.RegisterPotions;
import com.teamsevered.enhancedportalsreborn.util.LogHelper;
import com.teamsevered.enhancedportalsreborn.util.Reference;

import java.util.logging.Logger;

import net.minecraft.potion.PotionEffect;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.LoggerConfig;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class EnhancedPortalsReborn
{
	public static final PacketPipeline packetPipeline = new PacketPipeline();

	private static Logger logger;

	@Mod.Instance
	public static EnhancedPortalsReborn instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS ,serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public EnhancedPortalsReborn()
	{
		LoggerConfig fml = new LoggerConfig(FMLCommonHandler.instance().getFMLLogger().getName(), Level.ALL, true);
		MinecraftForge.EVENT_BUS.register(this);
	}

	//PRE INITIALIZATION

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new com.teamsevered.enhancedportalsreborn.network.LogOnHandler());

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
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
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
