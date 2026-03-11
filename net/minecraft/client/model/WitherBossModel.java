/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.boss.wither.WitherBoss;
/*     */ 
/*     */ 
/*     */ public class WitherBossModel<T extends WitherBoss>
/*     */   extends HierarchicalModel<T>
/*     */ {
/*     */   private static final String RIBCAGE = "ribcage";
/*     */   private static final String CENTER_HEAD = "center_head";
/*     */   private static final String RIGHT_HEAD = "right_head";
/*     */   private static final String LEFT_HEAD = "left_head";
/*     */   private static final float RIBCAGE_X_ROT_OFFSET = 0.065F;
/*     */   private static final float TAIL_X_ROT_OFFSET = 0.265F;
/*     */   private final ModelPart root;
/*     */   private final ModelPart centerHead;
/*     */   private final ModelPart rightHead;
/*     */   private final ModelPart leftHead;
/*     */   private final ModelPart ribcage;
/*     */   private final ModelPart tail;
/*     */   
/*     */   public WitherBossModel(ModelPart $$0) {
/*  32 */     this.root = $$0;
/*  33 */     this.ribcage = $$0.getChild("ribcage");
/*  34 */     this.tail = $$0.getChild("tail");
/*     */     
/*  36 */     this.centerHead = $$0.getChild("center_head");
/*  37 */     this.rightHead = $$0.getChild("right_head");
/*  38 */     this.leftHead = $$0.getChild("left_head");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
/*  42 */     MeshDefinition $$1 = new MeshDefinition();
/*  43 */     PartDefinition $$2 = $$1.getRoot();
/*     */     
/*  45 */     $$2.addOrReplaceChild("shoulders", 
/*  46 */         CubeListBuilder.create()
/*  47 */         .texOffs(0, 16).addBox(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  51 */     float $$3 = 0.20420352F;
/*     */     
/*  53 */     $$2.addOrReplaceChild("ribcage", 
/*  54 */         CubeListBuilder.create()
/*  55 */         .texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, $$0)
/*  56 */         .texOffs(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, $$0)
/*  57 */         .texOffs(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, $$0)
/*  58 */         .texOffs(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, $$0), 
/*  59 */         PartPose.offsetAndRotation(-2.0F, 6.9F, -0.5F, 0.20420352F, 0.0F, 0.0F));
/*     */     
/*  61 */     $$2.addOrReplaceChild("tail", 
/*  62 */         CubeListBuilder.create()
/*  63 */         .texOffs(12, 22).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, $$0), 
/*  64 */         PartPose.offsetAndRotation(-2.0F, 6.9F + Mth.cos(0.20420352F) * 10.0F, -0.5F + Mth.sin(0.20420352F) * 10.0F, 0.83252203F, 0.0F, 0.0F));
/*     */ 
/*     */     
/*  67 */     $$2.addOrReplaceChild("center_head", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  73 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, $$0);
/*  74 */     $$2.addOrReplaceChild("right_head", $$4, PartPose.offset(-8.0F, 4.0F, 0.0F));
/*  75 */     $$2.addOrReplaceChild("left_head", $$4, PartPose.offset(10.0F, 4.0F, 0.0F));
/*     */     
/*  77 */     return LayerDefinition.create($$1, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  82 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  87 */     float $$6 = Mth.cos($$3 * 0.1F);
/*  88 */     this.ribcage.xRot = (0.065F + 0.05F * $$6) * 3.1415927F;
/*     */     
/*  90 */     this.tail.setPos(-2.0F, 6.9F + Mth.cos(this.ribcage.xRot) * 10.0F, -0.5F + Mth.sin(this.ribcage.xRot) * 10.0F);
/*  91 */     this.tail.xRot = (0.265F + 0.1F * $$6) * 3.1415927F;
/*     */     
/*  93 */     this.centerHead.yRot = $$4 * 0.017453292F;
/*  94 */     this.centerHead.xRot = $$5 * 0.017453292F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/*  99 */     setupHeadRotation($$0, this.rightHead, 0);
/* 100 */     setupHeadRotation($$0, this.leftHead, 1);
/*     */   }
/*     */   
/*     */   private static <T extends WitherBoss> void setupHeadRotation(T $$0, ModelPart $$1, int $$2) {
/* 104 */     $$1.yRot = ($$0.getHeadYRot($$2) - ((WitherBoss)$$0).yBodyRot) * 0.017453292F;
/* 105 */     $$1.xRot = $$0.getHeadXRot($$2) * 0.017453292F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\WitherBossModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */