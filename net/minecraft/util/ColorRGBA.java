/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public final class ColorRGBA extends Record {
/*    */   private final int rgba;
/*    */   private static final String CUSTOM_COLOR_PREFIX = "#";
/*    */   public static final Codec<ColorRGBA> CODEC;
/*    */   
/*  9 */   public ColorRGBA(int $$0) { this.rgba = $$0; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/ColorRGBA;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/util/ColorRGBA; } public int rgba() { return this.rgba; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/ColorRGBA;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/ColorRGBA;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   } static {
/* 12 */     CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*    */           if (!$$0.startsWith("#")) {
/*    */             return DataResult.error(());
/*    */           }
/*    */           
/*    */           try {
/*    */             int $$1 = (int)Long.parseLong($$0.substring(1), 16);
/*    */             return DataResult.success(new ColorRGBA($$1));
/* 20 */           } catch (NumberFormatException $$2) {
/*    */             return DataResult.error(());
/*    */           } 
/*    */         }ColorRGBA::formatValue);
/*    */   }
/*    */ 
/*    */   
/*    */   private String formatValue() {
/* 28 */     return String.format(Locale.ROOT, "#%08X", new Object[] { Integer.valueOf(this.rgba) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 33 */     return formatValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ColorRGBA.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */