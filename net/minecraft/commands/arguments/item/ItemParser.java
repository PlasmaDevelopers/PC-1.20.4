/*     */ package net.minecraft.commands.arguments.item;
/*     */ import com.mojang.brigadier.ImmutableStringReader;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.TagParser;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.item.Item;
/*     */ 
/*     */ public class ItemParser {
/*  29 */   private static final SimpleCommandExceptionType ERROR_NO_TAGS_ALLOWED = new SimpleCommandExceptionType((Message)Component.translatable("argument.item.tag.disallowed")); private static final DynamicCommandExceptionType ERROR_UNKNOWN_ITEM; private static final DynamicCommandExceptionType ERROR_UNKNOWN_TAG; private static final char SYNTAX_START_NBT = '{'; private static final char SYNTAX_TAG = '#'; static {
/*  30 */     ERROR_UNKNOWN_ITEM = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.item.id.invalid", new Object[] { $$0 }));
/*  31 */     ERROR_UNKNOWN_TAG = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("arguments.item.tag.unknown", new Object[] { $$0 }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  36 */   private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> SUGGEST_NOTHING = SuggestionsBuilder::buildFuture;
/*     */   
/*     */   private final HolderLookup<Item> items;
/*     */   private final StringReader reader;
/*     */   private final boolean allowTags;
/*     */   private Either<Holder<Item>, HolderSet<Item>> result;
/*     */   @Nullable
/*     */   private CompoundTag nbt;
/*  44 */   private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> suggestions = SUGGEST_NOTHING;
/*     */   
/*     */   private ItemParser(HolderLookup<Item> $$0, StringReader $$1, boolean $$2) {
/*  47 */     this.items = $$0;
/*  48 */     this.reader = $$1;
/*  49 */     this.allowTags = $$2;
/*     */   } public static final class ItemResult extends Record { private final Holder<Item> item; @Nullable
/*     */     private final CompoundTag nbt;
/*  52 */     public ItemResult(Holder<Item> $$0, @Nullable CompoundTag $$1) { this.item = $$0; this.nbt = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #52	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  52 */       //   0	7	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult; } public Holder<Item> item() { return this.item; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #52	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #52	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;
/*  52 */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public CompoundTag nbt() { return this.nbt; } } public static final class TagResult extends Record { private final HolderSet<Item> tag; @Nullable
/*     */     private final CompoundTag nbt;
/*  54 */     public TagResult(HolderSet<Item> $$0, @Nullable CompoundTag $$1) { this.tag = $$0; this.nbt = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/item/ItemParser$TagResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #54	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$TagResult; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/item/ItemParser$TagResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #54	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$TagResult; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/item/ItemParser$TagResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #54	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$TagResult;
/*  54 */       //   0	8	1	$$0	Ljava/lang/Object; } public HolderSet<Item> tag() { return this.tag; } @Nullable public CompoundTag nbt() { return this.nbt; }
/*     */      }
/*     */   public static ItemResult parseForItem(HolderLookup<Item> $$0, StringReader $$1) throws CommandSyntaxException {
/*  57 */     int $$2 = $$1.getCursor();
/*     */     try {
/*  59 */       ItemParser $$3 = new ItemParser($$0, $$1, false);
/*  60 */       $$3.parse();
/*  61 */       Holder<Item> $$4 = (Holder<Item>)$$3.result.left().orElseThrow(() -> new IllegalStateException("Parser returned unexpected tag name"));
/*  62 */       return new ItemResult($$4, $$3.nbt);
/*  63 */     } catch (CommandSyntaxException $$5) {
/*  64 */       $$1.setCursor($$2);
/*  65 */       throw $$5;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Either<ItemResult, TagResult> parseForTesting(HolderLookup<Item> $$0, StringReader $$1) throws CommandSyntaxException {
/*  70 */     int $$2 = $$1.getCursor();
/*     */     try {
/*  72 */       ItemParser $$3 = new ItemParser($$0, $$1, true);
/*  73 */       $$3.parse();
/*  74 */       return $$3.result.mapBoth($$1 -> new ItemResult($$1, $$0.nbt), $$1 -> new TagResult($$1, $$0.nbt));
/*     */ 
/*     */     
/*     */     }
/*  78 */     catch (CommandSyntaxException $$4) {
/*  79 */       $$1.setCursor($$2);
/*  80 */       throw $$4;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static CompletableFuture<Suggestions> fillSuggestions(HolderLookup<Item> $$0, SuggestionsBuilder $$1, boolean $$2) {
/*  85 */     StringReader $$3 = new StringReader($$1.getInput());
/*  86 */     $$3.setCursor($$1.getStart());
/*     */     
/*  88 */     ItemParser $$4 = new ItemParser($$0, $$3, $$2);
/*     */     try {
/*  90 */       $$4.parse();
/*  91 */     } catch (CommandSyntaxException commandSyntaxException) {}
/*     */ 
/*     */     
/*  94 */     return $$4.suggestions.apply($$1.createOffset($$3.getCursor()));
/*     */   }
/*     */   
/*     */   private void readItem() throws CommandSyntaxException {
/*  98 */     int $$0 = this.reader.getCursor();
/*  99 */     ResourceLocation $$1 = ResourceLocation.read(this.reader);
/* 100 */     Optional<? extends Holder<Item>> $$2 = this.items.get(ResourceKey.create(Registries.ITEM, $$1));
/* 101 */     this.result = Either.left($$2.<Throwable>orElseThrow(() -> {
/*     */             this.reader.setCursor($$0);
/*     */             return ERROR_UNKNOWN_ITEM.createWithContext((ImmutableStringReader)this.reader, $$1);
/*     */           }));
/*     */   }
/*     */   
/*     */   private void readTag() throws CommandSyntaxException {
/* 108 */     if (!this.allowTags) {
/* 109 */       throw ERROR_NO_TAGS_ALLOWED.createWithContext(this.reader);
/*     */     }
/*     */     
/* 112 */     int $$0 = this.reader.getCursor();
/* 113 */     this.reader.expect('#');
/* 114 */     this.suggestions = this::suggestTag;
/* 115 */     ResourceLocation $$1 = ResourceLocation.read(this.reader);
/* 116 */     Optional<? extends HolderSet<Item>> $$2 = this.items.get(TagKey.create(Registries.ITEM, $$1));
/* 117 */     this.result = Either.right($$2.<Throwable>orElseThrow(() -> {
/*     */             this.reader.setCursor($$0);
/*     */             return ERROR_UNKNOWN_TAG.createWithContext((ImmutableStringReader)this.reader, $$1);
/*     */           }));
/*     */   }
/*     */   
/*     */   private void readNbt() throws CommandSyntaxException {
/* 124 */     this.nbt = (new TagParser(this.reader)).readStruct();
/*     */   }
/*     */   
/*     */   private void parse() throws CommandSyntaxException {
/* 128 */     if (this.allowTags) {
/* 129 */       this.suggestions = this::suggestItemIdOrTag;
/*     */     } else {
/* 131 */       this.suggestions = this::suggestItem;
/*     */     } 
/* 133 */     if (this.reader.canRead() && this.reader.peek() == '#') {
/* 134 */       readTag();
/*     */     } else {
/* 136 */       readItem();
/*     */     } 
/* 138 */     this.suggestions = this::suggestOpenNbt;
/* 139 */     if (this.reader.canRead() && this.reader.peek() == '{') {
/* 140 */       this.suggestions = SUGGEST_NOTHING;
/* 141 */       readNbt();
/*     */     } 
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOpenNbt(SuggestionsBuilder $$0) {
/* 146 */     if ($$0.getRemaining().isEmpty()) {
/* 147 */       $$0.suggest(String.valueOf('{'));
/*     */     }
/* 149 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestTag(SuggestionsBuilder $$0) {
/* 153 */     return SharedSuggestionProvider.suggestResource(this.items.listTagIds().map(TagKey::location), $$0, String.valueOf('#'));
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestItem(SuggestionsBuilder $$0) {
/* 157 */     return SharedSuggestionProvider.suggestResource(this.items.listElementIds().map(ResourceKey::location), $$0);
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestItemIdOrTag(SuggestionsBuilder $$0) {
/* 161 */     suggestTag($$0);
/* 162 */     return suggestItem($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\item\ItemParser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */