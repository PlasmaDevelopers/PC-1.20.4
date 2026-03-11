/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LlamaFollowCaravanGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.animal.Wolf;
/*     */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.LlamaSpit;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.WoolCarpetBlock;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Llama extends AbstractChestedHorse implements VariantHolder<Llama.Variant>, RangedAttackMob {
/*     */   private static final int MAX_STRENGTH = 5;
/*  70 */   private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemLike[] { (ItemLike)Items.WHEAT, (ItemLike)Blocks.HAY_BLOCK.asItem() });
/*     */   
/*  72 */   private static final EntityDataAccessor<Integer> DATA_STRENGTH_ID = SynchedEntityData.defineId(Llama.class, EntityDataSerializers.INT);
/*  73 */   private static final EntityDataAccessor<Integer> DATA_SWAG_ID = SynchedEntityData.defineId(Llama.class, EntityDataSerializers.INT);
/*  74 */   private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Llama.class, EntityDataSerializers.INT);
/*     */   boolean didSpit;
/*     */   @Nullable
/*     */   private Llama caravanHead;
/*     */   @Nullable
/*     */   private Llama caravanTail;
/*     */   
/*     */   public enum Variant
/*     */     implements StringRepresentable
/*     */   {
/*  84 */     CREAMY(0, "creamy"),
/*  85 */     WHITE(1, "white"),
/*  86 */     BROWN(2, "brown"),
/*  87 */     GRAY(3, "gray");
/*     */ 
/*     */     
/*  90 */     public static final Codec<Variant> CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */     
/*  92 */     private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
/*     */     
/*     */     final int id;
/*     */     
/*     */     Variant(int $$0, String $$1) {
/*  97 */       this.id = $$0;
/*  98 */       this.name = $$1;
/*     */     } private final String name; static {
/*     */     
/*     */     } public int getId() {
/* 102 */       return this.id;
/*     */     }
/*     */     
/*     */     public static Variant byId(int $$0) {
/* 106 */       return BY_ID.apply($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 111 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public Llama(EntityType<? extends Llama> $$0, Level $$1) {
/* 116 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public boolean isTraderLlama() {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   private void setStrength(int $$0) {
/* 124 */     this.entityData.set(DATA_STRENGTH_ID, Integer.valueOf(Math.max(1, Math.min(5, $$0))));
/*     */   }
/*     */   
/*     */   private void setRandomStrength(RandomSource $$0) {
/* 128 */     int $$1 = ($$0.nextFloat() < 0.04F) ? 5 : 3;
/*     */     
/* 130 */     setStrength(1 + $$0.nextInt($$1));
/*     */   }
/*     */   
/*     */   public int getStrength() {
/* 134 */     return ((Integer)this.entityData.get(DATA_STRENGTH_ID)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 139 */     super.addAdditionalSaveData($$0);
/*     */     
/* 141 */     $$0.putInt("Variant", (getVariant()).id);
/* 142 */     $$0.putInt("Strength", getStrength());
/*     */     
/* 144 */     if (!this.inventory.getItem(1).isEmpty()) {
/* 145 */       $$0.put("DecorItem", (Tag)this.inventory.getItem(1).save(new CompoundTag()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 151 */     setStrength($$0.getInt("Strength"));
/*     */     
/* 153 */     super.readAdditionalSaveData($$0);
/* 154 */     setVariant(Variant.byId($$0.getInt("Variant")));
/*     */     
/* 156 */     if ($$0.contains("DecorItem", 10)) {
/* 157 */       this.inventory.setItem(1, ItemStack.of($$0.getCompound("DecorItem")));
/*     */     }
/*     */     
/* 160 */     updateContainerEquipment();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 165 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/* 166 */     this.goalSelector.addGoal(1, (Goal)new RunAroundLikeCrazyGoal(this, 1.2D));
/* 167 */     this.goalSelector.addGoal(2, (Goal)new LlamaFollowCaravanGoal(this, 2.0999999046325684D));
/* 168 */     this.goalSelector.addGoal(3, (Goal)new RangedAttackGoal(this, 1.25D, 40, 20.0F));
/* 169 */     this.goalSelector.addGoal(3, (Goal)new PanicGoal((PathfinderMob)this, 1.2D));
/* 170 */     this.goalSelector.addGoal(4, (Goal)new BreedGoal(this, 1.0D));
/* 171 */     this.goalSelector.addGoal(5, (Goal)new TemptGoal((PathfinderMob)this, 1.25D, Ingredient.of(new ItemLike[] { (ItemLike)Items.HAY_BLOCK }, ), false));
/* 172 */     this.goalSelector.addGoal(6, (Goal)new FollowParentGoal(this, 1.0D));
/* 173 */     this.goalSelector.addGoal(7, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.7D));
/* 174 */     this.goalSelector.addGoal(8, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/* 175 */     this.goalSelector.addGoal(9, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/* 177 */     this.targetSelector.addGoal(1, (Goal)new LlamaHurtByTargetGoal(this));
/* 178 */     this.targetSelector.addGoal(2, (Goal)new LlamaAttackWolfGoal(this));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 182 */     return createBaseChestedHorseAttributes()
/* 183 */       .add(Attributes.FOLLOW_RANGE, 40.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 188 */     super.defineSynchedData();
/*     */     
/* 190 */     this.entityData.define(DATA_STRENGTH_ID, Integer.valueOf(0));
/* 191 */     this.entityData.define(DATA_SWAG_ID, Integer.valueOf(-1));
/* 192 */     this.entityData.define(DATA_VARIANT_ID, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 197 */     return Variant.byId(((Integer)this.entityData.get(DATA_VARIANT_ID)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Variant $$0) {
/* 202 */     this.entityData.set(DATA_VARIANT_ID, Integer.valueOf($$0.id));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getInventorySize() {
/* 207 */     if (hasChest()) {
/* 208 */       return 2 + 3 * getInventoryColumns();
/*     */     }
/* 210 */     return super.getInventorySize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 217 */     return FOOD_ITEMS.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean handleEating(Player $$0, ItemStack $$1) {
/* 222 */     int $$2 = 0;
/* 223 */     int $$3 = 0;
/* 224 */     float $$4 = 0.0F;
/* 225 */     boolean $$5 = false;
/*     */     
/* 227 */     if ($$1.is(Items.WHEAT)) {
/* 228 */       $$2 = 10;
/* 229 */       $$3 = 3;
/* 230 */       $$4 = 2.0F;
/* 231 */     } else if ($$1.is(Blocks.HAY_BLOCK.asItem())) {
/* 232 */       $$2 = 90;
/* 233 */       $$3 = 6;
/* 234 */       $$4 = 10.0F;
/* 235 */       if (isTamed() && getAge() == 0 && canFallInLove()) {
/* 236 */         $$5 = true;
/* 237 */         setInLove($$0);
/*     */       } 
/*     */     } 
/* 240 */     if (getHealth() < getMaxHealth() && $$4 > 0.0F) {
/* 241 */       heal($$4);
/* 242 */       $$5 = true;
/*     */     } 
/* 244 */     if (isBaby() && $$2 > 0) {
/* 245 */       level().addParticle((ParticleOptions)ParticleTypes.HAPPY_VILLAGER, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
/* 246 */       if (!(level()).isClientSide) {
/* 247 */         ageUp($$2);
/*     */       }
/* 249 */       $$5 = true;
/*     */     } 
/* 251 */     if ($$3 > 0 && ($$5 || !isTamed()) && getTemper() < getMaxTemper()) {
/* 252 */       $$5 = true;
/* 253 */       if (!(level()).isClientSide) {
/* 254 */         modifyTemper($$3);
/*     */       }
/*     */     } 
/* 257 */     if ($$5 && 
/* 258 */       !isSilent()) {
/* 259 */       SoundEvent $$6 = getEatingSound();
/* 260 */       if ($$6 != null) {
/* 261 */         level().playSound(null, getX(), getY(), getZ(), getEatingSound(), getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 266 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImmobile() {
/* 271 */     return (isDeadOrDying() || isEating());
/*     */   }
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     LlamaGroupData llamaGroupData;
/*     */     Variant $$7;
/* 277 */     RandomSource $$5 = $$0.getRandom();
/* 278 */     setRandomStrength($$5);
/*     */ 
/*     */     
/* 281 */     if ($$3 instanceof LlamaGroupData) {
/* 282 */       Variant $$6 = ((LlamaGroupData)$$3).variant;
/*     */     } else {
/* 284 */       $$7 = (Variant)Util.getRandom((Object[])Variant.values(), $$5);
/* 285 */       llamaGroupData = new LlamaGroupData($$7);
/*     */     } 
/* 287 */     setVariant($$7);
/*     */     
/* 289 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)llamaGroupData, $$4);
/*     */   }
/*     */   
/*     */   private static class LlamaGroupData extends AgeableMob.AgeableMobGroupData {
/*     */     public final Llama.Variant variant;
/*     */     
/*     */     LlamaGroupData(Llama.Variant $$0) {
/* 296 */       super(true);
/* 297 */       this.variant = $$0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canPerformRearing() {
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAngrySound() {
/* 308 */     return SoundEvents.LLAMA_ANGRY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 313 */     return SoundEvents.LLAMA_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 318 */     return SoundEvents.LLAMA_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 323 */     return SoundEvents.LLAMA_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getEatingSound() {
/* 329 */     return SoundEvents.LLAMA_EAT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 334 */     playSound(SoundEvents.LLAMA_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playChestEquipsSound() {
/* 339 */     playSound(SoundEvents.LLAMA_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventoryColumns() {
/* 344 */     return getStrength();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canWearArmor() {
/* 349 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWearingArmor() {
/* 354 */     return !this.inventory.getItem(1).isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isArmor(ItemStack $$0) {
/* 359 */     return $$0.is(ItemTags.WOOL_CARPETS);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSaddleable() {
/* 364 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerChanged(Container $$0) {
/* 369 */     DyeColor $$1 = getSwag();
/* 370 */     super.containerChanged($$0);
/*     */     
/* 372 */     DyeColor $$2 = getSwag();
/* 373 */     if (this.tickCount > 20 && $$2 != null && $$2 != $$1) {
/* 374 */       playSound(SoundEvents.LLAMA_SWAG, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateContainerEquipment() {
/* 380 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 384 */     super.updateContainerEquipment();
/*     */     
/* 386 */     setSwag(getDyeColor(this.inventory.getItem(1)));
/*     */   }
/*     */   
/*     */   private void setSwag(@Nullable DyeColor $$0) {
/* 390 */     this.entityData.set(DATA_SWAG_ID, Integer.valueOf(($$0 == null) ? -1 : $$0.getId()));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static DyeColor getDyeColor(ItemStack $$0) {
/* 395 */     Block $$1 = Block.byItem($$0.getItem());
/* 396 */     if ($$1 instanceof WoolCarpetBlock) {
/* 397 */       return ((WoolCarpetBlock)$$1).getColor();
/*     */     }
/* 399 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DyeColor getSwag() {
/* 404 */     int $$0 = ((Integer)this.entityData.get(DATA_SWAG_ID)).intValue();
/* 405 */     return ($$0 == -1) ? null : DyeColor.byId($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTemper() {
/* 410 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 415 */     return ($$0 != this && $$0 instanceof Llama && canParent() && ((Llama)$$0).canParent());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Llama getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 421 */     Llama $$2 = makeNewLlama();
/*     */     
/* 423 */     if ($$2 != null) {
/* 424 */       setOffspringAttributes($$1, $$2);
/*     */       
/* 426 */       Llama $$3 = (Llama)$$1;
/*     */       
/* 428 */       int $$4 = this.random.nextInt(Math.max(getStrength(), $$3.getStrength())) + 1;
/* 429 */       if (this.random.nextFloat() < 0.03F) {
/* 430 */         $$4++;
/*     */       }
/* 432 */       $$2.setStrength($$4);
/*     */       
/* 434 */       $$2.setVariant(this.random.nextBoolean() ? getVariant() : $$3.getVariant());
/*     */     } 
/* 436 */     return $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Llama makeNewLlama() {
/* 441 */     return (Llama)EntityType.LLAMA.create(level());
/*     */   }
/*     */   
/*     */   private void spit(LivingEntity $$0) {
/* 445 */     LlamaSpit $$1 = new LlamaSpit(level(), this);
/* 446 */     double $$2 = $$0.getX() - getX();
/* 447 */     double $$3 = $$0.getY(0.3333333333333333D) - $$1.getY();
/* 448 */     double $$4 = $$0.getZ() - getZ();
/* 449 */     double $$5 = Math.sqrt($$2 * $$2 + $$4 * $$4) * 0.20000000298023224D;
/* 450 */     $$1.shoot($$2, $$3 + $$5, $$4, 1.5F, 10.0F);
/* 451 */     if (!isSilent()) {
/* 452 */       level().playSound(null, getX(), getY(), getZ(), SoundEvents.LLAMA_SPIT, getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*     */     }
/*     */     
/* 455 */     level().addFreshEntity((Entity)$$1);
/* 456 */     this.didSpit = true;
/*     */   }
/*     */   
/*     */   void setDidSpit(boolean $$0) {
/* 460 */     this.didSpit = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/* 465 */     int $$3 = calculateFallDamage($$0, $$1);
/* 466 */     if ($$3 <= 0) {
/* 467 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 471 */     if ($$0 >= 6.0F) {
/* 472 */       hurt($$2, $$3);
/*     */       
/* 474 */       if (isVehicle()) {
/* 475 */         for (Entity $$4 : getIndirectPassengers()) {
/* 476 */           $$4.hurt($$2, $$3);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 481 */     playBlockFallSound();
/* 482 */     return true;
/*     */   }
/*     */   
/*     */   public void leaveCaravan() {
/* 486 */     if (this.caravanHead != null) {
/* 487 */       this.caravanHead.caravanTail = null;
/*     */     }
/* 489 */     this.caravanHead = null;
/*     */   }
/*     */   
/*     */   public void joinCaravan(Llama $$0) {
/* 493 */     this.caravanHead = $$0;
/* 494 */     this.caravanHead.caravanTail = this;
/*     */   }
/*     */   
/*     */   public boolean hasCaravanTail() {
/* 498 */     return (this.caravanTail != null);
/*     */   }
/*     */   
/*     */   public boolean inCaravan() {
/* 502 */     return (this.caravanHead != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Llama getCaravanHead() {
/* 507 */     return this.caravanHead;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double followLeashSpeed() {
/* 512 */     return 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void followMommy() {
/* 517 */     if (!inCaravan() && isBaby()) {
/* 518 */       super.followMommy();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canEatGrass() {
/* 524 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 529 */     spit($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 534 */     return new Vec3(0.0D, 0.75D * getEyeHeight(), getBbWidth() * 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 539 */     return new Vector3f(0.0F, $$1.height - (isBaby() ? 0.8125F : 0.5F) * $$2, -0.3F * $$2);
/*     */   }
/*     */   
/*     */   private static class LlamaHurtByTargetGoal extends HurtByTargetGoal {
/*     */     public LlamaHurtByTargetGoal(Llama $$0) {
/* 544 */       super((PathfinderMob)$$0, new Class[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 549 */       Mob mob = this.mob; if (mob instanceof Llama) { Llama $$0 = (Llama)mob;
/* 550 */         if ($$0.didSpit) {
/* 551 */           $$0.setDidSpit(false);
/* 552 */           return false;
/*     */         }  }
/*     */       
/* 555 */       return super.canContinueToUse();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LlamaAttackWolfGoal extends NearestAttackableTargetGoal<Wolf> {
/*     */     public LlamaAttackWolfGoal(Llama $$0) {
/* 561 */       super((Mob)$$0, Wolf.class, 16, false, true, $$0 -> !((Wolf)$$0).isTame());
/*     */     }
/*     */ 
/*     */     
/*     */     protected double getFollowDistance() {
/* 566 */       return super.getFollowDistance() * 0.25D;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\Llama.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */