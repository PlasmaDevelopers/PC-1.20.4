/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ 
/*    */ public class ShieldModel
/*    */   extends Model {
/*    */   private static final String PLATE = "plate";
/*    */   private static final String HANDLE = "handle";
/*    */   private static final int SHIELD_WIDTH = 10;
/*    */   private static final int SHIELD_HEIGHT = 20;
/*    */   private final ModelPart root;
/*    */   private final ModelPart plate;
/*    */   private final ModelPart handle;
/*    */   
/*    */   public ShieldModel(ModelPart $$0) {
/* 24 */     super(RenderType::entitySolid);
/* 25 */     this.root = $$0;
/* 26 */     this.plate = $$0.getChild("plate");
/* 27 */     this.handle = $$0.getChild("handle");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createLayer() {
/* 31 */     MeshDefinition $$0 = new MeshDefinition();
/* 32 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 34 */     $$1.addOrReplaceChild("plate", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(0, 0).addBox(-6.0F, -11.0F, -2.0F, 12.0F, 22.0F, 1.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 39 */     $$1.addOrReplaceChild("handle", 
/* 40 */         CubeListBuilder.create()
/* 41 */         .texOffs(26, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 45 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */   
/*    */   public ModelPart plate() {
/* 49 */     return this.plate;
/*    */   }
/*    */   
/*    */   public ModelPart handle() {
/* 53 */     return this.handle;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 58 */     this.root.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ShieldModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */