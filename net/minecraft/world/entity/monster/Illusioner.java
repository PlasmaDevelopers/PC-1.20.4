/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Illusioner
/*     */   extends SpellcasterIllager
/*     */   implements RangedAttackMob {
/*     */   private static final int NUM_ILLUSIONS = 4;
/*     */   private static final int ILLUSION_TRANSITION_TICKS = 3;
/*     */   
/*     */   public Illusioner(EntityType<? extends Illusioner> $$0, Level $$1) {
/*  53 */     super((EntityType)$$0, $$1);
/*     */     
/*  55 */     this.xpReward = 5;
/*     */     
/*  57 */     this.clientSideIllusionOffsets = new Vec3[2][4];
/*  58 */     for (int $$2 = 0; $$2 < 4; $$2++) {
/*  59 */       this.clientSideIllusionOffsets[0][$$2] = Vec3.ZERO;
/*  60 */       this.clientSideIllusionOffsets[1][$$2] = Vec3.ZERO;
/*     */     } 
/*     */   }
/*     */   private static final int ILLUSION_SPREAD = 3; private int clientSideIllusionTicks; private final Vec3[][] clientSideIllusionOffsets;
/*     */   
/*     */   protected void registerGoals() {
/*  66 */     super.registerGoals();
/*     */     
/*  68 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  69 */     this.goalSelector.addGoal(1, new SpellcasterIllager.SpellcasterCastingSpellGoal(this));
/*  70 */     this.goalSelector.addGoal(4, new IllusionerMirrorSpellGoal());
/*  71 */     this.goalSelector.addGoal(5, new IllusionerBlindnessSpellGoal());
/*  72 */     this.goalSelector.addGoal(6, (Goal)new RangedBowAttackGoal((Monster)this, 0.5D, 20, 15.0F));
/*  73 */     this.goalSelector.addGoal(8, (Goal)new RandomStrollGoal((PathfinderMob)this, 0.6D));
/*  74 */     this.goalSelector.addGoal(9, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0F, 1.0F));
/*  75 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0F));
/*     */     
/*  77 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[] { Raider.class })).setAlertOthers(new Class[0]));
/*  78 */     this.targetSelector.addGoal(2, (Goal)(new NearestAttackableTargetGoal((Mob)this, Player.class, true)).setUnseenMemoryTicks(300));
/*  79 */     this.targetSelector.addGoal(3, (Goal)(new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, false)).setUnseenMemoryTicks(300));
/*  80 */     this.targetSelector.addGoal(3, (Goal)(new NearestAttackableTargetGoal((Mob)this, IronGolem.class, false)).setUnseenMemoryTicks(300));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  84 */     return Monster.createMonsterAttributes()
/*  85 */       .add(Attributes.MOVEMENT_SPEED, 0.5D)
/*  86 */       .add(Attributes.FOLLOW_RANGE, 18.0D)
/*  87 */       .add(Attributes.MAX_HEALTH, 32.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  92 */     setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.BOW));
/*     */     
/*  94 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  99 */     super.defineSynchedData();
/*     */   }
/*     */ 
/*     */   
/*     */   public AABB getBoundingBoxForCulling() {
/* 104 */     return getBoundingBox().inflate(3.0D, 0.0D, 3.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 109 */     super.aiStep();
/*     */     
/* 111 */     if ((level()).isClientSide && isInvisible()) {
/* 112 */       this.clientSideIllusionTicks--;
/* 113 */       if (this.clientSideIllusionTicks < 0) {
/* 114 */         this.clientSideIllusionTicks = 0;
/*     */       }
/*     */       
/* 117 */       if (this.hurtTime == 1 || this.tickCount % 1200 == 0) {
/* 118 */         this.clientSideIllusionTicks = 3;
/*     */         
/* 120 */         float $$0 = -6.0F;
/* 121 */         int $$1 = 13;
/*     */         
/* 123 */         for (int $$2 = 0; $$2 < 4; $$2++) {
/* 124 */           this.clientSideIllusionOffsets[0][$$2] = this.clientSideIllusionOffsets[1][$$2];
/* 125 */           this.clientSideIllusionOffsets[1][$$2] = new Vec3((-6.0F + this.random.nextInt(13)) * 0.5D, Math.max(0, this.random.nextInt(6) - 4), (-6.0F + this.random.nextInt(13)) * 0.5D);
/*     */         } 
/* 127 */         for (int $$3 = 0; $$3 < 16; $$3++) {
/* 128 */           level().addParticle((ParticleOptions)ParticleTypes.CLOUD, getRandomX(0.5D), getRandomY(), getZ(0.5D), 0.0D, 0.0D, 0.0D);
/*     */         }
/*     */         
/* 131 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.ILLUSIONER_MIRROR_MOVE, getSoundSource(), 1.0F, 1.0F, false);
/* 132 */       } else if (this.hurtTime == this.hurtDuration - 1) {
/* 133 */         this.clientSideIllusionTicks = 3;
/* 134 */         for (int $$4 = 0; $$4 < 4; $$4++) {
/* 135 */           this.clientSideIllusionOffsets[0][$$4] = this.clientSideIllusionOffsets[1][$$4];
/* 136 */           this.clientSideIllusionOffsets[1][$$4] = new Vec3(0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getCelebrateSound() {
/* 144 */     return SoundEvents.ILLUSIONER_AMBIENT;
/*     */   }
/*     */   
/*     */   public Vec3[] getIllusionOffsets(float $$0) {
/* 148 */     if (this.clientSideIllusionTicks <= 0) {
/* 149 */       return this.clientSideIllusionOffsets[1];
/*     */     }
/* 151 */     double $$1 = ((this.clientSideIllusionTicks - $$0) / 3.0F);
/* 152 */     $$1 = Math.pow($$1, 0.25D);
/* 153 */     Vec3[] $$2 = new Vec3[4];
/* 154 */     for (int $$3 = 0; $$3 < 4; $$3++) {
/* 155 */       $$2[$$3] = this.clientSideIllusionOffsets[1][$$3].scale(1.0D - $$1).add(this.clientSideIllusionOffsets[0][$$3].scale($$1));
/*     */     }
/* 157 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlliedTo(Entity $$0) {
/* 162 */     if (super.isAlliedTo($$0)) {
/* 163 */       return true;
/*     */     }
/* 165 */     if ($$0 instanceof LivingEntity && ((LivingEntity)$$0).getMobType() == MobType.ILLAGER)
/*     */     {
/* 167 */       return (getTeam() == null && $$0.getTeam() == null);
/*     */     }
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 174 */     return SoundEvents.ILLUSIONER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 179 */     return SoundEvents.ILLUSIONER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 184 */     return SoundEvents.ILLUSIONER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getCastingSoundEvent() {
/* 189 */     return SoundEvents.ILLUSIONER_CAST_SPELL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyRaidBuffs(int $$0, boolean $$1) {}
/*     */ 
/*     */   
/*     */   private class IllusionerMirrorSpellGoal
/*     */     extends SpellcasterIllager.SpellcasterUseSpellGoal
/*     */   {
/*     */     public boolean canUse() {
/* 200 */       if (!super.canUse()) {
/* 201 */         return false;
/*     */       }
/* 203 */       if (Illusioner.this.hasEffect(MobEffects.INVISIBILITY)) {
/* 204 */         return false;
/*     */       }
/* 206 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingTime() {
/* 211 */       return 20;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingInterval() {
/* 216 */       return 340;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void performSpellCasting() {
/* 221 */       Illusioner.this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 1200));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected SoundEvent getSpellPrepareSound() {
/* 227 */       return SoundEvents.ILLUSIONER_PREPARE_MIRROR;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SpellcasterIllager.IllagerSpell getSpell() {
/* 232 */       return SpellcasterIllager.IllagerSpell.DISAPPEAR;
/*     */     }
/*     */   }
/*     */   
/*     */   private class IllusionerBlindnessSpellGoal
/*     */     extends SpellcasterIllager.SpellcasterUseSpellGoal {
/*     */     private int lastTargetId;
/*     */     
/*     */     public boolean canUse() {
/* 241 */       if (!super.canUse()) {
/* 242 */         return false;
/*     */       }
/* 244 */       if (Illusioner.this.getTarget() == null) {
/* 245 */         return false;
/*     */       }
/* 247 */       if (Illusioner.this.getTarget().getId() == this.lastTargetId) {
/* 248 */         return false;
/*     */       }
/* 250 */       if (!Illusioner.this.level().getCurrentDifficultyAt(Illusioner.this.blockPosition()).isHarderThan(Difficulty.NORMAL.ordinal())) {
/* 251 */         return false;
/*     */       }
/* 253 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 258 */       super.start();
/*     */       
/* 260 */       LivingEntity $$0 = Illusioner.this.getTarget();
/* 261 */       if ($$0 != null) {
/* 262 */         this.lastTargetId = $$0.getId();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingTime() {
/* 268 */       return 20;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingInterval() {
/* 273 */       return 180;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void performSpellCasting() {
/* 278 */       Illusioner.this.getTarget().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400), (Entity)Illusioner.this);
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent getSpellPrepareSound() {
/* 283 */       return SoundEvents.ILLUSIONER_PREPARE_BLINDNESS;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SpellcasterIllager.IllagerSpell getSpell() {
/* 288 */       return SpellcasterIllager.IllagerSpell.BLINDNESS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 294 */     ItemStack $$2 = getProjectile(getItemInHand(ProjectileUtil.getWeaponHoldingHand((LivingEntity)this, Items.BOW)));
/* 295 */     AbstractArrow $$3 = ProjectileUtil.getMobArrow((LivingEntity)this, $$2, $$1);
/*     */     
/* 297 */     double $$4 = $$0.getX() - getX();
/* 298 */     double $$5 = $$0.getY(0.3333333333333333D) - $$3.getY();
/* 299 */     double $$6 = $$0.getZ() - getZ();
/* 300 */     double $$7 = Math.sqrt($$4 * $$4 + $$6 * $$6);
/* 301 */     $$3.shoot($$4, $$5 + $$7 * 0.20000000298023224D, $$6, 1.6F, (14 - level().getDifficulty().getId() * 4));
/* 302 */     playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
/* 303 */     level().addFreshEntity((Entity)$$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractIllager.IllagerArmPose getArmPose() {
/* 308 */     if (isCastingSpell())
/* 309 */       return AbstractIllager.IllagerArmPose.SPELLCASTING; 
/* 310 */     if (isAggressive()) {
/* 311 */       return AbstractIllager.IllagerArmPose.BOW_AND_ARROW;
/*     */     }
/* 313 */     return AbstractIllager.IllagerArmPose.CROSSED;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Illusioner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */