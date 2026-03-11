/*    */ package net.minecraft.util;
/*    */ 
/*    */ public final class PngInfo extends Record {
/*    */   private final int width;
/*    */   private final int height;
/*    */   private static final long PNG_HEADER = -8552249625308161526L;
/*    */   private static final int IHDR_TYPE = 1229472850;
/*    */   private static final int IHDR_SIZE = 13;
/*    */   
/* 10 */   public PngInfo(int $$0, int $$1) { this.width = $$0; this.height = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/PngInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/util/PngInfo; } public int width() { return this.width; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/PngInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/PngInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/PngInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/PngInfo;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public int height() { return this.height; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PngInfo fromStream(InputStream $$0) throws IOException {
/* 16 */     DataInputStream $$1 = new DataInputStream($$0);
/* 17 */     if ($$1.readLong() != -8552249625308161526L) {
/* 18 */       throw new IOException("Bad PNG Signature");
/*    */     }
/*    */ 
/*    */     
/* 22 */     if ($$1.readInt() != 13) {
/* 23 */       throw new IOException("Bad length for IHDR chunk!");
/*    */     }
/*    */     
/* 26 */     if ($$1.readInt() != 1229472850) {
/* 27 */       throw new IOException("Bad type for IHDR chunk!");
/*    */     }
/*    */     
/* 30 */     int $$2 = $$1.readInt();
/* 31 */     int $$3 = $$1.readInt();
/*    */     
/* 33 */     return new PngInfo($$2, $$3);
/*    */   }
/*    */   
/*    */   public static PngInfo fromBytes(byte[] $$0) throws IOException {
/* 37 */     return fromStream(new ByteArrayInputStream($$0));
/*    */   }
/*    */   
/*    */   public static void validateHeader(ByteBuffer $$0) throws IOException {
/* 41 */     ByteOrder $$1 = $$0.order();
/* 42 */     $$0.order(ByteOrder.BIG_ENDIAN);
/* 43 */     if ($$0.getLong(0) != -8552249625308161526L) {
/* 44 */       throw new IOException("Bad PNG Signature");
/*    */     }
/*    */ 
/*    */     
/* 48 */     if ($$0.getInt(8) != 13) {
/* 49 */       throw new IOException("Bad length for IHDR chunk!");
/*    */     }
/*    */     
/* 52 */     if ($$0.getInt(12) != 1229472850) {
/* 53 */       throw new IOException("Bad type for IHDR chunk!");
/*    */     }
/* 55 */     $$0.order($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\PngInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */