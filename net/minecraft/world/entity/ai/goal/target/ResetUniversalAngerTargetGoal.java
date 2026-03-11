/*    */ package net.minecraft.world.entity.ai.goal.target;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.world.entity.EntitySelector;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.NeutralMob;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.entity.ai.goal.Goal;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResetUniversalAngerTargetGoal<T extends Mob & NeutralMob>
/*    */   extends Goal
/*    */ {
/*    */   private static final int ALERT_RANGE_Y = 10;
/*    */   private final T mob;
/*    */   private final boolean alertOthersOfSameType;
/*    */   private int lastHurtByPlayerTimestamp;
/*    */   
/*    */   public ResetUniversalAngerTargetGoal(T $$0, boolean $$1) {
/* 33 */     this.mob = $$0;
/* 34 */     this.alertOthersOfSameType = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 39 */     return (this.mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER) && wasHurtByPlayer());
/*    */   }
/*    */   
/*    */   private boolean wasHurtByPlayer() {
/* 43 */     return (this.mob.getLastHurtByMob() != null && this.mob
/* 44 */       .getLastHurtByMob().getType() == EntityType.PLAYER && this.mob
/* 45 */       .getLastHurtByMobTimestamp() > this.lastHurtByPlayerTimestamp);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 50 */     this.lastHurtByPlayerTimestamp = this.mob.getLastHurtByMobTimestamp();
/* 51 */     ((NeutralMob)this.mob).forgetCurrentTargetAndRefreshUniversalAnger();
/* 52 */     if (this.alertOthersOfSameType) {
/* 53 */       getNearbyMobsOfSameType().stream()
/* 54 */         .filter($$0 -> ($$0 != this.mob))
/* 55 */         .map($$0 -> (NeutralMob)$$0)
/* 56 */         .forEach(NeutralMob::forgetCurrentTargetAndRefreshUniversalAnger);
/*    */     }
/* 58 */     super.start();
/*    */   }
/*    */   
/*    */   private List<? extends Mob> getNearbyMobsOfSameType() {
/* 62 */     double $$0 = this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
/* 63 */     AABB $$1 = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate($$0, 10.0D, $$0);
/* 64 */     return this.mob.level().getEntitiesOfClass(this.mob.getClass(), $$1, EntitySelector.NO_SPECTATORS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\ResetUniversalAngerTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */