/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.platform.Window;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.nio.ByteBuffer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ShaderInstance;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ 
/*     */ 
/*     */ public class VertexBuffer
/*     */   implements AutoCloseable
/*     */ {
/*     */   private final Usage usage;
/*     */   private int vertexBufferId;
/*     */   private int indexBufferId;
/*     */   private int arrayObjectId;
/*     */   @Nullable
/*     */   private VertexFormat format;
/*     */   @Nullable
/*     */   private RenderSystem.AutoStorageIndexBuffer sequentialIndices;
/*     */   private VertexFormat.IndexType indexType;
/*     */   private int indexCount;
/*     */   private VertexFormat.Mode mode;
/*     */   
/*     */   public VertexBuffer(Usage $$0) {
/*  30 */     this.usage = $$0;
/*  31 */     RenderSystem.assertOnRenderThread();
/*  32 */     this.vertexBufferId = GlStateManager._glGenBuffers();
/*  33 */     this.indexBufferId = GlStateManager._glGenBuffers();
/*  34 */     this.arrayObjectId = GlStateManager._glGenVertexArrays();
/*     */   }
/*     */   
/*     */   public void upload(BufferBuilder.RenderedBuffer $$0) {
/*     */     try {
/*  39 */       if (isInvalid()) {
/*     */         return;
/*     */       }
/*     */       
/*  43 */       RenderSystem.assertOnRenderThread();
/*     */       
/*  45 */       BufferBuilder.DrawState $$1 = $$0.drawState();
/*     */       
/*  47 */       this.format = uploadVertexBuffer($$1, $$0.vertexBuffer());
/*  48 */       this.sequentialIndices = uploadIndexBuffer($$1, $$0.indexBuffer());
/*     */       
/*  50 */       this.indexCount = $$1.indexCount();
/*  51 */       this.indexType = $$1.indexType();
/*  52 */       this.mode = $$1.mode();
/*     */     } finally {
/*  54 */       $$0.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private VertexFormat uploadVertexBuffer(BufferBuilder.DrawState $$0, @Nullable ByteBuffer $$1) {
/*  59 */     boolean $$2 = false;
/*  60 */     if (!$$0.format().equals(this.format)) {
/*  61 */       if (this.format != null) {
/*  62 */         this.format.clearBufferState();
/*     */       }
/*  64 */       GlStateManager._glBindBuffer(34962, this.vertexBufferId);
/*  65 */       $$0.format().setupBufferState();
/*  66 */       $$2 = true;
/*     */     } 
/*     */     
/*  69 */     if ($$1 != null) {
/*  70 */       if (!$$2) {
/*  71 */         GlStateManager._glBindBuffer(34962, this.vertexBufferId);
/*     */       }
/*     */       
/*  74 */       RenderSystem.glBufferData(34962, $$1, this.usage.id);
/*     */     } 
/*     */     
/*  77 */     return $$0.format();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private RenderSystem.AutoStorageIndexBuffer uploadIndexBuffer(BufferBuilder.DrawState $$0, @Nullable ByteBuffer $$1) {
/*  82 */     if ($$1 == null) {
/*  83 */       RenderSystem.AutoStorageIndexBuffer $$2 = RenderSystem.getSequentialBuffer($$0.mode());
/*     */       
/*  85 */       if ($$2 != this.sequentialIndices || !$$2.hasStorage($$0.indexCount())) {
/*  86 */         $$2.bind($$0.indexCount());
/*     */       }
/*     */       
/*  89 */       return $$2;
/*     */     } 
/*  91 */     GlStateManager._glBindBuffer(34963, this.indexBufferId);
/*  92 */     RenderSystem.glBufferData(34963, $$1, this.usage.id);
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind() {
/*  99 */     BufferUploader.invalidate();
/* 100 */     GlStateManager._glBindVertexArray(this.arrayObjectId);
/*     */   }
/*     */   
/*     */   public static void unbind() {
/* 104 */     BufferUploader.invalidate();
/* 105 */     GlStateManager._glBindVertexArray(0);
/*     */   }
/*     */   
/*     */   public void draw() {
/* 109 */     RenderSystem.drawElements(this.mode.asGLMode, this.indexCount, (getIndexType()).asGLType);
/*     */   }
/*     */   
/*     */   private VertexFormat.IndexType getIndexType() {
/* 113 */     RenderSystem.AutoStorageIndexBuffer $$0 = this.sequentialIndices;
/* 114 */     return ($$0 != null) ? $$0.type() : this.indexType;
/*     */   }
/*     */   
/*     */   public void drawWithShader(Matrix4f $$0, Matrix4f $$1, ShaderInstance $$2) {
/* 118 */     if (!RenderSystem.isOnRenderThread()) {
/* 119 */       RenderSystem.recordRenderCall(() -> _drawWithShader(new Matrix4f((Matrix4fc)$$0), new Matrix4f((Matrix4fc)$$1), $$2));
/*     */     }
/*     */     else {
/*     */       
/* 123 */       _drawWithShader($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _drawWithShader(Matrix4f $$0, Matrix4f $$1, ShaderInstance $$2) {
/* 128 */     for (int $$3 = 0; $$3 < 12; $$3++) {
/* 129 */       int $$4 = RenderSystem.getShaderTexture($$3);
/* 130 */       $$2.setSampler("Sampler" + $$3, Integer.valueOf($$4));
/*     */     } 
/*     */     
/* 133 */     if ($$2.MODEL_VIEW_MATRIX != null) {
/* 134 */       $$2.MODEL_VIEW_MATRIX.set($$0);
/*     */     }
/* 136 */     if ($$2.PROJECTION_MATRIX != null) {
/* 137 */       $$2.PROJECTION_MATRIX.set($$1);
/*     */     }
/* 139 */     if ($$2.INVERSE_VIEW_ROTATION_MATRIX != null) {
/* 140 */       $$2.INVERSE_VIEW_ROTATION_MATRIX.set(RenderSystem.getInverseViewRotationMatrix());
/*     */     }
/*     */     
/* 143 */     if ($$2.COLOR_MODULATOR != null) {
/* 144 */       $$2.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
/*     */     }
/* 146 */     if ($$2.GLINT_ALPHA != null) {
/* 147 */       $$2.GLINT_ALPHA.set(RenderSystem.getShaderGlintAlpha());
/*     */     }
/* 149 */     if ($$2.FOG_START != null) {
/* 150 */       $$2.FOG_START.set(RenderSystem.getShaderFogStart());
/*     */     }
/* 152 */     if ($$2.FOG_END != null) {
/* 153 */       $$2.FOG_END.set(RenderSystem.getShaderFogEnd());
/*     */     }
/* 155 */     if ($$2.FOG_COLOR != null) {
/* 156 */       $$2.FOG_COLOR.set(RenderSystem.getShaderFogColor());
/*     */     }
/* 158 */     if ($$2.FOG_SHAPE != null) {
/* 159 */       $$2.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
/*     */     }
/*     */     
/* 162 */     if ($$2.TEXTURE_MATRIX != null) {
/* 163 */       $$2.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
/*     */     }
/* 165 */     if ($$2.GAME_TIME != null) {
/* 166 */       $$2.GAME_TIME.set(RenderSystem.getShaderGameTime());
/*     */     }
/*     */     
/* 169 */     if ($$2.SCREEN_SIZE != null) {
/* 170 */       Window $$5 = Minecraft.getInstance().getWindow();
/* 171 */       $$2.SCREEN_SIZE.set($$5.getWidth(), $$5.getHeight());
/*     */     } 
/*     */     
/* 174 */     if ($$2.LINE_WIDTH != null && (this.mode == VertexFormat.Mode.LINES || this.mode == VertexFormat.Mode.LINE_STRIP)) {
/* 175 */       $$2.LINE_WIDTH.set(RenderSystem.getShaderLineWidth());
/*     */     }
/*     */     
/* 178 */     RenderSystem.setupShaderLights($$2);
/*     */     
/* 180 */     $$2.apply();
/* 181 */     draw();
/* 182 */     $$2.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 187 */     if (this.vertexBufferId >= 0) {
/* 188 */       RenderSystem.glDeleteBuffers(this.vertexBufferId);
/* 189 */       this.vertexBufferId = -1;
/*     */     } 
/* 191 */     if (this.indexBufferId >= 0) {
/* 192 */       RenderSystem.glDeleteBuffers(this.indexBufferId);
/* 193 */       this.indexBufferId = -1;
/*     */     } 
/* 195 */     if (this.arrayObjectId >= 0) {
/* 196 */       RenderSystem.glDeleteVertexArrays(this.arrayObjectId);
/* 197 */       this.arrayObjectId = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public VertexFormat getFormat() {
/* 202 */     return this.format;
/*     */   }
/*     */   
/*     */   public boolean isInvalid() {
/* 206 */     return (this.arrayObjectId == -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Usage
/*     */   {
/* 213 */     STATIC(35044),
/*     */ 
/*     */     
/* 216 */     DYNAMIC(35048);
/*     */     
/*     */     final int id;
/*     */ 
/*     */     
/*     */     Usage(int $$0) {
/* 222 */       this.id = $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexBuffer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */