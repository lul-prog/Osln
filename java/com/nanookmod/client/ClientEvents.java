package com.nanookmod.client;

import com.nanookmod.NanookMod;
import com.nanookmod.client.render.NanookClawProjectileRenderer;
import com.nanookmod.client.render.NanookRenderer;
import com.nanookmod.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NanookMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.NANOOK.get(), NanookRenderer::new);
        event.registerEntityRenderer(ModEntities.NANOOK_CLAW_PROJECTILE.get(), NanookClawProjectileRenderer::new);
    }
}