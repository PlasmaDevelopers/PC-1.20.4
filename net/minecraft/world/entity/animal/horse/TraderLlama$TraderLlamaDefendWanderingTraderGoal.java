/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.target.TargetGoal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.npc.WanderingTrader;
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
/*     */ public class TraderLlamaDefendWanderingTraderGoal
/*     */   extends TargetGoal
/*     */ {
/*     */   private final Llama llama;
/*     */   private LivingEntity ownerLastHurtBy;
/*     */   private int timestamp;
/*     */   
/*     */   public TraderLlamaDefendWanderingTraderGoal(Llama $$0) {
/* 135 */     super((Mob)$$0, false);
/* 136 */     this.llama = $$0;
/* 137 */     setFlags(EnumSet.of(Goal.Flag.TARGET));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 142 */     if (!this.llama.isLeashed()) {
/* 143 */       return false;
/*     */     }
/* 145 */     Entity $$0 = this.llama.getLeashHolder();
/* 146 */     if (!($$0 instanceof WanderingTrader)) {
/* 147 */       return false;
/*     */     }
/*     */     
/* 150 */     WanderingTrader $$1 = (WanderingTrader)$$0;
/* 151 */     this.ownerLastHurtBy = $$1.getLastHurtByMob();
/* 152 */     int $$2 = $$1.getLastHurtByMobTimestamp();
/* 153 */     return ($$2 != this.timestamp && canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 158 */     this.mob.setTarget(this.ownerLastHurtBy);
/*     */     
/* 160 */     Entity $$0 = this.llama.getLeashHolder();
/* 161 */     if ($$0 instanceof WanderingTrader) {
/* 162 */       this.timestamp = ((WanderingTrader)$$0).getLastHurtByMobTimestamp();
/*     */     }
/*     */     
/* 165 */     super.start();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\TraderLlama$TraderLlamaDefendWanderingTraderGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */