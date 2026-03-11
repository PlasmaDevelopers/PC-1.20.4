/*     */ package net.minecraft.commands.arguments.selector.options;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.CriterionProgress;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.nbt.TagParser;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.PlayerAdvancements;
/*     */ import net.minecraft.server.ServerAdvancementManager;
/*     */ import net.minecraft.server.ServerScoreboard;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.LootDataType;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import net.minecraft.world.scores.ReadOnlyScoreInfo;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ 
/*     */ public class EntitySelectorOptions {
/*  53 */   private static final Map<String, Option> OPTIONS = Maps.newHashMap(); public static final DynamicCommandExceptionType ERROR_UNKNOWN_OPTION; public static final DynamicCommandExceptionType ERROR_INAPPLICABLE_OPTION;
/*     */   static {
/*  55 */     ERROR_UNKNOWN_OPTION = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.entity.options.unknown", new Object[] { $$0 }));
/*  56 */     ERROR_INAPPLICABLE_OPTION = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.entity.options.inapplicable", new Object[] { $$0 }));
/*  57 */   } public static final SimpleCommandExceptionType ERROR_RANGE_NEGATIVE = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.options.distance.negative"));
/*  58 */   public static final SimpleCommandExceptionType ERROR_LEVEL_NEGATIVE = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.options.level.negative"));
/*  59 */   public static final SimpleCommandExceptionType ERROR_LIMIT_TOO_SMALL = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.options.limit.toosmall")); public static final DynamicCommandExceptionType ERROR_SORT_UNKNOWN; public static final DynamicCommandExceptionType ERROR_GAME_MODE_INVALID; public static final DynamicCommandExceptionType ERROR_ENTITY_TYPE_INVALID; static {
/*  60 */     ERROR_SORT_UNKNOWN = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.entity.options.sort.irreversible", new Object[] { $$0 }));
/*  61 */     ERROR_GAME_MODE_INVALID = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.entity.options.mode.invalid", new Object[] { $$0 }));
/*  62 */     ERROR_ENTITY_TYPE_INVALID = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.entity.options.type.invalid", new Object[] { $$0 }));
/*     */   }
/*     */   private static void register(String $$0, Modifier $$1, Predicate<EntitySelectorParser> $$2, Component $$3) {
/*  65 */     OPTIONS.put($$0, new Option($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public static void bootStrap() {
/*  69 */     if (!OPTIONS.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     register("name", $$0 -> {
/*     */           int $$1 = $$0.getReader().getCursor();
/*     */           boolean $$2 = $$0.shouldInvertValue();
/*     */           String $$3 = $$0.getReader().readString();
/*     */           if ($$0.hasNameNotEquals() && !$$2) {
/*     */             $$0.getReader().setCursor($$1);
/*     */             throw ERROR_INAPPLICABLE_OPTION.createWithContext($$0.getReader(), "name");
/*     */           } 
/*     */           if ($$2) {
/*     */             $$0.setHasNameNotEquals(true);
/*     */           } else {
/*     */             $$0.setHasNameEquals(true);
/*     */           } 
/*     */           $$0.addPredicate(());
/*  87 */         }$$0 -> !$$0.hasNameEquals(), (Component)Component.translatable("argument.entity.options.name.description"));
/*     */     
/*  89 */     register("distance", $$0 -> {
/*     */           int $$1 = $$0.getReader().getCursor();
/*     */           MinMaxBounds.Doubles $$2 = MinMaxBounds.Doubles.fromReader($$0.getReader());
/*     */           if (($$2.min().isPresent() && ((Double)$$2.min().get()).doubleValue() < 0.0D) || ($$2.max().isPresent() && ((Double)$$2.max().get()).doubleValue() < 0.0D)) {
/*     */             $$0.getReader().setCursor($$1);
/*     */             throw ERROR_RANGE_NEGATIVE.createWithContext($$0.getReader());
/*     */           } 
/*     */           $$0.setDistance($$2);
/*     */           $$0.setWorldLimited();
/*  98 */         }$$0 -> $$0.getDistance().isAny(), (Component)Component.translatable("argument.entity.options.distance.description"));
/*     */     
/* 100 */     register("level", $$0 -> {
/*     */           int $$1 = $$0.getReader().getCursor();
/*     */           MinMaxBounds.Ints $$2 = MinMaxBounds.Ints.fromReader($$0.getReader());
/*     */           if (($$2.min().isPresent() && ((Integer)$$2.min().get()).intValue() < 0) || ($$2.max().isPresent() && ((Integer)$$2.max().get()).intValue() < 0)) {
/*     */             $$0.getReader().setCursor($$1);
/*     */             throw ERROR_LEVEL_NEGATIVE.createWithContext($$0.getReader());
/*     */           } 
/*     */           $$0.setLevel($$2);
/*     */           $$0.setIncludesEntities(false);
/* 109 */         }$$0 -> $$0.getLevel().isAny(), (Component)Component.translatable("argument.entity.options.level.description"));
/*     */     
/* 111 */     register("x", $$0 -> {
/*     */           $$0.setWorldLimited();
/*     */           $$0.setX($$0.getReader().readDouble());
/* 114 */         }$$0 -> ($$0.getX() == null), (Component)Component.translatable("argument.entity.options.x.description"));
/*     */     
/* 116 */     register("y", $$0 -> {
/*     */           $$0.setWorldLimited();
/*     */           $$0.setY($$0.getReader().readDouble());
/* 119 */         }$$0 -> ($$0.getY() == null), (Component)Component.translatable("argument.entity.options.y.description"));
/*     */     
/* 121 */     register("z", $$0 -> {
/*     */           $$0.setWorldLimited();
/*     */           $$0.setZ($$0.getReader().readDouble());
/* 124 */         }$$0 -> ($$0.getZ() == null), (Component)Component.translatable("argument.entity.options.z.description"));
/*     */     
/* 126 */     register("dx", $$0 -> {
/*     */           $$0.setWorldLimited();
/*     */           $$0.setDeltaX($$0.getReader().readDouble());
/* 129 */         }$$0 -> ($$0.getDeltaX() == null), (Component)Component.translatable("argument.entity.options.dx.description"));
/*     */     
/* 131 */     register("dy", $$0 -> {
/*     */           $$0.setWorldLimited();
/*     */           $$0.setDeltaY($$0.getReader().readDouble());
/* 134 */         }$$0 -> ($$0.getDeltaY() == null), (Component)Component.translatable("argument.entity.options.dy.description"));
/*     */     
/* 136 */     register("dz", $$0 -> {
/*     */           $$0.setWorldLimited();
/*     */           $$0.setDeltaZ($$0.getReader().readDouble());
/* 139 */         }$$0 -> ($$0.getDeltaZ() == null), (Component)Component.translatable("argument.entity.options.dz.description"));
/*     */     
/* 141 */     register("x_rotation", $$0 -> $$0.setRotX(WrappedMinMaxBounds.fromReader($$0.getReader(), true, Mth::wrapDegrees)), $$0 -> ($$0.getRotX() == WrappedMinMaxBounds.ANY), 
/*     */         
/* 143 */         (Component)Component.translatable("argument.entity.options.x_rotation.description"));
/*     */     
/* 145 */     register("y_rotation", $$0 -> $$0.setRotY(WrappedMinMaxBounds.fromReader($$0.getReader(), true, Mth::wrapDegrees)), $$0 -> ($$0.getRotY() == WrappedMinMaxBounds.ANY), 
/*     */         
/* 147 */         (Component)Component.translatable("argument.entity.options.y_rotation.description"));
/*     */     
/* 149 */     register("limit", $$0 -> {
/*     */           int $$1 = $$0.getReader().getCursor();
/*     */           int $$2 = $$0.getReader().readInt();
/*     */           if ($$2 < 1) {
/*     */             $$0.getReader().setCursor($$1);
/*     */             throw ERROR_LIMIT_TOO_SMALL.createWithContext($$0.getReader());
/*     */           } 
/*     */           $$0.setMaxResults($$2);
/*     */           $$0.setLimited(true);
/* 158 */         }$$0 -> (!$$0.isCurrentEntity() && !$$0.isLimited()), (Component)Component.translatable("argument.entity.options.limit.description"));
/*     */     
/* 160 */     register("sort", $$0 -> {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           // Byte code:
/*     */           //   0: aload_0
/*     */           //   1: invokevirtual getReader : ()Lcom/mojang/brigadier/StringReader;
/*     */           //   4: invokevirtual getCursor : ()I
/*     */           //   7: istore_1
/*     */           //   8: aload_0
/*     */           //   9: invokevirtual getReader : ()Lcom/mojang/brigadier/StringReader;
/*     */           //   12: invokevirtual readUnquotedString : ()Ljava/lang/String;
/*     */           //   15: astore_2
/*     */           //   16: aload_0
/*     */           //   17: <illegal opcode> apply : ()Ljava/util/function/BiFunction;
/*     */           //   22: invokevirtual setSuggestions : (Ljava/util/function/BiFunction;)V
/*     */           //   25: aload_0
/*     */           //   26: aload_2
/*     */           //   27: astore_3
/*     */           //   28: iconst_m1
/*     */           //   29: istore #4
/*     */           //   31: aload_3
/*     */           //   32: invokevirtual hashCode : ()I
/*     */           //   35: lookupswitch default -> 137, -938285885 -> 108, 1510793967 -> 92, 1780188658 -> 124, 1825779806 -> 76
/*     */           //   76: aload_3
/*     */           //   77: ldc_w 'nearest'
/*     */           //   80: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */           //   83: ifeq -> 137
/*     */           //   86: iconst_0
/*     */           //   87: istore #4
/*     */           //   89: goto -> 137
/*     */           //   92: aload_3
/*     */           //   93: ldc_w 'furthest'
/*     */           //   96: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */           //   99: ifeq -> 137
/*     */           //   102: iconst_1
/*     */           //   103: istore #4
/*     */           //   105: goto -> 137
/*     */           //   108: aload_3
/*     */           //   109: ldc_w 'random'
/*     */           //   112: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */           //   115: ifeq -> 137
/*     */           //   118: iconst_2
/*     */           //   119: istore #4
/*     */           //   121: goto -> 137
/*     */           //   124: aload_3
/*     */           //   125: ldc_w 'arbitrary'
/*     */           //   128: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */           //   131: ifeq -> 137
/*     */           //   134: iconst_3
/*     */           //   135: istore #4
/*     */           //   137: iload #4
/*     */           //   139: tableswitch default -> 192, 0 -> 168, 1 -> 174, 2 -> 180, 3 -> 186
/*     */           //   168: getstatic net/minecraft/commands/arguments/selector/EntitySelectorParser.ORDER_NEAREST : Ljava/util/function/BiConsumer;
/*     */           //   171: goto -> 212
/*     */           //   174: getstatic net/minecraft/commands/arguments/selector/EntitySelectorParser.ORDER_FURTHEST : Ljava/util/function/BiConsumer;
/*     */           //   177: goto -> 212
/*     */           //   180: getstatic net/minecraft/commands/arguments/selector/EntitySelectorParser.ORDER_RANDOM : Ljava/util/function/BiConsumer;
/*     */           //   183: goto -> 212
/*     */           //   186: getstatic net/minecraft/commands/arguments/selector/EntitySelector.ORDER_ARBITRARY : Ljava/util/function/BiConsumer;
/*     */           //   189: goto -> 212
/*     */           //   192: aload_0
/*     */           //   193: invokevirtual getReader : ()Lcom/mojang/brigadier/StringReader;
/*     */           //   196: iload_1
/*     */           //   197: invokevirtual setCursor : (I)V
/*     */           //   200: getstatic net/minecraft/commands/arguments/selector/options/EntitySelectorOptions.ERROR_SORT_UNKNOWN : Lcom/mojang/brigadier/exceptions/DynamicCommandExceptionType;
/*     */           //   203: aload_0
/*     */           //   204: invokevirtual getReader : ()Lcom/mojang/brigadier/StringReader;
/*     */           //   207: aload_2
/*     */           //   208: invokevirtual createWithContext : (Lcom/mojang/brigadier/ImmutableStringReader;Ljava/lang/Object;)Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
/*     */           //   211: athrow
/*     */           //   212: invokevirtual setOrder : (Ljava/util/function/BiConsumer;)V
/*     */           //   215: aload_0
/*     */           //   216: iconst_1
/*     */           //   217: invokevirtual setSorted : (Z)V
/*     */           //   220: return
/*     */           // Line number table:
/*     */           //   Java source line number -> byte code offset
/*     */           //   #161	-> 0
/*     */           //   #162	-> 8
/*     */           //   #163	-> 16
/*     */           //   #164	-> 25
/*     */           //   #165	-> 168
/*     */           //   #166	-> 174
/*     */           //   #167	-> 180
/*     */           //   #168	-> 186
/*     */           //   #170	-> 192
/*     */           //   #171	-> 200
/*     */           //   #164	-> 212
/*     */           //   #174	-> 215
/*     */           //   #175	-> 220
/*     */           // Local variable table:
/*     */           //   start	length	slot	name	descriptor
/*     */           //   0	221	0	$$0	Lnet/minecraft/commands/arguments/selector/EntitySelectorParser;
/*     */           //   8	213	1	$$1	I
/*     */           //   16	205	2	$$2	Ljava/lang/String;
/* 175 */         }$$0 -> (!$$0.isCurrentEntity() && !$$0.isSorted()), (Component)Component.translatable("argument.entity.options.sort.description"));
/*     */     
/* 177 */     register("gamemode", $$0 -> {
/*     */           $$0.setSuggestions(());
/*     */ 
/*     */ 
/*     */           
/*     */           int $$1 = $$0.getReader().getCursor();
/*     */ 
/*     */ 
/*     */           
/*     */           boolean $$2 = $$0.shouldInvertValue();
/*     */ 
/*     */ 
/*     */           
/*     */           if ($$0.hasGamemodeNotEquals() && !$$2) {
/*     */             $$0.getReader().setCursor($$1);
/*     */ 
/*     */ 
/*     */             
/*     */             throw ERROR_INAPPLICABLE_OPTION.createWithContext($$0.getReader(), "gamemode");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           String $$3 = $$0.getReader().readUnquotedString();
/*     */ 
/*     */ 
/*     */           
/*     */           GameType $$4 = GameType.byName($$3, null);
/*     */ 
/*     */ 
/*     */           
/*     */           if ($$4 == null) {
/*     */             $$0.getReader().setCursor($$1);
/*     */ 
/*     */ 
/*     */             
/*     */             throw ERROR_GAME_MODE_INVALID.createWithContext($$0.getReader(), $$3);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.setIncludesEntities(false);
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.addPredicate(());
/*     */ 
/*     */ 
/*     */           
/*     */           if ($$2) {
/*     */             $$0.setHasGamemodeNotEquals(true);
/*     */           } else {
/*     */             $$0.setHasGamemodeEquals(true);
/*     */           } 
/* 231 */         }$$0 -> !$$0.hasGamemodeEquals(), (Component)Component.translatable("argument.entity.options.gamemode.description"));
/*     */     
/* 233 */     register("team", $$0 -> {
/*     */           boolean $$1 = $$0.shouldInvertValue();
/*     */ 
/*     */ 
/*     */           
/*     */           String $$2 = $$0.getReader().readUnquotedString();
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.addPredicate(());
/*     */ 
/*     */           
/*     */           if ($$1) {
/*     */             $$0.setHasTeamNotEquals(true);
/*     */           } else {
/*     */             $$0.setHasTeamEquals(true);
/*     */           } 
/* 250 */         }$$0 -> !$$0.hasTeamEquals(), (Component)Component.translatable("argument.entity.options.team.description"));
/*     */     
/* 252 */     register("type", $$0 -> {
/*     */           $$0.setSuggestions(());
/*     */ 
/*     */           
/*     */           int $$1 = $$0.getReader().getCursor();
/*     */ 
/*     */           
/*     */           boolean $$2 = $$0.shouldInvertValue();
/*     */ 
/*     */           
/*     */           if ($$0.isTypeLimitedInversely() && !$$2) {
/*     */             $$0.getReader().setCursor($$1);
/*     */             
/*     */             throw ERROR_INAPPLICABLE_OPTION.createWithContext($$0.getReader(), "type");
/*     */           } 
/*     */           
/*     */           if ($$2) {
/*     */             $$0.setTypeLimitedInversely();
/*     */           }
/*     */           
/*     */           if ($$0.isTag()) {
/*     */             TagKey<EntityType<?>> $$3 = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.read($$0.getReader()));
/*     */             
/*     */             $$0.addPredicate(());
/*     */           } else {
/*     */             ResourceLocation $$4 = ResourceLocation.read($$0.getReader());
/*     */             
/*     */             EntityType<?> $$5 = (EntityType)BuiltInRegistries.ENTITY_TYPE.getOptional($$4).orElseThrow(());
/*     */             
/*     */             if (Objects.equals(EntityType.PLAYER, $$5) && !$$2) {
/*     */               $$0.setIncludesEntities(false);
/*     */             }
/*     */             
/*     */             $$0.addPredicate(());
/*     */             
/*     */             if (!$$2) {
/*     */               $$0.limitToType($$5);
/*     */             }
/*     */           } 
/* 291 */         }$$0 -> !$$0.isTypeLimited(), (Component)Component.translatable("argument.entity.options.type.description"));
/*     */     
/* 293 */     register("tag", $$0 -> {
/*     */           boolean $$1 = $$0.shouldInvertValue();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           String $$2 = $$0.getReader().readUnquotedString();
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.addPredicate(());
/* 304 */         }$$0 -> true, (Component)Component.translatable("argument.entity.options.tag.description"));
/*     */     
/* 306 */     register("nbt", $$0 -> {
/*     */           boolean $$1 = $$0.shouldInvertValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           CompoundTag $$2 = (new TagParser($$0.getReader())).readStruct();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.addPredicate(());
/* 320 */         }$$0 -> true, (Component)Component.translatable("argument.entity.options.nbt.description"));
/*     */     
/* 322 */     register("scores", $$0 -> {
/*     */           StringReader $$1 = $$0.getReader();
/*     */ 
/*     */           
/*     */           Map<String, MinMaxBounds.Ints> $$2 = Maps.newHashMap();
/*     */ 
/*     */           
/*     */           $$1.expect('{');
/*     */ 
/*     */           
/*     */           $$1.skipWhitespace();
/*     */ 
/*     */           
/*     */           while ($$1.canRead() && $$1.peek() != '}') {
/*     */             $$1.skipWhitespace();
/*     */ 
/*     */             
/*     */             String $$3 = $$1.readUnquotedString();
/*     */             
/*     */             $$1.skipWhitespace();
/*     */             
/*     */             $$1.expect('=');
/*     */             
/*     */             $$1.skipWhitespace();
/*     */             
/*     */             MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.fromReader($$1);
/*     */             
/*     */             $$2.put($$3, $$4);
/*     */             
/*     */             $$1.skipWhitespace();
/*     */             
/*     */             if ($$1.canRead() && $$1.peek() == ',') {
/*     */               $$1.skip();
/*     */             }
/*     */           } 
/*     */           
/*     */           $$1.expect('}');
/*     */           
/*     */           if (!$$2.isEmpty()) {
/*     */             $$0.addPredicate(());
/*     */           }
/*     */           
/*     */           $$0.setHasScores(true);
/* 365 */         }$$0 -> !$$0.hasScores(), (Component)Component.translatable("argument.entity.options.scores.description"));
/*     */     
/* 367 */     register("advancements", $$0 -> {
/*     */           StringReader $$1 = $$0.getReader();
/*     */           
/*     */           Map<ResourceLocation, Predicate<AdvancementProgress>> $$2 = Maps.newHashMap();
/*     */           
/*     */           $$1.expect('{');
/*     */           
/*     */           $$1.skipWhitespace();
/*     */           
/*     */           while ($$1.canRead() && $$1.peek() != '}') {
/*     */             $$1.skipWhitespace();
/*     */             
/*     */             ResourceLocation $$3 = ResourceLocation.read($$1);
/*     */             
/*     */             $$1.skipWhitespace();
/*     */             
/*     */             $$1.expect('=');
/*     */             
/*     */             $$1.skipWhitespace();
/*     */             
/*     */             if ($$1.canRead() && $$1.peek() == '{') {
/*     */               Map<String, Predicate<CriterionProgress>> $$4 = Maps.newHashMap();
/*     */               
/*     */               $$1.skipWhitespace();
/*     */               
/*     */               $$1.expect('{');
/*     */               
/*     */               $$1.skipWhitespace();
/*     */               
/*     */               while ($$1.canRead() && $$1.peek() != '}') {
/*     */                 $$1.skipWhitespace();
/*     */                 
/*     */                 String $$5 = $$1.readUnquotedString();
/*     */                 
/*     */                 $$1.skipWhitespace();
/*     */                 
/*     */                 $$1.expect('=');
/*     */                 
/*     */                 $$1.skipWhitespace();
/*     */                 
/*     */                 boolean $$6 = $$1.readBoolean();
/*     */                 
/*     */                 $$4.put($$5, ());
/*     */                 
/*     */                 $$1.skipWhitespace();
/*     */                 
/*     */                 if ($$1.canRead() && $$1.peek() == ',') {
/*     */                   $$1.skip();
/*     */                 }
/*     */               } 
/*     */               
/*     */               $$1.skipWhitespace();
/*     */               
/*     */               $$1.expect('}');
/*     */               
/*     */               $$1.skipWhitespace();
/*     */               
/*     */               $$2.put($$3, ());
/*     */             } else {
/*     */               boolean $$7 = $$1.readBoolean();
/*     */               
/*     */               $$2.put($$3, ());
/*     */             } 
/*     */             
/*     */             $$1.skipWhitespace();
/*     */             
/*     */             if ($$1.canRead() && $$1.peek() == ',') {
/*     */               $$1.skip();
/*     */             }
/*     */           } 
/*     */           
/*     */           $$1.expect('}');
/*     */           if (!$$2.isEmpty()) {
/*     */             $$0.addPredicate(());
/*     */             $$0.setIncludesEntities(false);
/*     */           } 
/*     */           $$0.setHasAdvancements(true);
/* 444 */         }$$0 -> !$$0.hasAdvancements(), (Component)Component.translatable("argument.entity.options.advancements.description"));
/*     */     
/* 446 */     register("predicate", $$0 -> {
/*     */           boolean $$1 = $$0.shouldInvertValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           ResourceLocation $$2 = ResourceLocation.read($$0.getReader());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.addPredicate(());
/* 467 */         }$$0 -> true, (Component)Component.translatable("argument.entity.options.predicate.description"));
/*     */   }
/*     */   
/*     */   public static Modifier get(EntitySelectorParser $$0, String $$1, int $$2) throws CommandSyntaxException {
/* 471 */     Option $$3 = OPTIONS.get($$1);
/* 472 */     if ($$3 != null) {
/* 473 */       if ($$3.canUse.test($$0)) {
/* 474 */         return $$3.modifier;
/*     */       }
/* 476 */       throw ERROR_INAPPLICABLE_OPTION.createWithContext($$0.getReader(), $$1);
/*     */     } 
/*     */     
/* 479 */     $$0.getReader().setCursor($$2);
/* 480 */     throw ERROR_UNKNOWN_OPTION.createWithContext($$0.getReader(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void suggestNames(EntitySelectorParser $$0, SuggestionsBuilder $$1) {
/* 485 */     String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 486 */     for (Map.Entry<String, Option> $$3 : OPTIONS.entrySet()) {
/* 487 */       if (((Option)$$3.getValue()).canUse.test($$0) && ((String)$$3.getKey()).toLowerCase(Locale.ROOT).startsWith($$2))
/* 488 */         $$1.suggest((String)$$3.getKey() + "=", (Message)((Option)$$3.getValue()).description); 
/*     */     } 
/*     */   } private static final class Option extends Record {
/*     */     final EntitySelectorOptions.Modifier modifier; final Predicate<EntitySelectorParser> canUse; final Component description; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions$Option;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #497	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions$Option;
/*     */     }
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions$Option;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #497	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions$Option;
/*     */     }
/* 497 */     Option(EntitySelectorOptions.Modifier $$0, Predicate<EntitySelectorParser> $$1, Component $$2) { this.modifier = $$0; this.canUse = $$1; this.description = $$2; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions$Option;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #497	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions$Option;
/* 497 */       //   0	8	1	$$0	Ljava/lang/Object; } public EntitySelectorOptions.Modifier modifier() { return this.modifier; } public Predicate<EntitySelectorParser> canUse() { return this.canUse; } public Component description() { return this.description; }
/*     */   
/*     */   }
/*     */   
/*     */   public static interface Modifier {
/*     */     void handle(EntitySelectorParser param1EntitySelectorParser) throws CommandSyntaxException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\selector\options\EntitySelectorOptions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */