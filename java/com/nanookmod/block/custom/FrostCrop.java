package com.nanookmod.block.custom;

import com.nanookmod.registry.ModBlocks;
import com.nanookmod.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FrostCrop extends CropBlock {

    // Requisito de luz mínimo para crecer
    private static final int MIN_LIGHT_LEVEL = 8;

    // Probabilidad base de crecimiento por tick (más baja = más lento)
    // Vanilla usa ~0.25, nosotros usamos 0.15 para que tarde 15-20 min
    private static final float BASE_GROWTH_CHANCE = 0.15f;

    public FrostCrop(Properties pProperties) {
        super(pProperties);
    }

    // Define dónde se puede plantar
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(ModBlocks.FROST_FARMLAND.get());
    }

    // Define qué item dropea como semilla
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.FROST_SEEDS.get();
    }

    // Sobrescribimos el crecimiento para hacerlo más lento y requerir luz
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        // Verificar requisito de luz
        if (pLevel.getMaxLocalRawBrightness(pPos) < MIN_LIGHT_LEVEL) {
            return; // No crece si hay poca luz
        }

        int currentAge = this.getAge(pState);

        // Solo intentar crecer si no está madura
        if (currentAge < this.getMaxAge()) {
            // Probabilidad base de crecimiento (más lenta que vanilla)
            float growthChance = BASE_GROWTH_CHANCE;

            // Bonus si la farmland está "hidratada" (en nuestro caso, siempre lo está)
            // Esto es solo para mantener la lógica de Minecraft
            if (pLevel.getBlockState(pPos.below()).is(ModBlocks.FROST_FARMLAND.get())) {
                growthChance *= 1.5f; // 50% más rápido si está en farmland
            }

            // Intentar crecer
            if (pRandom.nextFloat() < growthChance) {
                pLevel.setBlock(pPos, this.getStateForAge(currentAge + 1), 3);
            }
        }
    }
}