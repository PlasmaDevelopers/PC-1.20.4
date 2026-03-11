/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class BookModel
/*     */   extends Model
/*     */ {
/*     */   private static final String LEFT_PAGES = "left_pages";
/*     */   private static final String RIGHT_PAGES = "right_pages";
/*     */   private static final String FLIP_PAGE_1 = "flip_page1";
/*     */   private static final String FLIP_PAGE_2 = "flip_page2";
/*     */   private final ModelPart root;
/*     */   private final ModelPart leftLid;
/*     */   private final ModelPart rightLid;
/*     */   private final ModelPart leftPages;
/*     */   private final ModelPart rightPages;
/*     */   private final ModelPart flipPage1;
/*     */   private final ModelPart flipPage2;
/*     */   
/*     */   public BookModel(ModelPart $$0) {
/*  30 */     super(RenderType::entitySolid);
/*  31 */     this.root = $$0;
/*  32 */     this.leftLid = $$0.getChild("left_lid");
/*  33 */     this.rightLid = $$0.getChild("right_lid");
/*  34 */     this.leftPages = $$0.getChild("left_pages");
/*  35 */     this.rightPages = $$0.getChild("right_pages");
/*  36 */     this.flipPage1 = $$0.getChild("flip_page1");
/*  37 */     this.flipPage2 = $$0.getChild("flip_page2");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  41 */     MeshDefinition $$0 = new MeshDefinition();
/*  42 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  44 */     $$1.addOrReplaceChild("left_lid", 
/*  45 */         CubeListBuilder.create()
/*  46 */         .texOffs(0, 0).addBox(-6.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), 
/*  47 */         PartPose.offset(0.0F, 0.0F, -1.0F));
/*     */     
/*  49 */     $$1.addOrReplaceChild("right_lid", 
/*  50 */         CubeListBuilder.create()
/*  51 */         .texOffs(16, 0).addBox(0.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), 
/*  52 */         PartPose.offset(0.0F, 0.0F, 1.0F));
/*     */     
/*  54 */     $$1.addOrReplaceChild("seam", 
/*  55 */         CubeListBuilder.create()
/*  56 */         .texOffs(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.005F), 
/*  57 */         PartPose.rotation(0.0F, 1.5707964F, 0.0F));
/*     */     
/*  59 */     $$1.addOrReplaceChild("left_pages", 
/*  60 */         CubeListBuilder.create()
/*  61 */         .texOffs(0, 10).addBox(0.0F, -4.0F, -0.99F, 5.0F, 8.0F, 1.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  64 */     $$1.addOrReplaceChild("right_pages", 
/*  65 */         CubeListBuilder.create()
/*  66 */         .texOffs(12, 10).addBox(0.0F, -4.0F, -0.01F, 5.0F, 8.0F, 1.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  70 */     CubeListBuilder $$2 = CubeListBuilder.create().texOffs(24, 10).addBox(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
/*  71 */     $$1.addOrReplaceChild("flip_page1", $$2, PartPose.ZERO);
/*  72 */     $$1.addOrReplaceChild("flip_page2", $$2, PartPose.ZERO);
/*     */     
/*  74 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/*  79 */     render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   public void render(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/*  83 */     this.root.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   public void setupAnim(float $$0, float $$1, float $$2, float $$3) {
/*  87 */     float $$4 = (Mth.sin($$0 * 0.02F) * 0.1F + 1.25F) * $$3;
/*     */     
/*  89 */     this.leftLid.yRot = 3.1415927F + $$4;
/*  90 */     this.rightLid.yRot = -$$4;
/*  91 */     this.leftPages.yRot = $$4;
/*  92 */     this.rightPages.yRot = -$$4;
/*     */     
/*  94 */     this.flipPage1.yRot = $$4 - $$4 * 2.0F * $$1;
/*  95 */     this.flipPage2.yRot = $$4 - $$4 * 2.0F * $$2;
/*     */     
/*  97 */     this.leftPages.x = Mth.sin($$4);
/*  98 */     this.rightPages.x = Mth.sin($$4);
/*  99 */     this.flipPage1.x = Mth.sin($$4);
/* 100 */     this.flipPage2.x = Mth.sin($$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\BookModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */