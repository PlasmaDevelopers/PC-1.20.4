/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.animation.definitions.FrogAnimation;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.frog.Frog;
/*     */ 
/*     */ public class FrogModel<T extends Frog>
/*     */   extends HierarchicalModel<T> {
/*     */   private static final float MAX_WALK_ANIMATION_SPEED = 1.5F;
/*     */   private static final float MAX_SWIM_ANIMATION_SPEED = 1.0F;
/*     */   private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;
/*     */   private final ModelPart root;
/*     */   private final ModelPart body;
/*     */   private final ModelPart head;
/*     */   private final ModelPart eyes;
/*     */   private final ModelPart tongue;
/*     */   private final ModelPart leftArm;
/*     */   private final ModelPart rightArm;
/*     */   private final ModelPart leftLeg;
/*     */   private final ModelPart rightLeg;
/*     */   private final ModelPart croakingBody;
/*     */   
/*     */   public FrogModel(ModelPart $$0) {
/*  31 */     this.root = $$0.getChild("root");
/*  32 */     this.body = this.root.getChild("body");
/*  33 */     this.head = this.body.getChild("head");
/*  34 */     this.eyes = this.head.getChild("eyes");
/*  35 */     this.tongue = this.body.getChild("tongue");
/*  36 */     this.leftArm = this.body.getChild("left_arm");
/*  37 */     this.rightArm = this.body.getChild("right_arm");
/*  38 */     this.leftLeg = this.root.getChild("left_leg");
/*  39 */     this.rightLeg = this.root.getChild("right_leg");
/*  40 */     this.croakingBody = this.body.getChild("croaking_body");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  44 */     MeshDefinition $$0 = new MeshDefinition();
/*  45 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  47 */     PartDefinition $$2 = $$1.addOrReplaceChild("root", 
/*  48 */         CubeListBuilder.create(), 
/*  49 */         PartPose.offset(0.0F, 24.0F, 0.0F));
/*     */     
/*  51 */     PartDefinition $$3 = $$2.addOrReplaceChild("body", 
/*  52 */         CubeListBuilder.create()
/*  53 */         .texOffs(3, 1).addBox(-3.5F, -2.0F, -8.0F, 7.0F, 3.0F, 9.0F)
/*  54 */         .texOffs(23, 22).addBox(-3.5F, -1.0F, -8.0F, 7.0F, 0.0F, 9.0F), 
/*  55 */         PartPose.offset(0.0F, -2.0F, 4.0F));
/*     */     
/*  57 */     PartDefinition $$4 = $$3.addOrReplaceChild("head", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(23, 13).addBox(-3.5F, -1.0F, -7.0F, 7.0F, 0.0F, 9.0F)
/*  60 */         .texOffs(0, 13).addBox(-3.5F, -2.0F, -7.0F, 7.0F, 3.0F, 9.0F), 
/*  61 */         PartPose.offset(0.0F, -2.0F, -1.0F));
/*     */     
/*  63 */     PartDefinition $$5 = $$4.addOrReplaceChild("eyes", 
/*  64 */         CubeListBuilder.create(), 
/*  65 */         PartPose.offset(-0.5F, 0.0F, 2.0F));
/*     */     
/*  67 */     $$5.addOrReplaceChild("right_eye", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(0, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F), 
/*  70 */         PartPose.offset(-1.5F, -3.0F, -6.5F));
/*     */     
/*  72 */     $$5.addOrReplaceChild("left_eye", 
/*  73 */         CubeListBuilder.create()
/*  74 */         .texOffs(0, 5).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F), 
/*  75 */         PartPose.offset(2.5F, -3.0F, -6.5F));
/*     */ 
/*     */     
/*  78 */     $$3.addOrReplaceChild("croaking_body", 
/*  79 */         CubeListBuilder.create()
/*  80 */         .texOffs(26, 5).addBox(-3.5F, -0.1F, -2.9F, 7.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), 
/*  81 */         PartPose.offset(0.0F, -1.0F, -5.0F));
/*     */ 
/*     */     
/*  84 */     PartDefinition $$6 = $$3.addOrReplaceChild("tongue", 
/*  85 */         CubeListBuilder.create()
/*  86 */         .texOffs(17, 13).addBox(-2.0F, 0.0F, -7.1F, 4.0F, 0.0F, 7.0F), 
/*  87 */         PartPose.offset(0.0F, -1.01F, 1.0F));
/*     */ 
/*     */     
/*  90 */     PartDefinition $$7 = $$3.addOrReplaceChild("left_arm", 
/*  91 */         CubeListBuilder.create()
/*  92 */         .texOffs(0, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F), 
/*  93 */         PartPose.offset(4.0F, -1.0F, -6.5F));
/*     */ 
/*     */     
/*  96 */     $$7.addOrReplaceChild("left_hand", 
/*  97 */         CubeListBuilder.create()
/*  98 */         .texOffs(18, 40).addBox(-4.0F, 0.01F, -4.0F, 8.0F, 0.0F, 8.0F), 
/*  99 */         PartPose.offset(0.0F, 3.0F, -1.0F));
/*     */ 
/*     */     
/* 102 */     PartDefinition $$8 = $$3.addOrReplaceChild("right_arm", 
/* 103 */         CubeListBuilder.create()
/* 104 */         .texOffs(0, 38).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F), 
/* 105 */         PartPose.offset(-4.0F, -1.0F, -6.5F));
/*     */ 
/*     */     
/* 108 */     $$8.addOrReplaceChild("right_hand", 
/* 109 */         CubeListBuilder.create()
/* 110 */         .texOffs(2, 40).addBox(-4.0F, 0.01F, -5.0F, 8.0F, 0.0F, 8.0F), 
/* 111 */         PartPose.offset(0.0F, 3.0F, 0.0F));
/*     */ 
/*     */     
/* 114 */     PartDefinition $$9 = $$2.addOrReplaceChild("left_leg", 
/* 115 */         CubeListBuilder.create()
/* 116 */         .texOffs(14, 25).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F), 
/* 117 */         PartPose.offset(3.5F, -3.0F, 4.0F));
/*     */ 
/*     */     
/* 120 */     $$9.addOrReplaceChild("left_foot", 
/* 121 */         CubeListBuilder.create()
/* 122 */         .texOffs(2, 32).addBox(-4.0F, 0.01F, -4.0F, 8.0F, 0.0F, 8.0F), 
/* 123 */         PartPose.offset(2.0F, 3.0F, 0.0F));
/*     */ 
/*     */     
/* 126 */     PartDefinition $$10 = $$2.addOrReplaceChild("right_leg", 
/* 127 */         CubeListBuilder.create()
/* 128 */         .texOffs(0, 25).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F), 
/* 129 */         PartPose.offset(-3.5F, -3.0F, 4.0F));
/*     */ 
/*     */     
/* 132 */     $$10.addOrReplaceChild("right_foot", 
/* 133 */         CubeListBuilder.create()
/* 134 */         .texOffs(18, 32).addBox(-4.0F, 0.01F, -4.0F, 8.0F, 0.0F, 8.0F), 
/* 135 */         PartPose.offset(-2.0F, 3.0F, 0.0F));
/*     */ 
/*     */     
/* 138 */     return LayerDefinition.create($$0, 48, 48);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 143 */     root().getAllParts().forEach(ModelPart::resetPose);
/*     */     
/* 145 */     animate(((Frog)$$0).jumpAnimationState, FrogAnimation.FROG_JUMP, $$3);
/* 146 */     animate(((Frog)$$0).croakAnimationState, FrogAnimation.FROG_CROAK, $$3);
/* 147 */     animate(((Frog)$$0).tongueAnimationState, FrogAnimation.FROG_TONGUE, $$3);
/*     */     
/* 149 */     if ($$0.isInWaterOrBubble()) {
/* 150 */       animateWalk(FrogAnimation.FROG_SWIM, $$1, $$2, 1.0F, 2.5F);
/*     */     } else {
/* 152 */       animateWalk(FrogAnimation.FROG_WALK, $$1, $$2, 1.5F, 2.5F);
/*     */     } 
/*     */     
/* 155 */     animate(((Frog)$$0).swimIdleAnimationState, FrogAnimation.FROG_IDLE_WATER, $$3);
/*     */     
/* 157 */     this.croakingBody.visible = ((Frog)$$0).croakAnimationState.isStarted();
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 162 */     return this.root;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\FrogModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */