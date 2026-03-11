/*     */ package net.minecraft.tags;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.FileToIdConverter;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.DependencySorter;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TagLoader<T> {
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   final Function<ResourceLocation, Optional<? extends T>> idToValue;
/*     */   private final String directory;
/*     */   
/*     */   public TagLoader(Function<ResourceLocation, Optional<? extends T>> $$0, String $$1) {
/*  37 */     this.idToValue = $$0;
/*  38 */     this.directory = $$1;
/*     */   }
/*     */   
/*     */   public Map<ResourceLocation, List<EntryWithSource>> load(ResourceManager $$0) {
/*  42 */     Map<ResourceLocation, List<EntryWithSource>> $$1 = Maps.newHashMap();
/*     */     
/*  44 */     FileToIdConverter $$2 = FileToIdConverter.json(this.directory);
/*     */     
/*  46 */     for (Map.Entry<ResourceLocation, List<Resource>> $$3 : (Iterable<Map.Entry<ResourceLocation, List<Resource>>>)$$2.listMatchingResourceStacks($$0).entrySet()) {
/*  47 */       ResourceLocation $$4 = $$3.getKey();
/*  48 */       ResourceLocation $$5 = $$2.fileToId($$4);
/*  49 */       for (Resource $$6 : $$3.getValue()) { 
/*  50 */         try { Reader $$7 = $$6.openAsReader(); 
/*  51 */           try { JsonElement $$8 = JsonParser.parseReader($$7);
/*  52 */             List<EntryWithSource> $$9 = $$1.computeIfAbsent($$5, $$0 -> new ArrayList());
/*  53 */             Objects.requireNonNull(LOGGER); TagFile $$10 = (TagFile)TagFile.CODEC.parse(new Dynamic((DynamicOps)JsonOps.INSTANCE, $$8)).getOrThrow(false, LOGGER::error);
/*  54 */             if ($$10.replace()) {
/*  55 */               $$9.clear();
/*     */             }
/*  57 */             String $$11 = $$6.sourcePackId();
/*  58 */             $$10.entries().forEach($$2 -> $$0.add(new EntryWithSource($$2, $$1)));
/*  59 */             if ($$7 != null) $$7.close();  } catch (Throwable throwable) { if ($$7 != null) try { $$7.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$12)
/*  60 */         { LOGGER.error("Couldn't read tag list {} from {} in data pack {}", new Object[] { $$5, $$4, $$6.sourcePackId(), $$12 }); }
/*     */          }
/*     */     
/*     */     } 
/*     */     
/*  65 */     return $$1;
/*     */   }
/*     */   public static final class EntryWithSource extends Record { final TagEntry entry; private final String source;
/*  68 */     public EntryWithSource(TagEntry $$0, String $$1) { this.entry = $$0; this.source = $$1; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/tags/TagLoader$EntryWithSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #68	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/tags/TagLoader$EntryWithSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/tags/TagLoader$EntryWithSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #68	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/tags/TagLoader$EntryWithSource;
/*  68 */       //   0	8	1	$$0	Ljava/lang/Object; } public TagEntry entry() { return this.entry; } public String source() { return this.source; }
/*     */     
/*     */     public String toString() {
/*  71 */       return "" + this.entry + " (from " + this.entry + ")";
/*     */     } }
/*     */   private static final class SortingEntry extends Record implements DependencySorter.Entry<ResourceLocation> { final List<TagLoader.EntryWithSource> entries;
/*     */     
/*  75 */     SortingEntry(List<TagLoader.EntryWithSource> $$0) { this.entries = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/tags/TagLoader$SortingEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #75	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  75 */       //   0	7	0	this	Lnet/minecraft/tags/TagLoader$SortingEntry; } public List<TagLoader.EntryWithSource> entries() { return this.entries; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/tags/TagLoader$SortingEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #75	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/tags/TagLoader$SortingEntry; }
/*     */     public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/tags/TagLoader$SortingEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #75	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/tags/TagLoader$SortingEntry;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; } public void visitRequiredDependencies(Consumer<ResourceLocation> $$0) {
/*  78 */       this.entries.forEach($$1 -> $$1.entry.visitRequiredDependencies($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public void visitOptionalDependencies(Consumer<ResourceLocation> $$0) {
/*  83 */       this.entries.forEach($$1 -> $$1.entry.visitOptionalDependencies($$0));
/*     */     } }
/*     */ 
/*     */   
/*     */   private Either<Collection<EntryWithSource>, Collection<T>> build(TagEntry.Lookup<T> $$0, List<EntryWithSource> $$1) {
/*  88 */     ImmutableSet.Builder<T> $$2 = ImmutableSet.builder();
/*  89 */     List<EntryWithSource> $$3 = new ArrayList<>();
/*  90 */     for (EntryWithSource $$4 : $$1) {
/*  91 */       Objects.requireNonNull($$2); if (!$$4.entry().build($$0, $$2::add)) {
/*  92 */         $$3.add($$4);
/*     */       }
/*     */     } 
/*  95 */     return $$3.isEmpty() ? Either.right($$2.build()) : Either.left($$3);
/*     */   }
/*     */   
/*     */   public Map<ResourceLocation, Collection<T>> build(Map<ResourceLocation, List<EntryWithSource>> $$0) {
/*  99 */     final Map<ResourceLocation, Collection<T>> newTags = Maps.newHashMap();
/*     */     
/* 101 */     TagEntry.Lookup<T> $$2 = new TagEntry.Lookup<T>()
/*     */       {
/*     */         @Nullable
/*     */         public T element(ResourceLocation $$0) {
/* 105 */           return ((Optional<T>)TagLoader.this.idToValue.apply($$0)).orElse(null);
/*     */         }
/*     */ 
/*     */         
/*     */         @Nullable
/*     */         public Collection<T> tag(ResourceLocation $$0) {
/* 111 */           return (Collection<T>)newTags.get($$0);
/*     */         }
/*     */       };
/*     */     
/* 115 */     DependencySorter<ResourceLocation, SortingEntry> $$3 = new DependencySorter();
/*     */     
/* 117 */     $$0.forEach(($$1, $$2) -> $$0.addEntry($$1, new SortingEntry($$2)));
/*     */     
/* 119 */     $$3.orderByDependencies(($$2, $$3) -> build($$0, $$3.entries).ifLeft(()).ifRight(()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     return $$1;
/*     */   }
/*     */   
/*     */   public Map<ResourceLocation, Collection<T>> loadAndBuild(ResourceManager $$0) {
/* 128 */     return build(load($$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagLoader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */