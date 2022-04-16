package maxhyper.dtatmospheric.growthlogic;

import com.ferreusveritas.dynamictrees.api.configurations.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionSelectionContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.PositionalSpeciesContext;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AspenLogic extends GrowthLogicKit {

    public static final ConfigurationProperty<Integer> LOWEST_CANOPY_HEIGHT = ConfigurationProperty.integer("lowest_canopy_height");

    public AspenLogic(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected GrowthLogicKitConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(HEIGHT_VARIATION, 3)
                .with(LOWEST_CANOPY_HEIGHT, 14);
    }

    @Override
    protected void registerProperties() {
        this.register(HEIGHT_VARIATION, LOWEST_CANOPY_HEIGHT);
    }

    @Override
    public int[] populateDirectionProbabilityMap(GrowthLogicKitConfiguration configuration, DirectionManipulationContext context) {
        final GrowSignal signal = context.signal();
        final int[] probMap = context.probMap();
        final int radius = context.radius();

        Direction originDir = signal.dir.getOpposite();

        // Alter probability map for direction change
        probMap[0] = signal.isInTrunk() ? 0 : 1;
        probMap[1] = signal.isInTrunk() ? 4 : 1;

        // Set all side probabilities to 0 to start out
        probMap[2] = probMap[3] = probMap[4] = probMap[5] = 0;

        // Allow for turning if in trunk, otherwise reinforce current travel direction
        if (signal.isInTrunk()) {
            // Make it so every 2 blocks the trunk has a higher chance of branching out, except at the tip
            if (radius > 1){
                // Choose a random direction to do the branch
                int directionSelection = Math.abs(CoordUtils.coordHashCode(context.pos(), 2)) % 4;
                probMap[2 + directionSelection] = (signal.numSteps % 2 == 0) ? 3 : 0;
            }
        } else {
            // If we're twig or small branch, don't grow up or down
            if (radius < 3) {
                probMap[0] = 0;
                probMap[1] = 0;
            }

            // Reinforce current travel direction
            probMap[signal.dir.ordinal()] += (signal.numTurns == 1 ? 2 : 1);
        }

        //allow all sides if the height reaches the canopy height
        if (signal.delta.getY() >= configuration.get(LOWEST_CANOPY_HEIGHT)){
            probMap[Direction.UP.ordinal()] = context.species().getUpProbability();
            probMap[2] = probMap[3] = probMap[4] = probMap[5] = 1;
        }

        // Disable the direction we came from
        probMap[originDir.ordinal()] = 0;

        return probMap;
    }


    @Override
    public Direction selectNewDirection(GrowthLogicKitConfiguration configuration, DirectionSelectionContext context) {
        final GrowSignal signal = context.signal();
        Direction newDir = super.selectNewDirection(configuration, context);
        // Turned out of trunk
        if (signal.isInTrunk() && newDir != Direction.UP && signal.delta.getY() < configuration.get(LOWEST_CANOPY_HEIGHT)) {
            // Reduce the energy so branches don't expand too much

            // Branches can have 1-3 energy
            float energyAddition = ((CoordUtils.coordHashCode(signal.rootPos.offset(signal.delta), 1) % 1000) / 1000.f) * 2.f;

            signal.energy = 1 + energyAddition;
        }
        return newDir;
    }

    public static int getHashedVariation (World world, BlockPos pos, int heightVariation){
        long day = world.getGameTime() / 24000L;
        int month = (int)day / 30;//Change the hashs every in-game month
        return (CoordUtils.coordHashCode(pos.above(month), 2) % heightVariation);//Vary the height energy by a psuedorandom hash function
    }

    @Override
    public float getEnergy(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        World world = context.world();
        BlockPos pos = context.pos();
        return super.getEnergy(configuration, context) * context.species().biomeSuitability(world, pos)
                + getHashedVariation(world, pos, configuration.get(HEIGHT_VARIATION));
    }

    @Override
    public int getLowestBranchHeight(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        return super.getLowestBranchHeight(configuration, context)
                + getHashedVariation(context.world(), context.pos(), configuration.get(HEIGHT_VARIATION));
    }

}
