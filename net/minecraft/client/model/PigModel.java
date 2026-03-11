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
/*    */ 
/*    */ public class PigModel<T extends Entity>
/*    */   extends QuadrupedModel<T> {
/*    */   public PigModel(ModelPart $$0) {
/* 15 */     super($$0, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
/* 19 */     MeshDefinition $$1 = QuadrupedModel.createBodyMesh(6, $$0);
/* 20 */     PartDefinition $$2 = $$1.getRoot();
/*    */     
/* 22 */     $$2.addOrReplaceChild("head", CubeListBuilder.create()
/* 23 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, $$0)
/* 24 */         .texOffs(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, $$0), 
/* 25 */         PartPose.offset(0.0F, 12.0F, -6.0F));
/*    */ 
/*    */     
/* 28 */     return LayerDefinition.create($$1, 64, 32);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PigModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */