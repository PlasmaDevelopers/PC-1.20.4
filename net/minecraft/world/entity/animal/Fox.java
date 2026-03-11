/*      */ package net.minecraft.world.entity.animal;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.particles.ItemParticleOption;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.NbtUtils;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.stats.Stats;
/*      */ import net.minecraft.tags.BiomeTags;
/*      */ import net.minecraft.tags.BlockTags;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.tags.ItemTags;
/*      */ import net.minecraft.util.ByIdMap;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.StringRepresentable;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.entity.AgeableMob;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityDimensions;
/*      */ import net.minecraft.world.entity.EntitySelector;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.EquipmentSlot;
/*      */ import net.minecraft.world.entity.ExperienceOrb;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.MobSpawnType;
/*      */ import net.minecraft.world.entity.PathfinderMob;
/*      */ import net.minecraft.world.entity.Pose;
/*      */ import net.minecraft.world.entity.SpawnGroupData;
/*      */ import net.minecraft.world.entity.TamableAnimal;
/*      */ import net.minecraft.world.entity.VariantHolder;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.ai.control.LookControl;
/*      */ import net.minecraft.world.entity.ai.control.MoveControl;
/*      */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*      */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*      */ import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FleeSunGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*      */ import net.minecraft.world.entity.ai.goal.Goal;
/*      */ import net.minecraft.world.entity.ai.goal.JumpGoal;
/*      */ import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
/*      */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*      */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*      */ import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
/*      */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*      */ import net.minecraft.world.entity.ai.goal.StrollThroughVillageGoal;
/*      */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*      */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*      */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.level.BlockAndTintGetter;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelAccessor;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.ServerLevelAccessor;
/*      */ import net.minecraft.world.level.biome.Biome;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.CaveVines;
/*      */ import net.minecraft.world.level.block.SweetBerryBushBlock;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import org.joml.Vector3f;
/*      */ 
/*      */ public class Fox extends Animal implements VariantHolder<Fox.Type> {
/*  101 */   private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.INT);
/*  102 */   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.BYTE);
/*      */   
/*      */   private static final int FLAG_SITTING = 1;
/*      */   
/*      */   public static final int FLAG_CROUCHING = 4;
/*      */   public static final int FLAG_INTERESTED = 8;
/*      */   public static final int FLAG_POUNCING = 16;
/*      */   private static final int FLAG_SLEEPING = 32;
/*      */   private static final int FLAG_FACEPLANTED = 64;
/*      */   private static final int FLAG_DEFENDING = 128;
/*  112 */   private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.OPTIONAL_UUID);
/*  113 */   private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.OPTIONAL_UUID); static final Predicate<ItemEntity> ALLOWED_ITEMS;
/*      */   static {
/*  115 */     ALLOWED_ITEMS = ($$0 -> (!$$0.hasPickUpDelay() && $$0.isAlive()));
/*      */     
/*  117 */     TRUSTED_TARGET_SELECTOR = ($$0 -> {
/*      */         if ($$0 instanceof LivingEntity) {
/*  119 */           LivingEntity $$1 = (LivingEntity)$$0; return ($$1.getLastHurtMob() != null && $$1.getLastHurtMobTimestamp() < $$1.tickCount + 600);
/*      */         } 
/*      */         
/*      */         return false;
/*      */       });
/*  124 */     STALKABLE_PREY = ($$0 -> ($$0 instanceof Chicken || $$0 instanceof Rabbit));
/*      */     
/*  126 */     AVOID_PLAYERS = ($$0 -> (!$$0.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test($$0)));
/*      */   }
/*      */   
/*      */   private static final Predicate<Entity> TRUSTED_TARGET_SELECTOR;
/*      */   static final Predicate<Entity> STALKABLE_PREY;
/*      */   private static final Predicate<Entity> AVOID_PLAYERS;
/*      */   private static final int MIN_TICKS_BEFORE_EAT = 600;
/*      */   private Goal landTargetGoal;
/*      */   private Goal turtleEggTargetGoal;
/*      */   private Goal fishTargetGoal;
/*      */   private float interestedAngle;
/*      */   private float interestedAngleO;
/*      */   float crouchAmount;
/*      */   float crouchAmountO;
/*      */   private int ticksSinceEaten;
/*      */   
/*      */   public enum Type implements StringRepresentable {
/*  143 */     RED(0, "red"),
/*  144 */     SNOW(1, "snow");
/*      */     
/*  146 */     public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
/*      */     
/*  148 */     private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Type::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*      */     
/*      */     private final int id;
/*      */ 
/*      */     
/*      */     Type(int $$0, String $$1) {
/*  154 */       this.id = $$0;
/*  155 */       this.name = $$1;
/*      */     } private final String name; static {
/*      */     
/*      */     }
/*      */     public String getSerializedName() {
/*  160 */       return this.name;
/*      */     }
/*      */     
/*      */     public int getId() {
/*  164 */       return this.id;
/*      */     }
/*      */     
/*      */     public static Type byName(String $$0) {
/*  168 */       return (Type)CODEC.byName($$0, RED);
/*      */     }
/*      */     
/*      */     public static Type byId(int $$0) {
/*  172 */       return BY_ID.apply($$0);
/*      */     }
/*      */     
/*      */     public static Type byBiome(Holder<Biome> $$0) {
/*  176 */       return $$0.is(BiomeTags.SPAWNS_SNOW_FOXES) ? SNOW : RED;
/*      */     }
/*      */   }
/*      */   
/*      */   public Fox(EntityType<? extends Fox> $$0, Level $$1) {
/*  181 */     super((EntityType)$$0, $$1);
/*      */     
/*  183 */     this.lookControl = new FoxLookControl();
/*  184 */     this.moveControl = new FoxMoveControl();
/*      */     
/*  186 */     setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
/*  187 */     setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
/*      */     
/*  189 */     setCanPickUpLoot(true);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  194 */     super.defineSynchedData();
/*  195 */     this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
/*  196 */     this.entityData.define(DATA_TRUSTED_ID_1, Optional.empty());
/*  197 */     this.entityData.define(DATA_TYPE_ID, Integer.valueOf(0));
/*  198 */     this.entityData.define(DATA_FLAGS_ID, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerGoals() {
/*  203 */     this.landTargetGoal = (Goal)new NearestAttackableTargetGoal((Mob)this, Animal.class, 10, false, false, $$0 -> ($$0 instanceof Chicken || $$0 instanceof Rabbit));
/*  204 */     this.turtleEggTargetGoal = (Goal)new NearestAttackableTargetGoal((Mob)this, Turtle.class, 10, false, false, Turtle.BABY_ON_LAND_SELECTOR);
/*  205 */     this.fishTargetGoal = (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractFish.class, 20, false, false, $$0 -> $$0 instanceof AbstractSchoolingFish);
/*      */     
/*  207 */     this.goalSelector.addGoal(0, (Goal)new FoxFloatGoal());
/*  208 */     this.goalSelector.addGoal(0, (Goal)new ClimbOnTopOfPowderSnowGoal((Mob)this, level()));
/*  209 */     this.goalSelector.addGoal(1, new FaceplantGoal());
/*  210 */     this.goalSelector.addGoal(2, (Goal)new FoxPanicGoal(2.2D));
/*  211 */     this.goalSelector.addGoal(3, (Goal)new FoxBreedGoal(1.0D));
/*  212 */     this.goalSelector.addGoal(4, (Goal)new AvoidEntityGoal((PathfinderMob)this, Player.class, 16.0F, 1.6D, 1.4D, $$0 -> (AVOID_PLAYERS.test($$0) && !trusts($$0.getUUID()) && !isDefending())));
/*  213 */     this.goalSelector.addGoal(4, (Goal)new AvoidEntityGoal((PathfinderMob)this, Wolf.class, 8.0F, 1.6D, 1.4D, $$0 -> (!((Wolf)$$0).isTame() && !isDefending())));
/*  214 */     this.goalSelector.addGoal(4, (Goal)new AvoidEntityGoal((PathfinderMob)this, PolarBear.class, 8.0F, 1.6D, 1.4D, $$0 -> !isDefending()));
/*  215 */     this.goalSelector.addGoal(5, new StalkPreyGoal());
/*  216 */     this.goalSelector.addGoal(6, (Goal)new FoxPounceGoal());
/*  217 */     this.goalSelector.addGoal(6, (Goal)new SeekShelterGoal(1.25D));
/*  218 */     this.goalSelector.addGoal(7, (Goal)new FoxMeleeAttackGoal(1.2000000476837158D, true));
/*  219 */     this.goalSelector.addGoal(7, new SleepGoal());
/*  220 */     this.goalSelector.addGoal(8, (Goal)new FoxFollowParentGoal(this, 1.25D));
/*  221 */     this.goalSelector.addGoal(9, (Goal)new FoxStrollThroughVillageGoal(32, 200));
/*  222 */     this.goalSelector.addGoal(10, (Goal)new FoxEatBerriesGoal(1.2000000476837158D, 12, 1));
/*  223 */     this.goalSelector.addGoal(10, (Goal)new LeapAtTargetGoal((Mob)this, 0.4F));
/*  224 */     this.goalSelector.addGoal(11, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/*  225 */     this.goalSelector.addGoal(11, new FoxSearchForItemsGoal());
/*  226 */     this.goalSelector.addGoal(12, (Goal)new FoxLookAtPlayerGoal((Mob)this, (Class)Player.class, 24.0F));
/*  227 */     this.goalSelector.addGoal(13, new PerchAndSearchGoal());
/*      */     
/*  229 */     this.targetSelector.addGoal(3, (Goal)new DefendTrustedTargetGoal(LivingEntity.class, false, false, $$0 -> (TRUSTED_TARGET_SELECTOR.test($$0) && !trusts($$0.getUUID()))));
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundEvent getEatingSound(ItemStack $$0) {
/*  234 */     return SoundEvents.FOX_EAT;
/*      */   }
/*      */ 
/*      */   
/*      */   public void aiStep() {
/*  239 */     if (!(level()).isClientSide && isAlive() && isEffectiveAi()) {
/*      */       
/*  241 */       this.ticksSinceEaten++;
/*  242 */       ItemStack $$0 = getItemBySlot(EquipmentSlot.MAINHAND);
/*  243 */       if (canEat($$0)) {
/*  244 */         if (this.ticksSinceEaten > 600) {
/*  245 */           ItemStack $$1 = $$0.finishUsingItem(level(), (LivingEntity)this);
/*  246 */           if (!$$1.isEmpty()) {
/*  247 */             setItemSlot(EquipmentSlot.MAINHAND, $$1);
/*      */           }
/*  249 */           this.ticksSinceEaten = 0;
/*  250 */         } else if (this.ticksSinceEaten > 560 && 
/*  251 */           this.random.nextFloat() < 0.1F) {
/*  252 */           playSound(getEatingSound($$0), 1.0F, 1.0F);
/*  253 */           level().broadcastEntityEvent((Entity)this, (byte)45);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  259 */       LivingEntity $$2 = getTarget();
/*  260 */       if ($$2 == null || !$$2.isAlive()) {
/*  261 */         setIsCrouching(false);
/*  262 */         setIsInterested(false);
/*      */       } 
/*      */     } 
/*      */     
/*  266 */     if (isSleeping() || isImmobile()) {
/*  267 */       this.jumping = false;
/*  268 */       this.xxa = 0.0F;
/*  269 */       this.zza = 0.0F;
/*      */     } 
/*      */     
/*  272 */     super.aiStep();
/*      */     
/*  274 */     if (isDefending() && this.random.nextFloat() < 0.05F) {
/*  275 */       playSound(SoundEvents.FOX_AGGRO, 1.0F, 1.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isImmobile() {
/*  281 */     return isDeadOrDying();
/*      */   }
/*      */   
/*      */   private boolean canEat(ItemStack $$0) {
/*  285 */     return ($$0.getItem().isEdible() && getTarget() == null && onGround() && !isSleeping());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/*  290 */     if ($$0.nextFloat() < 0.2F) {
/*  291 */       ItemStack $$8; float $$2 = $$0.nextFloat();
/*      */       
/*  293 */       if ($$2 < 0.05F) {
/*  294 */         ItemStack $$3 = new ItemStack((ItemLike)Items.EMERALD);
/*  295 */       } else if ($$2 < 0.2F) {
/*  296 */         ItemStack $$4 = new ItemStack((ItemLike)Items.EGG);
/*  297 */       } else if ($$2 < 0.4F) {
/*  298 */         ItemStack $$5 = $$0.nextBoolean() ? new ItemStack((ItemLike)Items.RABBIT_FOOT) : new ItemStack((ItemLike)Items.RABBIT_HIDE);
/*  299 */       } else if ($$2 < 0.6F) {
/*  300 */         ItemStack $$6 = new ItemStack((ItemLike)Items.WHEAT);
/*  301 */       } else if ($$2 < 0.8F) {
/*  302 */         ItemStack $$7 = new ItemStack((ItemLike)Items.LEATHER);
/*      */       } else {
/*  304 */         $$8 = new ItemStack((ItemLike)Items.FEATHER);
/*      */       } 
/*  306 */       setItemSlot(EquipmentSlot.MAINHAND, $$8);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEvent(byte $$0) {
/*  312 */     if ($$0 == 45) {
/*  313 */       ItemStack $$1 = getItemBySlot(EquipmentSlot.MAINHAND);
/*  314 */       if (!$$1.isEmpty()) {
/*  315 */         for (int $$2 = 0; $$2 < 8; $$2++) {
/*      */ 
/*      */           
/*  318 */           Vec3 $$3 = (new Vec3((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).xRot(-getXRot() * 0.017453292F).yRot(-getYRot() * 0.017453292F);
/*      */           
/*  320 */           level().addParticle((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, $$1), getX() + (getLookAngle()).x / 2.0D, getY(), getZ() + (getLookAngle()).z / 2.0D, $$3.x, $$3.y + 0.05D, $$3.z);
/*      */         } 
/*      */       }
/*      */     } else {
/*  324 */       super.handleEntityEvent($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static AttributeSupplier.Builder createAttributes() {
/*  329 */     return Mob.createMobAttributes()
/*  330 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/*  331 */       .add(Attributes.MAX_HEALTH, 10.0D)
/*  332 */       .add(Attributes.FOLLOW_RANGE, 32.0D)
/*  333 */       .add(Attributes.ATTACK_DAMAGE, 2.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Fox getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*  339 */     Fox $$2 = (Fox)EntityType.FOX.create((Level)$$0);
/*  340 */     if ($$2 != null) {
/*  341 */       $$2.setVariant(this.random.nextBoolean() ? getVariant() : ((Fox)$$1).getVariant());
/*      */     }
/*  343 */     return $$2;
/*      */   }
/*      */   
/*      */   public static boolean checkFoxSpawnRules(EntityType<Fox> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/*  347 */     return ($$1.getBlockState($$3.below()).is(BlockTags.FOXES_SPAWNABLE_ON) && 
/*  348 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*      */     FoxGroupData foxGroupData;
/*  354 */     Holder<Biome> $$5 = $$0.getBiome(blockPosition());
/*  355 */     Type $$6 = Type.byBiome($$5);
/*  356 */     boolean $$7 = false;
/*  357 */     if ($$3 instanceof FoxGroupData) { FoxGroupData $$8 = (FoxGroupData)$$3;
/*      */       
/*  359 */       $$6 = $$8.type;
/*  360 */       if ($$8.getGroupSize() >= 2) {
/*  361 */         $$7 = true;
/*      */       } }
/*      */     else
/*  364 */     { foxGroupData = new FoxGroupData($$6); }
/*      */ 
/*      */     
/*  367 */     setVariant($$6);
/*  368 */     if ($$7) {
/*  369 */       setAge(-24000);
/*      */     }
/*      */     
/*  372 */     if ($$0 instanceof ServerLevel) {
/*  373 */       setTargetGoals();
/*      */     }
/*      */     
/*  376 */     populateDefaultEquipmentSlots($$0.getRandom(), $$1);
/*      */     
/*  378 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)foxGroupData, $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setTargetGoals() {
/*  383 */     if (getVariant() == Type.RED) {
/*  384 */       this.targetSelector.addGoal(4, this.landTargetGoal);
/*  385 */       this.targetSelector.addGoal(4, this.turtleEggTargetGoal);
/*  386 */       this.targetSelector.addGoal(6, this.fishTargetGoal);
/*      */     } else {
/*  388 */       this.targetSelector.addGoal(4, this.fishTargetGoal);
/*  389 */       this.targetSelector.addGoal(6, this.landTargetGoal);
/*  390 */       this.targetSelector.addGoal(6, this.turtleEggTargetGoal);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void usePlayerItem(Player $$0, InteractionHand $$1, ItemStack $$2) {
/*  396 */     if (isFood($$2)) {
/*  397 */       playSound(getEatingSound($$2), 1.0F, 1.0F);
/*      */     }
/*  399 */     super.usePlayerItem($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  404 */     if (isBaby()) {
/*  405 */       return $$1.height * 0.85F;
/*      */     }
/*  407 */     return 0.4F;
/*      */   }
/*      */ 
/*      */   
/*      */   public Type getVariant() {
/*  412 */     return Type.byId(((Integer)this.entityData.get(DATA_TYPE_ID)).intValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVariant(Type $$0) {
/*  417 */     this.entityData.set(DATA_TYPE_ID, Integer.valueOf($$0.getId()));
/*      */   }
/*      */   
/*      */   List<UUID> getTrustedUUIDs() {
/*  421 */     List<UUID> $$0 = Lists.newArrayList();
/*  422 */     $$0.add(((Optional<UUID>)this.entityData.get(DATA_TRUSTED_ID_0)).orElse(null));
/*  423 */     $$0.add(((Optional<UUID>)this.entityData.get(DATA_TRUSTED_ID_1)).orElse(null));
/*  424 */     return $$0;
/*      */   }
/*      */   
/*      */   void addTrustedUUID(@Nullable UUID $$0) {
/*  428 */     if (((Optional)this.entityData.get(DATA_TRUSTED_ID_0)).isPresent()) {
/*  429 */       this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable($$0));
/*      */     } else {
/*  431 */       this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable($$0));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  437 */     super.addAdditionalSaveData($$0);
/*  438 */     List<UUID> $$1 = getTrustedUUIDs();
/*  439 */     ListTag $$2 = new ListTag();
/*  440 */     for (UUID $$3 : $$1) {
/*  441 */       if ($$3 != null) {
/*  442 */         $$2.add(NbtUtils.createUUID($$3));
/*      */       }
/*      */     } 
/*  445 */     $$0.put("Trusted", (Tag)$$2);
/*  446 */     $$0.putBoolean("Sleeping", isSleeping());
/*  447 */     $$0.putString("Type", getVariant().getSerializedName());
/*  448 */     $$0.putBoolean("Sitting", isSitting());
/*  449 */     $$0.putBoolean("Crouching", isCrouching());
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  454 */     super.readAdditionalSaveData($$0);
/*  455 */     ListTag $$1 = $$0.getList("Trusted", 11);
/*  456 */     for (Tag $$2 : $$1) {
/*  457 */       addTrustedUUID(NbtUtils.loadUUID($$2));
/*      */     }
/*  459 */     setSleeping($$0.getBoolean("Sleeping"));
/*  460 */     setVariant(Type.byName($$0.getString("Type")));
/*  461 */     setSitting($$0.getBoolean("Sitting"));
/*  462 */     setIsCrouching($$0.getBoolean("Crouching"));
/*      */ 
/*      */     
/*  465 */     if (level() instanceof ServerLevel) {
/*  466 */       setTargetGoals();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isSitting() {
/*  471 */     return getFlag(1);
/*      */   }
/*      */   
/*      */   public void setSitting(boolean $$0) {
/*  475 */     setFlag(1, $$0);
/*      */   }
/*      */   
/*      */   public boolean isFaceplanted() {
/*  479 */     return getFlag(64);
/*      */   }
/*      */   
/*      */   void setFaceplanted(boolean $$0) {
/*  483 */     setFlag(64, $$0);
/*      */   }
/*      */   
/*      */   boolean isDefending() {
/*  487 */     return getFlag(128);
/*      */   }
/*      */   
/*      */   void setDefending(boolean $$0) {
/*  491 */     setFlag(128, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSleeping() {
/*  496 */     return getFlag(32);
/*      */   }
/*      */   
/*      */   void setSleeping(boolean $$0) {
/*  500 */     setFlag(32, $$0);
/*      */   }
/*      */   
/*      */   private void setFlag(int $$0, boolean $$1) {
/*  504 */     if ($$1) {
/*  505 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)(((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() | $$0)));
/*      */     } else {
/*  507 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)(((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & ($$0 ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean getFlag(int $$0) {
/*  512 */     return ((((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & $$0) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canTakeItem(ItemStack $$0) {
/*  517 */     EquipmentSlot $$1 = Mob.getEquipmentSlotForItem($$0);
/*  518 */     if (!getItemBySlot($$1).isEmpty()) {
/*  519 */       return false;
/*      */     }
/*  521 */     return ($$1 == EquipmentSlot.MAINHAND && super.canTakeItem($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canHoldItem(ItemStack $$0) {
/*  526 */     Item $$1 = $$0.getItem();
/*  527 */     ItemStack $$2 = getItemBySlot(EquipmentSlot.MAINHAND);
/*      */     
/*  529 */     return ($$2.isEmpty() || (this.ticksSinceEaten > 0 && $$1.isEdible() && !$$2.getItem().isEdible()));
/*      */   }
/*      */   
/*      */   private void spitOutItem(ItemStack $$0) {
/*  533 */     if ($$0.isEmpty() || (level()).isClientSide) {
/*      */       return;
/*      */     }
/*      */     
/*  537 */     ItemEntity $$1 = new ItemEntity(level(), getX() + (getLookAngle()).x, getY() + 1.0D, getZ() + (getLookAngle()).z, $$0);
/*  538 */     $$1.setPickUpDelay(40);
/*  539 */     $$1.setThrower((Entity)this);
/*      */     
/*  541 */     playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
/*  542 */     level().addFreshEntity((Entity)$$1);
/*      */   }
/*      */   
/*      */   private void dropItemStack(ItemStack $$0) {
/*  546 */     ItemEntity $$1 = new ItemEntity(level(), getX(), getY(), getZ(), $$0);
/*  547 */     level().addFreshEntity((Entity)$$1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void pickUpItem(ItemEntity $$0) {
/*  552 */     ItemStack $$1 = $$0.getItem();
/*  553 */     if (canHoldItem($$1)) {
/*  554 */       int $$2 = $$1.getCount();
/*  555 */       if ($$2 > 1) {
/*  556 */         dropItemStack($$1.split($$2 - 1));
/*      */       }
/*      */       
/*  559 */       spitOutItem(getItemBySlot(EquipmentSlot.MAINHAND));
/*      */       
/*  561 */       onItemPickup($$0);
/*      */       
/*  563 */       setItemSlot(EquipmentSlot.MAINHAND, $$1.split(1));
/*  564 */       setGuaranteedDrop(EquipmentSlot.MAINHAND);
/*  565 */       take((Entity)$$0, $$1.getCount());
/*  566 */       $$0.discard();
/*  567 */       this.ticksSinceEaten = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  573 */     super.tick();
/*      */     
/*  575 */     if (isEffectiveAi()) {
/*  576 */       boolean $$0 = isInWater();
/*  577 */       if ($$0 || getTarget() != null || level().isThundering()) {
/*  578 */         wakeUp();
/*      */       }
/*      */       
/*  581 */       if ($$0 || isSleeping()) {
/*  582 */         setSitting(false);
/*      */       }
/*      */       
/*  585 */       if (isFaceplanted() && (level()).random.nextFloat() < 0.2F) {
/*  586 */         BlockPos $$1 = blockPosition();
/*  587 */         BlockState $$2 = level().getBlockState($$1);
/*  588 */         level().levelEvent(2001, $$1, Block.getId($$2));
/*      */       } 
/*      */     } 
/*      */     
/*  592 */     this.interestedAngleO = this.interestedAngle;
/*  593 */     if (isInterested()) {
/*  594 */       this.interestedAngle += (1.0F - this.interestedAngle) * 0.4F;
/*      */     } else {
/*  596 */       this.interestedAngle += (0.0F - this.interestedAngle) * 0.4F;
/*      */     } 
/*      */     
/*  599 */     this.crouchAmountO = this.crouchAmount;
/*  600 */     if (isCrouching()) {
/*  601 */       this.crouchAmount += 0.2F;
/*  602 */       if (this.crouchAmount > 3.0F) {
/*  603 */         this.crouchAmount = 3.0F;
/*      */       }
/*      */     } else {
/*  606 */       this.crouchAmount = 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFood(ItemStack $$0) {
/*  612 */     return $$0.is(ItemTags.FOX_FOOD);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onOffspringSpawnedFromEgg(Player $$0, Mob $$1) {
/*  617 */     ((Fox)$$1).addTrustedUUID($$0.getUUID());
/*      */   }
/*      */   
/*      */   public boolean isPouncing() {
/*  621 */     return getFlag(16);
/*      */   }
/*      */   
/*      */   public void setIsPouncing(boolean $$0) {
/*  625 */     setFlag(16, $$0);
/*      */   }
/*      */   
/*      */   public boolean isJumping() {
/*  629 */     return this.jumping;
/*      */   }
/*      */   
/*      */   public boolean isFullyCrouched() {
/*  633 */     return (this.crouchAmount == 3.0F);
/*      */   }
/*      */   
/*      */   public void setIsCrouching(boolean $$0) {
/*  637 */     setFlag(4, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCrouching() {
/*  642 */     return getFlag(4);
/*      */   }
/*      */   
/*      */   public void setIsInterested(boolean $$0) {
/*  646 */     setFlag(8, $$0);
/*      */   }
/*      */   
/*      */   public boolean isInterested() {
/*  650 */     return getFlag(8);
/*      */   }
/*      */   
/*      */   public float getHeadRollAngle(float $$0) {
/*  654 */     return Mth.lerp($$0, this.interestedAngleO, this.interestedAngle) * 0.11F * 3.1415927F;
/*      */   }
/*      */   
/*      */   public float getCrouchAmount(float $$0) {
/*  658 */     return Mth.lerp($$0, this.crouchAmountO, this.crouchAmount);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTarget(@Nullable LivingEntity $$0) {
/*  663 */     if (isDefending() && $$0 == null) {
/*  664 */       setDefending(false);
/*      */     }
/*  666 */     super.setTarget($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected int calculateFallDamage(float $$0, float $$1) {
/*  671 */     return Mth.ceil(($$0 - 5.0F) * $$1);
/*      */   }
/*      */   
/*      */   void wakeUp() {
/*  675 */     setSleeping(false);
/*      */   }
/*      */   
/*      */   void clearStates() {
/*  679 */     setIsInterested(false);
/*  680 */     setIsCrouching(false);
/*  681 */     setSitting(false);
/*  682 */     setSleeping(false);
/*  683 */     setDefending(false);
/*  684 */     setFaceplanted(false);
/*      */   }
/*      */   
/*      */   boolean canMove() {
/*  688 */     return (!isSleeping() && !isSitting() && !isFaceplanted());
/*      */   }
/*      */ 
/*      */   
/*      */   public void playAmbientSound() {
/*  693 */     SoundEvent $$0 = getAmbientSound();
/*      */     
/*  695 */     if ($$0 == SoundEvents.FOX_SCREECH) {
/*  696 */       playSound($$0, 2.0F, getVoicePitch());
/*      */     } else {
/*  698 */       super.playAmbientSound();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAmbientSound() {
/*  705 */     if (isSleeping()) {
/*  706 */       return SoundEvents.FOX_SLEEP;
/*      */     }
/*  708 */     if (!level().isDay() && this.random.nextFloat() < 0.1F) {
/*  709 */       List<Player> $$0 = level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(16.0D, 16.0D, 16.0D), EntitySelector.NO_SPECTATORS);
/*  710 */       if ($$0.isEmpty()) {
/*  711 */         return SoundEvents.FOX_SCREECH;
/*      */       }
/*      */     } 
/*  714 */     return SoundEvents.FOX_AMBIENT;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  720 */     return SoundEvents.FOX_HURT;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getDeathSound() {
/*  726 */     return SoundEvents.FOX_DEATH;
/*      */   }
/*      */   
/*      */   boolean trusts(UUID $$0) {
/*  730 */     return getTrustedUUIDs().contains($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dropAllDeathLoot(DamageSource $$0) {
/*  735 */     ItemStack $$1 = getItemBySlot(EquipmentSlot.MAINHAND);
/*      */     
/*  737 */     if (!$$1.isEmpty()) {
/*  738 */       spawnAtLocation($$1);
/*  739 */       setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/*      */     } 
/*      */     
/*  742 */     super.dropAllDeathLoot($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/*  747 */     return new Vector3f(0.0F, $$1.height + -0.0625F * $$2, -0.25F * $$2);
/*      */   }
/*      */   
/*      */   public static boolean isPathClear(Fox $$0, LivingEntity $$1) {
/*  751 */     double $$2 = $$1.getZ() - $$0.getZ();
/*  752 */     double $$3 = $$1.getX() - $$0.getX();
/*  753 */     double $$4 = $$2 / $$3;
/*      */     
/*  755 */     int $$5 = 6;
/*  756 */     for (int $$6 = 0; $$6 < 6; $$6++) {
/*  757 */       double $$7 = ($$4 == 0.0D) ? 0.0D : ($$2 * ($$6 / 6.0F));
/*  758 */       double $$8 = ($$4 == 0.0D) ? ($$3 * ($$6 / 6.0F)) : ($$7 / $$4);
/*  759 */       for (int $$9 = 1; $$9 < 4; $$9++) {
/*  760 */         if (!$$0.level().getBlockState(BlockPos.containing($$0.getX() + $$8, $$0.getY() + $$9, $$0.getZ() + $$7)).canBeReplaced()) {
/*  761 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  766 */     return true;
/*      */   }
/*      */   
/*      */   private class FoxSearchForItemsGoal extends Goal {
/*      */     public FoxSearchForItemsGoal() {
/*  771 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  776 */       if (!Fox.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
/*  777 */         return false;
/*      */       }
/*      */       
/*  780 */       if (Fox.this.getTarget() != null || Fox.this.getLastHurtByMob() != null) {
/*  781 */         return false;
/*      */       }
/*      */       
/*  784 */       if (!Fox.this.canMove()) {
/*  785 */         return false;
/*      */       }
/*      */       
/*  788 */       if (Fox.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
/*  789 */         return false;
/*      */       }
/*  791 */       List<ItemEntity> $$0 = Fox.this.level().getEntitiesOfClass(ItemEntity.class, Fox.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Fox.ALLOWED_ITEMS);
/*  792 */       return (!$$0.isEmpty() && Fox.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/*  797 */       List<ItemEntity> $$0 = Fox.this.level().getEntitiesOfClass(ItemEntity.class, Fox.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Fox.ALLOWED_ITEMS);
/*  798 */       ItemStack $$1 = Fox.this.getItemBySlot(EquipmentSlot.MAINHAND);
/*      */       
/*  800 */       if ($$1.isEmpty() && !$$0.isEmpty()) {
/*  801 */         Fox.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158D);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  807 */       List<ItemEntity> $$0 = Fox.this.level().getEntitiesOfClass(ItemEntity.class, Fox.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Fox.ALLOWED_ITEMS);
/*  808 */       if (!$$0.isEmpty())
/*  809 */         Fox.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158D); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxMoveControl
/*      */     extends MoveControl {
/*      */     public FoxMoveControl() {
/*  816 */       super((Mob)Fox.this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/*  821 */       if (Fox.this.canMove())
/*  822 */         super.tick(); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class StalkPreyGoal
/*      */     extends Goal {
/*      */     public StalkPreyGoal() {
/*  829 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  834 */       if (Fox.this.isSleeping()) {
/*  835 */         return false;
/*      */       }
/*      */       
/*  838 */       LivingEntity $$0 = Fox.this.getTarget();
/*  839 */       return ($$0 != null && $$0.isAlive() && Fox.STALKABLE_PREY.test($$0) && Fox.this.distanceToSqr((Entity)$$0) > 36.0D && !Fox.this.isCrouching() && !Fox.this.isInterested() && !Fox.this.jumping);
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  844 */       Fox.this.setSitting(false);
/*  845 */       Fox.this.setFaceplanted(false);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void stop() {
/*  851 */       LivingEntity $$0 = Fox.this.getTarget();
/*  852 */       if ($$0 != null && Fox.isPathClear(Fox.this, $$0)) {
/*  853 */         Fox.this.setIsInterested(true);
/*  854 */         Fox.this.setIsCrouching(true);
/*  855 */         Fox.this.getNavigation().stop();
/*  856 */         Fox.this.getLookControl().setLookAt((Entity)$$0, Fox.this.getMaxHeadYRot(), Fox.this.getMaxHeadXRot());
/*      */       } else {
/*  858 */         Fox.this.setIsInterested(false);
/*  859 */         Fox.this.setIsCrouching(false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/*  865 */       LivingEntity $$0 = Fox.this.getTarget();
/*  866 */       if ($$0 == null) {
/*      */         return;
/*      */       }
/*  869 */       Fox.this.getLookControl().setLookAt((Entity)$$0, Fox.this.getMaxHeadYRot(), Fox.this.getMaxHeadXRot());
/*  870 */       if (Fox.this.distanceToSqr((Entity)$$0) <= 36.0D) {
/*  871 */         Fox.this.setIsInterested(true);
/*  872 */         Fox.this.setIsCrouching(true);
/*  873 */         Fox.this.getNavigation().stop();
/*      */       } else {
/*  875 */         Fox.this.getNavigation().moveTo((Entity)$$0, 1.5D);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxMeleeAttackGoal extends MeleeAttackGoal {
/*      */     public FoxMeleeAttackGoal(double $$0, boolean $$1) {
/*  882 */       super((PathfinderMob)Fox.this, $$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void checkAndPerformAttack(LivingEntity $$0) {
/*  887 */       if (canPerformAttack($$0)) {
/*  888 */         resetAttackCooldown();
/*  889 */         this.mob.doHurtTarget((Entity)$$0);
/*  890 */         Fox.this.playSound(SoundEvents.FOX_BITE, 1.0F, 1.0F);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  896 */       Fox.this.setIsInterested(false);
/*  897 */       super.start();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  902 */       return (!Fox.this.isSitting() && !Fox.this.isSleeping() && !Fox.this.isCrouching() && !Fox.this.isFaceplanted() && super.canUse());
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxBreedGoal extends BreedGoal {
/*      */     public FoxBreedGoal(double $$0) {
/*  908 */       super(Fox.this, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  913 */       ((Fox)this.animal).clearStates();
/*  914 */       ((Fox)this.partner).clearStates();
/*  915 */       super.start();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void breed() {
/*  920 */       ServerLevel $$0 = (ServerLevel)this.level;
/*  921 */       Fox $$1 = (Fox)this.animal.getBreedOffspring($$0, this.partner);
/*  922 */       if ($$1 == null) {
/*      */         return;
/*      */       }
/*      */       
/*  926 */       ServerPlayer $$2 = this.animal.getLoveCause();
/*  927 */       ServerPlayer $$3 = this.partner.getLoveCause();
/*  928 */       ServerPlayer $$4 = $$2;
/*      */       
/*  930 */       if ($$2 != null) {
/*  931 */         $$1.addTrustedUUID($$2.getUUID());
/*      */       } else {
/*  933 */         $$4 = $$3;
/*      */       } 
/*      */       
/*  936 */       if ($$3 != null && $$2 != $$3) {
/*  937 */         $$1.addTrustedUUID($$3.getUUID());
/*      */       }
/*      */       
/*  940 */       if ($$4 != null) {
/*  941 */         $$4.awardStat(Stats.ANIMALS_BRED);
/*  942 */         CriteriaTriggers.BRED_ANIMALS.trigger($$4, this.animal, this.partner, $$1);
/*      */       } 
/*      */       
/*  945 */       this.animal.setAge(6000);
/*  946 */       this.partner.setAge(6000);
/*  947 */       this.animal.resetLove();
/*  948 */       this.partner.resetLove();
/*  949 */       $$1.setAge(-24000);
/*  950 */       $$1.moveTo(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);
/*  951 */       $$0.addFreshEntityWithPassengers((Entity)$$1);
/*      */       
/*  953 */       this.level.broadcastEntityEvent((Entity)this.animal, (byte)18);
/*      */       
/*  955 */       if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT))
/*  956 */         this.level.addFreshEntity((Entity)new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), this.animal.getRandom().nextInt(7) + 1)); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class DefendTrustedTargetGoal
/*      */     extends NearestAttackableTargetGoal<LivingEntity> {
/*      */     @Nullable
/*      */     private LivingEntity trustedLastHurtBy;
/*      */     @Nullable
/*      */     private LivingEntity trustedLastHurt;
/*      */     private int timestamp;
/*      */     
/*      */     public DefendTrustedTargetGoal(Class<LivingEntity> $$0, boolean $$1, @Nullable boolean $$2, Predicate<LivingEntity> $$3) {
/*  969 */       super((Mob)Fox.this, $$0, 10, $$1, $$2, $$3);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  974 */       if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
/*  975 */         return false;
/*      */       }
/*      */       
/*  978 */       for (UUID $$0 : Fox.this.getTrustedUUIDs()) {
/*  979 */         if ($$0 == null || !(Fox.this.level() instanceof ServerLevel)) {
/*      */           continue;
/*      */         }
/*      */         
/*  983 */         Entity $$1 = ((ServerLevel)Fox.this.level()).getEntity($$0);
/*  984 */         if (!($$1 instanceof LivingEntity)) {
/*      */           continue;
/*      */         }
/*  987 */         LivingEntity $$2 = (LivingEntity)$$1;
/*  988 */         this.trustedLastHurt = $$2;
/*  989 */         this.trustedLastHurtBy = $$2.getLastHurtByMob();
/*  990 */         int $$3 = $$2.getLastHurtByMobTimestamp();
/*  991 */         return ($$3 != this.timestamp && canAttack(this.trustedLastHurtBy, this.targetConditions));
/*      */       } 
/*  993 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  998 */       setTarget(this.trustedLastHurtBy);
/*  999 */       this.target = this.trustedLastHurtBy;
/*      */       
/* 1001 */       if (this.trustedLastHurt != null) {
/* 1002 */         this.timestamp = this.trustedLastHurt.getLastHurtByMobTimestamp();
/*      */       }
/*      */       
/* 1005 */       Fox.this.playSound(SoundEvents.FOX_AGGRO, 1.0F, 1.0F);
/*      */       
/* 1007 */       Fox.this.setDefending(true);
/*      */ 
/*      */       
/* 1010 */       Fox.this.wakeUp();
/*      */       
/* 1012 */       super.start();
/*      */     }
/*      */   }
/*      */   
/*      */   private class SeekShelterGoal extends FleeSunGoal {
/*      */     private int interval;
/*      */     
/*      */     public SeekShelterGoal(double $$0) {
/* 1020 */       super((PathfinderMob)Fox.this, $$0);
/* 1021 */       this.interval = reducedTickDelay(100);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1026 */       if (Fox.this.isSleeping() || this.mob.getTarget() != null) {
/* 1027 */         return false;
/*      */       }
/* 1029 */       if (Fox.this.level().isThundering() && Fox.this.level().canSeeSky(this.mob.blockPosition())) {
/* 1030 */         return setWantedPos();
/*      */       }
/* 1032 */       if (this.interval > 0) {
/* 1033 */         this.interval--;
/* 1034 */         return false;
/*      */       } 
/* 1036 */       this.interval = 100;
/*      */       
/* 1038 */       BlockPos $$0 = this.mob.blockPosition();
/*      */       
/* 1040 */       return (Fox.this.level().isDay() && Fox.this
/* 1041 */         .level().canSeeSky($$0) && 
/* 1042 */         !((ServerLevel)Fox.this.level()).isVillage($$0) && 
/* 1043 */         setWantedPos());
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1048 */       Fox.this.clearStates();
/* 1049 */       super.start();
/*      */     }
/*      */   }
/*      */   
/*      */   public class FoxAlertableEntitiesSelector
/*      */     implements Predicate<LivingEntity> {
/*      */     public boolean test(LivingEntity $$0) {
/* 1056 */       if ($$0 instanceof Fox) {
/* 1057 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1061 */       if ($$0 instanceof Chicken || $$0 instanceof Rabbit || $$0 instanceof net.minecraft.world.entity.monster.Monster) {
/* 1062 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1066 */       if ($$0 instanceof TamableAnimal) {
/* 1067 */         return !((TamableAnimal)$$0).isTame();
/*      */       }
/*      */ 
/*      */       
/* 1071 */       if ($$0 instanceof Player && ($$0.isSpectator() || ((Player)$$0).isCreative())) {
/* 1072 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1076 */       if (Fox.this.trusts($$0.getUUID())) {
/* 1077 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1081 */       return (!$$0.isSleeping() && !$$0.isDiscrete());
/*      */     } }
/*      */   private abstract class FoxBehaviorGoal extends Goal { private final TargetingConditions alertableTargeting;
/*      */     
/*      */     FoxBehaviorGoal() {
/* 1086 */       this.alertableTargeting = TargetingConditions.forCombat().range(12.0D).ignoreLineOfSight().selector(new Fox.FoxAlertableEntitiesSelector());
/*      */     }
/*      */     protected boolean hasShelter() {
/* 1089 */       BlockPos $$0 = BlockPos.containing(Fox.this.getX(), (Fox.this.getBoundingBox()).maxY, Fox.this.getZ());
/* 1090 */       return (!Fox.this.level().canSeeSky($$0) && Fox.this.getWalkTargetValue($$0) >= 0.0F);
/*      */     }
/*      */     
/*      */     protected boolean alertable() {
/* 1094 */       return !Fox.this.level().getNearbyEntities(LivingEntity.class, this.alertableTargeting, (LivingEntity)Fox.this, Fox.this.getBoundingBox().inflate(12.0D, 6.0D, 12.0D)).isEmpty();
/*      */     } }
/*      */ 
/*      */   
/*      */   private class SleepGoal extends FoxBehaviorGoal {
/* 1099 */     private static final int WAIT_TIME_BEFORE_SLEEP = reducedTickDelay(140);
/*      */     private int countdown;
/*      */     
/*      */     public SleepGoal() {
/* 1103 */       this.countdown = Fox.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);
/* 1104 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1109 */       if (Fox.this.xxa != 0.0F || Fox.this.yya != 0.0F || Fox.this.zza != 0.0F) {
/* 1110 */         return false;
/*      */       }
/* 1112 */       return (canSleep() || Fox.this.isSleeping());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1117 */       return canSleep();
/*      */     }
/*      */     
/*      */     private boolean canSleep() {
/* 1121 */       if (this.countdown > 0) {
/* 1122 */         this.countdown--;
/* 1123 */         return false;
/*      */       } 
/* 1125 */       return (Fox.this.level().isDay() && hasShelter() && !alertable() && !Fox.this.isInPowderSnow);
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1130 */       this.countdown = Fox.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);
/* 1131 */       Fox.this.clearStates();
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1136 */       Fox.this.setSitting(false);
/* 1137 */       Fox.this.setIsCrouching(false);
/* 1138 */       Fox.this.setIsInterested(false);
/* 1139 */       Fox.this.setJumping(false);
/* 1140 */       Fox.this.setSleeping(true);
/* 1141 */       Fox.this.getNavigation().stop();
/* 1142 */       Fox.this.getMoveControl().setWantedPosition(Fox.this.getX(), Fox.this.getY(), Fox.this.getZ(), 0.0D);
/*      */     }
/*      */   }
/*      */   
/*      */   private class PerchAndSearchGoal extends FoxBehaviorGoal {
/*      */     private double relX;
/*      */     private double relZ;
/*      */     private int lookTime;
/*      */     private int looksRemaining;
/*      */     
/*      */     public PerchAndSearchGoal() {
/* 1153 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1158 */       return (Fox.this.getLastHurtByMob() == null && Fox.this.getRandom().nextFloat() < 0.02F && !Fox.this.isSleeping() && Fox.this.getTarget() == null && Fox.this.getNavigation().isDone() && !alertable() && !Fox.this.isPouncing() && !Fox.this.isCrouching());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1163 */       return (this.looksRemaining > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1168 */       resetLook();
/* 1169 */       this.looksRemaining = 2 + Fox.this.getRandom().nextInt(3);
/* 1170 */       Fox.this.setSitting(true);
/* 1171 */       Fox.this.getNavigation().stop();
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1176 */       Fox.this.setSitting(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1181 */       this.lookTime--;
/* 1182 */       if (this.lookTime <= 0) {
/* 1183 */         this.looksRemaining--;
/* 1184 */         resetLook();
/*      */       } 
/* 1186 */       Fox.this.getLookControl().setLookAt(Fox.this.getX() + this.relX, Fox.this.getEyeY(), Fox.this.getZ() + this.relZ, Fox.this.getMaxHeadYRot(), Fox.this.getMaxHeadXRot());
/*      */     }
/*      */     
/*      */     private void resetLook() {
/* 1190 */       double $$0 = 6.283185307179586D * Fox.this.getRandom().nextDouble();
/* 1191 */       this.relX = Math.cos($$0);
/* 1192 */       this.relZ = Math.sin($$0);
/* 1193 */       this.lookTime = adjustedTickDelay(80 + Fox.this.getRandom().nextInt(20));
/*      */     }
/*      */   }
/*      */   
/*      */   public class FoxEatBerriesGoal
/*      */     extends MoveToBlockGoal {
/*      */     private static final int WAIT_TICKS = 40;
/*      */     protected int ticksWaited;
/*      */     
/*      */     public FoxEatBerriesGoal(double $$1, int $$2, int $$3) {
/* 1203 */       super((PathfinderMob)$$0, $$1, $$2, $$3);
/*      */     }
/*      */ 
/*      */     
/*      */     public double acceptedDistance() {
/* 1208 */       return 2.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean shouldRecalculatePath() {
/* 1213 */       return (this.tryTicks % 100 == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 1218 */       BlockState $$2 = $$0.getBlockState($$1);
/* 1219 */       return (($$2.is(Blocks.SWEET_BERRY_BUSH) && ((Integer)$$2.getValue((Property)SweetBerryBushBlock.AGE)).intValue() >= 2) || 
/* 1220 */         CaveVines.hasGlowBerries($$2));
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1225 */       if (isReachedTarget()) {
/* 1226 */         if (this.ticksWaited >= 40) {
/* 1227 */           onReachedTarget();
/*      */         } else {
/* 1229 */           this.ticksWaited++;
/*      */         } 
/* 1231 */       } else if (!isReachedTarget() && Fox.this.random.nextFloat() < 0.05F) {
/* 1232 */         Fox.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
/*      */       } 
/*      */       
/* 1235 */       super.tick();
/*      */     }
/*      */     
/*      */     protected void onReachedTarget() {
/* 1239 */       if (!Fox.this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/*      */         return;
/*      */       }
/*      */       
/* 1243 */       BlockState $$0 = Fox.this.level().getBlockState(this.blockPos);
/*      */ 
/*      */       
/* 1246 */       if ($$0.is(Blocks.SWEET_BERRY_BUSH)) {
/* 1247 */         pickSweetBerries($$0);
/* 1248 */       } else if (CaveVines.hasGlowBerries($$0)) {
/* 1249 */         pickGlowBerry($$0);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void pickGlowBerry(BlockState $$0) {
/* 1254 */       CaveVines.use((Entity)Fox.this, $$0, Fox.this.level(), this.blockPos);
/*      */     }
/*      */     
/*      */     private void pickSweetBerries(BlockState $$0) {
/* 1258 */       int $$1 = ((Integer)$$0.getValue((Property)SweetBerryBushBlock.AGE)).intValue();
/* 1259 */       $$0.setValue((Property)SweetBerryBushBlock.AGE, Integer.valueOf(1));
/* 1260 */       int $$2 = 1 + (Fox.this.level()).random.nextInt(2) + (($$1 == 3) ? 1 : 0);
/* 1261 */       ItemStack $$3 = Fox.this.getItemBySlot(EquipmentSlot.MAINHAND);
/* 1262 */       if ($$3.isEmpty()) {
/* 1263 */         Fox.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.SWEET_BERRIES));
/* 1264 */         $$2--;
/*      */       } 
/* 1266 */       if ($$2 > 0) {
/* 1267 */         Block.popResource(Fox.this.level(), this.blockPos, new ItemStack((ItemLike)Items.SWEET_BERRIES, $$2));
/*      */       }
/* 1269 */       Fox.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
/* 1270 */       Fox.this.level().setBlock(this.blockPos, (BlockState)$$0.setValue((Property)SweetBerryBushBlock.AGE, Integer.valueOf(1)), 2);
/* 1271 */       Fox.this.level().gameEvent(GameEvent.BLOCK_CHANGE, this.blockPos, GameEvent.Context.of((Entity)Fox.this));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1276 */       return (!Fox.this.isSleeping() && super.canUse());
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1281 */       this.ticksWaited = 0;
/* 1282 */       Fox.this.setSitting(false);
/* 1283 */       super.start();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class FoxGroupData extends AgeableMob.AgeableMobGroupData {
/*      */     public final Fox.Type type;
/*      */     
/*      */     public FoxGroupData(Fox.Type $$0) {
/* 1291 */       super(false);
/* 1292 */       this.type = $$0;
/*      */     }
/*      */   }
/*      */   
/*      */   private class FaceplantGoal extends Goal {
/*      */     int countdown;
/*      */     
/*      */     public FaceplantGoal() {
/* 1300 */       setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1305 */       return Fox.this.isFaceplanted();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1310 */       return (canUse() && this.countdown > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1315 */       this.countdown = adjustedTickDelay(40);
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1320 */       Fox.this.setFaceplanted(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1325 */       this.countdown--;
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxPanicGoal extends PanicGoal {
/*      */     public FoxPanicGoal(double $$0) {
/* 1331 */       super((PathfinderMob)Fox.this, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean shouldPanic() {
/* 1336 */       return (!Fox.this.isDefending() && super.shouldPanic());
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxStrollThroughVillageGoal extends StrollThroughVillageGoal {
/*      */     public FoxStrollThroughVillageGoal(int $$0, int $$1) {
/* 1342 */       super((PathfinderMob)Fox.this, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1347 */       Fox.this.clearStates();
/* 1348 */       super.start();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1353 */       return (super.canUse() && canFoxMove());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1358 */       return (super.canContinueToUse() && canFoxMove());
/*      */     }
/*      */     
/*      */     private boolean canFoxMove() {
/* 1362 */       return (!Fox.this.isSleeping() && !Fox.this.isSitting() && !Fox.this.isDefending() && Fox.this.getTarget() == null);
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxFloatGoal extends FloatGoal {
/*      */     public FoxFloatGoal() {
/* 1368 */       super((Mob)Fox.this);
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1373 */       super.start();
/* 1374 */       Fox.this.clearStates();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1379 */       return ((Fox.this.isInWater() && Fox.this.getFluidHeight(FluidTags.WATER) > 0.25D) || Fox.this.isInLava());
/*      */     }
/*      */   }
/*      */   
/*      */   public class FoxPounceGoal
/*      */     extends JumpGoal {
/*      */     public boolean canUse() {
/* 1386 */       if (!Fox.this.isFullyCrouched()) {
/* 1387 */         return false;
/*      */       }
/*      */       
/* 1390 */       LivingEntity $$0 = Fox.this.getTarget();
/*      */       
/* 1392 */       if ($$0 == null || !$$0.isAlive()) {
/* 1393 */         return false;
/*      */       }
/*      */       
/* 1396 */       if ($$0.getMotionDirection() != $$0.getDirection()) {
/* 1397 */         return false;
/*      */       }
/*      */       
/* 1400 */       boolean $$1 = Fox.isPathClear(Fox.this, $$0);
/* 1401 */       if (!$$1) {
/* 1402 */         Fox.this.getNavigation().createPath((Entity)$$0, 0);
/* 1403 */         Fox.this.setIsCrouching(false);
/* 1404 */         Fox.this.setIsInterested(false);
/*      */       } 
/*      */       
/* 1407 */       return $$1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1412 */       LivingEntity $$0 = Fox.this.getTarget();
/*      */       
/* 1414 */       if ($$0 == null || !$$0.isAlive()) {
/* 1415 */         return false;
/*      */       }
/*      */       
/* 1418 */       double $$1 = (Fox.this.getDeltaMovement()).y;
/* 1419 */       return (($$1 * $$1 >= 0.05000000074505806D || Math.abs(Fox.this.getXRot()) >= 15.0F || !Fox.this.onGround()) && !Fox.this.isFaceplanted());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isInterruptable() {
/* 1424 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1429 */       Fox.this.setJumping(true);
/* 1430 */       Fox.this.setIsPouncing(true);
/* 1431 */       Fox.this.setIsInterested(false);
/*      */       
/* 1433 */       LivingEntity $$0 = Fox.this.getTarget();
/* 1434 */       if ($$0 != null) {
/* 1435 */         Fox.this.getLookControl().setLookAt((Entity)$$0, 60.0F, 30.0F);
/*      */         
/* 1437 */         Vec3 $$1 = (new Vec3($$0.getX() - Fox.this.getX(), $$0.getY() - Fox.this.getY(), $$0.getZ() - Fox.this.getZ())).normalize();
/* 1438 */         Fox.this.setDeltaMovement(Fox.this.getDeltaMovement().add($$1.x * 0.8D, 0.9D, $$1.z * 0.8D));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1445 */       Fox.this.getNavigation().stop();
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1450 */       Fox.this.setIsCrouching(false);
/* 1451 */       Fox.this.crouchAmount = 0.0F;
/* 1452 */       Fox.this.crouchAmountO = 0.0F;
/* 1453 */       Fox.this.setIsInterested(false);
/* 1454 */       Fox.this.setIsPouncing(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1459 */       LivingEntity $$0 = Fox.this.getTarget();
/*      */       
/* 1461 */       if ($$0 != null) {
/* 1462 */         Fox.this.getLookControl().setLookAt((Entity)$$0, 60.0F, 30.0F);
/*      */       }
/*      */       
/* 1465 */       if (!Fox.this.isFaceplanted()) {
/* 1466 */         Vec3 $$1 = Fox.this.getDeltaMovement();
/* 1467 */         if ($$1.y * $$1.y < 0.029999999329447746D && Fox.this.getXRot() != 0.0F) {
/* 1468 */           Fox.this.setXRot(Mth.rotLerp(0.2F, Fox.this.getXRot(), 0.0F));
/*      */         } else {
/* 1470 */           double $$2 = $$1.horizontalDistance();
/* 1471 */           double $$3 = Math.signum(-$$1.y) * Math.acos($$2 / $$1.length()) * 57.2957763671875D;
/* 1472 */           Fox.this.setXRot((float)$$3);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1477 */       if ($$0 != null && Fox.this.distanceTo((Entity)$$0) <= 2.0F) {
/* 1478 */         Fox.this.doHurtTarget((Entity)$$0);
/*      */       }
/* 1480 */       else if (Fox.this.getXRot() > 0.0F && Fox.this.onGround() && (float)(Fox.this.getDeltaMovement()).y != 0.0F && 
/* 1481 */         Fox.this.level().getBlockState(Fox.this.blockPosition()).is(Blocks.SNOW)) {
/* 1482 */         Fox.this.setXRot(60.0F);
/* 1483 */         Fox.this.setTarget((LivingEntity)null);
/* 1484 */         Fox.this.setFaceplanted(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLeashOffset() {
/* 1493 */     return new Vec3(0.0D, (0.55F * getEyeHeight()), (getBbWidth() * 0.4F));
/*      */   }
/*      */   
/*      */   public class FoxLookControl extends LookControl {
/*      */     public FoxLookControl() {
/* 1498 */       super((Mob)$$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1503 */       if (!Fox.this.isSleeping()) {
/* 1504 */         super.tick();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean resetXRotOnTick() {
/* 1510 */       return (!Fox.this.isPouncing() && !Fox.this.isCrouching() && !Fox.this.isInterested() && !Fox.this.isFaceplanted());
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxFollowParentGoal extends FollowParentGoal {
/*      */     private final Fox fox;
/*      */     
/*      */     public FoxFollowParentGoal(Fox $$0, double $$1) {
/* 1518 */       super($$0, $$1);
/* 1519 */       this.fox = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1524 */       return (!this.fox.isDefending() && super.canUse());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1529 */       return (!this.fox.isDefending() && super.canContinueToUse());
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1534 */       this.fox.clearStates();
/* 1535 */       super.start();
/*      */     }
/*      */   }
/*      */   
/*      */   private class FoxLookAtPlayerGoal extends LookAtPlayerGoal {
/*      */     public FoxLookAtPlayerGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2) {
/* 1541 */       super($$0, $$1, $$2);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1546 */       return (super.canUse() && !Fox.this.isFaceplanted() && !Fox.this.isInterested());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1551 */       return (super.canContinueToUse() && !Fox.this.isFaceplanted() && !Fox.this.isInterested());
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Fox.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */