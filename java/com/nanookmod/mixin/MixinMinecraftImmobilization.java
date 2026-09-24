package com.nanookmod.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import com.nanookmod.registry.ModEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Con el efecto IMMOBILIZATION (stun) activo, hacemos que Minecraft "crea"
 * que ninguna tecla está pulsada: esto bloquea por completo el movimiento
 * con teclado (WASD, salto, sprint, agacharse...) sin desynchronizar los
 * binds del jugador.
 */
@Mixin(Minecraft.class)
public class MixinMinecraftImmobilization {

    @Inject(method = "isKeyDown", at = @At("HEAD"), cancellable = true)
    private void nanook$blockKeyboard(InputConstants.Key key, CallbackInfoReturnable<Boolean> cir) {
        Minecraft mc = (Minecraft) (Object) this;
        LocalPlayer player = mc.player;

        if (player != null && player.hasEffect(ModEffects.IMMOBILIZATION.get())) {
            cir.setReturnValue(false);
        }
    }

    /**
     * Congela la cámara: al devolver una delta de 0, el ratón no gira la
     * vista mientras dure el efecto (la rotación actual se mantiene).
     */
    @Inject(method = "getMouseDelta", at = @At("HEAD"), cancellable = true)
    private void nanook$freezeCamera(DeltaTracker tracker, CallbackInfoReturnable<Double> cir) {
        Minecraft mc = (Minecraft) (Object) this;
        LocalPlayer player = mc.player;

        if (player != null && player.hasEffect(ModEffects.IMMOBILIZATION.get())) {
            cir.setReturnValue(0.0D);
        }
    }
}
