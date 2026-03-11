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
/*    */ public class SlimeModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private final ModelPart root;
/*    */   
/*    */   public SlimeModel(ModelPart $$0) {
/* 16 */     this.root = $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createOuterBodyLayer() {
/* 20 */     MeshDefinition $$0 = new MeshDefinition();
/* 21 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 23 */     $$1.addOrReplaceChild("cube", 
/* 24 */         CubeListBuilder.create()
/* 25 */         .texOffs(0, 0).addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 29 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createInnerBodyLayer() {
/* 33 */     MeshDefinition $$0 = new MeshDefinition();
/* 34 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 36 */     $$1.addOrReplaceChild("cube", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(0, 16).addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 41 */     $$1.addOrReplaceChild("right_eye", 
/* 42 */         CubeListBuilder.create()
/* 43 */         .texOffs(32, 0).addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 46 */     $$1.addOrReplaceChild("left_eye", 
/* 47 */         CubeListBuilder.create()
/* 48 */         .texOffs(32, 4).addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 51 */     $$1.addOrReplaceChild("mouth", 
/* 52 */         CubeListBuilder.create()
/* 53 */         .texOffs(32, 8).addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 57 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {}
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 66 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SlimeModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */