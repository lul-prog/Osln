package com.nanookmod;

import com.nanookmod.entity.NanookClawProjectile;
import com.nanookmod.registry.*; // incluye ModFeatures
import com.nanookmod.world.biome.region.MarPrimigenioRegion;
import com.nanookmod.world.biome.surface.MarPrimigenioSurfaceRules;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(NanookMod.MOD_ID)
public class NanookMod {

    public static final String MOD_ID = "nanookmod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public NanookMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        ModTrunkPlacerTypes.TRUNK_PLACER_TYPES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerAttributes);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Nanook Mod: inicializando.");

        // Registrar TerraBlender
        event.enqueueWork(() -> {
            // Registrar la región del bioma con peso 2
            terrablender.api.Regions.register(new MarPrimigenioRegion(1));
            LOGGER.info("Nanook Mod: Región Mar Primigenio registrada");

            // Registrar las surface rules - IMPORTANTE: usar RuleCategory.OVERWORLD
            terrablender.api.SurfaceRuleManager.addSurfaceRules(
                    terrablender.api.SurfaceRuleManager.RuleCategory.OVERWORLD,
                    MOD_ID,
                    MarPrimigenioSurfaceRules.makeRules()
            );
            LOGGER.info("Nanook Mod: Surface rules registradas");
        });
    }

    /**
     * Aquí se registran los "atributos" de cada entidad nueva (vida, velocidad, etc.).
     * Es obligatorio para cualquier entidad viva (LivingEntity) en Forge.
     */
    private void registerAttributes(final EntityAttributeCreationEvent event) {
        event.put(ModEntities.NANOOK.get(), com.nanookmod.entity.NanookEntity.createAttributes().build());
    }
}

