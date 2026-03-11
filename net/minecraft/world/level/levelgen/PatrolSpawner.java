/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.monster.PatrollingMonster;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.CustomSpawner;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class PatrolSpawner implements CustomSpawner {
/*     */   public int tick(ServerLevel $$0, boolean $$1, boolean $$2) {
/*  25 */     if (!$$1) {
/*  26 */       return 0;
/*     */     }
/*     */     
/*  29 */     if (!$$0.getGameRules().getBoolean(GameRules.RULE_DO_PATROL_SPAWNING)) {
/*  30 */       return 0;
/*     */     }
/*     */     
/*  33 */     RandomSource $$3 = $$0.random;
/*     */     
/*  35 */     this.nextTick--;
/*  36 */     if (this.nextTick > 0) {
/*  37 */       return 0;
/*     */     }
/*     */     
/*  40 */     this.nextTick += 12000 + $$3.nextInt(1200);
/*     */     
/*  42 */     long $$4 = $$0.getDayTime() / 24000L;
/*  43 */     if ($$4 < 5L || !$$0.isDay()) {
/*  44 */       return 0;
/*     */     }
/*     */     
/*  47 */     if ($$3.nextInt(5) != 0) {
/*  48 */       return 0;
/*     */     }
/*     */     
/*  51 */     int $$5 = $$0.players().size();
/*  52 */     if ($$5 < 1) {
/*  53 */       return 0;
/*     */     }
/*     */     
/*  56 */     Player $$6 = $$0.players().get($$3.nextInt($$5));
/*  57 */     if ($$6.isSpectator()) {
/*  58 */       return 0;
/*     */     }
/*     */     
/*  61 */     if ($$0.isCloseToVillage($$6.blockPosition(), 2)) {
/*  62 */       return 0;
/*     */     }
/*     */     
/*  65 */     int $$7 = (24 + $$3.nextInt(24)) * ($$3.nextBoolean() ? -1 : 1);
/*  66 */     int $$8 = (24 + $$3.nextInt(24)) * ($$3.nextBoolean() ? -1 : 1);
/*  67 */     BlockPos.MutableBlockPos $$9 = $$6.blockPosition().mutable().move($$7, 0, $$8);
/*     */ 
/*     */     
/*  70 */     int $$10 = 10;
/*  71 */     if (!$$0.hasChunksAt($$9.getX() - 10, $$9.getZ() - 10, $$9.getX() + 10, $$9.getZ() + 10)) {
/*  72 */       return 0;
/*     */     }
/*     */     
/*  75 */     Holder<Biome> $$11 = $$0.getBiome((BlockPos)$$9);
/*  76 */     if ($$11.is(BiomeTags.WITHOUT_PATROL_SPAWNS)) {
/*  77 */       return 0;
/*     */     }
/*     */     
/*  80 */     int $$12 = 0;
/*     */     
/*  82 */     int $$13 = (int)Math.ceil($$0.getCurrentDifficultyAt((BlockPos)$$9).getEffectiveDifficulty()) + 1;
/*  83 */     for (int $$14 = 0; $$14 < $$13; $$14++) {
/*  84 */       $$12++;
/*     */       
/*  86 */       $$9.setY($$0.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (BlockPos)$$9).getY());
/*     */       
/*  88 */       if ($$14 == 0) {
/*  89 */         if (!spawnPatrolMember($$0, (BlockPos)$$9, $$3, true)) {
/*     */           break;
/*     */         }
/*     */       } else {
/*  93 */         spawnPatrolMember($$0, (BlockPos)$$9, $$3, false);
/*     */       } 
/*     */       
/*  96 */       $$9.setX($$9.getX() + $$3.nextInt(5) - $$3.nextInt(5));
/*  97 */       $$9.setZ($$9.getZ() + $$3.nextInt(5) - $$3.nextInt(5));
/*     */     } 
/*     */     
/* 100 */     return $$12;
/*     */   }
/*     */   private int nextTick;
/*     */   private boolean spawnPatrolMember(ServerLevel $$0, BlockPos $$1, RandomSource $$2, boolean $$3) {
/* 104 */     BlockState $$4 = $$0.getBlockState($$1);
/* 105 */     if (!NaturalSpawner.isValidEmptySpawnBlock((BlockGetter)$$0, $$1, $$4, $$4.getFluidState(), EntityType.PILLAGER)) {
/* 106 */       return false;
/*     */     }
/*     */     
/* 109 */     if (!PatrollingMonster.checkPatrollingMonsterSpawnRules(EntityType.PILLAGER, (LevelAccessor)$$0, MobSpawnType.PATROL, $$1, $$2)) {
/* 110 */       return false;
/*     */     }
/*     */     
/* 113 */     PatrollingMonster $$5 = (PatrollingMonster)EntityType.PILLAGER.create((Level)$$0);
/* 114 */     if ($$5 != null) {
/* 115 */       if ($$3) {
/* 116 */         $$5.setPatrolLeader(true);
/* 117 */         $$5.findPatrolTarget();
/*     */       } 
/*     */       
/* 120 */       $$5.setPos($$1.getX(), $$1.getY(), $$1.getZ());
/* 121 */       $$5.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$1), MobSpawnType.PATROL, null, null);
/*     */       
/* 123 */       $$0.addFreshEntityWithPassengers((Entity)$$5);
/* 124 */       return true;
/*     */     } 
/*     */     
/* 127 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\PatrolSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */