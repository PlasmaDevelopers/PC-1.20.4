/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public interface BufferVertexConsumer
/*     */   extends VertexConsumer {
/*     */   VertexFormatElement currentElement();
/*     */   
/*     */   void nextElement();
/*     */   
/*     */   void putByte(int paramInt, byte paramByte);
/*     */   
/*     */   void putShort(int paramInt, short paramShort);
/*     */   
/*     */   void putFloat(int paramInt, float paramFloat);
/*     */   
/*     */   default VertexConsumer vertex(double $$0, double $$1, double $$2) {
/*  18 */     if (currentElement().getUsage() != VertexFormatElement.Usage.POSITION) {
/*  19 */       return this;
/*     */     }
/*  21 */     if (currentElement().getType() != VertexFormatElement.Type.FLOAT || currentElement().getCount() != 3) {
/*  22 */       throw new IllegalStateException();
/*     */     }
/*  24 */     putFloat(0, (float)$$0);
/*  25 */     putFloat(4, (float)$$1);
/*  26 */     putFloat(8, (float)$$2);
/*  27 */     nextElement();
/*  28 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   default VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/*  33 */     VertexFormatElement $$4 = currentElement();
/*  34 */     if ($$4.getUsage() != VertexFormatElement.Usage.COLOR) {
/*  35 */       return this;
/*     */     }
/*  37 */     if ($$4.getType() != VertexFormatElement.Type.UBYTE || $$4.getCount() != 4) {
/*  38 */       throw new IllegalStateException();
/*     */     }
/*  40 */     putByte(0, (byte)$$0);
/*  41 */     putByte(1, (byte)$$1);
/*  42 */     putByte(2, (byte)$$2);
/*  43 */     putByte(3, (byte)$$3);
/*  44 */     nextElement();
/*  45 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   default VertexConsumer uv(float $$0, float $$1) {
/*  50 */     VertexFormatElement $$2 = currentElement();
/*  51 */     if ($$2.getUsage() != VertexFormatElement.Usage.UV || $$2.getIndex() != 0) {
/*  52 */       return this;
/*     */     }
/*  54 */     if ($$2.getType() != VertexFormatElement.Type.FLOAT || $$2.getCount() != 2) {
/*  55 */       throw new IllegalStateException();
/*     */     }
/*  57 */     putFloat(0, $$0);
/*  58 */     putFloat(4, $$1);
/*  59 */     nextElement();
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   default VertexConsumer overlayCoords(int $$0, int $$1) {
/*  65 */     return uvShort((short)$$0, (short)$$1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   default VertexConsumer uv2(int $$0, int $$1) {
/*  70 */     return uvShort((short)$$0, (short)$$1, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   default VertexConsumer uvShort(short $$0, short $$1, int $$2) {
/*  75 */     VertexFormatElement $$3 = currentElement();
/*  76 */     if ($$3.getUsage() != VertexFormatElement.Usage.UV || $$3.getIndex() != $$2) {
/*  77 */       return this;
/*     */     }
/*  79 */     if ($$3.getType() != VertexFormatElement.Type.SHORT || $$3.getCount() != 2) {
/*  80 */       throw new IllegalStateException();
/*     */     }
/*  82 */     putShort(0, $$0);
/*  83 */     putShort(2, $$1);
/*  84 */     nextElement();
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   default VertexConsumer normal(float $$0, float $$1, float $$2) {
/*  90 */     VertexFormatElement $$3 = currentElement();
/*  91 */     if ($$3.getUsage() != VertexFormatElement.Usage.NORMAL) {
/*  92 */       return this;
/*     */     }
/*  94 */     if ($$3.getType() != VertexFormatElement.Type.BYTE || $$3.getCount() != 3) {
/*  95 */       throw new IllegalStateException();
/*     */     }
/*  97 */     putByte(0, normalIntValue($$0));
/*  98 */     putByte(1, normalIntValue($$1));
/*  99 */     putByte(2, normalIntValue($$2));
/* 100 */     nextElement();
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   static byte normalIntValue(float $$0) {
/* 105 */     return (byte)((int)(Mth.clamp($$0, -1.0F, 1.0F) * 127.0F) & 0xFF);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\BufferVertexConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */