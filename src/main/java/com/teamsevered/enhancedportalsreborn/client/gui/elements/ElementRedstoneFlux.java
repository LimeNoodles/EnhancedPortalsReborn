package com.teamsevered.enhancedportalsreborn.client.gui.elements;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.energy.EnergyStorage;

import org.lwjgl.opengl.GL11;

public class ElementRedstoneFlux extends BaseElement
{
    EnergyStorage s;

    public ElementRedstoneFlux(BaseGui gui, int x, int y, EnergyStorage es)
    {
        super(gui, x, y, 14, 42);
        s = es;
        texture = new ResourceLocation("enhancedportals", "textures/gui/elements.png");
    }

    @Override
    public void addTooltip(List<String> list)
    {
        list.add(s.getEnergyStored() + " / " + s.getMaxEnergyStored() + " RF");
    }

    @Override
    protected void drawContent()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        parent.getTextureManager().bindTexture(texture);
        parent.drawTexturedModalRect(posX, posY, 228, 0, sizeX, sizeY);

        if (!isDisabled())
        {
            int height = 0, currentProgress = s.getEnergyStored(), maxProgress = s.getMaxEnergyStored();

            if (currentProgress > 0)
            {
                height = Math.round((float) currentProgress * sizeY / maxProgress);
            }

            parent.drawTexturedModalRect(posX, posY + sizeY - height, 228 + sizeX, sizeY - height, sizeX, height);
        }
    }

    @Override
    public void update()
    {

    }
}
