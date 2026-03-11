/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.FastColor;
/*     */ import net.minecraft.util.PngInfo;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.stb.STBIWriteCallback;
/*     */ import org.lwjgl.stb.STBImage;
/*     */ import org.lwjgl.stb.STBImageResize;
/*     */ import org.lwjgl.stb.STBImageWrite;
/*     */ import org.lwjgl.stb.STBTTFontinfo;
/*     */ import org.lwjgl.stb.STBTruetype;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NativeImage
/*     */   implements AutoCloseable
/*     */ {
/*  41 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  43 */   private static final Set<StandardOpenOption> OPEN_OPTIONS = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
/*     */   
/*     */   private final Format format;
/*     */   
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final boolean useStbFree;
/*     */   private long pixels;
/*     */   private final long size;
/*     */   
/*     */   public NativeImage(int $$0, int $$1, boolean $$2) {
/*  54 */     this(Format.RGBA, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public NativeImage(Format $$0, int $$1, int $$2, boolean $$3) {
/*  58 */     if ($$1 <= 0 || $$2 <= 0) {
/*  59 */       throw new IllegalArgumentException("Invalid texture size: " + $$1 + "x" + $$2);
/*     */     }
/*  61 */     this.format = $$0;
/*  62 */     this.width = $$1;
/*  63 */     this.height = $$2;
/*  64 */     this.size = $$1 * $$2 * $$0.components();
/*  65 */     this.useStbFree = false;
/*  66 */     if ($$3) {
/*  67 */       this.pixels = MemoryUtil.nmemCalloc(1L, this.size);
/*     */     } else {
/*  69 */       this.pixels = MemoryUtil.nmemAlloc(this.size);
/*     */     } 
/*  71 */     if (this.pixels == 0L) {
/*  72 */       throw new IllegalStateException("Unable to allocate texture of size " + $$1 + "x" + $$2 + " (" + $$0.components() + " channels)");
/*     */     }
/*     */   }
/*     */   
/*     */   private NativeImage(Format $$0, int $$1, int $$2, boolean $$3, long $$4) {
/*  77 */     if ($$1 <= 0 || $$2 <= 0) {
/*  78 */       throw new IllegalArgumentException("Invalid texture size: " + $$1 + "x" + $$2);
/*     */     }
/*  80 */     this.format = $$0;
/*  81 */     this.width = $$1;
/*  82 */     this.height = $$2;
/*  83 */     this.useStbFree = $$3;
/*  84 */     this.pixels = $$4;
/*  85 */     this.size = $$1 * $$2 * $$0.components();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return "NativeImage[" + this.format + " " + this.width + "x" + this.height + "@" + this.pixels + (this.useStbFree ? "S" : "N") + "]";
/*     */   }
/*     */   
/*     */   private boolean isOutsideBounds(int $$0, int $$1) {
/*  94 */     return ($$0 < 0 || $$0 >= this.width || $$1 < 0 || $$1 >= this.height);
/*     */   }
/*     */   
/*     */   public static NativeImage read(InputStream $$0) throws IOException {
/*  98 */     return read(Format.RGBA, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static NativeImage read(@Nullable Format $$0, InputStream $$1) throws IOException {
/* 103 */     ByteBuffer $$2 = null;
/*     */     try {
/* 105 */       $$2 = TextureUtil.readResource($$1);
/* 106 */       $$2.rewind();
/* 107 */       return read($$0, $$2);
/*     */     } finally {
/* 109 */       MemoryUtil.memFree($$2);
/* 110 */       IOUtils.closeQuietly($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static NativeImage read(ByteBuffer $$0) throws IOException {
/* 115 */     return read(Format.RGBA, $$0);
/*     */   }
/*     */   
/*     */   public static NativeImage read(byte[] $$0) throws IOException {
/* 119 */     MemoryStack $$1 = MemoryStack.stackPush(); 
/* 120 */     try { ByteBuffer $$2 = $$1.malloc($$0.length);
/* 121 */       $$2.put($$0);
/* 122 */       $$2.rewind();
/* 123 */       NativeImage nativeImage = read($$2);
/* 124 */       if ($$1 != null) $$1.close();  return nativeImage; }
/*     */     catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 129 */      } public static NativeImage read(@Nullable Format $$0, ByteBuffer $$1) throws IOException { if ($$0 != null && !$$0.supportedByStb()) {
/* 130 */       throw new UnsupportedOperationException("Don't know how to read format " + $$0);
/*     */     }
/* 132 */     if (MemoryUtil.memAddress($$1) == 0L) {
/* 133 */       throw new IllegalArgumentException("Invalid buffer");
/*     */     }
/* 135 */     PngInfo.validateHeader($$1);
/* 136 */     MemoryStack $$2 = MemoryStack.stackPush(); 
/* 137 */     try { IntBuffer $$3 = $$2.mallocInt(1);
/* 138 */       IntBuffer $$4 = $$2.mallocInt(1);
/* 139 */       IntBuffer $$5 = $$2.mallocInt(1);
/*     */       
/* 141 */       ByteBuffer $$6 = STBImage.stbi_load_from_memory($$1, $$3, $$4, $$5, ($$0 == null) ? 0 : $$0.components);
/* 142 */       if ($$6 == null) {
/* 143 */         throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
/*     */       }
/* 145 */       NativeImage nativeImage = new NativeImage(($$0 == null) ? Format.getStbFormat($$5.get(0)) : $$0, $$3.get(0), $$4.get(0), true, MemoryUtil.memAddress($$6));
/* 146 */       if ($$2 != null) $$2.close();  return nativeImage; } catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 150 */      } private static void setFilter(boolean $$0, boolean $$1) { RenderSystem.assertOnRenderThreadOrInit();
/* 151 */     if ($$0) {
/* 152 */       GlStateManager._texParameter(3553, 10241, $$1 ? 9987 : 9729);
/* 153 */       GlStateManager._texParameter(3553, 10240, 9729);
/*     */     } else {
/* 155 */       GlStateManager._texParameter(3553, 10241, $$1 ? 9986 : 9728);
/* 156 */       GlStateManager._texParameter(3553, 10240, 9728);
/*     */     }  }
/*     */ 
/*     */   
/*     */   private void checkAllocated() {
/* 161 */     if (this.pixels == 0L) {
/* 162 */       throw new IllegalStateException("Image is not allocated.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 170 */     if (this.pixels != 0L) {
/* 171 */       if (this.useStbFree) {
/* 172 */         STBImage.nstbi_image_free(this.pixels);
/*     */       } else {
/* 174 */         MemoryUtil.nmemFree(this.pixels);
/*     */       } 
/*     */     }
/* 177 */     this.pixels = 0L;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 181 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 185 */     return this.height;
/*     */   }
/*     */   
/*     */   public Format format() {
/* 189 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPixelRGBA(int $$0, int $$1) {
/* 195 */     if (this.format != Format.RGBA)
/* 196 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelRGBA only works on RGBA images; have %s", new Object[] { this.format })); 
/* 197 */     if (isOutsideBounds($$0, $$1)) {
/* 198 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf(this.width), Integer.valueOf(this.height) }));
/*     */     }
/* 200 */     checkAllocated();
/* 201 */     long $$2 = ($$0 + $$1 * this.width) * 4L;
/* 202 */     return MemoryUtil.memGetInt(this.pixels + $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixelRGBA(int $$0, int $$1, int $$2) {
/* 208 */     if (this.format != Format.RGBA)
/* 209 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "setPixelRGBA only works on RGBA images; have %s", new Object[] { this.format })); 
/* 210 */     if (isOutsideBounds($$0, $$1)) {
/* 211 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf(this.width), Integer.valueOf(this.height) }));
/*     */     }
/* 213 */     checkAllocated();
/* 214 */     long $$3 = ($$0 + $$1 * this.width) * 4L;
/* 215 */     MemoryUtil.memPutInt(this.pixels + $$3, $$2);
/*     */   }
/*     */   
/*     */   public NativeImage mappedCopy(IntUnaryOperator $$0) {
/* 219 */     if (this.format != Format.RGBA) {
/* 220 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "function application only works on RGBA images; have %s", new Object[] { this.format }));
/*     */     }
/* 222 */     checkAllocated();
/*     */     
/* 224 */     NativeImage $$1 = new NativeImage(this.width, this.height, false);
/* 225 */     int $$2 = this.width * this.height;
/* 226 */     IntBuffer $$3 = MemoryUtil.memIntBuffer(this.pixels, $$2);
/* 227 */     IntBuffer $$4 = MemoryUtil.memIntBuffer($$1.pixels, $$2);
/* 228 */     for (int $$5 = 0; $$5 < $$2; $$5++) {
/* 229 */       $$4.put($$5, $$0.applyAsInt($$3.get($$5)));
/*     */     }
/* 231 */     return $$1;
/*     */   }
/*     */   
/*     */   public void applyToAllPixels(IntUnaryOperator $$0) {
/* 235 */     if (this.format != Format.RGBA) {
/* 236 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "function application only works on RGBA images; have %s", new Object[] { this.format }));
/*     */     }
/* 238 */     checkAllocated();
/*     */     
/* 240 */     int $$1 = this.width * this.height;
/* 241 */     IntBuffer $$2 = MemoryUtil.memIntBuffer(this.pixels, $$1);
/* 242 */     for (int $$3 = 0; $$3 < $$1; $$3++) {
/* 243 */       $$2.put($$3, $$0.applyAsInt($$2.get($$3)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getPixelsRGBA() {
/* 250 */     if (this.format != Format.RGBA) {
/* 251 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "getPixelsRGBA only works on RGBA images; have %s", new Object[] { this.format }));
/*     */     }
/* 253 */     checkAllocated();
/* 254 */     int[] $$0 = new int[this.width * this.height];
/* 255 */     MemoryUtil.memIntBuffer(this.pixels, this.width * this.height).get($$0);
/* 256 */     return $$0;
/*     */   }
/*     */   
/*     */   public void setPixelLuminance(int $$0, int $$1, byte $$2) {
/* 260 */     RenderSystem.assertOnRenderThread();
/*     */     
/* 262 */     if (!this.format.hasLuminance())
/* 263 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "setPixelLuminance only works on image with luminance; have %s", new Object[] { this.format })); 
/* 264 */     if (isOutsideBounds($$0, $$1)) {
/* 265 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf(this.width), Integer.valueOf(this.height) }));
/*     */     }
/* 267 */     checkAllocated();
/* 268 */     long $$3 = ($$0 + $$1 * this.width) * this.format.components() + (this.format.luminanceOffset() / 8);
/* 269 */     MemoryUtil.memPutByte(this.pixels + $$3, $$2);
/*     */   }
/*     */   
/*     */   public byte getRedOrLuminance(int $$0, int $$1) {
/* 273 */     RenderSystem.assertOnRenderThread();
/* 274 */     if (!this.format.hasLuminanceOrRed())
/* 275 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "no red or luminance in %s", new Object[] { this.format })); 
/* 276 */     if (isOutsideBounds($$0, $$1)) {
/* 277 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf(this.width), Integer.valueOf(this.height) }));
/*     */     }
/*     */     
/* 280 */     int $$2 = ($$0 + $$1 * this.width) * this.format.components() + this.format.luminanceOrRedOffset() / 8;
/* 281 */     return MemoryUtil.memGetByte(this.pixels + $$2);
/*     */   }
/*     */   
/*     */   public byte getGreenOrLuminance(int $$0, int $$1) {
/* 285 */     RenderSystem.assertOnRenderThread();
/* 286 */     if (!this.format.hasLuminanceOrGreen())
/* 287 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "no green or luminance in %s", new Object[] { this.format })); 
/* 288 */     if (isOutsideBounds($$0, $$1)) {
/* 289 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf(this.width), Integer.valueOf(this.height) }));
/*     */     }
/*     */     
/* 292 */     int $$2 = ($$0 + $$1 * this.width) * this.format.components() + this.format.luminanceOrGreenOffset() / 8;
/* 293 */     return MemoryUtil.memGetByte(this.pixels + $$2);
/*     */   }
/*     */   
/*     */   public byte getBlueOrLuminance(int $$0, int $$1) {
/* 297 */     RenderSystem.assertOnRenderThread();
/* 298 */     if (!this.format.hasLuminanceOrBlue())
/* 299 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "no blue or luminance in %s", new Object[] { this.format })); 
/* 300 */     if (isOutsideBounds($$0, $$1)) {
/* 301 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf(this.width), Integer.valueOf(this.height) }));
/*     */     }
/*     */     
/* 304 */     int $$2 = ($$0 + $$1 * this.width) * this.format.components() + this.format.luminanceOrBlueOffset() / 8;
/* 305 */     return MemoryUtil.memGetByte(this.pixels + $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getLuminanceOrAlpha(int $$0, int $$1) {
/* 310 */     if (!this.format.hasLuminanceOrAlpha())
/* 311 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "no luminance or alpha in %s", new Object[] { this.format })); 
/* 312 */     if (isOutsideBounds($$0, $$1)) {
/* 313 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "(%s, %s) outside of image bounds (%s, %s)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf(this.width), Integer.valueOf(this.height) }));
/*     */     }
/*     */     
/* 316 */     int $$2 = ($$0 + $$1 * this.width) * this.format.components() + this.format.luminanceOrAlphaOffset() / 8;
/* 317 */     return MemoryUtil.memGetByte(this.pixels + $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void blendPixel(int $$0, int $$1, int $$2) {
/* 325 */     if (this.format != Format.RGBA) {
/* 326 */       throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
/*     */     }
/* 328 */     int $$3 = getPixelRGBA($$0, $$1);
/*     */     
/* 330 */     float $$4 = FastColor.ABGR32.alpha($$2) / 255.0F;
/* 331 */     float $$5 = FastColor.ABGR32.blue($$2) / 255.0F;
/* 332 */     float $$6 = FastColor.ABGR32.green($$2) / 255.0F;
/* 333 */     float $$7 = FastColor.ABGR32.red($$2) / 255.0F;
/*     */     
/* 335 */     float $$8 = FastColor.ABGR32.alpha($$3) / 255.0F;
/* 336 */     float $$9 = FastColor.ABGR32.blue($$3) / 255.0F;
/* 337 */     float $$10 = FastColor.ABGR32.green($$3) / 255.0F;
/* 338 */     float $$11 = FastColor.ABGR32.red($$3) / 255.0F;
/*     */     
/* 340 */     float $$12 = $$4;
/* 341 */     float $$13 = 1.0F - $$4;
/*     */     
/* 343 */     float $$14 = $$4 * $$12 + $$8 * $$13;
/* 344 */     float $$15 = $$5 * $$12 + $$9 * $$13;
/* 345 */     float $$16 = $$6 * $$12 + $$10 * $$13;
/* 346 */     float $$17 = $$7 * $$12 + $$11 * $$13;
/*     */     
/* 348 */     if ($$14 > 1.0F) {
/* 349 */       $$14 = 1.0F;
/*     */     }
/* 351 */     if ($$15 > 1.0F) {
/* 352 */       $$15 = 1.0F;
/*     */     }
/* 354 */     if ($$16 > 1.0F) {
/* 355 */       $$16 = 1.0F;
/*     */     }
/* 357 */     if ($$17 > 1.0F) {
/* 358 */       $$17 = 1.0F;
/*     */     }
/*     */     
/* 361 */     int $$18 = (int)($$14 * 255.0F);
/* 362 */     int $$19 = (int)($$15 * 255.0F);
/* 363 */     int $$20 = (int)($$16 * 255.0F);
/* 364 */     int $$21 = (int)($$17 * 255.0F);
/*     */     
/* 366 */     setPixelRGBA($$0, $$1, FastColor.ABGR32.color($$18, $$19, $$20, $$21));
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int[] makePixelArray() {
/* 372 */     if (this.format != Format.RGBA) {
/* 373 */       throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
/*     */     }
/* 375 */     checkAllocated();
/* 376 */     int[] $$0 = new int[getWidth() * getHeight()];
/* 377 */     for (int $$1 = 0; $$1 < getHeight(); $$1++) {
/* 378 */       for (int $$2 = 0; $$2 < getWidth(); $$2++) {
/* 379 */         int $$3 = getPixelRGBA($$2, $$1);
/* 380 */         $$0[$$2 + $$1 * getWidth()] = FastColor.ARGB32.color(
/* 381 */             FastColor.ABGR32.alpha($$3), 
/* 382 */             FastColor.ABGR32.red($$3), 
/* 383 */             FastColor.ABGR32.green($$3), 
/* 384 */             FastColor.ABGR32.blue($$3));
/*     */       } 
/*     */     } 
/*     */     
/* 388 */     return $$0;
/*     */   }
/*     */   
/*     */   public void upload(int $$0, int $$1, int $$2, boolean $$3) {
/* 392 */     upload($$0, $$1, $$2, 0, 0, this.width, this.height, false, $$3);
/*     */   }
/*     */   
/*     */   public void upload(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, boolean $$7, boolean $$8) {
/* 396 */     upload($$0, $$1, $$2, $$3, $$4, $$5, $$6, false, false, $$7, $$8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void upload(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, boolean $$7, boolean $$8, boolean $$9, boolean $$10) {
/* 402 */     if (!RenderSystem.isOnRenderThreadOrInit()) {
/* 403 */       RenderSystem.recordRenderCall(() -> _upload($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10));
/*     */     }
/*     */     else {
/*     */       
/* 407 */       _upload($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _upload(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, boolean $$7, boolean $$8, boolean $$9, boolean $$10) {
/*     */     try {
/* 413 */       RenderSystem.assertOnRenderThreadOrInit();
/* 414 */       checkAllocated();
/* 415 */       setFilter($$7, $$9);
/*     */ 
/*     */       
/* 418 */       if ($$5 == getWidth()) {
/* 419 */         GlStateManager._pixelStore(3314, 0);
/*     */       } else {
/* 421 */         GlStateManager._pixelStore(3314, getWidth());
/*     */       } 
/* 423 */       GlStateManager._pixelStore(3316, $$3);
/* 424 */       GlStateManager._pixelStore(3315, $$4);
/*     */       
/* 426 */       this.format.setUnpackPixelStoreState();
/*     */       
/* 428 */       GlStateManager._texSubImage2D(3553, $$0, $$1, $$2, $$5, $$6, this.format.glFormat(), 5121, this.pixels);
/* 429 */       if ($$8) {
/* 430 */         GlStateManager._texParameter(3553, 10242, 33071);
/* 431 */         GlStateManager._texParameter(3553, 10243, 33071);
/*     */       } 
/*     */     } finally {
/* 434 */       if ($$10) {
/* 435 */         close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void downloadTexture(int $$0, boolean $$1) {
/* 441 */     RenderSystem.assertOnRenderThread();
/* 442 */     checkAllocated();
/*     */     
/* 444 */     this.format.setPackPixelStoreState();
/*     */     
/* 446 */     GlStateManager._getTexImage(3553, $$0, this.format.glFormat(), 5121, this.pixels);
/*     */     
/* 448 */     if ($$1 && this.format.hasAlpha()) {
/* 449 */       for (int $$2 = 0; $$2 < getHeight(); $$2++) {
/* 450 */         for (int $$3 = 0; $$3 < getWidth(); $$3++) {
/* 451 */           setPixelRGBA($$3, $$2, getPixelRGBA($$3, $$2) | 255 << this.format.alphaOffset());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void downloadDepthBuffer(float $$0) {
/* 458 */     RenderSystem.assertOnRenderThread();
/* 459 */     if (this.format.components() != 1) {
/* 460 */       throw new IllegalStateException("Depth buffer must be stored in NativeImage with 1 component.");
/*     */     }
/* 462 */     checkAllocated();
/* 463 */     this.format.setPackPixelStoreState();
/* 464 */     GlStateManager._readPixels(0, 0, this.width, this.height, 6402, 5121, this.pixels);
/*     */   }
/*     */   
/*     */   public void drawPixels() {
/* 468 */     RenderSystem.assertOnRenderThread();
/* 469 */     this.format.setUnpackPixelStoreState();
/* 470 */     GlStateManager._glDrawPixels(this.width, this.height, this.format.glFormat(), 5121, this.pixels);
/*     */   }
/*     */   
/*     */   public void writeToFile(File $$0) throws IOException {
/* 474 */     writeToFile($$0.toPath());
/*     */   }
/*     */   
/*     */   public void copyFromFont(STBTTFontinfo $$0, int $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7, int $$8, int $$9) {
/* 478 */     if ($$8 < 0 || $$8 + $$2 > getWidth() || $$9 < 0 || $$9 + $$3 > getHeight()) {
/* 479 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", new Object[] { Integer.valueOf($$8), Integer.valueOf($$9), Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf(getWidth()), Integer.valueOf(getHeight()) }));
/*     */     }
/* 481 */     if (this.format.components() != 1) {
/* 482 */       throw new IllegalArgumentException("Can only write fonts into 1-component images.");
/*     */     }
/* 484 */     STBTruetype.nstbtt_MakeGlyphBitmapSubpixel($$0.address(), this.pixels + $$8 + ($$9 * getWidth()), $$2, $$3, getWidth(), $$4, $$5, $$6, $$7, $$1);
/*     */   }
/*     */   
/*     */   private static class WriteCallback extends STBIWriteCallback {
/*     */     private final WritableByteChannel output;
/*     */     @Nullable
/*     */     private IOException exception;
/*     */     
/*     */     WriteCallback(WritableByteChannel $$0) {
/* 493 */       this.output = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void invoke(long $$0, long $$1, int $$2) {
/* 498 */       ByteBuffer $$3 = getData($$1, $$2);
/*     */       try {
/* 500 */         this.output.write($$3);
/* 501 */       } catch (IOException $$4) {
/* 502 */         this.exception = $$4;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void throwIfException() throws IOException {
/* 507 */       if (this.exception != null) {
/* 508 */         throw this.exception;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToFile(Path $$0) throws IOException {
/* 515 */     if (!this.format.supportedByStb()) {
/* 516 */       throw new UnsupportedOperationException("Don't know how to write format " + this.format);
/*     */     }
/* 518 */     checkAllocated();
/* 519 */     WritableByteChannel $$1 = Files.newByteChannel($$0, (Set)OPEN_OPTIONS, (FileAttribute<?>[])new FileAttribute[0]); 
/* 520 */     try { if (!writeToChannel($$1)) {
/* 521 */         throw new IOException("Could not write image to the PNG file \"" + $$0.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
/*     */       }
/* 523 */       if ($$1 != null) $$1.close();  }
/*     */     catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 528 */      } public byte[] asByteArray() throws IOException { ByteArrayOutputStream $$0 = new ByteArrayOutputStream(); 
/* 529 */     try { WritableByteChannel $$1 = Channels.newChannel($$0);
/*     */       
/* 531 */       try { if (!writeToChannel($$1)) {
/* 532 */           throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
/*     */         }
/*     */         
/* 535 */         byte[] arrayOfByte = $$0.toByteArray();
/* 536 */         if ($$1 != null) $$1.close();  $$0.close(); return arrayOfByte; } catch (Throwable throwable) { if ($$1 != null)
/*     */           try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { try { $$0.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 540 */      } private boolean writeToChannel(WritableByteChannel $$0) throws IOException { WriteCallback $$1 = new WriteCallback($$0);
/*     */     try {
/* 542 */       int $$2 = Math.min(getHeight(), Integer.MAX_VALUE / getWidth() / this.format.components());
/* 543 */       if ($$2 < getHeight()) {
/* 544 */         LOGGER.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", Integer.valueOf(getHeight()), Integer.valueOf($$2));
/*     */       }
/* 546 */       if (STBImageWrite.nstbi_write_png_to_func($$1.address(), 0L, getWidth(), $$2, this.format.components(), this.pixels, 0) == 0) {
/* 547 */         return false;
/*     */       }
/*     */       
/* 550 */       $$1.throwIfException();
/* 551 */       return true;
/*     */     } finally {
/* 553 */       $$1.free();
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyFrom(NativeImage $$0) {
/* 562 */     if ($$0.format() != this.format) {
/* 563 */       throw new UnsupportedOperationException("Image formats don't match.");
/*     */     }
/* 565 */     int $$1 = this.format.components();
/* 566 */     checkAllocated();
/* 567 */     $$0.checkAllocated();
/* 568 */     if (this.width == $$0.width) {
/* 569 */       MemoryUtil.memCopy($$0.pixels, this.pixels, Math.min(this.size, $$0.size));
/*     */     } else {
/* 571 */       int $$2 = Math.min(getWidth(), $$0.getWidth());
/* 572 */       int $$3 = Math.min(getHeight(), $$0.getHeight());
/* 573 */       for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 574 */         int $$5 = $$4 * $$0.getWidth() * $$1;
/* 575 */         int $$6 = $$4 * getWidth() * $$1;
/* 576 */         MemoryUtil.memCopy($$0.pixels + $$5, this.pixels + $$6, $$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillRect(int $$0, int $$1, int $$2, int $$3, int $$4) {
/* 583 */     for (int $$5 = $$1; $$5 < $$1 + $$3; $$5++) {
/* 584 */       for (int $$6 = $$0; $$6 < $$0 + $$2; $$6++) {
/* 585 */         setPixelRGBA($$6, $$5, $$4);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyRect(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, boolean $$6, boolean $$7) {
/* 592 */     copyRect(this, $$0, $$1, $$0 + $$2, $$1 + $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyRect(NativeImage $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, boolean $$7, boolean $$8) {
/* 600 */     for (int $$9 = 0; $$9 < $$6; $$9++) {
/* 601 */       for (int $$10 = 0; $$10 < $$5; $$10++) {
/* 602 */         int $$11 = $$7 ? ($$5 - 1 - $$10) : $$10;
/* 603 */         int $$12 = $$8 ? ($$6 - 1 - $$9) : $$9;
/* 604 */         int $$13 = getPixelRGBA($$1 + $$10, $$2 + $$9);
/* 605 */         $$0.setPixelRGBA($$3 + $$11, $$4 + $$12, $$13);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flipY() {
/* 612 */     checkAllocated();
/* 613 */     int $$0 = this.format.components();
/* 614 */     int $$1 = getWidth() * $$0;
/* 615 */     long $$2 = MemoryUtil.nmemAlloc($$1);
/*     */     try {
/* 617 */       for (int $$3 = 0; $$3 < getHeight() / 2; $$3++) {
/* 618 */         int $$4 = $$3 * getWidth() * $$0;
/* 619 */         int $$5 = (getHeight() - 1 - $$3) * getWidth() * $$0;
/* 620 */         MemoryUtil.memCopy(this.pixels + $$4, $$2, $$1);
/* 621 */         MemoryUtil.memCopy(this.pixels + $$5, this.pixels + $$4, $$1);
/* 622 */         MemoryUtil.memCopy($$2, this.pixels + $$5, $$1);
/*     */       } 
/*     */     } finally {
/* 625 */       MemoryUtil.nmemFree($$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resizeSubRectTo(int $$0, int $$1, int $$2, int $$3, NativeImage $$4) {
/* 632 */     checkAllocated();
/* 633 */     if ($$4.format() != this.format) {
/* 634 */       throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
/*     */     }
/* 636 */     int $$5 = this.format.components();
/* 637 */     STBImageResize.nstbir_resize_uint8(this.pixels + (($$0 + $$1 * 
/* 638 */         getWidth()) * $$5), $$2, $$3, getWidth() * $$5, $$4.pixels, $$4
/* 639 */         .getWidth(), $$4.getHeight(), 0, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void untrack() {
/* 645 */     DebugMemoryUntracker.untrack(this.pixels);
/*     */   }
/*     */   
/*     */   public enum InternalGlFormat {
/* 649 */     RGBA(6408),
/* 650 */     RGB(6407),
/* 651 */     RG(33319),
/* 652 */     RED(6403);
/*     */     
/*     */     private final int glFormat;
/*     */ 
/*     */     
/*     */     InternalGlFormat(int $$0) {
/* 658 */       this.glFormat = $$0;
/*     */     }
/*     */     
/*     */     public int glFormat() {
/* 662 */       return this.glFormat;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Format
/*     */   {
/* 670 */     RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 676 */     RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 682 */     LUMINANCE_ALPHA(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 688 */     LUMINANCE(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);
/*     */     
/*     */     final int components;
/*     */     
/*     */     private final int glFormat;
/*     */     
/*     */     private final boolean hasRed;
/*     */     
/*     */     private final boolean hasGreen;
/*     */     
/*     */     private final boolean hasBlue;
/*     */     
/*     */     private final boolean hasLuminance;
/*     */     
/*     */     private final boolean hasAlpha;
/*     */     
/*     */     private final int redOffset;
/*     */     
/*     */     private final int greenOffset;
/*     */     
/*     */     private final int blueOffset;
/*     */     private final int luminanceOffset;
/*     */     private final int alphaOffset;
/*     */     private final boolean supportedByStb;
/*     */     
/*     */     Format(int $$0, int $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6, int $$7, int $$8, int $$9, int $$10, int $$11, boolean $$12) {
/* 714 */       this.components = $$0;
/* 715 */       this.glFormat = $$1;
/* 716 */       this.hasRed = $$2;
/* 717 */       this.hasGreen = $$3;
/* 718 */       this.hasBlue = $$4;
/* 719 */       this.hasLuminance = $$5;
/* 720 */       this.hasAlpha = $$6;
/* 721 */       this.redOffset = $$7;
/* 722 */       this.greenOffset = $$8;
/* 723 */       this.blueOffset = $$9;
/* 724 */       this.luminanceOffset = $$10;
/* 725 */       this.alphaOffset = $$11;
/* 726 */       this.supportedByStb = $$12;
/*     */     }
/*     */     
/*     */     public int components() {
/* 730 */       return this.components;
/*     */     }
/*     */     
/*     */     public void setPackPixelStoreState() {
/* 734 */       RenderSystem.assertOnRenderThread();
/* 735 */       GlStateManager._pixelStore(3333, components());
/*     */     }
/*     */     
/*     */     public void setUnpackPixelStoreState() {
/* 739 */       RenderSystem.assertOnRenderThreadOrInit();
/* 740 */       GlStateManager._pixelStore(3317, components());
/*     */     }
/*     */     
/*     */     public int glFormat() {
/* 744 */       return this.glFormat;
/*     */     }
/*     */     
/*     */     public boolean hasRed() {
/* 748 */       return this.hasRed;
/*     */     }
/*     */     
/*     */     public boolean hasGreen() {
/* 752 */       return this.hasGreen;
/*     */     }
/*     */     
/*     */     public boolean hasBlue() {
/* 756 */       return this.hasBlue;
/*     */     }
/*     */     
/*     */     public boolean hasLuminance() {
/* 760 */       return this.hasLuminance;
/*     */     }
/*     */     
/*     */     public boolean hasAlpha() {
/* 764 */       return this.hasAlpha;
/*     */     }
/*     */     
/*     */     public int redOffset() {
/* 768 */       return this.redOffset;
/*     */     }
/*     */     
/*     */     public int greenOffset() {
/* 772 */       return this.greenOffset;
/*     */     }
/*     */     
/*     */     public int blueOffset() {
/* 776 */       return this.blueOffset;
/*     */     }
/*     */     
/*     */     public int luminanceOffset() {
/* 780 */       return this.luminanceOffset;
/*     */     }
/*     */     
/*     */     public int alphaOffset() {
/* 784 */       return this.alphaOffset;
/*     */     }
/*     */     
/*     */     public boolean hasLuminanceOrRed() {
/* 788 */       return (this.hasLuminance || this.hasRed);
/*     */     }
/*     */     
/*     */     public boolean hasLuminanceOrGreen() {
/* 792 */       return (this.hasLuminance || this.hasGreen);
/*     */     }
/*     */     
/*     */     public boolean hasLuminanceOrBlue() {
/* 796 */       return (this.hasLuminance || this.hasBlue);
/*     */     }
/*     */     
/*     */     public boolean hasLuminanceOrAlpha() {
/* 800 */       return (this.hasLuminance || this.hasAlpha);
/*     */     }
/*     */     
/*     */     public int luminanceOrRedOffset() {
/* 804 */       return this.hasLuminance ? this.luminanceOffset : this.redOffset;
/*     */     }
/*     */     
/*     */     public int luminanceOrGreenOffset() {
/* 808 */       return this.hasLuminance ? this.luminanceOffset : this.greenOffset;
/*     */     }
/*     */     
/*     */     public int luminanceOrBlueOffset() {
/* 812 */       return this.hasLuminance ? this.luminanceOffset : this.blueOffset;
/*     */     }
/*     */     
/*     */     public int luminanceOrAlphaOffset() {
/* 816 */       return this.hasLuminance ? this.luminanceOffset : this.alphaOffset;
/*     */     }
/*     */     
/*     */     public boolean supportedByStb() {
/* 820 */       return this.supportedByStb;
/*     */     }
/*     */     
/*     */     static Format getStbFormat(int $$0) {
/* 824 */       switch ($$0) {
/*     */         case 1:
/* 826 */           return LUMINANCE;
/*     */         case 2:
/* 828 */           return LUMINANCE_ALPHA;
/*     */         case 3:
/* 830 */           return RGB;
/*     */       } 
/*     */       
/* 833 */       return RGBA;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\NativeImage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */