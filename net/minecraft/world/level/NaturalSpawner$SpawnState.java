/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
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
/*     */ public class SpawnState
/*     */ {
/*     */   private final int spawnableChunkCount;
/*     */   private final Object2IntOpenHashMap<MobCategory> mobCategoryCounts;
/*     */   private final PotentialCalculator spawnPotential;
/*     */   private final Object2IntMap<MobCategory> unmodifiableMobCategoryCounts;
/*     */   private final LocalMobCapCalculator localMobCapCalculator;
/*     */   @Nullable
/*     */   private BlockPos lastCheckedPos;
/*     */   @Nullable
/*     */   private EntityType<?> lastCheckedType;
/*     */   private double lastCharge;
/*     */   
/*     */   SpawnState(int $$0, Object2IntOpenHashMap<MobCategory> $$1, PotentialCalculator $$2, LocalMobCapCalculator $$3) {
/*  77 */     this.spawnableChunkCount = $$0;
/*  78 */     this.mobCategoryCounts = $$1;
/*  79 */     this.spawnPotential = $$2;
/*  80 */     this.localMobCapCalculator = $$3;
/*  81 */     this.unmodifiableMobCategoryCounts = Object2IntMaps.unmodifiable((Object2IntMap)$$1);
/*     */   }
/*     */   
/*     */   private boolean canSpawn(EntityType<?> $$0, BlockPos $$1, ChunkAccess $$2) {
/*  85 */     this.lastCheckedPos = $$1;
/*  86 */     this.lastCheckedType = $$0;
/*     */     
/*  88 */     MobSpawnSettings.MobSpawnCost $$3 = NaturalSpawner.getRoughBiome($$1, $$2).getMobSettings().getMobSpawnCost($$0);
/*  89 */     if ($$3 == null) {
/*  90 */       this.lastCharge = 0.0D;
/*  91 */       return true;
/*     */     } 
/*  93 */     double $$4 = $$3.charge();
/*  94 */     this.lastCharge = $$4;
/*  95 */     double $$5 = this.spawnPotential.getPotentialEnergyChange($$1, $$4);
/*  96 */     return ($$5 <= $$3.energyBudget());
/*     */   }
/*     */   private void afterSpawn(Mob $$0, ChunkAccess $$1) {
/*     */     double $$7;
/* 100 */     EntityType<?> $$2 = $$0.getType();
/*     */     
/* 102 */     BlockPos $$3 = $$0.blockPosition();
/* 103 */     if ($$3.equals(this.lastCheckedPos) && $$2 == this.lastCheckedType) {
/* 104 */       double $$4 = this.lastCharge;
/*     */     } else {
/*     */       
/* 107 */       MobSpawnSettings.MobSpawnCost $$5 = NaturalSpawner.getRoughBiome($$3, $$1).getMobSettings().getMobSpawnCost($$2);
/* 108 */       if ($$5 != null) {
/* 109 */         double $$6 = $$5.charge();
/*     */       } else {
/* 111 */         $$7 = 0.0D;
/*     */       } 
/*     */     } 
/* 114 */     this.spawnPotential.addCharge($$3, $$7);
/* 115 */     MobCategory $$8 = $$2.getCategory();
/* 116 */     this.mobCategoryCounts.addTo($$8, 1);
/* 117 */     this.localMobCapCalculator.addMob(new ChunkPos($$3), $$8);
/*     */   }
/*     */   
/*     */   public int getSpawnableChunkCount() {
/* 121 */     return this.spawnableChunkCount;
/*     */   }
/*     */   
/*     */   public Object2IntMap<MobCategory> getMobCategoryCounts() {
/* 125 */     return this.unmodifiableMobCategoryCounts;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean canSpawnForCategory(MobCategory $$0, ChunkPos $$1) {
/* 130 */     int $$2 = $$0.getMaxInstancesPerChunk() * this.spawnableChunkCount / NaturalSpawner.MAGIC_NUMBER;
/* 131 */     if (this.mobCategoryCounts.getInt($$0) >= $$2) {
/* 132 */       return false;
/*     */     }
/*     */     
/* 135 */     if (!this.localMobCapCalculator.canSpawn($$0, $$1)) return false; 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\NaturalSpawner$SpawnState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */