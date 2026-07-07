package com.nanookmod.entity;

import com.nanookmod.entity.NanookClawProjectile;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * Entidad del boss Nanook.
 *
 * Habilidades portadas:
 *   - ATTACK_CLAW (nanook_attack_claw): daño 12 cono r=5, telegraph 20/40 ticks.
 *   - ATTACK_ICE_FIST (nanook_attack_fist): daño 10 + freeze 40 + slowness, mismo timing.
 *   - ATTACK_CLAW_PROJECTILE (nanook_claw_fist_projectile): 3 proyectiles en abanico
 *     (+2 más si vida <50%, replicando la condición del YAML), delay 25 ticks,
 *     animación forward_claw_projectile (duración real a confirmar al probar).
 */
public class NanookEntity extends Monster implements GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> DATA_ATTACK_STATE =
            SynchedEntityData.defineId(NanookEntity.class, EntityDataSerializers.INT);

    public static final int ATTACK_NONE = 0;
    public static final int ATTACK_CLAW = 1;
    public static final int ATTACK_ICE_FIST = 2;
    public static final int ATTACK_CLAW_PROJECTILE = 3;

    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation CLAW_PUNCH_ANIM = RawAnimation.begin().thenPlay("claw_punch");
    private static final RawAnimation ICE_PUNCH_ANIM = RawAnimation.begin().thenPlay("ice_punch");
    private static final RawAnimation CLAW_PROJECTILE_ANIM = RawAnimation.begin().thenPlay("forward_claw_projectile");

    private static final int DAMAGE_TICK = 20;
    private static final int ATTACK_DURATION_TICKS = 40;

    // El ataque de proyectiles tiene su propio timing (delay 25, no 20, según el YAML).
    private static final int PROJECTILE_FIRE_TICK = 25;
    private static final int PROJECTILE_ATTACK_DURATION_TICKS = 40;

    private int attackTicksRemaining = -1;
    private boolean damageAppliedThisAttack = false;
    private int lastAttackStateAnimated = ATTACK_NONE;

    public NanookEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 500.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 4.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ATTACK_STATE, ATTACK_NONE);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new NanookClawAttackGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // ---- Soporte GeckoLib ----

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 5, this::movementPredicate));
    }

    private PlayState movementPredicate(AnimationState<NanookEntity> state) {
        int attackState = this.getAttackState();

        if (attackState != ATTACK_NONE) {
            if (this.lastAttackStateAnimated != attackState) {
                this.lastAttackStateAnimated = attackState;
                RawAnimation anim = pickAnimationFor(attackState);
                return state.setAndContinue(anim);
            }
            return PlayState.CONTINUE;
        }

        this.lastAttackStateAnimated = ATTACK_NONE;

        if (state.isMoving()) {
            return state.setAndContinue(WALK_ANIM);
        }
        return state.setAndContinue(IDLE_ANIM);
    }

    private RawAnimation pickAnimationFor(int attackState) {
        if (attackState == ATTACK_CLAW) {
            return CLAW_PUNCH_ANIM;
        } else if (attackState == ATTACK_ICE_FIST) {
            return ICE_PUNCH_ANIM;
        }
        return CLAW_PROJECTILE_ANIM;
    }

    // ---- Lógica de ataques ----

    public int getAttackState() {
        return this.getEntityData().get(DATA_ATTACK_STATE);
    }

    public boolean isAttacking() {
        return this.getAttackState() != ATTACK_NONE;
    }

    public void triggerClawAttack() {
        if (!this.isAttacking()) {
            this.startAttack(ATTACK_CLAW, ATTACK_DURATION_TICKS);
        }
    }

    public void triggerIceFistAttack() {
        if (!this.isAttacking()) {
            this.startAttack(ATTACK_ICE_FIST, ATTACK_DURATION_TICKS);
        }
    }

    public void triggerClawProjectileAttack() {
        if (!this.isAttacking()) {
            this.startAttack(ATTACK_CLAW_PROJECTILE, PROJECTILE_ATTACK_DURATION_TICKS);
        }
    }

    private void startAttack(int attackType, int durationTicks) {
        this.attackTicksRemaining = durationTicks;
        this.damageAppliedThisAttack = false;
        this.getEntityData().set(DATA_ATTACK_STATE, attackType);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (this.attackTicksRemaining > 0) {
                int attackState = this.getAttackState();
                int totalDuration = (attackState == ATTACK_CLAW_PROJECTILE)
                        ? PROJECTILE_ATTACK_DURATION_TICKS : ATTACK_DURATION_TICKS;
                int fireTick = (attackState == ATTACK_CLAW_PROJECTILE) ? PROJECTILE_FIRE_TICK : DAMAGE_TICK;
                int elapsed = totalDuration - this.attackTicksRemaining;

                if (elapsed >= fireTick && !this.damageAppliedThisAttack) {
                    applyAttackEffects(attackState);
                    this.damageAppliedThisAttack = true;
                }

                this.attackTicksRemaining--;
            } else if (this.attackTicksRemaining == 0) {
                this.attackTicksRemaining = -1;
                this.damageAppliedThisAttack = false;
                this.getEntityData().set(DATA_ATTACK_STATE, ATTACK_NONE);
            }
        }
    }

    private void applyAttackEffects(int attackType) {
        if (attackType == ATTACK_CLAW) {
            applyClawDamage();
        } else if (attackType == ATTACK_ICE_FIST) {
            applyIceFistEffects();
        } else if (attackType == ATTACK_CLAW_PROJECTILE) {
            fireClawProjectiles();
        }
    }

    private void applyClawDamage() {
        forEachTargetInCone(5.0D, 180.0, player ->
                player.hurt(this.damageSources().mobAttack(this), 12.0F));
    }

    private void applyIceFistEffects() {
        forEachTargetInCone(5.0D, 180.0, player -> {
            player.hurt(this.damageSources().mobAttack(this), 10.0F);
            player.setTicksFrozen(40);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
        });
    }

    /**
     * nanook_claw_fist_projectile: dispara 3 proyectiles en abanico (centro, +1, -1
     * grados de offset lateral), y 2 más (+2, -2) si la vida actual está por debajo
     * del 50% — replicando exactamente la condición "<50%" del skill original.
     * Velocidad v=30 del YAML, aproximada aquí como una velocidad fija del proyectil.
     */
    private void fireClawProjectiles() {
        if (this.level().isClientSide) {
            return;
        }

        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }

        boolean lowHealthPhase = this.getHealth() < this.getMaxHealth() * 0.5F;

        // Offsets laterales en grados respecto a la dirección hacia el objetivo.
        double[] offsets = lowHealthPhase
                ? new double[]{0, 8, -8, 16, -16}
                : new double[]{0, 8, -8};

        for (double offsetDegrees : offsets) {
            shootClawProjectile(target, offsetDegrees);
        }
    }

    private void shootClawProjectile(LivingEntity target, double offsetDegrees) {
        Vec3 toTargetFlat = new Vec3(
                target.getX() - this.getX(),
                0.0D,
                target.getZ() - this.getZ()
        );

        if (toTargetFlat.lengthSqr() < 1.0E-4) {
            toTargetFlat = this.getForward();
        }

        Vec3 direction = rotateAroundY(toTargetFlat.normalize(), offsetDegrees);

        NanookClawProjectile projectile = new NanookClawProjectile(this.level(), this);
        // Altura "a ras del suelo": cerca de los pies de Nanook, no de los ojos.
        double groundY = this.getY() + 0.3;
        projectile.setPos(this.getX(), groundY, this.getZ());

        double speed = 1.5D;
        projectile.setDeltaMovement(direction.scale(speed));
        this.level().addFreshEntity(projectile);
    }

    private Vec3 rotateAroundY(Vec3 vec, double degrees) {
        double radians = Math.toRadians(degrees);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double x = vec.x * cos - vec.z * sin;
        double z = vec.x * sin + vec.z * cos;
        return new Vec3(x, vec.y, z);
    }

    private void forEachTargetInCone(double radius, double coneAngleDegrees, java.util.function.Consumer<Player> action) {
        for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(radius))) {
            if (this.distanceTo(player) <= radius && isInFrontCone(player, coneAngleDegrees)) {
                action.accept(player);
            }
        }
    }

    private boolean isInFrontCone(Player player, double coneAngleDegrees) {
        double dx = player.getX() - this.getX();
        double dz = player.getZ() - this.getZ();
        double angleToPlayer = Math.toDegrees(Math.atan2(dz, dx));
        double yaw = Mth.wrapDegrees(this.getYRot() + 90.0);
        double diff = Mth.wrapDegrees(angleToPlayer - yaw);
        return Math.abs(diff) <= coneAngleDegrees / 2.0;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
