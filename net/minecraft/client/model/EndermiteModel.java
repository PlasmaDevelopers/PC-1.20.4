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
/*    */ public class EndermiteModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private static final int BODY_COUNT = 4;
/* 15 */   private static final int[][] BODY_SIZES = new int[][] { { 4, 3, 2 }, { 6, 4, 5 }, { 3, 3, 1 }, { 1, 2, 1 } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   private static final int[][] BODY_TEXS = new int[][] { { 0, 0 }, { 0, 5 }, { 0, 14 }, { 0, 18 } };
/*    */ 
/*    */   
/*    */   private final ModelPart root;
/*    */ 
/*    */   
/*    */   private final ModelPart[] bodyParts;
/*    */ 
/*    */ 
/*    */   
/*    */   public EndermiteModel(ModelPart $$0) {
/* 33 */     this.root = $$0;
/* 34 */     this.bodyParts = new ModelPart[4];
/* 35 */     for (int $$1 = 0; $$1 < 4; $$1++) {
/* 36 */       this.bodyParts[$$1] = $$0.getChild(createSegmentName($$1));
/*    */     }
/*    */   }
/*    */   
/*    */   private static String createSegmentName(int $$0) {
/* 41 */     return "segment" + $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 45 */     MeshDefinition $$0 = new MeshDefinition();
/* 46 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 48 */     float $$2 = -3.5F;
/* 49 */     for (int $$3 = 0; $$3 < 4; $$3++) {
/* 50 */       $$1.addOrReplaceChild(createSegmentName($$3), 
/* 51 */           CubeListBuilder.create()
/* 52 */           .texOffs(BODY_TEXS[$$3][0], BODY_TEXS[$$3][1]).addBox(BODY_SIZES[$$3][0] * -0.5F, 0.0F, BODY_SIZES[$$3][2] * -0.5F, BODY_SIZES[$$3][0], BODY_SIZES[$$3][1], BODY_SIZES[$$3][2]), 
/* 53 */           PartPose.offset(0.0F, (24 - BODY_SIZES[$$3][1]), $$2));
/*    */       
/* 55 */       if ($$3 < 3) {
/* 56 */         $$2 += (BODY_SIZES[$$3][2] + BODY_SIZES[$$3 + 1][2]) * 0.5F;
/*    */       }
/*    */     } 
/*    */     
/* 60 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 65 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 70 */     for (int $$6 = 0; $$6 < this.bodyParts.length; $$6++) {
/* 71 */       (this.bodyParts[$$6]).yRot = Mth.cos($$3 * 0.9F + $$6 * 0.15F * 3.1415927F) * 3.1415927F * 0.01F * (1 + Math.abs($$6 - 2));
/* 72 */       (this.bodyParts[$$6]).x = Mth.sin($$3 * 0.9F + $$6 * 0.15F * 3.1415927F) * 3.1415927F * 0.1F * Math.abs($$6 - 2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\EndermiteModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */