/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlainBuilder
/*     */ {
/*  81 */   private final Map<GenerationStep.Carving, List<Holder<ConfiguredWorldCarver<?>>>> carvers = Maps.newLinkedHashMap();
/*  82 */   private final List<List<Holder<PlacedFeature>>> features = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainBuilder addFeature(GenerationStep.Decoration $$0, Holder<PlacedFeature> $$1) {
/*  88 */     return addFeature($$0.ordinal(), $$1);
/*     */   }
/*     */   
/*     */   public PlainBuilder addFeature(int $$0, Holder<PlacedFeature> $$1) {
/*  92 */     addFeatureStepsUpTo($$0);
/*  93 */     ((List<Holder<PlacedFeature>>)this.features.get($$0)).add($$1);
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public PlainBuilder addCarver(GenerationStep.Carving $$0, Holder<ConfiguredWorldCarver<?>> $$1) {
/*  98 */     ((List<Holder<ConfiguredWorldCarver<?>>>)this.carvers.computeIfAbsent($$0, $$0 -> Lists.newArrayList())).add($$1);
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   private void addFeatureStepsUpTo(int $$0) {
/* 103 */     while (this.features.size() <= $$0) {
/* 104 */       this.features.add(Lists.newArrayList());
/*     */     }
/*     */   }
/*     */   
/*     */   public BiomeGenerationSettings build() {
/* 109 */     return new BiomeGenerationSettings((Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>>)this.carvers
/* 110 */         .entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, $$0 -> HolderSet.direct((List)$$0.getValue()))), (List<HolderSet<PlacedFeature>>)this.features
/* 111 */         .stream().map(HolderSet::direct).collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeGenerationSettings$PlainBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */