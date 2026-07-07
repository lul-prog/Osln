package com.nanookmod.registry;

import com.nanookmod.NanookMod;
import com.nanookmod.block.entity.FrostLanternBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NanookMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<FrostLanternBlockEntity>> FROST_LANTERN =
            BLOCK_ENTITIES.register("frost_lantern", () ->
                    BlockEntityType.Builder.of(FrostLanternBlockEntity::new,
                            ModBlocks.FROST_LANTERN.get()).build(null));
}