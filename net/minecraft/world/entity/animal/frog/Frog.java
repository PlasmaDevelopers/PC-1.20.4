/*     */ package net.minecraft.world.entity.animal.frog;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalInt;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.AnimationState;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.animal.FrogVariant;
/*     */ import net.minecraft.world.entity.monster.Slime;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.PathFinder;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Frog extends Animal implements VariantHolder<FrogVariant> {
/*  72 */   public static final Ingredient TEMPTATION_ITEM = Ingredient.of(new ItemLike[] { (ItemLike)Items.SLIME_BALL });
/*     */   
/*  74 */   protected static final ImmutableList<SensorType<? extends Sensor<? super Frog>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.FROG_ATTACKABLES, SensorType.FROG_TEMPTATIONS, SensorType.IS_IN_WATER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.BREED_TARGET, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, (Object[])new MemoryModuleType[] { MemoryModuleType.IS_TEMPTED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.IS_IN_WATER, MemoryModuleType.IS_PREGNANT, MemoryModuleType.IS_PANICKING, MemoryModuleType.UNREACHABLE_TONGUE_TARGETS });
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
/* 105 */   private static final EntityDataAccessor<FrogVariant> DATA_VARIANT_ID = SynchedEntityData.defineId(Frog.class, EntityDataSerializers.FROG_VARIANT);
/* 106 */   private static final EntityDataAccessor<OptionalInt> DATA_TONGUE_TARGET_ID = SynchedEntityData.defineId(Frog.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
/*     */   
/*     */   private static final int FROG_FALL_DAMAGE_REDUCTION = 5;
/*     */   public static final String VARIANT_KEY = "variant";
/* 110 */   public final AnimationState jumpAnimationState = new AnimationState();
/* 111 */   public final AnimationState croakAnimationState = new AnimationState();
/* 112 */   public final AnimationState tongueAnimationState = new AnimationState();
/* 113 */   public final AnimationState swimIdleAnimationState = new AnimationState();
/*     */   
/*     */   public Frog(EntityType<? extends Animal> $$0, Level $$1) {
/* 116 */     super($$0, $$1);
/* 117 */     this.lookControl = new FrogLookControl((Mob)this);
/*     */     
/* 119 */     setPathfindingMalus(BlockPathTypes.WATER, 4.0F);
/* 120 */     setPathfindingMalus(BlockPathTypes.TRAPDOOR, -1.0F);
/* 121 */     this.moveControl = (MoveControl)new SmoothSwimmingMoveControl((Mob)this, 85, 10, 0.02F, 0.1F, true);
/* 122 */     setMaxUpStep(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Frog> brainProvider() {
/* 127 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 132 */     return FrogAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Frog> getBrain() {
/* 138 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 143 */     super.defineSynchedData();
/* 144 */     this.entityData.define(DATA_VARIANT_ID, FrogVariant.TEMPERATE);
/* 145 */     this.entityData.define(DATA_TONGUE_TARGET_ID, OptionalInt.empty());
/*     */   }
/*     */   
/*     */   public void eraseTongueTarget() {
/* 149 */     this.entityData.set(DATA_TONGUE_TARGET_ID, OptionalInt.empty());
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Entity> getTongueTarget() {
/* 154 */     Objects.requireNonNull(level()); return ((OptionalInt)this.entityData.get(DATA_TONGUE_TARGET_ID)).stream().<Entity>mapToObj(level()::getEntity)
/* 155 */       .filter(Objects::nonNull)
/* 156 */       .findFirst();
/*     */   }
/*     */   
/*     */   public void setTongueTarget(Entity $$0) {
/* 160 */     this.entityData.set(DATA_TONGUE_TARGET_ID, OptionalInt.of($$0.getId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeadRotSpeed() {
/* 165 */     return 35;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 170 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public FrogVariant getVariant() {
/* 175 */     return (FrogVariant)this.entityData.get(DATA_VARIANT_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(FrogVariant $$0) {
/* 180 */     this.entityData.set(DATA_VARIANT_ID, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 185 */     super.addAdditionalSaveData($$0);
/* 186 */     $$0.putString("variant", BuiltInRegistries.FROG_VARIANT.getKey(getVariant()).toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 191 */     super.readAdditionalSaveData($$0);
/* 192 */     FrogVariant $$1 = (FrogVariant)BuiltInRegistries.FROG_VARIANT.get(ResourceLocation.tryParse($$0.getString("variant")));
/* 193 */     if ($$1 != null) {
/* 194 */       setVariant($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 200 */     level().getProfiler().push("frogBrain");
/* 201 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 202 */     level().getProfiler().pop();
/*     */     
/* 204 */     level().getProfiler().push("frogActivityUpdate");
/* 205 */     FrogAi.updateActivity(this);
/* 206 */     level().getProfiler().pop();
/*     */     
/* 208 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 213 */     if (level().isClientSide()) {
/* 214 */       this.swimIdleAnimationState.animateWhen((isInWaterOrBubble() && !this.walkAnimation.isMoving()), this.tickCount);
/*     */     }
/*     */     
/* 217 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 222 */     if (DATA_POSE.equals($$0)) {
/* 223 */       Pose $$1 = getPose();
/*     */       
/* 225 */       if ($$1 == Pose.LONG_JUMPING) {
/* 226 */         this.jumpAnimationState.start(this.tickCount);
/*     */       } else {
/* 228 */         this.jumpAnimationState.stop();
/*     */       } 
/*     */       
/* 231 */       if ($$1 == Pose.CROAKING) {
/* 232 */         this.croakAnimationState.start(this.tickCount);
/*     */       } else {
/* 234 */         this.croakAnimationState.stop();
/*     */       } 
/*     */       
/* 237 */       if ($$1 == Pose.USING_TONGUE) {
/* 238 */         this.tongueAnimationState.start(this.tickCount);
/*     */       } else {
/* 240 */         this.tongueAnimationState.stop();
/*     */       } 
/*     */     } 
/* 243 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateWalkAnimation(float $$0) {
/*     */     float $$2;
/* 249 */     if (this.jumpAnimationState.isStarted()) {
/* 250 */       float $$1 = 0.0F;
/*     */     } else {
/* 252 */       $$2 = Math.min($$0 * 25.0F, 1.0F);
/*     */     } 
/* 254 */     this.walkAnimation.update($$2, 0.4F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 260 */     Frog $$2 = (Frog)EntityType.FROG.create((Level)$$0);
/* 261 */     if ($$2 != null) {
/* 262 */       FrogAi.initMemories($$2, $$0.getRandom());
/*     */     }
/*     */     
/* 265 */     return (AgeableMob)$$2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBaby() {
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaby(boolean $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnChildFromBreeding(ServerLevel $$0, Animal $$1) {
/* 282 */     finalizeSpawnChildFromBreeding($$0, $$1, null);
/*     */     
/* 284 */     getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 290 */     Holder<Biome> $$5 = $$0.getBiome(blockPosition());
/* 291 */     if ($$5.is(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)) {
/* 292 */       setVariant(FrogVariant.COLD);
/* 293 */     } else if ($$5.is(BiomeTags.SPAWNS_WARM_VARIANT_FROGS)) {
/* 294 */       setVariant(FrogVariant.WARM);
/*     */     } else {
/* 296 */       setVariant(FrogVariant.TEMPERATE);
/*     */     } 
/*     */     
/* 299 */     FrogAi.initMemories(this, $$0.getRandom());
/*     */     
/* 301 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private class FrogLookControl extends LookControl {
/*     */     FrogLookControl(Mob $$0) {
/* 306 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean resetXRotOnTick() {
/* 311 */       return Frog.this.getTongueTarget().isEmpty();
/*     */     }
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 316 */     return Mob.createMobAttributes()
/* 317 */       .add(Attributes.MOVEMENT_SPEED, 1.0D)
/* 318 */       .add(Attributes.MAX_HEALTH, 10.0D)
/* 319 */       .add(Attributes.ATTACK_DAMAGE, 10.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 325 */     return SoundEvents.FROG_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 331 */     return SoundEvents.FROG_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 337 */     return SoundEvents.FROG_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 342 */     playSound(SoundEvents.FROG_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPushedByFluid() {
/* 347 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 352 */     super.sendDebugPackets();
/* 353 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int calculateFallDamage(float $$0, float $$1) {
/* 358 */     return super.calculateFallDamage($$0, $$1) - 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 363 */     if (isControlledByLocalInstance() && isInWater()) {
/* 364 */       moveRelative(getSpeed(), $$0);
/* 365 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 367 */       setDeltaMovement(getDeltaMovement().scale(0.9D));
/*     */     } else {
/* 369 */       super.travel($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canEat(LivingEntity $$0) {
/* 375 */     if ($$0 instanceof Slime) { Slime $$1 = (Slime)$$0; if ($$1.getSize() != 1)
/* 376 */         return false;  }
/*     */     
/* 378 */     return $$0.getType().is(EntityTypeTags.FROG_FOOD);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 383 */     return (PathNavigation)new FrogPathNavigation(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 388 */     return new Vector3f(0.0F, $$1.height - 0.125F * $$2, -0.25F * $$2);
/*     */   }
/*     */   
/*     */   private static class FrogPathNavigation extends AmphibiousPathNavigation {
/*     */     FrogPathNavigation(Frog $$0, Level $$1) {
/* 393 */       super((Mob)$$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canCutCorner(BlockPathTypes $$0) {
/* 398 */       return ($$0 != BlockPathTypes.WATER_BORDER && super.canCutCorner($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     protected PathFinder createPathFinder(int $$0) {
/* 403 */       this.nodeEvaluator = (NodeEvaluator)new Frog.FrogNodeEvaluator(true);
/* 404 */       this.nodeEvaluator.setCanPassDoors(true);
/* 405 */       return new PathFinder(this.nodeEvaluator, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FrogNodeEvaluator extends AmphibiousNodeEvaluator {
/* 410 */     private final BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();
/*     */     
/*     */     public FrogNodeEvaluator(boolean $$0) {
/* 413 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Node getStart() {
/* 418 */       if (!this.mob.isInWater()) {
/* 419 */         return super.getStart();
/*     */       }
/* 421 */       return getStartNode(new BlockPos(Mth.floor((this.mob.getBoundingBox()).minX), Mth.floor((this.mob.getBoundingBox()).minY), Mth.floor((this.mob.getBoundingBox()).minZ)));
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3) {
/* 426 */       this.belowPos.set($$1, $$2 - 1, $$3);
/*     */       
/* 428 */       BlockState $$4 = $$0.getBlockState((BlockPos)this.belowPos);
/* 429 */       if ($$4.is(BlockTags.FROG_PREFER_JUMP_TO)) {
/* 430 */         return BlockPathTypes.OPEN;
/*     */       }
/*     */       
/* 433 */       return super.getBlockPathType($$0, $$1, $$2, $$3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 439 */     return TEMPTATION_ITEM.test($$0);
/*     */   }
/*     */   
/*     */   public static boolean checkFrogSpawnRules(EntityType<? extends Animal> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 443 */     return ($$1.getBlockState($$3.below()).is(BlockTags.FROGS_SPAWNABLE_ON) && 
/* 444 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\frog\Frog.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */