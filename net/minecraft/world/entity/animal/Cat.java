/*     */ package net.minecraft.world.entity.animal;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.CatVariantTags;
/*     */ import net.minecraft.tags.StructureTags;
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
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.TamableAnimal;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.CatLieOnBedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.DyeItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.BedBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Cat extends TamableAnimal implements VariantHolder<CatVariant> {
/*  83 */   private static final Ingredient TEMPT_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.COD, (ItemLike)Items.SALMON }); public static final double TEMPT_SPEED_MOD = 0.6D; public static final double WALK_SPEED_MOD = 0.8D;
/*     */   public static final double SPRINT_SPEED_MOD = 1.33D;
/*  85 */   private static final EntityDataAccessor<CatVariant> DATA_VARIANT_ID = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.CAT_VARIANT);
/*  86 */   private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.BOOLEAN);
/*  87 */   private static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.BOOLEAN);
/*  88 */   private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.INT);
/*     */   
/*     */   private CatAvoidEntityGoal<Player> avoidPlayersGoal;
/*     */   
/*     */   @Nullable
/*     */   private TemptGoal temptGoal;
/*     */   private float lieDownAmount;
/*     */   private float lieDownAmountO;
/*     */   private float lieDownAmountTail;
/*     */   private float lieDownAmountOTail;
/*     */   private float relaxStateOneAmount;
/*     */   private float relaxStateOneAmountO;
/*     */   
/*     */   public Cat(EntityType<? extends Cat> $$0, Level $$1) {
/* 102 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public ResourceLocation getResourceLocation() {
/* 106 */     return getVariant().texture();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 111 */     this.temptGoal = new CatTemptGoal(this, 0.6D, TEMPT_INGREDIENT, true);
/*     */     
/* 113 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/* 114 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 1.5D));
/* 115 */     this.goalSelector.addGoal(2, (Goal)new SitWhenOrderedToGoal(this));
/* 116 */     this.goalSelector.addGoal(3, new CatRelaxOnOwnerGoal(this));
/* 117 */     this.goalSelector.addGoal(4, (Goal)this.temptGoal);
/* 118 */     this.goalSelector.addGoal(5, (Goal)new CatLieOnBedGoal(this, 1.1D, 8));
/* 119 */     this.goalSelector.addGoal(6, (Goal)new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
/* 120 */     this.goalSelector.addGoal(7, (Goal)new CatSitOnBlockGoal(this, 0.8D));
/* 121 */     this.goalSelector.addGoal(8, (Goal)new LeapAtTargetGoal((Mob)this, 0.3F));
/* 122 */     this.goalSelector.addGoal(9, (Goal)new OcelotAttackGoal((Mob)this));
/* 123 */     this.goalSelector.addGoal(10, (Goal)new BreedGoal((Animal)this, 0.8D));
/* 124 */     this.goalSelector.addGoal(11, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.8D, 1.0000001E-5F));
/* 125 */     this.goalSelector.addGoal(12, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 10.0F));
/*     */     
/* 127 */     this.targetSelector.addGoal(1, (Goal)new NonTameRandomTargetGoal(this, Rabbit.class, false, null));
/* 128 */     this.targetSelector.addGoal(1, (Goal)new NonTameRandomTargetGoal(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
/*     */   }
/*     */ 
/*     */   
/*     */   public CatVariant getVariant() {
/* 133 */     return (CatVariant)this.entityData.get(DATA_VARIANT_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(CatVariant $$0) {
/* 138 */     this.entityData.set(DATA_VARIANT_ID, $$0);
/*     */   }
/*     */   
/*     */   public void setLying(boolean $$0) {
/* 142 */     this.entityData.set(IS_LYING, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean isLying() {
/* 146 */     return ((Boolean)this.entityData.get(IS_LYING)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setRelaxStateOne(boolean $$0) {
/* 150 */     this.entityData.set(RELAX_STATE_ONE, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean isRelaxStateOne() {
/* 154 */     return ((Boolean)this.entityData.get(RELAX_STATE_ONE)).booleanValue();
/*     */   }
/*     */   
/*     */   public DyeColor getCollarColor() {
/* 158 */     return DyeColor.byId(((Integer)this.entityData.get(DATA_COLLAR_COLOR)).intValue());
/*     */   }
/*     */   
/*     */   public void setCollarColor(DyeColor $$0) {
/* 162 */     this.entityData.set(DATA_COLLAR_COLOR, Integer.valueOf($$0.getId()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 167 */     super.defineSynchedData();
/*     */     
/* 169 */     this.entityData.define(DATA_VARIANT_ID, BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.BLACK));
/* 170 */     this.entityData.define(IS_LYING, Boolean.valueOf(false));
/* 171 */     this.entityData.define(RELAX_STATE_ONE, Boolean.valueOf(false));
/* 172 */     this.entityData.define(DATA_COLLAR_COLOR, Integer.valueOf(DyeColor.RED.getId()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 177 */     super.addAdditionalSaveData($$0);
/* 178 */     $$0.putString("variant", BuiltInRegistries.CAT_VARIANT.getKey(getVariant()).toString());
/* 179 */     $$0.putByte("CollarColor", (byte)getCollarColor().getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 184 */     super.readAdditionalSaveData($$0);
/* 185 */     CatVariant $$1 = (CatVariant)BuiltInRegistries.CAT_VARIANT.get(ResourceLocation.tryParse($$0.getString("variant")));
/* 186 */     if ($$1 != null) {
/* 187 */       setVariant($$1);
/*     */     }
/* 189 */     if ($$0.contains("CollarColor", 99)) {
/* 190 */       setCollarColor(DyeColor.byId($$0.getInt("CollarColor")));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void customServerAiStep() {
/* 196 */     if (getMoveControl().hasWanted()) {
/* 197 */       double $$0 = getMoveControl().getSpeedModifier();
/* 198 */       if ($$0 == 0.6D) {
/* 199 */         setPose(Pose.CROUCHING);
/* 200 */         setSprinting(false);
/* 201 */       } else if ($$0 == 1.33D) {
/* 202 */         setPose(Pose.STANDING);
/* 203 */         setSprinting(true);
/*     */       } else {
/* 205 */         setPose(Pose.STANDING);
/* 206 */         setSprinting(false);
/*     */       } 
/*     */     } else {
/* 209 */       setPose(Pose.STANDING);
/* 210 */       setSprinting(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 217 */     if (isTame()) {
/* 218 */       if (isInLove()) {
/* 219 */         return SoundEvents.CAT_PURR;
/*     */       }
/* 221 */       if (this.random.nextInt(4) == 0) {
/* 222 */         return SoundEvents.CAT_PURREOW;
/*     */       }
/* 224 */       return SoundEvents.CAT_AMBIENT;
/*     */     } 
/*     */     
/* 227 */     return SoundEvents.CAT_STRAY_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmbientSoundInterval() {
/* 232 */     return 120;
/*     */   }
/*     */   
/*     */   public void hiss() {
/* 236 */     playSound(SoundEvents.CAT_HISS, getSoundVolume(), getVoicePitch());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 241 */     return SoundEvents.CAT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 246 */     return SoundEvents.CAT_DEATH;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 250 */     return Mob.createMobAttributes()
/* 251 */       .add(Attributes.MAX_HEALTH, 10.0D)
/* 252 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 253 */       .add(Attributes.ATTACK_DAMAGE, 3.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void usePlayerItem(Player $$0, InteractionHand $$1, ItemStack $$2) {
/* 258 */     if (isFood($$2)) {
/* 259 */       playSound(SoundEvents.CAT_EAT, 1.0F, 1.0F);
/*     */     }
/* 261 */     super.usePlayerItem($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private float getAttackDamage() {
/* 265 */     return (float)getAttributeValue(Attributes.ATTACK_DAMAGE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 270 */     return $$0.hurt(damageSources().mobAttack((LivingEntity)this), getAttackDamage());
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 275 */     super.tick();
/*     */     
/* 277 */     if (this.temptGoal != null && this.temptGoal.isRunning() && !isTame() && this.tickCount % 100 == 0) {
/* 278 */       playSound(SoundEvents.CAT_BEG_FOR_FOOD, 1.0F, 1.0F);
/*     */     }
/* 280 */     handleLieDown();
/*     */   }
/*     */   
/*     */   private void handleLieDown() {
/* 284 */     if ((isLying() || isRelaxStateOne()) && this.tickCount % 5 == 0) {
/* 285 */       playSound(SoundEvents.CAT_PURR, 0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), 1.0F);
/*     */     }
/* 287 */     updateLieDownAmount();
/* 288 */     updateRelaxStateOneAmount();
/*     */   }
/*     */   
/*     */   private void updateLieDownAmount() {
/* 292 */     this.lieDownAmountO = this.lieDownAmount;
/* 293 */     this.lieDownAmountOTail = this.lieDownAmountTail;
/* 294 */     if (isLying()) {
/* 295 */       this.lieDownAmount = Math.min(1.0F, this.lieDownAmount + 0.15F);
/* 296 */       this.lieDownAmountTail = Math.min(1.0F, this.lieDownAmountTail + 0.08F);
/*     */     } else {
/* 298 */       this.lieDownAmount = Math.max(0.0F, this.lieDownAmount - 0.22F);
/* 299 */       this.lieDownAmountTail = Math.max(0.0F, this.lieDownAmountTail - 0.13F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateRelaxStateOneAmount() {
/* 304 */     this.relaxStateOneAmountO = this.relaxStateOneAmount;
/* 305 */     if (isRelaxStateOne()) {
/* 306 */       this.relaxStateOneAmount = Math.min(1.0F, this.relaxStateOneAmount + 0.1F);
/*     */     } else {
/* 308 */       this.relaxStateOneAmount = Math.max(0.0F, this.relaxStateOneAmount - 0.13F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getLieDownAmount(float $$0) {
/* 313 */     return Mth.lerp($$0, this.lieDownAmountO, this.lieDownAmount);
/*     */   }
/*     */   
/*     */   public float getLieDownAmountTail(float $$0) {
/* 317 */     return Mth.lerp($$0, this.lieDownAmountOTail, this.lieDownAmountTail);
/*     */   }
/*     */   
/*     */   public float getRelaxStateOneAmount(float $$0) {
/* 321 */     return Mth.lerp($$0, this.relaxStateOneAmountO, this.relaxStateOneAmount);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Cat getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 327 */     Cat $$2 = (Cat)EntityType.CAT.create((Level)$$0);
/* 328 */     if ($$2 != null && $$1 instanceof Cat) { Cat $$3 = (Cat)$$1;
/* 329 */       if (this.random.nextBoolean()) {
/* 330 */         $$2.setVariant(getVariant());
/*     */       } else {
/* 332 */         $$2.setVariant($$3.getVariant());
/*     */       } 
/*     */       
/* 335 */       if (isTame()) {
/* 336 */         $$2.setOwnerUUID(getOwnerUUID());
/* 337 */         $$2.setTame(true);
/* 338 */         if (this.random.nextBoolean()) {
/* 339 */           $$2.setCollarColor(getCollarColor());
/*     */         } else {
/* 341 */           $$2.setCollarColor($$3.getCollarColor());
/*     */         } 
/*     */       }  }
/*     */ 
/*     */     
/* 346 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 351 */     if (!isTame()) {
/* 352 */       return false;
/*     */     }
/*     */     
/* 355 */     if (!($$0 instanceof Cat)) {
/* 356 */       return false;
/*     */     }
/*     */     
/* 359 */     Cat $$1 = (Cat)$$0;
/* 360 */     return ($$1.isTame() && super.canMate($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 366 */     $$3 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 368 */     boolean $$5 = ($$0.getMoonBrightness() > 0.9F);
/* 369 */     TagKey<CatVariant> $$6 = $$5 ? CatVariantTags.FULL_MOON_SPAWNS : CatVariantTags.DEFAULT_SPAWNS;
/* 370 */     BuiltInRegistries.CAT_VARIANT.getTag($$6).flatMap($$1 -> $$1.getRandomElement($$0.getRandom())).ifPresent($$0 -> setVariant((CatVariant)$$0.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 376 */     ServerLevel $$7 = $$0.getLevel();
/* 377 */     if ($$7.structureManager().getStructureWithPieceAt(blockPosition(), StructureTags.CATS_SPAWN_AS_BLACK).isValid()) {
/* 378 */       setVariant((CatVariant)BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.ALL_BLACK));
/* 379 */       setPersistenceRequired();
/*     */     } 
/*     */     
/* 382 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 387 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 388 */     Item $$3 = $$2.getItem();
/*     */     
/* 390 */     if ((level()).isClientSide) {
/* 391 */       if (isTame() && isOwnedBy((LivingEntity)$$0)) {
/* 392 */         return InteractionResult.SUCCESS;
/*     */       }
/* 394 */       if (isFood($$2) && (getHealth() < getMaxHealth() || !isTame())) {
/* 395 */         return InteractionResult.SUCCESS;
/*     */       }
/* 397 */       return InteractionResult.PASS;
/*     */     } 
/*     */     
/* 400 */     if (isTame()) {
/* 401 */       if (isOwnedBy((LivingEntity)$$0)) {
/* 402 */         if ($$3 instanceof DyeItem)
/* 403 */         { DyeColor $$4 = ((DyeItem)$$3).getDyeColor();
/* 404 */           if ($$4 != getCollarColor()) {
/* 405 */             setCollarColor($$4);
/*     */             
/* 407 */             if (!($$0.getAbilities()).instabuild) {
/* 408 */               $$2.shrink(1);
/*     */             }
/*     */             
/* 411 */             setPersistenceRequired();
/* 412 */             return InteractionResult.CONSUME;
/*     */           }  }
/* 414 */         else { if ($$3.isEdible() && isFood($$2) && getHealth() < getMaxHealth()) {
/* 415 */             usePlayerItem($$0, $$1, $$2);
/* 416 */             heal($$3.getFoodProperties().getNutrition());
/* 417 */             return InteractionResult.CONSUME;
/*     */           } 
/*     */           
/* 420 */           InteractionResult $$5 = super.mobInteract($$0, $$1);
/* 421 */           if (!$$5.consumesAction() || isBaby()) {
/* 422 */             setOrderedToSit(!isOrderedToSit());
/*     */           }
/* 424 */           return $$5; }
/*     */ 
/*     */       
/*     */       }
/* 428 */     } else if (isFood($$2)) {
/* 429 */       usePlayerItem($$0, $$1, $$2);
/*     */       
/* 431 */       if (this.random.nextInt(3) == 0) {
/* 432 */         tame($$0);
/* 433 */         setOrderedToSit(true);
/* 434 */         level().broadcastEntityEvent((Entity)this, (byte)7);
/*     */       } else {
/* 436 */         level().broadcastEntityEvent((Entity)this, (byte)6);
/*     */       } 
/*     */       
/* 439 */       setPersistenceRequired();
/* 440 */       return InteractionResult.CONSUME;
/*     */     } 
/*     */ 
/*     */     
/* 444 */     InteractionResult $$6 = super.mobInteract($$0, $$1);
/*     */     
/* 446 */     if ($$6.consumesAction()) {
/* 447 */       setPersistenceRequired();
/*     */     }
/*     */     
/* 450 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 455 */     return TEMPT_INGREDIENT.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 460 */     return $$1.height * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 465 */     return (!isTame() && this.tickCount > 2400);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reassessTameGoals() {
/* 470 */     if (this.avoidPlayersGoal == null) {
/* 471 */       this.avoidPlayersGoal = new CatAvoidEntityGoal<>(this, Player.class, 16.0F, 0.8D, 1.33D);
/*     */     }
/*     */     
/* 474 */     this.goalSelector.removeGoal((Goal)this.avoidPlayersGoal);
/*     */     
/* 476 */     if (!isTame()) {
/* 477 */       this.goalSelector.addGoal(4, (Goal)this.avoidPlayersGoal);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSteppingCarefully() {
/* 483 */     return (isCrouching() || super.isSteppingCarefully());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 488 */     return new Vector3f(0.0F, $$1.height - 0.1875F * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   private static class CatAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
/*     */     private final Cat cat;
/*     */     
/*     */     public CatAvoidEntityGoal(Cat $$0, Class<T> $$1, float $$2, double $$3, double $$4) {
/* 495 */       super((PathfinderMob)$$0, $$1, $$2, $$3, $$4, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
/* 496 */       this.cat = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 501 */       return (!this.cat.isTame() && super.canUse());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 506 */       return (!this.cat.isTame() && super.canContinueToUse());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CatTemptGoal extends TemptGoal {
/*     */     @Nullable
/*     */     private Player selectedPlayer;
/*     */     private final Cat cat;
/*     */     
/*     */     public CatTemptGoal(Cat $$0, double $$1, Ingredient $$2, boolean $$3) {
/* 516 */       super((PathfinderMob)$$0, $$1, $$2, $$3);
/* 517 */       this.cat = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 522 */       super.tick();
/*     */       
/* 524 */       if (this.selectedPlayer == null && this.mob.getRandom().nextInt(adjustedTickDelay(600)) == 0) {
/* 525 */         this.selectedPlayer = this.player;
/* 526 */       } else if (this.mob.getRandom().nextInt(adjustedTickDelay(500)) == 0) {
/* 527 */         this.selectedPlayer = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean canScare() {
/* 533 */       if (this.selectedPlayer != null && this.selectedPlayer.equals(this.player)) {
/* 534 */         return false;
/*     */       }
/*     */       
/* 537 */       return super.canScare();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 542 */       return (super.canUse() && !this.cat.isTame());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CatRelaxOnOwnerGoal extends Goal {
/*     */     private final Cat cat;
/*     */     @Nullable
/*     */     private Player ownerPlayer;
/*     */     @Nullable
/*     */     private BlockPos goalPos;
/*     */     private int onBedTicks;
/*     */     
/*     */     public CatRelaxOnOwnerGoal(Cat $$0) {
/* 555 */       this.cat = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 560 */       if (!this.cat.isTame()) {
/* 561 */         return false;
/*     */       }
/*     */       
/* 564 */       if (this.cat.isOrderedToSit()) {
/* 565 */         return false;
/*     */       }
/*     */       
/* 568 */       LivingEntity $$0 = this.cat.getOwner();
/* 569 */       if ($$0 instanceof Player) {
/* 570 */         this.ownerPlayer = (Player)$$0;
/*     */         
/* 572 */         if (!$$0.isSleeping()) {
/* 573 */           return false;
/*     */         }
/*     */         
/* 576 */         if (this.cat.distanceToSqr((Entity)this.ownerPlayer) > 100.0D) {
/* 577 */           return false;
/*     */         }
/*     */         
/* 580 */         BlockPos $$1 = this.ownerPlayer.blockPosition();
/* 581 */         BlockState $$2 = this.cat.level().getBlockState($$1);
/* 582 */         if ($$2.is(BlockTags.BEDS)) {
/* 583 */           this.goalPos = $$2.getOptionalValue((Property)BedBlock.FACING).map($$1 -> $$0.relative($$1.getOpposite())).orElseGet(() -> new BlockPos((Vec3i)$$0));
/* 584 */           return !spaceIsOccupied();
/*     */         } 
/*     */       } 
/*     */       
/* 588 */       return false;
/*     */     }
/*     */     
/*     */     private boolean spaceIsOccupied() {
/* 592 */       List<Cat> $$0 = this.cat.level().getEntitiesOfClass(Cat.class, (new AABB(this.goalPos)).inflate(2.0D));
/* 593 */       for (Cat $$1 : $$0) {
/* 594 */         if ($$1 != this.cat && ($$1.isLying() || $$1.isRelaxStateOne())) {
/* 595 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 599 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 604 */       return (this.cat.isTame() && !this.cat.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !spaceIsOccupied());
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 609 */       if (this.goalPos != null) {
/* 610 */         this.cat.setInSittingPose(false);
/* 611 */         this.cat.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.100000023841858D);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 617 */       this.cat.setLying(false);
/*     */       
/* 619 */       float $$0 = this.cat.level().getTimeOfDay(1.0F);
/* 620 */       if (this.ownerPlayer.getSleepTimer() >= 100 && $$0 > 0.77D && $$0 < 0.8D && this.cat.level().getRandom().nextFloat() < 0.7D) {
/* 621 */         giveMorningGift();
/*     */       }
/*     */       
/* 624 */       this.onBedTicks = 0;
/* 625 */       this.cat.setRelaxStateOne(false);
/* 626 */       this.cat.getNavigation().stop();
/*     */     }
/*     */     
/*     */     private void giveMorningGift() {
/* 630 */       RandomSource $$0 = this.cat.getRandom();
/* 631 */       BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/* 632 */       $$1.set(this.cat.isLeashed() ? (Vec3i)this.cat.getLeashHolder().blockPosition() : (Vec3i)this.cat.blockPosition());
/* 633 */       this.cat.randomTeleport(($$1.getX() + $$0.nextInt(11) - 5), ($$1.getY() + $$0.nextInt(5) - 2), ($$1.getZ() + $$0.nextInt(11) - 5), false);
/*     */       
/* 635 */       $$1.set((Vec3i)this.cat.blockPosition());
/* 636 */       LootTable $$2 = this.cat.level().getServer().getLootData().getLootTable(BuiltInLootTables.CAT_MORNING_GIFT);
/*     */ 
/*     */ 
/*     */       
/* 640 */       LootParams $$3 = (new LootParams.Builder((ServerLevel)this.cat.level())).withParameter(LootContextParams.ORIGIN, this.cat.position()).withParameter(LootContextParams.THIS_ENTITY, this.cat).create(LootContextParamSets.GIFT);
/* 641 */       ObjectArrayList objectArrayList = $$2.getRandomItems($$3);
/* 642 */       for (ItemStack $$5 : objectArrayList) {
/* 643 */         this.cat.level().addFreshEntity((Entity)new ItemEntity(this.cat.level(), $$1.getX() - Mth.sin(this.cat.yBodyRot * 0.017453292F), $$1.getY(), $$1.getZ() + Mth.cos(this.cat.yBodyRot * 0.017453292F), $$5));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 649 */       if (this.ownerPlayer != null && this.goalPos != null) {
/* 650 */         this.cat.setInSittingPose(false);
/* 651 */         this.cat.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.100000023841858D);
/* 652 */         if (this.cat.distanceToSqr((Entity)this.ownerPlayer) < 2.5D) {
/* 653 */           this.onBedTicks++;
/* 654 */           if (this.onBedTicks > adjustedTickDelay(16)) {
/* 655 */             this.cat.setLying(true);
/* 656 */             this.cat.setRelaxStateOne(false);
/*     */           } else {
/* 658 */             this.cat.lookAt((Entity)this.ownerPlayer, 45.0F, 45.0F);
/* 659 */             this.cat.setRelaxStateOne(true);
/*     */           } 
/*     */         } else {
/* 662 */           this.cat.setLying(false);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Cat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */