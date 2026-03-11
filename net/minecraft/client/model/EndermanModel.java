/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ 
/*     */ public class EndermanModel<T extends LivingEntity> extends HumanoidModel<T> {
/*     */   public boolean carrying;
/*     */   public boolean creepy;
/*     */   
/*     */   public EndermanModel(ModelPart $$0) {
/*  18 */     super($$0);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  22 */     float $$0 = -14.0F;
/*  23 */     MeshDefinition $$1 = HumanoidModel.createMesh(CubeDeformation.NONE, -14.0F);
/*  24 */     PartDefinition $$2 = $$1.getRoot();
/*     */     
/*  26 */     PartPose $$3 = PartPose.offset(0.0F, -13.0F, 0.0F);
/*  27 */     $$2.addOrReplaceChild("hat", 
/*  28 */         CubeListBuilder.create()
/*  29 */         .texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)), $$3);
/*     */ 
/*     */     
/*  32 */     $$2.addOrReplaceChild("head", 
/*  33 */         CubeListBuilder.create()
/*  34 */         .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), $$3);
/*     */ 
/*     */     
/*  37 */     $$2.addOrReplaceChild("body", 
/*  38 */         CubeListBuilder.create()
/*  39 */         .texOffs(32, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), 
/*  40 */         PartPose.offset(0.0F, -14.0F, 0.0F));
/*     */     
/*  42 */     $$2.addOrReplaceChild("right_arm", 
/*  43 */         CubeListBuilder.create()
/*  44 */         .texOffs(56, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F), 
/*  45 */         PartPose.offset(-5.0F, -12.0F, 0.0F));
/*     */     
/*  47 */     $$2.addOrReplaceChild("left_arm", 
/*  48 */         CubeListBuilder.create()
/*  49 */         .texOffs(56, 0).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F), 
/*  50 */         PartPose.offset(5.0F, -12.0F, 0.0F));
/*     */     
/*  52 */     $$2.addOrReplaceChild("right_leg", 
/*  53 */         CubeListBuilder.create()
/*  54 */         .texOffs(56, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F), 
/*  55 */         PartPose.offset(-2.0F, -5.0F, 0.0F));
/*     */     
/*  57 */     $$2.addOrReplaceChild("left_leg", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(56, 0).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F), 
/*  60 */         PartPose.offset(2.0F, -5.0F, 0.0F));
/*     */ 
/*     */     
/*  63 */     return LayerDefinition.create($$1, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  68 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  70 */     this.head.visible = true;
/*     */     
/*  72 */     int $$6 = -14;
/*  73 */     this.body.xRot = 0.0F;
/*  74 */     this.body.y = -14.0F;
/*  75 */     this.body.z = -0.0F;
/*     */     
/*  77 */     this.rightLeg.xRot -= 0.0F;
/*  78 */     this.leftLeg.xRot -= 0.0F;
/*     */     
/*  80 */     this.rightArm.xRot *= 0.5F;
/*  81 */     this.leftArm.xRot *= 0.5F;
/*  82 */     this.rightLeg.xRot *= 0.5F;
/*  83 */     this.leftLeg.xRot *= 0.5F;
/*     */     
/*  85 */     float $$7 = 0.4F;
/*  86 */     if (this.rightArm.xRot > 0.4F) {
/*  87 */       this.rightArm.xRot = 0.4F;
/*     */     }
/*  89 */     if (this.leftArm.xRot > 0.4F) {
/*  90 */       this.leftArm.xRot = 0.4F;
/*     */     }
/*  92 */     if (this.rightArm.xRot < -0.4F) {
/*  93 */       this.rightArm.xRot = -0.4F;
/*     */     }
/*  95 */     if (this.leftArm.xRot < -0.4F) {
/*  96 */       this.leftArm.xRot = -0.4F;
/*     */     }
/*  98 */     if (this.rightLeg.xRot > 0.4F) {
/*  99 */       this.rightLeg.xRot = 0.4F;
/*     */     }
/* 101 */     if (this.leftLeg.xRot > 0.4F) {
/* 102 */       this.leftLeg.xRot = 0.4F;
/*     */     }
/* 104 */     if (this.rightLeg.xRot < -0.4F) {
/* 105 */       this.rightLeg.xRot = -0.4F;
/*     */     }
/* 107 */     if (this.leftLeg.xRot < -0.4F) {
/* 108 */       this.leftLeg.xRot = -0.4F;
/*     */     }
/*     */     
/* 111 */     if (this.carrying) {
/* 112 */       this.rightArm.xRot = -0.5F;
/* 113 */       this.leftArm.xRot = -0.5F;
/* 114 */       this.rightArm.zRot = 0.05F;
/* 115 */       this.leftArm.zRot = -0.05F;
/*     */     } 
/*     */     
/* 118 */     this.rightLeg.z = 0.0F;
/* 119 */     this.leftLeg.z = 0.0F;
/*     */     
/* 121 */     this.rightLeg.y = -5.0F;
/* 122 */     this.leftLeg.y = -5.0F;
/*     */     
/* 124 */     this.head.z = -0.0F;
/* 125 */     this.head.y = -13.0F;
/*     */     
/* 127 */     this.hat.x = this.head.x;
/* 128 */     this.hat.y = this.head.y;
/* 129 */     this.hat.z = this.head.z;
/* 130 */     this.hat.xRot = this.head.xRot;
/* 131 */     this.hat.yRot = this.head.yRot;
/* 132 */     this.hat.zRot = this.head.zRot;
/*     */     
/* 134 */     if (this.creepy) {
/* 135 */       float $$8 = 1.0F;
/* 136 */       this.head.y -= 5.0F;
/*     */     } 
/*     */     
/* 139 */     int $$9 = -14;
/* 140 */     this.rightArm.setPos(-5.0F, -12.0F, 0.0F);
/* 141 */     this.leftArm.setPos(5.0F, -12.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\EndermanModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */