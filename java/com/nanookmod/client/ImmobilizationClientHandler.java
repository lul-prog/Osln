package com.nanookmod.client;

import com.nanookmod.NanookMod;
import com.nanookmod.registry.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Del lado del cliente, mientras el jugador local tenga el efecto
 * IMMOBILIZATION (tipo stun):
 *  - Bloquea el input de movimiento del teclado (WASD, salto, sprint...).
 *  - Bloquea el giro de la cámara con el ratón.
 */
@Mod.EventBusSubscriber(modid = NanookMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ImmobilizationClientHandler {

    @SubscribeEvent
    public static void onMovementInput(InputEvent.MovementInput event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        if (player.hasEffect(ModEffects.IMMOBILIZATION.get())) {
            // Cero en todos los ejes: no avanza, no strafea, no salta.
            // (El bloqueo del ratón/cámara se hace en MixinMinecraftImmobilization)
            event.getMovementInput().forward = 0.0F;
            event.getMovementInput().upwards = 0.0F;
        }
    }
}
