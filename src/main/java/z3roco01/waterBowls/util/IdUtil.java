package z3roco01.waterBowls.util;

import net.minecraft.util.Identifier;
import z3roco01.waterBowls.WaterBowls;

public class IdUtil {
    public static Identifier mkId(String id) {
        return new Identifier(WaterBowls.MOD_ID, id);
    }
}
