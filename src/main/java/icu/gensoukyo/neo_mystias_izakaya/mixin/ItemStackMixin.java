package icu.gensoukyo.neo_mystias_izakaya.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "finishUsingItem", at = @At("TAIL"))
    public void tagFoodItemEvent(Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        // TODO 读组件 然后去触发 TagFoodItemEvent
    }
}
