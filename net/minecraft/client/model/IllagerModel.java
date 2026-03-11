/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.monster.AbstractIllager;
/*     */ 
/*     */ public class IllagerModel<T extends AbstractIllager>
/*     */   extends HierarchicalModel<T>
/*     */   implements ArmedModel, HeadedModel
/*     */ {
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart hat;
/*     */   private final ModelPart arms;
/*     */   
/*     */   public IllagerModel(ModelPart $$0) {
/*  28 */     this.root = $$0;
/*  29 */     this.head = $$0.getChild("head");
/*  30 */     this.hat = this.head.getChild("hat");
/*  31 */     this.hat.visible = false;
/*  32 */     this.arms = $$0.getChild("arms");
/*  33 */     this.leftLeg = $$0.getChild("left_leg");
/*  34 */     this.rightLeg = $$0.getChild("right_leg");
/*  35 */     this.leftArm = $$0.getChild("left_arm");
/*  36 */     this.rightArm = $$0.getChild("right_arm");
/*     */   }
/*     */   private final ModelPart leftLeg; private final ModelPart rightLeg; private final ModelPart rightArm; private final ModelPart leftArm;
/*     */   public static LayerDefinition createBodyLayer() {
/*  40 */     MeshDefinition $$0 = new MeshDefinition();
/*  41 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  43 */     PartDefinition $$2 = $$1.addOrReplaceChild("head", 
/*  44 */         CubeListBuilder.create()
/*  45 */         .texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), 
/*  46 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/*  48 */     $$2.addOrReplaceChild("hat", 
/*  49 */         CubeListBuilder.create()
/*  50 */         .texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  54 */     $$2.addOrReplaceChild("nose", 
/*  55 */         CubeListBuilder.create()
/*  56 */         .texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), 
/*  57 */         PartPose.offset(0.0F, -2.0F, 0.0F));
/*     */     
/*  59 */     $$1.addOrReplaceChild("body", 
/*  60 */         CubeListBuilder.create()
/*  61 */         .texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F)
/*  62 */         .texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), 
/*  63 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*     */     
/*  65 */     PartDefinition $$3 = $$1.addOrReplaceChild("arms", 
/*  66 */         CubeListBuilder.create()
/*  67 */         .texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F)
/*  68 */         .texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), 
/*  69 */         PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
/*     */     
/*  71 */     $$3.addOrReplaceChild("left_shoulder", 
/*  72 */         CubeListBuilder.create()
/*  73 */         .texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);
/*     */ 
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
/*  86 */     $$1.addOrReplaceChild("right_arm", 
/*  87 */         CubeListBuilder.create()
/*  88 */         .texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  89 */         PartPose.offset(-5.0F, 2.0F, 0.0F));
/*     */     
/*  91 */     $$1.addOrReplaceChild("left_arm", 
/*  92 */         CubeListBuilder.create()
/*  93 */         .texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
/*  94 */         PartPose.offset(5.0F, 2.0F, 0.0F));
/*     */ 
/*     */     
/*  97 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 102 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 107 */     this.head.yRot = $$4 * 0.017453292F;
/* 108 */     this.head.xRot = $$5 * 0.017453292F;
/*     */     
/* 110 */     if (this.riding) {
/* 111 */       this.rightArm.xRot = -0.62831855F;
/* 112 */       this.rightArm.yRot = 0.0F;
/* 113 */       this.rightArm.zRot = 0.0F;
/*     */       
/* 115 */       this.leftArm.xRot = -0.62831855F;
/* 116 */       this.leftArm.yRot = 0.0F;
/* 117 */       this.leftArm.zRot = 0.0F;
/*     */       
/* 119 */       this.rightLeg.xRot = -1.4137167F;
/* 120 */       this.rightLeg.yRot = 0.31415927F;
/* 121 */       this.rightLeg.zRot = 0.07853982F;
/*     */       
/* 123 */       this.leftLeg.xRot = -1.4137167F;
/* 124 */       this.leftLeg.yRot = -0.31415927F;
/* 125 */       this.leftLeg.zRot = -0.07853982F;
/*     */     } else {
/* 127 */       this.rightArm.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 2.0F * $$2 * 0.5F;
/* 128 */       this.rightArm.yRot = 0.0F;
/* 129 */       this.rightArm.zRot = 0.0F;
/*     */       
/* 131 */       this.leftArm.xRot = Mth.cos($$1 * 0.6662F) * 2.0F * $$2 * 0.5F;
/* 132 */       this.leftArm.yRot = 0.0F;
/* 133 */       this.leftArm.zRot = 0.0F;
/*     */       
/* 135 */       this.rightLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2 * 0.5F;
/* 136 */       this.rightLeg.yRot = 0.0F;
/* 137 */       this.rightLeg.zRot = 0.0F;
/*     */       
/* 139 */       this.leftLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2 * 0.5F;
/* 140 */       this.leftLeg.yRot = 0.0F;
/* 141 */       this.leftLeg.zRot = 0.0F;
/*     */     } 
/*     */     
/* 144 */     AbstractIllager.IllagerArmPose $$6 = $$0.getArmPose();
/*     */     
/* 146 */     if ($$6 == AbstractIllager.IllagerArmPose.ATTACKING) {
/* 147 */       if ($$0.getMainHandItem().isEmpty()) {
/* 148 */         AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, $$3);
/*     */       } else {
/* 150 */         AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, (Mob)$$0, this.attackTime, $$3);
/*     */       } 
/* 152 */     } else if ($$6 == AbstractIllager.IllagerArmPose.SPELLCASTING) {
/* 153 */       this.rightArm.z = 0.0F;
/* 154 */       this.rightArm.x = -5.0F;
/* 155 */       this.leftArm.z = 0.0F;
/* 156 */       this.leftArm.x = 5.0F;
/* 157 */       this.rightArm.xRot = Mth.cos($$3 * 0.6662F) * 0.25F;
/* 158 */       this.leftArm.xRot = Mth.cos($$3 * 0.6662F) * 0.25F;
/* 159 */       this.rightArm.zRot = 2.3561945F;
/* 160 */       this.leftArm.zRot = -2.3561945F;
/*     */       
/* 162 */       this.rightArm.yRot = 0.0F;
/* 163 */       this.leftArm.yRot = 0.0F;
/* 164 */     } else if ($$6 == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
/* 165 */       this.rightArm.yRot = -0.1F + this.head.yRot;
/* 166 */       this.rightArm.xRot = -1.5707964F + this.head.xRot;
/* 167 */       this.leftArm.xRot = -0.9424779F + this.head.xRot;
/* 168 */       this.head.yRot -= 0.4F;
/* 169 */       this.leftArm.zRot = 1.5707964F;
/* 170 */     } else if ($$6 == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
/* 171 */       AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
/* 172 */     } else if ($$6 == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
/* 173 */       AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, (LivingEntity)$$0, true);
/* 174 */     } else if ($$6 == AbstractIllager.IllagerArmPose.CELEBRATING) {
/* 175 */       this.rightArm.z = 0.0F;
/* 176 */       this.rightArm.x = -5.0F;
/* 177 */       this.rightArm.xRot = Mth.cos($$3 * 0.6662F) * 0.05F;
/* 178 */       this.rightArm.zRot = 2.670354F;
/* 179 */       this.rightArm.yRot = 0.0F;
/*     */       
/* 181 */       this.leftArm.z = 0.0F;
/* 182 */       this.leftArm.x = 5.0F;
/* 183 */       this.leftArm.xRot = Mth.cos($$3 * 0.6662F) * 0.05F;
/* 184 */       this.leftArm.zRot = -2.3561945F;
/* 185 */       this.leftArm.yRot = 0.0F;
/*     */     } 
/*     */     
/* 188 */     boolean $$7 = ($$6 == AbstractIllager.IllagerArmPose.CROSSED);
/* 189 */     this.arms.visible = $$7;
/* 190 */     this.leftArm.visible = !$$7;
/* 191 */     this.rightArm.visible = !$$7;
/*     */   }
/*     */   
/*     */   private ModelPart getArm(HumanoidArm $$0) {
/* 195 */     if ($$0 == HumanoidArm.LEFT) {
/* 196 */       return this.leftArm;
/*     */     }
/* 198 */     return this.rightArm;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart getHat() {
/* 203 */     return this.hat;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart getHead() {
/* 208 */     return this.head;
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToHand(HumanoidArm $$0, PoseStack $$1) {
/* 213 */     getArm($$0).translateAndRotate($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\IllagerModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */