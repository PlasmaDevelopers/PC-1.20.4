/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ public class SpiderModel<T extends Entity>
/*     */   extends HierarchicalModel<T>
/*     */ {
/*     */   private static final String BODY_0 = "body0";
/*     */   private static final String BODY_1 = "body1";
/*     */   private static final String RIGHT_MIDDLE_FRONT_LEG = "right_middle_front_leg";
/*     */   private static final String LEFT_MIDDLE_FRONT_LEG = "left_middle_front_leg";
/*     */   private static final String RIGHT_MIDDLE_HIND_LEG = "right_middle_hind_leg";
/*     */   private static final String LEFT_MIDDLE_HIND_LEG = "left_middle_hind_leg";
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart rightMiddleHindLeg;
/*     */   private final ModelPart leftMiddleHindLeg;
/*     */   private final ModelPart rightMiddleFrontLeg;
/*     */   private final ModelPart leftMiddleFrontLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   
/*     */   public SpiderModel(ModelPart $$0) {
/*  33 */     this.root = $$0;
/*  34 */     this.head = $$0.getChild("head");
/*  35 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  36 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*  37 */     this.rightMiddleHindLeg = $$0.getChild("right_middle_hind_leg");
/*  38 */     this.leftMiddleHindLeg = $$0.getChild("left_middle_hind_leg");
/*  39 */     this.rightMiddleFrontLeg = $$0.getChild("right_middle_front_leg");
/*  40 */     this.leftMiddleFrontLeg = $$0.getChild("left_middle_front_leg");
/*  41 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  42 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createSpiderBodyLayer() {
/*  46 */     MeshDefinition $$0 = new MeshDefinition();
/*  47 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  49 */     int $$2 = 15;
/*  50 */     $$1.addOrReplaceChild("head", 
/*  51 */         CubeListBuilder.create()
/*  52 */         .texOffs(32, 4).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F), 
/*  53 */         PartPose.offset(0.0F, 15.0F, -3.0F));
/*     */     
/*  55 */     $$1.addOrReplaceChild("body0", 
/*  56 */         CubeListBuilder.create()
/*  57 */         .texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), 
/*  58 */         PartPose.offset(0.0F, 15.0F, 0.0F));
/*     */     
/*  60 */     $$1.addOrReplaceChild("body1", 
/*  61 */         CubeListBuilder.create()
/*  62 */         .texOffs(0, 12).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F), 
/*  63 */         PartPose.offset(0.0F, 15.0F, 9.0F));
/*     */ 
/*     */     
/*  66 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
/*     */     
/*  68 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
/*     */     
/*  70 */     $$1.addOrReplaceChild("right_hind_leg", $$3, PartPose.offset(-4.0F, 15.0F, 2.0F));
/*  71 */     $$1.addOrReplaceChild("left_hind_leg", $$4, PartPose.offset(4.0F, 15.0F, 2.0F));
/*  72 */     $$1.addOrReplaceChild("right_middle_hind_leg", $$3, PartPose.offset(-4.0F, 15.0F, 1.0F));
/*  73 */     $$1.addOrReplaceChild("left_middle_hind_leg", $$4, PartPose.offset(4.0F, 15.0F, 1.0F));
/*  74 */     $$1.addOrReplaceChild("right_middle_front_leg", $$3, PartPose.offset(-4.0F, 15.0F, 0.0F));
/*  75 */     $$1.addOrReplaceChild("left_middle_front_leg", $$4, PartPose.offset(4.0F, 15.0F, 0.0F));
/*  76 */     $$1.addOrReplaceChild("right_front_leg", $$3, PartPose.offset(-4.0F, 15.0F, -1.0F));
/*  77 */     $$1.addOrReplaceChild("left_front_leg", $$4, PartPose.offset(4.0F, 15.0F, -1.0F));
/*     */     
/*  79 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  84 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  89 */     this.head.yRot = $$4 * 0.017453292F;
/*  90 */     this.head.xRot = $$5 * 0.017453292F;
/*     */     
/*  92 */     float $$6 = 0.7853982F;
/*  93 */     this.rightHindLeg.zRot = -0.7853982F;
/*  94 */     this.leftHindLeg.zRot = 0.7853982F;
/*     */     
/*  96 */     this.rightMiddleHindLeg.zRot = -0.58119464F;
/*  97 */     this.leftMiddleHindLeg.zRot = 0.58119464F;
/*     */     
/*  99 */     this.rightMiddleFrontLeg.zRot = -0.58119464F;
/* 100 */     this.leftMiddleFrontLeg.zRot = 0.58119464F;
/*     */     
/* 102 */     this.rightFrontLeg.zRot = -0.7853982F;
/* 103 */     this.leftFrontLeg.zRot = 0.7853982F;
/*     */     
/* 105 */     float $$7 = -0.0F;
/* 106 */     float $$8 = 0.3926991F;
/* 107 */     this.rightHindLeg.yRot = 0.7853982F;
/* 108 */     this.leftHindLeg.yRot = -0.7853982F;
/* 109 */     this.rightMiddleHindLeg.yRot = 0.3926991F;
/* 110 */     this.leftMiddleHindLeg.yRot = -0.3926991F;
/* 111 */     this.rightMiddleFrontLeg.yRot = -0.3926991F;
/* 112 */     this.leftMiddleFrontLeg.yRot = 0.3926991F;
/* 113 */     this.rightFrontLeg.yRot = -0.7853982F;
/* 114 */     this.leftFrontLeg.yRot = 0.7853982F;
/*     */     
/* 116 */     float $$9 = -(Mth.cos($$1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * $$2;
/* 117 */     float $$10 = -(Mth.cos($$1 * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * $$2;
/* 118 */     float $$11 = -(Mth.cos($$1 * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * $$2;
/* 119 */     float $$12 = -(Mth.cos($$1 * 0.6662F * 2.0F + 4.712389F) * 0.4F) * $$2;
/*     */     
/* 121 */     float $$13 = Math.abs(Mth.sin($$1 * 0.6662F + 0.0F) * 0.4F) * $$2;
/* 122 */     float $$14 = Math.abs(Mth.sin($$1 * 0.6662F + 3.1415927F) * 0.4F) * $$2;
/* 123 */     float $$15 = Math.abs(Mth.sin($$1 * 0.6662F + 1.5707964F) * 0.4F) * $$2;
/* 124 */     float $$16 = Math.abs(Mth.sin($$1 * 0.6662F + 4.712389F) * 0.4F) * $$2;
/*     */     
/* 126 */     this.rightHindLeg.yRot += $$9;
/* 127 */     this.leftHindLeg.yRot += -$$9;
/* 128 */     this.rightMiddleHindLeg.yRot += $$10;
/* 129 */     this.leftMiddleHindLeg.yRot += -$$10;
/* 130 */     this.rightMiddleFrontLeg.yRot += $$11;
/* 131 */     this.leftMiddleFrontLeg.yRot += -$$11;
/* 132 */     this.rightFrontLeg.yRot += $$12;
/* 133 */     this.leftFrontLeg.yRot += -$$12;
/*     */     
/* 135 */     this.rightHindLeg.zRot += $$13;
/* 136 */     this.leftHindLeg.zRot += -$$13;
/* 137 */     this.rightMiddleHindLeg.zRot += $$14;
/* 138 */     this.leftMiddleHindLeg.zRot += -$$14;
/* 139 */     this.rightMiddleFrontLeg.zRot += $$15;
/* 140 */     this.leftMiddleFrontLeg.zRot += -$$15;
/* 141 */     this.rightFrontLeg.zRot += $$16;
/* 142 */     this.leftFrontLeg.zRot += -$$16;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SpiderModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */