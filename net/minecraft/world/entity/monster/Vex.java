/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.TraceableEntity;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.TargetGoal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Vex
/*     */   extends Monster implements TraceableEntity {
/*     */   public static final float FLAP_DEGREES_PER_TICK = 45.836624F;
/*  51 */   public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
/*     */   
/*  53 */   protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Vex.class, EntityDataSerializers.BYTE);
/*     */   
/*     */   private static final int FLAG_IS_CHARGING = 1;
/*     */   
/*     */   @Nullable
/*     */   Mob owner;
/*     */   @Nullable
/*     */   private BlockPos boundOrigin;
/*     */   private boolean hasLimitedLife;
/*     */   private int limitedLifeTicks;
/*     */   
/*     */   public Vex(EntityType<? extends Vex> $$0, Level $$1) {
/*  65 */     super((EntityType)$$0, $$1);
/*     */     
/*  67 */     this.moveControl = new VexMoveControl(this);
/*     */     
/*  69 */     this.xpReward = 3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  74 */     return $$1.height - 0.28125F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlapping() {
/*  81 */     return (this.tickCount % TICKS_PER_FLAP == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(MoverType $$0, Vec3 $$1) {
/*  86 */     super.move($$0, $$1);
/*     */     
/*  88 */     checkInsideBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  93 */     this.noPhysics = true;
/*  94 */     super.tick();
/*  95 */     this.noPhysics = false;
/*     */     
/*  97 */     setNoGravity(true);
/*     */     
/*  99 */     if (this.hasLimitedLife && 
/* 100 */       --this.limitedLifeTicks <= 0) {
/* 101 */       this.limitedLifeTicks = 20;
/* 102 */       hurt(damageSources().starve(), 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 109 */     super.registerGoals();
/*     */     
/* 111 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/* 112 */     this.goalSelector.addGoal(4, new VexChargeAttackGoal());
/* 113 */     this.goalSelector.addGoal(8, new VexRandomMoveGoal());
/* 114 */     this.goalSelector.addGoal(9, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0F, 1.0F));
/* 115 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0F));
/*     */     
/* 117 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[] { Raider.class })).setAlertOthers(new Class[0]));
/* 118 */     this.targetSelector.addGoal(2, (Goal)new VexCopyOwnerTargetGoal(this));
/* 119 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 123 */     return Monster.createMonsterAttributes()
/* 124 */       .add(Attributes.MAX_HEALTH, 14.0D)
/* 125 */       .add(Attributes.ATTACK_DAMAGE, 4.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 130 */     super.defineSynchedData();
/*     */     
/* 132 */     this.entityData.define(DATA_FLAGS_ID, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 137 */     super.readAdditionalSaveData($$0);
/*     */     
/* 139 */     if ($$0.contains("BoundX")) {
/* 140 */       this.boundOrigin = new BlockPos($$0.getInt("BoundX"), $$0.getInt("BoundY"), $$0.getInt("BoundZ"));
/*     */     }
/* 142 */     if ($$0.contains("LifeTicks")) {
/* 143 */       setLimitedLife($$0.getInt("LifeTicks"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void restoreFrom(Entity $$0) {
/* 149 */     super.restoreFrom($$0);
/* 150 */     if ($$0 instanceof Vex) { Vex $$1 = (Vex)$$0;
/* 151 */       this.owner = $$1.getOwner(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 157 */     super.addAdditionalSaveData($$0);
/*     */     
/* 159 */     if (this.boundOrigin != null) {
/* 160 */       $$0.putInt("BoundX", this.boundOrigin.getX());
/* 161 */       $$0.putInt("BoundY", this.boundOrigin.getY());
/* 162 */       $$0.putInt("BoundZ", this.boundOrigin.getZ());
/*     */     } 
/* 164 */     if (this.hasLimitedLife) {
/* 165 */       $$0.putInt("LifeTicks", this.limitedLifeTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Mob getOwner() {
/* 172 */     return this.owner;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getBoundOrigin() {
/* 177 */     return this.boundOrigin;
/*     */   }
/*     */   
/*     */   public void setBoundOrigin(@Nullable BlockPos $$0) {
/* 181 */     this.boundOrigin = $$0;
/*     */   }
/*     */   
/*     */   private boolean getVexFlag(int $$0) {
/* 185 */     int $$1 = ((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue();
/* 186 */     return (($$1 & $$0) != 0);
/*     */   }
/*     */   
/*     */   private void setVexFlag(int $$0, boolean $$1) {
/* 190 */     int $$2 = ((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue();
/* 191 */     if ($$1) {
/* 192 */       $$2 |= $$0;
/*     */     } else {
/* 194 */       $$2 &= $$0 ^ 0xFFFFFFFF;
/*     */     } 
/* 196 */     this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)($$2 & 0xFF)));
/*     */   }
/*     */   
/*     */   public boolean isCharging() {
/* 200 */     return getVexFlag(1);
/*     */   }
/*     */   
/*     */   public void setIsCharging(boolean $$0) {
/* 204 */     setVexFlag(1, $$0);
/*     */   }
/*     */   
/*     */   public void setOwner(Mob $$0) {
/* 208 */     this.owner = $$0;
/*     */   }
/*     */   
/*     */   public void setLimitedLife(int $$0) {
/* 212 */     this.hasLimitedLife = true;
/* 213 */     this.limitedLifeTicks = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 218 */     return SoundEvents.VEX_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 223 */     return SoundEvents.VEX_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 228 */     return SoundEvents.VEX_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLightLevelDependentMagicValue() {
/* 233 */     return 1.0F;
/*     */   }
/*     */   
/*     */   private class VexMoveControl extends MoveControl {
/*     */     public VexMoveControl(Vex $$0) {
/* 238 */       super((Mob)$$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 243 */       if (this.operation != MoveControl.Operation.MOVE_TO) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 251 */       Vec3 $$0 = new Vec3(this.wantedX - Vex.this.getX(), this.wantedY - Vex.this.getY(), this.wantedZ - Vex.this.getZ());
/*     */ 
/*     */       
/* 254 */       double $$1 = $$0.length();
/* 255 */       if ($$1 < Vex.this.getBoundingBox().getSize()) {
/* 256 */         this.operation = MoveControl.Operation.WAIT;
/* 257 */         Vex.this.setDeltaMovement(Vex.this.getDeltaMovement().scale(0.5D));
/*     */       } else {
/* 259 */         Vex.this.setDeltaMovement(Vex.this.getDeltaMovement().add($$0.scale(this.speedModifier * 0.05D / $$1)));
/*     */         
/* 261 */         if (Vex.this.getTarget() == null) {
/* 262 */           Vec3 $$2 = Vex.this.getDeltaMovement();
/* 263 */           Vex.this.setYRot(-((float)Mth.atan2($$2.x, $$2.z)) * 57.295776F);
/* 264 */           Vex.this.yBodyRot = Vex.this.getYRot();
/*     */         } else {
/*     */           
/* 267 */           double $$3 = Vex.this.getTarget().getX() - Vex.this.getX();
/* 268 */           double $$4 = Vex.this.getTarget().getZ() - Vex.this.getZ();
/* 269 */           Vex.this.setYRot(-((float)Mth.atan2($$3, $$4)) * 57.295776F);
/* 270 */           Vex.this.yBodyRot = Vex.this.getYRot();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class VexChargeAttackGoal extends Goal {
/*     */     public VexChargeAttackGoal() {
/* 278 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 283 */       LivingEntity $$0 = Vex.this.getTarget();
/* 284 */       if ($$0 != null && $$0.isAlive() && !Vex.this.getMoveControl().hasWanted() && Vex.this.random.nextInt(reducedTickDelay(7)) == 0) {
/* 285 */         return (Vex.this.distanceToSqr((Entity)$$0) > 4.0D);
/*     */       }
/* 287 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 292 */       return (Vex.this.getMoveControl().hasWanted() && Vex.this.isCharging() && Vex.this.getTarget() != null && Vex.this.getTarget().isAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 297 */       LivingEntity $$0 = Vex.this.getTarget();
/* 298 */       if ($$0 != null) {
/* 299 */         Vec3 $$1 = $$0.getEyePosition();
/* 300 */         Vex.this.moveControl.setWantedPosition($$1.x, $$1.y, $$1.z, 1.0D);
/*     */       } 
/* 302 */       Vex.this.setIsCharging(true);
/* 303 */       Vex.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 308 */       Vex.this.setIsCharging(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresUpdateEveryTick() {
/* 313 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 318 */       LivingEntity $$0 = Vex.this.getTarget();
/* 319 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/* 322 */       if (Vex.this.getBoundingBox().intersects($$0.getBoundingBox())) {
/* 323 */         Vex.this.doHurtTarget((Entity)$$0);
/* 324 */         Vex.this.setIsCharging(false);
/*     */       } else {
/* 326 */         double $$1 = Vex.this.distanceToSqr((Entity)$$0);
/* 327 */         if ($$1 < 9.0D) {
/* 328 */           Vec3 $$2 = $$0.getEyePosition();
/* 329 */           Vex.this.moveControl.setWantedPosition($$2.x, $$2.y, $$2.z, 1.0D);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class VexRandomMoveGoal extends Goal {
/*     */     public VexRandomMoveGoal() {
/* 337 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 342 */       return (!Vex.this.getMoveControl().hasWanted() && Vex.this.random.nextInt(reducedTickDelay(7)) == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 347 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 352 */       BlockPos $$0 = Vex.this.getBoundOrigin();
/* 353 */       if ($$0 == null)
/*     */       {
/* 355 */         $$0 = Vex.this.blockPosition();
/*     */       }
/*     */       
/* 358 */       for (int $$1 = 0; $$1 < 3; $$1++) {
/* 359 */         BlockPos $$2 = $$0.offset(Vex.this.random.nextInt(15) - 7, Vex.this.random.nextInt(11) - 5, Vex.this.random.nextInt(15) - 7);
/* 360 */         if (Vex.this.level().isEmptyBlock($$2)) {
/* 361 */           Vex.this.moveControl.setWantedPosition($$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, 0.25D);
/* 362 */           if (Vex.this.getTarget() == null) {
/* 363 */             Vex.this.getLookControl().setLookAt($$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, 180.0F, 20.0F);
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 374 */     RandomSource $$5 = $$0.getRandom();
/* 375 */     populateDefaultEquipmentSlots($$5, $$1);
/* 376 */     populateDefaultEquipmentEnchantments($$5, $$1);
/*     */     
/* 378 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 383 */     setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.IRON_SWORD));
/* 384 */     setDropChance(EquipmentSlot.MAINHAND, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 389 */     return 0.04F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 394 */     return new Vector3f(0.0F, $$1.height - 0.0625F * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   private class VexCopyOwnerTargetGoal extends TargetGoal {
/* 398 */     private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
/*     */     
/*     */     public VexCopyOwnerTargetGoal(PathfinderMob $$0) {
/* 401 */       super((Mob)$$0, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 406 */       return (Vex.this.owner != null && Vex.this.owner.getTarget() != null && canAttack(Vex.this.owner.getTarget(), this.copyOwnerTargeting));
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 411 */       Vex.this.setTarget(Vex.this.owner.getTarget());
/* 412 */       super.start();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Vex.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */