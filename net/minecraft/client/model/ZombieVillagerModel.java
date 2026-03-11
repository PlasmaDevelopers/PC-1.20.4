/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.monster.Zombie;
/*     */ 
/*     */ public class ZombieVillagerModel<T extends Zombie> extends HumanoidModel<T> implements VillagerHeadModel {
/*     */   private final ModelPart hatRim;
/*     */   
/*     */   public ZombieVillagerModel(ModelPart $$0) {
/*  18 */     super($$0);
/*  19 */     this.hatRim = this.hat.getChild("hat_rim");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  23 */     MeshDefinition $$0 = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
/*  24 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  26 */     $$1.addOrReplaceChild("head", (new CubeListBuilder())
/*     */         
/*  28 */         .texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F)
/*  29 */         .texOffs(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  32 */     PartDefinition $$2 = $$1.addOrReplaceChild("hat", 
/*  33 */         CubeListBuilder.create()
/*  34 */         .texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
/*     */ 
/*     */     
/*  37 */     $$2.addOrReplaceChild("hat_rim", 
/*  38 */         CubeListBuilder.create()
/*  39 */         .texOffs(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F), 
/*  40 */         PartPose.rotation(-1.5707964F, 0.0F, 0.0F));
/*     */     
/*  42 */     $$1.addOrReplaceChild("body", 
/*  43 */         CubeListBuilder.create()
/*  44 */         .texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F)
/*  45 */         .texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.05F)), PartPose.ZERO);
/*     */ 
/*     */     
/*  48 */     $$1.addOrReplaceChild("right_arm", 
/*  49 */         CubeListBuilder.create()
/*  50 */         .texOffs(44, 22).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  51 */         PartPose.offset(-5.0F, 2.0F, 0.0F));
/*     */     
/*  53 */     $$1.addOrReplaceChild("left_arm", 
/*  54 */         CubeListBuilder.create()
/*  55 */         .texOffs(44, 22).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  56 */         PartPose.offset(5.0F, 2.0F, 0.0F));
/*     */     
/*  58 */     $$1.addOrReplaceChild("right_leg", 
/*  59 */         CubeListBuilder.create()
/*  60 */         .texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  61 */         PartPose.offset(-2.0F, 12.0F, 0.0F));
/*     */     
/*  63 */     $$1.addOrReplaceChild("left_leg", 
/*  64 */         CubeListBuilder.create()
/*  65 */         .texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  66 */         PartPose.offset(2.0F, 12.0F, 0.0F));
/*     */ 
/*     */     
/*  69 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createArmorLayer(CubeDeformation $$0) {
/*  73 */     MeshDefinition $$1 = HumanoidModel.createMesh($$0, 0.0F);
/*  74 */     PartDefinition $$2 = $$1.getRoot();
/*     */     
/*  76 */     $$2.addOrReplaceChild("head", 
/*  77 */         CubeListBuilder.create()
/*  78 */         .texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */     
/*  81 */     $$2.addOrReplaceChild("body", 
/*  82 */         CubeListBuilder.create()
/*  83 */         .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, $$0.extend(0.1F)), PartPose.ZERO);
/*     */ 
/*     */     
/*  86 */     $$2.addOrReplaceChild("right_leg", 
/*  87 */         CubeListBuilder.create()
/*  88 */         .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(0.1F)), 
/*  89 */         PartPose.offset(-2.0F, 12.0F, 0.0F));
/*     */     
/*  91 */     $$2.addOrReplaceChild("left_leg", 
/*  92 */         CubeListBuilder.create()
/*  93 */         .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(0.1F)), 
/*  94 */         PartPose.offset(2.0F, 12.0F, 0.0F));
/*     */ 
/*     */ 
/*     */     
/*  98 */     $$2.getChild("hat").addOrReplaceChild("hat_rim", 
/*  99 */         CubeListBuilder.create(), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/* 103 */     return LayerDefinition.create($$1, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 108 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/* 110 */     AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, $$0.isAggressive(), this.attackTime, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void hatVisible(boolean $$0) {
/* 115 */     this.head.visible = $$0;
/* 116 */     this.hat.visible = $$0;
/* 117 */     this.hatRim.visible = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ZombieVillagerModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */