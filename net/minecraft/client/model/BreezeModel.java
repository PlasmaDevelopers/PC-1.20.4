/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.animation.definitions.BreezeAnimation;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*     */ 
/*     */ 
/*     */ public class BreezeModel<T extends Breeze>
/*     */   extends HierarchicalModel<T>
/*     */ {
/*     */   private static final float WIND_TOP_SPEED = 0.6F;
/*     */   private static final float WIND_MIDDLE_SPEED = 0.8F;
/*     */   private static final float WIND_BOTTOM_SPEED = 1.0F;
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart windBody;
/*     */   private final ModelPart windTop;
/*     */   private final ModelPart windMid;
/*     */   private final ModelPart windBottom;
/*     */   private final ModelPart rods;
/*     */   
/*     */   public BreezeModel(ModelPart $$0) {
/*  32 */     super(RenderType::entityTranslucent);
/*  33 */     this.root = $$0;
/*  34 */     this.windBody = $$0.getChild("wind_body");
/*  35 */     this.windBottom = this.windBody.getChild("wind_bottom");
/*  36 */     this.windMid = this.windBottom.getChild("wind_mid");
/*  37 */     this.windTop = this.windMid.getChild("wind_top");
/*  38 */     this.head = $$0.getChild("body").getChild("head");
/*  39 */     this.rods = $$0.getChild("body").getChild("rods");
/*     */   }
/*     */ 
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  44 */     MeshDefinition $$0 = new MeshDefinition();
/*  45 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  47 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/*  49 */     PartDefinition $$3 = $$2.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));
/*  50 */     $$3.addOrReplaceChild("rod_1", CubeListBuilder.create()
/*  51 */         .texOffs(0, 17).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5981F, -3.0F, 1.5F, -2.7489F, -1.0472F, 3.1416F));
/*  52 */     $$3.addOrReplaceChild("rod_2", CubeListBuilder.create()
/*  53 */         .texOffs(0, 17).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5981F, -3.0F, 1.5F, -2.7489F, 1.0472F, 3.1416F));
/*  54 */     $$3.addOrReplaceChild("rod_3", CubeListBuilder.create()
/*  55 */         .texOffs(0, 17).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0.3927F, 0.0F, 0.0F));
/*     */     
/*  57 */     PartDefinition $$4 = $$2.addOrReplaceChild("head", CubeListBuilder.create()
/*  58 */         .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));
/*     */     
/*  60 */     PartDefinition $$5 = $$1.addOrReplaceChild("wind_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/*  61 */     PartDefinition $$6 = $$5.addOrReplaceChild("wind_bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
/*  62 */     PartDefinition $$7 = $$6.addOrReplaceChild("wind_mid", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));
/*  63 */     $$7.addOrReplaceChild("wind_top", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));
/*     */     
/*  65 */     return LayerDefinition.create($$0, 32, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public static LayerDefinition createEyesLayer() {
/*  70 */     MeshDefinition $$0 = new MeshDefinition();
/*  71 */     PartDefinition $$1 = $$0.getRoot();
/*     */ 
/*     */     
/*  74 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/*  76 */     PartDefinition $$3 = $$2.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));
/*  77 */     PartDefinition $$4 = $$2.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));
/*     */     
/*  79 */     $$4.addOrReplaceChild("eyes", CubeListBuilder.create()
/*  80 */         .texOffs(4, 24).addBox(-5.0F, -5.0F, -4.2F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
/*  81 */         .texOffs(7, 16).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/*  83 */     PartDefinition $$5 = $$1.addOrReplaceChild("wind_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/*  84 */     PartDefinition $$6 = $$5.addOrReplaceChild("wind_bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
/*  85 */     PartDefinition $$7 = $$6.addOrReplaceChild("wind_mid", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));
/*  86 */     $$7.addOrReplaceChild("wind_top", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));
/*     */     
/*  88 */     return LayerDefinition.create($$0, 32, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public static LayerDefinition createWindBodyLayer() {
/*  93 */     MeshDefinition $$0 = new MeshDefinition();
/*  94 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  96 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/*  98 */     PartDefinition $$3 = $$2.addOrReplaceChild("rods", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));
/*     */     
/* 100 */     $$2.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));
/*     */     
/* 102 */     PartDefinition $$4 = $$1.addOrReplaceChild("wind_body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/* 103 */     PartDefinition $$5 = $$4.addOrReplaceChild("wind_bottom", CubeListBuilder.create()
/* 104 */         .texOffs(1, 83).addBox(-2.5F, -7.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
/*     */     
/* 106 */     PartDefinition $$6 = $$5.addOrReplaceChild("wind_mid", CubeListBuilder.create()
/* 107 */         .texOffs(74, 28).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
/* 108 */         .texOffs(78, 32).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
/* 109 */         .texOffs(49, 71).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));
/*     */     
/* 111 */     $$6.addOrReplaceChild("wind_top", CubeListBuilder.create()
/* 112 */         .texOffs(0, 0).addBox(-9.0F, -8.0F, -9.0F, 18.0F, 8.0F, 18.0F, new CubeDeformation(0.0F))
/* 113 */         .texOffs(6, 6).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
/* 114 */         .texOffs(105, 57).addBox(-2.5F, -8.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));
/*     */     
/* 116 */     return LayerDefinition.create($$0, 128, 128);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 121 */     root().getAllParts().forEach(ModelPart::resetPose);
/*     */     
/* 123 */     float $$6 = $$3 * 3.1415927F * -0.1F;
/*     */     
/* 125 */     this.windTop.x = Mth.cos($$6) * 1.0F * 0.6F;
/* 126 */     this.windTop.z = Mth.sin($$6) * 1.0F * 0.6F;
/*     */     
/* 128 */     this.windMid.x = Mth.sin($$6) * 0.5F * 0.8F;
/* 129 */     this.windMid.z = Mth.cos($$6) * 0.8F;
/*     */     
/* 131 */     this.windBottom.x = Mth.cos($$6) * -0.25F * 1.0F;
/* 132 */     this.windBottom.z = Mth.sin($$6) * -0.25F * 1.0F;
/*     */     
/* 134 */     this.head.y = 4.0F + Mth.cos($$6) / 4.0F;
/* 135 */     this.rods.yRot = $$3 * 3.1415927F * 0.1F;
/*     */     
/* 137 */     animate(((Breeze)$$0).shoot, BreezeAnimation.SHOOT, $$3);
/* 138 */     animate(((Breeze)$$0).slide, BreezeAnimation.SLIDE, $$3);
/* 139 */     animate(((Breeze)$$0).longJump, BreezeAnimation.JUMP, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 144 */     return this.root;
/*     */   }
/*     */   
/*     */   public ModelPart windTop() {
/* 148 */     return this.windTop;
/*     */   }
/*     */   
/*     */   public ModelPart windMiddle() {
/* 152 */     return this.windMid;
/*     */   }
/*     */   
/*     */   public ModelPart windBottom() {
/* 156 */     return this.windBottom;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\BreezeModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */