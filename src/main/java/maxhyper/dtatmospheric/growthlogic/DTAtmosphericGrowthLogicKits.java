package maxhyper.dtatmospheric.growthlogic;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import maxhyper.dtatmospheric.DynamicTreesAtmospheric;
import net.minecraft.util.ResourceLocation;

public class DTAtmosphericGrowthLogicKits {

    public static final GrowthLogicKit TWISTING = new TwistingTreeLogic(new ResourceLocation(DynamicTreesAtmospheric.MOD_ID, "twisting"));
    public static final GrowthLogicKit ASPEN = new AspenLogic(new ResourceLocation(DynamicTreesAtmospheric.MOD_ID, "aspen"));

    public static void register(final Registry<GrowthLogicKit> registry) {
        registry.registerAll(TWISTING, ASPEN);
    }

}
