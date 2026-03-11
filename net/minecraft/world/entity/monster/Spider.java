/*     */ package net.minecraft.world.entity.monster;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Spider extends Monster {
/*  49 */   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Spider.class, EntityDataSerializers.BYTE); private static final float SPIDER_SPECIAL_EFFECT_CHANCE = 0.1F;
/*     */   
/*     */   public Spider(EntityType<? extends Spider> $$0, Level $$1) {
/*  52 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  57 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/*     */     
/*  59 */     this.goalSelector.addGoal(3, (Goal)new LeapAtTargetGoal((Mob)this, 0.4F));
/*  60 */     this.goalSelector.addGoal(4, (Goal)new SpiderAttackGoal(this));
/*     */     
/*  62 */     this.goalSelector.addGoal(5, (Goal)new WaterAvoidingRandomStrollGoal(this, 0.8D));
/*  63 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/*  64 */     this.goalSelector.addGoal(6, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  66 */     this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal(this, new Class[0]));
/*  67 */     this.targetSelector.addGoal(2, (Goal)new SpiderTargetGoal<>(this, Player.class));
/*  68 */     this.targetSelector.addGoal(3, (Goal)new SpiderTargetGoal<>(this, IronGolem.class));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/*  73 */     return new Vector3f(0.0F, $$1.height * 0.85F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/*  78 */     return (PathNavigation)new WallClimberNavigation((Mob)this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  83 */     super.defineSynchedData();
/*     */     
/*  85 */     this.entityData.define(DATA_FLAGS_ID, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  90 */     super.tick();
/*     */     
/*  92 */     if (!(level()).isClientSide)
/*     */     {
/*     */       
/*  95 */       setClimbing(this.horizontalCollision);
/*     */     }
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 100 */     return Monster.createMonsterAttributes()
/* 101 */       .add(Attributes.MAX_HEALTH, 16.0D)
/* 102 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 107 */     return SoundEvents.SPIDER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 112 */     return SoundEvents.SPIDER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 117 */     return SoundEvents.SPIDER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 122 */     playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onClimbable() {
/* 131 */     return isClimbing();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeStuckInBlock(BlockState $$0, Vec3 $$1) {
/* 137 */     if (!$$0.is(Blocks.COBWEB)) {
/* 138 */       super.makeStuckInBlock($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 144 */     return MobType.ARTHROPOD;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeAffected(MobEffectInstance $$0) {
/* 149 */     if ($$0.getEffect() == MobEffects.POISON) {
/* 150 */       return false;
/*     */     }
/* 152 */     return super.canBeAffected($$0);
/*     */   }
/*     */   
/*     */   public boolean isClimbing() {
/* 156 */     return ((((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setClimbing(boolean $$0) {
/* 160 */     byte $$1 = ((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue();
/* 161 */     if ($$0) {
/* 162 */       $$1 = (byte)($$1 | 0x1);
/*     */     } else {
/* 164 */       $$1 = (byte)($$1 & 0xFFFFFFFE);
/*     */     } 
/* 166 */     this.entityData.set(DATA_FLAGS_ID, Byte.valueOf($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 172 */     $$3 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 174 */     RandomSource $$5 = $$0.getRandom();
/* 175 */     if ($$5.nextInt(100) == 0) {
/* 176 */       Skeleton $$6 = (Skeleton)EntityType.SKELETON.create(level());
/* 177 */       if ($$6 != null) {
/* 178 */         $$6.moveTo(getX(), getY(), getZ(), getYRot(), 0.0F);
/* 179 */         $$6.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)null, (CompoundTag)null);
/* 180 */         $$6.startRiding((Entity)this);
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     if ($$3 == null) {
/* 185 */       $$3 = new SpiderEffectsGroupData();
/*     */       
/* 187 */       if ($$0.getDifficulty() == Difficulty.HARD && $$5.nextFloat() < 0.1F * $$1.getSpecialMultiplier()) {
/* 188 */         ((SpiderEffectsGroupData)$$3).setRandomEffect($$5);
/*     */       }
/*     */     } 
/* 191 */     if ($$3 instanceof SpiderEffectsGroupData) { SpiderEffectsGroupData $$7 = (SpiderEffectsGroupData)$$3;
/* 192 */       MobEffect $$8 = $$7.effect;
/* 193 */       if ($$8 != null) {
/* 194 */         addEffect(new MobEffectInstance($$8, -1));
/*     */       } }
/*     */ 
/*     */     
/* 198 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 203 */     return 0.65F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 208 */     return ($$0.getBbWidth() <= getBbWidth()) ? -0.3125F : 0.0F;
/*     */   }
/*     */   
/*     */   public static class SpiderEffectsGroupData
/*     */     implements SpawnGroupData
/*     */   {
/*     */     @Nullable
/*     */     public MobEffect effect;
/*     */     
/*     */     public void setRandomEffect(RandomSource $$0) {
/* 218 */       int $$1 = $$0.nextInt(5);
/* 219 */       if ($$1 <= 1) {
/* 220 */         this.effect = MobEffects.MOVEMENT_SPEED;
/* 221 */       } else if ($$1 <= 2) {
/* 222 */         this.effect = MobEffects.DAMAGE_BOOST;
/* 223 */       } else if ($$1 <= 3) {
/* 224 */         this.effect = MobEffects.REGENERATION;
/* 225 */       } else if ($$1 <= 4) {
/* 226 */         this.effect = MobEffects.INVISIBILITY;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SpiderAttackGoal extends MeleeAttackGoal {
/*     */     public SpiderAttackGoal(Spider $$0) {
/* 233 */       super($$0, 1.0D, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 238 */       return (super.canUse() && !this.mob.isVehicle());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 243 */       float $$0 = this.mob.getLightLevelDependentMagicValue();
/* 244 */       if ($$0 >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
/* 245 */         this.mob.setTarget(null);
/* 246 */         return false;
/*     */       } 
/* 248 */       return super.canContinueToUse();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SpiderTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
/*     */     public SpiderTargetGoal(Spider $$0, Class<T> $$1) {
/* 254 */       super((Mob)$$0, $$1, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 259 */       float $$0 = this.mob.getLightLevelDependentMagicValue();
/* 260 */       if ($$0 >= 0.5F) {
/* 261 */         return false;
/*     */       }
/*     */       
/* 264 */       return super.canUse();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Spider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */