/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.HumanoidArm;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.CrossbowItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnimationUtils
/*    */ {
/*    */   public static void animateCrossbowHold(ModelPart $$0, ModelPart $$1, ModelPart $$2, boolean $$3) {
/* 18 */     ModelPart $$4 = $$3 ? $$0 : $$1;
/* 19 */     ModelPart $$5 = $$3 ? $$1 : $$0;
/*    */     
/* 21 */     $$4.yRot = ($$3 ? -0.3F : 0.3F) + $$2.yRot;
/* 22 */     $$5.yRot = ($$3 ? 0.6F : -0.6F) + $$2.yRot;
/* 23 */     $$4.xRot = -1.5707964F + $$2.xRot + 0.1F;
/* 24 */     $$5.xRot = -1.5F + $$2.xRot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void animateCrossbowCharge(ModelPart $$0, ModelPart $$1, LivingEntity $$2, boolean $$3) {
/* 31 */     ModelPart $$4 = $$3 ? $$0 : $$1;
/* 32 */     ModelPart $$5 = $$3 ? $$1 : $$0;
/*    */     
/* 34 */     $$4.yRot = $$3 ? -0.8F : 0.8F;
/* 35 */     $$4.xRot = -0.97079635F;
/* 36 */     $$5.xRot = $$4.xRot;
/*    */ 
/*    */     
/* 39 */     float $$6 = CrossbowItem.getChargeDuration($$2.getUseItem());
/* 40 */     float $$7 = Mth.clamp($$2.getTicksUsingItem(), 0.0F, $$6);
/* 41 */     float $$8 = $$7 / $$6;
/* 42 */     $$5.yRot = Mth.lerp($$8, 0.4F, 0.85F) * ($$3 ? true : -1);
/* 43 */     $$5.xRot = Mth.lerp($$8, $$5.xRot, -1.5707964F);
/*    */   }
/*    */   
/*    */   public static <T extends net.minecraft.world.entity.Mob> void swingWeaponDown(ModelPart $$0, ModelPart $$1, T $$2, float $$3, float $$4) {
/* 47 */     float $$5 = Mth.sin($$3 * 3.1415927F);
/* 48 */     float $$6 = Mth.sin((1.0F - (1.0F - $$3) * (1.0F - $$3)) * 3.1415927F);
/* 49 */     $$0.zRot = 0.0F;
/* 50 */     $$1.zRot = 0.0F;
/* 51 */     $$0.yRot = 0.15707964F;
/* 52 */     $$1.yRot = -0.15707964F;
/*    */     
/* 54 */     if ($$2.getMainArm() == HumanoidArm.RIGHT) {
/* 55 */       $$0.xRot = -1.8849558F + Mth.cos($$4 * 0.09F) * 0.15F;
/* 56 */       $$1.xRot = -0.0F + Mth.cos($$4 * 0.19F) * 0.5F;
/*    */       
/* 58 */       $$0.xRot += $$5 * 2.2F - $$6 * 0.4F;
/* 59 */       $$1.xRot += $$5 * 1.2F - $$6 * 0.4F;
/*    */     } else {
/* 61 */       $$0.xRot = -0.0F + Mth.cos($$4 * 0.19F) * 0.5F;
/* 62 */       $$1.xRot = -1.8849558F + Mth.cos($$4 * 0.09F) * 0.15F;
/*    */       
/* 64 */       $$0.xRot += $$5 * 1.2F - $$6 * 0.4F;
/* 65 */       $$1.xRot += $$5 * 2.2F - $$6 * 0.4F;
/*    */     } 
/*    */     
/* 68 */     bobArms($$0, $$1, $$4);
/*    */   }
/*    */   
/*    */   public static void bobModelPart(ModelPart $$0, float $$1, float $$2) {
/* 72 */     $$0.zRot += $$2 * (Mth.cos($$1 * 0.09F) * 0.05F + 0.05F);
/* 73 */     $$0.xRot += $$2 * Mth.sin($$1 * 0.067F) * 0.05F;
/*    */   }
/*    */   
/*    */   public static void bobArms(ModelPart $$0, ModelPart $$1, float $$2) {
/* 77 */     bobModelPart($$0, $$2, 1.0F);
/* 78 */     bobModelPart($$1, $$2, -1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void animateZombieArms(ModelPart $$0, ModelPart $$1, boolean $$2, float $$3, float $$4) {
/* 85 */     float $$5 = Mth.sin($$3 * 3.1415927F);
/* 86 */     float $$6 = Mth.sin((1.0F - (1.0F - $$3) * (1.0F - $$3)) * 3.1415927F);
/* 87 */     $$1.zRot = 0.0F;
/* 88 */     $$0.zRot = 0.0F;
/* 89 */     $$1.yRot = -(0.1F - $$5 * 0.6F);
/* 90 */     $$0.yRot = 0.1F - $$5 * 0.6F;
/*    */     
/* 92 */     float $$7 = -3.1415927F / ($$2 ? 1.5F : 2.25F);
/* 93 */     $$1.xRot = $$7;
/* 94 */     $$0.xRot = $$7;
/*    */     
/* 96 */     $$1.xRot += $$5 * 1.2F - $$6 * 0.4F;
/* 97 */     $$0.xRot += $$5 * 1.2F - $$6 * 0.4F;
/*    */     
/* 99 */     bobArms($$1, $$0, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\AnimationUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */