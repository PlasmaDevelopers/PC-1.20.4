/*     */ package net.minecraft.world.item.crafting;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ 
/*     */ public final class ShapedRecipePattern extends Record {
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final NonNullList<Ingredient> ingredients;
/*     */   private final Optional<Data> data;
/*     */   private static final int MAX_SIZE = 3;
/*     */   public static final MapCodec<ShapedRecipePattern> MAP_CODEC;
/*     */   
/*  21 */   public ShapedRecipePattern(int $$0, int $$1, NonNullList<Ingredient> $$2, Optional<Data> $$3) { this.width = $$0; this.height = $$1; this.ingredients = $$2; this.data = $$3; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/crafting/ShapedRecipePattern;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #21	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  21 */     //   0	7	0	this	Lnet/minecraft/world/item/crafting/ShapedRecipePattern; } public int width() { return this.width; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/crafting/ShapedRecipePattern;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #21	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/item/crafting/ShapedRecipePattern; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/crafting/ShapedRecipePattern;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #21	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/item/crafting/ShapedRecipePattern;
/*  21 */     //   0	8	1	$$0	Ljava/lang/Object; } public int height() { return this.height; } public NonNullList<Ingredient> ingredients() { return this.ingredients; } public Optional<Data> data() { return this.data; }
/*     */   
/*     */   static {
/*  24 */     MAP_CODEC = Data.MAP_CODEC.flatXmap(ShapedRecipePattern::unpack, $$0 -> (DataResult)$$0.data().<DataResult>map(DataResult::success).orElseGet(()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShapedRecipePattern of(Map<Character, Ingredient> $$0, String... $$1) {
/*  30 */     return of($$0, List.of($$1));
/*     */   }
/*     */   
/*     */   public static ShapedRecipePattern of(Map<Character, Ingredient> $$0, List<String> $$1) {
/*  34 */     Data $$2 = new Data($$0, $$1);
/*  35 */     return (ShapedRecipePattern)Util.getOrThrow(unpack($$2), IllegalArgumentException::new);
/*     */   }
/*     */   
/*     */   private static DataResult<ShapedRecipePattern> unpack(Data $$0) {
/*  39 */     String[] $$1 = shrink($$0.pattern);
/*  40 */     int $$2 = $$1[0].length();
/*  41 */     int $$3 = $$1.length;
/*  42 */     NonNullList<Ingredient> $$4 = NonNullList.withSize($$2 * $$3, Ingredient.EMPTY);
/*  43 */     CharArraySet charArraySet = new CharArraySet($$0.key.keySet());
/*     */     
/*  45 */     for (int $$6 = 0; $$6 < $$1.length; $$6++) {
/*  46 */       String $$7 = $$1[$$6];
/*  47 */       for (int $$8 = 0; $$8 < $$7.length(); $$8++) {
/*  48 */         char $$9 = $$7.charAt($$8);
/*  49 */         Ingredient $$10 = ($$9 == ' ') ? Ingredient.EMPTY : $$0.key.get(Character.valueOf($$9));
/*  50 */         if ($$10 == null) {
/*  51 */           return DataResult.error(() -> "Pattern references symbol '" + $$0 + "' but it's not defined in the key");
/*     */         }
/*  53 */         charArraySet.remove($$9);
/*  54 */         $$4.set($$8 + $$2 * $$6, $$10);
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     if (!charArraySet.isEmpty()) {
/*  59 */       return DataResult.error(() -> "Key defines symbols that aren't used in pattern: " + $$0);
/*     */     }
/*     */     
/*  62 */     return DataResult.success(new ShapedRecipePattern($$2, $$3, $$4, Optional.of($$0)));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static String[] shrink(List<String> $$0) {
/*  67 */     int $$1 = Integer.MAX_VALUE;
/*  68 */     int $$2 = 0;
/*  69 */     int $$3 = 0;
/*  70 */     int $$4 = 0;
/*     */     
/*  72 */     for (int $$5 = 0; $$5 < $$0.size(); $$5++) {
/*  73 */       String $$6 = $$0.get($$5);
/*     */       
/*  75 */       $$1 = Math.min($$1, firstNonSpace($$6));
/*  76 */       int $$7 = lastNonSpace($$6);
/*  77 */       $$2 = Math.max($$2, $$7);
/*     */ 
/*     */       
/*  80 */       if ($$7 < 0) {
/*  81 */         if ($$3 == $$5) {
/*  82 */           $$3++;
/*     */         }
/*  84 */         $$4++;
/*     */       } else {
/*  86 */         $$4 = 0;
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     if ($$0.size() == $$4) {
/*  91 */       return new String[0];
/*     */     }
/*     */     
/*  94 */     String[] $$8 = new String[$$0.size() - $$4 - $$3];
/*  95 */     for (int $$9 = 0; $$9 < $$8.length; $$9++) {
/*  96 */       $$8[$$9] = ((String)$$0.get($$9 + $$3)).substring($$1, $$2 + 1);
/*     */     }
/*     */     
/*  99 */     return $$8;
/*     */   }
/*     */   
/*     */   private static int firstNonSpace(String $$0) {
/* 103 */     int $$1 = 0;
/* 104 */     while ($$1 < $$0.length() && $$0.charAt($$1) == ' ') {
/* 105 */       $$1++;
/*     */     }
/* 107 */     return $$1;
/*     */   }
/*     */   
/*     */   private static int lastNonSpace(String $$0) {
/* 111 */     int $$1 = $$0.length() - 1;
/* 112 */     while ($$1 >= 0 && $$0.charAt($$1) == ' ') {
/* 113 */       $$1--;
/*     */     }
/* 115 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean matches(CraftingContainer $$0) {
/* 119 */     for (int $$1 = 0; $$1 <= $$0.getWidth() - this.width; $$1++) {
/* 120 */       for (int $$2 = 0; $$2 <= $$0.getHeight() - this.height; $$2++) {
/* 121 */         if (matches($$0, $$1, $$2, true)) {
/* 122 */           return true;
/*     */         }
/* 124 */         if (matches($$0, $$1, $$2, false)) {
/* 125 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   private boolean matches(CraftingContainer $$0, int $$1, int $$2, boolean $$3) {
/* 133 */     for (int $$4 = 0; $$4 < $$0.getWidth(); $$4++) {
/* 134 */       for (int $$5 = 0; $$5 < $$0.getHeight(); $$5++) {
/* 135 */         int $$6 = $$4 - $$1;
/* 136 */         int $$7 = $$5 - $$2;
/* 137 */         Ingredient $$8 = Ingredient.EMPTY;
/* 138 */         if ($$6 >= 0 && $$7 >= 0 && $$6 < this.width && $$7 < this.height) {
/* 139 */           if ($$3) {
/* 140 */             $$8 = (Ingredient)this.ingredients.get(this.width - $$6 - 1 + $$7 * this.width);
/*     */           } else {
/* 142 */             $$8 = (Ingredient)this.ingredients.get($$6 + $$7 * this.width);
/*     */           } 
/*     */         }
/* 145 */         if (!$$8.test($$0.getItem($$4 + $$5 * $$0.getWidth()))) {
/* 146 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   public void toNetwork(FriendlyByteBuf $$0) {
/* 154 */     $$0.writeVarInt(this.width);
/* 155 */     $$0.writeVarInt(this.height);
/* 156 */     for (Ingredient $$1 : this.ingredients) {
/* 157 */       $$1.toNetwork($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public static ShapedRecipePattern fromNetwork(FriendlyByteBuf $$0) {
/* 162 */     int $$1 = $$0.readVarInt();
/* 163 */     int $$2 = $$0.readVarInt();
/* 164 */     NonNullList<Ingredient> $$3 = NonNullList.withSize($$1 * $$2, Ingredient.EMPTY);
/* 165 */     $$3.replaceAll($$1 -> Ingredient.fromNetwork($$0));
/* 166 */     return new ShapedRecipePattern($$1, $$2, $$3, Optional.empty());
/*     */   }
/*     */   public static final class Data extends Record { final Map<Character, Ingredient> key; final List<String> pattern; private static final Codec<List<String>> PATTERN_CODEC; private static final Codec<Character> SYMBOL_CODEC; public static final MapCodec<Data> MAP_CODEC;
/* 169 */     public Data(Map<Character, Ingredient> $$0, List<String> $$1) { this.key = $$0; this.pattern = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/crafting/ShapedRecipePattern$Data;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #169	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/ShapedRecipePattern$Data; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/crafting/ShapedRecipePattern$Data;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #169	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/ShapedRecipePattern$Data; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/crafting/ShapedRecipePattern$Data;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #169	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/item/crafting/ShapedRecipePattern$Data;
/* 169 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<Character, Ingredient> key() { return this.key; } public List<String> pattern() { return this.pattern; } static {
/* 170 */       PATTERN_CODEC = Codec.STRING.listOf().comapFlatMap($$0 -> {
/*     */             if ($$0.size() > 3) {
/*     */               return DataResult.error(());
/*     */             }
/*     */             if ($$0.isEmpty()) {
/*     */               return DataResult.error(());
/*     */             }
/*     */             int $$1 = ((String)$$0.get(0)).length();
/*     */             for (String $$2 : $$0) {
/*     */               if ($$2.length() > 3)
/*     */                 return DataResult.error(()); 
/*     */               if ($$1 != $$2.length())
/*     */                 return DataResult.error(()); 
/*     */             } 
/*     */             return DataResult.success($$0);
/* 185 */           }Function.identity());
/*     */       
/* 187 */       SYMBOL_CODEC = Codec.STRING.comapFlatMap($$0 -> ($$0.length() != 1) ? DataResult.error(()) : (" ".equals($$0) ? DataResult.error(()) : DataResult.success(Character.valueOf($$0.charAt(0)))), String::valueOf);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictUnboundedMap(SYMBOL_CODEC, Ingredient.CODEC_NONEMPTY).fieldOf("key").forGetter(()), (App)PATTERN_CODEC.fieldOf("pattern").forGetter(())).apply((Applicative)$$0, Data::new));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\ShapedRecipePattern.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */