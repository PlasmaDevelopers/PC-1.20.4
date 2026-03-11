/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HurtBySensor
/*    */   extends Sensor<LivingEntity>
/*    */ {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 21 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/* 26 */     Brain<?> $$2 = $$1.getBrain();
/* 27 */     DamageSource $$3 = $$1.getLastDamageSource();
/* 28 */     if ($$3 != null) {
/* 29 */       $$2.setMemory(MemoryModuleType.HURT_BY, $$1.getLastDamageSource());
/* 30 */       Entity $$4 = $$3.getEntity();
/* 31 */       if ($$4 instanceof LivingEntity) {
/* 32 */         $$2.setMemory(MemoryModuleType.HURT_BY_ENTITY, $$4);
/*    */       }
/*    */     } else {
/* 35 */       $$2.eraseMemory(MemoryModuleType.HURT_BY);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     $$2.getMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent($$2 -> {
/*    */           if (!$$2.isAlive() || $$2.level() != $$0)
/*    */             $$1.eraseMemory(MemoryModuleType.HURT_BY_ENTITY); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\HurtBySensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */