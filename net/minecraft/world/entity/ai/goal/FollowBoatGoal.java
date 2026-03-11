/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FollowBoatGoal
/*     */   extends Goal
/*     */ {
/*     */   private int timeToRecalcPath;
/*     */   private final PathfinderMob mob;
/*     */   @Nullable
/*     */   private Player following;
/*     */   private BoatGoals currentGoal;
/*     */   
/*     */   public FollowBoatGoal(PathfinderMob $$0) {
/*  30 */     this.mob = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  35 */     List<Boat> $$0 = this.mob.level().getEntitiesOfClass(Boat.class, this.mob.getBoundingBox().inflate(5.0D));
/*  36 */     boolean $$1 = false;
/*  37 */     for (Boat $$2 : $$0) {
/*  38 */       LivingEntity livingEntity = $$2.getControllingPassenger();
/*  39 */       if (livingEntity instanceof Player && (
/*  40 */         Mth.abs(((Player)livingEntity).xxa) > 0.0F || Mth.abs(((Player)livingEntity).zza) > 0.0F)) {
/*  41 */         $$1 = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/*  47 */     return ((this.following != null && (Mth.abs(this.following.xxa) > 0.0F || Mth.abs(this.following.zza) > 0.0F)) || $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInterruptable() {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  57 */     return (this.following != null && this.following.isPassenger() && (Mth.abs(this.following.xxa) > 0.0F || Mth.abs(this.following.zza) > 0.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  62 */     List<Boat> $$0 = this.mob.level().getEntitiesOfClass(Boat.class, this.mob.getBoundingBox().inflate(5.0D));
/*  63 */     for (Boat $$1 : $$0) {
/*  64 */       LivingEntity livingEntity = $$1.getControllingPassenger(); if (livingEntity instanceof Player) { Player $$2 = (Player)livingEntity;
/*  65 */         this.following = $$2;
/*     */         
/*     */         break; }
/*     */     
/*     */     } 
/*  70 */     this.timeToRecalcPath = 0;
/*  71 */     this.currentGoal = BoatGoals.GO_TO_BOAT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  76 */     this.following = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  81 */     boolean $$0 = (Mth.abs(this.following.xxa) > 0.0F || Mth.abs(this.following.zza) > 0.0F);
/*  82 */     float $$1 = (this.currentGoal == BoatGoals.GO_IN_BOAT_DIRECTION) ? ($$0 ? 0.01F : 0.0F) : 0.015F;
/*     */     
/*  84 */     this.mob.moveRelative($$1, new Vec3(this.mob.xxa, this.mob.yya, this.mob.zza));
/*  85 */     this.mob.move(MoverType.SELF, this.mob.getDeltaMovement());
/*     */     
/*  87 */     if (--this.timeToRecalcPath > 0) {
/*     */       return;
/*     */     }
/*  90 */     this.timeToRecalcPath = adjustedTickDelay(10);
/*     */     
/*  92 */     if (this.currentGoal == BoatGoals.GO_TO_BOAT) {
/*  93 */       BlockPos $$2 = this.following.blockPosition().relative(this.following.getDirection().getOpposite());
/*  94 */       $$2 = $$2.offset(0, -1, 0);
/*  95 */       this.mob.getNavigation().moveTo($$2.getX(), $$2.getY(), $$2.getZ(), 1.0D);
/*     */       
/*  97 */       if (this.mob.distanceTo((Entity)this.following) < 4.0F) {
/*  98 */         this.timeToRecalcPath = 0;
/*  99 */         this.currentGoal = BoatGoals.GO_IN_BOAT_DIRECTION;
/*     */       } 
/* 101 */     } else if (this.currentGoal == BoatGoals.GO_IN_BOAT_DIRECTION) {
/*     */       
/* 103 */       Direction $$3 = this.following.getMotionDirection();
/* 104 */       BlockPos $$4 = this.following.blockPosition().relative($$3, 10);
/*     */ 
/*     */       
/* 107 */       this.mob.getNavigation().moveTo($$4.getX(), ($$4.getY() - 1), $$4.getZ(), 1.0D);
/*     */       
/* 109 */       if (this.mob.distanceTo((Entity)this.following) > 12.0F) {
/* 110 */         this.timeToRecalcPath = 0;
/* 111 */         this.currentGoal = BoatGoals.GO_TO_BOAT;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\FollowBoatGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */