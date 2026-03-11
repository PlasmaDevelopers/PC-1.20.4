/*     */ package net.minecraft.world.entity.npc;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnPlacements;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.entity.animal.horse.TraderLlama;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.CustomSpawner;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.storage.ServerLevelData;
/*     */ 
/*     */ 
/*     */ public class WanderingTraderSpawner
/*     */   implements CustomSpawner
/*     */ {
/*     */   private static final int DEFAULT_TICK_DELAY = 1200;
/*     */   public static final int DEFAULT_SPAWN_DELAY = 24000;
/*     */   private static final int MIN_SPAWN_CHANCE = 25;
/*     */   private static final int MAX_SPAWN_CHANCE = 75;
/*     */   private static final int SPAWN_CHANCE_INCREASE = 25;
/*     */   private static final int SPAWN_ONE_IN_X_CHANCE = 10;
/*     */   private static final int NUMBER_OF_SPAWN_ATTEMPTS = 10;
/*  38 */   private final RandomSource random = RandomSource.create();
/*     */   private final ServerLevelData serverLevelData;
/*     */   private int tickDelay;
/*     */   private int spawnDelay;
/*     */   private int spawnChance;
/*     */   
/*     */   public WanderingTraderSpawner(ServerLevelData $$0) {
/*  45 */     this.serverLevelData = $$0;
/*  46 */     this.tickDelay = 1200;
/*  47 */     this.spawnDelay = $$0.getWanderingTraderSpawnDelay();
/*  48 */     this.spawnChance = $$0.getWanderingTraderSpawnChance();
/*     */     
/*  50 */     if (this.spawnDelay == 0 && this.spawnChance == 0) {
/*  51 */       this.spawnDelay = 24000;
/*  52 */       $$0.setWanderingTraderSpawnDelay(this.spawnDelay);
/*  53 */       this.spawnChance = 25;
/*  54 */       $$0.setWanderingTraderSpawnChance(this.spawnChance);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int tick(ServerLevel $$0, boolean $$1, boolean $$2) {
/*  60 */     if (!$$0.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING)) {
/*  61 */       return 0;
/*     */     }
/*     */     
/*  64 */     if (--this.tickDelay > 0) {
/*  65 */       return 0;
/*     */     }
/*  67 */     this.tickDelay = 1200;
/*     */     
/*  69 */     this.spawnDelay -= 1200;
/*  70 */     this.serverLevelData.setWanderingTraderSpawnDelay(this.spawnDelay);
/*  71 */     if (this.spawnDelay > 0) {
/*  72 */       return 0;
/*     */     }
/*  74 */     this.spawnDelay = 24000;
/*     */     
/*  76 */     if (!$$0.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
/*  77 */       return 0;
/*     */     }
/*     */     
/*  80 */     int $$3 = this.spawnChance;
/*  81 */     this.spawnChance = Mth.clamp(this.spawnChance + 25, 25, 75);
/*  82 */     this.serverLevelData.setWanderingTraderSpawnChance(this.spawnChance);
/*     */     
/*  84 */     if (this.random.nextInt(100) > $$3) {
/*  85 */       return 0;
/*     */     }
/*     */     
/*  88 */     if (spawn($$0)) {
/*  89 */       this.spawnChance = 25;
/*  90 */       return 1;
/*     */     } 
/*     */     
/*  93 */     return 0;
/*     */   }
/*     */   
/*     */   private boolean spawn(ServerLevel $$0) {
/*  97 */     ServerPlayer serverPlayer = $$0.getRandomPlayer();
/*  98 */     if (serverPlayer == null) {
/*  99 */       return true;
/*     */     }
/*     */     
/* 102 */     if (this.random.nextInt(10) != 0) {
/* 103 */       return false;
/*     */     }
/*     */     
/* 106 */     BlockPos $$2 = serverPlayer.blockPosition();
/* 107 */     int $$3 = 48;
/*     */     
/* 109 */     PoiManager $$4 = $$0.getPoiManager();
/* 110 */     Optional<BlockPos> $$5 = $$4.find($$0 -> $$0.is(PoiTypes.MEETING), $$0 -> true, $$2, 48, PoiManager.Occupancy.ANY);
/*     */     
/* 112 */     BlockPos $$6 = $$5.orElse($$2);
/* 113 */     BlockPos $$7 = findSpawnPositionNear((LevelReader)$$0, $$6, 48);
/*     */     
/* 115 */     if ($$7 != null && hasEnoughSpace((BlockGetter)$$0, $$7)) {
/* 116 */       if ($$0.getBiome($$7).is(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)) {
/* 117 */         return false;
/*     */       }
/*     */       
/* 120 */       WanderingTrader $$8 = (WanderingTrader)EntityType.WANDERING_TRADER.spawn($$0, $$7, MobSpawnType.EVENT);
/*     */       
/* 122 */       if ($$8 != null) {
/* 123 */         for (int $$9 = 0; $$9 < 2; $$9++) {
/* 124 */           tryToSpawnLlamaFor($$0, $$8, 4);
/*     */         }
/* 126 */         this.serverLevelData.setWanderingTraderId($$8.getUUID());
/* 127 */         $$8.setDespawnDelay(48000);
/*     */         
/* 129 */         $$8.setWanderTarget($$6);
/* 130 */         $$8.restrictTo($$6, 16);
/* 131 */         return true;
/*     */       } 
/*     */     } 
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   private void tryToSpawnLlamaFor(ServerLevel $$0, WanderingTrader $$1, int $$2) {
/* 138 */     BlockPos $$3 = findSpawnPositionNear((LevelReader)$$0, $$1.blockPosition(), $$2);
/* 139 */     if ($$3 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 143 */     TraderLlama $$4 = (TraderLlama)EntityType.TRADER_LLAMA.spawn($$0, $$3, MobSpawnType.EVENT);
/* 144 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 148 */     $$4.setLeashedTo((Entity)$$1, true);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockPos findSpawnPositionNear(LevelReader $$0, BlockPos $$1, int $$2) {
/* 153 */     BlockPos $$3 = null;
/*     */     
/* 155 */     for (int $$4 = 0; $$4 < 10; $$4++) {
/* 156 */       int $$5 = $$1.getX() + this.random.nextInt($$2 * 2) - $$2;
/* 157 */       int $$6 = $$1.getZ() + this.random.nextInt($$2 * 2) - $$2;
/* 158 */       int $$7 = $$0.getHeight(Heightmap.Types.WORLD_SURFACE, $$5, $$6);
/* 159 */       BlockPos $$8 = new BlockPos($$5, $$7, $$6);
/*     */       
/* 161 */       if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, $$0, $$8, EntityType.WANDERING_TRADER)) {
/* 162 */         $$3 = $$8;
/*     */         break;
/*     */       } 
/*     */     } 
/* 166 */     return $$3;
/*     */   }
/*     */   
/*     */   private boolean hasEnoughSpace(BlockGetter $$0, BlockPos $$1) {
/* 170 */     for (BlockPos $$2 : BlockPos.betweenClosed($$1, $$1.offset(1, 2, 1))) {
/* 171 */       if (!$$0.getBlockState($$2).getCollisionShape($$0, $$2).isEmpty()) {
/* 172 */         return false;
/*     */       }
/*     */     } 
/* 175 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\WanderingTraderSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */