/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HorseModel<T extends AbstractHorse>
/*     */   extends AgeableListModel<T>
/*     */ {
/*     */   private static final float DEG_125 = 2.1816616F;
/*     */   private static final float DEG_60 = 1.0471976F;
/*     */   private static final float DEG_45 = 0.7853982F;
/*     */   private static final float DEG_30 = 0.5235988F;
/*     */   private static final float DEG_15 = 0.2617994F;
/*     */   protected static final String HEAD_PARTS = "head_parts";
/*     */   private static final String LEFT_HIND_BABY_LEG = "left_hind_baby_leg";
/*     */   private static final String RIGHT_HIND_BABY_LEG = "right_hind_baby_leg";
/*     */   private static final String LEFT_FRONT_BABY_LEG = "left_front_baby_leg";
/*     */   private static final String RIGHT_FRONT_BABY_LEG = "right_front_baby_leg";
/*     */   private static final String SADDLE = "saddle";
/*     */   private static final String LEFT_SADDLE_MOUTH = "left_saddle_mouth";
/*     */   private static final String LEFT_SADDLE_LINE = "left_saddle_line";
/*     */   private static final String RIGHT_SADDLE_MOUTH = "right_saddle_mouth";
/*     */   private static final String RIGHT_SADDLE_LINE = "right_saddle_line";
/*     */   private static final String HEAD_SADDLE = "head_saddle";
/*     */   private static final String MOUTH_SADDLE_WRAP = "mouth_saddle_wrap";
/*     */   protected final ModelPart body;
/*     */   protected final ModelPart headParts;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart rightHindBabyLeg;
/*     */   private final ModelPart leftHindBabyLeg;
/*     */   private final ModelPart rightFrontBabyLeg;
/*     */   private final ModelPart leftFrontBabyLeg;
/*     */   private final ModelPart tail;
/*     */   private final ModelPart[] saddleParts;
/*     */   private final ModelPart[] ridingParts;
/*     */   
/*     */   public HorseModel(ModelPart $$0) {
/*  51 */     super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
/*  52 */     this.body = $$0.getChild("body");
/*  53 */     this.headParts = $$0.getChild("head_parts");
/*     */     
/*  55 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  56 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*  57 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  58 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*     */     
/*  60 */     this.rightHindBabyLeg = $$0.getChild("right_hind_baby_leg");
/*  61 */     this.leftHindBabyLeg = $$0.getChild("left_hind_baby_leg");
/*  62 */     this.rightFrontBabyLeg = $$0.getChild("right_front_baby_leg");
/*  63 */     this.leftFrontBabyLeg = $$0.getChild("left_front_baby_leg");
/*     */     
/*  65 */     this.tail = this.body.getChild("tail");
/*  66 */     ModelPart $$1 = this.body.getChild("saddle");
/*  67 */     ModelPart $$2 = this.headParts.getChild("left_saddle_mouth");
/*  68 */     ModelPart $$3 = this.headParts.getChild("right_saddle_mouth");
/*  69 */     ModelPart $$4 = this.headParts.getChild("left_saddle_line");
/*  70 */     ModelPart $$5 = this.headParts.getChild("right_saddle_line");
/*  71 */     ModelPart $$6 = this.headParts.getChild("head_saddle");
/*  72 */     ModelPart $$7 = this.headParts.getChild("mouth_saddle_wrap");
/*     */     
/*  74 */     this.saddleParts = new ModelPart[] { $$1, $$2, $$3, $$6, $$7 };
/*  75 */     this.ridingParts = new ModelPart[] { $$4, $$5 };
/*     */   }
/*     */   
/*     */   public static MeshDefinition createBodyMesh(CubeDeformation $$0) {
/*  79 */     MeshDefinition $$1 = new MeshDefinition();
/*  80 */     PartDefinition $$2 = $$1.getRoot();
/*     */     
/*  82 */     PartDefinition $$3 = $$2.addOrReplaceChild("body", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(0, 32).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), 
/*  85 */         PartPose.offset(0.0F, 11.0F, 5.0F));
/*     */ 
/*     */ 
/*     */     
/*  89 */     PartDefinition $$4 = $$2.addOrReplaceChild("head_parts", 
/*  90 */         CubeListBuilder.create()
/*  91 */         .texOffs(0, 35).addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F), 
/*  92 */         PartPose.offsetAndRotation(0.0F, 4.0F, -12.0F, 0.5235988F, 0.0F, 0.0F));
/*     */ 
/*     */     
/*  95 */     PartDefinition $$5 = $$4.addOrReplaceChild("head", 
/*  96 */         CubeListBuilder.create()
/*  97 */         .texOffs(0, 13).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, $$0), PartPose.ZERO);
/*     */     
/*  99 */     $$4.addOrReplaceChild("mane", 
/* 100 */         CubeListBuilder.create()
/* 101 */         .texOffs(56, 36).addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */     
/* 104 */     $$4.addOrReplaceChild("upper_mouth", 
/* 105 */         CubeListBuilder.create()
/* 106 */         .texOffs(0, 25).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/* 110 */     $$2.addOrReplaceChild("left_hind_leg", 
/* 111 */         CubeListBuilder.create()
/* 112 */         .texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, $$0), 
/* 113 */         PartPose.offset(4.0F, 14.0F, 7.0F));
/*     */     
/* 115 */     $$2.addOrReplaceChild("right_hind_leg", 
/* 116 */         CubeListBuilder.create()
/* 117 */         .texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, $$0), 
/* 118 */         PartPose.offset(-4.0F, 14.0F, 7.0F));
/*     */     
/* 120 */     $$2.addOrReplaceChild("left_front_leg", 
/* 121 */         CubeListBuilder.create()
/* 122 */         .texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, $$0), 
/* 123 */         PartPose.offset(4.0F, 14.0F, -12.0F));
/*     */     
/* 125 */     $$2.addOrReplaceChild("right_front_leg", 
/* 126 */         CubeListBuilder.create()
/* 127 */         .texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, $$0), 
/* 128 */         PartPose.offset(-4.0F, 14.0F, -12.0F));
/*     */ 
/*     */ 
/*     */     
/* 132 */     CubeDeformation $$6 = $$0.extend(0.0F, 5.5F, 0.0F);
/* 133 */     $$2.addOrReplaceChild("left_hind_baby_leg", 
/* 134 */         CubeListBuilder.create()
/* 135 */         .texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, $$6), 
/* 136 */         PartPose.offset(4.0F, 14.0F, 7.0F));
/*     */     
/* 138 */     $$2.addOrReplaceChild("right_hind_baby_leg", 
/* 139 */         CubeListBuilder.create()
/* 140 */         .texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, $$6), 
/* 141 */         PartPose.offset(-4.0F, 14.0F, 7.0F));
/*     */     
/* 143 */     $$2.addOrReplaceChild("left_front_baby_leg", 
/* 144 */         CubeListBuilder.create()
/* 145 */         .texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, $$6), 
/* 146 */         PartPose.offset(4.0F, 14.0F, -12.0F));
/*     */     
/* 148 */     $$2.addOrReplaceChild("right_front_baby_leg", 
/* 149 */         CubeListBuilder.create()
/* 150 */         .texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, $$6), 
/* 151 */         PartPose.offset(-4.0F, 14.0F, -12.0F));
/*     */ 
/*     */     
/* 154 */     $$3.addOrReplaceChild("tail", 
/* 155 */         CubeListBuilder.create()
/* 156 */         .texOffs(42, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, $$0), 
/* 157 */         PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, 0.5235988F, 0.0F, 0.0F));
/*     */     
/* 159 */     $$3.addOrReplaceChild("saddle", 
/* 160 */         CubeListBuilder.create()
/* 161 */         .texOffs(26, 0).addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
/*     */ 
/*     */     
/* 164 */     $$4.addOrReplaceChild("left_saddle_mouth", 
/* 165 */         CubeListBuilder.create()
/* 166 */         .texOffs(29, 5).addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */     
/* 169 */     $$4.addOrReplaceChild("right_saddle_mouth", 
/* 170 */         CubeListBuilder.create()
/* 171 */         .texOffs(29, 5).addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */     
/* 174 */     $$4.addOrReplaceChild("left_saddle_line", 
/* 175 */         CubeListBuilder.create()
/* 176 */         .texOffs(32, 2).addBox(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F), 
/* 177 */         PartPose.rotation(-0.5235988F, 0.0F, 0.0F));
/*     */     
/* 179 */     $$4.addOrReplaceChild("right_saddle_line", 
/* 180 */         CubeListBuilder.create()
/* 181 */         .texOffs(32, 2).addBox(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F), 
/* 182 */         PartPose.rotation(-0.5235988F, 0.0F, 0.0F));
/*     */     
/* 184 */     $$4.addOrReplaceChild("head_saddle", 
/* 185 */         CubeListBuilder.create()
/* 186 */         .texOffs(1, 1).addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.22F)), PartPose.ZERO);
/*     */ 
/*     */     
/* 189 */     $$4.addOrReplaceChild("mouth_saddle_wrap", 
/* 190 */         CubeListBuilder.create()
/* 191 */         .texOffs(19, 0).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/* 195 */     $$5.addOrReplaceChild("left_ear", 
/* 196 */         CubeListBuilder.create()
/* 197 */         .texOffs(19, 16).addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO);
/*     */ 
/*     */     
/* 200 */     $$5.addOrReplaceChild("right_ear", 
/* 201 */         CubeListBuilder.create()
/* 202 */         .texOffs(19, 16).addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO);
/*     */ 
/*     */     
/* 205 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 210 */     boolean $$6 = $$0.isSaddled();
/* 211 */     boolean $$7 = $$0.isVehicle();
/*     */     
/* 213 */     for (ModelPart $$8 : this.saddleParts) {
/* 214 */       $$8.visible = $$6;
/*     */     }
/*     */     
/* 217 */     for (ModelPart $$9 : this.ridingParts) {
/* 218 */       $$9.visible = ($$7 && $$6);
/*     */     }
/* 220 */     this.body.y = 11.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<ModelPart> headParts() {
/* 225 */     return (Iterable<ModelPart>)ImmutableList.of(this.headParts);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 230 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightHindBabyLeg, this.leftHindBabyLeg, this.rightFrontBabyLeg, this.leftFrontBabyLeg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 235 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*     */     
/* 237 */     float $$4 = Mth.rotLerp($$3, ((AbstractHorse)$$0).yBodyRotO, ((AbstractHorse)$$0).yBodyRot);
/* 238 */     float $$5 = Mth.rotLerp($$3, ((AbstractHorse)$$0).yHeadRotO, ((AbstractHorse)$$0).yHeadRot);
/* 239 */     float $$6 = Mth.lerp($$3, ((AbstractHorse)$$0).xRotO, $$0.getXRot());
/* 240 */     float $$7 = $$5 - $$4;
/*     */ 
/*     */     
/* 243 */     float $$8 = $$6 * 0.017453292F;
/* 244 */     if ($$7 > 20.0F) {
/* 245 */       $$7 = 20.0F;
/*     */     }
/* 247 */     if ($$7 < -20.0F) {
/* 248 */       $$7 = -20.0F;
/*     */     }
/*     */     
/* 251 */     if ($$2 > 0.2F) {
/* 252 */       $$8 += Mth.cos($$1 * 0.8F) * 0.15F * $$2;
/*     */     }
/*     */     
/* 255 */     float $$9 = $$0.getEatAnim($$3);
/* 256 */     float $$10 = $$0.getStandAnim($$3);
/* 257 */     float $$11 = 1.0F - $$10;
/* 258 */     float $$12 = $$0.getMouthAnim($$3);
/* 259 */     boolean $$13 = (((AbstractHorse)$$0).tailCounter != 0);
/* 260 */     float $$14 = ((AbstractHorse)$$0).tickCount + $$3;
/*     */     
/* 262 */     this.headParts.y = 4.0F;
/* 263 */     this.headParts.z = -12.0F;
/* 264 */     this.body.xRot = 0.0F;
/*     */     
/* 266 */     this.headParts.xRot = 0.5235988F + $$8;
/* 267 */     this.headParts.yRot = $$7 * 0.017453292F;
/*     */     
/* 269 */     float $$15 = $$0.isInWater() ? 0.2F : 1.0F;
/* 270 */     float $$16 = Mth.cos($$15 * $$1 * 0.6662F + 3.1415927F);
/* 271 */     float $$17 = $$16 * 0.8F * $$2;
/*     */ 
/*     */     
/* 274 */     float $$18 = (1.0F - Math.max($$10, $$9)) * (0.5235988F + $$8 + $$12 * Mth.sin($$14) * 0.05F);
/* 275 */     this.headParts.xRot = $$10 * (0.2617994F + $$8) + $$9 * (2.1816616F + Mth.sin($$14) * 0.05F) + $$18;
/* 276 */     this.headParts.yRot = $$10 * $$7 * 0.017453292F + (1.0F - Math.max($$10, $$9)) * this.headParts.yRot;
/*     */     
/* 278 */     this.headParts.y = $$10 * -4.0F + $$9 * 11.0F + (1.0F - Math.max($$10, $$9)) * this.headParts.y;
/* 279 */     this.headParts.z = $$10 * -4.0F + $$9 * -12.0F + (1.0F - Math.max($$10, $$9)) * this.headParts.z;
/*     */     
/* 281 */     this.body.xRot = $$10 * -0.7853982F + $$11 * this.body.xRot;
/*     */     
/* 283 */     float $$19 = 0.2617994F * $$10;
/* 284 */     float $$20 = Mth.cos($$14 * 0.6F + 3.1415927F);
/*     */     
/* 286 */     this.leftFrontLeg.y = 2.0F * $$10 + 14.0F * $$11;
/* 287 */     this.leftFrontLeg.z = -6.0F * $$10 - 10.0F * $$11;
/* 288 */     this.rightFrontLeg.y = this.leftFrontLeg.y;
/* 289 */     this.rightFrontLeg.z = this.leftFrontLeg.z;
/*     */     
/* 291 */     float $$21 = (-1.0471976F + $$20) * $$10 + $$17 * $$11;
/* 292 */     float $$22 = (-1.0471976F - $$20) * $$10 - $$17 * $$11;
/*     */     
/* 294 */     this.leftHindLeg.xRot = $$19 - $$16 * 0.5F * $$2 * $$11;
/* 295 */     this.rightHindLeg.xRot = $$19 + $$16 * 0.5F * $$2 * $$11;
/* 296 */     this.leftFrontLeg.xRot = $$21;
/* 297 */     this.rightFrontLeg.xRot = $$22;
/*     */     
/* 299 */     this.tail.xRot = 0.5235988F + $$2 * 0.75F;
/* 300 */     this.tail.y = -5.0F + $$2;
/* 301 */     this.tail.z = 2.0F + $$2 * 2.0F;
/*     */     
/* 303 */     if ($$13) {
/* 304 */       this.tail.yRot = Mth.cos($$14 * 0.7F);
/*     */     } else {
/* 306 */       this.tail.yRot = 0.0F;
/*     */     } 
/*     */     
/* 309 */     this.rightHindBabyLeg.y = this.rightHindLeg.y;
/* 310 */     this.rightHindBabyLeg.z = this.rightHindLeg.z;
/* 311 */     this.rightHindBabyLeg.xRot = this.rightHindLeg.xRot;
/* 312 */     this.leftHindBabyLeg.y = this.leftHindLeg.y;
/* 313 */     this.leftHindBabyLeg.z = this.leftHindLeg.z;
/* 314 */     this.leftHindBabyLeg.xRot = this.leftHindLeg.xRot;
/* 315 */     this.rightFrontBabyLeg.y = this.rightFrontLeg.y;
/* 316 */     this.rightFrontBabyLeg.z = this.rightFrontLeg.z;
/* 317 */     this.rightFrontBabyLeg.xRot = this.rightFrontLeg.xRot;
/* 318 */     this.leftFrontBabyLeg.y = this.leftFrontLeg.y;
/* 319 */     this.leftFrontBabyLeg.z = this.leftFrontLeg.z;
/* 320 */     this.leftFrontBabyLeg.xRot = this.leftFrontLeg.xRot;
/*     */     
/* 322 */     boolean $$23 = $$0.isBaby();
/*     */     
/* 324 */     this.rightHindLeg.visible = !$$23;
/* 325 */     this.leftHindLeg.visible = !$$23;
/* 326 */     this.rightFrontLeg.visible = !$$23;
/* 327 */     this.leftFrontLeg.visible = !$$23;
/*     */     
/* 329 */     this.rightHindBabyLeg.visible = $$23;
/* 330 */     this.leftHindBabyLeg.visible = $$23;
/* 331 */     this.rightFrontBabyLeg.visible = $$23;
/* 332 */     this.leftFrontBabyLeg.visible = $$23;
/*     */     
/* 334 */     this.body.y = $$23 ? 10.8F : 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\HorseModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */