package z3roco01.waterBowls.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static z3roco01.waterBowls.util.IdUtil.mkId;

public class RegisterItems {
    public static final Item WATER_BOWL = new Item(new FabricItemSettings());
    public static final Item HONEY_BOWL = new HoneyBottleItem(new FabricItemSettings().recipeRemainder(Items.BOWL).food(FoodComponents.HONEY_BOTTLE));

    public static void register() {
        Registry.register(Registries.ITEM, mkId("water_bowl"), WATER_BOWL);
        Registry.register(Registries.ITEM, mkId("honey_bowl"), HONEY_BOWL);
    }
}
