/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.SmallFireball;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Blaze
/*     */   extends Monster {
/*  33 */   private float allowedHeightOffset = 0.5F;
/*     */   
/*     */   private int nextHeightOffsetChangeTick;
/*  36 */   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Blaze.class, EntityDataSerializers.BYTE);
/*     */   
/*     */   public Blaze(EntityType<? extends Blaze> $$0, Level $$1) {
/*  39 */     super((EntityType)$$0, $$1);
/*     */     
/*  41 */     setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
/*  42 */     setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
/*  43 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
/*  44 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
/*  45 */     this.xpReward = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  50 */     this.goalSelector.addGoal(4, new BlazeAttackGoal(this));
/*  51 */     this.goalSelector.addGoal(5, (Goal)new MoveTowardsRestrictionGoal(this, 1.0D));
/*  52 */     this.goalSelector.addGoal(7, (Goal)new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
/*  53 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/*  54 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  56 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
/*  57 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  61 */     return Monster.createMonsterAttributes()
/*  62 */       .add(Attributes.ATTACK_DAMAGE, 6.0D)
/*  63 */       .add(Attributes.MOVEMENT_SPEED, 0.23000000417232513D)
/*  64 */       .add(Attributes.FOLLOW_RANGE, 48.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  69 */     super.defineSynchedData();
/*     */     
/*  71 */     this.entityData.define(DATA_FLAGS_ID, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  76 */     return SoundEvents.BLAZE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  81 */     return SoundEvents.BLAZE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  86 */     return SoundEvents.BLAZE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLightLevelDependentMagicValue() {
/*  91 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void aiStep() {
/*  97 */     if (!onGround() && (getDeltaMovement()).y < 0.0D) {
/*  98 */       setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
/*     */     }
/*     */     
/* 101 */     if ((level()).isClientSide) {
/* 102 */       if (this.random.nextInt(24) == 0 && !isSilent()) {
/* 103 */         level().playLocalSound(getX() + 0.5D, getY() + 0.5D, getZ() + 0.5D, SoundEvents.BLAZE_BURN, getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/* 105 */       for (int $$0 = 0; $$0 < 2; $$0++) {
/* 106 */         level().addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     } 
/*     */     
/* 110 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSensitiveToWater() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 120 */     this.nextHeightOffsetChangeTick--;
/* 121 */     if (this.nextHeightOffsetChangeTick <= 0) {
/* 122 */       this.nextHeightOffsetChangeTick = 100;
/* 123 */       this.allowedHeightOffset = (float)this.random.triangle(0.5D, 6.891D);
/*     */     } 
/*     */     
/* 126 */     LivingEntity $$0 = getTarget();
/* 127 */     if ($$0 != null && $$0.getEyeY() > getEyeY() + this.allowedHeightOffset && canAttack($$0)) {
/* 128 */       Vec3 $$1 = getDeltaMovement();
/* 129 */       setDeltaMovement(getDeltaMovement().add(0.0D, (0.30000001192092896D - $$1.y) * 0.30000001192092896D, 0.0D));
/* 130 */       this.hasImpulse = true;
/*     */     } 
/*     */     
/* 133 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnFire() {
/* 138 */     return isCharged();
/*     */   }
/*     */   
/*     */   private boolean isCharged() {
/* 142 */     return ((((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & 0x1) != 0);
/*     */   }
/*     */   
/*     */   void setCharged(boolean $$0) {
/* 146 */     byte $$1 = ((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue();
/* 147 */     if ($$0) {
/* 148 */       $$1 = (byte)($$1 | 0x1);
/*     */     } else {
/* 150 */       $$1 = (byte)($$1 & 0xFFFFFFFE);
/*     */     } 
/* 152 */     this.entityData.set(DATA_FLAGS_ID, Byte.valueOf($$1));
/*     */   }
/*     */   
/*     */   private static class BlazeAttackGoal extends Goal {
/*     */     private final Blaze blaze;
/*     */     private int attackStep;
/*     */     private int attackTime;
/*     */     private int lastSeen;
/*     */     
/*     */     public BlazeAttackGoal(Blaze $$0) {
/* 162 */       this.blaze = $$0;
/*     */       
/* 164 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 169 */       LivingEntity $$0 = this.blaze.getTarget();
/* 170 */       return ($$0 != null && $$0.isAlive() && this.blaze.canAttack($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 175 */       this.attackStep = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 180 */       this.blaze.setCharged(false);
/* 181 */       this.lastSeen = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 186 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 191 */       this.attackTime--;
/*     */       
/* 193 */       LivingEntity $$0 = this.blaze.getTarget();
/*     */       
/* 195 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/*     */       
/* 199 */       boolean $$1 = this.blaze.getSensing().hasLineOfSight((Entity)$$0);
/*     */       
/* 201 */       if ($$1) {
/* 202 */         this.lastSeen = 0;
/*     */       } else {
/* 204 */         this.lastSeen++;
/*     */       } 
/*     */       
/* 207 */       double $$2 = this.blaze.distanceToSqr((Entity)$$0);
/*     */       
/* 209 */       if ($$2 < 4.0D) {
/* 210 */         if (!$$1) {
/*     */           return;
/*     */         }
/*     */         
/* 214 */         if (this.attackTime <= 0) {
/* 215 */           this.attackTime = 20;
/* 216 */           this.blaze.doHurtTarget((Entity)$$0);
/*     */         } 
/* 218 */         this.blaze.getMoveControl().setWantedPosition($$0.getX(), $$0.getY(), $$0.getZ(), 1.0D);
/* 219 */       } else if ($$2 < getFollowDistance() * getFollowDistance() && $$1) {
/* 220 */         double $$3 = $$0.getX() - this.blaze.getX();
/* 221 */         double $$4 = $$0.getY(0.5D) - this.blaze.getY(0.5D);
/* 222 */         double $$5 = $$0.getZ() - this.blaze.getZ();
/*     */         
/* 224 */         if (this.attackTime <= 0) {
/* 225 */           this.attackStep++;
/* 226 */           if (this.attackStep == 1) {
/* 227 */             this.attackTime = 60;
/* 228 */             this.blaze.setCharged(true);
/* 229 */           } else if (this.attackStep <= 4) {
/* 230 */             this.attackTime = 6;
/*     */           } else {
/* 232 */             this.attackTime = 100;
/* 233 */             this.attackStep = 0;
/* 234 */             this.blaze.setCharged(false);
/*     */           } 
/*     */           
/* 237 */           if (this.attackStep > 1) {
/* 238 */             double $$6 = Math.sqrt(Math.sqrt($$2)) * 0.5D;
/*     */             
/* 240 */             if (!this.blaze.isSilent()) {
/* 241 */               this.blaze.level().levelEvent(null, 1018, this.blaze.blockPosition(), 0);
/*     */             }
/* 243 */             for (int $$7 = 0; $$7 < 1; $$7++) {
/* 244 */               SmallFireball $$8 = new SmallFireball(this.blaze.level(), (LivingEntity)this.blaze, this.blaze.getRandom().triangle($$3, 2.297D * $$6), $$4, this.blaze.getRandom().triangle($$5, 2.297D * $$6));
/* 245 */               $$8.setPos($$8.getX(), this.blaze.getY(0.5D) + 0.5D, $$8.getZ());
/* 246 */               this.blaze.level().addFreshEntity((Entity)$$8);
/*     */             } 
/*     */           } 
/*     */         } 
/* 250 */         this.blaze.getLookControl().setLookAt((Entity)$$0, 10.0F, 10.0F);
/*     */       }
/* 252 */       else if (this.lastSeen < 5) {
/* 253 */         this.blaze.getMoveControl().setWantedPosition($$0.getX(), $$0.getY(), $$0.getZ(), 1.0D);
/*     */       } 
/*     */ 
/*     */       
/* 257 */       super.tick();
/*     */     }
/*     */     
/*     */     private double getFollowDistance() {
/* 261 */       return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Blaze.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */