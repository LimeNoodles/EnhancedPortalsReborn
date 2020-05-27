package enhancedportals.inventory.slot;

import enhancedportals.utility.IPortalModule;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPortalModule extends Slot
{
    public SlotPortalModule(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack s)
    {
        return s == null || s.getItem() instanceof IPortalModule;
    }
}
