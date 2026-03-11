/*     */ package net.minecraft.commands;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public interface SharedSuggestionProvider {
/*     */   public static class TextCoordinates {
/*  30 */     public static final TextCoordinates DEFAULT_LOCAL = new TextCoordinates("^", "^", "^");
/*     */     
/*  32 */     public static final TextCoordinates DEFAULT_GLOBAL = new TextCoordinates("~", "~", "~");
/*     */     
/*     */     public final String x;
/*     */     
/*     */     public final String y;
/*     */     
/*     */     public final String z;
/*     */     
/*     */     public TextCoordinates(String $$0, String $$1, String $$2) {
/*  41 */       this.x = $$0;
/*  42 */       this.y = $$1;
/*  43 */       this.z = $$2;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default Collection<String> getCustomTabSugggestions() {
/*  50 */     return getOnlinePlayerNames();
/*     */   }
/*     */   
/*     */   default Collection<String> getSelectedEntities() {
/*  54 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Collection<TextCoordinates> getRelevantCoordinates() {
/*  66 */     return Collections.singleton(TextCoordinates.DEFAULT_GLOBAL);
/*     */   }
/*     */   
/*     */   default Collection<TextCoordinates> getAbsoluteCoordinates() {
/*  70 */     return Collections.singleton(TextCoordinates.DEFAULT_GLOBAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ElementSuggestionType
/*     */   {
/*  80 */     TAGS,
/*  81 */     ELEMENTS,
/*  82 */     ALL;
/*     */ 
/*     */     
/*     */     public boolean shouldSuggestTags() {
/*  86 */       return (this == TAGS || this == ALL);
/*     */     }
/*     */     
/*     */     public boolean shouldSuggestElements() {
/*  90 */       return (this == ELEMENTS || this == ALL);
/*     */     }
/*     */   }
/*     */   
/*     */   default void suggestRegistryElements(Registry<?> $$0, ElementSuggestionType $$1, SuggestionsBuilder $$2) {
/*  95 */     if ($$1.shouldSuggestTags()) {
/*  96 */       suggestResource($$0.getTagNames().map(TagKey::location), $$2, "#");
/*     */     }
/*  98 */     if ($$1.shouldSuggestElements()) {
/*  99 */       suggestResource($$0.keySet(), $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> void filterResources(Iterable<T> $$0, String $$1, Function<T, ResourceLocation> $$2, Consumer<T> $$3) {
/* 108 */     boolean $$4 = ($$1.indexOf(':') > -1);
/* 109 */     for (T $$5 : $$0) {
/* 110 */       ResourceLocation $$6 = $$2.apply($$5);
/* 111 */       if ($$4) {
/* 112 */         String $$7 = $$6.toString();
/* 113 */         if (matchesSubStr($$1, $$7))
/* 114 */           $$3.accept($$5); 
/*     */         continue;
/*     */       } 
/* 117 */       if (matchesSubStr($$1, $$6.getNamespace()) || ($$6.getNamespace().equals("minecraft") && matchesSubStr($$1, $$6.getPath()))) {
/* 118 */         $$3.accept($$5);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static <T> void filterResources(Iterable<T> $$0, String $$1, String $$2, Function<T, ResourceLocation> $$3, Consumer<T> $$4) {
/* 125 */     if ($$1.isEmpty()) {
/* 126 */       $$0.forEach($$4);
/*     */     } else {
/* 128 */       String $$5 = Strings.commonPrefix($$1, $$2);
/* 129 */       if (!$$5.isEmpty()) {
/* 130 */         String $$6 = $$1.substring($$5.length());
/* 131 */         filterResources($$0, $$6, $$3, $$4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggestResource(Iterable<ResourceLocation> $$0, SuggestionsBuilder $$1, String $$2) {
/* 137 */     String $$3 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 138 */     filterResources($$0, $$3, $$2, $$0 -> $$0, $$2 -> $$0.suggest($$1 + $$1));
/* 139 */     return $$1.buildFuture();
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggestResource(Stream<ResourceLocation> $$0, SuggestionsBuilder $$1, String $$2) {
/* 143 */     Objects.requireNonNull($$0); return suggestResource($$0::iterator, $$1, $$2);
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggestResource(Iterable<ResourceLocation> $$0, SuggestionsBuilder $$1) {
/* 147 */     String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 148 */     filterResources($$0, $$2, $$0 -> $$0, $$1 -> $$0.suggest($$1.toString()));
/* 149 */     return $$1.buildFuture();
/*     */   }
/*     */   
/*     */   static <T> CompletableFuture<Suggestions> suggestResource(Iterable<T> $$0, SuggestionsBuilder $$1, Function<T, ResourceLocation> $$2, Function<T, Message> $$3) {
/* 153 */     String $$4 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 154 */     filterResources($$0, $$4, $$2, $$3 -> $$0.suggest(((ResourceLocation)$$1.apply($$3)).toString(), $$2.apply($$3)));
/* 155 */     return $$1.buildFuture();
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggestResource(Stream<ResourceLocation> $$0, SuggestionsBuilder $$1) {
/* 159 */     Objects.requireNonNull($$0); return suggestResource($$0::iterator, $$1);
/*     */   }
/*     */   
/*     */   static <T> CompletableFuture<Suggestions> suggestResource(Stream<T> $$0, SuggestionsBuilder $$1, Function<T, ResourceLocation> $$2, Function<T, Message> $$3) {
/* 163 */     Objects.requireNonNull($$0); return suggestResource($$0::iterator, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggestCoordinates(String $$0, Collection<TextCoordinates> $$1, SuggestionsBuilder $$2, Predicate<String> $$3) {
/* 167 */     List<String> $$4 = Lists.newArrayList();
/*     */     
/* 169 */     if (Strings.isNullOrEmpty($$0)) {
/* 170 */       for (TextCoordinates $$5 : $$1) {
/* 171 */         String $$6 = $$5.x + " " + $$5.x + " " + $$5.y;
/* 172 */         if ($$3.test($$6)) {
/* 173 */           $$4.add($$5.x);
/* 174 */           $$4.add($$5.x + " " + $$5.x);
/* 175 */           $$4.add($$6);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 179 */       String[] $$7 = $$0.split(" ");
/*     */       
/* 181 */       if ($$7.length == 1) {
/* 182 */         for (TextCoordinates $$8 : $$1) {
/* 183 */           String $$9 = $$7[0] + " " + $$7[0] + " " + $$8.y;
/* 184 */           if ($$3.test($$9)) {
/* 185 */             $$4.add($$7[0] + " " + $$7[0]);
/* 186 */             $$4.add($$9);
/*     */           } 
/*     */         } 
/* 189 */       } else if ($$7.length == 2) {
/* 190 */         for (TextCoordinates $$10 : $$1) {
/* 191 */           String $$11 = $$7[0] + " " + $$7[0] + " " + $$7[1];
/* 192 */           if ($$3.test($$11)) {
/* 193 */             $$4.add($$11);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 198 */     return suggest($$4, $$2);
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggest2DCoordinates(String $$0, Collection<TextCoordinates> $$1, SuggestionsBuilder $$2, Predicate<String> $$3) {
/* 202 */     List<String> $$4 = Lists.newArrayList();
/*     */     
/* 204 */     if (Strings.isNullOrEmpty($$0)) {
/* 205 */       for (TextCoordinates $$5 : $$1) {
/* 206 */         String $$6 = $$5.x + " " + $$5.x;
/* 207 */         if ($$3.test($$6)) {
/* 208 */           $$4.add($$5.x);
/* 209 */           $$4.add($$6);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 213 */       String[] $$7 = $$0.split(" ");
/* 214 */       if ($$7.length == 1) {
/* 215 */         for (TextCoordinates $$8 : $$1) {
/* 216 */           String $$9 = $$7[0] + " " + $$7[0];
/* 217 */           if ($$3.test($$9)) {
/* 218 */             $$4.add($$9);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 223 */     return suggest($$4, $$2);
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggest(Iterable<String> $$0, SuggestionsBuilder $$1) {
/* 227 */     String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 228 */     for (String $$3 : $$0) {
/* 229 */       if (matchesSubStr($$2, $$3.toLowerCase(Locale.ROOT))) {
/* 230 */         $$1.suggest($$3);
/*     */       }
/*     */     } 
/* 233 */     return $$1.buildFuture();
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggest(Stream<String> $$0, SuggestionsBuilder $$1) {
/* 237 */     String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 238 */     Objects.requireNonNull($$1); $$0.filter($$1 -> matchesSubStr($$0, $$1.toLowerCase(Locale.ROOT))).forEach($$1::suggest);
/* 239 */     return $$1.buildFuture();
/*     */   }
/*     */   
/*     */   static CompletableFuture<Suggestions> suggest(String[] $$0, SuggestionsBuilder $$1) {
/* 243 */     String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 244 */     for (String $$3 : $$0) {
/* 245 */       if (matchesSubStr($$2, $$3.toLowerCase(Locale.ROOT))) {
/* 246 */         $$1.suggest($$3);
/*     */       }
/*     */     } 
/* 249 */     return $$1.buildFuture();
/*     */   }
/*     */   
/*     */   static <T> CompletableFuture<Suggestions> suggest(Iterable<T> $$0, SuggestionsBuilder $$1, Function<T, String> $$2, Function<T, Message> $$3) {
/* 253 */     String $$4 = $$1.getRemaining().toLowerCase(Locale.ROOT);
/* 254 */     for (T $$5 : $$0) {
/* 255 */       String $$6 = $$2.apply($$5);
/* 256 */       if (matchesSubStr($$4, $$6.toLowerCase(Locale.ROOT))) {
/* 257 */         $$1.suggest($$6, $$3.apply($$5));
/*     */       }
/*     */     } 
/* 260 */     return $$1.buildFuture();
/*     */   }
/*     */   
/*     */   static boolean matchesSubStr(String $$0, String $$1) {
/* 264 */     int $$2 = 0;
/* 265 */     while (!$$1.startsWith($$0, $$2)) {
/* 266 */       $$2 = $$1.indexOf('_', $$2);
/* 267 */       if ($$2 < 0) {
/* 268 */         return false;
/*     */       }
/*     */       
/* 271 */       $$2++;
/*     */     } 
/*     */     
/* 274 */     return true;
/*     */   }
/*     */   
/*     */   Collection<String> getOnlinePlayerNames();
/*     */   
/*     */   Collection<String> getAllTeams();
/*     */   
/*     */   Stream<ResourceLocation> getAvailableSounds();
/*     */   
/*     */   Stream<ResourceLocation> getRecipeNames();
/*     */   
/*     */   CompletableFuture<Suggestions> customSuggestion(CommandContext<?> paramCommandContext);
/*     */   
/*     */   Set<ResourceKey<Level>> levels();
/*     */   
/*     */   RegistryAccess registryAccess();
/*     */   
/*     */   FeatureFlagSet enabledFeatures();
/*     */   
/*     */   CompletableFuture<Suggestions> suggestRegistryElements(ResourceKey<? extends Registry<?>> paramResourceKey, ElementSuggestionType paramElementSuggestionType, SuggestionsBuilder paramSuggestionsBuilder, CommandContext<?> paramCommandContext);
/*     */   
/*     */   boolean hasPermission(int paramInt);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\SharedSuggestionProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */