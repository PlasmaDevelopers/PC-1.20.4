/*     */ package net.minecraft.world.entity.monster;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.ItemBasedSteering;
/*     */ import net.minecraft.world.entity.ItemSteerable;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.Saddleable;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.vehicle.DismountHelper;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LiquidBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.level.pathfinder.PathFinder;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Strider extends Animal implements ItemSteerable, Saddleable {
/*  78 */   private static final UUID SUFFOCATING_MODIFIER_UUID = UUID.fromString("9e362924-01de-4ddd-a2b2-d0f7a405a174");
/*  79 */   private static final AttributeModifier SUFFOCATING_MODIFIER = new AttributeModifier(SUFFOCATING_MODIFIER_UUID, "Strider suffocating modifier", -0.3400000035762787D, AttributeModifier.Operation.MULTIPLY_BASE);
/*     */   
/*     */   private static final float SUFFOCATE_STEERING_MODIFIER = 0.35F;
/*     */   private static final float STEERING_MODIFIER = 0.55F;
/*  83 */   private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemLike[] { (ItemLike)Items.WARPED_FUNGUS });
/*  84 */   private static final Ingredient TEMPT_ITEMS = Ingredient.of(new ItemLike[] { (ItemLike)Items.WARPED_FUNGUS, (ItemLike)Items.WARPED_FUNGUS_ON_A_STICK });
/*  85 */   private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(Strider.class, EntityDataSerializers.INT);
/*  86 */   private static final EntityDataAccessor<Boolean> DATA_SUFFOCATING = SynchedEntityData.defineId(Strider.class, EntityDataSerializers.BOOLEAN);
/*  87 */   private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(Strider.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private final ItemBasedSteering steering;
/*     */   
/*     */   @Nullable
/*     */   private TemptGoal temptGoal;
/*     */   
/*     */   public Strider(EntityType<? extends Strider> $$0, Level $$1) {
/*  95 */     super($$0, $$1);
/*  96 */     this.steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);
/*     */     
/*  98 */     this.blocksBuilding = true;
/*     */     
/* 100 */     setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
/* 101 */     setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
/* 102 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
/* 103 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
/*     */   }
/*     */   
/*     */   public static boolean checkStriderSpawnRules(EntityType<Strider> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 107 */     BlockPos.MutableBlockPos $$5 = $$3.mutable();
/*     */     do {
/* 109 */       $$5.move(Direction.UP);
/* 110 */     } while ($$1.getFluidState((BlockPos)$$5).is(FluidTags.LAVA));
/*     */     
/* 112 */     return $$1.getBlockState((BlockPos)$$5).isAir();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 117 */     if (DATA_BOOST_TIME.equals($$0) && (level()).isClientSide) {
/* 118 */       this.steering.onSynced();
/*     */     }
/* 120 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 125 */     super.defineSynchedData();
/* 126 */     this.entityData.define(DATA_BOOST_TIME, Integer.valueOf(0));
/* 127 */     this.entityData.define(DATA_SUFFOCATING, Boolean.valueOf(false));
/* 128 */     this.entityData.define(DATA_SADDLE_ID, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 133 */     super.addAdditionalSaveData($$0);
/* 134 */     this.steering.addAdditionalSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 139 */     super.readAdditionalSaveData($$0);
/* 140 */     this.steering.readAdditionalSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSaddled() {
/* 145 */     return this.steering.hasSaddle();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSaddleable() {
/* 150 */     return (isAlive() && !isBaby());
/*     */   }
/*     */ 
/*     */   
/*     */   public void equipSaddle(@Nullable SoundSource $$0) {
/* 155 */     this.steering.setSaddle(true);
/* 156 */     if ($$0 != null) {
/* 157 */       level().playSound(null, (Entity)this, SoundEvents.STRIDER_SADDLE, $$0, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 163 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 1.65D));
/* 164 */     this.goalSelector.addGoal(2, (Goal)new BreedGoal(this, 1.0D));
/* 165 */     this.temptGoal = new TemptGoal((PathfinderMob)this, 1.4D, TEMPT_ITEMS, false);
/* 166 */     this.goalSelector.addGoal(3, (Goal)this.temptGoal);
/* 167 */     this.goalSelector.addGoal(4, (Goal)new StriderGoToLavaGoal(this, 1.0D));
/* 168 */     this.goalSelector.addGoal(5, (Goal)new FollowParentGoal(this, 1.0D));
/* 169 */     this.goalSelector.addGoal(7, (Goal)new RandomStrollGoal((PathfinderMob)this, 1.0D, 60));
/* 170 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/* 171 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/* 172 */     this.goalSelector.addGoal(9, (Goal)new LookAtPlayerGoal((Mob)this, Strider.class, 8.0F));
/*     */   }
/*     */   
/*     */   public void setSuffocating(boolean $$0) {
/* 176 */     this.entityData.set(DATA_SUFFOCATING, Boolean.valueOf($$0));
/*     */     
/* 178 */     AttributeInstance $$1 = getAttribute(Attributes.MOVEMENT_SPEED);
/* 179 */     if ($$1 != null) {
/* 180 */       $$1.removeModifier(SUFFOCATING_MODIFIER_UUID);
/* 181 */       if ($$0) {
/* 182 */         $$1.addTransientModifier(SUFFOCATING_MODIFIER);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSuffocating() {
/* 188 */     return ((Boolean)this.entityData.get(DATA_SUFFOCATING)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canStandOnFluid(FluidState $$0) {
/* 193 */     return $$0.is(FluidTags.LAVA);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 198 */     float $$3 = Math.min(0.25F, this.walkAnimation.speed());
/* 199 */     float $$4 = this.walkAnimation.position();
/*     */     
/* 201 */     float $$5 = 0.12F * Mth.cos($$4 * 1.5F) * 2.0F * $$3;
/* 202 */     return new Vector3f(0.0F, $$1.height + $$5 * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 207 */     return $$0.isUnobstructed((Entity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getControllingPassenger() {
/* 213 */     if (isSaddled()) { Entity entity = getFirstPassenger(); if (entity instanceof Player) { Player $$0 = (Player)entity; if ($$0.isHolding(Items.WARPED_FUNGUS_ON_A_STICK))
/* 214 */           return (LivingEntity)$$0;  }
/*     */        }
/* 216 */      return super.getControllingPassenger();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getDismountLocationForPassenger(LivingEntity $$0) {
/* 226 */     Vec3[] $$1 = { getCollisionHorizontalEscapeVector(getBbWidth(), $$0.getBbWidth(), $$0.getYRot()), getCollisionHorizontalEscapeVector(getBbWidth(), $$0.getBbWidth(), $$0.getYRot() - 22.5F), getCollisionHorizontalEscapeVector(getBbWidth(), $$0.getBbWidth(), $$0.getYRot() + 22.5F), getCollisionHorizontalEscapeVector(getBbWidth(), $$0.getBbWidth(), $$0.getYRot() - 45.0F), getCollisionHorizontalEscapeVector(getBbWidth(), $$0.getBbWidth(), $$0.getYRot() + 45.0F) };
/*     */ 
/*     */     
/* 229 */     Set<BlockPos> $$2 = Sets.newLinkedHashSet();
/* 230 */     double $$3 = (getBoundingBox()).maxY;
/* 231 */     double $$4 = (getBoundingBox()).minY - 0.5D;
/*     */     
/* 233 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 234 */     for (Vec3 $$6 : $$1) {
/* 235 */       $$5.set(getX() + $$6.x, $$3, getZ() + $$6.z);
/*     */       
/*     */       double $$7;
/* 238 */       for ($$7 = $$3; $$7 > $$4; $$7--) {
/* 239 */         $$2.add($$5.immutable());
/* 240 */         $$5.move(Direction.DOWN);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     for (BlockPos $$8 : $$2) {
/* 245 */       if (level().getFluidState($$8).is(FluidTags.LAVA)) {
/*     */         continue;
/*     */       }
/*     */       
/* 249 */       double $$9 = level().getBlockFloorHeight($$8);
/* 250 */       if (DismountHelper.isBlockFloorValid($$9)) {
/* 251 */         Vec3 $$10 = Vec3.upFromBottomCenterOf((Vec3i)$$8, $$9);
/*     */         
/* 253 */         for (UnmodifiableIterator<Pose> unmodifiableIterator = $$0.getDismountPoses().iterator(); unmodifiableIterator.hasNext(); ) { Pose $$11 = unmodifiableIterator.next();
/* 254 */           AABB $$12 = $$0.getLocalBoundsForPose($$11);
/*     */           
/* 256 */           if (DismountHelper.canDismountTo((CollisionGetter)level(), $$0, $$12.move($$10))) {
/* 257 */             $$0.setPose($$11);
/* 258 */             return $$10;
/*     */           }  }
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     return new Vec3(getX(), (getBoundingBox()).maxY, getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tickRidden(Player $$0, Vec3 $$1) {
/* 269 */     setRot($$0.getYRot(), $$0.getXRot() * 0.5F);
/* 270 */     this.yRotO = this.yBodyRot = this.yHeadRot = getYRot();
/* 271 */     this.steering.tickBoost();
/* 272 */     super.tickRidden($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getRiddenInput(Player $$0, Vec3 $$1) {
/* 277 */     return new Vec3(0.0D, 0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getRiddenSpeed(Player $$0) {
/* 282 */     return (float)(getAttributeValue(Attributes.MOVEMENT_SPEED) * (isSuffocating() ? 0.35F : 0.55F) * this.steering.boostFactor());
/*     */   }
/*     */ 
/*     */   
/*     */   protected float nextStep() {
/* 287 */     return this.moveDist + 0.6F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 292 */     playSound(isInLava() ? SoundEvents.STRIDER_STEP_LAVA : SoundEvents.STRIDER_STEP, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean boost() {
/* 297 */     return this.steering.boost(getRandom());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {
/* 302 */     checkInsideBlocks();
/*     */     
/* 304 */     if (isInLava()) {
/* 305 */       resetFallDistance();
/*     */       
/*     */       return;
/*     */     } 
/* 309 */     super.checkFallDamage($$0, $$1, $$2, $$3);
/*     */   }
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
/*     */   public void tick() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual isBeingTempted : ()Z
/*     */     //   4: ifeq -> 37
/*     */     //   7: aload_0
/*     */     //   8: getfield random : Lnet/minecraft/util/RandomSource;
/*     */     //   11: sipush #140
/*     */     //   14: invokeinterface nextInt : (I)I
/*     */     //   19: ifne -> 37
/*     */     //   22: aload_0
/*     */     //   23: getstatic net/minecraft/sounds/SoundEvents.STRIDER_HAPPY : Lnet/minecraft/sounds/SoundEvent;
/*     */     //   26: fconst_1
/*     */     //   27: aload_0
/*     */     //   28: invokevirtual getVoicePitch : ()F
/*     */     //   31: invokevirtual playSound : (Lnet/minecraft/sounds/SoundEvent;FF)V
/*     */     //   34: goto -> 70
/*     */     //   37: aload_0
/*     */     //   38: invokevirtual isPanicking : ()Z
/*     */     //   41: ifeq -> 70
/*     */     //   44: aload_0
/*     */     //   45: getfield random : Lnet/minecraft/util/RandomSource;
/*     */     //   48: bipush #60
/*     */     //   50: invokeinterface nextInt : (I)I
/*     */     //   55: ifne -> 70
/*     */     //   58: aload_0
/*     */     //   59: getstatic net/minecraft/sounds/SoundEvents.STRIDER_RETREAT : Lnet/minecraft/sounds/SoundEvent;
/*     */     //   62: fconst_1
/*     */     //   63: aload_0
/*     */     //   64: invokevirtual getVoicePitch : ()F
/*     */     //   67: invokevirtual playSound : (Lnet/minecraft/sounds/SoundEvent;FF)V
/*     */     //   70: aload_0
/*     */     //   71: invokevirtual isNoAi : ()Z
/*     */     //   74: ifne -> 186
/*     */     //   77: aload_0
/*     */     //   78: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*     */     //   81: aload_0
/*     */     //   82: invokevirtual blockPosition : ()Lnet/minecraft/core/BlockPos;
/*     */     //   85: invokevirtual getBlockState : (Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;
/*     */     //   88: astore_1
/*     */     //   89: aload_0
/*     */     //   90: invokevirtual getBlockStateOnLegacy : ()Lnet/minecraft/world/level/block/state/BlockState;
/*     */     //   93: astore_2
/*     */     //   94: aload_1
/*     */     //   95: getstatic net/minecraft/tags/BlockTags.STRIDER_WARM_BLOCKS : Lnet/minecraft/tags/TagKey;
/*     */     //   98: invokevirtual is : (Lnet/minecraft/tags/TagKey;)Z
/*     */     //   101: ifne -> 126
/*     */     //   104: aload_2
/*     */     //   105: getstatic net/minecraft/tags/BlockTags.STRIDER_WARM_BLOCKS : Lnet/minecraft/tags/TagKey;
/*     */     //   108: invokevirtual is : (Lnet/minecraft/tags/TagKey;)Z
/*     */     //   111: ifne -> 126
/*     */     //   114: aload_0
/*     */     //   115: getstatic net/minecraft/tags/FluidTags.LAVA : Lnet/minecraft/tags/TagKey;
/*     */     //   118: invokevirtual getFluidHeight : (Lnet/minecraft/tags/TagKey;)D
/*     */     //   121: dconst_0
/*     */     //   122: dcmpl
/*     */     //   123: ifle -> 130
/*     */     //   126: iconst_1
/*     */     //   127: goto -> 131
/*     */     //   130: iconst_0
/*     */     //   131: istore_3
/*     */     //   132: aload_0
/*     */     //   133: invokevirtual getVehicle : ()Lnet/minecraft/world/entity/Entity;
/*     */     //   136: astore #6
/*     */     //   138: aload #6
/*     */     //   140: instanceof net/minecraft/world/entity/monster/Strider
/*     */     //   143: ifeq -> 165
/*     */     //   146: aload #6
/*     */     //   148: checkcast net/minecraft/world/entity/monster/Strider
/*     */     //   151: astore #5
/*     */     //   153: aload #5
/*     */     //   155: invokevirtual isSuffocating : ()Z
/*     */     //   158: ifeq -> 165
/*     */     //   161: iconst_1
/*     */     //   162: goto -> 166
/*     */     //   165: iconst_0
/*     */     //   166: istore #4
/*     */     //   168: aload_0
/*     */     //   169: iload_3
/*     */     //   170: ifeq -> 178
/*     */     //   173: iload #4
/*     */     //   175: ifeq -> 182
/*     */     //   178: iconst_1
/*     */     //   179: goto -> 183
/*     */     //   182: iconst_0
/*     */     //   183: invokevirtual setSuffocating : (Z)V
/*     */     //   186: aload_0
/*     */     //   187: invokespecial tick : ()V
/*     */     //   190: aload_0
/*     */     //   191: invokevirtual floatStrider : ()V
/*     */     //   194: aload_0
/*     */     //   195: invokevirtual checkInsideBlocks : ()V
/*     */     //   198: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #314	-> 0
/*     */     //   #315	-> 22
/*     */     //   #316	-> 37
/*     */     //   #317	-> 58
/*     */     //   #320	-> 70
/*     */     //   #321	-> 77
/*     */     //   #322	-> 89
/*     */     //   #324	-> 94
/*     */     //   #325	-> 132
/*     */     //   #328	-> 168
/*     */     //   #331	-> 186
/*     */     //   #333	-> 190
/*     */     //   #334	-> 194
/*     */     //   #335	-> 198
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	199	0	this	Lnet/minecraft/world/entity/monster/Strider;
/*     */     //   89	97	1	$$0	Lnet/minecraft/world/level/block/state/BlockState;
/*     */     //   94	92	2	$$1	Lnet/minecraft/world/level/block/state/BlockState;
/*     */     //   132	54	3	$$2	Z
/*     */     //   153	12	5	$$3	Lnet/minecraft/world/entity/monster/Strider;
/*     */     //   168	18	4	$$4	Z
/*     */   }
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
/*     */   private boolean isBeingTempted() {
/* 338 */     return (this.temptGoal != null && this.temptGoal.isRunning());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldPassengersInheritMalus() {
/* 343 */     return true;
/*     */   }
/*     */   
/*     */   private void floatStrider() {
/* 347 */     if (isInLava()) {
/* 348 */       CollisionContext $$0 = CollisionContext.of((Entity)this);
/* 349 */       if (!$$0.isAbove(LiquidBlock.STABLE_SHAPE, blockPosition(), true) || level().getFluidState(blockPosition().above()).is(FluidTags.LAVA)) {
/* 350 */         setDeltaMovement(getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
/*     */       } else {
/* 352 */         setOnGround(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 358 */     return Mob.createMobAttributes()
/* 359 */       .add(Attributes.MOVEMENT_SPEED, 0.17499999701976776D)
/* 360 */       .add(Attributes.FOLLOW_RANGE, 16.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 365 */     if (isPanicking() || isBeingTempted()) {
/* 366 */       return null;
/*     */     }
/* 368 */     return SoundEvents.STRIDER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 373 */     return SoundEvents.STRIDER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 378 */     return SoundEvents.STRIDER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canAddPassenger(Entity $$0) {
/* 383 */     return (!isVehicle() && !isEyeInFluid(FluidTags.LAVA));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSensitiveToWater() {
/* 388 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnFire() {
/* 393 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 398 */     return (PathNavigation)new StriderPathNavigation(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 403 */     if ($$1.getBlockState($$0).getFluidState().is(FluidTags.LAVA)) {
/* 404 */       return 10.0F;
/*     */     }
/*     */ 
/*     */     
/* 408 */     return isInLava() ? Float.NEGATIVE_INFINITY : 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Strider getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 414 */     return (Strider)EntityType.STRIDER.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 419 */     return FOOD_ITEMS.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropEquipment() {
/* 424 */     super.dropEquipment();
/* 425 */     if (isSaddled()) {
/* 426 */       spawnAtLocation((ItemLike)Items.SADDLE);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 432 */     boolean $$2 = isFood($$0.getItemInHand($$1));
/*     */     
/* 434 */     if (!$$2 && isSaddled() && !isVehicle() && !$$0.isSecondaryUseActive()) {
/* 435 */       if (!(level()).isClientSide) {
/* 436 */         $$0.startRiding((Entity)this);
/*     */       }
/* 438 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 441 */     InteractionResult $$3 = super.mobInteract($$0, $$1);
/* 442 */     if (!$$3.consumesAction()) {
/* 443 */       ItemStack $$4 = $$0.getItemInHand($$1);
/* 444 */       if ($$4.is(Items.SADDLE)) {
/* 445 */         return $$4.interactLivingEntity($$0, (LivingEntity)this, $$1);
/*     */       }
/* 447 */       return InteractionResult.PASS;
/* 448 */     }  if ($$2 && !isSilent()) {
/* 449 */       level().playSound(null, getX(), getY(), getZ(), SoundEvents.STRIDER_EAT, getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*     */     }
/*     */     
/* 452 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 457 */     return new Vec3(0.0D, (0.6F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/* 464 */     if (isBaby()) {
/* 465 */       return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     }
/*     */     
/* 468 */     RandomSource $$5 = $$0.getRandom();
/* 469 */     if ($$5.nextInt(30) == 0) {
/* 470 */       Mob $$6 = (Mob)EntityType.ZOMBIFIED_PIGLIN.create((Level)$$0.getLevel());
/* 471 */       if ($$6 != null) {
/* 472 */         $$3 = spawnJockey($$0, $$1, $$6, new Zombie.ZombieGroupData(Zombie.getSpawnAsBabyOdds($$5), false));
/*     */         
/* 474 */         $$6.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.WARPED_FUNGUS_ON_A_STICK));
/* 475 */         equipSaddle((SoundSource)null);
/*     */       } 
/* 477 */     } else if ($$5.nextInt(10) == 0) {
/* 478 */       AgeableMob $$7 = (AgeableMob)EntityType.STRIDER.create((Level)$$0.getLevel());
/* 479 */       if ($$7 != null) {
/* 480 */         $$7.setAge(-24000);
/*     */         
/* 482 */         $$3 = spawnJockey($$0, $$1, (Mob)$$7, (SpawnGroupData)null);
/*     */       } 
/*     */     } else {
/* 485 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(0.5F);
/*     */     } 
/*     */     
/* 488 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*     */   }
/*     */   
/*     */   private SpawnGroupData spawnJockey(ServerLevelAccessor $$0, DifficultyInstance $$1, Mob $$2, @Nullable SpawnGroupData $$3) {
/* 492 */     $$2.moveTo(getX(), getY(), getZ(), getYRot(), 0.0F);
/* 493 */     $$2.finalizeSpawn($$0, $$1, MobSpawnType.JOCKEY, $$3, null);
/* 494 */     $$2.startRiding((Entity)this, true);
/*     */     
/* 496 */     return (SpawnGroupData)new AgeableMob.AgeableMobGroupData(0.0F);
/*     */   }
/*     */   
/*     */   private static class StriderPathNavigation extends GroundPathNavigation {
/*     */     StriderPathNavigation(Strider $$0, Level $$1) {
/* 501 */       super((Mob)$$0, $$1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PathFinder createPathFinder(int $$0) {
/* 507 */       this.nodeEvaluator = (NodeEvaluator)new WalkNodeEvaluator();
/* 508 */       this.nodeEvaluator.setCanPassDoors(true);
/* 509 */       return new PathFinder(this.nodeEvaluator, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean hasValidPathType(BlockPathTypes $$0) {
/* 514 */       if ($$0 == BlockPathTypes.LAVA || $$0 == BlockPathTypes.DAMAGE_FIRE || $$0 == BlockPathTypes.DANGER_FIRE) {
/* 515 */         return true;
/*     */       }
/*     */       
/* 518 */       return super.hasValidPathType($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isStableDestination(BlockPos $$0) {
/* 523 */       return (this.level.getBlockState($$0).is(Blocks.LAVA) || super.isStableDestination($$0));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class StriderGoToLavaGoal extends MoveToBlockGoal {
/*     */     private final Strider strider;
/*     */     
/*     */     StriderGoToLavaGoal(Strider $$0, double $$1) {
/* 531 */       super((PathfinderMob)$$0, $$1, 8, 2);
/* 532 */       this.strider = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getMoveToTarget() {
/* 537 */       return this.blockPos;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 542 */       return (!this.strider.isInLava() && isValidTarget((LevelReader)this.strider.level(), this.blockPos));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 547 */       return (!this.strider.isInLava() && super.canUse());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldRecalculatePath() {
/* 552 */       return (this.tryTicks % 20 == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 557 */       return ($$0.getBlockState($$1).is(Blocks.LAVA) && $$0.getBlockState($$1.above()).isPathfindable((BlockGetter)$$0, $$1, PathComputationType.LAND));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Strider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */