/*     */ package net.minecraft.world.entity.monster;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
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
/*     */ import net.minecraft.world.entity.NeutralMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class ZombifiedPiglin extends Zombie implements NeutralMob {
/*  47 */   private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
/*  48 */   private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION);
/*     */   
/*  50 */   private static final UniformInt FIRST_ANGER_SOUND_DELAY = TimeUtil.rangeOfSeconds(0, 1);
/*     */   
/*     */   private int playFirstAngerSoundIn;
/*  53 */   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
/*     */   
/*     */   private int remainingPersistentAngerTime;
/*     */   @Nullable
/*     */   private UUID persistentAngerTarget;
/*     */   private static final int ALERT_RANGE_Y = 10;
/*  59 */   private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
/*     */   
/*     */   private int ticksUntilNextAlert;
/*     */   private static final float ZOMBIFIED_PIGLIN_EYE_HEIGHT = 1.79F;
/*     */   private static final float ZOMBIFIED_PIGLIN_BABY_EYE_HEIGHT_ADJUSTMENT = 0.82F;
/*     */   
/*     */   public ZombifiedPiglin(EntityType<? extends ZombifiedPiglin> $$0, Level $$1) {
/*  66 */     super((EntityType)$$0, $$1);
/*  67 */     setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPersistentAngerTarget(@Nullable UUID $$0) {
/*  72 */     this.persistentAngerTarget = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addBehaviourGoals() {
/*  77 */     this.goalSelector.addGoal(2, (Goal)new ZombieAttackGoal(this, 1.0D, false));
/*  78 */     this.goalSelector.addGoal(7, (Goal)new WaterAvoidingRandomStrollGoal(this, 1.0D));
/*     */     
/*  80 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
/*  81 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, 10, true, false, this::isAngryAt));
/*  82 */     this.targetSelector.addGoal(3, (Goal)new ResetUniversalAngerTargetGoal((Mob)this, true));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  86 */     return Zombie.createAttributes()
/*  87 */       .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
/*  88 */       .add(Attributes.MOVEMENT_SPEED, 0.23000000417232513D)
/*  89 */       .add(Attributes.ATTACK_DAMAGE, 5.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  94 */     return isBaby() ? 0.96999997F : 1.79F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean convertsInWater() {
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 104 */     AttributeInstance $$0 = getAttribute(Attributes.MOVEMENT_SPEED);
/* 105 */     if (isAngry()) {
/* 106 */       if (!isBaby() && !$$0.hasModifier(SPEED_MODIFIER_ATTACKING)) {
/* 107 */         $$0.addTransientModifier(SPEED_MODIFIER_ATTACKING);
/*     */       }
/* 109 */       maybePlayFirstAngerSound();
/* 110 */     } else if ($$0.hasModifier(SPEED_MODIFIER_ATTACKING)) {
/* 111 */       $$0.removeModifier(SPEED_MODIFIER_ATTACKING.getId());
/*     */     } 
/*     */     
/* 114 */     updatePersistentAnger((ServerLevel)level(), true);
/* 115 */     if (getTarget() != null) {
/* 116 */       maybeAlertOthers();
/*     */     }
/*     */     
/* 119 */     if (isAngry())
/*     */     {
/*     */ 
/*     */       
/* 123 */       this.lastHurtByPlayerTime = this.tickCount;
/*     */     }
/*     */     
/* 126 */     super.customServerAiStep();
/*     */   }
/*     */   
/*     */   private void maybePlayFirstAngerSound() {
/* 130 */     if (this.playFirstAngerSoundIn > 0) {
/* 131 */       this.playFirstAngerSoundIn--;
/* 132 */       if (this.playFirstAngerSoundIn == 0) {
/* 133 */         playAngerSound();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void maybeAlertOthers() {
/* 143 */     if (this.ticksUntilNextAlert > 0) {
/* 144 */       this.ticksUntilNextAlert--;
/*     */       return;
/*     */     } 
/* 147 */     if (getSensing().hasLineOfSight((Entity)getTarget())) {
/* 148 */       alertOthers();
/*     */     }
/* 150 */     this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
/*     */   }
/*     */   
/*     */   private void alertOthers() {
/* 154 */     double $$0 = getAttributeValue(Attributes.FOLLOW_RANGE);
/* 155 */     AABB $$1 = AABB.unitCubeFromLowerCorner(position()).inflate($$0, 10.0D, $$0);
/* 156 */     level().getEntitiesOfClass(ZombifiedPiglin.class, $$1, EntitySelector.NO_SPECTATORS).stream()
/* 157 */       .filter($$0 -> ($$0 != this))
/* 158 */       .filter($$0 -> ($$0.getTarget() == null))
/* 159 */       .filter($$0 -> !$$0.isAlliedTo((Entity)getTarget()))
/* 160 */       .forEach($$0 -> $$0.setTarget(getTarget()));
/*     */   }
/*     */   
/*     */   private void playAngerSound() {
/* 164 */     playSound(SoundEvents.ZOMBIFIED_PIGLIN_ANGRY, getSoundVolume() * 2.0F, getVoicePitch() * 1.8F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(@Nullable LivingEntity $$0) {
/* 169 */     if (getTarget() == null && $$0 != null) {
/*     */ 
/*     */       
/* 172 */       this.playFirstAngerSoundIn = FIRST_ANGER_SOUND_DELAY.sample(this.random);
/* 173 */       this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if ($$0 instanceof Player) {
/* 181 */       setLastHurtByPlayer((Player)$$0);
/*     */     }
/* 183 */     super.setTarget($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startPersistentAngerTimer() {
/* 188 */     setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
/*     */   }
/*     */   
/*     */   public static boolean checkZombifiedPiglinSpawnRules(EntityType<ZombifiedPiglin> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 192 */     return ($$1.getDifficulty() != Difficulty.PEACEFUL && !$$1.getBlockState($$3.below()).is(Blocks.NETHER_WART_BLOCK));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 197 */     return ($$0.isUnobstructed((Entity)this) && !$$0.containsAnyLiquid(getBoundingBox()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 202 */     super.addAdditionalSaveData($$0);
/* 203 */     addPersistentAngerSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 208 */     super.readAdditionalSaveData($$0);
/* 209 */     readPersistentAngerSaveData(level(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRemainingPersistentAngerTime(int $$0) {
/* 214 */     this.remainingPersistentAngerTime = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRemainingPersistentAngerTime() {
/* 219 */     return this.remainingPersistentAngerTime;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 224 */     return isAngry() ? SoundEvents.ZOMBIFIED_PIGLIN_ANGRY : SoundEvents.ZOMBIFIED_PIGLIN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 229 */     return SoundEvents.ZOMBIFIED_PIGLIN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 234 */     return SoundEvents.ZOMBIFIED_PIGLIN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 239 */     setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.GOLDEN_SWORD));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSkull() {
/* 244 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomizeReinforcementsChance() {
/* 249 */     getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getPersistentAngerTarget() {
/* 255 */     return this.persistentAngerTarget;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPreventingPlayerRest(Player $$0) {
/* 260 */     return isAngryAt((LivingEntity)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wantsToPickUp(ItemStack $$0) {
/* 265 */     return canHoldItem($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 270 */     return new Vector3f(0.0F, $$1.height + 0.05F * $$2, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\ZombifiedPiglin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */