/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import net.minecraft.tags.EntityTypeTags;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class AxolotlAttackablesSensor
/*    */   extends NearestVisibleLivingEntitySensor {
/*    */   public static final float TARGET_DETECTION_DISTANCE = 8.0F;
/*    */   
/*    */   protected boolean isMatchingEntity(LivingEntity $$0, LivingEntity $$1) {
/* 13 */     return (isClose($$0, $$1) && $$1
/* 14 */       .isInWaterOrBubble() && (
/* 15 */       isHostileTarget($$1) || isHuntTarget($$0, $$1)) && 
/* 16 */       Sensor.isEntityAttackable($$0, $$1));
/*    */   }
/*    */   
/*    */   private boolean isHuntTarget(LivingEntity $$0, LivingEntity $$1) {
/* 20 */     return (!$$0.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN) && $$1.getType().is(EntityTypeTags.AXOLOTL_HUNT_TARGETS));
/*    */   }
/*    */   
/*    */   private boolean isHostileTarget(LivingEntity $$0) {
/* 24 */     return $$0.getType().is(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES);
/*    */   }
/*    */   
/*    */   private boolean isClose(LivingEntity $$0, LivingEntity $$1) {
/* 28 */     return ($$1.distanceToSqr((Entity)$$0) <= 64.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected MemoryModuleType<LivingEntity> getMemory() {
/* 33 */     return MemoryModuleType.NEAREST_ATTACKABLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\AxolotlAttackablesSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */