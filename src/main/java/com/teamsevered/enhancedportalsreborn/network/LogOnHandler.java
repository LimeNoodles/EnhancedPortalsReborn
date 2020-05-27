package com.teamsevered.enhancedportalsreborn.network;

import com.teamsevered.enhancedportalsreborn.proxy.CommonProxy;
import com.teamsevered.enhancedportalsreborn.util.Reference;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class LogOnHandler
{
    boolean displayed;

    @SubscribeEvent
    public void onLogIn(PlayerEvent.PlayerLoggedInEvent login)
    {
        if (!displayed && login.player != null && !CommonProxy.UPDATE_LATEST_VER.equals(Reference.VERSION))
        {
            EntityPlayer player = login.player;
            String lateVers = CommonProxy.UPDATE_LATEST_VER;
            CommonProxy.Notify(player, lateVers);
            displayed = true;
        }
    }
}
