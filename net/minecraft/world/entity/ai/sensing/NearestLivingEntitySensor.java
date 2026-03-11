/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NearestLivingEntitySensor<T extends LivingEntity>
/*    */   extends Sensor<T>
/*    */ {
/*    */   protected void doTick(ServerLevel $$0, T $$1) {
/* 22 */     AABB $$2 = $$1.getBoundingBox().inflate(radiusXZ(), radiusY(), radiusXZ());
/* 23 */     List<LivingEntity> $$3 = $$0.getEntitiesOfClass(LivingEntity.class, $$2, $$1 -> ($$1 != $$0 && $$1.isAlive()));
/* 24 */     Objects.requireNonNull($$1); $$3.sort(Comparator.comparingDouble($$1::distanceToSqr));
/*    */     
/* 26 */     Brain<?> $$4 = $$1.getBrain();
/* 27 */     $$4.setMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES, $$3);
/* 28 */     $$4.setMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, new NearestVisibleLivingEntities((LivingEntity)$$1, $$3));
/*    */   }
/*    */   
/*    */   protected int radiusXZ() {
/* 32 */     return 16;
/*    */   }
/*    */   
/*    */   protected int radiusY() {
/* 36 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 41 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\NearestLivingEntitySensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */