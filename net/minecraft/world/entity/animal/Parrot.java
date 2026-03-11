/*     */ package net.minecraft.world.entity.animal;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.Difficulty;
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
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.FlyingMoveControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowMobGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Parrot extends ShoulderRidingEntity implements VariantHolder<Parrot.Variant>, FlyingAnimal {
/*  77 */   private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Parrot.class, EntityDataSerializers.INT);
/*  78 */   private static final Predicate<Mob> NOT_PARROT_PREDICATE = new Predicate<Mob>()
/*     */     {
/*     */       public boolean test(@Nullable Mob $$0) {
/*  81 */         return ($$0 != null && Parrot.MOB_SOUND_MAP.containsKey($$0.getType()));
/*     */       }
/*     */     };
/*     */   
/*  85 */   private static final Item POISONOUS_FOOD = Items.COOKIE;
/*  86 */   private static final Set<Item> TAME_FOOD = Sets.newHashSet((Object[])new Item[] { Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD }); static final Map<EntityType<?>, SoundEvent> MOB_SOUND_MAP; public float flap;
/*     */   static {
/*  88 */     MOB_SOUND_MAP = (Map<EntityType<?>, SoundEvent>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put(EntityType.BLAZE, SoundEvents.PARROT_IMITATE_BLAZE);
/*     */           $$0.put(EntityType.BREEZE, SoundEvents.PARROT_IMITATE_BREEZE);
/*     */           $$0.put(EntityType.CAVE_SPIDER, SoundEvents.PARROT_IMITATE_SPIDER);
/*     */           $$0.put(EntityType.CREEPER, SoundEvents.PARROT_IMITATE_CREEPER);
/*     */           $$0.put(EntityType.DROWNED, SoundEvents.PARROT_IMITATE_DROWNED);
/*     */           $$0.put(EntityType.ELDER_GUARDIAN, SoundEvents.PARROT_IMITATE_ELDER_GUARDIAN);
/*     */           $$0.put(EntityType.ENDER_DRAGON, SoundEvents.PARROT_IMITATE_ENDER_DRAGON);
/*     */           $$0.put(EntityType.ENDERMITE, SoundEvents.PARROT_IMITATE_ENDERMITE);
/*     */           $$0.put(EntityType.EVOKER, SoundEvents.PARROT_IMITATE_EVOKER);
/*     */           $$0.put(EntityType.GHAST, SoundEvents.PARROT_IMITATE_GHAST);
/*     */           $$0.put(EntityType.GUARDIAN, SoundEvents.PARROT_IMITATE_GUARDIAN);
/*     */           $$0.put(EntityType.HOGLIN, SoundEvents.PARROT_IMITATE_HOGLIN);
/*     */           $$0.put(EntityType.HUSK, SoundEvents.PARROT_IMITATE_HUSK);
/*     */           $$0.put(EntityType.ILLUSIONER, SoundEvents.PARROT_IMITATE_ILLUSIONER);
/*     */           $$0.put(EntityType.MAGMA_CUBE, SoundEvents.PARROT_IMITATE_MAGMA_CUBE);
/*     */           $$0.put(EntityType.PHANTOM, SoundEvents.PARROT_IMITATE_PHANTOM);
/*     */           $$0.put(EntityType.PIGLIN, SoundEvents.PARROT_IMITATE_PIGLIN);
/*     */           $$0.put(EntityType.PIGLIN_BRUTE, SoundEvents.PARROT_IMITATE_PIGLIN_BRUTE);
/*     */           $$0.put(EntityType.PILLAGER, SoundEvents.PARROT_IMITATE_PILLAGER);
/*     */           $$0.put(EntityType.RAVAGER, SoundEvents.PARROT_IMITATE_RAVAGER);
/*     */           $$0.put(EntityType.SHULKER, SoundEvents.PARROT_IMITATE_SHULKER);
/*     */           $$0.put(EntityType.SILVERFISH, SoundEvents.PARROT_IMITATE_SILVERFISH);
/*     */           $$0.put(EntityType.SKELETON, SoundEvents.PARROT_IMITATE_SKELETON);
/*     */           $$0.put(EntityType.SLIME, SoundEvents.PARROT_IMITATE_SLIME);
/*     */           $$0.put(EntityType.SPIDER, SoundEvents.PARROT_IMITATE_SPIDER);
/*     */           $$0.put(EntityType.STRAY, SoundEvents.PARROT_IMITATE_STRAY);
/*     */           $$0.put(EntityType.VEX, SoundEvents.PARROT_IMITATE_VEX);
/*     */           $$0.put(EntityType.VINDICATOR, SoundEvents.PARROT_IMITATE_VINDICATOR);
/*     */           $$0.put(EntityType.WARDEN, SoundEvents.PARROT_IMITATE_WARDEN);
/*     */           $$0.put(EntityType.WITCH, SoundEvents.PARROT_IMITATE_WITCH);
/*     */           $$0.put(EntityType.WITHER, SoundEvents.PARROT_IMITATE_WITHER);
/*     */           $$0.put(EntityType.WITHER_SKELETON, SoundEvents.PARROT_IMITATE_WITHER_SKELETON);
/*     */           $$0.put(EntityType.ZOGLIN, SoundEvents.PARROT_IMITATE_ZOGLIN);
/*     */           $$0.put(EntityType.ZOMBIE, SoundEvents.PARROT_IMITATE_ZOMBIE);
/*     */           $$0.put(EntityType.ZOMBIE_VILLAGER, SoundEvents.PARROT_IMITATE_ZOMBIE_VILLAGER);
/*     */         });
/*     */   }
/*     */   
/*     */   public float flapSpeed;
/*     */   public float oFlapSpeed;
/*     */   public float oFlap;
/* 130 */   private float flapping = 1.0F;
/* 131 */   private float nextFlap = 1.0F;
/*     */   private boolean partyParrot;
/*     */   @Nullable
/*     */   private BlockPos jukebox;
/*     */   
/*     */   public enum Variant
/*     */     implements StringRepresentable {
/* 138 */     RED_BLUE(0, "red_blue"),
/* 139 */     BLUE(1, "blue"),
/* 140 */     GREEN(2, "green"),
/* 141 */     YELLOW_BLUE(3, "yellow_blue"),
/* 142 */     GRAY(4, "gray");
/*     */ 
/*     */     
/* 145 */     public static final Codec<Variant> CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */     
/* 147 */     private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.CLAMP); final int id; private final String name;
/*     */     static {
/*     */     
/*     */     }
/*     */     Variant(int $$0, String $$1) {
/* 152 */       this.id = $$0;
/* 153 */       this.name = $$1;
/*     */     }
/*     */     
/*     */     public int getId() {
/* 157 */       return this.id;
/*     */     }
/*     */     
/*     */     public static Variant byId(int $$0) {
/* 161 */       return BY_ID.apply($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 166 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public Parrot(EntityType<? extends Parrot> $$0, Level $$1) {
/* 171 */     super((EntityType)$$0, $$1);
/* 172 */     this.moveControl = (MoveControl)new FlyingMoveControl((Mob)this, 10, false);
/*     */ 
/*     */ 
/*     */     
/* 176 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
/* 177 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
/* 178 */     setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/* 184 */     setVariant((Variant)Util.getRandom((Object[])Variant.values(), $$0.getRandom()));
/*     */     
/* 186 */     if ($$3 == null) {
/* 187 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(false);
/*     */     }
/*     */     
/* 190 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBaby() {
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 200 */     this.goalSelector.addGoal(0, (Goal)new PanicGoal((PathfinderMob)this, 1.25D));
/* 201 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/* 202 */     this.goalSelector.addGoal(1, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/* 203 */     this.goalSelector.addGoal(2, (Goal)new SitWhenOrderedToGoal(this));
/* 204 */     this.goalSelector.addGoal(2, (Goal)new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
/* 205 */     this.goalSelector.addGoal(2, (Goal)new ParrotWanderGoal((PathfinderMob)this, 1.0D));
/* 206 */     this.goalSelector.addGoal(3, (Goal)new LandOnOwnersShoulderGoal(this));
/* 207 */     this.goalSelector.addGoal(3, (Goal)new FollowMobGoal((Mob)this, 1.0D, 3.0F, 7.0F));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 211 */     return Mob.createMobAttributes()
/* 212 */       .add(Attributes.MAX_HEALTH, 6.0D)
/* 213 */       .add(Attributes.FLYING_SPEED, 0.4000000059604645D)
/* 214 */       .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 219 */     FlyingPathNavigation $$1 = new FlyingPathNavigation((Mob)this, $$0);
/* 220 */     $$1.setCanOpenDoors(false);
/* 221 */     $$1.setCanFloat(true);
/* 222 */     $$1.setCanPassDoors(true);
/* 223 */     return (PathNavigation)$$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 228 */     return $$1.height * 0.6F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 233 */     if (this.jukebox == null || !this.jukebox.closerToCenterThan((Position)position(), 3.46D) || !level().getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {
/* 234 */       this.partyParrot = false;
/* 235 */       this.jukebox = null;
/*     */     } 
/*     */     
/* 238 */     if ((level()).random.nextInt(400) == 0) {
/* 239 */       imitateNearbyMobs(level(), (Entity)this);
/*     */     }
/*     */     
/* 242 */     super.aiStep();
/*     */     
/* 244 */     calculateFlapping();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRecordPlayingNearby(BlockPos $$0, boolean $$1) {
/* 249 */     this.jukebox = $$0;
/* 250 */     this.partyParrot = $$1;
/*     */   }
/*     */   
/*     */   public boolean isPartyParrot() {
/* 254 */     return this.partyParrot;
/*     */   }
/*     */   
/*     */   private void calculateFlapping() {
/* 258 */     this.oFlap = this.flap;
/* 259 */     this.oFlapSpeed = this.flapSpeed;
/*     */     
/* 261 */     this.flapSpeed += ((onGround() || isPassenger()) ? -1 : 4) * 0.3F;
/* 262 */     this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
/*     */     
/* 264 */     if (!onGround() && this.flapping < 1.0F) {
/* 265 */       this.flapping = 1.0F;
/*     */     }
/* 267 */     this.flapping *= 0.9F;
/*     */     
/* 269 */     Vec3 $$0 = getDeltaMovement();
/* 270 */     if (!onGround() && $$0.y < 0.0D) {
/* 271 */       setDeltaMovement($$0.multiply(1.0D, 0.6D, 1.0D));
/*     */     }
/*     */     
/* 274 */     this.flap += this.flapping * 2.0F;
/*     */   }
/*     */   
/*     */   public static boolean imitateNearbyMobs(Level $$0, Entity $$1) {
/* 278 */     if (!$$1.isAlive() || $$1.isSilent() || $$0.random.nextInt(2) != 0) {
/* 279 */       return false;
/*     */     }
/*     */     
/* 282 */     List<Mob> $$2 = $$0.getEntitiesOfClass(Mob.class, $$1.getBoundingBox().inflate(20.0D), NOT_PARROT_PREDICATE);
/* 283 */     if (!$$2.isEmpty()) {
/* 284 */       Mob $$3 = $$2.get($$0.random.nextInt($$2.size()));
/* 285 */       if (!$$3.isSilent()) {
/* 286 */         SoundEvent $$4 = getImitatedSound($$3.getType());
/* 287 */         $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), $$4, $$1.getSoundSource(), 0.7F, getPitch($$0.random));
/*     */         
/* 289 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 298 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*     */     
/* 300 */     if (!isTame() && TAME_FOOD.contains($$2.getItem())) {
/* 301 */       if (!($$0.getAbilities()).instabuild) {
/* 302 */         $$2.shrink(1);
/*     */       }
/*     */       
/* 305 */       if (!isSilent()) {
/* 306 */         level().playSound(null, getX(), getY(), getZ(), SoundEvents.PARROT_EAT, getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*     */       }
/*     */       
/* 309 */       if (!(level()).isClientSide) {
/* 310 */         if (this.random.nextInt(10) == 0) {
/* 311 */           tame($$0);
/* 312 */           level().broadcastEntityEvent((Entity)this, (byte)7);
/*     */         } else {
/* 314 */           level().broadcastEntityEvent((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 318 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/* 319 */     }  if ($$2.is(POISONOUS_FOOD)) {
/* 320 */       if (!($$0.getAbilities()).instabuild) {
/* 321 */         $$2.shrink(1);
/*     */       }
/*     */       
/* 324 */       addEffect(new MobEffectInstance(MobEffects.POISON, 900));
/* 325 */       if ($$0.isCreative() || !isInvulnerable()) {
/* 326 */         hurt(damageSources().playerAttack($$0), Float.MAX_VALUE);
/*     */       }
/*     */       
/* 329 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/* 330 */     }  if (!isFlying() && isTame() && isOwnedBy((LivingEntity)$$0)) {
/* 331 */       if (!(level()).isClientSide) {
/* 332 */         setOrderedToSit(!isOrderedToSit());
/*     */       }
/* 334 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 337 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 342 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean checkParrotSpawnRules(EntityType<Parrot> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 346 */     return ($$1.getBlockState($$3.below()).is(BlockTags.PARROTS_SPAWNABLE_ON) && 
/* 347 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 363 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 368 */     return $$0.hurt(damageSources().mobAttack((LivingEntity)this), 3.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SoundEvent getAmbientSound() {
/* 374 */     return getAmbient(level(), (level()).random);
/*     */   }
/*     */   
/*     */   public static SoundEvent getAmbient(Level $$0, RandomSource $$1) {
/* 378 */     if ($$0.getDifficulty() != Difficulty.PEACEFUL && $$1.nextInt(1000) == 0) {
/*     */       
/* 380 */       List<EntityType<?>> $$2 = Lists.newArrayList(MOB_SOUND_MAP.keySet());
/* 381 */       return getImitatedSound($$2.get($$1.nextInt($$2.size())));
/*     */     } 
/* 383 */     return SoundEvents.PARROT_AMBIENT;
/*     */   }
/*     */   
/*     */   private static SoundEvent getImitatedSound(EntityType<?> $$0) {
/* 387 */     return MOB_SOUND_MAP.getOrDefault($$0, SoundEvents.PARROT_AMBIENT);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 392 */     return SoundEvents.PARROT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 397 */     return SoundEvents.PARROT_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 402 */     playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFlapping() {
/* 407 */     return (this.flyDist > this.nextFlap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFlap() {
/* 412 */     playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
/* 413 */     this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVoicePitch() {
/* 418 */     return getPitch(this.random);
/*     */   }
/*     */   
/*     */   public static float getPitch(RandomSource $$0) {
/* 422 */     return ($$0.nextFloat() - $$0.nextFloat()) * 0.2F + 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 427 */     return SoundSource.NEUTRAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPushable() {
/* 432 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPush(Entity $$0) {
/* 437 */     if ($$0 instanceof Player) {
/*     */       return;
/*     */     }
/* 440 */     super.doPush($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 445 */     if (isInvulnerableTo($$0)) {
/* 446 */       return false;
/*     */     }
/* 448 */     if (!(level()).isClientSide) {
/* 449 */       setOrderedToSit(false);
/*     */     }
/*     */     
/* 452 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 457 */     return Variant.byId(((Integer)this.entityData.get(DATA_VARIANT_ID)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Variant $$0) {
/* 462 */     this.entityData.set(DATA_VARIANT_ID, Integer.valueOf($$0.id));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 467 */     super.defineSynchedData();
/* 468 */     this.entityData.define(DATA_VARIANT_ID, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 473 */     super.addAdditionalSaveData($$0);
/* 474 */     $$0.putInt("Variant", (getVariant()).id);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 479 */     super.readAdditionalSaveData($$0);
/* 480 */     setVariant(Variant.byId($$0.getInt("Variant")));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlying() {
/* 485 */     return !onGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 490 */     return new Vec3(0.0D, (0.5F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 495 */     return new Vector3f(0.0F, $$1.height - 0.4375F * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   private static class ParrotWanderGoal extends WaterAvoidingRandomFlyingGoal {
/*     */     public ParrotWanderGoal(PathfinderMob $$0, double $$1) {
/* 500 */       super($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected Vec3 getPosition() {
/* 506 */       Vec3 $$0 = null;
/* 507 */       if (this.mob.isInWater()) {
/* 508 */         $$0 = LandRandomPos.getPos(this.mob, 15, 15);
/*     */       }
/* 510 */       if (this.mob.getRandom().nextFloat() >= this.probability) {
/* 511 */         $$0 = getTreePos();
/*     */       }
/* 513 */       return ($$0 == null) ? super.getPosition() : $$0;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private Vec3 getTreePos() {
/* 518 */       BlockPos $$0 = this.mob.blockPosition();
/*     */       
/* 520 */       BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/* 521 */       BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
/* 522 */       Iterable<BlockPos> $$3 = BlockPos.betweenClosed(
/* 523 */           Mth.floor(this.mob.getX() - 3.0D), 
/* 524 */           Mth.floor(this.mob.getY() - 6.0D), 
/* 525 */           Mth.floor(this.mob.getZ() - 3.0D), 
/* 526 */           Mth.floor(this.mob.getX() + 3.0D), 
/* 527 */           Mth.floor(this.mob.getY() + 6.0D), 
/* 528 */           Mth.floor(this.mob.getZ() + 3.0D));
/*     */ 
/*     */       
/* 531 */       for (BlockPos $$4 : $$3) {
/* 532 */         if ($$0.equals($$4)) {
/*     */           continue;
/*     */         }
/*     */         
/* 536 */         BlockState $$5 = this.mob.level().getBlockState((BlockPos)$$2.setWithOffset((Vec3i)$$4, Direction.DOWN));
/* 537 */         boolean $$6 = ($$5.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock || $$5.is(BlockTags.LOGS));
/* 538 */         if ($$6 && this.mob.level().isEmptyBlock($$4) && this.mob.level().isEmptyBlock((BlockPos)$$1.setWithOffset((Vec3i)$$4, Direction.UP))) {
/* 539 */           return Vec3.atBottomCenterOf((Vec3i)$$4);
/*     */         }
/*     */       } 
/*     */       
/* 543 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Parrot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */