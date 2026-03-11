/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ 
/*     */ public abstract class Animal
/*     */   extends AgeableMob {
/*     */   protected static final int PARENT_AGE_AFTER_BREEDING = 6000;
/*     */   private int inLove;
/*     */   @Nullable
/*     */   private UUID loveCause;
/*     */   
/*     */   protected Animal(EntityType<? extends Animal> $$0, Level $$1) {
/*  43 */     super($$0, $$1);
/*  44 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
/*  45 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/*  50 */     if (getAge() != 0) {
/*  51 */       this.inLove = 0;
/*     */     }
/*  53 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/*  58 */     super.aiStep();
/*     */     
/*  60 */     if (getAge() != 0) {
/*  61 */       this.inLove = 0;
/*     */     }
/*     */     
/*  64 */     if (this.inLove > 0) {
/*  65 */       this.inLove--;
/*  66 */       if (this.inLove % 10 == 0) {
/*  67 */         double $$0 = this.random.nextGaussian() * 0.02D;
/*  68 */         double $$1 = this.random.nextGaussian() * 0.02D;
/*  69 */         double $$2 = this.random.nextGaussian() * 0.02D;
/*  70 */         level().addParticle((ParticleOptions)ParticleTypes.HEART, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), $$0, $$1, $$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/*  77 */     if (isInvulnerableTo($$0)) {
/*  78 */       return false;
/*     */     }
/*  80 */     this.inLove = 0;
/*  81 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/*  86 */     if ($$1.getBlockState($$0.below()).is(Blocks.GRASS_BLOCK)) {
/*  87 */       return 10.0F;
/*     */     }
/*  89 */     return $$1.getPathfindingCostFromLightLevels($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  94 */     super.addAdditionalSaveData($$0);
/*  95 */     $$0.putInt("InLove", this.inLove);
/*  96 */     if (this.loveCause != null) {
/*  97 */       $$0.putUUID("LoveCause", this.loveCause);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 103 */     super.readAdditionalSaveData($$0);
/* 104 */     this.inLove = $$0.getInt("InLove");
/* 105 */     this.loveCause = $$0.hasUUID("LoveCause") ? $$0.getUUID("LoveCause") : null;
/*     */   }
/*     */   
/*     */   public static boolean checkAnimalSpawnRules(EntityType<? extends Animal> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 109 */     boolean $$5 = (MobSpawnType.ignoresLightRequirements($$2) || isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/* 110 */     return ($$1.getBlockState($$3.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && $$5);
/*     */   }
/*     */   
/*     */   protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter $$0, BlockPos $$1) {
/* 114 */     return ($$0.getRawBrightness($$1, 0) > 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmbientSoundInterval() {
/* 119 */     return 120;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExperienceReward() {
/* 129 */     return 1 + (level()).random.nextInt(3);
/*     */   }
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 133 */     return $$0.is(Items.WHEAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 138 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 139 */     if (isFood($$2)) {
/* 140 */       int $$3 = getAge();
/* 141 */       if (!(level()).isClientSide && $$3 == 0 && canFallInLove()) {
/* 142 */         usePlayerItem($$0, $$1, $$2);
/* 143 */         setInLove($$0);
/* 144 */         return InteractionResult.SUCCESS;
/* 145 */       }  if (isBaby()) {
/* 146 */         usePlayerItem($$0, $$1, $$2);
/*     */         
/* 148 */         ageUp(getSpeedUpSecondsWhenFeeding(-$$3), true);
/* 149 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */       } 
/* 151 */       if ((level()).isClientSide) {
/* 152 */         return InteractionResult.CONSUME;
/*     */       }
/*     */     } 
/*     */     
/* 156 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */   
/*     */   protected void usePlayerItem(Player $$0, InteractionHand $$1, ItemStack $$2) {
/* 160 */     if (!($$0.getAbilities()).instabuild) {
/* 161 */       $$2.shrink(1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canFallInLove() {
/* 166 */     return (this.inLove <= 0);
/*     */   }
/*     */   
/*     */   public void setInLove(@Nullable Player $$0) {
/* 170 */     this.inLove = 600;
/*     */     
/* 172 */     if ($$0 != null) {
/* 173 */       this.loveCause = $$0.getUUID();
/*     */     }
/*     */     
/* 176 */     level().broadcastEntityEvent((Entity)this, (byte)18);
/*     */   }
/*     */   
/*     */   public void setInLoveTime(int $$0) {
/* 180 */     this.inLove = $$0;
/*     */   }
/*     */   
/*     */   public int getInLoveTime() {
/* 184 */     return this.inLove;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ServerPlayer getLoveCause() {
/* 189 */     if (this.loveCause == null) {
/* 190 */       return null;
/*     */     }
/* 192 */     Player $$0 = level().getPlayerByUUID(this.loveCause);
/* 193 */     if ($$0 instanceof ServerPlayer) {
/* 194 */       return (ServerPlayer)$$0;
/*     */     }
/* 196 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInLove() {
/* 201 */     return (this.inLove > 0);
/*     */   }
/*     */   
/*     */   public void resetLove() {
/* 205 */     this.inLove = 0;
/*     */   }
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 209 */     if ($$0 == this) {
/* 210 */       return false;
/*     */     }
/* 212 */     if ($$0.getClass() != getClass()) {
/* 213 */       return false;
/*     */     }
/* 215 */     return (isInLove() && $$0.isInLove());
/*     */   }
/*     */   
/*     */   public void spawnChildFromBreeding(ServerLevel $$0, Animal $$1) {
/* 219 */     AgeableMob $$2 = getBreedOffspring($$0, $$1);
/* 220 */     if ($$2 == null) {
/*     */       return;
/*     */     }
/* 223 */     $$2.setBaby(true);
/* 224 */     $$2.moveTo(getX(), getY(), getZ(), 0.0F, 0.0F);
/*     */     
/* 226 */     finalizeSpawnChildFromBreeding($$0, $$1, $$2);
/* 227 */     $$0.addFreshEntityWithPassengers((Entity)$$2);
/*     */   }
/*     */   
/*     */   public void finalizeSpawnChildFromBreeding(ServerLevel $$0, Animal $$1, @Nullable AgeableMob $$2) {
/* 231 */     Optional.<ServerPlayer>ofNullable(getLoveCause())
/* 232 */       .or(() -> Optional.ofNullable($$0.getLoveCause()))
/* 233 */       .ifPresent($$2 -> {
/*     */           $$2.awardStat(Stats.ANIMALS_BRED);
/*     */           
/*     */           CriteriaTriggers.BRED_ANIMALS.trigger($$2, this, $$0, $$1);
/*     */         });
/* 238 */     setAge(6000);
/* 239 */     $$1.setAge(6000);
/* 240 */     resetLove();
/* 241 */     $$1.resetLove();
/*     */     
/* 243 */     $$0.broadcastEntityEvent((Entity)this, (byte)18);
/*     */     
/* 245 */     if ($$0.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
/* 246 */       $$0.addFreshEntity((Entity)new ExperienceOrb((Level)$$0, getX(), getY(), getZ(), getRandom().nextInt(7) + 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 252 */     if ($$0 == 18) {
/* 253 */       for (int $$1 = 0; $$1 < 7; $$1++) {
/* 254 */         double $$2 = this.random.nextGaussian() * 0.02D;
/* 255 */         double $$3 = this.random.nextGaussian() * 0.02D;
/* 256 */         double $$4 = this.random.nextGaussian() * 0.02D;
/* 257 */         level().addParticle((ParticleOptions)ParticleTypes.HEART, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), $$2, $$3, $$4);
/*     */       } 
/*     */     } else {
/* 260 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Animal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */