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
/*    */ public class SalmonModel<T extends Entity>
/*    */   extends HierarchicalModel<T>
/*    */ {
/*    */   private static final String BODY_FRONT = "body_front";
/*    */   private static final String BODY_BACK = "body_back";
/*    */   private final ModelPart root;
/*    */   private final ModelPart bodyBack;
/*    */   
/*    */   public SalmonModel(ModelPart $$0) {
/* 21 */     this.root = $$0;
/* 22 */     this.bodyBack = $$0.getChild("body_back");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 26 */     MeshDefinition $$0 = new MeshDefinition();
/* 27 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 29 */     int $$2 = 20;
/* 30 */     PartDefinition $$3 = $$1.addOrReplaceChild("body_front", 
/* 31 */         CubeListBuilder.create()
/* 32 */         .texOffs(0, 0).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F), 
/* 33 */         PartPose.offset(0.0F, 20.0F, 0.0F));
/*    */     
/* 35 */     PartDefinition $$4 = $$1.addOrReplaceChild("body_back", 
/* 36 */         CubeListBuilder.create()
/* 37 */         .texOffs(0, 13).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F), 
/* 38 */         PartPose.offset(0.0F, 20.0F, 8.0F));
/*    */     
/* 40 */     $$1.addOrReplaceChild("head", 
/* 41 */         CubeListBuilder.create()
/* 42 */         .texOffs(22, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F), 
/* 43 */         PartPose.offset(0.0F, 20.0F, 0.0F));
/*    */     
/* 45 */     $$4.addOrReplaceChild("back_fin", 
/* 46 */         CubeListBuilder.create()
/* 47 */         .texOffs(20, 10).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F), 
/* 48 */         PartPose.offset(0.0F, 0.0F, 8.0F));
/*    */     
/* 50 */     $$3.addOrReplaceChild("top_front_fin", 
/* 51 */         CubeListBuilder.create()
/* 52 */         .texOffs(2, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F), 
/* 53 */         PartPose.offset(0.0F, -4.5F, 5.0F));
/*    */     
/* 55 */     $$4.addOrReplaceChild("top_back_fin", 
/* 56 */         CubeListBuilder.create()
/* 57 */         .texOffs(0, 2).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 4.0F), 
/* 58 */         PartPose.offset(0.0F, -4.5F, -1.0F));
/*    */     
/* 60 */     $$1.addOrReplaceChild("right_fin", 
/* 61 */         CubeListBuilder.create()
/* 62 */         .texOffs(-4, 0).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), 
/* 63 */         PartPose.offsetAndRotation(-1.5F, 21.5F, 0.0F, 0.0F, 0.0F, -0.7853982F));
/*    */     
/* 65 */     $$1.addOrReplaceChild("left_fin", 
/* 66 */         CubeListBuilder.create()
/* 67 */         .texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), 
/* 68 */         PartPose.offsetAndRotation(1.5F, 21.5F, 0.0F, 0.0F, 0.0F, 0.7853982F));
/*    */ 
/*    */     
/* 71 */     return LayerDefinition.create($$0, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 76 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 81 */     float $$6 = 1.0F;
/* 82 */     float $$7 = 1.0F;
/* 83 */     if (!$$0.isInWater()) {
/* 84 */       $$6 = 1.3F;
/* 85 */       $$7 = 1.7F;
/*    */     } 
/* 87 */     this.bodyBack.yRot = -$$6 * 0.25F * Mth.sin($$7 * 0.6F * $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SalmonModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */