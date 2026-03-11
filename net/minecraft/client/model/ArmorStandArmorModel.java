/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.decoration.ArmorStand;
/*    */ 
/*    */ public class ArmorStandArmorModel extends HumanoidModel<ArmorStand> {
/*    */   public ArmorStandArmorModel(ModelPart $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
/* 20 */     MeshDefinition $$1 = HumanoidModel.createMesh($$0, 0.0F);
/* 21 */     PartDefinition $$2 = $$1.getRoot();
/* 22 */     $$2.addOrReplaceChild("head", 
/* 23 */         CubeListBuilder.create()
/* 24 */         .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$0), 
/* 25 */         PartPose.offset(0.0F, 1.0F, 0.0F));
/*    */     
/* 27 */     $$2.addOrReplaceChild("hat", 
/* 28 */         CubeListBuilder.create()
/* 29 */         .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$0.extend(0.5F)), 
/* 30 */         PartPose.offset(0.0F, 1.0F, 0.0F));
/*    */     
/* 32 */     $$2.addOrReplaceChild("right_leg", 
/* 33 */         CubeListBuilder.create()
/* 34 */         .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(-0.1F)), 
/* 35 */         PartPose.offset(-1.9F, 11.0F, 0.0F));
/*    */     
/* 37 */     $$2.addOrReplaceChild("left_leg", 
/* 38 */         CubeListBuilder.create()
/* 39 */         .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(-0.1F)), 
/* 40 */         PartPose.offset(1.9F, 11.0F, 0.0F));
/*    */ 
/*    */     
/* 43 */     return LayerDefinition.create($$1, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(ArmorStand $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 48 */     this.head.xRot = 0.017453292F * $$0.getHeadPose().getX();
/* 49 */     this.head.yRot = 0.017453292F * $$0.getHeadPose().getY();
/* 50 */     this.head.zRot = 0.017453292F * $$0.getHeadPose().getZ();
/*    */     
/* 52 */     this.body.xRot = 0.017453292F * $$0.getBodyPose().getX();
/* 53 */     this.body.yRot = 0.017453292F * $$0.getBodyPose().getY();
/* 54 */     this.body.zRot = 0.017453292F * $$0.getBodyPose().getZ();
/*    */     
/* 56 */     this.leftArm.xRot = 0.017453292F * $$0.getLeftArmPose().getX();
/* 57 */     this.leftArm.yRot = 0.017453292F * $$0.getLeftArmPose().getY();
/* 58 */     this.leftArm.zRot = 0.017453292F * $$0.getLeftArmPose().getZ();
/*    */     
/* 60 */     this.rightArm.xRot = 0.017453292F * $$0.getRightArmPose().getX();
/* 61 */     this.rightArm.yRot = 0.017453292F * $$0.getRightArmPose().getY();
/* 62 */     this.rightArm.zRot = 0.017453292F * $$0.getRightArmPose().getZ();
/*    */     
/* 64 */     this.leftLeg.xRot = 0.017453292F * $$0.getLeftLegPose().getX();
/* 65 */     this.leftLeg.yRot = 0.017453292F * $$0.getLeftLegPose().getY();
/* 66 */     this.leftLeg.zRot = 0.017453292F * $$0.getLeftLegPose().getZ();
/*    */     
/* 68 */     this.rightLeg.xRot = 0.017453292F * $$0.getRightLegPose().getX();
/* 69 */     this.rightLeg.yRot = 0.017453292F * $$0.getRightLegPose().getY();
/* 70 */     this.rightLeg.zRot = 0.017453292F * $$0.getRightLegPose().getZ();
/*    */     
/* 72 */     this.hat.copyFrom(this.head);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ArmorStandArmorModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */