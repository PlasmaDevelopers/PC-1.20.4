/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.NeutralMob;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class IronGolem
/*     */   extends AbstractGolem
/*     */   implements NeutralMob
/*     */ {
/*  56 */   protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(IronGolem.class, EntityDataSerializers.BYTE);
/*     */   
/*     */   private static final int IRON_INGOT_HEAL_AMOUNT = 25;
/*     */   
/*     */   private int attackAnimationTick;
/*     */   private int offerFlowerTick;
/*  62 */   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
/*     */   private int remainingPersistentAngerTime;
/*     */   @Nullable
/*     */   private UUID persistentAngerTarget;
/*     */   
/*     */   public IronGolem(EntityType<? extends IronGolem> $$0, Level $$1) {
/*  68 */     super((EntityType)$$0, $$1);
/*  69 */     setMaxUpStep(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  74 */     this.goalSelector.addGoal(1, (Goal)new MeleeAttackGoal(this, 1.0D, true));
/*  75 */     this.goalSelector.addGoal(2, (Goal)new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
/*  76 */     this.goalSelector.addGoal(2, (Goal)new MoveBackToVillageGoal(this, 0.6D, false));
/*  77 */     this.goalSelector.addGoal(4, (Goal)new GolemRandomStrollInVillageGoal(this, 0.6D));
/*  78 */     this.goalSelector.addGoal(5, (Goal)new OfferFlowerGoal(this));
/*  79 */     this.goalSelector.addGoal(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  80 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  82 */     this.targetSelector.addGoal(1, (Goal)new DefendVillageTargetGoal(this));
/*  83 */     this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal(this, new Class[0]));
/*  84 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, this::isAngryAt));
/*  85 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Mob.class, 5, false, false, $$0 -> ($$0 instanceof net.minecraft.world.entity.monster.Enemy && !($$0 instanceof net.minecraft.world.entity.monster.Creeper))));
/*  86 */     this.targetSelector.addGoal(4, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  91 */     super.defineSynchedData();
/*  92 */     this.entityData.define(DATA_FLAGS_ID, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  96 */     return Mob.createMobAttributes()
/*  97 */       .add(Attributes.MAX_HEALTH, 100.0D)
/*  98 */       .add(Attributes.MOVEMENT_SPEED, 0.25D)
/*  99 */       .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
/* 100 */       .add(Attributes.ATTACK_DAMAGE, 15.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int decreaseAirSupply(int $$0) {
/* 106 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPush(Entity $$0) {
/* 111 */     if ($$0 instanceof net.minecraft.world.entity.monster.Enemy && !($$0 instanceof net.minecraft.world.entity.monster.Creeper) && 
/* 112 */       getRandom().nextInt(20) == 0) {
/* 113 */       setTarget((LivingEntity)$$0);
/*     */     }
/*     */     
/* 116 */     super.doPush($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 121 */     super.aiStep();
/*     */     
/* 123 */     if (this.attackAnimationTick > 0) {
/* 124 */       this.attackAnimationTick--;
/*     */     }
/* 126 */     if (this.offerFlowerTick > 0) {
/* 127 */       this.offerFlowerTick--;
/*     */     }
/*     */     
/* 130 */     if (!(level()).isClientSide) {
/* 131 */       updatePersistentAnger((ServerLevel)level(), true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawnSprintParticle() {
/* 137 */     return (getDeltaMovement().horizontalDistanceSqr() > 2.500000277905201E-7D && this.random.nextInt(5) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAttackType(EntityType<?> $$0) {
/* 142 */     if (isPlayerCreated() && $$0 == EntityType.PLAYER) {
/* 143 */       return false;
/*     */     }
/* 145 */     if ($$0 == EntityType.CREEPER) {
/* 146 */       return false;
/*     */     }
/* 148 */     return super.canAttackType($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 153 */     super.addAdditionalSaveData($$0);
/* 154 */     $$0.putBoolean("PlayerCreated", isPlayerCreated());
/* 155 */     addPersistentAngerSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 160 */     super.readAdditionalSaveData($$0);
/* 161 */     setPlayerCreated($$0.getBoolean("PlayerCreated"));
/* 162 */     readPersistentAngerSaveData(level(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startPersistentAngerTimer() {
/* 167 */     setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRemainingPersistentAngerTime(int $$0) {
/* 172 */     this.remainingPersistentAngerTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemainingPersistentAngerTime() {
/* 177 */     return this.remainingPersistentAngerTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPersistentAngerTarget(@Nullable UUID $$0) {
/* 182 */     this.persistentAngerTarget = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getPersistentAngerTarget() {
/* 188 */     return this.persistentAngerTarget;
/*     */   }
/*     */   
/*     */   private float getAttackDamage() {
/* 192 */     return (float)getAttributeValue(Attributes.ATTACK_DAMAGE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 197 */     this.attackAnimationTick = 10;
/* 198 */     level().broadcastEntityEvent((Entity)this, (byte)4);
/* 199 */     float $$1 = getAttackDamage();
/* 200 */     float $$2 = ((int)$$1 > 0) ? ($$1 / 2.0F + this.random.nextInt((int)$$1)) : $$1;
/* 201 */     boolean $$3 = $$0.hurt(damageSources().mobAttack((LivingEntity)this), $$2);
/* 202 */     if ($$3) {
/* 203 */       LivingEntity $$4 = (LivingEntity)$$0; double $$5 = ($$0 instanceof LivingEntity) ? $$4.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 0.0D;
/* 204 */       double $$6 = Math.max(0.0D, 1.0D - $$5);
/*     */       
/* 206 */       $$0.setDeltaMovement($$0.getDeltaMovement().add(0.0D, 0.4000000059604645D * $$6, 0.0D));
/* 207 */       doEnchantDamageEffects((LivingEntity)this, $$0);
/*     */     } 
/* 209 */     playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
/* 210 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 215 */     Crackiness $$2 = getCrackiness();
/* 216 */     boolean $$3 = super.hurt($$0, $$1);
/* 217 */     if ($$3 && getCrackiness() != $$2) {
/* 218 */       playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
/*     */     }
/* 220 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Crackiness getCrackiness() {
/* 227 */     return Crackiness.byFraction(getHealth() / getMaxHealth());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 232 */     if ($$0 == 4) {
/* 233 */       this.attackAnimationTick = 10;
/* 234 */       playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
/* 235 */     } else if ($$0 == 11) {
/* 236 */       this.offerFlowerTick = 400;
/* 237 */     } else if ($$0 == 34) {
/* 238 */       this.offerFlowerTick = 0;
/*     */     } else {
/* 240 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getAttackAnimationTick() {
/* 245 */     return this.attackAnimationTick;
/*     */   }
/*     */   
/*     */   public void offerFlower(boolean $$0) {
/* 249 */     if ($$0) {
/* 250 */       this.offerFlowerTick = 400;
/* 251 */       level().broadcastEntityEvent((Entity)this, (byte)11);
/*     */     } else {
/* 253 */       this.offerFlowerTick = 0;
/* 254 */       level().broadcastEntityEvent((Entity)this, (byte)34);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 260 */     return SoundEvents.IRON_GOLEM_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 265 */     return SoundEvents.IRON_GOLEM_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 270 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 271 */     if (!$$2.is(Items.IRON_INGOT)) {
/* 272 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 275 */     float $$3 = getHealth();
/* 276 */     heal(25.0F);
/* 277 */     if (getHealth() == $$3) {
/* 278 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 281 */     float $$4 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
/* 282 */     playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, $$4);
/*     */     
/* 284 */     if (!($$0.getAbilities()).instabuild) {
/* 285 */       $$2.shrink(1);
/*     */     }
/* 287 */     return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 292 */     playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public int getOfferFlowerTick() {
/* 296 */     return this.offerFlowerTick;
/*     */   }
/*     */   
/*     */   public boolean isPlayerCreated() {
/* 300 */     return ((((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setPlayerCreated(boolean $$0) {
/* 304 */     byte $$1 = ((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue();
/* 305 */     if ($$0) {
/* 306 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)($$1 | 0x1)));
/*     */     } else {
/* 308 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)($$1 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void die(DamageSource $$0) {
/* 315 */     super.die($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 321 */     BlockPos $$1 = blockPosition();
/* 322 */     BlockPos $$2 = $$1.below();
/* 323 */     BlockState $$3 = $$0.getBlockState($$2);
/* 324 */     if ($$3.entityCanStandOn((BlockGetter)$$0, $$2, (Entity)this)) {
/* 325 */       for (int $$4 = 1; $$4 < 3; $$4++) {
/* 326 */         BlockPos $$5 = $$1.above($$4);
/* 327 */         BlockState $$6 = $$0.getBlockState($$5);
/* 328 */         if (!NaturalSpawner.isValidEmptySpawnBlock((BlockGetter)$$0, $$5, $$6, $$6.getFluidState(), EntityType.IRON_GOLEM)) {
/* 329 */           return false;
/*     */         }
/*     */       } 
/* 332 */       return (NaturalSpawner.isValidEmptySpawnBlock((BlockGetter)$$0, $$1, $$0.getBlockState($$1), Fluids.EMPTY.defaultFluidState(), EntityType.IRON_GOLEM) && $$0
/* 333 */         .isUnobstructed((Entity)this));
/*     */     } 
/* 335 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 340 */     return new Vec3(0.0D, (0.875F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */   
/*     */   public enum Crackiness {
/* 344 */     NONE(1.0F),
/* 345 */     LOW(0.75F),
/* 346 */     MEDIUM(0.5F),
/* 347 */     HIGH(0.25F); private static final List<Crackiness> BY_DAMAGE;
/*     */     
/*     */     static {
/* 350 */       BY_DAMAGE = (List<Crackiness>)Stream.<Crackiness>of(values()).sorted(Comparator.comparingDouble($$0 -> $$0.fraction)).collect(ImmutableList.toImmutableList());
/*     */     }
/*     */     private final float fraction;
/*     */     
/*     */     Crackiness(float $$0) {
/* 355 */       this.fraction = $$0;
/*     */     }
/*     */     
/*     */     public static Crackiness byFraction(float $$0) {
/* 359 */       for (Crackiness $$1 : BY_DAMAGE) {
/* 360 */         if ($$0 < $$1.fraction) {
/* 361 */           return $$1;
/*     */         }
/*     */       } 
/*     */       
/* 365 */       return NONE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\IronGolem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */