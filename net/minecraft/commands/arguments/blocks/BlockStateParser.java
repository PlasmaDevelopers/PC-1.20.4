/*     */ package net.minecraft.commands.arguments.blocks;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.brigadier.ImmutableStringReader;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class BlockStateParser {
/*  37 */   public static final SimpleCommandExceptionType ERROR_NO_TAGS_ALLOWED = new SimpleCommandExceptionType((Message)Component.translatable("argument.block.tag.disallowed")); public static final DynamicCommandExceptionType ERROR_UNKNOWN_BLOCK; public static final Dynamic2CommandExceptionType ERROR_UNKNOWN_PROPERTY; public static final Dynamic2CommandExceptionType ERROR_DUPLICATE_PROPERTY; public static final Dynamic3CommandExceptionType ERROR_INVALID_VALUE; public static final Dynamic2CommandExceptionType ERROR_EXPECTED_VALUE; static {
/*  38 */     ERROR_UNKNOWN_BLOCK = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.block.id.invalid", new Object[] { $$0 }));
/*  39 */     ERROR_UNKNOWN_PROPERTY = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.block.property.unknown", new Object[] { $$0, $$1 }));
/*  40 */     ERROR_DUPLICATE_PROPERTY = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.block.property.duplicate", new Object[] { $$1, $$0 }));
/*  41 */     ERROR_INVALID_VALUE = new Dynamic3CommandExceptionType(($$0, $$1, $$2) -> Component.translatableEscape("argument.block.property.invalid", new Object[] { $$0, $$2, $$1 }));
/*  42 */     ERROR_EXPECTED_VALUE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.block.property.novalue", new Object[] { $$0, $$1 }));
/*  43 */   } public static final SimpleCommandExceptionType ERROR_EXPECTED_END_OF_PROPERTIES = new SimpleCommandExceptionType((Message)Component.translatable("argument.block.property.unclosed")); public static final DynamicCommandExceptionType ERROR_UNKNOWN_TAG; private static final char SYNTAX_START_PROPERTIES = '['; private static final char SYNTAX_START_NBT = '{'; private static final char SYNTAX_END_PROPERTIES = ']'; private static final char SYNTAX_EQUALS = '='; private static final char SYNTAX_PROPERTY_SEPARATOR = ','; private static final char SYNTAX_TAG = '#'; static {
/*  44 */     ERROR_UNKNOWN_TAG = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("arguments.block.tag.unknown", new Object[] { $$0 }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> SUGGEST_NOTHING = SuggestionsBuilder::buildFuture;
/*     */   
/*     */   private final HolderLookup<Block> blocks;
/*     */   private final StringReader reader;
/*     */   private final boolean forTesting;
/*     */   private final boolean allowNbt;
/*  59 */   private final Map<Property<?>, Comparable<?>> properties = Maps.newHashMap();
/*  60 */   private final Map<String, String> vagueProperties = Maps.newHashMap();
/*  61 */   private ResourceLocation id = new ResourceLocation("");
/*     */   @Nullable
/*     */   private StateDefinition<Block, BlockState> definition;
/*     */   @Nullable
/*     */   private BlockState state;
/*     */   @Nullable
/*     */   private CompoundTag nbt;
/*     */   @Nullable
/*     */   private HolderSet<Block> tag;
/*  70 */   private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> suggestions = SUGGEST_NOTHING;
/*     */   
/*     */   private BlockStateParser(HolderLookup<Block> $$0, StringReader $$1, boolean $$2, boolean $$3) {
/*  73 */     this.blocks = $$0;
/*  74 */     this.reader = $$1;
/*  75 */     this.forTesting = $$2;
/*  76 */     this.allowNbt = $$3;
/*     */   } public static final class BlockResult extends Record { private final BlockState blockState; private final Map<Property<?>, Comparable<?>> properties; @Nullable
/*     */     private final CompoundTag nbt;
/*  79 */     public BlockResult(BlockState $$0, Map<Property<?>, Comparable<?>> $$1, @Nullable CompoundTag $$2) { this.blockState = $$0; this.properties = $$1; this.nbt = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/blocks/BlockStateParser$BlockResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #79	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  79 */       //   0	7	0	this	Lnet/minecraft/commands/arguments/blocks/BlockStateParser$BlockResult; } public BlockState blockState() { return this.blockState; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/blocks/BlockStateParser$BlockResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #79	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/blocks/BlockStateParser$BlockResult; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/blocks/BlockStateParser$BlockResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #79	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/blocks/BlockStateParser$BlockResult;
/*  79 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<Property<?>, Comparable<?>> properties() { return this.properties; } @Nullable public CompoundTag nbt() { return this.nbt; } } public static final class TagResult extends Record { private final HolderSet<Block> tag; private final Map<String, String> vagueProperties; @Nullable
/*     */     private final CompoundTag nbt;
/*  81 */     public TagResult(HolderSet<Block> $$0, Map<String, String> $$1, @Nullable CompoundTag $$2) { this.tag = $$0; this.vagueProperties = $$1; this.nbt = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/blocks/BlockStateParser$TagResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #81	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/blocks/BlockStateParser$TagResult; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/blocks/BlockStateParser$TagResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #81	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/blocks/BlockStateParser$TagResult; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/blocks/BlockStateParser$TagResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #81	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/blocks/BlockStateParser$TagResult;
/*  81 */       //   0	8	1	$$0	Ljava/lang/Object; } public HolderSet<Block> tag() { return this.tag; } public Map<String, String> vagueProperties() { return this.vagueProperties; } @Nullable public CompoundTag nbt() { return this.nbt; }
/*     */      }
/*     */   public static BlockResult parseForBlock(HolderLookup<Block> $$0, String $$1, boolean $$2) throws CommandSyntaxException {
/*  84 */     return parseForBlock($$0, new StringReader($$1), $$2);
/*     */   }
/*     */   
/*     */   public static BlockResult parseForBlock(HolderLookup<Block> $$0, StringReader $$1, boolean $$2) throws CommandSyntaxException {
/*  88 */     int $$3 = $$1.getCursor();
/*     */     try {
/*  90 */       BlockStateParser $$4 = new BlockStateParser($$0, $$1, false, $$2);
/*  91 */       $$4.parse();
/*     */       
/*  93 */       return new BlockResult($$4.state, $$4.properties, $$4.nbt);
/*  94 */     } catch (CommandSyntaxException $$5) {
/*  95 */       $$1.setCursor($$3);
/*  96 */       throw $$5;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Either<BlockResult, TagResult> parseForTesting(HolderLookup<Block> $$0, String $$1, boolean $$2) throws CommandSyntaxException {
/* 101 */     return parseForTesting($$0, new StringReader($$1), $$2);
/*     */   }
/*     */   
/*     */   public static Either<BlockResult, TagResult> parseForTesting(HolderLookup<Block> $$0, StringReader $$1, boolean $$2) throws CommandSyntaxException {
/* 105 */     int $$3 = $$1.getCursor();
/*     */     try {
/* 107 */       BlockStateParser $$4 = new BlockStateParser($$0, $$1, true, $$2);
/* 108 */       $$4.parse();
/* 109 */       if ($$4.tag != null) {
/* 110 */         return Either.right(new TagResult($$4.tag, $$4.vagueProperties, $$4.nbt));
/*     */       }
/* 112 */       return Either.left(new BlockResult($$4.state, $$4.properties, $$4.nbt));
/* 113 */     } catch (CommandSyntaxException $$5) {
/* 114 */       $$1.setCursor($$3);
/* 115 */       throw $$5;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static CompletableFuture<Suggestions> fillSuggestions(HolderLookup<Block> $$0, SuggestionsBuilder $$1, boolean $$2, boolean $$3) {
/* 120 */     StringReader $$4 = new StringReader($$1.getInput());
/* 121 */     $$4.setCursor($$1.getStart());
/*     */     
/* 123 */     BlockStateParser $$5 = new BlockStateParser($$0, $$4, $$2, $$3);
/*     */     try {
/* 125 */       $$5.parse();
/* 126 */     } catch (CommandSyntaxException commandSyntaxException) {}
/*     */ 
/*     */     
/* 129 */     return $$5.suggestions.apply($$1.createOffset($$4.getCursor()));
/*     */   }
/*     */   
/*     */   private void parse() throws CommandSyntaxException {
/* 133 */     if (this.forTesting) {
/* 134 */       this.suggestions = this::suggestBlockIdOrTag;
/*     */     } else {
/* 136 */       this.suggestions = this::suggestItem;
/*     */     } 
/* 138 */     if (this.reader.canRead() && this.reader.peek() == '#') {
/* 139 */       readTag();
/* 140 */       this.suggestions = this::suggestOpenVaguePropertiesOrNbt;
/* 141 */       if (this.reader.canRead() && this.reader.peek() == '[') {
/* 142 */         readVagueProperties();
/* 143 */         this.suggestions = this::suggestOpenNbt;
/*     */       } 
/*     */     } else {
/* 146 */       readBlock();
/* 147 */       this.suggestions = this::suggestOpenPropertiesOrNbt;
/* 148 */       if (this.reader.canRead() && this.reader.peek() == '[') {
/* 149 */         readProperties();
/* 150 */         this.suggestions = this::suggestOpenNbt;
/*     */       } 
/*     */     } 
/* 153 */     if (this.allowNbt && this.reader.canRead() && this.reader.peek() == '{') {
/* 154 */       this.suggestions = SUGGEST_NOTHING;
/* 155 */       readNbt();
/*     */     } 
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestPropertyNameOrEnd(SuggestionsBuilder $$0) {
/* 160 */     if ($$0.getRemaining().isEmpty()) {
/* 161 */       $$0.suggest(String.valueOf(']'));
/*     */     }
/*     */     
/* 164 */     return suggestPropertyName($$0);
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestVaguePropertyNameOrEnd(SuggestionsBuilder $$0) {
/* 168 */     if ($$0.getRemaining().isEmpty()) {
/* 169 */       $$0.suggest(String.valueOf(']'));
/*     */     }
/* 171 */     return suggestVaguePropertyName($$0);
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestPropertyName(SuggestionsBuilder $$0) {
/* 175 */     String $$1 = $$0.getRemaining().toLowerCase(Locale.ROOT);
/* 176 */     for (Property<?> $$2 : (Iterable<Property<?>>)this.state.getProperties()) {
/* 177 */       if (!this.properties.containsKey($$2) && $$2.getName().startsWith($$1)) {
/* 178 */         $$0.suggest($$2.getName() + "=");
/*     */       }
/*     */     } 
/* 181 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestVaguePropertyName(SuggestionsBuilder $$0) {
/* 185 */     String $$1 = $$0.getRemaining().toLowerCase(Locale.ROOT);
/* 186 */     if (this.tag != null) {
/* 187 */       for (Holder<Block> $$2 : this.tag) {
/* 188 */         for (Property<?> $$3 : (Iterable<Property<?>>)((Block)$$2.value()).getStateDefinition().getProperties()) {
/* 189 */           if (!this.vagueProperties.containsKey($$3.getName()) && $$3.getName().startsWith($$1)) {
/* 190 */             $$0.suggest($$3.getName() + "=");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 195 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOpenNbt(SuggestionsBuilder $$0) {
/* 199 */     if ($$0.getRemaining().isEmpty() && hasBlockEntity()) {
/* 200 */       $$0.suggest(String.valueOf('{'));
/*     */     }
/* 202 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private boolean hasBlockEntity() {
/* 206 */     if (this.state != null) {
/* 207 */       return this.state.hasBlockEntity();
/*     */     }
/*     */     
/* 210 */     if (this.tag != null) {
/* 211 */       for (Holder<Block> $$0 : this.tag) {
/* 212 */         if (((Block)$$0.value()).defaultBlockState().hasBlockEntity()) {
/* 213 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 218 */     return false;
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestEquals(SuggestionsBuilder $$0) {
/* 222 */     if ($$0.getRemaining().isEmpty()) {
/* 223 */       $$0.suggest(String.valueOf('='));
/*     */     }
/* 225 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestNextPropertyOrEnd(SuggestionsBuilder $$0) {
/* 229 */     if ($$0.getRemaining().isEmpty()) {
/* 230 */       $$0.suggest(String.valueOf(']'));
/*     */     }
/* 232 */     if ($$0.getRemaining().isEmpty() && this.properties.size() < this.state.getProperties().size()) {
/* 233 */       $$0.suggest(String.valueOf(','));
/*     */     }
/* 235 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private static <T extends Comparable<T>> SuggestionsBuilder addSuggestions(SuggestionsBuilder $$0, Property<T> $$1) {
/* 239 */     for (Comparable comparable : $$1.getPossibleValues()) {
/* 240 */       if (comparable instanceof Integer) { Integer $$3 = (Integer)comparable;
/* 241 */         $$0.suggest($$3.intValue()); continue; }
/*     */       
/* 243 */       $$0.suggest($$1.getName(comparable));
/*     */     } 
/*     */     
/* 246 */     return $$0;
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestVaguePropertyValue(SuggestionsBuilder $$0, String $$1) {
/* 250 */     boolean $$2 = false;
/* 251 */     if (this.tag != null) {
/* 252 */       for (Holder<Block> $$3 : this.tag) {
/* 253 */         Block $$4 = (Block)$$3.value();
/* 254 */         Property<?> $$5 = $$4.getStateDefinition().getProperty($$1);
/* 255 */         if ($$5 != null) {
/* 256 */           addSuggestions($$0, $$5);
/*     */         }
/* 258 */         if (!$$2) {
/* 259 */           for (Property<?> $$6 : (Iterable<Property<?>>)$$4.getStateDefinition().getProperties()) {
/* 260 */             if (!this.vagueProperties.containsKey($$6.getName())) {
/* 261 */               $$2 = true;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 268 */     if ($$2) {
/* 269 */       $$0.suggest(String.valueOf(','));
/*     */     }
/* 271 */     $$0.suggest(String.valueOf(']'));
/* 272 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOpenVaguePropertiesOrNbt(SuggestionsBuilder $$0) {
/* 276 */     if ($$0.getRemaining().isEmpty() && 
/* 277 */       this.tag != null) {
/* 278 */       int i; boolean $$1 = false;
/* 279 */       boolean $$2 = false;
/*     */       
/* 281 */       for (Holder<Block> $$3 : this.tag) {
/* 282 */         Block $$4 = (Block)$$3.value();
/* 283 */         i = $$1 | (!$$4.getStateDefinition().getProperties().isEmpty() ? 1 : 0);
/* 284 */         $$2 |= $$4.defaultBlockState().hasBlockEntity();
/*     */         
/* 286 */         if (i != 0 && $$2) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 291 */       if (i != 0) {
/* 292 */         $$0.suggest(String.valueOf('['));
/*     */       }
/*     */       
/* 295 */       if ($$2) {
/* 296 */         $$0.suggest(String.valueOf('{'));
/*     */       }
/*     */     } 
/*     */     
/* 300 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOpenPropertiesOrNbt(SuggestionsBuilder $$0) {
/* 304 */     if ($$0.getRemaining().isEmpty()) {
/* 305 */       if (!this.definition.getProperties().isEmpty()) {
/* 306 */         $$0.suggest(String.valueOf('['));
/*     */       }
/* 308 */       if (this.state.hasBlockEntity()) {
/* 309 */         $$0.suggest(String.valueOf('{'));
/*     */       }
/*     */     } 
/* 312 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestTag(SuggestionsBuilder $$0) {
/* 316 */     return SharedSuggestionProvider.suggestResource(this.blocks.listTagIds().map(TagKey::location), $$0, String.valueOf('#'));
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestItem(SuggestionsBuilder $$0) {
/* 320 */     return SharedSuggestionProvider.suggestResource(this.blocks.listElementIds().map(ResourceKey::location), $$0);
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestBlockIdOrTag(SuggestionsBuilder $$0) {
/* 324 */     suggestTag($$0);
/* 325 */     suggestItem($$0);
/* 326 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private void readBlock() throws CommandSyntaxException {
/* 330 */     int $$0 = this.reader.getCursor();
/* 331 */     this.id = ResourceLocation.read(this.reader);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     Block $$1 = (Block)((Holder.Reference)this.blocks.get(ResourceKey.create(Registries.BLOCK, this.id)).orElseThrow(() -> { this.reader.setCursor($$0); return ERROR_UNKNOWN_BLOCK.createWithContext((ImmutableStringReader)this.reader, this.id.toString()); })).value();
/*     */     
/* 338 */     this.definition = $$1.getStateDefinition();
/* 339 */     this.state = $$1.defaultBlockState();
/*     */   }
/*     */   
/*     */   private void readTag() throws CommandSyntaxException {
/* 343 */     if (!this.forTesting) {
/* 344 */       throw ERROR_NO_TAGS_ALLOWED.createWithContext(this.reader);
/*     */     }
/*     */     
/* 347 */     int $$0 = this.reader.getCursor();
/* 348 */     this.reader.expect('#');
/* 349 */     this.suggestions = this::suggestTag;
/* 350 */     ResourceLocation $$1 = ResourceLocation.read(this.reader);
/* 351 */     this.tag = (HolderSet<Block>)this.blocks.get(TagKey.create(Registries.BLOCK, $$1)).orElseThrow(() -> {
/*     */           this.reader.setCursor($$0);
/*     */           return ERROR_UNKNOWN_TAG.createWithContext((ImmutableStringReader)this.reader, $$1.toString());
/*     */         });
/*     */   }
/*     */   
/*     */   private void readProperties() throws CommandSyntaxException {
/* 358 */     this.reader.skip();
/* 359 */     this.suggestions = this::suggestPropertyNameOrEnd;
/*     */     
/* 361 */     this.reader.skipWhitespace();
/* 362 */     while (this.reader.canRead() && this.reader.peek() != ']') {
/* 363 */       this.reader.skipWhitespace();
/* 364 */       int $$0 = this.reader.getCursor();
/* 365 */       String $$1 = this.reader.readString();
/* 366 */       Property<?> $$2 = this.definition.getProperty($$1);
/* 367 */       if ($$2 == null) {
/* 368 */         this.reader.setCursor($$0);
/* 369 */         throw ERROR_UNKNOWN_PROPERTY.createWithContext(this.reader, this.id.toString(), $$1);
/*     */       } 
/* 371 */       if (this.properties.containsKey($$2)) {
/* 372 */         this.reader.setCursor($$0);
/* 373 */         throw ERROR_DUPLICATE_PROPERTY.createWithContext(this.reader, this.id.toString(), $$1);
/*     */       } 
/*     */       
/* 376 */       this.reader.skipWhitespace();
/* 377 */       this.suggestions = this::suggestEquals;
/* 378 */       if (!this.reader.canRead() || this.reader.peek() != '=') {
/* 379 */         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader, this.id.toString(), $$1);
/*     */       }
/* 381 */       this.reader.skip();
/* 382 */       this.reader.skipWhitespace();
/*     */       
/* 384 */       this.suggestions = ($$1 -> addSuggestions($$1, $$0).buildFuture());
/* 385 */       int $$3 = this.reader.getCursor();
/* 386 */       setValue($$2, this.reader.readString(), $$3);
/*     */       
/* 388 */       this.suggestions = this::suggestNextPropertyOrEnd;
/* 389 */       this.reader.skipWhitespace();
/* 390 */       if (this.reader.canRead()) {
/* 391 */         if (this.reader.peek() == ',') {
/* 392 */           this.reader.skip();
/* 393 */           this.suggestions = this::suggestPropertyName; continue;
/* 394 */         }  if (this.reader.peek() == ']') {
/*     */           break;
/*     */         }
/* 397 */         throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 402 */     if (this.reader.canRead()) {
/* 403 */       this.reader.skip();
/*     */     } else {
/* 405 */       throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readVagueProperties() throws CommandSyntaxException {
/* 410 */     this.reader.skip();
/* 411 */     this.suggestions = this::suggestVaguePropertyNameOrEnd;
/* 412 */     int $$0 = -1;
/*     */     
/* 414 */     this.reader.skipWhitespace();
/* 415 */     while (this.reader.canRead() && this.reader.peek() != ']') {
/* 416 */       this.reader.skipWhitespace();
/* 417 */       int $$1 = this.reader.getCursor();
/* 418 */       String $$2 = this.reader.readString();
/* 419 */       if (this.vagueProperties.containsKey($$2)) {
/* 420 */         this.reader.setCursor($$1);
/* 421 */         throw ERROR_DUPLICATE_PROPERTY.createWithContext(this.reader, this.id.toString(), $$2);
/*     */       } 
/*     */       
/* 424 */       this.reader.skipWhitespace();
/* 425 */       if (!this.reader.canRead() || this.reader.peek() != '=') {
/* 426 */         this.reader.setCursor($$1);
/* 427 */         throw ERROR_EXPECTED_VALUE.createWithContext(this.reader, this.id.toString(), $$2);
/*     */       } 
/* 429 */       this.reader.skip();
/*     */       
/* 431 */       this.reader.skipWhitespace();
/* 432 */       this.suggestions = ($$1 -> suggestVaguePropertyValue($$1, $$0));
/* 433 */       $$0 = this.reader.getCursor();
/* 434 */       String $$3 = this.reader.readString();
/* 435 */       this.vagueProperties.put($$2, $$3);
/*     */       
/* 437 */       this.reader.skipWhitespace();
/* 438 */       if (this.reader.canRead()) {
/* 439 */         $$0 = -1;
/* 440 */         if (this.reader.peek() == ',') {
/* 441 */           this.reader.skip();
/* 442 */           this.suggestions = this::suggestVaguePropertyName; continue;
/* 443 */         }  if (this.reader.peek() == ']') {
/*     */           break;
/*     */         }
/* 446 */         throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 451 */     if (this.reader.canRead()) {
/* 452 */       this.reader.skip();
/*     */     } else {
/* 454 */       if ($$0 >= 0) {
/* 455 */         this.reader.setCursor($$0);
/*     */       }
/* 457 */       throw ERROR_EXPECTED_END_OF_PROPERTIES.createWithContext(this.reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readNbt() throws CommandSyntaxException {
/* 462 */     this.nbt = (new TagParser(this.reader)).readStruct();
/*     */   }
/*     */   
/*     */   private <T extends Comparable<T>> void setValue(Property<T> $$0, String $$1, int $$2) throws CommandSyntaxException {
/* 466 */     Optional<T> $$3 = $$0.getValue($$1);
/* 467 */     if ($$3.isPresent()) {
/* 468 */       this.state = (BlockState)this.state.setValue($$0, (Comparable)$$3.get());
/* 469 */       this.properties.put($$0, (Comparable)$$3.get());
/*     */     } else {
/* 471 */       this.reader.setCursor($$2);
/* 472 */       throw ERROR_INVALID_VALUE.createWithContext(this.reader, this.id.toString(), $$0.getName(), $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String serialize(BlockState $$0) {
/* 477 */     StringBuilder $$1 = new StringBuilder($$0.getBlockHolder().unwrapKey().map($$0 -> $$0.location().toString()).orElse("air"));
/* 478 */     if (!$$0.getProperties().isEmpty()) {
/* 479 */       $$1.append('[');
/* 480 */       boolean $$2 = false;
/* 481 */       for (UnmodifiableIterator<Map.Entry<Property<?>, Comparable<?>>> unmodifiableIterator = $$0.getValues().entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Property<?>, Comparable<?>> $$3 = unmodifiableIterator.next();
/* 482 */         if ($$2) {
/* 483 */           $$1.append(',');
/*     */         }
/*     */         
/* 486 */         appendProperty($$1, (Property<Comparable>)$$3.getKey(), $$3.getValue());
/* 487 */         $$2 = true; }
/*     */       
/* 489 */       $$1.append(']');
/*     */     } 
/* 491 */     return $$1.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Comparable<T>> void appendProperty(StringBuilder $$0, Property<T> $$1, Comparable<?> $$2) {
/* 496 */     $$0.append($$1.getName());
/* 497 */     $$0.append('=');
/* 498 */     $$0.append($$1.getName($$2));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\blocks\BlockStateParser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */