package com.teamsevered.enhancedportalsreborn.client.gui.tabs;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.tile.TileStabilizerMain;
import com.teamsevered.enhancedportalsreborn.util.ConfigurationHandler;
import com.teamsevered.enhancedportalsreborn.util.GeneralUtils;
import com.teamsevered.enhancedportalsreborn.util.Localization;

public class TabRedstoneFlux extends BaseTab
{
    TileStabilizerMain stabilizer;

    public TabRedstoneFlux(BaseGui gui, TileStabilizerMain s)
    {
        super(gui);
        name = "tab.redstoneFlux";
        stabilizer = s;
        maxHeight = 110;
        titleColour = 0xDDDD00;
        backgroundColor = 0xDD6600;
    }

    @Override
    public void drawFullyOpened()
    {
        int instability = stabilizer.powerState == 0 ? stabilizer.instability : stabilizer.powerState == 1 ? 20 : stabilizer.powerState == 2 ? 50 : 70;
        int powerCost = (int) (stabilizer.intActiveConnections * ConfigurationHandler.CONFIG_REDSTONE_FLUX_COST * GeneralUtils.getPowerMultiplier());
        powerCost -= (int) (powerCost * (instability / 100f));

        parent.getFontRenderer().drawStringWithShadow(Localization.get("tab.redstoneFlux.maxPower"), posX + 10, posY + 20, 0xAAAAAA);
        parent.getFontRenderer().drawString(stabilizer.getEnergyStorage() + " RF", posX + 17, posY + 32, 0x000000);

        parent.getFontRenderer().drawStringWithShadow(Localization.get("tab.redstoneFlux.storedPower"), posX + 10, posY + 45, 0xAAAAAA);
        parent.getFontRenderer().drawString(stabilizer.getEnergyStorage() + " RF", posX + 17, posY + 57, 0x000000);

        parent.getFontRenderer().drawStringWithShadow(Localization.get("tab.redstoneFlux.powerUsage"), posX + 10, posY + 70, 0xAAAAAA);
        parent.getFontRenderer().drawString(powerCost + " RF/s", posX + 17, posY + 83, 0x000000);
        parent.getFontRenderer().drawString(powerCost / 20 + " RF/t", posX + 17, posY + 94, 0x000000);
    }

    @Override
    public void drawFullyClosed()
    {

    }
}
