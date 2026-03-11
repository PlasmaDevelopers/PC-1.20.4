/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ 
/*     */ public abstract class AgeableMob
/*     */   extends PathfinderMob {
/*  18 */   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(AgeableMob.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   public static final int BABY_START_AGE = -24000;
/*     */   
/*     */   private static final int FORCED_AGE_PARTICLE_TICKS = 40;
/*     */   
/*     */   protected int age;
/*     */   protected int forcedAge;
/*     */   protected int forcedAgeTimer;
/*     */   
/*     */   protected AgeableMob(EntityType<? extends AgeableMob> $$0, Level $$1) {
/*  29 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  34 */     if ($$3 == null) {
/*  35 */       $$3 = new AgeableMobGroupData(true);
/*     */     }
/*     */     
/*  38 */     AgeableMobGroupData $$5 = (AgeableMobGroupData)$$3;
/*     */     
/*  40 */     if ($$5.isShouldSpawnBaby() && $$5.getGroupSize() > 0 && $$0.getRandom().nextFloat() <= $$5.getBabySpawnChance()) {
/*  41 */       setAge(-24000);
/*     */     }
/*     */     
/*  44 */     $$5.increaseGroupSizeByOne();
/*     */     
/*  46 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract AgeableMob getBreedOffspring(ServerLevel paramServerLevel, AgeableMob paramAgeableMob);
/*     */   
/*     */   protected void defineSynchedData() {
/*  54 */     super.defineSynchedData();
/*  55 */     this.entityData.define(DATA_BABY_ID, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean canBreed() {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public int getAge() {
/*  63 */     if ((level()).isClientSide) {
/*  64 */       return ((Boolean)this.entityData.get(DATA_BABY_ID)).booleanValue() ? -1 : 1;
/*     */     }
/*  66 */     return this.age;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ageUp(int $$0, boolean $$1) {
/*  71 */     int $$2 = getAge();
/*  72 */     int $$3 = $$2;
/*  73 */     $$2 += $$0 * 20;
/*  74 */     if ($$2 > 0) {
/*  75 */       $$2 = 0;
/*     */     }
/*  77 */     int $$4 = $$2 - $$3;
/*  78 */     setAge($$2);
/*  79 */     if ($$1) {
/*  80 */       this.forcedAge += $$4;
/*  81 */       if (this.forcedAgeTimer == 0) {
/*  82 */         this.forcedAgeTimer = 40;
/*     */       }
/*     */     } 
/*  85 */     if (getAge() == 0) {
/*  86 */       setAge(this.forcedAge);
/*     */     }
/*     */   }
/*     */   
/*     */   public void ageUp(int $$0) {
/*  91 */     ageUp($$0, false);
/*     */   }
/*     */   
/*     */   public void setAge(int $$0) {
/*  95 */     int $$1 = getAge();
/*  96 */     this.age = $$0;
/*     */     
/*  98 */     if (($$1 < 0 && $$0 >= 0) || ($$1 >= 0 && $$0 < 0)) {
/*  99 */       this.entityData.set(DATA_BABY_ID, Boolean.valueOf(($$0 < 0)));
/* 100 */       ageBoundaryReached();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 106 */     super.addAdditionalSaveData($$0);
/* 107 */     $$0.putInt("Age", getAge());
/* 108 */     $$0.putInt("ForcedAge", this.forcedAge);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 113 */     super.readAdditionalSaveData($$0);
/* 114 */     setAge($$0.getInt("Age"));
/* 115 */     this.forcedAge = $$0.getInt("ForcedAge");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 120 */     if (DATA_BABY_ID.equals($$0)) {
/* 121 */       refreshDimensions();
/*     */     }
/* 123 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 128 */     super.aiStep();
/*     */     
/* 130 */     if ((level()).isClientSide) {
/* 131 */       if (this.forcedAgeTimer > 0) {
/* 132 */         if (this.forcedAgeTimer % 4 == 0) {
/* 133 */           level().addParticle((ParticleOptions)ParticleTypes.HAPPY_VILLAGER, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
/*     */         }
/* 135 */         this.forcedAgeTimer--;
/*     */       } 
/* 137 */     } else if (isAlive()) {
/* 138 */       int $$0 = getAge();
/* 139 */       if ($$0 < 0) {
/* 140 */         $$0++;
/* 141 */         setAge($$0);
/* 142 */       } else if ($$0 > 0) {
/* 143 */         $$0--;
/* 144 */         setAge($$0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void ageBoundaryReached() {
/* 150 */     if (!isBaby() && 
/* 151 */       isPassenger()) {
/* 152 */       Entity entity = getVehicle(); if (entity instanceof Boat) { Boat $$0 = (Boat)entity;
/* 153 */         if (!$$0.hasEnoughSpaceFor(this))
/* 154 */           stopRiding();  }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBaby() {
/* 160 */     return (getAge() < 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBaby(boolean $$0) {
/* 165 */     setAge($$0 ? -24000 : 0);
/*     */   }
/*     */   
/*     */   public static int getSpeedUpSecondsWhenFeeding(int $$0) {
/* 169 */     return (int)(($$0 / 20) * 0.1F);
/*     */   }
/*     */   
/*     */   public static class AgeableMobGroupData implements SpawnGroupData {
/*     */     private int groupSize;
/*     */     private final boolean shouldSpawnBaby;
/*     */     private final float babySpawnChance;
/*     */     
/*     */     private AgeableMobGroupData(boolean $$0, float $$1) {
/* 178 */       this.shouldSpawnBaby = $$0;
/* 179 */       this.babySpawnChance = $$1;
/*     */     }
/*     */     
/*     */     public AgeableMobGroupData(boolean $$0) {
/* 183 */       this($$0, 0.05F);
/*     */     }
/*     */     
/*     */     public AgeableMobGroupData(float $$0) {
/* 187 */       this(true, $$0);
/*     */     }
/*     */     
/*     */     public int getGroupSize() {
/* 191 */       return this.groupSize;
/*     */     }
/*     */     
/*     */     public void increaseGroupSizeByOne() {
/* 195 */       this.groupSize++;
/*     */     }
/*     */     
/*     */     public boolean isShouldSpawnBaby() {
/* 199 */       return this.shouldSpawnBaby;
/*     */     }
/*     */     
/*     */     public float getBabySpawnChance() {
/* 203 */       return this.babySpawnChance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\AgeableMob.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */