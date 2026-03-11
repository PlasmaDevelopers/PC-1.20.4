/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import com.mojang.blaze3d.DontObfuscate;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.Path;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DontObfuscate
/*     */ public class TextureUtil
/*     */ {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final int MIN_MIPMAP_LEVEL = 0;
/*     */   
/*     */   private static final int DEFAULT_IMAGE_BUFFER_SIZE = 8192;
/*     */ 
/*     */   
/*     */   public static int generateTextureId() {
/*  35 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/*  37 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  38 */       int[] $$0 = new int[ThreadLocalRandom.current().nextInt(15) + 1];
/*  39 */       GlStateManager._genTextures($$0);
/*  40 */       int $$1 = GlStateManager._genTexture();
/*  41 */       GlStateManager._deleteTextures($$0);
/*  42 */       return $$1;
/*     */     } 
/*     */     
/*  45 */     return GlStateManager._genTexture();
/*     */   }
/*     */   
/*     */   public static void releaseTextureId(int $$0) {
/*  49 */     RenderSystem.assertOnRenderThreadOrInit();
/*  50 */     GlStateManager._deleteTexture($$0);
/*     */   }
/*     */   
/*     */   public static void prepareImage(int $$0, int $$1, int $$2) {
/*  54 */     prepareImage(NativeImage.InternalGlFormat.RGBA, $$0, 0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void prepareImage(NativeImage.InternalGlFormat $$0, int $$1, int $$2, int $$3) {
/*  58 */     prepareImage($$0, $$1, 0, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void prepareImage(int $$0, int $$1, int $$2, int $$3) {
/*  62 */     prepareImage(NativeImage.InternalGlFormat.RGBA, $$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void prepareImage(NativeImage.InternalGlFormat $$0, int $$1, int $$2, int $$3, int $$4) {
/*  66 */     RenderSystem.assertOnRenderThreadOrInit();
/*  67 */     bind($$1);
/*     */     
/*  69 */     if ($$2 >= 0) {
/*  70 */       GlStateManager._texParameter(3553, 33085, $$2);
/*  71 */       GlStateManager._texParameter(3553, 33082, 0);
/*  72 */       GlStateManager._texParameter(3553, 33083, $$2);
/*  73 */       GlStateManager._texParameter(3553, 34049, 0.0F);
/*     */     } 
/*     */     
/*  76 */     for (int $$5 = 0; $$5 <= $$2; $$5++) {
/*  77 */       GlStateManager._texImage2D(3553, $$5, $$0.glFormat(), $$3 >> $$5, $$4 >> $$5, 0, 6408, 5121, null);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void bind(int $$0) {
/*  82 */     RenderSystem.assertOnRenderThreadOrInit();
/*  83 */     GlStateManager._bindTexture($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuffer readResource(InputStream $$0) throws IOException {
/*  88 */     ReadableByteChannel $$1 = Channels.newChannel($$0);
/*  89 */     if ($$1 instanceof SeekableByteChannel) { SeekableByteChannel $$2 = (SeekableByteChannel)$$1;
/*  90 */       return readResource($$1, (int)$$2.size() + 1); }
/*     */     
/*  92 */     return readResource($$1, 8192);
/*     */   }
/*     */ 
/*     */   
/*     */   private static ByteBuffer readResource(ReadableByteChannel $$0, int $$1) throws IOException {
/*  97 */     ByteBuffer $$2 = MemoryUtil.memAlloc($$1);
/*     */     try {
/*  99 */       while ($$0.read($$2) != -1) {
/* 100 */         if (!$$2.hasRemaining()) {
/* 101 */           $$2 = MemoryUtil.memRealloc($$2, $$2.capacity() * 2);
/*     */         }
/*     */       } 
/* 104 */       return $$2;
/* 105 */     } catch (IOException $$3) {
/* 106 */       MemoryUtil.memFree($$2);
/* 107 */       throw $$3;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeAsPNG(Path $$0, String $$1, int $$2, int $$3, int $$4, int $$5) {
/* 112 */     writeAsPNG($$0, $$1, $$2, $$3, $$4, $$5, null);
/*     */   }
/*     */   
/*     */   public static void writeAsPNG(Path $$0, String $$1, int $$2, int $$3, int $$4, int $$5, @Nullable IntUnaryOperator $$6) {
/* 116 */     RenderSystem.assertOnRenderThread();
/* 117 */     bind($$2);
/*     */     
/* 119 */     for (int $$7 = 0; $$7 <= $$3; $$7++) {
/* 120 */       int $$8 = $$4 >> $$7;
/* 121 */       int $$9 = $$5 >> $$7;
/*     */       
/* 123 */       try { NativeImage $$10 = new NativeImage($$8, $$9, false); 
/* 124 */         try { $$10.downloadTexture($$7, false);
/*     */           
/* 126 */           if ($$6 != null) {
/* 127 */             $$10.applyToAllPixels($$6);
/*     */           }
/*     */           
/* 130 */           Path $$11 = $$0.resolve($$1 + "_" + $$1 + ".png");
/* 131 */           $$10.writeToFile($$11);
/* 132 */           LOGGER.debug("Exported png to: {}", $$11.toAbsolutePath());
/* 133 */           $$10.close(); } catch (Throwable throwable) { try { $$10.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$12)
/* 134 */       { LOGGER.debug("Unable to write: ", $$12); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Path getDebugTexturePath(Path $$0) {
/* 141 */     return $$0.resolve("screenshots").resolve("debug");
/*     */   }
/*     */   
/*     */   public static Path getDebugTexturePath() {
/* 145 */     return getDebugTexturePath(Path.of(".", new String[0]));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\TextureUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */