package z3roco01.waterBowls.event;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import z3roco01.waterBowls.item.RegisterItems;

public class UseItem {
    public static void register() {
        UseItemCallback.EVENT.register(UseItem::event);
    }

    private static TypedActionResult event(PlayerEntity player, World world, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if(player.isSpectator() || handStack.getItem() != Items.BOWL) return TypedActionResult.pass(handStack);

        BlockHitResult hitResult = Item.raycast(world, player, RaycastContext.FluidHandling.WATER);
        if(hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            if(world.getFluidState(pos).isIn(FluidTags.WATER)) {
                handStack.decrement(1);
                ItemStack waterBowlItemStack = new ItemStack(RegisterItems.WATER_BOWL);
                player.getInventory().offerOrDrop(waterBowlItemStack);

                return TypedActionResult.success(waterBowlItemStack);
            }
        }

        return TypedActionResult.pass(handStack);
    }
}
