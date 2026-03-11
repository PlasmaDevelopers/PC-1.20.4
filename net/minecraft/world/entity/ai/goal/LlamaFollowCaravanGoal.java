/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.horse.Llama;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LlamaFollowCaravanGoal
/*     */   extends Goal
/*     */ {
/*     */   public final Llama llama;
/*     */   private double speedModifier;
/*     */   private static final int CARAVAN_LIMIT = 8;
/*     */   private int distCheckCounter;
/*     */   
/*     */   public LlamaFollowCaravanGoal(Llama $$0, double $$1) {
/*  23 */     this.llama = $$0;
/*  24 */     this.speedModifier = $$1;
/*  25 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  30 */     if (this.llama.isLeashed() || this.llama.inCaravan()) {
/*  31 */       return false;
/*     */     }
/*     */     
/*  34 */     List<Entity> $$0 = this.llama.level().getEntities((Entity)this.llama, this.llama.getBoundingBox().inflate(9.0D, 4.0D, 9.0D), $$0 -> {
/*     */           EntityType<?> $$1 = $$0.getType();
/*  36 */           return ($$1 == EntityType.LLAMA || $$1 == EntityType.TRADER_LLAMA);
/*     */         });
/*     */     
/*  39 */     Llama $$1 = null;
/*  40 */     double $$2 = Double.MAX_VALUE;
/*  41 */     for (Entity $$3 : $$0) {
/*  42 */       Llama $$4 = (Llama)$$3;
/*     */       
/*  44 */       if (!$$4.inCaravan() || $$4.hasCaravanTail()) {
/*     */         continue;
/*     */       }
/*     */       
/*  48 */       double $$5 = this.llama.distanceToSqr((Entity)$$4);
/*  49 */       if ($$5 > $$2) {
/*     */         continue;
/*     */       }
/*     */       
/*  53 */       $$2 = $$5;
/*  54 */       $$1 = $$4;
/*     */     } 
/*     */     
/*  57 */     if ($$1 == null)
/*     */     {
/*  59 */       for (Entity $$6 : $$0) {
/*  60 */         Llama $$7 = (Llama)$$6;
/*     */         
/*  62 */         if (!$$7.isLeashed()) {
/*     */           continue;
/*     */         }
/*     */         
/*  66 */         if ($$7.hasCaravanTail()) {
/*     */           continue;
/*     */         }
/*     */         
/*  70 */         double $$8 = this.llama.distanceToSqr((Entity)$$7);
/*  71 */         if ($$8 > $$2) {
/*     */           continue;
/*     */         }
/*     */         
/*  75 */         $$2 = $$8;
/*  76 */         $$1 = $$7;
/*     */       } 
/*     */     }
/*     */     
/*  80 */     if ($$1 == null) {
/*  81 */       return false;
/*     */     }
/*  83 */     if ($$2 < 4.0D) {
/*  84 */       return false;
/*     */     }
/*     */     
/*  87 */     if (!$$1.isLeashed() && !firstIsLeashed($$1, 1)) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     this.llama.joinCaravan($$1);
/*     */     
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  98 */     if (!this.llama.inCaravan() || !this.llama.getCaravanHead().isAlive() || !firstIsLeashed(this.llama, 0)) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     double $$0 = this.llama.distanceToSqr((Entity)this.llama.getCaravanHead());
/* 103 */     if ($$0 > 676.0D) {
/* 104 */       if (this.speedModifier <= 3.0D) {
/* 105 */         this.speedModifier *= 1.2D;
/* 106 */         this.distCheckCounter = reducedTickDelay(40);
/* 107 */         return true;
/*     */       } 
/*     */       
/* 110 */       if (this.distCheckCounter == 0) {
/* 111 */         return false;
/*     */       }
/*     */     } 
/* 114 */     if (this.distCheckCounter > 0) {
/* 115 */       this.distCheckCounter--;
/*     */     }
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 122 */     this.llama.leaveCaravan();
/* 123 */     this.speedModifier = 2.1D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 128 */     if (!this.llama.inCaravan()) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     if (this.llama.getLeashHolder() instanceof net.minecraft.world.entity.decoration.LeashFenceKnotEntity) {
/*     */       return;
/*     */     }
/*     */     
/* 136 */     Llama $$0 = this.llama.getCaravanHead();
/* 137 */     double $$1 = this.llama.distanceTo((Entity)$$0);
/*     */     
/* 139 */     float $$2 = 2.0F;
/* 140 */     Vec3 $$3 = (new Vec3($$0.getX() - this.llama.getX(), $$0.getY() - this.llama.getY(), $$0.getZ() - this.llama.getZ())).normalize().scale(Math.max($$1 - 2.0D, 0.0D));
/* 141 */     this.llama.getNavigation().moveTo(this.llama.getX() + $$3.x, this.llama.getY() + $$3.y, this.llama.getZ() + $$3.z, this.speedModifier);
/*     */   }
/*     */   
/*     */   private boolean firstIsLeashed(Llama $$0, int $$1) {
/* 145 */     if ($$1 > 8) {
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     if ($$0.inCaravan()) {
/* 150 */       if ($$0.getCaravanHead().isLeashed()) {
/* 151 */         return true;
/*     */       }
/* 153 */       return firstIsLeashed($$0.getCaravanHead(), ++$$1);
/*     */     } 
/* 155 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\LlamaFollowCaravanGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */