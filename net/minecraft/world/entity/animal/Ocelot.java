/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Ocelot extends Animal {
/*     */   public static final double CROUCH_SPEED_MOD = 0.6D;
/*     */   public static final double WALK_SPEED_MOD = 0.8D;
/*     */   public static final double SPRINT_SPEED_MOD = 1.33D;
/*  62 */   private static final Ingredient TEMPT_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.COD, (ItemLike)Items.SALMON });
/*     */   
/*  64 */   private static final EntityDataAccessor<Boolean> DATA_TRUSTING = SynchedEntityData.defineId(Ocelot.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   @Nullable
/*     */   private OcelotAvoidEntityGoal<Player> ocelotAvoidPlayersGoal;
/*     */   @Nullable
/*     */   private OcelotTemptGoal temptGoal;
/*     */   
/*     */   public Ocelot(EntityType<? extends Ocelot> $$0, Level $$1) {
/*  72 */     super((EntityType)$$0, $$1);
/*     */     
/*  74 */     reassessTrustingGoals();
/*     */   }
/*     */   
/*     */   boolean isTrusting() {
/*  78 */     return ((Boolean)this.entityData.get(DATA_TRUSTING)).booleanValue();
/*     */   }
/*     */   
/*     */   private void setTrusting(boolean $$0) {
/*  82 */     this.entityData.set(DATA_TRUSTING, Boolean.valueOf($$0));
/*     */     
/*  84 */     reassessTrustingGoals();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  89 */     super.addAdditionalSaveData($$0);
/*     */     
/*  91 */     $$0.putBoolean("Trusting", isTrusting());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  96 */     super.readAdditionalSaveData($$0);
/*     */     
/*  98 */     setTrusting($$0.getBoolean("Trusting"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 103 */     super.defineSynchedData();
/*     */     
/* 105 */     this.entityData.define(DATA_TRUSTING, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 110 */     this.temptGoal = new OcelotTemptGoal(this, 0.6D, TEMPT_INGREDIENT, true);
/* 111 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/* 112 */     this.goalSelector.addGoal(3, (Goal)this.temptGoal);
/* 113 */     this.goalSelector.addGoal(7, (Goal)new LeapAtTargetGoal((Mob)this, 0.3F));
/* 114 */     this.goalSelector.addGoal(8, (Goal)new OcelotAttackGoal((Mob)this));
/* 115 */     this.goalSelector.addGoal(9, (Goal)new BreedGoal(this, 0.8D));
/* 116 */     this.goalSelector.addGoal(10, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.8D, 1.0000001E-5F));
/* 117 */     this.goalSelector.addGoal(11, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 10.0F));
/*     */     
/* 119 */     this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Chicken.class, false));
/* 120 */     this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Turtle.class, 10, false, false, Turtle.BABY_ON_LAND_SELECTOR));
/*     */   }
/*     */ 
/*     */   
/*     */   public void customServerAiStep() {
/* 125 */     if (getMoveControl().hasWanted()) {
/* 126 */       double $$0 = getMoveControl().getSpeedModifier();
/* 127 */       if ($$0 == 0.6D) {
/* 128 */         setPose(Pose.CROUCHING);
/* 129 */         setSprinting(false);
/* 130 */       } else if ($$0 == 1.33D) {
/* 131 */         setPose(Pose.STANDING);
/* 132 */         setSprinting(true);
/*     */       } else {
/* 134 */         setPose(Pose.STANDING);
/* 135 */         setSprinting(false);
/*     */       } 
/*     */     } else {
/* 138 */       setPose(Pose.STANDING);
/* 139 */       setSprinting(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 145 */     return (!isTrusting() && this.tickCount > 2400);
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 149 */     return Mob.createMobAttributes()
/* 150 */       .add(Attributes.MAX_HEALTH, 10.0D)
/* 151 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 152 */       .add(Attributes.ATTACK_DAMAGE, 3.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 158 */     return SoundEvents.OCELOT_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmbientSoundInterval() {
/* 163 */     return 900;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 168 */     return SoundEvents.OCELOT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 173 */     return SoundEvents.OCELOT_DEATH;
/*     */   }
/*     */   
/*     */   private float getAttackDamage() {
/* 177 */     return (float)getAttributeValue(Attributes.ATTACK_DAMAGE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 182 */     return $$0.hurt(damageSources().mobAttack((LivingEntity)this), getAttackDamage());
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 187 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 188 */     if ((this.temptGoal == null || this.temptGoal.isRunning()) && !isTrusting() && isFood($$2) && $$0.distanceToSqr((Entity)this) < 9.0D) {
/* 189 */       usePlayerItem($$0, $$1, $$2);
/*     */       
/* 191 */       if (!(level()).isClientSide) {
/* 192 */         if (this.random.nextInt(3) == 0) {
/* 193 */           setTrusting(true);
/* 194 */           spawnTrustingParticles(true);
/* 195 */           level().broadcastEntityEvent((Entity)this, (byte)41);
/*     */         } else {
/* 197 */           spawnTrustingParticles(false);
/* 198 */           level().broadcastEntityEvent((Entity)this, (byte)40);
/*     */         } 
/*     */       }
/*     */       
/* 202 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 205 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 210 */     if ($$0 == 41) {
/* 211 */       spawnTrustingParticles(true);
/* 212 */     } else if ($$0 == 40) {
/* 213 */       spawnTrustingParticles(false);
/*     */     } else {
/* 215 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnTrustingParticles(boolean $$0) {
/* 220 */     SimpleParticleType simpleParticleType = ParticleTypes.HEART;
/* 221 */     if (!$$0) {
/* 222 */       simpleParticleType = ParticleTypes.SMOKE;
/*     */     }
/* 224 */     for (int $$2 = 0; $$2 < 7; $$2++) {
/* 225 */       double $$3 = this.random.nextGaussian() * 0.02D;
/* 226 */       double $$4 = this.random.nextGaussian() * 0.02D;
/* 227 */       double $$5 = this.random.nextGaussian() * 0.02D;
/* 228 */       level().addParticle((ParticleOptions)simpleParticleType, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), $$3, $$4, $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void reassessTrustingGoals() {
/* 233 */     if (this.ocelotAvoidPlayersGoal == null) {
/* 234 */       this.ocelotAvoidPlayersGoal = new OcelotAvoidEntityGoal<>(this, Player.class, 16.0F, 0.8D, 1.33D);
/*     */     }
/*     */     
/* 237 */     this.goalSelector.removeGoal((Goal)this.ocelotAvoidPlayersGoal);
/*     */     
/* 239 */     if (!isTrusting()) {
/* 240 */       this.goalSelector.addGoal(4, (Goal)this.ocelotAvoidPlayersGoal);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ocelot getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 247 */     return (Ocelot)EntityType.OCELOT.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 252 */     return TEMPT_INGREDIENT.test($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkOcelotSpawnRules(EntityType<Ocelot> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 258 */     return ($$4.nextInt(3) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 263 */     if ($$0.isUnobstructed((Entity)this) && !$$0.containsAnyLiquid(getBoundingBox())) {
/* 264 */       BlockPos $$1 = blockPosition();
/* 265 */       if ($$1.getY() < $$0.getSeaLevel()) {
/* 266 */         return false;
/*     */       }
/*     */       
/* 269 */       BlockState $$2 = $$0.getBlockState($$1.below());
/* 270 */       if ($$2.is(Blocks.GRASS_BLOCK) || $$2.is(BlockTags.LEAVES)) {
/* 271 */         return true;
/*     */       }
/*     */     } 
/* 274 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/* 280 */     if ($$3 == null)
/*     */     {
/* 282 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(1.0F);
/*     */     }
/*     */     
/* 285 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 290 */     return new Vec3(0.0D, (0.5F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSteppingCarefully() {
/* 295 */     return (isCrouching() || super.isSteppingCarefully());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 300 */     return new Vector3f(0.0F, $$1.height - 0.0625F * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   private static class OcelotAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
/*     */     private final Ocelot ocelot;
/*     */     
/*     */     public OcelotAvoidEntityGoal(Ocelot $$0, Class<T> $$1, float $$2, double $$3, double $$4) {
/* 307 */       super((PathfinderMob)$$0, $$1, $$2, $$3, $$4, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
/* 308 */       this.ocelot = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 313 */       return (!this.ocelot.isTrusting() && super.canUse());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 318 */       return (!this.ocelot.isTrusting() && super.canContinueToUse());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class OcelotTemptGoal extends TemptGoal {
/*     */     private final Ocelot ocelot;
/*     */     
/*     */     public OcelotTemptGoal(Ocelot $$0, double $$1, Ingredient $$2, boolean $$3) {
/* 326 */       super((PathfinderMob)$$0, $$1, $$2, $$3);
/* 327 */       this.ocelot = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean canScare() {
/* 332 */       return (super.canScare() && !this.ocelot.isTrusting());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Ocelot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */