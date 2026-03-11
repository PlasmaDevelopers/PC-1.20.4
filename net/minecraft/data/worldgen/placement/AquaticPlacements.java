/*     */ package net.minecraft.data.worldgen.placement;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.data.worldgen.features.AquaticFeatures;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.BiomeFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.CountPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacementModifier;
/*     */ import net.minecraft.world.level.levelgen.placement.RarityFilter;
/*     */ 
/*     */ public class AquaticPlacements
/*     */ {
/*     */   private static List<PlacementModifier> seagrassPlacement(int $$0) {
/*  31 */     return (List)List.of(
/*  32 */         InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, 
/*     */         
/*  34 */         CountPlacement.of($$0), 
/*  35 */         BiomeFilter.biome());
/*     */   }
/*     */ 
/*     */   
/*  39 */   public static final ResourceKey<PlacedFeature> SEAGRASS_WARM = PlacementUtils.createKey("seagrass_warm");
/*  40 */   public static final ResourceKey<PlacedFeature> SEAGRASS_NORMAL = PlacementUtils.createKey("seagrass_normal");
/*  41 */   public static final ResourceKey<PlacedFeature> SEAGRASS_COLD = PlacementUtils.createKey("seagrass_cold");
/*  42 */   public static final ResourceKey<PlacedFeature> SEAGRASS_RIVER = PlacementUtils.createKey("seagrass_river");
/*  43 */   public static final ResourceKey<PlacedFeature> SEAGRASS_SWAMP = PlacementUtils.createKey("seagrass_swamp");
/*  44 */   public static final ResourceKey<PlacedFeature> SEAGRASS_DEEP_WARM = PlacementUtils.createKey("seagrass_deep_warm");
/*  45 */   public static final ResourceKey<PlacedFeature> SEAGRASS_DEEP = PlacementUtils.createKey("seagrass_deep");
/*  46 */   public static final ResourceKey<PlacedFeature> SEAGRASS_DEEP_COLD = PlacementUtils.createKey("seagrass_deep_cold");
/*  47 */   public static final ResourceKey<PlacedFeature> SEAGRASS_SIMPLE = PlacementUtils.createKey("seagrass_simple");
/*  48 */   public static final ResourceKey<PlacedFeature> SEA_PICKLE = PlacementUtils.createKey("sea_pickle");
/*  49 */   public static final ResourceKey<PlacedFeature> KELP_COLD = PlacementUtils.createKey("kelp_cold");
/*  50 */   public static final ResourceKey<PlacedFeature> KELP_WARM = PlacementUtils.createKey("kelp_warm");
/*  51 */   public static final ResourceKey<PlacedFeature> WARM_OCEAN_VEGETATION = PlacementUtils.createKey("warm_ocean_vegetation");
/*     */   
/*     */   public static void bootstrap(BootstapContext<PlacedFeature> $$0) {
/*  54 */     HolderGetter<ConfiguredFeature<?, ?>> $$1 = $$0.lookup(Registries.CONFIGURED_FEATURE);
/*  55 */     Holder.Reference<ConfiguredFeature<?, ?>> $$2 = $$1.getOrThrow(AquaticFeatures.SEAGRASS_SHORT);
/*  56 */     Holder.Reference<ConfiguredFeature<?, ?>> $$3 = $$1.getOrThrow(AquaticFeatures.SEAGRASS_SLIGHTLY_LESS_SHORT);
/*  57 */     Holder.Reference<ConfiguredFeature<?, ?>> $$4 = $$1.getOrThrow(AquaticFeatures.SEAGRASS_MID);
/*  58 */     Holder.Reference<ConfiguredFeature<?, ?>> $$5 = $$1.getOrThrow(AquaticFeatures.SEAGRASS_TALL);
/*  59 */     Holder.Reference<ConfiguredFeature<?, ?>> $$6 = $$1.getOrThrow(AquaticFeatures.SEAGRASS_SIMPLE);
/*  60 */     Holder.Reference<ConfiguredFeature<?, ?>> $$7 = $$1.getOrThrow(AquaticFeatures.SEA_PICKLE);
/*  61 */     Holder.Reference<ConfiguredFeature<?, ?>> $$8 = $$1.getOrThrow(AquaticFeatures.KELP);
/*  62 */     Holder.Reference<ConfiguredFeature<?, ?>> $$9 = $$1.getOrThrow(AquaticFeatures.WARM_OCEAN_VEGETATION);
/*     */     
/*  64 */     PlacementUtils.register($$0, SEAGRASS_WARM, (Holder<ConfiguredFeature<?, ?>>)$$2, seagrassPlacement(80));
/*  65 */     PlacementUtils.register($$0, SEAGRASS_NORMAL, (Holder<ConfiguredFeature<?, ?>>)$$2, seagrassPlacement(48));
/*  66 */     PlacementUtils.register($$0, SEAGRASS_COLD, (Holder<ConfiguredFeature<?, ?>>)$$2, seagrassPlacement(32));
/*  67 */     PlacementUtils.register($$0, SEAGRASS_RIVER, (Holder<ConfiguredFeature<?, ?>>)$$3, seagrassPlacement(48));
/*  68 */     PlacementUtils.register($$0, SEAGRASS_SWAMP, (Holder<ConfiguredFeature<?, ?>>)$$4, seagrassPlacement(64));
/*  69 */     PlacementUtils.register($$0, SEAGRASS_DEEP_WARM, (Holder<ConfiguredFeature<?, ?>>)$$5, seagrassPlacement(80));
/*  70 */     PlacementUtils.register($$0, SEAGRASS_DEEP, (Holder<ConfiguredFeature<?, ?>>)$$5, seagrassPlacement(48));
/*  71 */     PlacementUtils.register($$0, SEAGRASS_DEEP_COLD, (Holder<ConfiguredFeature<?, ?>>)$$5, seagrassPlacement(40));
/*     */     
/*  73 */     PlacementUtils.register($$0, SEAGRASS_SIMPLE, (Holder<ConfiguredFeature<?, ?>>)$$6, new PlacementModifier[] {
/*  74 */           (PlacementModifier)CarvingMaskPlacement.forStep(GenerationStep.Carving.LIQUID), 
/*  75 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(10), 
/*  76 */           (PlacementModifier)BlockPredicateFilter.forPredicate(
/*  77 */             BlockPredicate.allOf(new BlockPredicate[] {
/*  78 */                 BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), new Block[] { Blocks.STONE
/*  79 */                   }), BlockPredicate.matchesBlocks((Vec3i)BlockPos.ZERO, new Block[] { Blocks.WATER
/*  80 */                   }), BlockPredicate.matchesBlocks(Direction.UP.getNormal(), new Block[] { Blocks.WATER
/*     */                   })
/*     */               
/*  83 */               })), (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/*  86 */     PlacementUtils.register($$0, SEA_PICKLE, (Holder<ConfiguredFeature<?, ?>>)$$7, new PlacementModifier[] {
/*  87 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(16), 
/*  88 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, 
/*     */           
/*  90 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/*  93 */     PlacementUtils.register($$0, KELP_COLD, (Holder<ConfiguredFeature<?, ?>>)$$8, new PlacementModifier[] {
/*  94 */           (PlacementModifier)NoiseBasedCountPlacement.of(120, 80.0D, 0.0D), 
/*  95 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, 
/*     */           
/*  97 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 100 */     PlacementUtils.register($$0, KELP_WARM, (Holder<ConfiguredFeature<?, ?>>)$$8, new PlacementModifier[] {
/* 101 */           (PlacementModifier)NoiseBasedCountPlacement.of(80, 80.0D, 0.0D), 
/* 102 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, 
/*     */           
/* 104 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 107 */     PlacementUtils.register($$0, WARM_OCEAN_VEGETATION, (Holder<ConfiguredFeature<?, ?>>)$$9, new PlacementModifier[] {
/* 108 */           (PlacementModifier)NoiseBasedCountPlacement.of(20, 400.0D, 0.0D), 
/* 109 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, 
/*     */           
/* 111 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\placement\AquaticPlacements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */