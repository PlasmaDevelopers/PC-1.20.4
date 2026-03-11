/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Behavior<E extends LivingEntity>
/*     */   implements BehaviorControl<E>
/*     */ {
/*     */   public static final int DEFAULT_DURATION = 60;
/*     */   protected final Map<MemoryModuleType<?>, MemoryStatus> entryCondition;
/*  18 */   private Status status = Status.STOPPED;
/*     */   private long endTimestamp;
/*     */   private final int minDuration;
/*     */   private final int maxDuration;
/*     */   
/*     */   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> $$0) {
/*  24 */     this($$0, 60);
/*     */   }
/*     */   
/*     */   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> $$0, int $$1) {
/*  28 */     this($$0, $$1, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Behavior(Map<MemoryModuleType<?>, MemoryStatus> $$0, int $$1, int $$2) {
/*  35 */     this.minDuration = $$1;
/*  36 */     this.maxDuration = $$2;
/*  37 */     this.entryCondition = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Status getStatus() {
/*  42 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean tryStart(ServerLevel $$0, E $$1, long $$2) {
/*  47 */     if (hasRequiredMemories($$1) && checkExtraStartConditions($$0, $$1)) {
/*  48 */       this.status = Status.RUNNING;
/*  49 */       int $$3 = this.minDuration + $$0.getRandom().nextInt(this.maxDuration + 1 - this.minDuration);
/*  50 */       this.endTimestamp = $$2 + $$3;
/*  51 */       start($$0, $$1, $$2);
/*  52 */       return true;
/*     */     } 
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, E $$1, long $$2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void tickOrStop(ServerLevel $$0, E $$1, long $$2) {
/*  65 */     if (!timedOut($$2) && canStillUse($$0, $$1, $$2)) {
/*  66 */       tick($$0, $$1, $$2);
/*     */     } else {
/*  68 */       doStop($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, E $$1, long $$2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void doStop(ServerLevel $$0, E $$1, long $$2) {
/*  80 */     this.status = Status.STOPPED;
/*  81 */     stop($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, E $$1, long $$2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, E $$1, long $$2) {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean timedOut(long $$0) {
/* 106 */     return ($$0 > this.endTimestamp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, E $$1) {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String debugString() {
/* 119 */     return getClass().getSimpleName();
/*     */   }
/*     */   
/*     */   protected boolean hasRequiredMemories(E $$0) {
/* 123 */     for (Map.Entry<MemoryModuleType<?>, MemoryStatus> $$1 : this.entryCondition.entrySet()) {
/* 124 */       MemoryModuleType<?> $$2 = $$1.getKey();
/* 125 */       MemoryStatus $$3 = $$1.getValue();
/* 126 */       if (!$$0.getBrain().checkMemory($$2, $$3)) {
/* 127 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   public enum Status {
/* 135 */     STOPPED,
/* 136 */     RUNNING;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\Behavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */