/*     */ package com.mojang.blaze3d.pipeline;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.BufferUploader;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import com.mojang.blaze3d.vertex.VertexSorting;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ShaderInstance;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RenderTarget
/*     */ {
/*     */   private static final int RED_CHANNEL = 0;
/*     */   private static final int GREEN_CHANNEL = 1;
/*     */   private static final int BLUE_CHANNEL = 2;
/*     */   private static final int ALPHA_CHANNEL = 3;
/*     */   public int width;
/*     */   public int height;
/*     */   public int viewWidth;
/*     */   
/*     */   public RenderTarget(boolean $$0) {
/*  34 */     this.clearChannels = (float[])Util.make(() -> {
/*     */           float[] $$0 = new float[4];
/*     */           
/*     */           $$0[0] = 1.0F;
/*     */           
/*     */           $$0[1] = 1.0F;
/*     */           
/*     */           $$0[2] = 1.0F;
/*     */           
/*     */           $$0[3] = 0.0F;
/*     */           return (Supplier)$$0;
/*     */         });
/*  46 */     this.useDepth = $$0;
/*     */     
/*  48 */     this.frameBufferId = -1;
/*  49 */     this.colorTextureId = -1;
/*  50 */     this.depthBufferId = -1;
/*     */   }
/*     */   public int viewHeight; public final boolean useDepth; public int frameBufferId; protected int colorTextureId; protected int depthBufferId; private final float[] clearChannels; public int filterMode;
/*     */   public void resize(int $$0, int $$1, boolean $$2) {
/*  54 */     if (!RenderSystem.isOnRenderThread()) {
/*  55 */       RenderSystem.recordRenderCall(() -> _resize($$0, $$1, $$2));
/*     */     }
/*     */     else {
/*     */       
/*  59 */       _resize($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _resize(int $$0, int $$1, boolean $$2) {
/*  64 */     RenderSystem.assertOnRenderThreadOrInit();
/*  65 */     GlStateManager._enableDepthTest();
/*     */     
/*  67 */     if (this.frameBufferId >= 0) {
/*  68 */       destroyBuffers();
/*     */     }
/*  70 */     createBuffers($$0, $$1, $$2);
/*     */ 
/*     */     
/*  73 */     GlStateManager._glBindFramebuffer(36160, 0);
/*     */   }
/*     */   
/*     */   public void destroyBuffers() {
/*  77 */     RenderSystem.assertOnRenderThreadOrInit();
/*  78 */     unbindRead();
/*  79 */     unbindWrite();
/*     */     
/*  81 */     if (this.depthBufferId > -1) {
/*  82 */       TextureUtil.releaseTextureId(this.depthBufferId);
/*  83 */       this.depthBufferId = -1;
/*     */     } 
/*  85 */     if (this.colorTextureId > -1) {
/*  86 */       TextureUtil.releaseTextureId(this.colorTextureId);
/*  87 */       this.colorTextureId = -1;
/*     */     } 
/*  89 */     if (this.frameBufferId > -1) {
/*  90 */       GlStateManager._glBindFramebuffer(36160, 0);
/*  91 */       GlStateManager._glDeleteFramebuffers(this.frameBufferId);
/*  92 */       this.frameBufferId = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void copyDepthFrom(RenderTarget $$0) {
/*  97 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/*  99 */     GlStateManager._glBindFramebuffer(36008, $$0.frameBufferId);
/* 100 */     GlStateManager._glBindFramebuffer(36009, this.frameBufferId);
/* 101 */     GlStateManager._glBlitFrameBuffer(0, 0, $$0.width, $$0.height, 0, 0, this.width, this.height, 256, 9728);
/*     */     
/* 103 */     GlStateManager._glBindFramebuffer(36160, 0);
/*     */   }
/*     */   
/*     */   public void createBuffers(int $$0, int $$1, boolean $$2) {
/* 107 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 109 */     int $$3 = RenderSystem.maxSupportedTextureSize();
/* 110 */     if ($$0 <= 0 || $$0 > $$3 || $$1 <= 0 || $$1 > $$3) {
/* 111 */       throw new IllegalArgumentException("Window " + $$0 + "x" + $$1 + " size out of bounds (max. size: " + $$3 + ")");
/*     */     }
/*     */     
/* 114 */     this.viewWidth = $$0;
/* 115 */     this.viewHeight = $$1;
/*     */     
/* 117 */     this.width = $$0;
/* 118 */     this.height = $$1;
/*     */     
/* 120 */     this.frameBufferId = GlStateManager.glGenFramebuffers();
/* 121 */     this.colorTextureId = TextureUtil.generateTextureId();
/* 122 */     if (this.useDepth) {
/* 123 */       this.depthBufferId = TextureUtil.generateTextureId();
/* 124 */       GlStateManager._bindTexture(this.depthBufferId);
/* 125 */       GlStateManager._texParameter(3553, 10241, 9728);
/* 126 */       GlStateManager._texParameter(3553, 10240, 9728);
/* 127 */       GlStateManager._texParameter(3553, 34892, 0);
/* 128 */       GlStateManager._texParameter(3553, 10242, 33071);
/* 129 */       GlStateManager._texParameter(3553, 10243, 33071);
/* 130 */       GlStateManager._texImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, null);
/*     */     } 
/*     */     
/* 133 */     setFilterMode(9728);
/* 134 */     GlStateManager._bindTexture(this.colorTextureId);
/* 135 */     GlStateManager._texParameter(3553, 10242, 33071);
/* 136 */     GlStateManager._texParameter(3553, 10243, 33071);
/* 137 */     GlStateManager._texImage2D(3553, 0, 32856, this.width, this.height, 0, 6408, 5121, null);
/*     */     
/* 139 */     GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
/* 140 */     GlStateManager._glFramebufferTexture2D(36160, 36064, 3553, this.colorTextureId, 0);
/*     */     
/* 142 */     if (this.useDepth)
/*     */     {
/* 144 */       GlStateManager._glFramebufferTexture2D(36160, 36096, 3553, this.depthBufferId, 0);
/*     */     }
/*     */     
/* 147 */     checkStatus();
/*     */     
/* 149 */     clear($$2);
/* 150 */     unbindRead();
/*     */   }
/*     */   
/*     */   public void setFilterMode(int $$0) {
/* 154 */     RenderSystem.assertOnRenderThreadOrInit();
/* 155 */     this.filterMode = $$0;
/* 156 */     GlStateManager._bindTexture(this.colorTextureId);
/* 157 */     GlStateManager._texParameter(3553, 10241, $$0);
/* 158 */     GlStateManager._texParameter(3553, 10240, $$0);
/* 159 */     GlStateManager._bindTexture(0);
/*     */   }
/*     */   
/*     */   public void checkStatus() {
/* 163 */     RenderSystem.assertOnRenderThreadOrInit();
/* 164 */     int $$0 = GlStateManager.glCheckFramebufferStatus(36160);
/* 165 */     if ($$0 == 36053)
/*     */       return; 
/* 167 */     if ($$0 == 36054)
/* 168 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT"); 
/* 169 */     if ($$0 == 36055)
/* 170 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT"); 
/* 171 */     if ($$0 == 36059)
/* 172 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER"); 
/* 173 */     if ($$0 == 36060)
/* 174 */       throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER"); 
/* 175 */     if ($$0 == 36061)
/* 176 */       throw new RuntimeException("GL_FRAMEBUFFER_UNSUPPORTED"); 
/* 177 */     if ($$0 == 1285) {
/* 178 */       throw new RuntimeException("GL_OUT_OF_MEMORY");
/*     */     }
/* 180 */     throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindRead() {
/* 185 */     RenderSystem.assertOnRenderThread();
/* 186 */     GlStateManager._bindTexture(this.colorTextureId);
/*     */   }
/*     */   
/*     */   public void unbindRead() {
/* 190 */     RenderSystem.assertOnRenderThreadOrInit();
/* 191 */     GlStateManager._bindTexture(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindWrite(boolean $$0) {
/* 196 */     if (!RenderSystem.isOnRenderThread()) {
/* 197 */       RenderSystem.recordRenderCall(() -> _bindWrite($$0));
/*     */     }
/*     */     else {
/*     */       
/* 201 */       _bindWrite($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _bindWrite(boolean $$0) {
/* 206 */     RenderSystem.assertOnRenderThreadOrInit();
/* 207 */     GlStateManager._glBindFramebuffer(36160, this.frameBufferId);
/* 208 */     if ($$0) {
/* 209 */       GlStateManager._viewport(0, 0, this.viewWidth, this.viewHeight);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindWrite() {
/* 215 */     if (!RenderSystem.isOnRenderThread()) {
/* 216 */       RenderSystem.recordRenderCall(() -> GlStateManager._glBindFramebuffer(36160, 0));
/*     */     }
/*     */     else {
/*     */       
/* 220 */       GlStateManager._glBindFramebuffer(36160, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setClearColor(float $$0, float $$1, float $$2, float $$3) {
/* 225 */     this.clearChannels[0] = $$0;
/* 226 */     this.clearChannels[1] = $$1;
/* 227 */     this.clearChannels[2] = $$2;
/* 228 */     this.clearChannels[3] = $$3;
/*     */   }
/*     */   
/*     */   public void blitToScreen(int $$0, int $$1) {
/* 232 */     blitToScreen($$0, $$1, true);
/*     */   }
/*     */   
/*     */   public void blitToScreen(int $$0, int $$1, boolean $$2) {
/* 236 */     RenderSystem.assertOnGameThreadOrInit();
/* 237 */     if (!RenderSystem.isInInitPhase()) {
/* 238 */       RenderSystem.recordRenderCall(() -> _blitToScreen($$0, $$1, $$2));
/*     */     }
/*     */     else {
/*     */       
/* 242 */       _blitToScreen($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _blitToScreen(int $$0, int $$1, boolean $$2) {
/* 247 */     RenderSystem.assertOnRenderThread();
/* 248 */     GlStateManager._colorMask(true, true, true, false);
/* 249 */     GlStateManager._disableDepthTest();
/* 250 */     GlStateManager._depthMask(false);
/*     */     
/* 252 */     GlStateManager._viewport(0, 0, $$0, $$1);
/*     */     
/* 254 */     if ($$2) {
/* 255 */       GlStateManager._disableBlend();
/*     */     }
/*     */     
/* 258 */     Minecraft $$3 = Minecraft.getInstance();
/* 259 */     ShaderInstance $$4 = $$3.gameRenderer.blitShader;
/* 260 */     $$4.setSampler("DiffuseSampler", Integer.valueOf(this.colorTextureId));
/*     */     
/* 262 */     Matrix4f $$5 = (new Matrix4f()).setOrtho(0.0F, $$0, $$1, 0.0F, 1000.0F, 3000.0F);
/* 263 */     RenderSystem.setProjectionMatrix($$5, VertexSorting.ORTHOGRAPHIC_Z);
/*     */     
/* 265 */     if ($$4.MODEL_VIEW_MATRIX != null) {
/* 266 */       $$4.MODEL_VIEW_MATRIX.set((new Matrix4f()).translation(0.0F, 0.0F, -2000.0F));
/*     */     }
/* 268 */     if ($$4.PROJECTION_MATRIX != null) {
/* 269 */       $$4.PROJECTION_MATRIX.set($$5);
/*     */     }
/*     */     
/* 272 */     $$4.apply();
/*     */     
/* 274 */     float $$6 = $$0;
/* 275 */     float $$7 = $$1;
/* 276 */     float $$8 = this.viewWidth / this.width;
/* 277 */     float $$9 = this.viewHeight / this.height;
/*     */     
/* 279 */     Tesselator $$10 = RenderSystem.renderThreadTesselator();
/* 280 */     BufferBuilder $$11 = $$10.getBuilder();
/* 281 */     $$11.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
/* 282 */     $$11.vertex(0.0D, $$7, 0.0D).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
/* 283 */     $$11.vertex($$6, $$7, 0.0D).uv($$8, 0.0F).color(255, 255, 255, 255).endVertex();
/* 284 */     $$11.vertex($$6, 0.0D, 0.0D).uv($$8, $$9).color(255, 255, 255, 255).endVertex();
/* 285 */     $$11.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, $$9).color(255, 255, 255, 255).endVertex();
/*     */     
/* 287 */     BufferUploader.draw($$11.end());
/*     */     
/* 289 */     $$4.clear();
/*     */     
/* 291 */     GlStateManager._depthMask(true);
/* 292 */     GlStateManager._colorMask(true, true, true, true);
/*     */   }
/*     */   
/*     */   public void clear(boolean $$0) {
/* 296 */     RenderSystem.assertOnRenderThreadOrInit();
/* 297 */     bindWrite(true);
/* 298 */     GlStateManager._clearColor(this.clearChannels[0], this.clearChannels[1], this.clearChannels[2], this.clearChannels[3]);
/* 299 */     int $$1 = 16384;
/* 300 */     if (this.useDepth) {
/* 301 */       GlStateManager._clearDepth(1.0D);
/* 302 */       $$1 |= 0x100;
/*     */     } 
/* 304 */     GlStateManager._clear($$1, $$0);
/* 305 */     unbindWrite();
/*     */   }
/*     */   
/*     */   public int getColorTextureId() {
/* 309 */     return this.colorTextureId;
/*     */   }
/*     */   
/*     */   public int getDepthTextureId() {
/* 313 */     return this.depthBufferId;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\pipeline\RenderTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */