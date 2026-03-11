/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
/*     */ import net.minecraft.world.entity.monster.piglin.Piglin;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
/*     */ 
/*     */ public class PiglinModel<T extends Mob> extends PlayerModel<T> {
/*     */   public final ModelPart rightEar;
/*     */   private final ModelPart leftEar;
/*     */   private final PartPose bodyDefault;
/*     */   private final PartPose headDefault;
/*     */   private final PartPose leftArmDefault;
/*     */   private final PartPose rightArmDefault;
/*     */   
/*     */   public PiglinModel(ModelPart $$0) {
/*  27 */     super($$0, false);
/*  28 */     this.rightEar = this.head.getChild("right_ear");
/*  29 */     this.leftEar = this.head.getChild("left_ear");
/*     */     
/*  31 */     this.bodyDefault = this.body.storePose();
/*  32 */     this.headDefault = this.head.storePose();
/*  33 */     this.leftArmDefault = this.leftArm.storePose();
/*  34 */     this.rightArmDefault = this.rightArm.storePose();
/*     */   }
/*     */   
/*     */   public static MeshDefinition createMesh(CubeDeformation $$0) {
/*  38 */     MeshDefinition $$1 = PlayerModel.createMesh($$0, false);
/*     */     
/*  40 */     PartDefinition $$2 = $$1.getRoot();
/*  41 */     $$2.addOrReplaceChild("body", 
/*  42 */         CubeListBuilder.create()
/*  43 */         .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */     
/*  46 */     addHead($$0, $$1);
/*  47 */     $$2.addOrReplaceChild("hat", 
/*  48 */         CubeListBuilder.create(), PartPose.ZERO);
/*     */ 
/*     */     
/*  51 */     return $$1;
/*     */   }
/*     */   
/*     */   public static void addHead(CubeDeformation $$0, MeshDefinition $$1) {
/*  55 */     PartDefinition $$2 = $$1.getRoot();
/*  56 */     PartDefinition $$3 = $$2.addOrReplaceChild("head", 
/*  57 */         CubeListBuilder.create()
/*  58 */         .texOffs(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, $$0)
/*  59 */         .texOffs(31, 1).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, $$0)
/*  60 */         .texOffs(2, 4).addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, $$0)
/*  61 */         .texOffs(2, 0).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  65 */     $$3.addOrReplaceChild("left_ear", 
/*  66 */         CubeListBuilder.create()
/*  67 */         .texOffs(51, 6).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, $$0), 
/*  68 */         PartPose.offsetAndRotation(4.5F, -6.0F, 0.0F, 0.0F, 0.0F, -0.5235988F));
/*     */     
/*  70 */     $$3.addOrReplaceChild("right_ear", 
/*  71 */         CubeListBuilder.create()
/*  72 */         .texOffs(39, 6).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, $$0), 
/*  73 */         PartPose.offsetAndRotation(-4.5F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5235988F));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  79 */     this.body.loadPose(this.bodyDefault);
/*  80 */     this.head.loadPose(this.headDefault);
/*  81 */     this.leftArm.loadPose(this.leftArmDefault);
/*  82 */     this.rightArm.loadPose(this.rightArmDefault);
/*     */     
/*  84 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  86 */     float $$6 = 0.5235988F;
/*  87 */     float $$7 = $$3 * 0.1F + $$1 * 0.5F;
/*  88 */     float $$8 = 0.08F + $$2 * 0.4F;
/*  89 */     this.leftEar.zRot = -0.5235988F - Mth.cos($$7 * 1.2F) * $$8;
/*  90 */     this.rightEar.zRot = 0.5235988F + Mth.cos($$7) * $$8;
/*     */     
/*  92 */     if ($$0 instanceof AbstractPiglin) { AbstractPiglin $$9 = (AbstractPiglin)$$0;
/*  93 */       PiglinArmPose $$10 = $$9.getArmPose();
/*     */       
/*  95 */       if ($$10 == PiglinArmPose.DANCING) {
/*  96 */         float $$11 = $$3 / 60.0F;
/*  97 */         this.rightEar.zRot = 0.5235988F + 0.017453292F * Mth.sin($$11 * 30.0F) * 10.0F;
/*  98 */         this.leftEar.zRot = -0.5235988F - 0.017453292F * Mth.cos($$11 * 30.0F) * 10.0F;
/*  99 */         this.head.x = Mth.sin($$11 * 10.0F);
/* 100 */         this.head.y = Mth.sin($$11 * 40.0F) + 0.4F;
/* 101 */         this.rightArm.zRot = 0.017453292F * (70.0F + Mth.cos($$11 * 40.0F) * 10.0F);
/* 102 */         this.rightArm.zRot *= -1.0F;
/*     */         
/* 104 */         this.rightArm.y = Mth.sin($$11 * 40.0F) * 0.5F + 1.5F;
/* 105 */         this.leftArm.y = Mth.sin($$11 * 40.0F) * 0.5F + 1.5F;
/*     */         
/* 107 */         this.body.y = Mth.sin($$11 * 40.0F) * 0.35F;
/* 108 */       } else if ($$10 == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON && this.attackTime == 0.0F) {
/*     */         
/* 110 */         holdWeaponHigh($$0);
/* 111 */       } else if ($$10 == PiglinArmPose.CROSSBOW_HOLD) {
/* 112 */         AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, !$$0.isLeftHanded());
/* 113 */       } else if ($$10 == PiglinArmPose.CROSSBOW_CHARGE) {
/* 114 */         AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, (LivingEntity)$$0, !$$0.isLeftHanded());
/* 115 */       } else if ($$10 == PiglinArmPose.ADMIRING_ITEM) {
/* 116 */         this.head.xRot = 0.5F;
/* 117 */         this.head.yRot = 0.0F;
/* 118 */         if ($$0.isLeftHanded()) {
/* 119 */           this.rightArm.yRot = -0.5F;
/* 120 */           this.rightArm.xRot = -0.9F;
/*     */         } else {
/* 122 */           this.leftArm.yRot = 0.5F;
/* 123 */           this.leftArm.xRot = -0.9F;
/*     */         } 
/*     */       }  }
/* 126 */     else if ($$0.getType() == EntityType.ZOMBIFIED_PIGLIN)
/* 127 */     { AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, $$0.isAggressive(), this.attackTime, $$3); }
/*     */ 
/*     */     
/* 130 */     this.leftPants.copyFrom(this.leftLeg);
/* 131 */     this.rightPants.copyFrom(this.rightLeg);
/* 132 */     this.leftSleeve.copyFrom(this.leftArm);
/* 133 */     this.rightSleeve.copyFrom(this.rightArm);
/* 134 */     this.jacket.copyFrom(this.body);
/* 135 */     this.hat.copyFrom(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupAttackAnimation(T $$0, float $$1) {
/* 140 */     if (this.attackTime > 0.0F && $$0 instanceof Piglin && ((Piglin)$$0).getArmPose() == PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON) {
/* 141 */       AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, $$0, this.attackTime, $$1);
/*     */       return;
/*     */     } 
/* 144 */     super.setupAttackAnimation($$0, $$1);
/*     */   }
/*     */   
/*     */   private void holdWeaponHigh(T $$0) {
/* 148 */     if ($$0.isLeftHanded()) {
/* 149 */       this.leftArm.xRot = -1.8F;
/*     */     } else {
/* 151 */       this.rightArm.xRot = -1.8F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PiglinModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */