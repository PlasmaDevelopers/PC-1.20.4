/*     */ package net.minecraft.world.level.biome;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.Feature;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BiomeGenerationSettings {
/*  33 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  35 */   public static final BiomeGenerationSettings EMPTY = new BiomeGenerationSettings(
/*  36 */       (Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>>)ImmutableMap.of(), 
/*  37 */       (List<HolderSet<PlacedFeature>>)ImmutableList.of()); public static final MapCodec<BiomeGenerationSettings> CODEC; private final Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> carvers;
/*     */   
/*     */   static {
/*  40 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> {
/*     */           Objects.requireNonNull(LOGGER);
/*     */           Objects.requireNonNull(LOGGER);
/*     */           return (Function)$$0.group((App)Codec.simpleMap(GenerationStep.Carving.CODEC, ConfiguredWorldCarver.LIST_CODEC.promotePartial(Util.prefix("Carver: ", LOGGER::error)), StringRepresentable.keys((StringRepresentable[])GenerationStep.Carving.values())).fieldOf("carvers").forGetter(()), (App)PlacedFeature.LIST_OF_LISTS_CODEC.promotePartial(Util.prefix("Features: ", LOGGER::error)).fieldOf("features").forGetter(())).apply((Applicative)$$0, BiomeGenerationSettings::new);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private final List<HolderSet<PlacedFeature>> features;
/*     */   
/*     */   private final Supplier<List<ConfiguredFeature<?, ?>>> flowerFeatures;
/*     */   
/*     */   private final Supplier<Set<PlacedFeature>> featureSet;
/*     */ 
/*     */   
/*     */   BiomeGenerationSettings(Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> $$0, List<HolderSet<PlacedFeature>> $$1) {
/*  56 */     this.carvers = $$0;
/*  57 */     this.features = $$1;
/*     */ 
/*     */     
/*  60 */     this.flowerFeatures = (Supplier<List<ConfiguredFeature<?, ?>>>)Suppliers.memoize(() -> (List)$$0.stream().flatMap(HolderSet::stream).map(Holder::value).flatMap(PlacedFeature::getFeatures).filter(()).collect(ImmutableList.toImmutableList()));
/*  61 */     this.featureSet = (Supplier<Set<PlacedFeature>>)Suppliers.memoize(() -> (Set)$$0.stream().flatMap(HolderSet::stream).map(Holder::value).collect(Collectors.toSet()));
/*     */   }
/*     */   
/*     */   public Iterable<Holder<ConfiguredWorldCarver<?>>> getCarvers(GenerationStep.Carving $$0) {
/*  65 */     return Objects.<Iterable<Holder<ConfiguredWorldCarver<?>>>>requireNonNullElseGet((Iterable<Holder<ConfiguredWorldCarver<?>>>)this.carvers.get($$0), List::of);
/*     */   }
/*     */   
/*     */   public List<ConfiguredFeature<?, ?>> getFlowerFeatures() {
/*  69 */     return this.flowerFeatures.get();
/*     */   }
/*     */   
/*     */   public List<HolderSet<PlacedFeature>> features() {
/*  73 */     return this.features;
/*     */   }
/*     */   
/*     */   public boolean hasFeature(PlacedFeature $$0) {
/*  77 */     return ((Set)this.featureSet.get()).contains($$0);
/*     */   }
/*     */   
/*     */   public static class PlainBuilder {
/*  81 */     private final Map<GenerationStep.Carving, List<Holder<ConfiguredWorldCarver<?>>>> carvers = Maps.newLinkedHashMap();
/*  82 */     private final List<List<Holder<PlacedFeature>>> features = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PlainBuilder addFeature(GenerationStep.Decoration $$0, Holder<PlacedFeature> $$1) {
/*  88 */       return addFeature($$0.ordinal(), $$1);
/*     */     }
/*     */     
/*     */     public PlainBuilder addFeature(int $$0, Holder<PlacedFeature> $$1) {
/*  92 */       addFeatureStepsUpTo($$0);
/*  93 */       ((List<Holder<PlacedFeature>>)this.features.get($$0)).add($$1);
/*  94 */       return this;
/*     */     }
/*     */     
/*     */     public PlainBuilder addCarver(GenerationStep.Carving $$0, Holder<ConfiguredWorldCarver<?>> $$1) {
/*  98 */       ((List<Holder<ConfiguredWorldCarver<?>>>)this.carvers.computeIfAbsent($$0, $$0 -> Lists.newArrayList())).add($$1);
/*  99 */       return this;
/*     */     }
/*     */     
/*     */     private void addFeatureStepsUpTo(int $$0) {
/* 103 */       while (this.features.size() <= $$0) {
/* 104 */         this.features.add(Lists.newArrayList());
/*     */       }
/*     */     }
/*     */     
/*     */     public BiomeGenerationSettings build() {
/* 109 */       return new BiomeGenerationSettings((Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>>)this.carvers
/* 110 */           .entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, $$0 -> HolderSet.direct((List)$$0.getValue()))), (List<HolderSet<PlacedFeature>>)this.features
/* 111 */           .stream().map(HolderSet::direct).collect(ImmutableList.toImmutableList()));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */     extends PlainBuilder {
/*     */     private final HolderGetter<PlacedFeature> placedFeatures;
/*     */     private final HolderGetter<ConfiguredWorldCarver<?>> worldCarvers;
/*     */     
/*     */     public Builder(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 121 */       this.placedFeatures = $$0;
/* 122 */       this.worldCarvers = $$1;
/*     */     }
/*     */     
/*     */     public Builder addFeature(GenerationStep.Decoration $$0, ResourceKey<PlacedFeature> $$1) {
/* 126 */       addFeature($$0.ordinal(), (Holder<PlacedFeature>)this.placedFeatures.getOrThrow($$1));
/* 127 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addCarver(GenerationStep.Carving $$0, ResourceKey<ConfiguredWorldCarver<?>> $$1) {
/* 131 */       addCarver($$0, (Holder<ConfiguredWorldCarver<?>>)this.worldCarvers.getOrThrow($$1));
/* 132 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeGenerationSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */