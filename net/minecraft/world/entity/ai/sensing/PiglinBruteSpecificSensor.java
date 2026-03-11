/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
/*    */ 
/*    */ 
/*    */ public class PiglinBruteSpecificSensor
/*    */   extends Sensor<LivingEntity>
/*    */ {
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 23 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEARBY_ADULT_PIGLINS);
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
/*    */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/* 35 */     Brain<?> $$2 = $$1.getBrain();
/*    */     
/* 37 */     List<AbstractPiglin> $$3 = Lists.newArrayList();
/*    */ 
/*    */     
/* 40 */     NearestVisibleLivingEntities $$4 = $$2.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
/* 41 */     Objects.requireNonNull(Mob.class); Optional<Mob> $$5 = $$4.findClosest($$0 -> ($$0 instanceof net.minecraft.world.entity.monster.WitherSkeleton || $$0 instanceof net.minecraft.world.entity.boss.wither.WitherBoss)).map(Mob.class::cast);
/*    */     
/* 43 */     List<LivingEntity> $$6 = (List<LivingEntity>)$$2.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of());
/* 44 */     for (LivingEntity $$7 : $$6) {
/* 45 */       if ($$7 instanceof AbstractPiglin && ((AbstractPiglin)$$7).isAdult()) {
/* 46 */         $$3.add((AbstractPiglin)$$7);
/*    */       }
/*    */     } 
/*    */     
/* 50 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, $$5);
/* 51 */     $$2.setMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\PiglinBruteSpecificSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */