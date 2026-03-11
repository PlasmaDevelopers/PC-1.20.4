/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ 
/*     */ public class BoatModel extends ListModel<Boat> implements WaterPatchModel {
/*     */   private static final String LEFT_PADDLE = "left_paddle";
/*     */   private static final String RIGHT_PADDLE = "right_paddle";
/*     */   private static final String WATER_PATCH = "water_patch";
/*     */   private static final String BOTTOM = "bottom";
/*     */   private static final String BACK = "back";
/*     */   private static final String FRONT = "front";
/*     */   private static final String RIGHT = "right";
/*     */   private static final String LEFT = "left";
/*     */   private final ModelPart leftPaddle;
/*     */   private final ModelPart rightPaddle;
/*     */   private final ModelPart waterPatch;
/*     */   private final ImmutableList<ModelPart> parts;
/*     */   
/*     */   public BoatModel(ModelPart $$0) {
/*  29 */     this.leftPaddle = $$0.getChild("left_paddle");
/*  30 */     this.rightPaddle = $$0.getChild("right_paddle");
/*  31 */     this.waterPatch = $$0.getChild("water_patch");
/*     */     
/*  33 */     this.parts = createPartsBuilder($$0).build();
/*     */   }
/*     */   
/*     */   protected ImmutableList.Builder<ModelPart> createPartsBuilder(ModelPart $$0) {
/*  37 */     ImmutableList.Builder<ModelPart> $$1 = new ImmutableList.Builder();
/*  38 */     $$1.add((Object[])new ModelPart[] { $$0
/*  39 */           .getChild("bottom"), $$0
/*  40 */           .getChild("back"), $$0
/*  41 */           .getChild("front"), $$0
/*  42 */           .getChild("right"), $$0
/*  43 */           .getChild("left"), this.leftPaddle, this.rightPaddle });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     return $$1;
/*     */   }
/*     */   
/*     */   public static void createChildren(PartDefinition $$0) {
/*  52 */     int $$1 = 32;
/*  53 */     int $$2 = 6;
/*  54 */     int $$3 = 20;
/*  55 */     int $$4 = 4;
/*     */     
/*  57 */     int $$5 = 28;
/*     */     
/*  59 */     $$0.addOrReplaceChild("bottom", 
/*  60 */         CubeListBuilder.create()
/*  61 */         .texOffs(0, 0).addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), 
/*  62 */         PartPose.offsetAndRotation(0.0F, 3.0F, 1.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  64 */     $$0.addOrReplaceChild("back", 
/*  65 */         CubeListBuilder.create()
/*  66 */         .texOffs(0, 19).addBox(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F), 
/*  67 */         PartPose.offsetAndRotation(-15.0F, 4.0F, 4.0F, 0.0F, 4.712389F, 0.0F));
/*     */     
/*  69 */     $$0.addOrReplaceChild("front", 
/*  70 */         CubeListBuilder.create()
/*  71 */         .texOffs(0, 27).addBox(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F), 
/*  72 */         PartPose.offsetAndRotation(15.0F, 4.0F, 0.0F, 0.0F, 1.5707964F, 0.0F));
/*     */     
/*  74 */     $$0.addOrReplaceChild("right", 
/*  75 */         CubeListBuilder.create()
/*  76 */         .texOffs(0, 35).addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), 
/*  77 */         PartPose.offsetAndRotation(0.0F, 4.0F, -9.0F, 0.0F, 3.1415927F, 0.0F));
/*     */     
/*  79 */     $$0.addOrReplaceChild("left", 
/*  80 */         CubeListBuilder.create()
/*  81 */         .texOffs(0, 43).addBox(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), 
/*  82 */         PartPose.offset(0.0F, 4.0F, 9.0F));
/*     */ 
/*     */     
/*  85 */     int $$6 = 20;
/*  86 */     int $$7 = 7;
/*  87 */     int $$8 = 6;
/*  88 */     float $$9 = -5.0F;
/*     */     
/*  90 */     $$0.addOrReplaceChild("left_paddle", 
/*  91 */         CubeListBuilder.create()
/*  92 */         .texOffs(62, 0)
/*  93 */         .addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
/*  94 */         .addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), 
/*  95 */         PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
/*     */     
/*  97 */     $$0.addOrReplaceChild("right_paddle", 
/*  98 */         CubeListBuilder.create()
/*  99 */         .texOffs(62, 20)
/* 100 */         .addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
/* 101 */         .addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), 
/* 102 */         PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, 3.1415927F, 0.19634955F));
/*     */     
/* 104 */     $$0.addOrReplaceChild("water_patch", 
/* 105 */         CubeListBuilder.create()
/* 106 */         .texOffs(0, 0)
/* 107 */         .addBox(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), 
/* 108 */         PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, 1.5707964F, 0.0F, 0.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public static LayerDefinition createBodyModel() {
/* 113 */     MeshDefinition $$0 = new MeshDefinition();
/* 114 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/* 116 */     createChildren($$1);
/*     */ 
/*     */     
/* 119 */     return LayerDefinition.create($$0, 128, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(Boat $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 124 */     animatePaddle($$0, 0, this.leftPaddle, $$1);
/* 125 */     animatePaddle($$0, 1, this.rightPaddle, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableList<ModelPart> parts() {
/* 130 */     return this.parts;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart waterPatch() {
/* 135 */     return this.waterPatch;
/*     */   }
/*     */   
/*     */   private static void animatePaddle(Boat $$0, int $$1, ModelPart $$2, float $$3) {
/* 139 */     float $$4 = $$0.getRowingTime($$1, $$3);
/*     */     
/* 141 */     $$2.xRot = Mth.clampedLerp(-1.0471976F, -0.2617994F, (Mth.sin(-$$4) + 1.0F) / 2.0F);
/* 142 */     $$2.yRot = Mth.clampedLerp(-0.7853982F, 0.7853982F, (Mth.sin(-$$4 + 1.0F) + 1.0F) / 2.0F);
/*     */     
/* 144 */     if ($$1 == 1)
/* 145 */       $$2.yRot = 3.1415927F - $$2.yRot; 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\BoatModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */