/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SculkSensorBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.BlockPositionSource;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VibrationUser
/*     */   implements VibrationSystem.User
/*     */ {
/*     */   public static final int LISTENER_RANGE = 8;
/*     */   protected final BlockPos blockPos;
/*     */   private final PositionSource positionSource;
/*     */   
/*     */   public VibrationUser(BlockPos $$1) {
/*  98 */     this.blockPos = $$1;
/*  99 */     this.positionSource = (PositionSource)new BlockPositionSource($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getListenerRadius() {
/* 104 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionSource getPositionSource() {
/* 109 */     return this.positionSource;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTriggerAvoidVibration() {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable GameEvent.Context $$3) {
/* 123 */     if ($$1.equals(this.blockPos) && ($$2 == GameEvent.BLOCK_DESTROY || $$2 == GameEvent.BLOCK_PLACE)) {
/* 124 */       return false;
/*     */     }
/*     */     
/* 127 */     return SculkSensorBlock.canActivate(SculkSensorBlockEntity.this.getBlockState());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
/* 132 */     BlockState $$6 = SculkSensorBlockEntity.this.getBlockState();
/* 133 */     if (SculkSensorBlock.canActivate($$6)) {
/* 134 */       SculkSensorBlockEntity.this.setLastVibrationFrequency(VibrationSystem.getGameEventFrequency($$2));
/* 135 */       int $$7 = VibrationSystem.getRedstoneStrengthForDistance($$5, getListenerRadius());
/* 136 */       Block block = $$6.getBlock(); if (block instanceof SculkSensorBlock) { SculkSensorBlock $$8 = (SculkSensorBlock)block;
/* 137 */         $$8.activate($$3, (Level)$$0, this.blockPos, $$6, $$7, SculkSensorBlockEntity.this.getLastVibrationFrequency()); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataChanged() {
/* 144 */     SculkSensorBlockEntity.this.setChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresAdjacentChunksToBeTicking() {
/* 149 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SculkSensorBlockEntity$VibrationUser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */