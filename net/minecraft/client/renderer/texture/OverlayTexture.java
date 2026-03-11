/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OverlayTexture
/*    */   implements AutoCloseable
/*    */ {
/*    */   private static final int SIZE = 16;
/*    */   public static final int NO_WHITE_U = 0;
/*    */   public static final int RED_OVERLAY_V = 3;
/*    */   public static final int WHITE_OVERLAY_V = 10;
/* 17 */   public static final int NO_OVERLAY = pack(0, 10);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   private final DynamicTexture texture = new DynamicTexture(16, 16, false); public OverlayTexture() {
/* 23 */     NativeImage $$0 = this.texture.getPixels();
/*    */ 
/*    */ 
/*    */     
/* 27 */     for (int $$1 = 0; $$1 < 16; $$1++) {
/* 28 */       for (int $$2 = 0; $$2 < 16; $$2++) {
/* 29 */         if ($$1 < 8) {
/*    */           
/* 31 */           $$0.setPixelRGBA($$2, $$1, -1308622593);
/*    */         } else {
/*    */           
/* 34 */           int $$3 = (int)((1.0F - $$2 / 15.0F * 0.75F) * 255.0F);
/* 35 */           $$0.setPixelRGBA($$2, $$1, $$3 << 24 | 0xFFFFFF);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 40 */     RenderSystem.activeTexture(33985);
/* 41 */     this.texture.bind();
/* 42 */     $$0.upload(0, 0, 0, 0, 0, $$0.getWidth(), $$0.getHeight(), false, true, false, false);
/* 43 */     RenderSystem.activeTexture(33984);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 48 */     this.texture.close();
/*    */   }
/*    */   
/*    */   public void setupOverlayColor() {
/* 52 */     Objects.requireNonNull(this.texture); RenderSystem.setupOverlayColor(this.texture::getId, 16);
/*    */   }
/*    */   
/*    */   public static int u(float $$0) {
/* 56 */     return (int)($$0 * 15.0F);
/*    */   }
/*    */   
/*    */   public static int v(boolean $$0) {
/* 60 */     return $$0 ? 3 : 10;
/*    */   }
/*    */   
/*    */   public static int pack(int $$0, int $$1) {
/* 64 */     return $$0 | $$1 << 16;
/*    */   }
/*    */   
/*    */   public static int pack(float $$0, boolean $$1) {
/* 68 */     return pack(u($$0), v($$1));
/*    */   }
/*    */   
/*    */   public void teardownOverlayColor() {
/* 72 */     RenderSystem.teardownOverlayColor();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\OverlayTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */