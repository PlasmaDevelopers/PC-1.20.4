/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
/*     */ import net.minecraft.client.resources.metadata.animation.FrameSize;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*     */ import net.minecraft.server.packs.resources.ResourceMetadata;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpriteContents
/*     */   implements Stitcher.Entry, AutoCloseable
/*     */ {
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final ResourceLocation name;
/*     */   
/*     */   final int width;
/*     */   final int height;
/*     */   private final NativeImage originalImage;
/*     */   NativeImage[] byMipLevel;
/*     */   @Nullable
/*     */   private final AnimatedTexture animatedTexture;
/*     */   private final ResourceMetadata metadata;
/*     */   
/*     */   public SpriteContents(ResourceLocation $$0, FrameSize $$1, NativeImage $$2, ResourceMetadata $$3) {
/*  44 */     this.name = $$0;
/*  45 */     this.width = $$1.width();
/*  46 */     this.height = $$1.height();
/*  47 */     this.metadata = $$3;
/*     */     
/*  49 */     AnimationMetadataSection $$4 = $$3.getSection((MetadataSectionSerializer)AnimationMetadataSection.SERIALIZER).orElse(AnimationMetadataSection.EMPTY);
/*  50 */     this.animatedTexture = createAnimatedTexture($$1, $$2.getWidth(), $$2.getHeight(), $$4);
/*  51 */     this.originalImage = $$2;
/*  52 */     this.byMipLevel = new NativeImage[] { this.originalImage };
/*     */   }
/*     */   
/*     */   public void increaseMipLevel(int $$0) {
/*     */     try {
/*  57 */       this.byMipLevel = MipmapGenerator.generateMipLevels(this.byMipLevel, $$0);
/*  58 */     } catch (Throwable $$1) {
/*  59 */       CrashReport $$2 = CrashReport.forThrowable($$1, "Generating mipmaps for frame");
/*     */       
/*  61 */       CrashReportCategory $$3 = $$2.addCategory("Sprite being mipmapped");
/*  62 */       $$3.setDetail("First frame", () -> {
/*     */             StringBuilder $$0 = new StringBuilder();
/*     */             
/*     */             if ($$0.length() > 0) {
/*     */               $$0.append(", ");
/*     */             }
/*     */             
/*     */             $$0.append(this.originalImage.getWidth()).append("x").append(this.originalImage.getHeight());
/*     */             
/*     */             return $$0.toString();
/*     */           });
/*  73 */       CrashReportCategory $$4 = $$2.addCategory("Frame being iterated");
/*  74 */       $$4.setDetail("Sprite name", this.name);
/*  75 */       $$4.setDetail("Sprite size", () -> "" + this.width + " x " + this.width);
/*  76 */       $$4.setDetail("Sprite frames", () -> "" + getFrameCount() + " frames");
/*  77 */       $$4.setDetail("Mipmap levels", Integer.valueOf($$0));
/*     */       
/*  79 */       throw new ReportedException($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getFrameCount() {
/*  84 */     return (this.animatedTexture != null) ? this.animatedTexture.frames.size() : 1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private AnimatedTexture createAnimatedTexture(FrameSize $$0, int $$1, int $$2, AnimationMetadataSection $$3) {
/*  89 */     int $$4 = $$1 / $$0.width();
/*  90 */     int $$5 = $$2 / $$0.height();
/*  91 */     int $$6 = $$4 * $$5;
/*     */     
/*  93 */     List<FrameInfo> $$7 = new ArrayList<>();
/*  94 */     $$3.forEachFrame(($$1, $$2) -> $$0.add(new FrameInfo($$1, $$2)));
/*     */     
/*  96 */     if ($$7.isEmpty()) {
/*     */       
/*  98 */       for (int $$8 = 0; $$8 < $$6; $$8++) {
/*  99 */         $$7.add(new FrameInfo($$8, $$3.getDefaultFrameTime()));
/*     */       }
/*     */     } else {
/* 102 */       int $$9 = 0;
/* 103 */       IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
/*     */       
/* 105 */       Iterator<FrameInfo> $$11 = $$7.iterator();
/* 106 */       while ($$11.hasNext()) {
/* 107 */         FrameInfo $$12 = $$11.next();
/* 108 */         boolean $$13 = true;
/* 109 */         if ($$12.time <= 0) {
/* 110 */           LOGGER.warn("Invalid frame duration on sprite {} frame {}: {}", new Object[] { this.name, Integer.valueOf($$9), Integer.valueOf($$12.time) });
/* 111 */           $$13 = false;
/*     */         } 
/* 113 */         if ($$12.index < 0 || $$12.index >= $$6) {
/* 114 */           LOGGER.warn("Invalid frame index on sprite {} frame {}: {}", new Object[] { this.name, Integer.valueOf($$9), Integer.valueOf($$12.index) });
/* 115 */           $$13 = false;
/*     */         } 
/*     */         
/* 118 */         if ($$13) {
/* 119 */           intOpenHashSet.add($$12.index);
/*     */         } else {
/* 121 */           $$11.remove();
/*     */         } 
/* 123 */         $$9++;
/*     */       } 
/*     */       
/* 126 */       int[] $$14 = IntStream.range(0, $$6).filter($$1 -> !$$0.contains($$1)).toArray();
/* 127 */       if ($$14.length > 0) {
/* 128 */         LOGGER.warn("Unused frames in sprite {}: {}", this.name, Arrays.toString($$14));
/*     */       }
/*     */     } 
/*     */     
/* 132 */     if ($$7.size() <= 1) {
/* 133 */       return null;
/*     */     }
/*     */     
/* 136 */     return new AnimatedTexture((List<FrameInfo>)ImmutableList.copyOf($$7), $$4, $$3.isInterpolatedFrames());
/*     */   }
/*     */   
/*     */   void upload(int $$0, int $$1, int $$2, int $$3, NativeImage[] $$4) {
/* 140 */     for (int $$5 = 0; $$5 < this.byMipLevel.length; $$5++) {
/* 141 */       $$4[$$5].upload($$5, $$0 >> $$5, $$1 >> $$5, $$2 >> $$5, $$3 >> $$5, this.width >> $$5, this.height >> $$5, (this.byMipLevel.length > 1), false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int width() {
/* 147 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int height() {
/* 152 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation name() {
/* 157 */     return this.name;
/*     */   }
/*     */   
/*     */   public IntStream getUniqueFrames() {
/* 161 */     return (this.animatedTexture != null) ? this.animatedTexture.getUniqueFrames() : IntStream.of(1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpriteTicker createTicker() {
/* 166 */     return (this.animatedTexture != null) ? this.animatedTexture.createTicker() : null;
/*     */   }
/*     */   
/*     */   public ResourceMetadata metadata() {
/* 170 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 175 */     for (NativeImage $$0 : this.byMipLevel) {
/* 176 */       $$0.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 182 */     return "SpriteContents{name=" + this.name + ", frameCount=" + getFrameCount() + ", height=" + this.height + ", width=" + this.width + "}";
/*     */   }
/*     */   
/*     */   public boolean isTransparent(int $$0, int $$1, int $$2) {
/* 186 */     int $$3 = $$1;
/* 187 */     int $$4 = $$2;
/* 188 */     if (this.animatedTexture != null) {
/* 189 */       $$3 += this.animatedTexture.getFrameX($$0) * this.width;
/* 190 */       $$4 += this.animatedTexture.getFrameY($$0) * this.height;
/*     */     } 
/* 192 */     return ((this.originalImage.getPixelRGBA($$3, $$4) >> 24 & 0xFF) == 0);
/*     */   }
/*     */   
/*     */   public void uploadFirstFrame(int $$0, int $$1) {
/* 196 */     if (this.animatedTexture != null) {
/* 197 */       this.animatedTexture.uploadFirstFrame($$0, $$1);
/*     */     } else {
/* 199 */       upload($$0, $$1, 0, 0, this.byMipLevel);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class InterpolationData
/*     */     implements AutoCloseable
/*     */   {
/* 207 */     private final NativeImage[] activeFrame = new NativeImage[SpriteContents.this.byMipLevel.length];
/*     */     InterpolationData() {
/* 209 */       for (int $$0 = 0; $$0 < this.activeFrame.length; $$0++) {
/* 210 */         int $$1 = SpriteContents.this.width >> $$0;
/* 211 */         int $$2 = SpriteContents.this.height >> $$0;
/* 212 */         this.activeFrame[$$0] = new NativeImage($$1, $$2, false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void uploadInterpolatedFrame(int $$0, int $$1, SpriteContents.Ticker $$2) {
/* 218 */       SpriteContents.AnimatedTexture $$3 = $$2.animationInfo;
/* 219 */       List<SpriteContents.FrameInfo> $$4 = $$3.frames;
/* 220 */       SpriteContents.FrameInfo $$5 = $$4.get($$2.frame);
/* 221 */       double $$6 = 1.0D - $$2.subFrame / $$5.time;
/*     */       
/* 223 */       int $$7 = $$5.index;
/* 224 */       int $$8 = ((SpriteContents.FrameInfo)$$4.get(($$2.frame + 1) % $$4.size())).index;
/* 225 */       if ($$7 != $$8) {
/* 226 */         for (int $$9 = 0; $$9 < this.activeFrame.length; $$9++) {
/* 227 */           int $$10 = SpriteContents.this.width >> $$9;
/* 228 */           int $$11 = SpriteContents.this.height >> $$9;
/* 229 */           for (int $$12 = 0; $$12 < $$11; $$12++) {
/* 230 */             for (int $$13 = 0; $$13 < $$10; $$13++) {
/* 231 */               int $$14 = getPixel($$3, $$7, $$9, $$13, $$12);
/* 232 */               int $$15 = getPixel($$3, $$8, $$9, $$13, $$12);
/* 233 */               int $$16 = mix($$6, $$14 >> 16 & 0xFF, $$15 >> 16 & 0xFF);
/* 234 */               int $$17 = mix($$6, $$14 >> 8 & 0xFF, $$15 >> 8 & 0xFF);
/* 235 */               int $$18 = mix($$6, $$14 & 0xFF, $$15 & 0xFF);
/*     */               
/* 237 */               this.activeFrame[$$9].setPixelRGBA($$13, $$12, $$14 & 0xFF000000 | $$16 << 16 | $$17 << 8 | $$18);
/*     */             } 
/*     */           } 
/*     */         } 
/* 241 */         SpriteContents.this.upload($$0, $$1, 0, 0, this.activeFrame);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int getPixel(SpriteContents.AnimatedTexture $$0, int $$1, int $$2, int $$3, int $$4) {
/* 257 */       return SpriteContents.this.byMipLevel[$$2].getPixelRGBA($$3 + ($$0.getFrameX($$1) * SpriteContents.this.width >> $$2), $$4 + ($$0.getFrameY($$1) * SpriteContents.this.height >> $$2));
/*     */     }
/*     */     
/*     */     private int mix(double $$0, int $$1, int $$2) {
/* 261 */       return (int)($$0 * $$1 + (1.0D - $$0) * $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 266 */       for (NativeImage $$0 : this.activeFrame)
/* 267 */         $$0.close(); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FrameInfo
/*     */   {
/*     */     final int index;
/*     */     final int time;
/*     */     
/*     */     FrameInfo(int $$0, int $$1) {
/* 277 */       this.index = $$0;
/* 278 */       this.time = $$1;
/*     */     }
/*     */   }
/*     */   
/*     */   private class AnimatedTexture {
/*     */     final List<SpriteContents.FrameInfo> frames;
/*     */     private final int frameRowSize;
/*     */     private final boolean interpolateFrames;
/*     */     
/*     */     AnimatedTexture(List<SpriteContents.FrameInfo> $$0, int $$1, boolean $$2) {
/* 288 */       this.frames = $$0;
/* 289 */       this.frameRowSize = $$1;
/* 290 */       this.interpolateFrames = $$2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getFrameX(int $$0) {
/* 299 */       return $$0 % this.frameRowSize;
/*     */     }
/*     */     
/*     */     int getFrameY(int $$0) {
/* 303 */       return $$0 / this.frameRowSize;
/*     */     }
/*     */     
/*     */     void uploadFrame(int $$0, int $$1, int $$2) {
/* 307 */       int $$3 = getFrameX($$2) * SpriteContents.this.width;
/* 308 */       int $$4 = getFrameY($$2) * SpriteContents.this.height;
/* 309 */       SpriteContents.this.upload($$0, $$1, $$3, $$4, SpriteContents.this.byMipLevel);
/*     */     }
/*     */     
/*     */     public SpriteTicker createTicker() {
/* 313 */       return new SpriteContents.Ticker(this, this.interpolateFrames ? new SpriteContents.InterpolationData() : null);
/*     */     }
/*     */     
/*     */     public void uploadFirstFrame(int $$0, int $$1) {
/* 317 */       uploadFrame($$0, $$1, ((SpriteContents.FrameInfo)this.frames.get(0)).index);
/*     */     }
/*     */     
/*     */     public IntStream getUniqueFrames() {
/* 321 */       return this.frames.stream().mapToInt($$0 -> $$0.index).distinct();
/*     */     }
/*     */   }
/*     */   
/*     */   private class Ticker
/*     */     implements SpriteTicker {
/*     */     int frame;
/*     */     int subFrame;
/*     */     final SpriteContents.AnimatedTexture animationInfo;
/*     */     @Nullable
/*     */     private final SpriteContents.InterpolationData interpolationData;
/*     */     
/*     */     Ticker(@Nullable SpriteContents.AnimatedTexture $$0, SpriteContents.InterpolationData $$1) {
/* 334 */       this.animationInfo = $$0;
/* 335 */       this.interpolationData = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tickAndUpload(int $$0, int $$1) {
/* 340 */       this.subFrame++;
/* 341 */       SpriteContents.FrameInfo $$2 = this.animationInfo.frames.get(this.frame);
/* 342 */       if (this.subFrame >= $$2.time) {
/* 343 */         int $$3 = $$2.index;
/* 344 */         this.frame = (this.frame + 1) % this.animationInfo.frames.size();
/* 345 */         this.subFrame = 0;
/*     */         
/* 347 */         int $$4 = ((SpriteContents.FrameInfo)this.animationInfo.frames.get(this.frame)).index;
/* 348 */         if ($$3 != $$4) {
/* 349 */           this.animationInfo.uploadFrame($$0, $$1, $$4);
/*     */         }
/* 351 */       } else if (this.interpolationData != null) {
/* 352 */         if (!RenderSystem.isOnRenderThread()) {
/* 353 */           RenderSystem.recordRenderCall(() -> this.interpolationData.uploadInterpolatedFrame($$0, $$1, this));
/*     */         } else {
/* 355 */           this.interpolationData.uploadInterpolatedFrame($$0, $$1, this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 362 */       if (this.interpolationData != null)
/* 363 */         this.interpolationData.close(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\SpriteContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */