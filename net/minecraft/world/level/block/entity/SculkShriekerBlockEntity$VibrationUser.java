/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.GameEventTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.block.SculkShriekerBlock;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class VibrationUser
/*     */   implements VibrationSystem.User
/*     */ {
/*     */   private static final int LISTENER_RADIUS = 8;
/*     */   private final PositionSource positionSource;
/*     */   
/*     */   public VibrationUser() {
/* 214 */     this.positionSource = (PositionSource)new BlockPositionSource(paramSculkShriekerBlockEntity.worldPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getListenerRadius() {
/* 219 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionSource getPositionSource() {
/* 224 */     return this.positionSource;
/*     */   }
/*     */ 
/*     */   
/*     */   public TagKey<GameEvent> getListenableEvents() {
/* 229 */     return GameEventTags.SHRIEKER_CAN_LISTEN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, GameEvent.Context $$3) {
/* 234 */     return (!((Boolean)SculkShriekerBlockEntity.this.getBlockState().getValue((Property)SculkShriekerBlock.SHRIEKING)).booleanValue() && SculkShriekerBlockEntity.tryGetPlayer($$3.sourceEntity()) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
/* 239 */     SculkShriekerBlockEntity.this.tryShriek($$0, SculkShriekerBlockEntity.tryGetPlayer(($$4 != null) ? $$4 : $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataChanged() {
/* 244 */     SculkShriekerBlockEntity.this.setChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresAdjacentChunksToBeTicking() {
/* 249 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SculkShriekerBlockEntity$VibrationUser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */