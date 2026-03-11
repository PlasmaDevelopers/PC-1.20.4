/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.util.TimeUtil;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class TickRateManager
/*    */ {
/*    */   public static final float MIN_TICKRATE = 1.0F;
/* 10 */   protected float tickrate = 20.0F;
/* 11 */   protected long nanosecondsPerTick = TimeUtil.NANOSECONDS_PER_SECOND / 20L;
/* 12 */   protected int frozenTicksToRun = 0;
/*    */   protected boolean runGameElements = true;
/*    */   protected boolean isFrozen = false;
/*    */   
/*    */   public void setTickRate(float $$0) {
/* 17 */     this.tickrate = Math.max($$0, 1.0F);
/* 18 */     this.nanosecondsPerTick = (long)(TimeUtil.NANOSECONDS_PER_SECOND / this.tickrate);
/*    */   }
/*    */   
/*    */   public float tickrate() {
/* 22 */     return this.tickrate;
/*    */   }
/*    */   
/*    */   public float millisecondsPerTick() {
/* 26 */     return (float)this.nanosecondsPerTick / (float)TimeUtil.NANOSECONDS_PER_MILLISECOND;
/*    */   }
/*    */   
/*    */   public long nanosecondsPerTick() {
/* 30 */     return this.nanosecondsPerTick;
/*    */   }
/*    */   
/*    */   public boolean runsNormally() {
/* 34 */     return this.runGameElements;
/*    */   }
/*    */   
/*    */   public boolean isSteppingForward() {
/* 38 */     return (this.frozenTicksToRun > 0);
/*    */   }
/*    */   
/*    */   public void setFrozenTicksToRun(int $$0) {
/* 42 */     this.frozenTicksToRun = $$0;
/*    */   }
/*    */   
/*    */   public int frozenTicksToRun() {
/* 46 */     return this.frozenTicksToRun;
/*    */   }
/*    */   
/*    */   public void setFrozen(boolean $$0) {
/* 50 */     this.isFrozen = $$0;
/*    */   }
/*    */   
/*    */   public boolean isFrozen() {
/* 54 */     return this.isFrozen;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 58 */     this.runGameElements = (!this.isFrozen || this.frozenTicksToRun > 0);
/* 59 */     if (this.frozenTicksToRun > 0) {
/* 60 */       this.frozenTicksToRun--;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isEntityFrozen(Entity $$0) {
/* 65 */     return (!runsNormally() && !($$0 instanceof net.minecraft.world.entity.player.Player) && $$0.countPlayerPassengers() <= 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\TickRateManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */