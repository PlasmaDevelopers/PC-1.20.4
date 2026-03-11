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
/*     */ public class BlazeModel<T extends Entity>
/*     */   extends HierarchicalModel<T>
/*     */ {
/*     */   private final ModelPart root;
/*     */   private final ModelPart[] upperBodyParts;
/*     */   private final ModelPart head;
/*     */   
/*     */   public BlazeModel(ModelPart $$0) {
/*  21 */     this.root = $$0;
/*  22 */     this.head = $$0.getChild("head");
/*  23 */     this.upperBodyParts = new ModelPart[12];
/*  24 */     Arrays.setAll(this.upperBodyParts, $$1 -> $$0.getChild(getPartName($$1)));
/*     */   }
/*     */   
/*     */   private static String getPartName(int $$0) {
/*  28 */     return "part" + $$0;
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  32 */     MeshDefinition $$0 = new MeshDefinition();
/*  33 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  35 */     $$1.addOrReplaceChild("head", 
/*  36 */         CubeListBuilder.create()
/*  37 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     float $$2 = 0.0F;
/*     */     
/*  44 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
/*     */     
/*  46 */     for (int $$4 = 0; $$4 < 4; $$4++) {
/*  47 */       float $$5 = Mth.cos($$2) * 9.0F;
/*  48 */       float $$6 = -2.0F + Mth.cos(($$4 * 2) * 0.25F);
/*  49 */       float $$7 = Mth.sin($$2) * 9.0F;
/*  50 */       $$1.addOrReplaceChild(getPartName($$4), $$3, PartPose.offset($$5, $$6, $$7));
/*  51 */       $$2 += 1.5707964F;
/*     */     } 
/*     */     
/*  54 */     $$2 = 0.7853982F;
/*  55 */     for (int $$8 = 4; $$8 < 8; $$8++) {
/*  56 */       float $$9 = Mth.cos($$2) * 7.0F;
/*  57 */       float $$10 = 2.0F + Mth.cos(($$8 * 2) * 0.25F);
/*  58 */       float $$11 = Mth.sin($$2) * 7.0F;
/*  59 */       $$1.addOrReplaceChild(getPartName($$8), $$3, PartPose.offset($$9, $$10, $$11));
/*  60 */       $$2 += 1.5707964F;
/*     */     } 
/*     */     
/*  63 */     $$2 = 0.47123894F;
/*  64 */     for (int $$12 = 8; $$12 < 12; $$12++) {
/*  65 */       float $$13 = Mth.cos($$2) * 5.0F;
/*  66 */       float $$14 = 11.0F + Mth.cos($$12 * 1.5F * 0.5F);
/*  67 */       float $$15 = Mth.sin($$2) * 5.0F;
/*  68 */       $$1.addOrReplaceChild(getPartName($$12), $$3, PartPose.offset($$13, $$14, $$15));
/*  69 */       $$2 += 1.5707964F;
/*     */     } 
/*     */     
/*  72 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  77 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  82 */     float $$6 = $$3 * 3.1415927F * -0.1F;
/*  83 */     for (int $$7 = 0; $$7 < 4; $$7++) {
/*  84 */       (this.upperBodyParts[$$7]).y = -2.0F + Mth.cos((($$7 * 2) + $$3) * 0.25F);
/*  85 */       (this.upperBodyParts[$$7]).x = Mth.cos($$6) * 9.0F;
/*  86 */       (this.upperBodyParts[$$7]).z = Mth.sin($$6) * 9.0F;
/*  87 */       $$6 += 1.5707964F;
/*     */     } 
/*     */     
/*  90 */     $$6 = 0.7853982F + $$3 * 3.1415927F * 0.03F;
/*  91 */     for (int $$8 = 4; $$8 < 8; $$8++) {
/*  92 */       (this.upperBodyParts[$$8]).y = 2.0F + Mth.cos((($$8 * 2) + $$3) * 0.25F);
/*  93 */       (this.upperBodyParts[$$8]).x = Mth.cos($$6) * 7.0F;
/*  94 */       (this.upperBodyParts[$$8]).z = Mth.sin($$6) * 7.0F;
/*  95 */       $$6 += 1.5707964F;
/*     */     } 
/*     */     
/*  98 */     $$6 = 0.47123894F + $$3 * 3.1415927F * -0.05F;
/*  99 */     for (int $$9 = 8; $$9 < 12; $$9++) {
/* 100 */       (this.upperBodyParts[$$9]).y = 11.0F + Mth.cos(($$9 * 1.5F + $$3) * 0.5F);
/* 101 */       (this.upperBodyParts[$$9]).x = Mth.cos($$6) * 5.0F;
/* 102 */       (this.upperBodyParts[$$9]).z = Mth.sin($$6) * 5.0F;
/* 103 */       $$6 += 1.5707964F;
/*     */     } 
/*     */     
/* 106 */     this.head.yRot = $$4 * 0.017453292F;
/* 107 */     this.head.xRot = $$5 * 0.017453292F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\BlazeModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */