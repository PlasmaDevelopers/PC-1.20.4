/*     */ package net.minecraft.world.entity.monster.breeze;
/*     */ 
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AnimationState;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Breeze extends Monster {
/*     */   private static final int SLIDE_PARTICLES_AMOUNT = 20;
/*     */   private static final int IDLE_PARTICLES_AMOUNT = 1;
/*     */   private static final int JUMP_DUST_PARTICLES_AMOUNT = 20;
/*     */   private static final int JUMP_TRAIL_PARTICLES_AMOUNT = 3;
/*     */   private static final int JUMP_TRAIL_DURATION_TICKS = 5;
/*     */   private static final int JUMP_CIRCLE_DISTANCE_Y = 10;
/*     */   private static final float FALL_DISTANCE_SOUND_TRIGGER_THRESHOLD = 3.0F;
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  41 */     return Mob.createMobAttributes()
/*  42 */       .add(Attributes.MOVEMENT_SPEED, 0.6000000238418579D)
/*  43 */       .add(Attributes.MAX_HEALTH, 30.0D)
/*  44 */       .add(Attributes.FOLLOW_RANGE, 24.0D)
/*  45 */       .add(Attributes.ATTACK_DAMAGE, 2.0D);
/*     */   }
/*     */ 
/*     */   
/*  49 */   public AnimationState idle = new AnimationState();
/*  50 */   public AnimationState slide = new AnimationState();
/*  51 */   public AnimationState longJump = new AnimationState();
/*  52 */   public AnimationState shoot = new AnimationState();
/*  53 */   public AnimationState inhale = new AnimationState();
/*     */   
/*  55 */   private int jumpTrailStartedTick = 0;
/*     */   
/*     */   public Breeze(EntityType<? extends Monster> $$0, Level $$1) {
/*  58 */     super($$0, $$1);
/*  59 */     setPathfindingMalus(BlockPathTypes.DANGER_TRAPDOOR, -1.0F);
/*  60 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/*  65 */     return BreezeAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Brain<Breeze> getBrain() {
/*  70 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Breeze> brainProvider() {
/*  75 */     return Brain.provider(BreezeAi.MEMORY_TYPES, BreezeAi.SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAttack(LivingEntity $$0) {
/*  80 */     return ($$0.getType() != EntityType.BREEZE && super.canAttack($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  85 */     if (level().isClientSide() && DATA_POSE.equals($$0)) {
/*     */       
/*  87 */       resetAnimations();
/*     */       
/*  89 */       Pose $$1 = getPose();
/*  90 */       switch ($$1) { case SHOOTING:
/*  91 */           this.shoot.startIfStopped(this.tickCount); break;
/*  92 */         case INHALING: this.longJump.startIfStopped(this.tickCount); break;
/*  93 */         case SLIDING: this.slide.startIfStopped(this.tickCount);
/*     */           break; }
/*     */     
/*     */     } 
/*  97 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */   
/*     */   private void resetAnimations() {
/* 101 */     this.shoot.stop();
/* 102 */     this.idle.stop();
/* 103 */     this.inhale.stop();
/* 104 */     this.longJump.stop();
/* 105 */     this.slide.stop();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 110 */     switch (getPose()) { case SLIDING:
/* 111 */         emitGroundParticles(20); break;
/*     */       case SHOOTING: case INHALING: case STANDING:
/* 113 */         resetJumpTrail().emitGroundParticles(1 + getRandom().nextInt(1)); break;
/* 114 */       case LONG_JUMPING: emitJumpTrailParticles();
/*     */         break; }
/*     */     
/* 117 */     super.tick();
/*     */   }
/*     */   
/*     */   public Breeze resetJumpTrail() {
/* 121 */     this.jumpTrailStartedTick = 0;
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public Breeze emitJumpDustParticles() {
/* 126 */     Vec3 $$0 = position().add(0.0D, 0.10000000149011612D, 0.0D);
/*     */     
/* 128 */     for (int $$1 = 0; $$1 < 20; $$1++) {
/* 129 */       level().addParticle((ParticleOptions)ParticleTypes.GUST_DUST, $$0.x, $$0.y, $$0.z, 0.0D, 0.0D, 0.0D);
/*     */     }
/* 131 */     return this;
/*     */   }
/*     */   
/*     */   public void emitJumpTrailParticles() {
/* 135 */     if (++this.jumpTrailStartedTick > 5) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     BlockState $$0 = level().getBlockState(blockPosition().below());
/* 140 */     Vec3 $$1 = getDeltaMovement();
/* 141 */     Vec3 $$2 = position().add($$1).add(0.0D, 0.10000000149011612D, 0.0D);
/*     */     
/* 143 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/* 144 */       level().addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, $$0), $$2.x, $$2.y, $$2.z, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public void emitGroundParticles(int $$0) {
/* 149 */     Vec3 $$1 = getBoundingBox().getCenter();
/* 150 */     Vec3 $$2 = new Vec3($$1.x, (position()).y, $$1.z);
/* 151 */     BlockState $$3 = level().getBlockState(blockPosition().below());
/*     */     
/* 153 */     if ($$3.getRenderShape() == RenderShape.INVISIBLE) {
/*     */       return;
/*     */     }
/*     */     
/* 157 */     for (int $$4 = 0; $$4 < $$0; $$4++) {
/* 158 */       level().addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, $$3), $$2.x, $$2.y, $$2.z, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playAmbientSound() {
/* 165 */     level().playLocalSound((Entity)this, getAmbientSound(), getSoundSource(), 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 170 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 175 */     return SoundEvents.BREEZE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 180 */     return SoundEvents.BREEZE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 185 */     return onGround() ? SoundEvents.BREEZE_IDLE_GROUND : SoundEvents.BREEZE_IDLE_AIR;
/*     */   }
/*     */   
/*     */   public boolean withinOuterCircleRange(Vec3 $$0) {
/* 189 */     Vec3 $$1 = blockPosition().getCenter();
/* 190 */     return ($$0.closerThan($$1, 20.0D, 10.0D) && 
/* 191 */       !$$0.closerThan($$1, 8.0D, 10.0D));
/*     */   }
/*     */   
/*     */   public boolean withinMiddleCircleRange(Vec3 $$0) {
/* 195 */     Vec3 $$1 = blockPosition().getCenter();
/* 196 */     return ($$0.closerThan($$1, 8.0D, 10.0D) && 
/* 197 */       !$$0.closerThan($$1, 4.0D, 10.0D));
/*     */   }
/*     */   
/*     */   public boolean withinInnerCircleRange(Vec3 $$0) {
/* 201 */     Vec3 $$1 = blockPosition().getCenter();
/* 202 */     return $$0.closerThan($$1, 4.0D, 10.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 207 */     level().getProfiler().push("breezeBrain");
/* 208 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/*     */     
/* 210 */     level().getProfiler().popPush("breezeActivityUpdate");
/* 211 */     level().getProfiler().pop();
/*     */     
/* 213 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 218 */     super.sendDebugPackets();
/* 219 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/* 220 */     DebugPackets.sendBreezeInfo(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAttackType(EntityType<?> $$0) {
/* 225 */     return ($$0 == EntityType.PLAYER);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 230 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeadRotSpeed() {
/* 235 */     return 25;
/*     */   }
/*     */   
/*     */   public double getSnoutYPosition() {
/* 239 */     return getEyeY() - 0.4D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvulnerableTo(DamageSource $$0) {
/* 244 */     return ($$0.is(DamageTypeTags.BREEZE_IMMUNE_TO) || $$0.getEntity() instanceof Breeze || super.isInvulnerableTo($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFluidJumpThreshold() {
/* 250 */     return getEyeHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/* 256 */     if ($$0 > 3.0F) {
/* 257 */       playSound(SoundEvents.BREEZE_LAND, 1.0F, 1.0F);
/*     */     }
/* 259 */     return super.causeFallDamage($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 264 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\breeze\Breeze.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */