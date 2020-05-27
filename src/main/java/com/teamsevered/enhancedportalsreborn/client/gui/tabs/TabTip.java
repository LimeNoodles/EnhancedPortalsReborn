package com.teamsevered.enhancedportalsreborn.client.gui.tabs;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.util.Localization;

import java.util.List;

public class TabTip extends BaseTab
{
    String tip;

    public TabTip(BaseGui gui, String n)
    {
        super(gui, 0);
        name = "tab." + n;
        tip = Localization.get("tab." + n + ".info").replace("<NL>", "\n").replaceAll("<([^<]*)>", "\u00A7$1");
        List l = gui.getFontRenderer().listFormattedStringToWidth(tip, maxWidth - 14);
        maxHeight = l.size() * gui.getFontRenderer().FONT_HEIGHT + 25;
        backgroundColor = 0x33AA00;
    }

    @Override
    public void drawFullyOpened()
    {
        parent.getFontRenderer().drawSplitString(tip, posX - maxWidth + 7, posY + 20, maxWidth - 14, 0x000000);
    }

    @Override
    public void drawFullyClosed()
    {

    }
}
