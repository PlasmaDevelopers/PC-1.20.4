/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WrappedGoal extends Goal {
/*     */   private final Goal goal;
/*     */   private final int priority;
/*     */   private boolean isRunning;
/*     */   
/*     */   public WrappedGoal(int $$0, Goal $$1) {
/*  12 */     this.priority = $$0;
/*  13 */     this.goal = $$1;
/*     */   }
/*     */   
/*     */   public boolean canBeReplacedBy(WrappedGoal $$0) {
/*  17 */     return (isInterruptable() && $$0.getPriority() < getPriority());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  22 */     return this.goal.canUse();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  27 */     return this.goal.canContinueToUse();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInterruptable() {
/*  32 */     return this.goal.isInterruptable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  37 */     if (this.isRunning) {
/*     */       return;
/*     */     }
/*  40 */     this.isRunning = true;
/*  41 */     this.goal.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  46 */     if (!this.isRunning) {
/*     */       return;
/*     */     }
/*  49 */     this.isRunning = false;
/*  50 */     this.goal.stop();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/*  55 */     return this.goal.requiresUpdateEveryTick();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int adjustedTickDelay(int $$0) {
/*  60 */     return this.goal.adjustedTickDelay($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  65 */     this.goal.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFlags(EnumSet<Goal.Flag> $$0) {
/*  70 */     this.goal.setFlags($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumSet<Goal.Flag> getFlags() {
/*  75 */     return this.goal.getFlags();
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/*  79 */     return this.isRunning;
/*     */   }
/*     */   
/*     */   public int getPriority() {
/*  83 */     return this.priority;
/*     */   }
/*     */   
/*     */   public Goal getGoal() {
/*  87 */     return this.goal;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object $$0) {
/*  92 */     if (this == $$0) {
/*  93 */       return true;
/*     */     }
/*  95 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  96 */       return false;
/*     */     }
/*  98 */     return this.goal.equals(((WrappedGoal)$$0).goal);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     return this.goal.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\WrappedGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */