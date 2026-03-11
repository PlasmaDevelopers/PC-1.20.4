/*     */ package net.minecraft.data.worldgen;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.IronBarsBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.AxisAlignedLinearPosTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class ProcessorLists {
/*  36 */   private static final ResourceKey<StructureProcessorList> EMPTY = createKey("empty");
/*     */   
/*  38 */   public static final ResourceKey<StructureProcessorList> ZOMBIE_PLAINS = createKey("zombie_plains");
/*  39 */   public static final ResourceKey<StructureProcessorList> ZOMBIE_SAVANNA = createKey("zombie_savanna");
/*  40 */   public static final ResourceKey<StructureProcessorList> ZOMBIE_SNOWY = createKey("zombie_snowy");
/*  41 */   public static final ResourceKey<StructureProcessorList> ZOMBIE_TAIGA = createKey("zombie_taiga");
/*  42 */   public static final ResourceKey<StructureProcessorList> ZOMBIE_DESERT = createKey("zombie_desert");
/*  43 */   public static final ResourceKey<StructureProcessorList> MOSSIFY_10_PERCENT = createKey("mossify_10_percent");
/*  44 */   public static final ResourceKey<StructureProcessorList> MOSSIFY_20_PERCENT = createKey("mossify_20_percent");
/*  45 */   public static final ResourceKey<StructureProcessorList> MOSSIFY_70_PERCENT = createKey("mossify_70_percent");
/*  46 */   public static final ResourceKey<StructureProcessorList> STREET_PLAINS = createKey("street_plains");
/*  47 */   public static final ResourceKey<StructureProcessorList> STREET_SAVANNA = createKey("street_savanna");
/*  48 */   public static final ResourceKey<StructureProcessorList> STREET_SNOWY_OR_TAIGA = createKey("street_snowy_or_taiga");
/*  49 */   public static final ResourceKey<StructureProcessorList> FARM_PLAINS = createKey("farm_plains");
/*  50 */   public static final ResourceKey<StructureProcessorList> FARM_SAVANNA = createKey("farm_savanna");
/*  51 */   public static final ResourceKey<StructureProcessorList> FARM_SNOWY = createKey("farm_snowy");
/*  52 */   public static final ResourceKey<StructureProcessorList> FARM_TAIGA = createKey("farm_taiga");
/*  53 */   public static final ResourceKey<StructureProcessorList> FARM_DESERT = createKey("farm_desert");
/*  54 */   public static final ResourceKey<StructureProcessorList> OUTPOST_ROT = createKey("outpost_rot");
/*  55 */   public static final ResourceKey<StructureProcessorList> BOTTOM_RAMPART = createKey("bottom_rampart");
/*  56 */   public static final ResourceKey<StructureProcessorList> TREASURE_ROOMS = createKey("treasure_rooms");
/*  57 */   public static final ResourceKey<StructureProcessorList> HOUSING = createKey("housing");
/*  58 */   public static final ResourceKey<StructureProcessorList> SIDE_WALL_DEGRADATION = createKey("side_wall_degradation");
/*  59 */   public static final ResourceKey<StructureProcessorList> STABLE_DEGRADATION = createKey("stable_degradation");
/*  60 */   public static final ResourceKey<StructureProcessorList> BASTION_GENERIC_DEGRADATION = createKey("bastion_generic_degradation");
/*  61 */   public static final ResourceKey<StructureProcessorList> RAMPART_DEGRADATION = createKey("rampart_degradation");
/*  62 */   public static final ResourceKey<StructureProcessorList> ENTRANCE_REPLACEMENT = createKey("entrance_replacement");
/*  63 */   public static final ResourceKey<StructureProcessorList> BRIDGE = createKey("bridge");
/*  64 */   public static final ResourceKey<StructureProcessorList> ROOF = createKey("roof");
/*  65 */   public static final ResourceKey<StructureProcessorList> HIGH_WALL = createKey("high_wall");
/*  66 */   public static final ResourceKey<StructureProcessorList> HIGH_RAMPART = createKey("high_rampart");
/*  67 */   public static final ResourceKey<StructureProcessorList> FOSSIL_ROT = createKey("fossil_rot");
/*  68 */   public static final ResourceKey<StructureProcessorList> FOSSIL_COAL = createKey("fossil_coal");
/*  69 */   public static final ResourceKey<StructureProcessorList> FOSSIL_DIAMONDS = createKey("fossil_diamonds");
/*  70 */   public static final ResourceKey<StructureProcessorList> ANCIENT_CITY_START_DEGRADATION = createKey("ancient_city_start_degradation");
/*  71 */   public static final ResourceKey<StructureProcessorList> ANCIENT_CITY_GENERIC_DEGRADATION = createKey("ancient_city_generic_degradation");
/*  72 */   public static final ResourceKey<StructureProcessorList> ANCIENT_CITY_WALLS_DEGRADATION = createKey("ancient_city_walls_degradation");
/*  73 */   public static final ResourceKey<StructureProcessorList> TRAIL_RUINS_HOUSES_ARCHAEOLOGY = createKey("trail_ruins_houses_archaeology");
/*  74 */   public static final ResourceKey<StructureProcessorList> TRAIL_RUINS_ROADS_ARCHAEOLOGY = createKey("trail_ruins_roads_archaeology");
/*  75 */   public static final ResourceKey<StructureProcessorList> TRAIL_RUINS_TOWER_TOP_ARCHAEOLOGY = createKey("trail_ruins_tower_top_archaeology");
/*     */ 
/*     */   
/*     */   private static ResourceKey<StructureProcessorList> createKey(String $$0) {
/*  79 */     return ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   private static void register(BootstapContext<StructureProcessorList> $$0, ResourceKey<StructureProcessorList> $$1, List<StructureProcessor> $$2) {
/*  83 */     $$0.register($$1, new StructureProcessorList($$2));
/*     */   }
/*     */   
/*     */   public static void bootstrap(BootstapContext<StructureProcessorList> $$0) {
/*  87 */     HolderGetter<Block> $$1 = $$0.lookup(Registries.BLOCK);
/*     */     
/*  89 */     ProcessorRule $$2 = new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.BLACKSTONE, 0.01F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.GILDED_BLACKSTONE.defaultBlockState());
/*  90 */     ProcessorRule $$3 = new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GILDED_BLACKSTONE, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.BLACKSTONE.defaultBlockState());
/*     */     
/*  92 */     register($$0, EMPTY, (List<StructureProcessor>)ImmutableList.of());
/*     */     
/*  94 */     register($$0, ZOMBIE_PLAINS, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.8F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE
/*  95 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new TagMatchTest(BlockTags.DOORS), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/*  96 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/*  97 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.WALL_TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/*  98 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.07F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/*  99 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.MOSSY_COBBLESTONE, 0.07F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 100 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHITE_TERRACOTTA, 0.07F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 101 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.OAK_LOG, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 102 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.OAK_PLANKS, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 103 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.OAK_STAIRS, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 104 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.STRIPPED_OAK_LOG, 0.02F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 105 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GLASS_PANE, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 106 */                 .defaultBlockState()), (Object[])new ProcessorRule[] { new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 107 */                     .defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 108 */                     .defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CARROTS
/* 109 */                   .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.POTATOES
/* 110 */                   .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.BEETROOTS
/* 111 */                   .defaultBlockState()) }))));
/*     */ 
/*     */     
/* 114 */     register($$0, ZOMBIE_SAVANNA, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new TagMatchTest(BlockTags.DOORS), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 115 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 116 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.WALL_TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 117 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.ACACIA_PLANKS, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 118 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.ACACIA_STAIRS, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 119 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.ACACIA_LOG, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 120 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.ACACIA_WOOD, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 121 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.ORANGE_TERRACOTTA, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 122 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.YELLOW_TERRACOTTA, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 123 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.RED_TERRACOTTA, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 124 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GLASS_PANE, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 125 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 126 */                   .defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), (Object[])new ProcessorRule[] { new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 127 */                     .defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MELON_STEM
/* 128 */                   .defaultBlockState()) }))));
/*     */ 
/*     */     
/* 131 */     register($$0, ZOMBIE_SNOWY, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new TagMatchTest(BlockTags.DOORS), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 132 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 133 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.WALL_TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 134 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.LANTERN), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 135 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SPRUCE_PLANKS, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 136 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SPRUCE_SLAB, 0.4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 137 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.STRIPPED_SPRUCE_LOG, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 138 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.STRIPPED_SPRUCE_WOOD, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 139 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GLASS_PANE, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 140 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 141 */                   .defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 142 */                   .defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CARROTS
/* 143 */                 .defaultBlockState()), (Object[])new ProcessorRule[] { new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.8F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.POTATOES
/* 144 */                   .defaultBlockState()) }))));
/*     */ 
/*     */     
/* 147 */     register($$0, ZOMBIE_TAIGA, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.8F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE
/* 148 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new TagMatchTest(BlockTags.DOORS), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 149 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 150 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.WALL_TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 151 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.CAMPFIRE), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)Blocks.CAMPFIRE
/* 152 */                 .defaultBlockState().setValue((Property)CampfireBlock.LIT, Boolean.valueOf(false))), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.08F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 153 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SPRUCE_LOG, 0.08F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 154 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GLASS_PANE, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 155 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 156 */                   .defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new BlockStateMatchTest((BlockState)((BlockState)Blocks.GLASS_PANE
/* 157 */                   .defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.PUMPKIN_STEM
/* 158 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.POTATOES
/* 159 */                 .defaultBlockState()), (Object[])new ProcessorRule[0]))));
/*     */ 
/*     */     
/* 162 */     register($$0, ZOMBIE_DESERT, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new TagMatchTest(BlockTags.DOORS), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 163 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 164 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.WALL_TORCH), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 165 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SMOOTH_SANDSTONE, 0.08F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 166 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.CUT_SANDSTONE, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 167 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.TERRACOTTA, 0.08F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 168 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SMOOTH_SANDSTONE_STAIRS, 0.08F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 169 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SMOOTH_SANDSTONE_SLAB, 0.08F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COBWEB
/* 170 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.BEETROOTS
/* 171 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MELON_STEM
/* 172 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 175 */     register($$0, MOSSIFY_10_PERCENT, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE
/* 176 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 179 */     register($$0, MOSSIFY_20_PERCENT, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE
/* 180 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 183 */     register($$0, MOSSIFY_70_PERCENT, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.7F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE
/* 184 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 187 */     register($$0, STREET_PLAINS, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.DIRT_PATH), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.OAK_PLANKS
/* 188 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DIRT_PATH, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.GRASS_BLOCK
/* 189 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.GRASS_BLOCK), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.WATER
/* 190 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.DIRT), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.WATER
/* 191 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 194 */     register($$0, STREET_SAVANNA, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.DIRT_PATH), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.ACACIA_PLANKS
/* 195 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DIRT_PATH, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.GRASS_BLOCK
/* 196 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.GRASS_BLOCK), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.WATER
/* 197 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.DIRT), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.WATER
/* 198 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 201 */     register($$0, STREET_SNOWY_OR_TAIGA, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.DIRT_PATH), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.SPRUCE_PLANKS
/* 202 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.DIRT_PATH), (RuleTest)new BlockMatchTest(Blocks.ICE), Blocks.SPRUCE_PLANKS
/* 203 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DIRT_PATH, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.GRASS_BLOCK
/* 204 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.GRASS_BLOCK), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.WATER
/* 205 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.DIRT), (RuleTest)new BlockMatchTest(Blocks.WATER), Blocks.WATER
/* 206 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 209 */     register($$0, FARM_PLAINS, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CARROTS
/* 210 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.POTATOES
/* 211 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.BEETROOTS
/* 212 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 215 */     register($$0, FARM_SAVANNA, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MELON_STEM
/* 216 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 219 */     register($$0, FARM_SNOWY, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CARROTS
/* 220 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.8F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.POTATOES
/* 221 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 224 */     register($$0, FARM_TAIGA, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.PUMPKIN_STEM
/* 225 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.POTATOES
/* 226 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 229 */     register($$0, FARM_DESERT, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.BEETROOTS
/* 230 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WHEAT, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.MELON_STEM
/* 231 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 234 */     register($$0, OUTPOST_ROT, (List<StructureProcessor>)ImmutableList.of(new BlockRotProcessor(0.05F)));
/*     */     
/* 236 */     register($$0, BOTTOM_RAMPART, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.MAGMA_BLOCK, 0.75F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 237 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, 0.15F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.POLISHED_BLACKSTONE_BRICKS
/* 238 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     register($$0, TREASURE_ROOMS, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.35F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 244 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.CHISELED_POLISHED_BLACKSTONE, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 245 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     register($$0, HOUSING, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 251 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.BLACKSTONE, 1.0E-4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 252 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     register($$0, SIDE_WALL_DEGRADATION, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.CHISELED_POLISHED_BLACKSTONE, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 258 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GOLD_BLOCK, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 259 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     register($$0, STABLE_DEGRADATION, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 265 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.BLACKSTONE, 1.0E-4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 266 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     register($$0, BASTION_GENERIC_DEGRADATION, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 272 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.BLACKSTONE, 1.0E-4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 273 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GOLD_BLOCK, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 274 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     register($$0, RAMPART_DEGRADATION, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 280 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.BLACKSTONE, 0.01F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 281 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 1.0E-4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 282 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.BLACKSTONE, 1.0E-4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 283 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GOLD_BLOCK, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 284 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     register($$0, ENTRANCE_REPLACEMENT, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.CHISELED_POLISHED_BLACKSTONE, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 290 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GOLD_BLOCK, 0.6F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 291 */                 .defaultBlockState()), $$3, $$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     register($$0, BRIDGE, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 297 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.BLACKSTONE, 1.0E-4F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 298 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 301 */     register($$0, ROOF, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 302 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.15F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 303 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.BLACKSTONE
/* 304 */                 .defaultBlockState())))));
/*     */ 
/*     */     
/* 307 */     register($$0, HIGH_WALL, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.01F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 308 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 309 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.BLACKSTONE
/* 310 */                 .defaultBlockState()), $$3))));
/*     */ 
/*     */ 
/*     */     
/* 314 */     register($$0, HIGH_RAMPART, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor((List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GOLD_BLOCK, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
/* 315 */                 .defaultBlockState()), new ProcessorRule((RuleTest)AlwaysTrueTest.INSTANCE, (RuleTest)AlwaysTrueTest.INSTANCE, (PosRuleTest)new AxisAlignedLinearPosTest(0.0F, 0.05F, 0, 100, Direction.Axis.Y), Blocks.AIR
/* 316 */                 .defaultBlockState()), $$3))));
/*     */ 
/*     */ 
/*     */     
/* 320 */     register($$0, FOSSIL_ROT, (List<StructureProcessor>)ImmutableList.of(new BlockRotProcessor(0.9F), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     register($$0, FOSSIL_COAL, (List<StructureProcessor>)ImmutableList.of(new BlockRotProcessor(0.1F), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     register($$0, FOSSIL_DIAMONDS, (List<StructureProcessor>)ImmutableList.of(new BlockRotProcessor(0.1F), new RuleProcessor(
/*     */             
/* 332 */             (List)ImmutableList.of(new ProcessorRule((RuleTest)new BlockMatchTest(Blocks.COAL_ORE), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.DEEPSLATE_DIAMOND_ORE
/* 333 */                 .defaultBlockState()))), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     register($$0, ANCIENT_CITY_START_DEGRADATION, (List<StructureProcessor>)ImmutableList.of(new RuleProcessor(
/* 339 */             (List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS
/* 340 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES
/* 341 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 342 */                 .defaultBlockState()))), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     register($$0, ANCIENT_CITY_GENERIC_DEGRADATION, (List<StructureProcessor>)ImmutableList.of(new BlockRotProcessor((HolderSet)$$1
/* 348 */             .getOrThrow(BlockTags.ANCIENT_CITY_REPLACEABLE), 0.95F), new RuleProcessor(
/* 349 */             (List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS
/* 350 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES
/* 351 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 352 */                 .defaultBlockState()))), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     register($$0, ANCIENT_CITY_WALLS_DEGRADATION, (List<StructureProcessor>)ImmutableList.of(new BlockRotProcessor((HolderSet)$$1
/* 358 */             .getOrThrow(BlockTags.ANCIENT_CITY_REPLACEABLE), 0.95F), new RuleProcessor(
/* 359 */             (List)ImmutableList.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DEEPSLATE_BRICKS, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_BRICKS
/* 360 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DEEPSLATE_TILES, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.CRACKED_DEEPSLATE_TILES
/* 361 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.DEEPSLATE_TILE_SLAB, 0.3F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 362 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.SOUL_LANTERN, 0.05F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.AIR
/* 363 */                 .defaultBlockState()))), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     register($$0, TRAIL_RUINS_HOUSES_ARCHAEOLOGY, (List)List.of(new RuleProcessor(
/* 369 */             List.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.DIRT
/* 370 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT
/* 371 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.PACKED_MUD
/* 372 */                 .defaultBlockState()))), 
/*     */           
/* 374 */           trailsArchyLootProcessor(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON, 6), 
/* 375 */           trailsArchyLootProcessor(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE, 3)));
/*     */ 
/*     */     
/* 378 */     register($$0, TRAIL_RUINS_ROADS_ARCHAEOLOGY, (List)List.of(new RuleProcessor(
/* 379 */             List.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.DIRT
/* 380 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT
/* 381 */                 .defaultBlockState()), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.MUD_BRICKS, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, Blocks.PACKED_MUD
/* 382 */                 .defaultBlockState()))), 
/*     */           
/* 384 */           trailsArchyLootProcessor(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON, 2)));
/*     */ 
/*     */     
/* 387 */     register($$0, TRAIL_RUINS_TOWER_TOP_ARCHAEOLOGY, (List)List.of(
/* 388 */           trailsArchyLootProcessor(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON, 2)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static CappedProcessor trailsArchyLootProcessor(ResourceLocation $$0, int $$1) {
/* 393 */     return new CappedProcessor((StructureProcessor)new RuleProcessor(
/* 394 */           List.of(new ProcessorRule((RuleTest)new TagMatchTest(BlockTags.TRAIL_RUINS_REPLACEABLE), (RuleTest)AlwaysTrueTest.INSTANCE, (PosRuleTest)PosAlwaysTrueTest.INSTANCE, Blocks.SUSPICIOUS_GRAVEL
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 399 */               .defaultBlockState(), (RuleBlockEntityModifier)new AppendLoot($$0)))), 
/*     */ 
/*     */ 
/*     */         
/* 403 */         (IntProvider)ConstantInt.of($$1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\ProcessorLists.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */