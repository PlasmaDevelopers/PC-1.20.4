/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.players.OldUsersConverter;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.EntityGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ 
/*     */ public abstract class TamableAnimal extends Animal implements OwnableEntity {
/*  25 */   protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(TamableAnimal.class, EntityDataSerializers.BYTE);
/*  26 */   protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(TamableAnimal.class, EntityDataSerializers.OPTIONAL_UUID);
/*     */   
/*     */   private boolean orderedToSit;
/*     */   
/*     */   protected TamableAnimal(EntityType<? extends TamableAnimal> $$0, Level $$1) {
/*  31 */     super($$0, $$1);
/*  32 */     reassessTameGoals();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  37 */     super.defineSynchedData();
/*  38 */     this.entityData.define(DATA_FLAGS_ID, Byte.valueOf((byte)0));
/*  39 */     this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  44 */     super.addAdditionalSaveData($$0);
/*  45 */     if (getOwnerUUID() != null) {
/*  46 */       $$0.putUUID("Owner", getOwnerUUID());
/*     */     }
/*  48 */     $$0.putBoolean("Sitting", this.orderedToSit);
/*     */   }
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*     */     UUID $$3;
/*  53 */     super.readAdditionalSaveData($$0);
/*     */     
/*  55 */     if ($$0.hasUUID("Owner")) {
/*  56 */       UUID $$1 = $$0.getUUID("Owner");
/*     */     } else {
/*  58 */       String $$2 = $$0.getString("Owner");
/*  59 */       $$3 = OldUsersConverter.convertMobOwnerIfNecessary(getServer(), $$2);
/*     */     } 
/*  61 */     if ($$3 != null) {
/*     */       try {
/*  63 */         setOwnerUUID($$3);
/*  64 */         setTame(true);
/*  65 */       } catch (Throwable $$4) {
/*  66 */         setTame(false);
/*     */       } 
/*     */     }
/*  69 */     this.orderedToSit = $$0.getBoolean("Sitting");
/*  70 */     setInSittingPose(this.orderedToSit);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/*  75 */     return !isLeashed();
/*     */   }
/*     */   
/*     */   protected void spawnTamingParticles(boolean $$0) {
/*  79 */     SimpleParticleType simpleParticleType = ParticleTypes.HEART;
/*  80 */     if (!$$0) {
/*  81 */       simpleParticleType = ParticleTypes.SMOKE;
/*     */     }
/*  83 */     for (int $$2 = 0; $$2 < 7; $$2++) {
/*  84 */       double $$3 = this.random.nextGaussian() * 0.02D;
/*  85 */       double $$4 = this.random.nextGaussian() * 0.02D;
/*  86 */       double $$5 = this.random.nextGaussian() * 0.02D;
/*  87 */       level().addParticle((ParticleOptions)simpleParticleType, getRandomX(1.0D), getRandomY() + 0.5D, getRandomZ(1.0D), $$3, $$4, $$5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/*  93 */     if ($$0 == 7) {
/*  94 */       spawnTamingParticles(true);
/*  95 */     } else if ($$0 == 6) {
/*  96 */       spawnTamingParticles(false);
/*     */     } else {
/*  98 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isTame() {
/* 103 */     return ((((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public void setTame(boolean $$0) {
/* 107 */     byte $$1 = ((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue();
/* 108 */     if ($$0) {
/* 109 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)($$1 | 0x4)));
/*     */     } else {
/* 111 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)($$1 & 0xFFFFFFFB)));
/*     */     } 
/*     */     
/* 114 */     reassessTameGoals();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void reassessTameGoals() {}
/*     */   
/*     */   public boolean isInSittingPose() {
/* 121 */     return ((((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue() & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setInSittingPose(boolean $$0) {
/* 125 */     byte $$1 = ((Byte)this.entityData.get(DATA_FLAGS_ID)).byteValue();
/* 126 */     if ($$0) {
/* 127 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)($$1 | 0x1)));
/*     */     } else {
/* 129 */       this.entityData.set(DATA_FLAGS_ID, Byte.valueOf((byte)($$1 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getOwnerUUID() {
/* 136 */     return ((Optional<UUID>)this.entityData.get(DATA_OWNERUUID_ID)).orElse(null);
/*     */   }
/*     */   
/*     */   public void setOwnerUUID(@Nullable UUID $$0) {
/* 140 */     this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable($$0));
/*     */   }
/*     */   
/*     */   public void tame(Player $$0) {
/* 144 */     setTame(true);
/* 145 */     setOwnerUUID($$0.getUUID());
/* 146 */     if ($$0 instanceof ServerPlayer) {
/* 147 */       CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)$$0, this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAttack(LivingEntity $$0) {
/* 153 */     if (isOwnedBy($$0)) {
/* 154 */       return false;
/*     */     }
/* 156 */     return super.canAttack($$0);
/*     */   }
/*     */   
/*     */   public boolean isOwnedBy(LivingEntity $$0) {
/* 160 */     return ($$0 == getOwner());
/*     */   }
/*     */   
/*     */   public boolean wantsToAttack(LivingEntity $$0, LivingEntity $$1) {
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerTeam getTeam() {
/* 169 */     if (isTame()) {
/* 170 */       LivingEntity $$0 = getOwner();
/* 171 */       if ($$0 != null) {
/* 172 */         return $$0.getTeam();
/*     */       }
/*     */     } 
/* 175 */     return super.getTeam();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlliedTo(Entity $$0) {
/* 180 */     if (isTame()) {
/* 181 */       LivingEntity $$1 = getOwner();
/* 182 */       if ($$0 == $$1) {
/* 183 */         return true;
/*     */       }
/* 185 */       if ($$1 != null) {
/* 186 */         return $$1.isAlliedTo($$0);
/*     */       }
/*     */     } 
/* 189 */     return super.isAlliedTo($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void die(DamageSource $$0) {
/* 194 */     if (!(level()).isClientSide && level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && 
/* 195 */       getOwner() instanceof ServerPlayer) {
/* 196 */       getOwner().sendSystemMessage(getCombatTracker().getDeathMessage());
/*     */     }
/*     */     
/* 199 */     super.die($$0);
/*     */   }
/*     */   
/*     */   public boolean isOrderedToSit() {
/* 203 */     return this.orderedToSit;
/*     */   }
/*     */   
/*     */   public void setOrderedToSit(boolean $$0) {
/* 207 */     this.orderedToSit = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\TamableAnimal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */