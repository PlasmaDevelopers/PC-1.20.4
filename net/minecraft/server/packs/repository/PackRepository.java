/*     */ package net.minecraft.server.packs.repository;
/*     */ 
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ 
/*     */ public class PackRepository {
/*  23 */   private Map<String, Pack> available = (Map<String, Pack>)ImmutableMap.of(); private final Set<RepositorySource> sources;
/*  24 */   private List<Pack> selected = (List<Pack>)ImmutableList.of();
/*     */   
/*     */   public PackRepository(RepositorySource... $$0) {
/*  27 */     this.sources = (Set<RepositorySource>)ImmutableSet.copyOf((Object[])$$0);
/*     */   }
/*     */   
/*     */   public void reload() {
/*  31 */     List<String> $$0 = (List<String>)this.selected.stream().map(Pack::getId).collect(ImmutableList.toImmutableList());
/*  32 */     this.available = discoverAvailable();
/*  33 */     this.selected = rebuildSelected($$0);
/*     */   }
/*     */   
/*     */   private Map<String, Pack> discoverAvailable() {
/*  37 */     Map<String, Pack> $$0 = Maps.newTreeMap();
/*  38 */     for (RepositorySource $$1 : this.sources) {
/*  39 */       $$1.loadPacks($$1 -> $$0.put($$1.getId(), $$1));
/*     */     }
/*  41 */     return (Map<String, Pack>)ImmutableMap.copyOf($$0);
/*     */   }
/*     */   
/*     */   public void setSelected(Collection<String> $$0) {
/*  45 */     this.selected = rebuildSelected($$0);
/*     */   }
/*     */   
/*     */   public boolean addPack(String $$0) {
/*  49 */     Pack $$1 = this.available.get($$0);
/*  50 */     if ($$1 != null && !this.selected.contains($$1)) {
/*  51 */       List<Pack> $$2 = Lists.newArrayList(this.selected);
/*  52 */       $$2.add($$1);
/*  53 */       this.selected = $$2;
/*  54 */       return true;
/*     */     } 
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   public boolean removePack(String $$0) {
/*  60 */     Pack $$1 = this.available.get($$0);
/*  61 */     if ($$1 != null && this.selected.contains($$1)) {
/*  62 */       List<Pack> $$2 = Lists.newArrayList(this.selected);
/*  63 */       $$2.remove($$1);
/*  64 */       this.selected = $$2;
/*  65 */       return true;
/*     */     } 
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   private List<Pack> rebuildSelected(Collection<String> $$0) {
/*  71 */     List<Pack> $$1 = getAvailablePacks($$0).collect((Collector)Collectors.toList());
/*     */     
/*  73 */     for (Pack $$2 : this.available.values()) {
/*     */       
/*  75 */       if ($$2.isRequired() && !$$1.contains($$2)) {
/*  76 */         $$2.getDefaultPosition().insert($$1, $$2, (Function<Pack, Pack>)Functions.identity(), false);
/*     */       }
/*     */     } 
/*  79 */     return (List<Pack>)ImmutableList.copyOf($$1);
/*     */   }
/*     */   
/*     */   private Stream<Pack> getAvailablePacks(Collection<String> $$0) {
/*  83 */     Objects.requireNonNull(this.available); return $$0.stream().map(this.available::get).filter(Objects::nonNull);
/*     */   }
/*     */   
/*     */   public Collection<String> getAvailableIds() {
/*  87 */     return this.available.keySet();
/*     */   }
/*     */   
/*     */   public Collection<Pack> getAvailablePacks() {
/*  91 */     return this.available.values();
/*     */   }
/*     */   
/*     */   public Collection<String> getSelectedIds() {
/*  95 */     return (Collection<String>)this.selected.stream().map(Pack::getId).collect(ImmutableSet.toImmutableSet());
/*     */   }
/*     */   
/*     */   public FeatureFlagSet getRequestedFeatureFlags() {
/*  99 */     return getSelectedPacks().stream().map(Pack::getRequestedFeatures).reduce(FeatureFlagSet::join).orElse(FeatureFlagSet.of());
/*     */   }
/*     */   
/*     */   public Collection<Pack> getSelectedPacks() {
/* 103 */     return this.selected;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Pack getPack(String $$0) {
/* 108 */     return this.available.get($$0);
/*     */   }
/*     */   
/*     */   public boolean isAvailable(String $$0) {
/* 112 */     return this.available.containsKey($$0);
/*     */   }
/*     */   
/*     */   public List<PackResources> openAllSelected() {
/* 116 */     return (List<PackResources>)this.selected.stream().map(Pack::open).collect(ImmutableList.toImmutableList());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\PackRepository.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */