/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.animal.frog.Frog;
/*    */ 
/*    */ public class FrogAttackablesSensor
/*    */   extends NearestVisibleLivingEntitySensor {
/*    */   public static final float TARGET_DETECTION_DISTANCE = 10.0F;
/*    */   
/*    */   protected boolean isMatchingEntity(LivingEntity $$0, LivingEntity $$1) {
/* 16 */     if (!$$0.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN) && 
/* 17 */       Sensor.isEntityAttackable($$0, $$1) && 
/* 18 */       Frog.canEat($$1) && 
/* 19 */       !isUnreachableAttackTarget($$0, $$1))
/*    */     {
/* 21 */       return $$1.closerThan((Entity)$$0, 10.0D);
/*    */     }
/* 23 */     return false;
/*    */   }
/*    */   
/*    */   private boolean isUnreachableAttackTarget(LivingEntity $$0, LivingEntity $$1) {
/* 27 */     List<UUID> $$2 = $$0.getBrain().getMemory(MemoryModuleType.UNREACHABLE_TONGUE_TARGETS).orElseGet(java.util.ArrayList::new);
/* 28 */     return $$2.contains($$1.getUUID());
/*    */   }
/*    */ 
/*    */   
/*    */   protected MemoryModuleType<LivingEntity> getMemory() {
/* 33 */     return MemoryModuleType.NEAREST_ATTACKABLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\FrogAttackablesSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */