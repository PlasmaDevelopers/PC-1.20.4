/*      */ package net.minecraft.world.level.levelgen.structure.structures;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.StructureManager;
/*      */ import net.minecraft.world.level.WorldGenLevel;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.DoorBlock;
/*      */ import net.minecraft.world.level.block.EndPortalFrameBlock;
/*      */ import net.minecraft.world.level.block.FenceBlock;
/*      */ import net.minecraft.world.level.block.IronBarsBlock;
/*      */ import net.minecraft.world.level.block.LadderBlock;
/*      */ import net.minecraft.world.level.block.SlabBlock;
/*      */ import net.minecraft.world.level.block.StairBlock;
/*      */ import net.minecraft.world.level.block.WallTorchBlock;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.block.state.properties.SlabType;
/*      */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*      */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*      */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*      */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*      */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*      */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*      */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StrongholdPieces
/*      */ {
/*      */   private static final int SMALL_DOOR_WIDTH = 3;
/*      */   private static final int SMALL_DOOR_HEIGHT = 3;
/*      */   private static final int MAX_DEPTH = 50;
/*      */   private static final int LOWEST_Y_POSITION = 10;
/*      */   private static final boolean CHECK_AIR = true;
/*      */   public static final int MAGIC_START_Y = 64;
/*      */   
/*      */   private static class PieceWeight
/*      */   {
/*      */     public final Class<? extends StrongholdPieces.StrongholdPiece> pieceClass;
/*      */     public final int weight;
/*      */     public int placeCount;
/*      */     public final int maxPlaceCount;
/*      */     
/*      */     public PieceWeight(Class<? extends StrongholdPieces.StrongholdPiece> $$0, int $$1, int $$2) {
/*   60 */       this.pieceClass = $$0;
/*   61 */       this.weight = $$1;
/*   62 */       this.maxPlaceCount = $$2;
/*      */     }
/*      */     
/*      */     public boolean doPlace(int $$0) {
/*   66 */       return (this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount);
/*      */     }
/*      */     
/*      */     public boolean isValid() {
/*   70 */       return (this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount);
/*      */     }
/*      */   }
/*      */   
/*   74 */   private static final PieceWeight[] STRONGHOLD_PIECE_WEIGHTS = new PieceWeight[] { new PieceWeight((Class)Straight.class, 40, 0), new PieceWeight((Class)PrisonHall.class, 5, 5), new PieceWeight((Class)LeftTurn.class, 20, 0), new PieceWeight((Class)RightTurn.class, 20, 0), new PieceWeight((Class)RoomCrossing.class, 10, 6), new PieceWeight((Class)StraightStairsDown.class, 5, 5), new PieceWeight((Class)StairsDown.class, 5, 5), new PieceWeight((Class)FiveCrossing.class, 5, 4), new PieceWeight((Class)ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean doPlace(int $$0)
/*      */         {
/*   87 */           return (super.doPlace($$0) && $$0 > 4);
/*      */         }
/*      */       }, 
/*      */       new PieceWeight(PortalRoom.class, 20, 1)
/*      */       {
/*      */         public boolean doPlace(int $$0) {
/*   93 */           return (super.doPlace($$0) && $$0 > 5);
/*      */         }
/*      */       } };
/*      */   
/*      */   private static List<PieceWeight> currentPieces;
/*      */   static Class<? extends StrongholdPiece> imposedPiece;
/*      */   private static int totalWeight;
/*      */   
/*      */   public static void resetPieces() {
/*  102 */     currentPieces = Lists.newArrayList();
/*  103 */     for (PieceWeight $$0 : STRONGHOLD_PIECE_WEIGHTS) {
/*  104 */       $$0.placeCount = 0;
/*  105 */       currentPieces.add($$0);
/*      */     } 
/*  107 */     imposedPiece = null;
/*      */   }
/*      */   
/*      */   private static boolean updatePieceWeight() {
/*  111 */     boolean $$0 = false;
/*  112 */     totalWeight = 0;
/*  113 */     for (PieceWeight $$1 : currentPieces) {
/*  114 */       if ($$1.maxPlaceCount > 0 && $$1.placeCount < $$1.maxPlaceCount) {
/*  115 */         $$0 = true;
/*      */       }
/*  117 */       totalWeight += $$1.weight;
/*      */     } 
/*  119 */     return $$0;
/*      */   }
/*      */   
/*      */   private static StrongholdPiece findAndCreatePieceFactory(Class<? extends StrongholdPiece> $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, int $$5, @Nullable Direction $$6, int $$7) {
/*  123 */     StrongholdPiece $$8 = null;
/*      */     
/*  125 */     if ($$0 == Straight.class) {
/*  126 */       $$8 = Straight.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  127 */     } else if ($$0 == PrisonHall.class) {
/*  128 */       $$8 = PrisonHall.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  129 */     } else if ($$0 == LeftTurn.class) {
/*  130 */       $$8 = LeftTurn.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  131 */     } else if ($$0 == RightTurn.class) {
/*  132 */       $$8 = RightTurn.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  133 */     } else if ($$0 == RoomCrossing.class) {
/*  134 */       $$8 = RoomCrossing.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  135 */     } else if ($$0 == StraightStairsDown.class) {
/*  136 */       $$8 = StraightStairsDown.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  137 */     } else if ($$0 == StairsDown.class) {
/*  138 */       $$8 = StairsDown.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  139 */     } else if ($$0 == FiveCrossing.class) {
/*  140 */       $$8 = FiveCrossing.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  141 */     } else if ($$0 == ChestCorridor.class) {
/*  142 */       $$8 = ChestCorridor.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  143 */     } else if ($$0 == Library.class) {
/*  144 */       $$8 = Library.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  145 */     } else if ($$0 == PortalRoom.class) {
/*  146 */       $$8 = PortalRoom.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*      */     } 
/*      */     
/*  149 */     return $$8;
/*      */   }
/*      */   
/*      */   private static StrongholdPiece generatePieceFromSmallDoor(StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, int $$5, Direction $$6, int $$7) {
/*  153 */     if (!updatePieceWeight()) {
/*  154 */       return null;
/*      */     }
/*      */     
/*  157 */     if (imposedPiece != null) {
/*  158 */       StrongholdPiece $$8 = findAndCreatePieceFactory(imposedPiece, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  159 */       imposedPiece = null;
/*      */       
/*  161 */       if ($$8 != null) {
/*  162 */         return $$8;
/*      */       }
/*      */     } 
/*      */     
/*  166 */     int $$9 = 0;
/*  167 */     while ($$9 < 5) {
/*  168 */       $$9++;
/*      */       
/*  170 */       int $$10 = $$2.nextInt(totalWeight);
/*  171 */       for (PieceWeight $$11 : currentPieces) {
/*  172 */         $$10 -= $$11.weight;
/*  173 */         if ($$10 < 0) {
/*  174 */           if (!$$11.doPlace($$7) || $$11 == $$0.previousPiece) {
/*      */             break;
/*      */           }
/*      */           
/*  178 */           StrongholdPiece $$12 = findAndCreatePieceFactory($$11.pieceClass, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  179 */           if ($$12 != null) {
/*  180 */             $$11.placeCount++;
/*  181 */             $$0.previousPiece = $$11;
/*      */             
/*  183 */             if (!$$11.isValid()) {
/*  184 */               currentPieces.remove($$11);
/*      */             }
/*  186 */             return $$12;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  191 */     BoundingBox $$13 = FillerCorridor.findPieceBox($$1, $$2, $$3, $$4, $$5, $$6);
/*  192 */     if ($$13 != null && $$13.minY() > 1) {
/*  193 */       return new FillerCorridor($$7, $$13, $$6);
/*      */     }
/*      */     
/*  196 */     return null;
/*      */   }
/*      */   
/*      */   static StructurePiece generateAndAddPiece(StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, int $$5, @Nullable Direction $$6, int $$7) {
/*  200 */     if ($$7 > 50) {
/*  201 */       return null;
/*      */     }
/*  203 */     if (Math.abs($$3 - $$0.getBoundingBox().minX()) > 112 || Math.abs($$5 - $$0.getBoundingBox().minZ()) > 112) {
/*  204 */       return null;
/*      */     }
/*      */     
/*  207 */     StructurePiece $$8 = generatePieceFromSmallDoor($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7 + 1);
/*  208 */     if ($$8 != null) {
/*  209 */       $$1.addPiece($$8);
/*  210 */       $$0.pendingChildren.add($$8);
/*      */     } 
/*  212 */     return $$8;
/*      */   }
/*      */   
/*      */   private static abstract class StrongholdPiece extends StructurePiece {
/*  216 */     protected SmallDoorType entryDoor = SmallDoorType.OPENING;
/*      */     
/*      */     protected StrongholdPiece(StructurePieceType $$0, int $$1, BoundingBox $$2) {
/*  219 */       super($$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public StrongholdPiece(StructurePieceType $$0, CompoundTag $$1) {
/*  223 */       super($$0, $$1);
/*  224 */       this.entryDoor = SmallDoorType.valueOf($$1.getString("EntryDoor"));
/*      */     }
/*      */     
/*      */     protected enum SmallDoorType {
/*  228 */       OPENING, WOOD_DOOR, GRATES, IRON_DOOR;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  233 */       $$1.putString("EntryDoor", this.entryDoor.name());
/*      */     }
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
/*      */     protected void generateSmallDoor(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2, SmallDoorType $$3, int $$4, int $$5, int $$6) {
/*      */       // Byte code:
/*      */       //   0: getstatic net/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$3.$SwitchMap$net$minecraft$world$level$levelgen$structure$structures$StrongholdPieces$StrongholdPiece$SmallDoorType : [I
/*      */       //   3: aload #4
/*      */       //   5: invokevirtual ordinal : ()I
/*      */       //   8: iaload
/*      */       //   9: tableswitch default -> 866, 1 -> 40, 2 -> 76, 3 -> 277, 4 -> 596
/*      */       //   40: aload_0
/*      */       //   41: aload_1
/*      */       //   42: aload_3
/*      */       //   43: iload #5
/*      */       //   45: iload #6
/*      */       //   47: iload #7
/*      */       //   49: iload #5
/*      */       //   51: iconst_3
/*      */       //   52: iadd
/*      */       //   53: iconst_1
/*      */       //   54: isub
/*      */       //   55: iload #6
/*      */       //   57: iconst_3
/*      */       //   58: iadd
/*      */       //   59: iconst_1
/*      */       //   60: isub
/*      */       //   61: iload #7
/*      */       //   63: getstatic net/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StrongholdPiece.CAVE_AIR : Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   66: getstatic net/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StrongholdPiece.CAVE_AIR : Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   69: iconst_0
/*      */       //   70: invokevirtual generateBox : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;IIIIIILnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Z)V
/*      */       //   73: goto -> 866
/*      */       //   76: aload_0
/*      */       //   77: aload_1
/*      */       //   78: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   81: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   84: iload #5
/*      */       //   86: iload #6
/*      */       //   88: iload #7
/*      */       //   90: aload_3
/*      */       //   91: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   94: aload_0
/*      */       //   95: aload_1
/*      */       //   96: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   99: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   102: iload #5
/*      */       //   104: iload #6
/*      */       //   106: iconst_1
/*      */       //   107: iadd
/*      */       //   108: iload #7
/*      */       //   110: aload_3
/*      */       //   111: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   114: aload_0
/*      */       //   115: aload_1
/*      */       //   116: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   119: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   122: iload #5
/*      */       //   124: iload #6
/*      */       //   126: iconst_2
/*      */       //   127: iadd
/*      */       //   128: iload #7
/*      */       //   130: aload_3
/*      */       //   131: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   134: aload_0
/*      */       //   135: aload_1
/*      */       //   136: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   139: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   142: iload #5
/*      */       //   144: iconst_1
/*      */       //   145: iadd
/*      */       //   146: iload #6
/*      */       //   148: iconst_2
/*      */       //   149: iadd
/*      */       //   150: iload #7
/*      */       //   152: aload_3
/*      */       //   153: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   156: aload_0
/*      */       //   157: aload_1
/*      */       //   158: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   161: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   164: iload #5
/*      */       //   166: iconst_2
/*      */       //   167: iadd
/*      */       //   168: iload #6
/*      */       //   170: iconst_2
/*      */       //   171: iadd
/*      */       //   172: iload #7
/*      */       //   174: aload_3
/*      */       //   175: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   178: aload_0
/*      */       //   179: aload_1
/*      */       //   180: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   183: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   186: iload #5
/*      */       //   188: iconst_2
/*      */       //   189: iadd
/*      */       //   190: iload #6
/*      */       //   192: iconst_1
/*      */       //   193: iadd
/*      */       //   194: iload #7
/*      */       //   196: aload_3
/*      */       //   197: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   200: aload_0
/*      */       //   201: aload_1
/*      */       //   202: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   205: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   208: iload #5
/*      */       //   210: iconst_2
/*      */       //   211: iadd
/*      */       //   212: iload #6
/*      */       //   214: iload #7
/*      */       //   216: aload_3
/*      */       //   217: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   220: aload_0
/*      */       //   221: aload_1
/*      */       //   222: getstatic net/minecraft/world/level/block/Blocks.OAK_DOOR : Lnet/minecraft/world/level/block/Block;
/*      */       //   225: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   228: iload #5
/*      */       //   230: iconst_1
/*      */       //   231: iadd
/*      */       //   232: iload #6
/*      */       //   234: iload #7
/*      */       //   236: aload_3
/*      */       //   237: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   240: aload_0
/*      */       //   241: aload_1
/*      */       //   242: getstatic net/minecraft/world/level/block/Blocks.OAK_DOOR : Lnet/minecraft/world/level/block/Block;
/*      */       //   245: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   248: getstatic net/minecraft/world/level/block/DoorBlock.HALF : Lnet/minecraft/world/level/block/state/properties/EnumProperty;
/*      */       //   251: getstatic net/minecraft/world/level/block/state/properties/DoubleBlockHalf.UPPER : Lnet/minecraft/world/level/block/state/properties/DoubleBlockHalf;
/*      */       //   254: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   257: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   260: iload #5
/*      */       //   262: iconst_1
/*      */       //   263: iadd
/*      */       //   264: iload #6
/*      */       //   266: iconst_1
/*      */       //   267: iadd
/*      */       //   268: iload #7
/*      */       //   270: aload_3
/*      */       //   271: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   274: goto -> 866
/*      */       //   277: aload_0
/*      */       //   278: aload_1
/*      */       //   279: getstatic net/minecraft/world/level/block/Blocks.CAVE_AIR : Lnet/minecraft/world/level/block/Block;
/*      */       //   282: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   285: iload #5
/*      */       //   287: iconst_1
/*      */       //   288: iadd
/*      */       //   289: iload #6
/*      */       //   291: iload #7
/*      */       //   293: aload_3
/*      */       //   294: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   297: aload_0
/*      */       //   298: aload_1
/*      */       //   299: getstatic net/minecraft/world/level/block/Blocks.CAVE_AIR : Lnet/minecraft/world/level/block/Block;
/*      */       //   302: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   305: iload #5
/*      */       //   307: iconst_1
/*      */       //   308: iadd
/*      */       //   309: iload #6
/*      */       //   311: iconst_1
/*      */       //   312: iadd
/*      */       //   313: iload #7
/*      */       //   315: aload_3
/*      */       //   316: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   319: aload_0
/*      */       //   320: aload_1
/*      */       //   321: getstatic net/minecraft/world/level/block/Blocks.IRON_BARS : Lnet/minecraft/world/level/block/Block;
/*      */       //   324: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   327: getstatic net/minecraft/world/level/block/IronBarsBlock.WEST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   330: iconst_1
/*      */       //   331: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   334: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   337: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   340: iload #5
/*      */       //   342: iload #6
/*      */       //   344: iload #7
/*      */       //   346: aload_3
/*      */       //   347: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   350: aload_0
/*      */       //   351: aload_1
/*      */       //   352: getstatic net/minecraft/world/level/block/Blocks.IRON_BARS : Lnet/minecraft/world/level/block/Block;
/*      */       //   355: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   358: getstatic net/minecraft/world/level/block/IronBarsBlock.WEST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   361: iconst_1
/*      */       //   362: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   365: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   368: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   371: iload #5
/*      */       //   373: iload #6
/*      */       //   375: iconst_1
/*      */       //   376: iadd
/*      */       //   377: iload #7
/*      */       //   379: aload_3
/*      */       //   380: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   383: aload_0
/*      */       //   384: aload_1
/*      */       //   385: getstatic net/minecraft/world/level/block/Blocks.IRON_BARS : Lnet/minecraft/world/level/block/Block;
/*      */       //   388: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   391: getstatic net/minecraft/world/level/block/IronBarsBlock.EAST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   394: iconst_1
/*      */       //   395: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   398: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   401: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   404: getstatic net/minecraft/world/level/block/IronBarsBlock.WEST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   407: iconst_1
/*      */       //   408: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   411: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   414: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   417: iload #5
/*      */       //   419: iload #6
/*      */       //   421: iconst_2
/*      */       //   422: iadd
/*      */       //   423: iload #7
/*      */       //   425: aload_3
/*      */       //   426: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   429: aload_0
/*      */       //   430: aload_1
/*      */       //   431: getstatic net/minecraft/world/level/block/Blocks.IRON_BARS : Lnet/minecraft/world/level/block/Block;
/*      */       //   434: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   437: getstatic net/minecraft/world/level/block/IronBarsBlock.EAST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   440: iconst_1
/*      */       //   441: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   444: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   447: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   450: getstatic net/minecraft/world/level/block/IronBarsBlock.WEST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   453: iconst_1
/*      */       //   454: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   457: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   460: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   463: iload #5
/*      */       //   465: iconst_1
/*      */       //   466: iadd
/*      */       //   467: iload #6
/*      */       //   469: iconst_2
/*      */       //   470: iadd
/*      */       //   471: iload #7
/*      */       //   473: aload_3
/*      */       //   474: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   477: aload_0
/*      */       //   478: aload_1
/*      */       //   479: getstatic net/minecraft/world/level/block/Blocks.IRON_BARS : Lnet/minecraft/world/level/block/Block;
/*      */       //   482: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   485: getstatic net/minecraft/world/level/block/IronBarsBlock.EAST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   488: iconst_1
/*      */       //   489: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   492: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   495: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   498: getstatic net/minecraft/world/level/block/IronBarsBlock.WEST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   501: iconst_1
/*      */       //   502: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   505: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   508: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   511: iload #5
/*      */       //   513: iconst_2
/*      */       //   514: iadd
/*      */       //   515: iload #6
/*      */       //   517: iconst_2
/*      */       //   518: iadd
/*      */       //   519: iload #7
/*      */       //   521: aload_3
/*      */       //   522: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   525: aload_0
/*      */       //   526: aload_1
/*      */       //   527: getstatic net/minecraft/world/level/block/Blocks.IRON_BARS : Lnet/minecraft/world/level/block/Block;
/*      */       //   530: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   533: getstatic net/minecraft/world/level/block/IronBarsBlock.EAST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   536: iconst_1
/*      */       //   537: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   540: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   543: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   546: iload #5
/*      */       //   548: iconst_2
/*      */       //   549: iadd
/*      */       //   550: iload #6
/*      */       //   552: iconst_1
/*      */       //   553: iadd
/*      */       //   554: iload #7
/*      */       //   556: aload_3
/*      */       //   557: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   560: aload_0
/*      */       //   561: aload_1
/*      */       //   562: getstatic net/minecraft/world/level/block/Blocks.IRON_BARS : Lnet/minecraft/world/level/block/Block;
/*      */       //   565: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   568: getstatic net/minecraft/world/level/block/IronBarsBlock.EAST : Lnet/minecraft/world/level/block/state/properties/BooleanProperty;
/*      */       //   571: iconst_1
/*      */       //   572: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*      */       //   575: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   578: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   581: iload #5
/*      */       //   583: iconst_2
/*      */       //   584: iadd
/*      */       //   585: iload #6
/*      */       //   587: iload #7
/*      */       //   589: aload_3
/*      */       //   590: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   593: goto -> 866
/*      */       //   596: aload_0
/*      */       //   597: aload_1
/*      */       //   598: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   601: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   604: iload #5
/*      */       //   606: iload #6
/*      */       //   608: iload #7
/*      */       //   610: aload_3
/*      */       //   611: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   614: aload_0
/*      */       //   615: aload_1
/*      */       //   616: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   619: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   622: iload #5
/*      */       //   624: iload #6
/*      */       //   626: iconst_1
/*      */       //   627: iadd
/*      */       //   628: iload #7
/*      */       //   630: aload_3
/*      */       //   631: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   634: aload_0
/*      */       //   635: aload_1
/*      */       //   636: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   639: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   642: iload #5
/*      */       //   644: iload #6
/*      */       //   646: iconst_2
/*      */       //   647: iadd
/*      */       //   648: iload #7
/*      */       //   650: aload_3
/*      */       //   651: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   654: aload_0
/*      */       //   655: aload_1
/*      */       //   656: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   659: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   662: iload #5
/*      */       //   664: iconst_1
/*      */       //   665: iadd
/*      */       //   666: iload #6
/*      */       //   668: iconst_2
/*      */       //   669: iadd
/*      */       //   670: iload #7
/*      */       //   672: aload_3
/*      */       //   673: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   676: aload_0
/*      */       //   677: aload_1
/*      */       //   678: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   681: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   684: iload #5
/*      */       //   686: iconst_2
/*      */       //   687: iadd
/*      */       //   688: iload #6
/*      */       //   690: iconst_2
/*      */       //   691: iadd
/*      */       //   692: iload #7
/*      */       //   694: aload_3
/*      */       //   695: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   698: aload_0
/*      */       //   699: aload_1
/*      */       //   700: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   703: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   706: iload #5
/*      */       //   708: iconst_2
/*      */       //   709: iadd
/*      */       //   710: iload #6
/*      */       //   712: iconst_1
/*      */       //   713: iadd
/*      */       //   714: iload #7
/*      */       //   716: aload_3
/*      */       //   717: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   720: aload_0
/*      */       //   721: aload_1
/*      */       //   722: getstatic net/minecraft/world/level/block/Blocks.STONE_BRICKS : Lnet/minecraft/world/level/block/Block;
/*      */       //   725: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   728: iload #5
/*      */       //   730: iconst_2
/*      */       //   731: iadd
/*      */       //   732: iload #6
/*      */       //   734: iload #7
/*      */       //   736: aload_3
/*      */       //   737: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   740: aload_0
/*      */       //   741: aload_1
/*      */       //   742: getstatic net/minecraft/world/level/block/Blocks.IRON_DOOR : Lnet/minecraft/world/level/block/Block;
/*      */       //   745: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   748: iload #5
/*      */       //   750: iconst_1
/*      */       //   751: iadd
/*      */       //   752: iload #6
/*      */       //   754: iload #7
/*      */       //   756: aload_3
/*      */       //   757: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   760: aload_0
/*      */       //   761: aload_1
/*      */       //   762: getstatic net/minecraft/world/level/block/Blocks.IRON_DOOR : Lnet/minecraft/world/level/block/Block;
/*      */       //   765: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   768: getstatic net/minecraft/world/level/block/DoorBlock.HALF : Lnet/minecraft/world/level/block/state/properties/EnumProperty;
/*      */       //   771: getstatic net/minecraft/world/level/block/state/properties/DoubleBlockHalf.UPPER : Lnet/minecraft/world/level/block/state/properties/DoubleBlockHalf;
/*      */       //   774: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   777: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   780: iload #5
/*      */       //   782: iconst_1
/*      */       //   783: iadd
/*      */       //   784: iload #6
/*      */       //   786: iconst_1
/*      */       //   787: iadd
/*      */       //   788: iload #7
/*      */       //   790: aload_3
/*      */       //   791: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   794: aload_0
/*      */       //   795: aload_1
/*      */       //   796: getstatic net/minecraft/world/level/block/Blocks.STONE_BUTTON : Lnet/minecraft/world/level/block/Block;
/*      */       //   799: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   802: getstatic net/minecraft/world/level/block/ButtonBlock.FACING : Lnet/minecraft/world/level/block/state/properties/DirectionProperty;
/*      */       //   805: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*      */       //   808: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   811: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   814: iload #5
/*      */       //   816: iconst_2
/*      */       //   817: iadd
/*      */       //   818: iload #6
/*      */       //   820: iconst_1
/*      */       //   821: iadd
/*      */       //   822: iload #7
/*      */       //   824: iconst_1
/*      */       //   825: iadd
/*      */       //   826: aload_3
/*      */       //   827: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   830: aload_0
/*      */       //   831: aload_1
/*      */       //   832: getstatic net/minecraft/world/level/block/Blocks.STONE_BUTTON : Lnet/minecraft/world/level/block/Block;
/*      */       //   835: invokevirtual defaultBlockState : ()Lnet/minecraft/world/level/block/state/BlockState;
/*      */       //   838: getstatic net/minecraft/world/level/block/ButtonBlock.FACING : Lnet/minecraft/world/level/block/state/properties/DirectionProperty;
/*      */       //   841: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*      */       //   844: invokevirtual setValue : (Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;
/*      */       //   847: checkcast net/minecraft/world/level/block/state/BlockState
/*      */       //   850: iload #5
/*      */       //   852: iconst_2
/*      */       //   853: iadd
/*      */       //   854: iload #6
/*      */       //   856: iconst_1
/*      */       //   857: iadd
/*      */       //   858: iload #7
/*      */       //   860: iconst_1
/*      */       //   861: isub
/*      */       //   862: aload_3
/*      */       //   863: invokevirtual placeBlock : (Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V
/*      */       //   866: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #237	-> 0
/*      */       //   #239	-> 40
/*      */       //   #240	-> 73
/*      */       //   #242	-> 76
/*      */       //   #243	-> 94
/*      */       //   #244	-> 114
/*      */       //   #245	-> 134
/*      */       //   #246	-> 156
/*      */       //   #247	-> 178
/*      */       //   #248	-> 200
/*      */       //   #249	-> 220
/*      */       //   #250	-> 240
/*      */       //   #251	-> 274
/*      */       //   #253	-> 277
/*      */       //   #254	-> 297
/*      */       //   #255	-> 319
/*      */       //   #256	-> 350
/*      */       //   #257	-> 383
/*      */       //   #258	-> 429
/*      */       //   #259	-> 477
/*      */       //   #260	-> 525
/*      */       //   #261	-> 560
/*      */       //   #262	-> 593
/*      */       //   #264	-> 596
/*      */       //   #265	-> 614
/*      */       //   #266	-> 634
/*      */       //   #267	-> 654
/*      */       //   #268	-> 676
/*      */       //   #269	-> 698
/*      */       //   #270	-> 720
/*      */       //   #271	-> 740
/*      */       //   #272	-> 760
/*      */       //   #273	-> 794
/*      */       //   #274	-> 830
/*      */       //   #277	-> 866
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	867	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StrongholdPiece;
/*      */       //   0	867	1	$$0	Lnet/minecraft/world/level/WorldGenLevel;
/*      */       //   0	867	2	$$1	Lnet/minecraft/util/RandomSource;
/*      */       //   0	867	3	$$2	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   0	867	4	$$3	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StrongholdPiece$SmallDoorType;
/*      */       //   0	867	5	$$4	I
/*      */       //   0	867	6	$$5	I
/*      */       //   0	867	7	$$6	I
/*      */     }
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
/*      */     protected SmallDoorType randomSmallDoor(RandomSource $$0) {
/*  280 */       int $$1 = $$0.nextInt(5);
/*  281 */       switch ($$1)
/*      */       
/*      */       { 
/*      */         default:
/*  285 */           return SmallDoorType.OPENING;
/*      */         case 2:
/*  287 */           return SmallDoorType.WOOD_DOOR;
/*      */         case 3:
/*  289 */           return SmallDoorType.GRATES;
/*      */         case 4:
/*  291 */           break; }  return SmallDoorType.IRON_DOOR;
/*      */     }
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
/*      */     @Nullable
/*      */     protected StructurePiece generateSmallDoorChildForward(StrongholdPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: invokevirtual getOrientation : ()Lnet/minecraft/core/Direction;
/*      */       //   4: astore #6
/*      */       //   6: aload #6
/*      */       //   8: ifnull -> 220
/*      */       //   11: getstatic net/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$3.$SwitchMap$net$minecraft$core$Direction : [I
/*      */       //   14: aload #6
/*      */       //   16: invokevirtual ordinal : ()I
/*      */       //   19: iaload
/*      */       //   20: tableswitch default -> 220, 1 -> 52, 2 -> 94, 3 -> 136, 4 -> 178
/*      */       //   52: aload_1
/*      */       //   53: aload_2
/*      */       //   54: aload_3
/*      */       //   55: aload_0
/*      */       //   56: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   59: invokevirtual minX : ()I
/*      */       //   62: iload #4
/*      */       //   64: iadd
/*      */       //   65: aload_0
/*      */       //   66: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   69: invokevirtual minY : ()I
/*      */       //   72: iload #5
/*      */       //   74: iadd
/*      */       //   75: aload_0
/*      */       //   76: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   79: invokevirtual minZ : ()I
/*      */       //   82: iconst_1
/*      */       //   83: isub
/*      */       //   84: aload #6
/*      */       //   86: aload_0
/*      */       //   87: invokevirtual getGenDepth : ()I
/*      */       //   90: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   93: areturn
/*      */       //   94: aload_1
/*      */       //   95: aload_2
/*      */       //   96: aload_3
/*      */       //   97: aload_0
/*      */       //   98: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   101: invokevirtual minX : ()I
/*      */       //   104: iload #4
/*      */       //   106: iadd
/*      */       //   107: aload_0
/*      */       //   108: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   111: invokevirtual minY : ()I
/*      */       //   114: iload #5
/*      */       //   116: iadd
/*      */       //   117: aload_0
/*      */       //   118: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   121: invokevirtual maxZ : ()I
/*      */       //   124: iconst_1
/*      */       //   125: iadd
/*      */       //   126: aload #6
/*      */       //   128: aload_0
/*      */       //   129: invokevirtual getGenDepth : ()I
/*      */       //   132: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   135: areturn
/*      */       //   136: aload_1
/*      */       //   137: aload_2
/*      */       //   138: aload_3
/*      */       //   139: aload_0
/*      */       //   140: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   143: invokevirtual minX : ()I
/*      */       //   146: iconst_1
/*      */       //   147: isub
/*      */       //   148: aload_0
/*      */       //   149: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   152: invokevirtual minY : ()I
/*      */       //   155: iload #5
/*      */       //   157: iadd
/*      */       //   158: aload_0
/*      */       //   159: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   162: invokevirtual minZ : ()I
/*      */       //   165: iload #4
/*      */       //   167: iadd
/*      */       //   168: aload #6
/*      */       //   170: aload_0
/*      */       //   171: invokevirtual getGenDepth : ()I
/*      */       //   174: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   177: areturn
/*      */       //   178: aload_1
/*      */       //   179: aload_2
/*      */       //   180: aload_3
/*      */       //   181: aload_0
/*      */       //   182: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   185: invokevirtual maxX : ()I
/*      */       //   188: iconst_1
/*      */       //   189: iadd
/*      */       //   190: aload_0
/*      */       //   191: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   194: invokevirtual minY : ()I
/*      */       //   197: iload #5
/*      */       //   199: iadd
/*      */       //   200: aload_0
/*      */       //   201: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   204: invokevirtual minZ : ()I
/*      */       //   207: iload #4
/*      */       //   209: iadd
/*      */       //   210: aload #6
/*      */       //   212: aload_0
/*      */       //   213: invokevirtual getGenDepth : ()I
/*      */       //   216: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   219: areturn
/*      */       //   220: aconst_null
/*      */       //   221: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #297	-> 0
/*      */       //   #298	-> 6
/*      */       //   #299	-> 11
/*      */       //   #301	-> 52
/*      */       //   #303	-> 94
/*      */       //   #305	-> 136
/*      */       //   #307	-> 178
/*      */       //   #310	-> 220
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	222	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StrongholdPiece;
/*      */       //   0	222	1	$$0	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;
/*      */       //   0	222	2	$$1	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*      */       //   0	222	3	$$2	Lnet/minecraft/util/RandomSource;
/*      */       //   0	222	4	$$3	I
/*      */       //   0	222	5	$$4	I
/*      */       //   6	216	6	$$5	Lnet/minecraft/core/Direction;
/*      */     }
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
/*      */     @Nullable
/*      */     protected StructurePiece generateSmallDoorChildLeft(StrongholdPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: invokevirtual getOrientation : ()Lnet/minecraft/core/Direction;
/*      */       //   4: astore #6
/*      */       //   6: aload #6
/*      */       //   8: ifnull -> 224
/*      */       //   11: getstatic net/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$3.$SwitchMap$net$minecraft$core$Direction : [I
/*      */       //   14: aload #6
/*      */       //   16: invokevirtual ordinal : ()I
/*      */       //   19: iaload
/*      */       //   20: tableswitch default -> 224, 1 -> 52, 2 -> 95, 3 -> 138, 4 -> 181
/*      */       //   52: aload_1
/*      */       //   53: aload_2
/*      */       //   54: aload_3
/*      */       //   55: aload_0
/*      */       //   56: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   59: invokevirtual minX : ()I
/*      */       //   62: iconst_1
/*      */       //   63: isub
/*      */       //   64: aload_0
/*      */       //   65: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   68: invokevirtual minY : ()I
/*      */       //   71: iload #4
/*      */       //   73: iadd
/*      */       //   74: aload_0
/*      */       //   75: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   78: invokevirtual minZ : ()I
/*      */       //   81: iload #5
/*      */       //   83: iadd
/*      */       //   84: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*      */       //   87: aload_0
/*      */       //   88: invokevirtual getGenDepth : ()I
/*      */       //   91: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   94: areturn
/*      */       //   95: aload_1
/*      */       //   96: aload_2
/*      */       //   97: aload_3
/*      */       //   98: aload_0
/*      */       //   99: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   102: invokevirtual minX : ()I
/*      */       //   105: iconst_1
/*      */       //   106: isub
/*      */       //   107: aload_0
/*      */       //   108: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   111: invokevirtual minY : ()I
/*      */       //   114: iload #4
/*      */       //   116: iadd
/*      */       //   117: aload_0
/*      */       //   118: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   121: invokevirtual minZ : ()I
/*      */       //   124: iload #5
/*      */       //   126: iadd
/*      */       //   127: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*      */       //   130: aload_0
/*      */       //   131: invokevirtual getGenDepth : ()I
/*      */       //   134: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   137: areturn
/*      */       //   138: aload_1
/*      */       //   139: aload_2
/*      */       //   140: aload_3
/*      */       //   141: aload_0
/*      */       //   142: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   145: invokevirtual minX : ()I
/*      */       //   148: iload #5
/*      */       //   150: iadd
/*      */       //   151: aload_0
/*      */       //   152: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   155: invokevirtual minY : ()I
/*      */       //   158: iload #4
/*      */       //   160: iadd
/*      */       //   161: aload_0
/*      */       //   162: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   165: invokevirtual minZ : ()I
/*      */       //   168: iconst_1
/*      */       //   169: isub
/*      */       //   170: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*      */       //   173: aload_0
/*      */       //   174: invokevirtual getGenDepth : ()I
/*      */       //   177: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   180: areturn
/*      */       //   181: aload_1
/*      */       //   182: aload_2
/*      */       //   183: aload_3
/*      */       //   184: aload_0
/*      */       //   185: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   188: invokevirtual minX : ()I
/*      */       //   191: iload #5
/*      */       //   193: iadd
/*      */       //   194: aload_0
/*      */       //   195: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   198: invokevirtual minY : ()I
/*      */       //   201: iload #4
/*      */       //   203: iadd
/*      */       //   204: aload_0
/*      */       //   205: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   208: invokevirtual minZ : ()I
/*      */       //   211: iconst_1
/*      */       //   212: isub
/*      */       //   213: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*      */       //   216: aload_0
/*      */       //   217: invokevirtual getGenDepth : ()I
/*      */       //   220: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   223: areturn
/*      */       //   224: aconst_null
/*      */       //   225: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #315	-> 0
/*      */       //   #316	-> 6
/*      */       //   #317	-> 11
/*      */       //   #319	-> 52
/*      */       //   #321	-> 95
/*      */       //   #323	-> 138
/*      */       //   #325	-> 181
/*      */       //   #328	-> 224
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	226	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StrongholdPiece;
/*      */       //   0	226	1	$$0	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;
/*      */       //   0	226	2	$$1	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*      */       //   0	226	3	$$2	Lnet/minecraft/util/RandomSource;
/*      */       //   0	226	4	$$3	I
/*      */       //   0	226	5	$$4	I
/*      */       //   6	220	6	$$5	Lnet/minecraft/core/Direction;
/*      */     }
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
/*      */     @Nullable
/*      */     protected StructurePiece generateSmallDoorChildRight(StrongholdPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: invokevirtual getOrientation : ()Lnet/minecraft/core/Direction;
/*      */       //   4: astore #6
/*      */       //   6: aload #6
/*      */       //   8: ifnull -> 224
/*      */       //   11: getstatic net/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$3.$SwitchMap$net$minecraft$core$Direction : [I
/*      */       //   14: aload #6
/*      */       //   16: invokevirtual ordinal : ()I
/*      */       //   19: iaload
/*      */       //   20: tableswitch default -> 224, 1 -> 52, 2 -> 95, 3 -> 138, 4 -> 181
/*      */       //   52: aload_1
/*      */       //   53: aload_2
/*      */       //   54: aload_3
/*      */       //   55: aload_0
/*      */       //   56: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   59: invokevirtual maxX : ()I
/*      */       //   62: iconst_1
/*      */       //   63: iadd
/*      */       //   64: aload_0
/*      */       //   65: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   68: invokevirtual minY : ()I
/*      */       //   71: iload #4
/*      */       //   73: iadd
/*      */       //   74: aload_0
/*      */       //   75: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   78: invokevirtual minZ : ()I
/*      */       //   81: iload #5
/*      */       //   83: iadd
/*      */       //   84: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*      */       //   87: aload_0
/*      */       //   88: invokevirtual getGenDepth : ()I
/*      */       //   91: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   94: areturn
/*      */       //   95: aload_1
/*      */       //   96: aload_2
/*      */       //   97: aload_3
/*      */       //   98: aload_0
/*      */       //   99: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   102: invokevirtual maxX : ()I
/*      */       //   105: iconst_1
/*      */       //   106: iadd
/*      */       //   107: aload_0
/*      */       //   108: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   111: invokevirtual minY : ()I
/*      */       //   114: iload #4
/*      */       //   116: iadd
/*      */       //   117: aload_0
/*      */       //   118: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   121: invokevirtual minZ : ()I
/*      */       //   124: iload #5
/*      */       //   126: iadd
/*      */       //   127: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*      */       //   130: aload_0
/*      */       //   131: invokevirtual getGenDepth : ()I
/*      */       //   134: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   137: areturn
/*      */       //   138: aload_1
/*      */       //   139: aload_2
/*      */       //   140: aload_3
/*      */       //   141: aload_0
/*      */       //   142: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   145: invokevirtual minX : ()I
/*      */       //   148: iload #5
/*      */       //   150: iadd
/*      */       //   151: aload_0
/*      */       //   152: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   155: invokevirtual minY : ()I
/*      */       //   158: iload #4
/*      */       //   160: iadd
/*      */       //   161: aload_0
/*      */       //   162: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   165: invokevirtual maxZ : ()I
/*      */       //   168: iconst_1
/*      */       //   169: iadd
/*      */       //   170: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*      */       //   173: aload_0
/*      */       //   174: invokevirtual getGenDepth : ()I
/*      */       //   177: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   180: areturn
/*      */       //   181: aload_1
/*      */       //   182: aload_2
/*      */       //   183: aload_3
/*      */       //   184: aload_0
/*      */       //   185: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   188: invokevirtual minX : ()I
/*      */       //   191: iload #5
/*      */       //   193: iadd
/*      */       //   194: aload_0
/*      */       //   195: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   198: invokevirtual minY : ()I
/*      */       //   201: iload #4
/*      */       //   203: iadd
/*      */       //   204: aload_0
/*      */       //   205: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*      */       //   208: invokevirtual maxZ : ()I
/*      */       //   211: iconst_1
/*      */       //   212: iadd
/*      */       //   213: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*      */       //   216: aload_0
/*      */       //   217: invokevirtual getGenDepth : ()I
/*      */       //   220: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*      */       //   223: areturn
/*      */       //   224: aconst_null
/*      */       //   225: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #333	-> 0
/*      */       //   #334	-> 6
/*      */       //   #335	-> 11
/*      */       //   #337	-> 52
/*      */       //   #339	-> 95
/*      */       //   #341	-> 138
/*      */       //   #343	-> 181
/*      */       //   #346	-> 224
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	226	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StrongholdPiece;
/*      */       //   0	226	1	$$0	Lnet/minecraft/world/level/levelgen/structure/structures/StrongholdPieces$StartPiece;
/*      */       //   0	226	2	$$1	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*      */       //   0	226	3	$$2	Lnet/minecraft/util/RandomSource;
/*      */       //   0	226	4	$$3	I
/*      */       //   0	226	5	$$4	I
/*      */       //   6	220	6	$$5	Lnet/minecraft/core/Direction;
/*      */     }
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
/*      */     protected static boolean isOkBox(BoundingBox $$0) {
/*  350 */       return ($$0 != null && $$0.minY() > 10);
/*      */     } }
/*      */   
/*      */   protected enum SmallDoorType {
/*      */     OPENING, WOOD_DOOR, GRATES, IRON_DOOR;
/*      */   }
/*      */   
/*      */   public static class FillerCorridor extends StrongholdPiece {
/*      */     private final int steps;
/*      */     
/*      */     public FillerCorridor(int $$0, BoundingBox $$1, Direction $$2) {
/*  361 */       super(StructurePieceType.STRONGHOLD_FILLER_CORRIDOR, $$0, $$1);
/*      */       
/*  363 */       setOrientation($$2);
/*  364 */       this.steps = ($$2 == Direction.NORTH || $$2 == Direction.SOUTH) ? $$1.getZSpan() : $$1.getXSpan();
/*      */     }
/*      */     
/*      */     public FillerCorridor(CompoundTag $$0) {
/*  368 */       super(StructurePieceType.STRONGHOLD_FILLER_CORRIDOR, $$0);
/*  369 */       this.steps = $$0.getInt("Steps");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  374 */       super.addAdditionalSaveData($$0, $$1);
/*  375 */       $$1.putInt("Steps", this.steps);
/*      */     }
/*      */     
/*      */     public static BoundingBox findPieceBox(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5) {
/*  379 */       int $$6 = 3;
/*      */       
/*  381 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, 4, $$5);
/*      */       
/*  383 */       StructurePiece $$8 = $$0.findCollisionPiece($$7);
/*  384 */       if ($$8 == null)
/*      */       {
/*  386 */         return null;
/*      */       }
/*      */       
/*  389 */       if ($$8.getBoundingBox().minY() == $$7.minY())
/*      */       {
/*  391 */         for (int $$9 = 2; $$9 >= 1; $$9--) {
/*  392 */           $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, $$9, $$5);
/*  393 */           if (!$$8.getBoundingBox().intersects($$7))
/*      */           {
/*      */             
/*  396 */             return BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, $$9 + 1, $$5);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  401 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  407 */       for (int $$7 = 0; $$7 < this.steps; $$7++) {
/*      */         
/*  409 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 0, 0, $$7, $$4);
/*  410 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 0, $$7, $$4);
/*  411 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 0, $$7, $$4);
/*  412 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 0, $$7, $$4);
/*  413 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 4, 0, $$7, $$4);
/*      */         
/*  415 */         for (int $$8 = 1; $$8 <= 3; $$8++) {
/*  416 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 0, $$8, $$7, $$4);
/*  417 */           placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), 1, $$8, $$7, $$4);
/*  418 */           placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), 2, $$8, $$7, $$4);
/*  419 */           placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), 3, $$8, $$7, $$4);
/*  420 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 4, $$8, $$7, $$4);
/*      */         } 
/*      */         
/*  423 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 0, 4, $$7, $$4);
/*  424 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 4, $$7, $$4);
/*  425 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 4, $$7, $$4);
/*  426 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 4, $$7, $$4);
/*  427 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 4, 4, $$7, $$4);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class StairsDown
/*      */     extends StrongholdPiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 11;
/*      */     private static final int DEPTH = 5;
/*      */     private final boolean isSource;
/*      */     
/*      */     public StairsDown(StructurePieceType $$0, int $$1, int $$2, int $$3, Direction $$4) {
/*  440 */       super($$0, $$1, makeBoundingBox($$2, 64, $$3, $$4, 5, 11, 5));
/*      */       
/*  442 */       this.isSource = true;
/*  443 */       setOrientation($$4);
/*  444 */       this.entryDoor = StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;
/*      */     }
/*      */     
/*      */     public StairsDown(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  448 */       super(StructurePieceType.STRONGHOLD_STAIRS_DOWN, $$0, $$2);
/*      */       
/*  450 */       this.isSource = false;
/*  451 */       setOrientation($$3);
/*  452 */       this.entryDoor = randomSmallDoor($$1);
/*      */     }
/*      */     
/*      */     public StairsDown(StructurePieceType $$0, CompoundTag $$1) {
/*  456 */       super($$0, $$1);
/*  457 */       this.isSource = $$1.getBoolean("Source");
/*      */     }
/*      */     
/*      */     public StairsDown(CompoundTag $$0) {
/*  461 */       this(StructurePieceType.STRONGHOLD_STAIRS_DOWN, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  466 */       super.addAdditionalSaveData($$0, $$1);
/*  467 */       $$1.putBoolean("Source", this.isSource);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  472 */       if (this.isSource)
/*      */       {
/*  474 */         StrongholdPieces.imposedPiece = (Class)StrongholdPieces.FiveCrossing.class;
/*      */       }
/*  476 */       generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */     }
/*      */     
/*      */     public static StairsDown createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  480 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -7, 0, 5, 11, 5, $$5);
/*      */       
/*  482 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  483 */         return null;
/*      */       }
/*      */       
/*  486 */       return new StairsDown($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  492 */       generateBox($$0, $$4, 0, 0, 0, 4, 10, 4, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/*  494 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 7, 0);
/*      */       
/*  496 */       generateSmallDoor($$0, $$3, $$4, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 4);
/*      */ 
/*      */       
/*  499 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 6, 1, $$4);
/*  500 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5, 1, $$4);
/*  501 */       placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 6, 1, $$4);
/*  502 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5, 2, $$4);
/*  503 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 4, 3, $$4);
/*  504 */       placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 5, 3, $$4);
/*  505 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 4, 3, $$4);
/*  506 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 3, 3, $$4);
/*  507 */       placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 3, 4, 3, $$4);
/*  508 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 3, 2, $$4);
/*  509 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 2, 1, $$4);
/*  510 */       placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 3, 3, 1, $$4);
/*  511 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 2, 1, $$4);
/*  512 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 1, 1, $$4);
/*  513 */       placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 2, 1, $$4);
/*  514 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 1, 2, $$4);
/*  515 */       placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 1, 3, $$4);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class StartPiece
/*      */     extends StairsDown
/*      */   {
/*      */     public StrongholdPieces.PieceWeight previousPiece;
/*      */     @Nullable
/*      */     public StrongholdPieces.PortalRoom portalRoomPiece;
/*  525 */     public final List<StructurePiece> pendingChildren = Lists.newArrayList();
/*      */     
/*      */     public StartPiece(RandomSource $$0, int $$1, int $$2) {
/*  528 */       super(StructurePieceType.STRONGHOLD_START, 0, $$1, $$2, getRandomHorizontalDirection($$0));
/*      */     }
/*      */     
/*      */     public StartPiece(CompoundTag $$0) {
/*  532 */       super(StructurePieceType.STRONGHOLD_START, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public BlockPos getLocatorPosition() {
/*  537 */       if (this.portalRoomPiece != null) {
/*  538 */         return this.portalRoomPiece.getLocatorPosition();
/*      */       }
/*  540 */       return super.getLocatorPosition();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Straight
/*      */     extends StrongholdPiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 5;
/*      */     private static final int DEPTH = 7;
/*      */     private final boolean leftChild;
/*      */     private final boolean rightChild;
/*      */     
/*      */     public Straight(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  553 */       super(StructurePieceType.STRONGHOLD_STRAIGHT, $$0, $$2);
/*      */       
/*  555 */       setOrientation($$3);
/*  556 */       this.entryDoor = randomSmallDoor($$1);
/*      */       
/*  558 */       this.leftChild = ($$1.nextInt(2) == 0);
/*  559 */       this.rightChild = ($$1.nextInt(2) == 0);
/*      */     }
/*      */     
/*      */     public Straight(CompoundTag $$0) {
/*  563 */       super(StructurePieceType.STRONGHOLD_STRAIGHT, $$0);
/*  564 */       this.leftChild = $$0.getBoolean("Left");
/*  565 */       this.rightChild = $$0.getBoolean("Right");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  570 */       super.addAdditionalSaveData($$0, $$1);
/*  571 */       $$1.putBoolean("Left", this.leftChild);
/*  572 */       $$1.putBoolean("Right", this.rightChild);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  577 */       generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*  578 */       if (this.leftChild) {
/*  579 */         generateSmallDoorChildLeft((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 2);
/*      */       }
/*  581 */       if (this.rightChild) {
/*  582 */         generateSmallDoorChildRight((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 2);
/*      */       }
/*      */     }
/*      */     
/*      */     public static Straight createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  587 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, 7, $$5);
/*      */       
/*  589 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  590 */         return null;
/*      */       }
/*      */       
/*  593 */       return new Straight($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  599 */       generateBox($$0, $$4, 0, 0, 0, 4, 4, 6, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/*  601 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 1, 0);
/*      */       
/*  603 */       generateSmallDoor($$0, $$3, $$4, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
/*      */       
/*  605 */       BlockState $$7 = (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.EAST);
/*  606 */       BlockState $$8 = (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.WEST);
/*      */       
/*  608 */       maybeGenerateBlock($$0, $$4, $$3, 0.1F, 1, 2, 1, $$7);
/*  609 */       maybeGenerateBlock($$0, $$4, $$3, 0.1F, 3, 2, 1, $$8);
/*  610 */       maybeGenerateBlock($$0, $$4, $$3, 0.1F, 1, 2, 5, $$7);
/*  611 */       maybeGenerateBlock($$0, $$4, $$3, 0.1F, 3, 2, 5, $$8);
/*      */       
/*  613 */       if (this.leftChild) {
/*  614 */         generateBox($$0, $$4, 0, 1, 2, 0, 3, 4, CAVE_AIR, CAVE_AIR, false);
/*      */       }
/*  616 */       if (this.rightChild)
/*  617 */         generateBox($$0, $$4, 4, 1, 2, 4, 3, 4, CAVE_AIR, CAVE_AIR, false); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class ChestCorridor
/*      */     extends StrongholdPiece
/*      */   {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 5;
/*      */     private static final int DEPTH = 7;
/*      */     private boolean hasPlacedChest;
/*      */     
/*      */     public ChestCorridor(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  630 */       super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, $$0, $$2);
/*      */       
/*  632 */       setOrientation($$3);
/*  633 */       this.entryDoor = randomSmallDoor($$1);
/*      */     }
/*      */     
/*      */     public ChestCorridor(CompoundTag $$0) {
/*  637 */       super(StructurePieceType.STRONGHOLD_CHEST_CORRIDOR, $$0);
/*  638 */       this.hasPlacedChest = $$0.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  643 */       super.addAdditionalSaveData($$0, $$1);
/*  644 */       $$1.putBoolean("Chest", this.hasPlacedChest);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  649 */       generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */     }
/*      */     
/*      */     public static ChestCorridor createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  653 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, 7, $$5);
/*      */       
/*  655 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  656 */         return null;
/*      */       }
/*      */       
/*  659 */       return new ChestCorridor($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  665 */       generateBox($$0, $$4, 0, 0, 0, 4, 4, 6, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/*  667 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 1, 0);
/*      */       
/*  669 */       generateSmallDoor($$0, $$3, $$4, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
/*      */ 
/*      */       
/*  672 */       generateBox($$0, $$4, 3, 1, 2, 3, 1, 4, Blocks.STONE_BRICKS.defaultBlockState(), Blocks.STONE_BRICKS.defaultBlockState(), false);
/*  673 */       placeBlock($$0, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 1, 1, $$4);
/*  674 */       placeBlock($$0, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 1, 5, $$4);
/*  675 */       placeBlock($$0, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 2, 2, $$4);
/*  676 */       placeBlock($$0, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 3, 2, 4, $$4);
/*  677 */       for (int $$7 = 2; $$7 <= 4; $$7++) {
/*  678 */         placeBlock($$0, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 2, 1, $$7, $$4);
/*      */       }
/*      */       
/*  681 */       if (!this.hasPlacedChest && 
/*  682 */         $$4.isInside((Vec3i)getWorldPos(3, 2, 3))) {
/*  683 */         this.hasPlacedChest = true;
/*  684 */         createChest($$0, $$4, $$3, 3, 2, 3, BuiltInLootTables.STRONGHOLD_CORRIDOR);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class StraightStairsDown
/*      */     extends StrongholdPiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 11;
/*      */     private static final int DEPTH = 8;
/*      */     
/*      */     public StraightStairsDown(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  696 */       super(StructurePieceType.STRONGHOLD_STRAIGHT_STAIRS_DOWN, $$0, $$2);
/*      */       
/*  698 */       setOrientation($$3);
/*  699 */       this.entryDoor = randomSmallDoor($$1);
/*      */     }
/*      */     
/*      */     public StraightStairsDown(CompoundTag $$0) {
/*  703 */       super(StructurePieceType.STRONGHOLD_STRAIGHT_STAIRS_DOWN, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  708 */       generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */     }
/*      */     
/*      */     public static StraightStairsDown createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  712 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -7, 0, 5, 11, 8, $$5);
/*      */       
/*  714 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  715 */         return null;
/*      */       }
/*      */       
/*  718 */       return new StraightStairsDown($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  724 */       generateBox($$0, $$4, 0, 0, 0, 4, 10, 7, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/*  726 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 7, 0);
/*      */       
/*  728 */       generateSmallDoor($$0, $$3, $$4, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 7);
/*      */ 
/*      */       
/*  731 */       BlockState $$7 = (BlockState)Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.SOUTH);
/*  732 */       for (int $$8 = 0; $$8 < 6; $$8++) {
/*  733 */         placeBlock($$0, $$7, 1, 6 - $$8, 1 + $$8, $$4);
/*  734 */         placeBlock($$0, $$7, 2, 6 - $$8, 1 + $$8, $$4);
/*  735 */         placeBlock($$0, $$7, 3, 6 - $$8, 1 + $$8, $$4);
/*  736 */         if ($$8 < 5) {
/*  737 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5 - $$8, 1 + $$8, $$4);
/*  738 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 5 - $$8, 1 + $$8, $$4);
/*  739 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 5 - $$8, 1 + $$8, $$4);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static abstract class Turn extends StrongholdPiece {
/*      */     protected static final int WIDTH = 5;
/*      */     protected static final int HEIGHT = 5;
/*      */     protected static final int DEPTH = 5;
/*      */     
/*      */     protected Turn(StructurePieceType $$0, int $$1, BoundingBox $$2) {
/*  751 */       super($$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public Turn(StructurePieceType $$0, CompoundTag $$1) {
/*  755 */       super($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class LeftTurn extends Turn {
/*      */     public LeftTurn(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  761 */       super(StructurePieceType.STRONGHOLD_LEFT_TURN, $$0, $$2);
/*      */       
/*  763 */       setOrientation($$3);
/*  764 */       this.entryDoor = randomSmallDoor($$1);
/*      */     }
/*      */     
/*      */     public LeftTurn(CompoundTag $$0) {
/*  768 */       super(StructurePieceType.STRONGHOLD_LEFT_TURN, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  773 */       Direction $$3 = getOrientation();
/*  774 */       if ($$3 == Direction.NORTH || $$3 == Direction.EAST) {
/*  775 */         generateSmallDoorChildLeft((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */       } else {
/*  777 */         generateSmallDoorChildRight((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public static LeftTurn createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  782 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, 5, $$5);
/*      */       
/*  784 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  785 */         return null;
/*      */       }
/*      */       
/*  788 */       return new LeftTurn($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  794 */       generateBox($$0, $$4, 0, 0, 0, 4, 4, 4, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/*  796 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 1, 0);
/*      */       
/*  798 */       Direction $$7 = getOrientation();
/*  799 */       if ($$7 == Direction.NORTH || $$7 == Direction.EAST) {
/*  800 */         generateBox($$0, $$4, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, false);
/*      */       } else {
/*  802 */         generateBox($$0, $$4, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, false);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RightTurn extends Turn {
/*      */     public RightTurn(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  809 */       super(StructurePieceType.STRONGHOLD_RIGHT_TURN, $$0, $$2);
/*      */       
/*  811 */       setOrientation($$3);
/*  812 */       this.entryDoor = randomSmallDoor($$1);
/*      */     }
/*      */     
/*      */     public RightTurn(CompoundTag $$0) {
/*  816 */       super(StructurePieceType.STRONGHOLD_RIGHT_TURN, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  821 */       Direction $$3 = getOrientation();
/*  822 */       if ($$3 == Direction.NORTH || $$3 == Direction.EAST) {
/*  823 */         generateSmallDoorChildRight((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */       } else {
/*  825 */         generateSmallDoorChildLeft((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public static RightTurn createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  830 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, 5, $$5);
/*      */       
/*  832 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  833 */         return null;
/*      */       }
/*      */       
/*  836 */       return new RightTurn($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  842 */       generateBox($$0, $$4, 0, 0, 0, 4, 4, 4, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/*  844 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 1, 0);
/*      */       
/*  846 */       Direction $$7 = getOrientation();
/*  847 */       if ($$7 == Direction.NORTH || $$7 == Direction.EAST) {
/*  848 */         generateBox($$0, $$4, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, false);
/*      */       } else {
/*  850 */         generateBox($$0, $$4, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, false);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RoomCrossing
/*      */     extends StrongholdPiece {
/*      */     protected static final int WIDTH = 11;
/*      */     protected static final int HEIGHT = 7;
/*      */     protected static final int DEPTH = 11;
/*      */     protected final int type;
/*      */     
/*      */     public RoomCrossing(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  863 */       super(StructurePieceType.STRONGHOLD_ROOM_CROSSING, $$0, $$2);
/*      */       
/*  865 */       setOrientation($$3);
/*  866 */       this.entryDoor = randomSmallDoor($$1);
/*  867 */       this.type = $$1.nextInt(5);
/*      */     }
/*      */     
/*      */     public RoomCrossing(CompoundTag $$0) {
/*  871 */       super(StructurePieceType.STRONGHOLD_ROOM_CROSSING, $$0);
/*  872 */       this.type = $$0.getInt("Type");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  877 */       super.addAdditionalSaveData($$0, $$1);
/*  878 */       $$1.putInt("Type", this.type);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  883 */       generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 4, 1);
/*  884 */       generateSmallDoorChildLeft((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 4);
/*  885 */       generateSmallDoorChildRight((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 4);
/*      */     }
/*      */     
/*      */     public static RoomCrossing createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  889 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -4, -1, 0, 11, 7, 11, $$5);
/*      */       
/*  891 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  892 */         return null;
/*      */       }
/*      */       
/*  895 */       return new RoomCrossing($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*      */       int $$7;
/*  901 */       generateBox($$0, $$4, 0, 0, 0, 10, 6, 10, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/*  903 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 4, 1, 0);
/*      */       
/*  905 */       generateBox($$0, $$4, 4, 1, 10, 6, 3, 10, CAVE_AIR, CAVE_AIR, false);
/*  906 */       generateBox($$0, $$4, 0, 1, 4, 0, 3, 6, CAVE_AIR, CAVE_AIR, false);
/*  907 */       generateBox($$0, $$4, 10, 1, 4, 10, 3, 6, CAVE_AIR, CAVE_AIR, false);
/*      */       
/*  909 */       switch (this.type) {
/*      */         default:
/*      */           return;
/*      */         
/*      */         case 0:
/*  914 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 1, 5, $$4);
/*  915 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 2, 5, $$4);
/*  916 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 3, 5, $$4);
/*  917 */           placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.WEST), 4, 3, 5, $$4);
/*  918 */           placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.EAST), 6, 3, 5, $$4);
/*  919 */           placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.SOUTH), 5, 3, 4, $$4);
/*  920 */           placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.NORTH), 5, 3, 6, $$4);
/*  921 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 4, $$4);
/*  922 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 5, $$4);
/*  923 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 4, 1, 6, $$4);
/*  924 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 4, $$4);
/*  925 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 5, $$4);
/*  926 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 6, 1, 6, $$4);
/*  927 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 5, 1, 4, $$4);
/*  928 */           placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 5, 1, 6, $$4);
/*      */         
/*      */         case 1:
/*  931 */           for ($$7 = 0; $$7 < 5; $$7++) {
/*  932 */             placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 1, 3 + $$7, $$4);
/*  933 */             placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 7, 1, 3 + $$7, $$4);
/*  934 */             placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3 + $$7, 1, 3, $$4);
/*  935 */             placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3 + $$7, 1, 7, $$4);
/*      */           } 
/*  937 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 1, 5, $$4);
/*  938 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 2, 5, $$4);
/*  939 */           placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 5, 3, 5, $$4);
/*  940 */           placeBlock($$0, Blocks.WATER.defaultBlockState(), 5, 4, 5, $$4);
/*      */         case 2:
/*      */           break;
/*  943 */       }  for (int $$8 = 1; $$8 <= 9; $$8++) {
/*  944 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 1, 3, $$8, $$4);
/*  945 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 9, 3, $$8, $$4);
/*      */       } 
/*  947 */       for (int $$9 = 1; $$9 <= 9; $$9++) {
/*  948 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), $$9, 3, 1, $$4);
/*  949 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), $$9, 3, 9, $$4);
/*      */       } 
/*  951 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 1, 4, $$4);
/*  952 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 1, 6, $$4);
/*  953 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 3, 4, $$4);
/*  954 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 5, 3, 6, $$4);
/*  955 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, 1, 5, $$4);
/*  956 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, 1, 5, $$4);
/*  957 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, 3, 5, $$4);
/*  958 */       placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, 3, 5, $$4);
/*  959 */       for (int $$10 = 1; $$10 <= 3; $$10++) {
/*  960 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, $$10, 4, $$4);
/*  961 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, $$10, 4, $$4);
/*  962 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 4, $$10, 6, $$4);
/*  963 */         placeBlock($$0, Blocks.COBBLESTONE.defaultBlockState(), 6, $$10, 6, $$4);
/*      */       } 
/*  965 */       placeBlock($$0, Blocks.WALL_TORCH.defaultBlockState(), 5, 3, 5, $$4);
/*  966 */       for (int $$11 = 2; $$11 <= 8; $$11++) {
/*  967 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 2, 3, $$11, $$4);
/*  968 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 3, 3, $$11, $$4);
/*  969 */         if ($$11 <= 3 || $$11 >= 7) {
/*  970 */           placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 4, 3, $$11, $$4);
/*  971 */           placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 5, 3, $$11, $$4);
/*  972 */           placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 6, 3, $$11, $$4);
/*      */         } 
/*  974 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 7, 3, $$11, $$4);
/*  975 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 8, 3, $$11, $$4);
/*      */       } 
/*  977 */       BlockState $$12 = (BlockState)Blocks.LADDER.defaultBlockState().setValue((Property)LadderBlock.FACING, (Comparable)Direction.WEST);
/*  978 */       placeBlock($$0, $$12, 9, 1, 3, $$4);
/*  979 */       placeBlock($$0, $$12, 9, 2, 3, $$4);
/*  980 */       placeBlock($$0, $$12, 9, 3, 3, $$4);
/*      */       
/*  982 */       createChest($$0, $$4, $$3, 3, 4, 8, BuiltInLootTables.STRONGHOLD_CROSSING);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class PrisonHall
/*      */     extends StrongholdPiece
/*      */   {
/*      */     protected static final int WIDTH = 9;
/*      */     protected static final int HEIGHT = 5;
/*      */     protected static final int DEPTH = 11;
/*      */     
/*      */     public PrisonHall(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  995 */       super(StructurePieceType.STRONGHOLD_PRISON_HALL, $$0, $$2);
/*      */       
/*  997 */       setOrientation($$3);
/*  998 */       this.entryDoor = randomSmallDoor($$1);
/*      */     }
/*      */     
/*      */     public PrisonHall(CompoundTag $$0) {
/* 1002 */       super(StructurePieceType.STRONGHOLD_PRISON_HALL, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1007 */       generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*      */     }
/*      */     
/*      */     public static PrisonHall createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 1011 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 9, 5, 11, $$5);
/*      */       
/* 1013 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 1014 */         return null;
/*      */       }
/*      */       
/* 1017 */       return new PrisonHall($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1023 */       generateBox($$0, $$4, 0, 0, 0, 8, 4, 10, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/* 1025 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 1, 0);
/*      */       
/* 1027 */       generateBox($$0, $$4, 1, 1, 10, 3, 3, 10, CAVE_AIR, CAVE_AIR, false);
/*      */ 
/*      */       
/* 1030 */       generateBox($$0, $$4, 4, 1, 1, 4, 3, 1, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1031 */       generateBox($$0, $$4, 4, 1, 3, 4, 3, 3, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1032 */       generateBox($$0, $$4, 4, 1, 7, 4, 3, 7, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1033 */       generateBox($$0, $$4, 4, 1, 9, 4, 3, 9, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */ 
/*      */       
/* 1036 */       for (int $$7 = 1; $$7 <= 3; $$7++) {
/* 1037 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true)), 4, $$7, 4, $$4);
/* 1038 */         placeBlock($$0, (BlockState)((BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true)), 4, $$7, 5, $$4);
/* 1039 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true)), 4, $$7, 6, $$4);
/*      */         
/* 1041 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true)), 5, $$7, 5, $$4);
/* 1042 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true)), 6, $$7, 5, $$4);
/* 1043 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true)), 7, $$7, 5, $$4);
/*      */       } 
/*      */ 
/*      */       
/* 1047 */       placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true)), 4, 3, 2, $$4);
/* 1048 */       placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true)), 4, 3, 8, $$4);
/* 1049 */       BlockState $$8 = (BlockState)Blocks.IRON_DOOR.defaultBlockState().setValue((Property)DoorBlock.FACING, (Comparable)Direction.WEST);
/* 1050 */       BlockState $$9 = (BlockState)((BlockState)Blocks.IRON_DOOR.defaultBlockState().setValue((Property)DoorBlock.FACING, (Comparable)Direction.WEST)).setValue((Property)DoorBlock.HALF, (Comparable)DoubleBlockHalf.UPPER);
/* 1051 */       placeBlock($$0, $$8, 4, 1, 2, $$4);
/* 1052 */       placeBlock($$0, $$9, 4, 2, 2, $$4);
/* 1053 */       placeBlock($$0, $$8, 4, 1, 8, $$4);
/* 1054 */       placeBlock($$0, $$9, 4, 2, 8, $$4);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Library
/*      */     extends StrongholdPiece {
/*      */     protected static final int WIDTH = 14;
/*      */     protected static final int HEIGHT = 6;
/*      */     protected static final int TALL_HEIGHT = 11;
/*      */     protected static final int DEPTH = 15;
/*      */     private final boolean isTall;
/*      */     
/*      */     public Library(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/* 1067 */       super(StructurePieceType.STRONGHOLD_LIBRARY, $$0, $$2);
/*      */       
/* 1069 */       setOrientation($$3);
/* 1070 */       this.entryDoor = randomSmallDoor($$1);
/* 1071 */       this.isTall = ($$2.getYSpan() > 6);
/*      */     }
/*      */     
/*      */     public Library(CompoundTag $$0) {
/* 1075 */       super(StructurePieceType.STRONGHOLD_LIBRARY, $$0);
/* 1076 */       this.isTall = $$0.getBoolean("Tall");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 1081 */       super.addAdditionalSaveData($$0, $$1);
/* 1082 */       $$1.putBoolean("Tall", this.isTall);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Library createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 1087 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -4, -1, 0, 14, 11, 15, $$5);
/*      */       
/* 1089 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*      */         
/* 1091 */         $$7 = BoundingBox.orientBox($$2, $$3, $$4, -4, -1, 0, 14, 6, 15, $$5);
/*      */         
/* 1093 */         if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 1094 */           return null;
/*      */         }
/*      */       } 
/*      */       
/* 1098 */       return new Library($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1103 */       int $$7 = 11;
/* 1104 */       if (!this.isTall) {
/* 1105 */         $$7 = 6;
/*      */       }
/*      */ 
/*      */       
/* 1109 */       generateBox($$0, $$4, 0, 0, 0, 13, $$7 - 1, 14, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/* 1111 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 4, 1, 0);
/*      */ 
/*      */       
/* 1114 */       generateMaybeBox($$0, $$4, $$3, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.COBWEB.defaultBlockState(), Blocks.COBWEB.defaultBlockState(), false, false);
/*      */       
/* 1116 */       int $$8 = 1;
/* 1117 */       int $$9 = 12;
/*      */ 
/*      */       
/* 1120 */       for (int $$10 = 1; $$10 <= 13; $$10++) {
/* 1121 */         if (($$10 - 1) % 4 == 0) {
/* 1122 */           generateBox($$0, $$4, 1, 1, $$10, 1, 4, $$10, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/* 1123 */           generateBox($$0, $$4, 12, 1, $$10, 12, 4, $$10, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/*      */           
/* 1125 */           placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.EAST), 2, 3, $$10, $$4);
/* 1126 */           placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.WEST), 11, 3, $$10, $$4);
/*      */           
/* 1128 */           if (this.isTall) {
/* 1129 */             generateBox($$0, $$4, 1, 6, $$10, 1, 9, $$10, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/* 1130 */             generateBox($$0, $$4, 12, 6, $$10, 12, 9, $$10, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/*      */           } 
/*      */         } else {
/* 1133 */           generateBox($$0, $$4, 1, 1, $$10, 1, 4, $$10, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
/* 1134 */           generateBox($$0, $$4, 12, 1, $$10, 12, 4, $$10, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
/*      */           
/* 1136 */           if (this.isTall) {
/* 1137 */             generateBox($$0, $$4, 1, 6, $$10, 1, 9, $$10, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
/* 1138 */             generateBox($$0, $$4, 12, 6, $$10, 12, 9, $$10, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1144 */       for (int $$11 = 3; $$11 < 12; $$11 += 2) {
/* 1145 */         generateBox($$0, $$4, 3, 1, $$11, 4, 3, $$11, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
/* 1146 */         generateBox($$0, $$4, 6, 1, $$11, 7, 3, $$11, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
/* 1147 */         generateBox($$0, $$4, 9, 1, $$11, 10, 3, $$11, Blocks.BOOKSHELF.defaultBlockState(), Blocks.BOOKSHELF.defaultBlockState(), false);
/*      */       } 
/*      */       
/* 1150 */       if (this.isTall) {
/*      */         
/* 1152 */         generateBox($$0, $$4, 1, 5, 1, 3, 5, 13, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/* 1153 */         generateBox($$0, $$4, 10, 5, 1, 12, 5, 13, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/* 1154 */         generateBox($$0, $$4, 4, 5, 1, 9, 5, 2, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/* 1155 */         generateBox($$0, $$4, 4, 5, 12, 9, 5, 13, Blocks.OAK_PLANKS.defaultBlockState(), Blocks.OAK_PLANKS.defaultBlockState(), false);
/*      */         
/* 1157 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 9, 5, 11, $$4);
/* 1158 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 8, 5, 11, $$4);
/* 1159 */         placeBlock($$0, Blocks.OAK_PLANKS.defaultBlockState(), 9, 5, 10, $$4);
/*      */         
/* 1161 */         BlockState $$12 = (BlockState)((BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/* 1162 */         BlockState $$13 = (BlockState)((BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */ 
/*      */         
/* 1165 */         generateBox($$0, $$4, 3, 6, 3, 3, 6, 11, $$13, $$13, false);
/* 1166 */         generateBox($$0, $$4, 10, 6, 3, 10, 6, 9, $$13, $$13, false);
/* 1167 */         generateBox($$0, $$4, 4, 6, 2, 9, 6, 2, $$12, $$12, false);
/* 1168 */         generateBox($$0, $$4, 4, 6, 12, 7, 6, 12, $$12, $$12, false);
/*      */         
/* 1170 */         placeBlock($$0, (BlockState)((BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 3, 6, 2, $$4);
/* 1171 */         placeBlock($$0, (BlockState)((BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 3, 6, 12, $$4);
/* 1172 */         placeBlock($$0, (BlockState)((BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 10, 6, 2, $$4);
/*      */         
/* 1174 */         for (int $$14 = 0; $$14 <= 2; $$14++) {
/* 1175 */           placeBlock($$0, (BlockState)((BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 8 + $$14, 6, 12 - $$14, $$4);
/* 1176 */           if ($$14 != 2) {
/* 1177 */             placeBlock($$0, (BlockState)((BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 8 + $$14, 6, 11 - $$14, $$4);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1182 */         BlockState $$15 = (BlockState)Blocks.LADDER.defaultBlockState().setValue((Property)LadderBlock.FACING, (Comparable)Direction.SOUTH);
/* 1183 */         placeBlock($$0, $$15, 10, 1, 13, $$4);
/* 1184 */         placeBlock($$0, $$15, 10, 2, 13, $$4);
/* 1185 */         placeBlock($$0, $$15, 10, 3, 13, $$4);
/* 1186 */         placeBlock($$0, $$15, 10, 4, 13, $$4);
/* 1187 */         placeBlock($$0, $$15, 10, 5, 13, $$4);
/* 1188 */         placeBlock($$0, $$15, 10, 6, 13, $$4);
/* 1189 */         placeBlock($$0, $$15, 10, 7, 13, $$4);
/*      */ 
/*      */         
/* 1192 */         int $$16 = 7;
/* 1193 */         int $$17 = 7;
/* 1194 */         BlockState $$18 = (BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/* 1195 */         placeBlock($$0, $$18, 6, 9, 7, $$4);
/* 1196 */         BlockState $$19 = (BlockState)Blocks.OAK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true));
/* 1197 */         placeBlock($$0, $$19, 7, 9, 7, $$4);
/*      */         
/* 1199 */         placeBlock($$0, $$18, 6, 8, 7, $$4);
/* 1200 */         placeBlock($$0, $$19, 7, 8, 7, $$4);
/*      */         
/* 1202 */         BlockState $$20 = (BlockState)((BlockState)$$13.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*      */         
/* 1204 */         placeBlock($$0, $$20, 6, 7, 7, $$4);
/* 1205 */         placeBlock($$0, $$20, 7, 7, 7, $$4);
/*      */         
/* 1207 */         placeBlock($$0, $$18, 5, 7, 7, $$4);
/*      */         
/* 1209 */         placeBlock($$0, $$19, 8, 7, 7, $$4);
/*      */         
/* 1211 */         placeBlock($$0, (BlockState)$$18.setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true)), 6, 7, 6, $$4);
/* 1212 */         placeBlock($$0, (BlockState)$$18.setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true)), 6, 7, 8, $$4);
/*      */         
/* 1214 */         placeBlock($$0, (BlockState)$$19.setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true)), 7, 7, 6, $$4);
/* 1215 */         placeBlock($$0, (BlockState)$$19.setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true)), 7, 7, 8, $$4);
/*      */         
/* 1217 */         BlockState $$21 = Blocks.TORCH.defaultBlockState();
/* 1218 */         placeBlock($$0, $$21, 5, 8, 7, $$4);
/* 1219 */         placeBlock($$0, $$21, 8, 8, 7, $$4);
/* 1220 */         placeBlock($$0, $$21, 6, 8, 6, $$4);
/* 1221 */         placeBlock($$0, $$21, 6, 8, 8, $$4);
/* 1222 */         placeBlock($$0, $$21, 7, 8, 6, $$4);
/* 1223 */         placeBlock($$0, $$21, 7, 8, 8, $$4);
/*      */       } 
/*      */ 
/*      */       
/* 1227 */       createChest($$0, $$4, $$3, 3, 3, 5, BuiltInLootTables.STRONGHOLD_LIBRARY);
/* 1228 */       if (this.isTall) {
/* 1229 */         placeBlock($$0, CAVE_AIR, 12, 9, 1, $$4);
/* 1230 */         createChest($$0, $$4, $$3, 12, 8, 1, BuiltInLootTables.STRONGHOLD_LIBRARY);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class FiveCrossing
/*      */     extends StrongholdPiece {
/*      */     protected static final int WIDTH = 10;
/*      */     protected static final int HEIGHT = 9;
/*      */     protected static final int DEPTH = 11;
/*      */     private final boolean leftLow;
/*      */     private final boolean leftHigh;
/*      */     private final boolean rightLow;
/*      */     private final boolean rightHigh;
/*      */     
/*      */     public FiveCrossing(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/* 1246 */       super(StructurePieceType.STRONGHOLD_FIVE_CROSSING, $$0, $$2);
/*      */       
/* 1248 */       setOrientation($$3);
/* 1249 */       this.entryDoor = randomSmallDoor($$1);
/*      */       
/* 1251 */       this.leftLow = $$1.nextBoolean();
/* 1252 */       this.leftHigh = $$1.nextBoolean();
/* 1253 */       this.rightLow = $$1.nextBoolean();
/* 1254 */       this.rightHigh = ($$1.nextInt(3) > 0);
/*      */     }
/*      */     
/*      */     public FiveCrossing(CompoundTag $$0) {
/* 1258 */       super(StructurePieceType.STRONGHOLD_FIVE_CROSSING, $$0);
/* 1259 */       this.leftLow = $$0.getBoolean("leftLow");
/* 1260 */       this.leftHigh = $$0.getBoolean("leftHigh");
/* 1261 */       this.rightLow = $$0.getBoolean("rightLow");
/* 1262 */       this.rightHigh = $$0.getBoolean("rightHigh");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 1267 */       super.addAdditionalSaveData($$0, $$1);
/* 1268 */       $$1.putBoolean("leftLow", this.leftLow);
/* 1269 */       $$1.putBoolean("leftHigh", this.leftHigh);
/* 1270 */       $$1.putBoolean("rightLow", this.rightLow);
/* 1271 */       $$1.putBoolean("rightHigh", this.rightHigh);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1276 */       int $$3 = 3;
/* 1277 */       int $$4 = 5;
/*      */       
/* 1279 */       Direction $$5 = getOrientation();
/* 1280 */       if ($$5 == Direction.WEST || $$5 == Direction.NORTH) {
/* 1281 */         $$3 = 8 - $$3;
/* 1282 */         $$4 = 8 - $$4;
/*      */       } 
/*      */       
/* 1285 */       generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 5, 1);
/* 1286 */       if (this.leftLow) {
/* 1287 */         generateSmallDoorChildLeft((StrongholdPieces.StartPiece)$$0, $$1, $$2, $$3, 1);
/*      */       }
/* 1289 */       if (this.leftHigh) {
/* 1290 */         generateSmallDoorChildLeft((StrongholdPieces.StartPiece)$$0, $$1, $$2, $$4, 7);
/*      */       }
/* 1292 */       if (this.rightLow) {
/* 1293 */         generateSmallDoorChildRight((StrongholdPieces.StartPiece)$$0, $$1, $$2, $$3, 1);
/*      */       }
/* 1295 */       if (this.rightHigh) {
/* 1296 */         generateSmallDoorChildRight((StrongholdPieces.StartPiece)$$0, $$1, $$2, $$4, 7);
/*      */       }
/*      */     }
/*      */     
/*      */     public static FiveCrossing createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 1301 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -4, -3, 0, 10, 9, 11, $$5);
/*      */       
/* 1303 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 1304 */         return null;
/*      */       }
/*      */       
/* 1307 */       return new FiveCrossing($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1313 */       generateBox($$0, $$4, 0, 0, 0, 9, 8, 10, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/* 1315 */       generateSmallDoor($$0, $$3, $$4, this.entryDoor, 4, 3, 0);
/*      */ 
/*      */       
/* 1318 */       if (this.leftLow) {
/* 1319 */         generateBox($$0, $$4, 0, 3, 1, 0, 5, 3, CAVE_AIR, CAVE_AIR, false);
/*      */       }
/* 1321 */       if (this.rightLow) {
/* 1322 */         generateBox($$0, $$4, 9, 3, 1, 9, 5, 3, CAVE_AIR, CAVE_AIR, false);
/*      */       }
/* 1324 */       if (this.leftHigh) {
/* 1325 */         generateBox($$0, $$4, 0, 5, 7, 0, 7, 9, CAVE_AIR, CAVE_AIR, false);
/*      */       }
/* 1327 */       if (this.rightHigh) {
/* 1328 */         generateBox($$0, $$4, 9, 5, 7, 9, 7, 9, CAVE_AIR, CAVE_AIR, false);
/*      */       }
/* 1330 */       generateBox($$0, $$4, 5, 1, 10, 7, 3, 10, CAVE_AIR, CAVE_AIR, false);
/*      */ 
/*      */       
/* 1333 */       generateBox($$0, $$4, 1, 2, 1, 8, 2, 6, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/* 1335 */       generateBox($$0, $$4, 4, 1, 5, 4, 4, 9, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1336 */       generateBox($$0, $$4, 8, 1, 5, 8, 4, 9, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/* 1338 */       generateBox($$0, $$4, 1, 4, 7, 3, 4, 9, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */ 
/*      */       
/* 1341 */       generateBox($$0, $$4, 1, 3, 5, 3, 3, 6, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1342 */       generateBox($$0, $$4, 1, 3, 4, 3, 3, 4, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
/* 1343 */       generateBox($$0, $$4, 1, 4, 6, 3, 4, 6, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1346 */       generateBox($$0, $$4, 5, 1, 7, 7, 1, 8, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1347 */       generateBox($$0, $$4, 5, 1, 9, 7, 1, 9, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
/* 1348 */       generateBox($$0, $$4, 5, 2, 7, 7, 2, 7, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1351 */       generateBox($$0, $$4, 4, 5, 7, 4, 5, 9, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
/* 1352 */       generateBox($$0, $$4, 8, 5, 7, 8, 5, 9, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), false);
/* 1353 */       generateBox($$0, $$4, 5, 5, 7, 7, 5, 9, (BlockState)Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue((Property)SlabBlock.TYPE, (Comparable)SlabType.DOUBLE), (BlockState)Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue((Property)SlabBlock.TYPE, (Comparable)SlabType.DOUBLE), false);
/* 1354 */       placeBlock($$0, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.SOUTH), 6, 5, 6, $$4);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class PortalRoom
/*      */     extends StrongholdPiece {
/*      */     protected static final int WIDTH = 11;
/*      */     protected static final int HEIGHT = 8;
/*      */     protected static final int DEPTH = 16;
/*      */     private boolean hasPlacedSpawner;
/*      */     
/*      */     public PortalRoom(int $$0, BoundingBox $$1, Direction $$2) {
/* 1366 */       super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, $$0, $$1);
/*      */       
/* 1368 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public PortalRoom(CompoundTag $$0) {
/* 1372 */       super(StructurePieceType.STRONGHOLD_PORTAL_ROOM, $$0);
/* 1373 */       this.hasPlacedSpawner = $$0.getBoolean("Mob");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 1378 */       super.addAdditionalSaveData($$0, $$1);
/* 1379 */       $$1.putBoolean("Mob", this.hasPlacedSpawner);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1384 */       if ($$0 != null) {
/* 1385 */         ((StrongholdPieces.StartPiece)$$0).portalRoomPiece = this;
/*      */       }
/*      */     }
/*      */     
/*      */     public static PortalRoom createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/* 1390 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -4, -1, 0, 11, 8, 16, $$4);
/*      */       
/* 1392 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/* 1393 */         return null;
/*      */       }
/*      */       
/* 1396 */       return new PortalRoom($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1402 */       generateBox($$0, $$4, 0, 0, 0, 10, 7, 15, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */       
/* 1404 */       generateSmallDoor($$0, $$3, $$4, StrongholdPieces.StrongholdPiece.SmallDoorType.GRATES, 4, 1, 0);
/*      */ 
/*      */       
/* 1407 */       int $$7 = 6;
/* 1408 */       generateBox($$0, $$4, 1, 6, 1, 1, 6, 14, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1409 */       generateBox($$0, $$4, 9, 6, 1, 9, 6, 14, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1410 */       generateBox($$0, $$4, 2, 6, 1, 8, 6, 2, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1411 */       generateBox($$0, $$4, 2, 6, 14, 8, 6, 14, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*      */ 
/*      */       
/* 1414 */       generateBox($$0, $$4, 1, 1, 1, 2, 1, 4, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1415 */       generateBox($$0, $$4, 8, 1, 1, 9, 1, 4, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1416 */       generateBox($$0, $$4, 1, 1, 1, 1, 1, 3, Blocks.LAVA.defaultBlockState(), Blocks.LAVA.defaultBlockState(), false);
/* 1417 */       generateBox($$0, $$4, 9, 1, 1, 9, 1, 3, Blocks.LAVA.defaultBlockState(), Blocks.LAVA.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1420 */       generateBox($$0, $$4, 3, 1, 8, 7, 1, 12, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1421 */       generateBox($$0, $$4, 4, 1, 9, 6, 1, 11, Blocks.LAVA.defaultBlockState(), Blocks.LAVA.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1424 */       BlockState $$8 = (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.NORTH, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.SOUTH, Boolean.valueOf(true));
/* 1425 */       BlockState $$9 = (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true));
/* 1426 */       for (int $$10 = 3; $$10 < 14; $$10 += 2) {
/* 1427 */         generateBox($$0, $$4, 0, 3, $$10, 0, 4, $$10, $$8, $$8, false);
/* 1428 */         generateBox($$0, $$4, 10, 3, $$10, 10, 4, $$10, $$8, $$8, false);
/*      */       } 
/* 1430 */       for (int $$11 = 2; $$11 < 9; $$11 += 2) {
/* 1431 */         generateBox($$0, $$4, $$11, 3, 15, $$11, 4, 15, $$9, $$9, false);
/*      */       }
/*      */ 
/*      */       
/* 1435 */       BlockState $$12 = (BlockState)Blocks.STONE_BRICK_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.NORTH);
/* 1436 */       generateBox($$0, $$4, 4, 1, 5, 6, 1, 7, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1437 */       generateBox($$0, $$4, 4, 2, 6, 6, 2, 7, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1438 */       generateBox($$0, $$4, 4, 3, 7, 6, 3, 7, false, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/* 1439 */       for (int $$13 = 4; $$13 <= 6; $$13++) {
/* 1440 */         placeBlock($$0, $$12, $$13, 1, 4, $$4);
/* 1441 */         placeBlock($$0, $$12, $$13, 2, 5, $$4);
/* 1442 */         placeBlock($$0, $$12, $$13, 3, 6, $$4);
/*      */       } 
/*      */       
/* 1445 */       BlockState $$14 = (BlockState)Blocks.END_PORTAL_FRAME.defaultBlockState().setValue((Property)EndPortalFrameBlock.FACING, (Comparable)Direction.NORTH);
/* 1446 */       BlockState $$15 = (BlockState)Blocks.END_PORTAL_FRAME.defaultBlockState().setValue((Property)EndPortalFrameBlock.FACING, (Comparable)Direction.SOUTH);
/* 1447 */       BlockState $$16 = (BlockState)Blocks.END_PORTAL_FRAME.defaultBlockState().setValue((Property)EndPortalFrameBlock.FACING, (Comparable)Direction.EAST);
/* 1448 */       BlockState $$17 = (BlockState)Blocks.END_PORTAL_FRAME.defaultBlockState().setValue((Property)EndPortalFrameBlock.FACING, (Comparable)Direction.WEST);
/*      */       
/* 1450 */       boolean $$18 = true;
/* 1451 */       boolean[] $$19 = new boolean[12];
/* 1452 */       for (int $$20 = 0; $$20 < $$19.length; $$20++) {
/* 1453 */         $$19[$$20] = ($$3.nextFloat() > 0.9F);
/* 1454 */         $$18 &= $$19[$$20];
/*      */       } 
/*      */       
/* 1457 */       placeBlock($$0, (BlockState)$$14.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[0])), 4, 3, 8, $$4);
/* 1458 */       placeBlock($$0, (BlockState)$$14.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[1])), 5, 3, 8, $$4);
/* 1459 */       placeBlock($$0, (BlockState)$$14.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[2])), 6, 3, 8, $$4);
/* 1460 */       placeBlock($$0, (BlockState)$$15.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[3])), 4, 3, 12, $$4);
/* 1461 */       placeBlock($$0, (BlockState)$$15.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[4])), 5, 3, 12, $$4);
/* 1462 */       placeBlock($$0, (BlockState)$$15.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[5])), 6, 3, 12, $$4);
/* 1463 */       placeBlock($$0, (BlockState)$$16.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[6])), 3, 3, 9, $$4);
/* 1464 */       placeBlock($$0, (BlockState)$$16.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[7])), 3, 3, 10, $$4);
/* 1465 */       placeBlock($$0, (BlockState)$$16.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[8])), 3, 3, 11, $$4);
/* 1466 */       placeBlock($$0, (BlockState)$$17.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[9])), 7, 3, 9, $$4);
/* 1467 */       placeBlock($$0, (BlockState)$$17.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[10])), 7, 3, 10, $$4);
/* 1468 */       placeBlock($$0, (BlockState)$$17.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf($$19[11])), 7, 3, 11, $$4);
/*      */       
/* 1470 */       if ($$18) {
/* 1471 */         BlockState $$21 = Blocks.END_PORTAL.defaultBlockState();
/*      */         
/* 1473 */         placeBlock($$0, $$21, 4, 3, 9, $$4);
/* 1474 */         placeBlock($$0, $$21, 5, 3, 9, $$4);
/* 1475 */         placeBlock($$0, $$21, 6, 3, 9, $$4);
/* 1476 */         placeBlock($$0, $$21, 4, 3, 10, $$4);
/* 1477 */         placeBlock($$0, $$21, 5, 3, 10, $$4);
/* 1478 */         placeBlock($$0, $$21, 6, 3, 10, $$4);
/* 1479 */         placeBlock($$0, $$21, 4, 3, 11, $$4);
/* 1480 */         placeBlock($$0, $$21, 5, 3, 11, $$4);
/* 1481 */         placeBlock($$0, $$21, 6, 3, 11, $$4);
/*      */       } 
/*      */       
/* 1484 */       if (!this.hasPlacedSpawner) {
/* 1485 */         BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(5, 3, 6);
/* 1486 */         if ($$4.isInside((Vec3i)mutableBlockPos)) {
/* 1487 */           this.hasPlacedSpawner = true;
/* 1488 */           $$0.setBlock((BlockPos)mutableBlockPos, Blocks.SPAWNER.defaultBlockState(), 2);
/*      */           
/* 1490 */           BlockEntity $$23 = $$0.getBlockEntity((BlockPos)mutableBlockPos);
/* 1491 */           if ($$23 instanceof SpawnerBlockEntity) { SpawnerBlockEntity $$24 = (SpawnerBlockEntity)$$23;
/* 1492 */             $$24.setEntityId(EntityType.SILVERFISH, $$3); }
/*      */         
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SmoothStoneSelector
/*      */     extends StructurePiece.BlockSelector {
/*      */     public void next(RandomSource $$0, int $$1, int $$2, int $$3, boolean $$4) {
/* 1502 */       if ($$4) {
/* 1503 */         float $$5 = $$0.nextFloat();
/* 1504 */         if ($$5 < 0.2F) {
/* 1505 */           this.next = Blocks.CRACKED_STONE_BRICKS.defaultBlockState();
/* 1506 */         } else if ($$5 < 0.5F) {
/* 1507 */           this.next = Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
/* 1508 */         } else if ($$5 < 0.55F) {
/* 1509 */           this.next = Blocks.INFESTED_STONE_BRICKS.defaultBlockState();
/*      */         } else {
/* 1511 */           this.next = Blocks.STONE_BRICKS.defaultBlockState();
/*      */         } 
/*      */       } else {
/* 1514 */         this.next = Blocks.CAVE_AIR.defaultBlockState();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/* 1519 */   static final SmoothStoneSelector SMOOTH_STONE_SELECTOR = new SmoothStoneSelector();
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\StrongholdPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */