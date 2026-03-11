/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.dimension.DimensionType;
/*    */ 
/*    */ public interface VerticalAnchor
/*    */ {
/* 11 */   public static final Codec<VerticalAnchor> CODEC = ExtraCodecs.xor(Absolute.CODEC, 
/*    */       
/* 13 */       ExtraCodecs.xor(AboveBottom.CODEC, BelowTop.CODEC))
/*    */ 
/*    */ 
/*    */     
/* 17 */     .xmap(VerticalAnchor::merge, VerticalAnchor::split);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public static final VerticalAnchor BOTTOM = aboveBottom(0);
/* 23 */   public static final VerticalAnchor TOP = belowTop(0);
/*    */   
/*    */   static VerticalAnchor absolute(int $$0) {
/* 26 */     return new Absolute($$0);
/*    */   }
/*    */   
/*    */   static VerticalAnchor aboveBottom(int $$0) {
/* 30 */     return new AboveBottom($$0);
/*    */   }
/*    */   
/*    */   static VerticalAnchor belowTop(int $$0) {
/* 34 */     return new BelowTop($$0);
/*    */   }
/*    */   
/*    */   static VerticalAnchor bottom() {
/* 38 */     return BOTTOM;
/*    */   }
/*    */   
/*    */   static VerticalAnchor top() {
/* 42 */     return TOP;
/*    */   }
/*    */   
/*    */   private static VerticalAnchor merge(Either<Absolute, Either<AboveBottom, BelowTop>> $$0) {
/* 46 */     return (VerticalAnchor)$$0.map(Function.identity(), $$0 -> (Record)$$0.map(Function.identity(), Function.identity()));
/*    */   }
/*    */   
/*    */   private static Either<Absolute, Either<AboveBottom, BelowTop>> split(VerticalAnchor $$0) {
/* 50 */     if ($$0 instanceof Absolute) {
/* 51 */       return Either.left($$0);
/*    */     }
/* 53 */     return Either.right(($$0 instanceof AboveBottom) ? Either.left($$0) : Either.right($$0));
/*    */   }
/*    */   int resolveY(WorldGenerationContext paramWorldGenerationContext);
/*    */   public static final class Absolute extends Record implements VerticalAnchor { private final int y;
/*    */     
/* 58 */     public Absolute(int $$0) { this.y = $$0; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/VerticalAnchor$Absolute;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #58	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 58 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/VerticalAnchor$Absolute; } public int y() { return this.y; }
/*    */     public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/VerticalAnchor$Absolute;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #58	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/VerticalAnchor$Absolute;
/* 59 */       //   0	8	1	$$0	Ljava/lang/Object; } public static final Codec<Absolute> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("absolute").xmap(Absolute::new, Absolute::y).codec();
/*    */ 
/*    */     
/*    */     public int resolveY(WorldGenerationContext $$0) {
/* 63 */       return this.y;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 68 */       return "" + this.y + " absolute";
/*    */     } }
/*    */   public static final class AboveBottom extends Record implements VerticalAnchor { private final int offset;
/*    */     
/* 72 */     public AboveBottom(int $$0) { this.offset = $$0; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/VerticalAnchor$AboveBottom;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #72	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/VerticalAnchor$AboveBottom; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/VerticalAnchor$AboveBottom;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #72	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/VerticalAnchor$AboveBottom;
/* 72 */       //   0	8	1	$$0	Ljava/lang/Object; } public int offset() { return this.offset; }
/* 73 */      public static final Codec<AboveBottom> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("above_bottom").xmap(AboveBottom::new, AboveBottom::offset).codec();
/*    */ 
/*    */     
/*    */     public int resolveY(WorldGenerationContext $$0) {
/* 77 */       return $$0.getMinGenY() + this.offset;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 82 */       return "" + this.offset + " above bottom";
/*    */     } }
/*    */   public static final class BelowTop extends Record implements VerticalAnchor { private final int offset;
/*    */     
/* 86 */     public BelowTop(int $$0) { this.offset = $$0; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/VerticalAnchor$BelowTop;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #86	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/VerticalAnchor$BelowTop; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/VerticalAnchor$BelowTop;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #86	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/VerticalAnchor$BelowTop;
/* 86 */       //   0	8	1	$$0	Ljava/lang/Object; } public int offset() { return this.offset; }
/* 87 */      public static final Codec<BelowTop> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("below_top").xmap(BelowTop::new, BelowTop::offset).codec();
/*    */ 
/*    */     
/*    */     public int resolveY(WorldGenerationContext $$0) {
/* 91 */       return $$0.getGenDepth() - 1 + $$0.getMinGenY() - this.offset;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 96 */       return "" + this.offset + " below top";
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\VerticalAnchor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */