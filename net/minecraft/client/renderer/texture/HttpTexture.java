/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class HttpTexture
/*     */   extends SimpleTexture {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int SKIN_WIDTH = 64;
/*     */   
/*     */   private static final int SKIN_HEIGHT = 64;
/*     */   
/*     */   private static final int LEGACY_SKIN_HEIGHT = 32;
/*     */   
/*     */   @Nullable
/*     */   private final File file;
/*     */   private final String urlString;
/*     */   private final boolean processLegacySkin;
/*     */   @Nullable
/*     */   private final Runnable onDownloaded;
/*     */   @Nullable
/*     */   private CompletableFuture<?> future;
/*     */   private boolean uploaded;
/*     */   
/*     */   public HttpTexture(@Nullable File $$0, String $$1, ResourceLocation $$2, boolean $$3, @Nullable Runnable $$4) {
/*  43 */     super($$2);
/*  44 */     this.file = $$0;
/*  45 */     this.urlString = $$1;
/*  46 */     this.processLegacySkin = $$3;
/*  47 */     this.onDownloaded = $$4;
/*     */   }
/*     */   
/*     */   private void loadCallback(NativeImage $$0) {
/*  51 */     if (this.onDownloaded != null) {
/*  52 */       this.onDownloaded.run();
/*     */     }
/*  54 */     Minecraft.getInstance().execute(() -> {
/*     */           this.uploaded = true;
/*     */           if (!RenderSystem.isOnRenderThread()) {
/*     */             RenderSystem.recordRenderCall(());
/*     */           } else {
/*     */             upload($$0);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void upload(NativeImage $$0) {
/*  68 */     TextureUtil.prepareImage(getId(), $$0.getWidth(), $$0.getHeight());
/*  69 */     $$0.upload(0, 0, 0, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(ResourceManager $$0) throws IOException {
/*     */     NativeImage $$3;
/*  75 */     Minecraft.getInstance().execute(() -> {
/*     */           if (!this.uploaded) {
/*     */             try {
/*     */               super.load($$0);
/*  79 */             } catch (IOException $$1) {
/*     */               LOGGER.warn("Failed to load texture: {}", this.location, $$1);
/*     */             } 
/*     */             
/*     */             this.uploaded = true;
/*     */           } 
/*     */         });
/*  86 */     if (this.future != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  91 */     if (this.file != null && this.file.isFile()) {
/*  92 */       LOGGER.debug("Loading http texture from local cache ({})", this.file);
/*  93 */       FileInputStream $$1 = new FileInputStream(this.file);
/*  94 */       NativeImage $$2 = load($$1);
/*     */     } else {
/*  96 */       $$3 = null;
/*     */     } 
/*  98 */     if ($$3 != null) {
/*  99 */       loadCallback($$3);
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     this.future = CompletableFuture.runAsync(() -> {
/*     */           HttpURLConnection $$0 = null;
/*     */           
/*     */           LOGGER.debug("Downloading http texture from {} to {}", this.urlString, this.file);
/*     */           
/*     */           try {
/*     */             InputStream $$2;
/*     */             
/*     */             $$0 = (HttpURLConnection)(new URL(this.urlString)).openConnection(Minecraft.getInstance().getProxy());
/*     */             
/*     */             $$0.setDoInput(true);
/*     */             
/*     */             $$0.setDoOutput(false);
/*     */             
/*     */             $$0.connect();
/*     */             
/*     */             if ($$0.getResponseCode() / 100 != 2) {
/*     */               return;
/*     */             }
/*     */             
/*     */             if (this.file != null) {
/*     */               FileUtils.copyInputStreamToFile($$0.getInputStream(), this.file);
/*     */               InputStream $$1 = new FileInputStream(this.file);
/*     */             } else {
/*     */               $$2 = $$0.getInputStream();
/*     */             } 
/*     */             Minecraft.getInstance().execute(());
/* 130 */           } catch (Exception $$3) {
/*     */             LOGGER.error("Couldn't download http texture", $$3);
/*     */           } finally {
/*     */             if ($$0 != null) {
/*     */               $$0.disconnect();
/*     */             }
/*     */           } 
/* 137 */         }Util.backgroundExecutor());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private NativeImage load(InputStream $$0) {
/* 142 */     NativeImage $$1 = null;
/*     */     try {
/* 144 */       $$1 = NativeImage.read($$0);
/* 145 */       if (this.processLegacySkin) {
/* 146 */         $$1 = processLegacySkin($$1);
/*     */       }
/* 148 */     } catch (Exception $$2) {
/* 149 */       LOGGER.warn("Error while loading the skin texture", $$2);
/*     */     } 
/* 151 */     return $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private NativeImage processLegacySkin(NativeImage $$0) {
/* 156 */     int $$1 = $$0.getHeight();
/* 157 */     int $$2 = $$0.getWidth();
/* 158 */     if ($$2 != 64 || ($$1 != 32 && $$1 != 64)) {
/* 159 */       $$0.close();
/* 160 */       LOGGER.warn("Discarding incorrectly sized ({}x{}) skin texture from {}", new Object[] { Integer.valueOf($$2), Integer.valueOf($$1), this.urlString });
/* 161 */       return null;
/*     */     } 
/*     */     
/* 164 */     boolean $$3 = ($$1 == 32);
/* 165 */     if ($$3) {
/* 166 */       NativeImage $$4 = new NativeImage(64, 64, true);
/* 167 */       $$4.copyFrom($$0);
/* 168 */       $$0.close();
/* 169 */       $$0 = $$4;
/*     */       
/* 171 */       $$0.fillRect(0, 32, 64, 32, 0);
/*     */ 
/*     */       
/* 174 */       $$0.copyRect(4, 16, 16, 32, 4, 4, true, false);
/* 175 */       $$0.copyRect(8, 16, 16, 32, 4, 4, true, false);
/* 176 */       $$0.copyRect(0, 20, 24, 32, 4, 12, true, false);
/* 177 */       $$0.copyRect(4, 20, 16, 32, 4, 12, true, false);
/* 178 */       $$0.copyRect(8, 20, 8, 32, 4, 12, true, false);
/* 179 */       $$0.copyRect(12, 20, 16, 32, 4, 12, true, false);
/*     */ 
/*     */       
/* 182 */       $$0.copyRect(44, 16, -8, 32, 4, 4, true, false);
/* 183 */       $$0.copyRect(48, 16, -8, 32, 4, 4, true, false);
/* 184 */       $$0.copyRect(40, 20, 0, 32, 4, 12, true, false);
/* 185 */       $$0.copyRect(44, 20, -8, 32, 4, 12, true, false);
/* 186 */       $$0.copyRect(48, 20, -16, 32, 4, 12, true, false);
/* 187 */       $$0.copyRect(52, 20, -8, 32, 4, 12, true, false);
/*     */     } 
/*     */     
/* 190 */     setNoAlpha($$0, 0, 0, 32, 16);
/*     */     
/* 192 */     if ($$3) {
/* 193 */       doNotchTransparencyHack($$0, 32, 0, 64, 32);
/*     */     }
/* 195 */     setNoAlpha($$0, 0, 16, 64, 32);
/* 196 */     setNoAlpha($$0, 16, 48, 48, 64);
/*     */     
/* 198 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void doNotchTransparencyHack(NativeImage $$0, int $$1, int $$2, int $$3, int $$4) {
/* 202 */     for (int $$5 = $$1; $$5 < $$3; $$5++) {
/* 203 */       for (int $$6 = $$2; $$6 < $$4; $$6++) {
/* 204 */         int $$7 = $$0.getPixelRGBA($$5, $$6);
/* 205 */         if (($$7 >> 24 & 0xFF) < 128) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     for (int $$8 = $$1; $$8 < $$3; $$8++) {
/* 212 */       for (int $$9 = $$2; $$9 < $$4; $$9++) {
/* 213 */         $$0.setPixelRGBA($$8, $$9, $$0.getPixelRGBA($$8, $$9) & 0xFFFFFF);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setNoAlpha(NativeImage $$0, int $$1, int $$2, int $$3, int $$4) {
/* 219 */     for (int $$5 = $$1; $$5 < $$3; $$5++) {
/* 220 */       for (int $$6 = $$2; $$6 < $$4; $$6++)
/* 221 */         $$0.setPixelRGBA($$5, $$6, $$0.getPixelRGBA($$5, $$6) | 0xFF000000); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\HttpTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */