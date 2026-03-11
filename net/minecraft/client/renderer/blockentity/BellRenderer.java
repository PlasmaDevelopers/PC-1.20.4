/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.resources.model.Material;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.block.entity.BellBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ 
/*    */ public class BellRenderer implements BlockEntityRenderer<BellBlockEntity> {
/* 22 */   public static final Material BELL_RESOURCE_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/bell/bell_body"));
/*    */   
/*    */   private static final String BELL_BODY = "bell_body";
/*    */   private final ModelPart bellBody;
/*    */   
/*    */   public BellRenderer(BlockEntityRendererProvider.Context $$0) {
/* 28 */     ModelPart $$1 = $$0.bakeLayer(ModelLayers.BELL);
/* 29 */     this.bellBody = $$1.getChild("bell_body");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 33 */     MeshDefinition $$0 = new MeshDefinition();
/* 34 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 36 */     PartDefinition $$2 = $$1.addOrReplaceChild("bell_body", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F), 
/* 39 */         PartPose.offset(8.0F, 12.0F, 8.0F));
/*    */     
/* 41 */     $$2.addOrReplaceChild("bell_base", 
/* 42 */         CubeListBuilder.create()
/* 43 */         .texOffs(0, 13).addBox(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F), 
/* 44 */         PartPose.offset(-8.0F, -12.0F, -8.0F));
/*    */ 
/*    */     
/* 47 */     return LayerDefinition.create($$0, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(BellBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 52 */     float $$6 = $$0.ticks + $$1;
/*    */     
/* 54 */     float $$7 = 0.0F;
/* 55 */     float $$8 = 0.0F;
/*    */     
/* 57 */     if ($$0.shaking) {
/* 58 */       float $$9 = Mth.sin($$6 / 3.1415927F) / (4.0F + $$6 / 3.0F);
/* 59 */       if ($$0.clickDirection == Direction.NORTH) {
/* 60 */         $$7 = -$$9;
/* 61 */       } else if ($$0.clickDirection == Direction.SOUTH) {
/* 62 */         $$7 = $$9;
/* 63 */       } else if ($$0.clickDirection == Direction.EAST) {
/* 64 */         $$8 = -$$9;
/* 65 */       } else if ($$0.clickDirection == Direction.WEST) {
/* 66 */         $$8 = $$9;
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     this.bellBody.xRot = $$7;
/* 71 */     this.bellBody.zRot = $$8;
/*    */     
/* 73 */     VertexConsumer $$10 = BELL_RESOURCE_LOCATION.buffer($$3, RenderType::entitySolid);
/* 74 */     this.bellBody.render($$2, $$10, $$4, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BellRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */