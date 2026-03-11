/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NearestVisibleLivingEntitySensor
/*    */   extends Sensor<LivingEntity>
/*    */ {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 21 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(
/* 22 */         getMemory());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/* 28 */     $$1.getBrain().setMemory(getMemory(), getNearestEntity($$1));
/*    */   }
/*    */   
/*    */   private Optional<LivingEntity> getNearestEntity(LivingEntity $$0) {
/* 32 */     return getVisibleEntities($$0).flatMap($$1 -> $$1.findClosest(()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected Optional<NearestVisibleLivingEntities> getVisibleEntities(LivingEntity $$0) {
/* 37 */     return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
/*    */   }
/*    */   
/*    */   protected abstract boolean isMatchingEntity(LivingEntity paramLivingEntity1, LivingEntity paramLivingEntity2);
/*    */   
/*    */   protected abstract MemoryModuleType<LivingEntity> getMemory();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\NearestVisibleLivingEntitySensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */