/*     */ package net.minecraft.world.level.gameevent.vibrations;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Listener
/*     */   implements GameEventListener
/*     */ {
/*     */   private final VibrationSystem system;
/*     */   
/*     */   public Listener(VibrationSystem $$0) {
/* 221 */     this.system = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionSource getListenerSource() {
/* 226 */     return this.system.getVibrationUser().getPositionSource();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getListenerRadius() {
/* 231 */     return this.system.getVibrationUser().getListenerRadius();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleGameEvent(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/* 236 */     VibrationSystem.Data $$4 = this.system.getVibrationData();
/* 237 */     VibrationSystem.User $$5 = this.system.getVibrationUser();
/*     */ 
/*     */     
/* 240 */     if ($$4.getCurrentVibration() != null) {
/* 241 */       return false;
/*     */     }
/*     */     
/* 244 */     if (!$$5.isValidVibration($$1, $$2)) {
/* 245 */       return false;
/*     */     }
/*     */     
/* 248 */     Optional<Vec3> $$6 = $$5.getPositionSource().getPosition((Level)$$0);
/*     */     
/* 250 */     if ($$6.isEmpty()) {
/* 251 */       return false;
/*     */     }
/*     */     
/* 254 */     Vec3 $$7 = $$6.get();
/*     */ 
/*     */     
/* 257 */     if (!$$5.canReceiveVibration($$0, BlockPos.containing((Position)$$3), $$1, $$2)) {
/* 258 */       return false;
/*     */     }
/*     */     
/* 261 */     if (isOccluded((Level)$$0, $$3, $$7)) {
/* 262 */       return false;
/*     */     }
/*     */     
/* 265 */     scheduleVibration($$0, $$4, $$1, $$2, $$3, $$7);
/*     */     
/* 267 */     return true;
/*     */   }
/*     */   
/*     */   public void forceScheduleVibration(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/* 271 */     this.system.getVibrationUser().getPositionSource().getPosition((Level)$$0).ifPresent($$4 -> scheduleVibration($$0, this.system.getVibrationData(), $$1, $$2, $$3, $$4));
/*     */   }
/*     */   
/*     */   private void scheduleVibration(ServerLevel $$0, VibrationSystem.Data $$1, GameEvent $$2, GameEvent.Context $$3, Vec3 $$4, Vec3 $$5) {
/* 275 */     $$1.selectionStrategy.addCandidate(new VibrationInfo($$2, (float)$$4.distanceTo($$5), $$4, $$3.sourceEntity()), $$0.getGameTime());
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
/*     */   public static float distanceBetweenInBlocks(BlockPos $$0, BlockPos $$1) {
/* 291 */     return (float)Math.sqrt($$0.distSqr((Vec3i)$$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isOccluded(Level $$0, Vec3 $$1, Vec3 $$2) {
/* 300 */     Vec3 $$3 = new Vec3(Mth.floor($$1.x) + 0.5D, Mth.floor($$1.y) + 0.5D, Mth.floor($$1.z) + 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     Vec3 $$4 = new Vec3(Mth.floor($$2.x) + 0.5D, Mth.floor($$2.y) + 0.5D, Mth.floor($$2.z) + 0.5D);
/*     */ 
/*     */     
/* 308 */     for (Direction $$5 : Direction.values()) {
/* 309 */       Vec3 $$6 = $$3.relative($$5, 9.999999747378752E-6D);
/* 310 */       if ($$0.isBlockInLine(new ClipBlockStateContext($$6, $$4, $$0 -> $$0.is(BlockTags.OCCLUDES_VIBRATION_SIGNALS))).getType() != HitResult.Type.BLOCK) {
/* 311 */         return false;
/*     */       }
/*     */     } 
/* 314 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\vibrations\VibrationSystem$Listener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */