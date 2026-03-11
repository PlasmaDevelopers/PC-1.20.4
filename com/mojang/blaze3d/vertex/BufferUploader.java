/*    */ package com.mojang.blaze3d.vertex;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BufferUploader
/*    */ {
/*    */   @Nullable
/*    */   private static VertexBuffer lastImmediateBuffer;
/*    */   
/*    */   public static void reset() {
/* 12 */     if (lastImmediateBuffer != null) {
/* 13 */       invalidate();
/* 14 */       VertexBuffer.unbind();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void invalidate() {
/* 19 */     lastImmediateBuffer = null;
/*    */   }
/*    */   
/*    */   public static void drawWithShader(BufferBuilder.RenderedBuffer $$0) {
/* 23 */     if (!RenderSystem.isOnRenderThreadOrInit()) {
/* 24 */       RenderSystem.recordRenderCall(() -> _drawWithShader($$0));
/*    */     } else {
/* 26 */       _drawWithShader($$0);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void _drawWithShader(BufferBuilder.RenderedBuffer $$0) {
/* 31 */     VertexBuffer $$1 = upload($$0);
/* 32 */     if ($$1 != null) {
/* 33 */       $$1.drawWithShader(RenderSystem.getModelViewMatrix(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
/*    */     }
/*    */   }
/*    */   
/*    */   public static void draw(BufferBuilder.RenderedBuffer $$0) {
/* 38 */     VertexBuffer $$1 = upload($$0);
/* 39 */     if ($$1 != null) {
/* 40 */       $$1.draw();
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static VertexBuffer upload(BufferBuilder.RenderedBuffer $$0) {
/* 46 */     RenderSystem.assertOnRenderThread();
/*    */     
/* 48 */     if ($$0.isEmpty()) {
/* 49 */       $$0.release();
/* 50 */       return null;
/*    */     } 
/*    */     
/* 53 */     VertexBuffer $$1 = bindImmediateBuffer($$0.drawState().format());
/* 54 */     $$1.upload($$0);
/* 55 */     return $$1;
/*    */   }
/*    */   
/*    */   private static VertexBuffer bindImmediateBuffer(VertexFormat $$0) {
/* 59 */     VertexBuffer $$1 = $$0.getImmediateDrawVertexBuffer();
/* 60 */     bindImmediateBuffer($$1);
/* 61 */     return $$1;
/*    */   }
/*    */   
/*    */   private static void bindImmediateBuffer(VertexBuffer $$0) {
/* 65 */     if ($$0 != lastImmediateBuffer) {
/* 66 */       $$0.bind();
/* 67 */       lastImmediateBuffer = $$0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\BufferUploader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */