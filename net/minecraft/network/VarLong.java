/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ 
/*    */ public class VarLong {
/*    */   private static final int MAX_VARLONG_SIZE = 10;
/*    */   private static final int DATA_BITS_MASK = 127;
/*    */   private static final int CONTINUATION_BIT_MASK = 128;
/*    */   private static final int DATA_BITS_PER_BYTE = 7;
/*    */   
/*    */   public static int getByteSize(long $$0) {
/* 12 */     for (int $$1 = 1; $$1 < 10; $$1++) {
/* 13 */       if (($$0 & -1L << $$1 * 7) == 0L) {
/* 14 */         return $$1;
/*    */       }
/*    */     } 
/* 17 */     return 10;
/*    */   }
/*    */   
/*    */   public static boolean hasContinuationBit(byte $$0) {
/* 21 */     return (($$0 & 0x80) == 128);
/*    */   }
/*    */   public static long read(ByteBuf $$0) {
/*    */     byte $$3;
/* 25 */     long $$1 = 0L;
/* 26 */     int $$2 = 0;
/*    */     do {
/* 28 */       $$3 = $$0.readByte();
/*    */       
/* 30 */       $$1 |= ($$3 & Byte.MAX_VALUE) << $$2++ * 7;
/*    */       
/* 32 */       if ($$2 > 10) {
/* 33 */         throw new RuntimeException("VarLong too big");
/*    */       }
/*    */     }
/* 36 */     while (hasContinuationBit($$3));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     return $$1;
/*    */   }
/*    */   
/*    */   public static ByteBuf write(ByteBuf $$0, long $$1) {
/*    */     while (true) {
/* 46 */       if (($$1 & 0xFFFFFFFFFFFFFF80L) == 0L) {
/* 47 */         $$0.writeByte((int)$$1);
/* 48 */         return $$0;
/*    */       } 
/*    */       
/* 51 */       $$0.writeByte((int)($$1 & 0x7FL) | 0x80);
/* 52 */       $$1 >>>= 7L;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\VarLong.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */