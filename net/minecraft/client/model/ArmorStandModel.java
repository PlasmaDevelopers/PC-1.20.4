/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.decoration.ArmorStand;
/*     */ 
/*     */ public class ArmorStandModel extends ArmorStandArmorModel {
/*     */   private static final String RIGHT_BODY_STICK = "right_body_stick";
/*     */   private static final String LEFT_BODY_STICK = "left_body_stick";
/*     */   private static final String SHOULDER_STICK = "shoulder_stick";
/*     */   private static final String BASE_PLATE = "base_plate";
/*     */   private final ModelPart rightBodyStick;
/*     */   private final ModelPart leftBodyStick;
/*     */   private final ModelPart shoulderStick;
/*     */   private final ModelPart basePlate;
/*     */   
/*     */   public ArmorStandModel(ModelPart $$0) {
/*  30 */     super($$0);
/*  31 */     this.rightBodyStick = $$0.getChild("right_body_stick");
/*  32 */     this.leftBodyStick = $$0.getChild("left_body_stick");
/*  33 */     this.shoulderStick = $$0.getChild("shoulder_stick");
/*  34 */     this.basePlate = $$0.getChild("base_plate");
/*     */     
/*  36 */     this.hat.visible = false;
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  40 */     MeshDefinition $$0 = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
/*  41 */     PartDefinition $$1 = $$0.getRoot();
/*  42 */     $$1.addOrReplaceChild("head", 
/*  43 */         CubeListBuilder.create()
/*  44 */         .texOffs(0, 0).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F), 
/*  45 */         PartPose.offset(0.0F, 1.0F, 0.0F));
/*     */     
/*  47 */     $$1.addOrReplaceChild("body", 
/*  48 */         CubeListBuilder.create()
/*  49 */         .texOffs(0, 26).addBox(-6.0F, 0.0F, -1.5F, 12.0F, 3.0F, 3.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  52 */     $$1.addOrReplaceChild("right_arm", 
/*  53 */         CubeListBuilder.create()
/*  54 */         .texOffs(24, 0).addBox(-2.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), 
/*  55 */         PartPose.offset(-5.0F, 2.0F, 0.0F));
/*     */     
/*  57 */     $$1.addOrReplaceChild("left_arm", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(32, 16).mirror().addBox(0.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), 
/*  60 */         PartPose.offset(5.0F, 2.0F, 0.0F));
/*     */     
/*  62 */     $$1.addOrReplaceChild("right_leg", 
/*  63 */         CubeListBuilder.create()
/*  64 */         .texOffs(8, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F), 
/*  65 */         PartPose.offset(-1.9F, 12.0F, 0.0F));
/*     */     
/*  67 */     $$1.addOrReplaceChild("left_leg", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(40, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F), 
/*  70 */         PartPose.offset(1.9F, 12.0F, 0.0F));
/*     */     
/*  72 */     $$1.addOrReplaceChild("right_body_stick", 
/*  73 */         CubeListBuilder.create()
/*  74 */         .texOffs(16, 0).addBox(-3.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  77 */     $$1.addOrReplaceChild("left_body_stick", 
/*  78 */         CubeListBuilder.create()
/*  79 */         .texOffs(48, 16).addBox(1.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  82 */     $$1.addOrReplaceChild("shoulder_stick", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(0, 48).addBox(-4.0F, 10.0F, -1.0F, 8.0F, 2.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  87 */     $$1.addOrReplaceChild("base_plate", 
/*  88 */         CubeListBuilder.create()
/*  89 */         .texOffs(0, 32).addBox(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F), 
/*  90 */         PartPose.offset(0.0F, 12.0F, 0.0F));
/*     */ 
/*     */     
/*  93 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(ArmorStand $$0, float $$1, float $$2, float $$3) {
/*  98 */     this.basePlate.xRot = 0.0F;
/*  99 */     this.basePlate.yRot = 0.017453292F * -Mth.rotLerp($$3, $$0.yRotO, $$0.getYRot());
/* 100 */     this.basePlate.zRot = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(ArmorStand $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 105 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/* 107 */     this.leftArm.visible = $$0.isShowArms();
/* 108 */     this.rightArm.visible = $$0.isShowArms();
/* 109 */     this.basePlate.visible = !$$0.isNoBasePlate();
/*     */     
/* 111 */     this.rightBodyStick.xRot = 0.017453292F * $$0.getBodyPose().getX();
/* 112 */     this.rightBodyStick.yRot = 0.017453292F * $$0.getBodyPose().getY();
/* 113 */     this.rightBodyStick.zRot = 0.017453292F * $$0.getBodyPose().getZ();
/*     */     
/* 115 */     this.leftBodyStick.xRot = 0.017453292F * $$0.getBodyPose().getX();
/* 116 */     this.leftBodyStick.yRot = 0.017453292F * $$0.getBodyPose().getY();
/* 117 */     this.leftBodyStick.zRot = 0.017453292F * $$0.getBodyPose().getZ();
/*     */     
/* 119 */     this.shoulderStick.xRot = 0.017453292F * $$0.getBodyPose().getX();
/* 120 */     this.shoulderStick.yRot = 0.017453292F * $$0.getBodyPose().getY();
/* 121 */     this.shoulderStick.zRot = 0.017453292F * $$0.getBodyPose().getZ();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 126 */     return Iterables.concat(super.bodyParts(), (Iterable)ImmutableList.of(this.rightBodyStick, this.leftBodyStick, this.shoulderStick, this.basePlate));
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToHand(HumanoidArm $$0, PoseStack $$1) {
/* 131 */     ModelPart $$2 = getArm($$0);
/* 132 */     boolean $$3 = $$2.visible;
/* 133 */     $$2.visible = true;
/* 134 */     super.translateToHand($$0, $$1);
/* 135 */     $$2.visible = $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ArmorStandModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */