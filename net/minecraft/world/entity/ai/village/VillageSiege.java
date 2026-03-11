/*     */ package net.minecraft.world.entity.ai.village;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.Zombie;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.CustomSpawner;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class VillageSiege implements CustomSpawner {
/*  21 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private boolean hasSetupSiege;
/*  24 */   private State siegeState = State.SIEGE_DONE;
/*     */   private int zombiesToSpawn;
/*     */   private int nextSpawnTime;
/*     */   private int spawnX;
/*     */   private int spawnY;
/*     */   private int spawnZ;
/*     */   
/*     */   private enum State {
/*  32 */     SIEGE_CAN_ACTIVATE,
/*  33 */     SIEGE_TONIGHT,
/*  34 */     SIEGE_DONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tick(ServerLevel $$0, boolean $$1, boolean $$2) {
/*  40 */     if ($$0.isDay() || !$$1) {
/*  41 */       this.siegeState = State.SIEGE_DONE;
/*  42 */       this.hasSetupSiege = false;
/*  43 */       return 0;
/*     */     } 
/*     */     
/*  46 */     float $$3 = $$0.getTimeOfDay(0.0F);
/*  47 */     if ($$3 == 0.5D) {
/*  48 */       this.siegeState = ($$0.random.nextInt(10) == 0) ? State.SIEGE_TONIGHT : State.SIEGE_DONE;
/*     */     }
/*     */     
/*  51 */     if (this.siegeState == State.SIEGE_DONE) {
/*  52 */       return 0;
/*     */     }
/*     */     
/*  55 */     if (!this.hasSetupSiege) {
/*  56 */       if (tryToSetupSiege($$0)) {
/*  57 */         this.hasSetupSiege = true;
/*     */       } else {
/*  59 */         return 0;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  64 */     if (this.nextSpawnTime > 0) {
/*  65 */       this.nextSpawnTime--;
/*  66 */       return 0;
/*     */     } 
/*     */     
/*  69 */     this.nextSpawnTime = 2;
/*  70 */     if (this.zombiesToSpawn > 0) {
/*  71 */       trySpawn($$0);
/*  72 */       this.zombiesToSpawn--;
/*     */     } else {
/*  74 */       this.siegeState = State.SIEGE_DONE;
/*     */     } 
/*     */     
/*  77 */     return 1;
/*     */   }
/*     */   
/*     */   private boolean tryToSetupSiege(ServerLevel $$0) {
/*  81 */     for (Player $$1 : $$0.players()) {
/*  82 */       if (!$$1.isSpectator()) {
/*  83 */         BlockPos $$2 = $$1.blockPosition();
/*  84 */         if (!$$0.isVillage($$2) || $$0.getBiome($$2).is(BiomeTags.WITHOUT_ZOMBIE_SIEGES)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/*  89 */         for (int $$3 = 0; $$3 < 10; $$3++) {
/*  90 */           float $$4 = $$0.random.nextFloat() * 6.2831855F;
/*  91 */           this.spawnX = $$2.getX() + Mth.floor(Mth.cos($$4) * 32.0F);
/*  92 */           this.spawnY = $$2.getY();
/*  93 */           this.spawnZ = $$2.getZ() + Mth.floor(Mth.sin($$4) * 32.0F);
/*     */           
/*  95 */           if (findRandomSpawnPos($$0, new BlockPos(this.spawnX, this.spawnY, this.spawnZ)) != null) {
/*  96 */             this.nextSpawnTime = 0;
/*  97 */             this.zombiesToSpawn = 20;
/*     */             break;
/*     */           } 
/*     */         } 
/* 101 */         return true;
/*     */       } 
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */   private void trySpawn(ServerLevel $$0) {
/*     */     Zombie $$2;
/* 108 */     Vec3 $$1 = findRandomSpawnPos($$0, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
/* 109 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 114 */       $$2 = new Zombie((Level)$$0);
/* 115 */       $$2.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$2.blockPosition()), MobSpawnType.EVENT, null, null);
/* 116 */     } catch (Exception $$3) {
/* 117 */       LOGGER.warn("Failed to create zombie for village siege at {}", $$1, $$3);
/*     */       return;
/*     */     } 
/* 120 */     $$2.moveTo($$1.x, $$1.y, $$1.z, $$0.random.nextFloat() * 360.0F, 0.0F);
/* 121 */     $$0.addFreshEntityWithPassengers((Entity)$$2);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Vec3 findRandomSpawnPos(ServerLevel $$0, BlockPos $$1) {
/* 127 */     for (int $$2 = 0; $$2 < 10; $$2++) {
/* 128 */       int $$3 = $$1.getX() + $$0.random.nextInt(16) - 8;
/* 129 */       int $$4 = $$1.getZ() + $$0.random.nextInt(16) - 8;
/* 130 */       int $$5 = $$0.getHeight(Heightmap.Types.WORLD_SURFACE, $$3, $$4);
/* 131 */       BlockPos $$6 = new BlockPos($$3, $$5, $$4);
/*     */       
/* 133 */       if ($$0.isVillage($$6))
/*     */       {
/*     */         
/* 136 */         if (Monster.checkMonsterSpawnRules(EntityType.ZOMBIE, (ServerLevelAccessor)$$0, MobSpawnType.EVENT, $$6, $$0.random))
/* 137 */           return Vec3.atBottomCenterOf((Vec3i)$$6); 
/*     */       }
/*     */     } 
/* 140 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\village\VillageSiege.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */