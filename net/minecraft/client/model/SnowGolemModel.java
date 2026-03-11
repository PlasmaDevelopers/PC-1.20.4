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
/*    */ public class SnowGolemModel<T extends Entity>
/*    */   extends HierarchicalModel<T>
/*    */ {
/*    */   private static final String UPPER_BODY = "upper_body";
/*    */   private final ModelPart root;
/*    */   private final ModelPart upperBody;
/*    */   private final ModelPart head;
/*    */   private final ModelPart leftArm;
/*    */   private final ModelPart rightArm;
/*    */   
/*    */   public SnowGolemModel(ModelPart $$0) {
/* 24 */     this.root = $$0;
/* 25 */     this.head = $$0.getChild("head");
/* 26 */     this.leftArm = $$0.getChild("left_arm");
/* 27 */     this.rightArm = $$0.getChild("right_arm");
/* 28 */     this.upperBody = $$0.getChild("upper_body");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 32 */     MeshDefinition $$0 = new MeshDefinition();
/* 33 */     PartDefinition $$1 = $$0.getRoot();
/* 34 */     float $$2 = 4.0F;
/*    */     
/* 36 */     CubeDeformation $$3 = new CubeDeformation(-0.5F);
/*    */     
/* 38 */     $$1.addOrReplaceChild("head", 
/* 39 */         CubeListBuilder.create()
/* 40 */         .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, $$3), 
/* 41 */         PartPose.offset(0.0F, 4.0F, 0.0F));
/*    */ 
/*    */     
/* 44 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 12.0F, 2.0F, 2.0F, $$3);
/* 45 */     $$1.addOrReplaceChild("left_arm", $$4, PartPose.offsetAndRotation(5.0F, 6.0F, 1.0F, 0.0F, 0.0F, 1.0F));
/* 46 */     $$1.addOrReplaceChild("right_arm", $$4, PartPose.offsetAndRotation(-5.0F, 6.0F, -1.0F, 0.0F, 3.1415927F, -1.0F));
/*    */     
/* 48 */     $$1.addOrReplaceChild("upper_body", 
/* 49 */         CubeListBuilder.create()
/* 50 */         .texOffs(0, 16).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, $$3), 
/* 51 */         PartPose.offset(0.0F, 13.0F, 0.0F));
/*    */     
/* 53 */     $$1.addOrReplaceChild("lower_body", 
/* 54 */         CubeListBuilder.create()
/* 55 */         .texOffs(0, 36).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, $$3), 
/* 56 */         PartPose.offset(0.0F, 24.0F, 0.0F));
/*    */ 
/*    */     
/* 59 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 64 */     this.head.yRot = $$4 * 0.017453292F;
/* 65 */     this.head.xRot = $$5 * 0.017453292F;
/* 66 */     this.upperBody.yRot = $$4 * 0.017453292F * 0.25F;
/*    */     
/* 68 */     float $$6 = Mth.sin(this.upperBody.yRot);
/* 69 */     float $$7 = Mth.cos(this.upperBody.yRot);
/*    */     
/* 71 */     this.leftArm.yRot = this.upperBody.yRot;
/* 72 */     this.upperBody.yRot += 3.1415927F;
/*    */     
/* 74 */     this.leftArm.x = $$7 * 5.0F;
/* 75 */     this.leftArm.z = -$$6 * 5.0F;
/*    */     
/* 77 */     this.rightArm.x = -$$7 * 5.0F;
/* 78 */     this.rightArm.z = $$6 * 5.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 83 */     return this.root;
/*    */   }
/*    */   
/*    */   public ModelPart getHead() {
/* 87 */     return this.head;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SnowGolemModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */