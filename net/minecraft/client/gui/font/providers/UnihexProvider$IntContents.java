/*     */ package net.minecraft.client.gui.font.providers;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class IntContents
/*     */   extends Record
/*     */   implements UnihexProvider.LineData
/*     */ {
/*     */   private final int[] contents;
/*     */   private final int bitWidth;
/*     */   private static final int SIZE_24 = 24;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #290	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #290	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #290	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   private IntContents(int[] $$0, int $$1) {
/* 290 */     this.contents = $$0; this.bitWidth = $$1; } public int[] contents() { return this.contents; } public int bitWidth() { return this.bitWidth; }
/*     */ 
/*     */ 
/*     */   
/*     */   public int line(int $$0) {
/* 295 */     return this.contents[$$0];
/*     */   }
/*     */   
/*     */   static UnihexProvider.LineData read24(int $$0, ByteList $$1) {
/* 299 */     int[] $$2 = new int[16];
/* 300 */     int $$3 = 0;
/* 301 */     int $$4 = 0;
/* 302 */     for (int $$5 = 0; $$5 < 16; $$5++) {
/* 303 */       int $$6 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 304 */       int $$7 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 305 */       int $$8 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 306 */       int $$9 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 307 */       int $$10 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 308 */       int $$11 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 309 */       int $$12 = $$6 << 20 | $$7 << 16 | $$8 << 12 | $$9 << 8 | $$10 << 4 | $$11;
/* 310 */       $$2[$$5] = $$12 << 8;
/* 311 */       $$3 |= $$12;
/*     */     } 
/* 313 */     return new IntContents($$2, 24);
/*     */   }
/*     */   
/*     */   public static UnihexProvider.LineData read32(int $$0, ByteList $$1) {
/* 317 */     int[] $$2 = new int[16];
/* 318 */     int $$3 = 0;
/* 319 */     int $$4 = 0;
/* 320 */     for (int $$5 = 0; $$5 < 16; $$5++) {
/* 321 */       int $$6 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 322 */       int $$7 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 323 */       int $$8 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 324 */       int $$9 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 325 */       int $$10 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 326 */       int $$11 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 327 */       int $$12 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 328 */       int $$13 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 329 */       int $$14 = $$6 << 28 | $$7 << 24 | $$8 << 20 | $$9 << 16 | $$10 << 12 | $$11 << 8 | $$12 << 4 | $$13;
/* 330 */       $$2[$$5] = $$14;
/* 331 */       $$3 |= $$14;
/*     */     } 
/* 333 */     return new IntContents($$2, 32);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\UnihexProvider$IntContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */