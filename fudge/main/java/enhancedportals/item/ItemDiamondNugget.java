package enhancedportals.item;

public class ItemDiamondNugget extends ItemBase
{
    public static ItemDiamondNugget instance;

    public ItemDiamondNugget(String name)
    {
        super(name);
        setMaxStackSize(64);
        instance = this;
    }
}
