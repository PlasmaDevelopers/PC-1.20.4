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
/*    */ import net.minecraft.world.entity.animal.Sheep;
/*    */ 
/*    */ public class SheepFurModel<T extends Sheep>
/*    */   extends QuadrupedModel<T> {
/*    */   private float headXRot;
/*    */   
/*    */   public SheepFurModel(ModelPart $$0) {
/* 18 */     super($$0, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createFurLayer() {
/* 22 */     MeshDefinition $$0 = new MeshDefinition();
/* 23 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 25 */     $$1.addOrReplaceChild("head", 
/* 26 */         CubeListBuilder.create()
/* 27 */         .texOffs(0, 0).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.6F)), 
/* 28 */         PartPose.offset(0.0F, 6.0F, -8.0F));
/*    */     
/* 30 */     $$1.addOrReplaceChild("body", 
/* 31 */         CubeListBuilder.create()
/* 32 */         .texOffs(28, 8).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(1.75F)), 
/* 33 */         PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
/*    */ 
/*    */ 
/*    */     
/* 37 */     CubeListBuilder $$2 = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.5F));
/* 38 */     $$1.addOrReplaceChild("right_hind_leg", $$2, PartPose.offset(-3.0F, 12.0F, 7.0F));
/* 39 */     $$1.addOrReplaceChild("left_hind_leg", $$2, PartPose.offset(3.0F, 12.0F, 7.0F));
/* 40 */     $$1.addOrReplaceChild("right_front_leg", $$2, PartPose.offset(-3.0F, 12.0F, -5.0F));
/* 41 */     $$1.addOrReplaceChild("left_front_leg", $$2, PartPose.offset(3.0F, 12.0F, -5.0F));
/*    */     
/* 43 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 48 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*    */     
/* 50 */     this.head.y = 6.0F + $$0.getHeadEatPositionScale($$3) * 9.0F;
/* 51 */     this.headXRot = $$0.getHeadEatAngleScale($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 56 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 58 */     this.head.xRot = this.headXRot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SheepFurModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */