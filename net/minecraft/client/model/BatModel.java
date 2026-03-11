/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.animation.definitions.BatAnimation;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ambient.Bat;
/*     */ 
/*     */ public class BatModel
/*     */   extends HierarchicalModel<Bat> {
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightWing;
/*     */   private final ModelPart leftWing;
/*     */   private final ModelPart rightWingTip;
/*     */   private final ModelPart leftWingTip;
/*     */   private final ModelPart feet;
/*     */   
/*     */   public BatModel(ModelPart $$0) {
/*  26 */     super(RenderType::entityCutout);
/*  27 */     this.root = $$0;
/*  28 */     this.body = $$0.getChild("body");
/*  29 */     this.head = $$0.getChild("head");
/*  30 */     this.rightWing = this.body.getChild("right_wing");
/*  31 */     this.rightWingTip = this.rightWing.getChild("right_wing_tip");
/*  32 */     this.leftWing = this.body.getChild("left_wing");
/*  33 */     this.leftWingTip = this.leftWing.getChild("left_wing_tip");
/*  34 */     this.feet = this.body.getChild("feet");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  38 */     MeshDefinition $$0 = new MeshDefinition();
/*  39 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  41 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", 
/*  42 */         CubeListBuilder.create()
/*  43 */         .texOffs(0, 0).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 5.0F, 2.0F), 
/*  44 */         PartPose.offset(0.0F, 17.0F, 0.0F));
/*     */     
/*  46 */     PartDefinition $$3 = $$1.addOrReplaceChild("head", 
/*  47 */         CubeListBuilder.create()
/*  48 */         .texOffs(0, 7).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 3.0F, 2.0F), 
/*  49 */         PartPose.offset(0.0F, 17.0F, 0.0F));
/*     */     
/*  51 */     $$3.addOrReplaceChild("right_ear", 
/*  52 */         CubeListBuilder.create()
/*  53 */         .texOffs(1, 15).addBox(-2.5F, -4.0F, 0.0F, 3.0F, 5.0F, 0.0F), 
/*  54 */         PartPose.offset(-1.5F, -2.0F, 0.0F));
/*     */     
/*  56 */     $$3.addOrReplaceChild("left_ear", 
/*  57 */         CubeListBuilder.create()
/*  58 */         .texOffs(8, 15).addBox(-0.1F, -3.0F, 0.0F, 3.0F, 5.0F, 0.0F), 
/*  59 */         PartPose.offset(1.1F, -3.0F, 0.0F));
/*     */     
/*  61 */     PartDefinition $$4 = $$2.addOrReplaceChild("right_wing", 
/*  62 */         CubeListBuilder.create()
/*  63 */         .texOffs(12, 0).addBox(-2.0F, -2.0F, 0.0F, 2.0F, 7.0F, 0.0F), 
/*  64 */         PartPose.offset(-1.5F, 0.0F, 0.0F));
/*     */     
/*  66 */     $$4.addOrReplaceChild("right_wing_tip", 
/*  67 */         CubeListBuilder.create()
/*  68 */         .texOffs(16, 0).addBox(-6.0F, -2.0F, 0.0F, 6.0F, 8.0F, 0.0F), 
/*  69 */         PartPose.offset(-2.0F, 0.0F, 0.0F));
/*     */     
/*  71 */     PartDefinition $$5 = $$2.addOrReplaceChild("left_wing", 
/*  72 */         CubeListBuilder.create()
/*  73 */         .texOffs(12, 7).addBox(0.0F, -2.0F, 0.0F, 2.0F, 7.0F, 0.0F), 
/*  74 */         PartPose.offset(1.5F, 0.0F, 0.0F));
/*     */     
/*  76 */     $$5.addOrReplaceChild("left_wing_tip", 
/*  77 */         CubeListBuilder.create().texOffs(16, 8)
/*  78 */         .addBox(0.0F, -2.0F, 0.0F, 6.0F, 8.0F, 0.0F), 
/*  79 */         PartPose.offset(2.0F, 0.0F, 0.0F));
/*     */     
/*  81 */     $$2.addOrReplaceChild("feet", 
/*  82 */         CubeListBuilder.create().texOffs(16, 16)
/*  83 */         .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F), 
/*  84 */         PartPose.offset(0.0F, 5.0F, 0.0F));
/*     */     
/*  86 */     return LayerDefinition.create($$0, 32, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  91 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(Bat $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  96 */     root().getAllParts().forEach(ModelPart::resetPose);
/*  97 */     if ($$0.isResting()) {
/*  98 */       applyHeadRotation($$4);
/*     */     }
/* 100 */     animate($$0.flyAnimationState, BatAnimation.BAT_FLYING, $$3, 1.0F);
/* 101 */     animate($$0.restAnimationState, BatAnimation.BAT_RESTING, $$3, 1.0F);
/*     */   }
/*     */   
/*     */   private void applyHeadRotation(float $$0) {
/* 105 */     this.head.yRot = $$0 * 0.017453292F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\BatModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */