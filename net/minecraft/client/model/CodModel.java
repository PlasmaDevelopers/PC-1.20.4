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
/*    */ public class CodModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private final ModelPart root;
/*    */   private final ModelPart tailFin;
/*    */   
/*    */   public CodModel(ModelPart $$0) {
/* 18 */     this.root = $$0;
/* 19 */     this.tailFin = $$0.getChild("tail_fin");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 23 */     MeshDefinition $$0 = new MeshDefinition();
/* 24 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 26 */     int $$2 = 22;
/* 27 */     $$1.addOrReplaceChild("body", 
/* 28 */         CubeListBuilder.create()
/* 29 */         .texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 7.0F), 
/* 30 */         PartPose.offset(0.0F, 22.0F, 0.0F));
/*    */     
/* 32 */     $$1.addOrReplaceChild("head", 
/* 33 */         CubeListBuilder.create()
/* 34 */         .texOffs(11, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F), 
/* 35 */         PartPose.offset(0.0F, 22.0F, 0.0F));
/*    */     
/* 37 */     $$1.addOrReplaceChild("nose", 
/* 38 */         CubeListBuilder.create()
/* 39 */         .texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F), 
/* 40 */         PartPose.offset(0.0F, 22.0F, -3.0F));
/*    */     
/* 42 */     $$1.addOrReplaceChild("right_fin", 
/* 43 */         CubeListBuilder.create()
/* 44 */         .texOffs(22, 1).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F), 
/* 45 */         PartPose.offsetAndRotation(-1.0F, 23.0F, 0.0F, 0.0F, 0.0F, -0.7853982F));
/*    */     
/* 47 */     $$1.addOrReplaceChild("left_fin", 
/* 48 */         CubeListBuilder.create()
/* 49 */         .texOffs(22, 4).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F), 
/* 50 */         PartPose.offsetAndRotation(1.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0.7853982F));
/*    */     
/* 52 */     $$1.addOrReplaceChild("tail_fin", 
/* 53 */         CubeListBuilder.create()
/* 54 */         .texOffs(22, 3).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F), 
/* 55 */         PartPose.offset(0.0F, 22.0F, 7.0F));
/*    */     
/* 57 */     $$1.addOrReplaceChild("top_fin", 
/* 58 */         CubeListBuilder.create()
/* 59 */         .texOffs(20, -6).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 6.0F), 
/* 60 */         PartPose.offset(0.0F, 20.0F, 0.0F));
/*    */ 
/*    */     
/* 63 */     return LayerDefinition.create($$0, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 68 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 73 */     float $$6 = 1.0F;
/* 74 */     if (!$$0.isInWater()) {
/* 75 */       $$6 = 1.5F;
/*    */     }
/* 77 */     this.tailFin.yRot = -$$6 * 0.45F * Mth.sin(0.6F * $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\CodModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */