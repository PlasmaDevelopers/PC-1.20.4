/*     */ package net.minecraft.world.entity.animal;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.Shearable;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Snowball;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class SnowGolem extends AbstractGolem implements Shearable, RangedAttackMob {
/*  46 */   private static final EntityDataAccessor<Byte> DATA_PUMPKIN_ID = SynchedEntityData.defineId(SnowGolem.class, EntityDataSerializers.BYTE);
/*     */   
/*     */   private static final byte PUMPKIN_FLAG = 16;
/*     */   private static final float EYE_HEIGHT = 1.7F;
/*     */   
/*     */   public SnowGolem(EntityType<? extends SnowGolem> $$0, Level $$1) {
/*  52 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  57 */     this.goalSelector.addGoal(1, (Goal)new RangedAttackGoal(this, 1.25D, 20, 10.0F));
/*  58 */     this.goalSelector.addGoal(2, (Goal)new WaterAvoidingRandomStrollGoal(this, 1.0D, 1.0000001E-5F));
/*  59 */     this.goalSelector.addGoal(3, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  60 */     this.goalSelector.addGoal(4, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  62 */     this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Mob.class, 10, true, false, $$0 -> $$0 instanceof net.minecraft.world.entity.monster.Enemy));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  66 */     return Mob.createMobAttributes()
/*  67 */       .add(Attributes.MAX_HEALTH, 4.0D)
/*  68 */       .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  73 */     super.defineSynchedData();
/*  74 */     this.entityData.define(DATA_PUMPKIN_ID, Byte.valueOf((byte)16));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  79 */     super.addAdditionalSaveData($$0);
/*     */     
/*  81 */     $$0.putBoolean("Pumpkin", hasPumpkin());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  86 */     super.readAdditionalSaveData($$0);
/*     */     
/*  88 */     if ($$0.contains("Pumpkin")) {
/*  89 */       setPumpkin($$0.getBoolean("Pumpkin"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSensitiveToWater() {
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 100 */     super.aiStep();
/*     */     
/* 102 */     if (!(level()).isClientSide) {
/* 103 */       if (level().getBiome(blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS)) {
/* 104 */         hurt(damageSources().onFire(), 1.0F);
/*     */       }
/*     */       
/* 107 */       if (!level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 112 */       BlockState $$0 = Blocks.SNOW.defaultBlockState();
/* 113 */       for (int $$1 = 0; $$1 < 4; $$1++) {
/* 114 */         int $$2 = Mth.floor(getX() + (($$1 % 2 * 2 - 1) * 0.25F));
/* 115 */         int $$3 = Mth.floor(getY());
/* 116 */         int $$4 = Mth.floor(getZ() + (($$1 / 2 % 2 * 2 - 1) * 0.25F));
/* 117 */         BlockPos $$5 = new BlockPos($$2, $$3, $$4);
/* 118 */         if (level().getBlockState($$5).isAir() && $$0.canSurvive((LevelReader)level(), $$5)) {
/* 119 */           level().setBlockAndUpdate($$5, $$0);
/* 120 */           level().gameEvent(GameEvent.BLOCK_PLACE, $$5, GameEvent.Context.of((Entity)this, $$0));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 128 */     Snowball $$2 = new Snowball(level(), (LivingEntity)this);
/* 129 */     double $$3 = $$0.getEyeY() - 1.100000023841858D;
/* 130 */     double $$4 = $$0.getX() - getX();
/* 131 */     double $$5 = $$3 - $$2.getY();
/* 132 */     double $$6 = $$0.getZ() - getZ();
/* 133 */     double $$7 = Math.sqrt($$4 * $$4 + $$6 * $$6) * 0.20000000298023224D;
/* 134 */     $$2.shoot($$4, $$5 + $$7, $$6, 1.6F, 12.0F);
/*     */     
/* 136 */     playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (getRandom().nextFloat() * 0.4F + 0.8F));
/* 137 */     level().addFreshEntity((Entity)$$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 142 */     return 1.7F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 147 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 148 */     if ($$2.is(Items.SHEARS) && readyForShearing()) {
/* 149 */       shear(SoundSource.PLAYERS);
/* 150 */       gameEvent(GameEvent.SHEAR, (Entity)$$0);
/* 151 */       if (!(level()).isClientSide) {
/* 152 */         $$2.hurtAndBreak(1, (LivingEntity)$$0, $$1 -> $$1.broadcastBreakEvent($$0));
/*     */       }
/* 154 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/* 156 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void shear(SoundSource $$0) {
/* 161 */     level().playSound(null, (Entity)this, SoundEvents.SNOW_GOLEM_SHEAR, $$0, 1.0F, 1.0F);
/*     */     
/* 163 */     if (!level().isClientSide()) {
/* 164 */       setPumpkin(false);
/* 165 */       spawnAtLocation(new ItemStack((ItemLike)Items.CARVED_PUMPKIN), 1.7F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readyForShearing() {
/* 171 */     return (isAlive() && hasPumpkin());
/*     */   }
/*     */   
/*     */   public boolean hasPumpkin() {
/* 175 */     return ((((Byte)this.entityData.get(DATA_PUMPKIN_ID)).byteValue() & 0x10) != 0);
/*     */   }
/*     */   
/*     */   public void setPumpkin(boolean $$0) {
/* 179 */     byte $$1 = ((Byte)this.entityData.get(DATA_PUMPKIN_ID)).byteValue();
/* 180 */     if ($$0) {
/* 181 */       this.entityData.set(DATA_PUMPKIN_ID, Byte.valueOf((byte)($$1 | 0x10)));
/*     */     } else {
/* 183 */       this.entityData.set(DATA_PUMPKIN_ID, Byte.valueOf((byte)($$1 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 190 */     return SoundEvents.SNOW_GOLEM_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 196 */     return SoundEvents.SNOW_GOLEM_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 202 */     return SoundEvents.SNOW_GOLEM_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 207 */     return new Vec3(0.0D, (0.75F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\SnowGolem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */