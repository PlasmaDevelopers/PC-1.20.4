/*     */ package net.minecraft.world.entity.animal.goat;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.InstrumentTags;
/*     */ import net.minecraft.tags.TagKey;
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
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Instrument;
/*     */ import net.minecraft.world.item.InstrumentItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.ItemUtils;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Goat extends Animal {
/*  60 */   public static final EntityDimensions LONG_JUMPING_DIMENSIONS = EntityDimensions.scalable(0.9F, 1.3F).scale(0.7F);
/*     */   
/*     */   private static final int ADULT_ATTACK_DAMAGE = 2;
/*     */   
/*     */   private static final int BABY_ATTACK_DAMAGE = 1;
/*  65 */   protected static final ImmutableList<SensorType<? extends Sensor<? super Goat>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.NEAREST_ADULT, SensorType.HURT_BY, SensorType.GOAT_TEMPTATIONS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATE_RECENTLY, MemoryModuleType.BREED_TARGET, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, (Object[])new MemoryModuleType[] { MemoryModuleType.IS_TEMPTED, MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryModuleType.RAM_TARGET, MemoryModuleType.IS_PANICKING });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int GOAT_FALL_DAMAGE_REDUCTION = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double GOAT_SCREAMING_CHANCE = 0.02D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double UNIHORN_CHANCE = 0.10000000149011612D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private static final EntityDataAccessor<Boolean> DATA_IS_SCREAMING_GOAT = SynchedEntityData.defineId(Goat.class, EntityDataSerializers.BOOLEAN);
/*  97 */   private static final EntityDataAccessor<Boolean> DATA_HAS_LEFT_HORN = SynchedEntityData.defineId(Goat.class, EntityDataSerializers.BOOLEAN);
/*  98 */   private static final EntityDataAccessor<Boolean> DATA_HAS_RIGHT_HORN = SynchedEntityData.defineId(Goat.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private boolean isLoweringHead;
/*     */   private int lowerHeadTick;
/*     */   
/*     */   public Goat(EntityType<? extends Goat> $$0, Level $$1) {
/* 104 */     super($$0, $$1);
/*     */     
/* 106 */     getNavigation().setCanFloat(true);
/* 107 */     setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
/* 108 */     setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
/*     */   }
/*     */   
/*     */   public ItemStack createHorn() {
/* 112 */     RandomSource $$0 = RandomSource.create(getUUID().hashCode());
/* 113 */     TagKey<Instrument> $$1 = isScreamingGoat() ? InstrumentTags.SCREAMING_GOAT_HORNS : InstrumentTags.REGULAR_GOAT_HORNS;
/* 114 */     HolderSet.Named named = BuiltInRegistries.INSTRUMENT.getOrCreateTag($$1);
/* 115 */     return InstrumentItem.create(Items.GOAT_HORN, named.getRandomElement($$0).get());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Goat> brainProvider() {
/* 120 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 125 */     return GoatAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 129 */     return Mob.createMobAttributes()
/* 130 */       .add(Attributes.MAX_HEALTH, 10.0D)
/* 131 */       .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D)
/* 132 */       .add(Attributes.ATTACK_DAMAGE, 2.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void ageBoundaryReached() {
/* 137 */     if (isBaby()) {
/* 138 */       getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.0D);
/* 139 */       removeHorns();
/*     */     } else {
/* 141 */       getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0D);
/* 142 */       addHorns();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int calculateFallDamage(float $$0, float $$1) {
/* 148 */     return super.calculateFallDamage($$0, $$1) - 10;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 153 */     if (isScreamingGoat()) {
/* 154 */       return SoundEvents.GOAT_SCREAMING_AMBIENT;
/*     */     }
/* 156 */     return SoundEvents.GOAT_AMBIENT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 162 */     if (isScreamingGoat()) {
/* 163 */       return SoundEvents.GOAT_SCREAMING_HURT;
/*     */     }
/* 165 */     return SoundEvents.GOAT_HURT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 171 */     if (isScreamingGoat()) {
/* 172 */       return SoundEvents.GOAT_SCREAMING_DEATH;
/*     */     }
/* 174 */     return SoundEvents.GOAT_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 180 */     playSound(SoundEvents.GOAT_STEP, 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected SoundEvent getMilkingSound() {
/* 184 */     if (isScreamingGoat()) {
/* 185 */       return SoundEvents.GOAT_SCREAMING_MILK;
/*     */     }
/* 187 */     return SoundEvents.GOAT_MILK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Goat getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*     */     // Byte code:
/*     */     //   0: getstatic net/minecraft/world/entity/EntityType.GOAT : Lnet/minecraft/world/entity/EntityType;
/*     */     //   3: aload_1
/*     */     //   4: invokevirtual create : (Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;
/*     */     //   7: checkcast net/minecraft/world/entity/animal/goat/Goat
/*     */     //   10: astore_3
/*     */     //   11: aload_3
/*     */     //   12: ifnull -> 94
/*     */     //   15: aload_3
/*     */     //   16: aload_1
/*     */     //   17: invokevirtual getRandom : ()Lnet/minecraft/util/RandomSource;
/*     */     //   20: invokestatic initMemories : (Lnet/minecraft/world/entity/animal/goat/Goat;Lnet/minecraft/util/RandomSource;)V
/*     */     //   23: aload_1
/*     */     //   24: invokevirtual getRandom : ()Lnet/minecraft/util/RandomSource;
/*     */     //   27: invokeinterface nextBoolean : ()Z
/*     */     //   32: ifeq -> 39
/*     */     //   35: aload_0
/*     */     //   36: goto -> 40
/*     */     //   39: aload_2
/*     */     //   40: astore #4
/*     */     //   42: aload #4
/*     */     //   44: instanceof net/minecraft/world/entity/animal/goat/Goat
/*     */     //   47: ifeq -> 65
/*     */     //   50: aload #4
/*     */     //   52: checkcast net/minecraft/world/entity/animal/goat/Goat
/*     */     //   55: astore #6
/*     */     //   57: aload #6
/*     */     //   59: invokevirtual isScreamingGoat : ()Z
/*     */     //   62: ifne -> 81
/*     */     //   65: aload_1
/*     */     //   66: invokevirtual getRandom : ()Lnet/minecraft/util/RandomSource;
/*     */     //   69: invokeinterface nextDouble : ()D
/*     */     //   74: ldc2_w 0.02
/*     */     //   77: dcmpg
/*     */     //   78: ifge -> 85
/*     */     //   81: iconst_1
/*     */     //   82: goto -> 86
/*     */     //   85: iconst_0
/*     */     //   86: istore #5
/*     */     //   88: aload_3
/*     */     //   89: iload #5
/*     */     //   91: invokevirtual setScreamingGoat : (Z)V
/*     */     //   94: aload_3
/*     */     //   95: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #194	-> 0
/*     */     //   #196	-> 11
/*     */     //   #197	-> 15
/*     */     //   #198	-> 23
/*     */     //   #199	-> 42
/*     */     //   #200	-> 88
/*     */     //   #203	-> 94
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	96	0	this	Lnet/minecraft/world/entity/animal/goat/Goat;
/*     */     //   0	96	1	$$0	Lnet/minecraft/server/level/ServerLevel;
/*     */     //   0	96	2	$$1	Lnet/minecraft/world/entity/AgeableMob;
/*     */     //   11	85	3	$$2	Lnet/minecraft/world/entity/animal/goat/Goat;
/*     */     //   42	52	4	$$3	Lnet/minecraft/world/entity/AgeableMob;
/*     */     //   57	8	6	$$4	Lnet/minecraft/world/entity/animal/goat/Goat;
/*     */     //   88	6	5	$$5	Z
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Goat> getBrain() {
/* 208 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 213 */     level().getProfiler().push("goatBrain");
/* 214 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 215 */     level().getProfiler().pop();
/*     */     
/* 217 */     level().getProfiler().push("goatActivityUpdate");
/* 218 */     GoatAi.updateActivity(this);
/* 219 */     level().getProfiler().pop();
/*     */     
/* 221 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 226 */     return 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYHeadRot(float $$0) {
/* 231 */     int $$1 = getMaxHeadYRot();
/* 232 */     float $$2 = Mth.degreesDifference(this.yBodyRot, $$0);
/* 233 */     float $$3 = Mth.clamp($$2, -$$1, $$1);
/*     */     
/* 235 */     super.setYHeadRot(this.yBodyRot + $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getEatingSound(ItemStack $$0) {
/* 240 */     return isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_EAT : SoundEvents.GOAT_EAT;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 245 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 246 */     if ($$2.is(Items.BUCKET) && !isBaby()) {
/* 247 */       $$0.playSound(getMilkingSound(), 1.0F, 1.0F);
/* 248 */       ItemStack $$3 = ItemUtils.createFilledResult($$2, $$0, Items.MILK_BUCKET.getDefaultInstance());
/* 249 */       $$0.setItemInHand($$1, $$3);
/* 250 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 253 */     InteractionResult $$4 = super.mobInteract($$0, $$1);
/* 254 */     if ($$4.consumesAction() && isFood($$2)) {
/* 255 */       level().playSound(null, (Entity)this, getEatingSound($$2), SoundSource.NEUTRAL, 1.0F, Mth.randomBetween((level()).random, 0.8F, 1.2F));
/*     */     }
/*     */     
/* 258 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 263 */     RandomSource $$5 = $$0.getRandom();
/* 264 */     GoatAi.initMemories(this, $$5);
/*     */     
/* 266 */     setScreamingGoat(($$5.nextDouble() < 0.02D));
/* 267 */     ageBoundaryReached();
/* 268 */     if (!isBaby() && $$5.nextFloat() < 0.10000000149011612D) {
/* 269 */       EntityDataAccessor<Boolean> $$6 = $$5.nextBoolean() ? DATA_HAS_LEFT_HORN : DATA_HAS_RIGHT_HORN;
/* 270 */       this.entityData.set($$6, Boolean.valueOf(false));
/*     */     } 
/*     */     
/* 273 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 278 */     super.sendDebugPackets();
/* 279 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 284 */     return ($$0 == Pose.LONG_JUMPING) ? LONG_JUMPING_DIMENSIONS.scale(getScale()) : super.getDimensions($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 289 */     super.addAdditionalSaveData($$0);
/*     */     
/* 291 */     $$0.putBoolean("IsScreamingGoat", isScreamingGoat());
/* 292 */     $$0.putBoolean("HasLeftHorn", hasLeftHorn());
/* 293 */     $$0.putBoolean("HasRightHorn", hasRightHorn());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 298 */     super.readAdditionalSaveData($$0);
/*     */     
/* 300 */     setScreamingGoat($$0.getBoolean("IsScreamingGoat"));
/* 301 */     this.entityData.set(DATA_HAS_LEFT_HORN, Boolean.valueOf($$0.getBoolean("HasLeftHorn")));
/* 302 */     this.entityData.set(DATA_HAS_RIGHT_HORN, Boolean.valueOf($$0.getBoolean("HasRightHorn")));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 307 */     if ($$0 == 58) {
/* 308 */       this.isLoweringHead = true;
/* 309 */     } else if ($$0 == 59) {
/* 310 */       this.isLoweringHead = false;
/*     */     } else {
/* 312 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 318 */     if (this.isLoweringHead) {
/* 319 */       this.lowerHeadTick++;
/*     */     } else {
/* 321 */       this.lowerHeadTick -= 2;
/*     */     } 
/* 323 */     this.lowerHeadTick = Mth.clamp(this.lowerHeadTick, 0, 20);
/*     */     
/* 325 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 330 */     super.defineSynchedData();
/* 331 */     this.entityData.define(DATA_IS_SCREAMING_GOAT, Boolean.valueOf(false));
/* 332 */     this.entityData.define(DATA_HAS_LEFT_HORN, Boolean.valueOf(true));
/* 333 */     this.entityData.define(DATA_HAS_RIGHT_HORN, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public boolean hasLeftHorn() {
/* 337 */     return ((Boolean)this.entityData.get(DATA_HAS_LEFT_HORN)).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean hasRightHorn() {
/* 341 */     return ((Boolean)this.entityData.get(DATA_HAS_RIGHT_HORN)).booleanValue();
/*     */   }
/*     */   public boolean dropHorn() {
/*     */     EntityDataAccessor<Boolean> $$4;
/* 345 */     boolean $$0 = hasLeftHorn();
/* 346 */     boolean $$1 = hasRightHorn();
/*     */     
/* 348 */     if (!$$0 && !$$1) {
/* 349 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 354 */     if (!$$0) {
/* 355 */       EntityDataAccessor<Boolean> $$2 = DATA_HAS_RIGHT_HORN;
/* 356 */     } else if (!$$1) {
/* 357 */       EntityDataAccessor<Boolean> $$3 = DATA_HAS_LEFT_HORN;
/*     */     } else {
/* 359 */       $$4 = this.random.nextBoolean() ? DATA_HAS_LEFT_HORN : DATA_HAS_RIGHT_HORN;
/*     */     } 
/* 361 */     this.entityData.set($$4, Boolean.valueOf(false));
/*     */     
/* 363 */     Vec3 $$5 = position();
/* 364 */     ItemStack $$6 = createHorn();
/* 365 */     double $$7 = Mth.randomBetween(this.random, -0.2F, 0.2F);
/* 366 */     double $$8 = Mth.randomBetween(this.random, 0.3F, 0.7F);
/* 367 */     double $$9 = Mth.randomBetween(this.random, -0.2F, 0.2F);
/* 368 */     ItemEntity $$10 = new ItemEntity(level(), $$5.x(), $$5.y(), $$5.z(), $$6, $$7, $$8, $$9);
/* 369 */     level().addFreshEntity((Entity)$$10);
/* 370 */     return true;
/*     */   }
/*     */   
/*     */   public void addHorns() {
/* 374 */     this.entityData.set(DATA_HAS_LEFT_HORN, Boolean.valueOf(true));
/* 375 */     this.entityData.set(DATA_HAS_RIGHT_HORN, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void removeHorns() {
/* 379 */     this.entityData.set(DATA_HAS_LEFT_HORN, Boolean.valueOf(false));
/* 380 */     this.entityData.set(DATA_HAS_RIGHT_HORN, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean isScreamingGoat() {
/* 384 */     return ((Boolean)this.entityData.get(DATA_IS_SCREAMING_GOAT)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setScreamingGoat(boolean $$0) {
/* 388 */     this.entityData.set(DATA_IS_SCREAMING_GOAT, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public float getRammingXHeadRot() {
/* 392 */     return this.lowerHeadTick / 20.0F * 30.0F * 0.017453292F;
/*     */   }
/*     */   
/*     */   public static boolean checkGoatSpawnRules(EntityType<? extends Animal> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 396 */     return ($$1.getBlockState($$3.below()).is(BlockTags.GOATS_SPAWNABLE_ON) && 
/* 397 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 402 */     return new Vector3f(0.0F, $$1.height - 0.1875F * $$2, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\goat\Goat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */