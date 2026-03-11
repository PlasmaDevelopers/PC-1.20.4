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
/*     */ import net.minecraft.world.entity.animal.Panda;
/*     */ 
/*     */ public class PandaModel<T extends Panda> extends QuadrupedModel<T> {
/*     */   private float sitAmount;
/*     */   private float lieOnBackAmount;
/*     */   private float rollAmount;
/*     */   
/*     */   public PandaModel(ModelPart $$0) {
/*  19 */     super($$0, true, 23.0F, 4.8F, 2.7F, 3.0F, 49);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  23 */     MeshDefinition $$0 = new MeshDefinition();
/*  24 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  26 */     $$1.addOrReplaceChild("head", 
/*  27 */         CubeListBuilder.create()
/*  28 */         .texOffs(0, 6).addBox(-6.5F, -5.0F, -4.0F, 13.0F, 10.0F, 9.0F)
/*  29 */         .texOffs(45, 16).addBox("nose", -3.5F, 0.0F, -6.0F, 7.0F, 5.0F, 2.0F)
/*  30 */         .texOffs(52, 25).addBox("left_ear", 3.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F)
/*  31 */         .texOffs(52, 25).addBox("right_ear", -8.5F, -8.0F, -1.0F, 5.0F, 4.0F, 1.0F), 
/*  32 */         PartPose.offset(0.0F, 11.5F, -17.0F));
/*     */     
/*  34 */     $$1.addOrReplaceChild("body", 
/*  35 */         CubeListBuilder.create()
/*  36 */         .texOffs(0, 25).addBox(-9.5F, -13.0F, -6.5F, 19.0F, 26.0F, 13.0F), 
/*  37 */         PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));
/*     */ 
/*     */     
/*  40 */     int $$2 = 9;
/*  41 */     int $$3 = 6;
/*     */     
/*  43 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F);
/*  44 */     $$1.addOrReplaceChild("right_hind_leg", $$4, PartPose.offset(-5.5F, 15.0F, 9.0F));
/*  45 */     $$1.addOrReplaceChild("left_hind_leg", $$4, PartPose.offset(5.5F, 15.0F, 9.0F));
/*  46 */     $$1.addOrReplaceChild("right_front_leg", $$4, PartPose.offset(-5.5F, 15.0F, -9.0F));
/*  47 */     $$1.addOrReplaceChild("left_front_leg", $$4, PartPose.offset(5.5F, 15.0F, -9.0F));
/*     */     
/*  49 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/*  54 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*     */     
/*  56 */     this.sitAmount = $$0.getSitAmount($$3);
/*  57 */     this.lieOnBackAmount = $$0.getLieOnBackAmount($$3);
/*  58 */     this.rollAmount = $$0.isBaby() ? 0.0F : $$0.getRollAmount($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  63 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  65 */     boolean $$6 = ($$0.getUnhappyCounter() > 0);
/*  66 */     boolean $$7 = $$0.isSneezing();
/*  67 */     int $$8 = $$0.getSneezeCounter();
/*  68 */     boolean $$9 = $$0.isEating();
/*  69 */     boolean $$10 = $$0.isScared();
/*     */     
/*  71 */     if ($$6) {
/*  72 */       this.head.yRot = 0.35F * Mth.sin(0.6F * $$3);
/*  73 */       this.head.zRot = 0.35F * Mth.sin(0.6F * $$3);
/*     */       
/*  75 */       this.rightFrontLeg.xRot = -0.75F * Mth.sin(0.3F * $$3);
/*  76 */       this.leftFrontLeg.xRot = 0.75F * Mth.sin(0.3F * $$3);
/*     */     } else {
/*  78 */       this.head.zRot = 0.0F;
/*     */     } 
/*     */     
/*  81 */     if ($$7) {
/*  82 */       if ($$8 < 15) {
/*  83 */         this.head.xRot = -0.7853982F * $$8 / 14.0F;
/*  84 */       } else if ($$8 < 20) {
/*  85 */         float $$11 = (($$8 - 15) / 5);
/*  86 */         this.head.xRot = -0.7853982F + 0.7853982F * $$11;
/*     */       } 
/*     */     }
/*     */     
/*  90 */     if (this.sitAmount > 0.0F) {
/*  91 */       this.body.xRot = ModelUtils.rotlerpRad(this.body.xRot, 1.7407963F, this.sitAmount);
/*  92 */       this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, 1.5707964F, this.sitAmount);
/*     */ 
/*     */       
/*  95 */       this.rightFrontLeg.zRot = -0.27079642F;
/*  96 */       this.leftFrontLeg.zRot = 0.27079642F;
/*     */ 
/*     */       
/*  99 */       this.rightHindLeg.zRot = 0.5707964F;
/* 100 */       this.leftHindLeg.zRot = -0.5707964F;
/*     */       
/* 102 */       if ($$9) {
/* 103 */         this.head.xRot = 1.5707964F + 0.2F * Mth.sin($$3 * 0.6F);
/*     */         
/* 105 */         this.rightFrontLeg.xRot = -0.4F - 0.2F * Mth.sin($$3 * 0.6F);
/* 106 */         this.leftFrontLeg.xRot = -0.4F - 0.2F * Mth.sin($$3 * 0.6F);
/*     */       } 
/*     */       
/* 109 */       if ($$10) {
/* 110 */         this.head.xRot = 2.1707964F;
/* 111 */         this.rightFrontLeg.xRot = -0.9F;
/* 112 */         this.leftFrontLeg.xRot = -0.9F;
/*     */       } 
/*     */     } else {
/* 115 */       this.rightHindLeg.zRot = 0.0F;
/* 116 */       this.leftHindLeg.zRot = 0.0F;
/* 117 */       this.rightFrontLeg.zRot = 0.0F;
/* 118 */       this.leftFrontLeg.zRot = 0.0F;
/*     */     } 
/*     */     
/* 121 */     if (this.lieOnBackAmount > 0.0F) {
/* 122 */       this.rightHindLeg.xRot = -0.6F * Mth.sin($$3 * 0.15F);
/* 123 */       this.leftHindLeg.xRot = 0.6F * Mth.sin($$3 * 0.15F);
/* 124 */       this.rightFrontLeg.xRot = 0.3F * Mth.sin($$3 * 0.25F);
/* 125 */       this.leftFrontLeg.xRot = -0.3F * Mth.sin($$3 * 0.25F);
/*     */       
/* 127 */       this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, 1.5707964F, this.lieOnBackAmount);
/*     */     } 
/*     */     
/* 130 */     if (this.rollAmount > 0.0F) {
/* 131 */       this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, 2.0561945F, this.rollAmount);
/*     */       
/* 133 */       this.rightHindLeg.xRot = -0.5F * Mth.sin($$3 * 0.5F);
/* 134 */       this.leftHindLeg.xRot = 0.5F * Mth.sin($$3 * 0.5F);
/* 135 */       this.rightFrontLeg.xRot = 0.5F * Mth.sin($$3 * 0.5F);
/* 136 */       this.leftFrontLeg.xRot = -0.5F * Mth.sin($$3 * 0.5F);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PandaModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */