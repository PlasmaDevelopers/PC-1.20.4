/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ public class SilverfishModel<T extends Entity>
/*     */   extends HierarchicalModel<T>
/*     */ {
/*     */   private static final int BODY_COUNT = 7;
/*     */   private final ModelPart root;
/*  18 */   private final ModelPart[] bodyParts = new ModelPart[7];
/*  19 */   private final ModelPart[] bodyLayers = new ModelPart[3];
/*     */   
/*  21 */   private static final int[][] BODY_SIZES = new int[][] { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 }, { 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private static final int[][] BODY_TEXS = new int[][] { { 0, 0 }, { 0, 4 }, { 0, 9 }, { 0, 16 }, { 0, 22 }, { 11, 0 }, { 13, 4 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SilverfishModel(ModelPart $$0) {
/*  41 */     this.root = $$0;
/*  42 */     Arrays.setAll(this.bodyParts, $$1 -> $$0.getChild(getSegmentName($$1)));
/*  43 */     Arrays.setAll(this.bodyLayers, $$1 -> $$0.getChild(getLayerName($$1)));
/*     */   }
/*     */   
/*     */   private static String getLayerName(int $$0) {
/*  47 */     return "layer" + $$0;
/*     */   }
/*     */   
/*     */   private static String getSegmentName(int $$0) {
/*  51 */     return "segment" + $$0;
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  55 */     MeshDefinition $$0 = new MeshDefinition();
/*  56 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  58 */     float[] $$2 = new float[7];
/*  59 */     float $$3 = -3.5F;
/*  60 */     for (int $$4 = 0; $$4 < 7; $$4++) {
/*  61 */       $$1.addOrReplaceChild(getSegmentName($$4), 
/*  62 */           CubeListBuilder.create()
/*  63 */           .texOffs(BODY_TEXS[$$4][0], BODY_TEXS[$$4][1]).addBox(BODY_SIZES[$$4][0] * -0.5F, 0.0F, BODY_SIZES[$$4][2] * -0.5F, BODY_SIZES[$$4][0], BODY_SIZES[$$4][1], BODY_SIZES[$$4][2]), 
/*  64 */           PartPose.offset(0.0F, (24 - BODY_SIZES[$$4][1]), $$3));
/*     */       
/*  66 */       $$2[$$4] = $$3;
/*  67 */       if ($$4 < 6) {
/*  68 */         $$3 += (BODY_SIZES[$$4][2] + BODY_SIZES[$$4 + 1][2]) * 0.5F;
/*     */       }
/*     */     } 
/*     */     
/*  72 */     $$1.addOrReplaceChild(getLayerName(0), 
/*  73 */         CubeListBuilder.create()
/*  74 */         .texOffs(20, 0).addBox(-5.0F, 0.0F, BODY_SIZES[2][2] * -0.5F, 10.0F, 8.0F, BODY_SIZES[2][2]), 
/*  75 */         PartPose.offset(0.0F, 16.0F, $$2[2]));
/*     */     
/*  77 */     $$1.addOrReplaceChild(getLayerName(1), 
/*  78 */         CubeListBuilder.create()
/*  79 */         .texOffs(20, 11).addBox(-3.0F, 0.0F, BODY_SIZES[4][2] * -0.5F, 6.0F, 4.0F, BODY_SIZES[4][2]), 
/*  80 */         PartPose.offset(0.0F, 20.0F, $$2[4]));
/*     */     
/*  82 */     $$1.addOrReplaceChild(getLayerName(2), 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(20, 18).addBox(-3.0F, 0.0F, BODY_SIZES[4][2] * -0.5F, 6.0F, 5.0F, BODY_SIZES[1][2]), 
/*  85 */         PartPose.offset(0.0F, 19.0F, $$2[1]));
/*     */ 
/*     */     
/*  88 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  93 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  98 */     for (int $$6 = 0; $$6 < this.bodyParts.length; $$6++) {
/*  99 */       (this.bodyParts[$$6]).yRot = Mth.cos($$3 * 0.9F + $$6 * 0.15F * 3.1415927F) * 3.1415927F * 0.05F * (1 + Math.abs($$6 - 2));
/* 100 */       (this.bodyParts[$$6]).x = Mth.sin($$3 * 0.9F + $$6 * 0.15F * 3.1415927F) * 3.1415927F * 0.2F * Math.abs($$6 - 2);
/*     */     } 
/*     */     
/* 103 */     (this.bodyLayers[0]).yRot = (this.bodyParts[2]).yRot;
/* 104 */     (this.bodyLayers[1]).yRot = (this.bodyParts[4]).yRot;
/* 105 */     (this.bodyLayers[1]).x = (this.bodyParts[4]).x;
/* 106 */     (this.bodyLayers[2]).yRot = (this.bodyParts[1]).yRot;
/* 107 */     (this.bodyLayers[2]).x = (this.bodyParts[1]).x;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SilverfishModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */