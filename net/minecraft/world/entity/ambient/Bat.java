/*     */ package net.minecraft.world.entity.ambient;
/*     */ 
/*     */ import java.time.LocalDate;
/*     */ import java.time.temporal.ChronoField;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AnimationState;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Bat
/*     */   extends AmbientCreature {
/*     */   public static final float FLAP_LENGTH_SECONDS = 0.5F;
/*     */   public static final float TICKS_PER_FLAP = 10.0F;
/*  38 */   private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Bat.class, EntityDataSerializers.BYTE);
/*     */   private static final int FLAG_RESTING = 1;
/*  40 */   private static final TargetingConditions BAT_RESTING_TARGETING = TargetingConditions.forNonCombat().range(4.0D);
/*     */   
/*  42 */   public final AnimationState flyAnimationState = new AnimationState();
/*  43 */   public final AnimationState restAnimationState = new AnimationState();
/*     */   
/*     */   @Nullable
/*     */   private BlockPos targetPosition;
/*     */   
/*     */   public Bat(EntityType<? extends Bat> $$0, Level $$1) {
/*  49 */     super((EntityType)$$0, $$1);
/*     */     
/*  51 */     if (!$$1.isClientSide) {
/*  52 */       setResting(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlapping() {
/*  59 */     return (!isResting() && this.tickCount % 10.0F == 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  64 */     super.defineSynchedData();
/*  65 */     this.entityData.define(DATA_ID_FLAGS, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  70 */     return 0.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVoicePitch() {
/*  75 */     return super.getVoicePitch() * 0.95F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SoundEvent getAmbientSound() {
/*  81 */     if (isResting() && this.random.nextInt(4) != 0) {
/*  82 */       return null;
/*     */     }
/*  84 */     return SoundEvents.BAT_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  89 */     return SoundEvents.BAT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  94 */     return SoundEvents.BAT_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPushable() {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPush(Entity $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pushEntities() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 114 */     return Mob.createMobAttributes()
/* 115 */       .add(Attributes.MAX_HEALTH, 6.0D);
/*     */   }
/*     */   
/*     */   public boolean isResting() {
/* 119 */     return ((((Byte)this.entityData.get(DATA_ID_FLAGS)).byteValue() & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setResting(boolean $$0) {
/* 123 */     byte $$1 = ((Byte)this.entityData.get(DATA_ID_FLAGS)).byteValue();
/* 124 */     if ($$0) {
/* 125 */       this.entityData.set(DATA_ID_FLAGS, Byte.valueOf((byte)($$1 | 0x1)));
/*     */     } else {
/* 127 */       this.entityData.set(DATA_ID_FLAGS, Byte.valueOf((byte)($$1 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 133 */     super.tick();
/* 134 */     if (isResting()) {
/* 135 */       setDeltaMovement(Vec3.ZERO);
/* 136 */       setPosRaw(getX(), Mth.floor(getY()) + 1.0D - getBbHeight(), getZ());
/*     */     } else {
/* 138 */       setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
/*     */     } 
/* 140 */     setupAnimationStates();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 145 */     super.customServerAiStep();
/*     */     
/* 147 */     BlockPos $$0 = blockPosition();
/* 148 */     BlockPos $$1 = $$0.above();
/*     */     
/* 150 */     if (isResting()) {
/* 151 */       boolean $$2 = isSilent();
/* 152 */       if (level().getBlockState($$1).isRedstoneConductor((BlockGetter)level(), $$0)) {
/* 153 */         if (this.random.nextInt(200) == 0) {
/* 154 */           this.yHeadRot = this.random.nextInt(360);
/*     */         }
/*     */         
/* 157 */         if (level().getNearestPlayer(BAT_RESTING_TARGETING, (LivingEntity)this) != null) {
/* 158 */           setResting(false);
/* 159 */           if (!$$2) {
/* 160 */             level().levelEvent(null, 1025, $$0, 0);
/*     */           }
/*     */         } 
/*     */       } else {
/* 164 */         setResting(false);
/* 165 */         if (!$$2) {
/* 166 */           level().levelEvent(null, 1025, $$0, 0);
/*     */         }
/*     */       } 
/*     */     } else {
/* 170 */       if (this.targetPosition != null && (!level().isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= level().getMinBuildHeight())) {
/* 171 */         this.targetPosition = null;
/*     */       }
/* 173 */       if (this.targetPosition == null || this.random.nextInt(30) == 0 || this.targetPosition.closerToCenterThan((Position)position(), 2.0D)) {
/* 174 */         this.targetPosition = BlockPos.containing(getX() + this.random.nextInt(7) - this.random.nextInt(7), getY() + this.random.nextInt(6) - 2.0D, getZ() + this.random.nextInt(7) - this.random.nextInt(7));
/*     */       }
/*     */ 
/*     */       
/* 178 */       double $$3 = this.targetPosition.getX() + 0.5D - getX();
/* 179 */       double $$4 = this.targetPosition.getY() + 0.1D - getY();
/* 180 */       double $$5 = this.targetPosition.getZ() + 0.5D - getZ();
/*     */       
/* 182 */       Vec3 $$6 = getDeltaMovement();
/* 183 */       Vec3 $$7 = $$6.add((
/* 184 */           Math.signum($$3) * 0.5D - $$6.x) * 0.10000000149011612D, (
/* 185 */           Math.signum($$4) * 0.699999988079071D - $$6.y) * 0.10000000149011612D, (
/* 186 */           Math.signum($$5) * 0.5D - $$6.z) * 0.10000000149011612D);
/*     */       
/* 188 */       setDeltaMovement($$7);
/*     */       
/* 190 */       float $$8 = (float)(Mth.atan2($$7.z, $$7.x) * 57.2957763671875D) - 90.0F;
/* 191 */       float $$9 = Mth.wrapDegrees($$8 - getYRot());
/* 192 */       this.zza = 0.5F;
/* 193 */       setYRot(getYRot() + $$9);
/*     */       
/* 195 */       if (this.random.nextInt(100) == 0 && level().getBlockState($$1).isRedstoneConductor((BlockGetter)level(), $$1)) {
/* 196 */         setResting(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 203 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnoringBlockTriggers() {
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 219 */     if (isInvulnerableTo($$0)) {
/* 220 */       return false;
/*     */     }
/* 222 */     if (!(level()).isClientSide && 
/* 223 */       isResting()) {
/* 224 */       setResting(false);
/*     */     }
/*     */     
/* 227 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 232 */     super.readAdditionalSaveData($$0);
/* 233 */     this.entityData.set(DATA_ID_FLAGS, Byte.valueOf($$0.getByte("BatFlags")));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 238 */     super.addAdditionalSaveData($$0);
/* 239 */     $$0.putByte("BatFlags", ((Byte)this.entityData.get(DATA_ID_FLAGS)).byteValue());
/*     */   }
/*     */   
/*     */   public static boolean checkBatSpawnRules(EntityType<Bat> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 243 */     if ($$3.getY() >= $$1.getSeaLevel()) {
/* 244 */       return false;
/*     */     }
/*     */     
/* 247 */     int $$5 = $$1.getMaxLocalRawBrightness($$3);
/* 248 */     int $$6 = 4;
/*     */     
/* 250 */     if (isHalloween()) {
/* 251 */       $$6 = 7;
/* 252 */     } else if ($$4.nextBoolean()) {
/* 253 */       return false;
/*     */     } 
/*     */     
/* 256 */     if ($$5 > $$4.nextInt($$6)) {
/* 257 */       return false;
/*     */     }
/* 259 */     return checkMobSpawnRules($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private static boolean isHalloween() {
/* 263 */     LocalDate $$0 = LocalDate.now();
/* 264 */     int $$1 = $$0.get(ChronoField.DAY_OF_MONTH);
/* 265 */     int $$2 = $$0.get(ChronoField.MONTH_OF_YEAR);
/*     */     
/* 267 */     return (($$2 == 10 && $$1 >= 20) || ($$2 == 11 && $$1 <= 3));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 272 */     return $$1.height / 2.0F;
/*     */   }
/*     */   
/*     */   private void setupAnimationStates() {
/* 276 */     if (isResting()) {
/* 277 */       this.flyAnimationState.stop();
/* 278 */       this.restAnimationState.startIfStopped(this.tickCount);
/*     */     } else {
/* 280 */       this.restAnimationState.stop();
/* 281 */       this.flyAnimationState.startIfStopped(this.tickCount);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ambient\Bat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */