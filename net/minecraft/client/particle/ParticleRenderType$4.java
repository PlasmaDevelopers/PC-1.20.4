/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*    */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*    */ import com.mojang.blaze3d.vertex.Tesselator;
/*    */ import com.mojang.blaze3d.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ParticleRenderType
/*    */ {
/*    */   public void begin(BufferBuilder $$0, TextureManager $$1) {
/* 79 */     RenderSystem.disableBlend();
/* 80 */     RenderSystem.depthMask(true);
/* 81 */     RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
/* 82 */     $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*    */   }
/*    */ 
/*    */   
/*    */   public void end(Tesselator $$0) {
/* 87 */     $$0.end();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return "PARTICLE_SHEET_LIT";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleRenderType$4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */