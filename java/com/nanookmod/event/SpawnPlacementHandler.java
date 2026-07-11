package com.nanookmod.event;

import com.nanookmod.NanookMod;
import com.nanookmod.entity.SnowyBlizzEntity;
import com.nanookmod.registry.ModBlocks;
import com.nanookmod.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NanookMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpawnPlacementHandler {

    private static final double MIN_SPACING = 10.0D;
    static final boolean DEBUG_LOG = true;

    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.SNOWY_BLIZZ.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPlacementHandler::canSnowyBlizzSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }

    private static boolean canSnowyBlizzSpawn(EntityType<SnowyBlizzEntity> type, ServerLevelAccessor level,
                                              MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            debugLog(level, pos, "RECHAZADO: dificultad Peaceful");
            return false;
        }

        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        if (belowState.is(BlockTags.ICE) || belowState.is(ModBlocks.FROST_ICE.get())) {
            debugLog(level, pos, "RECHAZADO: piso es hielo");
            return false;
        }

        boolean standableSurface = belowState.is(Blocks.SNOW)
                || belowState.isFaceSturdy(level, belowPos, Direction.UP);

        if (!standableSurface) {
            debugLog(level, pos, "RECHAZADO: superficie no válida (" + belowState.getBlock() + ")");
            return false;
        }

        if (!isPassable(level, pos) || !isPassable(level, pos.above())) {
            BlockState blockingLow = level.getBlockState(pos);
            BlockState blockingHigh = level.getBlockState(pos.above());
            debugLog(level, pos, "RECHAZADO: sin espacio libre (bloque en pos=" + blockingLow.getBlock()
                    + ", bloque en pos+1=" + blockingHigh.getBlock() + ")");
            return false;
        }

        AABB nearbyArea = new AABB(pos).inflate(MIN_SPACING);
        int nearbyCount = level.getEntitiesOfClass(SnowyBlizzEntity.class, nearbyArea).size();
        if (nearbyCount > 0) {
            debugLog(level, pos, "RECHAZADO: ya hay " + nearbyCount + " cerca (spacing)");
            return false;
        }

        debugLog(level, pos, "ACEPTADO");
        return true;
    }

    static void debugLog(ServerLevelAccessor level, BlockPos pos, String result) {
        if (DEBUG_LOG) {
            long dayTime = level.getLevel().getDayTime() % 24000L;
            boolean isDay = level.getLevel().isDay();
            NanookMod.LOGGER.info("[snowy_blizz spawn] pos={} dayTime={} isDay={} -> {}",
                    pos, dayTime, isDay, result);
        }
    }

    private static boolean isPassable(ServerLevelAccessor level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        // ✅ Igual que hace Minecraft internamente (NaturalSpawner.isValidEmptySpawnBlock):
        // solo bloquea si el bloque es un cubo SÓLIDO completo. Así, una capa fina de
        // nieve (minecraft:snow) no cuenta como "sin espacio", ya que es caminable.
        return !state.isCollisionShapeFullBlock(level, pos) && state.getFluidState().isEmpty();
    }
}