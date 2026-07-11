package com.nanookmod.client;

import com.nanookmod.NanookMod;
import com.nanookmod.client.render.NanookClawProjectileRenderer;
import com.nanookmod.client.render.NanookRenderer;
import com.nanookmod.client.render.SnowyBlizzIceballRenderer;
import com.nanookmod.client.render.SnowyBlizzRenderer;
import com.nanookmod.client.screen.FrostBrewingStandScreen;
import com.nanookmod.registry.ModBlocks;
import com.nanookmod.registry.ModEntities;
import com.nanookmod.registry.ModMenus;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NanookMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.NANOOK.get(), NanookRenderer::new);
        event.registerEntityRenderer(ModEntities.NANOOK_CLAW_PROJECTILE.get(), NanookClawProjectileRenderer::new);

        event.registerEntityRenderer(ModEntities.SNOWY_BLIZZ.get(), SnowyBlizzRenderer::new);
        event.registerEntityRenderer(ModEntities.SNOWY_BLIZZ_ICEBALL.get(), SnowyBlizzIceballRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Render layer para el bloque (transparencias)
            if (ModBlocks.FROST_BREWING_STAND.isPresent()) {
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.FROST_BREWING_STAND.get(), RenderType.cutout());
            }

            // Registro de la Screen del menú
            if (ModMenus.FROST_BREWING_STAND.isPresent()) {
                net.minecraft.client.gui.screens.MenuScreens.register(
                        ModMenus.FROST_BREWING_STAND.get(),
                        FrostBrewingStandScreen::new
                );
            }
        });
    }
}