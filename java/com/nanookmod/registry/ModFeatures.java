package com.nanookmod.registry;

import com.nanookmod.NanookMod;
import com.nanookmod.world.feature.BigStonePileFeature;
import com.nanookmod.world.feature.FrostFreezeFeature;
import com.nanookmod.world.feature.PatchFeature;
import com.nanookmod.world.feature.ShallowWaterFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, NanookMod.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SHALLOW_WATER_FILL =
            FEATURES.register("shallow_water_fill",
                    () -> new ShallowWaterFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FROST_FREEZE =
            FEATURES.register("frost_freeze",
                    () -> new FrostFreezeFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BIG_STONE_PILE =
            FEATURES.register("big_stone_pile",
                    () -> new BigStonePileFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> PATCH_FEATURE =
            FEATURES.register("patch_feature",
                    () -> new PatchFeature(SimpleBlockConfiguration.CODEC));
}