/*     */ package net.minecraft.world.level.gameevent.vibrations;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Optional;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.VibrationParticleOption;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.GameEventTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.ClipBlockStateContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface VibrationSystem
/*     */ {
/*  49 */   public static final GameEvent[] RESONANCE_EVENTS = new GameEvent[] { GameEvent.RESONATE_1, GameEvent.RESONATE_2, GameEvent.RESONATE_3, GameEvent.RESONATE_4, GameEvent.RESONATE_5, GameEvent.RESONATE_6, GameEvent.RESONATE_7, GameEvent.RESONATE_8, GameEvent.RESONATE_9, GameEvent.RESONATE_10, GameEvent.RESONATE_11, GameEvent.RESONATE_12, GameEvent.RESONATE_13, GameEvent.RESONATE_14, GameEvent.RESONATE_15 };
/*     */   
/*     */   public static final ToIntFunction<GameEvent> VIBRATION_FREQUENCY_FOR_EVENT;
/*     */ 
/*     */   
/*     */   static {
/*  55 */     VIBRATION_FREQUENCY_FOR_EVENT = (ToIntFunction<GameEvent>)Util.make(new Object2IntOpenHashMap(), $$0 -> {
/*     */           $$0.defaultReturnValue(0);
/*     */           $$0.put(GameEvent.STEP, 1);
/*     */           $$0.put(GameEvent.SWIM, 1);
/*     */           $$0.put(GameEvent.FLAP, 1);
/*     */           $$0.put(GameEvent.PROJECTILE_LAND, 2);
/*     */           $$0.put(GameEvent.HIT_GROUND, 2);
/*     */           $$0.put(GameEvent.SPLASH, 2);
/*     */           $$0.put(GameEvent.ITEM_INTERACT_FINISH, 3);
/*     */           $$0.put(GameEvent.PROJECTILE_SHOOT, 3);
/*     */           $$0.put(GameEvent.INSTRUMENT_PLAY, 3);
/*     */           $$0.put(GameEvent.ENTITY_ACTION, 4);
/*     */           $$0.put(GameEvent.ELYTRA_GLIDE, 4);
/*     */           $$0.put(GameEvent.UNEQUIP, 4);
/*     */           $$0.put(GameEvent.ENTITY_DISMOUNT, 5);
/*     */           $$0.put(GameEvent.EQUIP, 5);
/*     */           $$0.put(GameEvent.ENTITY_INTERACT, 6);
/*     */           $$0.put(GameEvent.SHEAR, 6);
/*     */           $$0.put(GameEvent.ENTITY_MOUNT, 6);
/*     */           $$0.put(GameEvent.ENTITY_DAMAGE, 7);
/*     */           $$0.put(GameEvent.DRINK, 8);
/*     */           $$0.put(GameEvent.EAT, 8);
/*     */           $$0.put(GameEvent.CONTAINER_CLOSE, 9);
/*     */           $$0.put(GameEvent.BLOCK_CLOSE, 9);
/*     */           $$0.put(GameEvent.BLOCK_DEACTIVATE, 9);
/*     */           $$0.put(GameEvent.BLOCK_DETACH, 9);
/*     */           $$0.put(GameEvent.CONTAINER_OPEN, 10);
/*     */           $$0.put(GameEvent.BLOCK_OPEN, 10);
/*     */           $$0.put(GameEvent.BLOCK_ACTIVATE, 10);
/*     */           $$0.put(GameEvent.BLOCK_ATTACH, 10);
/*     */           $$0.put(GameEvent.PRIME_FUSE, 10);
/*     */           $$0.put(GameEvent.NOTE_BLOCK_PLAY, 10);
/*     */           $$0.put(GameEvent.BLOCK_CHANGE, 11);
/*     */           $$0.put(GameEvent.BLOCK_DESTROY, 12);
/*     */           $$0.put(GameEvent.FLUID_PICKUP, 12);
/*     */           $$0.put(GameEvent.BLOCK_PLACE, 13);
/*     */           $$0.put(GameEvent.FLUID_PLACE, 13);
/*     */           $$0.put(GameEvent.ENTITY_PLACE, 14);
/*     */           $$0.put(GameEvent.LIGHTNING_STRIKE, 14);
/*     */           $$0.put(GameEvent.TELEPORT, 14);
/*     */           $$0.put(GameEvent.ENTITY_DIE, 15);
/*     */           $$0.put(GameEvent.EXPLODE, 15);
/*     */           for (int $$1 = 1; $$1 <= 15; $$1++) {
/*     */             $$0.put(getResonanceEventByFrequency($$1), $$1);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getGameEventFrequency(GameEvent $$0) {
/* 135 */     return VIBRATION_FREQUENCY_FOR_EVENT.applyAsInt($$0);
/*     */   }
/*     */   
/*     */   static GameEvent getResonanceEventByFrequency(int $$0) {
/* 139 */     return RESONANCE_EVENTS[$$0 - 1];
/*     */   }
/*     */   
/*     */   static int getRedstoneStrengthForDistance(float $$0, int $$1) {
/* 143 */     double $$2 = 15.0D / $$1;
/* 144 */     return Math.max(1, 15 - Mth.floor($$2 * $$0));
/*     */   }
/*     */   
/*     */   Data getVibrationData();
/*     */   
/*     */   User getVibrationUser();
/*     */   
/*     */   public static final class Data {
/*     */     static {
/* 153 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)VibrationInfo.CODEC.optionalFieldOf("event").forGetter(()), (App)VibrationSelector.CODEC.fieldOf("selector").forGetter(Data::getSelectionStrategy), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("event_delay").orElse(Integer.valueOf(0)).forGetter(Data::getTravelTimeInTicks)).apply((Applicative)$$0, ()));
/*     */     }
/*     */ 
/*     */     
/*     */     public static Codec<Data> CODEC;
/*     */     
/*     */     public static final String NBT_TAG_KEY = "listener";
/*     */     
/*     */     @Nullable
/*     */     VibrationInfo currentVibration;
/*     */     private int travelTimeInTicks;
/*     */     final VibrationSelector selectionStrategy;
/*     */     private boolean reloadVibrationParticle;
/*     */     
/*     */     private Data(@Nullable VibrationInfo $$0, VibrationSelector $$1, int $$2, boolean $$3) {
/* 168 */       this.currentVibration = $$0;
/* 169 */       this.travelTimeInTicks = $$2;
/* 170 */       this.selectionStrategy = $$1;
/* 171 */       this.reloadVibrationParticle = $$3;
/*     */     }
/*     */     
/*     */     public Data() {
/* 175 */       this(null, new VibrationSelector(), 0, false);
/*     */     }
/*     */     
/*     */     public VibrationSelector getSelectionStrategy() {
/* 179 */       return this.selectionStrategy;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public VibrationInfo getCurrentVibration() {
/* 184 */       return this.currentVibration;
/*     */     }
/*     */     
/*     */     public void setCurrentVibration(@Nullable VibrationInfo $$0) {
/* 188 */       this.currentVibration = $$0;
/*     */     }
/*     */     
/*     */     public int getTravelTimeInTicks() {
/* 192 */       return this.travelTimeInTicks;
/*     */     }
/*     */     
/*     */     public void setTravelTimeInTicks(int $$0) {
/* 196 */       this.travelTimeInTicks = $$0;
/*     */     }
/*     */     
/*     */     public void decrementTravelTime() {
/* 200 */       this.travelTimeInTicks = Math.max(0, this.travelTimeInTicks - 1);
/*     */     }
/*     */     
/*     */     public boolean shouldReloadVibrationParticle() {
/* 204 */       return this.reloadVibrationParticle;
/*     */     }
/*     */     
/*     */     public void setReloadVibrationParticle(boolean $$0) {
/* 208 */       this.reloadVibrationParticle = $$0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Listener
/*     */     implements GameEventListener
/*     */   {
/*     */     private final VibrationSystem system;
/*     */ 
/*     */     
/*     */     public Listener(VibrationSystem $$0) {
/* 221 */       this.system = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionSource getListenerSource() {
/* 226 */       return this.system.getVibrationUser().getPositionSource();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getListenerRadius() {
/* 231 */       return this.system.getVibrationUser().getListenerRadius();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean handleGameEvent(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/* 236 */       VibrationSystem.Data $$4 = this.system.getVibrationData();
/* 237 */       VibrationSystem.User $$5 = this.system.getVibrationUser();
/*     */ 
/*     */       
/* 240 */       if ($$4.getCurrentVibration() != null) {
/* 241 */         return false;
/*     */       }
/*     */       
/* 244 */       if (!$$5.isValidVibration($$1, $$2)) {
/* 245 */         return false;
/*     */       }
/*     */       
/* 248 */       Optional<Vec3> $$6 = $$5.getPositionSource().getPosition((Level)$$0);
/*     */       
/* 250 */       if ($$6.isEmpty()) {
/* 251 */         return false;
/*     */       }
/*     */       
/* 254 */       Vec3 $$7 = $$6.get();
/*     */ 
/*     */       
/* 257 */       if (!$$5.canReceiveVibration($$0, BlockPos.containing((Position)$$3), $$1, $$2)) {
/* 258 */         return false;
/*     */       }
/*     */       
/* 261 */       if (isOccluded((Level)$$0, $$3, $$7)) {
/* 262 */         return false;
/*     */       }
/*     */       
/* 265 */       scheduleVibration($$0, $$4, $$1, $$2, $$3, $$7);
/*     */       
/* 267 */       return true;
/*     */     }
/*     */     
/*     */     public void forceScheduleVibration(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/* 271 */       this.system.getVibrationUser().getPositionSource().getPosition((Level)$$0).ifPresent($$4 -> scheduleVibration($$0, this.system.getVibrationData(), $$1, $$2, $$3, $$4));
/*     */     }
/*     */     
/*     */     private void scheduleVibration(ServerLevel $$0, VibrationSystem.Data $$1, GameEvent $$2, GameEvent.Context $$3, Vec3 $$4, Vec3 $$5) {
/* 275 */       $$1.selectionStrategy.addCandidate(new VibrationInfo($$2, (float)$$4.distanceTo($$5), $$4, $$3.sourceEntity()), $$0.getGameTime());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static float distanceBetweenInBlocks(BlockPos $$0, BlockPos $$1) {
/* 291 */       return (float)Math.sqrt($$0.distSqr((Vec3i)$$1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isOccluded(Level $$0, Vec3 $$1, Vec3 $$2) {
/* 300 */       Vec3 $$3 = new Vec3(Mth.floor($$1.x) + 0.5D, Mth.floor($$1.y) + 0.5D, Mth.floor($$1.z) + 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 305 */       Vec3 $$4 = new Vec3(Mth.floor($$2.x) + 0.5D, Mth.floor($$2.y) + 0.5D, Mth.floor($$2.z) + 0.5D);
/*     */ 
/*     */       
/* 308 */       for (Direction $$5 : Direction.values()) {
/* 309 */         Vec3 $$6 = $$3.relative($$5, 9.999999747378752E-6D);
/* 310 */         if ($$0.isBlockInLine(new ClipBlockStateContext($$6, $$4, $$0 -> $$0.is(BlockTags.OCCLUDES_VIBRATION_SIGNALS))).getType() != HitResult.Type.BLOCK) {
/* 311 */           return false;
/*     */         }
/*     */       } 
/* 314 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Ticker
/*     */   {
/*     */     static void tick(Level $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2) {
/*     */       ServerLevel $$3;
/* 327 */       if ($$0 instanceof ServerLevel) { $$3 = (ServerLevel)$$0; }
/*     */       else
/*     */       { return; }
/*     */       
/* 331 */       if ($$1.currentVibration == null) {
/* 332 */         trySelectAndScheduleVibration($$3, $$1, $$2);
/*     */       }
/*     */       
/* 335 */       if ($$1.currentVibration == null) {
/*     */         return;
/*     */       }
/*     */       
/* 339 */       boolean $$5 = ($$1.getTravelTimeInTicks() > 0);
/* 340 */       tryReloadVibrationParticle($$3, $$1, $$2);
/* 341 */       $$1.decrementTravelTime();
/*     */       
/* 343 */       if ($$1.getTravelTimeInTicks() <= 0) {
/* 344 */         $$5 = receiveVibration($$3, $$1, $$2, $$1.currentVibration);
/*     */       }
/*     */       
/* 347 */       if ($$5) {
/* 348 */         $$2.onDataChanged();
/*     */       }
/*     */     }
/*     */     
/*     */     private static void trySelectAndScheduleVibration(ServerLevel $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2) {
/* 353 */       $$1.getSelectionStrategy().chosenCandidate($$0.getGameTime()).ifPresent($$3 -> {
/*     */             $$0.setCurrentVibration($$3);
/*     */             Vec3 $$4 = $$3.pos();
/*     */             $$0.setTravelTimeInTicks($$1.calculateTravelTimeInTicks($$3.distance()));
/*     */             $$2.sendParticles((ParticleOptions)new VibrationParticleOption($$1.getPositionSource(), $$0.getTravelTimeInTicks()), $$4.x, $$4.y, $$4.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */             $$1.onDataChanged();
/*     */             $$0.getSelectionStrategy().startOver();
/*     */           });
/*     */     }
/*     */     
/*     */     private static void tryReloadVibrationParticle(ServerLevel $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2) {
/* 364 */       if (!$$1.shouldReloadVibrationParticle()) {
/*     */         return;
/*     */       }
/*     */       
/* 368 */       if ($$1.currentVibration == null) {
/* 369 */         $$1.setReloadVibrationParticle(false);
/*     */         
/*     */         return;
/*     */       } 
/* 373 */       Vec3 $$3 = $$1.currentVibration.pos();
/* 374 */       PositionSource $$4 = $$2.getPositionSource();
/* 375 */       Vec3 $$5 = $$4.getPosition((Level)$$0).orElse($$3);
/* 376 */       int $$6 = $$1.getTravelTimeInTicks();
/*     */       
/* 378 */       int $$7 = $$2.calculateTravelTimeInTicks($$1.currentVibration.distance());
/* 379 */       double $$8 = 1.0D - $$6 / $$7;
/*     */       
/* 381 */       double $$9 = Mth.lerp($$8, $$3.x, $$5.x);
/* 382 */       double $$10 = Mth.lerp($$8, $$3.y, $$5.y);
/* 383 */       double $$11 = Mth.lerp($$8, $$3.z, $$5.z);
/*     */       
/* 385 */       boolean $$12 = ($$0.sendParticles((ParticleOptions)new VibrationParticleOption($$4, $$6), $$9, $$10, $$11, 1, 0.0D, 0.0D, 0.0D, 0.0D) > 0);
/*     */       
/* 387 */       if ($$12) {
/* 388 */         $$1.setReloadVibrationParticle(false);
/*     */       }
/*     */     }
/*     */     
/*     */     private static boolean receiveVibration(ServerLevel $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2, VibrationInfo $$3) {
/* 393 */       BlockPos $$4 = BlockPos.containing((Position)$$3.pos());
/* 394 */       BlockPos $$5 = $$2.getPositionSource().getPosition((Level)$$0).map(BlockPos::containing).orElse($$4);
/*     */ 
/*     */ 
/*     */       
/* 398 */       if ($$2.requiresAdjacentChunksToBeTicking() && !areAdjacentChunksTicking((Level)$$0, $$5)) {
/* 399 */         return false;
/*     */       }
/*     */       
/* 402 */       $$2.onReceiveVibration($$0, $$4, $$3
/*     */ 
/*     */           
/* 405 */           .gameEvent(), $$3
/* 406 */           .getEntity($$0).orElse(null), $$3
/* 407 */           .getProjectileOwner($$0).orElse(null), 
/* 408 */           VibrationSystem.Listener.distanceBetweenInBlocks($$4, $$5));
/*     */ 
/*     */ 
/*     */       
/* 412 */       $$1.setCurrentVibration(null);
/* 413 */       return true;
/*     */     }
/*     */     
/*     */     private static boolean areAdjacentChunksTicking(Level $$0, BlockPos $$1) {
/* 417 */       ChunkPos $$2 = new ChunkPos($$1);
/*     */       
/* 419 */       for (int $$3 = $$2.x - 1; $$3 <= $$2.x + 1; $$3++) {
/* 420 */         for (int $$4 = $$2.z - 1; $$4 <= $$2.z + 1; $$4++) {
/* 421 */           if (!$$0.shouldTickBlocksAt(ChunkPos.asLong($$3, $$4)) || $$0.getChunkSource().getChunkNow($$3, $$4) == null) {
/* 422 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 427 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface User
/*     */   {
/*     */     int getListenerRadius();
/*     */ 
/*     */ 
/*     */     
/*     */     PositionSource getPositionSource();
/*     */ 
/*     */ 
/*     */     
/*     */     boolean canReceiveVibration(ServerLevel param1ServerLevel, BlockPos param1BlockPos, GameEvent param1GameEvent, GameEvent.Context param1Context);
/*     */ 
/*     */ 
/*     */     
/*     */     void onReceiveVibration(ServerLevel param1ServerLevel, BlockPos param1BlockPos, GameEvent param1GameEvent, @Nullable Entity param1Entity1, @Nullable Entity param1Entity2, float param1Float);
/*     */ 
/*     */ 
/*     */     
/*     */     default TagKey<GameEvent> getListenableEvents() {
/* 453 */       return GameEventTags.VIBRATIONS;
/*     */     }
/*     */     
/*     */     default boolean canTriggerAvoidVibration() {
/* 457 */       return false;
/*     */     }
/*     */     
/*     */     default boolean requiresAdjacentChunksToBeTicking() {
/* 461 */       return false;
/*     */     }
/*     */     
/*     */     default int calculateTravelTimeInTicks(float $$0) {
/* 465 */       return Mth.floor($$0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     default boolean isValidVibration(GameEvent $$0, GameEvent.Context $$1) {
/* 481 */       if (!$$0.is(getListenableEvents())) {
/* 482 */         return false;
/*     */       }
/*     */       
/* 485 */       Entity $$2 = $$1.sourceEntity();
/*     */       
/* 487 */       if ($$2 != null) {
/* 488 */         if ($$2.isSpectator()) {
/* 489 */           return false;
/*     */         }
/*     */         
/* 492 */         if ($$2.isSteppingCarefully() && $$0.is(GameEventTags.IGNORE_VIBRATIONS_SNEAKING)) {
/* 493 */           if (canTriggerAvoidVibration() && $$2 instanceof ServerPlayer) { ServerPlayer $$3 = (ServerPlayer)$$2;
/* 494 */             CriteriaTriggers.AVOID_VIBRATION.trigger($$3); }
/*     */ 
/*     */           
/* 497 */           return false;
/*     */         } 
/*     */         
/* 500 */         if ($$2.dampensVibrations()) {
/* 501 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 505 */       if ($$1.affectedState() != null) {
/* 506 */         return !$$1.affectedState().is(BlockTags.DAMPENS_VIBRATIONS);
/*     */       }
/*     */       
/* 509 */       return true;
/*     */     }
/*     */     
/*     */     default void onDataChanged() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\vibrations\VibrationSystem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */