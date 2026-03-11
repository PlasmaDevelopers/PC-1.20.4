/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import org.lwjgl.glfw.GLFWVidMode;
/*     */ 
/*     */ 
/*     */ public final class VideoMode
/*     */ {
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int redBits;
/*     */   private final int greenBits;
/*     */   private final int blueBits;
/*     */   private final int refreshRate;
/*     */   
/*     */   public VideoMode(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/*  22 */     this.width = $$0;
/*  23 */     this.height = $$1;
/*  24 */     this.redBits = $$2;
/*  25 */     this.greenBits = $$3;
/*  26 */     this.blueBits = $$4;
/*  27 */     this.refreshRate = $$5;
/*     */   }
/*     */   
/*     */   public VideoMode(GLFWVidMode.Buffer $$0) {
/*  31 */     this.width = $$0.width();
/*  32 */     this.height = $$0.height();
/*  33 */     this.redBits = $$0.redBits();
/*  34 */     this.greenBits = $$0.greenBits();
/*  35 */     this.blueBits = $$0.blueBits();
/*  36 */     this.refreshRate = $$0.refreshRate();
/*     */   }
/*     */   
/*     */   public VideoMode(GLFWVidMode $$0) {
/*  40 */     this.width = $$0.width();
/*  41 */     this.height = $$0.height();
/*  42 */     this.redBits = $$0.redBits();
/*  43 */     this.greenBits = $$0.greenBits();
/*  44 */     this.blueBits = $$0.blueBits();
/*  45 */     this.refreshRate = $$0.refreshRate();
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  49 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  53 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getRedBits() {
/*  57 */     return this.redBits;
/*     */   }
/*     */   
/*     */   public int getGreenBits() {
/*  61 */     return this.greenBits;
/*     */   }
/*     */   
/*     */   public int getBlueBits() {
/*  65 */     return this.blueBits;
/*     */   }
/*     */   
/*     */   public int getRefreshRate() {
/*  69 */     return this.refreshRate;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  74 */     if (this == $$0) {
/*  75 */       return true;
/*     */     }
/*  77 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  78 */       return false;
/*     */     }
/*  80 */     VideoMode $$1 = (VideoMode)$$0;
/*  81 */     return (this.width == $$1.width && this.height == $$1.height && this.redBits == $$1.redBits && this.greenBits == $$1.greenBits && this.blueBits == $$1.blueBits && this.refreshRate == $$1.refreshRate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     return Objects.hash(new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height), Integer.valueOf(this.redBits), Integer.valueOf(this.greenBits), Integer.valueOf(this.blueBits), Integer.valueOf(this.refreshRate) });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  97 */     return String.format(Locale.ROOT, "%sx%s@%s (%sbit)", new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height), Integer.valueOf(this.refreshRate), Integer.valueOf(this.redBits + this.greenBits + this.blueBits) });
/*     */   }
/*     */   
/* 100 */   private static final Pattern PATTERN = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");
/*     */   
/*     */   public static Optional<VideoMode> read(@Nullable String $$0) {
/* 103 */     if ($$0 == null) {
/* 104 */       return Optional.empty();
/*     */     }
/*     */     
/*     */     try {
/* 108 */       Matcher $$1 = PATTERN.matcher($$0);
/* 109 */       if ($$1.matches()) {
/* 110 */         int $$6, $$9, $$2 = Integer.parseInt($$1.group(1));
/* 111 */         int $$3 = Integer.parseInt($$1.group(2));
/* 112 */         String $$4 = $$1.group(3);
/*     */         
/* 114 */         if ($$4 == null) {
/* 115 */           int $$5 = 60;
/*     */         } else {
/* 117 */           $$6 = Integer.parseInt($$4);
/*     */         } 
/* 119 */         String $$7 = $$1.group(4);
/*     */         
/* 121 */         if ($$7 == null) {
/* 122 */           int $$8 = 24;
/*     */         } else {
/* 124 */           $$9 = Integer.parseInt($$7);
/*     */         } 
/* 126 */         int $$10 = $$9 / 3;
/* 127 */         return Optional.of(new VideoMode($$2, $$3, $$10, $$10, $$10, $$6));
/*     */       } 
/* 129 */     } catch (Exception exception) {}
/*     */     
/* 131 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public String write() {
/* 135 */     return String.format(Locale.ROOT, "%sx%s@%s:%s", new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height), Integer.valueOf(this.refreshRate), Integer.valueOf(this.redBits + this.greenBits + this.blueBits) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\VideoMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */