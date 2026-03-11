/*     */ package net.minecraft.world.entity.ai.sensing;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*     */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*     */ import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
/*     */ import net.minecraft.world.entity.monster.piglin.Piglin;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinAi;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinBrute;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.CampfireBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PiglinSpecificSensor
/*     */   extends Sensor<LivingEntity>
/*     */ {
/*     */   public Set<MemoryModuleType<?>> requires() {
/*  36 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, (Object[])new MemoryModuleType[] { MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEARBY_ADULT_PIGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_REPELLENT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doTick(ServerLevel $$0, LivingEntity $$1) {
/*  57 */     Brain<?> $$2 = $$1.getBrain();
/*     */     
/*  59 */     $$2.setMemory(MemoryModuleType.NEAREST_REPELLENT, findNearestRepellent($$0, $$1));
/*     */     
/*  61 */     Optional<Mob> $$3 = Optional.empty();
/*  62 */     Optional<Hoglin> $$4 = Optional.empty();
/*  63 */     Optional<Hoglin> $$5 = Optional.empty();
/*  64 */     Optional<Piglin> $$6 = Optional.empty();
/*  65 */     Optional<LivingEntity> $$7 = Optional.empty();
/*  66 */     Optional<Player> $$8 = Optional.empty();
/*  67 */     Optional<Player> $$9 = Optional.empty();
/*  68 */     int $$10 = 0;
/*     */     
/*  70 */     List<AbstractPiglin> $$11 = Lists.newArrayList();
/*  71 */     List<AbstractPiglin> $$12 = Lists.newArrayList();
/*     */ 
/*     */     
/*  74 */     NearestVisibleLivingEntities $$13 = $$2.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
/*  75 */     for (LivingEntity $$14 : $$13.findAll($$0 -> true)) {
/*  76 */       if ($$14 instanceof Hoglin) { Hoglin $$15 = (Hoglin)$$14;
/*  77 */         if ($$15.isBaby() && $$5.isEmpty()) {
/*  78 */           $$5 = Optional.of($$15); continue;
/*  79 */         }  if ($$15.isAdult()) {
/*  80 */           $$10++;
/*  81 */           if ($$4.isEmpty() && $$15.canBeHunted())
/*  82 */             $$4 = Optional.of($$15); 
/*     */         }  continue; }
/*     */       
/*  85 */       if ($$14 instanceof PiglinBrute) { PiglinBrute $$16 = (PiglinBrute)$$14;
/*  86 */         $$11.add($$16); continue; }
/*  87 */        if ($$14 instanceof Piglin) { Piglin $$17 = (Piglin)$$14;
/*  88 */         if ($$17.isBaby() && $$6.isEmpty()) {
/*  89 */           $$6 = Optional.of($$17); continue;
/*  90 */         }  if ($$17.isAdult())
/*  91 */           $$11.add($$17);  continue; }
/*     */       
/*  93 */       if ($$14 instanceof Player) { Player $$18 = (Player)$$14;
/*  94 */         if ($$8.isEmpty() && !PiglinAi.isWearingGold((LivingEntity)$$18) && $$1.canAttack($$14)) {
/*  95 */           $$8 = Optional.of($$18);
/*     */         }
/*  97 */         if ($$9.isEmpty() && !$$18.isSpectator() && PiglinAi.isPlayerHoldingLovedItem((LivingEntity)$$18))
/*  98 */           $$9 = Optional.of($$18);  continue; }
/*     */       
/* 100 */       if ($$3.isEmpty() && ($$14 instanceof net.minecraft.world.entity.monster.WitherSkeleton || $$14 instanceof net.minecraft.world.entity.boss.wither.WitherBoss)) {
/* 101 */         $$3 = Optional.of((Mob)$$14); continue;
/* 102 */       }  if ($$7.isEmpty() && PiglinAi.isZombified($$14.getType())) {
/* 103 */         $$7 = Optional.of($$14);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     List<LivingEntity> $$19 = (List<LivingEntity>)$$2.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of());
/* 108 */     for (LivingEntity $$20 : $$19) {
/* 109 */       if ($$20 instanceof AbstractPiglin) { AbstractPiglin $$21 = (AbstractPiglin)$$20; if ($$21.isAdult()) {
/* 110 */           $$12.add($$21);
/*     */         } }
/*     */     
/*     */     } 
/* 114 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, $$3);
/* 115 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, $$4);
/* 116 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, $$5);
/* 117 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, $$7);
/* 118 */     $$2.setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, $$8);
/* 119 */     $$2.setMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, $$9);
/* 120 */     $$2.setMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS, $$12);
/* 121 */     $$2.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, $$11);
/* 122 */     $$2.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, Integer.valueOf($$11.size()));
/* 123 */     $$2.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, Integer.valueOf($$10));
/*     */   }
/*     */   
/*     */   private static Optional<BlockPos> findNearestRepellent(ServerLevel $$0, LivingEntity $$1) {
/* 127 */     return BlockPos.findClosestMatch($$1
/* 128 */         .blockPosition(), 8, 4, $$1 -> isValidRepellent($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidRepellent(ServerLevel $$0, BlockPos $$1) {
/* 136 */     BlockState $$2 = $$0.getBlockState($$1);
/* 137 */     boolean $$3 = $$2.is(BlockTags.PIGLIN_REPELLENTS);
/* 138 */     if ($$3 && $$2.is(Blocks.SOUL_CAMPFIRE)) {
/* 139 */       return CampfireBlock.isLitCampfire($$2);
/*     */     }
/* 141 */     return $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\PiglinSpecificSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */