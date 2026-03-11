/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.animal.Squid;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class GlowSquid extends Squid {
/*  20 */   private static final EntityDataAccessor<Integer> DATA_DARK_TICKS_REMAINING = SynchedEntityData.defineId(GlowSquid.class, EntityDataSerializers.INT);
/*     */   
/*     */   public GlowSquid(EntityType<? extends GlowSquid> $$0, Level $$1) {
/*  23 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ParticleOptions getInkParticle() {
/*  28 */     return (ParticleOptions)ParticleTypes.GLOW_SQUID_INK;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  33 */     super.defineSynchedData();
/*  34 */     this.entityData.define(DATA_DARK_TICKS_REMAINING, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSquirtSound() {
/*  39 */     return SoundEvents.GLOW_SQUID_SQUIRT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  44 */     return SoundEvents.GLOW_SQUID_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  49 */     return SoundEvents.GLOW_SQUID_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  54 */     return SoundEvents.GLOW_SQUID_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  59 */     super.addAdditionalSaveData($$0);
/*  60 */     $$0.putInt("DarkTicksRemaining", getDarkTicksRemaining());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  65 */     super.readAdditionalSaveData($$0);
/*  66 */     setDarkTicks($$0.getInt("DarkTicksRemaining"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/*  71 */     super.aiStep();
/*     */     
/*  73 */     int $$0 = getDarkTicksRemaining();
/*  74 */     if ($$0 > 0) {
/*  75 */       setDarkTicks($$0 - 1);
/*     */     }
/*     */     
/*  78 */     level().addParticle((ParticleOptions)ParticleTypes.GLOW, getRandomX(0.6D), getRandomY(), getRandomZ(0.6D), 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/*  83 */     boolean $$2 = super.hurt($$0, $$1);
/*  84 */     if ($$2) {
/*  85 */       setDarkTicks(100);
/*     */     }
/*     */     
/*  88 */     return $$2;
/*     */   }
/*     */   
/*     */   private void setDarkTicks(int $$0) {
/*  92 */     this.entityData.set(DATA_DARK_TICKS_REMAINING, Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public int getDarkTicksRemaining() {
/*  96 */     return ((Integer)this.entityData.get(DATA_DARK_TICKS_REMAINING)).intValue();
/*     */   }
/*     */   
/*     */   public static boolean checkGlowSquidSpawnRules(EntityType<? extends LivingEntity> $$0, ServerLevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 100 */     return ($$3.getY() <= $$1.getSeaLevel() - 33 && $$1.getRawBrightness($$3, 0) == 0 && $$1.getBlockState($$3).is(Blocks.WATER));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\GlowSquid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */