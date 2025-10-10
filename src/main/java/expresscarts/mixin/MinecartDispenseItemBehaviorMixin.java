package expresscarts.mixin;

import expresscarts.ExpressCarts;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.MinecartDispenseItemBehavior;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecartDispenseItemBehavior.class)
public class MinecartDispenseItemBehaviorMixin extends DefaultDispenseItemBehavior {

    @Redirect(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/vehicle/AbstractMinecart;createMinecart(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/vehicle/AbstractMinecart;"
            )
    )
    private AbstractMinecart redirectCreateMinecart(
            Level level,
            double x,
            double y,
            double z,
            EntityType<? extends AbstractMinecart> type,
            EntitySpawnReason spawnReason,
            ItemStack spawnedFrom,
            @Nullable Player player) {

        if (spawnedFrom.is(Items.MINECART) && !"NotModified".equals(spawnedFrom.getHoverName().getString())) {
            return AbstractMinecart.createMinecart(level, x, y, z, ExpressCarts.EXPRESS_MINECART_ENTITY, spawnReason, spawnedFrom, player);
        }

        return AbstractMinecart.createMinecart(level, x, y, z, type, spawnReason, spawnedFrom, player);
    }
}