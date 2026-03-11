/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.Wolf;
/*     */ 
/*     */ public class WolfModel<T extends Wolf>
/*     */   extends ColorableAgeableListModel<T>
/*     */ {
/*     */   private static final String REAL_HEAD = "real_head";
/*     */   private static final String UPPER_BODY = "upper_body";
/*     */   private static final String REAL_TAIL = "real_tail";
/*     */   private final ModelPart head;
/*     */   private final ModelPart realHead;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart tail;
/*     */   private final ModelPart realTail;
/*     */   private final ModelPart upperBody;
/*     */   private static final int LEG_SIZE = 8;
/*     */   
/*     */   public WolfModel(ModelPart $$0) {
/*  33 */     this.head = $$0.getChild("head");
/*  34 */     this.realHead = this.head.getChild("real_head");
/*  35 */     this.body = $$0.getChild("body");
/*  36 */     this.upperBody = $$0.getChild("upper_body");
/*  37 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  38 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*  39 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  40 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*  41 */     this.tail = $$0.getChild("tail");
/*  42 */     this.realTail = this.tail.getChild("real_tail");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  46 */     MeshDefinition $$0 = new MeshDefinition();
/*  47 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  49 */     float $$2 = 13.5F;
/*     */     
/*  51 */     PartDefinition $$3 = $$1.addOrReplaceChild("head", 
/*  52 */         CubeListBuilder.create(), 
/*  53 */         PartPose.offset(-1.0F, 13.5F, -7.0F));
/*     */     
/*  55 */     $$3.addOrReplaceChild("real_head", 
/*  56 */         CubeListBuilder.create()
/*  57 */         .texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F)
/*  58 */         .texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F)
/*  59 */         .texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F)
/*  60 */         .texOffs(0, 10).addBox(-0.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  63 */     $$1.addOrReplaceChild("body", 
/*  64 */         CubeListBuilder.create()
/*  65 */         .texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), 
/*  66 */         PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  68 */     $$1.addOrReplaceChild("upper_body", 
/*  69 */         CubeListBuilder.create()
/*  70 */         .texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), 
/*  71 */         PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
/*     */ 
/*     */ 
/*     */     
/*  75 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
/*  76 */     $$1.addOrReplaceChild("right_hind_leg", $$4, PartPose.offset(-2.5F, 16.0F, 7.0F));
/*  77 */     $$1.addOrReplaceChild("left_hind_leg", $$4, PartPose.offset(0.5F, 16.0F, 7.0F));
/*  78 */     $$1.addOrReplaceChild("right_front_leg", $$4, PartPose.offset(-2.5F, 16.0F, -4.0F));
/*  79 */     $$1.addOrReplaceChild("left_front_leg", $$4, PartPose.offset(0.5F, 16.0F, -4.0F));
/*     */     
/*  81 */     PartDefinition $$5 = $$1.addOrReplaceChild("tail", 
/*  82 */         CubeListBuilder.create(), 
/*  83 */         PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
/*     */     
/*  85 */     $$5.addOrReplaceChild("real_tail", 
/*  86 */         CubeListBuilder.create()
/*  87 */         .texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  91 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/*  96 */     return (Iterable<ModelPart>)ImmutableList.of(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 101 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.upperBody);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 106 */     if ($$0.isAngry()) {
/* 107 */       this.tail.yRot = 0.0F;
/*     */     } else {
/* 109 */       this.tail.yRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*     */     } 
/*     */     
/* 112 */     if ($$0.isInSittingPose()) {
/* 113 */       this.upperBody.setPos(-1.0F, 16.0F, -3.0F);
/* 114 */       this.upperBody.xRot = 1.2566371F;
/* 115 */       this.upperBody.yRot = 0.0F;
/*     */       
/* 117 */       this.body.setPos(0.0F, 18.0F, 0.0F);
/* 118 */       this.body.xRot = 0.7853982F;
/*     */       
/* 120 */       this.tail.setPos(-1.0F, 21.0F, 6.0F);
/*     */       
/* 122 */       this.rightHindLeg.setPos(-2.5F, 22.7F, 2.0F);
/* 123 */       this.rightHindLeg.xRot = 4.712389F;
/* 124 */       this.leftHindLeg.setPos(0.5F, 22.7F, 2.0F);
/* 125 */       this.leftHindLeg.xRot = 4.712389F;
/*     */       
/* 127 */       this.rightFrontLeg.xRot = 5.811947F;
/* 128 */       this.rightFrontLeg.setPos(-2.49F, 17.0F, -4.0F);
/* 129 */       this.leftFrontLeg.xRot = 5.811947F;
/* 130 */       this.leftFrontLeg.setPos(0.51F, 17.0F, -4.0F);
/*     */     } else {
/* 132 */       this.body.setPos(0.0F, 14.0F, 2.0F);
/* 133 */       this.body.xRot = 1.5707964F;
/*     */       
/* 135 */       this.upperBody.setPos(-1.0F, 14.0F, -3.0F);
/* 136 */       this.upperBody.xRot = this.body.xRot;
/*     */       
/* 138 */       this.tail.setPos(-1.0F, 12.0F, 8.0F);
/*     */       
/* 140 */       this.rightHindLeg.setPos(-2.5F, 16.0F, 7.0F);
/* 141 */       this.leftHindLeg.setPos(0.5F, 16.0F, 7.0F);
/* 142 */       this.rightFrontLeg.setPos(-2.5F, 16.0F, -4.0F);
/* 143 */       this.leftFrontLeg.setPos(0.5F, 16.0F, -4.0F);
/*     */       
/* 145 */       this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/* 146 */       this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/* 147 */       this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/* 148 */       this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*     */     } 
/*     */     
/* 151 */     this.realHead.zRot = $$0.getHeadRollAngle($$3) + $$0.getBodyRollAngle($$3, 0.0F);
/*     */     
/* 153 */     this.upperBody.zRot = $$0.getBodyRollAngle($$3, -0.08F);
/* 154 */     this.body.zRot = $$0.getBodyRollAngle($$3, -0.16F);
/* 155 */     this.realTail.zRot = $$0.getBodyRollAngle($$3, -0.2F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 160 */     this.head.xRot = $$5 * 0.017453292F;
/* 161 */     this.head.yRot = $$4 * 0.017453292F;
/*     */     
/* 163 */     this.tail.xRot = $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\WolfModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */