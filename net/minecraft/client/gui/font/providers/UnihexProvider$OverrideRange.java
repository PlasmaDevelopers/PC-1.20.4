/*    */ package net.minecraft.client.gui.font.providers;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class OverrideRange
/*    */   extends Record
/*    */ {
/*    */   final int from;
/*    */   final int to;
/*    */   final UnihexProvider.Dimensions dimensions;
/*    */   private static final Codec<OverrideRange> RAW_CODEC;
/*    */   public static final Codec<OverrideRange> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   private OverrideRange(int $$0, int $$1, UnihexProvider.Dimensions $$2) {
/* 67 */     this.from = $$0; this.to = $$1; this.dimensions = $$2; } public int from() { return this.from; } public int to() { return this.to; } public UnihexProvider.Dimensions dimensions() { return this.dimensions; } static {
/* 68 */     RAW_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.CODEPOINT.fieldOf("from").forGetter(OverrideRange::from), (App)ExtraCodecs.CODEPOINT.fieldOf("to").forGetter(OverrideRange::to), (App)UnihexProvider.Dimensions.MAP_CODEC.forGetter(OverrideRange::dimensions)).apply((Applicative)$$0, OverrideRange::new));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 74 */     CODEC = ExtraCodecs.validate(RAW_CODEC, $$0 -> ($$0.from >= $$0.to) ? DataResult.error(()) : DataResult.success($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\UnihexProvider$OverrideRange.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */