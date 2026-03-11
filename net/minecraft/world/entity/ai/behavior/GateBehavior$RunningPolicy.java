/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum RunningPolicy
/*     */ {
/* 113 */   RUN_ONE
/*     */   {
/*     */     public <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> $$0, ServerLevel $$1, E $$2, long $$3) {
/* 116 */       $$0
/* 117 */         .filter($$0 -> ($$0.getStatus() == Behavior.Status.STOPPED))
/* 118 */         .filter($$3 -> $$3.tryStart($$0, $$1, $$2))
/* 119 */         .findFirst();
/*     */     }
/*     */   },
/* 122 */   TRY_ALL
/*     */   {
/*     */     public <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> $$0, ServerLevel $$1, E $$2, long $$3) {
/* 125 */       $$0
/* 126 */         .filter($$0 -> ($$0.getStatus() == Behavior.Status.STOPPED))
/* 127 */         .forEach($$3 -> $$3.tryStart($$0, $$1, $$2));
/*     */     }
/*     */   };
/*     */   
/*     */   public abstract <E extends LivingEntity> void apply(Stream<BehaviorControl<? super E>> paramStream, ServerLevel paramServerLevel, E paramE, long paramLong);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GateBehavior$RunningPolicy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */