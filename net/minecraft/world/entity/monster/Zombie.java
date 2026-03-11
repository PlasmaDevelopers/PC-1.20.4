/*     */ package net.minecraft.world.entity.monster;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.time.LocalDate;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
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
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.SpawnPlacements;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.GoalUtils;
/*     */ import net.minecraft.world.entity.animal.Chicken;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Zombie extends Monster {
/*  74 */   private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
/*  75 */   private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
/*     */   
/*  77 */   private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
/*  78 */   private static final EntityDataAccessor<Integer> DATA_SPECIAL_TYPE_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.INT);
/*  79 */   private static final EntityDataAccessor<Boolean> DATA_DROWNED_CONVERSION_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN); public static final float ZOMBIE_LEADER_CHANCE = 0.05F;
/*     */   public static final int REINFORCEMENT_ATTEMPTS = 50;
/*     */   public static final int REINFORCEMENT_RANGE_MAX = 40;
/*     */   public static final int REINFORCEMENT_RANGE_MIN = 7;
/*     */   protected static final float BABY_EYE_HEIGHT_ADJUSTMENT = 0.81F;
/*     */   private static final float BREAK_DOOR_CHANCE = 0.1F;
/*     */   private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE;
/*     */   
/*     */   static {
/*  88 */     DOOR_BREAKING_PREDICATE = ($$0 -> ($$0 == Difficulty.HARD));
/*  89 */   } private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal((Mob)this, DOOR_BREAKING_PREDICATE);
/*     */   
/*     */   private boolean canBreakDoors;
/*     */   private int inWaterTime;
/*     */   private int conversionTime;
/*     */   
/*     */   public Zombie(EntityType<? extends Zombie> $$0, Level $$1) {
/*  96 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public Zombie(Level $$0) {
/* 100 */     this(EntityType.ZOMBIE, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 105 */     this.goalSelector.addGoal(4, (Goal)new ZombieAttackTurtleEggGoal(this, 1.0D, 3));
/* 106 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/* 107 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/* 109 */     addBehaviourGoals();
/*     */   }
/*     */   
/*     */   protected void addBehaviourGoals() {
/* 113 */     this.goalSelector.addGoal(2, (Goal)new ZombieAttackGoal(this, 1.0D, false));
/* 114 */     this.goalSelector.addGoal(6, (Goal)new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
/* 115 */     this.goalSelector.addGoal(7, (Goal)new WaterAvoidingRandomStrollGoal(this, 1.0D));
/*     */     
/* 117 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[] { ZombifiedPiglin.class }));
/* 118 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/* 119 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, false));
/* 120 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
/* 121 */     this.targetSelector.addGoal(5, (Goal)new NearestAttackableTargetGoal((Mob)this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 125 */     return Monster.createMonsterAttributes()
/* 126 */       .add(Attributes.FOLLOW_RANGE, 35.0D)
/* 127 */       .add(Attributes.MOVEMENT_SPEED, 0.23000000417232513D)
/* 128 */       .add(Attributes.ATTACK_DAMAGE, 3.0D)
/* 129 */       .add(Attributes.ARMOR, 2.0D)
/* 130 */       .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 135 */     super.defineSynchedData();
/*     */     
/* 137 */     getEntityData().define(DATA_BABY_ID, Boolean.valueOf(false));
/* 138 */     getEntityData().define(DATA_SPECIAL_TYPE_ID, Integer.valueOf(0));
/* 139 */     getEntityData().define(DATA_DROWNED_CONVERSION_ID, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean isUnderWaterConverting() {
/* 143 */     return ((Boolean)getEntityData().get(DATA_DROWNED_CONVERSION_ID)).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean canBreakDoors() {
/* 147 */     return this.canBreakDoors;
/*     */   }
/*     */   
/*     */   public void setCanBreakDoors(boolean $$0) {
/* 151 */     if (supportsBreakDoorGoal() && GoalUtils.hasGroundPathNavigation((Mob)this)) {
/* 152 */       if (this.canBreakDoors != $$0) {
/* 153 */         this.canBreakDoors = $$0;
/* 154 */         ((GroundPathNavigation)getNavigation()).setCanOpenDoors($$0);
/*     */         
/* 156 */         if ($$0) {
/* 157 */           this.goalSelector.addGoal(1, (Goal)this.breakDoorGoal);
/*     */         } else {
/* 159 */           this.goalSelector.removeGoal((Goal)this.breakDoorGoal);
/*     */         }
/*     */       
/*     */       } 
/* 163 */     } else if (this.canBreakDoors) {
/* 164 */       this.goalSelector.removeGoal((Goal)this.breakDoorGoal);
/* 165 */       this.canBreakDoors = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean supportsBreakDoorGoal() {
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBaby() {
/* 176 */     return ((Boolean)getEntityData().get(DATA_BABY_ID)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExperienceReward() {
/* 181 */     if (isBaby()) {
/* 182 */       this.xpReward = (int)(this.xpReward * 2.5D);
/*     */     }
/*     */     
/* 185 */     return super.getExperienceReward();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBaby(boolean $$0) {
/* 190 */     getEntityData().set(DATA_BABY_ID, Boolean.valueOf($$0));
/*     */     
/* 192 */     if (level() != null && !(level()).isClientSide) {
/* 193 */       AttributeInstance $$1 = getAttribute(Attributes.MOVEMENT_SPEED);
/* 194 */       $$1.removeModifier(SPEED_MODIFIER_BABY.getId());
/* 195 */       if ($$0) {
/* 196 */         $$1.addTransientModifier(SPEED_MODIFIER_BABY);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 203 */     if (DATA_BABY_ID.equals($$0)) {
/* 204 */       refreshDimensions();
/*     */     }
/*     */     
/* 207 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */   
/*     */   protected boolean convertsInWater() {
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 216 */     if (!(level()).isClientSide && isAlive() && !isNoAi()) {
/* 217 */       if (isUnderWaterConverting()) {
/* 218 */         this.conversionTime--;
/*     */         
/* 220 */         if (this.conversionTime < 0) {
/* 221 */           doUnderWaterConversion();
/*     */         }
/* 223 */       } else if (convertsInWater()) {
/* 224 */         if (isEyeInFluid(FluidTags.WATER)) {
/* 225 */           this.inWaterTime++;
/*     */           
/* 227 */           if (this.inWaterTime >= 600) {
/* 228 */             startUnderWaterConversion(300);
/*     */           }
/*     */         } else {
/* 231 */           this.inWaterTime = -1;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 236 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 241 */     if (isAlive()) {
/* 242 */       boolean $$0 = (isSunSensitive() && isSunBurnTick());
/* 243 */       if ($$0) {
/* 244 */         ItemStack $$1 = getItemBySlot(EquipmentSlot.HEAD);
/* 245 */         if (!$$1.isEmpty()) {
/* 246 */           if ($$1.isDamageableItem()) {
/* 247 */             $$1.setDamageValue($$1.getDamageValue() + this.random.nextInt(2));
/* 248 */             if ($$1.getDamageValue() >= $$1.getMaxDamage()) {
/* 249 */               broadcastBreakEvent(EquipmentSlot.HEAD);
/* 250 */               setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
/*     */             } 
/*     */           } 
/*     */           
/* 254 */           $$0 = false;
/*     */         } 
/*     */         
/* 257 */         if ($$0) {
/* 258 */           setSecondsOnFire(8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     super.aiStep();
/*     */   }
/*     */   
/*     */   private void startUnderWaterConversion(int $$0) {
/* 267 */     this.conversionTime = $$0;
/* 268 */     getEntityData().set(DATA_DROWNED_CONVERSION_ID, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doUnderWaterConversion() {
/* 273 */     convertToZombieType(EntityType.DROWNED);
/* 274 */     if (!isSilent()) {
/* 275 */       level().levelEvent(null, 1040, blockPosition(), 0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void convertToZombieType(EntityType<? extends Zombie> $$0) {
/* 280 */     Zombie $$1 = (Zombie)convertTo($$0, true);
/* 281 */     if ($$1 != null) {
/* 282 */       $$1.handleAttributes($$1.level().getCurrentDifficultyAt($$1.blockPosition()).getSpecialMultiplier());
/* 283 */       $$1.setCanBreakDoors(($$1.supportsBreakDoorGoal() && canBreakDoors()));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isSunSensitive() {
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 293 */     if (!super.hurt($$0, $$1)) {
/* 294 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 298 */     if (!(level() instanceof ServerLevel)) {
/* 299 */       return false;
/*     */     }
/*     */     
/* 302 */     ServerLevel $$2 = (ServerLevel)level();
/*     */     
/* 304 */     LivingEntity $$3 = getTarget();
/* 305 */     if ($$3 == null && $$0.getEntity() instanceof LivingEntity) {
/* 306 */       $$3 = (LivingEntity)$$0.getEntity();
/*     */     }
/*     */     
/* 309 */     if ($$3 != null && level().getDifficulty() == Difficulty.HARD && this.random.nextFloat() < getAttributeValue(Attributes.SPAWN_REINFORCEMENTS_CHANCE) && level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
/* 310 */       int $$4 = Mth.floor(getX());
/* 311 */       int $$5 = Mth.floor(getY());
/* 312 */       int $$6 = Mth.floor(getZ());
/* 313 */       Zombie $$7 = new Zombie(level());
/*     */       
/* 315 */       for (int $$8 = 0; $$8 < 50; $$8++) {
/* 316 */         int $$9 = $$4 + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
/* 317 */         int $$10 = $$5 + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
/* 318 */         int $$11 = $$6 + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
/*     */         
/* 320 */         BlockPos $$12 = new BlockPos($$9, $$10, $$11);
/* 321 */         EntityType<?> $$13 = $$7.getType();
/* 322 */         SpawnPlacements.Type $$14 = SpawnPlacements.getPlacementType($$13);
/*     */         
/* 324 */         if (NaturalSpawner.isSpawnPositionOk($$14, (LevelReader)level(), $$12, $$13) && 
/* 325 */           SpawnPlacements.checkSpawnRules($$13, (ServerLevelAccessor)$$2, MobSpawnType.REINFORCEMENT, $$12, (level()).random)) {
/*     */           
/* 327 */           $$7.setPos($$9, $$10, $$11);
/*     */ 
/*     */           
/* 330 */           if (!level().hasNearbyAlivePlayer($$9, $$10, $$11, 7.0D) && level().isUnobstructed((Entity)$$7) && level().noCollision((Entity)$$7) && !level().containsAnyLiquid($$7.getBoundingBox())) {
/* 331 */             $$7.setTarget($$3);
/* 332 */             $$7.finalizeSpawn((ServerLevelAccessor)$$2, level().getCurrentDifficultyAt($$7.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
/* 333 */             $$2.addFreshEntityWithPassengers((Entity)$$7);
/*     */             
/* 335 */             getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, AttributeModifier.Operation.ADDITION));
/* 336 */             $$7.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, AttributeModifier.Operation.ADDITION));
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 343 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 348 */     boolean $$1 = super.doHurtTarget($$0);
/*     */     
/* 350 */     if ($$1) {
/* 351 */       float $$2 = level().getCurrentDifficultyAt(blockPosition()).getEffectiveDifficulty();
/*     */ 
/*     */       
/* 354 */       if (getMainHandItem().isEmpty() && 
/* 355 */         isOnFire() && this.random.nextFloat() < $$2 * 0.3F) {
/* 356 */         $$0.setSecondsOnFire(2 * (int)$$2);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 361 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 366 */     return SoundEvents.ZOMBIE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 371 */     return SoundEvents.ZOMBIE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 376 */     return SoundEvents.ZOMBIE_DEATH;
/*     */   }
/*     */   
/*     */   protected SoundEvent getStepSound() {
/* 380 */     return SoundEvents.ZOMBIE_STEP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 385 */     playSound(getStepSound(), 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 390 */     return MobType.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 395 */     super.populateDefaultEquipmentSlots($$0, $$1);
/*     */     
/* 397 */     if ($$0.nextFloat() < ((level().getDifficulty() == Difficulty.HARD) ? 0.05F : 0.01F)) {
/* 398 */       int $$2 = $$0.nextInt(3);
/* 399 */       if ($$2 == 0) {
/* 400 */         setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.IRON_SWORD));
/*     */       } else {
/* 402 */         setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.IRON_SHOVEL));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 409 */     super.addAdditionalSaveData($$0);
/*     */     
/* 411 */     $$0.putBoolean("IsBaby", isBaby());
/* 412 */     $$0.putBoolean("CanBreakDoors", canBreakDoors());
/*     */     
/* 414 */     $$0.putInt("InWaterTime", isInWater() ? this.inWaterTime : -1);
/* 415 */     $$0.putInt("DrownedConversionTime", isUnderWaterConverting() ? this.conversionTime : -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 420 */     super.readAdditionalSaveData($$0);
/*     */     
/* 422 */     setBaby($$0.getBoolean("IsBaby"));
/* 423 */     setCanBreakDoors($$0.getBoolean("CanBreakDoors"));
/*     */     
/* 425 */     this.inWaterTime = $$0.getInt("InWaterTime");
/*     */     
/* 427 */     if ($$0.contains("DrownedConversionTime", 99) && $$0.getInt("DrownedConversionTime") > -1) {
/* 428 */       startUnderWaterConversion($$0.getInt("DrownedConversionTime"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean killedEntity(ServerLevel $$0, LivingEntity $$1) {
/* 434 */     boolean $$2 = super.killedEntity($$0, $$1);
/*     */     
/* 436 */     if (($$0.getDifficulty() == Difficulty.NORMAL || $$0.getDifficulty() == Difficulty.HARD) && $$1 instanceof Villager) { Villager $$3 = (Villager)$$1;
/* 437 */       if ($$0.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
/* 438 */         return $$2;
/*     */       }
/*     */       
/* 441 */       ZombieVillager $$4 = (ZombieVillager)$$3.convertTo(EntityType.ZOMBIE_VILLAGER, false);
/* 442 */       if ($$4 != null) {
/* 443 */         $$4.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$4.blockPosition()), MobSpawnType.CONVERSION, new ZombieGroupData(false, true), null);
/* 444 */         $$4.setVillagerData($$3.getVillagerData());
/* 445 */         $$4.setGossips((Tag)$$3.getGossips().store((DynamicOps)NbtOps.INSTANCE));
/* 446 */         $$4.setTradeOffers($$3.getOffers().createTag());
/* 447 */         $$4.setVillagerXp($$3.getVillagerXp());
/*     */         
/* 449 */         if (!isSilent()) {
/* 450 */           $$0.levelEvent(null, 1026, blockPosition(), 0);
/*     */         }
/* 452 */         $$2 = false;
/*     */       }  }
/*     */     
/* 455 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 460 */     return isBaby() ? 0.93F : 1.74F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHoldItem(ItemStack $$0) {
/* 465 */     if ($$0.is(Items.EGG) && isBaby() && isPassenger()) {
/* 466 */       return false;
/*     */     }
/* 468 */     return super.canHoldItem($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wantsToPickUp(ItemStack $$0) {
/* 473 */     if ($$0.is(Items.GLOW_INK_SAC)) {
/* 474 */       return false;
/*     */     }
/* 476 */     return super.wantsToPickUp($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 482 */     RandomSource $$5 = $$0.getRandom();
/*     */     
/* 484 */     $$3 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/* 485 */     float $$6 = $$1.getSpecialMultiplier();
/*     */     
/* 487 */     setCanPickUpLoot(($$5.nextFloat() < 0.55F * $$6));
/*     */     
/* 489 */     if ($$3 == null) {
/* 490 */       $$3 = new ZombieGroupData(getSpawnAsBabyOdds($$5), true);
/*     */     }
/*     */     
/* 493 */     if ($$3 instanceof ZombieGroupData) { ZombieGroupData $$7 = (ZombieGroupData)$$3;
/*     */       
/* 495 */       if ($$7.isBaby) {
/* 496 */         setBaby(true);
/*     */         
/* 498 */         if ($$7.canSpawnJockey) {
/* 499 */           if ($$5.nextFloat() < 0.05D) {
/* 500 */             List<Chicken> $$8 = $$0.getEntitiesOfClass(Chicken.class, getBoundingBox().inflate(5.0D, 3.0D, 5.0D), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
/*     */             
/* 502 */             if (!$$8.isEmpty()) {
/* 503 */               Chicken $$9 = $$8.get(0);
/* 504 */               $$9.setChickenJockey(true);
/* 505 */               startRiding((Entity)$$9);
/*     */             } 
/* 507 */           } else if ($$5.nextFloat() < 0.05D) {
/* 508 */             Chicken $$10 = (Chicken)EntityType.CHICKEN.create(level());
/* 509 */             if ($$10 != null) {
/* 510 */               $$10.moveTo(getX(), getY(), getZ(), getYRot(), 0.0F);
/* 511 */               $$10.finalizeSpawn($$0, $$1, MobSpawnType.JOCKEY, null, null);
/* 512 */               $$10.setChickenJockey(true);
/* 513 */               startRiding((Entity)$$10);
/*     */ 
/*     */ 
/*     */               
/* 517 */               $$0.addFreshEntity((Entity)$$10);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 523 */       setCanBreakDoors((supportsBreakDoorGoal() && $$5.nextFloat() < $$6 * 0.1F));
/*     */       
/* 525 */       populateDefaultEquipmentSlots($$5, $$1);
/* 526 */       populateDefaultEquipmentEnchantments($$5, $$1); }
/*     */ 
/*     */     
/* 529 */     if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
/* 530 */       LocalDate $$11 = LocalDate.now();
/* 531 */       int $$12 = $$11.get(ChronoField.DAY_OF_MONTH);
/* 532 */       int $$13 = $$11.get(ChronoField.MONTH_OF_YEAR);
/*     */ 
/*     */       
/* 535 */       if ($$13 == 10 && $$12 == 31 && $$5.nextFloat() < 0.25F) {
/*     */         
/* 537 */         setItemSlot(EquipmentSlot.HEAD, new ItemStack(($$5.nextFloat() < 0.1F) ? (ItemLike)Blocks.JACK_O_LANTERN : (ItemLike)Blocks.CARVED_PUMPKIN));
/* 538 */         this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 542 */     handleAttributes($$6);
/*     */     
/* 544 */     return $$3;
/*     */   }
/*     */   
/*     */   public static boolean getSpawnAsBabyOdds(RandomSource $$0) {
/* 548 */     return ($$0.nextFloat() < 0.05F);
/*     */   }
/*     */   
/*     */   protected void handleAttributes(float $$0) {
/* 552 */     randomizeReinforcementsChance();
/* 553 */     getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05000000074505806D, AttributeModifier.Operation.ADDITION));
/* 554 */     double $$1 = this.random.nextDouble() * 1.5D * $$0;
/* 555 */     if ($$1 > 1.0D) {
/* 556 */       getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random zombie-spawn bonus", $$1, AttributeModifier.Operation.MULTIPLY_TOTAL));
/*     */     }
/*     */     
/* 559 */     if (this.random.nextFloat() < $$0 * 0.05F) {
/* 560 */       getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 0.25D + 0.5D, AttributeModifier.Operation.ADDITION));
/* 561 */       getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
/* 562 */       setCanBreakDoors(supportsBreakDoorGoal());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void randomizeReinforcementsChance() {
/* 567 */     getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.random.nextDouble() * 0.10000000149011612D);
/*     */   }
/*     */   
/*     */   public static class ZombieGroupData implements SpawnGroupData {
/*     */     public final boolean isBaby;
/*     */     public final boolean canSpawnJockey;
/*     */     
/*     */     public ZombieGroupData(boolean $$0, boolean $$1) {
/* 575 */       this.isBaby = $$0;
/* 576 */       this.canSpawnJockey = $$1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 582 */     return new Vector3f(0.0F, $$1.height + 0.0625F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 587 */     return -0.7F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/* 592 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/* 593 */     Entity $$3 = $$0.getEntity();
/* 594 */     if ($$3 instanceof Creeper) { Creeper $$4 = (Creeper)$$3;
/* 595 */       if ($$4.canDropMobsSkull()) {
/* 596 */         ItemStack $$5 = getSkull();
/* 597 */         if (!$$5.isEmpty()) {
/* 598 */           $$4.increaseDroppedSkulls();
/* 599 */           spawnAtLocation($$5);
/*     */         } 
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   protected ItemStack getSkull() {
/* 606 */     return new ItemStack((ItemLike)Items.ZOMBIE_HEAD);
/*     */   }
/*     */   
/*     */   private class ZombieAttackTurtleEggGoal extends RemoveBlockGoal {
/*     */     ZombieAttackTurtleEggGoal(PathfinderMob $$0, double $$1, int $$2) {
/* 611 */       super(Blocks.TURTLE_EGG, $$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void playDestroyProgressSound(LevelAccessor $$0, BlockPos $$1) {
/* 616 */       $$0.playSound(null, $$1, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + Zombie.this.random.nextFloat() * 0.2F);
/*     */     }
/*     */ 
/*     */     
/*     */     public void playBreakSound(Level $$0, BlockPos $$1) {
/* 621 */       $$0.playSound(null, $$1, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + $$0.random.nextFloat() * 0.2F);
/*     */     }
/*     */ 
/*     */     
/*     */     public double acceptedDistance() {
/* 626 */       return 1.14D;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Zombie.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */