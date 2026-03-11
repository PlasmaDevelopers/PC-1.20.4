/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Format
/*     */ {
/* 670 */   RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 676 */   RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 682 */   LUMINANCE_ALPHA(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 688 */   LUMINANCE(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);
/*     */   
/*     */   final int components;
/*     */   
/*     */   private final int glFormat;
/*     */   
/*     */   private final boolean hasRed;
/*     */   
/*     */   private final boolean hasGreen;
/*     */   
/*     */   private final boolean hasBlue;
/*     */   
/*     */   private final boolean hasLuminance;
/*     */   
/*     */   private final boolean hasAlpha;
/*     */   
/*     */   private final int redOffset;
/*     */   
/*     */   private final int greenOffset;
/*     */   
/*     */   private final int blueOffset;
/*     */   private final int luminanceOffset;
/*     */   private final int alphaOffset;
/*     */   private final boolean supportedByStb;
/*     */   
/*     */   Format(int $$0, int $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6, int $$7, int $$8, int $$9, int $$10, int $$11, boolean $$12) {
/* 714 */     this.components = $$0;
/* 715 */     this.glFormat = $$1;
/* 716 */     this.hasRed = $$2;
/* 717 */     this.hasGreen = $$3;
/* 718 */     this.hasBlue = $$4;
/* 719 */     this.hasLuminance = $$5;
/* 720 */     this.hasAlpha = $$6;
/* 721 */     this.redOffset = $$7;
/* 722 */     this.greenOffset = $$8;
/* 723 */     this.blueOffset = $$9;
/* 724 */     this.luminanceOffset = $$10;
/* 725 */     this.alphaOffset = $$11;
/* 726 */     this.supportedByStb = $$12;
/*     */   }
/*     */   
/*     */   public int components() {
/* 730 */     return this.components;
/*     */   }
/*     */   
/*     */   public void setPackPixelStoreState() {
/* 734 */     RenderSystem.assertOnRenderThread();
/* 735 */     GlStateManager._pixelStore(3333, components());
/*     */   }
/*     */   
/*     */   public void setUnpackPixelStoreState() {
/* 739 */     RenderSystem.assertOnRenderThreadOrInit();
/* 740 */     GlStateManager._pixelStore(3317, components());
/*     */   }
/*     */   
/*     */   public int glFormat() {
/* 744 */     return this.glFormat;
/*     */   }
/*     */   
/*     */   public boolean hasRed() {
/* 748 */     return this.hasRed;
/*     */   }
/*     */   
/*     */   public boolean hasGreen() {
/* 752 */     return this.hasGreen;
/*     */   }
/*     */   
/*     */   public boolean hasBlue() {
/* 756 */     return this.hasBlue;
/*     */   }
/*     */   
/*     */   public boolean hasLuminance() {
/* 760 */     return this.hasLuminance;
/*     */   }
/*     */   
/*     */   public boolean hasAlpha() {
/* 764 */     return this.hasAlpha;
/*     */   }
/*     */   
/*     */   public int redOffset() {
/* 768 */     return this.redOffset;
/*     */   }
/*     */   
/*     */   public int greenOffset() {
/* 772 */     return this.greenOffset;
/*     */   }
/*     */   
/*     */   public int blueOffset() {
/* 776 */     return this.blueOffset;
/*     */   }
/*     */   
/*     */   public int luminanceOffset() {
/* 780 */     return this.luminanceOffset;
/*     */   }
/*     */   
/*     */   public int alphaOffset() {
/* 784 */     return this.alphaOffset;
/*     */   }
/*     */   
/*     */   public boolean hasLuminanceOrRed() {
/* 788 */     return (this.hasLuminance || this.hasRed);
/*     */   }
/*     */   
/*     */   public boolean hasLuminanceOrGreen() {
/* 792 */     return (this.hasLuminance || this.hasGreen);
/*     */   }
/*     */   
/*     */   public boolean hasLuminanceOrBlue() {
/* 796 */     return (this.hasLuminance || this.hasBlue);
/*     */   }
/*     */   
/*     */   public boolean hasLuminanceOrAlpha() {
/* 800 */     return (this.hasLuminance || this.hasAlpha);
/*     */   }
/*     */   
/*     */   public int luminanceOrRedOffset() {
/* 804 */     return this.hasLuminance ? this.luminanceOffset : this.redOffset;
/*     */   }
/*     */   
/*     */   public int luminanceOrGreenOffset() {
/* 808 */     return this.hasLuminance ? this.luminanceOffset : this.greenOffset;
/*     */   }
/*     */   
/*     */   public int luminanceOrBlueOffset() {
/* 812 */     return this.hasLuminance ? this.luminanceOffset : this.blueOffset;
/*     */   }
/*     */   
/*     */   public int luminanceOrAlphaOffset() {
/* 816 */     return this.hasLuminance ? this.luminanceOffset : this.alphaOffset;
/*     */   }
/*     */   
/*     */   public boolean supportedByStb() {
/* 820 */     return this.supportedByStb;
/*     */   }
/*     */   
/*     */   static Format getStbFormat(int $$0) {
/* 824 */     switch ($$0) {
/*     */       case 1:
/* 826 */         return LUMINANCE;
/*     */       case 2:
/* 828 */         return LUMINANCE_ALPHA;
/*     */       case 3:
/* 830 */         return RGB;
/*     */     } 
/*     */     
/* 833 */     return RGBA;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\NativeImage$Format.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */