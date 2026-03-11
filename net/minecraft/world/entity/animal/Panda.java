/*      */ package net.minecraft.world.entity.animal;
/*      */ import java.util.EnumSet;
/*      */ import java.util.List;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.particles.ItemParticleOption;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.util.ByIdMap;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.StringRepresentable;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.entity.AgeableMob;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityDimensions;
/*      */ import net.minecraft.world.entity.EntitySelector;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.EquipmentSlot;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.MobSpawnType;
/*      */ import net.minecraft.world.entity.PathfinderMob;
/*      */ import net.minecraft.world.entity.SpawnGroupData;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.ai.control.MoveControl;
/*      */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*      */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*      */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*      */ import net.minecraft.world.entity.ai.goal.Goal;
/*      */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*      */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*      */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*      */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*      */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*      */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*      */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*      */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.monster.Monster;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.crafting.Ingredient;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.ServerLevelAccessor;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import org.joml.Vector3f;
/*      */ 
/*      */ public class Panda extends Animal {
/*   70 */   private static final EntityDataAccessor<Integer> UNHAPPY_COUNTER = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.INT);
/*   71 */   private static final EntityDataAccessor<Integer> SNEEZE_COUNTER = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.INT);
/*   72 */   private static final EntityDataAccessor<Integer> EAT_COUNTER = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.INT);
/*   73 */   private static final EntityDataAccessor<Byte> MAIN_GENE_ID = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.BYTE);
/*   74 */   private static final EntityDataAccessor<Byte> HIDDEN_GENE_ID = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.BYTE);
/*      */   
/*   76 */   private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Panda.class, EntityDataSerializers.BYTE);
/*      */   
/*   78 */   static final TargetingConditions BREED_TARGETING = TargetingConditions.forNonCombat().range(8.0D);
/*      */   
/*      */   private static final int FLAG_SNEEZE = 2;
/*      */   
/*      */   private static final int FLAG_ROLL = 4;
/*      */   
/*      */   private static final int FLAG_SIT = 8;
/*      */   
/*      */   private static final int FLAG_ON_BACK = 16;
/*      */   
/*      */   private static final int EAT_TICK_INTERVAL = 5;
/*      */   public static final int TOTAL_ROLL_STEPS = 32;
/*      */   private static final int TOTAL_UNHAPPY_TIME = 32;
/*      */   boolean gotBamboo;
/*      */   boolean didBite;
/*      */   public int rollCounter;
/*      */   private Vec3 rollDelta;
/*      */   private float sitAmount;
/*      */   private float sitAmountO;
/*      */   private float onBackAmount;
/*      */   private float onBackAmountO;
/*      */   private float rollAmount;
/*      */   private float rollAmountO;
/*      */   PandaLookAtPlayerGoal lookAtPlayerGoal;
/*      */   static final Predicate<ItemEntity> PANDA_ITEMS;
/*      */   
/*      */   public Panda(EntityType<? extends Panda> $$0, Level $$1) {
/*  105 */     super((EntityType)$$0, $$1);
/*      */     
/*  107 */     this.moveControl = new PandaMoveControl(this);
/*      */     
/*  109 */     if (!isBaby()) {
/*  110 */       setCanPickUpLoot(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canTakeItem(ItemStack $$0) {
/*  116 */     EquipmentSlot $$1 = Mob.getEquipmentSlotForItem($$0);
/*  117 */     if (!getItemBySlot($$1).isEmpty()) {
/*  118 */       return false;
/*      */     }
/*  120 */     return ($$1 == EquipmentSlot.MAINHAND && super.canTakeItem($$0));
/*      */   }
/*      */   
/*      */   public int getUnhappyCounter() {
/*  124 */     return ((Integer)this.entityData.get(UNHAPPY_COUNTER)).intValue();
/*      */   }
/*      */   
/*      */   public void setUnhappyCounter(int $$0) {
/*  128 */     this.entityData.set(UNHAPPY_COUNTER, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public boolean isSneezing() {
/*  132 */     return getFlag(2);
/*      */   }
/*      */   
/*      */   public boolean isSitting() {
/*  136 */     return getFlag(8);
/*      */   }
/*      */   
/*      */   public void sit(boolean $$0) {
/*  140 */     setFlag(8, $$0);
/*      */   }
/*      */   
/*      */   public boolean isOnBack() {
/*  144 */     return getFlag(16);
/*      */   }
/*      */   
/*      */   public void setOnBack(boolean $$0) {
/*  148 */     setFlag(16, $$0);
/*      */   }
/*      */   
/*      */   public boolean isEating() {
/*  152 */     return (((Integer)this.entityData.get(EAT_COUNTER)).intValue() > 0);
/*      */   }
/*      */   
/*      */   public void eat(boolean $$0) {
/*  156 */     this.entityData.set(EAT_COUNTER, Integer.valueOf($$0 ? 1 : 0));
/*      */   }
/*      */   
/*      */   private int getEatCounter() {
/*  160 */     return ((Integer)this.entityData.get(EAT_COUNTER)).intValue();
/*      */   }
/*      */   
/*      */   private void setEatCounter(int $$0) {
/*  164 */     this.entityData.set(EAT_COUNTER, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public void sneeze(boolean $$0) {
/*  168 */     setFlag(2, $$0);
/*      */     
/*  170 */     if (!$$0) {
/*  171 */       setSneezeCounter(0);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getSneezeCounter() {
/*  176 */     return ((Integer)this.entityData.get(SNEEZE_COUNTER)).intValue();
/*      */   }
/*      */   
/*      */   public void setSneezeCounter(int $$0) {
/*  180 */     this.entityData.set(SNEEZE_COUNTER, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public Gene getMainGene() {
/*  184 */     return Gene.byId(((Byte)this.entityData.get(MAIN_GENE_ID)).byteValue());
/*      */   }
/*      */   
/*      */   public void setMainGene(Gene $$0) {
/*  188 */     if ($$0.getId() > 6) {
/*  189 */       $$0 = Gene.getRandom(this.random);
/*      */     }
/*      */     
/*  192 */     this.entityData.set(MAIN_GENE_ID, Byte.valueOf((byte)$$0.getId()));
/*      */   }
/*      */   
/*      */   public Gene getHiddenGene() {
/*  196 */     return Gene.byId(((Byte)this.entityData.get(HIDDEN_GENE_ID)).byteValue());
/*      */   }
/*      */   
/*      */   public void setHiddenGene(Gene $$0) {
/*  200 */     if ($$0.getId() > 6) {
/*  201 */       $$0 = Gene.getRandom(this.random);
/*      */     }
/*      */     
/*  204 */     this.entityData.set(HIDDEN_GENE_ID, Byte.valueOf((byte)$$0.getId()));
/*      */   }
/*      */   
/*      */   public boolean isRolling() {
/*  208 */     return getFlag(4);
/*      */   }
/*      */   
/*      */   public void roll(boolean $$0) {
/*  212 */     setFlag(4, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  217 */     super.defineSynchedData();
/*  218 */     this.entityData.define(UNHAPPY_COUNTER, Integer.valueOf(0));
/*  219 */     this.entityData.define(SNEEZE_COUNTER, Integer.valueOf(0));
/*  220 */     this.entityData.define(MAIN_GENE_ID, Byte.valueOf((byte)0));
/*  221 */     this.entityData.define(HIDDEN_GENE_ID, Byte.valueOf((byte)0));
/*  222 */     this.entityData.define(DATA_ID_FLAGS, Byte.valueOf((byte)0));
/*  223 */     this.entityData.define(EAT_COUNTER, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   private boolean getFlag(int $$0) {
/*  227 */     return ((((Byte)this.entityData.get(DATA_ID_FLAGS)).byteValue() & $$0) != 0);
/*      */   }
/*      */   
/*      */   private void setFlag(int $$0, boolean $$1) {
/*  231 */     byte $$2 = ((Byte)this.entityData.get(DATA_ID_FLAGS)).byteValue();
/*  232 */     if ($$1) {
/*  233 */       this.entityData.set(DATA_ID_FLAGS, Byte.valueOf((byte)($$2 | $$0)));
/*      */     } else {
/*  235 */       this.entityData.set(DATA_ID_FLAGS, Byte.valueOf((byte)($$2 & ($$0 ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  241 */     super.addAdditionalSaveData($$0);
/*      */     
/*  243 */     $$0.putString("MainGene", getMainGene().getSerializedName());
/*  244 */     $$0.putString("HiddenGene", getHiddenGene().getSerializedName());
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  249 */     super.readAdditionalSaveData($$0);
/*      */     
/*  251 */     setMainGene(Gene.byName($$0.getString("MainGene")));
/*  252 */     setHiddenGene(Gene.byName($$0.getString("HiddenGene")));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*  258 */     Panda $$2 = (Panda)EntityType.PANDA.create((Level)$$0);
/*  259 */     if ($$2 != null) {
/*  260 */       if ($$1 instanceof Panda) { Panda $$3 = (Panda)$$1;
/*  261 */         $$2.setGeneFromParents(this, $$3); }
/*      */ 
/*      */       
/*  264 */       $$2.setAttributes();
/*      */     } 
/*      */     
/*  267 */     return $$2;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerGoals() {
/*  272 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  273 */     this.goalSelector.addGoal(2, (Goal)new PandaPanicGoal(this, 2.0D));
/*  274 */     this.goalSelector.addGoal(2, (Goal)new PandaBreedGoal(this, 1.0D));
/*  275 */     this.goalSelector.addGoal(3, (Goal)new PandaAttackGoal(this, 1.2000000476837158D, true));
/*  276 */     this.goalSelector.addGoal(4, (Goal)new TemptGoal((PathfinderMob)this, 1.0D, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.BAMBOO.asItem() }, ), false));
/*  277 */     this.goalSelector.addGoal(6, (Goal)new PandaAvoidGoal<>(this, Player.class, 8.0F, 2.0D, 2.0D));
/*  278 */     this.goalSelector.addGoal(6, (Goal)new PandaAvoidGoal<>(this, Monster.class, 4.0F, 2.0D, 2.0D));
/*  279 */     this.goalSelector.addGoal(7, new PandaSitGoal());
/*  280 */     this.goalSelector.addGoal(8, new PandaLieOnBackGoal(this));
/*  281 */     this.goalSelector.addGoal(8, new PandaSneezeGoal(this));
/*  282 */     this.lookAtPlayerGoal = new PandaLookAtPlayerGoal(this, (Class)Player.class, 6.0F);
/*  283 */     this.goalSelector.addGoal(9, (Goal)this.lookAtPlayerGoal);
/*  284 */     this.goalSelector.addGoal(10, (Goal)new RandomLookAroundGoal((Mob)this));
/*  285 */     this.goalSelector.addGoal(12, new PandaRollGoal(this));
/*  286 */     this.goalSelector.addGoal(13, (Goal)new FollowParentGoal(this, 1.25D));
/*  287 */     this.goalSelector.addGoal(14, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/*      */     
/*  289 */     this.targetSelector.addGoal(1, (Goal)(new PandaHurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
/*      */   }
/*      */   
/*      */   public static AttributeSupplier.Builder createAttributes() {
/*  293 */     return Mob.createMobAttributes()
/*  294 */       .add(Attributes.MOVEMENT_SPEED, 0.15000000596046448D)
/*  295 */       .add(Attributes.ATTACK_DAMAGE, 6.0D);
/*      */   }
/*      */   
/*      */   public enum Gene
/*      */     implements StringRepresentable
/*      */   {
/*  301 */     NORMAL(0, "normal", false),
/*  302 */     LAZY(1, "lazy", false),
/*  303 */     WORRIED(2, "worried", false),
/*  304 */     PLAYFUL(3, "playful", false),
/*  305 */     BROWN(4, "brown", true),
/*  306 */     WEAK(5, "weak", true),
/*  307 */     AGGRESSIVE(6, "aggressive", false);
/*      */     
/*  309 */     public static final StringRepresentable.EnumCodec<Gene> CODEC = StringRepresentable.fromEnum(Gene::values);
/*      */     
/*  311 */     private static final IntFunction<Gene> BY_ID = ByIdMap.continuous(Gene::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*      */     
/*      */     private static final int MAX_GENE = 6;
/*      */     
/*      */     private final int id;
/*      */ 
/*      */     
/*      */     Gene(int $$0, String $$1, boolean $$2) {
/*  319 */       this.id = $$0;
/*  320 */       this.name = $$1;
/*  321 */       this.isRecessive = $$2;
/*      */     } private final String name; private final boolean isRecessive; static {
/*      */     
/*      */     } public int getId() {
/*  325 */       return this.id;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getSerializedName() {
/*  330 */       return this.name;
/*      */     }
/*      */     
/*      */     public boolean isRecessive() {
/*  334 */       return this.isRecessive;
/*      */     }
/*      */     
/*      */     static Gene getVariantFromGenes(Gene $$0, Gene $$1) {
/*  338 */       if ($$0.isRecessive()) {
/*  339 */         if ($$0 == $$1) {
/*  340 */           return $$0;
/*      */         }
/*  342 */         return NORMAL;
/*      */       } 
/*      */ 
/*      */       
/*  346 */       return $$0;
/*      */     }
/*      */     
/*      */     public static Gene byId(int $$0) {
/*  350 */       return BY_ID.apply($$0);
/*      */     }
/*      */     
/*      */     public static Gene byName(String $$0) {
/*  354 */       return (Gene)CODEC.byName($$0, NORMAL);
/*      */     }
/*      */     
/*      */     public static Gene getRandom(RandomSource $$0) {
/*  358 */       int $$1 = $$0.nextInt(16);
/*  359 */       if ($$1 == 0) {
/*  360 */         return LAZY;
/*      */       }
/*  362 */       if ($$1 == 1) {
/*  363 */         return WORRIED;
/*      */       }
/*  365 */       if ($$1 == 2) {
/*  366 */         return PLAYFUL;
/*      */       }
/*  368 */       if ($$1 == 4) {
/*  369 */         return AGGRESSIVE;
/*      */       }
/*  371 */       if ($$1 < 9) {
/*  372 */         return WEAK;
/*      */       }
/*  374 */       if ($$1 < 11) {
/*  375 */         return BROWN;
/*      */       }
/*      */       
/*  378 */       return NORMAL;
/*      */     }
/*      */   }
/*      */   
/*      */   public Gene getVariant() {
/*  383 */     return Gene.getVariantFromGenes(getMainGene(), getHiddenGene());
/*      */   }
/*      */   
/*      */   public boolean isLazy() {
/*  387 */     return (getVariant() == Gene.LAZY);
/*      */   }
/*      */   
/*      */   public boolean isWorried() {
/*  391 */     return (getVariant() == Gene.WORRIED);
/*      */   }
/*      */   
/*      */   public boolean isPlayful() {
/*  395 */     return (getVariant() == Gene.PLAYFUL);
/*      */   }
/*      */   
/*      */   public boolean isBrown() {
/*  399 */     return (getVariant() == Gene.BROWN);
/*      */   }
/*      */   
/*      */   public boolean isWeak() {
/*  403 */     return (getVariant() == Gene.WEAK);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAggressive() {
/*  408 */     return (getVariant() == Gene.AGGRESSIVE);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeLeashed(Player $$0) {
/*  413 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean doHurtTarget(Entity $$0) {
/*  418 */     playSound(SoundEvents.PANDA_BITE, 1.0F, 1.0F);
/*  419 */     if (!isAggressive()) {
/*  420 */       this.didBite = true;
/*      */     }
/*  422 */     return super.doHurtTarget($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  427 */     super.tick();
/*      */     
/*  429 */     if (isWorried()) {
/*  430 */       if (level().isThundering() && !isInWater()) {
/*  431 */         sit(true);
/*  432 */         eat(false);
/*  433 */       } else if (!isEating()) {
/*  434 */         sit(false);
/*      */       } 
/*      */     }
/*      */     
/*  438 */     LivingEntity $$0 = getTarget();
/*  439 */     if ($$0 == null) {
/*  440 */       this.gotBamboo = false;
/*  441 */       this.didBite = false;
/*      */     } 
/*      */     
/*  444 */     if (getUnhappyCounter() > 0) {
/*  445 */       if ($$0 != null) {
/*  446 */         lookAt((Entity)$$0, 90.0F, 90.0F);
/*      */       }
/*      */       
/*  449 */       if (getUnhappyCounter() == 29 || getUnhappyCounter() == 14) {
/*  450 */         playSound(SoundEvents.PANDA_CANT_BREED, 1.0F, 1.0F);
/*      */       }
/*      */       
/*  453 */       setUnhappyCounter(getUnhappyCounter() - 1);
/*      */     } 
/*      */     
/*  456 */     if (isSneezing()) {
/*  457 */       setSneezeCounter(getSneezeCounter() + 1);
/*  458 */       if (getSneezeCounter() > 20) {
/*  459 */         sneeze(false);
/*  460 */         afterSneeze();
/*  461 */       } else if (getSneezeCounter() == 1) {
/*  462 */         playSound(SoundEvents.PANDA_PRE_SNEEZE, 1.0F, 1.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  466 */     if (isRolling()) {
/*  467 */       handleRoll();
/*      */     } else {
/*  469 */       this.rollCounter = 0;
/*      */     } 
/*      */     
/*  472 */     if (isSitting()) {
/*  473 */       setXRot(0.0F);
/*      */     }
/*      */     
/*  476 */     updateSitAmount();
/*  477 */     handleEating();
/*  478 */     updateOnBackAnimation();
/*  479 */     updateRollAmount();
/*      */   }
/*      */   
/*      */   public boolean isScared() {
/*  483 */     return (isWorried() && level().isThundering());
/*      */   }
/*      */   
/*      */   private void handleEating() {
/*  487 */     if (!isEating() && isSitting() && !isScared() && !getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && this.random.nextInt(80) == 1) {
/*  488 */       eat(true);
/*  489 */     } else if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() || !isSitting()) {
/*  490 */       eat(false);
/*      */     } 
/*      */     
/*  493 */     if (isEating()) {
/*  494 */       addEatingParticles();
/*      */       
/*  496 */       if (!(level()).isClientSide && getEatCounter() > 80 && this.random.nextInt(20) == 1) {
/*  497 */         if (getEatCounter() > 100 && isFoodOrCake(getItemBySlot(EquipmentSlot.MAINHAND))) {
/*      */           
/*  499 */           if (!(level()).isClientSide) {
/*  500 */             setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/*  501 */             gameEvent(GameEvent.EAT);
/*      */           } 
/*      */           
/*  504 */           sit(false);
/*      */         } 
/*  506 */         eat(false);
/*      */         
/*      */         return;
/*      */       } 
/*  510 */       setEatCounter(getEatCounter() + 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addEatingParticles() {
/*  515 */     if (getEatCounter() % 5 == 0) {
/*  516 */       playSound(SoundEvents.PANDA_EAT, 0.5F + 0.5F * this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*      */       
/*  518 */       for (int $$0 = 0; $$0 < 6; $$0++) {
/*  519 */         Vec3 $$1 = new Vec3((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, (this.random.nextFloat() - 0.5D) * 0.1D);
/*  520 */         $$1 = $$1.xRot(-getXRot() * 0.017453292F);
/*  521 */         $$1 = $$1.yRot(-getYRot() * 0.017453292F);
/*      */         
/*  523 */         double $$2 = -this.random.nextFloat() * 0.6D - 0.3D;
/*  524 */         Vec3 $$3 = new Vec3((this.random.nextFloat() - 0.5D) * 0.8D, $$2, 1.0D + (this.random.nextFloat() - 0.5D) * 0.4D);
/*  525 */         $$3 = $$3.yRot(-this.yBodyRot * 0.017453292F);
/*      */         
/*  527 */         $$3 = $$3.add(getX(), getEyeY() + 1.0D, getZ());
/*  528 */         level().addParticle((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, getItemBySlot(EquipmentSlot.MAINHAND)), $$3.x, $$3.y, $$3.z, $$1.x, $$1.y + 0.05D, $$1.z);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateSitAmount() {
/*  534 */     this.sitAmountO = this.sitAmount;
/*  535 */     if (isSitting()) {
/*  536 */       this.sitAmount = Math.min(1.0F, this.sitAmount + 0.15F);
/*      */     } else {
/*  538 */       this.sitAmount = Math.max(0.0F, this.sitAmount - 0.19F);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateOnBackAnimation() {
/*  543 */     this.onBackAmountO = this.onBackAmount;
/*  544 */     if (isOnBack()) {
/*  545 */       this.onBackAmount = Math.min(1.0F, this.onBackAmount + 0.15F);
/*      */     } else {
/*  547 */       this.onBackAmount = Math.max(0.0F, this.onBackAmount - 0.19F);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRollAmount() {
/*  552 */     this.rollAmountO = this.rollAmount;
/*  553 */     if (isRolling()) {
/*  554 */       this.rollAmount = Math.min(1.0F, this.rollAmount + 0.15F);
/*      */     } else {
/*  556 */       this.rollAmount = Math.max(0.0F, this.rollAmount - 0.19F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getSitAmount(float $$0) {
/*  561 */     return Mth.lerp($$0, this.sitAmountO, this.sitAmount);
/*      */   }
/*      */   
/*      */   public float getLieOnBackAmount(float $$0) {
/*  565 */     return Mth.lerp($$0, this.onBackAmountO, this.onBackAmount);
/*      */   }
/*      */   
/*      */   public float getRollAmount(float $$0) {
/*  569 */     return Mth.lerp($$0, this.rollAmountO, this.rollAmount);
/*      */   }
/*      */   
/*      */   private void handleRoll() {
/*  573 */     this.rollCounter++;
/*  574 */     if (this.rollCounter > 32) {
/*  575 */       roll(false);
/*      */       
/*      */       return;
/*      */     } 
/*  579 */     if (!(level()).isClientSide) {
/*  580 */       Vec3 $$0 = getDeltaMovement();
/*  581 */       if (this.rollCounter == 1) {
/*  582 */         float $$1 = getYRot() * 0.017453292F;
/*  583 */         float $$2 = isBaby() ? 0.1F : 0.2F;
/*  584 */         this
/*      */ 
/*      */           
/*  587 */           .rollDelta = new Vec3($$0.x + (-Mth.sin($$1) * $$2), 0.0D, $$0.z + (Mth.cos($$1) * $$2));
/*      */         
/*  589 */         setDeltaMovement(this.rollDelta.add(0.0D, 0.27D, 0.0D));
/*  590 */       } else if (this.rollCounter == 7.0F || this.rollCounter == 15.0F || this.rollCounter == 23.0F) {
/*  591 */         setDeltaMovement(0.0D, onGround() ? 0.27D : $$0.y, 0.0D);
/*      */       } else {
/*  593 */         setDeltaMovement(this.rollDelta.x, $$0.y, this.rollDelta.z);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void afterSneeze() {
/*  599 */     Vec3 $$0 = getDeltaMovement();
/*  600 */     level().addParticle((ParticleOptions)ParticleTypes.SNEEZE, getX() - (getBbWidth() + 1.0F) * 0.5D * Mth.sin(this.yBodyRot * 0.017453292F), getEyeY() - 0.10000000149011612D, getZ() + (getBbWidth() + 1.0F) * 0.5D * Mth.cos(this.yBodyRot * 0.017453292F), $$0.x, 0.0D, $$0.z);
/*  601 */     playSound(SoundEvents.PANDA_SNEEZE, 1.0F, 1.0F);
/*      */ 
/*      */     
/*  604 */     List<Panda> $$1 = level().getEntitiesOfClass(Panda.class, getBoundingBox().inflate(10.0D));
/*  605 */     for (Panda $$2 : $$1) {
/*  606 */       if (!$$2.isBaby() && $$2.onGround() && !$$2.isInWater() && $$2.canPerformAction()) {
/*  607 */         $$2.jumpFromGround();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  612 */     if (!level().isClientSide() && this.random.nextInt(700) == 0 && level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
/*  613 */       spawnAtLocation((ItemLike)Items.SLIME_BALL);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void pickUpItem(ItemEntity $$0) {
/*  619 */     if (getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && PANDA_ITEMS.test($$0)) {
/*  620 */       onItemPickup($$0);
/*  621 */       ItemStack $$1 = $$0.getItem();
/*  622 */       setItemSlot(EquipmentSlot.MAINHAND, $$1);
/*  623 */       setGuaranteedDrop(EquipmentSlot.MAINHAND);
/*  624 */       take((Entity)$$0, $$1.getCount());
/*  625 */       $$0.discard();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/*  631 */     if (!(level()).isClientSide) {
/*  632 */       sit(false);
/*      */     }
/*  634 */     return super.hurt($$0, $$1);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*      */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/*  640 */     RandomSource $$5 = $$0.getRandom();
/*  641 */     setMainGene(Gene.getRandom($$5));
/*  642 */     setHiddenGene(Gene.getRandom($$5));
/*      */     
/*  644 */     setAttributes();
/*      */     
/*  646 */     if ($$3 == null) {
/*  647 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(0.2F);
/*      */     }
/*      */     
/*  650 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*      */   }
/*      */   
/*      */   public void setGeneFromParents(Panda $$0, @Nullable Panda $$1) {
/*  654 */     if ($$1 == null) {
/*  655 */       if (this.random.nextBoolean()) {
/*  656 */         setMainGene($$0.getOneOfGenesRandomly());
/*  657 */         setHiddenGene(Gene.getRandom(this.random));
/*      */       } else {
/*  659 */         setMainGene(Gene.getRandom(this.random));
/*  660 */         setHiddenGene($$0.getOneOfGenesRandomly());
/*      */       }
/*      */     
/*  663 */     } else if (this.random.nextBoolean()) {
/*  664 */       setMainGene($$0.getOneOfGenesRandomly());
/*  665 */       setHiddenGene($$1.getOneOfGenesRandomly());
/*      */     } else {
/*  667 */       setMainGene($$1.getOneOfGenesRandomly());
/*  668 */       setHiddenGene($$0.getOneOfGenesRandomly());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  673 */     if (this.random.nextInt(32) == 0) {
/*  674 */       setMainGene(Gene.getRandom(this.random));
/*      */     }
/*      */     
/*  677 */     if (this.random.nextInt(32) == 0) {
/*  678 */       setHiddenGene(Gene.getRandom(this.random));
/*      */     }
/*      */   }
/*      */   
/*      */   private Gene getOneOfGenesRandomly() {
/*  683 */     if (this.random.nextBoolean()) {
/*  684 */       return getMainGene();
/*      */     }
/*      */     
/*  687 */     return getHiddenGene();
/*      */   }
/*      */   
/*      */   public void setAttributes() {
/*  691 */     if (isWeak()) {
/*  692 */       getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D);
/*      */     }
/*      */     
/*  695 */     if (isLazy()) {
/*  696 */       getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.07000000029802322D);
/*      */     }
/*      */   }
/*      */   
/*      */   void tryToSit() {
/*  701 */     if (!isInWater()) {
/*  702 */       setZza(0.0F);
/*  703 */       getNavigation().stop();
/*  704 */       sit(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/*  710 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*      */     
/*  712 */     if (isScared()) {
/*  713 */       return InteractionResult.PASS;
/*      */     }
/*      */     
/*  716 */     if (isOnBack()) {
/*  717 */       setOnBack(false);
/*  718 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */     } 
/*      */     
/*  721 */     if (isFood($$2)) {
/*  722 */       if (getTarget() != null) {
/*  723 */         this.gotBamboo = true;
/*      */       }
/*      */       
/*  726 */       if (isBaby()) {
/*  727 */         usePlayerItem($$0, $$1, $$2);
/*  728 */         ageUp((int)((-getAge() / 20) * 0.1F), true);
/*  729 */       } else if (!(level()).isClientSide && getAge() == 0 && canFallInLove()) {
/*  730 */         usePlayerItem($$0, $$1, $$2);
/*  731 */         setInLove($$0);
/*  732 */       } else if (!(level()).isClientSide && !isSitting() && !isInWater()) {
/*  733 */         tryToSit();
/*  734 */         eat(true);
/*      */         
/*  736 */         ItemStack $$3 = getItemBySlot(EquipmentSlot.MAINHAND);
/*  737 */         if (!$$3.isEmpty() && !($$0.getAbilities()).instabuild) {
/*  738 */           spawnAtLocation($$3);
/*      */         }
/*  740 */         setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)$$2.getItem(), 1));
/*      */         
/*  742 */         usePlayerItem($$0, $$1, $$2);
/*      */       } else {
/*  744 */         return InteractionResult.PASS;
/*      */       } 
/*      */       
/*  747 */       return InteractionResult.SUCCESS;
/*      */     } 
/*      */     
/*  750 */     return InteractionResult.PASS;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAmbientSound() {
/*  756 */     if (isAggressive())
/*  757 */       return SoundEvents.PANDA_AGGRESSIVE_AMBIENT; 
/*  758 */     if (isWorried()) {
/*  759 */       return SoundEvents.PANDA_WORRIED_AMBIENT;
/*      */     }
/*  761 */     return SoundEvents.PANDA_AMBIENT;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/*  767 */     playSound(SoundEvents.PANDA_STEP, 0.15F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFood(ItemStack $$0) {
/*  772 */     return $$0.is(Blocks.BAMBOO.asItem());
/*      */   }
/*      */   
/*      */   private boolean isFoodOrCake(ItemStack $$0) {
/*  776 */     return (isFood($$0) || $$0.is(Blocks.CAKE.asItem()));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getDeathSound() {
/*  782 */     return SoundEvents.PANDA_DEATH;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  788 */     return SoundEvents.PANDA_HURT;
/*      */   }
/*      */   
/*      */   public boolean canPerformAction() {
/*  792 */     return (!isOnBack() && !isScared() && !isEating() && !isRolling() && !isSitting());
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/*  797 */     return new Vector3f(0.0F, $$1.height - (isBaby() ? 0.4375F : 0.0F) * $$2, 0.0F);
/*      */   }
/*      */   
/*      */   private static class PandaMoveControl extends MoveControl {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaMoveControl(Panda $$0) {
/*  804 */       super((Mob)$$0);
/*  805 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/*  810 */       if (!this.panda.canPerformAction()) {
/*      */         return;
/*      */       }
/*      */       
/*  814 */       super.tick();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaAttackGoal extends MeleeAttackGoal {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaAttackGoal(Panda $$0, double $$1, boolean $$2) {
/*  822 */       super((PathfinderMob)$$0, $$1, $$2);
/*  823 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  828 */       return (this.panda.canPerformAction() && super.canUse());
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaLookAtPlayerGoal extends LookAtPlayerGoal {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaLookAtPlayerGoal(Panda $$0, Class<? extends LivingEntity> $$1, float $$2) {
/*  836 */       super((Mob)$$0, $$1, $$2);
/*  837 */       this.panda = $$0;
/*      */     }
/*      */     
/*      */     public void setTarget(LivingEntity $$0) {
/*  841 */       this.lookAt = (Entity)$$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/*  846 */       return (this.lookAt != null && super.canContinueToUse());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  851 */       if (this.mob.getRandom().nextFloat() >= this.probability) {
/*  852 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  857 */       if (this.lookAt == null) {
/*  858 */         if (this.lookAtType == Player.class) {
/*  859 */           this.lookAt = (Entity)this.mob.level().getNearestPlayer(this.lookAtContext, (LivingEntity)this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
/*      */         } else {
/*  861 */           this.lookAt = (Entity)this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.lookAtType, this.mob.getBoundingBox().inflate(this.lookDistance, 3.0D, this.lookDistance), $$0 -> true), this.lookAtContext, (LivingEntity)this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
/*      */         } 
/*      */       }
/*      */       
/*  865 */       return (this.panda.canPerformAction() && this.lookAt != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/*  870 */       if (this.lookAt != null)
/*  871 */         super.tick(); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaRollGoal
/*      */     extends Goal {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaRollGoal(Panda $$0) {
/*  880 */       this.panda = $$0;
/*  881 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  886 */       if ((!this.panda.isBaby() && !this.panda.isPlayful()) || !this.panda.onGround()) {
/*  887 */         return false;
/*      */       }
/*      */       
/*  890 */       if (!this.panda.canPerformAction()) {
/*  891 */         return false;
/*      */       }
/*      */       
/*  894 */       float $$0 = this.panda.getYRot() * 0.017453292F;
/*  895 */       float $$1 = -Mth.sin($$0);
/*  896 */       float $$2 = Mth.cos($$0);
/*  897 */       int $$3 = (Math.abs($$1) > 0.5D) ? Mth.sign($$1) : 0;
/*  898 */       int $$4 = (Math.abs($$2) > 0.5D) ? Mth.sign($$2) : 0;
/*      */       
/*  900 */       if (this.panda.level().getBlockState(this.panda.blockPosition().offset($$3, -1, $$4)).isAir()) {
/*  901 */         return true;
/*      */       }
/*      */       
/*  904 */       if (this.panda.isPlayful() && this.panda.random.nextInt(reducedTickDelay(60)) == 1) {
/*  905 */         return true;
/*      */       }
/*      */       
/*  908 */       return (this.panda.random.nextInt(reducedTickDelay(500)) == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/*  913 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  918 */       this.panda.roll(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isInterruptable() {
/*  923 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaSneezeGoal
/*      */     extends Goal {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaSneezeGoal(Panda $$0) {
/*  932 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  937 */       if (!this.panda.isBaby() || !this.panda.canPerformAction()) {
/*  938 */         return false;
/*      */       }
/*      */       
/*  941 */       if (this.panda.isWeak() && this.panda.random.nextInt(reducedTickDelay(500)) == 1) {
/*  942 */         return true;
/*      */       }
/*      */       
/*  945 */       return (this.panda.random.nextInt(reducedTickDelay(6000)) == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/*  950 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/*  955 */       this.panda.sneeze(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaBreedGoal extends BreedGoal {
/*      */     private final Panda panda;
/*      */     private int unhappyCooldown;
/*      */     
/*      */     public PandaBreedGoal(Panda $$0, double $$1) {
/*  964 */       super($$0, $$1);
/*  965 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/*  970 */       if (super.canUse() && this.panda.getUnhappyCounter() == 0) {
/*  971 */         if (!canFindBamboo()) {
/*  972 */           if (this.unhappyCooldown <= this.panda.tickCount) {
/*  973 */             this.panda.setUnhappyCounter(32);
/*  974 */             this.unhappyCooldown = this.panda.tickCount + 600;
/*  975 */             if (this.panda.isEffectiveAi()) {
/*  976 */               Player $$0 = this.level.getNearestPlayer(Panda.BREED_TARGETING, (LivingEntity)this.panda);
/*  977 */               this.panda.lookAtPlayerGoal.setTarget((LivingEntity)$$0);
/*      */             } 
/*      */           } 
/*      */           
/*  981 */           return false;
/*      */         } 
/*      */         
/*  984 */         return true;
/*      */       } 
/*      */       
/*  987 */       return false;
/*      */     }
/*      */     
/*      */     private boolean canFindBamboo() {
/*  991 */       BlockPos $$0 = this.panda.blockPosition();
/*  992 */       BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/*  993 */       for (int $$2 = 0; $$2 < 3; $$2++) {
/*  994 */         for (int $$3 = 0; $$3 < 8; $$3++) {
/*  995 */           int $$4; for ($$4 = 0; $$4 <= $$3; $$4 = ($$4 > 0) ? -$$4 : (1 - $$4)) {
/*  996 */             int $$5 = ($$4 < $$3 && $$4 > -$$3) ? $$3 : 0;
/*  997 */             for (; $$5 <= $$3; $$5 = ($$5 > 0) ? -$$5 : (1 - $$5)) {
/*  998 */               $$1.setWithOffset((Vec3i)$$0, $$4, $$2, $$5);
/*  999 */               if (this.level.getBlockState((BlockPos)$$1).is(Blocks.BAMBOO)) {
/* 1000 */                 return true;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1006 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaAvoidGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaAvoidGoal(Panda $$0, Class<T> $$1, float $$2, double $$3, double $$4) {
/* 1014 */       super((PathfinderMob)$$0, $$1, $$2, $$3, $$4, EntitySelector.NO_SPECTATORS::test);
/*      */       
/* 1016 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1021 */       return (this.panda.isWorried() && this.panda.canPerformAction() && super.canUse());
/*      */     } }
/*      */   
/*      */   static {
/* 1025 */     PANDA_ITEMS = ($$0 -> {
/*      */         ItemStack $$1 = $$0.getItem();
/* 1027 */         return (($$1.is(Blocks.BAMBOO.asItem()) || $$1.is(Blocks.CAKE.asItem())) && $$0.isAlive() && !$$0.hasPickUpDelay());
/*      */       });
/*      */   }
/*      */   
/*      */   private class PandaSitGoal extends Goal { private int cooldown;
/*      */     
/*      */     public PandaSitGoal() {
/* 1034 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1039 */       if (this.cooldown > Panda.this.tickCount || Panda.this.isBaby() || Panda.this.isInWater() || !Panda.this.canPerformAction() || Panda.this.getUnhappyCounter() > 0) {
/* 1040 */         return false;
/*      */       }
/*      */       
/* 1043 */       List<ItemEntity> $$0 = Panda.this.level().getEntitiesOfClass(ItemEntity.class, Panda.this.getBoundingBox().inflate(6.0D, 6.0D, 6.0D), Panda.PANDA_ITEMS);
/* 1044 */       return (!$$0.isEmpty() || !Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1049 */       if (Panda.this.isInWater() || (!Panda.this.isLazy() && Panda.this.random.nextInt(reducedTickDelay(600)) == 1)) {
/* 1050 */         return false;
/*      */       }
/*      */       
/* 1053 */       if (Panda.this.random.nextInt(reducedTickDelay(2000)) == 1) {
/* 1054 */         return false;
/*      */       }
/*      */       
/* 1057 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void tick() {
/* 1062 */       if (!Panda.this.isSitting() && !Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
/* 1063 */         Panda.this.tryToSit();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1069 */       List<ItemEntity> $$0 = Panda.this.level().getEntitiesOfClass(ItemEntity.class, Panda.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Panda.PANDA_ITEMS);
/* 1070 */       if (!$$0.isEmpty() && Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
/* 1071 */         Panda.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158D);
/* 1072 */       } else if (!Panda.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
/* 1073 */         Panda.this.tryToSit();
/*      */       } 
/*      */       
/* 1076 */       this.cooldown = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1081 */       ItemStack $$0 = Panda.this.getItemBySlot(EquipmentSlot.MAINHAND);
/* 1082 */       if (!$$0.isEmpty()) {
/* 1083 */         Panda.this.spawnAtLocation($$0);
/* 1084 */         Panda.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 1085 */         int $$1 = Panda.this.isLazy() ? (Panda.this.random.nextInt(50) + 10) : (Panda.this.random.nextInt(150) + 10);
/* 1086 */         this.cooldown = Panda.this.tickCount + $$1 * 20;
/*      */       } 
/*      */       
/* 1089 */       Panda.this.sit(false);
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class PandaLieOnBackGoal extends Goal {
/*      */     private final Panda panda;
/*      */     private int cooldown;
/*      */     
/*      */     public PandaLieOnBackGoal(Panda $$0) {
/* 1098 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canUse() {
/* 1103 */       return (this.cooldown < this.panda.tickCount && this.panda.isLazy() && this.panda.canPerformAction() && this.panda.random.nextInt(reducedTickDelay(400)) == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1108 */       if (this.panda.isInWater() || (!this.panda.isLazy() && this.panda.random.nextInt(reducedTickDelay(600)) == 1)) {
/* 1109 */         return false;
/*      */       }
/*      */       
/* 1112 */       if (this.panda.random.nextInt(reducedTickDelay(2000)) == 1) {
/* 1113 */         return false;
/*      */       }
/*      */       
/* 1116 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void start() {
/* 1121 */       this.panda.setOnBack(true);
/* 1122 */       this.cooldown = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void stop() {
/* 1127 */       this.panda.setOnBack(false);
/* 1128 */       this.cooldown = this.panda.tickCount + 200;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaHurtByTargetGoal extends HurtByTargetGoal {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaHurtByTargetGoal(Panda $$0, Class<?>... $$1) {
/* 1136 */       super((PathfinderMob)$$0, $$1);
/* 1137 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1142 */       if (this.panda.gotBamboo || this.panda.didBite) {
/* 1143 */         this.panda.setTarget(null);
/* 1144 */         return false;
/*      */       } 
/* 1146 */       return super.canContinueToUse();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void alertOther(Mob $$0, LivingEntity $$1) {
/* 1151 */       if ($$0 instanceof Panda && $$0.isAggressive())
/* 1152 */         $$0.setTarget($$1); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PandaPanicGoal
/*      */     extends PanicGoal {
/*      */     private final Panda panda;
/*      */     
/*      */     public PandaPanicGoal(Panda $$0, double $$1) {
/* 1161 */       super((PathfinderMob)$$0, $$1);
/* 1162 */       this.panda = $$0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean shouldPanic() {
/* 1168 */       return (this.mob.isFreezing() || this.mob.isOnFire());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canContinueToUse() {
/* 1173 */       if (this.panda.isSitting()) {
/* 1174 */         this.panda.getNavigation().stop();
/* 1175 */         return false;
/*      */       } 
/* 1177 */       return super.canContinueToUse();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Panda.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */