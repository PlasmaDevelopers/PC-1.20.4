/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.TargetGoal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.npc.WanderingTrader;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ 
/*     */ public class TraderLlama
/*     */   extends Llama {
/*     */   public TraderLlama(EntityType<? extends TraderLlama> $$0, Level $$1) {
/*  27 */     super((EntityType)$$0, $$1);
/*  28 */     this.despawnDelay = 47999;
/*     */   }
/*     */   private int despawnDelay;
/*     */   
/*     */   public boolean isTraderLlama() {
/*  33 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Llama makeNewLlama() {
/*  39 */     return (Llama)EntityType.TRADER_LLAMA.create(level());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  44 */     super.addAdditionalSaveData($$0);
/*  45 */     $$0.putInt("DespawnDelay", this.despawnDelay);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  50 */     super.readAdditionalSaveData($$0);
/*  51 */     if ($$0.contains("DespawnDelay", 99)) {
/*  52 */       this.despawnDelay = $$0.getInt("DespawnDelay");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  58 */     super.registerGoals();
/*     */     
/*  60 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 2.0D));
/*     */     
/*  62 */     this.targetSelector.addGoal(1, (Goal)new TraderLlamaDefendWanderingTraderGoal(this));
/*     */   }
/*     */   
/*     */   public void setDespawnDelay(int $$0) {
/*  66 */     this.despawnDelay = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPlayerRide(Player $$0) {
/*  71 */     Entity $$1 = getLeashHolder();
/*  72 */     if ($$1 instanceof WanderingTrader) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     super.doPlayerRide($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/*  81 */     super.aiStep();
/*     */     
/*  83 */     if (!(level()).isClientSide) {
/*  84 */       maybeDespawn();
/*     */     }
/*     */   }
/*     */   
/*     */   private void maybeDespawn() {
/*  89 */     if (!canDespawn()) {
/*     */       return;
/*     */     }
/*     */     
/*  93 */     this.despawnDelay = isLeashedToWanderingTrader() ? (((WanderingTrader)getLeashHolder()).getDespawnDelay() - 1) : (this.despawnDelay - 1);
/*     */     
/*  95 */     if (this.despawnDelay <= 0) {
/*  96 */       dropLeash(true, false);
/*  97 */       discard();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canDespawn() {
/* 102 */     return (!isTamed() && 
/* 103 */       !isLeashedToSomethingOtherThanTheWanderingTrader() && 
/* 104 */       !hasExactlyOnePlayerPassenger());
/*     */   }
/*     */   
/*     */   private boolean isLeashedToWanderingTrader() {
/* 108 */     return getLeashHolder() instanceof WanderingTrader;
/*     */   }
/*     */   
/*     */   private boolean isLeashedToSomethingOtherThanTheWanderingTrader() {
/* 112 */     return (isLeashed() && !isLeashedToWanderingTrader());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/* 118 */     if ($$2 == MobSpawnType.EVENT) {
/* 119 */       setAge(0);
/*     */     }
/*     */     
/* 122 */     if ($$3 == null) {
/* 123 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(false);
/*     */     }
/*     */     
/* 126 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*     */   }
/*     */   
/*     */   protected static class TraderLlamaDefendWanderingTraderGoal extends TargetGoal {
/*     */     private final Llama llama;
/*     */     private LivingEntity ownerLastHurtBy;
/*     */     private int timestamp;
/*     */     
/*     */     public TraderLlamaDefendWanderingTraderGoal(Llama $$0) {
/* 135 */       super((Mob)$$0, false);
/* 136 */       this.llama = $$0;
/* 137 */       setFlags(EnumSet.of(Goal.Flag.TARGET));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 142 */       if (!this.llama.isLeashed()) {
/* 143 */         return false;
/*     */       }
/* 145 */       Entity $$0 = this.llama.getLeashHolder();
/* 146 */       if (!($$0 instanceof WanderingTrader)) {
/* 147 */         return false;
/*     */       }
/*     */       
/* 150 */       WanderingTrader $$1 = (WanderingTrader)$$0;
/* 151 */       this.ownerLastHurtBy = $$1.getLastHurtByMob();
/* 152 */       int $$2 = $$1.getLastHurtByMobTimestamp();
/* 153 */       return ($$2 != this.timestamp && canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT));
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 158 */       this.mob.setTarget(this.ownerLastHurtBy);
/*     */       
/* 160 */       Entity $$0 = this.llama.getLeashHolder();
/* 161 */       if ($$0 instanceof WanderingTrader) {
/* 162 */         this.timestamp = ((WanderingTrader)$$0).getLastHurtByMobTimestamp();
/*     */       }
/*     */       
/* 165 */       super.start();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\TraderLlama.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */