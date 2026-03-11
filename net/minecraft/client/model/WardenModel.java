/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.animation.definitions.WardenAnimation;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.monster.warden.Warden;
/*     */ 
/*     */ 
/*     */ public class WardenModel<T extends Warden>
/*     */   extends HierarchicalModel<T>
/*     */ {
/*     */   private static final float DEFAULT_ARM_X_Y = 13.0F;
/*     */   private static final float DEFAULT_ARM_Z = 1.0F;
/*     */   private final ModelPart root;
/*     */   protected final ModelPart bone;
/*     */   protected final ModelPart body;
/*     */   protected final ModelPart head;
/*     */   protected final ModelPart rightTendril;
/*     */   protected final ModelPart leftTendril;
/*     */   protected final ModelPart leftLeg;
/*     */   protected final ModelPart leftArm;
/*     */   protected final ModelPart leftRibcage;
/*     */   protected final ModelPart rightArm;
/*     */   protected final ModelPart rightLeg;
/*     */   protected final ModelPart rightRibcage;
/*     */   private final List<ModelPart> tendrilsLayerModelParts;
/*     */   private final List<ModelPart> heartLayerModelParts;
/*     */   private final List<ModelPart> bioluminescentLayerModelParts;
/*     */   private final List<ModelPart> pulsatingSpotsLayerModelParts;
/*     */   
/*     */   public WardenModel(ModelPart $$0) {
/*  41 */     super(RenderType::entityCutoutNoCull);
/*     */     
/*  43 */     this.root = $$0;
/*  44 */     this.bone = $$0.getChild("bone");
/*  45 */     this.body = this.bone.getChild("body");
/*  46 */     this.head = this.body.getChild("head");
/*  47 */     this.rightLeg = this.bone.getChild("right_leg");
/*  48 */     this.leftLeg = this.bone.getChild("left_leg");
/*  49 */     this.rightArm = this.body.getChild("right_arm");
/*  50 */     this.leftArm = this.body.getChild("left_arm");
/*  51 */     this.rightTendril = this.head.getChild("right_tendril");
/*  52 */     this.leftTendril = this.head.getChild("left_tendril");
/*  53 */     this.rightRibcage = this.body.getChild("right_ribcage");
/*  54 */     this.leftRibcage = this.body.getChild("left_ribcage");
/*     */     
/*  56 */     this.tendrilsLayerModelParts = (List<ModelPart>)ImmutableList.of(this.leftTendril, this.rightTendril);
/*  57 */     this.heartLayerModelParts = (List<ModelPart>)ImmutableList.of(this.body);
/*  58 */     this.bioluminescentLayerModelParts = (List<ModelPart>)ImmutableList.of(this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
/*  59 */     this.pulsatingSpotsLayerModelParts = (List<ModelPart>)ImmutableList.of(this.body, this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  63 */     MeshDefinition $$0 = new MeshDefinition();
/*  64 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  66 */     PartDefinition $$2 = $$1.addOrReplaceChild("bone", 
/*  67 */         CubeListBuilder.create(), 
/*  68 */         PartPose.offset(0.0F, 24.0F, 0.0F));
/*     */     
/*  70 */     PartDefinition $$3 = $$2.addOrReplaceChild("body", 
/*  71 */         CubeListBuilder.create()
/*  72 */         .texOffs(0, 0).addBox(-9.0F, -13.0F, -4.0F, 18.0F, 21.0F, 11.0F), 
/*  73 */         PartPose.offset(0.0F, -21.0F, 0.0F));
/*     */     
/*  75 */     $$3.addOrReplaceChild("right_ribcage", 
/*  76 */         CubeListBuilder.create().texOffs(90, 11).addBox(-2.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F), 
/*  77 */         PartPose.offset(-7.0F, -2.0F, -4.0F));
/*     */ 
/*     */     
/*  80 */     $$3.addOrReplaceChild("left_ribcage", 
/*  81 */         CubeListBuilder.create()
/*  82 */         .texOffs(90, 11).mirror().addBox(-7.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F).mirror(false), 
/*  83 */         PartPose.offset(7.0F, -2.0F, -4.0F));
/*     */ 
/*     */     
/*  86 */     PartDefinition $$4 = $$3.addOrReplaceChild("head", 
/*  87 */         CubeListBuilder.create()
/*  88 */         .texOffs(0, 32).addBox(-8.0F, -16.0F, -5.0F, 16.0F, 16.0F, 10.0F), 
/*  89 */         PartPose.offset(0.0F, -13.0F, 0.0F));
/*     */ 
/*     */     
/*  92 */     $$4.addOrReplaceChild("right_tendril", 
/*  93 */         CubeListBuilder.create()
/*  94 */         .texOffs(52, 32).addBox(-16.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F), 
/*  95 */         PartPose.offset(-8.0F, -12.0F, 0.0F));
/*     */ 
/*     */     
/*  98 */     $$4.addOrReplaceChild("left_tendril", 
/*  99 */         CubeListBuilder.create()
/* 100 */         .texOffs(58, 0).addBox(0.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F), 
/* 101 */         PartPose.offset(8.0F, -12.0F, 0.0F));
/*     */ 
/*     */     
/* 104 */     $$3.addOrReplaceChild("right_arm", 
/* 105 */         CubeListBuilder.create()
/* 106 */         .texOffs(44, 50).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F), 
/* 107 */         PartPose.offset(-13.0F, -13.0F, 1.0F));
/*     */ 
/*     */     
/* 110 */     $$3.addOrReplaceChild("left_arm", 
/* 111 */         CubeListBuilder.create()
/* 112 */         .texOffs(0, 58).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F), 
/* 113 */         PartPose.offset(13.0F, -13.0F, 1.0F));
/*     */ 
/*     */     
/* 116 */     $$2.addOrReplaceChild("right_leg", 
/* 117 */         CubeListBuilder.create()
/* 118 */         .texOffs(76, 48).addBox(-3.1F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F), 
/* 119 */         PartPose.offset(-5.9F, -13.0F, 0.0F));
/*     */ 
/*     */     
/* 122 */     $$2.addOrReplaceChild("left_leg", 
/* 123 */         CubeListBuilder.create()
/* 124 */         .texOffs(76, 76).addBox(-2.9F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F), 
/* 125 */         PartPose.offset(5.9F, -13.0F, 0.0F));
/*     */ 
/*     */     
/* 128 */     return LayerDefinition.create($$0, 128, 128);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 133 */     root().getAllParts().forEach(ModelPart::resetPose);
/*     */     
/* 135 */     float $$6 = $$3 - ((Warden)$$0).tickCount;
/*     */ 
/*     */     
/* 138 */     animateHeadLookTarget($$4, $$5);
/* 139 */     animateWalk($$1, $$2);
/* 140 */     animateIdlePose($$3);
/* 141 */     animateTendrils($$0, $$3, $$6);
/*     */ 
/*     */     
/* 144 */     animate(((Warden)$$0).attackAnimationState, WardenAnimation.WARDEN_ATTACK, $$3);
/* 145 */     animate(((Warden)$$0).sonicBoomAnimationState, WardenAnimation.WARDEN_SONIC_BOOM, $$3);
/* 146 */     animate(((Warden)$$0).diggingAnimationState, WardenAnimation.WARDEN_DIG, $$3);
/* 147 */     animate(((Warden)$$0).emergeAnimationState, WardenAnimation.WARDEN_EMERGE, $$3);
/* 148 */     animate(((Warden)$$0).roarAnimationState, WardenAnimation.WARDEN_ROAR, $$3);
/* 149 */     animate(((Warden)$$0).sniffAnimationState, WardenAnimation.WARDEN_SNIFF, $$3);
/*     */   }
/*     */   
/*     */   private void animateHeadLookTarget(float $$0, float $$1) {
/* 153 */     this.head.xRot = $$1 * 0.017453292F;
/* 154 */     this.head.yRot = $$0 * 0.017453292F;
/*     */   }
/*     */   
/*     */   private void animateIdlePose(float $$0) {
/* 158 */     float $$1 = $$0 * 0.1F;
/* 159 */     float $$2 = Mth.cos($$1);
/* 160 */     float $$3 = Mth.sin($$1);
/*     */ 
/*     */     
/* 163 */     this.head.zRot += 0.06F * $$2;
/* 164 */     this.head.xRot += 0.06F * $$3;
/*     */     
/* 166 */     this.body.zRot += 0.025F * $$3;
/* 167 */     this.body.xRot += 0.025F * $$2;
/*     */   }
/*     */   
/*     */   private void animateWalk(float $$0, float $$1) {
/* 171 */     float $$2 = Math.min(0.5F, 3.0F * $$1);
/* 172 */     float $$3 = $$0 * 0.8662F;
/* 173 */     float $$4 = Mth.cos($$3);
/* 174 */     float $$5 = Mth.sin($$3);
/* 175 */     float $$6 = Math.min(0.35F, $$2);
/*     */     
/* 177 */     this.head.zRot += 0.3F * $$5 * $$2;
/* 178 */     this.head.xRot += 1.2F * Mth.cos($$3 + 1.5707964F) * $$6;
/*     */     
/* 180 */     this.body.zRot = 0.1F * $$5 * $$2;
/* 181 */     this.body.xRot = 1.0F * $$4 * $$6;
/*     */     
/* 183 */     this.leftLeg.xRot = 1.0F * $$4 * $$2;
/* 184 */     this.rightLeg.xRot = 1.0F * Mth.cos($$3 + 3.1415927F) * $$2;
/*     */     
/* 186 */     this.leftArm.xRot = -(0.8F * $$4 * $$2);
/* 187 */     this.leftArm.zRot = 0.0F;
/*     */     
/* 189 */     this.rightArm.xRot = -(0.8F * $$5 * $$2);
/* 190 */     this.rightArm.zRot = 0.0F;
/*     */     
/* 192 */     resetArmPoses();
/*     */   }
/*     */   
/*     */   private void resetArmPoses() {
/* 196 */     this.leftArm.yRot = 0.0F;
/* 197 */     this.leftArm.z = 1.0F;
/* 198 */     this.leftArm.x = 13.0F;
/* 199 */     this.leftArm.y = -13.0F;
/*     */     
/* 201 */     this.rightArm.yRot = 0.0F;
/* 202 */     this.rightArm.z = 1.0F;
/* 203 */     this.rightArm.x = -13.0F;
/* 204 */     this.rightArm.y = -13.0F;
/*     */   }
/*     */   
/*     */   private void animateTendrils(T $$0, float $$1, float $$2) {
/* 208 */     float $$3 = $$0.getTendrilAnimation($$2) * (float)(Math.cos($$1 * 2.25D) * Math.PI * 0.10000000149011612D);
/* 209 */     this.leftTendril.xRot = $$3;
/* 210 */     this.rightTendril.xRot = -$$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 215 */     return this.root;
/*     */   }
/*     */   
/*     */   public List<ModelPart> getTendrilsLayerModelParts() {
/* 219 */     return this.tendrilsLayerModelParts;
/*     */   }
/*     */   
/*     */   public List<ModelPart> getHeartLayerModelParts() {
/* 223 */     return this.heartLayerModelParts;
/*     */   }
/*     */   
/*     */   public List<ModelPart> getBioluminescentLayerModelParts() {
/* 227 */     return this.bioluminescentLayerModelParts;
/*     */   }
/*     */   
/*     */   public List<ModelPart> getPulsatingSpotsLayerModelParts() {
/* 231 */     return this.pulsatingSpotsLayerModelParts;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\WardenModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */