package z3roco01.waterBowls;

import net.fabricmc.api.ModInitializer;

import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z3roco01.waterBowls.event.UseItem;
import z3roco01.waterBowls.item.RegisterItems;

public class WaterBowls implements ModInitializer {
    public static final String MOD_ID = "water_bowls";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Starting init !");

        RegisterItems.register();
        UseItem.register();

	    LOGGER.info("Finished init !");
    }
}
