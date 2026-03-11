/*     */ package net.minecraft.server.packs.resources;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FallbackResourceManager implements ResourceManager {
/*  33 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  35 */   protected final List<PackEntry> fallbacks = Lists.newArrayList();
/*     */   private final PackType type;
/*     */   private final String namespace;
/*     */   
/*     */   public FallbackResourceManager(PackType $$0, String $$1) {
/*  40 */     this.type = $$0;
/*  41 */     this.namespace = $$1;
/*     */   }
/*     */   
/*     */   public void push(PackResources $$0) {
/*  45 */     pushInternal($$0.packId(), $$0, null);
/*     */   }
/*     */   
/*     */   public void push(PackResources $$0, Predicate<ResourceLocation> $$1) {
/*  49 */     pushInternal($$0.packId(), $$0, $$1);
/*     */   }
/*     */   
/*     */   public void pushFilterOnly(String $$0, Predicate<ResourceLocation> $$1) {
/*  53 */     pushInternal($$0, null, $$1);
/*     */   }
/*     */   
/*     */   private void pushInternal(String $$0, @Nullable PackResources $$1, @Nullable Predicate<ResourceLocation> $$2) {
/*  57 */     this.fallbacks.add(new PackEntry($$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getNamespaces() {
/*  62 */     return (Set<String>)ImmutableSet.of(this.namespace);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<Resource> getResource(ResourceLocation $$0) {
/*  68 */     for (int $$1 = this.fallbacks.size() - 1; $$1 >= 0; $$1--) {
/*  69 */       PackEntry $$2 = this.fallbacks.get($$1);
/*  70 */       PackResources $$3 = $$2.resources;
/*  71 */       if ($$3 != null) {
/*  72 */         IoSupplier<InputStream> $$4 = $$3.getResource(this.type, $$0);
/*  73 */         if ($$4 != null) {
/*  74 */           IoSupplier<ResourceMetadata> $$5 = createStackMetadataFinder($$0, $$1);
/*  75 */           return Optional.of(createResource($$3, $$0, $$4, $$5));
/*     */         } 
/*     */       } 
/*     */       
/*  79 */       if ($$2.isFiltered($$0)) {
/*  80 */         LOGGER.warn("Resource {} not found, but was filtered by pack {}", $$0, $$2.name);
/*  81 */         return Optional.empty();
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private static Resource createResource(PackResources $$0, ResourceLocation $$1, IoSupplier<InputStream> $$2, IoSupplier<ResourceMetadata> $$3) {
/*  89 */     return new Resource($$0, wrapForDebug($$1, $$0, $$2), $$3);
/*     */   }
/*     */   
/*     */   private static IoSupplier<InputStream> wrapForDebug(ResourceLocation $$0, PackResources $$1, IoSupplier<InputStream> $$2) {
/*  93 */     if (LOGGER.isDebugEnabled()) {
/*  94 */       return () -> new LeakedResourceWarningInputStream($$0.get(), $$1, $$2.packId());
/*     */     }
/*  96 */     return $$2;
/*     */   }
/*     */   
/*     */   private static class LeakedResourceWarningInputStream
/*     */     extends FilterInputStream {
/*     */     private final Supplier<String> message;
/*     */     private boolean closed;
/*     */     
/*     */     public LeakedResourceWarningInputStream(InputStream $$0, ResourceLocation $$1, String $$2) {
/* 105 */       super($$0);
/* 106 */       Exception $$3 = new Exception("Stacktrace");
/* 107 */       this.message = (() -> {
/*     */           StringWriter $$3 = new StringWriter();
/*     */           $$0.printStackTrace(new PrintWriter($$3));
/*     */           return "Leaked resource: '" + $$1 + "' loaded from pack: '" + $$2 + "'\n" + $$3;
/*     */         });
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 116 */       super.close();
/* 117 */       this.closed = true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 122 */       if (!this.closed) {
/* 123 */         FallbackResourceManager.LOGGER.warn("{}", this.message.get());
/*     */       }
/*     */       
/* 126 */       super.finalize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Resource> getResourceStack(ResourceLocation $$0) {
/* 132 */     ResourceLocation $$1 = getMetadataLocation($$0);
/* 133 */     List<Resource> $$2 = new ArrayList<>();
/*     */     
/* 135 */     boolean $$3 = false;
/* 136 */     String $$4 = null;
/*     */ 
/*     */     
/* 139 */     for (int $$5 = this.fallbacks.size() - 1; $$5 >= 0; $$5--) {
/* 140 */       PackEntry $$6 = this.fallbacks.get($$5);
/* 141 */       PackResources $$7 = $$6.resources;
/* 142 */       if ($$7 != null) {
/* 143 */         IoSupplier<InputStream> $$8 = $$7.getResource(this.type, $$0);
/* 144 */         if ($$8 != null) {
/*     */           IoSupplier<ResourceMetadata> $$10;
/* 146 */           if ($$3) {
/* 147 */             IoSupplier<ResourceMetadata> $$9 = ResourceMetadata.EMPTY_SUPPLIER;
/*     */           } else {
/* 149 */             $$10 = (() -> {
/*     */                 IoSupplier<InputStream> $$2 = $$0.getResource(this.type, $$1);
/*     */                 return ($$2 != null) ? parseMetadata($$2) : ResourceMetadata.EMPTY;
/*     */               });
/*     */           } 
/* 154 */           $$2.add(new Resource($$7, $$8, $$10));
/*     */         } 
/*     */       } 
/*     */       
/* 158 */       if ($$6.isFiltered($$0)) {
/* 159 */         $$4 = $$6.name; break;
/*     */       } 
/* 161 */       if ($$6.isFiltered($$1)) {
/* 162 */         $$3 = true;
/*     */       }
/*     */     } 
/*     */     
/* 166 */     if ($$2.isEmpty() && $$4 != null) {
/* 167 */       LOGGER.warn("Resource {} not found, but was filtered by pack {}", $$0, $$4);
/*     */     }
/*     */     
/* 170 */     return Lists.reverse($$2);
/*     */   }
/*     */   
/*     */   private static boolean isMetadata(ResourceLocation $$0) {
/* 174 */     return $$0.getPath().endsWith(".mcmeta");
/*     */   }
/*     */   
/*     */   private static ResourceLocation getResourceLocationFromMetadata(ResourceLocation $$0) {
/* 178 */     String $$1 = $$0.getPath().substring(0, $$0.getPath().length() - ".mcmeta".length());
/* 179 */     return $$0.withPath($$1);
/*     */   }
/*     */   
/*     */   static ResourceLocation getMetadataLocation(ResourceLocation $$0) {
/* 183 */     return $$0.withPath($$0.getPath() + ".mcmeta");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, Resource> listResources(String $$0, Predicate<ResourceLocation> $$1) {
/* 190 */     Map<ResourceLocation, ResourceWithSourceAndIndex> $$2 = new HashMap<>();
/* 191 */     Map<ResourceLocation, ResourceWithSourceAndIndex> $$3 = new HashMap<>();
/*     */     
/* 193 */     int $$4 = this.fallbacks.size();
/* 194 */     for (int $$5 = 0; $$5 < $$4; $$5++) {
/* 195 */       PackEntry $$6 = this.fallbacks.get($$5);
/* 196 */       $$6.filterAll($$2.keySet());
/* 197 */       $$6.filterAll($$3.keySet());
/*     */       
/* 199 */       PackResources $$7 = $$6.resources;
/* 200 */       if ($$7 != null) {
/* 201 */         int $$8 = $$5;
/* 202 */         $$7.listResources(this.type, this.namespace, $$0, ($$5, $$6) -> { if (isMetadata($$5)) { if ($$0.test(getResourceLocationFromMetadata($$5))) { static final class ResourceWithSourceAndIndex extends Record {
/*     */                     final PackResources packResources;
/*     */                     final IoSupplier<InputStream> resource;
/*     */                     final int packIndex;
/*     */                     ResourceWithSourceAndIndex(PackResources $$0, IoSupplier<InputStream> $$1, int $$2) { this.packResources = $$0; this.resource = $$1; this.packIndex = $$2; }
/*     */                     public final String toString() { // Byte code:
/*     */                       //   0: aload_0
/*     */                       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;)Ljava/lang/String;
/*     */                       //   6: areturn
/*     */                       // Line number table:
/*     */                       //   Java source line number -> byte code offset
/*     */                       //   #188	-> 0
/*     */                       // Local variable table:
/*     */                       //   start	length	slot	name	descriptor
/*     */                       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex; }
/*     */                     public final int hashCode() { // Byte code:
/*     */                       //   0: aload_0
/*     */                       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;)I
/*     */                       //   6: ireturn
/*     */                       // Line number table:
/*     */                       //   Java source line number -> byte code offset
/*     */                       //   #188	-> 0
/*     */                       // Local variable table:
/*     */                       //   start	length	slot	name	descriptor
/*     */                       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex; }
/*     */                     public final boolean equals(Object $$0) { // Byte code:
/*     */                       //   0: aload_0
/*     */                       //   1: aload_1
/*     */                       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;Ljava/lang/Object;)Z
/*     */                       //   7: ireturn
/*     */                       // Line number table:
/*     */                       //   Java source line number -> byte code offset
/*     */                       //   #188	-> 0
/*     */                       // Local variable table:
/*     */                       //   start	length	slot	name	descriptor
/*     */                       //   0	8	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$1ResourceWithSourceAndIndex;
/*     */                       //   0	8	1	$$0	Ljava/lang/Object; }
/*     */                     public PackResources packResources() { return this.packResources; } public IoSupplier<InputStream> resource() { return this.resource; } public int packIndex() { return this.packIndex; } }; $$1.put($$5, new ResourceWithSourceAndIndex($$2, $$6, $$3)); }
/*     */                  }
/*     */               else if ($$0.test($$5)) { $$4.put($$5, new ResourceWithSourceAndIndex($$2, $$6, $$3)); }
/*     */             
/*     */             });
/*     */       } 
/* 216 */     }  Map<ResourceLocation, Resource> $$9 = Maps.newTreeMap();
/* 217 */     $$2.forEach(($$2, $$3) -> {
/*     */           IoSupplier<ResourceMetadata> $$7;
/*     */           ResourceLocation $$4 = getMetadataLocation($$2);
/*     */           ResourceWithSourceAndIndex $$5 = (ResourceWithSourceAndIndex)$$0.get($$4);
/*     */           if ($$5 != null && $$5.packIndex >= $$3.packIndex) {
/*     */             IoSupplier<ResourceMetadata> $$6 = convertToMetadata($$5.resource);
/*     */           } else {
/*     */             $$7 = ResourceMetadata.EMPTY_SUPPLIER;
/*     */           } 
/*     */           $$1.put($$2, createResource($$3.packResources, $$2, $$3.resource, $$7));
/*     */         });
/* 228 */     return $$9;
/*     */   }
/*     */   
/*     */   private IoSupplier<ResourceMetadata> createStackMetadataFinder(ResourceLocation $$0, int $$1) {
/* 232 */     return () -> {
/*     */         ResourceLocation $$2 = getMetadataLocation($$0);
/*     */         for (int $$3 = this.fallbacks.size() - 1; $$3 >= $$1; $$3--) {
/*     */           PackEntry $$4 = this.fallbacks.get($$3);
/*     */           PackResources $$5 = $$4.resources;
/*     */           if ($$5 != null) {
/*     */             IoSupplier<InputStream> $$6 = $$5.getResource(this.type, $$2);
/*     */             if ($$6 != null) {
/*     */               return parseMetadata($$6);
/*     */             }
/*     */           } 
/*     */           if ($$4.isFiltered($$2)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         return ResourceMetadata.EMPTY;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IoSupplier<ResourceMetadata> convertToMetadata(IoSupplier<InputStream> $$0) {
/* 255 */     return () -> parseMetadata($$0);
/*     */   }
/*     */   
/*     */   private static ResourceMetadata parseMetadata(IoSupplier<InputStream> $$0) throws IOException {
/* 259 */     InputStream $$1 = $$0.get(); 
/* 260 */     try { ResourceMetadata resourceMetadata = ResourceMetadata.fromJsonStream($$1);
/* 261 */       if ($$1 != null) $$1.close();  return resourceMetadata; } catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 264 */      } private static final class EntryStack extends Record { final ResourceLocation fileLocation; private final ResourceLocation metadataLocation; final List<FallbackResourceManager.ResourceWithSource> fileSources; final Map<PackResources, IoSupplier<InputStream>> metaSources; public Map<PackResources, IoSupplier<InputStream>> metaSources() { return this.metaSources; } public List<FallbackResourceManager.ResourceWithSource> fileSources() { return this.fileSources; } public ResourceLocation metadataLocation() { return this.metadataLocation; } public ResourceLocation fileLocation() { return this.fileLocation; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #264	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #264	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #264	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 264 */       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$EntryStack; } private EntryStack(ResourceLocation $$0, ResourceLocation $$1, List<FallbackResourceManager.ResourceWithSource> $$2, Map<PackResources, IoSupplier<InputStream>> $$3) { this.fileLocation = $$0; this.metadataLocation = $$1; this.fileSources = $$2; this.metaSources = $$3; }
/*     */      EntryStack(ResourceLocation $$0) {
/* 266 */       this($$0, 
/*     */           
/* 268 */           FallbackResourceManager.getMetadataLocation($$0), new ArrayList<>(), (Map<PackResources, IoSupplier<InputStream>>)new Object2ObjectArrayMap());
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyPackFiltersToExistingResources(PackEntry $$0, Map<ResourceLocation, EntryStack> $$1) {
/* 276 */     for (EntryStack $$2 : $$1.values()) {
/* 277 */       if ($$0.isFiltered($$2.fileLocation)) {
/* 278 */         $$2.fileSources.clear(); continue;
/* 279 */       }  if ($$0.isFiltered($$2.metadataLocation())) {
/* 280 */         $$2.metaSources.clear();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void listPackResources(PackEntry $$0, String $$1, Predicate<ResourceLocation> $$2, Map<ResourceLocation, EntryStack> $$3) {
/* 286 */     PackResources $$4 = $$0.resources;
/* 287 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/* 290 */     $$4.listResources(this.type, this.namespace, $$1, ($$3, $$4) -> {
/*     */           if (isMetadata($$3)) {
/*     */             ResourceLocation $$5 = getResourceLocationFromMetadata($$3);
/*     */             if (!$$0.test($$5)) {
/*     */               return;
/*     */             }
/*     */             ((EntryStack)$$1.computeIfAbsent((K)$$5, EntryStack::new)).metaSources.put($$2, $$4);
/*     */           } else {
/*     */             if (!$$0.test($$3)) {
/*     */               return;
/*     */             }
/*     */             ((EntryStack)$$1.computeIfAbsent((K)$$3, EntryStack::new)).fileSources.add(new ResourceWithSource($$2, $$4));
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, List<Resource>> listResourceStacks(String $$0, Predicate<ResourceLocation> $$1) {
/* 308 */     Map<ResourceLocation, EntryStack> $$2 = Maps.newHashMap();
/*     */     
/* 310 */     for (PackEntry $$3 : this.fallbacks) {
/* 311 */       applyPackFiltersToExistingResources($$3, $$2);
/* 312 */       listPackResources($$3, $$0, $$1, $$2);
/*     */     } 
/*     */     
/* 315 */     TreeMap<ResourceLocation, List<Resource>> $$4 = Maps.newTreeMap();
/* 316 */     for (EntryStack $$5 : $$2.values()) {
/* 317 */       if ($$5.fileSources.isEmpty()) {
/*     */         continue;
/*     */       }
/*     */       
/* 321 */       List<Resource> $$6 = new ArrayList<>();
/* 322 */       for (ResourceWithSource $$7 : $$5.fileSources) {
/* 323 */         PackResources $$8 = $$7.source;
/* 324 */         IoSupplier<InputStream> $$9 = $$5.metaSources.get($$8);
/* 325 */         IoSupplier<ResourceMetadata> $$10 = ($$9 != null) ? convertToMetadata($$9) : ResourceMetadata.EMPTY_SUPPLIER;
/* 326 */         $$6.add(createResource($$8, $$5.fileLocation, $$7.resource, $$10));
/*     */       } 
/*     */       
/* 329 */       $$4.put($$5.fileLocation, $$6);
/*     */     } 
/* 331 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<PackResources> listPacks() {
/* 336 */     return this.fallbacks.stream().map($$0 -> $$0.resources).filter(Objects::nonNull); } private static final class PackEntry extends Record {
/*     */     final String name; @Nullable
/*     */     final PackResources resources; @Nullable
/* 339 */     private final Predicate<ResourceLocation> filter; PackEntry(String $$0, @Nullable PackResources $$1, @Nullable Predicate<ResourceLocation> $$2) { this.name = $$0; this.resources = $$1; this.filter = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #339	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #339	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #339	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$PackEntry;
/* 339 */       //   0	8	1	$$0	Ljava/lang/Object; } public String name() { return this.name; } @Nullable public PackResources resources() { return this.resources; } @Nullable public Predicate<ResourceLocation> filter() { return this.filter; }
/*     */      public void filterAll(Collection<ResourceLocation> $$0) {
/* 341 */       if (this.filter != null) {
/* 342 */         $$0.removeIf(this.filter);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean isFiltered(ResourceLocation $$0) {
/* 347 */       return (this.filter != null && this.filter.test($$0));
/*     */     }
/*     */   }
/*     */   private static final class ResourceWithSource extends Record { final PackResources source; final IoSupplier<InputStream> resource;
/* 351 */     ResourceWithSource(PackResources $$0, IoSupplier<InputStream> $$1) { this.source = $$0; this.resource = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #351	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #351	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #351	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/resources/FallbackResourceManager$ResourceWithSource;
/* 351 */       //   0	8	1	$$0	Ljava/lang/Object; } public PackResources source() { return this.source; } public IoSupplier<InputStream> resource() { return this.resource; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\FallbackResourceManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */