/*     */ package net.minecraft.world.level.block.entity.trialspawner;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.util.random.WeightedEntry;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public enum TrialSpawnerState implements StringRepresentable {
/*  19 */   INACTIVE("inactive", 0, ParticleEmission.NONE, -1.0D, false),
/*  20 */   WAITING_FOR_PLAYERS("waiting_for_players", 4, ParticleEmission.SMALL_FLAMES, 200.0D, true),
/*  21 */   ACTIVE("active", 8, ParticleEmission.FLAMES_AND_SMOKE, 1000.0D, true),
/*  22 */   WAITING_FOR_REWARD_EJECTION("waiting_for_reward_ejection", 8, ParticleEmission.SMALL_FLAMES, -1.0D, false),
/*  23 */   EJECTING_REWARD("ejecting_reward", 8, ParticleEmission.SMALL_FLAMES, -1.0D, false),
/*  24 */   COOLDOWN("cooldown", 0, ParticleEmission.SMOKE_INSIDE_AND_TOP_FACE, -1.0D, false); private static final float DELAY_BEFORE_EJECT_AFTER_KILLING_LAST_MOB = 40.0F; private static final int TIME_BETWEEN_EACH_EJECTION; private final String name;
/*     */   
/*     */   static {
/*  27 */     TIME_BETWEEN_EACH_EJECTION = Mth.floor(30.0F);
/*     */   }
/*     */   private final int lightLevel;
/*     */   private final double spinningMobSpeed;
/*     */   private final ParticleEmission particleEmission;
/*     */   private final boolean isCapableOfSpawning;
/*     */   
/*     */   TrialSpawnerState(String $$0, int $$1, ParticleEmission $$2, double $$3, boolean $$4) {
/*  35 */     this.name = $$0;
/*  36 */     this.lightLevel = $$1;
/*  37 */     this.particleEmission = $$2;
/*  38 */     this.spinningMobSpeed = $$3;
/*  39 */     this.isCapableOfSpawning = $$4;
/*     */   }
/*     */   TrialSpawnerState tickAndGetNext(BlockPos $$0, TrialSpawner $$1, ServerLevel $$2) {
/*     */     int $$6;
/*  43 */     TrialSpawnerData $$3 = $$1.getData();
/*  44 */     TrialSpawnerConfig $$4 = $$1.getConfig();
/*  45 */     PlayerDetector $$5 = $$1.getPlayerDetector();
/*     */     
/*  47 */     switch (this) { default: throw new IncompatibleClassChangeError();case INACTIVE: return 
/*  48 */           ($$3.getOrCreateDisplayEntity($$1, (Level)$$2, WAITING_FOR_PLAYERS) == null) ? 
/*  49 */           this : 
/*  50 */           WAITING_FOR_PLAYERS;
/*     */ 
/*     */ 
/*     */       
/*     */       case WAITING_FOR_PLAYERS:
/*  55 */         $$3.tryDetectPlayers($$2, $$0, $$5, $$4.requiredPlayerRange());
/*  56 */         return !$$3.hasMobToSpawn() ? INACTIVE : ($$3.detectedPlayers.isEmpty() ? 
/*  57 */           this : 
/*  58 */           ACTIVE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case ACTIVE:
/*  65 */         $$6 = $$3.countAdditionalPlayers($$0);
/*  66 */         $$3.tryDetectPlayers($$2, $$0, $$5, $$4.requiredPlayerRange());
/*     */         
/*  68 */         if ($$3.hasFinishedSpawningAllMobs($$4, $$6)) {
/*  69 */           if ($$3.haveAllCurrentMobsDied()) {
/*  70 */             $$3.cooldownEndsAt = $$2.getGameTime() + $$4.targetCooldownLength();
/*  71 */             $$3.totalMobsSpawned = 0;
/*  72 */             $$3.nextMobSpawnsAt = 0L;
/*     */           }
/*     */         
/*  75 */         } else if ($$3.isReadyToSpawnNextMob($$2, $$4, $$6)) {
/*  76 */           $$1.spawnMob($$2, $$0).ifPresent($$4 -> {
/*     */                 $$0.currentMobs.add($$4);
/*     */ 
/*     */                 
/*     */                 $$0.totalMobsSpawned++;
/*     */                 
/*     */                 $$0.nextMobSpawnsAt = $$1.getGameTime() + $$2.ticksBetweenSpawn();
/*     */                 
/*     */                 $$0.spawnPotentials.getRandom($$1.getRandom()).ifPresent(());
/*     */               });
/*     */         } 
/*     */         
/*  88 */         return !$$3.hasMobToSpawn() ? INACTIVE : this;
/*     */ 
/*     */       
/*     */       case WAITING_FOR_REWARD_EJECTION:
/*  92 */         $$2.playSound(null, $$0, SoundEvents.TRIAL_SPAWNER_OPEN_SHUTTER, SoundSource.BLOCKS);
/*  93 */         return $$3.isReadyToOpenShutter($$2, $$4, 40.0F) ? EJECTING_REWARD : 
/*     */           
/*  95 */           this;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case EJECTING_REWARD:
/* 103 */         $$2.playSound(null, $$0, SoundEvents.TRIAL_SPAWNER_CLOSE_SHUTTER, SoundSource.BLOCKS);
/* 104 */         $$3.ejectingLootTable = Optional.empty();
/*     */ 
/*     */ 
/*     */         
/* 108 */         if ($$3.ejectingLootTable.isEmpty()) {
/* 109 */           $$3.ejectingLootTable = $$4.lootTablesToEject().getRandomValue($$2.getRandom());
/*     */         }
/*     */         
/* 112 */         $$3.ejectingLootTable.ifPresent($$3 -> $$0.ejectReward($$1, $$2, $$3));
/* 113 */         $$3.detectedPlayers.remove($$3.detectedPlayers.iterator().next());
/* 114 */         return !$$3.isReadyToEjectItems($$2, $$4, TIME_BETWEEN_EACH_EJECTION) ? this : ($$3.detectedPlayers.isEmpty() ? COOLDOWN : this);
/*     */       case COOLDOWN:
/*     */         break; }
/*     */     
/* 118 */     $$3.cooldownEndsAt = 0L;
/* 119 */     return $$3.isCooldownFinished($$2) ? WAITING_FOR_PLAYERS : 
/*     */       
/* 121 */       this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int lightLevel() {
/* 127 */     return this.lightLevel;
/*     */   }
/*     */   
/*     */   public double spinningMobSpeed() {
/* 131 */     return this.spinningMobSpeed;
/*     */   }
/*     */   
/*     */   public boolean hasSpinningMob() {
/* 135 */     return (this.spinningMobSpeed >= 0.0D);
/*     */   }
/*     */   
/*     */   public boolean isCapableOfSpawning() {
/* 139 */     return this.isCapableOfSpawning;
/*     */   }
/*     */   
/*     */   public void emitParticles(Level $$0, BlockPos $$1) {
/* 143 */     this.particleEmission.emit($$0, $$0.getRandom(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 148 */     return this.name;
/*     */   }
/*     */   private static class LightLevel {
/*     */     private static final int UNLIT = 0; private static final int HALF_LIT = 4;
/*     */     private static final int LIT = 8; }
/*     */   
/*     */   private static class SpinningMob {
/*     */     private static final double NONE = -1.0D;
/*     */     private static final double SLOW = 200.0D;
/*     */     private static final double FAST = 1000.0D; }
/*     */   
/*     */   private static interface ParticleEmission { public static final ParticleEmission NONE = ($$0, $$1, $$2) -> {
/*     */       
/*     */       };
/*     */     public static final ParticleEmission SMALL_FLAMES;
/*     */     
/*     */     static {
/* 165 */       SMALL_FLAMES = (($$0, $$1, $$2) -> {
/*     */           if ($$1.nextInt(2) == 0) {
/*     */             Vec3 $$3 = $$2.getCenter().offsetRandom($$1, 0.9F);
/*     */             addParticle(ParticleTypes.SMALL_FLAME, $$3, $$0);
/*     */           } 
/*     */         });
/* 171 */       FLAMES_AND_SMOKE = (($$0, $$1, $$2) -> {
/*     */           Vec3 $$3 = $$2.getCenter().offsetRandom($$1, 1.0F);
/*     */           addParticle(ParticleTypes.SMOKE, $$3, $$0);
/*     */           addParticle(ParticleTypes.FLAME, $$3, $$0);
/*     */         });
/* 176 */       SMOKE_INSIDE_AND_TOP_FACE = (($$0, $$1, $$2) -> {
/*     */           Vec3 $$3 = $$2.getCenter().offsetRandom($$1, 0.9F);
/*     */           if ($$1.nextInt(3) == 0)
/*     */             addParticle(ParticleTypes.SMOKE, $$3, $$0); 
/*     */           if ($$0.getGameTime() % 20L == 0L) {
/*     */             Vec3 $$4 = $$2.getCenter().add(0.0D, 0.5D, 0.0D);
/*     */             int $$5 = $$0.getRandom().nextInt(4) + 20;
/*     */             for (int $$6 = 0; $$6 < $$5; $$6++)
/*     */               addParticle(ParticleTypes.SMOKE, $$4, $$0); 
/*     */           } 
/*     */         });
/*     */     }
/*     */     public static final ParticleEmission FLAMES_AND_SMOKE;
/*     */     public static final ParticleEmission SMOKE_INSIDE_AND_TOP_FACE;
/*     */     
/*     */     private static void addParticle(SimpleParticleType $$0, Vec3 $$1, Level $$2) {
/* 192 */       $$2.addParticle((ParticleOptions)$$0, $$1.x(), $$1.y(), $$1.z(), 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */     
/*     */     void emit(Level param1Level, RandomSource param1RandomSource, BlockPos param1BlockPos); }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\trialspawner\TrialSpawnerState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */