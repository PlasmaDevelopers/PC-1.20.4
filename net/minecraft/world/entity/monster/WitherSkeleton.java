/*     */ package net.minecraft.world.entity.monster;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ 
/*     */ public class WitherSkeleton extends AbstractSkeleton {
/*     */   public WitherSkeleton(EntityType<? extends WitherSkeleton> $$0, Level $$1) {
/*  34 */     super((EntityType)$$0, $$1);
/*     */     
/*  36 */     setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  41 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractPiglin.class, true));
/*  42 */     super.registerGoals();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  47 */     return SoundEvents.WITHER_SKELETON_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  52 */     return SoundEvents.WITHER_SKELETON_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  57 */     return SoundEvents.WITHER_SKELETON_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   SoundEvent getStepSound() {
/*  62 */     return SoundEvents.WITHER_SKELETON_STEP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/*  67 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/*  68 */     Entity $$3 = $$0.getEntity();
/*  69 */     if ($$3 instanceof Creeper) { Creeper $$4 = (Creeper)$$3;
/*  70 */       if ($$4.canDropMobsSkull()) {
/*  71 */         $$4.increaseDroppedSkulls();
/*  72 */         spawnAtLocation((ItemLike)Items.WITHER_SKELETON_SKULL);
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/*  79 */     setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.STONE_SWORD));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentEnchantments(RandomSource $$0, DifficultyInstance $$1) {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  89 */     SpawnGroupData $$5 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  91 */     getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
/*     */     
/*  93 */     reassessWeaponGoal();
/*     */     
/*  95 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 100 */     return 2.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 105 */     return -0.875F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 110 */     if (!super.doHurtTarget($$0)) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     if ($$0 instanceof LivingEntity) {
/* 115 */       ((LivingEntity)$$0).addEffect(new MobEffectInstance(MobEffects.WITHER, 200), (Entity)this);
/*     */     }
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractArrow getArrow(ItemStack $$0, float $$1) {
/* 122 */     AbstractArrow $$2 = super.getArrow($$0, $$1);
/* 123 */     $$2.setSecondsOnFire(100);
/* 124 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeAffected(MobEffectInstance $$0) {
/* 129 */     if ($$0.getEffect() == MobEffects.WITHER) {
/* 130 */       return false;
/*     */     }
/* 132 */     return super.canBeAffected($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\WitherSkeleton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */