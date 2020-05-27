package com.teamsevered.enhancedportalsreborn.client.gui.tabs;

import com.teamsevered.enhancedportalsreborn.client.gui.BaseGui;
import com.teamsevered.enhancedportalsreborn.client.gui.button.GuiRGBSlider;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class TabColour extends BaseTab
{
    GuiRGBSlider sliderR, sliderG, sliderB;
    GuiButton buttonSave, buttonReset;

    public TabColour(BaseGui gui, GuiRGBSlider r, GuiRGBSlider g, GuiRGBSlider b, GuiButton s, GuiButton rs)
    {
        super(gui);
        backgroundColor = 0x5396da;
        maxHeight += 89;
        maxWidth = 116;
        name = "gui.colour";
        //icon = ItemNanobrush.texture;
        sliderR = r;
        sliderG = g;
        sliderB = b;
        buttonSave = s;
        buttonReset = rs;
    }

//    @Override
    public void draw()
    {
//        super.draw();
        sliderR.visible = sliderG.visible = sliderB.visible = buttonSave.visible = buttonReset.visible = isFullyOpened();
    }

    @Override
    public void drawFullyOpened()
    {
        Gui.drawRect(posX + 2, posY + 19, posX + 3 + maxWidth - 8, posY + 21 + maxHeight - 26, 0x66000000);
    }

    @Override
    public void drawFullyClosed()
    {

    }

    @Override
    public boolean handleMouseClicked(int x, int y, int mouseButton)
    {
        x += parent.getGuiLeft();
        y += parent.getGuiTop();

        if (x >= posX + 3 && x < posX + 3 + maxWidth - 7 && y >= posY + 21 && y < posY + 21 + maxHeight - 25)
        {
            return true;
        }

        return super.handleMouseClicked(x, y, mouseButton);
    }
}
