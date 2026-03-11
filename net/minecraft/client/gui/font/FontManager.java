/*     */ package net.minecraft.client.gui.font;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.blaze3d.font.GlyphProvider;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.font.providers.GlyphProviderDefinition;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.FileToIdConverter;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.DependencySorter;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FontManager implements PreparableReloadListener, AutoCloseable {
/*  48 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final String FONTS_PATH = "fonts.json";
/*  50 */   public static final ResourceLocation MISSING_FONT = new ResourceLocation("minecraft", "missing");
/*  51 */   private static final FileToIdConverter FONT_DEFINITIONS = FileToIdConverter.json("font");
/*     */   
/*  53 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
/*     */   
/*     */   private final FontSet missingFontSet;
/*  56 */   private final List<GlyphProvider> providersToClose = new ArrayList<>();
/*  57 */   private final Map<ResourceLocation, FontSet> fontSets = new HashMap<>();
/*     */   private final TextureManager textureManager;
/*  59 */   private Map<ResourceLocation, ResourceLocation> renames = (Map<ResourceLocation, ResourceLocation>)ImmutableMap.of();
/*     */   
/*     */   public FontManager(TextureManager $$0) {
/*  62 */     this.textureManager = $$0;
/*  63 */     this.missingFontSet = (FontSet)Util.make(new FontSet($$0, MISSING_FONT), $$0 -> $$0.reload(Lists.newArrayList((Object[])new GlyphProvider[] { new AllMissingGlyphProvider() })));
/*     */   }
/*     */   private static final class BuilderId extends Record { private final ResourceLocation fontId; private final String pack; private final int index;
/*  66 */     BuilderId(ResourceLocation $$0, String $$1, int $$2) { this.fontId = $$0; this.pack = $$1; this.index = $$2; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/FontManager$BuilderId;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$BuilderId; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/FontManager$BuilderId;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/FontManager$BuilderId;
/*  66 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation fontId() { return this.fontId; } public String pack() { return this.pack; } public int index() { return this.index; }
/*     */     
/*     */     public String toString() {
/*  69 */       return "(" + this.fontId + ": builder #" + this.index + " from pack " + this.pack + ")";
/*     */     } }
/*     */   private static final class BuilderResult extends Record { private final FontManager.BuilderId id; final Either<CompletableFuture<Optional<GlyphProvider>>, ResourceLocation> result;
/*     */     
/*  73 */     BuilderResult(FontManager.BuilderId $$0, Either<CompletableFuture<Optional<GlyphProvider>>, ResourceLocation> $$1) { this.id = $$0; this.result = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/FontManager$BuilderResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #73	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  73 */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$BuilderResult; } public FontManager.BuilderId id() { return this.id; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/FontManager$BuilderResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #73	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$BuilderResult; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/FontManager$BuilderResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #73	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/FontManager$BuilderResult;
/*  73 */       //   0	8	1	$$0	Ljava/lang/Object; } public Either<CompletableFuture<Optional<GlyphProvider>>, ResourceLocation> result() { return this.result; }
/*     */      public Optional<List<GlyphProvider>> resolve(Function<ResourceLocation, List<GlyphProvider>> $$0) {
/*  75 */       return (Optional<List<GlyphProvider>>)this.result.map($$0 -> ((Optional)$$0.join()).map(List::of), $$1 -> {
/*     */             List<GlyphProvider> $$2 = $$0.apply($$1);
/*     */             if ($$2 == null) {
/*     */               FontManager.LOGGER.warn("Can't find font {} referenced by builder {}, either because it's missing, failed to load or is part of loading cycle", $$1, this.id);
/*     */               return Optional.empty();
/*     */             } 
/*     */             return Optional.of($$2);
/*     */           });
/*     */     } }
/*     */   
/*     */   private static final class UnresolvedBuilderBundle extends Record implements DependencySorter.Entry<ResourceLocation> { final ResourceLocation fontId;
/*     */     private final List<FontManager.BuilderResult> builders;
/*     */     private final Set<ResourceLocation> dependencies;
/*     */     
/*  89 */     private UnresolvedBuilderBundle(ResourceLocation $$0, List<FontManager.BuilderResult> $$1, Set<ResourceLocation> $$2) { this.fontId = $$0; this.builders = $$1; this.dependencies = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;
/*  89 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation fontId() { return this.fontId; } public List<FontManager.BuilderResult> builders() { return this.builders; } public Set<ResourceLocation> dependencies() { return this.dependencies; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public UnresolvedBuilderBundle(ResourceLocation $$0) {
/*  95 */       this($$0, new ArrayList<>(), new HashSet<>());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(FontManager.BuilderId $$0, GlyphProviderDefinition.Reference $$1) {
/* 103 */       this.builders.add(new FontManager.BuilderResult($$0, Either.right($$1.id())));
/* 104 */       this.dependencies.add($$1.id());
/*     */     }
/*     */     
/*     */     public void add(FontManager.BuilderId $$0, CompletableFuture<Optional<GlyphProvider>> $$1) {
/* 108 */       this.builders.add(new FontManager.BuilderResult($$0, Either.left($$1)));
/*     */     }
/*     */     
/*     */     private Stream<CompletableFuture<Optional<GlyphProvider>>> listBuilders() {
/* 112 */       return this.builders.stream().flatMap($$0 -> $$0.result.left().stream());
/*     */     }
/*     */     
/*     */     public Optional<List<GlyphProvider>> resolve(Function<ResourceLocation, List<GlyphProvider>> $$0) {
/* 116 */       List<GlyphProvider> $$1 = new ArrayList<>();
/* 117 */       for (FontManager.BuilderResult $$2 : this.builders) {
/* 118 */         Optional<List<GlyphProvider>> $$3 = $$2.resolve($$0);
/* 119 */         if ($$3.isPresent()) {
/* 120 */           $$1.addAll($$3.get()); continue;
/*     */         } 
/* 122 */         return Optional.empty();
/*     */       } 
/*     */       
/* 125 */       return Optional.of($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void visitRequiredDependencies(Consumer<ResourceLocation> $$0) {
/* 130 */       this.dependencies.forEach($$0);
/*     */     }
/*     */     
/*     */     public void visitOptionalDependencies(Consumer<ResourceLocation> $$0) {} }
/*     */ 
/*     */   
/*     */   private static final class Preparation extends Record { private final Map<ResourceLocation, List<GlyphProvider>> providers;
/*     */     final List<GlyphProvider> allProviders;
/*     */     
/* 139 */     Preparation(Map<ResourceLocation, List<GlyphProvider>> $$0, List<GlyphProvider> $$1) { this.providers = $$0; this.allProviders = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/FontManager$Preparation;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #139	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$Preparation; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/FontManager$Preparation;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #139	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$Preparation; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/FontManager$Preparation;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #139	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/FontManager$Preparation;
/* 139 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<ResourceLocation, List<GlyphProvider>> providers() { return this.providers; } public List<GlyphProvider> allProviders() { return this.allProviders; }
/*     */      }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/* 144 */     $$2.startTick();
/* 145 */     $$2.endTick();
/*     */ 
/*     */     
/* 148 */     Objects.requireNonNull($$0); return prepare($$1, $$4).thenCompose($$0::wait)
/* 149 */       .thenAcceptAsync($$1 -> apply($$1, $$0), $$5);
/*     */   }
/*     */   
/*     */   private CompletableFuture<Preparation> prepare(ResourceManager $$0, Executor $$1) {
/* 153 */     List<CompletableFuture<UnresolvedBuilderBundle>> $$2 = new ArrayList<>();
/*     */     
/* 155 */     for (Map.Entry<ResourceLocation, List<Resource>> $$3 : (Iterable<Map.Entry<ResourceLocation, List<Resource>>>)FONT_DEFINITIONS.listMatchingResourceStacks($$0).entrySet()) {
/* 156 */       ResourceLocation $$4 = FONT_DEFINITIONS.fileToId($$3.getKey());
/* 157 */       $$2.add(CompletableFuture.supplyAsync(() -> { List<Pair<BuilderId, GlyphProviderDefinition>> $$4 = loadResourceStack((List<Resource>)$$0.getValue(), $$1); UnresolvedBuilderBundle $$5 = new UnresolvedBuilderBundle($$1); for (Pair<BuilderId, GlyphProviderDefinition> $$6 : $$4) { BuilderId $$7 = (BuilderId)$$6.getFirst(); ((GlyphProviderDefinition)$$6.getSecond()).unpack().ifLeft(()).ifRight(()); }  return $$5; }$$1));
/*     */     } 
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
/*     */ 
/*     */     
/* 174 */     return Util.sequence($$2).thenCompose($$1 -> {
/*     */           List<CompletableFuture<Optional<GlyphProvider>>> $$2 = (List<CompletableFuture<Optional<GlyphProvider>>>)$$1.stream().flatMap(UnresolvedBuilderBundle::listBuilders).collect(Collectors.toCollection(ArrayList::new));
/*     */           GlyphProvider $$3 = new AllMissingGlyphProvider();
/*     */           $$2.add(CompletableFuture.completedFuture(Optional.of($$3)));
/*     */           return Util.sequence($$2).thenCompose(());
/*     */         });
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
/*     */   
/*     */   private CompletableFuture<Optional<GlyphProvider>> safeLoad(BuilderId $$0, GlyphProviderDefinition.Loader $$1, ResourceManager $$2, Executor $$3) {
/* 192 */     return CompletableFuture.supplyAsync(() -> {
/*     */           try {
/*     */             return Optional.of($$0.load($$1));
/* 195 */           } catch (Exception $$3) {
/*     */             LOGGER.warn("Failed to load builder {}, rejecting", $$2, $$3);
/*     */             return Optional.empty();
/*     */           } 
/*     */         }$$3);
/*     */   }
/*     */   
/*     */   private Map<ResourceLocation, List<GlyphProvider>> resolveProviders(List<UnresolvedBuilderBundle> $$0) {
/* 203 */     Map<ResourceLocation, List<GlyphProvider>> $$1 = new HashMap<>();
/*     */     
/* 205 */     DependencySorter<ResourceLocation, UnresolvedBuilderBundle> $$2 = new DependencySorter();
/* 206 */     $$0.forEach($$1 -> $$0.addEntry($$1.fontId, $$1));
/*     */     
/* 208 */     $$2.orderByDependencies(($$1, $$2) -> { Objects.requireNonNull($$0); $$2.resolve($$0::get).ifPresent(());
/* 209 */         }); return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void finalizeProviderLoading(List<GlyphProvider> $$0, GlyphProvider $$1) {
/* 214 */     $$0.add(0, $$1);
/*     */     
/* 216 */     IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
/* 217 */     for (GlyphProvider $$3 : $$0) {
/* 218 */       intOpenHashSet.addAll((IntCollection)$$3.getSupportedGlyphs());
/*     */     }
/*     */ 
/*     */     
/* 222 */     intOpenHashSet.forEach($$1 -> {
/*     */           if ($$1 == 32) {
/*     */             return;
/*     */           }
/*     */           for (GlyphProvider $$2 : Lists.reverse($$0)) {
/*     */             if ($$2.getGlyph($$1) != null) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void apply(Preparation $$0, ProfilerFiller $$1) {
/* 235 */     $$1.startTick();
/*     */     
/* 237 */     $$1.push("closing");
/*     */     
/* 239 */     this.fontSets.values().forEach(FontSet::close);
/* 240 */     this.fontSets.clear();
/*     */     
/* 242 */     this.providersToClose.forEach(GlyphProvider::close);
/* 243 */     this.providersToClose.clear();
/*     */     
/* 245 */     $$1.popPush("reloading");
/* 246 */     $$0.providers().forEach(($$0, $$1) -> {
/*     */           FontSet $$2 = new FontSet(this.textureManager, $$0);
/*     */           
/*     */           $$2.reload(Lists.reverse($$1));
/*     */           this.fontSets.put($$0, $$2);
/*     */         });
/* 252 */     this.providersToClose.addAll($$0.allProviders);
/*     */     
/* 254 */     $$1.pop();
/* 255 */     $$1.endTick();
/*     */     
/* 257 */     if (!this.fontSets.containsKey(getActualId(Minecraft.DEFAULT_FONT)))
/*     */     {
/* 259 */       throw new IllegalStateException("Default font failed to load");
/*     */     }
/*     */   }
/*     */   
/*     */   private static List<Pair<BuilderId, GlyphProviderDefinition>> loadResourceStack(List<Resource> $$0, ResourceLocation $$1) {
/* 264 */     List<Pair<BuilderId, GlyphProviderDefinition>> $$2 = new ArrayList<>();
/*     */     
/* 266 */     for (Resource $$3 : $$0) { 
/* 267 */       try { Reader $$4 = $$3.openAsReader(); 
/* 268 */         try { JsonElement $$5 = (JsonElement)GSON.fromJson($$4, JsonElement.class);
/* 269 */           FontDefinitionFile $$6 = (FontDefinitionFile)Util.getOrThrow(FontDefinitionFile.CODEC.parse((DynamicOps)JsonOps.INSTANCE, $$5), com.google.gson.JsonParseException::new);
/* 270 */           List<GlyphProviderDefinition> $$7 = $$6.providers;
/*     */           
/* 272 */           for (int $$8 = $$7.size() - 1; $$8 >= 0; $$8--) {
/* 273 */             BuilderId $$9 = new BuilderId($$1, $$3.sourcePackId(), $$8);
/* 274 */             $$2.add(Pair.of($$9, $$7.get($$8)));
/*     */           } 
/* 276 */           if ($$4 != null) $$4.close();  } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$10)
/* 277 */       { LOGGER.warn("Unable to load font '{}' in {} in resourcepack: '{}'", new Object[] { $$1, "fonts.json", $$3.sourcePackId(), $$10 }); }
/*     */        }
/*     */     
/* 280 */     return $$2;
/*     */   }
/*     */   
/*     */   public void setRenames(Map<ResourceLocation, ResourceLocation> $$0) {
/* 284 */     this.renames = $$0;
/*     */   }
/*     */   
/*     */   private ResourceLocation getActualId(ResourceLocation $$0) {
/* 288 */     return this.renames.getOrDefault($$0, $$0);
/*     */   }
/*     */   
/*     */   public Font createFont() {
/* 292 */     return new Font($$0 -> (FontSet)this.fontSets.getOrDefault(getActualId($$0), this.missingFontSet), false);
/*     */   }
/*     */   
/*     */   public Font createFontFilterFishy() {
/* 296 */     return new Font($$0 -> (FontSet)this.fontSets.getOrDefault(getActualId($$0), this.missingFontSet), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 301 */     this.fontSets.values().forEach(FontSet::close);
/* 302 */     this.providersToClose.forEach(GlyphProvider::close);
/* 303 */     this.missingFontSet.close();
/*     */   }
/*     */   private static final class FontDefinitionFile extends Record { final List<GlyphProviderDefinition> providers; public static final Codec<FontDefinitionFile> CODEC;
/* 306 */     private FontDefinitionFile(List<GlyphProviderDefinition> $$0) { this.providers = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/FontManager$FontDefinitionFile;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #306	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$FontDefinitionFile; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/FontManager$FontDefinitionFile;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #306	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$FontDefinitionFile; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/FontManager$FontDefinitionFile;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #306	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/FontManager$FontDefinitionFile;
/* 306 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<GlyphProviderDefinition> providers() { return this.providers; } static {
/* 307 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)GlyphProviderDefinition.CODEC.listOf().fieldOf("providers").forGetter(FontDefinitionFile::providers)).apply((Applicative)$$0, FontDefinitionFile::new));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\FontManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */