/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class Skeleton
/*     */   extends AbstractSkeleton
/*     */ {
/*     */   private static final int TOTAL_CONVERSION_TIME = 300;
/*  20 */   private static final EntityDataAccessor<Boolean> DATA_STRAY_CONVERSION_ID = SynchedEntityData.defineId(Skeleton.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   public static final String CONVERSION_TAG = "StrayConversionTime";
/*     */   private int inPowderSnowTime;
/*     */   private int conversionTime;
/*     */   
/*     */   public Skeleton(EntityType<? extends Skeleton> $$0, Level $$1) {
/*  27 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  32 */     super.defineSynchedData();
/*     */     
/*  34 */     getEntityData().define(DATA_STRAY_CONVERSION_ID, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean isFreezeConverting() {
/*  38 */     return ((Boolean)getEntityData().get(DATA_STRAY_CONVERSION_ID)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setFreezeConverting(boolean $$0) {
/*  42 */     this.entityData.set(DATA_STRAY_CONVERSION_ID, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShaking() {
/*  47 */     return isFreezeConverting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  52 */     if (!(level()).isClientSide && isAlive() && !isNoAi()) {
/*  53 */       if (this.isInPowderSnow) {
/*  54 */         if (isFreezeConverting()) {
/*  55 */           this.conversionTime--;
/*     */           
/*  57 */           if (this.conversionTime < 0) {
/*  58 */             doFreezeConversion();
/*     */           }
/*     */         } else {
/*  61 */           this.inPowderSnowTime++;
/*     */           
/*  63 */           if (this.inPowderSnowTime >= 140) {
/*  64 */             startFreezeConversion(300);
/*     */           }
/*     */         } 
/*     */       } else {
/*  68 */         this.inPowderSnowTime = -1;
/*  69 */         setFreezeConverting(false);
/*     */       } 
/*     */     }
/*     */     
/*  73 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  78 */     super.addAdditionalSaveData($$0);
/*     */     
/*  80 */     $$0.putInt("StrayConversionTime", isFreezeConverting() ? this.conversionTime : -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  85 */     super.readAdditionalSaveData($$0);
/*     */     
/*  87 */     if ($$0.contains("StrayConversionTime", 99) && $$0.getInt("StrayConversionTime") > -1) {
/*  88 */       startFreezeConversion($$0.getInt("StrayConversionTime"));
/*     */     }
/*     */   }
/*     */   
/*     */   private void startFreezeConversion(int $$0) {
/*  93 */     this.conversionTime = $$0;
/*  94 */     setFreezeConverting(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFreezeConversion() {
/*  99 */     convertTo(EntityType.STRAY, true);
/* 100 */     if (!isSilent()) {
/* 101 */       level().levelEvent(null, 1048, blockPosition(), 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFreeze() {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 113 */     return SoundEvents.SKELETON_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 118 */     return SoundEvents.SKELETON_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 123 */     return SoundEvents.SKELETON_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   SoundEvent getStepSound() {
/* 128 */     return SoundEvents.SKELETON_STEP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/* 133 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/* 134 */     Entity $$3 = $$0.getEntity();
/* 135 */     if ($$3 instanceof Creeper) { Creeper $$4 = (Creeper)$$3;
/* 136 */       if ($$4.canDropMobsSkull()) {
/* 137 */         $$4.increaseDroppedSkulls();
/* 138 */         spawnAtLocation((ItemLike)Items.SKELETON_SKULL);
/*     */       }  }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Skeleton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */