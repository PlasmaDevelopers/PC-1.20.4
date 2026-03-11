/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ 
/*     */ public class VillagerModel<T extends Entity>
/*     */   extends HierarchicalModel<T> implements HeadedModel, VillagerHeadModel {
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart hat;
/*     */   private final ModelPart hatRim;
/*     */   private final ModelPart rightLeg;
/*     */   private final ModelPart leftLeg;
/*     */   protected final ModelPart nose;
/*     */   
/*     */   public VillagerModel(ModelPart $$0) {
/*  24 */     this.root = $$0;
/*  25 */     this.head = $$0.getChild("head");
/*  26 */     this.hat = this.head.getChild("hat");
/*  27 */     this.hatRim = this.hat.getChild("hat_rim");
/*  28 */     this.nose = this.head.getChild("nose");
/*  29 */     this.rightLeg = $$0.getChild("right_leg");
/*  30 */     this.leftLeg = $$0.getChild("left_leg");
/*     */   }
/*     */   
/*     */   public static MeshDefinition createBodyModel() {
/*  34 */     MeshDefinition $$0 = new MeshDefinition();
/*  35 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  37 */     float $$2 = 0.5F;
/*     */     
/*  39 */     PartDefinition $$3 = $$1.addOrReplaceChild("head", 
/*  40 */         CubeListBuilder.create()
/*  41 */         .texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  44 */     PartDefinition $$4 = $$3.addOrReplaceChild("hat", 
/*  45 */         CubeListBuilder.create()
/*  46 */         .texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.51F)), PartPose.ZERO);
/*     */ 
/*     */     
/*  49 */     $$4.addOrReplaceChild("hat_rim", 
/*  50 */         CubeListBuilder.create()
/*  51 */         .texOffs(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F), 
/*  52 */         PartPose.rotation(-1.5707964F, 0.0F, 0.0F));
/*     */     
/*  54 */     $$3.addOrReplaceChild("nose", 
/*  55 */         CubeListBuilder.create()
/*  56 */         .texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), 
/*  57 */         PartPose.offset(0.0F, -2.0F, 0.0F));
/*     */     
/*  59 */     PartDefinition $$5 = $$1.addOrReplaceChild("body", 
/*  60 */         CubeListBuilder.create()
/*  61 */         .texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  64 */     $$5.addOrReplaceChild("jacket", 
/*  65 */         CubeListBuilder.create()
/*  66 */         .texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
/*     */ 
/*     */     
/*  69 */     $$1.addOrReplaceChild("arms", 
/*  70 */         CubeListBuilder.create()
/*  71 */         .texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F)
/*  72 */         .texOffs(44, 22).addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, true)
/*  73 */         .texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), 
/*  74 */         PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
/*     */     
/*  76 */     $$1.addOrReplaceChild("right_leg", 
/*  77 */         CubeListBuilder.create()
/*  78 */         .texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  79 */         PartPose.offset(-2.0F, 12.0F, 0.0F));
/*     */     
/*  81 */     $$1.addOrReplaceChild("left_leg", 
/*  82 */         CubeListBuilder.create()
/*  83 */         .texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  84 */         PartPose.offset(2.0F, 12.0F, 0.0F));
/*     */ 
/*     */     
/*  87 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  92 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  97 */     boolean $$6 = false;
/*  98 */     if ($$0 instanceof AbstractVillager) {
/*  99 */       $$6 = (((AbstractVillager)$$0).getUnhappyCounter() > 0);
/*     */     }
/*     */     
/* 102 */     this.head.yRot = $$4 * 0.017453292F;
/* 103 */     this.head.xRot = $$5 * 0.017453292F;
/*     */     
/* 105 */     if ($$6) {
/* 106 */       this.head.zRot = 0.3F * Mth.sin(0.45F * $$3);
/* 107 */       this.head.xRot = 0.4F;
/*     */     } else {
/* 109 */       this.head.zRot = 0.0F;
/*     */     } 
/*     */     
/* 112 */     this.rightLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2 * 0.5F;
/* 113 */     this.leftLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2 * 0.5F;
/* 114 */     this.rightLeg.yRot = 0.0F;
/* 115 */     this.leftLeg.yRot = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart getHead() {
/* 120 */     return this.head;
/*     */   }
/*     */ 
/*     */   
/*     */   public void hatVisible(boolean $$0) {
/* 125 */     this.head.visible = $$0;
/* 126 */     this.hat.visible = $$0;
/* 127 */     this.hatRim.visible = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\VillagerModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */