/*     */ package net.minecraft.commands.arguments.selector;
/*     */ 
/*     */ import com.google.common.primitives.Doubles;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
/*     */ import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntitySelectorParser
/*     */ {
/*     */   public static final char SYNTAX_SELECTOR_START = '@';
/*     */   private static final char SYNTAX_OPTIONS_START = '[';
/*     */   private static final char SYNTAX_OPTIONS_END = ']';
/*     */   public static final char SYNTAX_OPTIONS_KEY_VALUE_SEPARATOR = '=';
/*     */   private static final char SYNTAX_OPTIONS_SEPARATOR = ',';
/*     */   public static final char SYNTAX_NOT = '!';
/*     */   public static final char SYNTAX_TAG = '#';
/*     */   private static final char SELECTOR_NEAREST_PLAYER = 'p';
/*     */   private static final char SELECTOR_ALL_PLAYERS = 'a';
/*     */   private static final char SELECTOR_RANDOM_PLAYERS = 'r';
/*     */   private static final char SELECTOR_CURRENT_ENTITY = 's';
/*     */   private static final char SELECTOR_ALL_ENTITIES = 'e';
/*  49 */   public static final SimpleCommandExceptionType ERROR_INVALID_NAME_OR_UUID = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.invalid")); public static final DynamicCommandExceptionType ERROR_UNKNOWN_SELECTOR_TYPE; static {
/*  50 */     ERROR_UNKNOWN_SELECTOR_TYPE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.entity.selector.unknown", new Object[] { $$0 }));
/*  51 */   } public static final SimpleCommandExceptionType ERROR_SELECTORS_NOT_ALLOWED = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.selector.not_allowed"));
/*  52 */   public static final SimpleCommandExceptionType ERROR_MISSING_SELECTOR_TYPE = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.selector.missing")); public static final DynamicCommandExceptionType ERROR_EXPECTED_OPTION_VALUE; public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_NEAREST; public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_FURTHEST;
/*  53 */   public static final SimpleCommandExceptionType ERROR_EXPECTED_END_OF_OPTIONS = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.options.unterminated")); public static final BiConsumer<Vec3, List<? extends Entity>> ORDER_RANDOM; public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> SUGGEST_NOTHING; static {
/*  54 */     ERROR_EXPECTED_OPTION_VALUE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.entity.options.valueless", new Object[] { $$0 }));
/*     */     
/*  56 */     ORDER_NEAREST = (($$0, $$1) -> $$1.sort(()));
/*  57 */     ORDER_FURTHEST = (($$0, $$1) -> $$1.sort(()));
/*  58 */     ORDER_RANDOM = (($$0, $$1) -> Collections.shuffle($$1));
/*     */     
/*  60 */     SUGGEST_NOTHING = (($$0, $$1) -> $$0.buildFuture());
/*     */   }
/*     */   private final StringReader reader;
/*     */   private final boolean allowSelectors;
/*     */   private int maxResults;
/*     */   private boolean includesEntities;
/*     */   private boolean worldLimited;
/*  67 */   private MinMaxBounds.Doubles distance = MinMaxBounds.Doubles.ANY;
/*  68 */   private MinMaxBounds.Ints level = MinMaxBounds.Ints.ANY;
/*     */   @Nullable
/*     */   private Double x;
/*     */   @Nullable
/*     */   private Double y;
/*     */   @Nullable
/*     */   private Double z;
/*     */   @Nullable
/*     */   private Double deltaX;
/*     */   @Nullable
/*     */   private Double deltaY;
/*     */   @Nullable
/*     */   private Double deltaZ;
/*  81 */   private WrappedMinMaxBounds rotX = WrappedMinMaxBounds.ANY;
/*  82 */   private WrappedMinMaxBounds rotY = WrappedMinMaxBounds.ANY;
/*     */   private Predicate<Entity> predicate = $$0 -> true;
/*  84 */   private BiConsumer<Vec3, List<? extends Entity>> order = EntitySelector.ORDER_ARBITRARY;
/*     */   private boolean currentEntity;
/*     */   @Nullable
/*     */   private String playerName;
/*     */   private int startPosition;
/*     */   @Nullable
/*     */   private UUID entityUUID;
/*  91 */   private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> suggestions = SUGGEST_NOTHING;
/*     */   private boolean hasNameEquals;
/*     */   private boolean hasNameNotEquals;
/*     */   private boolean isLimited;
/*     */   private boolean isSorted;
/*     */   private boolean hasGamemodeEquals;
/*     */   private boolean hasGamemodeNotEquals;
/*     */   private boolean hasTeamEquals;
/*     */   private boolean hasTeamNotEquals;
/*     */   @Nullable
/*     */   private EntityType<?> type;
/*     */   private boolean typeInverse;
/*     */   private boolean hasScores;
/*     */   private boolean hasAdvancements;
/*     */   private boolean usesSelectors;
/*     */   
/*     */   public EntitySelectorParser(StringReader $$0) {
/* 108 */     this($$0, true);
/*     */   }
/*     */   
/*     */   public EntitySelectorParser(StringReader $$0, boolean $$1) {
/* 112 */     this.reader = $$0;
/* 113 */     this.allowSelectors = $$1;
/*     */   }
/*     */   public EntitySelector getSelector() {
/*     */     AABB $$3;
/*     */     Function<Vec3, Vec3> $$5;
/* 118 */     if (this.deltaX != null || this.deltaY != null || this.deltaZ != null) {
/* 119 */       AABB $$0 = createAabb((this.deltaX == null) ? 0.0D : this.deltaX.doubleValue(), (this.deltaY == null) ? 0.0D : this.deltaY.doubleValue(), (this.deltaZ == null) ? 0.0D : this.deltaZ.doubleValue());
/* 120 */     } else if (this.distance.max().isPresent()) {
/* 121 */       double $$1 = ((Double)this.distance.max().get()).doubleValue();
/* 122 */       AABB $$2 = new AABB(-$$1, -$$1, -$$1, $$1 + 1.0D, $$1 + 1.0D, $$1 + 1.0D);
/*     */     } else {
/* 124 */       $$3 = null;
/*     */     } 
/*     */     
/* 127 */     if (this.x == null && this.y == null && this.z == null) {
/* 128 */       Function<Vec3, Vec3> $$4 = $$0 -> $$0;
/*     */     } else {
/* 130 */       $$5 = ($$0 -> new Vec3((this.x == null) ? $$0.x : this.x.doubleValue(), (this.y == null) ? $$0.y : this.y.doubleValue(), (this.z == null) ? $$0.z : this.z.doubleValue()));
/*     */     } 
/* 132 */     return new EntitySelector(this.maxResults, this.includesEntities, this.worldLimited, this.predicate, this.distance, $$5, $$3, this.order, this.currentEntity, this.playerName, this.entityUUID, this.type, this.usesSelectors);
/*     */   }
/*     */   
/*     */   private AABB createAabb(double $$0, double $$1, double $$2) {
/* 136 */     boolean $$3 = ($$0 < 0.0D);
/* 137 */     boolean $$4 = ($$1 < 0.0D);
/* 138 */     boolean $$5 = ($$2 < 0.0D);
/* 139 */     double $$6 = $$3 ? $$0 : 0.0D;
/* 140 */     double $$7 = $$4 ? $$1 : 0.0D;
/* 141 */     double $$8 = $$5 ? $$2 : 0.0D;
/* 142 */     double $$9 = ($$3 ? 0.0D : $$0) + 1.0D;
/* 143 */     double $$10 = ($$4 ? 0.0D : $$1) + 1.0D;
/* 144 */     double $$11 = ($$5 ? 0.0D : $$2) + 1.0D;
/* 145 */     return new AABB($$6, $$7, $$8, $$9, $$10, $$11);
/*     */   }
/*     */   
/*     */   private void finalizePredicates() {
/* 149 */     if (this.rotX != WrappedMinMaxBounds.ANY) {
/* 150 */       this.predicate = this.predicate.and(createRotationPredicate(this.rotX, Entity::getXRot));
/*     */     }
/* 152 */     if (this.rotY != WrappedMinMaxBounds.ANY) {
/* 153 */       this.predicate = this.predicate.and(createRotationPredicate(this.rotY, Entity::getYRot));
/*     */     }
/* 155 */     if (!this.level.isAny()) {
/* 156 */       this.predicate = this.predicate.and($$0 -> !($$0 instanceof ServerPlayer) ? false : this.level.matches(((ServerPlayer)$$0).experienceLevel));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Predicate<Entity> createRotationPredicate(WrappedMinMaxBounds $$0, ToDoubleFunction<Entity> $$1) {
/* 166 */     double $$2 = Mth.wrapDegrees(($$0.min() == null) ? 0.0F : $$0.min().floatValue());
/* 167 */     double $$3 = Mth.wrapDegrees(($$0.max() == null) ? 359.0F : $$0.max().floatValue());
/* 168 */     return $$3 -> {
/*     */         double $$4 = Mth.wrapDegrees($$0.applyAsDouble($$3));
/*     */         
/* 171 */         return ($$1 > $$2) ? (($$4 >= $$1 || $$4 <= $$2)) : (
/*     */           
/* 173 */           ($$4 >= $$1 && $$4 <= $$2));
/*     */       };
/*     */   }
/*     */   
/*     */   protected void parseSelector() throws CommandSyntaxException {
/* 178 */     this.usesSelectors = true;
/* 179 */     this.suggestions = this::suggestSelector;
/* 180 */     if (!this.reader.canRead()) {
/* 181 */       throw ERROR_MISSING_SELECTOR_TYPE.createWithContext(this.reader);
/*     */     }
/* 183 */     int $$0 = this.reader.getCursor();
/* 184 */     char $$1 = this.reader.read();
/* 185 */     if ($$1 == 'p') {
/* 186 */       this.maxResults = 1;
/* 187 */       this.includesEntities = false;
/* 188 */       this.order = ORDER_NEAREST;
/* 189 */       limitToType(EntityType.PLAYER);
/* 190 */     } else if ($$1 == 'a') {
/* 191 */       this.maxResults = Integer.MAX_VALUE;
/* 192 */       this.includesEntities = false;
/* 193 */       this.order = EntitySelector.ORDER_ARBITRARY;
/* 194 */       limitToType(EntityType.PLAYER);
/* 195 */     } else if ($$1 == 'r') {
/* 196 */       this.maxResults = 1;
/* 197 */       this.includesEntities = false;
/* 198 */       this.order = ORDER_RANDOM;
/* 199 */       limitToType(EntityType.PLAYER);
/* 200 */     } else if ($$1 == 's') {
/* 201 */       this.maxResults = 1;
/* 202 */       this.includesEntities = true;
/* 203 */       this.currentEntity = true;
/* 204 */     } else if ($$1 == 'e') {
/* 205 */       this.maxResults = Integer.MAX_VALUE;
/* 206 */       this.includesEntities = true;
/* 207 */       this.order = EntitySelector.ORDER_ARBITRARY;
/* 208 */       this.predicate = Entity::isAlive;
/*     */     } else {
/* 210 */       this.reader.setCursor($$0);
/* 211 */       throw ERROR_UNKNOWN_SELECTOR_TYPE.createWithContext(this.reader, "@" + String.valueOf($$1));
/*     */     } 
/*     */     
/* 214 */     this.suggestions = this::suggestOpenOptions;
/* 215 */     if (this.reader.canRead() && this.reader.peek() == '[') {
/* 216 */       this.reader.skip();
/* 217 */       this.suggestions = this::suggestOptionsKeyOrClose;
/* 218 */       parseOptions();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void parseNameOrUUID() throws CommandSyntaxException {
/* 223 */     if (this.reader.canRead()) {
/* 224 */       this.suggestions = this::suggestName;
/*     */     }
/* 226 */     int $$0 = this.reader.getCursor();
/* 227 */     String $$1 = this.reader.readString();
/*     */     
/*     */     try {
/* 230 */       this.entityUUID = UUID.fromString($$1);
/* 231 */       this.includesEntities = true;
/* 232 */     } catch (IllegalArgumentException $$2) {
/* 233 */       if ($$1.isEmpty() || $$1.length() > 16) {
/* 234 */         this.reader.setCursor($$0);
/* 235 */         throw ERROR_INVALID_NAME_OR_UUID.createWithContext(this.reader);
/*     */       } 
/* 237 */       this.includesEntities = false;
/* 238 */       this.playerName = $$1;
/*     */     } 
/*     */     
/* 241 */     this.maxResults = 1;
/*     */   }
/*     */   
/*     */   protected void parseOptions() throws CommandSyntaxException {
/* 245 */     this.suggestions = this::suggestOptionsKey;
/* 246 */     this.reader.skipWhitespace();
/* 247 */     while (this.reader.canRead() && this.reader.peek() != ']') {
/* 248 */       this.reader.skipWhitespace();
/* 249 */       int $$0 = this.reader.getCursor();
/* 250 */       String $$1 = this.reader.readString();
/* 251 */       EntitySelectorOptions.Modifier $$2 = EntitySelectorOptions.get(this, $$1, $$0);
/* 252 */       this.reader.skipWhitespace();
/* 253 */       if (!this.reader.canRead() || this.reader.peek() != '=') {
/* 254 */         this.reader.setCursor($$0);
/* 255 */         throw ERROR_EXPECTED_OPTION_VALUE.createWithContext(this.reader, $$1);
/*     */       } 
/* 257 */       this.reader.skip();
/* 258 */       this.reader.skipWhitespace();
/*     */       
/* 260 */       this.suggestions = SUGGEST_NOTHING;
/* 261 */       $$2.handle(this);
/* 262 */       this.reader.skipWhitespace();
/*     */       
/* 264 */       this.suggestions = this::suggestOptionsNextOrClose;
/* 265 */       if (this.reader.canRead()) {
/* 266 */         if (this.reader.peek() == ',') {
/* 267 */           this.reader.skip();
/* 268 */           this.suggestions = this::suggestOptionsKey; continue;
/* 269 */         }  if (this.reader.peek() == ']') {
/*     */           break;
/*     */         }
/* 272 */         throw ERROR_EXPECTED_END_OF_OPTIONS.createWithContext(this.reader);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 277 */     if (this.reader.canRead()) {
/* 278 */       this.reader.skip();
/* 279 */       this.suggestions = SUGGEST_NOTHING;
/*     */     } else {
/* 281 */       throw ERROR_EXPECTED_END_OF_OPTIONS.createWithContext(this.reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldInvertValue() {
/* 286 */     this.reader.skipWhitespace();
/* 287 */     if (this.reader.canRead() && this.reader.peek() == '!') {
/* 288 */       this.reader.skip();
/* 289 */       this.reader.skipWhitespace();
/* 290 */       return true;
/*     */     } 
/* 292 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTag() {
/* 296 */     this.reader.skipWhitespace();
/* 297 */     if (this.reader.canRead() && this.reader.peek() == '#') {
/* 298 */       this.reader.skip();
/* 299 */       this.reader.skipWhitespace();
/* 300 */       return true;
/*     */     } 
/* 302 */     return false;
/*     */   }
/*     */   
/*     */   public StringReader getReader() {
/* 306 */     return this.reader;
/*     */   }
/*     */   
/*     */   public void addPredicate(Predicate<Entity> $$0) {
/* 310 */     this.predicate = this.predicate.and($$0);
/*     */   }
/*     */   
/*     */   public void setWorldLimited() {
/* 314 */     this.worldLimited = true;
/*     */   }
/*     */   
/*     */   public MinMaxBounds.Doubles getDistance() {
/* 318 */     return this.distance;
/*     */   }
/*     */   
/*     */   public void setDistance(MinMaxBounds.Doubles $$0) {
/* 322 */     this.distance = $$0;
/*     */   }
/*     */   
/*     */   public MinMaxBounds.Ints getLevel() {
/* 326 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(MinMaxBounds.Ints $$0) {
/* 330 */     this.level = $$0;
/*     */   }
/*     */   
/*     */   public WrappedMinMaxBounds getRotX() {
/* 334 */     return this.rotX;
/*     */   }
/*     */   
/*     */   public void setRotX(WrappedMinMaxBounds $$0) {
/* 338 */     this.rotX = $$0;
/*     */   }
/*     */   
/*     */   public WrappedMinMaxBounds getRotY() {
/* 342 */     return this.rotY;
/*     */   }
/*     */   
/*     */   public void setRotY(WrappedMinMaxBounds $$0) {
/* 346 */     this.rotY = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getX() {
/* 351 */     return this.x;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getY() {
/* 356 */     return this.y;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getZ() {
/* 361 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setX(double $$0) {
/* 365 */     this.x = Double.valueOf($$0);
/*     */   }
/*     */   
/*     */   public void setY(double $$0) {
/* 369 */     this.y = Double.valueOf($$0);
/*     */   }
/*     */   
/*     */   public void setZ(double $$0) {
/* 373 */     this.z = Double.valueOf($$0);
/*     */   }
/*     */   
/*     */   public void setDeltaX(double $$0) {
/* 377 */     this.deltaX = Double.valueOf($$0);
/*     */   }
/*     */   
/*     */   public void setDeltaY(double $$0) {
/* 381 */     this.deltaY = Double.valueOf($$0);
/*     */   }
/*     */   
/*     */   public void setDeltaZ(double $$0) {
/* 385 */     this.deltaZ = Double.valueOf($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getDeltaX() {
/* 390 */     return this.deltaX;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getDeltaY() {
/* 395 */     return this.deltaY;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getDeltaZ() {
/* 400 */     return this.deltaZ;
/*     */   }
/*     */   
/*     */   public void setMaxResults(int $$0) {
/* 404 */     this.maxResults = $$0;
/*     */   }
/*     */   
/*     */   public void setIncludesEntities(boolean $$0) {
/* 408 */     this.includesEntities = $$0;
/*     */   }
/*     */   
/*     */   public BiConsumer<Vec3, List<? extends Entity>> getOrder() {
/* 412 */     return this.order;
/*     */   }
/*     */   
/*     */   public void setOrder(BiConsumer<Vec3, List<? extends Entity>> $$0) {
/* 416 */     this.order = $$0;
/*     */   }
/*     */   
/*     */   public EntitySelector parse() throws CommandSyntaxException {
/* 420 */     this.startPosition = this.reader.getCursor();
/* 421 */     this.suggestions = this::suggestNameOrSelector;
/* 422 */     if (this.reader.canRead() && this.reader.peek() == '@') {
/* 423 */       if (!this.allowSelectors) {
/* 424 */         throw ERROR_SELECTORS_NOT_ALLOWED.createWithContext(this.reader);
/*     */       }
/* 426 */       this.reader.skip();
/* 427 */       parseSelector();
/*     */     } else {
/* 429 */       parseNameOrUUID();
/*     */     } 
/* 431 */     finalizePredicates();
/* 432 */     return getSelector();
/*     */   }
/*     */   
/*     */   private static void fillSelectorSuggestions(SuggestionsBuilder $$0) {
/* 436 */     $$0.suggest("@p", (Message)Component.translatable("argument.entity.selector.nearestPlayer"));
/* 437 */     $$0.suggest("@a", (Message)Component.translatable("argument.entity.selector.allPlayers"));
/* 438 */     $$0.suggest("@r", (Message)Component.translatable("argument.entity.selector.randomPlayer"));
/* 439 */     $$0.suggest("@s", (Message)Component.translatable("argument.entity.selector.self"));
/* 440 */     $$0.suggest("@e", (Message)Component.translatable("argument.entity.selector.allEntities"));
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestNameOrSelector(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 444 */     $$1.accept($$0);
/* 445 */     if (this.allowSelectors) {
/* 446 */       fillSelectorSuggestions($$0);
/*     */     }
/* 448 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestName(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 452 */     SuggestionsBuilder $$2 = $$0.createOffset(this.startPosition);
/* 453 */     $$1.accept($$2);
/* 454 */     return $$0.add($$2).buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestSelector(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 458 */     SuggestionsBuilder $$2 = $$0.createOffset($$0.getStart() - 1);
/* 459 */     fillSelectorSuggestions($$2);
/* 460 */     $$0.add($$2);
/* 461 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOpenOptions(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 465 */     $$0.suggest(String.valueOf('['));
/* 466 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOptionsKeyOrClose(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 470 */     $$0.suggest(String.valueOf(']'));
/* 471 */     EntitySelectorOptions.suggestNames(this, $$0);
/* 472 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOptionsKey(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 476 */     EntitySelectorOptions.suggestNames(this, $$0);
/* 477 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestOptionsNextOrClose(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 481 */     $$0.suggest(String.valueOf(','));
/* 482 */     $$0.suggest(String.valueOf(']'));
/* 483 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   private CompletableFuture<Suggestions> suggestEquals(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 487 */     $$0.suggest(String.valueOf('='));
/* 488 */     return $$0.buildFuture();
/*     */   }
/*     */   
/*     */   public boolean isCurrentEntity() {
/* 492 */     return this.currentEntity;
/*     */   }
/*     */   
/*     */   public void setSuggestions(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> $$0) {
/* 496 */     this.suggestions = $$0;
/*     */   }
/*     */   
/*     */   public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder $$0, Consumer<SuggestionsBuilder> $$1) {
/* 500 */     return this.suggestions.apply($$0.createOffset(this.reader.getCursor()), $$1);
/*     */   }
/*     */   
/*     */   public boolean hasNameEquals() {
/* 504 */     return this.hasNameEquals;
/*     */   }
/*     */   
/*     */   public void setHasNameEquals(boolean $$0) {
/* 508 */     this.hasNameEquals = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasNameNotEquals() {
/* 512 */     return this.hasNameNotEquals;
/*     */   }
/*     */   
/*     */   public void setHasNameNotEquals(boolean $$0) {
/* 516 */     this.hasNameNotEquals = $$0;
/*     */   }
/*     */   
/*     */   public boolean isLimited() {
/* 520 */     return this.isLimited;
/*     */   }
/*     */   
/*     */   public void setLimited(boolean $$0) {
/* 524 */     this.isLimited = $$0;
/*     */   }
/*     */   
/*     */   public boolean isSorted() {
/* 528 */     return this.isSorted;
/*     */   }
/*     */   
/*     */   public void setSorted(boolean $$0) {
/* 532 */     this.isSorted = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasGamemodeEquals() {
/* 536 */     return this.hasGamemodeEquals;
/*     */   }
/*     */   
/*     */   public void setHasGamemodeEquals(boolean $$0) {
/* 540 */     this.hasGamemodeEquals = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasGamemodeNotEquals() {
/* 544 */     return this.hasGamemodeNotEquals;
/*     */   }
/*     */   
/*     */   public void setHasGamemodeNotEquals(boolean $$0) {
/* 548 */     this.hasGamemodeNotEquals = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasTeamEquals() {
/* 552 */     return this.hasTeamEquals;
/*     */   }
/*     */   
/*     */   public void setHasTeamEquals(boolean $$0) {
/* 556 */     this.hasTeamEquals = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasTeamNotEquals() {
/* 560 */     return this.hasTeamNotEquals;
/*     */   }
/*     */   
/*     */   public void setHasTeamNotEquals(boolean $$0) {
/* 564 */     this.hasTeamNotEquals = $$0;
/*     */   }
/*     */   
/*     */   public void limitToType(EntityType<?> $$0) {
/* 568 */     this.type = $$0;
/*     */   }
/*     */   
/*     */   public void setTypeLimitedInversely() {
/* 572 */     this.typeInverse = true;
/*     */   }
/*     */   
/*     */   public boolean isTypeLimited() {
/* 576 */     return (this.type != null);
/*     */   }
/*     */   
/*     */   public boolean isTypeLimitedInversely() {
/* 580 */     return this.typeInverse;
/*     */   }
/*     */   
/*     */   public boolean hasScores() {
/* 584 */     return this.hasScores;
/*     */   }
/*     */   
/*     */   public void setHasScores(boolean $$0) {
/* 588 */     this.hasScores = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasAdvancements() {
/* 592 */     return this.hasAdvancements;
/*     */   }
/*     */   
/*     */   public void setHasAdvancements(boolean $$0) {
/* 596 */     this.hasAdvancements = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\selector\EntitySelectorParser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */