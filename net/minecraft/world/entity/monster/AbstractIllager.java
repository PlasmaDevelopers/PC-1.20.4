/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.MobType;
/*    */ import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
/*    */ import net.minecraft.world.entity.raid.Raider;
/*    */ import net.minecraft.world.level.Level;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public abstract class AbstractIllager extends Raider {
/*    */   public enum IllagerArmPose {
/* 16 */     CROSSED,
/* 17 */     ATTACKING,
/* 18 */     SPELLCASTING,
/* 19 */     BOW_AND_ARROW,
/* 20 */     CROSSBOW_HOLD,
/* 21 */     CROSSBOW_CHARGE,
/* 22 */     CELEBRATING,
/* 23 */     NEUTRAL;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractIllager(EntityType<? extends AbstractIllager> $$0, Level $$1) {
/* 28 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerGoals() {
/* 33 */     super.registerGoals();
/*    */   }
/*    */ 
/*    */   
/*    */   public MobType getMobType() {
/* 38 */     return MobType.ILLAGER;
/*    */   }
/*    */   
/*    */   public IllagerArmPose getArmPose() {
/* 42 */     return IllagerArmPose.CROSSED;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canAttack(LivingEntity $$0) {
/* 49 */     if ($$0 instanceof net.minecraft.world.entity.npc.AbstractVillager && $$0.isBaby()) {
/* 50 */       return false;
/*    */     }
/* 52 */     return super.canAttack($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float ridingOffset(Entity $$0) {
/* 57 */     return -0.6F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 62 */     return new Vector3f(0.0F, $$1.height + 0.05F * $$2, 0.0F);
/*    */   }
/*    */   
/*    */   protected class RaiderOpenDoorGoal extends OpenDoorGoal {
/*    */     public RaiderOpenDoorGoal(Raider $$1) {
/* 67 */       super((Mob)$$1, false);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean canUse() {
/* 72 */       return (super.canUse() && AbstractIllager.this.hasActiveRaid());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\AbstractIllager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */