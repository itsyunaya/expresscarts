package expresscarts.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import expresscarts.ExpressMinecartEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(MinecartItem.class)
public abstract class MinecartItemMixin {
    @Redirect(
            method = "useOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/vehicle/minecart/AbstractMinecart;createMinecart(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/vehicle/minecart/AbstractMinecart;")
    )
    private AbstractMinecart redirectMinecartCreation(Level level, double d, double e, double f, EntityType<AbstractMinecart> entityType, EntitySpawnReason entitySpawnReason, ItemStack itemStack, @Nullable Player player) {
        if ("NotModified".equals(itemStack.getHoverName().getString()) || !itemStack.is(Items.MINECART)) {
            return AbstractMinecart.createMinecart(level, d, e, f, entityType, entitySpawnReason, itemStack, player);
        } else {
            return ExpressMinecartEntity.createMinecart(level, d, e, f, entitySpawnReason, itemStack, player);
        }
    }

    @ModifyExpressionValue(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/minecart/AbstractMinecart;useExperimentalMovement(Lnet/minecraft/world/level/Level;)Z"))
    private boolean useExperimentalBehaviorForExpressMinecart(boolean original, @Local AbstractMinecart abstractMinecart) {
        return original || abstractMinecart instanceof ExpressMinecartEntity;
    }
}
