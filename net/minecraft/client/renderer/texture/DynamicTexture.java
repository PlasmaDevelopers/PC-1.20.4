/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import com.mojang.blaze3d.platform.TextureUtil;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class DynamicTexture
/*    */   extends AbstractTexture implements Dumpable {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   @Nullable
/*    */   private NativeImage pixels;
/*    */   
/*    */   public DynamicTexture(NativeImage $$0) {
/* 21 */     this.pixels = $$0;
/* 22 */     if (!RenderSystem.isOnRenderThread()) {
/* 23 */       RenderSystem.recordRenderCall(() -> {
/*    */             TextureUtil.prepareImage(getId(), this.pixels.getWidth(), this.pixels.getHeight());
/*    */             upload();
/*    */           });
/*    */     } else {
/* 28 */       TextureUtil.prepareImage(getId(), this.pixels.getWidth(), this.pixels.getHeight());
/* 29 */       upload();
/*    */     } 
/*    */   }
/*    */   
/*    */   public DynamicTexture(int $$0, int $$1, boolean $$2) {
/* 34 */     RenderSystem.assertOnGameThreadOrInit();
/* 35 */     this.pixels = new NativeImage($$0, $$1, $$2);
/* 36 */     TextureUtil.prepareImage(getId(), this.pixels.getWidth(), this.pixels.getHeight());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(ResourceManager $$0) {}
/*    */ 
/*    */   
/*    */   public void upload() {
/* 45 */     if (this.pixels != null) {
/* 46 */       bind();
/* 47 */       this.pixels.upload(0, 0, 0, false);
/*    */     } else {
/* 49 */       LOGGER.warn("Trying to upload disposed texture {}", Integer.valueOf(getId()));
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public NativeImage getPixels() {
/* 55 */     return this.pixels;
/*    */   }
/*    */   
/*    */   public void setPixels(NativeImage $$0) {
/* 59 */     if (this.pixels != null) {
/* 60 */       this.pixels.close();
/*    */     }
/* 62 */     this.pixels = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 67 */     if (this.pixels != null) {
/* 68 */       this.pixels.close();
/* 69 */       releaseId();
/* 70 */       this.pixels = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void dumpContents(ResourceLocation $$0, Path $$1) throws IOException {
/* 76 */     if (this.pixels != null) {
/* 77 */       String $$2 = $$0.toDebugFileName() + ".png";
/* 78 */       Path $$3 = $$1.resolve($$2);
/* 79 */       this.pixels.writeToFile($$3);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\DynamicTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */