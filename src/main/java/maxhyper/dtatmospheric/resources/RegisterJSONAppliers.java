package maxhyper.dtatmospheric.resources;

import com.ferreusveritas.dynamictrees.api.treepacks.ApplierRegistryEvent;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.deserialisation.PropertyAppliers;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.google.gson.JsonElement;
import maxhyper.dtatmospheric.DynamicTreesAtmospheric;
import maxhyper.dtatmospheric.blocks.FloweringMoradoLeavesProperties;
import maxhyper.dtatmospheric.blocks.ScruffyLeavesProperties;
import maxhyper.dtatmospheric.trees.AspenFamily;
import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DynamicTreesAtmospheric.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RegisterJSONAppliers {

    @SubscribeEvent
    public static void registerAppliersFamily(final ApplierRegistryEvent.Reload<Family, JsonElement> event) {
        registerFamilyAppliers(event.getAppliers());
    }
    @SubscribeEvent
    public static void registerAppliersLeavesProperties(final ApplierRegistryEvent.Reload<LeavesProperties, JsonElement> event) {
        registerLeavesPropertiesAppliers(event.getAppliers());
    }


    public static void registerFamilyAppliers(PropertyAppliers<Family, JsonElement> appliers) {
        appliers.register("primitive_watchful_log", AspenFamily.class, Block.class,
                AspenFamily::setPrimitiveWatchfulLog)
                .register("watchful_branch_chance", AspenFamily.class, Float.class,
                        AspenFamily::setWatchfulBranchChance);
    }
    public static void registerLeavesPropertiesAppliers(PropertyAppliers<LeavesProperties, JsonElement> appliers) {
        appliers.register("scruffy_leaf_chance", ScruffyLeavesProperties.class, Float.class, ScruffyLeavesProperties::setLeafChance)
                .register("scruffy_max_hydro", ScruffyLeavesProperties.class, Integer.class, ScruffyLeavesProperties::setMaxHydro);
    }

    @SubscribeEvent public static void registerAppliersFamily(final ApplierRegistryEvent.GatherData<Family, JsonElement> event) { registerFamilyAppliers(event.getAppliers()); }
    @SubscribeEvent public static void registerAppliersLeavesProperties(final ApplierRegistryEvent.GatherData<LeavesProperties, JsonElement> event) { registerLeavesPropertiesAppliers(event.getAppliers()); }

}