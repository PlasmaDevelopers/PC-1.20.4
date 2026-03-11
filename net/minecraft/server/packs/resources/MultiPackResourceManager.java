/*     */ package net.minecraft.server.packs.resources;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MultiPackResourceManager implements CloseableResourceManager {
/*  21 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Map<String, FallbackResourceManager> namespacedManagers;
/*     */   private final List<PackResources> packs;
/*     */   
/*     */   public MultiPackResourceManager(PackType $$0, List<PackResources> $$1) {
/*  27 */     this.packs = List.copyOf($$1);
/*     */     
/*  29 */     Map<String, FallbackResourceManager> $$2 = new HashMap<>();
/*     */     
/*  31 */     List<String> $$3 = $$1.stream().flatMap($$1 -> $$1.getNamespaces($$0).stream()).distinct().toList();
/*     */     
/*  33 */     for (PackResources $$4 : $$1) {
/*  34 */       ResourceFilterSection $$5 = getPackFilterSection($$4);
/*  35 */       Set<String> $$6 = $$4.getNamespaces($$0);
/*     */       
/*  37 */       Predicate<ResourceLocation> $$7 = ($$5 != null) ? ($$1 -> $$0.isPathFiltered($$1.getPath())) : null;
/*     */       
/*  39 */       for (String $$8 : $$3) {
/*  40 */         boolean $$9 = $$6.contains($$8);
/*  41 */         boolean $$10 = ($$5 != null && $$5.isNamespaceFiltered($$8));
/*  42 */         if ($$9 || $$10) {
/*  43 */           FallbackResourceManager $$11 = $$2.get($$8);
/*  44 */           if ($$11 == null) {
/*  45 */             $$11 = new FallbackResourceManager($$0, $$8);
/*  46 */             $$2.put($$8, $$11);
/*     */           } 
/*     */           
/*  49 */           if ($$9 && $$10) {
/*  50 */             $$11.push($$4, $$7); continue;
/*  51 */           }  if ($$9) {
/*  52 */             $$11.push($$4); continue;
/*     */           } 
/*  54 */           $$11.pushFilterOnly($$4.packId(), $$7);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  59 */     this.namespacedManagers = $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ResourceFilterSection getPackFilterSection(PackResources $$0) {
/*     */     try {
/*  65 */       return (ResourceFilterSection)$$0.getMetadataSection((MetadataSectionSerializer)ResourceFilterSection.TYPE);
/*  66 */     } catch (IOException $$1) {
/*  67 */       LOGGER.error("Failed to get filter section from pack {}", $$0.packId());
/*     */       
/*  69 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<String> getNamespaces() {
/*  74 */     return this.namespacedManagers.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Resource> getResource(ResourceLocation $$0) {
/*  79 */     ResourceManager $$1 = this.namespacedManagers.get($$0.getNamespace());
/*     */     
/*  81 */     if ($$1 != null) {
/*  82 */       return $$1.getResource($$0);
/*     */     }
/*     */     
/*  85 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Resource> getResourceStack(ResourceLocation $$0) {
/*  90 */     ResourceManager $$1 = this.namespacedManagers.get($$0.getNamespace());
/*     */     
/*  92 */     if ($$1 != null) {
/*  93 */       return $$1.getResourceStack($$0);
/*     */     }
/*  95 */     return List.of();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, Resource> listResources(String $$0, Predicate<ResourceLocation> $$1) {
/* 101 */     checkTrailingDirectoryPath($$0);
/*     */     
/* 103 */     Map<ResourceLocation, Resource> $$2 = new TreeMap<>();
/*     */ 
/*     */     
/* 106 */     for (FallbackResourceManager $$3 : this.namespacedManagers.values()) {
/* 107 */       $$2.putAll($$3.listResources($$0, $$1));
/*     */     }
/*     */     
/* 110 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ResourceLocation, List<Resource>> listResourceStacks(String $$0, Predicate<ResourceLocation> $$1) {
/* 115 */     checkTrailingDirectoryPath($$0);
/*     */     
/* 117 */     Map<ResourceLocation, List<Resource>> $$2 = new TreeMap<>();
/*     */ 
/*     */     
/* 120 */     for (FallbackResourceManager $$3 : this.namespacedManagers.values()) {
/* 121 */       $$2.putAll($$3.listResourceStacks($$0, $$1));
/*     */     }
/*     */     
/* 124 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkTrailingDirectoryPath(String $$0) {
/* 129 */     if ($$0.endsWith("/")) {
/* 130 */       throw new IllegalArgumentException("Trailing slash in path " + $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<PackResources> listPacks() {
/* 136 */     return this.packs.stream();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 141 */     this.packs.forEach(PackResources::close);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\MultiPackResourceManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */