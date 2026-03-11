/*     */ package net.minecraft.client;
/*     */ 
/*     */ import com.mojang.blaze3d.pipeline.RenderTarget;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Screenshot {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final String SCREENSHOT_DIR = "screenshots"; private int rowHeight;
/*     */   private final DataOutputStream outputStream;
/*     */   
/*     */   public static void grab(File $$0, RenderTarget $$1, Consumer<Component> $$2) {
/*  27 */     grab($$0, null, $$1, $$2);
/*     */   }
/*     */   private final byte[] bytes; private final int width; private final int height; private File file;
/*     */   public static void grab(File $$0, @Nullable String $$1, RenderTarget $$2, Consumer<Component> $$3) {
/*  31 */     if (!RenderSystem.isOnRenderThread()) {
/*  32 */       RenderSystem.recordRenderCall(() -> _grab($$0, $$1, $$2, $$3));
/*     */     }
/*     */     else {
/*     */       
/*  36 */       _grab($$0, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   private static void _grab(File $$0, @Nullable String $$1, RenderTarget $$2, Consumer<Component> $$3) {
/*     */     File $$7;
/*  41 */     NativeImage $$4 = takeScreenshot($$2);
/*  42 */     File $$5 = new File($$0, "screenshots");
/*  43 */     $$5.mkdir();
/*     */ 
/*     */     
/*  46 */     if ($$1 == null) {
/*  47 */       File $$6 = getFile($$5);
/*     */     } else {
/*  49 */       $$7 = new File($$5, $$1);
/*     */     } 
/*     */     
/*  52 */     Util.ioPool().execute(() -> {
/*     */           try {
/*     */             $$0.writeToFile($$1);
/*     */             
/*     */             MutableComponent mutableComponent = Component.literal($$1.getName()).withStyle(ChatFormatting.UNDERLINE).withStyle(());
/*     */             $$2.accept(Component.translatable("screenshot.success", new Object[] { mutableComponent }));
/*  58 */           } catch (Exception $$4) {
/*     */             LOGGER.warn("Couldn't save screenshot", $$4);
/*     */             $$2.accept(Component.translatable("screenshot.failure", new Object[] { $$4.getMessage() }));
/*     */           } finally {
/*     */             $$0.close();
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public static NativeImage takeScreenshot(RenderTarget $$0) {
/*  68 */     int $$1 = $$0.width;
/*  69 */     int $$2 = $$0.height;
/*     */     
/*  71 */     NativeImage $$3 = new NativeImage($$1, $$2, false);
/*     */     
/*  73 */     RenderSystem.bindTexture($$0.getColorTextureId());
/*  74 */     $$3.downloadTexture(0, true);
/*     */ 
/*     */     
/*  77 */     $$3.flipY();
/*     */     
/*  79 */     return $$3;
/*     */   }
/*     */   
/*     */   private static File getFile(File $$0) {
/*  83 */     String $$1 = Util.getFilenameFormattedDateTime();
/*     */     
/*  85 */     for (int $$2 = 1;; $$2++) {
/*  86 */       File $$3 = new File($$0, $$1 + $$1 + ".png");
/*  87 */       if (!$$3.exists()) {
/*  88 */         return $$3;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Screenshot(File $$0, int $$1, int $$2, int $$3) throws IOException {
/* 101 */     this.width = $$1;
/* 102 */     this.height = $$2;
/* 103 */     this.rowHeight = $$3;
/*     */     
/* 105 */     File $$4 = new File($$0, "screenshots");
/* 106 */     $$4.mkdir();
/*     */     
/* 108 */     String $$5 = "huge_" + Util.getFilenameFormattedDateTime();
/* 109 */     int $$6 = 1; while (true) {
/* 110 */       if ((this.file = new File($$4, $$5 + $$5 + ".tga")).exists()) {
/* 111 */         $$6++; continue;
/*     */       }  break;
/*     */     } 
/* 114 */     byte[] $$7 = new byte[18];
/* 115 */     $$7[2] = 2;
/* 116 */     $$7[12] = (byte)($$1 % 256);
/* 117 */     $$7[13] = (byte)($$1 / 256);
/* 118 */     $$7[14] = (byte)($$2 % 256);
/* 119 */     $$7[15] = (byte)($$2 / 256);
/* 120 */     $$7[16] = 24;
/*     */     
/* 122 */     this.bytes = new byte[$$1 * $$3 * 3];
/* 123 */     this.outputStream = new DataOutputStream(new FileOutputStream(this.file));
/* 124 */     this.outputStream.write($$7);
/*     */   }
/*     */   
/*     */   public void addRegion(ByteBuffer $$0, int $$1, int $$2, int $$3, int $$4) {
/* 128 */     int $$5 = $$3;
/* 129 */     int $$6 = $$4;
/* 130 */     if ($$5 > this.width - $$1) {
/* 131 */       $$5 = this.width - $$1;
/*     */     }
/* 133 */     if ($$6 > this.height - $$2) {
/* 134 */       $$6 = this.height - $$2;
/*     */     }
/* 136 */     this.rowHeight = $$6;
/*     */     
/* 138 */     for (int $$7 = 0; $$7 < $$6; $$7++) {
/* 139 */       $$0.position(($$4 - $$6) * $$3 * 3 + $$7 * $$3 * 3);
/* 140 */       int $$8 = ($$1 + $$7 * this.width) * 3;
/* 141 */       $$0.get(this.bytes, $$8, $$5 * 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveRow() throws IOException {
/* 146 */     this.outputStream.write(this.bytes, 0, this.width * 3 * this.rowHeight);
/*     */   }
/*     */   
/*     */   public File close() throws IOException {
/* 150 */     this.outputStream.close();
/* 151 */     return this.file;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\Screenshot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */