/*     */ package com.mojang.blaze3d.shaders;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlendMode
/*     */ {
/*     */   @Nullable
/*     */   private static BlendMode lastApplied;
/*     */   private final int srcColorFactor;
/*     */   private final int srcAlphaFactor;
/*     */   private final int dstColorFactor;
/*     */   private final int dstAlphaFactor;
/*     */   private final int blendFunc;
/*     */   private final boolean separateBlend;
/*     */   private final boolean opaque;
/*     */   
/*     */   private BlendMode(boolean $$0, boolean $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/*  22 */     this.separateBlend = $$0;
/*     */     
/*  24 */     this.srcColorFactor = $$2;
/*  25 */     this.dstColorFactor = $$3;
/*     */     
/*  27 */     this.srcAlphaFactor = $$4;
/*  28 */     this.dstAlphaFactor = $$5;
/*     */     
/*  30 */     this.opaque = $$1;
/*  31 */     this.blendFunc = $$6;
/*     */   }
/*     */   
/*     */   public BlendMode() {
/*  35 */     this(false, true, 1, 0, 1, 0, 32774);
/*     */   }
/*     */   
/*     */   public BlendMode(int $$0, int $$1, int $$2) {
/*  39 */     this(false, false, $$0, $$1, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public BlendMode(int $$0, int $$1, int $$2, int $$3, int $$4) {
/*  43 */     this(true, false, $$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void apply() {
/*  47 */     if (equals(lastApplied)) {
/*     */       return;
/*     */     }
/*     */     
/*  51 */     if (lastApplied == null || this.opaque != lastApplied.isOpaque()) {
/*  52 */       lastApplied = this;
/*  53 */       if (this.opaque) {
/*  54 */         RenderSystem.disableBlend();
/*     */         return;
/*     */       } 
/*  57 */       RenderSystem.enableBlend();
/*     */     } 
/*     */     
/*  60 */     RenderSystem.blendEquation(this.blendFunc);
/*     */     
/*  62 */     if (this.separateBlend) {
/*  63 */       RenderSystem.blendFuncSeparate(this.srcColorFactor, this.dstColorFactor, this.srcAlphaFactor, this.dstAlphaFactor);
/*     */     } else {
/*  65 */       RenderSystem.blendFunc(this.srcColorFactor, this.dstColorFactor);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  71 */     if (this == $$0) {
/*  72 */       return true;
/*     */     }
/*  74 */     if (!($$0 instanceof BlendMode)) {
/*  75 */       return false;
/*     */     }
/*     */     
/*  78 */     BlendMode $$1 = (BlendMode)$$0;
/*     */     
/*  80 */     if (this.blendFunc != $$1.blendFunc) {
/*  81 */       return false;
/*     */     }
/*  83 */     if (this.dstAlphaFactor != $$1.dstAlphaFactor) {
/*  84 */       return false;
/*     */     }
/*  86 */     if (this.dstColorFactor != $$1.dstColorFactor) {
/*  87 */       return false;
/*     */     }
/*  89 */     if (this.opaque != $$1.opaque) {
/*  90 */       return false;
/*     */     }
/*  92 */     if (this.separateBlend != $$1.separateBlend) {
/*  93 */       return false;
/*     */     }
/*  95 */     if (this.srcAlphaFactor != $$1.srcAlphaFactor) {
/*  96 */       return false;
/*     */     }
/*  98 */     return (this.srcColorFactor == $$1.srcColorFactor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     int $$0 = this.srcColorFactor;
/* 104 */     $$0 = 31 * $$0 + this.srcAlphaFactor;
/* 105 */     $$0 = 31 * $$0 + this.dstColorFactor;
/* 106 */     $$0 = 31 * $$0 + this.dstAlphaFactor;
/* 107 */     $$0 = 31 * $$0 + this.blendFunc;
/* 108 */     $$0 = 31 * $$0 + (this.separateBlend ? 1 : 0);
/* 109 */     $$0 = 31 * $$0 + (this.opaque ? 1 : 0);
/* 110 */     return $$0;
/*     */   }
/*     */   
/*     */   public boolean isOpaque() {
/* 114 */     return this.opaque;
/*     */   }
/*     */   
/*     */   public static int stringToBlendFunc(String $$0) {
/* 118 */     String $$1 = $$0.trim().toLowerCase(Locale.ROOT);
/*     */     
/* 120 */     if ("add".equals($$1))
/* 121 */       return 32774; 
/* 122 */     if ("subtract".equals($$1))
/* 123 */       return 32778; 
/* 124 */     if ("reversesubtract".equals($$1))
/* 125 */       return 32779; 
/* 126 */     if ("reverse_subtract".equals($$1))
/* 127 */       return 32779; 
/* 128 */     if ("min".equals($$1))
/* 129 */       return 32775; 
/* 130 */     if ("max".equals($$1)) {
/* 131 */       return 32776;
/*     */     }
/*     */     
/* 134 */     return 32774;
/*     */   }
/*     */   
/*     */   public static int stringToBlendFactor(String $$0) {
/* 138 */     String $$1 = $$0.trim().toLowerCase(Locale.ROOT);
/* 139 */     $$1 = $$1.replaceAll("_", "");
/* 140 */     $$1 = $$1.replaceAll("one", "1");
/* 141 */     $$1 = $$1.replaceAll("zero", "0");
/* 142 */     $$1 = $$1.replaceAll("minus", "-");
/*     */     
/* 144 */     if ("0".equals($$1))
/* 145 */       return 0; 
/* 146 */     if ("1".equals($$1))
/* 147 */       return 1; 
/* 148 */     if ("srccolor".equals($$1))
/* 149 */       return 768; 
/* 150 */     if ("1-srccolor".equals($$1))
/* 151 */       return 769; 
/* 152 */     if ("dstcolor".equals($$1))
/* 153 */       return 774; 
/* 154 */     if ("1-dstcolor".equals($$1))
/* 155 */       return 775; 
/* 156 */     if ("srcalpha".equals($$1))
/* 157 */       return 770; 
/* 158 */     if ("1-srcalpha".equals($$1))
/* 159 */       return 771; 
/* 160 */     if ("dstalpha".equals($$1))
/* 161 */       return 772; 
/* 162 */     if ("1-dstalpha".equals($$1)) {
/* 163 */       return 773;
/*     */     }
/* 165 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\BlendMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */