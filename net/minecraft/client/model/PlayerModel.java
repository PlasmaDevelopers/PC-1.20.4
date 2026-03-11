/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ public class PlayerModel<T extends LivingEntity>
/*     */   extends HumanoidModel<T>
/*     */ {
/*     */   private static final String EAR = "ear";
/*     */   private static final String CLOAK = "cloak";
/*     */   private static final String LEFT_SLEEVE = "left_sleeve";
/*     */   private static final String RIGHT_SLEEVE = "right_sleeve";
/*     */   private static final String LEFT_PANTS = "left_pants";
/*     */   private static final String RIGHT_PANTS = "right_pants";
/*     */   private final List<ModelPart> parts;
/*     */   public final ModelPart leftSleeve;
/*     */   public final ModelPart rightSleeve;
/*     */   public final ModelPart leftPants;
/*     */   public final ModelPart rightPants;
/*     */   public final ModelPart jacket;
/*     */   private final ModelPart cloak;
/*     */   private final ModelPart ear;
/*     */   private final boolean slim;
/*     */   
/*     */   public PlayerModel(ModelPart $$0, boolean $$1) {
/*  42 */     super($$0, RenderType::entityTranslucent);
/*  43 */     this.slim = $$1;
/*     */     
/*  45 */     this.ear = $$0.getChild("ear");
/*  46 */     this.cloak = $$0.getChild("cloak");
/*  47 */     this.leftSleeve = $$0.getChild("left_sleeve");
/*  48 */     this.rightSleeve = $$0.getChild("right_sleeve");
/*  49 */     this.leftPants = $$0.getChild("left_pants");
/*  50 */     this.rightPants = $$0.getChild("right_pants");
/*  51 */     this.jacket = $$0.getChild("jacket");
/*     */     
/*  53 */     this.parts = (List<ModelPart>)$$0.getAllParts().filter($$0 -> !$$0.isEmpty()).collect(ImmutableList.toImmutableList());
/*     */   }
/*     */   
/*     */   public static MeshDefinition createMesh(CubeDeformation $$0, boolean $$1) {
/*  57 */     MeshDefinition $$2 = HumanoidModel.createMesh($$0, 0.0F);
/*  58 */     PartDefinition $$3 = $$2.getRoot();
/*     */     
/*  60 */     $$3.addOrReplaceChild("ear", 
/*  61 */         CubeListBuilder.create()
/*  62 */         .texOffs(24, 0).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F, $$0), PartPose.ZERO);
/*     */ 
/*     */     
/*  65 */     $$3.addOrReplaceChild("cloak", 
/*  66 */         CubeListBuilder.create()
/*  67 */         .texOffs(0, 0).addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, $$0, 1.0F, 0.5F), 
/*  68 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */ 
/*     */     
/*  71 */     float $$4 = 0.25F;
/*  72 */     if ($$1) {
/*  73 */       $$3.addOrReplaceChild("left_arm", 
/*  74 */           CubeListBuilder.create()
/*  75 */           .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, $$0), 
/*  76 */           PartPose.offset(5.0F, 2.5F, 0.0F));
/*     */       
/*  78 */       $$3.addOrReplaceChild("right_arm", 
/*  79 */           CubeListBuilder.create()
/*  80 */           .texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, $$0), 
/*  81 */           PartPose.offset(-5.0F, 2.5F, 0.0F));
/*     */       
/*  83 */       $$3.addOrReplaceChild("left_sleeve", 
/*  84 */           CubeListBuilder.create()
/*  85 */           .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, $$0.extend(0.25F)), 
/*  86 */           PartPose.offset(5.0F, 2.5F, 0.0F));
/*     */       
/*  88 */       $$3.addOrReplaceChild("right_sleeve", 
/*  89 */           CubeListBuilder.create()
/*  90 */           .texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, $$0.extend(0.25F)), 
/*  91 */           PartPose.offset(-5.0F, 2.5F, 0.0F));
/*     */     } else {
/*     */       
/*  94 */       $$3.addOrReplaceChild("left_arm", 
/*  95 */           CubeListBuilder.create()
/*  96 */           .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/*  97 */           PartPose.offset(5.0F, 2.0F, 0.0F));
/*     */ 
/*     */       
/* 100 */       $$3.addOrReplaceChild("left_sleeve", 
/* 101 */           CubeListBuilder.create()
/* 102 */           .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(0.25F)), 
/* 103 */           PartPose.offset(5.0F, 2.0F, 0.0F));
/*     */       
/* 105 */       $$3.addOrReplaceChild("right_sleeve", 
/* 106 */           CubeListBuilder.create()
/* 107 */           .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(0.25F)), 
/* 108 */           PartPose.offset(-5.0F, 2.0F, 0.0F));
/*     */     } 
/*     */ 
/*     */     
/* 112 */     $$3.addOrReplaceChild("left_leg", 
/* 113 */         CubeListBuilder.create()
/* 114 */         .texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0), 
/* 115 */         PartPose.offset(1.9F, 12.0F, 0.0F));
/*     */     
/* 117 */     $$3.addOrReplaceChild("left_pants", 
/* 118 */         CubeListBuilder.create()
/* 119 */         .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(0.25F)), 
/* 120 */         PartPose.offset(1.9F, 12.0F, 0.0F));
/*     */     
/* 122 */     $$3.addOrReplaceChild("right_pants", 
/* 123 */         CubeListBuilder.create()
/* 124 */         .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(0.25F)), 
/* 125 */         PartPose.offset(-1.9F, 12.0F, 0.0F));
/*     */     
/* 127 */     $$3.addOrReplaceChild("jacket", 
/* 128 */         CubeListBuilder.create()
/* 129 */         .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, $$0.extend(0.25F)), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/* 133 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 138 */     return Iterables.concat(super.bodyParts(), (Iterable)ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.rightSleeve, this.jacket));
/*     */   }
/*     */   
/*     */   public void renderEars(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3) {
/* 142 */     this.ear.copyFrom(this.head);
/* 143 */     this.ear.x = 0.0F;
/* 144 */     this.ear.y = 0.0F;
/* 145 */     this.ear.render($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void renderCloak(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3) {
/* 149 */     this.cloak.render($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 154 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/* 156 */     this.leftPants.copyFrom(this.leftLeg);
/* 157 */     this.rightPants.copyFrom(this.rightLeg);
/* 158 */     this.leftSleeve.copyFrom(this.leftArm);
/* 159 */     this.rightSleeve.copyFrom(this.rightArm);
/* 160 */     this.jacket.copyFrom(this.body);
/*     */     
/* 162 */     if ($$0.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
/* 163 */       if ($$0.isCrouching()) {
/* 164 */         this.cloak.z = 1.4F;
/* 165 */         this.cloak.y = 1.85F;
/*     */       } else {
/* 167 */         this.cloak.z = 0.0F;
/* 168 */         this.cloak.y = 0.0F;
/*     */       }
/*     */     
/* 171 */     } else if ($$0.isCrouching()) {
/* 172 */       this.cloak.z = 0.3F;
/* 173 */       this.cloak.y = 0.8F;
/*     */     } else {
/* 175 */       this.cloak.z = -1.1F;
/* 176 */       this.cloak.y = -0.85F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllVisible(boolean $$0) {
/* 183 */     super.setAllVisible($$0);
/* 184 */     this.leftSleeve.visible = $$0;
/* 185 */     this.rightSleeve.visible = $$0;
/* 186 */     this.leftPants.visible = $$0;
/* 187 */     this.rightPants.visible = $$0;
/* 188 */     this.jacket.visible = $$0;
/* 189 */     this.cloak.visible = $$0;
/* 190 */     this.ear.visible = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToHand(HumanoidArm $$0, PoseStack $$1) {
/* 195 */     ModelPart $$2 = getArm($$0);
/* 196 */     if (this.slim) {
/* 197 */       float $$3 = 0.5F * (($$0 == HumanoidArm.RIGHT) ? true : -1);
/* 198 */       $$2.x += $$3;
/* 199 */       $$2.translateAndRotate($$1);
/* 200 */       $$2.x -= $$3;
/*     */     } else {
/* 202 */       $$2.translateAndRotate($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ModelPart getRandomModelPart(RandomSource $$0) {
/* 207 */     return this.parts.get($$0.nextInt(this.parts.size()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PlayerModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */