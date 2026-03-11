/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface NeutralMob
/*     */ {
/*     */   public static final String TAG_ANGER_TIME = "AngerTime";
/*     */   public static final String TAG_ANGRY_AT = "AngryAt";
/*     */   
/*     */   int getRemainingPersistentAngerTime();
/*     */   
/*     */   void setRemainingPersistentAngerTime(int paramInt);
/*     */   
/*     */   @Nullable
/*     */   UUID getPersistentAngerTarget();
/*     */   
/*     */   void setPersistentAngerTarget(@Nullable UUID paramUUID);
/*     */   
/*     */   void startPersistentAngerTimer();
/*     */   
/*     */   default void addPersistentAngerSaveData(CompoundTag $$0) {
/*  57 */     $$0.putInt("AngerTime", getRemainingPersistentAngerTime());
/*  58 */     if (getPersistentAngerTarget() != null) {
/*  59 */       $$0.putUUID("AngryAt", getPersistentAngerTarget());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void readPersistentAngerSaveData(Level $$0, CompoundTag $$1) {
/*  67 */     setRemainingPersistentAngerTime($$1.getInt("AngerTime"));
/*     */     
/*  69 */     if (!($$0 instanceof ServerLevel)) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     if (!$$1.hasUUID("AngryAt")) {
/*  74 */       setPersistentAngerTarget(null);
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     UUID $$2 = $$1.getUUID("AngryAt");
/*  79 */     setPersistentAngerTarget($$2);
/*     */     
/*  81 */     Entity $$3 = ((ServerLevel)$$0).getEntity($$2);
/*  82 */     if ($$3 == null) {
/*     */       return;
/*     */     }
/*  85 */     if ($$3 instanceof Mob) {
/*  86 */       setLastHurtByMob((Mob)$$3);
/*     */     }
/*  88 */     if ($$3.getType() == EntityType.PLAYER) {
/*  89 */       setLastHurtByPlayer((Player)$$3);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void updatePersistentAnger(ServerLevel $$0, boolean $$1) {
/*  97 */     LivingEntity $$2 = getTarget();
/*     */     
/*  99 */     UUID $$3 = getPersistentAngerTarget();
/* 100 */     if (($$2 == null || $$2.isDeadOrDying()) && $$3 != null && $$0.getEntity($$3) instanceof Mob) {
/*     */ 
/*     */ 
/*     */       
/* 104 */       stopBeingAngry();
/*     */       
/*     */       return;
/*     */     } 
/* 108 */     if ($$2 != null && !Objects.equals($$3, $$2.getUUID())) {
/*     */       
/* 110 */       setPersistentAngerTarget($$2.getUUID());
/* 111 */       startPersistentAngerTimer();
/*     */     } 
/*     */ 
/*     */     
/* 115 */     if (getRemainingPersistentAngerTime() > 0 && (
/* 116 */       $$2 == null || $$2.getType() != EntityType.PLAYER || !$$1)) {
/* 117 */       setRemainingPersistentAngerTime(getRemainingPersistentAngerTime() - 1);
/* 118 */       if (getRemainingPersistentAngerTime() == 0) {
/* 119 */         stopBeingAngry();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isAngryAt(LivingEntity $$0) {
/* 129 */     if (!canAttack($$0)) {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     if ($$0.getType() == EntityType.PLAYER && isAngryAtAllPlayers($$0.level())) {
/* 134 */       return true;
/*     */     }
/*     */     
/* 137 */     return $$0.getUUID().equals(getPersistentAngerTarget());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isAngryAtAllPlayers(Level $$0) {
/* 143 */     return ($$0.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER) && isAngry() && getPersistentAngerTarget() == null);
/*     */   }
/*     */   
/*     */   default boolean isAngry() {
/* 147 */     return (getRemainingPersistentAngerTime() > 0);
/*     */   }
/*     */   
/*     */   default void playerDied(Player $$0) {
/* 151 */     if (!$$0.level().getGameRules().getBoolean(GameRules.RULE_FORGIVE_DEAD_PLAYERS)) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     if (!$$0.getUUID().equals(getPersistentAngerTarget())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 160 */     stopBeingAngry();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void forgetCurrentTargetAndRefreshUniversalAnger() {
/* 167 */     stopBeingAngry();
/* 168 */     startPersistentAngerTimer();
/*     */   }
/*     */   
/*     */   default void stopBeingAngry() {
/* 172 */     setLastHurtByMob(null);
/* 173 */     setPersistentAngerTarget(null);
/* 174 */     setTarget(null);
/* 175 */     setRemainingPersistentAngerTime(0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   LivingEntity getLastHurtByMob();
/*     */   
/*     */   void setLastHurtByMob(@Nullable LivingEntity paramLivingEntity);
/*     */   
/*     */   void setLastHurtByPlayer(@Nullable Player paramPlayer);
/*     */   
/*     */   void setTarget(@Nullable LivingEntity paramLivingEntity);
/*     */   
/*     */   boolean canAttack(LivingEntity paramLivingEntity);
/*     */   
/*     */   @Nullable
/*     */   LivingEntity getTarget();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\NeutralMob.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */