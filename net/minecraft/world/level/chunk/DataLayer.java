/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataLayer
/*     */ {
/*     */   public static final int LAYER_COUNT = 16;
/*     */   public static final int LAYER_SIZE = 128;
/*     */   public static final int SIZE = 2048;
/*     */   private static final int NIBBLE_SIZE = 4;
/*     */   @Nullable
/*     */   protected byte[] data;
/*     */   private int defaultValue;
/*     */   
/*     */   public DataLayer() {
/*  22 */     this(0);
/*     */   }
/*     */   
/*     */   public DataLayer(int $$0) {
/*  26 */     this.defaultValue = $$0;
/*     */   }
/*     */   
/*     */   public DataLayer(byte[] $$0) {
/*  30 */     this.data = $$0;
/*  31 */     this.defaultValue = 0;
/*     */     
/*  33 */     if ($$0.length != 2048) {
/*  34 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("DataLayer should be 2048 bytes not: " + $$0.length));
/*     */     }
/*     */   }
/*     */   
/*     */   public int get(int $$0, int $$1, int $$2) {
/*  39 */     return get(getIndex($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public void set(int $$0, int $$1, int $$2, int $$3) {
/*  43 */     set(getIndex($$0, $$1, $$2), $$3);
/*     */   }
/*     */   
/*     */   private static int getIndex(int $$0, int $$1, int $$2) {
/*  47 */     return $$1 << 8 | $$2 << 4 | $$0;
/*     */   }
/*     */   
/*     */   private int get(int $$0) {
/*  51 */     if (this.data == null) {
/*  52 */       return this.defaultValue;
/*     */     }
/*  54 */     int $$1 = getByteIndex($$0);
/*  55 */     int $$2 = getNibbleIndex($$0);
/*  56 */     return this.data[$$1] >> 4 * $$2 & 0xF;
/*     */   }
/*     */   
/*     */   private void set(int $$0, int $$1) {
/*  60 */     byte[] $$2 = getData();
/*  61 */     int $$3 = getByteIndex($$0);
/*  62 */     int $$4 = getNibbleIndex($$0);
/*     */     
/*  64 */     int $$5 = 15 << 4 * $$4 ^ 0xFFFFFFFF;
/*  65 */     int $$6 = ($$1 & 0xF) << 4 * $$4;
/*  66 */     $$2[$$3] = (byte)($$2[$$3] & $$5 | $$6);
/*     */   }
/*     */   
/*     */   private static int getNibbleIndex(int $$0) {
/*  70 */     return $$0 & 0x1;
/*     */   }
/*     */   
/*     */   private static int getByteIndex(int $$0) {
/*  74 */     return $$0 >> 1;
/*     */   }
/*     */   
/*     */   public void fill(int $$0) {
/*  78 */     this.defaultValue = $$0;
/*  79 */     this.data = null;
/*     */   }
/*     */   
/*     */   private static byte packFilled(int $$0) {
/*  83 */     byte $$1 = (byte)$$0;
/*  84 */     for (int $$2 = 4; $$2 < 8; $$2 += 4) {
/*  85 */       $$1 = (byte)($$1 | $$0 << $$2);
/*     */     }
/*  87 */     return $$1;
/*     */   }
/*     */   
/*     */   public byte[] getData() {
/*  91 */     if (this.data == null) {
/*  92 */       this.data = new byte[2048];
/*  93 */       if (this.defaultValue != 0) {
/*  94 */         Arrays.fill(this.data, packFilled(this.defaultValue));
/*     */       }
/*     */     } 
/*  97 */     return this.data;
/*     */   }
/*     */   
/*     */   public DataLayer copy() {
/* 101 */     if (this.data == null) {
/* 102 */       return new DataLayer(this.defaultValue);
/*     */     }
/* 104 */     return new DataLayer((byte[])this.data.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 109 */     StringBuilder $$0 = new StringBuilder();
/* 110 */     for (int $$1 = 0; $$1 < 4096; $$1++) {
/* 111 */       $$0.append(Integer.toHexString(get($$1)));
/* 112 */       if (($$1 & 0xF) == 15) {
/* 113 */         $$0.append("\n");
/*     */       }
/* 115 */       if (($$1 & 0xFF) == 255) {
/* 116 */         $$0.append("\n");
/*     */       }
/*     */     } 
/* 119 */     return $$0.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForDebug
/*     */   public String layerToString(int $$0) {
/* 125 */     StringBuilder $$1 = new StringBuilder();
/* 126 */     for (int $$2 = 0; $$2 < 256; $$2++) {
/* 127 */       $$1.append(Integer.toHexString(get($$2)));
/* 128 */       if (($$2 & 0xF) == 15) {
/* 129 */         $$1.append("\n");
/*     */       }
/*     */     } 
/* 132 */     return $$1.toString();
/*     */   }
/*     */   
/*     */   public boolean isDefinitelyHomogenous() {
/* 136 */     return (this.data == null);
/*     */   }
/*     */   
/*     */   public boolean isDefinitelyFilledWith(int $$0) {
/* 140 */     return (this.data == null && this.defaultValue == $$0);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 144 */     return (this.data == null && this.defaultValue == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\DataLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */