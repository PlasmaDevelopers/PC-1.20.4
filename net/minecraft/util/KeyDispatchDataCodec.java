/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public final class KeyDispatchDataCodec<A> extends Record {
/*    */   private final Codec<A> codec;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/KeyDispatchDataCodec;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/KeyDispatchDataCodec;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/util/KeyDispatchDataCodec<TA;>;
/*    */   }
/*    */   
/* 11 */   public KeyDispatchDataCodec(Codec<A> $$0) { this.codec = $$0; } public Codec<A> codec() { return this.codec; }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/KeyDispatchDataCodec;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/KeyDispatchDataCodec;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/util/KeyDispatchDataCodec<TA;>;
/*    */   }
/*    */   @Deprecated
/*    */   public static <A> KeyDispatchDataCodec<A> of(Codec<A> $$0) {
/* 18 */     return new KeyDispatchDataCodec<>($$0);
/*    */   } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/KeyDispatchDataCodec;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/KeyDispatchDataCodec;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/util/KeyDispatchDataCodec<TA;>;
/*    */   } public static <A> KeyDispatchDataCodec<A> of(MapCodec<A> $$0) {
/* 22 */     return new KeyDispatchDataCodec<>($$0.codec());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\KeyDispatchDataCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */