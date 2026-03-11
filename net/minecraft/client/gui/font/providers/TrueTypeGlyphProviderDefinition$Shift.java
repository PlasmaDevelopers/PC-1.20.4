/*    */ package net.minecraft.client.gui.font.providers;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import java.util.List;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Shift
/*    */   extends Record
/*    */ {
/*    */   final float x;
/*    */   final float y;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Shift(float $$0, float $$1) {
/* 30 */     this.x = $$0; this.y = $$1; } public float x() { return this.x; } public float y() { return this.y; }
/* 31 */    public static final Shift NONE = new Shift(0.0F, 0.0F); public static final Codec<Shift> CODEC;
/*    */   static {
/* 33 */     CODEC = Codec.FLOAT.listOf().comapFlatMap($$0 -> Util.fixedSize($$0, 2).map(()), $$0 -> List.of(Float.valueOf($$0.x), Float.valueOf($$0.y)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\TrueTypeGlyphProviderDefinition$Shift.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */