/*    */ package net.minecraft.client.resources.metadata.gui;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.OptionalInt;
/*    */ import java.util.function.Function;
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
/*    */ public final class Border
/*    */   extends Record
/*    */ {
/*    */   private final int left;
/*    */   private final int top;
/*    */   private final int right;
/*    */   private final int bottom;
/*    */   private static final Codec<Border> VALUE_CODEC;
/*    */   private static final Codec<Border> RECORD_CODEC;
/*    */   static final Codec<Border> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #64	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Border(int $$0, int $$1, int $$2, int $$3) {
/* 64 */     this.left = $$0; this.top = $$1; this.right = $$2; this.bottom = $$3; } public int left() { return this.left; } public int top() { return this.top; } public int right() { return this.right; } public int bottom() { return this.bottom; } static {
/* 65 */     VALUE_CODEC = ExtraCodecs.POSITIVE_INT.flatComapMap($$0 -> new Border($$0.intValue(), $$0.intValue(), $$0.intValue(), $$0.intValue()), $$0 -> {
/*    */           OptionalInt $$1 = $$0.unpackValue();
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           return $$1.isPresent() ? DataResult.success(Integer.valueOf($$1.getAsInt())) : DataResult.error(());
/*    */         });
/*    */ 
/*    */ 
/*    */     
/* 76 */     RECORD_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("left").forGetter(Border::left), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("top").forGetter(Border::top), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("right").forGetter(Border::right), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("bottom").forGetter(Border::bottom)).apply((Applicative)$$0, Border::new));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     CODEC = Codec.either(VALUE_CODEC, RECORD_CODEC).xmap($$0 -> (Border)$$0.map(Function.identity(), Function.identity()), $$0 -> $$0.unpackValue().isPresent() ? Either.left($$0) : Either.right($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private OptionalInt unpackValue() {
/* 93 */     if (left() == top() && top() == right() && right() == bottom()) {
/* 94 */       return OptionalInt.of(left());
/*    */     }
/* 96 */     return OptionalInt.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\gui\GuiSpriteScaling$NineSlice$Border.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */