/*     */ package net.minecraft.world.entity.animal;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class AbstractFish extends WaterAnimal implements Bucketable {
/*  35 */   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(AbstractFish.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   public AbstractFish(EntityType<? extends AbstractFish> $$0, Level $$1) {
/*  38 */     super((EntityType)$$0, $$1);
/*     */     
/*  40 */     this.moveControl = new FishMoveControl(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  45 */     return $$1.height * 0.65F;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  49 */     return Mob.createMobAttributes()
/*  50 */       .add(Attributes.MAX_HEALTH, 3.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresCustomPersistence() {
/*  55 */     return (super.requiresCustomPersistence() || fromBucket());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/*  60 */     return (!fromBucket() && !hasCustomName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSpawnClusterSize() {
/*  65 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  70 */     super.defineSynchedData();
/*     */     
/*  72 */     this.entityData.define(FROM_BUCKET, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromBucket() {
/*  77 */     return ((Boolean)this.entityData.get(FROM_BUCKET)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFromBucket(boolean $$0) {
/*  82 */     this.entityData.set(FROM_BUCKET, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  87 */     super.addAdditionalSaveData($$0);
/*     */     
/*  89 */     $$0.putBoolean("FromBucket", fromBucket());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  94 */     super.readAdditionalSaveData($$0);
/*     */     
/*  96 */     setFromBucket($$0.getBoolean("FromBucket"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 101 */     super.registerGoals();
/*     */     
/* 103 */     this.goalSelector.addGoal(0, (Goal)new PanicGoal(this, 1.25D));
/* 104 */     Objects.requireNonNull(EntitySelector.NO_SPECTATORS); this.goalSelector.addGoal(2, (Goal)new AvoidEntityGoal(this, Player.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
/* 105 */     this.goalSelector.addGoal(4, (Goal)new FishSwimGoal(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 110 */     return (PathNavigation)new WaterBoundPathNavigation((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 115 */     if (isEffectiveAi() && isInWater()) {
/* 116 */       moveRelative(0.01F, $$0);
/* 117 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 119 */       setDeltaMovement(getDeltaMovement().scale(0.9D));
/* 120 */       if (getTarget() == null) {
/* 121 */         setDeltaMovement(getDeltaMovement().add(0.0D, -0.005D, 0.0D));
/*     */       }
/*     */     } else {
/* 124 */       super.travel($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 130 */     if (!isInWater() && onGround() && this.verticalCollision) {
/* 131 */       setDeltaMovement(getDeltaMovement().add(((this.random
/* 132 */             .nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4000000059604645D, ((this.random
/*     */             
/* 134 */             .nextFloat() * 2.0F - 1.0F) * 0.05F)));
/*     */       
/* 136 */       setOnGround(false);
/* 137 */       this.hasImpulse = true;
/* 138 */       playSound(getFlopSound(), getSoundVolume(), getVoicePitch());
/*     */     } 
/*     */     
/* 141 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   protected InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 146 */     return Bucketable.<AbstractFish>bucketMobPickup($$0, $$1, this).orElse(super.mobInteract($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveToBucketTag(ItemStack $$0) {
/* 151 */     Bucketable.saveDefaultDataToBucketTag((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadFromBucketTag(CompoundTag $$0) {
/* 156 */     Bucketable.loadDefaultDataFromBucketTag((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getPickupSound() {
/* 161 */     return SoundEvents.BUCKET_FILL_FISH;
/*     */   }
/*     */   
/*     */   private static class FishSwimGoal extends RandomSwimmingGoal {
/*     */     private final AbstractFish fish;
/*     */     
/*     */     public FishSwimGoal(AbstractFish $$0) {
/* 168 */       super($$0, 1.0D, 40);
/* 169 */       this.fish = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 174 */       return (this.fish.canRandomSwim() && super.canUse());
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean canRandomSwim() {
/* 179 */     return true;
/*     */   }
/*     */   protected abstract SoundEvent getFlopSound();
/*     */   
/*     */   private static class FishMoveControl extends MoveControl { private final AbstractFish fish;
/*     */     
/*     */     FishMoveControl(AbstractFish $$0) {
/* 186 */       super((Mob)$$0);
/* 187 */       this.fish = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 192 */       if (this.fish.isEyeInFluid(FluidTags.WATER))
/*     */       {
/* 194 */         this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
/*     */       }
/*     */       
/* 197 */       if (this.operation != MoveControl.Operation.MOVE_TO || this.fish.getNavigation().isDone()) {
/* 198 */         this.fish.setSpeed(0.0F);
/*     */         
/*     */         return;
/*     */       } 
/* 202 */       float $$0 = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
/* 203 */       this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), $$0));
/*     */       
/* 205 */       double $$1 = this.wantedX - this.fish.getX();
/* 206 */       double $$2 = this.wantedY - this.fish.getY();
/* 207 */       double $$3 = this.wantedZ - this.fish.getZ();
/*     */       
/* 209 */       if ($$2 != 0.0D) {
/* 210 */         double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/*     */         
/* 212 */         this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, this.fish.getSpeed() * $$2 / $$4 * 0.1D, 0.0D));
/*     */       } 
/*     */       
/* 215 */       if ($$1 != 0.0D || $$3 != 0.0D) {
/* 216 */         float $$5 = (float)(Mth.atan2($$3, $$1) * 57.2957763671875D) - 90.0F;
/*     */         
/* 218 */         this.fish.setYRot(rotlerp(this.fish.getYRot(), $$5, 90.0F));
/* 219 */         this.fish.yBodyRot = this.fish.getYRot();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/* 228 */     return SoundEvents.FISH_SWIM;
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\AbstractFish.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */