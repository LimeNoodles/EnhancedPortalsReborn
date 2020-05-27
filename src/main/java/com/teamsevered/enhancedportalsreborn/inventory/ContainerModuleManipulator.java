package com.teamsevered.enhancedportalsreborn.inventory;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.client.gui.GuiModuleManipulator;
import com.teamsevered.enhancedportalsreborn.inventory.slot.SlotPortalModule;
import com.teamsevered.enhancedportalsreborn.tile.TilePortalManipulator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerModuleManipulator extends BaseContainer
{
    TilePortalManipulator module;

    // Called when accessing the Module Manipulator in-game.
    public ContainerModuleManipulator(TilePortalManipulator m, InventoryPlayer p)
    {
        super(m, p, GuiModuleManipulator.CONTAINER_SIZE + BaseGui.bufferSpace + BaseGui.playerInventorySize);
        module = m;

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new SlotPortalModule(module, i, 8 + i * 18, GuiModuleManipulator.CONTAINER_SIZE - 24));
        }
    }

    @Override
    public void handleGuiPacket(NBTTagCompound tag, EntityPlayer player)
    {

    }
}
