package com.teamsevered.enhancedportalsreborn.tile;

import com.teamsevered.enhancedportalsreborn.util.DimensionCoordinates;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;

public class TileEP extends TileEntity
{
    public ChunkPos getChunkPos()
    {
        return new ChunkPos(getPos());
    }

    public DimensionCoordinates getDimensionCoordinates()
    {
        return new DimensionCoordinates(getPos(), world.provider.getDimension());
    }

    public void packetGuiFill(ByteBuf buffer)
    {

    }

    public void packetGuiUse(ByteBuf buffer)
    {

    }
}
