/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VillagerBabiesSensor
/*    */   extends Sensor<LivingEntity>
/*    */ {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 21 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/* 26 */     $$1.getBrain().setMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES, getNearestVillagerBabies($$1));
/*    */   }
/*    */   
/*    */   private List<LivingEntity> getNearestVillagerBabies(LivingEntity $$0) {
/* 30 */     return (List<LivingEntity>)ImmutableList.copyOf(getVisibleEntities($$0).findAll(this::isVillagerBaby));
/*    */   }
/*    */   
/*    */   private boolean isVillagerBaby(LivingEntity $$0) {
/* 34 */     return ($$0.getType() == EntityType.VILLAGER && $$0.isBaby());
/*    */   }
/*    */   
/*    */   private NearestVisibleLivingEntities getVisibleEntities(LivingEntity $$0) {
/* 38 */     return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
/* 39 */       .orElse(NearestVisibleLivingEntities.empty());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\VillagerBabiesSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */