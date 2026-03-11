/*     */ package net.minecraft.data.tags;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.data.CachedOutput;
/*     */ import net.minecraft.data.DataProvider;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagBuilder;
/*     */ import net.minecraft.tags.TagEntry;
/*     */ import net.minecraft.tags.TagFile;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.tags.TagManager;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class TagsProvider<T> implements DataProvider {
/*  33 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   protected final PackOutput.PathProvider pathProvider;
/*     */   private final CompletableFuture<HolderLookup.Provider> lookupProvider;
/*  37 */   private final CompletableFuture<Void> contentsDone = new CompletableFuture<>();
/*     */   
/*     */   private final CompletableFuture<TagLookup<T>> parentProvider;
/*     */   protected final ResourceKey<? extends Registry<T>> registryKey;
/*  41 */   private final Map<ResourceLocation, TagBuilder> builders = Maps.newLinkedHashMap();
/*     */   
/*     */   protected TagsProvider(PackOutput $$0, ResourceKey<? extends Registry<T>> $$1, CompletableFuture<HolderLookup.Provider> $$2) {
/*  44 */     this($$0, $$1, $$2, CompletableFuture.completedFuture(TagLookup.empty()));
/*     */   }
/*     */   
/*     */   protected TagsProvider(PackOutput $$0, ResourceKey<? extends Registry<T>> $$1, CompletableFuture<HolderLookup.Provider> $$2, CompletableFuture<TagLookup<T>> $$3) {
/*  48 */     this.pathProvider = $$0.createPathProvider(PackOutput.Target.DATA_PACK, TagManager.getTagDir($$1));
/*  49 */     this.registryKey = $$1;
/*     */     
/*  51 */     this.parentProvider = $$3;
/*  52 */     this.lookupProvider = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/*  57 */     return "Tags for " + this.registryKey.location();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<?> run(CachedOutput $$0) {
/*  66 */     return createContentsProvider()
/*  67 */       .thenApply($$0 -> {
/*     */           this.contentsDone.complete(null);
/*     */           
/*     */           return $$0;
/*  71 */         }).thenCombineAsync(this.parentProvider, ($$0, $$1) -> new CombinedData($$0, $$1))
/*  72 */       .thenCompose($$1 -> {
/*     */           static final class CombinedData<T> extends Record {
/*     */             final HolderLookup.Provider contents; final TagsProvider.TagLookup<T> parent; CombinedData(HolderLookup.Provider $$0, TagsProvider.TagLookup<T> $$1) {
/*     */               this.contents = $$0;
/*     */               this.parent = $$1;
/*     */             } public final String toString() {
/*     */               // Byte code:
/*     */               //   0: aload_0
/*     */               //   1: <illegal opcode> toString : (Lnet/minecraft/data/tags/TagsProvider$1CombinedData;)Ljava/lang/String;
/*     */               //   6: areturn
/*     */               // Line number table:
/*     */               //   Java source line number -> byte code offset
/*     */               //   #64	-> 0
/*     */               // Local variable table:
/*     */               //   start	length	slot	name	descriptor
/*     */               //   0	7	0	this	Lnet/minecraft/data/tags/TagsProvider$1CombinedData;
/*     */               // Local variable type table:
/*     */               //   start	length	slot	name	signature
/*     */               //   0	7	0	this	Lnet/minecraft/data/tags/TagsProvider$1CombinedData<TT;>;
/*     */             } public final int hashCode() {
/*     */               // Byte code:
/*     */               //   0: aload_0
/*     */               //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/tags/TagsProvider$1CombinedData;)I
/*     */               //   6: ireturn
/*     */               // Line number table:
/*     */               //   Java source line number -> byte code offset
/*     */               //   #64	-> 0
/*     */               // Local variable table:
/*     */               //   start	length	slot	name	descriptor
/*     */               //   0	7	0	this	Lnet/minecraft/data/tags/TagsProvider$1CombinedData;
/*     */               // Local variable type table:
/*     */               //   start	length	slot	name	signature
/*     */               //   0	7	0	this	Lnet/minecraft/data/tags/TagsProvider$1CombinedData<TT;>;
/*     */             } public final boolean equals(Object $$0) {
/*     */               // Byte code:
/*     */               //   0: aload_0
/*     */               //   1: aload_1
/*     */               //   2: <illegal opcode> equals : (Lnet/minecraft/data/tags/TagsProvider$1CombinedData;Ljava/lang/Object;)Z
/*     */               //   7: ireturn
/*     */               // Line number table:
/*     */               //   Java source line number -> byte code offset
/*     */               //   #64	-> 0
/*     */               // Local variable table:
/*     */               //   start	length	slot	name	descriptor
/*     */               //   0	8	0	this	Lnet/minecraft/data/tags/TagsProvider$1CombinedData;
/*     */               //   0	8	1	$$0	Ljava/lang/Object;
/*     */               // Local variable type table:
/*     */               //   start	length	slot	name	signature
/*     */               //   0	8	0	this	Lnet/minecraft/data/tags/TagsProvider$1CombinedData<TT;>;
/*     */             }
/*     */             public HolderLookup.Provider contents() {
/*     */               return this.contents;
/*     */             }
/*     */             public TagsProvider.TagLookup<T> parent() {
/*     */               return this.parent;
/*     */             } };
/*     */           HolderLookup.RegistryLookup<T> $$2 = $$1.contents.lookupOrThrow(this.registryKey);
/*     */           Predicate<ResourceLocation> $$3 = ();
/*     */           Predicate<ResourceLocation> $$4 = ();
/*     */           return CompletableFuture.allOf((CompletableFuture<?>[])this.builders.entrySet().stream().map(()).toArray(()));
/*     */         });
/*     */   }
/*     */   protected TagAppender<T> tag(TagKey<T> $$0) {
/*  97 */     TagBuilder $$1 = getOrCreateRawBuilder($$0);
/*  98 */     return new TagAppender<>($$1);
/*     */   }
/*     */   
/*     */   protected TagBuilder getOrCreateRawBuilder(TagKey<T> $$0) {
/* 102 */     return this.builders.computeIfAbsent($$0.location(), $$0 -> TagBuilder.create());
/*     */   }
/*     */   
/*     */   public CompletableFuture<TagLookup<T>> contentsGetter() {
/* 106 */     return this.contentsDone.thenApply($$0 -> ());
/*     */   }
/*     */   
/*     */   protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
/* 110 */     return this.lookupProvider.thenApply($$0 -> {
/*     */           this.builders.clear();
/*     */           addTags($$0);
/*     */           return $$0;
/*     */         });
/*     */   }
/*     */   protected abstract void addTags(HolderLookup.Provider paramProvider);
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface TagLookup<T> extends Function<TagKey<T>, Optional<TagBuilder>> { static <T> TagLookup<T> empty() {
/* 120 */       return $$0 -> Optional.empty();
/*     */     }
/*     */     
/*     */     default boolean contains(TagKey<T> $$0) {
/* 124 */       return apply($$0).isPresent();
/*     */     } }
/*     */ 
/*     */   
/*     */   protected static class TagAppender<T> {
/*     */     private final TagBuilder builder;
/*     */     
/*     */     protected TagAppender(TagBuilder $$0) {
/* 132 */       this.builder = $$0;
/*     */     }
/*     */     
/*     */     public final TagAppender<T> add(ResourceKey<T> $$0) {
/* 136 */       this.builder.addElement($$0.location());
/* 137 */       return this;
/*     */     }
/*     */     
/*     */     @SafeVarargs
/*     */     public final TagAppender<T> add(ResourceKey<T>... $$0) {
/* 142 */       for (ResourceKey<T> $$1 : $$0) {
/* 143 */         this.builder.addElement($$1.location());
/*     */       }
/* 145 */       return this;
/*     */     }
/*     */     
/*     */     public TagAppender<T> addOptional(ResourceLocation $$0) {
/* 149 */       this.builder.addOptionalElement($$0);
/* 150 */       return this;
/*     */     }
/*     */     
/*     */     public TagAppender<T> addTag(TagKey<T> $$0) {
/* 154 */       this.builder.addTag($$0.location());
/* 155 */       return this;
/*     */     }
/*     */     
/*     */     public TagAppender<T> addOptionalTag(ResourceLocation $$0) {
/* 159 */       this.builder.addOptionalTag($$0);
/* 160 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\TagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */