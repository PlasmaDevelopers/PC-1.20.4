/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.EntityModel;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragonModel
/*     */   extends EntityModel<EnderDragon>
/*     */ {
/*     */   private final ModelPart head;
/*     */   private final ModelPart neck;
/*     */   private final ModelPart jaw;
/*     */   private final ModelPart body;
/*     */   private final ModelPart leftWing;
/*     */   private final ModelPart leftWingTip;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart leftFrontLegTip;
/*     */   private final ModelPart leftFrontFoot;
/*     */   private final ModelPart leftRearLeg;
/*     */   private final ModelPart leftRearLegTip;
/*     */   private final ModelPart leftRearFoot;
/*     */   private final ModelPart rightWing;
/*     */   private final ModelPart rightWingTip;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart rightFrontLegTip;
/*     */   private final ModelPart rightFrontFoot;
/*     */   private final ModelPart rightRearLeg;
/*     */   private final ModelPart rightRearLegTip;
/*     */   private final ModelPart rightRearFoot;
/*     */   @Nullable
/*     */   private EnderDragon entity;
/*     */   private float a;
/*     */   
/*     */   public DragonModel(ModelPart $$0) {
/* 400 */     this.head = $$0.getChild("head");
/* 401 */     this.jaw = this.head.getChild("jaw");
/* 402 */     this.neck = $$0.getChild("neck");
/* 403 */     this.body = $$0.getChild("body");
/* 404 */     this.leftWing = $$0.getChild("left_wing");
/* 405 */     this.leftWingTip = this.leftWing.getChild("left_wing_tip");
/* 406 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/* 407 */     this.leftFrontLegTip = this.leftFrontLeg.getChild("left_front_leg_tip");
/* 408 */     this.leftFrontFoot = this.leftFrontLegTip.getChild("left_front_foot");
/* 409 */     this.leftRearLeg = $$0.getChild("left_hind_leg");
/* 410 */     this.leftRearLegTip = this.leftRearLeg.getChild("left_hind_leg_tip");
/* 411 */     this.leftRearFoot = this.leftRearLegTip.getChild("left_hind_foot");
/* 412 */     this.rightWing = $$0.getChild("right_wing");
/* 413 */     this.rightWingTip = this.rightWing.getChild("right_wing_tip");
/* 414 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/* 415 */     this.rightFrontLegTip = this.rightFrontLeg.getChild("right_front_leg_tip");
/* 416 */     this.rightFrontFoot = this.rightFrontLegTip.getChild("right_front_foot");
/* 417 */     this.rightRearLeg = $$0.getChild("right_hind_leg");
/* 418 */     this.rightRearLegTip = this.rightRearLeg.getChild("right_hind_leg_tip");
/* 419 */     this.rightRearFoot = this.rightRearLegTip.getChild("right_hind_foot");
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(EnderDragon $$0, float $$1, float $$2, float $$3) {
/* 424 */     this.entity = $$0;
/* 425 */     this.a = $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnim(EnderDragon $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {}
/*     */ 
/*     */   
/*     */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 434 */     $$0.pushPose();
/* 435 */     float $$8 = Mth.lerp(this.a, this.entity.oFlapTime, this.entity.flapTime);
/* 436 */     this.jaw.xRot = (float)(Math.sin(($$8 * 6.2831855F)) + 1.0D) * 0.2F;
/*     */     
/* 438 */     float $$9 = (float)(Math.sin(($$8 * 6.2831855F - 1.0F)) + 1.0D);
/* 439 */     $$9 = ($$9 * $$9 + $$9 * 2.0F) * 0.05F;
/*     */     
/* 441 */     $$0.translate(0.0F, $$9 - 2.0F, -3.0F);
/* 442 */     $$0.mulPose(Axis.XP.rotationDegrees($$9 * 2.0F));
/*     */     
/* 444 */     float $$10 = 0.0F;
/* 445 */     float $$11 = 20.0F;
/* 446 */     float $$12 = -12.0F;
/*     */     
/* 448 */     float $$13 = 1.5F;
/*     */     
/* 450 */     double[] $$14 = this.entity.getLatencyPos(6, this.a);
/*     */     
/* 452 */     float $$15 = Mth.wrapDegrees((float)(this.entity.getLatencyPos(5, this.a)[0] - this.entity.getLatencyPos(10, this.a)[0]));
/* 453 */     float $$16 = Mth.wrapDegrees((float)(this.entity.getLatencyPos(5, this.a)[0] + ($$15 / 2.0F)));
/*     */     
/* 455 */     float $$17 = $$8 * 6.2831855F;
/* 456 */     for (int $$18 = 0; $$18 < 5; $$18++) {
/* 457 */       double[] $$19 = this.entity.getLatencyPos(5 - $$18, this.a);
/* 458 */       float $$20 = (float)Math.cos(($$18 * 0.45F + $$17)) * 0.15F;
/* 459 */       this.neck.yRot = Mth.wrapDegrees((float)($$19[0] - $$14[0])) * 0.017453292F * 1.5F;
/* 460 */       this.neck.xRot = $$20 + this.entity.getHeadPartYOffset($$18, $$14, $$19) * 0.017453292F * 1.5F * 5.0F;
/* 461 */       this.neck.zRot = -Mth.wrapDegrees((float)($$19[0] - $$16)) * 0.017453292F * 1.5F;
/*     */       
/* 463 */       this.neck.y = $$11;
/* 464 */       this.neck.z = $$12;
/* 465 */       this.neck.x = $$10;
/* 466 */       $$11 += Mth.sin(this.neck.xRot) * 10.0F;
/* 467 */       $$12 -= Mth.cos(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 468 */       $$10 -= Mth.sin(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 469 */       this.neck.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/*     */     } 
/*     */     
/* 472 */     this.head.y = $$11;
/* 473 */     this.head.z = $$12;
/* 474 */     this.head.x = $$10;
/* 475 */     double[] $$21 = this.entity.getLatencyPos(0, this.a);
/* 476 */     this.head.yRot = Mth.wrapDegrees((float)($$21[0] - $$14[0])) * 0.017453292F;
/* 477 */     this.head.xRot = Mth.wrapDegrees(this.entity.getHeadPartYOffset(6, $$14, $$21)) * 0.017453292F * 1.5F * 5.0F;
/* 478 */     this.head.zRot = -Mth.wrapDegrees((float)($$21[0] - $$16)) * 0.017453292F;
/* 479 */     this.head.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/* 480 */     $$0.pushPose();
/* 481 */     $$0.translate(0.0F, 1.0F, 0.0F);
/* 482 */     $$0.mulPose(Axis.ZP.rotationDegrees(-$$15 * 1.5F));
/* 483 */     $$0.translate(0.0F, -1.0F, 0.0F);
/* 484 */     this.body.zRot = 0.0F;
/* 485 */     this.body.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/*     */     
/* 487 */     float $$22 = $$8 * 6.2831855F;
/* 488 */     this.leftWing.xRot = 0.125F - (float)Math.cos($$22) * 0.2F;
/* 489 */     this.leftWing.yRot = -0.25F;
/* 490 */     this.leftWing.zRot = -((float)(Math.sin($$22) + 0.125D)) * 0.8F;
/* 491 */     this.leftWingTip.zRot = (float)(Math.sin(($$22 + 2.0F)) + 0.5D) * 0.75F;
/*     */     
/* 493 */     this.rightWing.xRot = this.leftWing.xRot;
/* 494 */     this.rightWing.yRot = -this.leftWing.yRot;
/* 495 */     this.rightWing.zRot = -this.leftWing.zRot;
/* 496 */     this.rightWingTip.zRot = -this.leftWingTip.zRot;
/*     */     
/* 498 */     renderSide($$0, $$1, $$2, $$3, $$9, this.leftWing, this.leftFrontLeg, this.leftFrontLegTip, this.leftFrontFoot, this.leftRearLeg, this.leftRearLegTip, this.leftRearFoot, $$7);
/* 499 */     renderSide($$0, $$1, $$2, $$3, $$9, this.rightWing, this.rightFrontLeg, this.rightFrontLegTip, this.rightFrontFoot, this.rightRearLeg, this.rightRearLegTip, this.rightRearFoot, $$7);
/*     */     
/* 501 */     $$0.popPose();
/*     */     
/* 503 */     float $$23 = -Mth.sin($$8 * 6.2831855F) * 0.0F;
/* 504 */     $$17 = $$8 * 6.2831855F;
/* 505 */     $$11 = 10.0F;
/* 506 */     $$12 = 60.0F;
/* 507 */     $$10 = 0.0F;
/* 508 */     $$14 = this.entity.getLatencyPos(11, this.a);
/* 509 */     for (int $$24 = 0; $$24 < 12; $$24++) {
/* 510 */       $$21 = this.entity.getLatencyPos(12 + $$24, this.a);
/* 511 */       $$23 += Mth.sin($$24 * 0.45F + $$17) * 0.05F;
/* 512 */       this.neck.yRot = (Mth.wrapDegrees((float)($$21[0] - $$14[0])) * 1.5F + 180.0F) * 0.017453292F;
/* 513 */       this.neck.xRot = $$23 + (float)($$21[1] - $$14[1]) * 0.017453292F * 1.5F * 5.0F;
/* 514 */       this.neck.zRot = Mth.wrapDegrees((float)($$21[0] - $$16)) * 0.017453292F * 1.5F;
/* 515 */       this.neck.y = $$11;
/* 516 */       this.neck.z = $$12;
/* 517 */       this.neck.x = $$10;
/* 518 */       $$11 += Mth.sin(this.neck.xRot) * 10.0F;
/* 519 */       $$12 -= Mth.cos(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 520 */       $$10 -= Mth.sin(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 521 */       this.neck.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/*     */     } 
/* 523 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   private void renderSide(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, ModelPart $$5, ModelPart $$6, ModelPart $$7, ModelPart $$8, ModelPart $$9, ModelPart $$10, ModelPart $$11, float $$12) {
/* 527 */     $$9.xRot = 1.0F + $$4 * 0.1F;
/* 528 */     $$10.xRot = 0.5F + $$4 * 0.1F;
/* 529 */     $$11.xRot = 0.75F + $$4 * 0.1F;
/*     */     
/* 531 */     $$6.xRot = 1.3F + $$4 * 0.1F;
/* 532 */     $$7.xRot = -0.5F - $$4 * 0.1F;
/* 533 */     $$8.xRot = 0.75F + $$4 * 0.1F;
/*     */     
/* 535 */     $$5.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$12);
/* 536 */     $$6.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$12);
/* 537 */     $$9.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$12);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EnderDragonRenderer$DragonModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */