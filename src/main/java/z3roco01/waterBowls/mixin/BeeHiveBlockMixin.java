package z3roco01.waterBowls.mixin;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z3roco01.waterBowls.item.RegisterItems;

@Mixin(BeehiveBlock.class)
public abstract class BeeHiveBlockMixin
extends BlockWithEntity {
    @Shadow @Final public static IntProperty HONEY_LEVEL;

    @Shadow protected abstract boolean hasBees(World world, BlockPos pos);

    @Shadow protected abstract void angerNearbyBees(World world, BlockPos pos);

    @Shadow public abstract void takeHoney(World world, BlockState state, BlockPos pos);

    @Shadow public abstract void takeHoney(World world, BlockState state, BlockPos pos, @Nullable PlayerEntity player, BeehiveBlockEntity.BeeState beeState);

    protected BeeHiveBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "onUse", cancellable = true)
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack inHand = player.getStackInHand(hand);
        int honeyState = state.get(HONEY_LEVEL);
        if(honeyState >= 5 && inHand.isOf(Items.BOWL)) {
            inHand.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            player.getInventory().offerOrDrop(new ItemStack(RegisterItems.HONEY_BOWL));

            world.emitGameEvent((Entity)player, GameEvent.FLUID_PICKUP, pos);
            player.incrementStat(Stats.USED.getOrCreateStat(Items.BOWL));

            if (!CampfireBlock.isLitCampfireInRange(world, pos)) {
                if (this.hasBees(world, pos)) {
                    this.angerNearbyBees(world, pos);
                }
                this.takeHoney(world, state, pos, player, BeehiveBlockEntity.BeeState.EMERGENCY);
            } else {
                this.takeHoney(world, state, pos);
            }

            cir.setReturnValue(ActionResult.success(world.isClient));
            cir.cancel();
        }
    }
}
