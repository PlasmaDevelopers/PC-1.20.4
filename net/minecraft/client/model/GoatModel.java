/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.goat.Goat;
/*    */ 
/*    */ public class GoatModel<T extends Goat> extends QuadrupedModel<T> {
/*    */   public GoatModel(ModelPart $$0) {
/* 14 */     super($$0, true, 19.0F, 1.0F, 2.5F, 2.0F, 24);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 18 */     MeshDefinition $$0 = new MeshDefinition();
/* 19 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 21 */     PartDefinition $$2 = $$1.addOrReplaceChild("head", 
/* 22 */         CubeListBuilder.create()
/* 23 */         .texOffs(2, 61).addBox("right ear", -6.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F)
/* 24 */         .texOffs(2, 61).mirror().addBox("left ear", 2.0F, -11.0F, -10.0F, 3.0F, 2.0F, 1.0F)
/* 25 */         .texOffs(23, 52).addBox("goatee", -0.5F, -3.0F, -14.0F, 0.0F, 7.0F, 5.0F), 
/* 26 */         PartPose.offset(1.0F, 14.0F, 0.0F));
/*    */     
/* 28 */     $$2.addOrReplaceChild("left_horn", 
/* 29 */         CubeListBuilder.create()
/* 30 */         .texOffs(12, 55).addBox(-0.01F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), 
/* 31 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*    */     
/* 33 */     $$2.addOrReplaceChild("right_horn", 
/* 34 */         CubeListBuilder.create()
/* 35 */         .texOffs(12, 55).addBox(-2.99F, -16.0F, -10.0F, 2.0F, 7.0F, 2.0F), 
/* 36 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*    */     
/* 38 */     $$2.addOrReplaceChild("nose", 
/* 39 */         CubeListBuilder.create()
/* 40 */         .texOffs(34, 46).addBox(-3.0F, -4.0F, -8.0F, 5.0F, 7.0F, 10.0F), 
/* 41 */         PartPose.offsetAndRotation(0.0F, -8.0F, -8.0F, 0.9599F, 0.0F, 0.0F));
/*    */     
/* 43 */     $$1.addOrReplaceChild("body", 
/* 44 */         CubeListBuilder.create()
/* 45 */         .texOffs(1, 1).addBox(-4.0F, -17.0F, -7.0F, 9.0F, 11.0F, 16.0F)
/* 46 */         .texOffs(0, 28).addBox(-5.0F, -18.0F, -8.0F, 11.0F, 14.0F, 11.0F), 
/* 47 */         PartPose.offset(0.0F, 24.0F, 0.0F));
/*    */ 
/*    */     
/* 50 */     $$1.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(36, 29).addBox(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), PartPose.offset(1.0F, 14.0F, 4.0F));
/* 51 */     $$1.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(49, 29).addBox(0.0F, 4.0F, 0.0F, 3.0F, 6.0F, 3.0F), PartPose.offset(-3.0F, 14.0F, 4.0F));
/* 52 */     $$1.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(49, 2).addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), PartPose.offset(1.0F, 14.0F, -6.0F));
/* 53 */     $$1.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(35, 2).addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F), PartPose.offset(-3.0F, 14.0F, -6.0F));
/*    */     
/* 55 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 60 */     (this.head.getChild("left_horn")).visible = $$0.hasLeftHorn();
/* 61 */     (this.head.getChild("right_horn")).visible = $$0.hasRightHorn();
/*    */     
/* 63 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 65 */     float $$6 = $$0.getRammingXHeadRot();
/* 66 */     if ($$6 != 0.0F)
/* 67 */       this.head.xRot = $$6; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\GoatModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */