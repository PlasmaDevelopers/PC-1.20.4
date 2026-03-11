/*     */ package net.minecraft.world.entity.animal.frog;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.animal.AbstractFish;
/*     */ import net.minecraft.world.entity.animal.Bucketable;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class Tadpole extends AbstractFish {
/*     */   @VisibleForTesting
/*  41 */   public static int ticksToBeFrog = Math.abs(-24000);
/*  42 */   public static float HITBOX_WIDTH = 0.4F;
/*  43 */   public static float HITBOX_HEIGHT = 0.3F;
/*     */   
/*     */   private int age;
/*  46 */   protected static final ImmutableList<SensorType<? extends Sensor<? super Tadpole>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.FROG_TEMPTATIONS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.BREED_TARGET, MemoryModuleType.IS_PANICKING);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tadpole(EntityType<? extends AbstractFish> $$0, Level $$1) {
/*  68 */     super($$0, $$1);
/*     */     
/*  70 */     this.moveControl = (MoveControl)new SmoothSwimmingMoveControl((Mob)this, 85, 10, 0.02F, 0.1F, true);
/*  71 */     this.lookControl = (LookControl)new SmoothSwimmingLookControl((Mob)this, 10);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/*  76 */     return (PathNavigation)new WaterBoundPathNavigation((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Tadpole> brainProvider() {
/*  81 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/*  86 */     return TadpoleAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Tadpole> getBrain() {
/*  92 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getFlopSound() {
/*  97 */     return SoundEvents.TADPOLE_FLOP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 102 */     level().getProfiler().push("tadpoleBrain");
/* 103 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 104 */     level().getProfiler().pop();
/*     */     
/* 106 */     level().getProfiler().push("tadpoleActivityUpdate");
/* 107 */     TadpoleAi.updateActivity(this);
/* 108 */     level().getProfiler().pop();
/*     */     
/* 110 */     super.customServerAiStep();
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 114 */     return Mob.createMobAttributes()
/* 115 */       .add(Attributes.MOVEMENT_SPEED, 1.0D)
/* 116 */       .add(Attributes.MAX_HEALTH, 6.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 121 */     super.aiStep();
/*     */     
/* 123 */     if (!(level()).isClientSide) {
/* 124 */       setAge(this.age + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 130 */     super.addAdditionalSaveData($$0);
/* 131 */     $$0.putInt("Age", this.age);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 136 */     super.readAdditionalSaveData($$0);
/* 137 */     setAge($$0.getInt("Age"));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 149 */     return SoundEvents.TADPOLE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 155 */     return SoundEvents.TADPOLE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 160 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 161 */     if (isFood($$2)) {
/* 162 */       feed($$0, $$2);
/* 163 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/* 165 */     return Bucketable.bucketMobPickup($$0, $$1, (LivingEntity)this).orElse(super.mobInteract($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 170 */     super.sendDebugPackets();
/* 171 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean fromBucket() {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFromBucket(boolean $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveToBucketTag(ItemStack $$0) {
/* 187 */     Bucketable.saveDefaultDataToBucketTag((Mob)this, $$0);
/*     */     
/* 189 */     CompoundTag $$1 = $$0.getOrCreateTag();
/* 190 */     $$1.putInt("Age", getAge());
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadFromBucketTag(CompoundTag $$0) {
/* 195 */     Bucketable.loadDefaultDataFromBucketTag((Mob)this, $$0);
/*     */     
/* 197 */     if ($$0.contains("Age")) {
/* 198 */       setAge($$0.getInt("Age"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBucketItemStack() {
/* 204 */     return new ItemStack((ItemLike)Items.TADPOLE_BUCKET);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getPickupSound() {
/* 209 */     return SoundEvents.BUCKET_FILL_TADPOLE;
/*     */   }
/*     */   
/*     */   private boolean isFood(ItemStack $$0) {
/* 213 */     return Frog.TEMPTATION_ITEM.test($$0);
/*     */   }
/*     */   
/*     */   private void feed(Player $$0, ItemStack $$1) {
/* 217 */     usePlayerItem($$0, $$1);
/* 218 */     ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(getTicksLeftUntilAdult()));
/* 219 */     level().addParticle((ParticleOptions)ParticleTypes.HAPPY_VILLAGER, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   private void usePlayerItem(Player $$0, ItemStack $$1) {
/* 223 */     if (!($$0.getAbilities()).instabuild) {
/* 224 */       $$1.shrink(1);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getAge() {
/* 229 */     return this.age;
/*     */   }
/*     */   
/*     */   private void ageUp(int $$0) {
/* 233 */     setAge(this.age + $$0 * 20);
/*     */   }
/*     */   
/*     */   private void setAge(int $$0) {
/* 237 */     this.age = $$0;
/*     */     
/* 239 */     if (this.age >= ticksToBeFrog) {
/* 240 */       ageUp();
/*     */     }
/*     */   }
/*     */   
/*     */   private void ageUp() {
/* 245 */     Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$0 = (ServerLevel)level;
/* 246 */       Frog $$1 = (Frog)EntityType.FROG.create(level());
/* 247 */       if ($$1 != null) {
/* 248 */         $$1.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
/* 249 */         $$1.finalizeSpawn((ServerLevelAccessor)$$0, level().getCurrentDifficultyAt($$1.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
/* 250 */         $$1.setNoAi(isNoAi());
/* 251 */         if (hasCustomName()) {
/* 252 */           $$1.setCustomName(getCustomName());
/* 253 */           $$1.setCustomNameVisible(isCustomNameVisible());
/*     */         } 
/* 255 */         $$1.setPersistenceRequired();
/* 256 */         playSound(SoundEvents.TADPOLE_GROW_UP, 0.15F, 1.0F);
/* 257 */         $$0.addFreshEntityWithPassengers((Entity)$$1);
/* 258 */         discard();
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   private int getTicksLeftUntilAdult() {
/* 264 */     return Math.max(0, ticksToBeFrog - this.age);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDropExperience() {
/* 269 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\frog\Tadpole.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */