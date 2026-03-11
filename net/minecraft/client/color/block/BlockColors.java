/*     */ package net.minecraft.client.color.block;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.BiomeColors;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.IdMapper;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.FoliageColor;
/*     */ import net.minecraft.world.level.GrassColor;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.DoublePlantBlock;
/*     */ import net.minecraft.world.level.block.RedStoneWireBlock;
/*     */ import net.minecraft.world.level.block.StemBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.MapColor;
/*     */ 
/*     */ public class BlockColors
/*     */ {
/*     */   private static final int DEFAULT = -1;
/*  30 */   private final IdMapper<BlockColor> blockColors = new IdMapper(32);
/*  31 */   private final Map<Block, Set<Property<?>>> coloringStates = Maps.newHashMap();
/*     */   
/*     */   public static BlockColors createDefault() {
/*  34 */     BlockColors $$0 = new BlockColors();
/*     */     
/*  36 */     $$0.register(($$0, $$1, $$2, $$3) -> 
/*  37 */         ($$1 == null || $$2 == null) ? GrassColor.getDefaultColor() : BiomeColors.getAverageGrassColor($$1, ($$0.getValue((Property)DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) ? $$2.below() : $$2), new Block[] { Blocks.LARGE_FERN, Blocks.TALL_GRASS });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  42 */     $$0.addColoringState((Property<?>)DoublePlantBlock.HALF, new Block[] { Blocks.LARGE_FERN, Blocks.TALL_GRASS });
/*     */     
/*  44 */     $$0.register(($$0, $$1, $$2, $$3) -> 
/*  45 */         ($$1 == null || $$2 == null) ? GrassColor.getDefaultColor() : BiomeColors.getAverageGrassColor($$1, $$2), new Block[] { Blocks.GRASS_BLOCK, Blocks.FERN, Blocks.SHORT_GRASS, Blocks.POTTED_FERN });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  51 */     $$0.register(($$0, $$1, $$2, $$3) -> ($$3 != 0) ? (
/*     */         
/*  53 */         ($$1 == null || $$2 == null) ? GrassColor.getDefaultColor() : BiomeColors.getAverageGrassColor($$1, $$2)) : -1, new Block[] { Blocks.PINK_PETALS });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     $$0.register(($$0, $$1, $$2, $$3) -> FoliageColor.getEvergreenColor(), new Block[] { Blocks.SPRUCE_LEAVES });
/*  63 */     $$0.register(($$0, $$1, $$2, $$3) -> FoliageColor.getBirchColor(), new Block[] { Blocks.BIRCH_LEAVES });
/*     */     
/*  65 */     $$0.register(($$0, $$1, $$2, $$3) -> 
/*  66 */         ($$1 == null || $$2 == null) ? FoliageColor.getDefaultColor() : BiomeColors.getAverageFoliageColor($$1, $$2), new Block[] { Blocks.OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.VINE, Blocks.MANGROVE_LEAVES });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     $$0.register(($$0, $$1, $$2, $$3) -> 
/*  73 */         ($$1 == null || $$2 == null) ? -1 : BiomeColors.getAverageWaterColor($$1, $$2), new Block[] { Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.WATER_CAULDRON });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     $$0.register(($$0, $$1, $$2, $$3) -> RedStoneWireBlock.getColorForPower(((Integer)$$0.getValue((Property)RedStoneWireBlock.POWER)).intValue()), new Block[] { Blocks.REDSTONE_WIRE });
/*  80 */     $$0.addColoringState((Property<?>)RedStoneWireBlock.POWER, new Block[] { Blocks.REDSTONE_WIRE });
/*     */     
/*  82 */     $$0.register(($$0, $$1, $$2, $$3) -> 
/*  83 */         ($$1 == null || $$2 == null) ? -1 : BiomeColors.getAverageGrassColor($$1, $$2), new Block[] { Blocks.SUGAR_CANE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     $$0.register(($$0, $$1, $$2, $$3) -> 14731036, new Block[] { Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM });
/*     */     
/*  91 */     $$0.register(($$0, $$1, $$2, $$3) -> { int $$4 = ((Integer)$$0.getValue((Property)StemBlock.AGE)).intValue(); int $$5 = $$4 * 32; int $$6 = 255 - $$4 * 8; int $$7 = $$4 * 4; return $$5 << 16 | $$6 << 8 | $$7; }new Block[] { Blocks.MELON_STEM, Blocks.PUMPKIN_STEM });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     $$0.addColoringState((Property<?>)StemBlock.AGE, new Block[] { Blocks.MELON_STEM, Blocks.PUMPKIN_STEM });
/*     */     
/* 100 */     $$0.register(($$0, $$1, $$2, $$3) -> 
/* 101 */         ($$1 == null || $$2 == null) ? 7455580 : 2129968, new Block[] { Blocks.LILY_PAD });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     return $$0;
/*     */   }
/*     */   
/*     */   public int getColor(BlockState $$0, Level $$1, BlockPos $$2) {
/* 111 */     BlockColor $$3 = (BlockColor)this.blockColors.byId(BuiltInRegistries.BLOCK.getId($$0.getBlock()));
/*     */     
/* 113 */     if ($$3 != null) {
/* 114 */       return $$3.getColor($$0, null, null, 0);
/*     */     }
/*     */     
/* 117 */     MapColor $$4 = $$0.getMapColor((BlockGetter)$$1, $$2);
/* 118 */     return ($$4 != null) ? $$4.col : -1;
/*     */   }
/*     */   
/*     */   public int getColor(BlockState $$0, @Nullable BlockAndTintGetter $$1, @Nullable BlockPos $$2, int $$3) {
/* 122 */     BlockColor $$4 = (BlockColor)this.blockColors.byId(BuiltInRegistries.BLOCK.getId($$0.getBlock()));
/* 123 */     return ($$4 == null) ? -1 : $$4.getColor($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void register(BlockColor $$0, Block... $$1) {
/* 127 */     for (Block $$2 : $$1) {
/* 128 */       this.blockColors.addMapping($$0, BuiltInRegistries.BLOCK.getId($$2));
/*     */     }
/*     */   }
/*     */   
/*     */   private void addColoringStates(Set<Property<?>> $$0, Block... $$1) {
/* 133 */     for (Block $$2 : $$1) {
/* 134 */       this.coloringStates.put($$2, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addColoringState(Property<?> $$0, Block... $$1) {
/* 139 */     addColoringStates((Set<Property<?>>)ImmutableSet.of($$0), $$1);
/*     */   }
/*     */   
/*     */   public Set<Property<?>> getColoringProperties(Block $$0) {
/* 143 */     return (Set<Property<?>>)this.coloringStates.getOrDefault($$0, ImmutableSet.of());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\color\block\BlockColors.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */