/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JitteredLinearRetry
/*     */ {
/*     */   private static final int MIN_INTERVAL_INCREASE = 40;
/*     */   private static final int MAX_INTERVAL_INCREASE = 80;
/*     */   private static final int MAX_RETRY_PATHFINDING_INTERVAL = 400;
/*     */   private final RandomSource random;
/*     */   private long previousAttemptTimestamp;
/*     */   private long nextScheduledAttemptTimestamp;
/*     */   private int currentDelay;
/*     */   
/*     */   JitteredLinearRetry(RandomSource $$0, long $$1) {
/* 146 */     this.random = $$0;
/* 147 */     markAttempt($$1);
/*     */   }
/*     */   
/*     */   public void markAttempt(long $$0) {
/* 151 */     this.previousAttemptTimestamp = $$0;
/* 152 */     int $$1 = this.currentDelay + this.random.nextInt(40) + 40;
/* 153 */     this.currentDelay = Math.min($$1, 400);
/* 154 */     this.nextScheduledAttemptTimestamp = $$0 + this.currentDelay;
/*     */   }
/*     */   
/*     */   public boolean isStillValid(long $$0) {
/* 158 */     return ($$0 - this.previousAttemptTimestamp < 400L);
/*     */   }
/*     */   
/*     */   public boolean shouldRetry(long $$0) {
/* 162 */     return ($$0 >= this.nextScheduledAttemptTimestamp);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 167 */     return "RetryMarker{, previousAttemptAt=" + this.previousAttemptTimestamp + ", nextScheduledAttemptAt=" + this.nextScheduledAttemptTimestamp + ", currentDelay=" + this.currentDelay + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\AcquirePoi$JitteredLinearRetry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */