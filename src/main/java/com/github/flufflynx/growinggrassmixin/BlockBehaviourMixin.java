package com.github.flufflynx.growinggrassmixin;

import com.github.flufflynx.growinggrass.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    private void BlocksToRandomTick(BlockState state, CallbackInfoReturnable<Boolean> cir)
    {
        if (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.FERN) || state.is(Blocks.PODZOL))
            cir.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void onRandomTick(BlockState p_222954_, ServerLevel p_222955_, BlockPos p_222956_, RandomSource p_222957_, CallbackInfo ci)
    {
        if (p_222954_.is(Blocks.PODZOL) && Config.growShortGrass && p_222955_.random.nextDouble() <= Config.shortGrassChance) {
            if (p_222956_.above().getY() < p_222955_.getMaxBuildHeight() && p_222955_.getBlockState(p_222956_.above()).is(Blocks.AIR)) {
                p_222955_.setBlock(p_222956_.above(), Blocks.FERN.defaultBlockState(), 3);
            }
        } else if ((p_222954_.is(Blocks.SHORT_GRASS) || p_222954_.is(Blocks.FERN)) && Config.growTallGrass && p_222955_.random.nextDouble() <= Config.tallGrassChance) {
            BlockState BlockBellow = p_222955_.getBlockState(p_222956_.below());
            BlockState BlockAbove = p_222955_.getBlockState(p_222956_.above());
            if (p_222956_.above().getY() < p_222955_.getMaxBuildHeight() && BlockAbove.is(Blocks.AIR) && (BlockBellow.is(Blocks.GRASS_BLOCK)) || BlockBellow.is(Blocks.PODZOL) || BlockBellow.is(Blocks.MYCELIUM))
            {
                if (p_222954_.is(Blocks.SHORT_GRASS)) {
                    BlockState LowerHalf = Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
                    BlockState UpperHalf = Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
                    p_222955_.setBlock(p_222956_, LowerHalf, 3);
                    p_222955_.setBlock(p_222956_.above(), UpperHalf, 3);
                } else if (p_222954_.is(Blocks.FERN)) {
                    BlockState LowerHalf = Blocks.LARGE_FERN.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
                    BlockState UpperHalf = Blocks.LARGE_FERN.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
                    p_222955_.setBlock(p_222956_, LowerHalf, 3);
                    p_222955_.setBlock(p_222956_.above(), UpperHalf, 3);
                }
            }
        }
    }
}