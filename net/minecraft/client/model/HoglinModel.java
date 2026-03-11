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
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.monster.hoglin.HoglinBase;
/*     */ 
/*     */ public class HoglinModel<T extends Mob & HoglinBase>
/*     */   extends AgeableListModel<T>
/*     */ {
/*     */   private static final float DEFAULT_HEAD_X_ROT = 0.87266463F;
/*     */   private static final float ATTACK_HEAD_X_ROT_END = -0.34906584F;
/*     */   private final ModelPart head;
/*     */   private final ModelPart rightEar;
/*     */   private final ModelPart leftEar;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart mane;
/*     */   
/*     */   public HoglinModel(ModelPart $$0) {
/*  32 */     super(true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
/*  33 */     this.body = $$0.getChild("body");
/*  34 */     this.mane = this.body.getChild("mane");
/*  35 */     this.head = $$0.getChild("head");
/*  36 */     this.rightEar = this.head.getChild("right_ear");
/*  37 */     this.leftEar = this.head.getChild("left_ear");
/*  38 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  39 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*  40 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  41 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  45 */     MeshDefinition $$0 = new MeshDefinition();
/*  46 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  48 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", 
/*  49 */         CubeListBuilder.create()
/*  50 */         .texOffs(1, 1).addBox(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F), 
/*  51 */         PartPose.offset(0.0F, 7.0F, 0.0F));
/*  52 */     $$2.addOrReplaceChild("mane", 
/*  53 */         CubeListBuilder.create()
/*  54 */         .texOffs(90, 33).addBox(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, new CubeDeformation(0.001F)), 
/*  55 */         PartPose.offset(0.0F, -14.0F, -5.0F));
/*     */     
/*  57 */     PartDefinition $$3 = $$1.addOrReplaceChild("head", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(61, 1).addBox(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F), 
/*  60 */         PartPose.offsetAndRotation(0.0F, 2.0F, -12.0F, 0.87266463F, 0.0F, 0.0F));
/*     */     
/*  62 */     $$3.addOrReplaceChild("right_ear", 
/*  63 */         CubeListBuilder.create()
/*  64 */         .texOffs(1, 1).addBox(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F), 
/*  65 */         PartPose.offsetAndRotation(-6.0F, -2.0F, -3.0F, 0.0F, 0.0F, -0.6981317F));
/*     */     
/*  67 */     $$3.addOrReplaceChild("left_ear", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(1, 6).addBox(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F), 
/*  70 */         PartPose.offsetAndRotation(6.0F, -2.0F, -3.0F, 0.0F, 0.0F, 0.6981317F));
/*     */     
/*  72 */     $$3.addOrReplaceChild("right_horn", 
/*  73 */         CubeListBuilder.create()
/*  74 */         .texOffs(10, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), 
/*  75 */         PartPose.offset(-7.0F, 2.0F, -12.0F));
/*     */     
/*  77 */     $$3.addOrReplaceChild("left_horn", 
/*  78 */         CubeListBuilder.create()
/*  79 */         .texOffs(1, 13).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F), 
/*  80 */         PartPose.offset(7.0F, 2.0F, -12.0F));
/*     */ 
/*     */     
/*  83 */     int $$4 = 14;
/*  84 */     int $$5 = 11;
/*  85 */     $$1.addOrReplaceChild("right_front_leg", 
/*  86 */         CubeListBuilder.create()
/*  87 */         .texOffs(66, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F), 
/*  88 */         PartPose.offset(-4.0F, 10.0F, -8.5F));
/*     */     
/*  90 */     $$1.addOrReplaceChild("left_front_leg", 
/*  91 */         CubeListBuilder.create()
/*  92 */         .texOffs(41, 42).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F), 
/*  93 */         PartPose.offset(4.0F, 10.0F, -8.5F));
/*     */     
/*  95 */     $$1.addOrReplaceChild("right_hind_leg", 
/*  96 */         CubeListBuilder.create()
/*  97 */         .texOffs(21, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), 
/*  98 */         PartPose.offset(-5.0F, 13.0F, 10.0F));
/*     */     
/* 100 */     $$1.addOrReplaceChild("left_hind_leg", 
/* 101 */         CubeListBuilder.create()
/* 102 */         .texOffs(0, 45).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), 
/* 103 */         PartPose.offset(5.0F, 13.0F, 10.0F));
/*     */ 
/*     */     
/* 106 */     return LayerDefinition.create($$0, 128, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/* 111 */     return (Iterable<ModelPart>)ImmutableList.of(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 116 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.rightFrontLeg, this.leftFrontLeg, this.rightHindLeg, this.leftHindLeg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 121 */     this.rightEar.zRot = -0.6981317F - $$2 * Mth.sin($$1);
/* 122 */     this.leftEar.zRot = 0.6981317F + $$2 * Mth.sin($$1);
/* 123 */     this.head.yRot = $$4 * 0.017453292F;
/*     */     
/* 125 */     int $$6 = ((HoglinBase)$$0).getAttackAnimationRemainingTicks();
/*     */ 
/*     */     
/* 128 */     float $$7 = 1.0F - Mth.abs(10 - 2 * $$6) / 10.0F;
/* 129 */     this.head.xRot = Mth.lerp($$7, 0.87266463F, -0.34906584F);
/* 130 */     if ($$0.isBaby()) {
/*     */       
/* 132 */       this.head.y = Mth.lerp($$7, 2.0F, 5.0F);
/*     */       
/* 134 */       this.mane.z = -3.0F;
/*     */     } else {
/*     */       
/* 137 */       this.head.y = 2.0F;
/* 138 */       this.mane.z = -7.0F;
/*     */     } 
/*     */     
/* 141 */     float $$8 = 1.2F;
/* 142 */     this.rightFrontLeg.xRot = Mth.cos($$1) * 1.2F * $$2;
/* 143 */     this.leftFrontLeg.xRot = Mth.cos($$1 + 3.1415927F) * 1.2F * $$2;
/* 144 */     this.rightHindLeg.xRot = this.leftFrontLeg.xRot;
/* 145 */     this.leftHindLeg.xRot = this.rightFrontLeg.xRot;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\HoglinModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */