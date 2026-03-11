/*     */ package net.minecraft.world.entity.monster;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class MagmaCube extends Slime {
/*     */   public MagmaCube(EntityType<? extends MagmaCube> $$0, Level $$1) {
/*  25 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  29 */     return Monster.createMonsterAttributes()
/*  30 */       .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
/*     */   }
/*     */   
/*     */   public static boolean checkMagmaCubeSpawnRules(EntityType<MagmaCube> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/*  34 */     return ($$1.getDifficulty() != Difficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/*  39 */     return ($$0.isUnobstructed((Entity)this) && !$$0.containsAnyLiquid(getBoundingBox()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(int $$0, boolean $$1) {
/*  44 */     super.setSize($$0, $$1);
/*  45 */     getAttribute(Attributes.ARMOR).setBaseValue(($$0 * 3));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLightLevelDependentMagicValue() {
/*  50 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ParticleOptions getParticleType() {
/*  55 */     return (ParticleOptions)ParticleTypes.FLAME;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnFire() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getJumpDelay() {
/*  65 */     return super.getJumpDelay() * 4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decreaseSquish() {
/*  70 */     this.targetSquish *= 0.9F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void jumpFromGround() {
/*  75 */     Vec3 $$0 = getDeltaMovement();
/*  76 */     float $$1 = getSize() * 0.1F;
/*  77 */     setDeltaMovement($$0.x, (getJumpPower() + $$1), $$0.z);
/*  78 */     this.hasImpulse = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void jumpInLiquid(TagKey<Fluid> $$0) {
/*  83 */     if ($$0 == FluidTags.LAVA) {
/*  84 */       Vec3 $$1 = getDeltaMovement();
/*  85 */       setDeltaMovement($$1.x, (0.22F + getSize() * 0.05F), $$1.z);
/*  86 */       this.hasImpulse = true;
/*     */     } else {
/*  88 */       super.jumpInLiquid($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isDealsDamage() {
/*  94 */     return isEffectiveAi();
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getAttackDamage() {
/*  99 */     return super.getAttackDamage() + 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 104 */     if (isTiny()) {
/* 105 */       return SoundEvents.MAGMA_CUBE_HURT_SMALL;
/*     */     }
/* 107 */     return SoundEvents.MAGMA_CUBE_HURT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 113 */     if (isTiny()) {
/* 114 */       return SoundEvents.MAGMA_CUBE_DEATH_SMALL;
/*     */     }
/* 116 */     return SoundEvents.MAGMA_CUBE_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getSquishSound() {
/* 122 */     if (isTiny()) {
/* 123 */       return SoundEvents.MAGMA_CUBE_SQUISH_SMALL;
/*     */     }
/* 125 */     return SoundEvents.MAGMA_CUBE_SQUISH;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getJumpSound() {
/* 131 */     return SoundEvents.MAGMA_CUBE_JUMP;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\MagmaCube.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */