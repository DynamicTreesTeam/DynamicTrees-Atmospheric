package maxhyper.dtatmospheric;

import com.ferreusveritas.dynamictrees.api.cells.CellKit;
import com.ferreusveritas.dynamictrees.api.registry.RegistryEvent;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.trees.Family;
import maxhyper.dtatmospheric.blocks.FloweringMoradoLeavesProperties;
import maxhyper.dtatmospheric.blocks.ScruffyLeavesProperties;
import maxhyper.dtatmospheric.cells.DTAtmosphericCellKits;
import maxhyper.dtatmospheric.growthlogic.DTAtmosphericGrowthLogicKits;
import maxhyper.dtatmospheric.trees.AspenFamily;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTAtmosphericRegistries {

    public static void setup() {

    }

    public static final FeatureCanceller FRUIT_TREES_CANCELLER = new FeatureCanceller(new ResourceLocation(DynamicTreesAtmospheric.MOD_ID, "astree")) {
        @Override
        public boolean shouldCancel(ConfiguredFeature<?, ?> configuredFeature, BiomePropertySelectors.FeatureCancellations featureCancellations) {
            // Note it not in ForgeRegistries.FEATURES
            final ResourceLocation featureName = WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature);
            if (featureName == null) {
                return false;
            }
            // DynamicTreesAtmospheric.LOGGER.debug(((WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature) + "").startsWith("atmospheric:yucca_tree")) + "" + featureName);
            return featureCancellations.shouldCancelNamespace(featureName.getNamespace())
                    && ((WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature) + "").startsWith("atmospheric:yucca_tree")||(WorldGenRegistries.CONFIGURED_FEATURE.getKey(configuredFeature) + "").startsWith("atmospheric:patch_yucca_flower"));
        }

    };

    @SubscribeEvent
    public static void onFeatureCancellerRegistry(final RegistryEvent<FeatureCanceller> event) {
        event.getRegistry().registerAll(FRUIT_TREES_CANCELLER);
    }

    @SubscribeEvent
    public static void onGrowthLogicKitRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GrowthLogicKit> event) {
        DTAtmosphericGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onCellKitRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<CellKit> event) {
        DTAtmosphericCellKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFamilyTypes(final TypeRegistryEvent<Family> event) {
        event.registerType(DynamicTreesAtmospheric.resLoc("aspen"), AspenFamily.TYPE);
    }

    @SubscribeEvent
    public static void registerLeavesPropertiesTypes(final TypeRegistryEvent<LeavesProperties> event) {
        event.registerType(DynamicTreesAtmospheric.resLoc("scruffy"), ScruffyLeavesProperties.TYPE);
        event.registerType(DynamicTreesAtmospheric.resLoc("flowering_morado"), FloweringMoradoLeavesProperties.TYPE);
    }

}
