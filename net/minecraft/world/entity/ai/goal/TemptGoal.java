/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ 
/*     */ public class TemptGoal extends Goal {
/*  13 */   private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
/*     */   
/*     */   private final TargetingConditions targetingConditions;
/*     */   protected final PathfinderMob mob;
/*     */   private final double speedModifier;
/*     */   private double px;
/*     */   private double py;
/*     */   private double pz;
/*     */   private double pRotX;
/*     */   private double pRotY;
/*     */   @Nullable
/*     */   protected Player player;
/*     */   private int calmDown;
/*     */   private boolean isRunning;
/*     */   private final Ingredient items;
/*     */   private final boolean canScare;
/*     */   
/*     */   public TemptGoal(PathfinderMob $$0, double $$1, Ingredient $$2, boolean $$3) {
/*  31 */     this.mob = $$0;
/*  32 */     this.speedModifier = $$1;
/*  33 */     this.items = $$2;
/*  34 */     this.canScare = $$3;
/*  35 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*  36 */     this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  41 */     if (this.calmDown > 0) {
/*  42 */       this.calmDown--;
/*  43 */       return false;
/*     */     } 
/*  45 */     this.player = this.mob.level().getNearestPlayer(this.targetingConditions, (LivingEntity)this.mob);
/*  46 */     return (this.player != null);
/*     */   }
/*     */   
/*     */   private boolean shouldFollow(LivingEntity $$0) {
/*  50 */     return (this.items.test($$0.getMainHandItem()) || this.items.test($$0.getOffhandItem()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  55 */     if (canScare()) {
/*  56 */       if (this.mob.distanceToSqr((Entity)this.player) < 36.0D) {
/*  57 */         if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D) {
/*  58 */           return false;
/*     */         }
/*  60 */         if (Math.abs(this.player.getXRot() - this.pRotX) > 5.0D || Math.abs(this.player.getYRot() - this.pRotY) > 5.0D) {
/*  61 */           return false;
/*     */         }
/*     */       } else {
/*  64 */         this.px = this.player.getX();
/*  65 */         this.py = this.player.getY();
/*  66 */         this.pz = this.player.getZ();
/*     */       } 
/*  68 */       this.pRotX = this.player.getXRot();
/*  69 */       this.pRotY = this.player.getYRot();
/*     */     } 
/*  71 */     return canUse();
/*     */   }
/*     */   
/*     */   protected boolean canScare() {
/*  75 */     return this.canScare;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  80 */     this.px = this.player.getX();
/*  81 */     this.py = this.player.getY();
/*  82 */     this.pz = this.player.getZ();
/*  83 */     this.isRunning = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  88 */     this.player = null;
/*  89 */     this.mob.getNavigation().stop();
/*  90 */     this.calmDown = reducedTickDelay(100);
/*  91 */     this.isRunning = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  96 */     this.mob.getLookControl().setLookAt((Entity)this.player, (this.mob.getMaxHeadYRot() + 20), this.mob.getMaxHeadXRot());
/*  97 */     if (this.mob.distanceToSqr((Entity)this.player) < 6.25D) {
/*  98 */       this.mob.getNavigation().stop();
/*     */     } else {
/* 100 */       this.mob.getNavigation().moveTo((Entity)this.player, this.speedModifier);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/* 105 */     return this.isRunning;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\TemptGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */