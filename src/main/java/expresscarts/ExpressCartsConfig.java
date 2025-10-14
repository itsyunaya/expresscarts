package expresscarts;

import dev.xpple.betterconfig.api.Config;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class ExpressCartsConfig {
    @Config
    public static double maxMinecartSpeed = 16;

    @Config
    public static double waterSpeedMultiplier = 0.5;

    @Config
    public static boolean brakingEnabled = true;

    @Config
    public static double brakeSlowdown = 0.8;

    @Config
    public static Map<Block, Double> blockSpeedMultipliers = new HashMap<>();
}
