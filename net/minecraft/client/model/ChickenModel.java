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
/*     */ 
/*     */ public class ChickenModel<T extends Entity>
/*     */   extends AgeableListModel<T>
/*     */ {
/*     */   public static final String RED_THING = "red_thing";
/*     */   private final ModelPart head;
/*     */   private final ModelPart body;
/*     */   private final ModelPart rightLeg;
/*     */   private final ModelPart leftLeg;
/*     */   private final ModelPart rightWing;
/*     */   private final ModelPart leftWing;
/*     */   private final ModelPart beak;
/*     */   private final ModelPart redThing;
/*     */   
/*     */   public ChickenModel(ModelPart $$0) {
/*  27 */     this.head = $$0.getChild("head");
/*  28 */     this.beak = $$0.getChild("beak");
/*  29 */     this.redThing = $$0.getChild("red_thing");
/*  30 */     this.body = $$0.getChild("body");
/*  31 */     this.rightLeg = $$0.getChild("right_leg");
/*  32 */     this.leftLeg = $$0.getChild("left_leg");
/*  33 */     this.rightWing = $$0.getChild("right_wing");
/*  34 */     this.leftWing = $$0.getChild("left_wing");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  38 */     MeshDefinition $$0 = new MeshDefinition();
/*  39 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  41 */     int $$2 = 16;
/*  42 */     $$1.addOrReplaceChild("head", 
/*  43 */         CubeListBuilder.create()
/*  44 */         .texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), 
/*  45 */         PartPose.offset(0.0F, 15.0F, -4.0F));
/*     */     
/*  47 */     $$1.addOrReplaceChild("beak", 
/*  48 */         CubeListBuilder.create()
/*  49 */         .texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), 
/*  50 */         PartPose.offset(0.0F, 15.0F, -4.0F));
/*     */     
/*  52 */     $$1.addOrReplaceChild("red_thing", 
/*  53 */         CubeListBuilder.create()
/*  54 */         .texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), 
/*  55 */         PartPose.offset(0.0F, 15.0F, -4.0F));
/*     */     
/*  57 */     $$1.addOrReplaceChild("body", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), 
/*  60 */         PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));
/*     */ 
/*     */     
/*  63 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
/*     */     
/*  65 */     $$1.addOrReplaceChild("right_leg", $$3, PartPose.offset(-2.0F, 19.0F, 1.0F));
/*  66 */     $$1.addOrReplaceChild("left_leg", $$3, PartPose.offset(1.0F, 19.0F, 1.0F));
/*     */     
/*  68 */     $$1.addOrReplaceChild("right_wing", 
/*  69 */         CubeListBuilder.create()
/*  70 */         .texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), 
/*  71 */         PartPose.offset(-4.0F, 13.0F, 0.0F));
/*     */     
/*  73 */     $$1.addOrReplaceChild("left_wing", 
/*  74 */         CubeListBuilder.create()
/*  75 */         .texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), 
/*  76 */         PartPose.offset(4.0F, 13.0F, 0.0F));
/*     */ 
/*     */     
/*  79 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/*  84 */     return (Iterable<ModelPart>)ImmutableList.of(this.head, this.beak, this.redThing);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/*  89 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*  94 */     this.head.xRot = $$5 * 0.017453292F;
/*  95 */     this.head.yRot = $$4 * 0.017453292F;
/*     */     
/*  97 */     this.beak.xRot = this.head.xRot;
/*  98 */     this.beak.yRot = this.head.yRot;
/*     */     
/* 100 */     this.redThing.xRot = this.head.xRot;
/* 101 */     this.redThing.yRot = this.head.yRot;
/*     */     
/* 103 */     this.rightLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/* 104 */     this.leftLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/* 105 */     this.rightWing.zRot = $$3;
/* 106 */     this.leftWing.zRot = -$$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ChickenModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */