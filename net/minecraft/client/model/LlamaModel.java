/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*     */ 
/*     */ public class LlamaModel<T extends AbstractChestedHorse>
/*     */   extends EntityModel<T> {
/*     */   private final ModelPart head;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart rightChest;
/*     */   private final ModelPart leftChest;
/*     */   
/*     */   public LlamaModel(ModelPart $$0) {
/*  29 */     this.head = $$0.getChild("head");
/*  30 */     this.body = $$0.getChild("body");
/*  31 */     this.rightChest = $$0.getChild("right_chest");
/*  32 */     this.leftChest = $$0.getChild("left_chest");
/*  33 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  34 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*  35 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  36 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
/*  40 */     MeshDefinition $$1 = new MeshDefinition();
/*  41 */     PartDefinition $$2 = $$1.getRoot();
/*     */     
/*  43 */     $$2.addOrReplaceChild("head", 
/*  44 */         CubeListBuilder.create()
/*  45 */         .texOffs(0, 0).addBox(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, $$0)
/*  46 */         .texOffs(0, 14).addBox("neck", -4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, $$0)
/*  47 */         .texOffs(17, 0).addBox("ear", -4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, $$0)
/*  48 */         .texOffs(17, 0).addBox("ear", 1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, $$0), 
/*  49 */         PartPose.offset(0.0F, 7.0F, -6.0F));
/*     */     
/*  51 */     $$2.addOrReplaceChild("body", 
/*  52 */         CubeListBuilder.create()
/*  53 */         .texOffs(29, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, $$0), 
/*  54 */         PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  56 */     $$2.addOrReplaceChild("right_chest", 
/*  57 */         CubeListBuilder.create()
/*  58 */         .texOffs(45, 28).addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, $$0), 
/*  59 */         PartPose.offsetAndRotation(-8.5F, 3.0F, 3.0F, 0.0F, 1.5707964F, 0.0F));
/*     */     
/*  61 */     $$2.addOrReplaceChild("left_chest", 
/*  62 */         CubeListBuilder.create()
/*  63 */         .texOffs(45, 41).addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, $$0), 
/*  64 */         PartPose.offsetAndRotation(5.5F, 3.0F, 3.0F, 0.0F, 1.5707964F, 0.0F));
/*     */     
/*  66 */     int $$3 = 4;
/*  67 */     int $$4 = 14;
/*     */     
/*  69 */     CubeListBuilder $$5 = CubeListBuilder.create().texOffs(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, $$0);
/*  70 */     $$2.addOrReplaceChild("right_hind_leg", $$5, PartPose.offset(-3.5F, 10.0F, 6.0F));
/*  71 */     $$2.addOrReplaceChild("left_hind_leg", $$5, PartPose.offset(3.5F, 10.0F, 6.0F));
/*  72 */     $$2.addOrReplaceChild("right_front_leg", $$5, PartPose.offset(-3.5F, 10.0F, -5.0F));
/*  73 */     $$2.addOrReplaceChild("left_front_leg", $$5, PartPose.offset(3.5F, 10.0F, -5.0F));
/*     */     
/*  75 */     return LayerDefinition.create($$1, 128, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  80 */     this.head.xRot = $$5 * 0.017453292F;
/*  81 */     this.head.yRot = $$4 * 0.017453292F;
/*     */     
/*  83 */     this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*  84 */     this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/*  85 */     this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/*  86 */     this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*  87 */     boolean $$6 = (!$$0.isBaby() && $$0.hasChest());
/*  88 */     this.rightChest.visible = $$6;
/*  89 */     this.leftChest.visible = $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/*  94 */     if (this.young) {
/*  95 */       float $$8 = 2.0F;
/*  96 */       $$0.pushPose();
/*  97 */       float $$9 = 0.7F;
/*  98 */       $$0.scale(0.71428573F, 0.64935064F, 0.7936508F);
/*  99 */       $$0.translate(0.0F, 1.3125F, 0.22F);
/* 100 */       this.head.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */       
/* 102 */       $$0.popPose();
/* 103 */       $$0.pushPose();
/* 104 */       float $$10 = 1.1F;
/* 105 */       $$0.scale(0.625F, 0.45454544F, 0.45454544F);
/* 106 */       $$0.translate(0.0F, 2.0625F, 0.0F);
/* 107 */       this.body.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */       
/* 109 */       $$0.popPose();
/* 110 */       $$0.pushPose();
/* 111 */       $$0.scale(0.45454544F, 0.41322312F, 0.45454544F);
/* 112 */       $$0.translate(0.0F, 2.0625F, 0.0F);
/* 113 */       ImmutableList.of(this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightChest, this.leftChest).forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/* 114 */       $$0.popPose();
/*     */     } else {
/* 116 */       ImmutableList.of(this.head, this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightChest, this.leftChest).forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\LlamaModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */