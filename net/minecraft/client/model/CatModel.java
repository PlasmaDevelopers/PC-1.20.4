/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Cat;
/*    */ 
/*    */ public class CatModel<T extends Cat> extends OcelotModel<T> {
/*    */   private float lieDownAmount;
/*    */   private float lieDownAmountTail;
/*    */   private float relaxStateOneAmount;
/*    */   
/*    */   public CatModel(ModelPart $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 18 */     this.lieDownAmount = $$0.getLieDownAmount($$3);
/* 19 */     this.lieDownAmountTail = $$0.getLieDownAmountTail($$3);
/* 20 */     this.relaxStateOneAmount = $$0.getRelaxStateOneAmount($$3);
/*    */     
/* 22 */     if (this.lieDownAmount <= 0.0F) {
/*    */       
/* 24 */       this.head.xRot = 0.0F;
/* 25 */       this.head.zRot = 0.0F;
/* 26 */       this.leftFrontLeg.xRot = 0.0F;
/* 27 */       this.leftFrontLeg.zRot = 0.0F;
/* 28 */       this.rightFrontLeg.xRot = 0.0F;
/* 29 */       this.rightFrontLeg.zRot = 0.0F;
/* 30 */       this.rightFrontLeg.x = -1.2F;
/* 31 */       this.leftHindLeg.xRot = 0.0F;
/* 32 */       this.rightHindLeg.xRot = 0.0F;
/* 33 */       this.rightHindLeg.zRot = 0.0F;
/* 34 */       this.rightHindLeg.x = -1.1F;
/* 35 */       this.rightHindLeg.y = 18.0F;
/*    */     } 
/*    */     
/* 38 */     super.prepareMobModel($$0, $$1, $$2, $$3);
/*    */     
/* 40 */     if ($$0.isInSittingPose()) {
/* 41 */       this.body.xRot = 0.7853982F;
/* 42 */       this.body.y += -4.0F;
/* 43 */       this.body.z += 5.0F;
/* 44 */       this.head.y += -3.3F;
/* 45 */       this.head.z++;
/*    */       
/* 47 */       this.tail1.y += 8.0F;
/* 48 */       this.tail1.z += -2.0F;
/* 49 */       this.tail2.y += 2.0F;
/* 50 */       this.tail2.z += -0.8F;
/* 51 */       this.tail1.xRot = 1.7278761F;
/* 52 */       this.tail2.xRot = 2.670354F;
/*    */       
/* 54 */       this.leftFrontLeg.xRot = -0.15707964F;
/* 55 */       this.leftFrontLeg.y = 16.1F;
/* 56 */       this.leftFrontLeg.z = -7.0F;
/*    */       
/* 58 */       this.rightFrontLeg.xRot = -0.15707964F;
/* 59 */       this.rightFrontLeg.y = 16.1F;
/* 60 */       this.rightFrontLeg.z = -7.0F;
/*    */       
/* 62 */       this.leftHindLeg.xRot = -1.5707964F;
/* 63 */       this.leftHindLeg.y = 21.0F;
/* 64 */       this.leftHindLeg.z = 1.0F;
/*    */       
/* 66 */       this.rightHindLeg.xRot = -1.5707964F;
/* 67 */       this.rightHindLeg.y = 21.0F;
/* 68 */       this.rightHindLeg.z = 1.0F;
/* 69 */       this.state = 3;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 75 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 77 */     if (this.lieDownAmount > 0.0F) {
/* 78 */       this.head.zRot = ModelUtils.rotlerpRad(this.head.zRot, -1.2707963F, this.lieDownAmount);
/* 79 */       this.head.yRot = ModelUtils.rotlerpRad(this.head.yRot, 1.2707963F, this.lieDownAmount);
/* 80 */       this.leftFrontLeg.xRot = -1.2707963F;
/* 81 */       this.rightFrontLeg.xRot = -0.47079635F;
/* 82 */       this.rightFrontLeg.zRot = -0.2F;
/* 83 */       this.rightFrontLeg.x = -0.2F;
/* 84 */       this.leftHindLeg.xRot = -0.4F;
/* 85 */       this.rightHindLeg.xRot = 0.5F;
/* 86 */       this.rightHindLeg.zRot = -0.5F;
/* 87 */       this.rightHindLeg.x = -0.3F;
/* 88 */       this.rightHindLeg.y = 20.0F;
/*    */       
/* 90 */       this.tail1.xRot = ModelUtils.rotlerpRad(this.tail1.xRot, 0.8F, this.lieDownAmountTail);
/* 91 */       this.tail2.xRot = ModelUtils.rotlerpRad(this.tail2.xRot, -0.4F, this.lieDownAmountTail);
/*    */     } 
/*    */     
/* 94 */     if (this.relaxStateOneAmount > 0.0F)
/* 95 */       this.head.xRot = ModelUtils.rotlerpRad(this.head.xRot, -0.58177644F, this.relaxStateOneAmount); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\CatModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */