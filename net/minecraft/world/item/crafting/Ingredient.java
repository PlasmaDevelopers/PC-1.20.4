/*     */ package net.minecraft.world.item.crafting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntComparators;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public final class Ingredient implements Predicate<ItemStack> {
/*  31 */   public static final Ingredient EMPTY = new Ingredient(Stream.empty());
/*     */   
/*     */   private final Value[] values;
/*     */   @Nullable
/*     */   private ItemStack[] itemStacks;
/*     */   @Nullable
/*     */   private IntList stackingIds;
/*     */   
/*     */   private Ingredient(Stream<? extends Value> $$0) {
/*  40 */     this.values = $$0.<Value>toArray($$0 -> new Value[$$0]);
/*     */   }
/*     */   
/*     */   private Ingredient(Value[] $$0) {
/*  44 */     this.values = $$0;
/*     */   }
/*     */   
/*     */   public ItemStack[] getItems() {
/*  48 */     if (this.itemStacks == null) {
/*  49 */       this.itemStacks = (ItemStack[])Arrays.<Value>stream(this.values).flatMap($$0 -> $$0.getItems().stream()).distinct().toArray($$0 -> new ItemStack[$$0]);
/*     */     }
/*  51 */     return this.itemStacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(@Nullable ItemStack $$0) {
/*  56 */     if ($$0 == null) {
/*  57 */       return false;
/*     */     }
/*     */     
/*  60 */     if (isEmpty()) {
/*  61 */       return $$0.isEmpty();
/*     */     }
/*     */     
/*  64 */     for (ItemStack $$1 : getItems()) {
/*  65 */       if ($$1.is($$0.getItem())) {
/*  66 */         return true;
/*     */       }
/*     */     } 
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   public IntList getStackingIds() {
/*  73 */     if (this.stackingIds == null) {
/*  74 */       ItemStack[] $$0 = getItems();
/*  75 */       this.stackingIds = (IntList)new IntArrayList($$0.length);
/*  76 */       for (ItemStack $$1 : $$0) {
/*  77 */         this.stackingIds.add(StackedContents.getStackingIndex($$1));
/*     */       }
/*  79 */       this.stackingIds.sort(IntComparators.NATURAL_COMPARATOR);
/*     */     } 
/*     */     
/*  82 */     return this.stackingIds;
/*     */   }
/*     */   
/*     */   public void toNetwork(FriendlyByteBuf $$0) {
/*  86 */     $$0.writeCollection(Arrays.asList(getItems()), FriendlyByteBuf::writeItem);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  90 */     return (this.values.length == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  95 */     if ($$0 instanceof Ingredient) { Ingredient $$1 = (Ingredient)$$0;
/*  96 */       return Arrays.equals((Object[])this.values, (Object[])$$1.values); }
/*     */     
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   private static Ingredient fromValues(Stream<? extends Value> $$0) {
/* 102 */     Ingredient $$1 = new Ingredient($$0);
/*     */     
/* 104 */     return $$1.isEmpty() ? EMPTY : $$1;
/*     */   }
/*     */   
/*     */   public static Ingredient of() {
/* 108 */     return EMPTY;
/*     */   }
/*     */   
/*     */   public static Ingredient of(ItemLike... $$0) {
/* 112 */     return of(Arrays.<ItemLike>stream($$0).map(ItemStack::new));
/*     */   }
/*     */   
/*     */   public static Ingredient of(ItemStack... $$0) {
/* 116 */     return of(Arrays.stream($$0));
/*     */   }
/*     */   
/*     */   public static Ingredient of(Stream<ItemStack> $$0) {
/* 120 */     return fromValues($$0.filter($$0 -> !$$0.isEmpty()).map(ItemValue::new));
/*     */   }
/*     */   
/*     */   public static Ingredient of(TagKey<Item> $$0) {
/* 124 */     return fromValues(Stream.of(new TagValue($$0)));
/*     */   }
/*     */   
/*     */   public static Ingredient fromNetwork(FriendlyByteBuf $$0) {
/* 128 */     return fromValues($$0.readList(FriendlyByteBuf::readItem).stream().map(ItemValue::new));
/*     */   }
/*     */   
/*     */   private static Codec<Ingredient> codec(boolean $$0) {
/* 132 */     Codec<Value[]> $$1 = Codec.list(Value.CODEC).comapFlatMap($$1 -> 
/*     */         
/* 134 */         (!$$0 && $$1.size() < 1) ? DataResult.error(()) : DataResult.success($$1.toArray((Object[])new Value[0])), List::of);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return ExtraCodecs.either($$1, Value.CODEC).flatComapMap($$0 -> (Ingredient)$$0.map(Ingredient::new, ()), $$1 -> ($$1.values.length == 1) ? DataResult.success(Either.right($$1.values[0])) : (
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         ($$1.values.length == 0 && !$$0) ? DataResult.error(()) : DataResult.success(Either.left($$1.values))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final Codec<Ingredient> CODEC = codec(true);
/* 158 */   public static final Codec<Ingredient> CODEC_NONEMPTY = codec(false);
/*     */   private static interface Value { public static final Codec<Value> CODEC;
/*     */     static {
/* 161 */       CODEC = ExtraCodecs.xor(Ingredient.ItemValue.CODEC, Ingredient.TagValue.CODEC).xmap($$0 -> (Value)$$0.map((), ()), $$0 -> {
/*     */             if ($$0 instanceof Ingredient.TagValue) {
/*     */               Ingredient.TagValue $$1 = (Ingredient.TagValue)$$0;
/*     */               return Either.right($$1);
/*     */             } 
/*     */             if ($$0 instanceof Ingredient.ItemValue) {
/*     */               Ingredient.ItemValue $$2 = (Ingredient.ItemValue)$$0;
/*     */               return Either.left($$2);
/*     */             } 
/*     */             throw new UnsupportedOperationException("This is neither an item value nor a tag value.");
/*     */           });
/*     */     }
/*     */     Collection<ItemStack> getItems(); }
/*     */   private static final class ItemValue extends Record implements Value { private final ItemStack item; static final Codec<ItemValue> CODEC;
/*     */     
/* 176 */     private ItemValue(ItemStack $$0) { this.item = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/crafting/Ingredient$ItemValue;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #176	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 176 */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/Ingredient$ItemValue; } public ItemStack item() { return this.item; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/crafting/Ingredient$ItemValue;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #176	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 177 */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/Ingredient$ItemValue; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ItemStack.SINGLE_ITEM_CODEC.fieldOf("item").forGetter(())).apply((Applicative)$$0, ItemValue::new)); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 183 */       if ($$0 instanceof ItemValue) { ItemValue $$1 = (ItemValue)$$0;
/* 184 */         return ($$1.item.getItem().equals(this.item.getItem()) && $$1.item.getCount() == this.item.getCount()); }
/*     */       
/* 186 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<ItemStack> getItems() {
/* 191 */       return Collections.singleton(this.item);
/*     */     } }
/*     */   private static final class TagValue extends Record implements Value { private final TagKey<Item> tag; static final Codec<TagValue> CODEC;
/*     */     
/* 195 */     TagValue(TagKey<Item> $$0) { this.tag = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/crafting/Ingredient$TagValue;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #195	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/Ingredient$TagValue; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/crafting/Ingredient$TagValue;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #195	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 195 */       //   0	7	0	this	Lnet/minecraft/world/item/crafting/Ingredient$TagValue; } public TagKey<Item> tag() { return this.tag; } static {
/* 196 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(())).apply((Applicative)$$0, TagValue::new));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 202 */       if ($$0 instanceof TagValue) { TagValue $$1 = (TagValue)$$0;
/* 203 */         return $$1.tag.location().equals(this.tag.location()); }
/*     */       
/* 205 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<ItemStack> getItems() {
/* 210 */       List<ItemStack> $$0 = Lists.newArrayList();
/* 211 */       for (Holder<Item> $$1 : (Iterable<Holder<Item>>)BuiltInRegistries.ITEM.getTagOrEmpty(this.tag)) {
/* 212 */         $$0.add(new ItemStack($$1));
/*     */       }
/* 214 */       return $$0;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\Ingredient.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */