/*     */ package net.minecraft.world.entity.ai.behavior.declarative;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.K1;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PureMemory<E extends LivingEntity, F extends K1, Value>
/*     */   extends BehaviorBuilder<E, MemoryAccessor<F, Value>>
/*     */ {
/*     */   PureMemory(MemoryCondition<F, Value> $$0) {
/* 118 */     super(new BehaviorBuilder.TriggerWithResult<E, MemoryAccessor<F, Value>>($$0)
/*     */         {
/*     */           public MemoryAccessor<F, Value> tryTrigger(ServerLevel $$0, E $$1, long $$2)
/*     */           {
/* 122 */             Brain<?> $$3 = $$1.getBrain();
/* 123 */             Optional<Value> $$4 = $$3.getMemoryInternal(condition.memory());
/* 124 */             if ($$4 == null) {
/* 125 */               return null;
/*     */             }
/* 127 */             return condition.createAccessor($$3, $$4);
/*     */           }
/*     */ 
/*     */           
/*     */           public String debugString() {
/* 132 */             return "M[" + condition + "]";
/*     */           }
/*     */ 
/*     */           
/*     */           public String toString() {
/* 137 */             return debugString();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\declarative\BehaviorBuilder$PureMemory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */