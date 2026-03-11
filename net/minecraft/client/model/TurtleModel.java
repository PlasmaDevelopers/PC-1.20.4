/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
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
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ 
/*     */ public class TurtleModel<T extends Turtle>
/*     */   extends QuadrupedModel<T> {
/*     */   private static final String EGG_BELLY = "egg_belly";
/*     */   private final ModelPart eggBelly;
/*     */   
/*     */   public TurtleModel(ModelPart $$0) {
/*  23 */     super($$0, true, 120.0F, 0.0F, 9.0F, 6.0F, 120);
/*  24 */     this.eggBelly = $$0.getChild("egg_belly");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  28 */     MeshDefinition $$0 = new MeshDefinition();
/*  29 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  31 */     $$1.addOrReplaceChild("head", 
/*  32 */         CubeListBuilder.create()
/*  33 */         .texOffs(3, 0).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F), 
/*  34 */         PartPose.offset(0.0F, 19.0F, -10.0F));
/*     */     
/*  36 */     $$1.addOrReplaceChild("body", 
/*  37 */         CubeListBuilder.create()
/*  38 */         .texOffs(7, 37).addBox("shell", -9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F)
/*  39 */         .texOffs(31, 1).addBox("belly", -5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F), 
/*  40 */         PartPose.offsetAndRotation(0.0F, 11.0F, -10.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  42 */     $$1.addOrReplaceChild("egg_belly", 
/*  43 */         CubeListBuilder.create()
/*  44 */         .texOffs(70, 33).addBox(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F), 
/*  45 */         PartPose.offsetAndRotation(0.0F, 11.0F, -10.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  47 */     int $$2 = 1;
/*  48 */     $$1.addOrReplaceChild("right_hind_leg", 
/*  49 */         CubeListBuilder.create()
/*  50 */         .texOffs(1, 23).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F), 
/*  51 */         PartPose.offset(-3.5F, 22.0F, 11.0F));
/*     */     
/*  53 */     $$1.addOrReplaceChild("left_hind_leg", 
/*  54 */         CubeListBuilder.create()
/*  55 */         .texOffs(1, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F), 
/*  56 */         PartPose.offset(3.5F, 22.0F, 11.0F));
/*     */     
/*  58 */     $$1.addOrReplaceChild("right_front_leg", 
/*  59 */         CubeListBuilder.create()
/*  60 */         .texOffs(27, 30).addBox(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F), 
/*  61 */         PartPose.offset(-5.0F, 21.0F, -4.0F));
/*     */     
/*  63 */     $$1.addOrReplaceChild("left_front_leg", 
/*  64 */         CubeListBuilder.create()
/*  65 */         .texOffs(27, 24).addBox(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F), 
/*  66 */         PartPose.offset(5.0F, 21.0F, -4.0F));
/*     */ 
/*     */     
/*  69 */     return LayerDefinition.create($$0, 128, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/*  74 */     return Iterables.concat(super.bodyParts(), (Iterable)ImmutableList.of(this.eggBelly));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  79 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  81 */     this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F * 0.6F) * 0.5F * $$2;
/*  82 */     this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F * 0.6F + 3.1415927F) * 0.5F * $$2;
/*  83 */     this.rightFrontLeg.zRot = Mth.cos($$1 * 0.6662F * 0.6F + 3.1415927F) * 0.5F * $$2;
/*  84 */     this.leftFrontLeg.zRot = Mth.cos($$1 * 0.6662F * 0.6F) * 0.5F * $$2;
/*  85 */     this.rightFrontLeg.xRot = 0.0F;
/*  86 */     this.leftFrontLeg.xRot = 0.0F;
/*  87 */     this.rightFrontLeg.yRot = 0.0F;
/*  88 */     this.leftFrontLeg.yRot = 0.0F;
/*  89 */     this.rightHindLeg.yRot = 0.0F;
/*  90 */     this.leftHindLeg.yRot = 0.0F;
/*     */     
/*  92 */     if (!$$0.isInWater() && $$0.onGround()) {
/*  93 */       float $$6 = $$0.isLayingEgg() ? 4.0F : 1.0F;
/*  94 */       float $$7 = $$0.isLayingEgg() ? 2.0F : 1.0F;
/*  95 */       float $$8 = 5.0F;
/*     */       
/*  97 */       this.rightFrontLeg.yRot = Mth.cos($$6 * $$1 * 5.0F + 3.1415927F) * 8.0F * $$2 * $$7;
/*  98 */       this.rightFrontLeg.zRot = 0.0F;
/*  99 */       this.leftFrontLeg.yRot = Mth.cos($$6 * $$1 * 5.0F) * 8.0F * $$2 * $$7;
/* 100 */       this.leftFrontLeg.zRot = 0.0F;
/* 101 */       this.rightHindLeg.yRot = Mth.cos($$1 * 5.0F + 3.1415927F) * 3.0F * $$2;
/* 102 */       this.rightHindLeg.xRot = 0.0F;
/* 103 */       this.leftHindLeg.yRot = Mth.cos($$1 * 5.0F) * 3.0F * $$2;
/* 104 */       this.leftHindLeg.xRot = 0.0F;
/*     */     } 
/*     */     
/* 107 */     this.eggBelly.visible = (!this.young && $$0.hasEgg());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 112 */     boolean $$8 = this.eggBelly.visible;
/* 113 */     if ($$8) {
/* 114 */       $$0.pushPose();
/* 115 */       $$0.translate(0.0F, -0.08F, 0.0F);
/*     */     } 
/* 117 */     super.renderToBuffer($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 118 */     if ($$8)
/* 119 */       $$0.popPose(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\TurtleModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */