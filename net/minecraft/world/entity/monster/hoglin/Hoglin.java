/*     */ package net.minecraft.world.entity.monster.hoglin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.monster.Enemy;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.Zoglin;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Hoglin
/*     */   extends Animal
/*     */   implements Enemy, HoglinBase
/*     */ {
/*  59 */   private static final EntityDataAccessor<Boolean> DATA_IMMUNE_TO_ZOMBIFICATION = SynchedEntityData.defineId(Hoglin.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private static final float PROBABILITY_OF_SPAWNING_AS_BABY = 0.2F;
/*     */   
/*     */   private static final int MAX_HEALTH = 40;
/*     */   
/*     */   private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.3F;
/*     */   private static final int ATTACK_KNOCKBACK = 1;
/*     */   private static final float KNOCKBACK_RESISTANCE = 0.6F;
/*     */   private static final int ATTACK_DAMAGE = 6;
/*     */   private static final float BABY_ATTACK_DAMAGE = 0.5F;
/*     */   private static final int CONVERSION_TIME = 300;
/*     */   private int attackAnimationRemainingTicks;
/*     */   private int timeInOverworld;
/*     */   private boolean cannotBeHunted;
/*  74 */   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Hoglin>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ADULT, SensorType.HOGLIN_SPECIFIC_SENSOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, (Object[])new MemoryModuleType[] { MemoryModuleType.AVOID_TARGET, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.NEAREST_REPELLENT, MemoryModuleType.PACIFIED, MemoryModuleType.IS_PANICKING });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hoglin(EntityType<? extends Hoglin> $$0, Level $$1) {
/* 104 */     super($$0, $$1);
/* 105 */     this.xpReward = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/* 110 */     return !isLeashed();
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 114 */     return Monster.createMonsterAttributes()
/* 115 */       .add(Attributes.MAX_HEALTH, 40.0D)
/* 116 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 117 */       .add(Attributes.KNOCKBACK_RESISTANCE, 0.6000000238418579D)
/* 118 */       .add(Attributes.ATTACK_KNOCKBACK, 1.0D)
/* 119 */       .add(Attributes.ATTACK_DAMAGE, 6.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 124 */     if (!($$0 instanceof LivingEntity)) {
/* 125 */       return false;
/*     */     }
/* 127 */     this.attackAnimationRemainingTicks = 10;
/* 128 */     level().broadcastEntityEvent((Entity)this, (byte)4);
/*     */     
/* 130 */     playSound(SoundEvents.HOGLIN_ATTACK, 1.0F, getVoicePitch());
/* 131 */     HoglinAi.onHitTarget(this, (LivingEntity)$$0);
/* 132 */     return HoglinBase.hurtAndThrowTarget((LivingEntity)this, (LivingEntity)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void blockedByShield(LivingEntity $$0) {
/* 137 */     if (isAdult()) {
/* 138 */       HoglinBase.throwTarget((LivingEntity)this, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 144 */     boolean $$2 = super.hurt($$0, $$1);
/* 145 */     if ((level()).isClientSide) {
/* 146 */       return false;
/*     */     }
/* 148 */     if ($$2 && $$0.getEntity() instanceof LivingEntity) {
/* 149 */       HoglinAi.wasHurtBy(this, (LivingEntity)$$0.getEntity());
/*     */     }
/* 151 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Hoglin> brainProvider() {
/* 156 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 161 */     return HoglinAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Hoglin> getBrain() {
/* 167 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 172 */     level().getProfiler().push("hoglinBrain");
/* 173 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 174 */     level().getProfiler().pop();
/*     */     
/* 176 */     HoglinAi.updateActivity(this);
/*     */     
/* 178 */     if (isConverting()) {
/* 179 */       this.timeInOverworld++;
/* 180 */       if (this.timeInOverworld > 300) {
/* 181 */         playSoundEvent(SoundEvents.HOGLIN_CONVERTED_TO_ZOMBIFIED);
/* 182 */         finishConversion((ServerLevel)level());
/*     */       } 
/*     */     } else {
/* 185 */       this.timeInOverworld = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 192 */     if (this.attackAnimationRemainingTicks > 0) {
/* 193 */       this.attackAnimationRemainingTicks--;
/*     */     }
/* 195 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void ageBoundaryReached() {
/* 200 */     if (isBaby()) {
/* 201 */       this.xpReward = 3;
/* 202 */       getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.5D);
/*     */     } else {
/* 204 */       this.xpReward = 5;
/* 205 */       getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean checkHoglinSpawnRules(EntityType<Hoglin> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 210 */     return !$$1.getBlockState($$3.below()).is(Blocks.NETHER_WART_BLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 216 */     if ($$0.getRandom().nextFloat() < 0.2F) {
/* 217 */       setBaby(true);
/*     */     }
/*     */     
/* 220 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 225 */     return !isPersistenceRequired();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 230 */     if (HoglinAi.isPosNearNearestRepellent(this, $$0)) {
/* 231 */       return -1.0F;
/*     */     }
/* 233 */     if ($$1.getBlockState($$0.below()).is(Blocks.CRIMSON_NYLIUM))
/*     */     {
/* 235 */       return 10.0F;
/*     */     }
/* 237 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 242 */     return new Vector3f(0.0F, $$1.height + 0.09375F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 247 */     InteractionResult $$2 = super.mobInteract($$0, $$1);
/* 248 */     if ($$2.consumesAction()) {
/* 249 */       setPersistenceRequired();
/*     */     }
/* 251 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 257 */     if ($$0 == 4) {
/*     */       
/* 259 */       this.attackAnimationRemainingTicks = 10;
/* 260 */       playSound(SoundEvents.HOGLIN_ATTACK, 1.0F, getVoicePitch());
/*     */     } else {
/* 262 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAttackAnimationRemainingTicks() {
/* 268 */     return this.attackAnimationRemainingTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDropExperience() {
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExperienceReward() {
/* 278 */     return this.xpReward;
/*     */   }
/*     */   
/*     */   private void finishConversion(ServerLevel $$0) {
/* 282 */     Zoglin $$1 = (Zoglin)convertTo(EntityType.ZOGLIN, true);
/* 283 */     if ($$1 != null) {
/* 284 */       $$1.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 290 */     return $$0.is(Items.CRIMSON_FUNGUS);
/*     */   }
/*     */   
/*     */   public boolean isAdult() {
/* 294 */     return !isBaby();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 299 */     super.defineSynchedData();
/* 300 */     this.entityData.define(DATA_IMMUNE_TO_ZOMBIFICATION, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 305 */     super.addAdditionalSaveData($$0);
/* 306 */     if (isImmuneToZombification()) {
/* 307 */       $$0.putBoolean("IsImmuneToZombification", true);
/*     */     }
/* 309 */     $$0.putInt("TimeInOverworld", this.timeInOverworld);
/* 310 */     if (this.cannotBeHunted) {
/* 311 */       $$0.putBoolean("CannotBeHunted", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 317 */     super.readAdditionalSaveData($$0);
/* 318 */     setImmuneToZombification($$0.getBoolean("IsImmuneToZombification"));
/* 319 */     this.timeInOverworld = $$0.getInt("TimeInOverworld");
/* 320 */     setCannotBeHunted($$0.getBoolean("CannotBeHunted"));
/*     */   }
/*     */   
/*     */   public void setImmuneToZombification(boolean $$0) {
/* 324 */     getEntityData().set(DATA_IMMUNE_TO_ZOMBIFICATION, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   private boolean isImmuneToZombification() {
/* 328 */     return ((Boolean)getEntityData().get(DATA_IMMUNE_TO_ZOMBIFICATION)).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isConverting() {
/* 332 */     return (!level().dimensionType().piglinSafe() && !isImmuneToZombification() && !isNoAi());
/*     */   }
/*     */   
/*     */   private void setCannotBeHunted(boolean $$0) {
/* 336 */     this.cannotBeHunted = $$0;
/*     */   }
/*     */   
/*     */   public boolean canBeHunted() {
/* 340 */     return (isAdult() && !this.cannotBeHunted);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 346 */     Hoglin $$2 = (Hoglin)EntityType.HOGLIN.create((Level)$$0);
/* 347 */     if ($$2 != null) {
/* 348 */       $$2.setPersistenceRequired();
/*     */     }
/* 350 */     return (AgeableMob)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFallInLove() {
/* 355 */     return (!HoglinAi.isPacified(this) && super.canFallInLove());
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 360 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 365 */     if ((level()).isClientSide) {
/* 366 */       return null;
/*     */     }
/* 368 */     return HoglinAi.getSoundForCurrentActivity(this).orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 373 */     return SoundEvents.HOGLIN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 378 */     return SoundEvents.HOGLIN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/* 383 */     return SoundEvents.HOSTILE_SWIM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSplashSound() {
/* 388 */     return SoundEvents.HOSTILE_SPLASH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 393 */     playSound(SoundEvents.HOGLIN_STEP, 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void playSoundEvent(SoundEvent $$0) {
/* 397 */     playSound($$0, getSoundVolume(), getVoicePitch());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 402 */     super.sendDebugPackets();
/* 403 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\hoglin\Hoglin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */