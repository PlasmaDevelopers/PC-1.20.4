/*     */ package net.minecraft.world.entity.npc;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
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
/*     */ class WanderToPositionGoal
/*     */   extends Goal
/*     */ {
/*     */   final WanderingTrader trader;
/*     */   final double stopDistance;
/*     */   final double speedModifier;
/*     */   
/*     */   WanderToPositionGoal(WanderingTrader $$0, double $$1, double $$2) {
/* 263 */     this.trader = $$0;
/* 264 */     this.stopDistance = $$1;
/* 265 */     this.speedModifier = $$2;
/* 266 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 271 */     this.trader.setWanderTarget((BlockPos)null);
/* 272 */     WanderingTrader.access$000(WanderingTrader.this).stop();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 277 */     BlockPos $$0 = this.trader.getWanderTarget();
/* 278 */     return ($$0 != null && isTooFarAway($$0, this.stopDistance));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 283 */     BlockPos $$0 = this.trader.getWanderTarget();
/* 284 */     if ($$0 != null && WanderingTrader.access$100(WanderingTrader.this).isDone()) {
/* 285 */       if (isTooFarAway($$0, 10.0D)) {
/*     */         
/* 287 */         Vec3 $$1 = (new Vec3($$0.getX() - this.trader.getX(), $$0.getY() - this.trader.getY(), $$0.getZ() - this.trader.getZ())).normalize();
/* 288 */         Vec3 $$2 = $$1.scale(10.0D).add(this.trader.getX(), this.trader.getY(), this.trader.getZ());
/* 289 */         WanderingTrader.access$200(WanderingTrader.this).moveTo($$2.x, $$2.y, $$2.z, this.speedModifier);
/*     */       } else {
/* 291 */         WanderingTrader.access$300(WanderingTrader.this).moveTo($$0.getX(), $$0.getY(), $$0.getZ(), this.speedModifier);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isTooFarAway(BlockPos $$0, double $$1) {
/* 297 */     return !$$0.closerToCenterThan((Position)this.trader.position(), $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\WanderingTrader$WanderToPositionGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */