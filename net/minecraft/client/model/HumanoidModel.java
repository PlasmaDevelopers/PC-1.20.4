/*     */ package net.minecraft.client.model;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ 
/*     */ public class HumanoidModel<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
/*     */   public static final float OVERLAY_SCALE = 0.25F;
/*     */   public static final float HAT_OVERLAY_SCALE = 0.5F;
/*     */   public static final float LEGGINGS_OVERLAY_SCALE = -0.1F;
/*     */   private static final float DUCK_WALK_ROTATION = 0.005F;
/*     */   private static final float SPYGLASS_ARM_ROT_Y = 0.2617994F;
/*     */   private static final float SPYGLASS_ARM_ROT_X = 1.9198622F;
/*     */   private static final float SPYGLASS_ARM_CROUCH_ROT_X = 0.2617994F;
/*     */   private static final float HIGHEST_SHIELD_BLOCKING_ANGLE = -1.3962634F;
/*     */   private static final float LOWEST_SHIELD_BLOCKING_ANGLE = 0.43633232F;
/*     */   private static final float HORIZONTAL_SHIELD_MOVEMENT_LIMIT = 0.5235988F;
/*     */   public static final float TOOT_HORN_XROT_BASE = 1.4835298F;
/*     */   public static final float TOOT_HORN_YROT_BASE = 0.5235988F;
/*     */   public final ModelPart head;
/*     */   public final ModelPart hat;
/*     */   public final ModelPart body;
/*     */   public final ModelPart rightArm;
/*     */   public final ModelPart leftArm;
/*     */   public final ModelPart rightLeg;
/*     */   public final ModelPart leftLeg;
/*     */   
/*     */   public enum ArmPose {
/*  37 */     EMPTY(false),
/*  38 */     ITEM(false),
/*  39 */     BLOCK(false),
/*  40 */     BOW_AND_ARROW(true),
/*  41 */     THROW_SPEAR(false),
/*  42 */     CROSSBOW_CHARGE(true),
/*  43 */     CROSSBOW_HOLD(true),
/*  44 */     SPYGLASS(false),
/*  45 */     TOOT_HORN(false),
/*  46 */     BRUSH(false);
/*     */     
/*     */     private final boolean twoHanded;
/*     */ 
/*     */     
/*     */     ArmPose(boolean $$0) {
/*  52 */       this.twoHanded = $$0;
/*     */     }
/*     */     
/*     */     public boolean isTwoHanded() {
/*  56 */       return this.twoHanded;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public ArmPose leftArmPose = ArmPose.EMPTY;
/*  69 */   public ArmPose rightArmPose = ArmPose.EMPTY;
/*     */   public boolean crouching;
/*     */   public float swimAmount;
/*     */   
/*     */   public HumanoidModel(ModelPart $$0) {
/*  74 */     this($$0, RenderType::entityCutoutNoCull);
/*     */   }
/*     */   
/*     */   public HumanoidModel(ModelPart $$0, Function<ResourceLocation, RenderType> $$1) {
/*  78 */     super($$1, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
/*  79 */     this.head = $$0.getChild("head");
/*  80 */     this.hat = $$0.getChild("hat");
/*  81 */     this.body = $$0.getChild("body");
/*  82 */     this.rightArm = $$0.getChild("right_arm");
/*  83 */     this.leftArm = $$0.getChild("left_arm");
/*  84 */     this.rightLeg = $$0.getChild("right_leg");
/*  85 */     this.leftLeg = $$0.getChild("left_leg");
/*     */   }
/*     */   
/*     */   public static MeshDefinition createMesh(CubeDeformation $$0, float $$1) {
/*  89 */     MeshDefinition $$2 = new MeshDefinition();
/*  90 */     PartDefinition $$3 = $$2.getRoot();
/*  91 */     $$3.addOrReplaceChild("head", 
/*  92 */         CubeListBuilder.create()
/*  93 */         .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$0), 
/*  94 */         PartPose.offset(0.0F, 0.0F + $$1, 0.0F));
/*     */     
/*  96 */     $$3.addOrReplaceChild("hat", 
/*  97 */         CubeListBuilder.create()
/*  98 */         .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$0.extend(0.5F)), 
/*  99 */         PartPose.offset(0.0F, 0.0F + $$1, 0.0F));
/*     */     
/* 101 */     $$3.addOrReplaceChild("body", 
/* 102 */         CubeListBuilder.create()
/* 103 */         .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, $$0), 
/* 104 */         PartPose.offset(0.0F, 0.0F + $$1, 0.0F));
/*     */     
/* 106 */     $$3.addOrReplaceChild("right_arm", 
/* 107 */         CubeListBuilder.create()
/* 108 */         .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/* 109 */         PartPose.offset(-5.0F, 2.0F + $$1, 0.0F));
/*     */     
/* 111 */     $$3.addOrReplaceChild("left_arm", 
/* 112 */         CubeListBuilder.create()
/* 113 */         .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/* 114 */         PartPose.offset(5.0F, 2.0F + $$1, 0.0F));
/*     */     
/* 116 */     $$3.addOrReplaceChild("right_leg", 
/* 117 */         CubeListBuilder.create()
/* 118 */         .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/* 119 */         PartPose.offset(-1.9F, 12.0F + $$1, 0.0F));
/*     */     
/* 121 */     $$3.addOrReplaceChild("left_leg", 
/* 122 */         CubeListBuilder.create()
/* 123 */         .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/* 124 */         PartPose.offset(1.9F, 12.0F + $$1, 0.0F));
/*     */     
/* 126 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/* 131 */     return (Iterable<ModelPart>)ImmutableList.of(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 136 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg, this.hat);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 141 */     this.swimAmount = $$0.getSwimAmount($$3);
/* 142 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 147 */     boolean $$6 = ($$0.getFallFlyingTicks() > 4);
/* 148 */     boolean $$7 = $$0.isVisuallySwimming();
/*     */     
/* 150 */     this.head.yRot = $$4 * 0.017453292F;
/* 151 */     if ($$6) {
/* 152 */       this.head.xRot = -0.7853982F;
/* 153 */     } else if (this.swimAmount > 0.0F) {
/* 154 */       if ($$7) {
/* 155 */         this.head.xRot = rotlerpRad(this.swimAmount, this.head.xRot, -0.7853982F);
/*     */       } else {
/* 157 */         this.head.xRot = rotlerpRad(this.swimAmount, this.head.xRot, $$5 * 0.017453292F);
/*     */       } 
/*     */     } else {
/* 160 */       this.head.xRot = $$5 * 0.017453292F;
/*     */     } 
/*     */     
/* 163 */     this.body.yRot = 0.0F;
/* 164 */     this.rightArm.z = 0.0F;
/* 165 */     this.rightArm.x = -5.0F;
/* 166 */     this.leftArm.z = 0.0F;
/* 167 */     this.leftArm.x = 5.0F;
/*     */     
/* 169 */     float $$8 = 1.0F;
/* 170 */     if ($$6) {
/* 171 */       $$8 = (float)$$0.getDeltaMovement().lengthSqr();
/* 172 */       $$8 /= 0.2F;
/* 173 */       $$8 *= $$8 * $$8;
/*     */     } 
/* 175 */     if ($$8 < 1.0F) {
/* 176 */       $$8 = 1.0F;
/*     */     }
/*     */     
/* 179 */     this.rightArm.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 2.0F * $$2 * 0.5F / $$8;
/* 180 */     this.leftArm.xRot = Mth.cos($$1 * 0.6662F) * 2.0F * $$2 * 0.5F / $$8;
/*     */     
/* 182 */     this.rightArm.zRot = 0.0F;
/* 183 */     this.leftArm.zRot = 0.0F;
/*     */     
/* 185 */     this.rightLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2 / $$8;
/* 186 */     this.leftLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2 / $$8;
/* 187 */     this.rightLeg.yRot = 0.005F;
/* 188 */     this.leftLeg.yRot = -0.005F;
/* 189 */     this.rightLeg.zRot = 0.005F;
/* 190 */     this.leftLeg.zRot = -0.005F;
/*     */     
/* 192 */     if (this.riding) {
/* 193 */       this.rightArm.xRot += -0.62831855F;
/* 194 */       this.leftArm.xRot += -0.62831855F;
/*     */       
/* 196 */       this.rightLeg.xRot = -1.4137167F;
/* 197 */       this.rightLeg.yRot = 0.31415927F;
/* 198 */       this.rightLeg.zRot = 0.07853982F;
/*     */       
/* 200 */       this.leftLeg.xRot = -1.4137167F;
/* 201 */       this.leftLeg.yRot = -0.31415927F;
/* 202 */       this.leftLeg.zRot = -0.07853982F;
/*     */     } 
/*     */     
/* 205 */     this.rightArm.yRot = 0.0F;
/* 206 */     this.leftArm.yRot = 0.0F;
/*     */ 
/*     */     
/* 209 */     boolean $$9 = ($$0.getMainArm() == HumanoidArm.RIGHT);
/* 210 */     if ($$0.isUsingItem()) {
/* 211 */       boolean $$10 = ($$0.getUsedItemHand() == InteractionHand.MAIN_HAND);
/* 212 */       if ($$10 == $$9) {
/* 213 */         poseRightArm($$0);
/*     */       } else {
/* 215 */         poseLeftArm($$0);
/*     */       } 
/*     */     } else {
/*     */       
/* 219 */       boolean $$11 = $$9 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
/* 220 */       if ($$9 != $$11) {
/* 221 */         poseLeftArm($$0);
/* 222 */         poseRightArm($$0);
/*     */       } else {
/* 224 */         poseRightArm($$0);
/* 225 */         poseLeftArm($$0);
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     setupAttackAnimation($$0, $$3);
/*     */     
/* 231 */     if (this.crouching) {
/* 232 */       this.body.xRot = 0.5F;
/* 233 */       this.rightArm.xRot += 0.4F;
/* 234 */       this.leftArm.xRot += 0.4F;
/* 235 */       this.rightLeg.z = 4.0F;
/* 236 */       this.leftLeg.z = 4.0F;
/* 237 */       this.rightLeg.y = 12.2F;
/* 238 */       this.leftLeg.y = 12.2F;
/* 239 */       this.head.y = 4.2F;
/* 240 */       this.body.y = 3.2F;
/* 241 */       this.leftArm.y = 5.2F;
/* 242 */       this.rightArm.y = 5.2F;
/*     */     } else {
/* 244 */       this.body.xRot = 0.0F;
/* 245 */       this.rightLeg.z = 0.0F;
/* 246 */       this.leftLeg.z = 0.0F;
/* 247 */       this.rightLeg.y = 12.0F;
/* 248 */       this.leftLeg.y = 12.0F;
/* 249 */       this.head.y = 0.0F;
/* 250 */       this.body.y = 0.0F;
/* 251 */       this.leftArm.y = 2.0F;
/* 252 */       this.rightArm.y = 2.0F;
/*     */     } 
/*     */     
/* 255 */     if (this.rightArmPose != ArmPose.SPYGLASS) {
/* 256 */       AnimationUtils.bobModelPart(this.rightArm, $$3, 1.0F);
/*     */     }
/* 258 */     if (this.leftArmPose != ArmPose.SPYGLASS) {
/* 259 */       AnimationUtils.bobModelPart(this.leftArm, $$3, -1.0F);
/*     */     }
/*     */     
/* 262 */     if (this.swimAmount > 0.0F) {
/* 263 */       float $$12 = $$1 % 26.0F;
/*     */ 
/*     */       
/* 266 */       HumanoidArm $$13 = getAttackArm($$0);
/* 267 */       float $$14 = ($$13 == HumanoidArm.RIGHT && this.attackTime > 0.0F) ? 0.0F : this.swimAmount;
/* 268 */       float $$15 = ($$13 == HumanoidArm.LEFT && this.attackTime > 0.0F) ? 0.0F : this.swimAmount;
/*     */       
/* 270 */       if (!$$0.isUsingItem()) {
/* 271 */         if ($$12 < 14.0F) {
/* 272 */           this.leftArm.xRot = rotlerpRad($$15, this.leftArm.xRot, 0.0F);
/* 273 */           this.rightArm.xRot = Mth.lerp($$14, this.rightArm.xRot, 0.0F);
/*     */           
/* 275 */           this.leftArm.yRot = rotlerpRad($$15, this.leftArm.yRot, 3.1415927F);
/* 276 */           this.rightArm.yRot = Mth.lerp($$14, this.rightArm.yRot, 3.1415927F);
/*     */           
/* 278 */           this.leftArm.zRot = rotlerpRad($$15, this.leftArm.zRot, 3.1415927F + 1.8707964F * quadraticArmUpdate($$12) / quadraticArmUpdate(14.0F));
/* 279 */           this.rightArm.zRot = Mth.lerp($$14, this.rightArm.zRot, 3.1415927F - 1.8707964F * quadraticArmUpdate($$12) / quadraticArmUpdate(14.0F));
/* 280 */         } else if ($$12 >= 14.0F && $$12 < 22.0F) {
/* 281 */           float $$16 = ($$12 - 14.0F) / 8.0F;
/*     */           
/* 283 */           this.leftArm.xRot = rotlerpRad($$15, this.leftArm.xRot, 1.5707964F * $$16);
/* 284 */           this.rightArm.xRot = Mth.lerp($$14, this.rightArm.xRot, 1.5707964F * $$16);
/*     */           
/* 286 */           this.leftArm.yRot = rotlerpRad($$15, this.leftArm.yRot, 3.1415927F);
/* 287 */           this.rightArm.yRot = Mth.lerp($$14, this.rightArm.yRot, 3.1415927F);
/*     */           
/* 289 */           this.leftArm.zRot = rotlerpRad($$15, this.leftArm.zRot, 5.012389F - 1.8707964F * $$16);
/* 290 */           this.rightArm.zRot = Mth.lerp($$14, this.rightArm.zRot, 1.2707963F + 1.8707964F * $$16);
/* 291 */         } else if ($$12 >= 22.0F && $$12 < 26.0F) {
/* 292 */           float $$17 = ($$12 - 22.0F) / 4.0F;
/*     */           
/* 294 */           this.leftArm.xRot = rotlerpRad($$15, this.leftArm.xRot, 1.5707964F - 1.5707964F * $$17);
/* 295 */           this.rightArm.xRot = Mth.lerp($$14, this.rightArm.xRot, 1.5707964F - 1.5707964F * $$17);
/*     */           
/* 297 */           this.leftArm.yRot = rotlerpRad($$15, this.leftArm.yRot, 3.1415927F);
/* 298 */           this.rightArm.yRot = Mth.lerp($$14, this.rightArm.yRot, 3.1415927F);
/*     */           
/* 300 */           this.leftArm.zRot = rotlerpRad($$15, this.leftArm.zRot, 3.1415927F);
/* 301 */           this.rightArm.zRot = Mth.lerp($$14, this.rightArm.zRot, 3.1415927F);
/*     */         } 
/*     */       }
/*     */       
/* 305 */       float $$18 = 0.3F;
/* 306 */       float $$19 = 0.33333334F;
/* 307 */       this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos($$1 * 0.33333334F + 3.1415927F));
/* 308 */       this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos($$1 * 0.33333334F));
/*     */     } 
/*     */     
/* 311 */     this.hat.copyFrom(this.head);
/*     */   }
/*     */   
/*     */   private void poseRightArm(T $$0) {
/* 315 */     switch (this.rightArmPose) {
/*     */       case EMPTY:
/* 317 */         this.rightArm.yRot = 0.0F;
/*     */         break;
/*     */       case BLOCK:
/* 320 */         poseBlockingArm(this.rightArm, true);
/*     */         break;
/*     */       case ITEM:
/* 323 */         this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.31415927F;
/* 324 */         this.rightArm.yRot = 0.0F;
/*     */         break;
/*     */       case THROW_SPEAR:
/* 327 */         this.rightArm.xRot = this.rightArm.xRot * 0.5F - 3.1415927F;
/* 328 */         this.rightArm.yRot = 0.0F;
/*     */         break;
/*     */       case BOW_AND_ARROW:
/* 331 */         this.rightArm.yRot = -0.1F + this.head.yRot;
/* 332 */         this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
/* 333 */         this.rightArm.xRot = -1.5707964F + this.head.xRot;
/* 334 */         this.leftArm.xRot = -1.5707964F + this.head.xRot;
/*     */         break;
/*     */       case CROSSBOW_CHARGE:
/* 337 */         AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, (LivingEntity)$$0, true);
/*     */         break;
/*     */       case CROSSBOW_HOLD:
/* 340 */         AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
/*     */         break;
/*     */       case BRUSH:
/* 343 */         this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.62831855F;
/* 344 */         this.rightArm.yRot = 0.0F;
/*     */         break;
/*     */       case SPYGLASS:
/* 347 */         this.rightArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - ($$0.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
/* 348 */         this.head.yRot -= 0.2617994F;
/*     */         break;
/*     */       case TOOT_HORN:
/* 351 */         this.rightArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
/* 352 */         this.head.yRot -= 0.5235988F;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void poseLeftArm(T $$0) {
/* 358 */     switch (this.leftArmPose) {
/*     */       case EMPTY:
/* 360 */         this.leftArm.yRot = 0.0F;
/*     */         break;
/*     */       case BLOCK:
/* 363 */         poseBlockingArm(this.leftArm, false);
/*     */         break;
/*     */       case ITEM:
/* 366 */         this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.31415927F;
/* 367 */         this.leftArm.yRot = 0.0F;
/*     */         break;
/*     */       case THROW_SPEAR:
/* 370 */         this.leftArm.xRot = this.leftArm.xRot * 0.5F - 3.1415927F;
/* 371 */         this.leftArm.yRot = 0.0F;
/*     */         break;
/*     */       case BOW_AND_ARROW:
/* 374 */         this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
/* 375 */         this.leftArm.yRot = 0.1F + this.head.yRot;
/* 376 */         this.rightArm.xRot = -1.5707964F + this.head.xRot;
/* 377 */         this.leftArm.xRot = -1.5707964F + this.head.xRot;
/*     */         break;
/*     */       case CROSSBOW_CHARGE:
/* 380 */         AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, (LivingEntity)$$0, false);
/*     */         break;
/*     */       case CROSSBOW_HOLD:
/* 383 */         AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
/*     */         break;
/*     */       case BRUSH:
/* 386 */         this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.62831855F;
/* 387 */         this.leftArm.yRot = 0.0F;
/*     */         break;
/*     */       case SPYGLASS:
/* 390 */         this.leftArm.xRot = Mth.clamp(this.head.xRot - 1.9198622F - ($$0.isCrouching() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
/* 391 */         this.head.yRot += 0.2617994F;
/*     */         break;
/*     */       case TOOT_HORN:
/* 394 */         this.leftArm.xRot = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
/* 395 */         this.head.yRot += 0.5235988F;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void poseBlockingArm(ModelPart $$0, boolean $$1) {
/* 401 */     $$0.xRot = $$0.xRot * 0.5F - 0.9424779F + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
/* 402 */     $$0.yRot = ($$1 ? -30.0F : 30.0F) * 0.017453292F + Mth.clamp(this.head.yRot, -0.5235988F, 0.5235988F);
/*     */   }
/*     */   
/*     */   protected void setupAttackAnimation(T $$0, float $$1) {
/* 406 */     if (this.attackTime <= 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/* 410 */     HumanoidArm $$2 = getAttackArm($$0);
/* 411 */     ModelPart $$3 = getArm($$2);
/*     */     
/* 413 */     float $$4 = this.attackTime;
/* 414 */     this.body.yRot = Mth.sin(Mth.sqrt($$4) * 6.2831855F) * 0.2F;
/* 415 */     if ($$2 == HumanoidArm.LEFT) {
/* 416 */       this.body.yRot *= -1.0F;
/*     */     }
/* 418 */     this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
/* 419 */     this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F;
/* 420 */     this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
/* 421 */     this.leftArm.x = Mth.cos(this.body.yRot) * 5.0F;
/* 422 */     this.rightArm.yRot += this.body.yRot;
/* 423 */     this.leftArm.yRot += this.body.yRot;
/* 424 */     this.leftArm.xRot += this.body.yRot;
/*     */     
/* 426 */     $$4 = 1.0F - this.attackTime;
/* 427 */     $$4 *= $$4;
/* 428 */     $$4 *= $$4;
/* 429 */     $$4 = 1.0F - $$4;
/* 430 */     float $$5 = Mth.sin($$4 * 3.1415927F);
/* 431 */     float $$6 = Mth.sin(this.attackTime * 3.1415927F) * -(this.head.xRot - 0.7F) * 0.75F;
/* 432 */     $$3.xRot -= $$5 * 1.2F + $$6;
/* 433 */     $$3.yRot += this.body.yRot * 2.0F;
/* 434 */     $$3.zRot += Mth.sin(this.attackTime * 3.1415927F) * -0.4F;
/*     */   }
/*     */   
/*     */   protected float rotlerpRad(float $$0, float $$1, float $$2) {
/* 438 */     float $$3 = ($$2 - $$1) % 6.2831855F;
/* 439 */     if ($$3 < -3.1415927F) {
/* 440 */       $$3 += 6.2831855F;
/*     */     }
/* 442 */     if ($$3 >= 3.1415927F) {
/* 443 */       $$3 -= 6.2831855F;
/*     */     }
/* 445 */     return $$1 + $$0 * $$3;
/*     */   }
/*     */   
/*     */   private float quadraticArmUpdate(float $$0) {
/* 449 */     return -65.0F * $$0 + $$0 * $$0;
/*     */   }
/*     */   
/*     */   public void copyPropertiesTo(HumanoidModel<T> $$0) {
/* 453 */     copyPropertiesTo($$0);
/*     */     
/* 455 */     $$0.leftArmPose = this.leftArmPose;
/* 456 */     $$0.rightArmPose = this.rightArmPose;
/* 457 */     $$0.crouching = this.crouching;
/*     */     
/* 459 */     $$0.head.copyFrom(this.head);
/* 460 */     $$0.hat.copyFrom(this.hat);
/* 461 */     $$0.body.copyFrom(this.body);
/* 462 */     $$0.rightArm.copyFrom(this.rightArm);
/* 463 */     $$0.leftArm.copyFrom(this.leftArm);
/* 464 */     $$0.rightLeg.copyFrom(this.rightLeg);
/* 465 */     $$0.leftLeg.copyFrom(this.leftLeg);
/*     */   }
/*     */   
/*     */   public void setAllVisible(boolean $$0) {
/* 469 */     this.head.visible = $$0;
/* 470 */     this.hat.visible = $$0;
/* 471 */     this.body.visible = $$0;
/* 472 */     this.rightArm.visible = $$0;
/* 473 */     this.leftArm.visible = $$0;
/* 474 */     this.rightLeg.visible = $$0;
/* 475 */     this.leftLeg.visible = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToHand(HumanoidArm $$0, PoseStack $$1) {
/* 480 */     getArm($$0).translateAndRotate($$1);
/*     */   }
/*     */   
/*     */   protected ModelPart getArm(HumanoidArm $$0) {
/* 484 */     if ($$0 == HumanoidArm.LEFT) {
/* 485 */       return this.leftArm;
/*     */     }
/* 487 */     return this.rightArm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelPart getHead() {
/* 493 */     return this.head;
/*     */   }
/*     */   
/*     */   private HumanoidArm getAttackArm(T $$0) {
/* 497 */     HumanoidArm $$1 = $$0.getMainArm();
/* 498 */     return (((LivingEntity)$$0).swingingArm == InteractionHand.MAIN_HAND) ? $$1 : $$1.getOpposite();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\HumanoidModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */