package maxhyper.dtatmospheric.growthlogic;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.configurations.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.PositionalSpeciesContext;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TwistingTreeLogic extends GrowthLogicKit {

    public static final ConfigurationProperty<Float> CHANCE_TO_SPLIT = ConfigurationProperty.floatProperty("chance_to_split");
    public static final ConfigurationProperty<Float> CHANCE_TO_SPLIT_ENDS = ConfigurationProperty.floatProperty("chance_to_split_ends");
    public static final ConfigurationProperty<Integer> DOWN_PROBABILITY = ConfigurationProperty.integer("down_probability");
    public static final ConfigurationProperty<Boolean> SPLIT_ENDS = ConfigurationProperty.bool("split_ends");

    public TwistingTreeLogic(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected GrowthLogicKitConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(CHANCE_TO_SPLIT, 0.001f)
                .with(CHANCE_TO_SPLIT_ENDS, 0.5f)
                .with(DOWN_PROBABILITY, 0)
                .with(HEIGHT_VARIATION, 3)
                .with(SPLIT_ENDS, true);
    }

    @Override
    protected void registerProperties() {
        this.register(CHANCE_TO_SPLIT, CHANCE_TO_SPLIT_ENDS, DOWN_PROBABILITY, HEIGHT_VARIATION, SPLIT_ENDS);
    }

    private boolean shouldSplitEnds(GrowthLogicKitConfiguration configuration, BlockPos pos){
        if (configuration.get(SPLIT_ENDS)){
            int posHash = CoordUtils.coordHashCode(pos, 2);
            float rand = (float)posHash / 0xFFFF;
            return rand < configuration.get(CHANCE_TO_SPLIT_ENDS);
        }
        return false;
    }

    @Override
    public int[] populateDirectionProbabilityMap(GrowthLogicKitConfiguration configuration, DirectionManipulationContext context) {
        final World world = context.world();
        final GrowSignal signal = context.signal();
        final int[] probMap = context.probMap();
        final BlockPos pos = context.pos();
        Direction originDir = signal.dir.getOpposite();

        int count = 0;
        for (Direction direction : Direction.values()){
            int rad = TreeHelper.getRadius(world,pos.offset(direction.getNormal()));
            if (rad > 0) count++;
            probMap[direction.ordinal()] = rad + (world.getRandom().nextFloat() < configuration.get(CHANCE_TO_SPLIT) ? 1 : 0);
        }
        //If there are not enough valid branches, just allow any direction except up
        if (count <= 1 || (shouldSplitEnds(configuration, pos) && signal.energy < 3)){
            probMap[0] = configuration.get(DOWN_PROBABILITY);
            probMap[1] = context.species().getUpProbability();
            probMap[2] = probMap[3] = probMap[4] = probMap[5] = 1;
        }

        probMap[originDir.ordinal()] = 0; // Disable the direction we came from

        return probMap;
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
