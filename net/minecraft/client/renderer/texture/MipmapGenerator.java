/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import net.minecraft.Util;
/*     */ 
/*     */ public class MipmapGenerator {
/*     */   private static final int ALPHA_CUTOUT_CUTOFF = 96;
/*     */   private static final float[] POW22;
/*     */   
/*     */   static {
/*  11 */     POW22 = (float[])Util.make(new float[256], $$0 -> {
/*     */           for (int $$1 = 0; $$1 < $$0.length; $$1++)
/*     */             $$0[$$1] = (float)Math.pow(($$1 / 255.0F), 2.2D); 
/*     */         });
/*     */   }
/*     */   
/*     */   public static NativeImage[] generateMipLevels(NativeImage[] $$0, int $$1) {
/*  18 */     if ($$1 + 1 <= $$0.length) {
/*  19 */       return $$0;
/*     */     }
/*  21 */     NativeImage[] $$2 = new NativeImage[$$1 + 1];
/*  22 */     $$2[0] = $$0[0];
/*     */     
/*  24 */     boolean $$3 = hasTransparentPixel($$2[0]);
/*     */     
/*  26 */     for (int $$4 = 1; $$4 <= $$1; $$4++) {
/*  27 */       if ($$4 < $$0.length) {
/*  28 */         $$2[$$4] = $$0[$$4];
/*     */       } else {
/*  30 */         NativeImage $$5 = $$2[$$4 - 1];
/*  31 */         NativeImage $$6 = new NativeImage($$5.getWidth() >> 1, $$5.getHeight() >> 1, false);
/*     */         
/*  33 */         int $$7 = $$6.getWidth();
/*  34 */         int $$8 = $$6.getHeight();
/*     */         
/*  36 */         for (int $$9 = 0; $$9 < $$7; $$9++) {
/*  37 */           for (int $$10 = 0; $$10 < $$8; $$10++) {
/*  38 */             $$6.setPixelRGBA($$9, $$10, alphaBlend($$5
/*  39 */                   .getPixelRGBA($$9 * 2 + 0, $$10 * 2 + 0), $$5
/*  40 */                   .getPixelRGBA($$9 * 2 + 1, $$10 * 2 + 0), $$5
/*  41 */                   .getPixelRGBA($$9 * 2 + 0, $$10 * 2 + 1), $$5
/*  42 */                   .getPixelRGBA($$9 * 2 + 1, $$10 * 2 + 1), $$3));
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  48 */         $$2[$$4] = $$6;
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     return $$2;
/*     */   }
/*     */   
/*     */   private static boolean hasTransparentPixel(NativeImage $$0) {
/*  56 */     for (int $$1 = 0; $$1 < $$0.getWidth(); $$1++) {
/*  57 */       for (int $$2 = 0; $$2 < $$0.getHeight(); $$2++) {
/*  58 */         if ($$0.getPixelRGBA($$1, $$2) >> 24 == 0) {
/*  59 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   private static int alphaBlend(int $$0, int $$1, int $$2, int $$3, boolean $$4) {
/*  67 */     if ($$4) {
/*  68 */       float $$5 = 0.0F;
/*  69 */       float $$6 = 0.0F;
/*  70 */       float $$7 = 0.0F;
/*  71 */       float $$8 = 0.0F;
/*     */       
/*  73 */       if ($$0 >> 24 != 0) {
/*  74 */         $$5 += getPow22($$0 >> 24);
/*  75 */         $$6 += getPow22($$0 >> 16);
/*  76 */         $$7 += getPow22($$0 >> 8);
/*  77 */         $$8 += getPow22($$0 >> 0);
/*     */       } 
/*  79 */       if ($$1 >> 24 != 0) {
/*  80 */         $$5 += getPow22($$1 >> 24);
/*  81 */         $$6 += getPow22($$1 >> 16);
/*  82 */         $$7 += getPow22($$1 >> 8);
/*  83 */         $$8 += getPow22($$1 >> 0);
/*     */       } 
/*  85 */       if ($$2 >> 24 != 0) {
/*  86 */         $$5 += getPow22($$2 >> 24);
/*  87 */         $$6 += getPow22($$2 >> 16);
/*  88 */         $$7 += getPow22($$2 >> 8);
/*  89 */         $$8 += getPow22($$2 >> 0);
/*     */       } 
/*  91 */       if ($$3 >> 24 != 0) {
/*  92 */         $$5 += getPow22($$3 >> 24);
/*  93 */         $$6 += getPow22($$3 >> 16);
/*  94 */         $$7 += getPow22($$3 >> 8);
/*  95 */         $$8 += getPow22($$3 >> 0);
/*     */       } 
/*     */       
/*  98 */       $$5 /= 4.0F;
/*  99 */       $$6 /= 4.0F;
/* 100 */       $$7 /= 4.0F;
/* 101 */       $$8 /= 4.0F;
/*     */       
/* 103 */       int $$9 = (int)(Math.pow($$5, 0.45454545454545453D) * 255.0D);
/* 104 */       int $$10 = (int)(Math.pow($$6, 0.45454545454545453D) * 255.0D);
/* 105 */       int $$11 = (int)(Math.pow($$7, 0.45454545454545453D) * 255.0D);
/* 106 */       int $$12 = (int)(Math.pow($$8, 0.45454545454545453D) * 255.0D);
/*     */       
/* 108 */       if ($$9 < 96) {
/* 109 */         $$9 = 0;
/*     */       }
/* 111 */       return $$9 << 24 | $$10 << 16 | $$11 << 8 | $$12;
/*     */     } 
/* 113 */     int $$13 = gammaBlend($$0, $$1, $$2, $$3, 24);
/* 114 */     int $$14 = gammaBlend($$0, $$1, $$2, $$3, 16);
/* 115 */     int $$15 = gammaBlend($$0, $$1, $$2, $$3, 8);
/* 116 */     int $$16 = gammaBlend($$0, $$1, $$2, $$3, 0);
/*     */     
/* 118 */     return $$13 << 24 | $$14 << 16 | $$15 << 8 | $$16;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int gammaBlend(int $$0, int $$1, int $$2, int $$3, int $$4) {
/* 123 */     float $$5 = getPow22($$0 >> $$4);
/* 124 */     float $$6 = getPow22($$1 >> $$4);
/* 125 */     float $$7 = getPow22($$2 >> $$4);
/* 126 */     float $$8 = getPow22($$3 >> $$4);
/* 127 */     float $$9 = (float)(float)Math.pow(($$5 + $$6 + $$7 + $$8) * 0.25D, 0.45454545454545453D);
/*     */     
/* 129 */     return (int)($$9 * 255.0D);
/*     */   }
/*     */   
/*     */   private static float getPow22(int $$0) {
/* 133 */     return POW22[$$0 & 0xFF];
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\MipmapGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */