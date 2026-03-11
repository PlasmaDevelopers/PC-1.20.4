/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.AgeableMob;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdultSensor
/*    */   extends Sensor<AgeableMob>
/*    */ {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 19 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, AgeableMob $$1) {
/* 26 */     $$1.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).ifPresent($$1 -> setNearestVisibleAdult($$0, $$1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void setNearestVisibleAdult(AgeableMob $$0, NearestVisibleLivingEntities $$1) {
/* 32 */     Objects.requireNonNull(AgeableMob.class); Optional<AgeableMob> $$2 = $$1.findClosest($$1 -> ($$1.getType() == $$0.getType() && !$$1.isBaby())).map(AgeableMob.class::cast);
/* 33 */     $$0.getBrain().setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\AdultSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */