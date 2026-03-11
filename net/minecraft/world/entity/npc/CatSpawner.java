/*     */ package net.minecraft.world.entity.npc;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.StructureTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnPlacements;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.level.CustomSpawner;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public class CatSpawner implements CustomSpawner {
/*     */   private static final int TICK_DELAY = 1200;
/*     */   private int nextTick;
/*     */   
/*     */   public int tick(ServerLevel $$0, boolean $$1, boolean $$2) {
/*  30 */     if (!$$2 || !$$0.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
/*  31 */       return 0;
/*     */     }
/*     */     
/*  34 */     this.nextTick--;
/*  35 */     if (this.nextTick > 0) {
/*  36 */       return 0;
/*     */     }
/*     */     
/*  39 */     this.nextTick = 1200;
/*     */     
/*  41 */     ServerPlayer serverPlayer = $$0.getRandomPlayer();
/*  42 */     if (serverPlayer == null) {
/*  43 */       return 0;
/*     */     }
/*     */     
/*  46 */     RandomSource $$4 = $$0.random;
/*  47 */     int $$5 = (8 + $$4.nextInt(24)) * ($$4.nextBoolean() ? -1 : 1);
/*  48 */     int $$6 = (8 + $$4.nextInt(24)) * ($$4.nextBoolean() ? -1 : 1);
/*  49 */     BlockPos $$7 = serverPlayer.blockPosition().offset($$5, 0, $$6);
/*     */ 
/*     */     
/*  52 */     int $$8 = 10;
/*  53 */     if (!$$0.hasChunksAt($$7.getX() - 10, $$7.getZ() - 10, $$7.getX() + 10, $$7.getZ() + 10)) {
/*  54 */       return 0;
/*     */     }
/*     */     
/*  57 */     if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, (LevelReader)$$0, $$7, EntityType.CAT)) {
/*  58 */       if ($$0.isCloseToVillage($$7, 2)) {
/*  59 */         return spawnInVillage($$0, $$7);
/*     */       }
/*     */       
/*  62 */       if ($$0.structureManager().getStructureWithPieceAt($$7, StructureTags.CATS_SPAWN_IN).isValid()) {
/*  63 */         return spawnInHut($$0, $$7);
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return 0;
/*     */   }
/*     */   
/*     */   private int spawnInVillage(ServerLevel $$0, BlockPos $$1) {
/*  71 */     int $$2 = 48;
/*  72 */     if ($$0.getPoiManager().getCountInRange($$0 -> $$0.is(PoiTypes.HOME), $$1, 48, PoiManager.Occupancy.IS_OCCUPIED) > 4L) {
/*  73 */       List<Cat> $$3 = $$0.getEntitiesOfClass(Cat.class, (new AABB($$1)).inflate(48.0D, 8.0D, 48.0D));
/*  74 */       if ($$3.size() < 5) {
/*  75 */         return spawnCat($$1, $$0);
/*     */       }
/*     */     } 
/*  78 */     return 0;
/*     */   }
/*     */   
/*     */   private int spawnInHut(ServerLevel $$0, BlockPos $$1) {
/*  82 */     int $$2 = 16;
/*  83 */     List<Cat> $$3 = $$0.getEntitiesOfClass(Cat.class, (new AABB($$1)).inflate(16.0D, 8.0D, 16.0D));
/*  84 */     if ($$3.size() < 1) {
/*  85 */       return spawnCat($$1, $$0);
/*     */     }
/*     */     
/*  88 */     return 0;
/*     */   }
/*     */   
/*     */   private int spawnCat(BlockPos $$0, ServerLevel $$1) {
/*  92 */     Cat $$2 = (Cat)EntityType.CAT.create((Level)$$1);
/*  93 */     if ($$2 == null) {
/*  94 */       return 0;
/*     */     }
/*     */     
/*  97 */     $$2.finalizeSpawn((ServerLevelAccessor)$$1, $$1.getCurrentDifficultyAt($$0), MobSpawnType.NATURAL, null, null);
/*  98 */     $$2.moveTo($$0, 0.0F, 0.0F);
/*  99 */     $$1.addFreshEntityWithPassengers((Entity)$$2);
/* 100 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\CatSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */