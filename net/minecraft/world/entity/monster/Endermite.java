/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Endermite extends Monster {
/*     */   private static final int MAX_LIFE = 2400;
/*     */   
/*     */   public Endermite(EntityType<? extends Endermite> $$0, Level $$1) {
/*  39 */     super((EntityType)$$0, $$1);
/*  40 */     this.xpReward = 3;
/*     */   }
/*     */   private int life;
/*     */   
/*     */   protected void registerGoals() {
/*  45 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/*  46 */     this.goalSelector.addGoal(1, (Goal)new ClimbOnTopOfPowderSnowGoal((Mob)this, level()));
/*  47 */     this.goalSelector.addGoal(2, (Goal)new MeleeAttackGoal(this, 1.0D, false));
/*  48 */     this.goalSelector.addGoal(3, (Goal)new WaterAvoidingRandomStrollGoal(this, 1.0D));
/*  49 */     this.goalSelector.addGoal(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/*  50 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  52 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
/*  53 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  58 */     return 0.13F;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  62 */     return Monster.createMonsterAttributes()
/*  63 */       .add(Attributes.MAX_HEALTH, 8.0D)
/*  64 */       .add(Attributes.MOVEMENT_SPEED, 0.25D)
/*  65 */       .add(Attributes.ATTACK_DAMAGE, 2.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/*  70 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  75 */     return SoundEvents.ENDERMITE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  80 */     return SoundEvents.ENDERMITE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  85 */     return SoundEvents.ENDERMITE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/*  90 */     playSound(SoundEvents.ENDERMITE_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  95 */     super.readAdditionalSaveData($$0);
/*  96 */     this.life = $$0.getInt("Lifetime");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 101 */     super.addAdditionalSaveData($$0);
/* 102 */     $$0.putInt("Lifetime", this.life);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 108 */     this.yBodyRot = getYRot();
/*     */     
/* 110 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYBodyRot(float $$0) {
/* 115 */     setYRot($$0);
/* 116 */     super.setYBodyRot($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 121 */     super.aiStep();
/*     */     
/* 123 */     if ((level()).isClientSide) {
/* 124 */       for (int $$0 = 0; $$0 < 2; $$0++) {
/* 125 */         level().addParticle((ParticleOptions)ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
/*     */       }
/*     */     } else {
/* 128 */       if (!isPersistenceRequired()) {
/* 129 */         this.life++;
/*     */       }
/*     */       
/* 132 */       if (this.life >= 2400) {
/* 133 */         discard();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean checkEndermiteSpawnRules(EntityType<Endermite> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 139 */     if (checkAnyLightMonsterSpawnRules((EntityType)$$0, $$1, $$2, $$3, $$4)) {
/* 140 */       Player $$5 = $$1.getNearestPlayer($$3.getX() + 0.5D, $$3.getY() + 0.5D, $$3.getZ() + 0.5D, 5.0D, true);
/* 141 */       return ($$5 == null);
/*     */     } 
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 148 */     return MobType.ARTHROPOD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 153 */     return new Vector3f(0.0F, $$1.height - 0.0625F * $$2, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Endermite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */