/*     */ package net.minecraft.world.entity.animal;
/*     */ import com.google.common.collect.UnmodifiableIterator;
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
/*     */ import net.minecraft.world.Difficulty;
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
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.Saddleable;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.monster.ZombifiedPiglin;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.vehicle.DismountHelper;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Pig extends Animal implements ItemSteerable, Saddleable {
/*  54 */   private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.BOOLEAN);
/*  55 */   private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.INT);
/*  56 */   private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemLike[] { (ItemLike)Items.CARROT, (ItemLike)Items.POTATO, (ItemLike)Items.BEETROOT });
/*     */   
/*     */   private final ItemBasedSteering steering;
/*     */   
/*     */   public Pig(EntityType<? extends Pig> $$0, Level $$1) {
/*  61 */     super((EntityType)$$0, $$1);
/*  62 */     this.steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  67 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  68 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 1.25D));
/*  69 */     this.goalSelector.addGoal(3, (Goal)new BreedGoal(this, 1.0D));
/*  70 */     this.goalSelector.addGoal(4, (Goal)new TemptGoal((PathfinderMob)this, 1.2D, Ingredient.of(new ItemLike[] { (ItemLike)Items.CARROT_ON_A_STICK }, ), false));
/*  71 */     this.goalSelector.addGoal(4, (Goal)new TemptGoal((PathfinderMob)this, 1.2D, FOOD_ITEMS, false));
/*  72 */     this.goalSelector.addGoal(5, (Goal)new FollowParentGoal(this, 1.1D));
/*  73 */     this.goalSelector.addGoal(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/*  74 */     this.goalSelector.addGoal(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  75 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  79 */     return Mob.createMobAttributes()
/*  80 */       .add(Attributes.MAX_HEALTH, 10.0D)
/*  81 */       .add(Attributes.MOVEMENT_SPEED, 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getControllingPassenger() {
/*  87 */     if (isSaddled()) { Entity entity = getFirstPassenger(); if (entity instanceof Player) { Player $$0 = (Player)entity; if ($$0.isHolding(Items.CARROT_ON_A_STICK))
/*  88 */           return (LivingEntity)$$0;  }
/*     */        }
/*  90 */      return super.getControllingPassenger();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  95 */     if (DATA_BOOST_TIME.equals($$0) && (level()).isClientSide) {
/*  96 */       this.steering.onSynced();
/*     */     }
/*  98 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 103 */     super.defineSynchedData();
/* 104 */     this.entityData.define(DATA_SADDLE_ID, Boolean.valueOf(false));
/* 105 */     this.entityData.define(DATA_BOOST_TIME, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 110 */     super.addAdditionalSaveData($$0);
/* 111 */     this.steering.addAdditionalSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 116 */     super.readAdditionalSaveData($$0);
/* 117 */     this.steering.readAdditionalSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 122 */     return SoundEvents.PIG_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 127 */     return SoundEvents.PIG_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 132 */     return SoundEvents.PIG_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 137 */     playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 142 */     boolean $$2 = isFood($$0.getItemInHand($$1));
/*     */     
/* 144 */     if (!$$2 && isSaddled() && !isVehicle() && !$$0.isSecondaryUseActive()) {
/* 145 */       if (!(level()).isClientSide) {
/* 146 */         $$0.startRiding((Entity)this);
/*     */       }
/* 148 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 151 */     InteractionResult $$3 = super.mobInteract($$0, $$1);
/* 152 */     if (!$$3.consumesAction()) {
/* 153 */       ItemStack $$4 = $$0.getItemInHand($$1);
/* 154 */       if ($$4.is(Items.SADDLE)) {
/* 155 */         return $$4.interactLivingEntity($$0, (LivingEntity)this, $$1);
/*     */       }
/* 157 */       return InteractionResult.PASS;
/*     */     } 
/* 159 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSaddleable() {
/* 164 */     return (isAlive() && !isBaby());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropEquipment() {
/* 169 */     super.dropEquipment();
/* 170 */     if (isSaddled()) {
/* 171 */       spawnAtLocation((ItemLike)Items.SADDLE);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSaddled() {
/* 177 */     return this.steering.hasSaddle();
/*     */   }
/*     */ 
/*     */   
/*     */   public void equipSaddle(@Nullable SoundSource $$0) {
/* 182 */     this.steering.setSaddle(true);
/* 183 */     if ($$0 != null) {
/* 184 */       level().playSound(null, (Entity)this, SoundEvents.PIG_SADDLE, $$0, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getDismountLocationForPassenger(LivingEntity $$0) {
/* 190 */     Direction $$1 = getMotionDirection();
/* 191 */     if ($$1.getAxis() == Direction.Axis.Y) {
/* 192 */       return super.getDismountLocationForPassenger($$0);
/*     */     }
/*     */     
/* 195 */     int[][] $$2 = DismountHelper.offsetsForDirection($$1);
/* 196 */     BlockPos $$3 = blockPosition();
/* 197 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/*     */     
/* 199 */     for (UnmodifiableIterator<Pose> unmodifiableIterator = $$0.getDismountPoses().iterator(); unmodifiableIterator.hasNext(); ) { Pose $$5 = unmodifiableIterator.next();
/* 200 */       AABB $$6 = $$0.getLocalBoundsForPose($$5);
/*     */       
/* 202 */       for (int[] $$7 : $$2) {
/* 203 */         $$4.set($$3.getX() + $$7[0], $$3.getY(), $$3.getZ() + $$7[1]);
/*     */         
/* 205 */         double $$8 = level().getBlockFloorHeight((BlockPos)$$4);
/* 206 */         if (DismountHelper.isBlockFloorValid($$8)) {
/*     */ 
/*     */ 
/*     */           
/* 210 */           Vec3 $$9 = Vec3.upFromBottomCenterOf((Vec3i)$$4, $$8);
/* 211 */           if (DismountHelper.canDismountTo((CollisionGetter)level(), $$0, $$6.move($$9))) {
/* 212 */             $$0.setPose($$5);
/* 213 */             return $$9;
/*     */           } 
/*     */         } 
/*     */       }  }
/*     */     
/* 218 */     return super.getDismountLocationForPassenger($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
/* 223 */     if ($$0.getDifficulty() != Difficulty.PEACEFUL) {
/* 224 */       ZombifiedPiglin $$2 = (ZombifiedPiglin)EntityType.ZOMBIFIED_PIGLIN.create((Level)$$0);
/* 225 */       if ($$2 != null) {
/* 226 */         $$2.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.GOLDEN_SWORD));
/* 227 */         $$2.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
/* 228 */         $$2.setNoAi(isNoAi());
/* 229 */         $$2.setBaby(isBaby());
/* 230 */         if (hasCustomName()) {
/* 231 */           $$2.setCustomName(getCustomName());
/* 232 */           $$2.setCustomNameVisible(isCustomNameVisible());
/*     */         } 
/* 234 */         $$2.setPersistenceRequired();
/* 235 */         $$0.addFreshEntity((Entity)$$2);
/* 236 */         discard();
/*     */       } else {
/* 238 */         super.thunderHit($$0, $$1);
/*     */       } 
/*     */     } else {
/* 241 */       super.thunderHit($$0, $$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tickRidden(Player $$0, Vec3 $$1) {
/* 247 */     super.tickRidden($$0, $$1);
/* 248 */     setRot($$0.getYRot(), $$0.getXRot() * 0.5F);
/* 249 */     this.yRotO = this.yBodyRot = this.yHeadRot = getYRot();
/* 250 */     this.steering.tickBoost();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getRiddenInput(Player $$0, Vec3 $$1) {
/* 255 */     return new Vec3(0.0D, 0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getRiddenSpeed(Player $$0) {
/* 260 */     return (float)(getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225D * this.steering.boostFactor());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean boost() {
/* 265 */     return this.steering.boost(getRandom());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Pig getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 271 */     return (Pig)EntityType.PIG.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 276 */     return FOOD_ITEMS.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 281 */     return new Vec3(0.0D, (0.6F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 286 */     return new Vector3f(0.0F, $$1.height - 0.03125F * $$2, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Pig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */