/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.monster.Ravager;
/*     */ 
/*     */ public class RavagerModel
/*     */   extends HierarchicalModel<Ravager> {
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart mouth;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart neck;
/*     */   
/*     */   public RavagerModel(ModelPart $$0) {
/*  25 */     this.root = $$0;
/*  26 */     this.neck = $$0.getChild("neck");
/*  27 */     this.head = this.neck.getChild("head");
/*  28 */     this.mouth = this.head.getChild("mouth");
/*  29 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  30 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*  31 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*  32 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  36 */     MeshDefinition $$0 = new MeshDefinition();
/*  37 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  39 */     int $$2 = 16;
/*  40 */     PartDefinition $$3 = $$1.addOrReplaceChild("neck", 
/*  41 */         CubeListBuilder.create()
/*  42 */         .texOffs(68, 73).addBox(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F), 
/*  43 */         PartPose.offset(0.0F, -7.0F, 5.5F));
/*     */     
/*  45 */     PartDefinition $$4 = $$3.addOrReplaceChild("head", 
/*  46 */         CubeListBuilder.create()
/*  47 */         .texOffs(0, 0).addBox(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F)
/*  48 */         .texOffs(0, 0).addBox(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F), 
/*  49 */         PartPose.offset(0.0F, 16.0F, -17.0F));
/*     */ 
/*     */     
/*  52 */     $$4.addOrReplaceChild("right_horn", 
/*  53 */         CubeListBuilder.create()
/*  54 */         .texOffs(74, 55).addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F), 
/*  55 */         PartPose.offsetAndRotation(-10.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F));
/*     */     
/*  57 */     $$4.addOrReplaceChild("left_horn", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(74, 55).mirror().addBox(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F), 
/*  60 */         PartPose.offsetAndRotation(8.0F, -14.0F, -8.0F, 1.0995574F, 0.0F, 0.0F));
/*     */     
/*  62 */     $$4.addOrReplaceChild("mouth", 
/*  63 */         CubeListBuilder.create()
/*  64 */         .texOffs(0, 36).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F), 
/*  65 */         PartPose.offset(0.0F, -2.0F, 2.0F));
/*     */     
/*  67 */     $$1.addOrReplaceChild("body", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(0, 55).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F)
/*  70 */         .texOffs(0, 91).addBox(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F), 
/*  71 */         PartPose.offsetAndRotation(0.0F, 1.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  73 */     $$1.addOrReplaceChild("right_hind_leg", 
/*  74 */         CubeListBuilder.create()
/*  75 */         .texOffs(96, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), 
/*  76 */         PartPose.offset(-8.0F, -13.0F, 18.0F));
/*     */     
/*  78 */     $$1.addOrReplaceChild("left_hind_leg", 
/*  79 */         CubeListBuilder.create()
/*  80 */         .texOffs(96, 0).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), 
/*  81 */         PartPose.offset(8.0F, -13.0F, 18.0F));
/*     */     
/*  83 */     $$1.addOrReplaceChild("right_front_leg", 
/*  84 */         CubeListBuilder.create()
/*  85 */         .texOffs(64, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), 
/*  86 */         PartPose.offset(-8.0F, -13.0F, -5.0F));
/*     */     
/*  88 */     $$1.addOrReplaceChild("left_front_leg", 
/*  89 */         CubeListBuilder.create()
/*  90 */         .texOffs(64, 0).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F), 
/*  91 */         PartPose.offset(8.0F, -13.0F, -5.0F));
/*     */ 
/*     */     
/*  94 */     return LayerDefinition.create($$0, 128, 128);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/*  99 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(Ravager $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 104 */     this.head.xRot = $$5 * 0.017453292F;
/* 105 */     this.head.yRot = $$4 * 0.017453292F;
/*     */     
/* 107 */     float $$6 = 0.4F * $$2;
/* 108 */     this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F) * $$6;
/* 109 */     this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * $$6;
/* 110 */     this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * $$6;
/* 111 */     this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F) * $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(Ravager $$0, float $$1, float $$2, float $$3) {
/* 116 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*     */     
/* 118 */     int $$4 = $$0.getStunnedTick();
/* 119 */     int $$5 = $$0.getRoarTick();
/* 120 */     int $$6 = 20;
/* 121 */     int $$7 = $$0.getAttackTick();
/* 122 */     int $$8 = 10;
/* 123 */     if ($$7 > 0) {
/* 124 */       float $$9 = Mth.triangleWave($$7 - $$3, 10.0F);
/* 125 */       float $$10 = (1.0F + $$9) * 0.5F;
/* 126 */       float $$11 = $$10 * $$10 * $$10 * 12.0F;
/* 127 */       float $$12 = $$11 * Mth.sin(this.neck.xRot);
/* 128 */       this.neck.z = -6.5F + $$11;
/* 129 */       this.neck.y = -7.0F - $$12;
/*     */       
/* 131 */       float $$13 = Mth.sin(($$7 - $$3) / 10.0F * 3.1415927F * 0.25F);
/* 132 */       this.mouth.xRot = 1.5707964F * $$13;
/*     */       
/* 134 */       if ($$7 > 5) {
/* 135 */         this.mouth.xRot = Mth.sin(((-4 + $$7) - $$3) / 4.0F) * 3.1415927F * 0.4F;
/*     */       } else {
/* 137 */         this.mouth.xRot = 0.15707964F * Mth.sin(3.1415927F * ($$7 - $$3) / 10.0F);
/*     */       } 
/*     */     } else {
/* 140 */       float $$14 = -1.0F;
/* 141 */       float $$15 = -1.0F * Mth.sin(this.neck.xRot);
/* 142 */       this.neck.x = 0.0F;
/* 143 */       this.neck.y = -7.0F - $$15;
/* 144 */       this.neck.z = 5.5F;
/*     */       
/* 146 */       boolean $$16 = ($$4 > 0);
/* 147 */       this.neck.xRot = $$16 ? 0.21991149F : 0.0F;
/* 148 */       this.mouth.xRot = 3.1415927F * ($$16 ? 0.05F : 0.01F);
/*     */       
/* 150 */       if ($$16) {
/* 151 */         double $$17 = $$4 / 40.0D;
/* 152 */         this.neck.x = (float)Math.sin($$17 * 10.0D) * 3.0F;
/* 153 */       } else if ($$5 > 0) {
/* 154 */         float $$18 = Mth.sin(((20 - $$5) - $$3) / 20.0F * 3.1415927F * 0.25F);
/* 155 */         this.mouth.xRot = 1.5707964F * $$18;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\RavagerModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */