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
/*     */ import net.minecraft.world.entity.monster.Strider;
/*     */ 
/*     */ public class StriderModel<T extends Strider>
/*     */   extends HierarchicalModel<T> {
/*     */   private static final String RIGHT_BOTTOM_BRISTLE = "right_bottom_bristle";
/*     */   private static final String RIGHT_MIDDLE_BRISTLE = "right_middle_bristle";
/*     */   private static final String RIGHT_TOP_BRISTLE = "right_top_bristle";
/*     */   private static final String LEFT_TOP_BRISTLE = "left_top_bristle";
/*     */   private static final String LEFT_MIDDLE_BRISTLE = "left_middle_bristle";
/*     */   private static final String LEFT_BOTTOM_BRISTLE = "left_bottom_bristle";
/*     */   private final ModelPart root;
/*     */   private final ModelPart rightLeg;
/*     */   private final ModelPart leftLeg;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightBottomBristle;
/*     */   private final ModelPart rightMiddleBristle;
/*     */   private final ModelPart rightTopBristle;
/*     */   private final ModelPart leftTopBristle;
/*     */   private final ModelPart leftMiddleBristle;
/*     */   private final ModelPart leftBottomBristle;
/*     */   
/*     */   public StriderModel(ModelPart $$0) {
/*  33 */     this.root = $$0;
/*  34 */     this.rightLeg = $$0.getChild("right_leg");
/*  35 */     this.leftLeg = $$0.getChild("left_leg");
/*  36 */     this.body = $$0.getChild("body");
/*  37 */     this.rightBottomBristle = this.body.getChild("right_bottom_bristle");
/*  38 */     this.rightMiddleBristle = this.body.getChild("right_middle_bristle");
/*  39 */     this.rightTopBristle = this.body.getChild("right_top_bristle");
/*  40 */     this.leftTopBristle = this.body.getChild("left_top_bristle");
/*  41 */     this.leftMiddleBristle = this.body.getChild("left_middle_bristle");
/*  42 */     this.leftBottomBristle = this.body.getChild("left_bottom_bristle");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  46 */     MeshDefinition $$0 = new MeshDefinition();
/*  47 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  49 */     $$1.addOrReplaceChild("right_leg", 
/*  50 */         CubeListBuilder.create()
/*  51 */         .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F), 
/*  52 */         PartPose.offset(-4.0F, 8.0F, 0.0F));
/*     */     
/*  54 */     $$1.addOrReplaceChild("left_leg", 
/*  55 */         CubeListBuilder.create()
/*  56 */         .texOffs(0, 55).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F), 
/*  57 */         PartPose.offset(4.0F, 8.0F, 0.0F));
/*     */     
/*  59 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", 
/*  60 */         CubeListBuilder.create()
/*  61 */         .texOffs(0, 0).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 14.0F, 16.0F), 
/*  62 */         PartPose.offset(0.0F, 1.0F, 0.0F));
/*     */ 
/*     */     
/*  65 */     $$2.addOrReplaceChild("right_bottom_bristle", 
/*  66 */         CubeListBuilder.create()
/*  67 */         .texOffs(16, 65).addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true), 
/*  68 */         PartPose.offsetAndRotation(-8.0F, 4.0F, -8.0F, 0.0F, 0.0F, -1.2217305F));
/*     */     
/*  70 */     $$2.addOrReplaceChild("right_middle_bristle", 
/*  71 */         CubeListBuilder.create()
/*  72 */         .texOffs(16, 49).addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true), 
/*  73 */         PartPose.offsetAndRotation(-8.0F, -1.0F, -8.0F, 0.0F, 0.0F, -1.134464F));
/*     */     
/*  75 */     $$2.addOrReplaceChild("right_top_bristle", 
/*  76 */         CubeListBuilder.create()
/*  77 */         .texOffs(16, 33).addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true), 
/*  78 */         PartPose.offsetAndRotation(-8.0F, -5.0F, -8.0F, 0.0F, 0.0F, -0.87266463F));
/*     */     
/*  80 */     $$2.addOrReplaceChild("left_top_bristle", 
/*  81 */         CubeListBuilder.create()
/*  82 */         .texOffs(16, 33).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F), 
/*  83 */         PartPose.offsetAndRotation(8.0F, -6.0F, -8.0F, 0.0F, 0.0F, 0.87266463F));
/*     */     
/*  85 */     $$2.addOrReplaceChild("left_middle_bristle", 
/*  86 */         CubeListBuilder.create()
/*  87 */         .texOffs(16, 49).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F), 
/*  88 */         PartPose.offsetAndRotation(8.0F, -2.0F, -8.0F, 0.0F, 0.0F, 1.134464F));
/*     */     
/*  90 */     $$2.addOrReplaceChild("left_bottom_bristle", 
/*  91 */         CubeListBuilder.create()
/*  92 */         .texOffs(16, 65).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F), 
/*  93 */         PartPose.offsetAndRotation(8.0F, 3.0F, -8.0F, 0.0F, 0.0F, 1.2217305F));
/*     */ 
/*     */     
/*  96 */     return LayerDefinition.create($$0, 64, 128);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(Strider $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 101 */     $$2 = Math.min(0.25F, $$2);
/*     */     
/* 103 */     if (!$$0.isVehicle()) {
/* 104 */       this.body.xRot = $$5 * 0.017453292F;
/* 105 */       this.body.yRot = $$4 * 0.017453292F;
/*     */     } else {
/* 107 */       this.body.xRot = 0.0F;
/* 108 */       this.body.yRot = 0.0F;
/*     */     } 
/*     */     
/* 111 */     float $$6 = 1.5F;
/*     */     
/* 113 */     this.body.zRot = 0.1F * Mth.sin($$1 * 1.5F) * 4.0F * $$2;
/*     */     
/* 115 */     this.body.y = 2.0F;
/* 116 */     this.body.y -= 2.0F * Mth.cos($$1 * 1.5F) * 2.0F * $$2;
/*     */     
/* 118 */     this.leftLeg.xRot = Mth.sin($$1 * 1.5F * 0.5F) * 2.0F * $$2;
/* 119 */     this.rightLeg.xRot = Mth.sin($$1 * 1.5F * 0.5F + 3.1415927F) * 2.0F * $$2;
/*     */     
/* 121 */     this.leftLeg.zRot = 0.17453292F * Mth.cos($$1 * 1.5F * 0.5F) * $$2;
/* 122 */     this.rightLeg.zRot = 0.17453292F * Mth.cos($$1 * 1.5F * 0.5F + 3.1415927F) * $$2;
/*     */     
/* 124 */     this.leftLeg.y = 8.0F + 2.0F * Mth.sin($$1 * 1.5F * 0.5F + 3.1415927F) * 2.0F * $$2;
/* 125 */     this.rightLeg.y = 8.0F + 2.0F * Mth.sin($$1 * 1.5F * 0.5F) * 2.0F * $$2;
/*     */     
/* 127 */     this.rightBottomBristle.zRot = -1.2217305F;
/* 128 */     this.rightMiddleBristle.zRot = -1.134464F;
/* 129 */     this.rightTopBristle.zRot = -0.87266463F;
/* 130 */     this.leftTopBristle.zRot = 0.87266463F;
/* 131 */     this.leftMiddleBristle.zRot = 1.134464F;
/* 132 */     this.leftBottomBristle.zRot = 1.2217305F;
/*     */     
/* 134 */     float $$7 = Mth.cos($$1 * 1.5F + 3.1415927F) * $$2;
/*     */     
/* 136 */     this.rightBottomBristle.zRot += $$7 * 1.3F;
/* 137 */     this.rightMiddleBristle.zRot += $$7 * 1.2F;
/* 138 */     this.rightTopBristle.zRot += $$7 * 0.6F;
/*     */     
/* 140 */     this.leftTopBristle.zRot += $$7 * 0.6F;
/* 141 */     this.leftMiddleBristle.zRot += $$7 * 1.2F;
/* 142 */     this.leftBottomBristle.zRot += $$7 * 1.3F;
/*     */     
/* 144 */     float $$8 = 1.0F;
/* 145 */     float $$9 = 1.0F;
/*     */     
/* 147 */     this.rightBottomBristle.zRot += 0.05F * Mth.sin($$3 * 1.0F * -0.4F);
/* 148 */     this.rightMiddleBristle.zRot += 0.1F * Mth.sin($$3 * 1.0F * 0.2F);
/* 149 */     this.rightTopBristle.zRot += 0.1F * Mth.sin($$3 * 1.0F * 0.4F);
/*     */     
/* 151 */     this.leftTopBristle.zRot += 0.1F * Mth.sin($$3 * 1.0F * 0.4F);
/* 152 */     this.leftMiddleBristle.zRot += 0.1F * Mth.sin($$3 * 1.0F * 0.2F);
/* 153 */     this.leftBottomBristle.zRot += 0.05F * Mth.sin($$3 * 1.0F * -0.4F);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 158 */     return this.root;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\StriderModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */