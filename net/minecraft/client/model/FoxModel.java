/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.Fox;
/*     */ 
/*     */ public class FoxModel<T extends Fox>
/*     */   extends AgeableListModel<T>
/*     */ {
/*     */   public final ModelPart head;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart tail;
/*     */   private static final int LEG_SIZE = 6;
/*     */   private static final float HEAD_HEIGHT = 16.5F;
/*     */   private static final float LEG_POS = 17.5F;
/*     */   private float legMotionPos;
/*     */   
/*     */   public FoxModel(ModelPart $$0) {
/*  31 */     super(true, 8.0F, 3.35F);
/*  32 */     this.head = $$0.getChild("head");
/*  33 */     this.body = $$0.getChild("body");
/*  34 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  35 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*  36 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  37 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*  38 */     this.tail = this.body.getChild("tail");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  42 */     MeshDefinition $$0 = new MeshDefinition();
/*  43 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  45 */     PartDefinition $$2 = $$1.addOrReplaceChild("head", 
/*  46 */         CubeListBuilder.create()
/*  47 */         .texOffs(1, 5).addBox(-3.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F), 
/*  48 */         PartPose.offset(-1.0F, 16.5F, -3.0F));
/*     */     
/*  50 */     $$2.addOrReplaceChild("right_ear", 
/*  51 */         CubeListBuilder.create()
/*  52 */         .texOffs(8, 1).addBox(-3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  55 */     $$2.addOrReplaceChild("left_ear", 
/*  56 */         CubeListBuilder.create()
/*  57 */         .texOffs(15, 1).addBox(3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  60 */     $$2.addOrReplaceChild("nose", 
/*  61 */         CubeListBuilder.create()
/*  62 */         .texOffs(6, 18).addBox(-1.0F, 2.01F, -8.0F, 4.0F, 2.0F, 3.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  66 */     PartDefinition $$3 = $$1.addOrReplaceChild("body", 
/*  67 */         CubeListBuilder.create()
/*  68 */         .texOffs(24, 15).addBox(-3.0F, 3.999F, -3.5F, 6.0F, 11.0F, 6.0F), 
/*  69 */         PartPose.offsetAndRotation(0.0F, 16.0F, -6.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  71 */     CubeDeformation $$4 = new CubeDeformation(0.001F);
/*     */     
/*  73 */     CubeListBuilder $$5 = CubeListBuilder.create().texOffs(4, 24).addBox(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, $$4);
/*     */     
/*  75 */     CubeListBuilder $$6 = CubeListBuilder.create().texOffs(13, 24).addBox(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, $$4);
/*     */     
/*  77 */     $$1.addOrReplaceChild("right_hind_leg", $$6, PartPose.offset(-5.0F, 17.5F, 7.0F));
/*     */     
/*  79 */     $$1.addOrReplaceChild("left_hind_leg", $$5, PartPose.offset(-1.0F, 17.5F, 7.0F));
/*  80 */     $$1.addOrReplaceChild("right_front_leg", $$6, PartPose.offset(-5.0F, 17.5F, 0.0F));
/*  81 */     $$1.addOrReplaceChild("left_front_leg", $$5, PartPose.offset(-1.0F, 17.5F, 0.0F));
/*  82 */     $$3.addOrReplaceChild("tail", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(30, 0).addBox(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F), 
/*  85 */         PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, -0.05235988F, 0.0F, 0.0F));
/*     */ 
/*     */     
/*  88 */     return LayerDefinition.create($$0, 48, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/*  93 */     this.body.xRot = 1.5707964F;
/*  94 */     this.tail.xRot = -0.05235988F;
/*     */     
/*  96 */     this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*  97 */     this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/*  98 */     this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/*  99 */     this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*     */     
/* 101 */     this.head.setPos(-1.0F, 16.5F, -3.0F);
/* 102 */     this.head.yRot = 0.0F;
/* 103 */     this.head.zRot = $$0.getHeadRollAngle($$3);
/*     */     
/* 105 */     this.rightHindLeg.visible = true;
/* 106 */     this.leftHindLeg.visible = true;
/* 107 */     this.rightFrontLeg.visible = true;
/* 108 */     this.leftFrontLeg.visible = true;
/* 109 */     this.body.setPos(0.0F, 16.0F, -6.0F);
/* 110 */     this.body.zRot = 0.0F;
/* 111 */     this.rightHindLeg.setPos(-5.0F, 17.5F, 7.0F);
/* 112 */     this.leftHindLeg.setPos(-1.0F, 17.5F, 7.0F);
/*     */     
/* 114 */     if ($$0.isCrouching()) {
/* 115 */       this.body.xRot = 1.6755161F;
/* 116 */       float $$4 = $$0.getCrouchAmount($$3);
/* 117 */       this.body.setPos(0.0F, 16.0F + $$0.getCrouchAmount($$3), -6.0F);
/* 118 */       this.head.setPos(-1.0F, 16.5F + $$4, -3.0F);
/* 119 */       this.head.yRot = 0.0F;
/* 120 */     } else if ($$0.isSleeping()) {
/* 121 */       this.body.zRot = -1.5707964F;
/* 122 */       this.body.setPos(0.0F, 21.0F, -6.0F);
/* 123 */       this.tail.xRot = -2.6179938F;
/* 124 */       if (this.young) {
/* 125 */         this.tail.xRot = -2.1816616F;
/* 126 */         this.body.setPos(0.0F, 21.0F, -2.0F);
/*     */       } 
/* 128 */       this.head.setPos(1.0F, 19.49F, -3.0F);
/* 129 */       this.head.xRot = 0.0F;
/* 130 */       this.head.yRot = -2.0943952F;
/* 131 */       this.head.zRot = 0.0F;
/*     */       
/* 133 */       this.rightHindLeg.visible = false;
/* 134 */       this.leftHindLeg.visible = false;
/* 135 */       this.rightFrontLeg.visible = false;
/* 136 */       this.leftFrontLeg.visible = false;
/* 137 */     } else if ($$0.isSitting()) {
/* 138 */       this.body.xRot = 0.5235988F;
/* 139 */       this.body.setPos(0.0F, 9.0F, -3.0F);
/* 140 */       this.tail.xRot = 0.7853982F;
/* 141 */       this.tail.setPos(-4.0F, 15.0F, -2.0F);
/* 142 */       this.head.setPos(-1.0F, 10.0F, -0.25F);
/* 143 */       this.head.xRot = 0.0F;
/* 144 */       this.head.yRot = 0.0F;
/*     */       
/* 146 */       if (this.young) {
/* 147 */         this.head.setPos(-1.0F, 13.0F, -3.75F);
/*     */       }
/*     */       
/* 150 */       this.rightHindLeg.xRot = -1.3089969F;
/* 151 */       this.rightHindLeg.setPos(-5.0F, 21.5F, 6.75F);
/* 152 */       this.leftHindLeg.xRot = -1.3089969F;
/* 153 */       this.leftHindLeg.setPos(-1.0F, 21.5F, 6.75F);
/*     */       
/* 155 */       this.rightFrontLeg.xRot = -0.2617994F;
/* 156 */       this.leftFrontLeg.xRot = -0.2617994F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/* 162 */     return (Iterable<ModelPart>)ImmutableList.of(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 167 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 172 */     if (!$$0.isSleeping() && !$$0.isFaceplanted() && !$$0.isCrouching()) {
/* 173 */       this.head.xRot = $$5 * 0.017453292F;
/* 174 */       this.head.yRot = $$4 * 0.017453292F;
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if ($$0.isSleeping()) {
/* 179 */       this.head.xRot = 0.0F;
/* 180 */       this.head.yRot = -2.0943952F;
/* 181 */       this.head.zRot = Mth.cos($$3 * 0.027F) / 22.0F;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     if ($$0.isCrouching()) {
/* 186 */       float $$6 = Mth.cos($$3) * 0.01F;
/* 187 */       this.body.yRot = $$6;
/* 188 */       this.rightHindLeg.zRot = $$6;
/* 189 */       this.leftHindLeg.zRot = $$6;
/* 190 */       this.rightFrontLeg.zRot = $$6 / 2.0F;
/* 191 */       this.leftFrontLeg.zRot = $$6 / 2.0F;
/*     */     } 
/*     */ 
/*     */     
/* 195 */     if ($$0.isFaceplanted()) {
/* 196 */       float $$7 = 0.1F;
/* 197 */       this.legMotionPos += 0.67F;
/* 198 */       this.rightHindLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F) * 0.1F;
/* 199 */       this.leftHindLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F + 3.1415927F) * 0.1F;
/* 200 */       this.rightFrontLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F + 3.1415927F) * 0.1F;
/* 201 */       this.leftFrontLeg.xRot = Mth.cos(this.legMotionPos * 0.4662F) * 0.1F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\FoxModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */