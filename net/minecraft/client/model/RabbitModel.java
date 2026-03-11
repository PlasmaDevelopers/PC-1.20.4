/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.Rabbit;
/*     */ 
/*     */ public class RabbitModel<T extends Rabbit>
/*     */   extends EntityModel<T>
/*     */ {
/*     */   private static final float REAR_JUMP_ANGLE = 50.0F;
/*     */   private static final float FRONT_JUMP_ANGLE = -40.0F;
/*     */   private static final String LEFT_HAUNCH = "left_haunch";
/*     */   private static final String RIGHT_HAUNCH = "right_haunch";
/*     */   private final ModelPart leftRearFoot;
/*     */   private final ModelPart rightRearFoot;
/*     */   private final ModelPart leftHaunch;
/*     */   private final ModelPart rightHaunch;
/*     */   private final ModelPart body;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart head;
/*     */   private final ModelPart rightEar;
/*     */   private final ModelPart leftEar;
/*     */   private final ModelPart tail;
/*     */   private final ModelPart nose;
/*     */   private float jumpRotation;
/*     */   private static final float NEW_SCALE = 0.6F;
/*     */   
/*     */   public RabbitModel(ModelPart $$0) {
/*  39 */     this.leftRearFoot = $$0.getChild("left_hind_foot");
/*  40 */     this.rightRearFoot = $$0.getChild("right_hind_foot");
/*  41 */     this.leftHaunch = $$0.getChild("left_haunch");
/*  42 */     this.rightHaunch = $$0.getChild("right_haunch");
/*  43 */     this.body = $$0.getChild("body");
/*  44 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*  45 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  46 */     this.head = $$0.getChild("head");
/*  47 */     this.rightEar = $$0.getChild("right_ear");
/*  48 */     this.leftEar = $$0.getChild("left_ear");
/*  49 */     this.tail = $$0.getChild("tail");
/*  50 */     this.nose = $$0.getChild("nose");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  54 */     MeshDefinition $$0 = new MeshDefinition();
/*  55 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  57 */     $$1.addOrReplaceChild("left_hind_foot", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(26, 24).addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F), 
/*  60 */         PartPose.offset(3.0F, 17.5F, 3.7F));
/*     */     
/*  62 */     $$1.addOrReplaceChild("right_hind_foot", 
/*  63 */         CubeListBuilder.create()
/*  64 */         .texOffs(8, 24).addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F), 
/*  65 */         PartPose.offset(-3.0F, 17.5F, 3.7F));
/*     */     
/*  67 */     $$1.addOrReplaceChild("left_haunch", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(30, 15).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F), 
/*  70 */         PartPose.offsetAndRotation(3.0F, 17.5F, 3.7F, -0.34906584F, 0.0F, 0.0F));
/*     */     
/*  72 */     $$1.addOrReplaceChild("right_haunch", 
/*  73 */         CubeListBuilder.create()
/*  74 */         .texOffs(16, 15).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F), 
/*  75 */         PartPose.offsetAndRotation(-3.0F, 17.5F, 3.7F, -0.34906584F, 0.0F, 0.0F));
/*     */     
/*  77 */     $$1.addOrReplaceChild("body", 
/*  78 */         CubeListBuilder.create()
/*  79 */         .texOffs(0, 0).addBox(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F), 
/*  80 */         PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, -0.34906584F, 0.0F, 0.0F));
/*     */     
/*  82 */     $$1.addOrReplaceChild("left_front_leg", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(8, 15).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), 
/*  85 */         PartPose.offsetAndRotation(3.0F, 17.0F, -1.0F, -0.17453292F, 0.0F, 0.0F));
/*     */     
/*  87 */     $$1.addOrReplaceChild("right_front_leg", 
/*  88 */         CubeListBuilder.create()
/*  89 */         .texOffs(0, 15).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), 
/*  90 */         PartPose.offsetAndRotation(-3.0F, 17.0F, -1.0F, -0.17453292F, 0.0F, 0.0F));
/*     */     
/*  92 */     $$1.addOrReplaceChild("head", 
/*  93 */         CubeListBuilder.create()
/*  94 */         .texOffs(32, 0).addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F), 
/*  95 */         PartPose.offset(0.0F, 16.0F, -1.0F));
/*     */     
/*  97 */     $$1.addOrReplaceChild("right_ear", 
/*  98 */         CubeListBuilder.create()
/*  99 */         .texOffs(52, 0).addBox(-2.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F), 
/* 100 */         PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, 0.0F, -0.2617994F, 0.0F));
/*     */     
/* 102 */     $$1.addOrReplaceChild("left_ear", 
/* 103 */         CubeListBuilder.create()
/* 104 */         .texOffs(58, 0).addBox(0.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F), 
/* 105 */         PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, 0.0F, 0.2617994F, 0.0F));
/*     */     
/* 107 */     $$1.addOrReplaceChild("tail", 
/* 108 */         CubeListBuilder.create()
/* 109 */         .texOffs(52, 6).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F), 
/* 110 */         PartPose.offsetAndRotation(0.0F, 20.0F, 7.0F, -0.3490659F, 0.0F, 0.0F));
/*     */     
/* 112 */     $$1.addOrReplaceChild("nose", 
/* 113 */         CubeListBuilder.create()
/* 114 */         .texOffs(32, 9).addBox(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F), 
/* 115 */         PartPose.offset(0.0F, 16.0F, -1.0F));
/*     */ 
/*     */     
/* 118 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 125 */     if (this.young) {
/* 126 */       float $$8 = 1.5F;
/* 127 */       $$0.pushPose();
/* 128 */       $$0.scale(0.56666666F, 0.56666666F, 0.56666666F);
/* 129 */       $$0.translate(0.0F, 1.375F, 0.125F);
/*     */       
/* 131 */       ImmutableList.of(this.head, this.leftEar, this.rightEar, this.nose).forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/* 132 */       $$0.popPose();
/* 133 */       $$0.pushPose();
/* 134 */       $$0.scale(0.4F, 0.4F, 0.4F);
/* 135 */       $$0.translate(0.0F, 2.25F, 0.0F);
/* 136 */       ImmutableList.of(this.leftRearFoot, this.rightRearFoot, this.leftHaunch, this.rightHaunch, this.body, this.leftFrontLeg, this.rightFrontLeg, this.tail).forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/* 137 */       $$0.popPose();
/*     */     } else {
/* 139 */       $$0.pushPose();
/* 140 */       $$0.scale(0.6F, 0.6F, 0.6F);
/* 141 */       $$0.translate(0.0F, 1.0F, 0.0F);
/*     */       
/* 143 */       ImmutableList.of(this.leftRearFoot, this.rightRearFoot, this.leftHaunch, this.rightHaunch, this.body, this.leftFrontLeg, this.rightFrontLeg, this.head, this.rightEar, this.leftEar, this.tail, this.nose, (Object[])new ModelPart[0]).forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*     */       
/* 145 */       $$0.popPose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 151 */     float $$6 = $$3 - ((Rabbit)$$0).tickCount;
/*     */     
/* 153 */     this.nose.xRot = $$5 * 0.017453292F;
/* 154 */     this.head.xRot = $$5 * 0.017453292F;
/* 155 */     this.rightEar.xRot = $$5 * 0.017453292F;
/* 156 */     this.leftEar.xRot = $$5 * 0.017453292F;
/*     */     
/* 158 */     this.nose.yRot = $$4 * 0.017453292F;
/* 159 */     this.head.yRot = $$4 * 0.017453292F;
/*     */     
/* 161 */     this.nose.yRot -= 0.2617994F;
/* 162 */     this.nose.yRot += 0.2617994F;
/* 163 */     this.jumpRotation = Mth.sin($$0.getJumpCompletion($$6) * 3.1415927F);
/*     */     
/* 165 */     this.leftHaunch.xRot = (this.jumpRotation * 50.0F - 21.0F) * 0.017453292F;
/* 166 */     this.rightHaunch.xRot = (this.jumpRotation * 50.0F - 21.0F) * 0.017453292F;
/* 167 */     this.leftRearFoot.xRot = this.jumpRotation * 50.0F * 0.017453292F;
/* 168 */     this.rightRearFoot.xRot = this.jumpRotation * 50.0F * 0.017453292F;
/* 169 */     this.leftFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * 0.017453292F;
/* 170 */     this.rightFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * 0.017453292F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 175 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*     */     
/* 177 */     this.jumpRotation = Mth.sin($$0.getJumpCompletion($$3) * 3.1415927F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\RabbitModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */