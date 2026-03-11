/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Squid
/*     */   extends WaterAnimal
/*     */ {
/*     */   public float xBodyRot;
/*     */   public float xBodyRotO;
/*     */   public float zBodyRot;
/*     */   public float zBodyRotO;
/*     */   public float tentacleMovement;
/*     */   public float oldTentacleMovement;
/*     */   public float tentacleAngle;
/*     */   public float oldTentacleAngle;
/*     */   private float speed;
/*     */   private float tentacleSpeed;
/*     */   private float rotateSpeed;
/*     */   private float tx;
/*     */   private float ty;
/*     */   private float tz;
/*     */   
/*     */   public Squid(EntityType<? extends Squid> $$0, Level $$1) {
/*  50 */     super((EntityType)$$0, $$1);
/*     */     
/*  52 */     this.random.setSeed(getId());
/*  53 */     this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  58 */     this.goalSelector.addGoal(0, new SquidRandomMovementGoal(this));
/*  59 */     this.goalSelector.addGoal(1, new SquidFleeGoal());
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  63 */     return Mob.createMobAttributes()
/*  64 */       .add(Attributes.MAX_HEALTH, 10.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  69 */     return $$1.height * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  74 */     return SoundEvents.SQUID_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  79 */     return SoundEvents.SQUID_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  84 */     return SoundEvents.SQUID_DEATH;
/*     */   }
/*     */   
/*     */   protected SoundEvent getSquirtSound() {
/*  88 */     return SoundEvents.SQUID_SQUIRT;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/*  93 */     return !isLeashed();
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  98 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 103 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 108 */     super.aiStep();
/*     */     
/* 110 */     this.xBodyRotO = this.xBodyRot;
/* 111 */     this.zBodyRotO = this.zBodyRot;
/*     */     
/* 113 */     this.oldTentacleMovement = this.tentacleMovement;
/* 114 */     this.oldTentacleAngle = this.tentacleAngle;
/*     */     
/* 116 */     this.tentacleMovement += this.tentacleSpeed;
/* 117 */     if (this.tentacleMovement > 6.283185307179586D) {
/* 118 */       if ((level()).isClientSide) {
/* 119 */         this.tentacleMovement = 6.2831855F;
/*     */       } else {
/* 121 */         this.tentacleMovement -= 6.2831855F;
/* 122 */         if (this.random.nextInt(10) == 0) {
/* 123 */           this.tentacleSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
/*     */         }
/* 125 */         level().broadcastEntityEvent((Entity)this, (byte)19);
/*     */       } 
/*     */     }
/*     */     
/* 129 */     if (isInWaterOrBubble()) {
/* 130 */       if (this.tentacleMovement < 3.1415927F) {
/* 131 */         float $$0 = this.tentacleMovement / 3.1415927F;
/* 132 */         this.tentacleAngle = Mth.sin($$0 * $$0 * 3.1415927F) * 3.1415927F * 0.25F;
/*     */         
/* 134 */         if ($$0 > 0.75D) {
/* 135 */           this.speed = 1.0F;
/* 136 */           this.rotateSpeed = 1.0F;
/*     */         } else {
/* 138 */           this.rotateSpeed *= 0.8F;
/*     */         } 
/*     */       } else {
/* 141 */         this.tentacleAngle = 0.0F;
/* 142 */         this.speed *= 0.9F;
/* 143 */         this.rotateSpeed *= 0.99F;
/*     */       } 
/*     */       
/* 146 */       if (!(level()).isClientSide) {
/* 147 */         setDeltaMovement((this.tx * this.speed), (this.ty * this.speed), (this.tz * this.speed));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       Vec3 $$1 = getDeltaMovement();
/* 155 */       double $$2 = $$1.horizontalDistance();
/*     */       
/* 157 */       this.yBodyRot += (-((float)Mth.atan2($$1.x, $$1.z)) * 57.295776F - this.yBodyRot) * 0.1F;
/* 158 */       setYRot(this.yBodyRot);
/* 159 */       this.zBodyRot += 3.1415927F * this.rotateSpeed * 1.5F;
/* 160 */       this.xBodyRot += (-((float)Mth.atan2($$2, $$1.y)) * 57.295776F - this.xBodyRot) * 0.1F;
/*     */     } else {
/* 162 */       this.tentacleAngle = Mth.abs(Mth.sin(this.tentacleMovement)) * 3.1415927F * 0.25F;
/*     */       
/* 164 */       if (!(level()).isClientSide) {
/* 165 */         double $$3 = (getDeltaMovement()).y;
/*     */         
/* 167 */         if (hasEffect(MobEffects.LEVITATION)) {
/* 168 */           $$3 = 0.05D * (getEffect(MobEffects.LEVITATION).getAmplifier() + 1);
/* 169 */         } else if (!isNoGravity()) {
/* 170 */           $$3 -= 0.08D;
/*     */         } 
/*     */         
/* 173 */         setDeltaMovement(0.0D, $$3 * 0.9800000190734863D, 0.0D);
/*     */       } 
/*     */ 
/*     */       
/* 177 */       this.xBodyRot += (-90.0F - this.xBodyRot) * 0.02F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 183 */     if (super.hurt($$0, $$1) && getLastHurtByMob() != null) {
/* 184 */       if (!(level()).isClientSide) {
/* 185 */         spawnInk();
/*     */       }
/* 187 */       return true;
/*     */     } 
/*     */     
/* 190 */     return false;
/*     */   }
/*     */   
/*     */   private Vec3 rotateVector(Vec3 $$0) {
/* 194 */     Vec3 $$1 = $$0.xRot(this.xBodyRotO * 0.017453292F);
/* 195 */     $$1 = $$1.yRot(-this.yBodyRotO * 0.017453292F);
/* 196 */     return $$1;
/*     */   }
/*     */   
/*     */   private void spawnInk() {
/* 200 */     playSound(getSquirtSound(), getSoundVolume(), getVoicePitch());
/* 201 */     Vec3 $$0 = rotateVector(new Vec3(0.0D, -1.0D, 0.0D)).add(getX(), getY(), getZ());
/* 202 */     for (int $$1 = 0; $$1 < 30; $$1++) {
/* 203 */       Vec3 $$2 = rotateVector(new Vec3(this.random.nextFloat() * 0.6D - 0.3D, -1.0D, this.random.nextFloat() * 0.6D - 0.3D));
/* 204 */       Vec3 $$3 = $$2.scale(0.3D + (this.random.nextFloat() * 2.0F));
/* 205 */       ((ServerLevel)level()).sendParticles(getInkParticle(), $$0.x, $$0.y + 0.5D, $$0.z, 0, $$3.x, $$3.y, $$3.z, 0.10000000149011612D);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ParticleOptions getInkParticle() {
/* 210 */     return (ParticleOptions)ParticleTypes.SQUID_INK;
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 215 */     move(MoverType.SELF, getDeltaMovement());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 220 */     if ($$0 == 19) {
/* 221 */       this.tentacleMovement = 0.0F;
/*     */     } else {
/* 223 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMovementVector(float $$0, float $$1, float $$2) {
/* 228 */     this.tx = $$0;
/* 229 */     this.ty = $$1;
/* 230 */     this.tz = $$2;
/*     */   }
/*     */   
/*     */   public boolean hasMovementVector() {
/* 234 */     return (this.tx != 0.0F || this.ty != 0.0F || this.tz != 0.0F);
/*     */   }
/*     */   
/*     */   private class SquidRandomMovementGoal extends Goal {
/*     */     private final Squid squid;
/*     */     
/*     */     public SquidRandomMovementGoal(Squid $$0) {
/* 241 */       this.squid = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 246 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 251 */       int $$0 = this.squid.getNoActionTime();
/*     */       
/* 253 */       if ($$0 > 100) {
/* 254 */         this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
/* 255 */       } else if (this.squid.getRandom().nextInt(reducedTickDelay(50)) == 0 || !this.squid.wasTouchingWater || !this.squid.hasMovementVector()) {
/* 256 */         float $$1 = this.squid.getRandom().nextFloat() * 6.2831855F;
/* 257 */         float $$2 = Mth.cos($$1) * 0.2F;
/* 258 */         float $$3 = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
/* 259 */         float $$4 = Mth.sin($$1) * 0.2F;
/* 260 */         this.squid.setMovementVector($$2, $$3, $$4);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class SquidFleeGoal
/*     */     extends Goal
/*     */   {
/*     */     private static final float SQUID_FLEE_SPEED = 3.0F;
/*     */     private static final float SQUID_FLEE_MIN_DISTANCE = 5.0F;
/*     */     private static final float SQUID_FLEE_MAX_DISTANCE = 10.0F;
/*     */     private int fleeTicks;
/*     */     
/*     */     public boolean canUse() {
/* 274 */       LivingEntity $$0 = Squid.this.getLastHurtByMob();
/* 275 */       if (Squid.this.isInWater() && $$0 != null) {
/* 276 */         return (Squid.this.distanceToSqr((Entity)$$0) < 100.0D);
/*     */       }
/*     */       
/* 279 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 284 */       this.fleeTicks = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 289 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 294 */       this.fleeTicks++;
/*     */       
/* 296 */       LivingEntity $$0 = Squid.this.getLastHurtByMob();
/* 297 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/*     */       
/* 301 */       Vec3 $$1 = new Vec3(Squid.this.getX() - $$0.getX(), Squid.this.getY() - $$0.getY(), Squid.this.getZ() - $$0.getZ());
/*     */       
/* 303 */       BlockState $$2 = Squid.this.level().getBlockState(BlockPos.containing(Squid.this.getX() + $$1.x, Squid.this.getY() + $$1.y, Squid.this.getZ() + $$1.z));
/* 304 */       FluidState $$3 = Squid.this.level().getFluidState(BlockPos.containing(Squid.this.getX() + $$1.x, Squid.this.getY() + $$1.y, Squid.this.getZ() + $$1.z));
/* 305 */       if ($$3.is(FluidTags.WATER) || $$2.isAir()) {
/* 306 */         double $$4 = $$1.length();
/* 307 */         if ($$4 > 0.0D) {
/* 308 */           $$1.normalize();
/*     */           
/* 310 */           double $$5 = 3.0D;
/* 311 */           if ($$4 > 5.0D) {
/* 312 */             $$5 -= ($$4 - 5.0D) / 5.0D;
/*     */           }
/*     */           
/* 315 */           if ($$5 > 0.0D) {
/* 316 */             $$1 = $$1.scale($$5);
/*     */           }
/*     */         } 
/*     */         
/* 320 */         if ($$2.isAir()) {
/* 321 */           $$1 = $$1.subtract(0.0D, $$1.y, 0.0D);
/*     */         }
/*     */         
/* 324 */         Squid.this.setMovementVector((float)$$1.x / 20.0F, (float)$$1.y / 20.0F, (float)$$1.z / 20.0F);
/*     */       } 
/*     */       
/* 327 */       if (this.fleeTicks % 10 == 5)
/* 328 */         Squid.this.level().addParticle((ParticleOptions)ParticleTypes.BUBBLE, Squid.this.getX(), Squid.this.getY(), Squid.this.getZ(), 0.0D, 0.0D, 0.0D); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Squid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */