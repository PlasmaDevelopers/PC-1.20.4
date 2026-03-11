/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.player.AbstractClientPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ElytraModel<T extends LivingEntity>
/*    */   extends AgeableListModel<T> {
/*    */   private final ModelPart rightWing;
/*    */   private final ModelPart leftWing;
/*    */   
/*    */   public ElytraModel(ModelPart $$0) {
/* 22 */     this.leftWing = $$0.getChild("left_wing");
/* 23 */     this.rightWing = $$0.getChild("right_wing");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createLayer() {
/* 27 */     MeshDefinition $$0 = new MeshDefinition();
/* 28 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 30 */     CubeDeformation $$2 = new CubeDeformation(1.0F);
/* 31 */     $$1.addOrReplaceChild("left_wing", 
/* 32 */         CubeListBuilder.create()
/* 33 */         .texOffs(22, 0).addBox(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, $$2), 
/* 34 */         PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, -0.2617994F));
/*    */     
/* 36 */     $$1.addOrReplaceChild("right_wing", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(22, 0).mirror().addBox(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, $$2), 
/* 39 */         PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.2617994F, 0.0F, 0.2617994F));
/*    */ 
/*    */     
/* 42 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<ModelPart> headParts() {
/* 47 */     return (Iterable<ModelPart>)ImmutableList.of();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<ModelPart> bodyParts() {
/* 52 */     return (Iterable<ModelPart>)ImmutableList.of(this.leftWing, this.rightWing);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 57 */     float $$6 = 0.2617994F;
/* 58 */     float $$7 = -0.2617994F;
/* 59 */     float $$8 = 0.0F;
/* 60 */     float $$9 = 0.0F;
/*    */     
/* 62 */     if ($$0.isFallFlying()) {
/*    */       
/* 64 */       float $$10 = 1.0F;
/* 65 */       Vec3 $$11 = $$0.getDeltaMovement();
/* 66 */       if ($$11.y < 0.0D) {
/* 67 */         Vec3 $$12 = $$11.normalize();
/* 68 */         $$10 = 1.0F - (float)Math.pow(-$$12.y, 1.5D);
/*    */       } 
/*    */       
/* 71 */       $$6 = $$10 * 0.34906584F + (1.0F - $$10) * $$6;
/* 72 */       $$7 = $$10 * -1.5707964F + (1.0F - $$10) * $$7;
/* 73 */     } else if ($$0.isCrouching()) {
/* 74 */       $$6 = 0.6981317F;
/* 75 */       $$7 = -0.7853982F;
/* 76 */       $$8 = 3.0F;
/* 77 */       $$9 = 0.08726646F;
/*    */     } 
/*    */     
/* 80 */     this.leftWing.y = $$8;
/*    */     
/* 82 */     if ($$0 instanceof AbstractClientPlayer) { AbstractClientPlayer $$13 = (AbstractClientPlayer)$$0;
/* 83 */       $$13.elytraRotX += ($$6 - $$13.elytraRotX) * 0.1F;
/* 84 */       $$13.elytraRotY += ($$9 - $$13.elytraRotY) * 0.1F;
/* 85 */       $$13.elytraRotZ += ($$7 - $$13.elytraRotZ) * 0.1F;
/* 86 */       this.leftWing.xRot = $$13.elytraRotX;
/* 87 */       this.leftWing.yRot = $$13.elytraRotY;
/* 88 */       this.leftWing.zRot = $$13.elytraRotZ; }
/*    */     else
/* 90 */     { this.leftWing.xRot = $$6;
/* 91 */       this.leftWing.zRot = $$7;
/* 92 */       this.leftWing.yRot = $$9; }
/*    */ 
/*    */     
/* 95 */     this.rightWing.yRot = -this.leftWing.yRot;
/* 96 */     this.rightWing.y = this.leftWing.y;
/* 97 */     this.rightWing.xRot = this.leftWing.xRot;
/* 98 */     this.rightWing.zRot = -this.leftWing.zRot;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ElytraModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */