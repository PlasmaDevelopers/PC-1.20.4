/*     */ package net.minecraft.world.level.levelgen.flat;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Bootstrap
/*     */ {
/*     */   private final BootstapContext<FlatLevelGeneratorPreset> context;
/*     */   
/*     */   Bootstrap(BootstapContext<FlatLevelGeneratorPreset> $$0) {
/*  38 */     this.context = $$0;
/*     */   }
/*     */   
/*     */   private void register(ResourceKey<FlatLevelGeneratorPreset> $$0, ItemLike $$1, ResourceKey<Biome> $$2, Set<ResourceKey<StructureSet>> $$3, boolean $$4, boolean $$5, FlatLayerInfo... $$6) {
/*  42 */     HolderGetter<StructureSet> $$7 = this.context.lookup(Registries.STRUCTURE_SET);
/*  43 */     HolderGetter<PlacedFeature> $$8 = this.context.lookup(Registries.PLACED_FEATURE);
/*  44 */     HolderGetter<Biome> $$9 = this.context.lookup(Registries.BIOME);
/*     */     
/*  46 */     Objects.requireNonNull($$7); HolderSet.Direct<StructureSet> $$10 = HolderSet.direct((List)$$3.stream().map($$7::getOrThrow).collect(Collectors.toList()));
/*  47 */     FlatLevelGeneratorSettings $$11 = new FlatLevelGeneratorSettings((Optional)Optional.of($$10), (Holder<Biome>)$$9.getOrThrow($$2), FlatLevelGeneratorSettings.createLakesList($$8));
/*  48 */     if ($$4) {
/*  49 */       $$11.setDecoration();
/*     */     }
/*     */     
/*  52 */     if ($$5) {
/*  53 */       $$11.setAddLakes();
/*     */     }
/*     */     
/*  56 */     for (int $$12 = $$6.length - 1; $$12 >= 0; $$12--) {
/*  57 */       $$11.getLayersInfo().add($$6[$$12]);
/*     */     }
/*     */     
/*  60 */     this.context.register($$0, new FlatLevelGeneratorPreset((Holder<Item>)$$1
/*  61 */           .asItem().builtInRegistryHolder(), $$11));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  67 */     register(FlatLevelGeneratorPresets.CLASSIC_FLAT, (ItemLike)Blocks.GRASS_BLOCK, Biomes.PLAINS, 
/*     */ 
/*     */         
/*  70 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.VILLAGES), false, false, new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(2, Blocks.DIRT), new FlatLayerInfo(1, Blocks.BEDROCK) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     register(FlatLevelGeneratorPresets.TUNNELERS_DREAM, (ItemLike)Blocks.STONE, Biomes.WINDSWEPT_HILLS, 
/*     */ 
/*     */         
/*  83 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.MINESHAFTS, BuiltinStructureSets.STRONGHOLDS), true, false, new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(230, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
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
/*  95 */     register(FlatLevelGeneratorPresets.WATER_WORLD, (ItemLike)Items.WATER_BUCKET, Biomes.DEEP_OCEAN, 
/*     */ 
/*     */         
/*  98 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.OCEAN_RUINS, BuiltinStructureSets.SHIPWRECKS, BuiltinStructureSets.OCEAN_MONUMENTS), false, false, new FlatLayerInfo[] { new FlatLayerInfo(90, Blocks.WATER), new FlatLayerInfo(5, Blocks.GRAVEL), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(5, Blocks.STONE), new FlatLayerInfo(64, Blocks.DEEPSLATE), new FlatLayerInfo(1, Blocks.BEDROCK) });
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
/* 113 */     register(FlatLevelGeneratorPresets.OVERWORLD, (ItemLike)Blocks.SHORT_GRASS, Biomes.PLAINS, 
/*     */ 
/*     */         
/* 116 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.VILLAGES, BuiltinStructureSets.MINESHAFTS, BuiltinStructureSets.PILLAGER_OUTPOSTS, BuiltinStructureSets.RUINED_PORTALS, BuiltinStructureSets.STRONGHOLDS), true, true, new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
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
/* 131 */     register(FlatLevelGeneratorPresets.SNOWY_KINGDOM, (ItemLike)Blocks.SNOW, Biomes.SNOWY_PLAINS, 
/*     */ 
/*     */         
/* 134 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.VILLAGES, BuiltinStructureSets.IGLOOS), false, false, new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.SNOW), new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
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
/* 147 */     register(FlatLevelGeneratorPresets.BOTTOMLESS_PIT, (ItemLike)Items.FEATHER, Biomes.PLAINS, 
/*     */ 
/*     */         
/* 150 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.VILLAGES), false, false, new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(2, Blocks.COBBLESTONE) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     register(FlatLevelGeneratorPresets.DESERT, (ItemLike)Blocks.SAND, Biomes.DESERT, 
/*     */ 
/*     */         
/* 163 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.VILLAGES, BuiltinStructureSets.DESERT_PYRAMIDS, BuiltinStructureSets.MINESHAFTS, BuiltinStructureSets.STRONGHOLDS), true, false, new FlatLayerInfo[] { new FlatLayerInfo(8, Blocks.SAND), new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
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
/* 177 */     register(FlatLevelGeneratorPresets.REDSTONE_READY, (ItemLike)Items.REDSTONE, Biomes.DESERT, 
/*     */ 
/*     */         
/* 180 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(), false, false, new FlatLayerInfo[] { new FlatLayerInfo(116, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     register(FlatLevelGeneratorPresets.THE_VOID, (ItemLike)Blocks.BARRIER, Biomes.THE_VOID, 
/*     */ 
/*     */         
/* 191 */         (Set<ResourceKey<StructureSet>>)ImmutableSet.of(), true, false, new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.AIR) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\flat\FlatLevelGeneratorPresets$Bootstrap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */