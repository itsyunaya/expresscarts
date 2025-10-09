package expresscarts.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import expresscarts.ExpressMinecartEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(MinecartItem.class)
public abstract class MinecartItemMixin {
    @Redirect(
            method = "useOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/vehicle/AbstractMinecart;createMinecart(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/vehicle/AbstractMinecart;")
    )
    private AbstractMinecart redirectMinecartCreation(Level level, double x, double y, double z, EntityType<AbstractMinecart> type, EntitySpawnReason spawnReason, ItemStack spawnedFrom, Player player) {

        if ("NotModified".equals(spawnedFrom.getHoverName().getString())) {
            return AbstractMinecart.createMinecart(level, x, y, z, type, spawnReason, spawnedFrom, player);
        } else {
            return ExpressMinecartEntity.createMinecart(level, x, y, z, spawnReason, spawnedFrom, player);
        }
    }

    @ModifyExpressionValue(method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/AbstractMinecart;useExperimentalMovement(Lnet/minecraft/world/level/Level;)Z"))
    private boolean useExperimentalBehaviorForExpressMinecart(boolean original, @Local AbstractMinecart abstractMinecart) {
        return original || abstractMinecart instanceof ExpressMinecartEntity;
    }

}
