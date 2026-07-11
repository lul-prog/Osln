package com.nanookmod.registry;

import com.nanookmod.NanookMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {

    // Registro de Creative Tabs
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NanookMod.MOD_ID);

    // Pestaña principal del mod (ícono: Frost Grass)
    public static final RegistryObject<CreativeModeTab> NANOOK_TAB = CREATIVE_TABS.register("nanook_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.nanookmod.nanook_tab"))
                    .icon(() -> new ItemStack(ModBlocks.FROST_GRASS.get()))
                    .displayItems((parameters, output) -> {
                        // Bloques
                        output.accept(ModBlocks.FROST_DIRT.get());
                        output.accept(ModBlocks.FROST_ICE.get());
                        output.accept(ModBlocks.FROST_GRASS.get());
                        output.accept(ModBlocks.FROST_FARMLAND.get());
                        output.accept(ModBlocks.FROST_LANTERN.get());
                        output.accept(ModBlocks.SNOWY_BUSH.get());
                        output.accept(ModBlocks.DEAD_SNOW_BUSH.get());
                        output.accept(ModBlocks.SNOWY_TALL_GRASS.get());
                        output.accept(ModBlocks.SNOWY_DOUBLE_PLANT_FERN.get());
                        output.accept(ModBlocks.SNOWY_DOUBLE_PLANT_GRASS.get());
                        output.accept(ModBlocks.FROST_BREWING_STAND.get());

                        // Items
                        output.accept(ModItems.SUMMONING_STAFF.get());
                        output.accept(ModItems.FROZEN_APPLE.get());
                        output.accept(ModItems.FROST_SEEDS.get());
                        output.accept(ModItems.FROST_PETAL.get());
                        output.accept(ModItems.FROST_FUEL.get());
                        output.accept(ModItems.BLIZZ_ROD.get());
                        output.accept(ModItems.BLIZZ_ROD_POWDER.get());
                        output.accept(ModItems.FROST_BOTTLE_EMPTY.get());
                        output.accept(ModItems.FROST_BOTTLE.get());
                        output.accept(ModItems.PERMAFROST_POTION.get());
                        output.accept(ModItems.GREATER_PERMAFROST_POTION.get());
                    })
                    .build()
    );
}