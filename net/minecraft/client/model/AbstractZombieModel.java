/*    */ package net.minecraft.client.model;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public abstract class AbstractZombieModel<T extends Monster> extends HumanoidModel<T> {
/*    */   protected AbstractZombieModel(ModelPart $$0) {
/*  8 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 13 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/* 14 */     AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, isAggressive($$0), this.attackTime, $$3);
/*    */   }
/*    */   
/*    */   public abstract boolean isAggressive(T paramT);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\AbstractZombieModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */