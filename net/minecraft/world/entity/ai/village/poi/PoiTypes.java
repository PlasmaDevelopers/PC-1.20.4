/*     */ package net.minecraft.world.entity.ai.village.poi;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.block.BedBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BedPart;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class PoiTypes {
/*  25 */   public static final ResourceKey<PoiType> ARMORER = createKey("armorer");
/*  26 */   public static final ResourceKey<PoiType> BUTCHER = createKey("butcher");
/*  27 */   public static final ResourceKey<PoiType> CARTOGRAPHER = createKey("cartographer");
/*  28 */   public static final ResourceKey<PoiType> CLERIC = createKey("cleric");
/*  29 */   public static final ResourceKey<PoiType> FARMER = createKey("farmer");
/*  30 */   public static final ResourceKey<PoiType> FISHERMAN = createKey("fisherman");
/*  31 */   public static final ResourceKey<PoiType> FLETCHER = createKey("fletcher");
/*  32 */   public static final ResourceKey<PoiType> LEATHERWORKER = createKey("leatherworker");
/*  33 */   public static final ResourceKey<PoiType> LIBRARIAN = createKey("librarian");
/*  34 */   public static final ResourceKey<PoiType> MASON = createKey("mason");
/*  35 */   public static final ResourceKey<PoiType> SHEPHERD = createKey("shepherd");
/*  36 */   public static final ResourceKey<PoiType> TOOLSMITH = createKey("toolsmith");
/*  37 */   public static final ResourceKey<PoiType> WEAPONSMITH = createKey("weaponsmith");
/*  38 */   public static final ResourceKey<PoiType> HOME = createKey("home");
/*  39 */   public static final ResourceKey<PoiType> MEETING = createKey("meeting");
/*  40 */   public static final ResourceKey<PoiType> BEEHIVE = createKey("beehive");
/*  41 */   public static final ResourceKey<PoiType> BEE_NEST = createKey("bee_nest");
/*  42 */   public static final ResourceKey<PoiType> NETHER_PORTAL = createKey("nether_portal");
/*  43 */   public static final ResourceKey<PoiType> LODESTONE = createKey("lodestone");
/*  44 */   public static final ResourceKey<PoiType> LIGHTNING_ROD = createKey("lightning_rod");
/*     */   
/*     */   private static final Set<BlockState> BEDS;
/*     */   
/*     */   private static final Set<BlockState> CAULDRONS;
/*     */   
/*     */   static {
/*  51 */     BEDS = (Set<BlockState>)ImmutableList.of(Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, (Object[])new Block[] { Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED }).stream().flatMap($$0 -> $$0.getStateDefinition().getPossibleStates().stream()).filter($$0 -> ($$0.getValue((Property)BedBlock.PART) == BedPart.HEAD)).collect(ImmutableSet.toImmutableSet());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     CAULDRONS = (Set<BlockState>)ImmutableList.of(Blocks.CAULDRON, Blocks.LAVA_CAULDRON, Blocks.WATER_CAULDRON, Blocks.POWDER_SNOW_CAULDRON).stream().flatMap($$0 -> $$0.getStateDefinition().getPossibleStates().stream()).collect(ImmutableSet.toImmutableSet());
/*  58 */   } private static final Map<BlockState, Holder<PoiType>> TYPE_BY_STATE = Maps.newHashMap();
/*     */   
/*     */   private static Set<BlockState> getBlockStates(Block $$0) {
/*  61 */     return (Set<BlockState>)ImmutableSet.copyOf((Collection)$$0.getStateDefinition().getPossibleStates());
/*     */   }
/*     */   
/*     */   private static ResourceKey<PoiType> createKey(String $$0) {
/*  65 */     return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   private static PoiType register(Registry<PoiType> $$0, ResourceKey<PoiType> $$1, Set<BlockState> $$2, int $$3, int $$4) {
/*  69 */     PoiType $$5 = new PoiType($$2, $$3, $$4);
/*  70 */     Registry.register($$0, $$1, $$5);
/*  71 */     registerBlockStates((Holder<PoiType>)$$0.getHolderOrThrow($$1), $$2);
/*  72 */     return $$5;
/*     */   }
/*     */   
/*     */   private static void registerBlockStates(Holder<PoiType> $$0, Set<BlockState> $$1) {
/*  76 */     $$1.forEach($$1 -> {
/*     */           Holder<PoiType> $$2 = TYPE_BY_STATE.put($$1, $$0);
/*     */           if ($$2 != null) {
/*     */             throw (IllegalStateException)Util.pauseInIde(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", new Object[] { $$1 })));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static Optional<Holder<PoiType>> forState(BlockState $$0) {
/*  85 */     return Optional.ofNullable(TYPE_BY_STATE.get($$0));
/*     */   }
/*     */   
/*     */   public static boolean hasPoi(BlockState $$0) {
/*  89 */     return TYPE_BY_STATE.containsKey($$0);
/*     */   }
/*     */   
/*     */   public static PoiType bootstrap(Registry<PoiType> $$0) {
/*  93 */     register($$0, ARMORER, getBlockStates(Blocks.BLAST_FURNACE), 1, 1);
/*  94 */     register($$0, BUTCHER, getBlockStates(Blocks.SMOKER), 1, 1);
/*  95 */     register($$0, CARTOGRAPHER, getBlockStates(Blocks.CARTOGRAPHY_TABLE), 1, 1);
/*  96 */     register($$0, CLERIC, getBlockStates(Blocks.BREWING_STAND), 1, 1);
/*  97 */     register($$0, FARMER, getBlockStates(Blocks.COMPOSTER), 1, 1);
/*  98 */     register($$0, FISHERMAN, getBlockStates(Blocks.BARREL), 1, 1);
/*  99 */     register($$0, FLETCHER, getBlockStates(Blocks.FLETCHING_TABLE), 1, 1);
/* 100 */     register($$0, LEATHERWORKER, CAULDRONS, 1, 1);
/* 101 */     register($$0, LIBRARIAN, getBlockStates(Blocks.LECTERN), 1, 1);
/* 102 */     register($$0, MASON, getBlockStates(Blocks.STONECUTTER), 1, 1);
/* 103 */     register($$0, SHEPHERD, getBlockStates(Blocks.LOOM), 1, 1);
/* 104 */     register($$0, TOOLSMITH, getBlockStates(Blocks.SMITHING_TABLE), 1, 1);
/* 105 */     register($$0, WEAPONSMITH, getBlockStates(Blocks.GRINDSTONE), 1, 1);
/* 106 */     register($$0, HOME, BEDS, 1, 1);
/* 107 */     register($$0, MEETING, getBlockStates(Blocks.BELL), 32, 6);
/* 108 */     register($$0, BEEHIVE, getBlockStates(Blocks.BEEHIVE), 0, 1);
/* 109 */     register($$0, BEE_NEST, getBlockStates(Blocks.BEE_NEST), 0, 1);
/* 110 */     register($$0, NETHER_PORTAL, getBlockStates(Blocks.NETHER_PORTAL), 0, 1);
/* 111 */     register($$0, LODESTONE, getBlockStates(Blocks.LODESTONE), 0, 1);
/* 112 */     return register($$0, LIGHTNING_ROD, getBlockStates(Blocks.LIGHTNING_ROD), 0, 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\village\poi\PoiTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */