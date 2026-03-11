/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.StairBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class DesertPyramidPiece extends ScatteredFeaturePiece {
/*     */   public static final int WIDTH = 21;
/*  27 */   private final boolean[] hasPlacedChest = new boolean[4]; public static final int DEPTH = 21;
/*  28 */   private final List<BlockPos> potentialSuspiciousSandWorldPositions = new ArrayList<>();
/*  29 */   private BlockPos randomCollapsedRoofPos = BlockPos.ZERO;
/*     */   
/*     */   public DesertPyramidPiece(RandomSource $$0, int $$1, int $$2) {
/*  32 */     super(StructurePieceType.DESERT_PYRAMID_PIECE, $$1, 64, $$2, 21, 15, 21, getRandomHorizontalDirection($$0));
/*     */   }
/*     */   
/*     */   public DesertPyramidPiece(CompoundTag $$0) {
/*  36 */     super(StructurePieceType.DESERT_PYRAMID_PIECE, $$0);
/*  37 */     this.hasPlacedChest[0] = $$0.getBoolean("hasPlacedChest0");
/*  38 */     this.hasPlacedChest[1] = $$0.getBoolean("hasPlacedChest1");
/*  39 */     this.hasPlacedChest[2] = $$0.getBoolean("hasPlacedChest2");
/*  40 */     this.hasPlacedChest[3] = $$0.getBoolean("hasPlacedChest3");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  45 */     super.addAdditionalSaveData($$0, $$1);
/*  46 */     $$1.putBoolean("hasPlacedChest0", this.hasPlacedChest[0]);
/*  47 */     $$1.putBoolean("hasPlacedChest1", this.hasPlacedChest[1]);
/*  48 */     $$1.putBoolean("hasPlacedChest2", this.hasPlacedChest[2]);
/*  49 */     $$1.putBoolean("hasPlacedChest3", this.hasPlacedChest[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  54 */     if (!updateHeightPositionToLowestGroundHeight((LevelAccessor)$$0, -$$3.nextInt(3))) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  59 */     generateBox($$0, $$4, 0, -4, 0, this.width - 1, 0, this.depth - 1, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/*  60 */     for (int $$7 = 1; $$7 <= 9; $$7++) {
/*  61 */       generateBox($$0, $$4, $$7, $$7, $$7, this.width - 1 - $$7, $$7, this.depth - 1 - $$7, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/*  62 */       generateBox($$0, $$4, $$7 + 1, $$7, $$7 + 1, this.width - 2 - $$7, $$7, this.depth - 2 - $$7, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*     */     } 
/*  64 */     for (int $$8 = 0; $$8 < this.width; $$8++) {
/*  65 */       for (int $$9 = 0; $$9 < this.depth; $$9++) {
/*  66 */         int $$10 = -5;
/*  67 */         fillColumnDown($$0, Blocks.SANDSTONE.defaultBlockState(), $$8, -5, $$9, $$4);
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     BlockState $$11 = (BlockState)Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.NORTH);
/*  72 */     BlockState $$12 = (BlockState)Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.SOUTH);
/*  73 */     BlockState $$13 = (BlockState)Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.EAST);
/*  74 */     BlockState $$14 = (BlockState)Blocks.SANDSTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.WEST);
/*     */ 
/*     */     
/*  77 */     generateBox($$0, $$4, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*  78 */     generateBox($$0, $$4, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/*  79 */     placeBlock($$0, $$11, 2, 10, 0, $$4);
/*  80 */     placeBlock($$0, $$12, 2, 10, 4, $$4);
/*  81 */     placeBlock($$0, $$13, 0, 10, 2, $$4);
/*  82 */     placeBlock($$0, $$14, 4, 10, 2, $$4);
/*  83 */     generateBox($$0, $$4, this.width - 5, 0, 0, this.width - 1, 9, 4, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*  84 */     generateBox($$0, $$4, this.width - 4, 10, 1, this.width - 2, 10, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/*  85 */     placeBlock($$0, $$11, this.width - 3, 10, 0, $$4);
/*  86 */     placeBlock($$0, $$12, this.width - 3, 10, 4, $$4);
/*  87 */     placeBlock($$0, $$13, this.width - 5, 10, 2, $$4);
/*  88 */     placeBlock($$0, $$14, this.width - 1, 10, 2, $$4);
/*     */ 
/*     */     
/*  91 */     generateBox($$0, $$4, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*  92 */     generateBox($$0, $$4, 9, 1, 0, 11, 3, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*  93 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 9, 1, 1, $$4);
/*  94 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 9, 2, 1, $$4);
/*  95 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 9, 3, 1, $$4);
/*  96 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 10, 3, 1, $$4);
/*  97 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 11, 3, 1, $$4);
/*  98 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 11, 2, 1, $$4);
/*  99 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 11, 1, 1, $$4);
/*     */ 
/*     */     
/* 102 */     generateBox($$0, $$4, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 103 */     generateBox($$0, $$4, 4, 1, 2, 8, 2, 2, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 104 */     generateBox($$0, $$4, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 105 */     generateBox($$0, $$4, 12, 1, 2, 16, 2, 2, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*     */ 
/*     */     
/* 108 */     generateBox($$0, $$4, 5, 4, 5, this.width - 6, 4, this.depth - 6, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 109 */     generateBox($$0, $$4, 9, 4, 9, 11, 4, 11, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 110 */     generateBox($$0, $$4, 8, 1, 8, 8, 3, 8, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 111 */     generateBox($$0, $$4, 12, 1, 8, 12, 3, 8, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 112 */     generateBox($$0, $$4, 8, 1, 12, 8, 3, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 113 */     generateBox($$0, $$4, 12, 1, 12, 12, 3, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/*     */ 
/*     */     
/* 116 */     generateBox($$0, $$4, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 117 */     generateBox($$0, $$4, this.width - 5, 1, 5, this.width - 2, 4, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 118 */     generateBox($$0, $$4, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 119 */     generateBox($$0, $$4, this.width - 7, 7, 9, this.width - 7, 7, 11, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 120 */     generateBox($$0, $$4, 5, 5, 9, 5, 7, 11, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 121 */     generateBox($$0, $$4, this.width - 6, 5, 9, this.width - 6, 7, 11, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 122 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 5, 5, 10, $$4);
/* 123 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 5, 6, 10, $$4);
/* 124 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 6, 6, 10, $$4);
/* 125 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), this.width - 6, 5, 10, $$4);
/* 126 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), this.width - 6, 6, 10, $$4);
/* 127 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), this.width - 7, 6, 10, $$4);
/*     */ 
/*     */     
/* 130 */     generateBox($$0, $$4, 2, 4, 4, 2, 6, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 131 */     generateBox($$0, $$4, this.width - 3, 4, 4, this.width - 3, 6, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 132 */     placeBlock($$0, $$11, 2, 4, 5, $$4);
/* 133 */     placeBlock($$0, $$11, 2, 3, 4, $$4);
/* 134 */     placeBlock($$0, $$11, this.width - 3, 4, 5, $$4);
/* 135 */     placeBlock($$0, $$11, this.width - 3, 3, 4, $$4);
/* 136 */     generateBox($$0, $$4, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 137 */     generateBox($$0, $$4, this.width - 3, 1, 3, this.width - 2, 2, 3, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 138 */     placeBlock($$0, Blocks.SANDSTONE.defaultBlockState(), 1, 1, 2, $$4);
/* 139 */     placeBlock($$0, Blocks.SANDSTONE.defaultBlockState(), this.width - 2, 1, 2, $$4);
/* 140 */     placeBlock($$0, Blocks.SANDSTONE_SLAB.defaultBlockState(), 1, 2, 2, $$4);
/* 141 */     placeBlock($$0, Blocks.SANDSTONE_SLAB.defaultBlockState(), this.width - 2, 2, 2, $$4);
/* 142 */     placeBlock($$0, $$14, 2, 1, 2, $$4);
/* 143 */     placeBlock($$0, $$13, this.width - 3, 1, 2, $$4);
/*     */ 
/*     */     
/* 146 */     generateBox($$0, $$4, 4, 3, 5, 4, 3, 17, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 147 */     generateBox($$0, $$4, this.width - 5, 3, 5, this.width - 5, 3, 17, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 148 */     generateBox($$0, $$4, 3, 1, 5, 4, 2, 16, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 149 */     generateBox($$0, $$4, this.width - 6, 1, 5, this.width - 5, 2, 16, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 150 */     for (int $$15 = 5; $$15 <= 17; $$15 += 2) {
/* 151 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 4, 1, $$15, $$4);
/* 152 */       placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 4, 2, $$15, $$4);
/* 153 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), this.width - 5, 1, $$15, $$4);
/* 154 */       placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), this.width - 5, 2, $$15, $$4);
/*     */     } 
/* 156 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 7, $$4);
/* 157 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 8, $$4);
/* 158 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 0, 9, $$4);
/* 159 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 0, 9, $$4);
/* 160 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 8, 0, 10, $$4);
/* 161 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 12, 0, 10, $$4);
/* 162 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 7, 0, 10, $$4);
/* 163 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 13, 0, 10, $$4);
/* 164 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 0, 11, $$4);
/* 165 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 0, 11, $$4);
/* 166 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 12, $$4);
/* 167 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 10, 0, 13, $$4);
/* 168 */     placeBlock($$0, Blocks.BLUE_TERRACOTTA.defaultBlockState(), 10, 0, 10, $$4);
/*     */     
/*     */     int $$16;
/* 171 */     for ($$16 = 0; $$16 <= this.width - 1; $$16 += this.width - 1) {
/* 172 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 2, 1, $$4);
/* 173 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 2, 2, $$4);
/* 174 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 2, 3, $$4);
/* 175 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 3, 1, $$4);
/* 176 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 3, 2, $$4);
/* 177 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 3, 3, $$4);
/* 178 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 4, 1, $$4);
/* 179 */       placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), $$16, 4, 2, $$4);
/* 180 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 4, 3, $$4);
/* 181 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 5, 1, $$4);
/* 182 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 5, 2, $$4);
/* 183 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 5, 3, $$4);
/* 184 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 6, 1, $$4);
/* 185 */       placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), $$16, 6, 2, $$4);
/* 186 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 6, 3, $$4);
/* 187 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 7, 1, $$4);
/* 188 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 7, 2, $$4);
/* 189 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$16, 7, 3, $$4);
/* 190 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 8, 1, $$4);
/* 191 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 8, 2, $$4);
/* 192 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$16, 8, 3, $$4);
/*     */     }  int $$17;
/* 194 */     for ($$17 = 2; $$17 <= this.width - 3; $$17 += this.width - 3 - 2) {
/* 195 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 - 1, 2, 0, $$4);
/* 196 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17, 2, 0, $$4);
/* 197 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 + 1, 2, 0, $$4);
/* 198 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 - 1, 3, 0, $$4);
/* 199 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17, 3, 0, $$4);
/* 200 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 + 1, 3, 0, $$4);
/* 201 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17 - 1, 4, 0, $$4);
/* 202 */       placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), $$17, 4, 0, $$4);
/* 203 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17 + 1, 4, 0, $$4);
/* 204 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 - 1, 5, 0, $$4);
/* 205 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17, 5, 0, $$4);
/* 206 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 + 1, 5, 0, $$4);
/* 207 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17 - 1, 6, 0, $$4);
/* 208 */       placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), $$17, 6, 0, $$4);
/* 209 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17 + 1, 6, 0, $$4);
/* 210 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17 - 1, 7, 0, $$4);
/* 211 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17, 7, 0, $$4);
/* 212 */       placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), $$17 + 1, 7, 0, $$4);
/* 213 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 - 1, 8, 0, $$4);
/* 214 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17, 8, 0, $$4);
/* 215 */       placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), $$17 + 1, 8, 0, $$4);
/*     */     } 
/* 217 */     generateBox($$0, $$4, 8, 4, 0, 12, 6, 0, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 218 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 8, 6, 0, $$4);
/* 219 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 12, 6, 0, $$4);
/* 220 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 9, 5, 0, $$4);
/* 221 */     placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 10, 5, 0, $$4);
/* 222 */     placeBlock($$0, Blocks.ORANGE_TERRACOTTA.defaultBlockState(), 11, 5, 0, $$4);
/*     */ 
/*     */     
/* 225 */     generateBox($$0, $$4, 8, -14, 8, 12, -11, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 226 */     generateBox($$0, $$4, 8, -10, 8, 12, -10, 12, Blocks.CHISELED_SANDSTONE.defaultBlockState(), Blocks.CHISELED_SANDSTONE.defaultBlockState(), false);
/* 227 */     generateBox($$0, $$4, 8, -9, 8, 12, -9, 12, Blocks.CUT_SANDSTONE.defaultBlockState(), Blocks.CUT_SANDSTONE.defaultBlockState(), false);
/* 228 */     generateBox($$0, $$4, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), false);
/* 229 */     generateBox($$0, $$4, 9, -11, 9, 11, -1, 11, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 230 */     placeBlock($$0, Blocks.STONE_PRESSURE_PLATE.defaultBlockState(), 10, -11, 10, $$4);
/* 231 */     generateBox($$0, $$4, 9, -13, 9, 11, -13, 11, Blocks.TNT.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 232 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 8, -11, 10, $$4);
/* 233 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 8, -10, 10, $$4);
/* 234 */     placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 7, -10, 10, $$4);
/* 235 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 7, -11, 10, $$4);
/* 236 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 12, -11, 10, $$4);
/* 237 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 12, -10, 10, $$4);
/* 238 */     placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 13, -10, 10, $$4);
/* 239 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 13, -11, 10, $$4);
/* 240 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, -11, 8, $$4);
/* 241 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, -10, 8, $$4);
/* 242 */     placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 10, -10, 7, $$4);
/* 243 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 10, -11, 7, $$4);
/* 244 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, -11, 12, $$4);
/* 245 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, -10, 12, $$4);
/* 246 */     placeBlock($$0, Blocks.CHISELED_SANDSTONE.defaultBlockState(), 10, -10, 13, $$4);
/* 247 */     placeBlock($$0, Blocks.CUT_SANDSTONE.defaultBlockState(), 10, -11, 13, $$4);
/*     */ 
/*     */     
/* 250 */     for (Direction $$18 : Direction.Plane.HORIZONTAL) {
/* 251 */       if (!this.hasPlacedChest[$$18.get2DDataValue()]) {
/* 252 */         int $$19 = $$18.getStepX() * 2;
/* 253 */         int $$20 = $$18.getStepZ() * 2;
/* 254 */         this.hasPlacedChest[$$18.get2DDataValue()] = createChest($$0, $$4, $$3, 10 + $$19, -11, 10 + $$20, BuiltInLootTables.DESERT_PYRAMID);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 259 */     addCellar($$0, $$4);
/*     */   }
/*     */   
/*     */   private void addCellar(WorldGenLevel $$0, BoundingBox $$1) {
/* 263 */     BlockPos $$2 = new BlockPos(16, -4, 13);
/*     */     
/* 265 */     addCellarStairs($$2, $$0, $$1);
/* 266 */     addCellarRoom($$2, $$0, $$1);
/*     */   }
/*     */   
/*     */   private void addCellarStairs(BlockPos $$0, WorldGenLevel $$1, BoundingBox $$2) {
/* 270 */     int $$3 = $$0.getX();
/* 271 */     int $$4 = $$0.getY();
/* 272 */     int $$5 = $$0.getZ();
/*     */ 
/*     */     
/* 275 */     BlockState $$6 = Blocks.SANDSTONE_STAIRS.defaultBlockState();
/* 276 */     placeBlock($$1, $$6.rotate(Rotation.COUNTERCLOCKWISE_90), 13, -1, 17, $$2);
/* 277 */     placeBlock($$1, $$6.rotate(Rotation.COUNTERCLOCKWISE_90), 14, -2, 17, $$2);
/* 278 */     placeBlock($$1, $$6.rotate(Rotation.COUNTERCLOCKWISE_90), 15, -3, 17, $$2);
/*     */     
/* 280 */     BlockState $$7 = Blocks.SAND.defaultBlockState();
/* 281 */     BlockState $$8 = Blocks.SANDSTONE.defaultBlockState();
/* 282 */     boolean $$9 = $$1.getRandom().nextBoolean();
/* 283 */     placeBlock($$1, $$7, $$3 - 4, $$4 + 4, $$5 + 4, $$2);
/* 284 */     placeBlock($$1, $$7, $$3 - 3, $$4 + 4, $$5 + 4, $$2);
/* 285 */     placeBlock($$1, $$7, $$3 - 2, $$4 + 4, $$5 + 4, $$2);
/* 286 */     placeBlock($$1, $$7, $$3 - 1, $$4 + 4, $$5 + 4, $$2);
/* 287 */     placeBlock($$1, $$7, $$3, $$4 + 4, $$5 + 4, $$2);
/*     */ 
/*     */     
/* 290 */     placeBlock($$1, $$7, $$3 - 2, $$4 + 3, $$5 + 4, $$2);
/* 291 */     placeBlock($$1, $$9 ? $$7 : $$8, $$3 - 1, $$4 + 3, $$5 + 4, $$2);
/* 292 */     placeBlock($$1, !$$9 ? $$7 : $$8, $$3, $$4 + 3, $$5 + 4, $$2);
/*     */     
/* 294 */     placeBlock($$1, $$7, $$3 - 1, $$4 + 2, $$5 + 4, $$2);
/* 295 */     placeBlock($$1, $$8, $$3, $$4 + 2, $$5 + 4, $$2);
/* 296 */     placeBlock($$1, $$7, $$3, $$4 + 1, $$5 + 4, $$2);
/*     */   }
/*     */   
/*     */   private void addCellarRoom(BlockPos $$0, WorldGenLevel $$1, BoundingBox $$2) {
/* 300 */     int $$3 = $$0.getX();
/* 301 */     int $$4 = $$0.getY();
/* 302 */     int $$5 = $$0.getZ();
/*     */ 
/*     */     
/* 305 */     BlockState $$6 = Blocks.CUT_SANDSTONE.defaultBlockState();
/* 306 */     BlockState $$7 = Blocks.CHISELED_SANDSTONE.defaultBlockState();
/* 307 */     generateBox($$1, $$2, $$3 - 3, $$4 + 1, $$5 - 3, $$3 - 3, $$4 + 1, $$5 + 2, $$6, $$6, true);
/* 308 */     generateBox($$1, $$2, $$3 + 3, $$4 + 1, $$5 - 3, $$3 + 3, $$4 + 1, $$5 + 2, $$6, $$6, true);
/* 309 */     generateBox($$1, $$2, $$3 - 3, $$4 + 1, $$5 - 3, $$3 + 3, $$4 + 1, $$5 - 2, $$6, $$6, true);
/* 310 */     generateBox($$1, $$2, $$3 - 3, $$4 + 1, $$5 + 3, $$3 + 3, $$4 + 1, $$5 + 3, $$6, $$6, true);
/*     */     
/* 312 */     generateBox($$1, $$2, $$3 - 3, $$4 + 2, $$5 - 3, $$3 - 3, $$4 + 2, $$5 + 2, $$7, $$7, true);
/* 313 */     generateBox($$1, $$2, $$3 + 3, $$4 + 2, $$5 - 3, $$3 + 3, $$4 + 2, $$5 + 2, $$7, $$7, true);
/* 314 */     generateBox($$1, $$2, $$3 - 3, $$4 + 2, $$5 - 3, $$3 + 3, $$4 + 2, $$5 - 2, $$7, $$7, true);
/* 315 */     generateBox($$1, $$2, $$3 - 3, $$4 + 2, $$5 + 3, $$3 + 3, $$4 + 2, $$5 + 3, $$7, $$7, true);
/*     */     
/* 317 */     generateBox($$1, $$2, $$3 - 3, -1, $$5 - 3, $$3 - 3, -1, $$5 + 2, $$6, $$6, true);
/* 318 */     generateBox($$1, $$2, $$3 + 3, -1, $$5 - 3, $$3 + 3, -1, $$5 + 2, $$6, $$6, true);
/* 319 */     generateBox($$1, $$2, $$3 - 3, -1, $$5 - 3, $$3 + 3, -1, $$5 - 2, $$6, $$6, true);
/* 320 */     generateBox($$1, $$2, $$3 - 3, -1, $$5 + 3, $$3 + 3, -1, $$5 + 3, $$6, $$6, true);
/*     */     
/* 322 */     placeSandBox($$3 - 2, $$4 + 1, $$5 - 2, $$3 + 2, $$4 + 3, $$5 + 2);
/* 323 */     placeCollapsedRoof($$1, $$2, $$3 - 2, $$4 + 4, $$5 - 2, $$3 + 2, $$5 + 2);
/*     */     
/* 325 */     BlockState $$8 = Blocks.ORANGE_TERRACOTTA.defaultBlockState();
/* 326 */     BlockState $$9 = Blocks.BLUE_TERRACOTTA.defaultBlockState();
/*     */ 
/*     */     
/* 329 */     placeBlock($$1, $$9, $$3, $$4, $$5, $$2);
/*     */     
/* 331 */     placeBlock($$1, $$8, $$3 + 1, $$4, $$5 - 1, $$2);
/* 332 */     placeBlock($$1, $$8, $$3 + 1, $$4, $$5 + 1, $$2);
/* 333 */     placeBlock($$1, $$8, $$3 - 1, $$4, $$5 - 1, $$2);
/* 334 */     placeBlock($$1, $$8, $$3 - 1, $$4, $$5 + 1, $$2);
/*     */     
/* 336 */     placeBlock($$1, $$8, $$3 + 2, $$4, $$5, $$2);
/* 337 */     placeBlock($$1, $$8, $$3 - 2, $$4, $$5, $$2);
/* 338 */     placeBlock($$1, $$8, $$3, $$4, $$5 + 2, $$2);
/* 339 */     placeBlock($$1, $$8, $$3, $$4, $$5 - 2, $$2);
/*     */ 
/*     */     
/* 342 */     placeBlock($$1, $$8, $$3 + 3, $$4, $$5, $$2);
/* 343 */     placeSand($$3 + 3, $$4 + 1, $$5);
/* 344 */     placeSand($$3 + 3, $$4 + 2, $$5);
/* 345 */     placeBlock($$1, $$6, $$3 + 4, $$4 + 1, $$5, $$2);
/* 346 */     placeBlock($$1, $$7, $$3 + 4, $$4 + 2, $$5, $$2);
/*     */     
/* 348 */     placeBlock($$1, $$8, $$3 - 3, $$4, $$5, $$2);
/* 349 */     placeSand($$3 - 3, $$4 + 1, $$5);
/* 350 */     placeSand($$3 - 3, $$4 + 2, $$5);
/* 351 */     placeBlock($$1, $$6, $$3 - 4, $$4 + 1, $$5, $$2);
/* 352 */     placeBlock($$1, $$7, $$3 - 4, $$4 + 2, $$5, $$2);
/*     */     
/* 354 */     placeBlock($$1, $$8, $$3, $$4, $$5 + 3, $$2);
/* 355 */     placeSand($$3, $$4 + 1, $$5 + 3);
/* 356 */     placeSand($$3, $$4 + 2, $$5 + 3);
/*     */     
/* 358 */     placeBlock($$1, $$8, $$3, $$4, $$5 - 3, $$2);
/* 359 */     placeSand($$3, $$4 + 1, $$5 - 3);
/* 360 */     placeSand($$3, $$4 + 2, $$5 - 3);
/* 361 */     placeBlock($$1, $$6, $$3, $$4 + 1, $$5 - 4, $$2);
/* 362 */     placeBlock($$1, $$7, $$3, -2, $$5 - 4, $$2);
/*     */   }
/*     */   
/*     */   private void placeSand(int $$0, int $$1, int $$2) {
/* 366 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$0, $$1, $$2);
/* 367 */     this.potentialSuspiciousSandWorldPositions.add(mutableBlockPos);
/*     */   }
/*     */   
/*     */   private void placeSandBox(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 371 */     for (int $$6 = $$1; $$6 <= $$4; $$6++) {
/* 372 */       for (int $$7 = $$0; $$7 <= $$3; $$7++) {
/* 373 */         for (int $$8 = $$2; $$8 <= $$5; $$8++) {
/* 374 */           placeSand($$7, $$6, $$8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeCollapsedRoofPiece(WorldGenLevel $$0, int $$1, int $$2, int $$3, BoundingBox $$4) {
/* 382 */     if ($$0.getRandom().nextFloat() < 0.33F) {
/* 383 */       BlockState $$5 = Blocks.SANDSTONE.defaultBlockState();
/* 384 */       placeBlock($$0, $$5, $$1, $$2, $$3, $$4);
/*     */     } else {
/* 386 */       BlockState $$6 = Blocks.SAND.defaultBlockState();
/* 387 */       placeBlock($$0, $$6, $$1, $$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeCollapsedRoof(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 392 */     for (int $$7 = $$2; $$7 <= $$5; $$7++) {
/* 393 */       for (int $$8 = $$4; $$8 <= $$6; $$8++) {
/* 394 */         placeCollapsedRoofPiece($$0, $$7, $$3, $$8, $$1);
/*     */       }
/*     */     } 
/*     */     
/* 398 */     RandomSource $$9 = RandomSource.create($$0.getSeed()).forkPositional().at((BlockPos)getWorldPos($$2, $$3, $$4));
/* 399 */     int $$10 = $$9.nextIntBetweenInclusive($$2, $$5);
/* 400 */     int $$11 = $$9.nextIntBetweenInclusive($$4, $$6);
/* 401 */     this.randomCollapsedRoofPos = new BlockPos(getWorldX($$10, $$11), getWorldY($$3), getWorldZ($$10, $$11));
/*     */   }
/*     */   
/*     */   public List<BlockPos> getPotentialSuspiciousSandWorldPositions() {
/* 405 */     return this.potentialSuspiciousSandWorldPositions;
/*     */   }
/*     */   
/*     */   public BlockPos getRandomCollapsedRoofPos() {
/* 409 */     return this.randomCollapsedRoofPos;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\DesertPyramidPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */