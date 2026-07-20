package com.github.flufflynx.growinggrassmixin;


import com.github.flufflynx.growinggrass.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadingSnowyDirtBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void onRandomTick(BlockState p_222954_, ServerLevel p_222955_, BlockPos p_222956_, RandomSource p_222957_, CallbackInfo ci) {
        if ((p_222954_.is(Blocks.GRASS_BLOCK) || p_222954_.is(Blocks.MYCELIUM)) && Config.growShortGrass && p_222955_.random.nextInt(100) <= Config.shortGrassChance) {
            if (!p_222955_.isOutsideBuildHeight(p_222956_.above()) && p_222955_.getBlockState(p_222956_.above()).isAir()) {
                if (p_222954_.is(Blocks.GRASS_BLOCK)) {
                    p_222955_.setBlock(p_222956_.above(), Blocks.SHORT_GRASS.defaultBlockState(), 3);
                } else if (p_222954_.is(Blocks.MYCELIUM)) {
                    if (p_222955_.random.nextBoolean()) {
                        p_222955_.setBlock(p_222956_.above(), Blocks.RED_MUSHROOM.defaultBlockState(), 3);
                    } else {
                        p_222955_.setBlock(p_222956_.above(), Blocks.BROWN_MUSHROOM.defaultBlockState(), 3);
                    }
                }
            }
        }
    }
}
