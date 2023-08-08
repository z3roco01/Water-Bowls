package z3roco01.waterBowls.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneyBottleItem.class)
public abstract class HoneyBottleItemMixin
extends Item {
    public HoneyBottleItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "finishUsing", cancellable = true)
    public void finishUsingReturn(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if(cir.getReturnValue().getItem() == Items.GLASS_BOTTLE) cir.setReturnValue(new ItemStack(this.getRecipeRemainder()));    }
}
