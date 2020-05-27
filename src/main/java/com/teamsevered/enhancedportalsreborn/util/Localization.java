package com.teamsevered.enhancedportalsreborn.util;

import com.mojang.realmsclient.gui.ChatFormatting;

import com.teamsevered.enhancedportalsreborn.util.Reference;

import net.minecraft.client.resources.I18n;

public class Localization
{
    public static String get(String s)
    {
        return I18n.format(Reference.MOD_ID + "." + s);
    }

    public static String getChatError(String s)
    {
        return I18n.format((Reference.MOD_ID + ".error." + s), ChatFormatting.RED + (Reference.MOD_ID + ".error") + ChatFormatting.WHITE);
    }

    public static String getChatSuccess(String s)
    {
        return I18n.format((Reference.MOD_ID + ".success." + s), ChatFormatting.GREEN + (Reference.MOD_ID + ".success") + ChatFormatting.WHITE);
    }

    public static String getChatNotify(String s) {
        return I18n.format((Reference.MOD_ID + ".notify." + s), ChatFormatting.YELLOW + (Reference.MOD_ID + ".notify") + ChatFormatting.WHITE);
    }
}
