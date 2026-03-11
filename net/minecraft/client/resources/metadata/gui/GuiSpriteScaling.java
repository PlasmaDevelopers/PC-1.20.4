/*     */ package net.minecraft.client.resources.metadata.gui;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ public interface GuiSpriteScaling {
/*  14 */   public static final Codec<GuiSpriteScaling> CODEC = Type.CODEC.dispatch(GuiSpriteScaling::type, Type::codec);
/*     */   
/*  16 */   public static final GuiSpriteScaling DEFAULT = new Stretch(); Type type(); public static final class Stretch extends Record implements GuiSpriteScaling { public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Stretch;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #20	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Stretch;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Stretch;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #20	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Stretch;
/*     */     } public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Stretch;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #20	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Stretch;
/*     */     }
/*  21 */     public static final Codec<Stretch> CODEC = Codec.unit(Stretch::new);
/*     */ 
/*     */     
/*     */     public GuiSpriteScaling.Type type() {
/*  25 */       return GuiSpriteScaling.Type.STRETCH;
/*     */     } }
/*     */   public static final class Tile extends Record implements GuiSpriteScaling { private final int width; private final int height; public static final Codec<Tile> CODEC;
/*     */     
/*  29 */     public Tile(int $$0, int $$1) { this.width = $$0; this.height = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Tile;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Tile; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Tile;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Tile; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Tile;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$Tile;
/*  29 */       //   0	8	1	$$0	Ljava/lang/Object; } public int width() { return this.width; } public int height() { return this.height; } static {
/*  30 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.POSITIVE_INT.fieldOf("width").forGetter(Tile::width), (App)ExtraCodecs.POSITIVE_INT.fieldOf("height").forGetter(Tile::height)).apply((Applicative)$$0, Tile::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GuiSpriteScaling.Type type() {
/*  37 */       return GuiSpriteScaling.Type.TILE;
/*     */     } }
/*     */   public static final class NineSlice extends Record implements GuiSpriteScaling { private final int width; private final int height; private final Border border; public static final Codec<NineSlice> CODEC;
/*     */     
/*  41 */     public NineSlice(int $$0, int $$1, Border $$2) { this.width = $$0; this.height = $$1; this.border = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice;
/*  41 */       //   0	8	1	$$0	Ljava/lang/Object; } public int width() { return this.width; } public int height() { return this.height; } public Border border() { return this.border; } static {
/*  42 */       CODEC = ExtraCodecs.validate(RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.POSITIVE_INT.fieldOf("width").forGetter(NineSlice::width), (App)ExtraCodecs.POSITIVE_INT.fieldOf("height").forGetter(NineSlice::height), (App)Border.CODEC.fieldOf("border").forGetter(NineSlice::border)).apply((Applicative)$$0, NineSlice::new)), NineSlice::validate);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static DataResult<NineSlice> validate(NineSlice $$0) {
/*  49 */       Border $$1 = $$0.border();
/*  50 */       if ($$1.left() + $$1.right() >= $$0.width()) {
/*  51 */         return DataResult.error(() -> "Nine-sliced texture has no horizontal center slice: " + $$0.left() + " + " + $$0.right() + " >= " + $$1.width());
/*     */       }
/*  53 */       if ($$1.top() + $$1.bottom() >= $$0.height()) {
/*  54 */         return DataResult.error(() -> "Nine-sliced texture has no vertical center slice: " + $$0.top() + " + " + $$0.bottom() + " >= " + $$1.height());
/*     */       }
/*  56 */       return DataResult.success($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public GuiSpriteScaling.Type type() {
/*  61 */       return GuiSpriteScaling.Type.NINE_SLICE;
/*     */     }
/*     */     public static final class Border extends Record { private final int left; private final int top; private final int right; private final int bottom; private static final Codec<Border> VALUE_CODEC; private static final Codec<Border> RECORD_CODEC; static final Codec<Border> CODEC;
/*  64 */       public Border(int $$0, int $$1, int $$2, int $$3) { this.left = $$0; this.top = $$1; this.right = $$2; this.bottom = $$3; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #64	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #64	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border; } public final boolean equals(Object $$0) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #64	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;
/*  64 */         //   0	8	1	$$0	Ljava/lang/Object; } public int left() { return this.left; } public int top() { return this.top; } public int right() { return this.right; } public int bottom() { return this.bottom; } static {
/*  65 */         VALUE_CODEC = ExtraCodecs.POSITIVE_INT.flatComapMap($$0 -> new Border($$0.intValue(), $$0.intValue(), $$0.intValue(), $$0.intValue()), $$0 -> {
/*     */               OptionalInt $$1 = $$0.unpackValue();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               return $$1.isPresent() ? DataResult.success(Integer.valueOf($$1.getAsInt())) : DataResult.error(());
/*     */             });
/*     */ 
/*     */ 
/*     */         
/*  76 */         RECORD_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("left").forGetter(Border::left), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("top").forGetter(Border::top), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("right").forGetter(Border::right), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("bottom").forGetter(Border::bottom)).apply((Applicative)$$0, Border::new));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  84 */         CODEC = Codec.either(VALUE_CODEC, RECORD_CODEC).xmap($$0 -> (Border)$$0.map(Function.identity(), Function.identity()), $$0 -> $$0.unpackValue().isPresent() ? Either.left($$0) : Either.right($$0));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private OptionalInt unpackValue()
/*     */       {
/*  93 */         if (left() == top() && top() == right() && right() == bottom()) {
/*  94 */           return OptionalInt.of(left());
/*     */         }
/*  96 */         return OptionalInt.empty(); } } } public static final class Border extends Record { private final int left; private final int top; private final int right; private final int bottom; private static final Codec<Border> VALUE_CODEC; private static final Codec<Border> RECORD_CODEC; static final Codec<Border> CODEC; public Border(int $$0, int $$1, int $$2, int $$3) { this.left = $$0; this.top = $$1; this.right = $$2; this.bottom = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #64	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #64	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #64	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling$NineSlice$Border;
/*  96 */       //   0	8	1	$$0	Ljava/lang/Object; } public int left() { return this.left; } private OptionalInt unpackValue() { if (left() == top() && top() == right() && right() == bottom()) return OptionalInt.of(left());  return OptionalInt.empty(); }
/*     */     public int top() { return this.top; } public int right() { return this.right; } public int bottom() { return this.bottom; } static { VALUE_CODEC = ExtraCodecs.POSITIVE_INT.flatComapMap($$0 -> new Border($$0.intValue(), $$0.intValue(), $$0.intValue(), $$0.intValue()), $$0 -> {
/*     */             OptionalInt $$1 = $$0.unpackValue(); return $$1.isPresent() ? DataResult.success(Integer.valueOf($$1.getAsInt())) : DataResult.error(());
/*     */           }); RECORD_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("left").forGetter(Border::left), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("top").forGetter(Border::top), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("right").forGetter(Border::right), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("bottom").forGetter(Border::bottom)).apply((Applicative)$$0, Border::new)); CODEC = Codec.either(VALUE_CODEC, RECORD_CODEC).xmap($$0 -> (Border)$$0.map(Function.identity(), Function.identity()), $$0 -> $$0.unpackValue().isPresent() ? Either.left($$0) : Either.right($$0)); } }
/*     */    public enum Type implements StringRepresentable
/*     */   {
/* 102 */     STRETCH("stretch", GuiSpriteScaling.Stretch.CODEC),
/* 103 */     TILE("tile", GuiSpriteScaling.Tile.CODEC),
/* 104 */     NINE_SLICE("nine_slice", GuiSpriteScaling.NineSlice.CODEC);
/*     */ 
/*     */     
/* 107 */     public static final Codec<Type> CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values); private final String key; private final Codec<? extends GuiSpriteScaling> codec;
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     Type(String $$0, Codec<? extends GuiSpriteScaling> $$1) {
/* 113 */       this.key = $$0;
/* 114 */       this.codec = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 119 */       return this.key;
/*     */     }
/*     */     
/*     */     public Codec<? extends GuiSpriteScaling> codec() {
/* 123 */       return this.codec;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\gui\GuiSpriteScaling.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */