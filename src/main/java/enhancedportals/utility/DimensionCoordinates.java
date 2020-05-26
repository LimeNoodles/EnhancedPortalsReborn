package enhancedportals.utility;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class DimensionCoordinates extends ChunkPos
{
    public int dimension;

    public DimensionCoordinates(int x, int z, int dim)
    {
        super(x, z);
        dimension = dim;
    }

    public DimensionCoordinates(BlockPos pos, int dim)
    {
        super(pos);
        dimension = dim;
    }

    public DimensionCoordinates(NBTTagCompound tag)
    {
        this(tag.getInteger("X"), tag.getInteger("Z"), tag.getInteger("D"));
    }

    public DimensionCoordinates(DimensionCoordinates coord)
    {
        super(coord.x, coord.z);
        dimension = coord.dimension;
    }

    public BlockPos getBlock(int x, int y, int z)
    {
        WorldServer world = getWorld();

        if (!world.getChunkProvider().chunkExists(this.x >> 4, this.z >> 4))
        {
            world.getChunkProvider().loadChunk(this.x >> 4, this.z >> 4);
        }

        return new BlockPos(this.x << 4 + x, y, (this.z << 4) + z);
    }

    public TileEntity getTileEntity(BlockPos pos)
    {
        WorldServer world = getWorld();

        if (world == null)
        {
            DimensionManager.initDimension(dimension);
            world = DimensionManager.getWorld(dimension);

            if (world == null)
            {
                return null; // How?
            }
        }

        if (!world.getChunkProvider().chunkExists(this.x >> 4, this.z >> 4))
        {
            world.getChunkProvider().loadChunk(this.x >> 4, this.z >> 4);
        }

        return world.getTileEntity(pos);
    }

    public WorldServer getWorld()
    {
        WorldServer world = DimensionManager.getWorld(dimension);

        if (world == null)
        {
            DimensionManager.initDimension(dimension);
            world = DimensionManager.getWorld(dimension);

            if (world == null)
            {
                return null; // How?
            }
        }

        return world;
    }

    public DimensionCoordinates offset(EnumFacing facing)
    {
        return new DimensionCoordinates(this.x + facing.getFrontOffsetX(), this.z + facing.getFrontOffsetZ(), dimension);
    }

    @Override
    public String toString()
    {
        return String.format("WorldCoordinates (X %s, Y %s, Z %s, D %s)", this.x, this.z, dimension);
    }

    public void writeToNBT(NBTTagCompound t)
    {
        t.setInteger("X", this.x);
        t.setInteger("Z", this.z);
        t.setInteger("D", dimension);
    }
}
