/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class PufferfishSmallModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private final ModelPart root;
/*    */   private final ModelPart leftFin;
/*    */   private final ModelPart rightFin;
/*    */   
/*    */   public PufferfishSmallModel(ModelPart $$0) {
/* 19 */     this.root = $$0;
/* 20 */     this.leftFin = $$0.getChild("left_fin");
/* 21 */     this.rightFin = $$0.getChild("right_fin");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 25 */     MeshDefinition $$0 = new MeshDefinition();
/* 26 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 28 */     int $$2 = 23;
/* 29 */     $$1.addOrReplaceChild("body", 
/* 30 */         CubeListBuilder.create()
/* 31 */         .texOffs(0, 27).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F), 
/* 32 */         PartPose.offset(0.0F, 23.0F, 0.0F));
/*    */     
/* 34 */     $$1.addOrReplaceChild("right_eye", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(24, 6).addBox(-1.5F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F), 
/* 37 */         PartPose.offset(0.0F, 20.0F, 0.0F));
/*    */     
/* 39 */     $$1.addOrReplaceChild("left_eye", 
/* 40 */         CubeListBuilder.create()
/* 41 */         .texOffs(28, 6).addBox(0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F), 
/* 42 */         PartPose.offset(0.0F, 20.0F, 0.0F));
/*    */     
/* 44 */     $$1.addOrReplaceChild("back_fin", 
/* 45 */         CubeListBuilder.create()
/* 46 */         .texOffs(-3, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F), 
/* 47 */         PartPose.offset(0.0F, 22.0F, 1.5F));
/*    */     
/* 49 */     $$1.addOrReplaceChild("right_fin", 
/* 50 */         CubeListBuilder.create()
/* 51 */         .texOffs(25, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F), 
/* 52 */         PartPose.offset(-1.5F, 22.0F, -1.5F));
/*    */     
/* 54 */     $$1.addOrReplaceChild("left_fin", 
/* 55 */         CubeListBuilder.create()
/* 56 */         .texOffs(25, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F), 
/* 57 */         PartPose.offset(1.5F, 22.0F, -1.5F));
/*    */ 
/*    */     
/* 60 */     return LayerDefinition.create($$0, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 65 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 70 */     this.rightFin.zRot = -0.2F + 0.4F * Mth.sin($$3 * 0.2F);
/* 71 */     this.leftFin.zRot = 0.2F - 0.4F * Mth.sin($$3 * 0.2F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PufferfishSmallModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */