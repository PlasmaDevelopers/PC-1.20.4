/*    */ package net.minecraft.client.model;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Monster;
/*    */ import net.minecraft.world.entity.monster.Zombie;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class DrownedModel<T extends Zombie> extends ZombieModel<T> {
/*    */   public DrownedModel(ModelPart $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
/* 24 */     MeshDefinition $$1 = HumanoidModel.createMesh($$0, 0.0F);
/* 25 */     PartDefinition $$2 = $$1.getRoot();
/*    */     
/* 27 */     $$2.addOrReplaceChild("left_arm", 
/* 28 */         CubeListBuilder.create()
/* 29 */         .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/* 30 */         PartPose.offset(5.0F, 2.0F, 0.0F));
/*    */     
/* 32 */     $$2.addOrReplaceChild("left_leg", 
/* 33 */         CubeListBuilder.create()
/* 34 */         .texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/* 35 */         PartPose.offset(1.9F, 12.0F, 0.0F));
/*    */ 
/*    */     
/* 38 */     return LayerDefinition.create($$1, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 43 */     this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
/* 44 */     this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
/*    */     
/* 46 */     ItemStack $$4 = $$0.getItemInHand(InteractionHand.MAIN_HAND);
/* 47 */     if ($$4.is(Items.TRIDENT) && $$0.isAggressive()) {
/* 48 */       if ($$0.getMainArm() == HumanoidArm.RIGHT) {
/* 49 */         this.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
/*    */       } else {
/* 51 */         this.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
/*    */       } 
/*    */     }
/*    */     
/* 55 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 60 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 62 */     if (this.leftArmPose == HumanoidModel.ArmPose.THROW_SPEAR) {
/* 63 */       this.leftArm.xRot = this.leftArm.xRot * 0.5F - 3.1415927F;
/* 64 */       this.leftArm.yRot = 0.0F;
/*    */     } 
/*    */     
/* 67 */     if (this.rightArmPose == HumanoidModel.ArmPose.THROW_SPEAR) {
/* 68 */       this.rightArm.xRot = this.rightArm.xRot * 0.5F - 3.1415927F;
/* 69 */       this.rightArm.yRot = 0.0F;
/*    */     } 
/*    */     
/* 72 */     if (this.swimAmount > 0.0F) {
/* 73 */       this.rightArm.xRot = rotlerpRad(this.swimAmount, this.rightArm.xRot, -2.5132742F) + this.swimAmount * 0.35F * Mth.sin(0.1F * $$3);
/* 74 */       this.leftArm.xRot = rotlerpRad(this.swimAmount, this.leftArm.xRot, -2.5132742F) - this.swimAmount * 0.35F * Mth.sin(0.1F * $$3);
/* 75 */       this.rightArm.zRot = rotlerpRad(this.swimAmount, this.rightArm.zRot, -0.15F);
/* 76 */       this.leftArm.zRot = rotlerpRad(this.swimAmount, this.leftArm.zRot, 0.15F);
/*    */       
/* 78 */       this.leftLeg.xRot -= this.swimAmount * 0.55F * Mth.sin(0.1F * $$3);
/* 79 */       this.rightLeg.xRot += this.swimAmount * 0.55F * Mth.sin(0.1F * $$3);
/* 80 */       this.head.xRot = 0.0F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\DrownedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */