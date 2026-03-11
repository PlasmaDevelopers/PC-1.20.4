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
/*    */ public class LeashKnotModel<T extends Entity>
/*    */   extends HierarchicalModel<T>
/*    */ {
/*    */   private static final String KNOT = "knot";
/*    */   private final ModelPart root;
/*    */   private final ModelPart knot;
/*    */   
/*    */   public LeashKnotModel(ModelPart $$0) {
/* 19 */     this.root = $$0;
/* 20 */     this.knot = $$0.getChild("knot");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 24 */     MeshDefinition $$0 = new MeshDefinition();
/* 25 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 27 */     $$1.addOrReplaceChild("knot", 
/* 28 */         CubeListBuilder.create()
/* 29 */         .texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 33 */     return LayerDefinition.create($$0, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 38 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 43 */     this.knot.yRot = $$4 * 0.017453292F;
/* 44 */     this.knot.xRot = $$5 * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\LeashKnotModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */