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
/*    */ public class SheepModel<T extends Sheep>
/*    */   extends QuadrupedModel<T> {
/*    */   private float headXRot;
/*    */   
/*    */   public SheepModel(ModelPart $$0) {
/* 18 */     super($$0, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 22 */     MeshDefinition $$0 = QuadrupedModel.createBodyMesh(12, CubeDeformation.NONE);
/* 23 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 25 */     $$1.addOrReplaceChild("head", 
/* 26 */         CubeListBuilder.create()
/* 27 */         .texOffs(0, 0).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F), 
/* 28 */         PartPose.offset(0.0F, 6.0F, -8.0F));
/*    */     
/* 30 */     $$1.addOrReplaceChild("body", 
/* 31 */         CubeListBuilder.create()
/* 32 */         .texOffs(28, 8).addBox(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F), 
/* 33 */         PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
/*    */ 
/*    */     
/* 36 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 41 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*    */     
/* 43 */     this.head.y = 6.0F + $$0.getHeadEatPositionScale($$3) * 9.0F;
/* 44 */     this.headXRot = $$0.getHeadEatAngleScale($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 49 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 51 */     this.head.xRot = this.headXRot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SheepModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */