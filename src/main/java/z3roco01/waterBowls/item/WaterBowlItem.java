package z3roco01.waterBowls.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class WaterBowlItem extends Item {
    public WaterBowlItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);

        if(player.isSpectator()) return TypedActionResult.pass(handStack);

        BlockHitResult hitResult = Item.raycast(world, player, RaycastContext.FluidHandling.WATER);
        if(hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            if(world.getFluidState(pos).isIn(FluidTags.WATER)) {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);

                return TypedActionResult.success(ItemUsage.exchangeStack(handStack, player, new ItemStack(Items.BOWL, 1)));
            }
        }

        return TypedActionResult.pass(handStack);
    }
}
