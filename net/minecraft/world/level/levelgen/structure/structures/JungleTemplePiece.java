/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LeverBlock;
/*     */ import net.minecraft.world.level.block.RedStoneWireBlock;
/*     */ import net.minecraft.world.level.block.RepeaterBlock;
/*     */ import net.minecraft.world.level.block.StairBlock;
/*     */ import net.minecraft.world.level.block.TripWireBlock;
/*     */ import net.minecraft.world.level.block.TripWireHookBlock;
/*     */ import net.minecraft.world.level.block.VineBlock;
/*     */ import net.minecraft.world.level.block.piston.PistonBaseBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.AttachFace;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RedstoneSide;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ public class JungleTemplePiece extends ScatteredFeaturePiece {
/*     */   public static final int WIDTH = 12;
/*     */   public static final int DEPTH = 15;
/*     */   private boolean placedMainChest;
/*     */   
/*     */   public JungleTemplePiece(RandomSource $$0, int $$1, int $$2) {
/*  38 */     super(StructurePieceType.JUNGLE_PYRAMID_PIECE, $$1, 64, $$2, 12, 10, 15, getRandomHorizontalDirection($$0));
/*     */   }
/*     */   private boolean placedHiddenChest; private boolean placedTrap1; private boolean placedTrap2;
/*     */   public JungleTemplePiece(CompoundTag $$0) {
/*  42 */     super(StructurePieceType.JUNGLE_PYRAMID_PIECE, $$0);
/*  43 */     this.placedMainChest = $$0.getBoolean("placedMainChest");
/*  44 */     this.placedHiddenChest = $$0.getBoolean("placedHiddenChest");
/*  45 */     this.placedTrap1 = $$0.getBoolean("placedTrap1");
/*  46 */     this.placedTrap2 = $$0.getBoolean("placedTrap2");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  51 */     super.addAdditionalSaveData($$0, $$1);
/*  52 */     $$1.putBoolean("placedMainChest", this.placedMainChest);
/*  53 */     $$1.putBoolean("placedHiddenChest", this.placedHiddenChest);
/*  54 */     $$1.putBoolean("placedTrap1", this.placedTrap1);
/*  55 */     $$1.putBoolean("placedTrap2", this.placedTrap2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  60 */     if (!updateAverageGroundHeight((LevelAccessor)$$0, $$4, 0)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  65 */     generateBox($$0, $$4, 0, -4, 0, this.width - 1, 0, this.depth - 1, false, $$3, STONE_SELECTOR);
/*     */ 
/*     */     
/*  68 */     generateBox($$0, $$4, 2, 1, 2, 9, 2, 2, false, $$3, STONE_SELECTOR);
/*  69 */     generateBox($$0, $$4, 2, 1, 12, 9, 2, 12, false, $$3, STONE_SELECTOR);
/*  70 */     generateBox($$0, $$4, 2, 1, 3, 2, 2, 11, false, $$3, STONE_SELECTOR);
/*  71 */     generateBox($$0, $$4, 9, 1, 3, 9, 2, 11, false, $$3, STONE_SELECTOR);
/*     */ 
/*     */     
/*  74 */     generateBox($$0, $$4, 1, 3, 1, 10, 6, 1, false, $$3, STONE_SELECTOR);
/*  75 */     generateBox($$0, $$4, 1, 3, 13, 10, 6, 13, false, $$3, STONE_SELECTOR);
/*  76 */     generateBox($$0, $$4, 1, 3, 2, 1, 6, 12, false, $$3, STONE_SELECTOR);
/*  77 */     generateBox($$0, $$4, 10, 3, 2, 10, 6, 12, false, $$3, STONE_SELECTOR);
/*     */ 
/*     */     
/*  80 */     generateBox($$0, $$4, 2, 3, 2, 9, 3, 12, false, $$3, STONE_SELECTOR);
/*  81 */     generateBox($$0, $$4, 2, 6, 2, 9, 6, 12, false, $$3, STONE_SELECTOR);
/*  82 */     generateBox($$0, $$4, 3, 7, 3, 8, 7, 11, false, $$3, STONE_SELECTOR);
/*  83 */     generateBox($$0, $$4, 4, 8, 4, 7, 8, 10, false, $$3, STONE_SELECTOR);
/*     */ 
/*     */     
/*  86 */     generateAirBox($$0, $$4, 3, 1, 3, 8, 2, 11);
/*  87 */     generateAirBox($$0, $$4, 4, 3, 6, 7, 3, 9);
/*  88 */     generateAirBox($$0, $$4, 2, 4, 2, 9, 5, 12);
/*  89 */     generateAirBox($$0, $$4, 4, 6, 5, 7, 6, 9);
/*  90 */     generateAirBox($$0, $$4, 5, 7, 6, 6, 7, 8);
/*     */ 
/*     */     
/*  93 */     generateAirBox($$0, $$4, 5, 1, 2, 6, 2, 2);
/*  94 */     generateAirBox($$0, $$4, 5, 2, 12, 6, 2, 12);
/*  95 */     generateAirBox($$0, $$4, 5, 5, 1, 6, 5, 1);
/*  96 */     generateAirBox($$0, $$4, 5, 5, 13, 6, 5, 13);
/*  97 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 1, 5, 5, $$4);
/*  98 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, 5, 5, $$4);
/*  99 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 1, 5, 9, $$4);
/* 100 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, 5, 9, $$4);
/*     */ 
/*     */     
/* 103 */     for (int $$7 = 0; $$7 <= 14; $$7 += 14) {
/* 104 */       generateBox($$0, $$4, 2, 4, $$7, 2, 5, $$7, false, $$3, STONE_SELECTOR);
/* 105 */       generateBox($$0, $$4, 4, 4, $$7, 4, 5, $$7, false, $$3, STONE_SELECTOR);
/* 106 */       generateBox($$0, $$4, 7, 4, $$7, 7, 5, $$7, false, $$3, STONE_SELECTOR);
/* 107 */       generateBox($$0, $$4, 9, 4, $$7, 9, 5, $$7, false, $$3, STONE_SELECTOR);
/*     */     } 
/* 109 */     generateBox($$0, $$4, 5, 6, 0, 6, 6, 0, false, $$3, STONE_SELECTOR);
/* 110 */     for (int $$8 = 0; $$8 <= 11; $$8 += 11) {
/* 111 */       for (int $$9 = 2; $$9 <= 12; $$9 += 2) {
/* 112 */         generateBox($$0, $$4, $$8, 4, $$9, $$8, 5, $$9, false, $$3, STONE_SELECTOR);
/*     */       }
/* 114 */       generateBox($$0, $$4, $$8, 6, 5, $$8, 6, 5, false, $$3, STONE_SELECTOR);
/* 115 */       generateBox($$0, $$4, $$8, 6, 9, $$8, 6, 9, false, $$3, STONE_SELECTOR);
/*     */     } 
/* 117 */     generateBox($$0, $$4, 2, 7, 2, 2, 9, 2, false, $$3, STONE_SELECTOR);
/* 118 */     generateBox($$0, $$4, 9, 7, 2, 9, 9, 2, false, $$3, STONE_SELECTOR);
/* 119 */     generateBox($$0, $$4, 2, 7, 12, 2, 9, 12, false, $$3, STONE_SELECTOR);
/* 120 */     generateBox($$0, $$4, 9, 7, 12, 9, 9, 12, false, $$3, STONE_SELECTOR);
/* 121 */     generateBox($$0, $$4, 4, 9, 4, 4, 9, 4, false, $$3, STONE_SELECTOR);
/* 122 */     generateBox($$0, $$4, 7, 9, 4, 7, 9, 4, false, $$3, STONE_SELECTOR);
/* 123 */     generateBox($$0, $$4, 4, 9, 10, 4, 9, 10, false, $$3, STONE_SELECTOR);
/* 124 */     generateBox($$0, $$4, 7, 9, 10, 7, 9, 10, false, $$3, STONE_SELECTOR);
/* 125 */     generateBox($$0, $$4, 5, 9, 7, 6, 9, 7, false, $$3, STONE_SELECTOR);
/*     */     
/* 127 */     BlockState $$10 = (BlockState)Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.EAST);
/* 128 */     BlockState $$11 = (BlockState)Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.WEST);
/* 129 */     BlockState $$12 = (BlockState)Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.SOUTH);
/* 130 */     BlockState $$13 = (BlockState)Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.NORTH);
/*     */     
/* 132 */     placeBlock($$0, $$13, 5, 9, 6, $$4);
/* 133 */     placeBlock($$0, $$13, 6, 9, 6, $$4);
/* 134 */     placeBlock($$0, $$12, 5, 9, 8, $$4);
/* 135 */     placeBlock($$0, $$12, 6, 9, 8, $$4);
/*     */ 
/*     */     
/* 138 */     placeBlock($$0, $$13, 4, 0, 0, $$4);
/* 139 */     placeBlock($$0, $$13, 5, 0, 0, $$4);
/* 140 */     placeBlock($$0, $$13, 6, 0, 0, $$4);
/* 141 */     placeBlock($$0, $$13, 7, 0, 0, $$4);
/*     */ 
/*     */     
/* 144 */     placeBlock($$0, $$13, 4, 1, 8, $$4);
/* 145 */     placeBlock($$0, $$13, 4, 2, 9, $$4);
/* 146 */     placeBlock($$0, $$13, 4, 3, 10, $$4);
/* 147 */     placeBlock($$0, $$13, 7, 1, 8, $$4);
/* 148 */     placeBlock($$0, $$13, 7, 2, 9, $$4);
/* 149 */     placeBlock($$0, $$13, 7, 3, 10, $$4);
/* 150 */     generateBox($$0, $$4, 4, 1, 9, 4, 1, 9, false, $$3, STONE_SELECTOR);
/* 151 */     generateBox($$0, $$4, 7, 1, 9, 7, 1, 9, false, $$3, STONE_SELECTOR);
/* 152 */     generateBox($$0, $$4, 4, 1, 10, 7, 2, 10, false, $$3, STONE_SELECTOR);
/*     */ 
/*     */     
/* 155 */     generateBox($$0, $$4, 5, 4, 5, 6, 4, 5, false, $$3, STONE_SELECTOR);
/* 156 */     placeBlock($$0, $$10, 4, 4, 5, $$4);
/* 157 */     placeBlock($$0, $$11, 7, 4, 5, $$4);
/*     */ 
/*     */     
/* 160 */     for (int $$14 = 0; $$14 < 4; $$14++) {
/* 161 */       placeBlock($$0, $$12, 5, 0 - $$14, 6 + $$14, $$4);
/* 162 */       placeBlock($$0, $$12, 6, 0 - $$14, 6 + $$14, $$4);
/* 163 */       generateAirBox($$0, $$4, 5, 0 - $$14, 7 + $$14, 6, 0 - $$14, 9 + $$14);
/*     */     } 
/*     */ 
/*     */     
/* 167 */     generateAirBox($$0, $$4, 1, -3, 12, 10, -1, 13);
/* 168 */     generateAirBox($$0, $$4, 1, -3, 1, 3, -1, 13);
/* 169 */     generateAirBox($$0, $$4, 1, -3, 1, 9, -1, 5);
/* 170 */     for (int $$15 = 1; $$15 <= 13; $$15 += 2) {
/* 171 */       generateBox($$0, $$4, 1, -3, $$15, 1, -2, $$15, false, $$3, STONE_SELECTOR);
/*     */     }
/* 173 */     for (int $$16 = 2; $$16 <= 12; $$16 += 2) {
/* 174 */       generateBox($$0, $$4, 1, -1, $$16, 3, -1, $$16, false, $$3, STONE_SELECTOR);
/*     */     }
/* 176 */     generateBox($$0, $$4, 2, -2, 1, 5, -2, 1, false, $$3, STONE_SELECTOR);
/* 177 */     generateBox($$0, $$4, 7, -2, 1, 9, -2, 1, false, $$3, STONE_SELECTOR);
/* 178 */     generateBox($$0, $$4, 6, -3, 1, 6, -3, 1, false, $$3, STONE_SELECTOR);
/* 179 */     generateBox($$0, $$4, 6, -1, 1, 6, -1, 1, false, $$3, STONE_SELECTOR);
/*     */ 
/*     */     
/* 182 */     placeBlock($$0, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue((Property)TripWireHookBlock.FACING, (Comparable)Direction.EAST)).setValue((Property)TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 1, -3, 8, $$4);
/* 183 */     placeBlock($$0, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue((Property)TripWireHookBlock.FACING, (Comparable)Direction.WEST)).setValue((Property)TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 4, -3, 8, $$4);
/* 184 */     placeBlock($$0, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.defaultBlockState().setValue((Property)TripWireBlock.EAST, Boolean.valueOf(true))).setValue((Property)TripWireBlock.WEST, Boolean.valueOf(true))).setValue((Property)TripWireBlock.ATTACHED, Boolean.valueOf(true)), 2, -3, 8, $$4);
/* 185 */     placeBlock($$0, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.defaultBlockState().setValue((Property)TripWireBlock.EAST, Boolean.valueOf(true))).setValue((Property)TripWireBlock.WEST, Boolean.valueOf(true))).setValue((Property)TripWireBlock.ATTACHED, Boolean.valueOf(true)), 3, -3, 8, $$4);
/* 186 */     BlockState $$17 = (BlockState)((BlockState)Blocks.REDSTONE_WIRE.defaultBlockState().setValue((Property)RedStoneWireBlock.NORTH, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.SOUTH, (Comparable)RedstoneSide.SIDE);
/* 187 */     placeBlock($$0, $$17, 5, -3, 7, $$4);
/* 188 */     placeBlock($$0, $$17, 5, -3, 6, $$4);
/* 189 */     placeBlock($$0, $$17, 5, -3, 5, $$4);
/* 190 */     placeBlock($$0, $$17, 5, -3, 4, $$4);
/* 191 */     placeBlock($$0, $$17, 5, -3, 3, $$4);
/* 192 */     placeBlock($$0, $$17, 5, -3, 2, $$4);
/* 193 */     placeBlock($$0, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.defaultBlockState().setValue((Property)RedStoneWireBlock.NORTH, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.WEST, (Comparable)RedstoneSide.SIDE), 5, -3, 1, $$4);
/* 194 */     placeBlock($$0, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.defaultBlockState().setValue((Property)RedStoneWireBlock.EAST, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.WEST, (Comparable)RedstoneSide.SIDE), 4, -3, 1, $$4);
/* 195 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3, -3, 1, $$4);
/* 196 */     if (!this.placedTrap1) {
/* 197 */       this.placedTrap1 = createDispenser($$0, $$4, $$3, 3, -2, 1, Direction.NORTH, BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER);
/*     */     }
/* 199 */     placeBlock($$0, (BlockState)Blocks.VINE.defaultBlockState().setValue((Property)VineBlock.SOUTH, Boolean.valueOf(true)), 3, -2, 2, $$4);
/*     */ 
/*     */     
/* 202 */     placeBlock($$0, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue((Property)TripWireHookBlock.FACING, (Comparable)Direction.NORTH)).setValue((Property)TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 1, $$4);
/* 203 */     placeBlock($$0, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue((Property)TripWireHookBlock.FACING, (Comparable)Direction.SOUTH)).setValue((Property)TripWireHookBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 5, $$4);
/* 204 */     placeBlock($$0, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.defaultBlockState().setValue((Property)TripWireBlock.NORTH, Boolean.valueOf(true))).setValue((Property)TripWireBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)TripWireBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 2, $$4);
/* 205 */     placeBlock($$0, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.defaultBlockState().setValue((Property)TripWireBlock.NORTH, Boolean.valueOf(true))).setValue((Property)TripWireBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)TripWireBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 3, $$4);
/* 206 */     placeBlock($$0, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.defaultBlockState().setValue((Property)TripWireBlock.NORTH, Boolean.valueOf(true))).setValue((Property)TripWireBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)TripWireBlock.ATTACHED, Boolean.valueOf(true)), 7, -3, 4, $$4);
/* 207 */     placeBlock($$0, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.defaultBlockState().setValue((Property)RedStoneWireBlock.EAST, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.WEST, (Comparable)RedstoneSide.SIDE), 8, -3, 6, $$4);
/* 208 */     placeBlock($$0, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.defaultBlockState().setValue((Property)RedStoneWireBlock.WEST, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.SOUTH, (Comparable)RedstoneSide.SIDE), 9, -3, 6, $$4);
/* 209 */     placeBlock($$0, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.defaultBlockState().setValue((Property)RedStoneWireBlock.NORTH, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.SOUTH, (Comparable)RedstoneSide.UP), 9, -3, 5, $$4);
/* 210 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 4, $$4);
/* 211 */     placeBlock($$0, $$17, 9, -2, 4, $$4);
/* 212 */     if (!this.placedTrap2) {
/* 213 */       this.placedTrap2 = createDispenser($$0, $$4, $$3, 9, -2, 3, Direction.WEST, BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER);
/*     */     }
/* 215 */     placeBlock($$0, (BlockState)Blocks.VINE.defaultBlockState().setValue((Property)VineBlock.EAST, Boolean.valueOf(true)), 8, -1, 3, $$4);
/* 216 */     placeBlock($$0, (BlockState)Blocks.VINE.defaultBlockState().setValue((Property)VineBlock.EAST, Boolean.valueOf(true)), 8, -2, 3, $$4);
/* 217 */     if (!this.placedMainChest) {
/* 218 */       this.placedMainChest = createChest($$0, $$4, $$3, 8, -3, 3, BuiltInLootTables.JUNGLE_TEMPLE);
/*     */     }
/* 220 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 2, $$4);
/* 221 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 1, $$4);
/* 222 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 4, -3, 5, $$4);
/* 223 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -2, 5, $$4);
/* 224 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -1, 5, $$4);
/* 225 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 6, -3, 5, $$4);
/* 226 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -2, 5, $$4);
/* 227 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -1, 5, $$4);
/* 228 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 5, $$4);
/* 229 */     generateBox($$0, $$4, 9, -1, 1, 9, -1, 5, false, $$3, STONE_SELECTOR);
/*     */ 
/*     */     
/* 232 */     generateAirBox($$0, $$4, 8, -3, 8, 10, -1, 10);
/* 233 */     placeBlock($$0, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 8, -2, 11, $$4);
/* 234 */     placeBlock($$0, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 9, -2, 11, $$4);
/* 235 */     placeBlock($$0, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 10, -2, 11, $$4);
/* 236 */     BlockState $$18 = (BlockState)((BlockState)Blocks.LEVER.defaultBlockState().setValue((Property)LeverBlock.FACING, (Comparable)Direction.NORTH)).setValue((Property)LeverBlock.FACE, (Comparable)AttachFace.WALL);
/* 237 */     placeBlock($$0, $$18, 8, -2, 12, $$4);
/* 238 */     placeBlock($$0, $$18, 9, -2, 12, $$4);
/* 239 */     placeBlock($$0, $$18, 10, -2, 12, $$4);
/* 240 */     generateBox($$0, $$4, 8, -3, 8, 8, -3, 10, false, $$3, STONE_SELECTOR);
/* 241 */     generateBox($$0, $$4, 10, -3, 8, 10, -3, 10, false, $$3, STONE_SELECTOR);
/* 242 */     placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 10, -2, 9, $$4);
/* 243 */     placeBlock($$0, $$17, 8, -2, 9, $$4);
/* 244 */     placeBlock($$0, $$17, 8, -2, 10, $$4);
/* 245 */     placeBlock($$0, (BlockState)((BlockState)((BlockState)((BlockState)Blocks.REDSTONE_WIRE.defaultBlockState().setValue((Property)RedStoneWireBlock.NORTH, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.SOUTH, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.EAST, (Comparable)RedstoneSide.SIDE)).setValue((Property)RedStoneWireBlock.WEST, (Comparable)RedstoneSide.SIDE), 10, -1, 9, $$4);
/* 246 */     placeBlock($$0, (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue((Property)PistonBaseBlock.FACING, (Comparable)Direction.UP), 9, -2, 8, $$4);
/* 247 */     placeBlock($$0, (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue((Property)PistonBaseBlock.FACING, (Comparable)Direction.WEST), 10, -2, 8, $$4);
/* 248 */     placeBlock($$0, (BlockState)Blocks.STICKY_PISTON.defaultBlockState().setValue((Property)PistonBaseBlock.FACING, (Comparable)Direction.WEST), 10, -1, 8, $$4);
/* 249 */     placeBlock($$0, (BlockState)Blocks.REPEATER.defaultBlockState().setValue((Property)RepeaterBlock.FACING, (Comparable)Direction.NORTH), 10, -2, 10, $$4);
/* 250 */     if (!this.placedHiddenChest)
/* 251 */       this.placedHiddenChest = createChest($$0, $$4, $$3, 9, -3, 10, BuiltInLootTables.JUNGLE_TEMPLE); 
/*     */   }
/*     */   
/*     */   private static class MossStoneSelector
/*     */     extends StructurePiece.BlockSelector
/*     */   {
/*     */     public void next(RandomSource $$0, int $$1, int $$2, int $$3, boolean $$4) {
/* 258 */       if ($$0.nextFloat() < 0.4F) {
/* 259 */         this.next = Blocks.COBBLESTONE.defaultBlockState();
/*     */       } else {
/* 261 */         this.next = Blocks.MOSSY_COBBLESTONE.defaultBlockState();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 266 */   private static final MossStoneSelector STONE_SELECTOR = new MossStoneSelector();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\JungleTemplePiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */