package enhancedportals.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Blocks
{
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block BORDERED_QUARTZ = new BlockBase("bordered_quartz", Material.IRON);
    public static final Block DBS = new BlockBase("dbs", Material.IRON);
    public static final Block DBS_EMPTY = new BlockBase("dbs_empty", Material.IRON);
    public static final Block DIALLING_DEVICE = new BlockBase("dialling_device", Material.IRON);
    public static final Block ENDER_INFUSED_METAL = new BlockBase("ender_infused_metal", Material.IRON);
    public static final Block ENERGY_TRANSPORTATION_MODULE = new BlockBase("energy_transportation_module", Material.IRON);
    public static final Block FLUID_TRANSPORTATION_MODULE = new BlockBase("fluid_transportation_module", Material.IRON);
    public static final Block ITEM_TRANSPORTATION_MODULE = new BlockBase("item_transportation_module", Material.IRON);
    public static final Block NETWORK_INTERFACE = new BlockBase("network_interface", Material.IRON);
    public static final Block PORTAL = new BlockBase("portal", Material.IRON);
    public static final Block PORTAL_CONTROLLER = new BlockBase("portal_controller", Material.IRON);
    public static final Block PORTAL_FRAME = new BlockBase("portal_frame", Material.IRON);
    public static final Block PORTAL_MANIPULATOR = new BlockBase("portal_manipulator", Material.IRON);
    public static final Block PORTAL_STABILIZER = new BlockBase("portal_stabilizer", Material.IRON);
    public static final Block PROGRAMMABLE_INTERFACE = new BlockBase("programmable_interface", Material.IRON);
    public static final Block REDSTONE_INTERFACE = new BlockBase("redstone_interface", Material.IRON);
}
