/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GateBehavior<E extends LivingEntity>
/*     */   implements BehaviorControl<E>
/*     */ {
/*     */   private final Map<MemoryModuleType<?>, MemoryStatus> entryCondition;
/*     */   private final Set<MemoryModuleType<?>> exitErasedMemories;
/*     */   private final OrderPolicy orderPolicy;
/*     */   private final RunningPolicy runningPolicy;
/*  27 */   private final ShufflingList<BehaviorControl<? super E>> behaviors = new ShufflingList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private Behavior.Status status;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GateBehavior(Map<MemoryModuleType<?>, MemoryStatus> $$0, Set<MemoryModuleType<?>> $$1, OrderPolicy $$2, RunningPolicy $$3, List<Pair<? extends BehaviorControl<? super E>, Integer>> $$4) {
/*  37 */     this.status = Behavior.Status.STOPPED; this.entryCondition = $$0;
/*     */     this.exitErasedMemories = $$1;
/*     */     this.orderPolicy = $$2;
/*     */     this.runningPolicy = $$3;
/*  41 */     $$4.forEach($$0 -> this.behaviors.add((BehaviorControl<? super E>)$$0.getFirst(), ((Integer)$$0.getSecond()).intValue())); } public Behavior.Status getStatus() { return this.status; }
/*     */ 
/*     */   
/*     */   private boolean hasRequiredMemories(E $$0) {
/*  45 */     for (Map.Entry<MemoryModuleType<?>, MemoryStatus> $$1 : this.entryCondition.entrySet()) {
/*  46 */       MemoryModuleType<?> $$2 = $$1.getKey();
/*  47 */       MemoryStatus $$3 = $$1.getValue();
/*  48 */       if (!$$0.getBrain().checkMemory($$2, $$3)) {
/*  49 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean tryStart(ServerLevel $$0, E $$1, long $$2) {
/*  58 */     if (hasRequiredMemories($$1)) {
/*  59 */       this.status = Behavior.Status.RUNNING;
/*  60 */       this.orderPolicy.apply(this.behaviors);
/*  61 */       this.runningPolicy.apply(this.behaviors.stream(), $$0, $$1, $$2);
/*  62 */       return true;
/*     */     } 
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void tickOrStop(ServerLevel $$0, E $$1, long $$2) {
/*  70 */     this.behaviors.stream()
/*  71 */       .filter($$0 -> ($$0.getStatus() == Behavior.Status.RUNNING))
/*  72 */       .forEach($$3 -> $$3.tickOrStop($$0, $$1, $$2));
/*     */ 
/*     */     
/*  75 */     if (this.behaviors.stream().noneMatch($$0 -> ($$0.getStatus() == Behavior.Status.RUNNING))) {
/*  76 */       doStop($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final void doStop(ServerLevel $$0, E $$1, long $$2) {
/*  82 */     this.status = Behavior.Status.STOPPED;
/*     */     
/*  84 */     this.behaviors.stream()
/*  85 */       .filter($$0 -> ($$0.getStatus() == Behavior.Status.RUNNING))
/*  86 */       .forEach($$3 -> $$3.doStop($$0, $$1, $$2));
/*     */     
/*  88 */     Objects.requireNonNull($$1.getBrain()); this.exitErasedMemories.forEach($$1.getBrain()::eraseMemory);
/*     */   }
/*     */ 
/*     */   
/*     */   public String debugString() {
/*  93 */     return getClass().getSimpleName();
/*     */   }
/*     */   
/*     */   public enum OrderPolicy {
/*  97 */     ORDERED((String)($$0 -> { 
/*  98 */       })), SHUFFLED((String)ShufflingList::shuffle);
/*     */     
/*     */     private final Consumer<ShufflingList<?>> consumer;
/*     */ 
/*     */     
/*     */     OrderPolicy(Consumer<ShufflingList<?>> $$0) {
/* 104 */       this.consumer = $$0;
/*     */     }
/*     */     
/*     */     public void apply(ShufflingList<?> $$0) {
/* 108 */       this.consumer.accept($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum RunningPolicy {
/* 113 */     RUN_ONE
/*     */     {
/*     */       public <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> $$0, ServerLevel $$1, E $$2, long $$3) {
/* 116 */         $$0
/* 117 */           .filter($$0 -> ($$0.getStatus() == Behavior.Status.STOPPED))
/* 118 */           .filter($$3 -> $$3.tryStart($$0, $$1, $$2))
/* 119 */           .findFirst();
/*     */       }
/*     */     },
/* 122 */     TRY_ALL
/*     */     {
/*     */       public <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> $$0, ServerLevel $$1, E $$2, long $$3) {
/* 125 */         $$0
/* 126 */           .filter($$0 -> ($$0.getStatus() == Behavior.Status.STOPPED))
/* 127 */           .forEach($$3 -> $$3.tryStart($$0, $$1, $$2)); } }; public abstract <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> param1Stream, ServerLevel param1ServerLevel, E param1E, long param1Long); } enum null { public <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> $$0, ServerLevel $$1, E $$2, long $$3) { $$0.filter($$0 -> ($$0.getStatus() == Behavior.Status.STOPPED)).filter($$3 -> $$3.tryStart($$0, $$1, $$2)).findFirst(); } } enum null { public <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> $$0, ServerLevel $$1, E $$2, long $$3) { $$0.filter($$0 -> ($$0.getStatus() == Behavior.Status.STOPPED)).forEach($$3 -> $$3.tryStart($$0, $$1, $$2)); }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     Set<? extends BehaviorControl<? super E>> $$0 = (Set<? extends BehaviorControl<? super E>>)this.behaviors.stream().filter($$0 -> ($$0.getStatus() == Behavior.Status.RUNNING)).collect(Collectors.toSet());
/*     */     
/* 141 */     return "(" + getClass().getSimpleName() + "): " + $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GateBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */