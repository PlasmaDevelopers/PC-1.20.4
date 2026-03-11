/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LadderBlock;
/*     */ import net.minecraft.world.level.block.WallTorchBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoomCrossing
/*     */   extends StrongholdPieces.StrongholdPiece
/*     */ {
/*     */   protected static final int WIDTH = 11;
/*     */   protected static final int HEIGHT = 7;
/*     */   protected static final int DEPTH = 11;
/*     */   protected final int type;
/*     */   
/*     */   public RoomCrossing(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/* 863 */     super(StructurePieceType.STRONGHOLD_ROOM_CROSSING, $$0, $$2);
/*     */     
/* 865 */     setOrientation($$3);
/* 866 */     this.entryDoor = randomSmallDoor($$1);
/* 867 */     this.type = $$1.nextInt(5);
/*     */   }
/*     */   
/*     */   public RoomCrossing(CompoundTag $$0) {
/* 871 */     super(StructurePieceType.STRONGHOLD_ROOM_CROSSING, $$0);
/* 872 */     this.type = $$0.getInt("Type");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 877 */     super.addAdditionalSaveData($$0, $$1);
/* 878 */     $$1.putInt("Type", this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 883 */     generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 4, 1);
/* 884 */     generateSmallDoorChildLeft((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 4);
/* 885 */     generateSmallDoorChildRight((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 4);
/*     */   }
/*     */   
/*     */   public static RoomCrossing createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 889 */     BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -4, -1, 0, 11, 7, 11, $$5);
/*     */     
/* 891 */     if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 892 */       return null;
/*     */     }
/*     */     
/* 895 */     return new RoomCrossing($$6, $$1, $$7, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*     */     int $$7;
/* 901 */     generateBox($$0, $$4, 0, 0, 0, 10, 6, 10, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*     */     
/* 903 */     generateSmallDoor($$0, $$3, $$4, this.entryDoor, 4, 1, 0);
/*     */     
/* 905 */     generateBox($$0, $$4, 4, 1, 10, 6, 3, 10, CAVE_AIR, CAVE_AIR, false);
/* 906 */     generateBox($$0, $$4, 0, 1, 4, 0, 3, 6, CAVE_AIR, CAVE_AIR, false);
/* 907 */     generateBox($$0, $$4, 10, 1, 4, 10, 3, 6, CAVE_AIR, CAVE_AIR, false);
/*     */     
/* 909 */     switch (this.type) {
/*     */       default:
/*     */         return;
/*     */       
/*     */       case 0:
/* 914 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 1, 5, $$4);
/* 915 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 2, 5, $$4);
/* 916 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 3, 5, $$4);
/* 917 */         placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.WEST), 4, 3, 5, $$4);
/* 918 */         placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.EAST), 6, 3, 5, $$4);
/* 919 */         placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.SOUTH), 5, 3, 4, $$4);
/* 920 */         placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.NORTH), 5, 3, 6, $$4);
/* 921 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 4, $$4);
/* 922 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 5, $$4);
/* 923 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 6, $$4);
/* 924 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 4, $$4);
/* 925 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 5, $$4);
/* 926 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 6, $$4);
/* 927 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 5, 1, 4, $$4);
/* 928 */         placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 5, 1, 6, $$4);
/*     */       
/*     */       case 1:
/* 931 */         for ($$7 = 0; $$7 < 5; $$7++) {
/* 932 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 1, 3 + $$7, $$4);
/* 933 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 7, 1, 3 + $$7, $$4);
/* 934 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3 + $$7, 1, 3, $$4);
/* 935 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3 + $$7, 1, 7, $$4);
/*     */         } 
/* 937 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 1, 5, $$4);
/* 938 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 2, 5, $$4);
/* 939 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 3, 5, $$4);
/* 940 */         placeBlock($$0, Blocks.WATER.defaultBlockState(), 5, 4, 5, $$4);
/*     */       case 2:
/*     */         break;
/* 943 */     }  for (int $$8 = 1; $$8 <= 9; $$8++) {
/* 944 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 1, 3, $$8, $$4);
/* 945 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 9, 3, $$8, $$4);
/*     */     } 
/* 947 */     for (int $$9 = 1; $$9 <= 9; $$9++) {
/* 948 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), $$9, 3, 1, $$4);
/* 949 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), $$9, 3, 9, $$4);
/*     */     } 
/* 951 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 1, 4, $$4);
/* 952 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 1, 6, $$4);
/* 953 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 3, 4, $$4);
/* 954 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 3, 6, $$4);
/* 955 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, 1, 5, $$4);
/* 956 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, 1, 5, $$4);
/* 957 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, 3, 5, $$4);
/* 958 */     placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, 3, 5, $$4);
/* 959 */     for (int $$10 = 1; $$10 <= 3; $$10++) {
/* 960 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, $$10, 4, $$4);
/* 961 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, $$10, 4, $$4);
/* 962 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, $$10, 6, $$4);
/* 963 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, $$10, 6, $$4);
/*     */     } 
/* 965 */     placeBlock($$0, Blocks.WALL_TORCH.defaultBlockState(), 5, 3, 5, $$4);
/* 966 */     for (int $$11 = 2; $$11 <= 8; $$11++) {
/* 967 */       placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 2, 3, $$11, $$4);
/* 968 */       placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 3, 3, $$11, $$4);
/* 969 */       if ($$11 <= 3 || $$11 >= 7) {
/* 970 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 4, 3, $$11, $$4);
/* 971 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 5, 3, $$11, $$4);
/* 972 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 6, 3, $$11, $$4);
/*     */       } 
/* 974 */       placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 7, 3, $$11, $$4);
/* 975 */       placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 8, 3, $$11, $$4);
/*     */     } 
/* 977 */     BlockState $$12 = (BlockState)Blocks.LADDER.defaultBlockState().setValue((Property)LadderBlock.FACING, (Comparable)Direction.WEST);
/* 978 */     placeBlock($$0, $$12, 9, 1, 3, $$4);
/* 979 */     placeBlock($$0, $$12, 9, 2, 3, $$4);
/* 980 */     placeBlock($$0, $$12, 9, 3, 3, $$4);
/*     */     
/* 982 */     createChest($$0, $$4, $$3, 3, 4, 8, BuiltInLootTables.STRONGHOLD_CROSSING);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\StrongholdPieces$RoomCrossing.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */