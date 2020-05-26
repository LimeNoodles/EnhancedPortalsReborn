package enhancedportals.tile;

import enhancedportals.Reference;
import enhancedportals.block.BlockFrame;
import enhancedportals.item.ItemNanobrush;
import enhancedportals.network.GuiHandler;
import enhancedportals.portal.GlyphElement;
import enhancedportals.utility.GeneralUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Random;

public class TileRedstoneInterface extends TileFrame
{
    // Determines if this instance of a RS interface is Output or Input.
    public boolean isOutput = false;
    public byte state = 0, previousRedstoneState = 0;
    byte timeUntilOff = 0;
    static int TPS = 20;
    public static byte MAX_INPUT_STATE = 8, MAX_OUTPUT_STATE = 8;

    @Override
    public boolean activate(EntityPlayer player, ItemStack stack, BlockPos blockPos)
    {
        if (player.isSneaking())
        {
            return false;
        }

        TileController controller = getPortalController();

        if (stack != null && controller != null && controller.isFinalized())
        {
            if (GeneralUtils.isWrench(stack) && !player.isSneaking())
            {
                GuiHandler.openGui(player, this, Reference.GuiEnums.GUI_MISC.REDSTONE_INTERFACE.ordinal());
                return true;
            }
            else if (stack.getItem() == ItemNanobrush.instance)
            {
                GuiHandler.openGui(player, controller, Reference.GuiEnums.GUI_TEXTURE.TEXTURE_A.ordinal());
                return true;
            }
        }

        return false;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {

    }

    /*@Override
    public boolean canUpdate()
    {
        return true;
    }*/

    int getHighestPowerState(BlockPos pos, EnumFacing side)
    {
        byte highest = 0;

        for (int i = 0; i < 6; i++)
        {
            ChunkPos c = GeneralUtils.offset(getChunkPos(), EnumFacing.getFront(i));
            byte power = (byte) world.getRedstonePower(pos, side);

            if (power > highest)
            {
                highest = power;
            }
        }

        return highest;
    }

    public int isProvidingPower(int side)
    {
        if (timeUntilOff != 0)
        {
            return 15;
        }

        return 0;
    }

    private void notifyNeighbors()
    {
//        world.notifyNeighborsOfStateChange(pos, BlockFrame.instance);

        for (int i = 0; i < 6; i++)
        {
            EnumFacing d = EnumFacing.getFront(i);
            //todo world.notifyNeighborsOfStateChange(pos, BlockFrame.instance);
        }
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {

    }

    public void onEntityTeleport(Entity entity)
    {
        // Check if this RS Interface block is an Output block.
        if (isOutput)
        {
            // Check to see if the entity in the portal is any entity (state 4), a player (state 5), animal (state 6), or "mob" (state 7)
            if (state == 4 || state == 5 && entity instanceof EntityPlayer || state == 6 && entity instanceof EntityAnimal || state == 7 && entity instanceof EntityMob)
            {
                timeUntilOff = (byte) TPS;
            }

            notifyNeighbors();
        }
    }

    public void onNeighborBlockChange(BlockPos blockPos, EnumFacing side)
    {
        if (!isOutput && !world.isRemote)
        {
            TileController controller = getPortalController();

            if (controller == null)
            {
                return;
            }

            boolean hasDialler = controller.getDiallingDevices().size() > 0;
            int redstoneInputState = getHighestPowerState(blockPos, side);

            // Input: Remove portal on signal
            if (state == 1)
            {
                if (redstoneInputState > 0 && controller.isPortalActive())
                {
                    controller.connectionTerminate();
                }
                else if (redstoneInputState == 0 && !controller.isPortalActive())
                {
                    controller.connectionDial();
                }
            }
            // Input: Remove Portal on Pulse
            else if (state == 3 && redstoneInputState > 0 && previousRedstoneState == 0 && controller.isPortalActive())
            {
                controller.connectionTerminate();
            }
            else if (!hasDialler) // These require no dialler
            {
                // Input: Create Portal on Signal
                if (state == 0)
                {
                    if (redstoneInputState > 0 && !controller.isPortalActive())
                    {
                        controller.connectionDial();
                    }
                    else if (redstoneInputState == 0 && controller.isPortalActive())
                    {
                        controller.connectionTerminate();
                    }
                }
                // Input: Create Portal on Pulse
                else if (state == 2 && redstoneInputState > 0 && previousRedstoneState == 0 && !controller.isPortalActive())
                {
                    controller.connectionDial();
                }
            }
            else
            {
                TileDialingDevice dialler = controller.getDialDeviceRandom();

                if (dialler == null)
                {
                    return;
                }

                int glyphCount = dialler.glyphList.size();

                if (glyphCount == 0)
                {
                    return;
                }

                // Input: Dial specific identifier
                if (state == 4 && redstoneInputState > 0 && !controller.isPortalActive())
                {
                    if (redstoneInputState - 1 < glyphCount)
                    {
                        GlyphElement e = dialler.glyphList.get(redstoneInputState - 1);
                        controller.connectionDial(e.identifier, e.texture, null);
                    }
                }
                // Input: Dial specific identifier II
                else if (state == 5)
                {
                    if (redstoneInputState > 0 && !controller.isPortalActive())
                    {
                        if (redstoneInputState - 1 < glyphCount)
                        {
                            GlyphElement e = dialler.glyphList.get(redstoneInputState - 1);
                            controller.connectionDial(e.identifier, e.texture, null);
                        }
                    }
                    else if (redstoneInputState == 0 && controller.isPortalActive())
                    {
                        controller.connectionTerminate();
                    }
                }
                // Input: Dial random identifier
                else if (state == 6 && redstoneInputState > 0 && !controller.isPortalActive())
                {
                    GlyphElement e = dialler.glyphList.get(new Random().nextInt(glyphCount));
                    controller.connectionDial(e.identifier, e.texture, null);
                }
                // Input: Dial random identifier II
                else if (state == 7)
                {
                    if (redstoneInputState > 0 && !controller.isPortalActive())
                    {
                        GlyphElement e = dialler.glyphList.get(new Random().nextInt(glyphCount));
                        controller.connectionDial(e.identifier, e.texture, null);
                    }
                    else if (redstoneInputState == 0 && controller.isPortalActive())
                    {
                        controller.connectionTerminate();
                    }
                }
            }
        }
    }

    public void onPortalCreated()
    {
        if (isOutput)
        {
            // Output: Portal Created
            if (state == 0)
            {
                timeUntilOff = (byte) TPS;
            }
            else if (state == 2)
            {
                timeUntilOff = -1;
            }
            else if (state == 3)
            {
                timeUntilOff = 0;
            }

            notifyNeighbors();
        }
    }

    public void onPortalRemoved()
    {
        if (isOutput)
        {
            // Output: Portal Removed
            if (state == 1)
            {
                timeUntilOff = (byte) TPS;
            }
            else if (state == 2)
            {
                timeUntilOff = 0;
            }
            else if (state == 3)
            {
                timeUntilOff = -1;
            }

            notifyNeighbors();
        }
    }

    @Override
    public void packetGuiFill(ByteBuf buffer)
    {
        buffer.writeBoolean(isOutput);
        buffer.writeByte(state);
    }

    @Override
    public void packetGuiUse(ByteBuf buffer)
    {
        isOutput = buffer.readBoolean();
        setState(buffer.readByte());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        isOutput = tagCompound.getBoolean("output");
        state = tagCompound.getByte("state");
        previousRedstoneState = tagCompound.getByte("previousRedstoneState");
        timeUntilOff = tagCompound.getByte("timeUntilOff");
    }

    public void setState(byte newState)
    {
        state = newState;

        if (timeUntilOff != 0)
        {
            timeUntilOff = 0;
            notifyNeighbors();
        }
    }

    //todo update entity check Furnace?

    @Override
    public void update()
    {

        if (isOutput)
        {
            if (timeUntilOff > 1)
            {
                timeUntilOff--;
            }
            else if (timeUntilOff == 1)
            {
                timeUntilOff--;
                notifyNeighbors(); // Make sure we update our neighbors
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("output", isOutput);
        tagCompound.setByte("state", state);
        tagCompound.setByte("previousRedstoneState", previousRedstoneState);
        tagCompound.setByte("timeUntilOff", timeUntilOff);

        return tagCompound;
    }

}
