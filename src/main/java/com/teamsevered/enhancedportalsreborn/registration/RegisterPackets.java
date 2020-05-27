package com.teamsevered.enhancedportalsreborn.registration;

import com.teamsevered.enhancedportalsreborn.EnhancedPortalsReborn;
import com.teamsevered.enhancedportalsreborn.network.packet.*;

public class RegisterPackets
{
    public static final void preinit()
    {
        EnhancedPortalsReborn.packetPipeline.registerPacket(PacketRequestGui.class);
        EnhancedPortalsReborn.packetPipeline.registerPacket(PacketTextureData.class);
        EnhancedPortalsReborn.packetPipeline.registerPacket(PacketRerender.class);
        EnhancedPortalsReborn.packetPipeline.registerPacket(PacketGuiData.class);
        EnhancedPortalsReborn.packetPipeline.registerPacket(PacketGui.class);
    }
}
