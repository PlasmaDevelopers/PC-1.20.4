/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class CowModel<T extends Entity>
/*    */   extends QuadrupedModel<T>
/*    */ {
/*    */   public CowModel(ModelPart $$0) {
/* 15 */     super($$0, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 19 */     MeshDefinition $$0 = new MeshDefinition();
/* 20 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 22 */     int $$2 = 12;
/* 23 */     $$1.addOrReplaceChild("head", 
/* 24 */         CubeListBuilder.create()
/* 25 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F)
/* 26 */         .texOffs(22, 0).addBox("right_horn", -5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F)
/* 27 */         .texOffs(22, 0).addBox("left_horn", 4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F), 
/* 28 */         PartPose.offset(0.0F, 4.0F, -8.0F));
/*    */     
/* 30 */     $$1.addOrReplaceChild("body", 
/* 31 */         CubeListBuilder.create()
/* 32 */         .texOffs(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F)
/* 33 */         .texOffs(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F), 
/* 34 */         PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
/*    */ 
/*    */ 
/*    */     
/* 38 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
/* 39 */     $$1.addOrReplaceChild("right_hind_leg", $$3, PartPose.offset(-4.0F, 12.0F, 7.0F));
/* 40 */     $$1.addOrReplaceChild("left_hind_leg", $$3, PartPose.offset(4.0F, 12.0F, 7.0F));
/* 41 */     $$1.addOrReplaceChild("right_front_leg", $$3, PartPose.offset(-4.0F, 12.0F, -6.0F));
/* 42 */     $$1.addOrReplaceChild("left_front_leg", $$3, PartPose.offset(4.0F, 12.0F, -6.0F));
/*    */     
/* 44 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */   
/*    */   public ModelPart getHead() {
/* 48 */     return this.head;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\CowModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */