/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class QuadrupedModel<T extends Entity>
/*    */   extends AgeableListModel<T> {
/*    */   protected final ModelPart head;
/*    */   protected final ModelPart body;
/*    */   protected final ModelPart rightHindLeg;
/*    */   protected final ModelPart leftHindLeg;
/*    */   protected final ModelPart rightFrontLeg;
/*    */   protected final ModelPart leftFrontLeg;
/*    */   
/*    */   protected QuadrupedModel(ModelPart $$0, boolean $$1, float $$2, float $$3, float $$4, float $$5, int $$6) {
/* 23 */     super($$1, $$2, $$3, $$4, $$5, $$6);
/* 24 */     this.head = $$0.getChild("head");
/* 25 */     this.body = $$0.getChild("body");
/* 26 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/* 27 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/* 28 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/* 29 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*    */   }
/*    */   
/*    */   public static MeshDefinition createBodyMesh(int $$0, CubeDeformation $$1) {
/* 33 */     MeshDefinition $$2 = new MeshDefinition();
/* 34 */     PartDefinition $$3 = $$2.getRoot();
/*    */     
/* 36 */     $$3.addOrReplaceChild("head", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, $$1), 
/* 39 */         PartPose.offset(0.0F, (18 - $$0), -6.0F));
/*    */     
/* 41 */     $$3.addOrReplaceChild("body", 
/* 42 */         CubeListBuilder.create()
/* 43 */         .texOffs(28, 8).addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, $$1), 
/* 44 */         PartPose.offsetAndRotation(0.0F, (17 - $$0), 2.0F, 1.5707964F, 0.0F, 0.0F));
/*    */ 
/*    */     
/* 47 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, $$0, 4.0F, $$1);
/* 48 */     $$3.addOrReplaceChild("right_hind_leg", $$4, PartPose.offset(-3.0F, (24 - $$0), 7.0F));
/* 49 */     $$3.addOrReplaceChild("left_hind_leg", $$4, PartPose.offset(3.0F, (24 - $$0), 7.0F));
/* 50 */     $$3.addOrReplaceChild("right_front_leg", $$4, PartPose.offset(-3.0F, (24 - $$0), -5.0F));
/* 51 */     $$3.addOrReplaceChild("left_front_leg", $$4, PartPose.offset(3.0F, (24 - $$0), -5.0F));
/*    */     
/* 53 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<ModelPart> headParts() {
/* 58 */     return (Iterable<ModelPart>)ImmutableList.of(this.head);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<ModelPart> bodyParts() {
/* 63 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 68 */     this.head.xRot = $$5 * 0.017453292F;
/* 69 */     this.head.yRot = $$4 * 0.017453292F;
/*    */     
/* 71 */     this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/* 72 */     this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/* 73 */     this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * 1.4F * $$2;
/* 74 */     this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F) * 1.4F * $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\QuadrupedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */