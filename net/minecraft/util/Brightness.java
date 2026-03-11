/*    */ package net.minecraft.util;
/*    */ public final class Brightness extends Record {
/*    */   private final int block;
/*    */   private final int sky;
/*    */   
/*  6 */   public Brightness(int $$0, int $$1) { this.block = $$0; this.sky = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/Brightness;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/util/Brightness; } public int block() { return this.block; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/Brightness;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/Brightness; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/Brightness;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/Brightness;
/*  6 */     //   0	8	1	$$0	Ljava/lang/Object; } public int sky() { return this.sky; }
/*  7 */    public static final Codec<Integer> LIGHT_VALUE_CODEC = ExtraCodecs.intRange(0, 15); public static final Codec<Brightness> CODEC;
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LIGHT_VALUE_CODEC.fieldOf("block").forGetter(Brightness::block), (App)LIGHT_VALUE_CODEC.fieldOf("sky").forGetter(Brightness::sky)).apply((Applicative)$$0, Brightness::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public static Brightness FULL_BRIGHT = new Brightness(15, 15);
/*    */   
/*    */   public int pack() {
/* 17 */     return this.block << 4 | this.sky << 20;
/*    */   }
/*    */   
/*    */   public static Brightness unpack(int $$0) {
/* 21 */     int $$1 = $$0 >> 4 & 0xFFFF;
/* 22 */     int $$2 = $$0 >> 20 & 0xFFFF;
/* 23 */     return new Brightness($$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\Brightness.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */