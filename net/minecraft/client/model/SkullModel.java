/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ 
/*    */ public class SkullModel
/*    */   extends SkullModelBase
/*    */ {
/*    */   private final ModelPart root;
/*    */   protected final ModelPart head;
/*    */   
/*    */   public SkullModel(ModelPart $$0) {
/* 20 */     this.root = $$0;
/* 21 */     this.head = $$0.getChild("head");
/*    */   }
/*    */   
/*    */   public static MeshDefinition createHeadModel() {
/* 25 */     MeshDefinition $$0 = new MeshDefinition();
/* 26 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 28 */     $$1.addOrReplaceChild("head", 
/* 29 */         CubeListBuilder.create()
/* 30 */         .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 33 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createHumanoidHeadLayer() {
/* 37 */     MeshDefinition $$0 = createHeadModel();
/*    */     
/* 39 */     PartDefinition $$1 = $$0.getRoot();
/* 40 */     $$1.getChild("head").addOrReplaceChild("hat", 
/* 41 */         CubeListBuilder.create()
/* 42 */         .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 46 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */   
/*    */   public static LayerDefinition createMobHeadLayer() {
/* 50 */     MeshDefinition $$0 = createHeadModel();
/* 51 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(float $$0, float $$1, float $$2) {
/* 56 */     this.head.yRot = $$1 * 0.017453292F;
/* 57 */     this.head.xRot = $$2 * 0.017453292F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 62 */     this.root.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SkullModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */