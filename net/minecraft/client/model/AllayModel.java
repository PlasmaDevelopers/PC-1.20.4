/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Axis;
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
/*     */ import net.minecraft.world.entity.animal.allay.Allay;
/*     */ 
/*     */ public class AllayModel
/*     */   extends HierarchicalModel<Allay> implements ArmedModel {
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart body;
/*     */   private final ModelPart right_arm;
/*     */   private final ModelPart left_arm;
/*     */   private final ModelPart right_wing;
/*     */   private final ModelPart left_wing;
/*     */   private static final float FLYING_ANIMATION_X_ROT = 0.7853982F;
/*     */   private static final float MAX_HAND_HOLDING_ITEM_X_ROT_RAD = -1.134464F;
/*     */   private static final float MIN_HAND_HOLDING_ITEM_X_ROT_RAD = -1.0471976F;
/*     */   
/*     */   public AllayModel(ModelPart $$0) {
/*  32 */     super(RenderType::entityTranslucent);
/*  33 */     this.root = $$0.getChild("root");
/*  34 */     this.head = this.root.getChild("head");
/*  35 */     this.body = this.root.getChild("body");
/*  36 */     this.right_arm = this.body.getChild("right_arm");
/*  37 */     this.left_arm = this.body.getChild("left_arm");
/*  38 */     this.right_wing = this.body.getChild("right_wing");
/*  39 */     this.left_wing = this.body.getChild("left_wing");
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  44 */     return this.root;
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  48 */     MeshDefinition $$0 = new MeshDefinition();
/*  49 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  51 */     PartDefinition $$2 = $$1.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 23.5F, 0.0F));
/*  52 */     $$2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.99F, 0.0F));
/*  53 */     PartDefinition $$3 = $$2.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
/*  54 */         .texOffs(0, 16).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, -4.0F, 0.0F));
/*  55 */     $$3.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(23, 0).addBox(-0.75F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(-1.75F, 0.5F, 0.0F));
/*  56 */     $$3.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(23, 6).addBox(-0.25F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(1.75F, 0.5F, 0.0F));
/*  57 */     $$3.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 0.6F));
/*  58 */     $$3.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.6F));
/*     */     
/*  60 */     return LayerDefinition.create($$0, 32, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(Allay $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  65 */     root().getAllParts().forEach(ModelPart::resetPose);
/*     */     
/*  67 */     float $$6 = $$3 * 20.0F * 0.017453292F + $$1;
/*  68 */     float $$7 = Mth.cos($$6) * 3.1415927F * 0.15F + $$2;
/*  69 */     float $$8 = $$3 - $$0.tickCount;
/*  70 */     float $$9 = $$3 * 9.0F * 0.017453292F;
/*  71 */     float $$10 = Math.min($$2 / 0.3F, 1.0F);
/*  72 */     float $$11 = 1.0F - $$10;
/*  73 */     float $$12 = $$0.getHoldingItemAnimationProgress($$8);
/*     */     
/*  75 */     if ($$0.isDancing()) {
/*  76 */       float $$13 = $$3 * 8.0F * 0.017453292F + $$2;
/*  77 */       float $$14 = Mth.cos($$13) * 16.0F * 0.017453292F;
/*  78 */       float $$15 = $$0.getSpinningProgress($$8);
/*  79 */       float $$16 = Mth.cos($$13) * 14.0F * 0.017453292F;
/*  80 */       float $$17 = Mth.cos($$13) * 30.0F * 0.017453292F;
/*  81 */       this.root.yRot = $$0.isSpinning() ? (12.566371F * $$15) : this.root.yRot;
/*  82 */       this.root.zRot = $$14 * (1.0F - $$15);
/*  83 */       this.head.yRot = $$17 * (1.0F - $$15);
/*  84 */       this.head.zRot = $$16 * (1.0F - $$15);
/*     */     } else {
/*  86 */       this.head.xRot = $$5 * 0.017453292F;
/*  87 */       this.head.yRot = $$4 * 0.017453292F;
/*     */     } 
/*     */     
/*  90 */     this.right_wing.xRot = 0.43633232F * (1.0F - $$10);
/*  91 */     this.right_wing.yRot = -0.7853982F + $$7;
/*  92 */     this.left_wing.xRot = 0.43633232F * (1.0F - $$10);
/*  93 */     this.left_wing.yRot = 0.7853982F - $$7;
/*  94 */     this.body.xRot = $$10 * 0.7853982F;
/*  95 */     float $$18 = $$12 * Mth.lerp($$10, -1.0471976F, -1.134464F);
/*  96 */     this.root.y += (float)Math.cos($$9) * 0.25F * $$11;
/*  97 */     this.right_arm.xRot = $$18;
/*  98 */     this.left_arm.xRot = $$18;
/*  99 */     float $$19 = $$11 * (1.0F - $$12);
/* 100 */     float $$20 = 0.43633232F - Mth.cos($$9 + 4.712389F) * 3.1415927F * 0.075F * $$19;
/* 101 */     this.left_arm.zRot = -$$20;
/* 102 */     this.right_arm.zRot = $$20;
/* 103 */     this.right_arm.yRot = 0.27925268F * $$12;
/* 104 */     this.left_arm.yRot = -0.27925268F * $$12;
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToHand(HumanoidArm $$0, PoseStack $$1) {
/* 109 */     float $$2 = 1.0F;
/* 110 */     float $$3 = 3.0F;
/* 111 */     this.root.translateAndRotate($$1);
/* 112 */     this.body.translateAndRotate($$1);
/* 113 */     $$1.translate(0.0F, 0.0625F, 0.1875F);
/* 114 */     $$1.mulPose(Axis.XP.rotation(this.right_arm.xRot));
/* 115 */     $$1.scale(0.7F, 0.7F, 0.7F);
/* 116 */     $$1.translate(0.0625F, 0.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\AllayModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */