package maxhyper.dtatmospheric.blocks;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.blocks.leaves.LeavesProperties;
import com.minecraftabnormals.atmospheric.core.registry.AtmosphericParticles;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class FloweringMoradoLeavesProperties extends ScruffyLeavesProperties {

    public static final TypedRegistry.EntryType<LeavesProperties> TYPE = TypedRegistry.newType(FloweringMoradoLeavesProperties::new);

    public FloweringMoradoLeavesProperties(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected DynamicLeavesBlock createDynamicLeaves(final AbstractBlock.Properties properties) {
        return new DynamicLeavesBlock(this, properties){
            @OnlyIn(Dist.CLIENT)
            public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
                super.animateTick(stateIn, worldIn, pos, rand);
                int color = worldIn.getBiome(pos).getFoliageColor();
                double d0 = ((float)(color >> 16 & 255) / 255.0F);
                double d1 = ((float)(color >> 8 & 255) / 255.0F);
                double d2 = ((float)(color & 255) / 255.0F);
                if (rand.nextInt(40) == 0) {
                    BlockPos blockpos = pos.below();
                    if (worldIn.isEmptyBlock(blockpos)) {
                        double d3 = ((float)pos.getX() + rand.nextFloat());
                        double d4 = (double)pos.getY() - 0.05D;
                        double d6 = ((float)pos.getZ() + rand.nextFloat());
                        worldIn.addParticle(AtmosphericParticles.MORADO_BLOSSOM.get(), d3, d4, d6, d0, d1, d2);
                    }
                }
            }
        };
    }
}
