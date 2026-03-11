/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ 
/*    */ public class PiglinHeadModel
/*    */   extends SkullModelBase
/*    */ {
/*    */   private final ModelPart head;
/*    */   private final ModelPart leftEar;
/*    */   private final ModelPart rightEar;
/*    */   
/*    */   public PiglinHeadModel(ModelPart $$0) {
/* 17 */     this.head = $$0.getChild("head");
/* 18 */     this.leftEar = this.head.getChild("left_ear");
/* 19 */     this.rightEar = this.head.getChild("right_ear");
/*    */   }
/*    */   
/*    */   public static MeshDefinition createHeadModel() {
/* 23 */     MeshDefinition $$0 = new MeshDefinition();
/* 24 */     PiglinModel.addHead(CubeDeformation.NONE, $$0);
/*    */     
/* 26 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(float $$0, float $$1, float $$2) {
/* 31 */     this.head.yRot = $$1 * 0.017453292F;
/* 32 */     this.head.xRot = $$2 * 0.017453292F;
/*    */     
/* 34 */     float $$3 = 1.2F;
/* 35 */     this.leftEar.zRot = (float)-(Math.cos(($$0 * 3.1415927F * 0.2F * 1.2F)) + 2.5D) * 0.2F;
/* 36 */     this.rightEar.zRot = (float)(Math.cos(($$0 * 3.1415927F * 0.2F)) + 2.5D) * 0.2F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 41 */     this.head.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PiglinHeadModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */