/*     */ package net.minecraft.world.entity.animal;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class Pufferfish extends AbstractFish {
/*  32 */   private static final EntityDataAccessor<Integer> PUFF_STATE = SynchedEntityData.defineId(Pufferfish.class, EntityDataSerializers.INT);
/*     */   int inflateCounter;
/*     */   
/*     */   static {
/*  36 */     SCARY_MOB = ($$0 -> 
/*  37 */       ($$0 instanceof Player && ((Player)$$0).isCreative()) ? false : (
/*     */ 
/*     */ 
/*     */       
/*  41 */       ($$0.getType() == EntityType.AXOLOTL || $$0.getMobType() != MobType.WATER)));
/*     */   }
/*  43 */   int deflateTimer; private static final Predicate<LivingEntity> SCARY_MOB; static final TargetingConditions targetingConditions = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().selector(SCARY_MOB);
/*     */   
/*     */   public static final int STATE_SMALL = 0;
/*     */   public static final int STATE_MID = 1;
/*     */   public static final int STATE_FULL = 2;
/*     */   
/*     */   public Pufferfish(EntityType<? extends Pufferfish> $$0, Level $$1) {
/*  50 */     super((EntityType)$$0, $$1);
/*     */     
/*  52 */     refreshDimensions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  57 */     super.defineSynchedData();
/*     */     
/*  59 */     this.entityData.define(PUFF_STATE, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public int getPuffState() {
/*  63 */     return ((Integer)this.entityData.get(PUFF_STATE)).intValue();
/*     */   }
/*     */   
/*     */   public void setPuffState(int $$0) {
/*  67 */     this.entityData.set(PUFF_STATE, Integer.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  72 */     if (PUFF_STATE.equals($$0)) {
/*  73 */       refreshDimensions();
/*     */     }
/*     */     
/*  76 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  81 */     super.addAdditionalSaveData($$0);
/*     */     
/*  83 */     $$0.putInt("PuffState", getPuffState());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  88 */     super.readAdditionalSaveData($$0);
/*     */     
/*  90 */     setPuffState(Math.min($$0.getInt("PuffState"), 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBucketItemStack() {
/*  95 */     return new ItemStack((ItemLike)Items.PUFFERFISH_BUCKET);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 100 */     super.registerGoals();
/*     */     
/* 102 */     this.goalSelector.addGoal(1, new PufferfishPuffGoal(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 107 */     if (!(level()).isClientSide && isAlive() && isEffectiveAi()) {
/* 108 */       if (this.inflateCounter > 0) {
/*     */         
/* 110 */         if (getPuffState() == 0) {
/* 111 */           playSound(SoundEvents.PUFFER_FISH_BLOW_UP, getSoundVolume(), getVoicePitch());
/* 112 */           setPuffState(1);
/*     */         }
/* 114 */         else if (this.inflateCounter > 40 && getPuffState() == 1) {
/* 115 */           playSound(SoundEvents.PUFFER_FISH_BLOW_UP, getSoundVolume(), getVoicePitch());
/* 116 */           setPuffState(2);
/*     */         } 
/*     */ 
/*     */         
/* 120 */         this.inflateCounter++;
/* 121 */       } else if (getPuffState() != 0) {
/*     */         
/* 123 */         if (this.deflateTimer > 60 && getPuffState() == 2) {
/* 124 */           playSound(SoundEvents.PUFFER_FISH_BLOW_OUT, getSoundVolume(), getVoicePitch());
/* 125 */           setPuffState(1);
/* 126 */         } else if (this.deflateTimer > 100 && getPuffState() == 1) {
/* 127 */           playSound(SoundEvents.PUFFER_FISH_BLOW_OUT, getSoundVolume(), getVoicePitch());
/* 128 */           setPuffState(0);
/*     */         } 
/*     */         
/* 131 */         this.deflateTimer++;
/*     */       } 
/*     */     }
/* 134 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 139 */     super.aiStep();
/*     */     
/* 141 */     if (isAlive() && getPuffState() > 0) {
/* 142 */       List<Mob> $$0 = level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(0.3D), $$0 -> targetingConditions.test((LivingEntity)this, (LivingEntity)$$0));
/* 143 */       for (Mob $$1 : $$0) {
/* 144 */         if ($$1.isAlive()) {
/* 145 */           touch($$1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void touch(Mob $$0) {
/* 152 */     int $$1 = getPuffState();
/* 153 */     if ($$0.hurt(damageSources().mobAttack((LivingEntity)this), (1 + $$1))) {
/* 154 */       $$0.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * $$1, 0), (Entity)this);
/* 155 */       playSound(SoundEvents.PUFFER_FISH_STING, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerTouch(Player $$0) {
/* 161 */     int $$1 = getPuffState();
/* 162 */     if ($$0 instanceof ServerPlayer && $$1 > 0 && 
/* 163 */       $$0.hurt(damageSources().mobAttack((LivingEntity)this), (1 + $$1))) {
/* 164 */       if (!isSilent()) {
/* 165 */         ((ServerPlayer)$$0).connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.PUFFER_FISH_STING, 0.0F));
/*     */       }
/* 167 */       $$0.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * $$1, 0), (Entity)this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 174 */     return SoundEvents.PUFFER_FISH_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 179 */     return SoundEvents.PUFFER_FISH_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 184 */     return SoundEvents.PUFFER_FISH_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getFlopSound() {
/* 189 */     return SoundEvents.PUFFER_FISH_FLOP;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 194 */     return super.getDimensions($$0).scale(getScale(getPuffState()));
/*     */   }
/*     */   
/*     */   private static float getScale(int $$0) {
/* 198 */     switch ($$0) {
/*     */       case 1:
/* 200 */         return 0.7F;
/*     */       case 0:
/* 202 */         return 0.5F;
/*     */     } 
/* 204 */     return 1.0F;
/*     */   }
/*     */   
/*     */   private static class PufferfishPuffGoal
/*     */     extends Goal {
/*     */     private final Pufferfish fish;
/*     */     
/*     */     public PufferfishPuffGoal(Pufferfish $$0) {
/* 212 */       this.fish = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 217 */       List<LivingEntity> $$0 = this.fish.level().getEntitiesOfClass(LivingEntity.class, this.fish.getBoundingBox().inflate(2.0D), $$0 -> Pufferfish.targetingConditions.test((LivingEntity)this.fish, $$0));
/*     */       
/* 219 */       return !$$0.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 224 */       this.fish.inflateCounter = 1;
/* 225 */       this.fish.deflateTimer = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 230 */       this.fish.inflateCounter = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Pufferfish.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */