/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ 
/*    */ public class VillagerHostilesSensor
/*    */   extends NearestVisibleLivingEntitySensor
/*    */ {
/* 13 */   private static final ImmutableMap<EntityType<?>, Float> ACCEPTABLE_DISTANCE_FROM_HOSTILES = ImmutableMap.builder()
/* 14 */     .put(EntityType.DROWNED, Float.valueOf(8.0F))
/* 15 */     .put(EntityType.EVOKER, Float.valueOf(12.0F))
/* 16 */     .put(EntityType.HUSK, Float.valueOf(8.0F))
/* 17 */     .put(EntityType.ILLUSIONER, Float.valueOf(12.0F))
/* 18 */     .put(EntityType.PILLAGER, Float.valueOf(15.0F))
/* 19 */     .put(EntityType.RAVAGER, Float.valueOf(12.0F))
/* 20 */     .put(EntityType.VEX, Float.valueOf(8.0F))
/* 21 */     .put(EntityType.VINDICATOR, Float.valueOf(10.0F))
/* 22 */     .put(EntityType.ZOGLIN, Float.valueOf(10.0F))
/* 23 */     .put(EntityType.ZOMBIE, Float.valueOf(8.0F))
/* 24 */     .put(EntityType.ZOMBIE_VILLAGER, Float.valueOf(8.0F))
/* 25 */     .build();
/*    */ 
/*    */   
/*    */   protected boolean isMatchingEntity(LivingEntity $$0, LivingEntity $$1) {
/* 29 */     return (isHostile($$1) && isClose($$0, $$1));
/*    */   }
/*    */   
/*    */   private boolean isClose(LivingEntity $$0, LivingEntity $$1) {
/* 33 */     float $$2 = ((Float)ACCEPTABLE_DISTANCE_FROM_HOSTILES.get($$1.getType())).floatValue();
/* 34 */     return ($$1.distanceToSqr((Entity)$$0) <= ($$2 * $$2));
/*    */   }
/*    */ 
/*    */   
/*    */   protected MemoryModuleType<LivingEntity> getMemory() {
/* 39 */     return MemoryModuleType.NEAREST_HOSTILE;
/*    */   }
/*    */   
/*    */   private boolean isHostile(LivingEntity $$0) {
/* 43 */     return ACCEPTABLE_DISTANCE_FROM_HOSTILES.containsKey($$0.getType());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\VillagerHostilesSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */