/*      */ package net.minecraft.data.models;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.IntStream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.FrontAndTop;
/*      */ import net.minecraft.data.BlockFamilies;
/*      */ import net.minecraft.data.BlockFamily;
/*      */ import net.minecraft.data.models.blockstates.BlockStateGenerator;
/*      */ import net.minecraft.data.models.blockstates.Condition;
/*      */ import net.minecraft.data.models.blockstates.MultiPartGenerator;
/*      */ import net.minecraft.data.models.blockstates.MultiVariantGenerator;
/*      */ import net.minecraft.data.models.blockstates.PropertyDispatch;
/*      */ import net.minecraft.data.models.blockstates.Variant;
/*      */ import net.minecraft.data.models.blockstates.VariantProperties;
/*      */ import net.minecraft.data.models.model.DelegatedModel;
/*      */ import net.minecraft.data.models.model.ModelLocationUtils;
/*      */ import net.minecraft.data.models.model.ModelTemplate;
/*      */ import net.minecraft.data.models.model.ModelTemplates;
/*      */ import net.minecraft.data.models.model.TextureMapping;
/*      */ import net.minecraft.data.models.model.TextureSlot;
/*      */ import net.minecraft.data.models.model.TexturedModel;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.SpawnEggItem;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.CrafterBlock;
/*      */ import net.minecraft.world.level.block.LayeredCauldronBlock;
/*      */ import net.minecraft.world.level.block.MangrovePropaguleBlock;
/*      */ import net.minecraft.world.level.block.PitcherCropBlock;
/*      */ import net.minecraft.world.level.block.SnifferEggBlock;
/*      */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerState;
/*      */ import net.minecraft.world.level.block.state.properties.AttachFace;
/*      */ import net.minecraft.world.level.block.state.properties.BambooLeaves;
/*      */ import net.minecraft.world.level.block.state.properties.BellAttachType;
/*      */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*      */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*      */ import net.minecraft.world.level.block.state.properties.ComparatorMode;
/*      */ import net.minecraft.world.level.block.state.properties.DoorHingeSide;
/*      */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*      */ import net.minecraft.world.level.block.state.properties.DripstoneThickness;
/*      */ import net.minecraft.world.level.block.state.properties.Half;
/*      */ import net.minecraft.world.level.block.state.properties.PistonType;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.block.state.properties.RailShape;
/*      */ import net.minecraft.world.level.block.state.properties.RedstoneSide;
/*      */ import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
/*      */ import net.minecraft.world.level.block.state.properties.SlabType;
/*      */ import net.minecraft.world.level.block.state.properties.StairsShape;
/*      */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*      */ import net.minecraft.world.level.block.state.properties.Tilt;
/*      */ import net.minecraft.world.level.block.state.properties.WallSide;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BlockModelGenerators
/*      */ {
/*      */   final Consumer<BlockStateGenerator> blockStateOutput;
/*      */   final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
/*      */   private final Consumer<Item> skippedAutoModelsOutput;
/*  104 */   final List<Block> nonOrientableTrapdoor = (List<Block>)ImmutableList.of(Blocks.OAK_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.IRON_TRAPDOOR);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   final Map<Block, BlockStateGeneratorSupplier> fullBlockModelCustomGenerators = (Map<Block, BlockStateGeneratorSupplier>)ImmutableMap.builder()
/*  112 */     .put(Blocks.STONE, BlockModelGenerators::createMirroredCubeGenerator)
/*  113 */     .put(Blocks.DEEPSLATE, BlockModelGenerators::createMirroredColumnGenerator)
/*  114 */     .put(Blocks.MUD_BRICKS, BlockModelGenerators::createNorthWestMirroredCubeGenerator)
/*  115 */     .build(); final Map<Block, TexturedModel> texturedModels;
/*      */   
/*      */   private static BlockStateGenerator createMirroredCubeGenerator(Block $$0, ResourceLocation $$1, TextureMapping $$2, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$3) {
/*  118 */     ResourceLocation $$4 = ModelTemplates.CUBE_MIRRORED_ALL.create($$0, $$2, $$3);
/*  119 */     return (BlockStateGenerator)createRotatedVariant($$0, $$1, $$4);
/*      */   }
/*      */   
/*      */   private static BlockStateGenerator createNorthWestMirroredCubeGenerator(Block $$0, ResourceLocation $$1, TextureMapping $$2, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$3) {
/*  123 */     ResourceLocation $$4 = ModelTemplates.CUBE_NORTH_WEST_MIRRORED_ALL.create($$0, $$2, $$3);
/*  124 */     return (BlockStateGenerator)createSimpleBlock($$0, $$4);
/*      */   }
/*      */   
/*      */   private static BlockStateGenerator createMirroredColumnGenerator(Block $$0, ResourceLocation $$1, TextureMapping $$2, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$3) {
/*  128 */     ResourceLocation $$4 = ModelTemplates.CUBE_COLUMN_MIRRORED.create($$0, $$2, $$3);
/*  129 */     return (BlockStateGenerator)createRotatedVariant($$0, $$1, $$4).with(createRotatedPillar());
/*      */   }
/*      */   public BlockModelGenerators(Consumer<BlockStateGenerator> $$0, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$1, Consumer<Item> $$2) {
/*  132 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  154 */       .texturedModels = (Map<Block, TexturedModel>)ImmutableMap.builder().put(Blocks.SANDSTONE, TexturedModel.TOP_BOTTOM_WITH_WALL.get(Blocks.SANDSTONE)).put(Blocks.RED_SANDSTONE, TexturedModel.TOP_BOTTOM_WITH_WALL.get(Blocks.RED_SANDSTONE)).put(Blocks.SMOOTH_SANDSTONE, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_top"))).put(Blocks.SMOOTH_RED_SANDSTONE, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_top"))).put(Blocks.CUT_SANDSTONE, TexturedModel.COLUMN.get(Blocks.SANDSTONE).updateTextures($$0 -> $$0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CUT_SANDSTONE)))).put(Blocks.CUT_RED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.RED_SANDSTONE).updateTextures($$0 -> $$0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CUT_RED_SANDSTONE)))).put(Blocks.QUARTZ_BLOCK, TexturedModel.COLUMN.get(Blocks.QUARTZ_BLOCK)).put(Blocks.SMOOTH_QUARTZ, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.QUARTZ_BLOCK, "_bottom"))).put(Blocks.BLACKSTONE, TexturedModel.COLUMN_WITH_WALL.get(Blocks.BLACKSTONE)).put(Blocks.DEEPSLATE, TexturedModel.COLUMN_WITH_WALL.get(Blocks.DEEPSLATE)).put(Blocks.CHISELED_QUARTZ_BLOCK, TexturedModel.COLUMN.get(Blocks.CHISELED_QUARTZ_BLOCK).updateTextures($$0 -> $$0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_QUARTZ_BLOCK)))).put(Blocks.CHISELED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.CHISELED_SANDSTONE).updateTextures($$0 -> { $$0.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_top")); $$0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_SANDSTONE)); })).put(Blocks.CHISELED_RED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.CHISELED_RED_SANDSTONE).updateTextures($$0 -> { $$0.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_top")); $$0.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_RED_SANDSTONE)); })).put(Blocks.CHISELED_TUFF_BRICKS, TexturedModel.COLUMN_WITH_WALL.get(Blocks.CHISELED_TUFF_BRICKS)).put(Blocks.CHISELED_TUFF, TexturedModel.COLUMN_WITH_WALL.get(Blocks.CHISELED_TUFF)).build();
/*      */ 
/*      */     
/*  157 */     this.blockStateOutput = $$0;
/*  158 */     this.modelOutput = $$1;
/*  159 */     this.skippedAutoModelsOutput = $$2;
/*      */   }
/*      */   
/*      */   void skipAutoItemBlock(Block $$0) {
/*  163 */     this.skippedAutoModelsOutput.accept($$0.asItem());
/*      */   }
/*      */   
/*      */   void delegateItemModel(Block $$0, ResourceLocation $$1) {
/*  167 */     this.modelOutput.accept(ModelLocationUtils.getModelLocation($$0.asItem()), new DelegatedModel($$1));
/*      */   }
/*      */   
/*      */   private void delegateItemModel(Item $$0, ResourceLocation $$1) {
/*  171 */     this.modelOutput.accept(ModelLocationUtils.getModelLocation($$0), new DelegatedModel($$1));
/*      */   }
/*      */   
/*      */   void createSimpleFlatItemModel(Item $$0) {
/*  175 */     ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation($$0), TextureMapping.layer0($$0), this.modelOutput);
/*      */   }
/*      */   
/*      */   private void createSimpleFlatItemModel(Block $$0) {
/*  179 */     Item $$1 = $$0.asItem();
/*  180 */     if ($$1 != Items.AIR) {
/*  181 */       ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation($$1), TextureMapping.layer0($$0), this.modelOutput);
/*      */     }
/*      */   }
/*      */   
/*      */   private void createSimpleFlatItemModel(Block $$0, String $$1) {
/*  186 */     Item $$2 = $$0.asItem();
/*  187 */     ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation($$2), TextureMapping.layer0(TextureMapping.getBlockTexture($$0, $$1)), this.modelOutput);
/*      */   }
/*      */   
/*      */   private static PropertyDispatch createHorizontalFacingDispatch() {
/*  191 */     return (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.HORIZONTAL_FACING)
/*  192 */       .select((Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  193 */       .select((Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  194 */       .select((Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  195 */       .select((Comparable)Direction.NORTH, Variant.variant());
/*      */   }
/*      */   
/*      */   private static PropertyDispatch createHorizontalFacingDispatchAlt() {
/*  199 */     return (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.HORIZONTAL_FACING)
/*  200 */       .select((Comparable)Direction.SOUTH, Variant.variant())
/*  201 */       .select((Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  202 */       .select((Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  203 */       .select((Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
/*      */   }
/*      */   
/*      */   private static PropertyDispatch createTorchHorizontalDispatch() {
/*  207 */     return (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.HORIZONTAL_FACING)
/*  208 */       .select((Comparable)Direction.EAST, Variant.variant())
/*  209 */       .select((Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  210 */       .select((Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  211 */       .select((Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
/*      */   }
/*      */   
/*      */   private static PropertyDispatch createFacingDispatch() {
/*  215 */     return (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.FACING)
/*  216 */       .select((Comparable)Direction.DOWN, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
/*  217 */       .select((Comparable)Direction.UP, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
/*  218 */       .select((Comparable)Direction.NORTH, Variant.variant())
/*  219 */       .select((Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  220 */       .select((Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  221 */       .select((Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
/*      */   }
/*      */   
/*      */   private static MultiVariantGenerator createRotatedVariant(Block $$0, ResourceLocation $$1) {
/*  225 */     return MultiVariantGenerator.multiVariant($$0, createRotatedVariants($$1));
/*      */   }
/*      */   
/*      */   private static Variant[] createRotatedVariants(ResourceLocation $$0) {
/*  229 */     return new Variant[] { Variant.variant().with(VariantProperties.MODEL, $$0), 
/*  230 */         Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), 
/*  231 */         Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), 
/*  232 */         Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270) };
/*      */   }
/*      */   
/*      */   private static MultiVariantGenerator createRotatedVariant(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
/*  236 */     return MultiVariantGenerator.multiVariant($$0, new Variant[] {
/*  237 */           Variant.variant().with(VariantProperties.MODEL, $$1), 
/*  238 */           Variant.variant().with(VariantProperties.MODEL, $$2), 
/*  239 */           Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), 
/*  240 */           Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
/*      */         });
/*      */   }
/*      */   
/*      */   private static PropertyDispatch createBooleanModelDispatch(BooleanProperty $$0, ResourceLocation $$1, ResourceLocation $$2) {
/*  245 */     return (PropertyDispatch)PropertyDispatch.property((Property)$$0)
/*  246 */       .select(Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  247 */       .select(Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2));
/*      */   }
/*      */   
/*      */   private void createRotatedMirroredVariantBlock(Block $$0) {
/*  251 */     ResourceLocation $$1 = TexturedModel.CUBE.create($$0, this.modelOutput);
/*  252 */     ResourceLocation $$2 = TexturedModel.CUBE_MIRRORED.create($$0, this.modelOutput);
/*  253 */     this.blockStateOutput.accept(createRotatedVariant($$0, $$1, $$2));
/*      */   }
/*      */   
/*      */   private void createRotatedVariantBlock(Block $$0) {
/*  257 */     ResourceLocation $$1 = TexturedModel.CUBE.create($$0, this.modelOutput);
/*  258 */     this.blockStateOutput.accept(createRotatedVariant($$0, $$1));
/*      */   }
/*      */   
/*      */   private void createBrushableBlock(Block $$0) {
/*  262 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0)
/*  263 */         .with(
/*  264 */           PropertyDispatch.property((Property)BlockStateProperties.DUSTED)
/*  265 */           .generate($$1 -> {
/*      */               String $$2 = "_" + $$1;
/*      */               
/*      */               ResourceLocation $$3 = TextureMapping.getBlockTexture($$0, $$2);
/*      */               
/*      */               return Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_ALL.createWithSuffix($$0, $$2, (new TextureMapping()).put(TextureSlot.ALL, $$3), this.modelOutput));
/*      */             })));
/*  272 */     delegateItemModel($$0, TextureMapping.getBlockTexture($$0, "_0"));
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createButton(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
/*  276 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  277 */       .with(
/*  278 */         (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.POWERED)
/*  279 */         .select(Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  280 */         .select(Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$2)))
/*      */       
/*  282 */       .with(
/*  283 */         (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.ATTACH_FACE, (Property)BlockStateProperties.HORIZONTAL_FACING)
/*  284 */         .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  285 */         .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  286 */         .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  287 */         .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.NORTH, Variant.variant())
/*      */         
/*  289 */         .select((Comparable)AttachFace.WALL, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  290 */         .select((Comparable)AttachFace.WALL, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  291 */         .select((Comparable)AttachFace.WALL, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  292 */         .select((Comparable)AttachFace.WALL, (Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*      */         
/*  294 */         .select((Comparable)AttachFace.CEILING, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
/*  295 */         .select((Comparable)AttachFace.CEILING, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
/*  296 */         .select((Comparable)AttachFace.CEILING, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
/*  297 */         .select((Comparable)AttachFace.CEILING, (Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)));
/*      */   }
/*      */ 
/*      */   
/*      */   private static PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> configureDoorHalf(PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> $$0, DoubleBlockHalf $$1, ResourceLocation $$2, ResourceLocation $$3, ResourceLocation $$4, ResourceLocation $$5) {
/*  302 */     return $$0
/*  303 */       .select((Comparable)Direction.EAST, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2))
/*  304 */       .select((Comparable)Direction.SOUTH, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  305 */       .select((Comparable)Direction.WEST, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  306 */       .select((Comparable)Direction.NORTH, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */       
/*  308 */       .select((Comparable)Direction.EAST, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$4))
/*  309 */       .select((Comparable)Direction.SOUTH, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  310 */       .select((Comparable)Direction.WEST, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  311 */       .select((Comparable)Direction.NORTH, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */       
/*  313 */       .select((Comparable)Direction.EAST, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  314 */       .select((Comparable)Direction.SOUTH, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  315 */       .select((Comparable)Direction.WEST, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  316 */       .select((Comparable)Direction.NORTH, (Comparable)$$1, (Comparable)DoorHingeSide.LEFT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3))
/*      */       
/*  318 */       .select((Comparable)Direction.EAST, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  319 */       .select((Comparable)Direction.SOUTH, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$5))
/*  320 */       .select((Comparable)Direction.WEST, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  321 */       .select((Comparable)Direction.NORTH, (Comparable)$$1, (Comparable)DoorHingeSide.RIGHT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180));
/*      */   }
/*      */   
/*      */   private static BlockStateGenerator createDoor(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3, ResourceLocation $$4, ResourceLocation $$5, ResourceLocation $$6, ResourceLocation $$7, ResourceLocation $$8) {
/*  325 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  326 */       .with(
/*  327 */         (PropertyDispatch)configureDoorHalf(
/*  328 */           configureDoorHalf(PropertyDispatch.properties((Property)BlockStateProperties.HORIZONTAL_FACING, (Property)BlockStateProperties.DOUBLE_BLOCK_HALF, (Property)BlockStateProperties.DOOR_HINGE, (Property)BlockStateProperties.OPEN), DoubleBlockHalf.LOWER, $$1, $$2, $$3, $$4), DoubleBlockHalf.UPPER, $$5, $$6, $$7, $$8));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BlockStateGenerator createCustomFence(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3, ResourceLocation $$4, ResourceLocation $$5) {
/*  335 */     return (BlockStateGenerator)MultiPartGenerator.multiPart($$0)
/*  336 */       .with(Variant.variant().with(VariantProperties.MODEL, $$1))
/*  337 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)))
/*  338 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)))
/*  339 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)))
/*  340 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$5).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)));
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createFence(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
/*  344 */     return (BlockStateGenerator)MultiPartGenerator.multiPart($$0)
/*  345 */       .with(Variant.variant().with(VariantProperties.MODEL, $$1))
/*  346 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  347 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  348 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  349 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)));
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createWall(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
/*  353 */     return (BlockStateGenerator)MultiPartGenerator.multiPart($$0)
/*  354 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.UP, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$1))
/*      */       
/*  356 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH_WALL, (Comparable)WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  357 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST_WALL, (Comparable)WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  358 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH_WALL, (Comparable)WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  359 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST_WALL, (Comparable)WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*      */       
/*  361 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH_WALL, (Comparable)WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  362 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST_WALL, (Comparable)WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  363 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH_WALL, (Comparable)WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  364 */       .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST_WALL, (Comparable)WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)));
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createFenceGate(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3, ResourceLocation $$4, boolean $$5) {
/*  368 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.UV_LOCK, Boolean.valueOf($$5)))
/*  369 */       .with(createHorizontalFacingDispatchAlt())
/*  370 */       .with(
/*  371 */         (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.IN_WALL, (Property)BlockStateProperties.OPEN)
/*  372 */         .select(Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2))
/*  373 */         .select(Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$4))
/*  374 */         .select(Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  375 */         .select(Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3)));
/*      */   }
/*      */ 
/*      */   
/*      */   static BlockStateGenerator createStairs(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
/*  380 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  381 */       .with(
/*  382 */         (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.HORIZONTAL_FACING, (Property)BlockStateProperties.HALF, (Property)BlockStateProperties.STAIRS_SHAPE)
/*  383 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2))
/*  384 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  385 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  386 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  387 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3))
/*  388 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  389 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  390 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  391 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  392 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  393 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3))
/*  394 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  395 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1))
/*  396 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  397 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  398 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  399 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  400 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  401 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1))
/*  402 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  403 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  404 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  405 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  406 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, (Comparable)StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  407 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  408 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  409 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  410 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  411 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  412 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  413 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  414 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, (Comparable)StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  415 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  416 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  417 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  418 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  419 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  420 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  421 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*  422 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, (Comparable)StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BlockStateGenerator createOrientableTrapdoor(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
/*  428 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  429 */       .with(
/*  430 */         (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.HORIZONTAL_FACING, (Property)BlockStateProperties.HALF, (Property)BlockStateProperties.OPEN)
/*  431 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2))
/*  432 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  433 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  434 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  435 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  436 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  437 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  438 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  439 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3))
/*  440 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  441 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  442 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  443 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  444 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
/*  445 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  446 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
/*      */   }
/*      */ 
/*      */   
/*      */   private static BlockStateGenerator createTrapdoor(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
/*  451 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  452 */       .with(
/*  453 */         (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.HORIZONTAL_FACING, (Property)BlockStateProperties.HALF, (Property)BlockStateProperties.OPEN)
/*  454 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2))
/*  455 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2))
/*  456 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2))
/*  457 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$2))
/*  458 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  459 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  460 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  461 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1))
/*  462 */         .select((Comparable)Direction.NORTH, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3))
/*  463 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  464 */         .select((Comparable)Direction.EAST, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  465 */         .select((Comparable)Direction.WEST, (Comparable)Half.BOTTOM, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  466 */         .select((Comparable)Direction.NORTH, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3))
/*  467 */         .select((Comparable)Direction.SOUTH, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*  468 */         .select((Comparable)Direction.EAST, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  469 */         .select((Comparable)Direction.WEST, (Comparable)Half.TOP, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
/*      */   }
/*      */ 
/*      */   
/*      */   static MultiVariantGenerator createSimpleBlock(Block $$0, ResourceLocation $$1) {
/*  474 */     return MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, $$1));
/*      */   }
/*      */   
/*      */   private static PropertyDispatch createRotatedPillar() {
/*  478 */     return (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.AXIS)
/*  479 */       .select((Comparable)Direction.Axis.Y, Variant.variant())
/*  480 */       .select((Comparable)Direction.Axis.Z, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
/*  481 */       .select((Comparable)Direction.Axis.X, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createPillarBlockUVLocked(Block $$0, TextureMapping $$1, BiConsumer<ResourceLocation, Supplier<JsonElement>> $$2) {
/*  485 */     ResourceLocation $$3 = ModelTemplates.CUBE_COLUMN_UV_LOCKED_X.create($$0, $$1, $$2);
/*  486 */     ResourceLocation $$4 = ModelTemplates.CUBE_COLUMN_UV_LOCKED_Y.create($$0, $$1, $$2);
/*  487 */     ResourceLocation $$5 = ModelTemplates.CUBE_COLUMN_UV_LOCKED_Z.create($$0, $$1, $$2);
/*  488 */     ResourceLocation $$6 = ModelTemplates.CUBE_COLUMN.create($$0, $$1, $$2);
/*      */     
/*  490 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, $$6))
/*  491 */       .with(
/*  492 */         (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.AXIS)
/*  493 */         .select((Comparable)Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, $$3))
/*  494 */         .select((Comparable)Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, $$4))
/*  495 */         .select((Comparable)Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, $$5)));
/*      */   }
/*      */ 
/*      */   
/*      */   static BlockStateGenerator createAxisAlignedPillarBlock(Block $$0, ResourceLocation $$1) {
/*  500 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, $$1)).with(createRotatedPillar());
/*      */   }
/*      */   
/*      */   private void createAxisAlignedPillarBlockCustomModel(Block $$0, ResourceLocation $$1) {
/*  504 */     this.blockStateOutput.accept(createAxisAlignedPillarBlock($$0, $$1));
/*      */   }
/*      */   
/*      */   public void createAxisAlignedPillarBlock(Block $$0, TexturedModel.Provider $$1) {
/*  508 */     ResourceLocation $$2 = $$1.create($$0, this.modelOutput);
/*  509 */     this.blockStateOutput.accept(createAxisAlignedPillarBlock($$0, $$2));
/*      */   }
/*      */   
/*      */   private void createHorizontallyRotatedBlock(Block $$0, TexturedModel.Provider $$1) {
/*  513 */     ResourceLocation $$2 = $$1.create($$0, this.modelOutput);
/*  514 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, $$2)).with(createHorizontalFacingDispatch()));
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createRotatedPillarWithHorizontalVariant(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
/*  518 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  519 */       .with(
/*  520 */         (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.AXIS)
/*  521 */         .select((Comparable)Direction.Axis.Y, Variant.variant().with(VariantProperties.MODEL, $$1))
/*  522 */         .select((Comparable)Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
/*  523 */         .select((Comparable)Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createRotatedPillarWithHorizontalVariant(Block $$0, TexturedModel.Provider $$1, TexturedModel.Provider $$2) {
/*  528 */     ResourceLocation $$3 = $$1.create($$0, this.modelOutput);
/*  529 */     ResourceLocation $$4 = $$2.create($$0, this.modelOutput);
/*  530 */     this.blockStateOutput.accept(createRotatedPillarWithHorizontalVariant($$0, $$3, $$4));
/*      */   }
/*      */   
/*      */   private ResourceLocation createSuffixedVariant(Block $$0, String $$1, ModelTemplate $$2, Function<ResourceLocation, TextureMapping> $$3) {
/*  534 */     return $$2.createWithSuffix($$0, $$1, $$3.apply(TextureMapping.getBlockTexture($$0, $$1)), this.modelOutput);
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createPressurePlate(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
/*  538 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  539 */       .with(createBooleanModelDispatch(BlockStateProperties.POWERED, $$2, $$1));
/*      */   }
/*      */   
/*      */   static BlockStateGenerator createSlab(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
/*  543 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/*  544 */       .with(
/*  545 */         (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.SLAB_TYPE)
/*  546 */         .select((Comparable)SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, $$1))
/*  547 */         .select((Comparable)SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, $$2))
/*  548 */         .select((Comparable)SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, $$3)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void createTrivialCube(Block $$0) {
/*  553 */     createTrivialBlock($$0, TexturedModel.CUBE);
/*      */   }
/*      */   
/*      */   public void createTrivialBlock(Block $$0, TexturedModel.Provider $$1) {
/*  557 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$1.create($$0, this.modelOutput)));
/*      */   }
/*      */   
/*      */   private void createTrivialBlock(Block $$0, TextureMapping $$1, ModelTemplate $$2) {
/*  561 */     ResourceLocation $$3 = $$2.create($$0, $$1, this.modelOutput);
/*  562 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$3));
/*      */   }
/*      */   
/*  565 */   static final Map<BlockFamily.Variant, BiConsumer<BlockFamilyProvider, Block>> SHAPE_CONSUMERS = (Map<BlockFamily.Variant, BiConsumer<BlockFamilyProvider, Block>>)ImmutableMap.builder()
/*  566 */     .put(BlockFamily.Variant.BUTTON, BlockFamilyProvider::button)
/*  567 */     .put(BlockFamily.Variant.DOOR, BlockFamilyProvider::door)
/*  568 */     .put(BlockFamily.Variant.CHISELED, BlockFamilyProvider::fullBlockVariant)
/*  569 */     .put(BlockFamily.Variant.CRACKED, BlockFamilyProvider::fullBlockVariant)
/*  570 */     .put(BlockFamily.Variant.CUSTOM_FENCE, BlockFamilyProvider::customFence)
/*  571 */     .put(BlockFamily.Variant.FENCE, BlockFamilyProvider::fence)
/*  572 */     .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, BlockFamilyProvider::customFenceGate)
/*  573 */     .put(BlockFamily.Variant.FENCE_GATE, BlockFamilyProvider::fenceGate)
/*  574 */     .put(BlockFamily.Variant.SIGN, BlockFamilyProvider::sign)
/*  575 */     .put(BlockFamily.Variant.SLAB, BlockFamilyProvider::slab)
/*  576 */     .put(BlockFamily.Variant.STAIRS, BlockFamilyProvider::stairs)
/*  577 */     .put(BlockFamily.Variant.PRESSURE_PLATE, BlockFamilyProvider::pressurePlate)
/*  578 */     .put(BlockFamily.Variant.TRAPDOOR, BlockFamilyProvider::trapdoor)
/*  579 */     .put(BlockFamily.Variant.WALL, BlockFamilyProvider::wall)
/*  580 */     .build();
/*      */   public static final List<Pair<BooleanProperty, Function<ResourceLocation, Variant>>> MULTIFACE_GENERATOR;
/*      */   
/*      */   private class BlockFamilyProvider { private final TextureMapping mapping;
/*  584 */     private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
/*      */     
/*      */     @Nullable
/*      */     private BlockFamily family;
/*      */     @Nullable
/*      */     private ResourceLocation fullBlock;
/*  590 */     private final Set<Block> skipGeneratingModelsFor = new HashSet<>();
/*      */     
/*      */     public BlockFamilyProvider(TextureMapping $$0) {
/*  593 */       this.mapping = $$0;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider fullBlock(Block $$0, ModelTemplate $$1) {
/*  597 */       this.fullBlock = $$1.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  598 */       if (BlockModelGenerators.this.fullBlockModelCustomGenerators.containsKey($$0)) {
/*  599 */         BlockModelGenerators.this.blockStateOutput.accept(((BlockModelGenerators.BlockStateGeneratorSupplier)BlockModelGenerators.this.fullBlockModelCustomGenerators.get($$0)).create($$0, this.fullBlock, this.mapping, BlockModelGenerators.this.modelOutput));
/*      */       } else {
/*  601 */         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$0, this.fullBlock));
/*      */       } 
/*  603 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider donateModelTo(Block $$0, Block $$1) {
/*  607 */       ResourceLocation $$2 = ModelLocationUtils.getModelLocation($$0);
/*  608 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$1, $$2));
/*  609 */       BlockModelGenerators.this.delegateItemModel($$1, $$2);
/*  610 */       this.skipGeneratingModelsFor.add($$1);
/*  611 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider button(Block $$0) {
/*  615 */       ResourceLocation $$1 = ModelTemplates.BUTTON.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  616 */       ResourceLocation $$2 = ModelTemplates.BUTTON_PRESSED.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  617 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createButton($$0, $$1, $$2));
/*      */       
/*  619 */       ResourceLocation $$3 = ModelTemplates.BUTTON_INVENTORY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  620 */       BlockModelGenerators.this.delegateItemModel($$0, $$3);
/*  621 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider wall(Block $$0) {
/*  625 */       ResourceLocation $$1 = ModelTemplates.WALL_POST.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  626 */       ResourceLocation $$2 = ModelTemplates.WALL_LOW_SIDE.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  627 */       ResourceLocation $$3 = ModelTemplates.WALL_TALL_SIDE.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  628 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createWall($$0, $$1, $$2, $$3));
/*      */       
/*  630 */       ResourceLocation $$4 = ModelTemplates.WALL_INVENTORY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  631 */       BlockModelGenerators.this.delegateItemModel($$0, $$4);
/*  632 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider customFence(Block $$0) {
/*  636 */       TextureMapping $$1 = TextureMapping.customParticle($$0);
/*      */       
/*  638 */       ResourceLocation $$2 = ModelTemplates.CUSTOM_FENCE_POST.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  639 */       ResourceLocation $$3 = ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  640 */       ResourceLocation $$4 = ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  641 */       ResourceLocation $$5 = ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  642 */       ResourceLocation $$6 = ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  643 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createCustomFence($$0, $$2, $$3, $$4, $$5, $$6));
/*      */       
/*  645 */       ResourceLocation $$7 = ModelTemplates.CUSTOM_FENCE_INVENTORY.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  646 */       BlockModelGenerators.this.delegateItemModel($$0, $$7);
/*  647 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider fence(Block $$0) {
/*  651 */       ResourceLocation $$1 = ModelTemplates.FENCE_POST.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  652 */       ResourceLocation $$2 = ModelTemplates.FENCE_SIDE.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  653 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFence($$0, $$1, $$2));
/*      */       
/*  655 */       ResourceLocation $$3 = ModelTemplates.FENCE_INVENTORY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  656 */       BlockModelGenerators.this.delegateItemModel($$0, $$3);
/*  657 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider customFenceGate(Block $$0) {
/*  661 */       TextureMapping $$1 = TextureMapping.customParticle($$0);
/*      */       
/*  663 */       ResourceLocation $$2 = ModelTemplates.CUSTOM_FENCE_GATE_OPEN.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  664 */       ResourceLocation $$3 = ModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  665 */       ResourceLocation $$4 = ModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  666 */       ResourceLocation $$5 = ModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  667 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFenceGate($$0, $$2, $$3, $$4, $$5, false));
/*  668 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider fenceGate(Block $$0) {
/*  672 */       ResourceLocation $$1 = ModelTemplates.FENCE_GATE_OPEN.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  673 */       ResourceLocation $$2 = ModelTemplates.FENCE_GATE_CLOSED.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  674 */       ResourceLocation $$3 = ModelTemplates.FENCE_GATE_WALL_OPEN.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  675 */       ResourceLocation $$4 = ModelTemplates.FENCE_GATE_WALL_CLOSED.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  676 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createFenceGate($$0, $$1, $$2, $$3, $$4, true));
/*  677 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider pressurePlate(Block $$0) {
/*  681 */       ResourceLocation $$1 = ModelTemplates.PRESSURE_PLATE_UP.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  682 */       ResourceLocation $$2 = ModelTemplates.PRESSURE_PLATE_DOWN.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  683 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate($$0, $$1, $$2));
/*  684 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider sign(Block $$0) {
/*  688 */       if (this.family == null) {
/*  689 */         throw new IllegalStateException("Family not defined");
/*      */       }
/*  691 */       Block $$1 = (Block)this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
/*  692 */       ResourceLocation $$2 = ModelTemplates.PARTICLE_ONLY.create($$0, this.mapping, BlockModelGenerators.this.modelOutput);
/*  693 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$0, $$2));
/*  694 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$1, $$2));
/*  695 */       BlockModelGenerators.this.createSimpleFlatItemModel($$0.asItem());
/*  696 */       BlockModelGenerators.this.skipAutoItemBlock($$1);
/*  697 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider slab(Block $$0) {
/*  701 */       if (this.fullBlock == null) {
/*  702 */         throw new IllegalStateException("Full block not generated yet");
/*      */       }
/*  704 */       ResourceLocation $$1 = getOrCreateModel(ModelTemplates.SLAB_BOTTOM, $$0);
/*  705 */       ResourceLocation $$2 = getOrCreateModel(ModelTemplates.SLAB_TOP, $$0);
/*      */       
/*  707 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSlab($$0, $$1, $$2, this.fullBlock));
/*  708 */       BlockModelGenerators.this.delegateItemModel($$0, $$1);
/*  709 */       return this;
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider stairs(Block $$0) {
/*  713 */       ResourceLocation $$1 = getOrCreateModel(ModelTemplates.STAIRS_INNER, $$0);
/*  714 */       ResourceLocation $$2 = getOrCreateModel(ModelTemplates.STAIRS_STRAIGHT, $$0);
/*  715 */       ResourceLocation $$3 = getOrCreateModel(ModelTemplates.STAIRS_OUTER, $$0);
/*      */       
/*  717 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createStairs($$0, $$1, $$2, $$3));
/*  718 */       BlockModelGenerators.this.delegateItemModel($$0, $$2);
/*  719 */       return this;
/*      */     }
/*      */     
/*      */     private BlockFamilyProvider fullBlockVariant(Block $$0) {
/*  723 */       TexturedModel $$1 = BlockModelGenerators.this.texturedModels.getOrDefault($$0, TexturedModel.CUBE.get($$0));
/*  724 */       ResourceLocation $$2 = $$1.create($$0, BlockModelGenerators.this.modelOutput);
/*  725 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$0, $$2));
/*  726 */       return this;
/*      */     }
/*      */     
/*      */     private BlockFamilyProvider door(Block $$0) {
/*  730 */       BlockModelGenerators.this.createDoor($$0);
/*  731 */       return this;
/*      */     }
/*      */     
/*      */     private void trapdoor(Block $$0) {
/*  735 */       if (BlockModelGenerators.this.nonOrientableTrapdoor.contains($$0)) {
/*  736 */         BlockModelGenerators.this.createTrapdoor($$0);
/*      */       } else {
/*  738 */         BlockModelGenerators.this.createOrientableTrapdoor($$0);
/*      */       } 
/*      */     }
/*      */     
/*      */     private ResourceLocation getOrCreateModel(ModelTemplate $$0, Block $$1) {
/*  743 */       return this.models.computeIfAbsent($$0, $$1 -> $$1.create($$0, this.mapping, BlockModelGenerators.this.modelOutput));
/*      */     }
/*      */     
/*      */     public BlockFamilyProvider generateFor(BlockFamily $$0) {
/*  747 */       this.family = $$0;
/*  748 */       $$0.getVariants().forEach(($$0, $$1) -> {
/*      */             if (this.skipGeneratingModelsFor.contains($$1)) {
/*      */               return;
/*      */             }
/*      */             BiConsumer<BlockFamilyProvider, Block> $$2 = BlockModelGenerators.SHAPE_CONSUMERS.get($$0);
/*      */             if ($$2 != null) {
/*      */               $$2.accept(this, $$1);
/*      */             }
/*      */           });
/*  757 */       return this;
/*      */     } }
/*      */ 
/*      */   
/*      */   private BlockFamilyProvider family(Block $$0) {
/*  762 */     TexturedModel $$1 = this.texturedModels.getOrDefault($$0, TexturedModel.CUBE.get($$0));
/*  763 */     return (new BlockFamilyProvider($$1.getMapping())).fullBlock($$0, $$1.getTemplate());
/*      */   }
/*      */   
/*      */   public void createHangingSign(Block $$0, Block $$1, Block $$2) {
/*  767 */     TextureMapping $$3 = TextureMapping.particle($$0);
/*      */     
/*  769 */     ResourceLocation $$4 = ModelTemplates.PARTICLE_ONLY.create($$1, $$3, this.modelOutput);
/*  770 */     this.blockStateOutput.accept(createSimpleBlock($$1, $$4));
/*  771 */     this.blockStateOutput.accept(createSimpleBlock($$2, $$4));
/*  772 */     createSimpleFlatItemModel($$1.asItem());
/*  773 */     skipAutoItemBlock($$2);
/*      */   }
/*      */   
/*      */   void createDoor(Block $$0) {
/*  777 */     TextureMapping $$1 = TextureMapping.door($$0);
/*  778 */     ResourceLocation $$2 = ModelTemplates.DOOR_BOTTOM_LEFT.create($$0, $$1, this.modelOutput);
/*  779 */     ResourceLocation $$3 = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create($$0, $$1, this.modelOutput);
/*  780 */     ResourceLocation $$4 = ModelTemplates.DOOR_BOTTOM_RIGHT.create($$0, $$1, this.modelOutput);
/*  781 */     ResourceLocation $$5 = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create($$0, $$1, this.modelOutput);
/*  782 */     ResourceLocation $$6 = ModelTemplates.DOOR_TOP_LEFT.create($$0, $$1, this.modelOutput);
/*  783 */     ResourceLocation $$7 = ModelTemplates.DOOR_TOP_LEFT_OPEN.create($$0, $$1, this.modelOutput);
/*  784 */     ResourceLocation $$8 = ModelTemplates.DOOR_TOP_RIGHT.create($$0, $$1, this.modelOutput);
/*  785 */     ResourceLocation $$9 = ModelTemplates.DOOR_TOP_RIGHT_OPEN.create($$0, $$1, this.modelOutput);
/*      */     
/*  787 */     createSimpleFlatItemModel($$0.asItem());
/*  788 */     this.blockStateOutput.accept(createDoor($$0, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9));
/*      */   }
/*      */   
/*      */   private void copyDoorModel(Block $$0, Block $$1) {
/*  792 */     ResourceLocation $$2 = ModelTemplates.DOOR_BOTTOM_LEFT.getDefaultModelLocation($$0);
/*  793 */     ResourceLocation $$3 = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.getDefaultModelLocation($$0);
/*  794 */     ResourceLocation $$4 = ModelTemplates.DOOR_BOTTOM_RIGHT.getDefaultModelLocation($$0);
/*  795 */     ResourceLocation $$5 = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.getDefaultModelLocation($$0);
/*  796 */     ResourceLocation $$6 = ModelTemplates.DOOR_TOP_LEFT.getDefaultModelLocation($$0);
/*  797 */     ResourceLocation $$7 = ModelTemplates.DOOR_TOP_LEFT_OPEN.getDefaultModelLocation($$0);
/*  798 */     ResourceLocation $$8 = ModelTemplates.DOOR_TOP_RIGHT.getDefaultModelLocation($$0);
/*  799 */     ResourceLocation $$9 = ModelTemplates.DOOR_TOP_RIGHT_OPEN.getDefaultModelLocation($$0);
/*      */     
/*  801 */     delegateItemModel($$1, ModelLocationUtils.getModelLocation($$0.asItem()));
/*  802 */     this.blockStateOutput.accept(createDoor($$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9));
/*      */   }
/*      */   
/*      */   void createOrientableTrapdoor(Block $$0) {
/*  806 */     TextureMapping $$1 = TextureMapping.defaultTexture($$0);
/*  807 */     ResourceLocation $$2 = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create($$0, $$1, this.modelOutput);
/*  808 */     ResourceLocation $$3 = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create($$0, $$1, this.modelOutput);
/*  809 */     ResourceLocation $$4 = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create($$0, $$1, this.modelOutput);
/*      */     
/*  811 */     this.blockStateOutput.accept(createOrientableTrapdoor($$0, $$2, $$3, $$4));
/*  812 */     delegateItemModel($$0, $$3);
/*      */   }
/*      */   
/*      */   void createTrapdoor(Block $$0) {
/*  816 */     TextureMapping $$1 = TextureMapping.defaultTexture($$0);
/*  817 */     ResourceLocation $$2 = ModelTemplates.TRAPDOOR_TOP.create($$0, $$1, this.modelOutput);
/*  818 */     ResourceLocation $$3 = ModelTemplates.TRAPDOOR_BOTTOM.create($$0, $$1, this.modelOutput);
/*  819 */     ResourceLocation $$4 = ModelTemplates.TRAPDOOR_OPEN.create($$0, $$1, this.modelOutput);
/*      */     
/*  821 */     this.blockStateOutput.accept(createTrapdoor($$0, $$2, $$3, $$4));
/*  822 */     delegateItemModel($$0, $$3);
/*      */   }
/*      */   
/*      */   private void copyTrapdoorModel(Block $$0, Block $$1) {
/*  826 */     ResourceLocation $$2 = ModelTemplates.TRAPDOOR_TOP.getDefaultModelLocation($$0);
/*  827 */     ResourceLocation $$3 = ModelTemplates.TRAPDOOR_BOTTOM.getDefaultModelLocation($$0);
/*  828 */     ResourceLocation $$4 = ModelTemplates.TRAPDOOR_OPEN.getDefaultModelLocation($$0);
/*      */     
/*  830 */     delegateItemModel($$1, ModelLocationUtils.getModelLocation($$0.asItem()));
/*  831 */     this.blockStateOutput.accept(createTrapdoor($$1, $$2, $$3, $$4));
/*      */   }
/*      */   
/*      */   private void createBigDripLeafBlock() {
/*  835 */     skipAutoItemBlock(Blocks.BIG_DRIPLEAF);
/*  836 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.BIG_DRIPLEAF);
/*  837 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.BIG_DRIPLEAF, "_partial_tilt");
/*  838 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation(Blocks.BIG_DRIPLEAF, "_full_tilt");
/*      */     
/*  840 */     this.blockStateOutput.accept(
/*  841 */         MultiVariantGenerator.multiVariant(Blocks.BIG_DRIPLEAF)
/*  842 */         .with(createHorizontalFacingDispatch())
/*  843 */         .with((PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.TILT)
/*  844 */           .select((Comparable)Tilt.NONE, Variant.variant().with(VariantProperties.MODEL, $$0))
/*  845 */           .select((Comparable)Tilt.UNSTABLE, Variant.variant().with(VariantProperties.MODEL, $$0))
/*  846 */           .select((Comparable)Tilt.PARTIAL, Variant.variant().with(VariantProperties.MODEL, $$1))
/*  847 */           .select((Comparable)Tilt.FULL, Variant.variant().with(VariantProperties.MODEL, $$2))));
/*      */   }
/*      */ 
/*      */   
/*      */   private class WoodProvider
/*      */   {
/*      */     private final TextureMapping logMapping;
/*      */     
/*      */     public WoodProvider(TextureMapping $$0) {
/*  856 */       this.logMapping = $$0;
/*      */     }
/*      */     
/*      */     public WoodProvider wood(Block $$0) {
/*  860 */       TextureMapping $$1 = this.logMapping.copyAndUpdate(TextureSlot.END, this.logMapping.get(TextureSlot.SIDE));
/*  861 */       ResourceLocation $$2 = ModelTemplates.CUBE_COLUMN.create($$0, $$1, BlockModelGenerators.this.modelOutput);
/*  862 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock($$0, $$2));
/*  863 */       return this;
/*      */     }
/*      */     
/*      */     public WoodProvider log(Block $$0) {
/*  867 */       ResourceLocation $$1 = ModelTemplates.CUBE_COLUMN.create($$0, this.logMapping, BlockModelGenerators.this.modelOutput);
/*  868 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock($$0, $$1));
/*  869 */       return this;
/*      */     }
/*      */     
/*      */     public WoodProvider logWithHorizontal(Block $$0) {
/*  873 */       ResourceLocation $$1 = ModelTemplates.CUBE_COLUMN.create($$0, this.logMapping, BlockModelGenerators.this.modelOutput);
/*  874 */       ResourceLocation $$2 = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create($$0, this.logMapping, BlockModelGenerators.this.modelOutput);
/*  875 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createRotatedPillarWithHorizontalVariant($$0, $$1, $$2));
/*  876 */       return this;
/*      */     }
/*      */     
/*      */     public WoodProvider logUVLocked(Block $$0) {
/*  880 */       BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPillarBlockUVLocked($$0, this.logMapping, BlockModelGenerators.this.modelOutput));
/*  881 */       return this;
/*      */     }
/*      */   }
/*      */   
/*      */   private WoodProvider woodProvider(Block $$0) {
/*  886 */     return new WoodProvider(TextureMapping.logColumn($$0));
/*      */   }
/*      */   
/*      */   private void createNonTemplateModelBlock(Block $$0) {
/*  890 */     createNonTemplateModelBlock($$0, $$0);
/*      */   }
/*      */   
/*      */   private void createNonTemplateModelBlock(Block $$0, Block $$1) {
/*  894 */     this.blockStateOutput.accept(createSimpleBlock($$0, ModelLocationUtils.getModelLocation($$1)));
/*      */   }
/*      */   
/*      */   private enum TintState {
/*  898 */     TINTED,
/*  899 */     NOT_TINTED;
/*      */     
/*      */     public ModelTemplate getCross() {
/*  902 */       return (this == TINTED) ? ModelTemplates.TINTED_CROSS : ModelTemplates.CROSS;
/*      */     }
/*      */     
/*      */     public ModelTemplate getCrossPot() {
/*  906 */       return (this == TINTED) ? ModelTemplates.TINTED_FLOWER_POT_CROSS : ModelTemplates.FLOWER_POT_CROSS;
/*      */     }
/*      */   }
/*      */   
/*      */   private void createCrossBlockWithDefaultItem(Block $$0, TintState $$1) {
/*  911 */     createSimpleFlatItemModel($$0);
/*  912 */     createCrossBlock($$0, $$1);
/*      */   }
/*      */   
/*      */   private void createCrossBlockWithDefaultItem(Block $$0, TintState $$1, TextureMapping $$2) {
/*  916 */     createSimpleFlatItemModel($$0);
/*  917 */     createCrossBlock($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   private void createCrossBlock(Block $$0, TintState $$1) {
/*  921 */     TextureMapping $$2 = TextureMapping.cross($$0);
/*  922 */     createCrossBlock($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   private void createCrossBlock(Block $$0, TintState $$1, TextureMapping $$2) {
/*  926 */     ResourceLocation $$3 = $$1.getCross().create($$0, $$2, this.modelOutput);
/*  927 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$3));
/*      */   }
/*      */   
/*      */   private void createCrossBlock(Block $$0, TintState $$1, Property<Integer> $$2, int... $$3) {
/*  931 */     if ($$2.getPossibleValues().size() != $$3.length) {
/*  932 */       throw new IllegalArgumentException("missing values for property: " + $$2);
/*      */     }
/*      */     
/*  935 */     PropertyDispatch $$4 = PropertyDispatch.property($$2).generate($$3 -> {
/*      */           String $$4 = "_stage" + $$0[$$3.intValue()];
/*      */           
/*      */           TextureMapping $$5 = TextureMapping.cross(TextureMapping.getBlockTexture($$1, $$4));
/*      */           
/*      */           ResourceLocation $$6 = $$2.getCross().createWithSuffix($$1, $$4, $$5, this.modelOutput);
/*      */           return Variant.variant().with(VariantProperties.MODEL, $$6);
/*      */         });
/*  943 */     createSimpleFlatItemModel($$0.asItem());
/*  944 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0).with($$4));
/*      */   }
/*      */   
/*      */   private void createPlant(Block $$0, Block $$1, TintState $$2) {
/*  948 */     createCrossBlockWithDefaultItem($$0, $$2);
/*      */     
/*  950 */     TextureMapping $$3 = TextureMapping.plant($$0);
/*  951 */     ResourceLocation $$4 = $$2.getCrossPot().create($$1, $$3, this.modelOutput);
/*  952 */     this.blockStateOutput.accept(createSimpleBlock($$1, $$4));
/*      */   }
/*      */   
/*      */   private void createCoralFans(Block $$0, Block $$1) {
/*  956 */     TexturedModel $$2 = TexturedModel.CORAL_FAN.get($$0);
/*      */     
/*  958 */     ResourceLocation $$3 = $$2.create($$0, this.modelOutput);
/*  959 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$3));
/*      */     
/*  961 */     ResourceLocation $$4 = ModelTemplates.CORAL_WALL_FAN.create($$1, $$2.getMapping(), this.modelOutput);
/*  962 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$1, Variant.variant().with(VariantProperties.MODEL, $$4)).with(createHorizontalFacingDispatch()));
/*      */     
/*  964 */     createSimpleFlatItemModel($$0);
/*      */   }
/*      */   
/*      */   private void createStems(Block $$0, Block $$1) {
/*  968 */     createSimpleFlatItemModel($$0.asItem());
/*  969 */     TextureMapping $$2 = TextureMapping.stem($$0);
/*  970 */     TextureMapping $$3 = TextureMapping.attachedStem($$0, $$1);
/*      */     
/*  972 */     ResourceLocation $$4 = ModelTemplates.ATTACHED_STEM.create($$1, $$3, this.modelOutput);
/*  973 */     this.blockStateOutput.accept(
/*  974 */         MultiVariantGenerator.multiVariant($$1, Variant.variant()
/*  975 */           .with(VariantProperties.MODEL, $$4))
/*  976 */         .with((PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.HORIZONTAL_FACING)
/*  977 */           .select((Comparable)Direction.WEST, Variant.variant())
/*  978 */           .select((Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*  979 */           .select((Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*  980 */           .select((Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))));
/*      */ 
/*      */     
/*  983 */     this.blockStateOutput.accept(
/*  984 */         MultiVariantGenerator.multiVariant($$0)
/*  985 */         .with(
/*  986 */           PropertyDispatch.property((Property)BlockStateProperties.AGE_7).generate($$2 -> Variant.variant().with(VariantProperties.MODEL, ModelTemplates.STEMS[$$2.intValue()].create($$0, $$1, this.modelOutput)))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createPitcherPlant() {
/*  992 */     Block $$0 = Blocks.PITCHER_PLANT;
/*  993 */     createSimpleFlatItemModel($$0.asItem());
/*  994 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation($$0, "_top");
/*  995 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation($$0, "_bottom");
/*  996 */     createDoubleBlock($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   private void createPitcherCrop() {
/* 1000 */     Block $$0 = Blocks.PITCHER_CROP;
/*      */     
/* 1002 */     createSimpleFlatItemModel($$0.asItem());
/*      */ 
/*      */ 
/*      */     
/* 1006 */     PropertyDispatch $$1 = PropertyDispatch.properties((Property)PitcherCropBlock.AGE, (Property)BlockStateProperties.DOUBLE_BLOCK_HALF).generate(($$1, $$2) -> { switch ($$2) { default:
/*      */               throw new IncompatibleClassChangeError();
/*      */             case UPPER:
/*      */             
/*      */             case LOWER:
/*      */               break; }
/*      */            return Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation($$0, "_bottom_stage_" + $$1));
/* 1013 */         }); this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0).with($$1));
/*      */   }
/*      */   
/*      */   private void createCoral(Block $$0, Block $$1, Block $$2, Block $$3, Block $$4, Block $$5, Block $$6, Block $$7) {
/* 1017 */     createCrossBlockWithDefaultItem($$0, TintState.NOT_TINTED);
/* 1018 */     createCrossBlockWithDefaultItem($$1, TintState.NOT_TINTED);
/*      */     
/* 1020 */     createTrivialCube($$2);
/* 1021 */     createTrivialCube($$3);
/*      */     
/* 1023 */     createCoralFans($$4, $$6);
/* 1024 */     createCoralFans($$5, $$7);
/*      */   }
/*      */   
/*      */   private void createDoublePlant(Block $$0, TintState $$1) {
/* 1028 */     createSimpleFlatItemModel($$0, "_top");
/* 1029 */     ResourceLocation $$2 = createSuffixedVariant($$0, "_top", $$1.getCross(), TextureMapping::cross);
/* 1030 */     ResourceLocation $$3 = createSuffixedVariant($$0, "_bottom", $$1.getCross(), TextureMapping::cross);
/* 1031 */     createDoubleBlock($$0, $$2, $$3);
/*      */   }
/*      */   
/*      */   private void createSunflower() {
/* 1035 */     createSimpleFlatItemModel(Blocks.SUNFLOWER, "_front");
/* 1036 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.SUNFLOWER, "_top");
/* 1037 */     ResourceLocation $$1 = createSuffixedVariant(Blocks.SUNFLOWER, "_bottom", TintState.NOT_TINTED.getCross(), TextureMapping::cross);
/* 1038 */     createDoubleBlock(Blocks.SUNFLOWER, $$0, $$1);
/*      */   }
/*      */   
/*      */   private void createTallSeagrass() {
/* 1042 */     ResourceLocation $$0 = createSuffixedVariant(Blocks.TALL_SEAGRASS, "_top", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
/* 1043 */     ResourceLocation $$1 = createSuffixedVariant(Blocks.TALL_SEAGRASS, "_bottom", ModelTemplates.SEAGRASS, TextureMapping::defaultTexture);
/* 1044 */     createDoubleBlock(Blocks.TALL_SEAGRASS, $$0, $$1);
/*      */   }
/*      */   
/*      */   private void createSmallDripleaf() {
/* 1048 */     skipAutoItemBlock(Blocks.SMALL_DRIPLEAF);
/* 1049 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.SMALL_DRIPLEAF, "_top");
/* 1050 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.SMALL_DRIPLEAF, "_bottom");
/* 1051 */     this.blockStateOutput.accept(
/* 1052 */         MultiVariantGenerator.multiVariant(Blocks.SMALL_DRIPLEAF)
/* 1053 */         .with(createHorizontalFacingDispatch())
/* 1054 */         .with(
/* 1055 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.DOUBLE_BLOCK_HALF)
/* 1056 */           .select((Comparable)DoubleBlockHalf.LOWER, Variant.variant().with(VariantProperties.MODEL, $$1))
/* 1057 */           .select((Comparable)DoubleBlockHalf.UPPER, Variant.variant().with(VariantProperties.MODEL, $$0))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createDoubleBlock(Block $$0, ResourceLocation $$1, ResourceLocation $$2) {
/* 1063 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0)
/* 1064 */         .with(
/* 1065 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.DOUBLE_BLOCK_HALF)
/* 1066 */           .select((Comparable)DoubleBlockHalf.LOWER, Variant.variant().with(VariantProperties.MODEL, $$2))
/* 1067 */           .select((Comparable)DoubleBlockHalf.UPPER, Variant.variant().with(VariantProperties.MODEL, $$1))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createPassiveRail(Block $$0) {
/* 1073 */     TextureMapping $$1 = TextureMapping.rail($$0);
/* 1074 */     TextureMapping $$2 = TextureMapping.rail(TextureMapping.getBlockTexture($$0, "_corner"));
/*      */     
/* 1076 */     ResourceLocation $$3 = ModelTemplates.RAIL_FLAT.create($$0, $$1, this.modelOutput);
/* 1077 */     ResourceLocation $$4 = ModelTemplates.RAIL_CURVED.create($$0, $$2, this.modelOutput);
/* 1078 */     ResourceLocation $$5 = ModelTemplates.RAIL_RAISED_NE.create($$0, $$1, this.modelOutput);
/* 1079 */     ResourceLocation $$6 = ModelTemplates.RAIL_RAISED_SW.create($$0, $$1, this.modelOutput);
/*      */     
/* 1081 */     createSimpleFlatItemModel($$0);
/* 1082 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0)
/* 1083 */         .with(
/* 1084 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.RAIL_SHAPE)
/* 1085 */           .select((Comparable)RailShape.NORTH_SOUTH, Variant.variant().with(VariantProperties.MODEL, $$3))
/* 1086 */           .select((Comparable)RailShape.EAST_WEST, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */           
/* 1088 */           .select((Comparable)RailShape.ASCENDING_EAST, Variant.variant().with(VariantProperties.MODEL, $$5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1089 */           .select((Comparable)RailShape.ASCENDING_WEST, Variant.variant().with(VariantProperties.MODEL, $$6).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1090 */           .select((Comparable)RailShape.ASCENDING_NORTH, Variant.variant().with(VariantProperties.MODEL, $$5))
/* 1091 */           .select((Comparable)RailShape.ASCENDING_SOUTH, Variant.variant().with(VariantProperties.MODEL, $$6))
/*      */           
/* 1093 */           .select((Comparable)RailShape.SOUTH_EAST, Variant.variant().with(VariantProperties.MODEL, $$4))
/* 1094 */           .select((Comparable)RailShape.SOUTH_WEST, Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1095 */           .select((Comparable)RailShape.NORTH_WEST, Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1096 */           .select((Comparable)RailShape.NORTH_EAST, Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createActiveRail(Block $$0) {
/* 1102 */     ResourceLocation $$1 = createSuffixedVariant($$0, "", ModelTemplates.RAIL_FLAT, TextureMapping::rail);
/* 1103 */     ResourceLocation $$2 = createSuffixedVariant($$0, "", ModelTemplates.RAIL_RAISED_NE, TextureMapping::rail);
/* 1104 */     ResourceLocation $$3 = createSuffixedVariant($$0, "", ModelTemplates.RAIL_RAISED_SW, TextureMapping::rail);
/*      */     
/* 1106 */     ResourceLocation $$4 = createSuffixedVariant($$0, "_on", ModelTemplates.RAIL_FLAT, TextureMapping::rail);
/* 1107 */     ResourceLocation $$5 = createSuffixedVariant($$0, "_on", ModelTemplates.RAIL_RAISED_NE, TextureMapping::rail);
/* 1108 */     ResourceLocation $$6 = createSuffixedVariant($$0, "_on", ModelTemplates.RAIL_RAISED_SW, TextureMapping::rail);
/*      */ 
/*      */ 
/*      */     
/* 1112 */     PropertyDispatch $$7 = PropertyDispatch.properties((Property)BlockStateProperties.POWERED, (Property)BlockStateProperties.RAIL_SHAPE_STRAIGHT).generate(($$6, $$7) -> {
/*      */           switch ($$7) {
/*      */             case UPPER:
/*      */               return Variant.variant().with(VariantProperties.MODEL, $$6.booleanValue() ? $$0 : $$1);
/*      */             
/*      */             case LOWER:
/*      */               return Variant.variant().with(VariantProperties.MODEL, $$6.booleanValue() ? $$0 : $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
/*      */             
/*      */             case null:
/*      */               return Variant.variant().with(VariantProperties.MODEL, $$6.booleanValue() ? $$2 : $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
/*      */             case null:
/*      */               return Variant.variant().with(VariantProperties.MODEL, $$6.booleanValue() ? $$4 : $$5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
/*      */             case null:
/*      */               return Variant.variant().with(VariantProperties.MODEL, $$6.booleanValue() ? $$2 : $$3);
/*      */             case null:
/*      */               return Variant.variant().with(VariantProperties.MODEL, $$6.booleanValue() ? $$4 : $$5);
/*      */           } 
/*      */           throw new UnsupportedOperationException("Fix you generator!");
/*      */         });
/* 1131 */     createSimpleFlatItemModel($$0);
/* 1132 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0).with($$7));
/*      */   }
/*      */   
/*      */   private class BlockEntityModelGenerator {
/*      */     private final ResourceLocation baseModel;
/*      */     
/*      */     public BlockEntityModelGenerator(ResourceLocation $$0, Block $$1) {
/* 1139 */       this.baseModel = ModelTemplates.PARTICLE_ONLY.create($$0, TextureMapping.particle($$1), BlockModelGenerators.this.modelOutput);
/*      */     }
/*      */     
/*      */     public BlockEntityModelGenerator create(Block... $$0) {
/* 1143 */       for (Block $$1 : $$0) {
/* 1144 */         BlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock($$1, this.baseModel));
/*      */       }
/* 1146 */       return this;
/*      */     }
/*      */     
/*      */     public BlockEntityModelGenerator createWithoutBlockItem(Block... $$0) {
/* 1150 */       for (Block $$1 : $$0) {
/* 1151 */         BlockModelGenerators.this.skipAutoItemBlock($$1);
/*      */       }
/* 1153 */       return create($$0);
/*      */     }
/*      */     
/*      */     public BlockEntityModelGenerator createWithCustomBlockItemModel(ModelTemplate $$0, Block... $$1) {
/* 1157 */       for (Block $$2 : $$1) {
/* 1158 */         $$0.create(ModelLocationUtils.getModelLocation($$2.asItem()), TextureMapping.particle($$2), BlockModelGenerators.this.modelOutput);
/*      */       }
/* 1160 */       return create($$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private BlockEntityModelGenerator blockEntityModels(ResourceLocation $$0, Block $$1) {
/* 1165 */     return new BlockEntityModelGenerator($$0, $$1);
/*      */   }
/*      */   
/*      */   private BlockEntityModelGenerator blockEntityModels(Block $$0, Block $$1) {
/* 1169 */     return new BlockEntityModelGenerator(ModelLocationUtils.getModelLocation($$0), $$1);
/*      */   }
/*      */   
/*      */   private void createAirLikeBlock(Block $$0, Item $$1) {
/* 1173 */     ResourceLocation $$2 = ModelTemplates.PARTICLE_ONLY.create($$0, TextureMapping.particleFromItem($$1), this.modelOutput);
/* 1174 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$2));
/*      */   }
/*      */   
/*      */   private void createAirLikeBlock(Block $$0, ResourceLocation $$1) {
/* 1178 */     ResourceLocation $$2 = ModelTemplates.PARTICLE_ONLY.create($$0, TextureMapping.particle($$1), this.modelOutput);
/* 1179 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$2));
/*      */   }
/*      */   
/*      */   private void createFullAndCarpetBlocks(Block $$0, Block $$1) {
/* 1183 */     createTrivialCube($$0);
/*      */ 
/*      */     
/* 1186 */     ResourceLocation $$2 = TexturedModel.CARPET.get($$0).create($$1, this.modelOutput);
/* 1187 */     this.blockStateOutput.accept(createSimpleBlock($$1, $$2));
/*      */   }
/*      */   
/*      */   private void createFlowerBed(Block $$0) {
/* 1191 */     createSimpleFlatItemModel($$0.asItem());
/* 1192 */     ResourceLocation $$1 = TexturedModel.FLOWERBED_1.create($$0, this.modelOutput);
/* 1193 */     ResourceLocation $$2 = TexturedModel.FLOWERBED_2.create($$0, this.modelOutput);
/* 1194 */     ResourceLocation $$3 = TexturedModel.FLOWERBED_3.create($$0, this.modelOutput);
/* 1195 */     ResourceLocation $$4 = TexturedModel.FLOWERBED_4.create($$0, this.modelOutput);
/*      */     
/* 1197 */     this.blockStateOutput.accept(
/* 1198 */         MultiPartGenerator.multiPart($$0)
/* 1199 */         .with(
/* 1200 */           (Condition)Condition.condition()
/* 1201 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(1), (Comparable[])new Integer[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)
/* 1202 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.NORTH), 
/* 1203 */           Variant.variant().with(VariantProperties.MODEL, $$1))
/*      */         
/* 1205 */         .with(
/* 1206 */           (Condition)Condition.condition()
/* 1207 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(1), (Comparable[])new Integer[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)
/* 1208 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.EAST), 
/* 1209 */           Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 1211 */         .with(
/* 1212 */           (Condition)Condition.condition()
/* 1213 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(1), (Comparable[])new Integer[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)
/* 1214 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.SOUTH), 
/* 1215 */           Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*      */         
/* 1217 */         .with(
/* 1218 */           (Condition)Condition.condition()
/* 1219 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(1), (Comparable[])new Integer[] { Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)
/* 1220 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.WEST), 
/* 1221 */           Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */         
/* 1223 */         .with(
/* 1224 */           (Condition)Condition.condition()
/* 1225 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(2), (Comparable[])new Integer[] { Integer.valueOf(3), Integer.valueOf(4)
/* 1226 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.NORTH), 
/* 1227 */           Variant.variant().with(VariantProperties.MODEL, $$2))
/*      */         
/* 1229 */         .with(
/* 1230 */           (Condition)Condition.condition()
/* 1231 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(2), (Comparable[])new Integer[] { Integer.valueOf(3), Integer.valueOf(4)
/* 1232 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.EAST), 
/* 1233 */           Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 1235 */         .with(
/* 1236 */           (Condition)Condition.condition()
/* 1237 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(2), (Comparable[])new Integer[] { Integer.valueOf(3), Integer.valueOf(4)
/* 1238 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.SOUTH), 
/* 1239 */           Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*      */         
/* 1241 */         .with(
/* 1242 */           (Condition)Condition.condition()
/* 1243 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(2), (Comparable[])new Integer[] { Integer.valueOf(3), Integer.valueOf(4)
/* 1244 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.WEST), 
/* 1245 */           Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */         
/* 1247 */         .with(
/* 1248 */           (Condition)Condition.condition()
/* 1249 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(3), (Comparable[])new Integer[] { Integer.valueOf(4)
/* 1250 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.NORTH), 
/* 1251 */           Variant.variant().with(VariantProperties.MODEL, $$3))
/*      */         
/* 1253 */         .with(
/* 1254 */           (Condition)Condition.condition()
/* 1255 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(3), (Comparable[])new Integer[] { Integer.valueOf(4)
/* 1256 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.EAST), 
/* 1257 */           Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 1259 */         .with(
/* 1260 */           (Condition)Condition.condition()
/* 1261 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(3), (Comparable[])new Integer[] { Integer.valueOf(4)
/* 1262 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.SOUTH), 
/* 1263 */           Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*      */         
/* 1265 */         .with(
/* 1266 */           (Condition)Condition.condition()
/* 1267 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(3), (Comparable[])new Integer[] { Integer.valueOf(4)
/* 1268 */             }).term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.WEST), 
/* 1269 */           Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */         
/* 1271 */         .with(
/* 1272 */           (Condition)Condition.condition()
/* 1273 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(4))
/* 1274 */           .term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.NORTH), 
/* 1275 */           Variant.variant().with(VariantProperties.MODEL, $$4))
/*      */         
/* 1277 */         .with(
/* 1278 */           (Condition)Condition.condition()
/* 1279 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(4))
/* 1280 */           .term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.EAST), 
/* 1281 */           Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 1283 */         .with(
/* 1284 */           (Condition)Condition.condition()
/* 1285 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(4))
/* 1286 */           .term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.SOUTH), 
/* 1287 */           Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*      */         
/* 1289 */         .with(
/* 1290 */           (Condition)Condition.condition()
/* 1291 */           .term((Property)BlockStateProperties.FLOWER_AMOUNT, Integer.valueOf(4))
/* 1292 */           .term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)Direction.WEST), 
/* 1293 */           Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createColoredBlockWithRandomRotations(TexturedModel.Provider $$0, Block... $$1) {
/* 1299 */     for (Block $$2 : $$1) {
/* 1300 */       ResourceLocation $$3 = $$0.create($$2, this.modelOutput);
/* 1301 */       this.blockStateOutput.accept(createRotatedVariant($$2, $$3));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void createColoredBlockWithStateRotations(TexturedModel.Provider $$0, Block... $$1) {
/* 1306 */     for (Block $$2 : $$1) {
/* 1307 */       ResourceLocation $$3 = $$0.create($$2, this.modelOutput);
/* 1308 */       this.blockStateOutput.accept(
/* 1309 */           MultiVariantGenerator.multiVariant($$2, Variant.variant().with(VariantProperties.MODEL, $$3))
/* 1310 */           .with(createHorizontalFacingDispatchAlt()));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void createGlassBlocks(Block $$0, Block $$1) {
/* 1316 */     createTrivialCube($$0);
/*      */     
/* 1318 */     TextureMapping $$2 = TextureMapping.pane($$0, $$1);
/* 1319 */     ResourceLocation $$3 = ModelTemplates.STAINED_GLASS_PANE_POST.create($$1, $$2, this.modelOutput);
/* 1320 */     ResourceLocation $$4 = ModelTemplates.STAINED_GLASS_PANE_SIDE.create($$1, $$2, this.modelOutput);
/* 1321 */     ResourceLocation $$5 = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create($$1, $$2, this.modelOutput);
/* 1322 */     ResourceLocation $$6 = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create($$1, $$2, this.modelOutput);
/* 1323 */     ResourceLocation $$7 = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create($$1, $$2, this.modelOutput);
/*      */     
/* 1325 */     Item $$8 = $$1.asItem();
/* 1326 */     ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation($$8), TextureMapping.layer0($$0), this.modelOutput);
/*      */     
/* 1328 */     this.blockStateOutput.accept(
/* 1329 */         MultiPartGenerator.multiPart($$1)
/* 1330 */         .with(Variant.variant().with(VariantProperties.MODEL, $$3))
/* 1331 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$4))
/* 1332 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1333 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$5))
/* 1334 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 1336 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$6))
/* 1337 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$7))
/* 1338 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$7).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1339 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$6).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createCommandBlock(Block $$0) {
/* 1344 */     TextureMapping $$1 = TextureMapping.commandBlock($$0);
/*      */     
/* 1346 */     ResourceLocation $$2 = ModelTemplates.COMMAND_BLOCK.create($$0, $$1, this.modelOutput);
/* 1347 */     ResourceLocation $$3 = createSuffixedVariant($$0, "_conditional", ModelTemplates.COMMAND_BLOCK, $$1 -> $$0.copyAndUpdate(TextureSlot.SIDE, $$1));
/*      */     
/* 1349 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0)
/* 1350 */         .with(createBooleanModelDispatch(BlockStateProperties.CONDITIONAL, $$3, $$2))
/* 1351 */         .with(createFacingDispatch()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createAnvil(Block $$0) {
/* 1356 */     ResourceLocation $$1 = TexturedModel.ANVIL.create($$0, this.modelOutput);
/* 1357 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$1).with(createHorizontalFacingDispatchAlt()));
/*      */   }
/*      */   
/*      */   private List<Variant> createBambooModels(int $$0) {
/* 1361 */     String $$1 = "_age" + $$0;
/* 1362 */     return (List<Variant>)IntStream.range(1, 5)
/* 1363 */       .mapToObj($$1 -> Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.BAMBOO, "" + $$1 + $$1)))
/* 1364 */       .collect(Collectors.toList());
/*      */   }
/*      */   
/*      */   private void createBamboo() {
/* 1368 */     skipAutoItemBlock(Blocks.BAMBOO);
/* 1369 */     this.blockStateOutput.accept(
/* 1370 */         MultiPartGenerator.multiPart(Blocks.BAMBOO)
/* 1371 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.AGE_1, Integer.valueOf(0)), createBambooModels(0))
/* 1372 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.AGE_1, Integer.valueOf(1)), createBambooModels(1))
/* 1373 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.BAMBOO_LEAVES, (Comparable)BambooLeaves.SMALL), 
/* 1374 */           Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.BAMBOO, "_small_leaves")))
/*      */         
/* 1376 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.BAMBOO_LEAVES, (Comparable)BambooLeaves.LARGE), 
/* 1377 */           Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.BAMBOO, "_large_leaves"))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyDispatch createColumnWithFacing() {
/* 1383 */     return (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.FACING)
/* 1384 */       .select((Comparable)Direction.DOWN, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
/* 1385 */       .select((Comparable)Direction.UP, Variant.variant())
/* 1386 */       .select((Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
/* 1387 */       .select((Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1388 */       .select((Comparable)Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/* 1389 */       .select((Comparable)Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
/*      */   }
/*      */   
/*      */   private void createBarrel() {
/* 1393 */     ResourceLocation $$0 = TextureMapping.getBlockTexture(Blocks.BARREL, "_top_open");
/*      */     
/* 1395 */     this.blockStateOutput.accept(
/* 1396 */         MultiVariantGenerator.multiVariant(Blocks.BARREL)
/* 1397 */         .with(createColumnWithFacing())
/* 1398 */         .with((PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.OPEN)
/* 1399 */           .select(Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, TexturedModel.CUBE_TOP_BOTTOM.create(Blocks.BARREL, this.modelOutput)))
/* 1400 */           .select(Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, TexturedModel.CUBE_TOP_BOTTOM.get(Blocks.BARREL).updateTextures($$1 -> $$1.put(TextureSlot.TOP, $$0)).createWithSuffix(Blocks.BARREL, "_open", this.modelOutput)))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T extends Comparable<T>> PropertyDispatch createEmptyOrFullDispatch(Property<T> $$0, T $$1, ResourceLocation $$2, ResourceLocation $$3) {
/* 1406 */     Variant $$4 = Variant.variant().with(VariantProperties.MODEL, $$2);
/* 1407 */     Variant $$5 = Variant.variant().with(VariantProperties.MODEL, $$3);
/*      */     
/* 1409 */     return PropertyDispatch.property($$0).generate($$3 -> {
/*      */           boolean $$4 = ($$3.compareTo($$0) >= 0);
/*      */           return $$4 ? $$1 : $$2;
/*      */         });
/*      */   }
/*      */   
/*      */   private void createBeeNest(Block $$0, Function<Block, TextureMapping> $$1) {
/* 1416 */     TextureMapping $$2 = ((TextureMapping)$$1.apply($$0)).copyForced(TextureSlot.SIDE, TextureSlot.PARTICLE);
/* 1417 */     TextureMapping $$3 = $$2.copyAndUpdate(TextureSlot.FRONT, TextureMapping.getBlockTexture($$0, "_front_honey"));
/*      */     
/* 1419 */     ResourceLocation $$4 = ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.create($$0, $$2, this.modelOutput);
/* 1420 */     ResourceLocation $$5 = ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.createWithSuffix($$0, "_honey", $$3, this.modelOutput);
/*      */     
/* 1422 */     this.blockStateOutput.accept(
/* 1423 */         MultiVariantGenerator.multiVariant($$0)
/* 1424 */         .with(createHorizontalFacingDispatch())
/* 1425 */         .with(createEmptyOrFullDispatch((Property<Integer>)BlockStateProperties.LEVEL_HONEY, Integer.valueOf(5), $$5, $$4)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createCropBlock(Block $$0, Property<Integer> $$1, int... $$2) {
/* 1430 */     if ($$1.getPossibleValues().size() != $$2.length) {
/* 1431 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/* 1434 */     Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
/*      */     
/* 1436 */     PropertyDispatch $$4 = PropertyDispatch.property($$1).generate($$3 -> {
/*      */           int $$4 = $$0[$$3.intValue()];
/*      */           
/*      */           ResourceLocation $$5 = (ResourceLocation)$$1.computeIfAbsent($$4, ());
/*      */           return Variant.variant().with(VariantProperties.MODEL, $$5);
/*      */         });
/* 1442 */     createSimpleFlatItemModel($$0.asItem());
/* 1443 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0).with($$4));
/*      */   }
/*      */   
/*      */   private void createBell() {
/* 1447 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.BELL, "_floor");
/* 1448 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.BELL, "_ceiling");
/* 1449 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation(Blocks.BELL, "_wall");
/* 1450 */     ResourceLocation $$3 = ModelLocationUtils.getModelLocation(Blocks.BELL, "_between_walls");
/*      */     
/* 1452 */     createSimpleFlatItemModel(Items.BELL);
/* 1453 */     this.blockStateOutput.accept(
/* 1454 */         MultiVariantGenerator.multiVariant(Blocks.BELL)
/* 1455 */         .with(
/* 1456 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.HORIZONTAL_FACING, (Property)BlockStateProperties.BELL_ATTACHMENT)
/* 1457 */           .select((Comparable)Direction.NORTH, (Comparable)BellAttachType.FLOOR, Variant.variant().with(VariantProperties.MODEL, $$0))
/* 1458 */           .select((Comparable)Direction.SOUTH, (Comparable)BellAttachType.FLOOR, Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1459 */           .select((Comparable)Direction.EAST, (Comparable)BellAttachType.FLOOR, Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1460 */           .select((Comparable)Direction.WEST, (Comparable)BellAttachType.FLOOR, Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */           
/* 1462 */           .select((Comparable)Direction.NORTH, (Comparable)BellAttachType.CEILING, Variant.variant().with(VariantProperties.MODEL, $$1))
/* 1463 */           .select((Comparable)Direction.SOUTH, (Comparable)BellAttachType.CEILING, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1464 */           .select((Comparable)Direction.EAST, (Comparable)BellAttachType.CEILING, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1465 */           .select((Comparable)Direction.WEST, (Comparable)BellAttachType.CEILING, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */           
/* 1467 */           .select((Comparable)Direction.NORTH, (Comparable)BellAttachType.SINGLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/* 1468 */           .select((Comparable)Direction.SOUTH, (Comparable)BellAttachType.SINGLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1469 */           .select((Comparable)Direction.EAST, (Comparable)BellAttachType.SINGLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$2))
/* 1470 */           .select((Comparable)Direction.WEST, (Comparable)BellAttachType.SINGLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*      */           
/* 1472 */           .select((Comparable)Direction.SOUTH, (Comparable)BellAttachType.DOUBLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1473 */           .select((Comparable)Direction.NORTH, (Comparable)BellAttachType.DOUBLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/* 1474 */           .select((Comparable)Direction.EAST, (Comparable)BellAttachType.DOUBLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$3))
/* 1475 */           .select((Comparable)Direction.WEST, (Comparable)BellAttachType.DOUBLE_WALL, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createGrindstone() {
/* 1481 */     this.blockStateOutput.accept(
/* 1482 */         MultiVariantGenerator.multiVariant(Blocks.GRINDSTONE, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.GRINDSTONE)))
/* 1483 */         .with(
/* 1484 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.ATTACH_FACE, (Property)BlockStateProperties.HORIZONTAL_FACING)
/* 1485 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.NORTH, Variant.variant())
/* 1486 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1487 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1488 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */           
/* 1490 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
/* 1491 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1492 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1493 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */           
/* 1495 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
/* 1496 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1497 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1498 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createFurnace(Block $$0, TexturedModel.Provider $$1) {
/* 1504 */     ResourceLocation $$2 = $$1.create($$0, this.modelOutput);
/*      */     
/* 1506 */     ResourceLocation $$3 = TextureMapping.getBlockTexture($$0, "_front_on");
/* 1507 */     ResourceLocation $$4 = $$1.get($$0).updateTextures($$1 -> $$1.put(TextureSlot.FRONT, $$0)).createWithSuffix($$0, "_on", this.modelOutput);
/*      */     
/* 1509 */     this.blockStateOutput.accept(
/* 1510 */         MultiVariantGenerator.multiVariant($$0)
/* 1511 */         .with(createBooleanModelDispatch(BlockStateProperties.LIT, $$4, $$2))
/* 1512 */         .with(createHorizontalFacingDispatch()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createCampfires(Block... $$0) {
/* 1517 */     ResourceLocation $$1 = ModelLocationUtils.decorateBlockModelLocation("campfire_off");
/*      */     
/* 1519 */     for (Block $$2 : $$0) {
/* 1520 */       ResourceLocation $$3 = ModelTemplates.CAMPFIRE.create($$2, TextureMapping.campfire($$2), this.modelOutput);
/*      */       
/* 1522 */       createSimpleFlatItemModel($$2.asItem());
/* 1523 */       this.blockStateOutput.accept(
/* 1524 */           MultiVariantGenerator.multiVariant($$2)
/* 1525 */           .with(createBooleanModelDispatch(BlockStateProperties.LIT, $$3, $$1))
/* 1526 */           .with(createHorizontalFacingDispatchAlt()));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void createAzalea(Block $$0) {
/* 1532 */     ResourceLocation $$1 = ModelTemplates.AZALEA.create($$0, TextureMapping.cubeTop($$0), this.modelOutput);
/* 1533 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$1));
/*      */   }
/*      */   
/*      */   private void createPottedAzalea(Block $$0) {
/*      */     ResourceLocation $$2;
/* 1538 */     if ($$0 == Blocks.POTTED_FLOWERING_AZALEA) {
/* 1539 */       ResourceLocation $$1 = ModelTemplates.POTTED_FLOWERING_AZALEA.create($$0, TextureMapping.pottedAzalea($$0), this.modelOutput);
/*      */     } else {
/* 1541 */       $$2 = ModelTemplates.POTTED_AZALEA.create($$0, TextureMapping.pottedAzalea($$0), this.modelOutput);
/*      */     } 
/* 1543 */     this.blockStateOutput.accept(createSimpleBlock($$0, $$2));
/*      */   }
/*      */   
/*      */   private void createBookshelf() {
/* 1547 */     TextureMapping $$0 = TextureMapping.column(TextureMapping.getBlockTexture(Blocks.BOOKSHELF), TextureMapping.getBlockTexture(Blocks.OAK_PLANKS));
/* 1548 */     ResourceLocation $$1 = ModelTemplates.CUBE_COLUMN.create(Blocks.BOOKSHELF, $$0, this.modelOutput);
/* 1549 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.BOOKSHELF, $$1));
/*      */   }
/*      */   
/*      */   private void createRedstoneWire() {
/* 1553 */     createSimpleFlatItemModel(Items.REDSTONE);
/* 1554 */     this.blockStateOutput.accept(
/* 1555 */         MultiPartGenerator.multiPart(Blocks.REDSTONE_WIRE)
/* 1556 */         .with(
/* 1557 */           Condition.or(new Condition[] {
/* 1558 */               (Condition)Condition.condition()
/* 1559 */               .term((Property)BlockStateProperties.NORTH_REDSTONE, (Comparable)RedstoneSide.NONE)
/* 1560 */               .term((Property)BlockStateProperties.EAST_REDSTONE, (Comparable)RedstoneSide.NONE)
/* 1561 */               .term((Property)BlockStateProperties.SOUTH_REDSTONE, (Comparable)RedstoneSide.NONE)
/* 1562 */               .term((Property)BlockStateProperties.WEST_REDSTONE, (Comparable)RedstoneSide.NONE), 
/* 1563 */               (Condition)Condition.condition()
/* 1564 */               .term((Property)BlockStateProperties.NORTH_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1565 */                 }).term((Property)BlockStateProperties.EAST_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1566 */                 }), (Condition)Condition.condition()
/* 1567 */               .term((Property)BlockStateProperties.EAST_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1568 */                 }).term((Property)BlockStateProperties.SOUTH_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1569 */                 }), (Condition)Condition.condition()
/* 1570 */               .term((Property)BlockStateProperties.SOUTH_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1571 */                 }).term((Property)BlockStateProperties.WEST_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1572 */                 }), (Condition)Condition.condition()
/* 1573 */               .term((Property)BlockStateProperties.WEST_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1574 */                 }).term((Property)BlockStateProperties.NORTH_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/*      */                 })
/* 1576 */             }), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_dot")))
/*      */         
/* 1578 */         .with(
/* 1579 */           (Condition)Condition.condition()
/* 1580 */           .term((Property)BlockStateProperties.NORTH_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1581 */             }), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side0")))
/*      */         
/* 1583 */         .with(
/* 1584 */           (Condition)Condition.condition()
/* 1585 */           .term((Property)BlockStateProperties.SOUTH_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1586 */             }), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side_alt0")))
/*      */         
/* 1588 */         .with(
/* 1589 */           (Condition)Condition.condition()
/* 1590 */           .term((Property)BlockStateProperties.EAST_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1591 */             }), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side_alt1")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */         
/* 1593 */         .with(
/* 1594 */           (Condition)Condition.condition()
/* 1595 */           .term((Property)BlockStateProperties.WEST_REDSTONE, (Comparable)RedstoneSide.SIDE, (Comparable[])new RedstoneSide[] { RedstoneSide.UP
/* 1596 */             }), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_side1")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */         
/* 1598 */         .with(
/* 1599 */           (Condition)Condition.condition().term((Property)BlockStateProperties.NORTH_REDSTONE, (Comparable)RedstoneSide.UP), 
/* 1600 */           Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up")))
/*      */         
/* 1602 */         .with(
/* 1603 */           (Condition)Condition.condition().term((Property)BlockStateProperties.EAST_REDSTONE, (Comparable)RedstoneSide.UP), 
/* 1604 */           Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 1606 */         .with(
/* 1607 */           (Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH_REDSTONE, (Comparable)RedstoneSide.UP), 
/* 1608 */           Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/*      */         
/* 1610 */         .with(
/* 1611 */           (Condition)Condition.condition().term((Property)BlockStateProperties.WEST_REDSTONE, (Comparable)RedstoneSide.UP), 
/* 1612 */           Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.decorateBlockModelLocation("redstone_dust_up")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createComparator() {
/* 1618 */     createSimpleFlatItemModel(Items.COMPARATOR);
/* 1619 */     this.blockStateOutput.accept(
/* 1620 */         MultiVariantGenerator.multiVariant(Blocks.COMPARATOR)
/* 1621 */         .with(createHorizontalFacingDispatchAlt())
/* 1622 */         .with(
/* 1623 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.MODE_COMPARATOR, (Property)BlockStateProperties.POWERED)
/* 1624 */           .select((Comparable)ComparatorMode.COMPARE, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR)))
/* 1625 */           .select((Comparable)ComparatorMode.COMPARE, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR, "_on")))
/* 1626 */           .select((Comparable)ComparatorMode.SUBTRACT, Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR, "_subtract")))
/* 1627 */           .select((Comparable)ComparatorMode.SUBTRACT, Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COMPARATOR, "_on_subtract")))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSmoothStoneSlab() {
/* 1633 */     TextureMapping $$0 = TextureMapping.cube(Blocks.SMOOTH_STONE);
/* 1634 */     TextureMapping $$1 = TextureMapping.column(
/* 1635 */         TextureMapping.getBlockTexture(Blocks.SMOOTH_STONE_SLAB, "_side"), $$0
/* 1636 */         .get(TextureSlot.TOP));
/*      */     
/* 1638 */     ResourceLocation $$2 = ModelTemplates.SLAB_BOTTOM.create(Blocks.SMOOTH_STONE_SLAB, $$1, this.modelOutput);
/* 1639 */     ResourceLocation $$3 = ModelTemplates.SLAB_TOP.create(Blocks.SMOOTH_STONE_SLAB, $$1, this.modelOutput);
/* 1640 */     ResourceLocation $$4 = ModelTemplates.CUBE_COLUMN.createWithOverride(Blocks.SMOOTH_STONE_SLAB, "_double", $$1, this.modelOutput);
/* 1641 */     this.blockStateOutput.accept(createSlab(Blocks.SMOOTH_STONE_SLAB, $$2, $$3, $$4));
/* 1642 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.SMOOTH_STONE, ModelTemplates.CUBE_ALL.create(Blocks.SMOOTH_STONE, $$0, this.modelOutput)));
/*      */   }
/*      */   
/*      */   private void createBrewingStand() {
/* 1646 */     createSimpleFlatItemModel(Items.BREWING_STAND);
/* 1647 */     this.blockStateOutput.accept(
/* 1648 */         MultiPartGenerator.multiPart(Blocks.BREWING_STAND)
/* 1649 */         .with(Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND)))
/* 1650 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.HAS_BOTTLE_0, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_bottle0")))
/* 1651 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.HAS_BOTTLE_1, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_bottle1")))
/* 1652 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.HAS_BOTTLE_2, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_bottle2")))
/*      */         
/* 1654 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.HAS_BOTTLE_0, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_empty0")))
/* 1655 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.HAS_BOTTLE_1, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_empty1")))
/* 1656 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.HAS_BOTTLE_2, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.BREWING_STAND, "_empty2"))));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createMushroomBlock(Block $$0) {
/* 1661 */     ResourceLocation $$1 = ModelTemplates.SINGLE_FACE.create($$0, TextureMapping.defaultTexture($$0), this.modelOutput);
/* 1662 */     ResourceLocation $$2 = ModelLocationUtils.decorateBlockModelLocation("mushroom_block_inside");
/*      */     
/* 1664 */     this.blockStateOutput.accept(
/* 1665 */         MultiPartGenerator.multiPart($$0)
/* 1666 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$1))
/* 1667 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1668 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1669 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1670 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.UP, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1671 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.DOWN, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*      */         
/* 1673 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$2))
/* 1674 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)))
/* 1675 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)))
/* 1676 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)))
/* 1677 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.UP, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(false)))
/* 1678 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.DOWN, Boolean.valueOf(false)), Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(false))));
/*      */ 
/*      */     
/* 1681 */     delegateItemModel($$0, TexturedModel.CUBE.createWithSuffix($$0, "_inventory", this.modelOutput));
/*      */   }
/*      */   
/*      */   private void createCakeBlock() {
/* 1685 */     createSimpleFlatItemModel(Items.CAKE);
/* 1686 */     this.blockStateOutput.accept(
/* 1687 */         MultiVariantGenerator.multiVariant(Blocks.CAKE)
/* 1688 */         .with(
/* 1689 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.BITES)
/* 1690 */           .select(Integer.valueOf(0), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE)))
/* 1691 */           .select(Integer.valueOf(1), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice1")))
/* 1692 */           .select(Integer.valueOf(2), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice2")))
/* 1693 */           .select(Integer.valueOf(3), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice3")))
/* 1694 */           .select(Integer.valueOf(4), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice4")))
/* 1695 */           .select(Integer.valueOf(5), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice5")))
/* 1696 */           .select(Integer.valueOf(6), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.CAKE, "_slice6")))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createCartographyTable() {
/* 1709 */     TextureMapping $$0 = (new TextureMapping()).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side3")).put(TextureSlot.DOWN, TextureMapping.getBlockTexture(Blocks.DARK_OAK_PLANKS)).put(TextureSlot.UP, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_top")).put(TextureSlot.NORTH, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side3")).put(TextureSlot.EAST, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side3")).put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side1")).put(TextureSlot.WEST, TextureMapping.getBlockTexture(Blocks.CARTOGRAPHY_TABLE, "_side2"));
/*      */     
/* 1711 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.CARTOGRAPHY_TABLE, ModelTemplates.CUBE.create(Blocks.CARTOGRAPHY_TABLE, $$0, this.modelOutput)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSmithingTable() {
/* 1722 */     TextureMapping $$0 = (new TextureMapping()).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(TextureSlot.DOWN, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_bottom")).put(TextureSlot.UP, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_top")).put(TextureSlot.NORTH, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_front")).put(TextureSlot.EAST, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_side")).put(TextureSlot.WEST, TextureMapping.getBlockTexture(Blocks.SMITHING_TABLE, "_side"));
/*      */     
/* 1724 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.SMITHING_TABLE, ModelTemplates.CUBE.create(Blocks.SMITHING_TABLE, $$0, this.modelOutput)));
/*      */   }
/*      */   
/*      */   private void createCraftingTableLike(Block $$0, Block $$1, BiFunction<Block, Block, TextureMapping> $$2) {
/* 1728 */     TextureMapping $$3 = $$2.apply($$0, $$1);
/* 1729 */     this.blockStateOutput.accept(createSimpleBlock($$0, ModelTemplates.CUBE.create($$0, $$3, this.modelOutput)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void createGenericCube(Block $$0) {
/* 1740 */     TextureMapping $$1 = (new TextureMapping()).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture($$0, "_particle")).put(TextureSlot.DOWN, TextureMapping.getBlockTexture($$0, "_down")).put(TextureSlot.UP, TextureMapping.getBlockTexture($$0, "_up")).put(TextureSlot.NORTH, TextureMapping.getBlockTexture($$0, "_north")).put(TextureSlot.SOUTH, TextureMapping.getBlockTexture($$0, "_south")).put(TextureSlot.EAST, TextureMapping.getBlockTexture($$0, "_east")).put(TextureSlot.WEST, TextureMapping.getBlockTexture($$0, "_west"));
/*      */     
/* 1742 */     this.blockStateOutput.accept(createSimpleBlock($$0, ModelTemplates.CUBE.create($$0, $$1, this.modelOutput)));
/*      */   }
/*      */   
/*      */   private void createPumpkins() {
/* 1746 */     TextureMapping $$0 = TextureMapping.column(Blocks.PUMPKIN);
/*      */     
/* 1748 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.PUMPKIN, ModelLocationUtils.getModelLocation(Blocks.PUMPKIN)));
/*      */     
/* 1750 */     createPumpkinVariant(Blocks.CARVED_PUMPKIN, $$0);
/* 1751 */     createPumpkinVariant(Blocks.JACK_O_LANTERN, $$0);
/*      */   }
/*      */   
/*      */   private void createPumpkinVariant(Block $$0, TextureMapping $$1) {
/* 1755 */     ResourceLocation $$2 = ModelTemplates.CUBE_ORIENTABLE.create($$0, $$1.copyAndUpdate(TextureSlot.FRONT, TextureMapping.getBlockTexture($$0)), this.modelOutput);
/* 1756 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, $$2)).with(createHorizontalFacingDispatch()));
/*      */   }
/*      */   
/*      */   private void createCauldrons() {
/* 1760 */     createSimpleFlatItemModel(Items.CAULDRON);
/* 1761 */     createNonTemplateModelBlock(Blocks.CAULDRON);
/*      */     
/* 1763 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.LAVA_CAULDRON, ModelTemplates.CAULDRON_FULL.create(Blocks.LAVA_CAULDRON, TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.LAVA, "_still")), this.modelOutput)));
/*      */     
/* 1765 */     this.blockStateOutput.accept(
/* 1766 */         MultiVariantGenerator.multiVariant(Blocks.WATER_CAULDRON)
/* 1767 */         .with(
/* 1768 */           (PropertyDispatch)PropertyDispatch.property((Property)LayeredCauldronBlock.LEVEL)
/* 1769 */           .select(Integer.valueOf(1), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CAULDRON_LEVEL1.createWithSuffix(Blocks.WATER_CAULDRON, "_level1", TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.WATER, "_still")), this.modelOutput)))
/* 1770 */           .select(Integer.valueOf(2), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CAULDRON_LEVEL2.createWithSuffix(Blocks.WATER_CAULDRON, "_level2", TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.WATER, "_still")), this.modelOutput)))
/* 1771 */           .select(Integer.valueOf(3), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CAULDRON_FULL.createWithSuffix(Blocks.WATER_CAULDRON, "_full", TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.WATER, "_still")), this.modelOutput)))));
/*      */ 
/*      */ 
/*      */     
/* 1775 */     this.blockStateOutput.accept(
/* 1776 */         MultiVariantGenerator.multiVariant(Blocks.POWDER_SNOW_CAULDRON)
/* 1777 */         .with(
/* 1778 */           (PropertyDispatch)PropertyDispatch.property((Property)LayeredCauldronBlock.LEVEL)
/* 1779 */           .select(Integer.valueOf(1), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CAULDRON_LEVEL1.createWithSuffix(Blocks.POWDER_SNOW_CAULDRON, "_level1", TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.POWDER_SNOW)), this.modelOutput)))
/* 1780 */           .select(Integer.valueOf(2), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CAULDRON_LEVEL2.createWithSuffix(Blocks.POWDER_SNOW_CAULDRON, "_level2", TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.POWDER_SNOW)), this.modelOutput)))
/* 1781 */           .select(Integer.valueOf(3), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CAULDRON_FULL.createWithSuffix(Blocks.POWDER_SNOW_CAULDRON, "_full", TextureMapping.cauldron(TextureMapping.getBlockTexture(Blocks.POWDER_SNOW)), this.modelOutput)))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createChorusFlower() {
/* 1787 */     TextureMapping $$0 = TextureMapping.defaultTexture(Blocks.CHORUS_FLOWER);
/* 1788 */     ResourceLocation $$1 = ModelTemplates.CHORUS_FLOWER.create(Blocks.CHORUS_FLOWER, $$0, this.modelOutput);
/* 1789 */     ResourceLocation $$2 = createSuffixedVariant(Blocks.CHORUS_FLOWER, "_dead", ModelTemplates.CHORUS_FLOWER, $$1 -> $$0.copyAndUpdate(TextureSlot.TEXTURE, $$1));
/*      */     
/* 1791 */     this.blockStateOutput.accept(
/* 1792 */         MultiVariantGenerator.multiVariant(Blocks.CHORUS_FLOWER)
/* 1793 */         .with(createEmptyOrFullDispatch((Property<Integer>)BlockStateProperties.AGE_5, Integer.valueOf(5), $$2, $$1)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createCrafterBlock() {
/* 1798 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.CRAFTER);
/* 1799 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.CRAFTER, "_triggered");
/* 1800 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation(Blocks.CRAFTER, "_crafting");
/* 1801 */     ResourceLocation $$3 = ModelLocationUtils.getModelLocation(Blocks.CRAFTER, "_crafting_triggered");
/*      */     
/* 1803 */     this.blockStateOutput.accept(
/* 1804 */         MultiVariantGenerator.multiVariant(Blocks.CRAFTER)
/* 1805 */         .with(PropertyDispatch.property((Property)BlockStateProperties.ORIENTATION)
/* 1806 */           .generate($$0 -> applyRotation($$0, Variant.variant())))
/*      */         
/* 1808 */         .with((PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.TRIGGERED, (Property)CrafterBlock.CRAFTING)
/* 1809 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$0))
/* 1810 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$3))
/* 1811 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$1))
/* 1812 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$2))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createDispenserBlock(Block $$0) {
/* 1820 */     TextureMapping $$1 = (new TextureMapping()).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FURNACE, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.FURNACE, "_side")).put(TextureSlot.FRONT, TextureMapping.getBlockTexture($$0, "_front"));
/*      */ 
/*      */ 
/*      */     
/* 1824 */     TextureMapping $$2 = (new TextureMapping()).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.FURNACE, "_top")).put(TextureSlot.FRONT, TextureMapping.getBlockTexture($$0, "_front_vertical"));
/*      */     
/* 1826 */     ResourceLocation $$3 = ModelTemplates.CUBE_ORIENTABLE.create($$0, $$1, this.modelOutput);
/* 1827 */     ResourceLocation $$4 = ModelTemplates.CUBE_ORIENTABLE_VERTICAL.create($$0, $$2, this.modelOutput);
/*      */     
/* 1829 */     this.blockStateOutput.accept(
/* 1830 */         MultiVariantGenerator.multiVariant($$0)
/* 1831 */         .with(
/* 1832 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.FACING)
/* 1833 */           .select((Comparable)Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
/* 1834 */           .select((Comparable)Direction.UP, Variant.variant().with(VariantProperties.MODEL, $$4))
/*      */           
/* 1836 */           .select((Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, $$3))
/* 1837 */           .select((Comparable)Direction.EAST, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 1838 */           .select((Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 1839 */           .select((Comparable)Direction.WEST, Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createEndPortalFrame() {
/* 1846 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.END_PORTAL_FRAME);
/* 1847 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.END_PORTAL_FRAME, "_filled");
/*      */     
/* 1849 */     this.blockStateOutput.accept(
/* 1850 */         MultiVariantGenerator.multiVariant(Blocks.END_PORTAL_FRAME)
/* 1851 */         .with(
/* 1852 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.EYE)
/* 1853 */           .select(Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$0))
/* 1854 */           .select(Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$1)))
/*      */         
/* 1856 */         .with(
/* 1857 */           createHorizontalFacingDispatchAlt()));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createChorusPlant() {
/* 1863 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_side");
/* 1864 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside");
/* 1865 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside1");
/* 1866 */     ResourceLocation $$3 = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside2");
/* 1867 */     ResourceLocation $$4 = ModelLocationUtils.getModelLocation(Blocks.CHORUS_PLANT, "_noside3");
/*      */     
/* 1869 */     this.blockStateOutput.accept(
/* 1870 */         MultiPartGenerator.multiPart(Blocks.CHORUS_PLANT)
/* 1871 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$0))
/* 1872 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1873 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1874 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1875 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.UP, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/* 1876 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.DOWN, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)))
/*      */         
/* 1878 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false)), new Variant[] {
/* 1879 */             Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.WEIGHT, Integer.valueOf(2)), 
/* 1880 */             Variant.variant().with(VariantProperties.MODEL, $$2), 
/* 1881 */             Variant.variant().with(VariantProperties.MODEL, $$3), 
/* 1882 */             Variant.variant().with(VariantProperties.MODEL, $$4)
/*      */ 
/*      */           
/* 1885 */           }).with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(false)), new Variant[] {
/* 1886 */             Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1887 */             Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1888 */             Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1889 */             Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.WEIGHT, Integer.valueOf(2)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))
/*      */ 
/*      */           
/* 1892 */           }).with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false)), new Variant[] {
/* 1893 */             Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1894 */             Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1895 */             Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.WEIGHT, Integer.valueOf(2)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1896 */             Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))
/*      */ 
/*      */           
/* 1899 */           }).with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)), new Variant[] {
/* 1900 */             Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1901 */             Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.WEIGHT, Integer.valueOf(2)).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1902 */             Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1903 */             Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))
/*      */ 
/*      */           
/* 1906 */           }).with((Condition)Condition.condition().term((Property)BlockStateProperties.UP, Boolean.valueOf(false)), new Variant[] {
/* 1907 */             Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.WEIGHT, Integer.valueOf(2)).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1908 */             Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1909 */             Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1910 */             Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))
/*      */ 
/*      */           
/* 1913 */           }).with((Condition)Condition.condition().term((Property)BlockStateProperties.DOWN, Boolean.valueOf(false)), new Variant[] {
/* 1914 */             Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1915 */             Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1916 */             Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)), 
/* 1917 */             Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.WEIGHT, Integer.valueOf(2)).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))
/*      */           }));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createComposter() {
/* 1923 */     this.blockStateOutput.accept(MultiPartGenerator.multiPart(Blocks.COMPOSTER)
/* 1924 */         .with(Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER)))
/* 1925 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(1)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents1")))
/* 1926 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(2)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents2")))
/* 1927 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(3)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents3")))
/* 1928 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(4)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents4")))
/* 1929 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(5)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents5")))
/* 1930 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(6)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents6")))
/* 1931 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(7)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents7")))
/* 1932 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.LEVEL_COMPOSTER, Integer.valueOf(8)), Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.COMPOSTER, "_contents_ready"))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createCopperBulb(Block $$0) {
/* 1938 */     ResourceLocation $$1 = ModelTemplates.CUBE_ALL.create($$0, TextureMapping.cube($$0), this.modelOutput);
/* 1939 */     ResourceLocation $$2 = createSuffixedVariant($$0, "_powered", ModelTemplates.CUBE_ALL, TextureMapping::cube);
/* 1940 */     ResourceLocation $$3 = createSuffixedVariant($$0, "_lit", ModelTemplates.CUBE_ALL, TextureMapping::cube);
/* 1941 */     ResourceLocation $$4 = createSuffixedVariant($$0, "_lit_powered", ModelTemplates.CUBE_ALL, TextureMapping::cube);
/*      */     
/* 1943 */     this.blockStateOutput.accept(createCopperBulb($$0, $$1, $$3, $$2, $$4));
/*      */   }
/*      */   
/*      */   private BlockStateGenerator createCopperBulb(Block $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3, ResourceLocation $$4) {
/* 1947 */     return (BlockStateGenerator)MultiVariantGenerator.multiVariant($$0)
/* 1948 */       .with(
/* 1949 */         PropertyDispatch.properties((Property)BlockStateProperties.LIT, (Property)BlockStateProperties.POWERED)
/* 1950 */         .generate(($$4, $$5) -> $$4.booleanValue() ? Variant.variant().with(VariantProperties.MODEL, $$5.booleanValue() ? $$0 : $$1) : Variant.variant().with(VariantProperties.MODEL, $$5.booleanValue() ? $$2 : $$3)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void copyCopperBulbModel(Block $$0, Block $$1) {
/* 1960 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation($$0);
/* 1961 */     ResourceLocation $$3 = ModelLocationUtils.getModelLocation($$0, "_powered");
/* 1962 */     ResourceLocation $$4 = ModelLocationUtils.getModelLocation($$0, "_lit");
/* 1963 */     ResourceLocation $$5 = ModelLocationUtils.getModelLocation($$0, "_lit_powered");
/*      */     
/* 1965 */     delegateItemModel($$1, ModelLocationUtils.getModelLocation($$0.asItem()));
/* 1966 */     this.blockStateOutput.accept(createCopperBulb($$1, $$2, $$4, $$3, $$5));
/*      */   }
/*      */   
/*      */   private void createAmethystCluster(Block $$0) {
/* 1970 */     skipAutoItemBlock($$0);
/* 1971 */     this.blockStateOutput.accept(
/* 1972 */         MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CROSS.create($$0, TextureMapping.cross($$0), this.modelOutput)))
/* 1973 */         .with(createColumnWithFacing()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createAmethystClusters() {
/* 1978 */     createAmethystCluster(Blocks.SMALL_AMETHYST_BUD);
/* 1979 */     createAmethystCluster(Blocks.MEDIUM_AMETHYST_BUD);
/* 1980 */     createAmethystCluster(Blocks.LARGE_AMETHYST_BUD);
/* 1981 */     createAmethystCluster(Blocks.AMETHYST_CLUSTER);
/*      */   }
/*      */   
/*      */   private void createPointedDripstone() {
/* 1985 */     skipAutoItemBlock(Blocks.POINTED_DRIPSTONE);
/*      */     
/* 1987 */     PropertyDispatch.C2<Direction, DripstoneThickness> $$0 = PropertyDispatch.properties((Property)BlockStateProperties.VERTICAL_DIRECTION, (Property)BlockStateProperties.DRIPSTONE_THICKNESS);
/* 1988 */     for (DripstoneThickness $$1 : DripstoneThickness.values()) {
/* 1989 */       $$0.select((Comparable)Direction.UP, (Comparable)$$1, createPointedDripstoneVariant(Direction.UP, $$1));
/*      */     }
/* 1991 */     for (DripstoneThickness $$2 : DripstoneThickness.values()) {
/* 1992 */       $$0.select((Comparable)Direction.DOWN, (Comparable)$$2, createPointedDripstoneVariant(Direction.DOWN, $$2));
/*      */     }
/* 1994 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(Blocks.POINTED_DRIPSTONE).with((PropertyDispatch)$$0));
/*      */   }
/*      */   
/*      */   private Variant createPointedDripstoneVariant(Direction $$0, DripstoneThickness $$1) {
/* 1998 */     String $$2 = "_" + $$0.getSerializedName() + "_" + $$1.getSerializedName();
/* 1999 */     TextureMapping $$3 = TextureMapping.cross(TextureMapping.getBlockTexture(Blocks.POINTED_DRIPSTONE, $$2));
/* 2000 */     return Variant.variant().with(VariantProperties.MODEL, ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(Blocks.POINTED_DRIPSTONE, $$2, $$3, this.modelOutput));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNyliumBlock(Block $$0) {
/* 2007 */     TextureMapping $$1 = (new TextureMapping()).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.NETHERRACK)).put(TextureSlot.TOP, TextureMapping.getBlockTexture($$0)).put(TextureSlot.SIDE, TextureMapping.getBlockTexture($$0, "_side"));
/*      */     
/* 2009 */     this.blockStateOutput.accept(createSimpleBlock($$0, ModelTemplates.CUBE_BOTTOM_TOP.create($$0, $$1, this.modelOutput)));
/*      */   }
/*      */   
/*      */   private void createDaylightDetector() {
/* 2013 */     ResourceLocation $$0 = TextureMapping.getBlockTexture(Blocks.DAYLIGHT_DETECTOR, "_side");
/* 2014 */     TextureMapping $$1 = (new TextureMapping()).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.DAYLIGHT_DETECTOR, "_top")).put(TextureSlot.SIDE, $$0);
/* 2015 */     TextureMapping $$2 = (new TextureMapping()).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.DAYLIGHT_DETECTOR, "_inverted_top")).put(TextureSlot.SIDE, $$0);
/*      */     
/* 2017 */     this.blockStateOutput.accept(
/* 2018 */         MultiVariantGenerator.multiVariant(Blocks.DAYLIGHT_DETECTOR)
/* 2019 */         .with(
/* 2020 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.INVERTED)
/* 2021 */           .select(Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.DAYLIGHT_DETECTOR.create(Blocks.DAYLIGHT_DETECTOR, $$1, this.modelOutput)))
/* 2022 */           .select(Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.DAYLIGHT_DETECTOR.create(ModelLocationUtils.getModelLocation(Blocks.DAYLIGHT_DETECTOR, "_inverted"), $$2, this.modelOutput)))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createRotatableColumn(Block $$0) {
/* 2028 */     this.blockStateOutput.accept(
/* 2029 */         MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation($$0)))
/* 2030 */         .with(createColumnWithFacing()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createLightningRod() {
/* 2035 */     Block $$0 = Blocks.LIGHTNING_ROD;
/* 2036 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation($$0, "_on");
/* 2037 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation($$0);
/*      */     
/* 2039 */     this.blockStateOutput.accept(
/* 2040 */         MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation($$0)))
/* 2041 */         .with(createColumnWithFacing())
/* 2042 */         .with(createBooleanModelDispatch(BlockStateProperties.POWERED, $$1, $$2)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createFarmland() {
/* 2047 */     TextureMapping $$0 = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND));
/* 2048 */     TextureMapping $$1 = (new TextureMapping()).put(TextureSlot.DIRT, TextureMapping.getBlockTexture(Blocks.DIRT)).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist"));
/*      */     
/* 2050 */     ResourceLocation $$2 = ModelTemplates.FARMLAND.create(Blocks.FARMLAND, $$0, this.modelOutput);
/* 2051 */     ResourceLocation $$3 = ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(Blocks.FARMLAND, "_moist"), $$1, this.modelOutput);
/*      */     
/* 2053 */     this.blockStateOutput.accept(
/* 2054 */         MultiVariantGenerator.multiVariant(Blocks.FARMLAND)
/* 2055 */         .with(createEmptyOrFullDispatch((Property<Integer>)BlockStateProperties.MOISTURE, Integer.valueOf(7), $$3, $$2)));
/*      */   }
/*      */ 
/*      */   
/*      */   private List<ResourceLocation> createFloorFireModels(Block $$0) {
/* 2060 */     ResourceLocation $$1 = ModelTemplates.FIRE_FLOOR.create(ModelLocationUtils.getModelLocation($$0, "_floor0"), TextureMapping.fire0($$0), this.modelOutput);
/* 2061 */     ResourceLocation $$2 = ModelTemplates.FIRE_FLOOR.create(ModelLocationUtils.getModelLocation($$0, "_floor1"), TextureMapping.fire1($$0), this.modelOutput);
/*      */     
/* 2063 */     return (List<ResourceLocation>)ImmutableList.of($$1, $$2);
/*      */   }
/*      */   
/*      */   private List<ResourceLocation> createSideFireModels(Block $$0) {
/* 2067 */     ResourceLocation $$1 = ModelTemplates.FIRE_SIDE.create(ModelLocationUtils.getModelLocation($$0, "_side0"), TextureMapping.fire0($$0), this.modelOutput);
/* 2068 */     ResourceLocation $$2 = ModelTemplates.FIRE_SIDE.create(ModelLocationUtils.getModelLocation($$0, "_side1"), TextureMapping.fire1($$0), this.modelOutput);
/*      */     
/* 2070 */     ResourceLocation $$3 = ModelTemplates.FIRE_SIDE_ALT.create(ModelLocationUtils.getModelLocation($$0, "_side_alt0"), TextureMapping.fire0($$0), this.modelOutput);
/* 2071 */     ResourceLocation $$4 = ModelTemplates.FIRE_SIDE_ALT.create(ModelLocationUtils.getModelLocation($$0, "_side_alt1"), TextureMapping.fire1($$0), this.modelOutput);
/*      */     
/* 2073 */     return (List<ResourceLocation>)ImmutableList.of($$1, $$2, $$3, $$4);
/*      */   }
/*      */   
/*      */   private List<ResourceLocation> createTopFireModels(Block $$0) {
/* 2077 */     ResourceLocation $$1 = ModelTemplates.FIRE_UP.create(ModelLocationUtils.getModelLocation($$0, "_up0"), TextureMapping.fire0($$0), this.modelOutput);
/* 2078 */     ResourceLocation $$2 = ModelTemplates.FIRE_UP.create(ModelLocationUtils.getModelLocation($$0, "_up1"), TextureMapping.fire1($$0), this.modelOutput);
/*      */     
/* 2080 */     ResourceLocation $$3 = ModelTemplates.FIRE_UP_ALT.create(ModelLocationUtils.getModelLocation($$0, "_up_alt0"), TextureMapping.fire0($$0), this.modelOutput);
/* 2081 */     ResourceLocation $$4 = ModelTemplates.FIRE_UP_ALT.create(ModelLocationUtils.getModelLocation($$0, "_up_alt1"), TextureMapping.fire1($$0), this.modelOutput);
/*      */     
/* 2083 */     return (List<ResourceLocation>)ImmutableList.of($$1, $$2, $$3, $$4);
/*      */   }
/*      */   
/*      */   private static List<Variant> wrapModels(List<ResourceLocation> $$0, UnaryOperator<Variant> $$1) {
/* 2087 */     return (List<Variant>)$$0.stream().map($$0 -> Variant.variant().with(VariantProperties.MODEL, $$0)).map($$1).collect(Collectors.toList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createFire() {
/* 2096 */     Condition.TerminalCondition terminalCondition = Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false)).term((Property)BlockStateProperties.EAST, Boolean.valueOf(false)).term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false)).term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)).term((Property)BlockStateProperties.UP, Boolean.valueOf(false));
/* 2097 */     List<ResourceLocation> $$1 = createFloorFireModels(Blocks.FIRE);
/* 2098 */     List<ResourceLocation> $$2 = createSideFireModels(Blocks.FIRE);
/* 2099 */     List<ResourceLocation> $$3 = createTopFireModels(Blocks.FIRE);
/*      */     
/* 2101 */     this.blockStateOutput.accept(
/* 2102 */         MultiPartGenerator.multiPart(Blocks.FIRE)
/* 2103 */         .with((Condition)terminalCondition, 
/*      */           
/* 2105 */           wrapModels($$1, $$0 -> $$0))
/*      */         
/* 2107 */         .with(
/* 2108 */           Condition.or(new Condition[] { (Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true)), (Condition)terminalCondition
/* 2109 */             }), wrapModels($$2, $$0 -> $$0))
/*      */         
/* 2111 */         .with(
/* 2112 */           Condition.or(new Condition[] { (Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(true)), (Condition)terminalCondition
/* 2113 */             }), wrapModels($$2, $$0 -> $$0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)))
/*      */         
/* 2115 */         .with(
/* 2116 */           Condition.or(new Condition[] { (Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true)), (Condition)terminalCondition
/* 2117 */             }), wrapModels($$2, $$0 -> $$0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)))
/*      */         
/* 2119 */         .with(
/* 2120 */           Condition.or(new Condition[] { (Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), (Condition)terminalCondition
/* 2121 */             }), wrapModels($$2, $$0 -> $$0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)))
/*      */         
/* 2123 */         .with(
/* 2124 */           (Condition)Condition.condition().term((Property)BlockStateProperties.UP, Boolean.valueOf(true)), 
/* 2125 */           wrapModels($$3, $$0 -> $$0)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSoulFire() {
/* 2131 */     List<ResourceLocation> $$0 = createFloorFireModels(Blocks.SOUL_FIRE);
/* 2132 */     List<ResourceLocation> $$1 = createSideFireModels(Blocks.SOUL_FIRE);
/*      */     
/* 2134 */     this.blockStateOutput.accept(
/* 2135 */         MultiPartGenerator.multiPart(Blocks.SOUL_FIRE)
/* 2136 */         .with(wrapModels($$0, $$0 -> $$0))
/* 2137 */         .with(wrapModels($$1, $$0 -> $$0))
/* 2138 */         .with(wrapModels($$1, $$0 -> $$0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)))
/* 2139 */         .with(wrapModels($$1, $$0 -> $$0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)))
/* 2140 */         .with(wrapModels($$1, $$0 -> $$0.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createLantern(Block $$0) {
/* 2145 */     ResourceLocation $$1 = TexturedModel.LANTERN.create($$0, this.modelOutput);
/* 2146 */     ResourceLocation $$2 = TexturedModel.HANGING_LANTERN.create($$0, this.modelOutput);
/*      */     
/* 2148 */     createSimpleFlatItemModel($$0.asItem());
/* 2149 */     this.blockStateOutput.accept(
/* 2150 */         MultiVariantGenerator.multiVariant($$0)
/* 2151 */         .with(createBooleanModelDispatch(BlockStateProperties.HANGING, $$2, $$1)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createMuddyMangroveRoots() {
/* 2156 */     TextureMapping $$0 = TextureMapping.column(TextureMapping.getBlockTexture(Blocks.MUDDY_MANGROVE_ROOTS, "_side"), TextureMapping.getBlockTexture(Blocks.MUDDY_MANGROVE_ROOTS, "_top"));
/* 2157 */     ResourceLocation $$1 = ModelTemplates.CUBE_COLUMN.create(Blocks.MUDDY_MANGROVE_ROOTS, $$0, this.modelOutput);
/* 2158 */     this.blockStateOutput.accept(createAxisAlignedPillarBlock(Blocks.MUDDY_MANGROVE_ROOTS, $$1));
/*      */   }
/*      */   
/*      */   private void createMangrovePropagule() {
/* 2162 */     createSimpleFlatItemModel(Items.MANGROVE_PROPAGULE);
/*      */     
/* 2164 */     Block $$0 = Blocks.MANGROVE_PROPAGULE;
/* 2165 */     PropertyDispatch.C2<Boolean, Integer> $$1 = PropertyDispatch.properties((Property)MangrovePropaguleBlock.HANGING, (Property)MangrovePropaguleBlock.AGE);
/*      */ 
/*      */ 
/*      */     
/* 2169 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation($$0);
/* 2170 */     for (int $$3 = 0; $$3 <= 4; $$3++) {
/* 2171 */       ResourceLocation $$4 = ModelLocationUtils.getModelLocation($$0, "_hanging_" + $$3);
/* 2172 */       $$1.select(Boolean.valueOf(true), Integer.valueOf($$3), Variant.variant().with(VariantProperties.MODEL, $$4));
/* 2173 */       $$1.select(Boolean.valueOf(false), Integer.valueOf($$3), Variant.variant().with(VariantProperties.MODEL, $$2));
/*      */     } 
/* 2175 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(Blocks.MANGROVE_PROPAGULE).with((PropertyDispatch)$$1));
/*      */   }
/*      */   
/*      */   private void createFrostedIce() {
/* 2179 */     this.blockStateOutput.accept(
/* 2180 */         MultiVariantGenerator.multiVariant(Blocks.FROSTED_ICE)
/* 2181 */         .with(
/* 2182 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.AGE_3)
/* 2183 */           .select(Integer.valueOf(0), Variant.variant().with(VariantProperties.MODEL, createSuffixedVariant(Blocks.FROSTED_ICE, "_0", ModelTemplates.CUBE_ALL, TextureMapping::cube)))
/* 2184 */           .select(Integer.valueOf(1), Variant.variant().with(VariantProperties.MODEL, createSuffixedVariant(Blocks.FROSTED_ICE, "_1", ModelTemplates.CUBE_ALL, TextureMapping::cube)))
/* 2185 */           .select(Integer.valueOf(2), Variant.variant().with(VariantProperties.MODEL, createSuffixedVariant(Blocks.FROSTED_ICE, "_2", ModelTemplates.CUBE_ALL, TextureMapping::cube)))
/* 2186 */           .select(Integer.valueOf(3), Variant.variant().with(VariantProperties.MODEL, createSuffixedVariant(Blocks.FROSTED_ICE, "_3", ModelTemplates.CUBE_ALL, TextureMapping::cube)))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createGrassBlocks() {
/* 2192 */     ResourceLocation $$0 = TextureMapping.getBlockTexture(Blocks.DIRT);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2197 */     TextureMapping $$1 = (new TextureMapping()).put(TextureSlot.BOTTOM, $$0).copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.GRASS_BLOCK, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.GRASS_BLOCK, "_snow"));
/*      */     
/* 2199 */     Variant $$2 = Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.GRASS_BLOCK, "_snow", $$1, this.modelOutput));
/*      */     
/* 2201 */     createGrassLikeBlock(Blocks.GRASS_BLOCK, ModelLocationUtils.getModelLocation(Blocks.GRASS_BLOCK), $$2);
/*      */     
/* 2203 */     ResourceLocation $$3 = TexturedModel.CUBE_TOP_BOTTOM.get(Blocks.MYCELIUM).updateTextures($$1 -> $$1.put(TextureSlot.BOTTOM, $$0)).create(Blocks.MYCELIUM, this.modelOutput);
/* 2204 */     createGrassLikeBlock(Blocks.MYCELIUM, $$3, $$2);
/*      */     
/* 2206 */     ResourceLocation $$4 = TexturedModel.CUBE_TOP_BOTTOM.get(Blocks.PODZOL).updateTextures($$1 -> $$1.put(TextureSlot.BOTTOM, $$0)).create(Blocks.PODZOL, this.modelOutput);
/* 2207 */     createGrassLikeBlock(Blocks.PODZOL, $$4, $$2);
/*      */   }
/*      */   
/*      */   private void createGrassLikeBlock(Block $$0, ResourceLocation $$1, Variant $$2) {
/* 2211 */     List<Variant> $$3 = Arrays.asList(createRotatedVariants($$1));
/* 2212 */     this.blockStateOutput.accept(
/* 2213 */         MultiVariantGenerator.multiVariant($$0)
/* 2214 */         .with(
/* 2215 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.SNOWY)
/* 2216 */           .select(Boolean.valueOf(true), $$2)
/* 2217 */           .select(Boolean.valueOf(false), $$3)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createCocoa() {
/* 2223 */     createSimpleFlatItemModel(Items.COCOA_BEANS);
/* 2224 */     this.blockStateOutput.accept(
/* 2225 */         MultiVariantGenerator.multiVariant(Blocks.COCOA)
/* 2226 */         .with(
/* 2227 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.AGE_2)
/* 2228 */           .select(Integer.valueOf(0), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COCOA, "_stage0")))
/* 2229 */           .select(Integer.valueOf(1), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COCOA, "_stage1")))
/* 2230 */           .select(Integer.valueOf(2), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.COCOA, "_stage2"))))
/*      */         
/* 2232 */         .with(createHorizontalFacingDispatchAlt()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createDirtPath() {
/* 2237 */     this.blockStateOutput.accept(createRotatedVariant(Blocks.DIRT_PATH, ModelLocationUtils.getModelLocation(Blocks.DIRT_PATH)));
/*      */   }
/*      */   
/*      */   private void createWeightedPressurePlate(Block $$0, Block $$1) {
/* 2241 */     TextureMapping $$2 = TextureMapping.defaultTexture($$1);
/* 2242 */     ResourceLocation $$3 = ModelTemplates.PRESSURE_PLATE_UP.create($$0, $$2, this.modelOutput);
/* 2243 */     ResourceLocation $$4 = ModelTemplates.PRESSURE_PLATE_DOWN.create($$0, $$2, this.modelOutput);
/*      */     
/* 2245 */     this.blockStateOutput.accept(
/* 2246 */         MultiVariantGenerator.multiVariant($$0)
/* 2247 */         .with(createEmptyOrFullDispatch((Property<Integer>)BlockStateProperties.POWER, Integer.valueOf(1), $$4, $$3)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createHopper() {
/* 2252 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.HOPPER);
/* 2253 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.HOPPER, "_side");
/*      */     
/* 2255 */     createSimpleFlatItemModel(Items.HOPPER);
/* 2256 */     this.blockStateOutput.accept(
/* 2257 */         MultiVariantGenerator.multiVariant(Blocks.HOPPER)
/* 2258 */         .with(
/* 2259 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.FACING_HOPPER)
/* 2260 */           .select((Comparable)Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, $$0))
/* 2261 */           .select((Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, $$1))
/* 2262 */           .select((Comparable)Direction.EAST, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2263 */           .select((Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2264 */           .select((Comparable)Direction.WEST, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void copyModel(Block $$0, Block $$1) {
/* 2271 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation($$0);
/* 2272 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant($$1, Variant.variant().with(VariantProperties.MODEL, $$2)));
/* 2273 */     delegateItemModel($$1, $$2);
/*      */   }
/*      */   
/*      */   private void createIronBars() {
/* 2277 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_post_ends");
/* 2278 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_post");
/* 2279 */     ResourceLocation $$2 = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_cap");
/* 2280 */     ResourceLocation $$3 = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_cap_alt");
/* 2281 */     ResourceLocation $$4 = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_side");
/* 2282 */     ResourceLocation $$5 = ModelLocationUtils.getModelLocation(Blocks.IRON_BARS, "_side_alt");
/*      */     
/* 2284 */     this.blockStateOutput.accept(
/* 2285 */         MultiPartGenerator.multiPart(Blocks.IRON_BARS)
/* 2286 */         .with(Variant.variant().with(VariantProperties.MODEL, $$0))
/* 2287 */         .with(
/* 2288 */           (Condition)Condition.condition()
/* 2289 */           .term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false))
/* 2290 */           .term((Property)BlockStateProperties.EAST, Boolean.valueOf(false))
/* 2291 */           .term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false))
/* 2292 */           .term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)), 
/* 2293 */           Variant.variant().with(VariantProperties.MODEL, $$1))
/*      */         
/* 2295 */         .with(
/* 2296 */           (Condition)Condition.condition()
/* 2297 */           .term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true))
/* 2298 */           .term((Property)BlockStateProperties.EAST, Boolean.valueOf(false))
/* 2299 */           .term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false))
/* 2300 */           .term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)), 
/* 2301 */           Variant.variant().with(VariantProperties.MODEL, $$2))
/*      */         
/* 2303 */         .with(
/* 2304 */           (Condition)Condition.condition()
/* 2305 */           .term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false))
/* 2306 */           .term((Property)BlockStateProperties.EAST, Boolean.valueOf(true))
/* 2307 */           .term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false))
/* 2308 */           .term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)), 
/* 2309 */           Variant.variant().with(VariantProperties.MODEL, $$2).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 2311 */         .with(
/* 2312 */           (Condition)Condition.condition()
/* 2313 */           .term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false))
/* 2314 */           .term((Property)BlockStateProperties.EAST, Boolean.valueOf(false))
/* 2315 */           .term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true))
/* 2316 */           .term((Property)BlockStateProperties.WEST, Boolean.valueOf(false)), 
/* 2317 */           Variant.variant().with(VariantProperties.MODEL, $$3))
/*      */         
/* 2319 */         .with(
/* 2320 */           (Condition)Condition.condition()
/* 2321 */           .term((Property)BlockStateProperties.NORTH, Boolean.valueOf(false))
/* 2322 */           .term((Property)BlockStateProperties.EAST, Boolean.valueOf(false))
/* 2323 */           .term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(false))
/* 2324 */           .term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), 
/* 2325 */           Variant.variant().with(VariantProperties.MODEL, $$3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */         
/* 2327 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.NORTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$4))
/* 2328 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.EAST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2329 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.SOUTH, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$5))
/* 2330 */         .with((Condition)Condition.condition().term((Property)BlockStateProperties.WEST, Boolean.valueOf(true)), Variant.variant().with(VariantProperties.MODEL, $$5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
/*      */     
/* 2332 */     createSimpleFlatItemModel(Blocks.IRON_BARS);
/*      */   }
/*      */   
/*      */   private void createNonTemplateHorizontalBlock(Block $$0) {
/* 2336 */     this.blockStateOutput.accept(
/* 2337 */         MultiVariantGenerator.multiVariant($$0, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation($$0)))
/* 2338 */         .with(createHorizontalFacingDispatch()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createLever() {
/* 2343 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.LEVER);
/* 2344 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.LEVER, "_on");
/*      */     
/* 2346 */     createSimpleFlatItemModel(Blocks.LEVER);
/* 2347 */     this.blockStateOutput.accept(
/* 2348 */         MultiVariantGenerator.multiVariant(Blocks.LEVER)
/* 2349 */         .with(
/* 2350 */           createBooleanModelDispatch(BlockStateProperties.POWERED, $$0, $$1))
/*      */         
/* 2352 */         .with(
/* 2353 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.ATTACH_FACE, (Property)BlockStateProperties.HORIZONTAL_FACING)
/* 2354 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2355 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/* 2356 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
/* 2357 */           .select((Comparable)AttachFace.CEILING, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */           
/* 2359 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.NORTH, Variant.variant())
/* 2360 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2361 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2362 */           .select((Comparable)AttachFace.FLOOR, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */           
/* 2364 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
/* 2365 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2366 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2367 */           .select((Comparable)AttachFace.WALL, (Comparable)Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createLilyPad() {
/* 2373 */     createSimpleFlatItemModel(Blocks.LILY_PAD);
/* 2374 */     this.blockStateOutput.accept(createRotatedVariant(Blocks.LILY_PAD, ModelLocationUtils.getModelLocation(Blocks.LILY_PAD)));
/*      */   }
/*      */   
/*      */   private void createFrogspawnBlock() {
/* 2378 */     createSimpleFlatItemModel(Blocks.FROGSPAWN);
/* 2379 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.FROGSPAWN, ModelLocationUtils.getModelLocation(Blocks.FROGSPAWN)));
/*      */   }
/*      */   
/*      */   private void createNetherPortalBlock() {
/* 2383 */     this.blockStateOutput.accept(
/* 2384 */         MultiVariantGenerator.multiVariant(Blocks.NETHER_PORTAL)
/* 2385 */         .with(
/* 2386 */           (PropertyDispatch)PropertyDispatch.property((Property)BlockStateProperties.HORIZONTAL_AXIS)
/* 2387 */           .select((Comparable)Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.NETHER_PORTAL, "_ns")))
/* 2388 */           .select((Comparable)Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.NETHER_PORTAL, "_ew")))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNetherrack() {
/* 2394 */     ResourceLocation $$0 = TexturedModel.CUBE.create(Blocks.NETHERRACK, this.modelOutput);
/* 2395 */     this.blockStateOutput.accept(
/* 2396 */         MultiVariantGenerator.multiVariant(Blocks.NETHERRACK, new Variant[] { 
/* 2397 */             Variant.variant().with(VariantProperties.MODEL, $$0), 
/* 2398 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), 
/* 2399 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), 
/* 2400 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270), 
/*      */             
/* 2402 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), 
/* 2403 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), 
/* 2404 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), 
/* 2405 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270), 
/*      */             
/* 2407 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), 
/* 2408 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), 
/* 2409 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), 
/* 2410 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270), 
/*      */             
/* 2412 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270), 
/* 2413 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90), 
/* 2414 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180), 
/* 2415 */             Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createObserver() {
/* 2422 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.OBSERVER);
/* 2423 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.OBSERVER, "_on");
/*      */     
/* 2425 */     this.blockStateOutput.accept(
/* 2426 */         MultiVariantGenerator.multiVariant(Blocks.OBSERVER)
/* 2427 */         .with(createBooleanModelDispatch(BlockStateProperties.POWERED, $$1, $$0))
/* 2428 */         .with(createFacingDispatch()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createPistons() {
/* 2435 */     TextureMapping $$0 = (new TextureMapping()).put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.PISTON, "_bottom")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.PISTON, "_side"));
/*      */     
/* 2437 */     ResourceLocation $$1 = TextureMapping.getBlockTexture(Blocks.PISTON, "_top_sticky");
/* 2438 */     ResourceLocation $$2 = TextureMapping.getBlockTexture(Blocks.PISTON, "_top");
/*      */     
/* 2440 */     TextureMapping $$3 = $$0.copyAndUpdate(TextureSlot.PLATFORM, $$1);
/* 2441 */     TextureMapping $$4 = $$0.copyAndUpdate(TextureSlot.PLATFORM, $$2);
/*      */     
/* 2443 */     ResourceLocation $$5 = ModelLocationUtils.getModelLocation(Blocks.PISTON, "_base");
/*      */     
/* 2445 */     createPistonVariant(Blocks.PISTON, $$5, $$4);
/* 2446 */     createPistonVariant(Blocks.STICKY_PISTON, $$5, $$3);
/*      */     
/* 2448 */     ResourceLocation $$6 = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.PISTON, "_inventory", $$0.copyAndUpdate(TextureSlot.TOP, $$2), this.modelOutput);
/* 2449 */     ResourceLocation $$7 = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.STICKY_PISTON, "_inventory", $$0.copyAndUpdate(TextureSlot.TOP, $$1), this.modelOutput);
/*      */     
/* 2451 */     delegateItemModel(Blocks.PISTON, $$6);
/* 2452 */     delegateItemModel(Blocks.STICKY_PISTON, $$7);
/*      */   }
/*      */   
/*      */   private void createPistonVariant(Block $$0, ResourceLocation $$1, TextureMapping $$2) {
/* 2456 */     ResourceLocation $$3 = ModelTemplates.PISTON.create($$0, $$2, this.modelOutput);
/* 2457 */     this.blockStateOutput.accept(
/* 2458 */         MultiVariantGenerator.multiVariant($$0)
/* 2459 */         .with(createBooleanModelDispatch(BlockStateProperties.EXTENDED, $$1, $$3))
/* 2460 */         .with(createFacingDispatch()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createPistonHeads() {
/* 2467 */     TextureMapping $$0 = (new TextureMapping()).put(TextureSlot.UNSTICKY, TextureMapping.getBlockTexture(Blocks.PISTON, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.PISTON, "_side"));
/*      */     
/* 2469 */     TextureMapping $$1 = $$0.copyAndUpdate(TextureSlot.PLATFORM, TextureMapping.getBlockTexture(Blocks.PISTON, "_top_sticky"));
/* 2470 */     TextureMapping $$2 = $$0.copyAndUpdate(TextureSlot.PLATFORM, TextureMapping.getBlockTexture(Blocks.PISTON, "_top"));
/*      */     
/* 2472 */     this.blockStateOutput.accept(
/* 2473 */         MultiVariantGenerator.multiVariant(Blocks.PISTON_HEAD)
/* 2474 */         .with(
/* 2475 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.SHORT, (Property)BlockStateProperties.PISTON_TYPE)
/* 2476 */           .select(Boolean.valueOf(false), (Comparable)PistonType.DEFAULT, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.PISTON_HEAD.createWithSuffix(Blocks.PISTON, "_head", $$2, this.modelOutput)))
/* 2477 */           .select(Boolean.valueOf(false), (Comparable)PistonType.STICKY, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.PISTON_HEAD.createWithSuffix(Blocks.PISTON, "_head_sticky", $$1, this.modelOutput)))
/* 2478 */           .select(Boolean.valueOf(true), (Comparable)PistonType.DEFAULT, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.PISTON_HEAD_SHORT.createWithSuffix(Blocks.PISTON, "_head_short", $$2, this.modelOutput)))
/* 2479 */           .select(Boolean.valueOf(true), (Comparable)PistonType.STICKY, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.PISTON_HEAD_SHORT.createWithSuffix(Blocks.PISTON, "_head_short_sticky", $$1, this.modelOutput))))
/*      */         
/* 2481 */         .with(
/* 2482 */           createFacingDispatch()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createTrialSpawner()
/*      */   {
/* 2488 */     Block $$0 = Blocks.TRIAL_SPAWNER;
/* 2489 */     TextureMapping $$1 = TextureMapping.trialSpawner($$0, "_side_inactive", "_top_inactive");
/* 2490 */     TextureMapping $$2 = TextureMapping.trialSpawner($$0, "_side_active", "_top_active");
/* 2491 */     TextureMapping $$3 = TextureMapping.trialSpawner($$0, "_side_active", "_top_ejecting_reward");
/*      */     
/* 2493 */     ResourceLocation $$4 = ModelTemplates.CUBE_BOTTOM_TOP_INNER_FACES.create($$0, $$1, this.modelOutput);
/* 2494 */     ResourceLocation $$5 = ModelTemplates.CUBE_BOTTOM_TOP_INNER_FACES.createWithSuffix($$0, "_active", $$2, this.modelOutput);
/* 2495 */     ResourceLocation $$6 = ModelTemplates.CUBE_BOTTOM_TOP_INNER_FACES.createWithSuffix($$0, "_ejecting_reward", $$3, this.modelOutput);
/* 2496 */     delegateItemModel($$0, $$4);
/* 2497 */     this.blockStateOutput.accept(
/* 2498 */         MultiVariantGenerator.multiVariant($$0)
/* 2499 */         .with(
/* 2500 */           PropertyDispatch.property((Property)BlockStateProperties.TRIAL_SPAWNER_STATE)
/* 2501 */           .generate($$3 -> { switch ($$3) { default: throw new IncompatibleClassChangeError();
/*      */                 case UPPER: case LOWER:
/*      */                 
/*      */                 case null:
/*      */                 case null:
/*      */                 case null:
/*      */                 
/*      */                 case null:
/* 2509 */                   break; }  return Variant.variant().with(VariantProperties.MODEL, $$2); }))); } private void createSculkSensor() { ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.SCULK_SENSOR, "_inactive");
/* 2510 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.SCULK_SENSOR, "_active");
/* 2511 */     delegateItemModel(Blocks.SCULK_SENSOR, $$0);
/* 2512 */     this.blockStateOutput.accept(
/* 2513 */         MultiVariantGenerator.multiVariant(Blocks.SCULK_SENSOR)
/* 2514 */         .with(
/* 2515 */           PropertyDispatch.property((Property)BlockStateProperties.SCULK_SENSOR_PHASE)
/* 2516 */           .generate($$2 -> Variant.variant().with(VariantProperties.MODEL, ($$2 == SculkSensorPhase.ACTIVE || $$2 == SculkSensorPhase.COOLDOWN) ? $$0 : $$1)))); }
/*      */ 
/*      */   
/*      */   private void createCalibratedSculkSensor() {
/* 2520 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.CALIBRATED_SCULK_SENSOR, "_inactive");
/* 2521 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.CALIBRATED_SCULK_SENSOR, "_active");
/* 2522 */     delegateItemModel(Blocks.CALIBRATED_SCULK_SENSOR, $$0);
/* 2523 */     this.blockStateOutput.accept(
/* 2524 */         MultiVariantGenerator.multiVariant(Blocks.CALIBRATED_SCULK_SENSOR)
/* 2525 */         .with(
/* 2526 */           PropertyDispatch.property((Property)BlockStateProperties.SCULK_SENSOR_PHASE)
/* 2527 */           .generate($$2 -> Variant.variant().with(VariantProperties.MODEL, ($$2 == SculkSensorPhase.ACTIVE || $$2 == SculkSensorPhase.COOLDOWN) ? $$0 : $$1)))
/* 2528 */         .with(createHorizontalFacingDispatch()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createSculkShrieker() {
/* 2533 */     ResourceLocation $$0 = ModelTemplates.SCULK_SHRIEKER.create(Blocks.SCULK_SHRIEKER, TextureMapping.sculkShrieker(false), this.modelOutput);
/* 2534 */     ResourceLocation $$1 = ModelTemplates.SCULK_SHRIEKER.createWithSuffix(Blocks.SCULK_SHRIEKER, "_can_summon", TextureMapping.sculkShrieker(true), this.modelOutput);
/*      */     
/* 2536 */     delegateItemModel(Blocks.SCULK_SHRIEKER, $$0);
/* 2537 */     this.blockStateOutput.accept(
/* 2538 */         MultiVariantGenerator.multiVariant(Blocks.SCULK_SHRIEKER)
/* 2539 */         .with(createBooleanModelDispatch(BlockStateProperties.CAN_SUMMON, $$1, $$0)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createScaffolding() {
/* 2544 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.SCAFFOLDING, "_stable");
/* 2545 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.SCAFFOLDING, "_unstable");
/* 2546 */     delegateItemModel(Blocks.SCAFFOLDING, $$0);
/* 2547 */     this.blockStateOutput.accept(
/* 2548 */         MultiVariantGenerator.multiVariant(Blocks.SCAFFOLDING)
/* 2549 */         .with(createBooleanModelDispatch(BlockStateProperties.BOTTOM, $$1, $$0)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createCaveVines() {
/* 2554 */     ResourceLocation $$0 = createSuffixedVariant(Blocks.CAVE_VINES, "", ModelTemplates.CROSS, TextureMapping::cross);
/* 2555 */     ResourceLocation $$1 = createSuffixedVariant(Blocks.CAVE_VINES, "_lit", ModelTemplates.CROSS, TextureMapping::cross);
/*      */     
/* 2557 */     this.blockStateOutput.accept(
/* 2558 */         MultiVariantGenerator.multiVariant(Blocks.CAVE_VINES)
/* 2559 */         .with(createBooleanModelDispatch(BlockStateProperties.BERRIES, $$1, $$0)));
/*      */ 
/*      */     
/* 2562 */     ResourceLocation $$2 = createSuffixedVariant(Blocks.CAVE_VINES_PLANT, "", ModelTemplates.CROSS, TextureMapping::cross);
/* 2563 */     ResourceLocation $$3 = createSuffixedVariant(Blocks.CAVE_VINES_PLANT, "_lit", ModelTemplates.CROSS, TextureMapping::cross);
/*      */     
/* 2565 */     this.blockStateOutput.accept(
/* 2566 */         MultiVariantGenerator.multiVariant(Blocks.CAVE_VINES_PLANT)
/* 2567 */         .with(createBooleanModelDispatch(BlockStateProperties.BERRIES, $$3, $$2)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createRedstoneLamp() {
/* 2572 */     ResourceLocation $$0 = TexturedModel.CUBE.create(Blocks.REDSTONE_LAMP, this.modelOutput);
/* 2573 */     ResourceLocation $$1 = createSuffixedVariant(Blocks.REDSTONE_LAMP, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube);
/*      */     
/* 2575 */     this.blockStateOutput.accept(
/* 2576 */         MultiVariantGenerator.multiVariant(Blocks.REDSTONE_LAMP)
/* 2577 */         .with(createBooleanModelDispatch(BlockStateProperties.LIT, $$1, $$0)));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createNormalTorch(Block $$0, Block $$1) {
/* 2582 */     TextureMapping $$2 = TextureMapping.torch($$0);
/*      */     
/* 2584 */     this.blockStateOutput.accept(createSimpleBlock($$0, ModelTemplates.TORCH.create($$0, $$2, this.modelOutput)));
/*      */     
/* 2586 */     this.blockStateOutput.accept(
/* 2587 */         MultiVariantGenerator.multiVariant($$1, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.WALL_TORCH.create($$1, $$2, this.modelOutput)))
/* 2588 */         .with(createTorchHorizontalDispatch()));
/*      */     
/* 2590 */     createSimpleFlatItemModel($$0);
/* 2591 */     skipAutoItemBlock($$1);
/*      */   }
/*      */   
/*      */   private void createRedstoneTorch() {
/* 2595 */     TextureMapping $$0 = TextureMapping.torch(Blocks.REDSTONE_TORCH);
/* 2596 */     TextureMapping $$1 = TextureMapping.torch(TextureMapping.getBlockTexture(Blocks.REDSTONE_TORCH, "_off"));
/*      */     
/* 2598 */     ResourceLocation $$2 = ModelTemplates.TORCH.create(Blocks.REDSTONE_TORCH, $$0, this.modelOutput);
/* 2599 */     ResourceLocation $$3 = ModelTemplates.TORCH.createWithSuffix(Blocks.REDSTONE_TORCH, "_off", $$1, this.modelOutput);
/*      */     
/* 2601 */     this.blockStateOutput.accept(
/* 2602 */         MultiVariantGenerator.multiVariant(Blocks.REDSTONE_TORCH)
/* 2603 */         .with(createBooleanModelDispatch(BlockStateProperties.LIT, $$2, $$3)));
/*      */ 
/*      */     
/* 2606 */     ResourceLocation $$4 = ModelTemplates.WALL_TORCH.create(Blocks.REDSTONE_WALL_TORCH, $$0, this.modelOutput);
/* 2607 */     ResourceLocation $$5 = ModelTemplates.WALL_TORCH.createWithSuffix(Blocks.REDSTONE_WALL_TORCH, "_off", $$1, this.modelOutput);
/*      */     
/* 2609 */     this.blockStateOutput.accept(
/* 2610 */         MultiVariantGenerator.multiVariant(Blocks.REDSTONE_WALL_TORCH)
/* 2611 */         .with(createBooleanModelDispatch(BlockStateProperties.LIT, $$4, $$5))
/* 2612 */         .with(createTorchHorizontalDispatch()));
/*      */     
/* 2614 */     createSimpleFlatItemModel(Blocks.REDSTONE_TORCH);
/* 2615 */     skipAutoItemBlock(Blocks.REDSTONE_WALL_TORCH);
/*      */   }
/*      */   
/*      */   private void createRepeater() {
/* 2619 */     createSimpleFlatItemModel(Items.REPEATER);
/* 2620 */     this.blockStateOutput.accept(
/* 2621 */         MultiVariantGenerator.multiVariant(Blocks.REPEATER)
/* 2622 */         .with(
/* 2623 */           PropertyDispatch.properties((Property)BlockStateProperties.DELAY, (Property)BlockStateProperties.LOCKED, (Property)BlockStateProperties.POWERED)
/* 2624 */           .generate(($$0, $$1, $$2) -> {
/*      */               StringBuilder $$3 = new StringBuilder();
/*      */               
/*      */               $$3.append('_').append($$0).append("tick");
/*      */               
/*      */               if ($$2.booleanValue()) {
/*      */                 $$3.append("_on");
/*      */               }
/*      */               if ($$1.booleanValue()) {
/*      */                 $$3.append("_locked");
/*      */               }
/*      */               return Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.REPEATER, $$3.toString()));
/* 2636 */             })).with(createHorizontalFacingDispatchAlt()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createSeaPickle() {
/* 2641 */     createSimpleFlatItemModel(Items.SEA_PICKLE);
/*      */     
/* 2643 */     this.blockStateOutput.accept(
/* 2644 */         MultiVariantGenerator.multiVariant(Blocks.SEA_PICKLE)
/* 2645 */         .with(
/* 2646 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.PICKLES, (Property)BlockStateProperties.WATERLOGGED)
/* 2647 */           .select(Integer.valueOf(1), Boolean.valueOf(false), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("dead_sea_pickle"))))
/* 2648 */           .select(Integer.valueOf(2), Boolean.valueOf(false), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("two_dead_sea_pickles"))))
/* 2649 */           .select(Integer.valueOf(3), Boolean.valueOf(false), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("three_dead_sea_pickles"))))
/* 2650 */           .select(Integer.valueOf(4), Boolean.valueOf(false), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("four_dead_sea_pickles"))))
/*      */           
/* 2652 */           .select(Integer.valueOf(1), Boolean.valueOf(true), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("sea_pickle"))))
/* 2653 */           .select(Integer.valueOf(2), Boolean.valueOf(true), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("two_sea_pickles"))))
/* 2654 */           .select(Integer.valueOf(3), Boolean.valueOf(true), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("three_sea_pickles"))))
/* 2655 */           .select(Integer.valueOf(4), Boolean.valueOf(true), Arrays.asList(createRotatedVariants(ModelLocationUtils.decorateBlockModelLocation("four_sea_pickles"))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSnowBlocks() {
/* 2662 */     TextureMapping $$0 = TextureMapping.cube(Blocks.SNOW);
/* 2663 */     ResourceLocation $$1 = ModelTemplates.CUBE_ALL.create(Blocks.SNOW_BLOCK, $$0, this.modelOutput);
/*      */     
/* 2665 */     this.blockStateOutput.accept(
/* 2666 */         MultiVariantGenerator.multiVariant(Blocks.SNOW)
/* 2667 */         .with(
/* 2668 */           PropertyDispatch.property((Property)BlockStateProperties.LAYERS)
/* 2669 */           .generate($$1 -> Variant.variant().with(VariantProperties.MODEL, ($$1.intValue() < 8) ? ModelLocationUtils.getModelLocation(Blocks.SNOW, "_height" + $$1.intValue() * 2) : $$0))));
/*      */ 
/*      */ 
/*      */     
/* 2673 */     delegateItemModel(Blocks.SNOW, ModelLocationUtils.getModelLocation(Blocks.SNOW, "_height2"));
/* 2674 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.SNOW_BLOCK, $$1));
/*      */   }
/*      */   
/*      */   private void createStonecutter() {
/* 2678 */     this.blockStateOutput.accept(
/* 2679 */         MultiVariantGenerator.multiVariant(Blocks.STONECUTTER, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.STONECUTTER)))
/* 2680 */         .with(createHorizontalFacingDispatch()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createStructureBlock() {
/* 2685 */     ResourceLocation $$0 = TexturedModel.CUBE.create(Blocks.STRUCTURE_BLOCK, this.modelOutput);
/* 2686 */     delegateItemModel(Blocks.STRUCTURE_BLOCK, $$0);
/*      */     
/* 2688 */     this.blockStateOutput.accept(
/* 2689 */         MultiVariantGenerator.multiVariant(Blocks.STRUCTURE_BLOCK)
/* 2690 */         .with(
/* 2691 */           PropertyDispatch.property((Property)BlockStateProperties.STRUCTUREBLOCK_MODE)
/* 2692 */           .generate($$0 -> Variant.variant().with(VariantProperties.MODEL, createSuffixedVariant(Blocks.STRUCTURE_BLOCK, "_" + $$0.getSerializedName(), ModelTemplates.CUBE_ALL, TextureMapping::cube)))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSweetBerryBush() {
/* 2699 */     createSimpleFlatItemModel(Items.SWEET_BERRIES);
/* 2700 */     this.blockStateOutput.accept(
/* 2701 */         MultiVariantGenerator.multiVariant(Blocks.SWEET_BERRY_BUSH)
/* 2702 */         .with(
/* 2703 */           PropertyDispatch.property((Property)BlockStateProperties.AGE_3)
/* 2704 */           .generate($$0 -> Variant.variant().with(VariantProperties.MODEL, createSuffixedVariant(Blocks.SWEET_BERRY_BUSH, "_stage" + $$0, ModelTemplates.CROSS, TextureMapping::cross)))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createTripwire() {
/* 2712 */     createSimpleFlatItemModel(Items.STRING);
/* 2713 */     this.blockStateOutput.accept(
/* 2714 */         MultiVariantGenerator.multiVariant(Blocks.TRIPWIRE)
/* 2715 */         .with(
/* 2716 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.ATTACHED, (Property)BlockStateProperties.EAST, (Property)BlockStateProperties.NORTH, (Property)BlockStateProperties.SOUTH, (Property)BlockStateProperties.WEST)
/*      */           
/* 2718 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ns")))
/*      */ 
/*      */           
/* 2721 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2722 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n")))
/* 2723 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2724 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_n")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */ 
/*      */           
/* 2727 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne")))
/* 2728 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2729 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2730 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ne")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/* 2731 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ns")))
/* 2732 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_ns")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */ 
/*      */           
/* 2735 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse")))
/* 2736 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2737 */           .select(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2738 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nse")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */ 
/*      */           
/* 2741 */           .select(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_nsew")))
/*      */ 
/*      */           
/* 2744 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ns")))
/*      */ 
/*      */           
/* 2747 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n")))
/* 2748 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2749 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2750 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_n")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */ 
/*      */           
/* 2753 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne")))
/* 2754 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2755 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2756 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ne")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/* 2757 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ns")))
/* 2758 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_ns")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/*      */ 
/*      */           
/* 2761 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse")))
/* 2762 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
/* 2763 */           .select(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
/* 2764 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nse")).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
/*      */ 
/*      */           
/* 2767 */           .select(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(Blocks.TRIPWIRE, "_attached_nsew")))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createTripwireHook() {
/* 2773 */     createSimpleFlatItemModel(Blocks.TRIPWIRE_HOOK);
/* 2774 */     this.blockStateOutput.accept(
/* 2775 */         MultiVariantGenerator.multiVariant(Blocks.TRIPWIRE_HOOK)
/* 2776 */         .with(
/* 2777 */           PropertyDispatch.properties((Property)BlockStateProperties.ATTACHED, (Property)BlockStateProperties.POWERED)
/* 2778 */           .generate(($$0, $$1) -> Variant.variant().with(VariantProperties.MODEL, TextureMapping.getBlockTexture(Blocks.TRIPWIRE_HOOK, ($$0.booleanValue() ? "_attached" : "") + ($$0.booleanValue() ? "_attached" : "")))))
/*      */         
/* 2780 */         .with(createHorizontalFacingDispatch()));
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation createTurtleEggModel(int $$0, String $$1, TextureMapping $$2) {
/* 2785 */     switch ($$0) {
/*      */       case 1:
/* 2787 */         return ModelTemplates.TURTLE_EGG.create(ModelLocationUtils.decorateBlockModelLocation($$1 + "turtle_egg"), $$2, this.modelOutput);
/*      */       case 2:
/* 2789 */         return ModelTemplates.TWO_TURTLE_EGGS.create(ModelLocationUtils.decorateBlockModelLocation("two_" + $$1 + "turtle_eggs"), $$2, this.modelOutput);
/*      */       case 3:
/* 2791 */         return ModelTemplates.THREE_TURTLE_EGGS.create(ModelLocationUtils.decorateBlockModelLocation("three_" + $$1 + "turtle_eggs"), $$2, this.modelOutput);
/*      */       case 4:
/* 2793 */         return ModelTemplates.FOUR_TURTLE_EGGS.create(ModelLocationUtils.decorateBlockModelLocation("four_" + $$1 + "turtle_eggs"), $$2, this.modelOutput);
/*      */     } 
/* 2795 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation createTurtleEggModel(Integer $$0, Integer $$1) {
/* 2800 */     switch ($$1.intValue()) {
/*      */       case 0:
/* 2802 */         return createTurtleEggModel($$0.intValue(), "", TextureMapping.cube(TextureMapping.getBlockTexture(Blocks.TURTLE_EGG)));
/*      */       case 1:
/* 2804 */         return createTurtleEggModel($$0.intValue(), "slightly_cracked_", TextureMapping.cube(TextureMapping.getBlockTexture(Blocks.TURTLE_EGG, "_slightly_cracked")));
/*      */       case 2:
/* 2806 */         return createTurtleEggModel($$0.intValue(), "very_cracked_", TextureMapping.cube(TextureMapping.getBlockTexture(Blocks.TURTLE_EGG, "_very_cracked")));
/*      */     } 
/* 2808 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   private void createTurtleEgg() {
/* 2813 */     createSimpleFlatItemModel(Items.TURTLE_EGG);
/* 2814 */     this.blockStateOutput.accept(
/* 2815 */         MultiVariantGenerator.multiVariant(Blocks.TURTLE_EGG)
/* 2816 */         .with(
/* 2817 */           PropertyDispatch.properties((Property)BlockStateProperties.EGGS, (Property)BlockStateProperties.HATCH)
/* 2818 */           .generateList(($$0, $$1) -> Arrays.asList(createRotatedVariants(createTurtleEggModel($$0, $$1))))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSnifferEgg() {
/* 2824 */     createSimpleFlatItemModel(Items.SNIFFER_EGG);
/*      */     
/* 2826 */     Function<Integer, ResourceLocation> $$0 = $$0 -> {
/*      */         switch ($$0.intValue()) {
/*      */           case 1:
/*      */           
/*      */           
/*      */           case 2:
/*      */           
/*      */         } 
/*      */         String $$1 = "_not_cracked";
/*      */         TextureMapping $$2 = TextureMapping.snifferEgg($$1);
/*      */         return ModelTemplates.SNIFFER_EGG.createWithSuffix(Blocks.SNIFFER_EGG, $$1, $$2, this.modelOutput);
/*      */       };
/* 2838 */     this.blockStateOutput.accept(
/* 2839 */         MultiVariantGenerator.multiVariant(Blocks.SNIFFER_EGG)
/* 2840 */         .with(PropertyDispatch.property((Property)SnifferEggBlock.HATCH)
/* 2841 */           .generate($$1 -> Variant.variant().with(VariantProperties.MODEL, $$0.apply($$1)))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 2848 */     MULTIFACE_GENERATOR = List.of(
/* 2849 */         Pair.of(BlockStateProperties.NORTH, $$0 -> Variant.variant().with(VariantProperties.MODEL, $$0)), 
/* 2850 */         Pair.of(BlockStateProperties.EAST, $$0 -> Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))), 
/* 2851 */         Pair.of(BlockStateProperties.SOUTH, $$0 -> Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))), 
/* 2852 */         Pair.of(BlockStateProperties.WEST, $$0 -> Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))), 
/* 2853 */         Pair.of(BlockStateProperties.UP, $$0 -> Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))), 
/* 2854 */         Pair.of(BlockStateProperties.DOWN, $$0 -> Variant.variant().with(VariantProperties.MODEL, $$0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, Boolean.valueOf(true))));
/*      */   }
/*      */   
/*      */   private void createMultiface(Block $$0) {
/* 2858 */     createSimpleFlatItemModel($$0);
/*      */     
/* 2860 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation($$0);
/* 2861 */     MultiPartGenerator $$2 = MultiPartGenerator.multiPart($$0);
/*      */     
/* 2863 */     Condition.TerminalCondition $$3 = (Condition.TerminalCondition)Util.make(Condition.condition(), $$1 -> MULTIFACE_GENERATOR.stream().map(Pair::getFirst).forEach(()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2871 */     for (Pair<BooleanProperty, Function<ResourceLocation, Variant>> $$4 : MULTIFACE_GENERATOR) {
/* 2872 */       BooleanProperty $$5 = (BooleanProperty)$$4.getFirst();
/* 2873 */       Function<ResourceLocation, Variant> $$6 = (Function<ResourceLocation, Variant>)$$4.getSecond();
/* 2874 */       if ($$0.defaultBlockState().hasProperty((Property)$$5)) {
/* 2875 */         $$2.with((Condition)Condition.condition().term((Property)$$5, Boolean.valueOf(true)), $$6.apply($$1));
/* 2876 */         $$2.with((Condition)$$3, $$6.apply($$1));
/*      */       } 
/*      */     } 
/*      */     
/* 2880 */     this.blockStateOutput.accept($$2);
/*      */   }
/*      */   
/*      */   private void createSculkCatalyst() {
/* 2884 */     ResourceLocation $$0 = TextureMapping.getBlockTexture(Blocks.SCULK_CATALYST, "_bottom");
/*      */ 
/*      */ 
/*      */     
/* 2888 */     TextureMapping $$1 = (new TextureMapping()).put(TextureSlot.BOTTOM, $$0).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.SCULK_CATALYST, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.SCULK_CATALYST, "_side"));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2893 */     TextureMapping $$2 = (new TextureMapping()).put(TextureSlot.BOTTOM, $$0).put(TextureSlot.TOP, TextureMapping.getBlockTexture(Blocks.SCULK_CATALYST, "_top_bloom")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.SCULK_CATALYST, "_side_bloom"));
/*      */     
/* 2895 */     ResourceLocation $$3 = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.SCULK_CATALYST, "", $$1, this.modelOutput);
/* 2896 */     ResourceLocation $$4 = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.SCULK_CATALYST, "_bloom", $$2, this.modelOutput);
/*      */     
/* 2898 */     this.blockStateOutput.accept(
/* 2899 */         MultiVariantGenerator.multiVariant(Blocks.SCULK_CATALYST)
/* 2900 */         .with(
/* 2901 */           PropertyDispatch.property((Property)BlockStateProperties.BLOOM)
/* 2902 */           .generate($$2 -> Variant.variant().with(VariantProperties.MODEL, $$2.booleanValue() ? $$0 : $$1))));
/*      */ 
/*      */     
/* 2905 */     delegateItemModel(Items.SCULK_CATALYST, $$3);
/*      */   }
/*      */   
/*      */   private void createChiseledBookshelf() {
/* 2909 */     Block $$0 = Blocks.CHISELED_BOOKSHELF;
/* 2910 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation($$0);
/* 2911 */     MultiPartGenerator $$2 = MultiPartGenerator.multiPart($$0);
/*      */     
/* 2913 */     Map.<Direction, VariantProperties.Rotation>of(Direction.NORTH, VariantProperties.Rotation.R0, Direction.EAST, VariantProperties.Rotation.R90, Direction.SOUTH, VariantProperties.Rotation.R180, Direction.WEST, VariantProperties.Rotation.R270)
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2918 */       .forEach(($$2, $$3) -> {
/*      */           Condition.TerminalCondition $$4 = Condition.condition().term((Property)BlockStateProperties.HORIZONTAL_FACING, (Comparable)$$2);
/*      */           
/*      */           $$0.with((Condition)$$4, Variant.variant().with(VariantProperties.MODEL, $$1).with(VariantProperties.Y_ROT, $$3).with(VariantProperties.UV_LOCK, Boolean.valueOf(true)));
/*      */           addSlotStateAndRotationVariants($$0, $$4, $$3);
/*      */         });
/* 2924 */     this.blockStateOutput.accept($$2);
/* 2925 */     delegateItemModel($$0, ModelLocationUtils.getModelLocation($$0, "_inventory"));
/* 2926 */     CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.clear();
/*      */   }
/*      */   
/*      */   private void addSlotStateAndRotationVariants(MultiPartGenerator $$0, Condition.TerminalCondition $$1, VariantProperties.Rotation $$2) {
/* 2930 */     List.<Pair>of(
/* 2931 */         Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_0_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_LEFT), 
/* 2932 */         Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_1_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_MID), 
/* 2933 */         Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_2_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_RIGHT), 
/* 2934 */         Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_3_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT), 
/* 2935 */         Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_4_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_MID), 
/* 2936 */         Pair.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_5_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT))
/* 2937 */       .forEach($$3 -> {
/*      */           BooleanProperty $$4 = (BooleanProperty)$$3.getFirst();
/*      */           ModelTemplate $$5 = (ModelTemplate)$$3.getSecond();
/*      */           addBookSlotModel($$0, $$1, $$2, $$4, $$5, true);
/*      */           addBookSlotModel($$0, $$1, $$2, $$4, $$5, false);
/*      */         });
/*      */   }
/*      */   private static final class BookSlotModelCacheKey extends Record { private final ModelTemplate template; private final String modelSuffix;
/* 2945 */     BookSlotModelCacheKey(ModelTemplate $$0, String $$1) { this.template = $$0; this.modelSuffix = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/models/BlockModelGenerators$BookSlotModelCacheKey;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2945	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/* 2945 */       //   0	7	0	this	Lnet/minecraft/data/models/BlockModelGenerators$BookSlotModelCacheKey; } public ModelTemplate template() { return this.template; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/models/BlockModelGenerators$BookSlotModelCacheKey;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2945	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/data/models/BlockModelGenerators$BookSlotModelCacheKey; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/models/BlockModelGenerators$BookSlotModelCacheKey;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2945	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/data/models/BlockModelGenerators$BookSlotModelCacheKey;
/* 2945 */       //   0	8	1	$$0	Ljava/lang/Object; } public String modelSuffix() { return this.modelSuffix; }
/*      */      }
/*      */   
/* 2948 */   private static final Map<BookSlotModelCacheKey, ResourceLocation> CHISELED_BOOKSHELF_SLOT_MODEL_CACHE = new HashMap<>();
/*      */   
/*      */   private void addBookSlotModel(MultiPartGenerator $$0, Condition.TerminalCondition $$1, VariantProperties.Rotation $$2, BooleanProperty $$3, ModelTemplate $$4, boolean $$5) {
/* 2951 */     String $$6 = $$5 ? "_occupied" : "_empty";
/* 2952 */     TextureMapping $$7 = (new TextureMapping()).put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(Blocks.CHISELED_BOOKSHELF, $$6));
/* 2953 */     BookSlotModelCacheKey $$8 = new BookSlotModelCacheKey($$4, $$6);
/* 2954 */     ResourceLocation $$9 = CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.computeIfAbsent($$8, $$3 -> $$0.createWithSuffix(Blocks.CHISELED_BOOKSHELF, $$1, $$2, this.modelOutput));
/*      */     
/* 2956 */     $$0.with(
/* 2957 */         Condition.and(new Condition[] {
/*      */             
/* 2959 */             (Condition)$$1, (Condition)Condition.condition().term((Property)$$3, Boolean.valueOf($$5))
/*      */           
/* 2961 */           }), Variant.variant().with(VariantProperties.MODEL, $$9)
/* 2962 */         .with(VariantProperties.Y_ROT, $$2));
/*      */   }
/*      */ 
/*      */   
/*      */   private void createMagmaBlock() {
/* 2967 */     this.blockStateOutput.accept(createSimpleBlock(Blocks.MAGMA_BLOCK, ModelTemplates.CUBE_ALL.create(Blocks.MAGMA_BLOCK, TextureMapping.cube(ModelLocationUtils.decorateBlockModelLocation("magma")), this.modelOutput)));
/*      */   }
/*      */   
/*      */   private void createShulkerBox(Block $$0) {
/* 2971 */     createTrivialBlock($$0, TexturedModel.PARTICLE_ONLY);
/* 2972 */     ModelTemplates.SHULKER_BOX_INVENTORY.create(ModelLocationUtils.getModelLocation($$0.asItem()), TextureMapping.particle($$0), this.modelOutput);
/*      */   }
/*      */   
/*      */   private void createGrowingPlant(Block $$0, Block $$1, TintState $$2) {
/* 2976 */     createCrossBlock($$0, $$2);
/* 2977 */     createCrossBlock($$1, $$2);
/*      */   }
/*      */   
/*      */   private void createBedItem(Block $$0, Block $$1) {
/* 2981 */     ModelTemplates.BED_INVENTORY.create(ModelLocationUtils.getModelLocation($$0.asItem()), TextureMapping.particle($$1), this.modelOutput);
/*      */   }
/*      */   
/*      */   private void createInfestedStone() {
/* 2985 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.STONE);
/* 2986 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.STONE, "_mirrored");
/* 2987 */     this.blockStateOutput.accept(createRotatedVariant(Blocks.INFESTED_STONE, $$0, $$1));
/* 2988 */     delegateItemModel(Blocks.INFESTED_STONE, $$0);
/*      */   }
/*      */   
/*      */   private void createInfestedDeepslate() {
/* 2992 */     ResourceLocation $$0 = ModelLocationUtils.getModelLocation(Blocks.DEEPSLATE);
/* 2993 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation(Blocks.DEEPSLATE, "_mirrored");
/* 2994 */     this.blockStateOutput.accept(createRotatedVariant(Blocks.INFESTED_DEEPSLATE, $$0, $$1).with(createRotatedPillar()));
/* 2995 */     delegateItemModel(Blocks.INFESTED_DEEPSLATE, $$0);
/*      */   }
/*      */   
/*      */   private void createNetherRoots(Block $$0, Block $$1) {
/* 2999 */     createCrossBlockWithDefaultItem($$0, TintState.NOT_TINTED);
/* 3000 */     TextureMapping $$2 = TextureMapping.plant(TextureMapping.getBlockTexture($$0, "_pot"));
/* 3001 */     ResourceLocation $$3 = TintState.NOT_TINTED.getCrossPot().create($$1, $$2, this.modelOutput);
/* 3002 */     this.blockStateOutput.accept(createSimpleBlock($$1, $$3));
/*      */   }
/*      */   
/*      */   private void createRespawnAnchor() {
/* 3006 */     ResourceLocation $$0 = TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_bottom");
/* 3007 */     ResourceLocation $$1 = TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_top_off");
/* 3008 */     ResourceLocation $$2 = TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_top");
/* 3009 */     ResourceLocation[] $$3 = new ResourceLocation[5];
/* 3010 */     for (int $$4 = 0; $$4 < 5; $$4++) {
/*      */ 
/*      */ 
/*      */       
/* 3014 */       TextureMapping $$5 = (new TextureMapping()).put(TextureSlot.BOTTOM, $$0).put(TextureSlot.TOP, ($$4 == 0) ? $$1 : $$2).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.RESPAWN_ANCHOR, "_side" + $$4));
/* 3015 */       $$3[$$4] = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(Blocks.RESPAWN_ANCHOR, "_" + $$4, $$5, this.modelOutput);
/*      */     } 
/*      */     
/* 3018 */     this.blockStateOutput.accept(
/* 3019 */         MultiVariantGenerator.multiVariant(Blocks.RESPAWN_ANCHOR)
/* 3020 */         .with(
/* 3021 */           PropertyDispatch.property((Property)BlockStateProperties.RESPAWN_ANCHOR_CHARGES)
/* 3022 */           .generate($$1 -> Variant.variant().with(VariantProperties.MODEL, $$0[$$1.intValue()]))));
/*      */ 
/*      */     
/* 3025 */     delegateItemModel(Items.RESPAWN_ANCHOR, $$3[0]);
/*      */   }
/*      */   
/*      */   private Variant applyRotation(FrontAndTop $$0, Variant $$1) {
/* 3029 */     switch ($$0) {
/*      */       case UPPER:
/* 3031 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90);
/*      */       case LOWER:
/* 3033 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
/*      */       case null:
/* 3035 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
/*      */       case null:
/* 3037 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
/*      */       case null:
/* 3039 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
/*      */       case null:
/* 3041 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270);
/*      */       case null:
/* 3043 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
/*      */       case null:
/* 3045 */         return $$1.with(VariantProperties.X_ROT, VariantProperties.Rotation.R270).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
/*      */       
/*      */       case null:
/* 3048 */         return $$1;
/*      */       case null:
/* 3050 */         return $$1.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
/*      */       case null:
/* 3052 */         return $$1.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
/*      */       case null:
/* 3054 */         return $$1.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
/*      */     } 
/* 3056 */     throw new UnsupportedOperationException("Rotation " + $$0 + " can't be expressed with existing x and y values");
/*      */   }
/*      */ 
/*      */   
/*      */   private void createJigsaw() {
/* 3061 */     ResourceLocation $$0 = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_top");
/* 3062 */     ResourceLocation $$1 = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_bottom");
/* 3063 */     ResourceLocation $$2 = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_side");
/* 3064 */     ResourceLocation $$3 = TextureMapping.getBlockTexture(Blocks.JIGSAW, "_lock");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3075 */     TextureMapping $$4 = (new TextureMapping()).put(TextureSlot.DOWN, $$2).put(TextureSlot.WEST, $$2).put(TextureSlot.EAST, $$2).put(TextureSlot.PARTICLE, $$0).put(TextureSlot.NORTH, $$0).put(TextureSlot.SOUTH, $$1).put(TextureSlot.UP, $$3);
/*      */     
/* 3077 */     ResourceLocation $$5 = ModelTemplates.CUBE_DIRECTIONAL.create(Blocks.JIGSAW, $$4, this.modelOutput);
/* 3078 */     this.blockStateOutput.accept(
/* 3079 */         MultiVariantGenerator.multiVariant(Blocks.JIGSAW, Variant.variant().with(VariantProperties.MODEL, $$5))
/* 3080 */         .with(
/* 3081 */           PropertyDispatch.property((Property)BlockStateProperties.ORIENTATION)
/* 3082 */           .generate($$0 -> applyRotation($$0, Variant.variant()))));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createPetrifiedOakSlab() {
/* 3088 */     Block $$0 = Blocks.OAK_PLANKS;
/* 3089 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation($$0);
/* 3090 */     TexturedModel $$2 = TexturedModel.CUBE.get($$0);
/* 3091 */     Block $$3 = Blocks.PETRIFIED_OAK_SLAB;
/* 3092 */     ResourceLocation $$4 = ModelTemplates.SLAB_BOTTOM.create($$3, $$2.getMapping(), this.modelOutput);
/* 3093 */     ResourceLocation $$5 = ModelTemplates.SLAB_TOP.create($$3, $$2.getMapping(), this.modelOutput);
/* 3094 */     this.blockStateOutput.accept(createSlab($$3, $$4, $$5, $$1));
/*      */   }
/*      */   
/*      */   public void run() {
/* 3098 */     BlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach($$0 -> family($$0.getBaseBlock()).generateFor($$0));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3103 */     family(Blocks.CUT_COPPER)
/* 3104 */       .generateFor(BlockFamilies.CUT_COPPER)
/* 3105 */       .donateModelTo(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER)
/* 3106 */       .donateModelTo(Blocks.CHISELED_COPPER, Blocks.WAXED_CHISELED_COPPER)
/* 3107 */       .generateFor(BlockFamilies.WAXED_CUT_COPPER);
/*      */     
/* 3109 */     family(Blocks.EXPOSED_CUT_COPPER)
/* 3110 */       .generateFor(BlockFamilies.EXPOSED_CUT_COPPER)
/* 3111 */       .donateModelTo(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER)
/* 3112 */       .donateModelTo(Blocks.EXPOSED_CHISELED_COPPER, Blocks.WAXED_EXPOSED_CHISELED_COPPER)
/* 3113 */       .generateFor(BlockFamilies.WAXED_EXPOSED_CUT_COPPER);
/*      */     
/* 3115 */     family(Blocks.WEATHERED_CUT_COPPER)
/* 3116 */       .generateFor(BlockFamilies.WEATHERED_CUT_COPPER)
/* 3117 */       .donateModelTo(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER)
/* 3118 */       .donateModelTo(Blocks.WEATHERED_CHISELED_COPPER, Blocks.WAXED_WEATHERED_CHISELED_COPPER)
/* 3119 */       .generateFor(BlockFamilies.WAXED_WEATHERED_CUT_COPPER);
/*      */     
/* 3121 */     family(Blocks.OXIDIZED_CUT_COPPER)
/* 3122 */       .generateFor(BlockFamilies.OXIDIZED_CUT_COPPER)
/* 3123 */       .donateModelTo(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER)
/* 3124 */       .donateModelTo(Blocks.OXIDIZED_CHISELED_COPPER, Blocks.WAXED_OXIDIZED_CHISELED_COPPER)
/* 3125 */       .generateFor(BlockFamilies.WAXED_OXIDIZED_CUT_COPPER);
/*      */     
/* 3127 */     createCopperBulb(Blocks.COPPER_BULB);
/* 3128 */     createCopperBulb(Blocks.EXPOSED_COPPER_BULB);
/* 3129 */     createCopperBulb(Blocks.WEATHERED_COPPER_BULB);
/* 3130 */     createCopperBulb(Blocks.OXIDIZED_COPPER_BULB);
/* 3131 */     copyCopperBulbModel(Blocks.COPPER_BULB, Blocks.WAXED_COPPER_BULB);
/* 3132 */     copyCopperBulbModel(Blocks.EXPOSED_COPPER_BULB, Blocks.WAXED_EXPOSED_COPPER_BULB);
/* 3133 */     copyCopperBulbModel(Blocks.WEATHERED_COPPER_BULB, Blocks.WAXED_WEATHERED_COPPER_BULB);
/* 3134 */     copyCopperBulbModel(Blocks.OXIDIZED_COPPER_BULB, Blocks.WAXED_OXIDIZED_COPPER_BULB);
/*      */     
/* 3136 */     createNonTemplateModelBlock(Blocks.AIR);
/* 3137 */     createNonTemplateModelBlock(Blocks.CAVE_AIR, Blocks.AIR);
/* 3138 */     createNonTemplateModelBlock(Blocks.VOID_AIR, Blocks.AIR);
/* 3139 */     createNonTemplateModelBlock(Blocks.BEACON);
/* 3140 */     createNonTemplateModelBlock(Blocks.CACTUS);
/* 3141 */     createNonTemplateModelBlock(Blocks.BUBBLE_COLUMN, Blocks.WATER);
/* 3142 */     createNonTemplateModelBlock(Blocks.DRAGON_EGG);
/* 3143 */     createNonTemplateModelBlock(Blocks.DRIED_KELP_BLOCK);
/* 3144 */     createNonTemplateModelBlock(Blocks.ENCHANTING_TABLE);
/* 3145 */     createNonTemplateModelBlock(Blocks.FLOWER_POT);
/* 3146 */     createSimpleFlatItemModel(Items.FLOWER_POT);
/* 3147 */     createNonTemplateModelBlock(Blocks.HONEY_BLOCK);
/* 3148 */     createNonTemplateModelBlock(Blocks.WATER);
/* 3149 */     createNonTemplateModelBlock(Blocks.LAVA);
/* 3150 */     createNonTemplateModelBlock(Blocks.SLIME_BLOCK);
/* 3151 */     createSimpleFlatItemModel(Items.CHAIN);
/* 3152 */     createCandleAndCandleCake(Blocks.WHITE_CANDLE, Blocks.WHITE_CANDLE_CAKE);
/* 3153 */     createCandleAndCandleCake(Blocks.ORANGE_CANDLE, Blocks.ORANGE_CANDLE_CAKE);
/* 3154 */     createCandleAndCandleCake(Blocks.MAGENTA_CANDLE, Blocks.MAGENTA_CANDLE_CAKE);
/* 3155 */     createCandleAndCandleCake(Blocks.LIGHT_BLUE_CANDLE, Blocks.LIGHT_BLUE_CANDLE_CAKE);
/* 3156 */     createCandleAndCandleCake(Blocks.YELLOW_CANDLE, Blocks.YELLOW_CANDLE_CAKE);
/* 3157 */     createCandleAndCandleCake(Blocks.LIME_CANDLE, Blocks.LIME_CANDLE_CAKE);
/* 3158 */     createCandleAndCandleCake(Blocks.PINK_CANDLE, Blocks.PINK_CANDLE_CAKE);
/* 3159 */     createCandleAndCandleCake(Blocks.GRAY_CANDLE, Blocks.GRAY_CANDLE_CAKE);
/* 3160 */     createCandleAndCandleCake(Blocks.LIGHT_GRAY_CANDLE, Blocks.LIGHT_GRAY_CANDLE_CAKE);
/* 3161 */     createCandleAndCandleCake(Blocks.CYAN_CANDLE, Blocks.CYAN_CANDLE_CAKE);
/* 3162 */     createCandleAndCandleCake(Blocks.PURPLE_CANDLE, Blocks.PURPLE_CANDLE_CAKE);
/* 3163 */     createCandleAndCandleCake(Blocks.BLUE_CANDLE, Blocks.BLUE_CANDLE_CAKE);
/* 3164 */     createCandleAndCandleCake(Blocks.BROWN_CANDLE, Blocks.BROWN_CANDLE_CAKE);
/* 3165 */     createCandleAndCandleCake(Blocks.GREEN_CANDLE, Blocks.GREEN_CANDLE_CAKE);
/* 3166 */     createCandleAndCandleCake(Blocks.RED_CANDLE, Blocks.RED_CANDLE_CAKE);
/* 3167 */     createCandleAndCandleCake(Blocks.BLACK_CANDLE, Blocks.BLACK_CANDLE_CAKE);
/* 3168 */     createCandleAndCandleCake(Blocks.CANDLE, Blocks.CANDLE_CAKE);
/*      */     
/* 3170 */     createNonTemplateModelBlock(Blocks.POTTED_BAMBOO);
/* 3171 */     createNonTemplateModelBlock(Blocks.POTTED_CACTUS);
/* 3172 */     createNonTemplateModelBlock(Blocks.POWDER_SNOW);
/* 3173 */     createNonTemplateModelBlock(Blocks.SPORE_BLOSSOM);
/* 3174 */     createAzalea(Blocks.AZALEA);
/* 3175 */     createAzalea(Blocks.FLOWERING_AZALEA);
/* 3176 */     createPottedAzalea(Blocks.POTTED_AZALEA);
/* 3177 */     createPottedAzalea(Blocks.POTTED_FLOWERING_AZALEA);
/* 3178 */     createCaveVines();
/* 3179 */     createFullAndCarpetBlocks(Blocks.MOSS_BLOCK, Blocks.MOSS_CARPET);
/* 3180 */     createFlowerBed(Blocks.PINK_PETALS);
/*      */     
/* 3182 */     createAirLikeBlock(Blocks.BARRIER, Items.BARRIER);
/* 3183 */     createSimpleFlatItemModel(Items.BARRIER);
/* 3184 */     createLightBlock();
/* 3185 */     createAirLikeBlock(Blocks.STRUCTURE_VOID, Items.STRUCTURE_VOID);
/* 3186 */     createSimpleFlatItemModel(Items.STRUCTURE_VOID);
/* 3187 */     createAirLikeBlock(Blocks.MOVING_PISTON, TextureMapping.getBlockTexture(Blocks.PISTON, "_side"));
/*      */     
/* 3189 */     createTrivialCube(Blocks.COAL_ORE);
/* 3190 */     createTrivialCube(Blocks.DEEPSLATE_COAL_ORE);
/* 3191 */     createTrivialCube(Blocks.COAL_BLOCK);
/* 3192 */     createTrivialCube(Blocks.DIAMOND_ORE);
/* 3193 */     createTrivialCube(Blocks.DEEPSLATE_DIAMOND_ORE);
/* 3194 */     createTrivialCube(Blocks.DIAMOND_BLOCK);
/* 3195 */     createTrivialCube(Blocks.EMERALD_ORE);
/* 3196 */     createTrivialCube(Blocks.DEEPSLATE_EMERALD_ORE);
/* 3197 */     createTrivialCube(Blocks.EMERALD_BLOCK);
/* 3198 */     createTrivialCube(Blocks.GOLD_ORE);
/* 3199 */     createTrivialCube(Blocks.NETHER_GOLD_ORE);
/* 3200 */     createTrivialCube(Blocks.DEEPSLATE_GOLD_ORE);
/* 3201 */     createTrivialCube(Blocks.GOLD_BLOCK);
/* 3202 */     createTrivialCube(Blocks.IRON_ORE);
/* 3203 */     createTrivialCube(Blocks.DEEPSLATE_IRON_ORE);
/* 3204 */     createTrivialCube(Blocks.IRON_BLOCK);
/* 3205 */     createTrivialBlock(Blocks.ANCIENT_DEBRIS, TexturedModel.COLUMN);
/* 3206 */     createTrivialCube(Blocks.NETHERITE_BLOCK);
/* 3207 */     createTrivialCube(Blocks.LAPIS_ORE);
/* 3208 */     createTrivialCube(Blocks.DEEPSLATE_LAPIS_ORE);
/* 3209 */     createTrivialCube(Blocks.LAPIS_BLOCK);
/* 3210 */     createTrivialCube(Blocks.NETHER_QUARTZ_ORE);
/* 3211 */     createTrivialCube(Blocks.REDSTONE_ORE);
/* 3212 */     createTrivialCube(Blocks.DEEPSLATE_REDSTONE_ORE);
/* 3213 */     createTrivialCube(Blocks.REDSTONE_BLOCK);
/* 3214 */     createTrivialCube(Blocks.GILDED_BLACKSTONE);
/*      */     
/* 3216 */     createTrivialCube(Blocks.BLUE_ICE);
/* 3217 */     createTrivialCube(Blocks.CLAY);
/* 3218 */     createTrivialCube(Blocks.COARSE_DIRT);
/* 3219 */     createTrivialCube(Blocks.CRYING_OBSIDIAN);
/* 3220 */     createTrivialCube(Blocks.END_STONE);
/* 3221 */     createTrivialCube(Blocks.GLOWSTONE);
/* 3222 */     createTrivialCube(Blocks.GRAVEL);
/* 3223 */     createTrivialCube(Blocks.HONEYCOMB_BLOCK);
/* 3224 */     createTrivialCube(Blocks.ICE);
/* 3225 */     createTrivialBlock(Blocks.JUKEBOX, TexturedModel.CUBE_TOP);
/* 3226 */     createTrivialBlock(Blocks.LODESTONE, TexturedModel.COLUMN);
/* 3227 */     createTrivialBlock(Blocks.MELON, TexturedModel.COLUMN);
/* 3228 */     createNonTemplateModelBlock(Blocks.MANGROVE_ROOTS);
/* 3229 */     createNonTemplateModelBlock(Blocks.POTTED_MANGROVE_PROPAGULE);
/* 3230 */     createTrivialCube(Blocks.NETHER_WART_BLOCK);
/* 3231 */     createTrivialCube(Blocks.NOTE_BLOCK);
/* 3232 */     createTrivialCube(Blocks.PACKED_ICE);
/* 3233 */     createTrivialCube(Blocks.OBSIDIAN);
/* 3234 */     createTrivialCube(Blocks.QUARTZ_BRICKS);
/* 3235 */     createTrivialCube(Blocks.SEA_LANTERN);
/* 3236 */     createTrivialCube(Blocks.SHROOMLIGHT);
/* 3237 */     createTrivialCube(Blocks.SOUL_SAND);
/* 3238 */     createTrivialCube(Blocks.SOUL_SOIL);
/* 3239 */     createTrivialBlock(Blocks.SPAWNER, TexturedModel.CUBE_INNER_FACES);
/* 3240 */     createTrivialCube(Blocks.SPONGE);
/* 3241 */     createTrivialBlock(Blocks.SEAGRASS, TexturedModel.SEAGRASS);
/* 3242 */     createSimpleFlatItemModel(Items.SEAGRASS);
/* 3243 */     createTrivialBlock(Blocks.TNT, TexturedModel.CUBE_TOP_BOTTOM);
/* 3244 */     createTrivialBlock(Blocks.TARGET, TexturedModel.COLUMN);
/* 3245 */     createTrivialCube(Blocks.WARPED_WART_BLOCK);
/* 3246 */     createTrivialCube(Blocks.WET_SPONGE);
/* 3247 */     createTrivialCube(Blocks.AMETHYST_BLOCK);
/* 3248 */     createTrivialCube(Blocks.BUDDING_AMETHYST);
/* 3249 */     createTrivialCube(Blocks.CALCITE);
/* 3250 */     createTrivialCube(Blocks.DRIPSTONE_BLOCK);
/* 3251 */     createTrivialCube(Blocks.RAW_IRON_BLOCK);
/* 3252 */     createTrivialCube(Blocks.RAW_COPPER_BLOCK);
/* 3253 */     createTrivialCube(Blocks.RAW_GOLD_BLOCK);
/* 3254 */     createRotatedMirroredVariantBlock(Blocks.SCULK);
/*      */     
/* 3256 */     createPetrifiedOakSlab();
/*      */     
/* 3258 */     createTrivialCube(Blocks.COPPER_ORE);
/* 3259 */     createTrivialCube(Blocks.DEEPSLATE_COPPER_ORE);
/* 3260 */     createTrivialCube(Blocks.COPPER_BLOCK);
/* 3261 */     createTrivialCube(Blocks.EXPOSED_COPPER);
/* 3262 */     createTrivialCube(Blocks.WEATHERED_COPPER);
/* 3263 */     createTrivialCube(Blocks.OXIDIZED_COPPER);
/* 3264 */     copyModel(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK);
/* 3265 */     copyModel(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER);
/* 3266 */     copyModel(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER);
/* 3267 */     copyModel(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER);
/* 3268 */     createDoor(Blocks.COPPER_DOOR);
/* 3269 */     createDoor(Blocks.EXPOSED_COPPER_DOOR);
/* 3270 */     createDoor(Blocks.WEATHERED_COPPER_DOOR);
/* 3271 */     createDoor(Blocks.OXIDIZED_COPPER_DOOR);
/* 3272 */     copyDoorModel(Blocks.COPPER_DOOR, Blocks.WAXED_COPPER_DOOR);
/* 3273 */     copyDoorModel(Blocks.EXPOSED_COPPER_DOOR, Blocks.WAXED_EXPOSED_COPPER_DOOR);
/* 3274 */     copyDoorModel(Blocks.WEATHERED_COPPER_DOOR, Blocks.WAXED_WEATHERED_COPPER_DOOR);
/* 3275 */     copyDoorModel(Blocks.OXIDIZED_COPPER_DOOR, Blocks.WAXED_OXIDIZED_COPPER_DOOR);
/* 3276 */     createTrapdoor(Blocks.COPPER_TRAPDOOR);
/* 3277 */     createTrapdoor(Blocks.EXPOSED_COPPER_TRAPDOOR);
/* 3278 */     createTrapdoor(Blocks.WEATHERED_COPPER_TRAPDOOR);
/* 3279 */     createTrapdoor(Blocks.OXIDIZED_COPPER_TRAPDOOR);
/* 3280 */     copyTrapdoorModel(Blocks.COPPER_TRAPDOOR, Blocks.WAXED_COPPER_TRAPDOOR);
/* 3281 */     copyTrapdoorModel(Blocks.EXPOSED_COPPER_TRAPDOOR, Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR);
/* 3282 */     copyTrapdoorModel(Blocks.WEATHERED_COPPER_TRAPDOOR, Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR);
/* 3283 */     copyTrapdoorModel(Blocks.OXIDIZED_COPPER_TRAPDOOR, Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR);
/* 3284 */     createTrivialCube(Blocks.COPPER_GRATE);
/* 3285 */     createTrivialCube(Blocks.EXPOSED_COPPER_GRATE);
/* 3286 */     createTrivialCube(Blocks.WEATHERED_COPPER_GRATE);
/* 3287 */     createTrivialCube(Blocks.OXIDIZED_COPPER_GRATE);
/* 3288 */     copyModel(Blocks.COPPER_GRATE, Blocks.WAXED_COPPER_GRATE);
/* 3289 */     copyModel(Blocks.EXPOSED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER_GRATE);
/* 3290 */     copyModel(Blocks.WEATHERED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER_GRATE);
/* 3291 */     copyModel(Blocks.OXIDIZED_COPPER_GRATE, Blocks.WAXED_OXIDIZED_COPPER_GRATE);
/*      */     
/* 3293 */     createWeightedPressurePlate(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.GOLD_BLOCK);
/* 3294 */     createWeightedPressurePlate(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.IRON_BLOCK);
/*      */     
/* 3296 */     createAmethystClusters();
/* 3297 */     createBookshelf();
/* 3298 */     createChiseledBookshelf();
/* 3299 */     createBrewingStand();
/* 3300 */     createCakeBlock();
/* 3301 */     createCampfires(new Block[] { Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE });
/* 3302 */     createCartographyTable();
/* 3303 */     createCauldrons();
/* 3304 */     createChorusFlower();
/* 3305 */     createChorusPlant();
/* 3306 */     createComposter();
/* 3307 */     createDaylightDetector();
/* 3308 */     createEndPortalFrame();
/* 3309 */     createRotatableColumn(Blocks.END_ROD);
/* 3310 */     createLightningRod();
/* 3311 */     createFarmland();
/* 3312 */     createFire();
/* 3313 */     createSoulFire();
/* 3314 */     createFrostedIce();
/* 3315 */     createGrassBlocks();
/* 3316 */     createCocoa();
/* 3317 */     createDirtPath();
/* 3318 */     createGrindstone();
/* 3319 */     createHopper();
/* 3320 */     createIronBars();
/* 3321 */     createLever();
/* 3322 */     createLilyPad();
/* 3323 */     createNetherPortalBlock();
/* 3324 */     createNetherrack();
/* 3325 */     createObserver();
/* 3326 */     createPistons();
/* 3327 */     createPistonHeads();
/* 3328 */     createScaffolding();
/* 3329 */     createRedstoneTorch();
/* 3330 */     createRedstoneLamp();
/* 3331 */     createRepeater();
/* 3332 */     createSeaPickle();
/* 3333 */     createSmithingTable();
/* 3334 */     createSnowBlocks();
/* 3335 */     createStonecutter();
/* 3336 */     createStructureBlock();
/* 3337 */     createSweetBerryBush();
/* 3338 */     createTripwire();
/* 3339 */     createTripwireHook();
/* 3340 */     createTurtleEgg();
/* 3341 */     createSnifferEgg();
/* 3342 */     createMultiface(Blocks.VINE);
/* 3343 */     createMultiface(Blocks.GLOW_LICHEN);
/* 3344 */     createMultiface(Blocks.SCULK_VEIN);
/* 3345 */     createMagmaBlock();
/* 3346 */     createJigsaw();
/* 3347 */     createSculkSensor();
/* 3348 */     createCalibratedSculkSensor();
/* 3349 */     createSculkShrieker();
/* 3350 */     createFrogspawnBlock();
/* 3351 */     createMangrovePropagule();
/* 3352 */     createMuddyMangroveRoots();
/* 3353 */     createTrialSpawner();
/*      */     
/* 3355 */     createNonTemplateHorizontalBlock(Blocks.LADDER);
/* 3356 */     createSimpleFlatItemModel(Blocks.LADDER);
/* 3357 */     createNonTemplateHorizontalBlock(Blocks.LECTERN);
/*      */     
/* 3359 */     createBigDripLeafBlock();
/* 3360 */     createNonTemplateHorizontalBlock(Blocks.BIG_DRIPLEAF_STEM);
/*      */     
/* 3362 */     createNormalTorch(Blocks.TORCH, Blocks.WALL_TORCH);
/* 3363 */     createNormalTorch(Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH);
/*      */     
/* 3365 */     createCraftingTableLike(Blocks.CRAFTING_TABLE, Blocks.OAK_PLANKS, TextureMapping::craftingTable);
/* 3366 */     createCraftingTableLike(Blocks.FLETCHING_TABLE, Blocks.BIRCH_PLANKS, TextureMapping::fletchingTable);
/*      */     
/* 3368 */     createNyliumBlock(Blocks.CRIMSON_NYLIUM);
/* 3369 */     createNyliumBlock(Blocks.WARPED_NYLIUM);
/*      */     
/* 3371 */     createDispenserBlock(Blocks.DISPENSER);
/* 3372 */     createDispenserBlock(Blocks.DROPPER);
/* 3373 */     createCrafterBlock();
/*      */     
/* 3375 */     createLantern(Blocks.LANTERN);
/* 3376 */     createLantern(Blocks.SOUL_LANTERN);
/*      */     
/* 3378 */     createAxisAlignedPillarBlockCustomModel(Blocks.CHAIN, ModelLocationUtils.getModelLocation(Blocks.CHAIN));
/* 3379 */     createAxisAlignedPillarBlock(Blocks.BASALT, TexturedModel.COLUMN);
/* 3380 */     createAxisAlignedPillarBlock(Blocks.POLISHED_BASALT, TexturedModel.COLUMN);
/* 3381 */     createTrivialCube(Blocks.SMOOTH_BASALT);
/* 3382 */     createAxisAlignedPillarBlock(Blocks.BONE_BLOCK, TexturedModel.COLUMN);
/* 3383 */     createRotatedVariantBlock(Blocks.DIRT);
/* 3384 */     createRotatedVariantBlock(Blocks.ROOTED_DIRT);
/* 3385 */     createRotatedVariantBlock(Blocks.SAND);
/* 3386 */     createBrushableBlock(Blocks.SUSPICIOUS_SAND);
/* 3387 */     createBrushableBlock(Blocks.SUSPICIOUS_GRAVEL);
/* 3388 */     createRotatedVariantBlock(Blocks.RED_SAND);
/* 3389 */     createRotatedMirroredVariantBlock(Blocks.BEDROCK);
/* 3390 */     createTrivialBlock(Blocks.REINFORCED_DEEPSLATE, TexturedModel.CUBE_TOP_BOTTOM);
/* 3391 */     createRotatedPillarWithHorizontalVariant(Blocks.HAY_BLOCK, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
/* 3392 */     createRotatedPillarWithHorizontalVariant(Blocks.PURPUR_PILLAR, TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
/* 3393 */     createRotatedPillarWithHorizontalVariant(Blocks.QUARTZ_PILLAR, TexturedModel.COLUMN_ALT, TexturedModel.COLUMN_HORIZONTAL_ALT);
/* 3394 */     createRotatedPillarWithHorizontalVariant(Blocks.OCHRE_FROGLIGHT, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
/* 3395 */     createRotatedPillarWithHorizontalVariant(Blocks.VERDANT_FROGLIGHT, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
/* 3396 */     createRotatedPillarWithHorizontalVariant(Blocks.PEARLESCENT_FROGLIGHT, TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
/*      */     
/* 3398 */     createHorizontallyRotatedBlock(Blocks.LOOM, TexturedModel.ORIENTABLE);
/*      */     
/* 3400 */     createPumpkins();
/* 3401 */     createBeeNest(Blocks.BEE_NEST, TextureMapping::orientableCube);
/* 3402 */     createBeeNest(Blocks.BEEHIVE, TextureMapping::orientableCubeSameEnds);
/*      */ 
/*      */     
/* 3405 */     createCropBlock(Blocks.BEETROOTS, (Property<Integer>)BlockStateProperties.AGE_3, new int[] { 0, 1, 2, 3 });
/* 3406 */     createCropBlock(Blocks.CARROTS, (Property<Integer>)BlockStateProperties.AGE_7, new int[] { 0, 0, 1, 1, 2, 2, 2, 3 });
/* 3407 */     createCropBlock(Blocks.NETHER_WART, (Property<Integer>)BlockStateProperties.AGE_3, new int[] { 0, 1, 1, 2 });
/* 3408 */     createCropBlock(Blocks.POTATOES, (Property<Integer>)BlockStateProperties.AGE_7, new int[] { 0, 0, 1, 1, 2, 2, 2, 3 });
/* 3409 */     createCropBlock(Blocks.WHEAT, (Property<Integer>)BlockStateProperties.AGE_7, new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });
/* 3410 */     createCrossBlock(Blocks.TORCHFLOWER_CROP, TintState.NOT_TINTED, (Property<Integer>)BlockStateProperties.AGE_1, new int[] { 0, 1 });
/* 3411 */     createPitcherCrop();
/* 3412 */     createPitcherPlant();
/*      */     
/* 3414 */     blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("decorated_pot"), Blocks.TERRACOTTA)
/* 3415 */       .createWithoutBlockItem(new Block[] { Blocks.DECORATED_POT });
/*      */     
/* 3417 */     blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("banner"), Blocks.OAK_PLANKS)
/* 3418 */       .createWithCustomBlockItemModel(ModelTemplates.BANNER_INVENTORY, new Block[] {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           Blocks.WHITE_BANNER, Blocks.ORANGE_BANNER, Blocks.MAGENTA_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.YELLOW_BANNER, Blocks.LIME_BANNER, Blocks.PINK_BANNER, Blocks.GRAY_BANNER, Blocks.LIGHT_GRAY_BANNER, Blocks.CYAN_BANNER,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           Blocks.PURPLE_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.GREEN_BANNER, Blocks.RED_BANNER, Blocks.BLACK_BANNER
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3436 */         }).createWithoutBlockItem(new Block[] { 
/*      */           Blocks.WHITE_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER, Blocks.LIME_WALL_BANNER, Blocks.PINK_WALL_BANNER, Blocks.GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.CYAN_WALL_BANNER, 
/*      */           Blocks.PURPLE_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.BLACK_WALL_BANNER });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3455 */     blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("bed"), Blocks.OAK_PLANKS)
/* 3456 */       .createWithoutBlockItem(new Block[] { 
/*      */           Blocks.WHITE_BED, Blocks.ORANGE_BED, Blocks.MAGENTA_BED, Blocks.LIGHT_BLUE_BED, Blocks.YELLOW_BED, Blocks.LIME_BED, Blocks.PINK_BED, Blocks.GRAY_BED, Blocks.LIGHT_GRAY_BED, Blocks.CYAN_BED, 
/*      */           Blocks.PURPLE_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.GREEN_BED, Blocks.RED_BED, Blocks.BLACK_BED });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3475 */     createBedItem(Blocks.WHITE_BED, Blocks.WHITE_WOOL);
/* 3476 */     createBedItem(Blocks.ORANGE_BED, Blocks.ORANGE_WOOL);
/* 3477 */     createBedItem(Blocks.MAGENTA_BED, Blocks.MAGENTA_WOOL);
/* 3478 */     createBedItem(Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_WOOL);
/* 3479 */     createBedItem(Blocks.YELLOW_BED, Blocks.YELLOW_WOOL);
/* 3480 */     createBedItem(Blocks.LIME_BED, Blocks.LIME_WOOL);
/* 3481 */     createBedItem(Blocks.PINK_BED, Blocks.PINK_WOOL);
/* 3482 */     createBedItem(Blocks.GRAY_BED, Blocks.GRAY_WOOL);
/* 3483 */     createBedItem(Blocks.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_WOOL);
/* 3484 */     createBedItem(Blocks.CYAN_BED, Blocks.CYAN_WOOL);
/* 3485 */     createBedItem(Blocks.PURPLE_BED, Blocks.PURPLE_WOOL);
/* 3486 */     createBedItem(Blocks.BLUE_BED, Blocks.BLUE_WOOL);
/* 3487 */     createBedItem(Blocks.BROWN_BED, Blocks.BROWN_WOOL);
/* 3488 */     createBedItem(Blocks.GREEN_BED, Blocks.GREEN_WOOL);
/* 3489 */     createBedItem(Blocks.RED_BED, Blocks.RED_WOOL);
/* 3490 */     createBedItem(Blocks.BLACK_BED, Blocks.BLACK_WOOL);
/*      */     
/* 3492 */     blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("skull"), Blocks.SOUL_SAND)
/* 3493 */       .createWithCustomBlockItemModel(ModelTemplates.SKULL_INVENTORY, new Block[] {
/*      */ 
/*      */           
/*      */           Blocks.CREEPER_HEAD, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PIGLIN_HEAD
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3501 */         }).create(new Block[] {
/*      */           
/*      */           Blocks.DRAGON_HEAD
/* 3504 */         }).createWithoutBlockItem(new Block[] { Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_WALL_HEAD, Blocks.PLAYER_WALL_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.SKELETON_WALL_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL, Blocks.PIGLIN_WALL_HEAD });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3514 */     createShulkerBox(Blocks.SHULKER_BOX);
/* 3515 */     createShulkerBox(Blocks.WHITE_SHULKER_BOX);
/* 3516 */     createShulkerBox(Blocks.ORANGE_SHULKER_BOX);
/* 3517 */     createShulkerBox(Blocks.MAGENTA_SHULKER_BOX);
/* 3518 */     createShulkerBox(Blocks.LIGHT_BLUE_SHULKER_BOX);
/* 3519 */     createShulkerBox(Blocks.YELLOW_SHULKER_BOX);
/* 3520 */     createShulkerBox(Blocks.LIME_SHULKER_BOX);
/* 3521 */     createShulkerBox(Blocks.PINK_SHULKER_BOX);
/* 3522 */     createShulkerBox(Blocks.GRAY_SHULKER_BOX);
/* 3523 */     createShulkerBox(Blocks.LIGHT_GRAY_SHULKER_BOX);
/* 3524 */     createShulkerBox(Blocks.CYAN_SHULKER_BOX);
/* 3525 */     createShulkerBox(Blocks.PURPLE_SHULKER_BOX);
/* 3526 */     createShulkerBox(Blocks.BLUE_SHULKER_BOX);
/* 3527 */     createShulkerBox(Blocks.BROWN_SHULKER_BOX);
/* 3528 */     createShulkerBox(Blocks.GREEN_SHULKER_BOX);
/* 3529 */     createShulkerBox(Blocks.RED_SHULKER_BOX);
/* 3530 */     createShulkerBox(Blocks.BLACK_SHULKER_BOX);
/*      */     
/* 3532 */     createTrivialBlock(Blocks.CONDUIT, TexturedModel.PARTICLE_ONLY);
/* 3533 */     skipAutoItemBlock(Blocks.CONDUIT);
/*      */     
/* 3535 */     blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("chest"), Blocks.OAK_PLANKS)
/* 3536 */       .createWithoutBlockItem(new Block[] { Blocks.CHEST, Blocks.TRAPPED_CHEST });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3541 */     blockEntityModels(ModelLocationUtils.decorateBlockModelLocation("ender_chest"), Blocks.OBSIDIAN)
/* 3542 */       .createWithoutBlockItem(new Block[] { Blocks.ENDER_CHEST });
/*      */ 
/*      */ 
/*      */     
/* 3546 */     blockEntityModels(Blocks.END_PORTAL, Blocks.OBSIDIAN)
/* 3547 */       .create(new Block[] { Blocks.END_PORTAL, Blocks.END_GATEWAY });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3552 */     createTrivialCube(Blocks.AZALEA_LEAVES);
/* 3553 */     createTrivialCube(Blocks.FLOWERING_AZALEA_LEAVES);
/* 3554 */     createTrivialCube(Blocks.WHITE_CONCRETE);
/* 3555 */     createTrivialCube(Blocks.ORANGE_CONCRETE);
/* 3556 */     createTrivialCube(Blocks.MAGENTA_CONCRETE);
/* 3557 */     createTrivialCube(Blocks.LIGHT_BLUE_CONCRETE);
/* 3558 */     createTrivialCube(Blocks.YELLOW_CONCRETE);
/* 3559 */     createTrivialCube(Blocks.LIME_CONCRETE);
/* 3560 */     createTrivialCube(Blocks.PINK_CONCRETE);
/* 3561 */     createTrivialCube(Blocks.GRAY_CONCRETE);
/* 3562 */     createTrivialCube(Blocks.LIGHT_GRAY_CONCRETE);
/* 3563 */     createTrivialCube(Blocks.CYAN_CONCRETE);
/* 3564 */     createTrivialCube(Blocks.PURPLE_CONCRETE);
/* 3565 */     createTrivialCube(Blocks.BLUE_CONCRETE);
/* 3566 */     createTrivialCube(Blocks.BROWN_CONCRETE);
/* 3567 */     createTrivialCube(Blocks.GREEN_CONCRETE);
/* 3568 */     createTrivialCube(Blocks.RED_CONCRETE);
/* 3569 */     createTrivialCube(Blocks.BLACK_CONCRETE);
/*      */     
/* 3571 */     createColoredBlockWithRandomRotations(TexturedModel.CUBE, new Block[] { Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3590 */     createTrivialCube(Blocks.TERRACOTTA);
/* 3591 */     createTrivialCube(Blocks.WHITE_TERRACOTTA);
/* 3592 */     createTrivialCube(Blocks.ORANGE_TERRACOTTA);
/* 3593 */     createTrivialCube(Blocks.MAGENTA_TERRACOTTA);
/* 3594 */     createTrivialCube(Blocks.LIGHT_BLUE_TERRACOTTA);
/* 3595 */     createTrivialCube(Blocks.YELLOW_TERRACOTTA);
/* 3596 */     createTrivialCube(Blocks.LIME_TERRACOTTA);
/* 3597 */     createTrivialCube(Blocks.PINK_TERRACOTTA);
/* 3598 */     createTrivialCube(Blocks.GRAY_TERRACOTTA);
/* 3599 */     createTrivialCube(Blocks.LIGHT_GRAY_TERRACOTTA);
/* 3600 */     createTrivialCube(Blocks.CYAN_TERRACOTTA);
/* 3601 */     createTrivialCube(Blocks.PURPLE_TERRACOTTA);
/* 3602 */     createTrivialCube(Blocks.BLUE_TERRACOTTA);
/* 3603 */     createTrivialCube(Blocks.BROWN_TERRACOTTA);
/* 3604 */     createTrivialCube(Blocks.GREEN_TERRACOTTA);
/* 3605 */     createTrivialCube(Blocks.RED_TERRACOTTA);
/* 3606 */     createTrivialCube(Blocks.BLACK_TERRACOTTA);
/*      */     
/* 3608 */     createTrivialCube(Blocks.TINTED_GLASS);
/* 3609 */     createGlassBlocks(Blocks.GLASS, Blocks.GLASS_PANE);
/* 3610 */     createGlassBlocks(Blocks.WHITE_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS_PANE);
/* 3611 */     createGlassBlocks(Blocks.ORANGE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS_PANE);
/* 3612 */     createGlassBlocks(Blocks.MAGENTA_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS_PANE);
/* 3613 */     createGlassBlocks(Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
/* 3614 */     createGlassBlocks(Blocks.YELLOW_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS_PANE);
/* 3615 */     createGlassBlocks(Blocks.LIME_STAINED_GLASS, Blocks.LIME_STAINED_GLASS_PANE);
/* 3616 */     createGlassBlocks(Blocks.PINK_STAINED_GLASS, Blocks.PINK_STAINED_GLASS_PANE);
/* 3617 */     createGlassBlocks(Blocks.GRAY_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS_PANE);
/* 3618 */     createGlassBlocks(Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
/* 3619 */     createGlassBlocks(Blocks.CYAN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS_PANE);
/* 3620 */     createGlassBlocks(Blocks.PURPLE_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS_PANE);
/* 3621 */     createGlassBlocks(Blocks.BLUE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS_PANE);
/* 3622 */     createGlassBlocks(Blocks.BROWN_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS_PANE);
/* 3623 */     createGlassBlocks(Blocks.GREEN_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS_PANE);
/* 3624 */     createGlassBlocks(Blocks.RED_STAINED_GLASS, Blocks.RED_STAINED_GLASS_PANE);
/* 3625 */     createGlassBlocks(Blocks.BLACK_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS_PANE);
/*      */     
/* 3627 */     createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, new Block[] { Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3646 */     createFullAndCarpetBlocks(Blocks.WHITE_WOOL, Blocks.WHITE_CARPET);
/* 3647 */     createFullAndCarpetBlocks(Blocks.ORANGE_WOOL, Blocks.ORANGE_CARPET);
/* 3648 */     createFullAndCarpetBlocks(Blocks.MAGENTA_WOOL, Blocks.MAGENTA_CARPET);
/* 3649 */     createFullAndCarpetBlocks(Blocks.LIGHT_BLUE_WOOL, Blocks.LIGHT_BLUE_CARPET);
/* 3650 */     createFullAndCarpetBlocks(Blocks.YELLOW_WOOL, Blocks.YELLOW_CARPET);
/* 3651 */     createFullAndCarpetBlocks(Blocks.LIME_WOOL, Blocks.LIME_CARPET);
/* 3652 */     createFullAndCarpetBlocks(Blocks.PINK_WOOL, Blocks.PINK_CARPET);
/* 3653 */     createFullAndCarpetBlocks(Blocks.GRAY_WOOL, Blocks.GRAY_CARPET);
/* 3654 */     createFullAndCarpetBlocks(Blocks.LIGHT_GRAY_WOOL, Blocks.LIGHT_GRAY_CARPET);
/* 3655 */     createFullAndCarpetBlocks(Blocks.CYAN_WOOL, Blocks.CYAN_CARPET);
/* 3656 */     createFullAndCarpetBlocks(Blocks.PURPLE_WOOL, Blocks.PURPLE_CARPET);
/* 3657 */     createFullAndCarpetBlocks(Blocks.BLUE_WOOL, Blocks.BLUE_CARPET);
/* 3658 */     createFullAndCarpetBlocks(Blocks.BROWN_WOOL, Blocks.BROWN_CARPET);
/* 3659 */     createFullAndCarpetBlocks(Blocks.GREEN_WOOL, Blocks.GREEN_CARPET);
/* 3660 */     createFullAndCarpetBlocks(Blocks.RED_WOOL, Blocks.RED_CARPET);
/* 3661 */     createFullAndCarpetBlocks(Blocks.BLACK_WOOL, Blocks.BLACK_CARPET);
/*      */     
/* 3663 */     createTrivialCube(Blocks.MUD);
/* 3664 */     createTrivialCube(Blocks.PACKED_MUD);
/*      */     
/* 3666 */     createPlant(Blocks.FERN, Blocks.POTTED_FERN, TintState.TINTED);
/* 3667 */     createPlant(Blocks.DANDELION, Blocks.POTTED_DANDELION, TintState.NOT_TINTED);
/* 3668 */     createPlant(Blocks.POPPY, Blocks.POTTED_POPPY, TintState.NOT_TINTED);
/* 3669 */     createPlant(Blocks.BLUE_ORCHID, Blocks.POTTED_BLUE_ORCHID, TintState.NOT_TINTED);
/* 3670 */     createPlant(Blocks.ALLIUM, Blocks.POTTED_ALLIUM, TintState.NOT_TINTED);
/* 3671 */     createPlant(Blocks.AZURE_BLUET, Blocks.POTTED_AZURE_BLUET, TintState.NOT_TINTED);
/* 3672 */     createPlant(Blocks.RED_TULIP, Blocks.POTTED_RED_TULIP, TintState.NOT_TINTED);
/* 3673 */     createPlant(Blocks.ORANGE_TULIP, Blocks.POTTED_ORANGE_TULIP, TintState.NOT_TINTED);
/* 3674 */     createPlant(Blocks.WHITE_TULIP, Blocks.POTTED_WHITE_TULIP, TintState.NOT_TINTED);
/* 3675 */     createPlant(Blocks.PINK_TULIP, Blocks.POTTED_PINK_TULIP, TintState.NOT_TINTED);
/* 3676 */     createPlant(Blocks.OXEYE_DAISY, Blocks.POTTED_OXEYE_DAISY, TintState.NOT_TINTED);
/* 3677 */     createPlant(Blocks.CORNFLOWER, Blocks.POTTED_CORNFLOWER, TintState.NOT_TINTED);
/* 3678 */     createPlant(Blocks.LILY_OF_THE_VALLEY, Blocks.POTTED_LILY_OF_THE_VALLEY, TintState.NOT_TINTED);
/* 3679 */     createPlant(Blocks.WITHER_ROSE, Blocks.POTTED_WITHER_ROSE, TintState.NOT_TINTED);
/* 3680 */     createPlant(Blocks.RED_MUSHROOM, Blocks.POTTED_RED_MUSHROOM, TintState.NOT_TINTED);
/* 3681 */     createPlant(Blocks.BROWN_MUSHROOM, Blocks.POTTED_BROWN_MUSHROOM, TintState.NOT_TINTED);
/* 3682 */     createPlant(Blocks.DEAD_BUSH, Blocks.POTTED_DEAD_BUSH, TintState.NOT_TINTED);
/* 3683 */     createPlant(Blocks.TORCHFLOWER, Blocks.POTTED_TORCHFLOWER, TintState.NOT_TINTED);
/*      */     
/* 3685 */     createPointedDripstone();
/*      */     
/* 3687 */     createMushroomBlock(Blocks.BROWN_MUSHROOM_BLOCK);
/* 3688 */     createMushroomBlock(Blocks.RED_MUSHROOM_BLOCK);
/* 3689 */     createMushroomBlock(Blocks.MUSHROOM_STEM);
/*      */     
/* 3691 */     createCrossBlockWithDefaultItem(Blocks.SHORT_GRASS, TintState.TINTED);
/* 3692 */     createCrossBlock(Blocks.SUGAR_CANE, TintState.TINTED);
/* 3693 */     createSimpleFlatItemModel(Items.SUGAR_CANE);
/* 3694 */     createGrowingPlant(Blocks.KELP, Blocks.KELP_PLANT, TintState.NOT_TINTED);
/* 3695 */     createSimpleFlatItemModel(Items.KELP);
/* 3696 */     skipAutoItemBlock(Blocks.KELP_PLANT);
/* 3697 */     createCrossBlock(Blocks.HANGING_ROOTS, TintState.NOT_TINTED);
/* 3698 */     skipAutoItemBlock(Blocks.HANGING_ROOTS);
/* 3699 */     skipAutoItemBlock(Blocks.CAVE_VINES_PLANT);
/* 3700 */     createGrowingPlant(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT, TintState.NOT_TINTED);
/* 3701 */     createGrowingPlant(Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT, TintState.NOT_TINTED);
/* 3702 */     createSimpleFlatItemModel(Blocks.WEEPING_VINES, "_plant");
/* 3703 */     skipAutoItemBlock(Blocks.WEEPING_VINES_PLANT);
/* 3704 */     createSimpleFlatItemModel(Blocks.TWISTING_VINES, "_plant");
/* 3705 */     skipAutoItemBlock(Blocks.TWISTING_VINES_PLANT);
/* 3706 */     createCrossBlockWithDefaultItem(Blocks.BAMBOO_SAPLING, TintState.TINTED, TextureMapping.cross(TextureMapping.getBlockTexture(Blocks.BAMBOO, "_stage0")));
/* 3707 */     createBamboo();
/*      */     
/* 3709 */     createCrossBlockWithDefaultItem(Blocks.COBWEB, TintState.NOT_TINTED);
/*      */     
/* 3711 */     createDoublePlant(Blocks.LILAC, TintState.NOT_TINTED);
/* 3712 */     createDoublePlant(Blocks.ROSE_BUSH, TintState.NOT_TINTED);
/* 3713 */     createDoublePlant(Blocks.PEONY, TintState.NOT_TINTED);
/* 3714 */     createDoublePlant(Blocks.TALL_GRASS, TintState.TINTED);
/* 3715 */     createDoublePlant(Blocks.LARGE_FERN, TintState.TINTED);
/*      */     
/* 3717 */     createSunflower();
/* 3718 */     createTallSeagrass();
/* 3719 */     createSmallDripleaf();
/*      */     
/* 3721 */     createCoral(Blocks.TUBE_CORAL, Blocks.DEAD_TUBE_CORAL, Blocks.TUBE_CORAL_BLOCK, Blocks.DEAD_TUBE_CORAL_BLOCK, Blocks.TUBE_CORAL_FAN, Blocks.DEAD_TUBE_CORAL_FAN, Blocks.TUBE_CORAL_WALL_FAN, Blocks.DEAD_TUBE_CORAL_WALL_FAN);
/* 3722 */     createCoral(Blocks.BRAIN_CORAL, Blocks.DEAD_BRAIN_CORAL, Blocks.BRAIN_CORAL_BLOCK, Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.BRAIN_CORAL_FAN, Blocks.DEAD_BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_WALL_FAN, Blocks.DEAD_BRAIN_CORAL_WALL_FAN);
/* 3723 */     createCoral(Blocks.BUBBLE_CORAL, Blocks.DEAD_BUBBLE_CORAL, Blocks.BUBBLE_CORAL_BLOCK, Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.BUBBLE_CORAL_FAN, Blocks.DEAD_BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN, Blocks.DEAD_BUBBLE_CORAL_WALL_FAN);
/* 3724 */     createCoral(Blocks.FIRE_CORAL, Blocks.DEAD_FIRE_CORAL, Blocks.FIRE_CORAL_BLOCK, Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.FIRE_CORAL_FAN, Blocks.DEAD_FIRE_CORAL_FAN, Blocks.FIRE_CORAL_WALL_FAN, Blocks.DEAD_FIRE_CORAL_WALL_FAN);
/* 3725 */     createCoral(Blocks.HORN_CORAL, Blocks.DEAD_HORN_CORAL, Blocks.HORN_CORAL_BLOCK, Blocks.DEAD_HORN_CORAL_BLOCK, Blocks.HORN_CORAL_FAN, Blocks.DEAD_HORN_CORAL_FAN, Blocks.HORN_CORAL_WALL_FAN, Blocks.DEAD_HORN_CORAL_WALL_FAN);
/*      */     
/* 3727 */     createStems(Blocks.MELON_STEM, Blocks.ATTACHED_MELON_STEM);
/* 3728 */     createStems(Blocks.PUMPKIN_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
/*      */     
/* 3730 */     woodProvider(Blocks.MANGROVE_LOG).logWithHorizontal(Blocks.MANGROVE_LOG).wood(Blocks.MANGROVE_WOOD);
/* 3731 */     woodProvider(Blocks.STRIPPED_MANGROVE_LOG).logWithHorizontal(Blocks.STRIPPED_MANGROVE_LOG).wood(Blocks.STRIPPED_MANGROVE_WOOD);
/* 3732 */     createHangingSign(Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_HANGING_SIGN, Blocks.MANGROVE_WALL_HANGING_SIGN);
/* 3733 */     createTrivialBlock(Blocks.MANGROVE_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3735 */     woodProvider(Blocks.ACACIA_LOG).logWithHorizontal(Blocks.ACACIA_LOG).wood(Blocks.ACACIA_WOOD);
/* 3736 */     woodProvider(Blocks.STRIPPED_ACACIA_LOG).logWithHorizontal(Blocks.STRIPPED_ACACIA_LOG).wood(Blocks.STRIPPED_ACACIA_WOOD);
/* 3737 */     createHangingSign(Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_HANGING_SIGN, Blocks.ACACIA_WALL_HANGING_SIGN);
/* 3738 */     createPlant(Blocks.ACACIA_SAPLING, Blocks.POTTED_ACACIA_SAPLING, TintState.NOT_TINTED);
/* 3739 */     createTrivialBlock(Blocks.ACACIA_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3741 */     woodProvider(Blocks.CHERRY_LOG).logUVLocked(Blocks.CHERRY_LOG).wood(Blocks.CHERRY_WOOD);
/* 3742 */     woodProvider(Blocks.STRIPPED_CHERRY_LOG).logUVLocked(Blocks.STRIPPED_CHERRY_LOG).wood(Blocks.STRIPPED_CHERRY_WOOD);
/* 3743 */     createHangingSign(Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_HANGING_SIGN, Blocks.CHERRY_WALL_HANGING_SIGN);
/* 3744 */     createPlant(Blocks.CHERRY_SAPLING, Blocks.POTTED_CHERRY_SAPLING, TintState.NOT_TINTED);
/* 3745 */     createTrivialBlock(Blocks.CHERRY_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3747 */     woodProvider(Blocks.BIRCH_LOG).logWithHorizontal(Blocks.BIRCH_LOG).wood(Blocks.BIRCH_WOOD);
/* 3748 */     woodProvider(Blocks.STRIPPED_BIRCH_LOG).logWithHorizontal(Blocks.STRIPPED_BIRCH_LOG).wood(Blocks.STRIPPED_BIRCH_WOOD);
/* 3749 */     createHangingSign(Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_HANGING_SIGN, Blocks.BIRCH_WALL_HANGING_SIGN);
/* 3750 */     createPlant(Blocks.BIRCH_SAPLING, Blocks.POTTED_BIRCH_SAPLING, TintState.NOT_TINTED);
/* 3751 */     createTrivialBlock(Blocks.BIRCH_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3753 */     woodProvider(Blocks.OAK_LOG).logWithHorizontal(Blocks.OAK_LOG).wood(Blocks.OAK_WOOD);
/* 3754 */     woodProvider(Blocks.STRIPPED_OAK_LOG).logWithHorizontal(Blocks.STRIPPED_OAK_LOG).wood(Blocks.STRIPPED_OAK_WOOD);
/* 3755 */     createHangingSign(Blocks.STRIPPED_OAK_LOG, Blocks.OAK_HANGING_SIGN, Blocks.OAK_WALL_HANGING_SIGN);
/* 3756 */     createPlant(Blocks.OAK_SAPLING, Blocks.POTTED_OAK_SAPLING, TintState.NOT_TINTED);
/* 3757 */     createTrivialBlock(Blocks.OAK_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3759 */     woodProvider(Blocks.SPRUCE_LOG).logWithHorizontal(Blocks.SPRUCE_LOG).wood(Blocks.SPRUCE_WOOD);
/* 3760 */     woodProvider(Blocks.STRIPPED_SPRUCE_LOG).logWithHorizontal(Blocks.STRIPPED_SPRUCE_LOG).wood(Blocks.STRIPPED_SPRUCE_WOOD);
/* 3761 */     createHangingSign(Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_HANGING_SIGN, Blocks.SPRUCE_WALL_HANGING_SIGN);
/* 3762 */     createPlant(Blocks.SPRUCE_SAPLING, Blocks.POTTED_SPRUCE_SAPLING, TintState.NOT_TINTED);
/* 3763 */     createTrivialBlock(Blocks.SPRUCE_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3765 */     woodProvider(Blocks.DARK_OAK_LOG).logWithHorizontal(Blocks.DARK_OAK_LOG).wood(Blocks.DARK_OAK_WOOD);
/* 3766 */     woodProvider(Blocks.STRIPPED_DARK_OAK_LOG).logWithHorizontal(Blocks.STRIPPED_DARK_OAK_LOG).wood(Blocks.STRIPPED_DARK_OAK_WOOD);
/* 3767 */     createHangingSign(Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_HANGING_SIGN, Blocks.DARK_OAK_WALL_HANGING_SIGN);
/* 3768 */     createPlant(Blocks.DARK_OAK_SAPLING, Blocks.POTTED_DARK_OAK_SAPLING, TintState.NOT_TINTED);
/* 3769 */     createTrivialBlock(Blocks.DARK_OAK_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3771 */     woodProvider(Blocks.JUNGLE_LOG).logWithHorizontal(Blocks.JUNGLE_LOG).wood(Blocks.JUNGLE_WOOD);
/* 3772 */     woodProvider(Blocks.STRIPPED_JUNGLE_LOG).logWithHorizontal(Blocks.STRIPPED_JUNGLE_LOG).wood(Blocks.STRIPPED_JUNGLE_WOOD);
/* 3773 */     createHangingSign(Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_HANGING_SIGN, Blocks.JUNGLE_WALL_HANGING_SIGN);
/* 3774 */     createPlant(Blocks.JUNGLE_SAPLING, Blocks.POTTED_JUNGLE_SAPLING, TintState.NOT_TINTED);
/* 3775 */     createTrivialBlock(Blocks.JUNGLE_LEAVES, TexturedModel.LEAVES);
/*      */     
/* 3777 */     woodProvider(Blocks.CRIMSON_STEM).log(Blocks.CRIMSON_STEM).wood(Blocks.CRIMSON_HYPHAE);
/* 3778 */     woodProvider(Blocks.STRIPPED_CRIMSON_STEM).log(Blocks.STRIPPED_CRIMSON_STEM).wood(Blocks.STRIPPED_CRIMSON_HYPHAE);
/* 3779 */     createHangingSign(Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_HANGING_SIGN, Blocks.CRIMSON_WALL_HANGING_SIGN);
/* 3780 */     createPlant(Blocks.CRIMSON_FUNGUS, Blocks.POTTED_CRIMSON_FUNGUS, TintState.NOT_TINTED);
/* 3781 */     createNetherRoots(Blocks.CRIMSON_ROOTS, Blocks.POTTED_CRIMSON_ROOTS);
/*      */     
/* 3783 */     woodProvider(Blocks.WARPED_STEM).log(Blocks.WARPED_STEM).wood(Blocks.WARPED_HYPHAE);
/* 3784 */     woodProvider(Blocks.STRIPPED_WARPED_STEM).log(Blocks.STRIPPED_WARPED_STEM).wood(Blocks.STRIPPED_WARPED_HYPHAE);
/* 3785 */     createHangingSign(Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_HANGING_SIGN, Blocks.WARPED_WALL_HANGING_SIGN);
/* 3786 */     createPlant(Blocks.WARPED_FUNGUS, Blocks.POTTED_WARPED_FUNGUS, TintState.NOT_TINTED);
/* 3787 */     createNetherRoots(Blocks.WARPED_ROOTS, Blocks.POTTED_WARPED_ROOTS);
/*      */     
/* 3789 */     woodProvider(Blocks.BAMBOO_BLOCK).logUVLocked(Blocks.BAMBOO_BLOCK);
/* 3790 */     woodProvider(Blocks.STRIPPED_BAMBOO_BLOCK).logUVLocked(Blocks.STRIPPED_BAMBOO_BLOCK);
/* 3791 */     createHangingSign(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_HANGING_SIGN, Blocks.BAMBOO_WALL_HANGING_SIGN);
/*      */     
/* 3793 */     createCrossBlock(Blocks.NETHER_SPROUTS, TintState.NOT_TINTED);
/* 3794 */     createSimpleFlatItemModel(Items.NETHER_SPROUTS);
/*      */     
/* 3796 */     createDoor(Blocks.IRON_DOOR);
/* 3797 */     createTrapdoor(Blocks.IRON_TRAPDOOR);
/*      */     
/* 3799 */     createSmoothStoneSlab();
/*      */     
/* 3801 */     createPassiveRail(Blocks.RAIL);
/* 3802 */     createActiveRail(Blocks.POWERED_RAIL);
/* 3803 */     createActiveRail(Blocks.DETECTOR_RAIL);
/* 3804 */     createActiveRail(Blocks.ACTIVATOR_RAIL);
/*      */     
/* 3806 */     createComparator();
/*      */     
/* 3808 */     createCommandBlock(Blocks.COMMAND_BLOCK);
/* 3809 */     createCommandBlock(Blocks.REPEATING_COMMAND_BLOCK);
/* 3810 */     createCommandBlock(Blocks.CHAIN_COMMAND_BLOCK);
/*      */     
/* 3812 */     createAnvil(Blocks.ANVIL);
/* 3813 */     createAnvil(Blocks.CHIPPED_ANVIL);
/* 3814 */     createAnvil(Blocks.DAMAGED_ANVIL);
/*      */     
/* 3816 */     createBarrel();
/* 3817 */     createBell();
/*      */     
/* 3819 */     createFurnace(Blocks.FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
/* 3820 */     createFurnace(Blocks.BLAST_FURNACE, TexturedModel.ORIENTABLE_ONLY_TOP);
/* 3821 */     createFurnace(Blocks.SMOKER, TexturedModel.ORIENTABLE);
/*      */     
/* 3823 */     createRedstoneWire();
/*      */     
/* 3825 */     createRespawnAnchor();
/* 3826 */     createSculkCatalyst();
/*      */     
/* 3828 */     copyModel(Blocks.CHISELED_STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS);
/* 3829 */     copyModel(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE);
/* 3830 */     copyModel(Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS);
/* 3831 */     copyModel(Blocks.MOSSY_STONE_BRICKS, Blocks.INFESTED_MOSSY_STONE_BRICKS);
/* 3832 */     createInfestedStone();
/* 3833 */     copyModel(Blocks.STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS);
/* 3834 */     createInfestedDeepslate();
/*      */     
/* 3836 */     SpawnEggItem.eggs().forEach($$0 -> delegateItemModel((Item)$$0, ModelLocationUtils.decorateItemModelLocation("template_spawn_egg")));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createLightBlock() {
/* 3844 */     skipAutoItemBlock(Blocks.LIGHT);
/*      */     
/* 3846 */     PropertyDispatch.C1<Integer> $$0 = PropertyDispatch.property((Property)BlockStateProperties.LEVEL);
/* 3847 */     for (int $$1 = 0; $$1 < 16; $$1++) {
/* 3848 */       String $$2 = String.format(Locale.ROOT, "_%02d", new Object[] { Integer.valueOf($$1) });
/* 3849 */       ResourceLocation $$3 = TextureMapping.getItemTexture(Items.LIGHT, $$2);
/* 3850 */       $$0.select(Integer.valueOf($$1), Variant.variant().with(VariantProperties.MODEL, ModelTemplates.PARTICLE_ONLY.createWithSuffix(Blocks.LIGHT, $$2, TextureMapping.particle($$3), this.modelOutput)));
/* 3851 */       ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(Items.LIGHT, $$2), TextureMapping.layer0($$3), this.modelOutput);
/*      */     } 
/*      */     
/* 3854 */     this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(Blocks.LIGHT).with((PropertyDispatch)$$0));
/*      */   }
/*      */   
/*      */   private void createCandleAndCandleCake(Block $$0, Block $$1) {
/* 3858 */     createSimpleFlatItemModel($$0.asItem());
/*      */     
/* 3860 */     TextureMapping $$2 = TextureMapping.cube(TextureMapping.getBlockTexture($$0));
/* 3861 */     TextureMapping $$3 = TextureMapping.cube(TextureMapping.getBlockTexture($$0, "_lit"));
/*      */     
/* 3863 */     ResourceLocation $$4 = ModelTemplates.CANDLE.createWithSuffix($$0, "_one_candle", $$2, this.modelOutput);
/* 3864 */     ResourceLocation $$5 = ModelTemplates.TWO_CANDLES.createWithSuffix($$0, "_two_candles", $$2, this.modelOutput);
/* 3865 */     ResourceLocation $$6 = ModelTemplates.THREE_CANDLES.createWithSuffix($$0, "_three_candles", $$2, this.modelOutput);
/* 3866 */     ResourceLocation $$7 = ModelTemplates.FOUR_CANDLES.createWithSuffix($$0, "_four_candles", $$2, this.modelOutput);
/*      */     
/* 3868 */     ResourceLocation $$8 = ModelTemplates.CANDLE.createWithSuffix($$0, "_one_candle_lit", $$3, this.modelOutput);
/* 3869 */     ResourceLocation $$9 = ModelTemplates.TWO_CANDLES.createWithSuffix($$0, "_two_candles_lit", $$3, this.modelOutput);
/* 3870 */     ResourceLocation $$10 = ModelTemplates.THREE_CANDLES.createWithSuffix($$0, "_three_candles_lit", $$3, this.modelOutput);
/* 3871 */     ResourceLocation $$11 = ModelTemplates.FOUR_CANDLES.createWithSuffix($$0, "_four_candles_lit", $$3, this.modelOutput);
/*      */     
/* 3873 */     this.blockStateOutput.accept(
/* 3874 */         MultiVariantGenerator.multiVariant($$0)
/* 3875 */         .with(
/* 3876 */           (PropertyDispatch)PropertyDispatch.properties((Property)BlockStateProperties.CANDLES, (Property)BlockStateProperties.LIT)
/* 3877 */           .select(Integer.valueOf(1), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$4))
/* 3878 */           .select(Integer.valueOf(2), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$5))
/* 3879 */           .select(Integer.valueOf(3), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$6))
/* 3880 */           .select(Integer.valueOf(4), Boolean.valueOf(false), Variant.variant().with(VariantProperties.MODEL, $$7))
/* 3881 */           .select(Integer.valueOf(1), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$8))
/* 3882 */           .select(Integer.valueOf(2), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$9))
/* 3883 */           .select(Integer.valueOf(3), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$10))
/* 3884 */           .select(Integer.valueOf(4), Boolean.valueOf(true), Variant.variant().with(VariantProperties.MODEL, $$11))));
/*      */ 
/*      */ 
/*      */     
/* 3888 */     ResourceLocation $$12 = ModelTemplates.CANDLE_CAKE.create($$1, TextureMapping.candleCake($$0, false), this.modelOutput);
/* 3889 */     ResourceLocation $$13 = ModelTemplates.CANDLE_CAKE.createWithSuffix($$1, "_lit", TextureMapping.candleCake($$0, true), this.modelOutput);
/* 3890 */     this.blockStateOutput.accept(
/* 3891 */         MultiVariantGenerator.multiVariant($$1)
/* 3892 */         .with(createBooleanModelDispatch(BlockStateProperties.LIT, $$13, $$12)));
/*      */   }
/*      */   
/*      */   @FunctionalInterface
/*      */   private static interface BlockStateGeneratorSupplier {
/*      */     BlockStateGenerator create(Block param1Block, ResourceLocation param1ResourceLocation, TextureMapping param1TextureMapping, BiConsumer<ResourceLocation, Supplier<JsonElement>> param1BiConsumer);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\BlockModelGenerators.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */