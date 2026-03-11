/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.projectile.SmallFireball;
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
/*     */ class BlazeAttackGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Blaze blaze;
/*     */   private int attackStep;
/*     */   private int attackTime;
/*     */   private int lastSeen;
/*     */   
/*     */   public BlazeAttackGoal(Blaze $$0) {
/* 162 */     this.blaze = $$0;
/*     */     
/* 164 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 169 */     LivingEntity $$0 = this.blaze.getTarget();
/* 170 */     return ($$0 != null && $$0.isAlive() && this.blaze.canAttack($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 175 */     this.attackStep = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 180 */     this.blaze.setCharged(false);
/* 181 */     this.lastSeen = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 191 */     this.attackTime--;
/*     */     
/* 193 */     LivingEntity $$0 = this.blaze.getTarget();
/*     */     
/* 195 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 199 */     boolean $$1 = this.blaze.getSensing().hasLineOfSight((Entity)$$0);
/*     */     
/* 201 */     if ($$1) {
/* 202 */       this.lastSeen = 0;
/*     */     } else {
/* 204 */       this.lastSeen++;
/*     */     } 
/*     */     
/* 207 */     double $$2 = this.blaze.distanceToSqr((Entity)$$0);
/*     */     
/* 209 */     if ($$2 < 4.0D) {
/* 210 */       if (!$$1) {
/*     */         return;
/*     */       }
/*     */       
/* 214 */       if (this.attackTime <= 0) {
/* 215 */         this.attackTime = 20;
/* 216 */         this.blaze.doHurtTarget((Entity)$$0);
/*     */       } 
/* 218 */       this.blaze.getMoveControl().setWantedPosition($$0.getX(), $$0.getY(), $$0.getZ(), 1.0D);
/* 219 */     } else if ($$2 < getFollowDistance() * getFollowDistance() && $$1) {
/* 220 */       double $$3 = $$0.getX() - this.blaze.getX();
/* 221 */       double $$4 = $$0.getY(0.5D) - this.blaze.getY(0.5D);
/* 222 */       double $$5 = $$0.getZ() - this.blaze.getZ();
/*     */       
/* 224 */       if (this.attackTime <= 0) {
/* 225 */         this.attackStep++;
/* 226 */         if (this.attackStep == 1) {
/* 227 */           this.attackTime = 60;
/* 228 */           this.blaze.setCharged(true);
/* 229 */         } else if (this.attackStep <= 4) {
/* 230 */           this.attackTime = 6;
/*     */         } else {
/* 232 */           this.attackTime = 100;
/* 233 */           this.attackStep = 0;
/* 234 */           this.blaze.setCharged(false);
/*     */         } 
/*     */         
/* 237 */         if (this.attackStep > 1) {
/* 238 */           double $$6 = Math.sqrt(Math.sqrt($$2)) * 0.5D;
/*     */           
/* 240 */           if (!this.blaze.isSilent()) {
/* 241 */             this.blaze.level().levelEvent(null, 1018, this.blaze.blockPosition(), 0);
/*     */           }
/* 243 */           for (int $$7 = 0; $$7 < 1; $$7++) {
/* 244 */             SmallFireball $$8 = new SmallFireball(this.blaze.level(), (LivingEntity)this.blaze, this.blaze.getRandom().triangle($$3, 2.297D * $$6), $$4, this.blaze.getRandom().triangle($$5, 2.297D * $$6));
/* 245 */             $$8.setPos($$8.getX(), this.blaze.getY(0.5D) + 0.5D, $$8.getZ());
/* 246 */             this.blaze.level().addFreshEntity((Entity)$$8);
/*     */           } 
/*     */         } 
/*     */       } 
/* 250 */       this.blaze.getLookControl().setLookAt((Entity)$$0, 10.0F, 10.0F);
/*     */     }
/* 252 */     else if (this.lastSeen < 5) {
/* 253 */       this.blaze.getMoveControl().setWantedPosition($$0.getX(), $$0.getY(), $$0.getZ(), 1.0D);
/*     */     } 
/*     */ 
/*     */     
/* 257 */     super.tick();
/*     */   }
/*     */   
/*     */   private double getFollowDistance() {
/* 261 */     return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Blaze$BlazeAttackGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */