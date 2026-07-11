package com.nanookmod.registry;

import com.nanookmod.NanookMod;
import com.nanookmod.effect.PermafrostEffect;
import com.nanookmod.effect.PermafrostImmunityEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NanookMod.MOD_ID);

    public static final RegistryObject<MobEffect> PERMAFROST =
            EFFECTS.register("permafrost", PermafrostEffect::new);

    public static final RegistryObject<MobEffect> PERMAFROST_IMMUNITY = EFFECTS.register("permafrost_immunity",
            () -> new PermafrostImmunityEffect());
}
