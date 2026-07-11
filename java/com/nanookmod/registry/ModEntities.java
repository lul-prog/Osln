package com.nanookmod.registry;

import com.nanookmod.NanookMod;
import com.nanookmod.entity.NanookClawProjectile;
import com.nanookmod.entity.NanookEntity;
import com.nanookmod.entity.SnowyBlizzEntity;
import com.nanookmod.entity.SnowyBlizzIceball;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Aquí se registran todos los tipos de entidad del mod.
 * Cuando agreguemos los otros 3 mobs base, cada uno tendrá su propia línea aquí.
 */
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NanookMod.MOD_ID);

    public static final RegistryObject<EntityType<NanookEntity>> NANOOK =
            ENTITY_TYPES.register("nanook",
                    () -> EntityType.Builder.of(NanookEntity::new, MobCategory.MONSTER)
                            // Tamaño de la hitbox; Nanook es un boss grande, ajusta según el modelo real.
                            .sized(2.0f, 3.1f)
                            .clientTrackingRange(16)
                            .build("nanook"));

    public static final RegistryObject<EntityType<NanookClawProjectile>> NANOOK_CLAW_PROJECTILE =
            ENTITY_TYPES.register("nanook_claw_projectile",
                    () -> EntityType.Builder.<NanookClawProjectile>of(NanookClawProjectile::new, MobCategory.MISC)
                            .sized(1.0f, 2.4f)
                            .clientTrackingRange(10)
                            .updateInterval(1)
                            .build("nanook_claw_projectile"));

    public static final RegistryObject<EntityType<SnowyBlizzEntity>> SNOWY_BLIZZ =
            ENTITY_TYPES.register("snowy_blizz",
                    () -> EntityType.Builder.of(SnowyBlizzEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .clientTrackingRange(10)
                            .build("snowy_blizz"));

    public static final RegistryObject<EntityType<SnowyBlizzIceball>> SNOWY_BLIZZ_ICEBALL =
            ENTITY_TYPES.register("snowy_blizz_iceball",
                    () -> EntityType.Builder.<SnowyBlizzIceball>of(SnowyBlizzIceball::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(10)
                            .updateInterval(1)
                            .build("snowy_blizz_iceball"));
}
