package enhancedportals.item;

import enhancedportals.network.CommonProxy;

public class ItemBlankPortalModule extends ItemBase
{
    public static ItemBlankPortalModule instance;

    public ItemBlankPortalModule(String name)
    {
        super(name);
        instance = this;
    }
    
}
