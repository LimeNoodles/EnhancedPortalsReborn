package enhancedportals;

public class Reference
{
    public static final String MOD_NAME = "Enhanced Portals: Reborn";
    public static final String MOD_ID = "epreborn";
    public static final String MOD_VERSION = "1.12.2-1.0.0";
    public static final String URL = "https://raw.githubusercontent.com/Solace7/enhancedportals/master/docs/VERSION";
    public static final String COMMON_PROXY = "enhancedportals.network.CommonProxy";
    public static final String SERVER_PROXY = "enhancedportals.network.ServerProxy";
    public static final String CLIENT_PROXY = "enhancedportals.network.ClientProxy";
    public static final String GUI_FACTORY_CLASS = "enhancedportals.client.gui.GuiFactory";
    public static final String MODID_OPENCOMPUTERS = "OpenComputers";
    public static final String MODID_COMPUTERCRAFT = "ComputerCraft";
    public static final String MODID_THERMALEXPANSION = "ThermalExpansion";
    public static final String DEPENDENCIES = "";
    public static final String ACCEPTED_VERSIONS = "1.12.2";
    public static boolean disableParticles = false;
    public static boolean disableSounds = false;
    public static boolean forceFrameOverlay = false;
    public static boolean portalDestroysBlocks = false;
    public static int activePortalsPerRow = 2;
    public static boolean recipeVanilla = true;
    public static boolean recipeTE = true;
    public static boolean requirePower = true;
    public static int initializationCost = 10000;
    public static int entityBaseCost = 1000;
    public static int keepAliveCost = 10;
    public static int potionIDFeatherfall = 40;
}

class GuiEnums
{
        public enum GUI_DIAL {
            DIAL_A, DIAL_B, DIAL_C, DIAL_D, DIAL_E, ADDRESS_DIAL
        }

        public enum GUI_ADDRESS_BOOK {
            ADDRESS_BOOK_A, ADDRESS_BOOK_B, ADDRESS_BOOK_C, ADDRESS_BOOK_D, ADDRESS_BOOK_E
        }

        public enum GUI_CONTROLLER {
            CONTROLLER_A, CONTROLLER_B
        }

        public enum GUI_TEXTURE {
            TEXTURE_A, TEXTURE_B, TEXTURE_C, TEXTURE_DIAL_EDIT_A, TEXTURE_DIAL_EDIT_B, TEXTURE_DIAL_EDIT_C, TEXTURE_DIAL_SAVE_A, TEXTURE_DIAL_SAVE_B, TEXTURE_DIAL_SAVE_C
        }

        public enum GUI_TRANFER {
            TRANSFER_FLUID, TRANSFER_ENERGY, TRANSFER_ITEM
        }

        public enum GUI_MISC {
            DIM_BRIDGE_STABILIZER, REDSTONE_INTERFACE, MODULE_MANIPULATOR, NETWORK_INTERFACE_A, NETWORK_INTERFACE_B
        }
    }
