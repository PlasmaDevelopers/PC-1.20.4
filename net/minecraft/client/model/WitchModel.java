/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class WitchModel<T extends Entity>
/*    */   extends VillagerModel<T> {
/*    */   private boolean holdingItem;
/*    */   
/*    */   public WitchModel(ModelPart $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 22 */     MeshDefinition $$0 = VillagerModel.createBodyModel();
/* 23 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 25 */     PartDefinition $$2 = $$1.addOrReplaceChild("head", 
/* 26 */         CubeListBuilder.create()
/* 27 */         .texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 30 */     PartDefinition $$3 = $$2.addOrReplaceChild("hat", 
/* 31 */         CubeListBuilder.create()
/* 32 */         .texOffs(0, 64).addBox(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F), 
/* 33 */         PartPose.offset(-5.0F, -10.03125F, -5.0F));
/*    */     
/* 35 */     PartDefinition $$4 = $$3.addOrReplaceChild("hat2", 
/* 36 */         CubeListBuilder.create()
/* 37 */         .texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F), 
/* 38 */         PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.05235988F, 0.0F, 0.02617994F));
/*    */     
/* 40 */     PartDefinition $$5 = $$4.addOrReplaceChild("hat3", 
/* 41 */         CubeListBuilder.create()
/* 42 */         .texOffs(0, 87).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F), 
/* 43 */         PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.10471976F, 0.0F, 0.05235988F));
/*    */     
/* 45 */     $$5.addOrReplaceChild("hat4", 
/* 46 */         CubeListBuilder.create()
/* 47 */         .texOffs(0, 95).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), 
/* 48 */         PartPose.offsetAndRotation(1.75F, -2.0F, 2.0F, -0.20943952F, 0.0F, 0.10471976F));
/*    */ 
/*    */     
/* 51 */     PartDefinition $$6 = $$2.getChild("nose");
/* 52 */     $$6.addOrReplaceChild("mole", 
/* 53 */         CubeListBuilder.create()
/* 54 */         .texOffs(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), 
/* 55 */         PartPose.offset(0.0F, -2.0F, 0.0F));
/*    */ 
/*    */     
/* 58 */     return LayerDefinition.create($$0, 64, 128);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 63 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 65 */     this.nose.setPos(0.0F, -2.0F, 0.0F);
/*    */     
/* 67 */     float $$6 = 0.01F * ($$0.getId() % 10);
/* 68 */     this.nose.xRot = Mth.sin(((Entity)$$0).tickCount * $$6) * 4.5F * 0.017453292F;
/* 69 */     this.nose.yRot = 0.0F;
/* 70 */     this.nose.zRot = Mth.cos(((Entity)$$0).tickCount * $$6) * 2.5F * 0.017453292F;
/*    */     
/* 72 */     if (this.holdingItem) {
/* 73 */       this.nose.setPos(0.0F, 1.0F, -1.5F);
/* 74 */       this.nose.xRot = -0.9F;
/*    */     } 
/*    */   }
/*    */   
/*    */   public ModelPart getNose() {
/* 79 */     return this.nose;
/*    */   }
/*    */   
/*    */   public void setHoldingItem(boolean $$0) {
/* 83 */     this.holdingItem = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\WitchModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */