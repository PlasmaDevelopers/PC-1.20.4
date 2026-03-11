/*     */ package net.minecraft.world.entity.animal;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Chicken extends Animal {
/*  42 */   private static final Ingredient FOOD_ITEMS = Ingredient.of(new ItemLike[] { (ItemLike)Items.WHEAT_SEEDS, (ItemLike)Items.MELON_SEEDS, (ItemLike)Items.PUMPKIN_SEEDS, (ItemLike)Items.BEETROOT_SEEDS, (ItemLike)Items.TORCHFLOWER_SEEDS, (ItemLike)Items.PITCHER_POD });
/*     */   
/*     */   public float flap;
/*     */   public float flapSpeed;
/*     */   public float oFlapSpeed;
/*     */   public float oFlap;
/*  48 */   public float flapping = 1.0F;
/*  49 */   private float nextFlap = 1.0F;
/*     */   public int eggTime;
/*     */   public boolean isChickenJockey;
/*     */   
/*     */   public Chicken(EntityType<? extends Chicken> $$0, Level $$1) {
/*  54 */     super((EntityType)$$0, $$1);
/*     */     
/*  56 */     this.eggTime = this.random.nextInt(6000) + 6000;
/*     */     
/*  58 */     setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  63 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  64 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 1.4D));
/*  65 */     this.goalSelector.addGoal(2, (Goal)new BreedGoal(this, 1.0D));
/*  66 */     this.goalSelector.addGoal(3, (Goal)new TemptGoal((PathfinderMob)this, 1.0D, FOOD_ITEMS, false));
/*  67 */     this.goalSelector.addGoal(4, (Goal)new FollowParentGoal(this, 1.1D));
/*  68 */     this.goalSelector.addGoal(5, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/*  69 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  70 */     this.goalSelector.addGoal(7, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  75 */     return isBaby() ? ($$1.height * 0.85F) : ($$1.height * 0.92F);
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  79 */     return Mob.createMobAttributes()
/*  80 */       .add(Attributes.MAX_HEALTH, 4.0D)
/*  81 */       .add(Attributes.MOVEMENT_SPEED, 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/*  86 */     super.aiStep();
/*     */     
/*  88 */     this.oFlap = this.flap;
/*  89 */     this.oFlapSpeed = this.flapSpeed;
/*     */     
/*  91 */     this.flapSpeed += (onGround() ? -1.0F : 4.0F) * 0.3F;
/*  92 */     this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
/*     */     
/*  94 */     if (!onGround() && this.flapping < 1.0F) {
/*  95 */       this.flapping = 1.0F;
/*     */     }
/*  97 */     this.flapping *= 0.9F;
/*     */     
/*  99 */     Vec3 $$0 = getDeltaMovement();
/* 100 */     if (!onGround() && $$0.y < 0.0D) {
/* 101 */       setDeltaMovement($$0.multiply(1.0D, 0.6D, 1.0D));
/*     */     }
/*     */     
/* 104 */     this.flap += this.flapping * 2.0F;
/*     */     
/* 106 */     if (!(level()).isClientSide && isAlive() && !isBaby() && !isChickenJockey() && --this.eggTime <= 0) {
/* 107 */       playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 108 */       spawnAtLocation((ItemLike)Items.EGG);
/* 109 */       gameEvent(GameEvent.ENTITY_PLACE);
/* 110 */       this.eggTime = this.random.nextInt(6000) + 6000;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFlapping() {
/* 116 */     return (this.flyDist > this.nextFlap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFlap() {
/* 121 */     this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 126 */     return SoundEvents.CHICKEN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 131 */     return SoundEvents.CHICKEN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 136 */     return SoundEvents.CHICKEN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 141 */     playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Chicken getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 147 */     return (Chicken)EntityType.CHICKEN.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 152 */     return FOOD_ITEMS.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExperienceReward() {
/* 157 */     if (isChickenJockey()) {
/* 158 */       return 10;
/*     */     }
/* 160 */     return super.getExperienceReward();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 165 */     super.readAdditionalSaveData($$0);
/* 166 */     this.isChickenJockey = $$0.getBoolean("IsChickenJockey");
/* 167 */     if ($$0.contains("EggLayTime")) {
/* 168 */       this.eggTime = $$0.getInt("EggLayTime");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 174 */     super.addAdditionalSaveData($$0);
/* 175 */     $$0.putBoolean("IsChickenJockey", this.isChickenJockey);
/* 176 */     $$0.putInt("EggLayTime", this.eggTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 181 */     return isChickenJockey();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void positionRider(Entity $$0, Entity.MoveFunction $$1) {
/* 186 */     super.positionRider($$0, $$1);
/* 187 */     if ($$0 instanceof LivingEntity) {
/* 188 */       ((LivingEntity)$$0).yBodyRot = this.yBodyRot;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 194 */     return new Vector3f(0.0F, $$1.height, -0.1F * $$2);
/*     */   }
/*     */   
/*     */   public boolean isChickenJockey() {
/* 198 */     return this.isChickenJockey;
/*     */   }
/*     */   
/*     */   public void setChickenJockey(boolean $$0) {
/* 202 */     this.isChickenJockey = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Chicken.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */