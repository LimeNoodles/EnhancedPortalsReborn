package enhancedportals.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import enhancedportals.proxy.CommonProxy;
import enhancedportals.utility.Reference;

public class LogOnHandler
{
    @SubscribeEvent
    public void onLogIn(PlayerEvent.PlayerLoggedInEvent login)
    {
        if (login.player != null && !CommonProxy.lateVers.equals(Reference.VERSION))
        {
            EntityPlayer player = login.player;
            String lateVers = CommonProxy.lateVers;
            CommonProxy.Notify(player, lateVers);
        }
    }
}
