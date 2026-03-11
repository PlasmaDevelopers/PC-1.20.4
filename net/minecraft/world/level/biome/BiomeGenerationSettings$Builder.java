/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.resources.ResourceKey;
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
/*     */ public class Builder
/*     */   extends BiomeGenerationSettings.PlainBuilder
/*     */ {
/*     */   private final HolderGetter<PlacedFeature> placedFeatures;
/*     */   private final HolderGetter<ConfiguredWorldCarver<?>> worldCarvers;
/*     */   
/*     */   public Builder(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 121 */     this.placedFeatures = $$0;
/* 122 */     this.worldCarvers = $$1;
/*     */   }
/*     */   
/*     */   public Builder addFeature(GenerationStep.Decoration $$0, ResourceKey<PlacedFeature> $$1) {
/* 126 */     addFeature($$0.ordinal(), (Holder<PlacedFeature>)this.placedFeatures.getOrThrow($$1));
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public Builder addCarver(GenerationStep.Carving $$0, ResourceKey<ConfiguredWorldCarver<?>> $$1) {
/* 131 */     addCarver($$0, (Holder<ConfiguredWorldCarver<?>>)this.worldCarvers.getOrThrow($$1));
/* 132 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeGenerationSettings$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */