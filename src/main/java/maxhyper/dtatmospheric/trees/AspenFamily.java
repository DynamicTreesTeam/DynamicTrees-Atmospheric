package maxhyper.dtatmospheric.trees;

import com.ferreusveritas.dynamictrees.api.registry.TypedRegistry;
import com.ferreusveritas.dynamictrees.blocks.branches.BasicBranchBlock;
import com.ferreusveritas.dynamictrees.blocks.branches.BranchBlock;
import com.ferreusveritas.dynamictrees.init.DTConfigs;
import com.ferreusveritas.dynamictrees.systems.nodemappers.NetVolumeNode;
import com.ferreusveritas.dynamictrees.trees.Family;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.Optionals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Optional;

public class AspenFamily extends Family {

    public static final TypedRegistry.EntryType<Family> TYPE = TypedRegistry.newType(AspenFamily::new);

    protected BranchBlock altBranchBlock;
    protected float watchfulBranchChance = 0.4f;

    public AspenFamily(ResourceLocation name) {
        super(name);
    }

    @Override
    public void setupBlocks() {
        super.setupBlocks();

        this.altBranchBlock = setupBranch(
                createBranch(getBranchName("watchful_")),
                true
        );
    }

    public Family setPrimitiveWatchfulLog(Block primitiveLog) {
        altBranchBlock.setPrimitiveLogDrops(new ItemStack(primitiveLog));
        return this;
    }

    public Optional<BranchBlock> getWatchfulBranch() {
        return Optionals.ofBlock(altBranchBlock);
    }

    public void setWatchfulBranchChance(Float chance) {
        watchfulBranchChance = chance == null ? 0 : chance;
    }

    @Override
    public Optional<BranchBlock> getBranchForPlacement(IWorld world, Species species, BlockPos pos) {
        if (world.getRandom().nextFloat() < watchfulBranchChance)
            return Optional.ofNullable(altBranchBlock);
        else
            return super.getBranchForPlacement(world, species, pos);
    }
}
