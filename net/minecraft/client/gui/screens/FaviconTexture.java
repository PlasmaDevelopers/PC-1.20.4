/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import com.google.common.hash.Hashing;
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class FaviconTexture
/*    */   implements AutoCloseable {
/* 14 */   private static final ResourceLocation MISSING_LOCATION = new ResourceLocation("textures/misc/unknown_server.png");
/*    */   
/*    */   private static final int WIDTH = 64;
/*    */   
/*    */   private static final int HEIGHT = 64;
/*    */   private final TextureManager textureManager;
/*    */   private final ResourceLocation textureLocation;
/*    */   @Nullable
/*    */   private DynamicTexture texture;
/*    */   private boolean closed;
/*    */   
/*    */   private FaviconTexture(TextureManager $$0, ResourceLocation $$1) {
/* 26 */     this.textureManager = $$0;
/* 27 */     this.textureLocation = $$1;
/*    */   }
/*    */   
/*    */   public static FaviconTexture forWorld(TextureManager $$0, String $$1) {
/* 31 */     return new FaviconTexture($$0, new ResourceLocation("minecraft", "worlds/" + Util.sanitizeName($$1, ResourceLocation::validPathChar) + "/" + Hashing.sha1().hashUnencodedChars($$1) + "/icon"));
/*    */   }
/*    */   
/*    */   public static FaviconTexture forServer(TextureManager $$0, String $$1) {
/* 35 */     return new FaviconTexture($$0, new ResourceLocation("minecraft", "servers/" + Hashing.sha1().hashUnencodedChars($$1) + "/icon"));
/*    */   }
/*    */   
/*    */   public void upload(NativeImage $$0) {
/* 39 */     if ($$0.getWidth() != 64 || $$0.getHeight() != 64) {
/* 40 */       $$0.close();
/* 41 */       throw new IllegalArgumentException("Icon must be 64x64, but was " + $$0.getWidth() + "x" + $$0.getHeight());
/*    */     } 
/*    */     try {
/* 44 */       checkOpen();
/*    */       
/* 46 */       if (this.texture == null) {
/* 47 */         this.texture = new DynamicTexture($$0);
/*    */       } else {
/* 49 */         this.texture.setPixels($$0);
/* 50 */         this.texture.upload();
/*    */       } 
/* 52 */       this.textureManager.register(this.textureLocation, (AbstractTexture)this.texture);
/* 53 */     } catch (Throwable $$1) {
/* 54 */       $$0.close();
/* 55 */       clear();
/* 56 */       throw $$1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 61 */     checkOpen();
/* 62 */     if (this.texture != null) {
/* 63 */       this.textureManager.release(this.textureLocation);
/* 64 */       this.texture.close();
/* 65 */       this.texture = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public ResourceLocation textureLocation() {
/* 70 */     return (this.texture != null) ? this.textureLocation : MISSING_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 75 */     clear();
/* 76 */     this.closed = true;
/*    */   }
/*    */   
/*    */   private void checkOpen() {
/* 80 */     if (this.closed)
/* 81 */       throw new IllegalStateException("Icon already closed"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\FaviconTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */