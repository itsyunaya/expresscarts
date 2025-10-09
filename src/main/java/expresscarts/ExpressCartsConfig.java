package expresscarts;

import dev.xpple.betterconfig.api.Config;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class ExpressCartsConfig {
    @Config(comment = "Maximum speed of Express Minecarts, in blocks per second.")
    public static double maxMinecartSpeed = 16;

    @Config(comment = "Multiplier to the max minecart speed when in water.")
    public static double waterSpeedMultiplier = 0.5;

    @Config(comment = "Whether players may hold the backwards movement key to brake.")
    public static boolean brakingEnabled = true;

    @Config(comment = "Amount by which an Express Minecart's velocity is scaled each tick its passenger holds the brakes.")
    public static double brakeSlowdown = 0.8;

    @Config(comment = "Max speed multipliers when the rails are on certain blocks.")
    public static Map<Block, Double> blockSpeedMultipliers = new HashMap<>();
}
