/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class TheEndPortalRenderer<T extends TheEndPortalBlockEntity> implements BlockEntityRenderer<T> {
/* 13 */   public static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");
/* 14 */   public static final ResourceLocation END_PORTAL_LOCATION = new ResourceLocation("textures/entity/end_portal.png");
/*    */ 
/*    */   
/*    */   public TheEndPortalRenderer(BlockEntityRendererProvider.Context $$0) {}
/*    */   
/*    */   public void render(T $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 20 */     Matrix4f $$6 = $$2.last().pose();
/*    */     
/* 22 */     renderCube($$0, $$6, $$3.getBuffer(renderType()));
/*    */   }
/*    */   
/*    */   private void renderCube(T $$0, Matrix4f $$1, VertexConsumer $$2) {
/* 26 */     float $$3 = getOffsetDown();
/* 27 */     float $$4 = getOffsetUp();
/*    */     
/* 29 */     renderFace($$0, $$1, $$2, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
/* 30 */     renderFace($$0, $$1, $$2, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
/* 31 */     renderFace($$0, $$1, $$2, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
/* 32 */     renderFace($$0, $$1, $$2, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
/* 33 */     renderFace($$0, $$1, $$2, 0.0F, 1.0F, $$3, $$3, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
/* 34 */     renderFace($$0, $$1, $$2, 0.0F, 1.0F, $$4, $$4, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
/*    */   }
/*    */   
/*    */   private void renderFace(T $$0, Matrix4f $$1, VertexConsumer $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, Direction $$11) {
/* 38 */     if ($$0.shouldRenderFace($$11)) {
/* 39 */       $$2.vertex($$1, $$3, $$5, $$7).endVertex();
/* 40 */       $$2.vertex($$1, $$4, $$5, $$8).endVertex();
/* 41 */       $$2.vertex($$1, $$4, $$6, $$9).endVertex();
/* 42 */       $$2.vertex($$1, $$3, $$6, $$10).endVertex();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected float getOffsetUp() {
/* 47 */     return 0.75F;
/*    */   }
/*    */   
/*    */   protected float getOffsetDown() {
/* 51 */     return 0.375F;
/*    */   }
/*    */   
/*    */   protected RenderType renderType() {
/* 55 */     return RenderType.endPortal();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\TheEndPortalRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */