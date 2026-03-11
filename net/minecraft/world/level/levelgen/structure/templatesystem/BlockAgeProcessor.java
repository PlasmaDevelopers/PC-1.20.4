/*     */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*     */ import com.mojang.serialization.Codec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.StairBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Half;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class BlockAgeProcessor extends StructureProcessor {
/*     */   public static final Codec<BlockAgeProcessor> CODEC;
/*     */   
/*     */   static {
/*  19 */     CODEC = Codec.FLOAT.fieldOf("mossiness").xmap(BlockAgeProcessor::new, $$0 -> Float.valueOf($$0.mossiness)).codec();
/*     */   }
/*     */   private static final float PROBABILITY_OF_REPLACING_FULL_BLOCK = 0.5F;
/*     */   private static final float PROBABILITY_OF_REPLACING_STAIRS = 0.5F;
/*     */   private static final float PROBABILITY_OF_REPLACING_OBSIDIAN = 0.15F;
/*  24 */   private static final BlockState[] NON_MOSSY_REPLACEMENTS = new BlockState[] { Blocks.STONE_SLAB
/*  25 */       .defaultBlockState(), Blocks.STONE_BRICK_SLAB
/*  26 */       .defaultBlockState() };
/*     */   
/*     */   private final float mossiness;
/*     */ 
/*     */   
/*     */   public BlockAgeProcessor(float $$0) {
/*  32 */     this.mossiness = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/*  38 */     RandomSource $$6 = $$5.getRandom($$4.pos());
/*     */     
/*  40 */     BlockState $$7 = $$4.state();
/*  41 */     BlockPos $$8 = $$4.pos();
/*  42 */     BlockState $$9 = null;
/*  43 */     if ($$7.is(Blocks.STONE_BRICKS) || $$7.is(Blocks.STONE) || $$7.is(Blocks.CHISELED_STONE_BRICKS)) {
/*  44 */       $$9 = maybeReplaceFullStoneBlock($$6);
/*  45 */     } else if ($$7.is(BlockTags.STAIRS)) {
/*  46 */       $$9 = maybeReplaceStairs($$6, $$4.state());
/*  47 */     } else if ($$7.is(BlockTags.SLABS)) {
/*  48 */       $$9 = maybeReplaceSlab($$6);
/*  49 */     } else if ($$7.is(BlockTags.WALLS)) {
/*  50 */       $$9 = maybeReplaceWall($$6);
/*  51 */     } else if ($$7.is(Blocks.OBSIDIAN)) {
/*  52 */       $$9 = maybeReplaceObsidian($$6);
/*     */     } 
/*  54 */     if ($$9 != null) {
/*  55 */       return new StructureTemplate.StructureBlockInfo($$8, $$9, $$4.nbt());
/*     */     }
/*  57 */     return $$4;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockState maybeReplaceFullStoneBlock(RandomSource $$0) {
/*  62 */     if ($$0.nextFloat() >= 0.5F) {
/*  63 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  67 */     BlockState[] $$1 = { Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), getRandomFacingStairs($$0, Blocks.STONE_BRICK_STAIRS) };
/*     */ 
/*     */ 
/*     */     
/*  71 */     BlockState[] $$2 = { Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), getRandomFacingStairs($$0, Blocks.MOSSY_STONE_BRICK_STAIRS) };
/*     */ 
/*     */     
/*  74 */     return getRandomBlock($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockState maybeReplaceStairs(RandomSource $$0, BlockState $$1) {
/*  79 */     Direction $$2 = (Direction)$$1.getValue((Property)StairBlock.FACING);
/*  80 */     Half $$3 = (Half)$$1.getValue((Property)StairBlock.HALF);
/*     */     
/*  82 */     if ($$0.nextFloat() >= 0.5F) {
/*  83 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  88 */     BlockState[] $$4 = { (BlockState)((BlockState)Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)$$2)).setValue((Property)StairBlock.HALF, (Comparable)$$3), Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState() };
/*     */ 
/*     */     
/*  91 */     return getRandomBlock($$0, NON_MOSSY_REPLACEMENTS, $$4);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockState maybeReplaceSlab(RandomSource $$0) {
/*  96 */     if ($$0.nextFloat() < this.mossiness) {
/*  97 */       return Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState();
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockState maybeReplaceWall(RandomSource $$0) {
/* 104 */     if ($$0.nextFloat() < this.mossiness) {
/* 105 */       return Blocks.MOSSY_STONE_BRICK_WALL.defaultBlockState();
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockState maybeReplaceObsidian(RandomSource $$0) {
/* 112 */     if ($$0.nextFloat() < 0.15F) {
/* 113 */       return Blocks.CRYING_OBSIDIAN.defaultBlockState();
/*     */     }
/* 115 */     return null;
/*     */   }
/*     */   
/*     */   private static BlockState getRandomFacingStairs(RandomSource $$0, Block $$1) {
/* 119 */     return (BlockState)((BlockState)$$1.defaultBlockState()
/* 120 */       .setValue((Property)StairBlock.FACING, (Comparable)Direction.Plane.HORIZONTAL.getRandomDirection($$0)))
/* 121 */       .setValue((Property)StairBlock.HALF, (Comparable)Util.getRandom((Object[])Half.values(), $$0));
/*     */   }
/*     */   
/*     */   private BlockState getRandomBlock(RandomSource $$0, BlockState[] $$1, BlockState[] $$2) {
/* 125 */     if ($$0.nextFloat() < this.mossiness) {
/* 126 */       return getRandomBlock($$0, $$2);
/*     */     }
/* 128 */     return getRandomBlock($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockState getRandomBlock(RandomSource $$0, BlockState[] $$1) {
/* 133 */     return $$1[$$0.nextInt($$1.length)];
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureProcessorType<?> getType() {
/* 138 */     return StructureProcessorType.BLOCK_AGE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\BlockAgeProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */