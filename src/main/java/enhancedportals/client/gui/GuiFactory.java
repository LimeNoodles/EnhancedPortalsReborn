package enhancedportals.client.gui;

import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

public class GuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance)
    {

    }

    @Override
    public boolean hasConfigGui() {
        return false;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return null;
    }

    //@Override
    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return GuiEPConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }

    //@Override
    //public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
    //{
        //return null;
    //}
}
