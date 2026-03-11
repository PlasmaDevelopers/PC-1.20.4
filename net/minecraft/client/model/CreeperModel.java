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
/*    */ public class CreeperModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private final ModelPart root;
/*    */   private final ModelPart head;
/*    */   private final ModelPart rightHindLeg;
/*    */   private final ModelPart leftHindLeg;
/*    */   private final ModelPart rightFrontLeg;
/*    */   private final ModelPart leftFrontLeg;
/*    */   private static final int Y_OFFSET = 6;
/*    */   
/*    */   public CreeperModel(ModelPart $$0) {
/* 24 */     this.root = $$0;
/* 25 */     this.head = $$0.getChild("head");
/* 26 */     this.leftHindLeg = $$0.getChild("right_hind_leg");
/* 27 */     this.rightHindLeg = $$0.getChild("left_hind_leg");
/* 28 */     this.leftFrontLeg = $$0.getChild("right_front_leg");
/* 29 */     this.rightFrontLeg = $$0.getChild("left_front_leg");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
/* 33 */     MeshDefinition $$1 = new MeshDefinition();
/* 34 */     PartDefinition $$2 = $$1.getRoot();
/*    */     
/* 36 */     $$2.addOrReplaceChild("head", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$0), 
/* 39 */         PartPose.offset(0.0F, 6.0F, 0.0F));
/*    */ 
/*    */ 
/*    */     
/* 43 */     $$2.addOrReplaceChild("body", 
/* 44 */         CubeListBuilder.create()
/* 45 */         .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, $$0), 
/* 46 */         PartPose.offset(0.0F, 6.0F, 0.0F));
/*    */ 
/*    */ 
/*    */     
/* 50 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, $$0);
/* 51 */     $$2.addOrReplaceChild("right_hind_leg", $$3, PartPose.offset(-2.0F, 18.0F, 4.0F));
/* 52 */     $$2.addOrReplaceChild("left_hind_leg", $$3, PartPose.offset(2.0F, 18.0F, 4.0F));
/* 53 */     $$2.addOrReplaceChild("right_front_leg", $$3, PartPose.offset(-2.0F, 18.0F, -4.0F));
/* 54 */     $$2.addOrReplaceChild("left_front_leg", $$3, PartPose.offset(2.0F, 18.0F, -4.0F));
/*    */     
/* 56 */     return LayerDefinition.create($$1, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 61 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 66 */     this.head.yRot = $$4 * 0.017453292F;
/* 67 */     this.head.xRot = $$5 * 0.017453292F;
/*    */     
/* 69 */     this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/* 70 */     this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/* 71 */     this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/* 72 */     this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\CreeperModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */