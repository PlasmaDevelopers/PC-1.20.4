/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ 
/*     */ public class IronGolemModel<T extends IronGolem> extends HierarchicalModel<T> {
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart rightArm;
/*     */   private final ModelPart leftArm;
/*     */   private final ModelPart rightLeg;
/*     */   private final ModelPart leftLeg;
/*     */   
/*     */   public IronGolemModel(ModelPart $$0) {
/*  23 */     this.root = $$0;
/*  24 */     this.head = $$0.getChild("head");
/*  25 */     this.rightArm = $$0.getChild("right_arm");
/*  26 */     this.leftArm = $$0.getChild("left_arm");
/*  27 */     this.rightLeg = $$0.getChild("right_leg");
/*  28 */     this.leftLeg = $$0.getChild("left_leg");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  32 */     MeshDefinition $$0 = new MeshDefinition();
/*  33 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  35 */     $$1.addOrReplaceChild("head", 
/*  36 */         CubeListBuilder.create()
/*  37 */         .texOffs(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F)
/*  38 */         .texOffs(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F), 
/*  39 */         PartPose.offset(0.0F, -7.0F, -2.0F));
/*     */     
/*  41 */     $$1.addOrReplaceChild("body", 
/*  42 */         CubeListBuilder.create()
/*  43 */         .texOffs(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F)
/*  44 */         .texOffs(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)), 
/*  45 */         PartPose.offset(0.0F, -7.0F, 0.0F));
/*     */     
/*  47 */     $$1.addOrReplaceChild("right_arm", 
/*  48 */         CubeListBuilder.create()
/*  49 */         .texOffs(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), 
/*  50 */         PartPose.offset(0.0F, -7.0F, 0.0F));
/*     */     
/*  52 */     $$1.addOrReplaceChild("left_arm", 
/*  53 */         CubeListBuilder.create()
/*  54 */         .texOffs(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F), 
/*  55 */         PartPose.offset(0.0F, -7.0F, 0.0F));
/*     */     
/*  57 */     $$1.addOrReplaceChild("right_leg", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), 
/*  60 */         PartPose.offset(-4.0F, 11.0F, 0.0F));
/*     */     
/*  62 */     $$1.addOrReplaceChild("left_leg", 
/*  63 */         CubeListBuilder.create()
/*  64 */         .texOffs(60, 0).mirror().addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F), 
/*  65 */         PartPose.offset(5.0F, 11.0F, 0.0F));
/*     */ 
/*     */     
/*  68 */     return LayerDefinition.create($$0, 128, 128);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  73 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  78 */     this.head.yRot = $$4 * 0.017453292F;
/*  79 */     this.head.xRot = $$5 * 0.017453292F;
/*     */     
/*  81 */     this.rightLeg.xRot = -1.5F * Mth.triangleWave($$1, 13.0F) * $$2;
/*  82 */     this.leftLeg.xRot = 1.5F * Mth.triangleWave($$1, 13.0F) * $$2;
/*  83 */     this.rightLeg.yRot = 0.0F;
/*  84 */     this.leftLeg.yRot = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/*  89 */     int $$4 = $$0.getAttackAnimationTick();
/*  90 */     if ($$4 > 0) {
/*  91 */       this.rightArm.xRot = -2.0F + 1.5F * Mth.triangleWave($$4 - $$3, 10.0F);
/*  92 */       this.leftArm.xRot = -2.0F + 1.5F * Mth.triangleWave($$4 - $$3, 10.0F);
/*     */     } else {
/*  94 */       int $$5 = $$0.getOfferFlowerTick();
/*  95 */       if ($$5 > 0) {
/*  96 */         this.rightArm.xRot = -0.8F + 0.025F * Mth.triangleWave($$5, 70.0F);
/*  97 */         this.leftArm.xRot = 0.0F;
/*     */       } else {
/*  99 */         this.rightArm.xRot = (-0.2F + 1.5F * Mth.triangleWave($$1, 13.0F)) * $$2;
/* 100 */         this.leftArm.xRot = (-0.2F - 1.5F * Mth.triangleWave($$1, 13.0F)) * $$2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ModelPart getFlowerHoldingArm() {
/* 106 */     return this.rightArm;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\IronGolemModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */