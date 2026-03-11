/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.GameRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ 
/*     */ public interface ParticleRenderType {
/*  13 */   public static final ParticleRenderType TERRAIN_SHEET = new ParticleRenderType()
/*     */     {
/*     */       public void begin(BufferBuilder $$0, TextureManager $$1) {
/*  16 */         RenderSystem.enableBlend();
/*  17 */         RenderSystem.defaultBlendFunc();
/*  18 */         RenderSystem.depthMask(true);
/*  19 */         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
/*  20 */         $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*     */       }
/*     */ 
/*     */       
/*     */       public void end(Tesselator $$0) {
/*  25 */         $$0.end();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  30 */         return "TERRAIN_SHEET";
/*     */       }
/*     */     };
/*     */   
/*  34 */   public static final ParticleRenderType PARTICLE_SHEET_OPAQUE = new ParticleRenderType()
/*     */     {
/*     */       public void begin(BufferBuilder $$0, TextureManager $$1) {
/*  37 */         RenderSystem.disableBlend();
/*  38 */         RenderSystem.depthMask(true);
/*  39 */         RenderSystem.setShader(GameRenderer::getParticleShader);
/*  40 */         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
/*  41 */         $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*     */       }
/*     */ 
/*     */       
/*     */       public void end(Tesselator $$0) {
/*  46 */         $$0.end();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  51 */         return "PARTICLE_SHEET_OPAQUE";
/*     */       }
/*     */     };
/*     */   
/*  55 */   public static final ParticleRenderType PARTICLE_SHEET_TRANSLUCENT = new ParticleRenderType()
/*     */     {
/*     */       public void begin(BufferBuilder $$0, TextureManager $$1) {
/*  58 */         RenderSystem.depthMask(true);
/*  59 */         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
/*  60 */         RenderSystem.enableBlend();
/*  61 */         RenderSystem.defaultBlendFunc();
/*  62 */         $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*     */       }
/*     */ 
/*     */       
/*     */       public void end(Tesselator $$0) {
/*  67 */         $$0.end();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  72 */         return "PARTICLE_SHEET_TRANSLUCENT";
/*     */       }
/*     */     };
/*     */   
/*  76 */   public static final ParticleRenderType PARTICLE_SHEET_LIT = new ParticleRenderType()
/*     */     {
/*     */       public void begin(BufferBuilder $$0, TextureManager $$1) {
/*  79 */         RenderSystem.disableBlend();
/*  80 */         RenderSystem.depthMask(true);
/*  81 */         RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
/*  82 */         $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*     */       }
/*     */ 
/*     */       
/*     */       public void end(Tesselator $$0) {
/*  87 */         $$0.end();
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  92 */         return "PARTICLE_SHEET_LIT";
/*     */       }
/*     */     };
/*     */   
/*  96 */   public static final ParticleRenderType CUSTOM = new ParticleRenderType()
/*     */     {
/*     */       public void begin(BufferBuilder $$0, TextureManager $$1) {
/*  99 */         RenderSystem.depthMask(true);
/* 100 */         RenderSystem.disableBlend();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void end(Tesselator $$0) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public String toString() {
/* 110 */         return "CUSTOM";
/*     */       }
/*     */     };
/*     */   
/* 114 */   public static final ParticleRenderType NO_RENDER = new ParticleRenderType()
/*     */     {
/*     */       public void begin(BufferBuilder $$0, TextureManager $$1) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void end(Tesselator $$0) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public String toString() {
/* 125 */         return "NO_RENDER";
/*     */       }
/*     */     };
/*     */   
/*     */   void begin(BufferBuilder paramBufferBuilder, TextureManager paramTextureManager);
/*     */   
/*     */   void end(Tesselator paramTesselator);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleRenderType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */