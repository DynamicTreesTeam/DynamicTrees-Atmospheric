package maxhyper.dtatmospheric;

import com.ferreusveritas.dynamictrees.api.cells.CellKit;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.trees.Family;
import maxhyper.dtatmospheric.blocks.FloweringMoradoLeavesProperties;
import maxhyper.dtatmospheric.blocks.ScruffyLeavesProperties;
import maxhyper.dtatmospheric.cells.DTAtmosphericCellKits;
import maxhyper.dtatmospheric.growthlogic.DTAtmosphericGrowthLogicKits;
import maxhyper.dtatmospheric.trees.AspenFamily;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class DTAtmosphericRegistries {

    public static void setup() { }

    @SubscribeEvent
    public static void onGrowthLogicKitRegistry (final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GrowthLogicKit> event) {
        DTAtmosphericGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onCellKitRegistry (final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<CellKit> event) {
        DTAtmosphericCellKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFamilyTypes (final TypeRegistryEvent<Family> event) {
        event.registerType(DynamicTreesAtmospheric.resLoc("aspen"), AspenFamily.TYPE);
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(DynamicTreesAtmospheric.resLoc("scruffy"), ScruffyLeavesProperties.TYPE);
        event.registerType(DynamicTreesAtmospheric.resLoc("flowering_morado"), FloweringMoradoLeavesProperties.TYPE);
    }

}
