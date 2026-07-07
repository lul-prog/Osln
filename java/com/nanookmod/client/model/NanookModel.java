package com.nanookmod.client.model;

import com.nanookmod.NanookMod;
import com.nanookmod.entity.NanookEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/**
 * Indica a GeckoLib dónde están los 3 archivos que necesita cada entidad animada:
 *  - el modelo  (.geo.json, exportado desde Blockbench con el plugin de GeckoLib)
 *  - la textura (.png)
 *  - la animación (.animation.json, exportado desde Blockbench)
 *
 * IMPORTANTE: nocsy_icebear_boss.bbmodel debe re-exportarse desde Blockbench
 * usando el formato "Geckolib Model" (no el .bbmodel original) para generar
 * estos 3 archivos. Las rutas de abajo asumen que se guardan con estos nombres.
 */
public class NanookModel extends GeoModel<NanookEntity> {

    @Override
    public ResourceLocation getModelResource(NanookEntity animatable) {
        return new ResourceLocation(NanookMod.MOD_ID, "geo/entity/nanook.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NanookEntity animatable) {
        return new ResourceLocation(NanookMod.MOD_ID, "textures/entity/nanook.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NanookEntity animatable) {
        return new ResourceLocation(NanookMod.MOD_ID, "animations/entity/nanook.animation.json");
    }
}
