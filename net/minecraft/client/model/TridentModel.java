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
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class TridentModel extends Model {
/* 15 */   public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/trident.png");
/*    */   
/*    */   private final ModelPart root;
/*    */   
/*    */   public TridentModel(ModelPart $$0) {
/* 20 */     super(RenderType::entitySolid);
/* 21 */     this.root = $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createLayer() {
/* 25 */     MeshDefinition $$0 = new MeshDefinition();
/* 26 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 28 */     PartDefinition $$2 = $$1.addOrReplaceChild("pole", 
/* 29 */         CubeListBuilder.create()
/* 30 */         .texOffs(0, 6).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 33 */     $$2.addOrReplaceChild("base", 
/* 34 */         CubeListBuilder.create()
/* 35 */         .texOffs(4, 0).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 38 */     $$2.addOrReplaceChild("left_spike", 
/* 39 */         CubeListBuilder.create()
/* 40 */         .texOffs(4, 3).addBox(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 43 */     $$2.addOrReplaceChild("middle_spike", 
/* 44 */         CubeListBuilder.create()
/* 45 */         .texOffs(0, 0).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 48 */     $$2.addOrReplaceChild("right_spike", 
/* 49 */         CubeListBuilder.create()
/* 50 */         .texOffs(4, 3).mirror().addBox(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 54 */     return LayerDefinition.create($$0, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 59 */     this.root.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\TridentModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */