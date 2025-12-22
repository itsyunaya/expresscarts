package expresscarts.mixin;

import expresscarts.ExpressCarts;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.MinecartDispenseItemBehavior;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
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
                    target = "Lnet/minecraft/world/entity/vehicle/minecart/AbstractMinecart;createMinecart(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/vehicle/minecart/AbstractMinecart;")
    )
    private AbstractMinecart redirectCreateMinecart(
            Level level,
            double d,
            double e,
            double f,
            EntityType<? extends AbstractMinecart> entityType,
            EntitySpawnReason entitySpawnReason,
            ItemStack itemStack,
            @Nullable Player player) {
        if (itemStack.is(Items.MINECART) && !"NotModified".equals(itemStack.getHoverName().getString())) {
            return AbstractMinecart.createMinecart(level, d, e, f, ExpressCarts.EXPRESS_MINECART_ENTITY, entitySpawnReason, itemStack, player);
        }

        return AbstractMinecart.createMinecart(level, d, e, f, entityType, entitySpawnReason, itemStack, player);
    }
}