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
/*     */ import net.minecraft.world.entity.animal.Bee;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeeModel<T extends Bee>
/*     */   extends AgeableListModel<T>
/*     */ {
/*     */   private static final float BEE_Y_BASE = 19.0F;
/*     */   private static final String BONE = "bone";
/*     */   private static final String STINGER = "stinger";
/*     */   private static final String LEFT_ANTENNA = "left_antenna";
/*     */   private static final String RIGHT_ANTENNA = "right_antenna";
/*     */   private static final String FRONT_LEGS = "front_legs";
/*     */   private static final String MIDDLE_LEGS = "middle_legs";
/*     */   private static final String BACK_LEGS = "back_legs";
/*     */   private final ModelPart bone;
/*     */   private final ModelPart rightWing;
/*     */   private final ModelPart leftWing;
/*     */   private final ModelPart frontLeg;
/*     */   private final ModelPart midLeg;
/*     */   private final ModelPart backLeg;
/*     */   private final ModelPart stinger;
/*     */   private final ModelPart leftAntenna;
/*     */   private final ModelPart rightAntenna;
/*     */   private float rollAmount;
/*     */   
/*     */   public BeeModel(ModelPart $$0) {
/*  40 */     super(false, 24.0F, 0.0F);
/*     */     
/*  42 */     this.bone = $$0.getChild("bone");
/*     */     
/*  44 */     ModelPart $$1 = this.bone.getChild("body");
/*  45 */     this.stinger = $$1.getChild("stinger");
/*  46 */     this.leftAntenna = $$1.getChild("left_antenna");
/*  47 */     this.rightAntenna = $$1.getChild("right_antenna");
/*     */     
/*  49 */     this.rightWing = this.bone.getChild("right_wing");
/*  50 */     this.leftWing = this.bone.getChild("left_wing");
/*  51 */     this.frontLeg = this.bone.getChild("front_legs");
/*  52 */     this.midLeg = this.bone.getChild("middle_legs");
/*  53 */     this.backLeg = this.bone.getChild("back_legs");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  57 */     float $$0 = 19.0F;
/*     */     
/*  59 */     MeshDefinition $$1 = new MeshDefinition();
/*  60 */     PartDefinition $$2 = $$1.getRoot();
/*     */     
/*  62 */     PartDefinition $$3 = $$2.addOrReplaceChild("bone", 
/*  63 */         CubeListBuilder.create(), 
/*  64 */         PartPose.offset(0.0F, 19.0F, 0.0F));
/*     */     
/*  66 */     PartDefinition $$4 = $$3.addOrReplaceChild("body", 
/*  67 */         CubeListBuilder.create()
/*  68 */         .texOffs(0, 0).addBox(-3.5F, -4.0F, -5.0F, 7.0F, 7.0F, 10.0F), PartPose.ZERO);
/*     */     
/*  70 */     $$4.addOrReplaceChild("stinger", 
/*  71 */         CubeListBuilder.create()
/*  72 */         .texOffs(26, 7).addBox(0.0F, -1.0F, 5.0F, 0.0F, 1.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  75 */     $$4.addOrReplaceChild("left_antenna", 
/*  76 */         CubeListBuilder.create()
/*  77 */         .texOffs(2, 0).addBox(1.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F), 
/*  78 */         PartPose.offset(0.0F, -2.0F, -5.0F));
/*     */     
/*  80 */     $$4.addOrReplaceChild("right_antenna", 
/*  81 */         CubeListBuilder.create()
/*  82 */         .texOffs(2, 3).addBox(-2.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F), 
/*  83 */         PartPose.offset(0.0F, -2.0F, -5.0F));
/*     */ 
/*     */     
/*  86 */     CubeDeformation $$5 = new CubeDeformation(0.001F);
/*  87 */     $$3.addOrReplaceChild("right_wing", 
/*  88 */         CubeListBuilder.create()
/*  89 */         .texOffs(0, 18).addBox(-9.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, $$5), 
/*  90 */         PartPose.offsetAndRotation(-1.5F, -4.0F, -3.0F, 0.0F, -0.2618F, 0.0F));
/*     */     
/*  92 */     $$3.addOrReplaceChild("left_wing", 
/*  93 */         CubeListBuilder.create()
/*  94 */         .texOffs(0, 18).mirror().addBox(0.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, $$5), 
/*  95 */         PartPose.offsetAndRotation(1.5F, -4.0F, -3.0F, 0.0F, 0.2618F, 0.0F));
/*     */     
/*  97 */     $$3.addOrReplaceChild("front_legs", 
/*  98 */         CubeListBuilder.create()
/*  99 */         .addBox("front_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 1), 
/* 100 */         PartPose.offset(1.5F, 3.0F, -2.0F));
/*     */     
/* 102 */     $$3.addOrReplaceChild("middle_legs", 
/* 103 */         CubeListBuilder.create()
/* 104 */         .addBox("middle_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 3), 
/* 105 */         PartPose.offset(1.5F, 3.0F, 0.0F));
/*     */     
/* 107 */     $$3.addOrReplaceChild("back_legs", 
/* 108 */         CubeListBuilder.create()
/* 109 */         .addBox("back_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 5), 
/* 110 */         PartPose.offset(1.5F, 3.0F, 2.0F));
/*     */ 
/*     */     
/* 113 */     return LayerDefinition.create($$1, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 118 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*     */     
/* 120 */     this.rollAmount = $$0.getRollAmount($$3);
/*     */ 
/*     */     
/* 123 */     this.stinger.visible = !$$0.hasStung();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 128 */     this.rightWing.xRot = 0.0F;
/* 129 */     this.leftAntenna.xRot = 0.0F;
/* 130 */     this.rightAntenna.xRot = 0.0F;
/* 131 */     this.bone.xRot = 0.0F;
/*     */     
/* 133 */     boolean $$6 = ($$0.onGround() && $$0.getDeltaMovement().lengthSqr() < 1.0E-7D);
/* 134 */     if ($$6) {
/* 135 */       this.rightWing.yRot = -0.2618F;
/* 136 */       this.rightWing.zRot = 0.0F;
/* 137 */       this.leftWing.xRot = 0.0F;
/* 138 */       this.leftWing.yRot = 0.2618F;
/* 139 */       this.leftWing.zRot = 0.0F;
/*     */       
/* 141 */       this.frontLeg.xRot = 0.0F;
/* 142 */       this.midLeg.xRot = 0.0F;
/* 143 */       this.backLeg.xRot = 0.0F;
/*     */     } else {
/* 145 */       float $$7 = $$3 * 120.32113F * 0.017453292F;
/* 146 */       this.rightWing.yRot = 0.0F;
/* 147 */       this.rightWing.zRot = Mth.cos($$7) * 3.1415927F * 0.15F;
/*     */       
/* 149 */       this.leftWing.xRot = this.rightWing.xRot;
/* 150 */       this.leftWing.yRot = this.rightWing.yRot;
/* 151 */       this.leftWing.zRot = -this.rightWing.zRot;
/*     */       
/* 153 */       this.frontLeg.xRot = 0.7853982F;
/* 154 */       this.midLeg.xRot = 0.7853982F;
/* 155 */       this.backLeg.xRot = 0.7853982F;
/*     */       
/* 157 */       this.bone.xRot = 0.0F;
/* 158 */       this.bone.yRot = 0.0F;
/* 159 */       this.bone.zRot = 0.0F;
/*     */     } 
/*     */     
/* 162 */     if (!$$0.isAngry()) {
/* 163 */       this.bone.xRot = 0.0F;
/* 164 */       this.bone.yRot = 0.0F;
/* 165 */       this.bone.zRot = 0.0F;
/*     */ 
/*     */       
/* 168 */       if (!$$6) {
/* 169 */         float $$8 = Mth.cos($$3 * 0.18F);
/* 170 */         this.bone.xRot = 0.1F + $$8 * 3.1415927F * 0.025F;
/*     */         
/* 172 */         this.leftAntenna.xRot = $$8 * 3.1415927F * 0.03F;
/* 173 */         this.rightAntenna.xRot = $$8 * 3.1415927F * 0.03F;
/*     */         
/* 175 */         this.frontLeg.xRot = -$$8 * 3.1415927F * 0.1F + 0.3926991F;
/* 176 */         this.backLeg.xRot = -$$8 * 3.1415927F * 0.05F + 0.7853982F;
/*     */         
/* 178 */         this.bone.y = 19.0F - Mth.cos($$3 * 0.18F) * 0.9F;
/*     */       } 
/*     */     } 
/*     */     
/* 182 */     if (this.rollAmount > 0.0F) {
/* 183 */       this.bone.xRot = ModelUtils.rotlerpRad(this.bone.xRot, 3.0915928F, this.rollAmount);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/* 189 */     return (Iterable<ModelPart>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 194 */     return (Iterable<ModelPart>)ImmutableList.of(this.bone);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\BeeModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */