/*     */ package net.minecraft.data.worldgen;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.random.WeightedEntry;
/*     */ import net.minecraft.util.random.WeightedRandomList;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*     */ import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
/*     */ import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
/*     */ import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
/*     */ import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
/*     */ import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.SwampHutStructure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;
/*     */ 
/*     */ public class Structures {
/*     */   public static Structure.StructureSettings structure(HolderSet<Biome> $$0, Map<MobCategory, StructureSpawnOverride> $$1, GenerationStep.Decoration $$2, TerrainAdjustment $$3) {
/*  49 */     return new Structure.StructureSettings($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Structure.StructureSettings structure(HolderSet<Biome> $$0, GenerationStep.Decoration $$1, TerrainAdjustment $$2) {
/*  58 */     return structure($$0, Map.of(), $$1, $$2);
/*     */   }
/*     */   
/*     */   private static Structure.StructureSettings structure(HolderSet<Biome> $$0, TerrainAdjustment $$1) {
/*  62 */     return structure($$0, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, $$1);
/*     */   }
/*     */   
/*     */   public static void bootstrap(BootstapContext<Structure> $$0) {
/*  66 */     HolderGetter<Biome> $$1 = $$0.lookup(Registries.BIOME);
/*  67 */     HolderGetter<StructureTemplatePool> $$2 = $$0.lookup(Registries.TEMPLATE_POOL);
/*     */     
/*  69 */     $$0.register(BuiltinStructures.PILLAGER_OUTPOST, new JigsawStructure(
/*  70 */           structure((HolderSet<Biome>)$$1
/*  71 */             .getOrThrow(BiomeTags.HAS_PILLAGER_OUTPOST), 
/*  72 */             Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, 
/*  73 */                 WeightedRandomList.create((WeightedEntry[])new MobSpawnSettings.SpawnerData[] {
/*     */ 
/*     */                     
/*     */                     new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 1, 1, 1)
/*     */ 
/*     */ 
/*     */                   
/*  80 */                   }))), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), (Holder)$$2.getOrThrow(PillagerOutpostPools.START), 7, 
/*     */           
/*  82 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     $$0.register(BuiltinStructures.MINESHAFT, new MineshaftStructure(
/*  88 */           structure((HolderSet<Biome>)$$1
/*  89 */             .getOrThrow(BiomeTags.HAS_MINESHAFT), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE), MineshaftStructure.Type.NORMAL));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     $$0.register(BuiltinStructures.MINESHAFT_MESA, new MineshaftStructure(
/*  97 */           structure((HolderSet<Biome>)$$1
/*  98 */             .getOrThrow(BiomeTags.HAS_MINESHAFT_MESA), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE), MineshaftStructure.Type.MESA));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     $$0.register(BuiltinStructures.WOODLAND_MANSION, new WoodlandMansionStructure(structure((HolderSet<Biome>)$$1
/* 106 */             .getOrThrow(BiomeTags.HAS_WOODLAND_MANSION), TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */     
/* 110 */     $$0.register(BuiltinStructures.JUNGLE_TEMPLE, new JungleTempleStructure(structure((HolderSet<Biome>)$$1
/* 111 */             .getOrThrow(BiomeTags.HAS_JUNGLE_TEMPLE), TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */     
/* 115 */     $$0.register(BuiltinStructures.DESERT_PYRAMID, new DesertPyramidStructure(structure((HolderSet<Biome>)$$1
/* 116 */             .getOrThrow(BiomeTags.HAS_DESERT_PYRAMID), TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */     
/* 120 */     $$0.register(BuiltinStructures.IGLOO, new IglooStructure(structure((HolderSet<Biome>)$$1
/* 121 */             .getOrThrow(BiomeTags.HAS_IGLOO), TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */     
/* 125 */     $$0.register(BuiltinStructures.SHIPWRECK, new ShipwreckStructure(
/* 126 */           structure((HolderSet<Biome>)$$1
/* 127 */             .getOrThrow(BiomeTags.HAS_SHIPWRECK), TerrainAdjustment.NONE), false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     $$0.register(BuiltinStructures.SHIPWRECK_BEACHED, new ShipwreckStructure(
/* 134 */           structure((HolderSet<Biome>)$$1
/* 135 */             .getOrThrow(BiomeTags.HAS_SHIPWRECK_BEACHED), TerrainAdjustment.NONE), true));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     $$0.register(BuiltinStructures.SWAMP_HUT, new SwampHutStructure(structure((HolderSet<Biome>)$$1
/* 142 */             .getOrThrow(BiomeTags.HAS_SWAMP_HUT), 
/* 143 */             Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, 
/* 144 */                 WeightedRandomList.create((WeightedEntry[])new MobSpawnSettings.SpawnerData[] { new MobSpawnSettings.SpawnerData(EntityType.WITCH, 1, 1, 1) })), MobCategory.CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, 
/*     */ 
/*     */                 
/* 147 */                 WeightedRandomList.create((WeightedEntry[])new MobSpawnSettings.SpawnerData[] { new MobSpawnSettings.SpawnerData(EntityType.CAT, 1, 1, 1) }))), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     $$0.register(BuiltinStructures.STRONGHOLD, new StrongholdStructure(structure((HolderSet<Biome>)$$1
/* 156 */             .getOrThrow(BiomeTags.HAS_STRONGHOLD), TerrainAdjustment.BURY)));
/*     */ 
/*     */ 
/*     */     
/* 160 */     $$0.register(BuiltinStructures.OCEAN_MONUMENT, new OceanMonumentStructure(structure((HolderSet<Biome>)$$1
/* 161 */             .getOrThrow(BiomeTags.HAS_OCEAN_MONUMENT), 
/* 162 */             Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, 
/* 163 */                 WeightedRandomList.create((WeightedEntry[])new MobSpawnSettings.SpawnerData[] { new MobSpawnSettings.SpawnerData(EntityType.GUARDIAN, 1, 2, 4) })), MobCategory.UNDERGROUND_WATER_CREATURE, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST), MobCategory.AXOLOTLS, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, MobSpawnSettings.EMPTY_MOB_LIST)), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     $$0.register(BuiltinStructures.OCEAN_RUIN_COLD, new OceanRuinStructure(
/* 174 */           structure((HolderSet<Biome>)$$1
/* 175 */             .getOrThrow(BiomeTags.HAS_OCEAN_RUIN_COLD), TerrainAdjustment.NONE), OceanRuinStructure.Type.COLD, 0.3F, 0.9F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     $$0.register(BuiltinStructures.OCEAN_RUIN_WARM, new OceanRuinStructure(
/* 184 */           structure((HolderSet<Biome>)$$1
/* 185 */             .getOrThrow(BiomeTags.HAS_OCEAN_RUIN_WARM), TerrainAdjustment.NONE), OceanRuinStructure.Type.WARM, 0.3F, 0.9F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     $$0.register(BuiltinStructures.FORTRESS, new NetherFortressStructure(structure((HolderSet<Biome>)$$1
/* 194 */             .getOrThrow(BiomeTags.HAS_NETHER_FORTRESS), 
/* 195 */             Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, NetherFortressStructure.FORTRESS_ENEMIES)), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     $$0.register(BuiltinStructures.NETHER_FOSSIL, new NetherFossilStructure(
/* 203 */           structure((HolderSet<Biome>)$$1
/* 204 */             .getOrThrow(BiomeTags.HAS_NETHER_FOSSIL), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.BEARD_THIN), 
/*     */ 
/*     */ 
/*     */           
/* 208 */           (HeightProvider)UniformHeight.of(VerticalAnchor.absolute(32), VerticalAnchor.belowTop(2))));
/*     */ 
/*     */     
/* 211 */     $$0.register(BuiltinStructures.END_CITY, new EndCityStructure(structure((HolderSet<Biome>)$$1
/* 212 */             .getOrThrow(BiomeTags.HAS_END_CITY), TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */     
/* 216 */     $$0.register(BuiltinStructures.BURIED_TREASURE, new BuriedTreasureStructure(structure((HolderSet<Biome>)$$1
/* 217 */             .getOrThrow(BiomeTags.HAS_BURIED_TREASURE), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     $$0.register(BuiltinStructures.BASTION_REMNANT, new JigsawStructure(
/* 223 */           structure((HolderSet<Biome>)$$1
/* 224 */             .getOrThrow(BiomeTags.HAS_BASTION_REMNANT), TerrainAdjustment.NONE), (Holder)$$2
/*     */ 
/*     */           
/* 227 */           .getOrThrow(BastionPieces.START), 6, 
/*     */           
/* 229 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(33)), false));
/*     */ 
/*     */ 
/*     */     
/* 233 */     $$0.register(BuiltinStructures.VILLAGE_PLAINS, new JigsawStructure(
/* 234 */           structure((HolderSet<Biome>)$$1
/* 235 */             .getOrThrow(BiomeTags.HAS_VILLAGE_PLAINS), TerrainAdjustment.BEARD_THIN), (Holder)$$2
/*     */ 
/*     */           
/* 238 */           .getOrThrow(PlainVillagePools.START), 6, 
/*     */           
/* 240 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     $$0.register(BuiltinStructures.VILLAGE_DESERT, new JigsawStructure(
/* 246 */           structure((HolderSet<Biome>)$$1
/* 247 */             .getOrThrow(BiomeTags.HAS_VILLAGE_DESERT), TerrainAdjustment.BEARD_THIN), (Holder)$$2
/*     */ 
/*     */           
/* 250 */           .getOrThrow(DesertVillagePools.START), 6, 
/*     */           
/* 252 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     $$0.register(BuiltinStructures.VILLAGE_SAVANNA, new JigsawStructure(
/* 258 */           structure((HolderSet<Biome>)$$1
/* 259 */             .getOrThrow(BiomeTags.HAS_VILLAGE_SAVANNA), TerrainAdjustment.BEARD_THIN), (Holder)$$2
/*     */ 
/*     */           
/* 262 */           .getOrThrow(SavannaVillagePools.START), 6, 
/*     */           
/* 264 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     $$0.register(BuiltinStructures.VILLAGE_SNOWY, new JigsawStructure(
/* 270 */           structure((HolderSet<Biome>)$$1
/* 271 */             .getOrThrow(BiomeTags.HAS_VILLAGE_SNOWY), TerrainAdjustment.BEARD_THIN), (Holder)$$2
/*     */ 
/*     */           
/* 274 */           .getOrThrow(SnowyVillagePools.START), 6, 
/*     */           
/* 276 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     $$0.register(BuiltinStructures.VILLAGE_TAIGA, new JigsawStructure(
/* 282 */           structure((HolderSet<Biome>)$$1
/* 283 */             .getOrThrow(BiomeTags.HAS_VILLAGE_TAIGA), TerrainAdjustment.BEARD_THIN), (Holder)$$2
/*     */ 
/*     */           
/* 286 */           .getOrThrow(TaigaVillagePools.START), 6, 
/*     */           
/* 288 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 293 */     $$0.register(BuiltinStructures.RUINED_PORTAL_STANDARD, new RuinedPortalStructure(
/* 294 */           structure((HolderSet<Biome>)$$1
/* 295 */             .getOrThrow(BiomeTags.HAS_RUINED_PORTAL_STANDARD), TerrainAdjustment.NONE), 
/*     */ 
/*     */           
/* 298 */           List.of(new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.UNDERGROUND, 1.0F, 0.2F, false, false, true, false, 0.5F), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE, 0.5F, 0.2F, false, false, true, false, 0.5F))));
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
/* 322 */     $$0.register(BuiltinStructures.RUINED_PORTAL_DESERT, new RuinedPortalStructure(
/* 323 */           structure((HolderSet<Biome>)$$1
/* 324 */             .getOrThrow(BiomeTags.HAS_RUINED_PORTAL_DESERT), TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED, 0.0F, 0.0F, false, false, false, false, 1.0F)));
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
/* 339 */     $$0.register(BuiltinStructures.RUINED_PORTAL_JUNGLE, new RuinedPortalStructure(
/* 340 */           structure((HolderSet<Biome>)$$1
/* 341 */             .getOrThrow(BiomeTags.HAS_RUINED_PORTAL_JUNGLE), TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE, 0.5F, 0.8F, true, true, false, false, 1.0F)));
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
/* 356 */     $$0.register(BuiltinStructures.RUINED_PORTAL_SWAMP, new RuinedPortalStructure(
/* 357 */           structure((HolderSet<Biome>)$$1
/* 358 */             .getOrThrow(BiomeTags.HAS_RUINED_PORTAL_SWAMP), TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR, 0.0F, 0.5F, false, true, false, false, 1.0F)));
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
/* 373 */     $$0.register(BuiltinStructures.RUINED_PORTAL_MOUNTAIN, new RuinedPortalStructure(
/* 374 */           structure((HolderSet<Biome>)$$1
/* 375 */             .getOrThrow(BiomeTags.HAS_RUINED_PORTAL_MOUNTAIN), TerrainAdjustment.NONE), 
/*     */ 
/*     */           
/* 378 */           List.of(new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN, 1.0F, 0.2F, false, false, true, false, 0.5F), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE, 0.5F, 0.2F, false, false, true, false, 0.5F))));
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
/* 402 */     $$0.register(BuiltinStructures.RUINED_PORTAL_OCEAN, new RuinedPortalStructure(
/* 403 */           structure((HolderSet<Biome>)$$1
/* 404 */             .getOrThrow(BiomeTags.HAS_RUINED_PORTAL_OCEAN), TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR, 0.0F, 0.8F, false, false, true, false, 1.0F)));
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
/* 419 */     $$0.register(BuiltinStructures.RUINED_PORTAL_NETHER, new RuinedPortalStructure(
/* 420 */           structure((HolderSet<Biome>)$$1
/* 421 */             .getOrThrow(BiomeTags.HAS_RUINED_PORTAL_NETHER), TerrainAdjustment.NONE), new RuinedPortalStructure.Setup(RuinedPortalPiece.VerticalPlacement.IN_NETHER, 0.5F, 0.0F, false, false, false, true, 1.0F)));
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
/* 436 */     $$0.register(BuiltinStructures.ANCIENT_CITY, new JigsawStructure(
/* 437 */           structure((HolderSet<Biome>)$$1
/* 438 */             .getOrThrow(BiomeTags.HAS_ANCIENT_CITY), 
/* 439 */             (Map<MobCategory, StructureSpawnOverride>)Arrays.<MobCategory>stream(MobCategory.values()).collect(Collectors.toMap($$0 -> $$0, $$0 -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))), GenerationStep.Decoration.UNDERGROUND_DECORATION, TerrainAdjustment.BEARD_BOX), (Holder)$$2
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 445 */           .getOrThrow(AncientCityStructurePieces.START), 
/* 446 */           Optional.of(new ResourceLocation("city_anchor")), 7, 
/*     */           
/* 448 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(-27)), false, 
/*     */           
/* 450 */           Optional.empty(), 116, 
/*     */           
/* 452 */           List.of()));
/*     */ 
/*     */     
/* 455 */     $$0.register(BuiltinStructures.TRAIL_RUINS, new JigsawStructure(
/* 456 */           structure((HolderSet<Biome>)$$1
/* 457 */             .getOrThrow(BiomeTags.HAS_TRAIL_RUINS), 
/* 458 */             Map.of(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.BURY), (Holder)$$2
/*     */ 
/*     */ 
/*     */           
/* 462 */           .getOrThrow(TrailRuinsStructurePools.START), 7, 
/*     */           
/* 464 */           (HeightProvider)ConstantHeight.of(VerticalAnchor.absolute(-15)), false, Heightmap.Types.WORLD_SURFACE_WG));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\Structures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */