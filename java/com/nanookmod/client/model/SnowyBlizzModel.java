package com.nanookmod.client.model;

import com.nanookmod.NanookMod;
import com.nanookmod.entity.SnowyBlizzEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SnowyBlizzModel extends GeoModel<SnowyBlizzEntity> {

    @Override
    public ResourceLocation getModelResource(SnowyBlizzEntity animatable) {
        return new ResourceLocation(NanookMod.MOD_ID, "geo/entity/snowy_blizz.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SnowyBlizzEntity animatable) {
        return new ResourceLocation(NanookMod.MOD_ID, "textures/entity/snowy_blizz.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SnowyBlizzEntity animatable) {
        return new ResourceLocation(NanookMod.MOD_ID, "animations/entity/snowy_blizz.animation.json");
    }
}
