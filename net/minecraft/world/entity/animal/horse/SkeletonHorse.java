/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class SkeletonHorse extends AbstractHorse {
/*  31 */   private final SkeletonTrapGoal skeletonTrapGoal = new SkeletonTrapGoal(this);
/*     */   
/*     */   private static final int TRAP_MAX_LIFE = 18000;
/*     */   private boolean isTrap;
/*     */   private int trapTime;
/*     */   
/*     */   public SkeletonHorse(EntityType<? extends SkeletonHorse> $$0, Level $$1) {
/*  38 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  42 */     return createBaseHorseAttributes()
/*  43 */       .add(Attributes.MAX_HEALTH, 15.0D)
/*  44 */       .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
/*     */   }
/*     */   
/*     */   public static boolean checkSkeletonHorseSpawnRules(EntityType<? extends Animal> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/*  48 */     if (MobSpawnType.isSpawner($$2)) {
/*  49 */       return (MobSpawnType.ignoresLightRequirements($$2) || isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */     }
/*  51 */     return Animal.checkAnimalSpawnRules($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomizeAttributes(RandomSource $$0) {
/*  56 */     Objects.requireNonNull($$0); getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(generateJumpStrength($$0::nextDouble));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addBehaviourGoals() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  66 */     if (isEyeInFluid(FluidTags.WATER)) {
/*  67 */       return SoundEvents.SKELETON_HORSE_AMBIENT_WATER;
/*     */     }
/*  69 */     return SoundEvents.SKELETON_HORSE_AMBIENT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  75 */     return SoundEvents.SKELETON_HORSE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  80 */     return SoundEvents.SKELETON_HORSE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/*  85 */     if (onGround()) {
/*  86 */       if (isVehicle()) {
/*  87 */         this.gallopSoundCounter++;
/*  88 */         if (this.gallopSoundCounter > 5 && this.gallopSoundCounter % 3 == 0)
/*  89 */           return SoundEvents.SKELETON_HORSE_GALLOP_WATER; 
/*  90 */         if (this.gallopSoundCounter <= 5) {
/*  91 */           return SoundEvents.SKELETON_HORSE_STEP_WATER;
/*     */         }
/*     */       } else {
/*  94 */         return SoundEvents.SKELETON_HORSE_STEP_WATER;
/*     */       } 
/*     */     }
/*  97 */     return SoundEvents.SKELETON_HORSE_SWIM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playSwimSound(float $$0) {
/* 102 */     if (onGround()) {
/* 103 */       super.playSwimSound(0.3F);
/*     */     } else {
/* 105 */       super.playSwimSound(Math.min(0.1F, $$0 * 25.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playJumpSound() {
/* 111 */     if (isInWater()) {
/* 112 */       playSound(SoundEvents.SKELETON_HORSE_JUMP_WATER, 0.4F, 1.0F);
/*     */     } else {
/* 114 */       super.playJumpSound();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 120 */     return MobType.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 125 */     return new Vector3f(0.0F, $$1.height - (isBaby() ? 0.03125F : 0.28125F) * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 130 */     super.aiStep();
/*     */     
/* 132 */     if (isTrap() && this.trapTime++ >= 18000) {
/* 133 */       discard();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 139 */     super.addAdditionalSaveData($$0);
/*     */     
/* 141 */     $$0.putBoolean("SkeletonTrap", isTrap());
/* 142 */     $$0.putInt("SkeletonTrapTime", this.trapTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 147 */     super.readAdditionalSaveData($$0);
/*     */     
/* 149 */     setTrap($$0.getBoolean("SkeletonTrap"));
/* 150 */     this.trapTime = $$0.getInt("SkeletonTrapTime");
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getWaterSlowDown() {
/* 155 */     return 0.96F;
/*     */   }
/*     */   
/*     */   public boolean isTrap() {
/* 159 */     return this.isTrap;
/*     */   }
/*     */   
/*     */   public void setTrap(boolean $$0) {
/* 163 */     if ($$0 == this.isTrap) {
/*     */       return;
/*     */     }
/*     */     
/* 167 */     this.isTrap = $$0;
/* 168 */     if ($$0) {
/* 169 */       this.goalSelector.addGoal(1, this.skeletonTrapGoal);
/*     */     } else {
/* 171 */       this.goalSelector.removeGoal(this.skeletonTrapGoal);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 178 */     return (AgeableMob)EntityType.SKELETON_HORSE.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 183 */     if (!isTamed()) {
/* 184 */       return InteractionResult.PASS;
/*     */     }
/* 186 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\SkeletonHorse.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */