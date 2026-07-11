package com.nanookmod.registry;

import com.nanookmod.NanookMod;
import com.nanookmod.item.custom.FrozenAppleItem;
import com.nanookmod.item.custom.SummoningStaffItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;

public class ModItems {
    // Creamos el registro de items usando el ID de tu mod
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NanookMod.MOD_ID);

    // Registramos el bastón. "stacksTo(1)" hace que no se pueda apilar en el inventario.
    public static final RegistryObject<Item> SUMMONING_STAFF = ITEMS.register("summoning_staff",
            () -> new SummoningStaffItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FROZEN_APPLE = ITEMS.register("frozen_apple",
            () -> new FrozenAppleItem(new Item.Properties()
                    .stacksTo(1),
                    6,    // nutrition: 6 puntos = 3 muslitos
                    0.8f  // saturationModifier
            ));
    public static final RegistryObject<Item> FROST_DIRT = ITEMS.register("frost_dirt",
            () -> new BlockItem(ModBlocks.FROST_DIRT.get(), new Item.Properties()));

    public static final RegistryObject<Item> FROST_ICE = ITEMS.register("frost_ice",
            () -> new BlockItem(ModBlocks.FROST_ICE.get(), new Item.Properties()));

    public static final RegistryObject<Item> FROST_GRASS = ITEMS.register("frost_grass",
            () -> new BlockItem(ModBlocks.FROST_GRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> FROST_FARMLAND = ITEMS.register("frost_farmland",
            () -> new BlockItem(ModBlocks.FROST_FARMLAND.get(), new Item.Properties()));

    public static final RegistryObject<Item> FROST_SEEDS = ITEMS.register("frost_seeds",
            () -> new ItemNameBlockItem(ModBlocks.FROST_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> FROST_PETAL = ITEMS.register("frost_petal",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FROST_FUEL = ITEMS.register("frost_fuel",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FROST_LANTERN = ITEMS.register("frost_lantern",
            () -> new BlockItem(ModBlocks.FROST_LANTERN.get(), new Item.Properties()));

    // Items de la vegetación
    public static final RegistryObject<Item> SNOWY_BUSH_ITEM = ITEMS.register("snowy_bush",
            () -> new BlockItem(ModBlocks.SNOWY_BUSH.get(), new Item.Properties()));

    public static final RegistryObject<Item> DEAD_SNOW_BUSH_ITEM = ITEMS.register("dead_snow_bush",
            () -> new BlockItem(ModBlocks.DEAD_SNOW_BUSH.get(), new Item.Properties()));

    public static final RegistryObject<Item> SNOWY_TALL_GRASS_ITEM = ITEMS.register("snowy_tall_grass",
            () -> new BlockItem(ModBlocks.SNOWY_TALL_GRASS.get(), new Item.Properties()));

    // Items de vegetación alta (2 bloques)
    public static final RegistryObject<Item> SNOWY_DOUBLE_PLANT_FERN_ITEM = ITEMS.register("snowy_double_plant_fern",
            () -> new BlockItem(ModBlocks.SNOWY_DOUBLE_PLANT_FERN.get(), new Item.Properties()));

    public static final RegistryObject<Item> SNOWY_DOUBLE_PLANT_GRASS_ITEM = ITEMS.register("snowy_double_plant_grass",
            () -> new BlockItem(ModBlocks.SNOWY_DOUBLE_PLANT_GRASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> CLAW_PROJECTILE_ITEM = ITEMS.register("nanook_claw_projectile",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SNOWY_BLIZZ_ICEBALL_ITEM = ITEMS.register("snowy_blizz_iceball",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FROST_BREWING_STAND = ITEMS.register("frost_brewing_stand",
            () -> new BlockItem(ModBlocks.FROST_BREWING_STAND.get(), new Item.Properties()));

    // Botella de Escarcha VACÍA (máx 16)
    public static final RegistryObject<Item> FROST_BOTTLE_EMPTY = ITEMS.register("frost_bottle_empty",
            () -> new com.nanookmod.item.custom.FrostBottleEmptyItem(new Item.Properties().stacksTo(16)));

    // Botella de Escarcha LLENA con agua (máx 1)
    public static final RegistryObject<Item> FROST_BOTTLE = ITEMS.register("frost_bottle",
            () -> new com.nanookmod.item.custom.FrostBottleItem(new Item.Properties().stacksTo(1)));

    // Poción de Inmunidad al Permafrost - MAX 1, con brillo
    public static final RegistryObject<Item> PERMAFROST_POTION = ITEMS.register("permafrost_potion",
            () -> new com.nanookmod.item.custom.PotionItem(new Item.Properties()));

    // Vara de Blizz (drop del mob)
    public static final RegistryObject<Item> BLIZZ_ROD = ITEMS.register("blizz_rod",
            () -> new Item(new Item.Properties()));

    // Polvo de Vara de Blizz (combustible)
    public static final RegistryObject<Item> BLIZZ_ROD_POWDER = ITEMS.register("blizz_rod_powder",
            () -> new Item(new Item.Properties()));

    // Poción de Inmunidad al Permafrost MEJORADA (inmunidad total) - MAX 1, con brillo
    public static final RegistryObject<Item> GREATER_PERMAFROST_POTION = ITEMS.register("greater_permafrost_potion",
            () -> new com.nanookmod.item.custom.FrostPotionItem(new Item.Properties()));
}