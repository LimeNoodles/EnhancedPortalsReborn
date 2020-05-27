package com.teamsevered.enhancedportalsreborn.tile;

import com.teamsevered.enhancedportalsreborn.util.GeneralUtils;
import com.teamsevered.enhancedportalsreborn.items.ItemNanobrush;
import com.teamsevered.enhancedportalsreborn.network.GuiHandler;
import com.teamsevered.enhancedportalsreborn.util.GeneralUtils;
import com.teamsevered.enhancedportalsreborn.util.GuiEnums;
import com.teamsevered.enhancedportalsreborn.util.Localization;
import com.teamsevered.enhancedportalsreborn.util.Reference;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.common.Optional.InterfaceList;
import net.minecraftforge.fml.common.Optional.Method;

@InterfaceList(value = {@Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = Reference.MODID_OPENCOMPUTERS)})
public class TileNetworkInterface extends TileFrame implements SimpleComponent
{
//    @Override
    public boolean activate(EntityPlayer player, ItemStack stack)
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
                if (controller.getIdentifierUnique() == null)
                {
                    if (!world.isRemote)
                    {
                        player.sendMessage(new TextComponentString(Localization.getChatError("noUidSet")));
                    }
                }
                else
                {
                    GuiHandler.openGui(player, controller, GuiEnums.GUI_MISC.NETWORK_INTERFACE_A.ordinal());
                }
            }
            else if (stack.getItem() == ItemNanobrush.instance)
            {
                GuiHandler.openGui(player, controller, GuiEnums.GUI_TEXTURE.TEXTURE_A.ordinal());
                return true;
            }
        }

        return false;
    }

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {

    }

//    @Override
    public boolean canUpdate()
    {
        return true;
    }


    //OpenComputers Methods

    @Callback(doc = "function():boolean -- Attempts to create a connection to the next portal in the network.")
    @Method(modid = Reference.MODID_OPENCOMPUTERS)
    public Object[] dial(Context context, Arguments args)
    {
        getPortalController().connectionDial();
        return new Object[]{true};
    }

    @Override
    @Method(modid = Reference.MODID_OPENCOMPUTERS)
    public String getComponentName()
    {
        return "ep_interface_network";
    }

//    @Override
    @Method(modid = Reference.MODID_OPENCOMPUTERS)
    public String[] getMethodNames()
    {
        return new String[]{"dial", "terminate"};
    }

//    @Override
    @Method(modid = Reference.MODID_OPENCOMPUTERS)
    public String getType()
    {
        return "ep_interface_network";
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {

    }

    @Callback(doc = "function():boolean -- Terminates any active connection.")
    @Method(modid = Reference.MODID_OPENCOMPUTERS)
    public Object[] terminate(Context context, Arguments args)
    {
        getPortalController().connectionTerminate();
        return new Object[]{true};
    }
}
