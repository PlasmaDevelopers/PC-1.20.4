/*     */ package com.mojang.blaze3d.pipeline;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ public class MainTarget
/*     */   extends RenderTarget
/*     */ {
/*     */   public static final int DEFAULT_WIDTH = 854;
/*     */   public static final int DEFAULT_HEIGHT = 480;
/*  16 */   static final Dimension DEFAULT_DIMENSIONS = new Dimension(854, 480);
/*     */   
/*     */   public MainTarget(int $$0, int $$1) {
/*  19 */     super(true);
/*  20 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/*  22 */     if (!RenderSystem.isOnRenderThread()) {
/*  23 */       RenderSystem.recordRenderCall(() -> createFrameBuffer($$0, $$1));
/*     */     } else {
/*  25 */       createFrameBuffer($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createFrameBuffer(int $$0, int $$1) {
/*  30 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/*  32 */     Dimension $$2 = allocateAttachments($$0, $$1);
/*     */     
/*  34 */     this.frameBufferId = GlStateManager.glGenFramebuffers();
/*  35 */     GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
/*     */     
/*  37 */     GlStateManager._bindTexture(this.colorTextureId);
/*  38 */     GlStateManager._texParameter(3553, 10241, 9728);
/*  39 */     GlStateManager._texParameter(3553, 10240, 9728);
/*  40 */     GlStateManager._texParameter(3553, 10242, 33071);
/*  41 */     GlStateManager._texParameter(3553, 10243, 33071);
/*  42 */     GlStateManager._glFramebufferTexture2D(36160, 36064, 3553, this.colorTextureId, 0);
/*     */     
/*  44 */     GlStateManager._bindTexture(this.depthBufferId);
/*  45 */     GlStateManager._texParameter(3553, 34892, 0);
/*  46 */     GlStateManager._texParameter(3553, 10241, 9728);
/*  47 */     GlStateManager._texParameter(3553, 10240, 9728);
/*  48 */     GlStateManager._texParameter(3553, 10242, 33071);
/*  49 */     GlStateManager._texParameter(3553, 10243, 33071);
/*  50 */     GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.depthBufferId, 0);
/*     */     
/*  52 */     GlStateManager._bindTexture(0);
/*     */     
/*  54 */     this.viewWidth = $$2.width;
/*  55 */     this.viewHeight = $$2.height;
/*     */     
/*  57 */     this.width = $$2.width;
/*  58 */     this.height = $$2.height;
/*     */     
/*  60 */     checkStatus();
/*  61 */     GlStateManager._glBindFramebuffer(36160, 0);
/*     */   }
/*     */   
/*     */   private Dimension allocateAttachments(int $$0, int $$1) {
/*  65 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/*  67 */     this.colorTextureId = TextureUtil.generateTextureId();
/*  68 */     this.depthBufferId = TextureUtil.generateTextureId();
/*     */     
/*  70 */     AttachmentState $$2 = AttachmentState.NONE;
/*  71 */     for (Dimension $$3 : Dimension.listWithFallback($$0, $$1)) {
/*  72 */       $$2 = AttachmentState.NONE;
/*     */       
/*  74 */       if (allocateColorAttachment($$3)) {
/*  75 */         $$2 = $$2.with(AttachmentState.COLOR);
/*     */       }
/*  77 */       if (allocateDepthAttachment($$3)) {
/*  78 */         $$2 = $$2.with(AttachmentState.DEPTH);
/*     */       }
/*     */       
/*  81 */       if ($$2 == AttachmentState.COLOR_DEPTH) {
/*  82 */         return $$3;
/*     */       }
/*     */     } 
/*     */     
/*  86 */     throw new RuntimeException("Unrecoverable GL_OUT_OF_MEMORY (allocated attachments = " + $$2.name() + ")");
/*     */   }
/*     */   
/*     */   private boolean allocateColorAttachment(Dimension $$0) {
/*  90 */     RenderSystem.assertOnRenderThreadOrInit();
/*  91 */     GlStateManager._getError();
/*     */     
/*  93 */     GlStateManager._bindTexture(this.colorTextureId);
/*  94 */     GlStateManager._texImage2D(3553, 0, 32856, $$0.width, $$0.height, 0, 6408, 5121, null);
/*     */     
/*  96 */     return (GlStateManager._getError() != 1285);
/*     */   }
/*     */   
/*     */   private boolean allocateDepthAttachment(Dimension $$0) {
/* 100 */     RenderSystem.assertOnRenderThreadOrInit();
/* 101 */     GlStateManager._getError();
/*     */     
/* 103 */     GlStateManager._bindTexture(this.depthBufferId);
/* 104 */     GlStateManager._texImage2D(3553, 0, 6402, $$0.width, $$0.height, 0, 6402, 5126, null);
/*     */     
/* 106 */     return (GlStateManager._getError() != 1285);
/*     */   }
/*     */   
/*     */   private enum AttachmentState {
/* 110 */     NONE,
/* 111 */     COLOR,
/* 112 */     DEPTH,
/* 113 */     COLOR_DEPTH;
/*     */     
/* 115 */     private static final AttachmentState[] VALUES = values();
/*     */     
/*     */     AttachmentState with(AttachmentState $$0) {
/* 118 */       return VALUES[ordinal() | $$0.ordinal()];
/*     */     }
/*     */     static {
/*     */     
/*     */     } }
/*     */   private static class Dimension { public final int width;
/*     */     public final int height;
/*     */     
/*     */     Dimension(int $$0, int $$1) {
/* 127 */       this.width = $$0;
/* 128 */       this.height = $$1;
/*     */     }
/*     */     
/*     */     static List<Dimension> listWithFallback(int $$0, int $$1) {
/* 132 */       RenderSystem.assertOnRenderThreadOrInit();
/*     */       
/* 134 */       int $$2 = RenderSystem.maxSupportedTextureSize();
/* 135 */       if ($$0 <= 0 || $$0 > $$2 || $$1 <= 0 || $$1 > $$2) {
/* 136 */         return (List<Dimension>)ImmutableList.of(MainTarget.DEFAULT_DIMENSIONS);
/*     */       }
/* 138 */       return (List<Dimension>)ImmutableList.of(new Dimension($$0, $$1), MainTarget.DEFAULT_DIMENSIONS);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 147 */       if (this == $$0) {
/* 148 */         return true;
/*     */       }
/* 150 */       if ($$0 == null || getClass() != $$0.getClass()) {
/* 151 */         return false;
/*     */       }
/* 153 */       Dimension $$1 = (Dimension)$$0;
/* 154 */       return (this.width == $$1.width && this.height == $$1.height);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 159 */       return Objects.hash(new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height) });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 164 */       return "" + this.width + "x" + this.width;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\pipeline\MainTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */