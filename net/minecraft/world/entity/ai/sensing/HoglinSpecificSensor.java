/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*    */ import net.minecraft.world.entity.monster.piglin.Piglin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HoglinSpecificSensor
/*    */   extends Sensor<Hoglin>
/*    */ {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 26 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_REPELLENT, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, (Object[])new MemoryModuleType[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, Hoglin $$1) {
/* 41 */     Brain<?> $$2 = $$1.getBrain();
/*    */     
/* 43 */     $$2.setMemory(MemoryModuleType.NEAREST_REPELLENT, findNearestRepellent($$0, $$1));
/*    */     
/* 45 */     Optional<Piglin> $$3 = Optional.empty();
/* 46 */     int $$4 = 0;
/*    */     
/* 48 */     List<Hoglin> $$5 = Lists.newArrayList();
/*    */ 
/*    */     
/* 51 */     NearestVisibleLivingEntities $$6 = $$2.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
/* 52 */     for (LivingEntity $$7 : $$6.findAll($$0 -> (!$$0.isBaby() && ($$0 instanceof Piglin || $$0 instanceof Hoglin)))) {
/* 53 */       if ($$7 instanceof Piglin) { Piglin $$8 = (Piglin)$$7;
/* 54 */         $$4++;
/* 55 */         if ($$3.isEmpty()) {
/* 56 */           $$3 = Optional.of($$8);
/*    */         } }
/*    */ 
/*    */       
/* 60 */       if ($$7 instanceof Hoglin) { Hoglin $$9 = (Hoglin)$$7;
/* 61 */         $$5.add($$9); }
/*    */     
/*    */     } 
/*    */     
/* 65 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, $$3);
/* 66 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, $$5);
/* 67 */     $$2.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, Integer.valueOf($$4));
/* 68 */     $$2.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, Integer.valueOf($$5.size()));
/*    */   }
/*    */   
/*    */   private Optional<BlockPos> findNearestRepellent(ServerLevel $$0, Hoglin $$1) {
/* 72 */     return BlockPos.findClosestMatch($$1
/* 73 */         .blockPosition(), 8, 4, $$1 -> $$0.getBlockState($$1).is(BlockTags.HOGLIN_REPELLENTS));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\HoglinSpecificSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */