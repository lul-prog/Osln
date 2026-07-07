package com.nanookmod.registry;

import com.nanookmod.NanookMod;
import com.nanookmod.block.custom.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    // Creamos el registro de bloques
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NanookMod.MOD_ID);

    // 1. Tierra Helada (Frost Dirt)
    public static final RegistryObject<Block> FROST_DIRT = BLOCKS.register("frost_dirt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .sound(SoundType.GRASS)));

    public static final RegistryObject<Block> FROST_ICE = BLOCKS.register("frost_ice",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.ICE)
                    .sound(SoundType.GLASS)
                    .noOcclusion()));

    // 3. Pasto Helado (Frost Grass)
    public static final RegistryObject<Block> FROST_GRASS = BLOCKS.register("frost_grass",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)
                    .sound(SoundType.GRASS)));

    // 4. Tierra de Cultivo Helada (Frost Farmland)
    public static final RegistryObject<Block> FROST_FARMLAND = BLOCKS.register("frost_farmland",
            () -> new FrostFarmland(BlockBehaviour.Properties.copy(Blocks.FARMLAND)
                    .sound(SoundType.GLASS)));

    // Planta gélida (el cultivo en sí)
    public static final RegistryObject<Block> FROST_CROP = BLOCKS.register("frost_crop",
            () -> new FrostCrop(BlockBehaviour.Properties.copy(Blocks.WHEAT)
                    .noCollission()));

    // Linterna Gélida
    public static final RegistryObject<Block> FROST_LANTERN = BLOCKS.register("frost_lantern",
            () -> new FrostLanternBlock(BlockBehaviour.Properties.of()
                    .strength(3.0F)
                    .lightLevel(state -> {
                        int fuelState = state.getValue(FrostLanternBlock.FUEL_STATE);
                        if (fuelState == 2) return 14;
                        if (fuelState == 1) return 7;
                        return 0;
                    })
                    .noOcclusion()
                    .sound(SoundType.LANTERN)));

    // Snowy Bush (Arbusto nevado)
    public static final RegistryObject<Block> SNOWY_BUSH = BLOCKS.register("snowy_bush",
            () -> new FrostBushBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)
                    .strength(0.0F)
                    .sound(SoundType.GRASS)
                    .noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));

    // Dead Snow Bush (Arbusto muerto)
    public static final RegistryObject<Block> DEAD_SNOW_BUSH = BLOCKS.register("dead_snow_bush",
            () -> new FrostBushBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)
                    .strength(0.0F)
                    .sound(SoundType.GRASS)
                    .noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));

    // Snowy Tall Grass (Pasto alto)
    public static final RegistryObject<Block> SNOWY_TALL_GRASS = BLOCKS.register("snowy_tall_grass",
            () -> new FrostBushBlock(BlockBehaviour.Properties.copy(Blocks.FERN)
                    .strength(0.0F)
                    .sound(SoundType.GRASS)
                    .noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));
    // ============================================
    // VEGETACIÓN ALTA (2 bloques de altura)
    // ============================================

    // Snowy Double Plant Fern (Arbusto/Helecho alto)
    public static final RegistryObject<Block> SNOWY_DOUBLE_PLANT_FERN = BLOCKS.register("snowy_double_plant_fern",
            () -> new FrostDoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.LARGE_FERN)
                    .strength(0.0F)
                    .sound(SoundType.GRASS)
                    .noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));

    // Snowy Double Plant Grass (Pasto alto)
    public static final RegistryObject<Block> SNOWY_DOUBLE_PLANT_GRASS = BLOCKS.register("snowy_double_plant_grass",
            () -> new FrostDoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)
                    .strength(0.0F)
                    .sound(SoundType.GRASS)
                    .noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));
}