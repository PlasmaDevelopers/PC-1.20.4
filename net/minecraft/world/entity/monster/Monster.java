/*     */ package net.minecraft.world.entity.monster;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.ProjectileWeaponItem;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ 
/*     */ public abstract class Monster extends PathfinderMob implements Enemy {
/*     */   protected Monster(EntityType<? extends Monster> $$0, Level $$1) {
/*  31 */     super($$0, $$1);
/*  32 */     this.xpReward = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/*  37 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/*  42 */     updateSwingTime();
/*  43 */     updateNoActionTime();
/*  44 */     super.aiStep();
/*     */   }
/*     */   
/*     */   protected void updateNoActionTime() {
/*  48 */     float $$0 = getLightLevelDependentMagicValue();
/*  49 */     if ($$0 > 0.5F) {
/*  50 */       this.noActionTime += 2;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDespawnInPeaceful() {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/*  61 */     return SoundEvents.HOSTILE_SWIM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSplashSound() {
/*  66 */     return SoundEvents.HOSTILE_SPLASH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  71 */     return SoundEvents.HOSTILE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  76 */     return SoundEvents.HOSTILE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public LivingEntity.Fallsounds getFallSounds() {
/*  81 */     return new LivingEntity.Fallsounds(SoundEvents.HOSTILE_SMALL_FALL, SoundEvents.HOSTILE_BIG_FALL);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/*  86 */     return -$$1.getPathfindingCostFromLightLevels($$0);
/*     */   }
/*     */   
/*     */   public static boolean isDarkEnoughToSpawn(ServerLevelAccessor $$0, BlockPos $$1, RandomSource $$2) {
/*  90 */     if ($$0.getBrightness(LightLayer.SKY, $$1) > $$2.nextInt(32)) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     DimensionType $$3 = $$0.dimensionType();
/*  95 */     int $$4 = $$3.monsterSpawnBlockLightLimit();
/*  96 */     if ($$4 < 15 && $$0.getBrightness(LightLayer.BLOCK, $$1) > $$4) {
/*  97 */       return false;
/*     */     }
/*     */     
/* 100 */     int $$5 = $$0.getLevel().isThundering() ? $$0.getMaxLocalRawBrightness($$1, 10) : $$0.getMaxLocalRawBrightness($$1);
/* 101 */     return ($$5 <= $$3.monsterSpawnLightTest().sample($$2));
/*     */   }
/*     */   
/*     */   public static boolean checkMonsterSpawnRules(EntityType<? extends Monster> $$0, ServerLevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 105 */     return ($$1.getDifficulty() != Difficulty.PEACEFUL && (
/* 106 */       MobSpawnType.ignoresLightRequirements($$2) || isDarkEnoughToSpawn($$1, $$3, $$4)) && 
/* 107 */       checkMobSpawnRules($$0, (LevelAccessor)$$1, $$2, $$3, $$4));
/*     */   }
/*     */   
/*     */   public static boolean checkAnyLightMonsterSpawnRules(EntityType<? extends Monster> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 111 */     return ($$1.getDifficulty() != Difficulty.PEACEFUL && 
/* 112 */       checkMobSpawnRules($$0, $$1, $$2, $$3, $$4));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createMonsterAttributes() {
/* 116 */     return Mob.createMobAttributes()
/* 117 */       .add(Attributes.ATTACK_DAMAGE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDropExperience() {
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDropLoot() {
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isPreventingPlayerRest(Player $$0) {
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getProjectile(ItemStack $$0) {
/* 136 */     if ($$0.getItem() instanceof ProjectileWeaponItem) {
/* 137 */       Predicate<ItemStack> $$1 = ((ProjectileWeaponItem)$$0.getItem()).getSupportedHeldProjectiles();
/* 138 */       ItemStack $$2 = ProjectileWeaponItem.getHeldProjectile((LivingEntity)this, $$1);
/* 139 */       return $$2.isEmpty() ? new ItemStack((ItemLike)Items.ARROW) : $$2;
/*     */     } 
/* 141 */     return ItemStack.EMPTY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Monster.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */