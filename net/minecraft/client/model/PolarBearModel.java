/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.PolarBear;
/*    */ 
/*    */ public class PolarBearModel<T extends PolarBear>
/*    */   extends QuadrupedModel<T> {
/*    */   public PolarBearModel(ModelPart $$0) {
/* 15 */     super($$0, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 19 */     MeshDefinition $$0 = new MeshDefinition();
/* 20 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 22 */     $$1.addOrReplaceChild("head", 
/* 23 */         CubeListBuilder.create()
/* 24 */         .texOffs(0, 0).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F)
/* 25 */         .texOffs(0, 44).addBox("mouth", -2.5F, 1.0F, -6.0F, 5.0F, 3.0F, 3.0F)
/* 26 */         .texOffs(26, 0).addBox("right_ear", -4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F)
/* 27 */         .texOffs(26, 0).mirror().addBox("left_ear", 2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F), 
/* 28 */         PartPose.offset(0.0F, 10.0F, -16.0F));
/*    */     
/* 30 */     $$1.addOrReplaceChild("body", 
/* 31 */         CubeListBuilder.create()
/* 32 */         .texOffs(0, 19).addBox(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F)
/* 33 */         .texOffs(39, 0).addBox(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F), 
/* 34 */         PartPose.offsetAndRotation(-2.0F, 9.0F, 12.0F, 1.5707964F, 0.0F, 0.0F));
/*    */ 
/*    */     
/* 37 */     int $$2 = 10;
/*    */     
/* 39 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(50, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F);
/* 40 */     $$1.addOrReplaceChild("right_hind_leg", $$3, PartPose.offset(-4.5F, 14.0F, 6.0F));
/* 41 */     $$1.addOrReplaceChild("left_hind_leg", $$3, PartPose.offset(4.5F, 14.0F, 6.0F));
/*    */ 
/*    */     
/* 44 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(50, 40).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F);
/* 45 */     $$1.addOrReplaceChild("right_front_leg", $$4, PartPose.offset(-3.5F, 14.0F, -8.0F));
/* 46 */     $$1.addOrReplaceChild("left_front_leg", $$4, PartPose.offset(3.5F, 14.0F, -8.0F));
/*    */     
/* 48 */     return LayerDefinition.create($$0, 128, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 53 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 55 */     float $$6 = $$3 - ((PolarBear)$$0).tickCount;
/* 56 */     float $$7 = $$0.getStandingAnimationScale($$6);
/* 57 */     $$7 *= $$7;
/* 58 */     float $$8 = 1.0F - $$7;
/*    */     
/* 60 */     this.body.xRot = 1.5707964F - $$7 * 3.1415927F * 0.35F;
/* 61 */     this.body.y = 9.0F * $$8 + 11.0F * $$7;
/*    */     
/* 63 */     this.rightFrontLeg.y = 14.0F * $$8 - 6.0F * $$7;
/* 64 */     this.rightFrontLeg.z = -8.0F * $$8 - 4.0F * $$7;
/* 65 */     this.rightFrontLeg.xRot -= $$7 * 3.1415927F * 0.45F;
/*    */     
/* 67 */     this.leftFrontLeg.y = this.rightFrontLeg.y;
/* 68 */     this.leftFrontLeg.z = this.rightFrontLeg.z;
/* 69 */     this.leftFrontLeg.xRot -= $$7 * 3.1415927F * 0.45F;
/*    */     
/* 71 */     if (this.young) {
/* 72 */       this.head.y = 10.0F * $$8 - 9.0F * $$7;
/* 73 */       this.head.z = -16.0F * $$8 - 7.0F * $$7;
/*    */     } else {
/* 75 */       this.head.y = 10.0F * $$8 - 14.0F * $$7;
/* 76 */       this.head.z = -16.0F * $$8 - 3.0F * $$7;
/*    */     } 
/* 78 */     this.head.xRot += $$7 * 3.1415927F * 0.15F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PolarBearModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */