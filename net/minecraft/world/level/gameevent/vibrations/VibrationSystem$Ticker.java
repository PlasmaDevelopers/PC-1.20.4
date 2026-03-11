/*     */ package net.minecraft.world.level.gameevent.vibrations;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.VibrationParticleOption;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
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
/*     */ public interface Ticker
/*     */ {
/*     */   static void tick(Level $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2) {
/*     */     ServerLevel $$3;
/* 327 */     if ($$0 instanceof ServerLevel) { $$3 = (ServerLevel)$$0; }
/*     */     else
/*     */     { return; }
/*     */     
/* 331 */     if ($$1.currentVibration == null) {
/* 332 */       trySelectAndScheduleVibration($$3, $$1, $$2);
/*     */     }
/*     */     
/* 335 */     if ($$1.currentVibration == null) {
/*     */       return;
/*     */     }
/*     */     
/* 339 */     boolean $$5 = ($$1.getTravelTimeInTicks() > 0);
/* 340 */     tryReloadVibrationParticle($$3, $$1, $$2);
/* 341 */     $$1.decrementTravelTime();
/*     */     
/* 343 */     if ($$1.getTravelTimeInTicks() <= 0) {
/* 344 */       $$5 = receiveVibration($$3, $$1, $$2, $$1.currentVibration);
/*     */     }
/*     */     
/* 347 */     if ($$5) {
/* 348 */       $$2.onDataChanged();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void trySelectAndScheduleVibration(ServerLevel $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2) {
/* 353 */     $$1.getSelectionStrategy().chosenCandidate($$0.getGameTime()).ifPresent($$3 -> {
/*     */           $$0.setCurrentVibration($$3);
/*     */           Vec3 $$4 = $$3.pos();
/*     */           $$0.setTravelTimeInTicks($$1.calculateTravelTimeInTicks($$3.distance()));
/*     */           $$2.sendParticles((ParticleOptions)new VibrationParticleOption($$1.getPositionSource(), $$0.getTravelTimeInTicks()), $$4.x, $$4.y, $$4.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */           $$1.onDataChanged();
/*     */           $$0.getSelectionStrategy().startOver();
/*     */         });
/*     */   }
/*     */   
/*     */   private static void tryReloadVibrationParticle(ServerLevel $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2) {
/* 364 */     if (!$$1.shouldReloadVibrationParticle()) {
/*     */       return;
/*     */     }
/*     */     
/* 368 */     if ($$1.currentVibration == null) {
/* 369 */       $$1.setReloadVibrationParticle(false);
/*     */       
/*     */       return;
/*     */     } 
/* 373 */     Vec3 $$3 = $$1.currentVibration.pos();
/* 374 */     PositionSource $$4 = $$2.getPositionSource();
/* 375 */     Vec3 $$5 = $$4.getPosition((Level)$$0).orElse($$3);
/* 376 */     int $$6 = $$1.getTravelTimeInTicks();
/*     */     
/* 378 */     int $$7 = $$2.calculateTravelTimeInTicks($$1.currentVibration.distance());
/* 379 */     double $$8 = 1.0D - $$6 / $$7;
/*     */     
/* 381 */     double $$9 = Mth.lerp($$8, $$3.x, $$5.x);
/* 382 */     double $$10 = Mth.lerp($$8, $$3.y, $$5.y);
/* 383 */     double $$11 = Mth.lerp($$8, $$3.z, $$5.z);
/*     */     
/* 385 */     boolean $$12 = ($$0.sendParticles((ParticleOptions)new VibrationParticleOption($$4, $$6), $$9, $$10, $$11, 1, 0.0D, 0.0D, 0.0D, 0.0D) > 0);
/*     */     
/* 387 */     if ($$12) {
/* 388 */       $$1.setReloadVibrationParticle(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean receiveVibration(ServerLevel $$0, VibrationSystem.Data $$1, VibrationSystem.User $$2, VibrationInfo $$3) {
/* 393 */     BlockPos $$4 = BlockPos.containing((Position)$$3.pos());
/* 394 */     BlockPos $$5 = $$2.getPositionSource().getPosition((Level)$$0).map(BlockPos::containing).orElse($$4);
/*     */ 
/*     */ 
/*     */     
/* 398 */     if ($$2.requiresAdjacentChunksToBeTicking() && !areAdjacentChunksTicking((Level)$$0, $$5)) {
/* 399 */       return false;
/*     */     }
/*     */     
/* 402 */     $$2.onReceiveVibration($$0, $$4, $$3
/*     */ 
/*     */         
/* 405 */         .gameEvent(), $$3
/* 406 */         .getEntity($$0).orElse(null), $$3
/* 407 */         .getProjectileOwner($$0).orElse(null), 
/* 408 */         VibrationSystem.Listener.distanceBetweenInBlocks($$4, $$5));
/*     */ 
/*     */ 
/*     */     
/* 412 */     $$1.setCurrentVibration(null);
/* 413 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean areAdjacentChunksTicking(Level $$0, BlockPos $$1) {
/* 417 */     ChunkPos $$2 = new ChunkPos($$1);
/*     */     
/* 419 */     for (int $$3 = $$2.x - 1; $$3 <= $$2.x + 1; $$3++) {
/* 420 */       for (int $$4 = $$2.z - 1; $$4 <= $$2.z + 1; $$4++) {
/* 421 */         if (!$$0.shouldTickBlocksAt(ChunkPos.asLong($$3, $$4)) || $$0.getChunkSource().getChunkNow($$3, $$4) == null) {
/* 422 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 427 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\vibrations\VibrationSystem$Ticker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */