/*     */ package net.minecraft.world.entity.monster.piglin;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.util.GoalUtils;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.ZombifiedPiglin;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public abstract class AbstractPiglin
/*     */   extends Monster
/*     */ {
/*  30 */   protected static final EntityDataAccessor<Boolean> DATA_IMMUNE_TO_ZOMBIFICATION = SynchedEntityData.defineId(AbstractPiglin.class, EntityDataSerializers.BOOLEAN);
/*     */   protected static final int CONVERSION_TIME = 300;
/*     */   protected static final float PIGLIN_EYE_HEIGHT = 1.79F;
/*     */   protected int timeInOverworld;
/*     */   
/*     */   public AbstractPiglin(EntityType<? extends AbstractPiglin> $$0, Level $$1) {
/*  36 */     super($$0, $$1);
/*  37 */     setCanPickUpLoot(true);
/*  38 */     applyOpenDoorsAbility();
/*  39 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
/*  40 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
/*     */   }
/*     */   
/*     */   private void applyOpenDoorsAbility() {
/*  44 */     if (GoalUtils.hasGroundPathNavigation((Mob)this)) {
/*  45 */       ((GroundPathNavigation)getNavigation()).setCanOpenDoors(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  51 */     return 1.79F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/*  56 */     return -0.7F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/*  61 */     return new Vector3f(0.0F, $$1.height + 0.0625F * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   protected abstract boolean canHunt();
/*     */   
/*     */   public void setImmuneToZombification(boolean $$0) {
/*  67 */     getEntityData().set(DATA_IMMUNE_TO_ZOMBIFICATION, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   protected boolean isImmuneToZombification() {
/*  71 */     return ((Boolean)getEntityData().get(DATA_IMMUNE_TO_ZOMBIFICATION)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  76 */     super.defineSynchedData();
/*  77 */     this.entityData.define(DATA_IMMUNE_TO_ZOMBIFICATION, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  82 */     super.addAdditionalSaveData($$0);
/*     */     
/*  84 */     if (isImmuneToZombification()) {
/*  85 */       $$0.putBoolean("IsImmuneToZombification", true);
/*     */     }
/*  87 */     $$0.putInt("TimeInOverworld", this.timeInOverworld);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  92 */     super.readAdditionalSaveData($$0);
/*     */     
/*  94 */     setImmuneToZombification($$0.getBoolean("IsImmuneToZombification"));
/*  95 */     this.timeInOverworld = $$0.getInt("TimeInOverworld");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 100 */     super.customServerAiStep();
/*     */     
/* 102 */     if (isConverting()) {
/* 103 */       this.timeInOverworld++;
/*     */     } else {
/* 105 */       this.timeInOverworld = 0;
/*     */     } 
/* 107 */     if (this.timeInOverworld > 300) {
/* 108 */       playConvertedSound();
/* 109 */       finishConversion((ServerLevel)level());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isConverting() {
/* 114 */     return (!level().dimensionType().piglinSafe() && !isImmuneToZombification() && !isNoAi());
/*     */   }
/*     */   
/*     */   protected void finishConversion(ServerLevel $$0) {
/* 118 */     ZombifiedPiglin $$1 = (ZombifiedPiglin)convertTo(EntityType.ZOMBIFIED_PIGLIN, true);
/* 119 */     if ($$1 != null) {
/* 120 */       $$1.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isAdult() {
/* 125 */     return !isBaby();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract PiglinArmPose getArmPose();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getTarget() {
/* 134 */     return this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
/*     */   }
/*     */   
/*     */   protected boolean isHoldingMeleeWeapon() {
/* 138 */     return getMainHandItem().getItem() instanceof net.minecraft.world.item.TieredItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playAmbientSound() {
/* 143 */     if (PiglinAi.isIdle(this)) {
/* 144 */       super.playAmbientSound();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 150 */     super.sendDebugPackets();
/* 151 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */   
/*     */   protected abstract void playConvertedSound();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\AbstractPiglin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */