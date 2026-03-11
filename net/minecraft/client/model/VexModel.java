/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
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
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.monster.Vex;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class VexModel extends HierarchicalModel<Vex> implements ArmedModel {
/*     */   private final ModelPart root;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightArm;
/*     */   private final ModelPart leftArm;
/*     */   private final ModelPart rightWing;
/*     */   private final ModelPart leftWing;
/*     */   private final ModelPart head;
/*     */   
/*     */   public VexModel(ModelPart $$0) {
/*  28 */     super(RenderType::entityTranslucent);
/*  29 */     this.root = $$0.getChild("root");
/*  30 */     this.body = this.root.getChild("body");
/*  31 */     this.rightArm = this.body.getChild("right_arm");
/*  32 */     this.leftArm = this.body.getChild("left_arm");
/*  33 */     this.rightWing = this.body.getChild("right_wing");
/*  34 */     this.leftWing = this.body.getChild("left_wing");
/*  35 */     this.head = this.root.getChild("head");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  39 */     MeshDefinition $$0 = new MeshDefinition();
/*  40 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  42 */     PartDefinition $$2 = $$1.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 0.0F));
/*  43 */     $$2.addOrReplaceChild("head", CubeListBuilder.create()
/*  44 */         .texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), 
/*  45 */         PartPose.offset(0.0F, 20.0F, 0.0F));
/*     */     
/*  47 */     PartDefinition $$3 = $$2.addOrReplaceChild("body", CubeListBuilder.create()
/*  48 */         .texOffs(0, 10).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
/*  49 */         .texOffs(0, 16).addBox(-1.5F, 1.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), 
/*  50 */         PartPose.offset(0.0F, 20.0F, 0.0F));
/*     */     
/*  52 */     $$3.addOrReplaceChild("right_arm", CubeListBuilder.create()
/*  53 */         .texOffs(23, 0).addBox(-1.25F, -0.5F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), 
/*  54 */         PartPose.offset(-1.75F, 0.25F, 0.0F));
/*  55 */     $$3.addOrReplaceChild("left_arm", CubeListBuilder.create()
/*  56 */         .texOffs(23, 6).addBox(-0.75F, -0.5F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), 
/*  57 */         PartPose.offset(1.75F, 0.25F, 0.0F));
/*     */     
/*  59 */     $$3.addOrReplaceChild("left_wing", CubeListBuilder.create()
/*  60 */         .texOffs(16, 14).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), 
/*  61 */         PartPose.offset(0.5F, 1.0F, 1.0F));
/*  62 */     $$3.addOrReplaceChild("right_wing", CubeListBuilder.create()
/*  63 */         .texOffs(16, 14).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), 
/*  64 */         PartPose.offset(-0.5F, 1.0F, 1.0F));
/*     */     
/*  66 */     return LayerDefinition.create($$0, 32, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(Vex $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  71 */     root().getAllParts().forEach(ModelPart::resetPose);
/*  72 */     this.head.yRot = $$4 * 0.017453292F;
/*  73 */     this.head.xRot = $$5 * 0.017453292F;
/*  74 */     float $$6 = Mth.cos($$3 * 5.5F * 0.017453292F) * 0.1F;
/*     */     
/*  76 */     this.rightArm.zRot = 0.62831855F + $$6;
/*  77 */     this.leftArm.zRot = -(0.62831855F + $$6);
/*     */     
/*  79 */     if ($$0.isCharging()) {
/*  80 */       this.body.xRot = 0.0F;
/*  81 */       setArmsCharging($$0.getMainHandItem(), $$0.getOffhandItem(), $$6);
/*     */     } else {
/*  83 */       this.body.xRot = 0.15707964F;
/*     */     } 
/*  85 */     this.leftWing.yRot = 1.0995574F + Mth.cos($$3 * 45.836624F * 0.017453292F) * 0.017453292F * 16.2F;
/*  86 */     this.rightWing.yRot = -this.leftWing.yRot;
/*  87 */     this.leftWing.xRot = 0.47123888F;
/*  88 */     this.leftWing.zRot = -0.47123888F;
/*  89 */     this.rightWing.xRot = 0.47123888F;
/*  90 */     this.rightWing.zRot = 0.47123888F;
/*     */   }
/*     */   
/*     */   private void setArmsCharging(ItemStack $$0, ItemStack $$1, float $$2) {
/*  94 */     if ($$0.isEmpty() && $$1.isEmpty()) {
/*  95 */       this.rightArm.xRot = -1.2217305F;
/*  96 */       this.rightArm.yRot = 0.2617994F;
/*  97 */       this.rightArm.zRot = -0.47123888F - $$2;
/*  98 */       this.leftArm.xRot = -1.2217305F;
/*  99 */       this.leftArm.yRot = -0.2617994F;
/* 100 */       this.leftArm.zRot = 0.47123888F + $$2;
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     if (!$$0.isEmpty()) {
/* 105 */       this.rightArm.xRot = 3.6651914F;
/* 106 */       this.rightArm.yRot = 0.2617994F;
/* 107 */       this.rightArm.zRot = -0.47123888F - $$2;
/*     */     } 
/* 109 */     if (!$$1.isEmpty()) {
/* 110 */       this.leftArm.xRot = 3.6651914F;
/* 111 */       this.leftArm.yRot = -0.2617994F;
/* 112 */       this.leftArm.zRot = 0.47123888F + $$2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 118 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToHand(HumanoidArm $$0, PoseStack $$1) {
/* 123 */     boolean $$2 = ($$0 == HumanoidArm.RIGHT);
/* 124 */     ModelPart $$3 = $$2 ? this.rightArm : this.leftArm;
/* 125 */     this.root.translateAndRotate($$1);
/* 126 */     this.body.translateAndRotate($$1);
/* 127 */     $$3.translateAndRotate($$1);
/* 128 */     $$1.scale(0.55F, 0.55F, 0.55F);
/* 129 */     offsetStackPosition($$1, $$2);
/*     */   }
/*     */   
/*     */   private void offsetStackPosition(PoseStack $$0, boolean $$1) {
/* 133 */     if ($$1) {
/* 134 */       $$0.translate(0.046875D, -0.15625D, 0.078125D);
/*     */     } else {
/* 136 */       $$0.translate(-0.046875D, -0.15625D, 0.078125D);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\VexModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */