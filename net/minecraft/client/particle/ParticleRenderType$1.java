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
/*    */ class null
/*    */   implements ParticleRenderType
/*    */ {
/*    */   public void begin(BufferBuilder $$0, TextureManager $$1) {
/* 16 */     RenderSystem.enableBlend();
/* 17 */     RenderSystem.defaultBlendFunc();
/* 18 */     RenderSystem.depthMask(true);
/* 19 */     RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
/* 20 */     $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*    */   }
/*    */ 
/*    */   
/*    */   public void end(Tesselator $$0) {
/* 25 */     $$0.end();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 30 */     return "TERRAIN_SHEET";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleRenderType$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */