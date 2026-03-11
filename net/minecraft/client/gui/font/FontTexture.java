/*     */ package net.minecraft.client.gui.font;
/*     */ 
/*     */ import com.mojang.blaze3d.font.SheetGlyphInfo;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.Dumpable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ 
/*     */ public class FontTexture
/*     */   extends AbstractTexture
/*     */   implements Dumpable {
/*     */   private static final int SIZE = 256;
/*     */   private final GlyphRenderTypes renderTypes;
/*     */   private final boolean colored;
/*     */   private final Node root;
/*     */   
/*     */   public FontTexture(GlyphRenderTypes $$0, boolean $$1) {
/*  23 */     this.colored = $$1;
/*  24 */     this.root = new Node(0, 0, 256, 256);
/*  25 */     TextureUtil.prepareImage($$1 ? NativeImage.InternalGlFormat.RGBA : NativeImage.InternalGlFormat.RED, getId(), 256, 256);
/*  26 */     this.renderTypes = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(ResourceManager $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  36 */     releaseId();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BakedGlyph add(SheetGlyphInfo $$0) {
/*  41 */     if ($$0.isColored() != this.colored) {
/*  42 */       return null;
/*     */     }
/*     */     
/*  45 */     Node $$1 = this.root.insert($$0);
/*     */     
/*  47 */     if ($$1 != null) {
/*  48 */       bind();
/*  49 */       $$0.upload($$1.x, $$1.y);
/*     */       
/*  51 */       float $$2 = 256.0F;
/*  52 */       float $$3 = 256.0F;
/*     */ 
/*     */ 
/*     */       
/*  56 */       float $$4 = 0.01F;
/*     */       
/*  58 */       return new BakedGlyph(this.renderTypes, ($$1.x + 0.01F) / 256.0F, ($$1.x - 0.01F + $$0
/*     */           
/*  60 */           .getPixelWidth()) / 256.0F, ($$1.y + 0.01F) / 256.0F, ($$1.y - 0.01F + $$0
/*     */           
/*  62 */           .getPixelHeight()) / 256.0F, $$0
/*     */           
/*  64 */           .getLeft(), $$0
/*  65 */           .getRight(), $$0
/*  66 */           .getUp(), $$0
/*  67 */           .getDown());
/*     */     } 
/*     */ 
/*     */     
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dumpContents(ResourceLocation $$0, Path $$1) {
/*  76 */     String $$2 = $$0.toDebugFileName();
/*  77 */     TextureUtil.writeAsPNG($$1, $$2, getId(), 0, 256, 256, $$0 -> (($$0 & 0xFF000000) == 0) ? -16777216 : $$0);
/*     */   }
/*     */   
/*     */   private static class Node {
/*     */     final int x;
/*     */     final int y;
/*     */     private final int width;
/*     */     private final int height;
/*     */     @Nullable
/*     */     private Node left;
/*     */     @Nullable
/*     */     private Node right;
/*     */     private boolean occupied;
/*     */     
/*     */     Node(int $$0, int $$1, int $$2, int $$3) {
/*  92 */       this.x = $$0;
/*  93 */       this.y = $$1;
/*  94 */       this.width = $$2;
/*  95 */       this.height = $$3;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     Node insert(SheetGlyphInfo $$0) {
/* 100 */       if (this.left != null && this.right != null) {
/* 101 */         Node $$1 = this.left.insert($$0);
/* 102 */         if ($$1 == null) {
/* 103 */           $$1 = this.right.insert($$0);
/*     */         }
/* 105 */         return $$1;
/*     */       } 
/*     */       
/* 108 */       if (this.occupied) {
/* 109 */         return null;
/*     */       }
/* 111 */       int $$2 = $$0.getPixelWidth();
/* 112 */       int $$3 = $$0.getPixelHeight();
/*     */       
/* 114 */       if ($$2 > this.width || $$3 > this.height) {
/* 115 */         return null;
/*     */       }
/* 117 */       if ($$2 == this.width && $$3 == this.height) {
/* 118 */         this.occupied = true;
/* 119 */         return this;
/*     */       } 
/*     */       
/* 122 */       int $$4 = this.width - $$2;
/* 123 */       int $$5 = this.height - $$3;
/*     */       
/* 125 */       if ($$4 > $$5) {
/* 126 */         this.left = new Node(this.x, this.y, $$2, this.height);
/* 127 */         this.right = new Node(this.x + $$2 + 1, this.y, this.width - $$2 - 1, this.height);
/*     */       } else {
/* 129 */         this.left = new Node(this.x, this.y, this.width, $$3);
/* 130 */         this.right = new Node(this.x, this.y + $$3 + 1, this.width, this.height - $$3 - 1);
/*     */       } 
/* 132 */       return this.left.insert($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\FontTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */