/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkSource;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.redstone.NeighborUpdater;
/*     */ import net.minecraft.world.level.storage.LevelData;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.ticks.LevelTickAccess;
/*     */ import net.minecraft.world.ticks.ScheduledTick;
/*     */ import net.minecraft.world.ticks.TickPriority;
/*     */ 
/*     */ public interface LevelAccessor
/*     */   extends CommonLevelAccessor, LevelTimeAccess {
/*     */   default long dayTime() {
/*  31 */     return getLevelData().getDayTime();
/*     */   }
/*     */   
/*     */   long nextSubTickCount();
/*     */   
/*     */   LevelTickAccess<Block> getBlockTicks();
/*     */   
/*     */   private <T> ScheduledTick<T> createTick(BlockPos $$0, T $$1, int $$2, TickPriority $$3) {
/*  39 */     return new ScheduledTick($$1, $$0, getLevelData().getGameTime() + $$2, $$3, nextSubTickCount());
/*     */   }
/*     */   
/*     */   private <T> ScheduledTick<T> createTick(BlockPos $$0, T $$1, int $$2) {
/*  43 */     return new ScheduledTick($$1, $$0, getLevelData().getGameTime() + $$2, nextSubTickCount());
/*     */   }
/*     */   
/*     */   default void scheduleTick(BlockPos $$0, Block $$1, int $$2, TickPriority $$3) {
/*  47 */     getBlockTicks().schedule(createTick($$0, $$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   default void scheduleTick(BlockPos $$0, Block $$1, int $$2) {
/*  51 */     getBlockTicks().schedule(createTick($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   LevelTickAccess<Fluid> getFluidTicks();
/*     */   
/*     */   default void scheduleTick(BlockPos $$0, Fluid $$1, int $$2, TickPriority $$3) {
/*  57 */     getFluidTicks().schedule(createTick($$0, $$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   default void scheduleTick(BlockPos $$0, Fluid $$1, int $$2) {
/*  61 */     getFluidTicks().schedule(createTick($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   LevelData getLevelData();
/*     */   
/*     */   DifficultyInstance getCurrentDifficultyAt(BlockPos paramBlockPos);
/*     */   
/*     */   @Nullable
/*     */   MinecraftServer getServer();
/*     */   
/*     */   default Difficulty getDifficulty() {
/*  72 */     return getLevelData().getDifficulty();
/*     */   }
/*     */ 
/*     */   
/*     */   ChunkSource getChunkSource();
/*     */   
/*     */   default boolean hasChunk(int $$0, int $$1) {
/*  79 */     return getChunkSource().hasChunk($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   RandomSource getRandom();
/*     */   
/*     */   default void blockUpdated(BlockPos $$0, Block $$1) {}
/*     */   
/*     */   default void neighborShapeChanged(Direction $$0, BlockState $$1, BlockPos $$2, BlockPos $$3, int $$4, int $$5) {
/*  88 */     NeighborUpdater.executeShapeUpdate(this, $$0, $$1, $$2, $$3, $$4, $$5 - 1);
/*     */   }
/*     */   
/*     */   default void playSound(@Nullable Player $$0, BlockPos $$1, SoundEvent $$2, SoundSource $$3) {
/*  92 */     playSound($$0, $$1, $$2, $$3, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   void playSound(@Nullable Player paramPlayer, BlockPos paramBlockPos, SoundEvent paramSoundEvent, SoundSource paramSoundSource, float paramFloat1, float paramFloat2);
/*     */   
/*     */   void addParticle(ParticleOptions paramParticleOptions, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   void levelEvent(@Nullable Player paramPlayer, int paramInt1, BlockPos paramBlockPos, int paramInt2);
/*     */   
/*     */   default void levelEvent(int $$0, BlockPos $$1, int $$2) {
/* 102 */     levelEvent(null, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   void gameEvent(GameEvent paramGameEvent, Vec3 paramVec3, GameEvent.Context paramContext);
/*     */   
/*     */   default void gameEvent(@Nullable Entity $$0, GameEvent $$1, Vec3 $$2) {
/* 108 */     gameEvent($$1, $$2, new GameEvent.Context($$0, null));
/*     */   }
/*     */   
/*     */   default void gameEvent(@Nullable Entity $$0, GameEvent $$1, BlockPos $$2) {
/* 112 */     gameEvent($$1, $$2, new GameEvent.Context($$0, null));
/*     */   }
/*     */   
/*     */   default void gameEvent(GameEvent $$0, BlockPos $$1, GameEvent.Context $$2) {
/* 116 */     gameEvent($$0, Vec3.atCenterOf((Vec3i)$$1), $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LevelAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */