/*    */ package net.minecraft.client.model;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.HumanoidArm;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class SkeletonModel<T extends Mob & RangedAttackMob> extends HumanoidModel<T> {
/*    */   public SkeletonModel(ModelPart $$0) {
/* 22 */     super($$0);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 26 */     MeshDefinition $$0 = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
/* 27 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 29 */     $$1.addOrReplaceChild("right_arm", 
/* 30 */         CubeListBuilder.create()
/* 31 */         .texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), 
/* 32 */         PartPose.offset(-5.0F, 2.0F, 0.0F));
/*    */     
/* 34 */     $$1.addOrReplaceChild("left_arm", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), 
/* 37 */         PartPose.offset(5.0F, 2.0F, 0.0F));
/*    */     
/* 39 */     $$1.addOrReplaceChild("right_leg", 
/* 40 */         CubeListBuilder.create()
/* 41 */         .texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), 
/* 42 */         PartPose.offset(-2.0F, 12.0F, 0.0F));
/*    */     
/* 44 */     $$1.addOrReplaceChild("left_leg", 
/* 45 */         CubeListBuilder.create()
/* 46 */         .texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), 
/* 47 */         PartPose.offset(2.0F, 12.0F, 0.0F));
/*    */ 
/*    */     
/* 50 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 55 */     this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
/* 56 */     this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
/*    */     
/* 58 */     ItemStack $$4 = $$0.getItemInHand(InteractionHand.MAIN_HAND);
/* 59 */     if ($$4.is(Items.BOW) && $$0.isAggressive()) {
/* 60 */       if ($$0.getMainArm() == HumanoidArm.RIGHT) {
/* 61 */         this.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
/*    */       } else {
/* 63 */         this.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
/*    */       } 
/*    */     }
/*    */     
/* 67 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 72 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 74 */     ItemStack $$6 = $$0.getMainHandItem();
/* 75 */     if ($$0.isAggressive() && ($$6.isEmpty() || !$$6.is(Items.BOW))) {
/* 76 */       float $$7 = Mth.sin(this.attackTime * 3.1415927F);
/* 77 */       float $$8 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * 3.1415927F);
/* 78 */       this.rightArm.zRot = 0.0F;
/* 79 */       this.leftArm.zRot = 0.0F;
/* 80 */       this.rightArm.yRot = -(0.1F - $$7 * 0.6F);
/* 81 */       this.leftArm.yRot = 0.1F - $$7 * 0.6F;
/* 82 */       this.rightArm.xRot = -1.5707964F;
/* 83 */       this.leftArm.xRot = -1.5707964F;
/* 84 */       this.rightArm.xRot -= $$7 * 1.2F - $$8 * 0.4F;
/* 85 */       this.leftArm.xRot -= $$7 * 1.2F - $$8 * 0.4F;
/*    */       
/* 87 */       AnimationUtils.bobArms(this.rightArm, this.leftArm, $$3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void translateToHand(HumanoidArm $$0, PoseStack $$1) {
/* 93 */     float $$2 = ($$0 == HumanoidArm.RIGHT) ? 1.0F : -1.0F;
/* 94 */     ModelPart $$3 = getArm($$0);
/* 95 */     $$3.x += $$2;
/* 96 */     $$3.translateAndRotate($$1);
/* 97 */     $$3.x -= $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SkeletonModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */