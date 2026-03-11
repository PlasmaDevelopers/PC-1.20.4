/*    */ package net.minecraft.client.model.dragon;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.SkullModelBase;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ 
/*    */ public class DragonHeadModel
/*    */   extends SkullModelBase
/*    */ {
/*    */   private final ModelPart head;
/*    */   private final ModelPart jaw;
/*    */   
/*    */   public DragonHeadModel(ModelPart $$0) {
/* 20 */     this.head = $$0.getChild("head");
/* 21 */     this.jaw = this.head.getChild("jaw");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createHeadLayer() {
/* 25 */     MeshDefinition $$0 = new MeshDefinition();
/* 26 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 28 */     float $$2 = -16.0F;
/* 29 */     PartDefinition $$3 = $$1.addOrReplaceChild("head", 
/* 30 */         CubeListBuilder.create()
/* 31 */         .addBox("upper_lip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 176, 44)
/* 32 */         .addBox("upper_head", -8.0F, -8.0F, -10.0F, 16, 16, 16, 112, 30)
/* 33 */         .mirror(true)
/* 34 */         .addBox("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
/* 35 */         .addBox("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0)
/* 36 */         .mirror(false)
/* 37 */         .addBox("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
/* 38 */         .addBox("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 42 */     $$3.addOrReplaceChild("jaw", 
/* 43 */         CubeListBuilder.create()
/* 44 */         .texOffs(176, 65).addBox("jaw", -6.0F, 0.0F, -16.0F, 12.0F, 4.0F, 16.0F), 
/* 45 */         PartPose.offset(0.0F, 4.0F, -8.0F));
/*    */ 
/*    */     
/* 48 */     return LayerDefinition.create($$0, 256, 256);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(float $$0, float $$1, float $$2) {
/* 53 */     this.jaw.xRot = (float)(Math.sin(($$0 * 3.1415927F * 0.2F)) + 1.0D) * 0.2F;
/*    */     
/* 55 */     this.head.yRot = $$1 * 0.017453292F;
/* 56 */     this.head.xRot = $$2 * 0.017453292F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 61 */     $$0.pushPose();
/* 62 */     $$0.translate(0.0F, -0.374375F, 0.0F);
/* 63 */     $$0.scale(0.75F, 0.75F, 0.75F);
/*    */     
/* 65 */     this.head.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 66 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\dragon\DragonHeadModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */