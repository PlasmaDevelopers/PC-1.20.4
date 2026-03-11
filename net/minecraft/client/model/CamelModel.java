/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.animation.definitions.CamelAnimation;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.camel.Camel;
/*     */ 
/*     */ public class CamelModel<T extends Camel>
/*     */   extends HierarchicalModel<T>
/*     */ {
/*     */   private static final float MAX_WALK_ANIMATION_SPEED = 2.0F;
/*     */   private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;
/*     */   private static final float BABY_SCALE = 0.45F;
/*     */   private static final float BABY_Y_OFFSET = 29.35F;
/*     */   private static final String SADDLE = "saddle";
/*     */   private static final String BRIDLE = "bridle";
/*     */   private static final String REINS = "reins";
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart[] saddleParts;
/*     */   private final ModelPart[] ridingParts;
/*     */   
/*     */   public CamelModel(ModelPart $$0) {
/*  33 */     this.root = $$0;
/*  34 */     ModelPart $$1 = $$0.getChild("body");
/*  35 */     this.head = $$1.getChild("head");
/*  36 */     this.saddleParts = new ModelPart[] { $$1.getChild("saddle"), this.head.getChild("bridle") };
/*  37 */     this.ridingParts = new ModelPart[] { this.head.getChild("reins") };
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  41 */     MeshDefinition $$0 = new MeshDefinition();
/*  42 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  44 */     CubeDeformation $$2 = new CubeDeformation(0.05F);
/*     */     
/*  46 */     PartDefinition $$3 = $$1.addOrReplaceChild("body", CubeListBuilder.create()
/*  47 */         .texOffs(0, 25).addBox(-7.5F, -12.0F, -23.5F, 15.0F, 12.0F, 27.0F), 
/*  48 */         PartPose.offset(0.0F, 4.0F, 9.5F));
/*     */     
/*  50 */     $$3.addOrReplaceChild("hump", CubeListBuilder.create()
/*  51 */         .texOffs(74, 0).addBox(-4.5F, -5.0F, -5.5F, 9.0F, 5.0F, 11.0F), 
/*  52 */         PartPose.offset(0.0F, -12.0F, -10.0F));
/*     */     
/*  54 */     $$3.addOrReplaceChild("tail", CubeListBuilder.create()
/*  55 */         .texOffs(122, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 0.0F), 
/*  56 */         PartPose.offset(0.0F, -9.0F, 3.5F));
/*     */     
/*  58 */     PartDefinition $$4 = $$3.addOrReplaceChild("head", CubeListBuilder.create()
/*  59 */         .texOffs(60, 24).addBox(-3.5F, -7.0F, -15.0F, 7.0F, 8.0F, 19.0F)
/*  60 */         .texOffs(21, 0).addBox(-3.5F, -21.0F, -15.0F, 7.0F, 14.0F, 7.0F)
/*  61 */         .texOffs(50, 0).addBox(-2.5F, -21.0F, -21.0F, 5.0F, 5.0F, 6.0F), 
/*  62 */         PartPose.offset(0.0F, -3.0F, -19.5F));
/*     */     
/*  64 */     $$4.addOrReplaceChild("left_ear", CubeListBuilder.create()
/*  65 */         .texOffs(45, 0).addBox(-0.5F, 0.5F, -1.0F, 3.0F, 1.0F, 2.0F), 
/*  66 */         PartPose.offset(2.5F, -21.0F, -9.5F));
/*     */     
/*  68 */     $$4.addOrReplaceChild("right_ear", CubeListBuilder.create()
/*  69 */         .texOffs(67, 0).addBox(-2.5F, 0.5F, -1.0F, 3.0F, 1.0F, 2.0F), 
/*  70 */         PartPose.offset(-2.5F, -21.0F, -9.5F));
/*     */     
/*  72 */     $$1.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
/*  73 */         .texOffs(58, 16).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F), 
/*  74 */         PartPose.offset(4.9F, 1.0F, 9.5F));
/*     */     
/*  76 */     $$1.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
/*  77 */         .texOffs(94, 16).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F), 
/*  78 */         PartPose.offset(-4.9F, 1.0F, 9.5F));
/*     */     
/*  80 */     $$1.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
/*  81 */         .texOffs(0, 0).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F), 
/*  82 */         PartPose.offset(4.9F, 1.0F, -10.5F));
/*     */     
/*  84 */     $$1.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
/*  85 */         .texOffs(0, 26).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F), 
/*  86 */         PartPose.offset(-4.9F, 1.0F, -10.5F));
/*     */     
/*  88 */     $$3.addOrReplaceChild("saddle", CubeListBuilder.create()
/*  89 */         .texOffs(74, 64).addBox(-4.5F, -17.0F, -15.5F, 9.0F, 5.0F, 11.0F, $$2)
/*  90 */         .texOffs(92, 114).addBox(-3.5F, -20.0F, -15.5F, 7.0F, 3.0F, 11.0F, $$2)
/*  91 */         .texOffs(0, 89).addBox(-7.5F, -12.0F, -23.5F, 15.0F, 12.0F, 27.0F, $$2), 
/*  92 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/*  94 */     $$4.addOrReplaceChild("reins", CubeListBuilder.create()
/*  95 */         .texOffs(98, 42).addBox(3.51F, -18.0F, -17.0F, 0.0F, 7.0F, 15.0F)
/*  96 */         .texOffs(84, 57).addBox(-3.5F, -18.0F, -2.0F, 7.0F, 7.0F, 0.0F)
/*  97 */         .texOffs(98, 42).addBox(-3.51F, -18.0F, -17.0F, 0.0F, 7.0F, 15.0F), 
/*  98 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/* 100 */     $$4.addOrReplaceChild("bridle", CubeListBuilder.create()
/* 101 */         .texOffs(60, 87).addBox(-3.5F, -7.0F, -15.0F, 7.0F, 8.0F, 19.0F, $$2)
/* 102 */         .texOffs(21, 64).addBox(-3.5F, -21.0F, -15.0F, 7.0F, 14.0F, 7.0F, $$2)
/* 103 */         .texOffs(50, 64).addBox(-2.5F, -21.0F, -21.0F, 5.0F, 5.0F, 6.0F, $$2)
/* 104 */         .texOffs(74, 70).addBox(2.5F, -19.0F, -18.0F, 1.0F, 2.0F, 2.0F)
/* 105 */         .texOffs(74, 70).mirror().addBox(-3.5F, -19.0F, -18.0F, 1.0F, 2.0F, 2.0F), 
/* 106 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/* 108 */     return LayerDefinition.create($$0, 128, 128);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 113 */     root().getAllParts().forEach(ModelPart::resetPose);
/*     */ 
/*     */     
/* 116 */     applyHeadRotation($$0, $$4, $$5, $$3);
/* 117 */     toggleInvisibleParts($$0);
/*     */     
/* 119 */     animateWalk(CamelAnimation.CAMEL_WALK, $$1, $$2, 2.0F, 2.5F);
/*     */     
/* 121 */     animate(((Camel)$$0).sitAnimationState, CamelAnimation.CAMEL_SIT, $$3, 1.0F);
/* 122 */     animate(((Camel)$$0).sitPoseAnimationState, CamelAnimation.CAMEL_SIT_POSE, $$3, 1.0F);
/* 123 */     animate(((Camel)$$0).sitUpAnimationState, CamelAnimation.CAMEL_STANDUP, $$3, 1.0F);
/* 124 */     animate(((Camel)$$0).idleAnimationState, CamelAnimation.CAMEL_IDLE, $$3, 1.0F);
/* 125 */     animate(((Camel)$$0).dashAnimationState, CamelAnimation.CAMEL_DASH, $$3, 1.0F);
/*     */   }
/*     */   
/*     */   private void applyHeadRotation(T $$0, float $$1, float $$2, float $$3) {
/* 129 */     $$1 = Mth.clamp($$1, -30.0F, 30.0F);
/* 130 */     $$2 = Mth.clamp($$2, -25.0F, 45.0F);
/*     */     
/* 132 */     if ($$0.getJumpCooldown() > 0) {
/* 133 */       float $$4 = $$3 - ((Camel)$$0).tickCount;
/*     */       
/* 135 */       float $$5 = 45.0F * ($$0.getJumpCooldown() - $$4) / 55.0F;
/* 136 */       $$2 = Mth.clamp($$2 + $$5, -25.0F, 70.0F);
/*     */     } 
/*     */     
/* 139 */     this.head.yRot = $$1 * 0.017453292F;
/* 140 */     this.head.xRot = $$2 * 0.017453292F;
/*     */   }
/*     */   
/*     */   private void toggleInvisibleParts(T $$0) {
/* 144 */     boolean $$1 = $$0.isSaddled();
/* 145 */     boolean $$2 = $$0.isVehicle();
/*     */     
/* 147 */     for (ModelPart $$3 : this.saddleParts) {
/* 148 */       $$3.visible = $$1;
/*     */     }
/*     */     
/* 151 */     for (ModelPart $$4 : this.ridingParts) {
/* 152 */       $$4.visible = ($$2 && $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 158 */     if (this.young) {
/* 159 */       $$0.pushPose();
/* 160 */       $$0.scale(0.45F, 0.45F, 0.45F);
/* 161 */       $$0.translate(0.0F, 1.834375F, 0.0F);
/* 162 */       root().render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 163 */       $$0.popPose();
/*     */     } else {
/* 165 */       root().render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 171 */     return this.root;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\CamelModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */