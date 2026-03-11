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
/*      */ import net.minecraft.world.level.block.FenceBlock;
/*      */ import net.minecraft.world.level.block.StairBlock;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*      */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*      */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*      */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*      */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*      */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.material.Fluids;
/*      */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*      */ 
/*      */ public class NetherFortressPieces {
/*      */   private static final int MAX_DEPTH = 30;
/*      */   private static final int LOWEST_Y_POSITION = 10;
/*      */   public static final int MAGIC_START_Y = 64;
/*      */   
/*      */   private static class PieceWeight {
/*      */     public final Class<? extends NetherFortressPieces.NetherBridgePiece> pieceClass;
/*      */     public final int weight;
/*      */     public int placeCount;
/*      */     public final int maxPlaceCount;
/*      */     public final boolean allowInRow;
/*      */     
/*      */     public PieceWeight(Class<? extends NetherFortressPieces.NetherBridgePiece> $$0, int $$1, int $$2, boolean $$3) {
/*   45 */       this.pieceClass = $$0;
/*   46 */       this.weight = $$1;
/*   47 */       this.maxPlaceCount = $$2;
/*   48 */       this.allowInRow = $$3;
/*      */     }
/*      */     
/*      */     public PieceWeight(Class<? extends NetherFortressPieces.NetherBridgePiece> $$0, int $$1, int $$2) {
/*   52 */       this($$0, $$1, $$2, false);
/*      */     }
/*      */     
/*      */     public boolean doPlace(int $$0) {
/*   56 */       return (this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount);
/*      */     }
/*      */     
/*      */     public boolean isValid() {
/*   60 */       return (this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount);
/*      */     }
/*      */   }
/*      */   
/*   64 */   static final PieceWeight[] BRIDGE_PIECE_WEIGHTS = new PieceWeight[] { new PieceWeight((Class)BridgeStraight.class, 30, 0, true), new PieceWeight((Class)BridgeCrossing.class, 10, 4), new PieceWeight((Class)RoomCrossing.class, 10, 4), new PieceWeight((Class)StairsRoom.class, 10, 3), new PieceWeight((Class)MonsterThrone.class, 5, 2), new PieceWeight((Class)CastleEntrance.class, 5, 1) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   72 */   static final PieceWeight[] CASTLE_PIECE_WEIGHTS = new PieceWeight[] { new PieceWeight((Class)CastleSmallCorridorPiece.class, 25, 0, true), new PieceWeight((Class)CastleSmallCorridorCrossingPiece.class, 15, 5), new PieceWeight((Class)CastleSmallCorridorRightTurnPiece.class, 5, 10), new PieceWeight((Class)CastleSmallCorridorLeftTurnPiece.class, 5, 10), new PieceWeight((Class)CastleCorridorStairsPiece.class, 10, 3, true), new PieceWeight((Class)CastleCorridorTBalconyPiece.class, 7, 2), new PieceWeight((Class)CastleStalkRoom.class, 5, 2) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static NetherBridgePiece findAndCreateBridgePieceFactory(PieceWeight $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, int $$5, Direction $$6, int $$7) {
/*   83 */     Class<? extends NetherBridgePiece> $$8 = $$0.pieceClass;
/*   84 */     NetherBridgePiece $$9 = null;
/*      */     
/*   86 */     if ($$8 == BridgeStraight.class) {
/*   87 */       $$9 = BridgeStraight.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*   88 */     } else if ($$8 == BridgeCrossing.class) {
/*   89 */       $$9 = BridgeCrossing.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*   90 */     } else if ($$8 == RoomCrossing.class) {
/*   91 */       $$9 = RoomCrossing.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*   92 */     } else if ($$8 == StairsRoom.class) {
/*   93 */       $$9 = StairsRoom.createPiece($$1, $$3, $$4, $$5, $$7, $$6);
/*   94 */     } else if ($$8 == MonsterThrone.class) {
/*   95 */       $$9 = MonsterThrone.createPiece($$1, $$3, $$4, $$5, $$7, $$6);
/*   96 */     } else if ($$8 == CastleEntrance.class) {
/*   97 */       $$9 = CastleEntrance.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*   98 */     } else if ($$8 == CastleSmallCorridorPiece.class) {
/*   99 */       $$9 = CastleSmallCorridorPiece.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*  100 */     } else if ($$8 == CastleSmallCorridorRightTurnPiece.class) {
/*  101 */       $$9 = CastleSmallCorridorRightTurnPiece.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  102 */     } else if ($$8 == CastleSmallCorridorLeftTurnPiece.class) {
/*  103 */       $$9 = CastleSmallCorridorLeftTurnPiece.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  104 */     } else if ($$8 == CastleCorridorStairsPiece.class) {
/*  105 */       $$9 = CastleCorridorStairsPiece.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*  106 */     } else if ($$8 == CastleCorridorTBalconyPiece.class) {
/*  107 */       $$9 = CastleCorridorTBalconyPiece.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*  108 */     } else if ($$8 == CastleSmallCorridorCrossingPiece.class) {
/*  109 */       $$9 = CastleSmallCorridorCrossingPiece.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*  110 */     } else if ($$8 == CastleStalkRoom.class) {
/*  111 */       $$9 = CastleStalkRoom.createPiece($$1, $$3, $$4, $$5, $$6, $$7);
/*      */     } 
/*  113 */     return $$9;
/*      */   }
/*      */   
/*      */   private static abstract class NetherBridgePiece extends StructurePiece {
/*      */     protected NetherBridgePiece(StructurePieceType $$0, int $$1, BoundingBox $$2) {
/*  118 */       super($$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     public NetherBridgePiece(StructurePieceType $$0, CompoundTag $$1) {
/*  122 */       super($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {}
/*      */ 
/*      */     
/*      */     private int updatePieceWeight(List<NetherFortressPieces.PieceWeight> $$0) {
/*  130 */       boolean $$1 = false;
/*  131 */       int $$2 = 0;
/*  132 */       for (NetherFortressPieces.PieceWeight $$3 : $$0) {
/*  133 */         if ($$3.maxPlaceCount > 0 && $$3.placeCount < $$3.maxPlaceCount) {
/*  134 */           $$1 = true;
/*      */         }
/*  136 */         $$2 += $$3.weight;
/*      */       } 
/*  138 */       return $$1 ? $$2 : -1;
/*      */     }
/*      */     
/*      */     private NetherBridgePiece generatePiece(NetherFortressPieces.StartPiece $$0, List<NetherFortressPieces.PieceWeight> $$1, StructurePieceAccessor $$2, RandomSource $$3, int $$4, int $$5, int $$6, Direction $$7, int $$8) {
/*  142 */       int $$9 = updatePieceWeight($$1);
/*  143 */       boolean $$10 = ($$9 > 0 && $$8 <= 30);
/*      */       
/*  145 */       int $$11 = 0;
/*  146 */       while ($$11 < 5 && $$10) {
/*  147 */         $$11++;
/*      */         
/*  149 */         int $$12 = $$3.nextInt($$9);
/*  150 */         for (NetherFortressPieces.PieceWeight $$13 : $$1) {
/*  151 */           $$12 -= $$13.weight;
/*  152 */           if ($$12 < 0) {
/*  153 */             if (!$$13.doPlace($$8) || ($$13 == $$0.previousPiece && !$$13.allowInRow)) {
/*      */               break;
/*      */             }
/*      */             
/*  157 */             NetherBridgePiece $$14 = NetherFortressPieces.findAndCreateBridgePieceFactory($$13, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*  158 */             if ($$14 != null) {
/*  159 */               $$13.placeCount++;
/*  160 */               $$0.previousPiece = $$13;
/*      */               
/*  162 */               if (!$$13.isValid()) {
/*  163 */                 $$1.remove($$13);
/*      */               }
/*  165 */               return $$14;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  170 */       return NetherFortressPieces.BridgeEndFiller.createPiece($$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*      */     }
/*      */     
/*      */     private StructurePiece generateAndAddPiece(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, int $$5, @Nullable Direction $$6, int $$7, boolean $$8) {
/*  174 */       if (Math.abs($$3 - $$0.getBoundingBox().minX()) > 112 || Math.abs($$5 - $$0.getBoundingBox().minZ()) > 112) {
/*  175 */         return NetherFortressPieces.BridgeEndFiller.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*      */       }
/*  177 */       List<NetherFortressPieces.PieceWeight> $$9 = $$0.availableBridgePieces;
/*  178 */       if ($$8) {
/*  179 */         $$9 = $$0.availableCastlePieces;
/*      */       }
/*  181 */       StructurePiece $$10 = generatePiece($$0, $$9, $$1, $$2, $$3, $$4, $$5, $$6, $$7 + 1);
/*  182 */       if ($$10 != null) {
/*  183 */         $$1.addPiece($$10);
/*  184 */         $$0.pendingChildren.add($$10);
/*      */       } 
/*  186 */       return $$10;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     protected StructurePiece generateChildForward(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, boolean $$5) {
/*  191 */       Direction $$6 = getOrientation();
/*  192 */       if ($$6 != null) {
/*  193 */         switch ($$6) {
/*      */           case NORTH:
/*  195 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$3, this.boundingBox.minY() + $$4, this.boundingBox.minZ() - 1, $$6, getGenDepth(), $$5);
/*      */           case SOUTH:
/*  197 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$3, this.boundingBox.minY() + $$4, this.boundingBox.maxZ() + 1, $$6, getGenDepth(), $$5);
/*      */           case WEST:
/*  199 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$4, this.boundingBox.minZ() + $$3, $$6, getGenDepth(), $$5);
/*      */           case EAST:
/*  201 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$4, this.boundingBox.minZ() + $$3, $$6, getGenDepth(), $$5);
/*      */         } 
/*      */       }
/*  204 */       return null;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     protected StructurePiece generateChildLeft(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, boolean $$5) {
/*  209 */       Direction $$6 = getOrientation();
/*  210 */       if ($$6 != null) {
/*  211 */         switch ($$6) {
/*      */           case NORTH:
/*  213 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.WEST, getGenDepth(), $$5);
/*      */           case SOUTH:
/*  215 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.WEST, getGenDepth(), $$5);
/*      */           case WEST:
/*  217 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.minZ() - 1, Direction.NORTH, getGenDepth(), $$5);
/*      */           case EAST:
/*  219 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.minZ() - 1, Direction.NORTH, getGenDepth(), $$5);
/*      */         } 
/*      */       }
/*  222 */       return null;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     protected StructurePiece generateChildRight(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, boolean $$5) {
/*  227 */       Direction $$6 = getOrientation();
/*  228 */       if ($$6 != null) {
/*  229 */         switch ($$6) {
/*      */           case NORTH:
/*  231 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.EAST, getGenDepth(), $$5);
/*      */           case SOUTH:
/*  233 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.EAST, getGenDepth(), $$5);
/*      */           case WEST:
/*  235 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.maxZ() + 1, Direction.SOUTH, getGenDepth(), $$5);
/*      */           case EAST:
/*  237 */             return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.maxZ() + 1, Direction.SOUTH, getGenDepth(), $$5);
/*      */         } 
/*      */       }
/*  240 */       return null;
/*      */     }
/*      */     
/*      */     protected static boolean isOkBox(BoundingBox $$0) {
/*  244 */       return ($$0 != null && $$0.minY() > 10);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class StartPiece
/*      */     extends BridgeCrossing
/*      */   {
/*      */     public NetherFortressPieces.PieceWeight previousPiece;
/*      */     public List<NetherFortressPieces.PieceWeight> availableBridgePieces;
/*      */     public List<NetherFortressPieces.PieceWeight> availableCastlePieces;
/*  255 */     public final List<StructurePiece> pendingChildren = Lists.newArrayList();
/*      */     
/*      */     public StartPiece(RandomSource $$0, int $$1, int $$2) {
/*  258 */       super($$1, $$2, getRandomHorizontalDirection($$0));
/*      */       
/*  260 */       this.availableBridgePieces = Lists.newArrayList();
/*  261 */       for (NetherFortressPieces.PieceWeight $$3 : NetherFortressPieces.BRIDGE_PIECE_WEIGHTS) {
/*  262 */         $$3.placeCount = 0;
/*  263 */         this.availableBridgePieces.add($$3);
/*      */       } 
/*      */       
/*  266 */       this.availableCastlePieces = Lists.newArrayList();
/*  267 */       for (NetherFortressPieces.PieceWeight $$4 : NetherFortressPieces.CASTLE_PIECE_WEIGHTS) {
/*  268 */         $$4.placeCount = 0;
/*  269 */         this.availableCastlePieces.add($$4);
/*      */       } 
/*      */     }
/*      */     
/*      */     public StartPiece(CompoundTag $$0) {
/*  274 */       super(StructurePieceType.NETHER_FORTRESS_START, $$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class BridgeStraight extends NetherBridgePiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 10;
/*      */     private static final int DEPTH = 19;
/*      */     
/*      */     public BridgeStraight(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  284 */       super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT, $$0, $$2);
/*      */       
/*  286 */       setOrientation($$3);
/*      */     }
/*      */     
/*      */     public BridgeStraight(CompoundTag $$0) {
/*  290 */       super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  295 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 1, 3, false);
/*      */     }
/*      */     
/*      */     public static BridgeStraight createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  299 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -3, 0, 5, 10, 19, $$5);
/*      */       
/*  301 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  302 */         return null;
/*      */       }
/*      */       
/*  305 */       return new BridgeStraight($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  311 */       generateBox($$0, $$4, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  313 */       generateBox($$0, $$4, 1, 5, 0, 3, 7, 18, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  316 */       generateBox($$0, $$4, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  317 */       generateBox($$0, $$4, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  320 */       generateBox($$0, $$4, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  321 */       generateBox($$0, $$4, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  322 */       generateBox($$0, $$4, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  323 */       generateBox($$0, $$4, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  325 */       for (int $$7 = 0; $$7 <= 4; $$7++) {
/*  326 */         for (int $$8 = 0; $$8 <= 2; $$8++) {
/*  327 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$7, -1, $$8, $$4);
/*  328 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$7, -1, 18 - $$8, $$4);
/*      */         } 
/*      */       } 
/*      */       
/*  332 */       BlockState $$9 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*  333 */       BlockState $$10 = (BlockState)$$9.setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*  334 */       BlockState $$11 = (BlockState)$$9.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true));
/*  335 */       generateBox($$0, $$4, 0, 1, 1, 0, 4, 1, $$10, $$10, false);
/*  336 */       generateBox($$0, $$4, 0, 3, 4, 0, 4, 4, $$10, $$10, false);
/*  337 */       generateBox($$0, $$4, 0, 3, 14, 0, 4, 14, $$10, $$10, false);
/*  338 */       generateBox($$0, $$4, 0, 1, 17, 0, 4, 17, $$10, $$10, false);
/*  339 */       generateBox($$0, $$4, 4, 1, 1, 4, 4, 1, $$11, $$11, false);
/*  340 */       generateBox($$0, $$4, 4, 3, 4, 4, 4, 4, $$11, $$11, false);
/*  341 */       generateBox($$0, $$4, 4, 3, 14, 4, 4, 14, $$11, $$11, false);
/*  342 */       generateBox($$0, $$4, 4, 1, 17, 4, 4, 17, $$11, $$11, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class BridgeEndFiller
/*      */     extends NetherBridgePiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 10;
/*      */     private static final int DEPTH = 8;
/*      */     private final int selfSeed;
/*      */     
/*      */     public BridgeEndFiller(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  354 */       super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END_FILLER, $$0, $$2);
/*      */       
/*  356 */       setOrientation($$3);
/*  357 */       this.selfSeed = $$1.nextInt();
/*      */     }
/*      */     
/*      */     public BridgeEndFiller(CompoundTag $$0) {
/*  361 */       super(StructurePieceType.NETHER_FORTRESS_BRIDGE_END_FILLER, $$0);
/*  362 */       this.selfSeed = $$0.getInt("Seed");
/*      */     }
/*      */     
/*      */     public static BridgeEndFiller createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  366 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -3, 0, 5, 10, 8, $$5);
/*      */       
/*  368 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  369 */         return null;
/*      */       }
/*      */       
/*  372 */       return new BridgeEndFiller($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  377 */       super.addAdditionalSaveData($$0, $$1);
/*      */       
/*  379 */       $$1.putInt("Seed", this.selfSeed);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  384 */       RandomSource $$7 = RandomSource.create(this.selfSeed);
/*      */ 
/*      */       
/*  387 */       for (int $$8 = 0; $$8 <= 4; $$8++) {
/*  388 */         for (int $$9 = 3; $$9 <= 4; $$9++) {
/*  389 */           int $$10 = $$7.nextInt(8);
/*  390 */           generateBox($$0, $$4, $$8, $$9, 0, $$8, $$9, $$10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  396 */       int $$11 = $$7.nextInt(8);
/*  397 */       generateBox($$0, $$4, 0, 5, 0, 0, 5, $$11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  400 */       int $$12 = $$7.nextInt(8);
/*  401 */       generateBox($$0, $$4, 4, 5, 0, 4, 5, $$12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */ 
/*      */       
/*  405 */       for (int $$13 = 0; $$13 <= 4; $$13++) {
/*  406 */         int $$14 = $$7.nextInt(5);
/*  407 */         generateBox($$0, $$4, $$13, 2, 0, $$13, 2, $$14, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       } 
/*  409 */       for (int $$15 = 0; $$15 <= 4; $$15++) {
/*  410 */         for (int $$16 = 0; $$16 <= 1; $$16++) {
/*  411 */           int $$17 = $$7.nextInt(3);
/*  412 */           generateBox($$0, $$4, $$15, $$16, 0, $$15, $$16, $$17, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class BridgeCrossing extends NetherBridgePiece {
/*      */     private static final int WIDTH = 19;
/*      */     private static final int HEIGHT = 10;
/*      */     private static final int DEPTH = 19;
/*      */     
/*      */     public BridgeCrossing(int $$0, BoundingBox $$1, Direction $$2) {
/*  424 */       super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, $$0, $$1);
/*      */       
/*  426 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     protected BridgeCrossing(int $$0, int $$1, Direction $$2) {
/*  430 */       super(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, 0, StructurePiece.makeBoundingBox($$0, 64, $$1, $$2, 19, 10, 19));
/*      */       
/*  432 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     protected BridgeCrossing(StructurePieceType $$0, CompoundTag $$1) {
/*  436 */       super($$0, $$1);
/*      */     }
/*      */     
/*      */     public BridgeCrossing(CompoundTag $$0) {
/*  440 */       this(StructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  445 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 8, 3, false);
/*  446 */       generateChildLeft((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 3, 8, false);
/*  447 */       generateChildRight((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 3, 8, false);
/*      */     }
/*      */     
/*      */     public static BridgeCrossing createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/*  451 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -8, -3, 0, 19, 10, 19, $$4);
/*      */       
/*  453 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/*  454 */         return null;
/*      */       }
/*      */       
/*  457 */       return new BridgeCrossing($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  463 */       generateBox($$0, $$4, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  464 */       generateBox($$0, $$4, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  466 */       generateBox($$0, $$4, 8, 5, 0, 10, 7, 18, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*  467 */       generateBox($$0, $$4, 0, 5, 8, 18, 7, 10, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */       
/*  469 */       generateBox($$0, $$4, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  470 */       generateBox($$0, $$4, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  471 */       generateBox($$0, $$4, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  472 */       generateBox($$0, $$4, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  473 */       generateBox($$0, $$4, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  474 */       generateBox($$0, $$4, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  475 */       generateBox($$0, $$4, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  476 */       generateBox($$0, $$4, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  479 */       generateBox($$0, $$4, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  480 */       generateBox($$0, $$4, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  481 */       generateBox($$0, $$4, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  482 */       generateBox($$0, $$4, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  483 */       for (int $$7 = 7; $$7 <= 11; $$7++) {
/*  484 */         for (int $$8 = 0; $$8 <= 2; $$8++) {
/*  485 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$7, -1, $$8, $$4);
/*  486 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$7, -1, 18 - $$8, $$4);
/*      */         } 
/*      */       } 
/*      */       
/*  490 */       generateBox($$0, $$4, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  491 */       generateBox($$0, $$4, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  492 */       generateBox($$0, $$4, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  493 */       generateBox($$0, $$4, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  494 */       for (int $$9 = 0; $$9 <= 2; $$9++) {
/*  495 */         for (int $$10 = 7; $$10 <= 11; $$10++) {
/*  496 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$9, -1, $$10, $$4);
/*  497 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 18 - $$9, -1, $$10, $$4);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RoomCrossing extends NetherBridgePiece {
/*      */     private static final int WIDTH = 7;
/*      */     private static final int HEIGHT = 9;
/*      */     private static final int DEPTH = 7;
/*      */     
/*      */     public RoomCrossing(int $$0, BoundingBox $$1, Direction $$2) {
/*  509 */       super(StructurePieceType.NETHER_FORTRESS_ROOM_CROSSING, $$0, $$1);
/*      */       
/*  511 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public RoomCrossing(CompoundTag $$0) {
/*  515 */       super(StructurePieceType.NETHER_FORTRESS_ROOM_CROSSING, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  520 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 2, 0, false);
/*  521 */       generateChildLeft((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, 2, false);
/*  522 */       generateChildRight((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, 2, false);
/*      */     }
/*      */     
/*      */     public static RoomCrossing createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/*  526 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -2, 0, 0, 7, 9, 7, $$4);
/*      */       
/*  528 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/*  529 */         return null;
/*      */       }
/*      */       
/*  532 */       return new RoomCrossing($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  538 */       generateBox($$0, $$4, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  540 */       generateBox($$0, $$4, 0, 2, 0, 6, 7, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  543 */       generateBox($$0, $$4, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  544 */       generateBox($$0, $$4, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  545 */       generateBox($$0, $$4, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  546 */       generateBox($$0, $$4, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  547 */       generateBox($$0, $$4, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  548 */       generateBox($$0, $$4, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  549 */       generateBox($$0, $$4, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  550 */       generateBox($$0, $$4, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  553 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*  554 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */       
/*  556 */       generateBox($$0, $$4, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  557 */       generateBox($$0, $$4, 2, 5, 0, 4, 5, 0, $$7, $$7, false);
/*  558 */       generateBox($$0, $$4, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  559 */       generateBox($$0, $$4, 2, 5, 6, 4, 5, 6, $$7, $$7, false);
/*  560 */       generateBox($$0, $$4, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  561 */       generateBox($$0, $$4, 0, 5, 2, 0, 5, 4, $$8, $$8, false);
/*  562 */       generateBox($$0, $$4, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  563 */       generateBox($$0, $$4, 6, 5, 2, 6, 5, 4, $$8, $$8, false);
/*      */ 
/*      */       
/*  566 */       for (int $$9 = 0; $$9 <= 6; $$9++) {
/*  567 */         for (int $$10 = 0; $$10 <= 6; $$10++)
/*  568 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$9, -1, $$10, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class StairsRoom
/*      */     extends NetherBridgePiece {
/*      */     private static final int WIDTH = 7;
/*      */     private static final int HEIGHT = 11;
/*      */     private static final int DEPTH = 7;
/*      */     
/*      */     public StairsRoom(int $$0, BoundingBox $$1, Direction $$2) {
/*  580 */       super(StructurePieceType.NETHER_FORTRESS_STAIRS_ROOM, $$0, $$1);
/*      */       
/*  582 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public StairsRoom(CompoundTag $$0) {
/*  586 */       super(StructurePieceType.NETHER_FORTRESS_STAIRS_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  591 */       generateChildRight((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 6, 2, false);
/*      */     }
/*      */     
/*      */     public static StairsRoom createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, int $$4, Direction $$5) {
/*  595 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -2, 0, 0, 7, 11, 7, $$5);
/*      */       
/*  597 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/*  598 */         return null;
/*      */       }
/*      */       
/*  601 */       return new StairsRoom($$4, $$6, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  607 */       generateBox($$0, $$4, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  609 */       generateBox($$0, $$4, 0, 2, 0, 6, 10, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  612 */       generateBox($$0, $$4, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  613 */       generateBox($$0, $$4, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  614 */       generateBox($$0, $$4, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  615 */       generateBox($$0, $$4, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  616 */       generateBox($$0, $$4, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  619 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*  620 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */       
/*  622 */       generateBox($$0, $$4, 0, 3, 2, 0, 5, 4, $$8, $$8, false);
/*  623 */       generateBox($$0, $$4, 6, 3, 2, 6, 5, 2, $$8, $$8, false);
/*  624 */       generateBox($$0, $$4, 6, 3, 4, 6, 5, 4, $$8, $$8, false);
/*      */ 
/*      */       
/*  627 */       placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 5, 2, 5, $$4);
/*  628 */       generateBox($$0, $$4, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  629 */       generateBox($$0, $$4, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  630 */       generateBox($$0, $$4, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  631 */       generateBox($$0, $$4, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  634 */       generateBox($$0, $$4, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  635 */       generateBox($$0, $$4, 6, 8, 2, 6, 8, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  638 */       generateBox($$0, $$4, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  639 */       generateBox($$0, $$4, 2, 5, 0, 4, 5, 0, $$7, $$7, false);
/*      */       
/*  641 */       for (int $$9 = 0; $$9 <= 6; $$9++) {
/*  642 */         for (int $$10 = 0; $$10 <= 6; $$10++)
/*  643 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$9, -1, $$10, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MonsterThrone
/*      */     extends NetherBridgePiece
/*      */   {
/*      */     private static final int WIDTH = 7;
/*      */     private static final int HEIGHT = 8;
/*      */     private static final int DEPTH = 9;
/*      */     private boolean hasPlacedSpawner;
/*      */     
/*      */     public MonsterThrone(int $$0, BoundingBox $$1, Direction $$2) {
/*  657 */       super(StructurePieceType.NETHER_FORTRESS_MONSTER_THRONE, $$0, $$1);
/*      */       
/*  659 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public MonsterThrone(CompoundTag $$0) {
/*  663 */       super(StructurePieceType.NETHER_FORTRESS_MONSTER_THRONE, $$0);
/*  664 */       this.hasPlacedSpawner = $$0.getBoolean("Mob");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  669 */       super.addAdditionalSaveData($$0, $$1);
/*      */       
/*  671 */       $$1.putBoolean("Mob", this.hasPlacedSpawner);
/*      */     }
/*      */     
/*      */     public static MonsterThrone createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, int $$4, Direction $$5) {
/*  675 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -2, 0, 0, 7, 8, 9, $$5);
/*      */       
/*  677 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/*  678 */         return null;
/*      */       }
/*      */       
/*  681 */       return new MonsterThrone($$4, $$6, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  687 */       generateBox($$0, $$4, 0, 2, 0, 6, 7, 7, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  690 */       generateBox($$0, $$4, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  691 */       generateBox($$0, $$4, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  692 */       generateBox($$0, $$4, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  693 */       generateBox($$0, $$4, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  696 */       generateBox($$0, $$4, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  697 */       generateBox($$0, $$4, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  698 */       generateBox($$0, $$4, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  699 */       generateBox($$0, $$4, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  700 */       generateBox($$0, $$4, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  701 */       generateBox($$0, $$4, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  702 */       generateBox($$0, $$4, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  704 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*  705 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */       
/*  707 */       placeBlock($$0, (BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 1, 6, 3, $$4);
/*  708 */       placeBlock($$0, (BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 5, 6, 3, $$4);
/*      */       
/*  710 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.EAST, Boolean.valueOf(true))).setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true)), 0, 6, 3, $$4);
/*  711 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true)), 6, 6, 3, $$4);
/*      */       
/*  713 */       generateBox($$0, $$4, 0, 6, 4, 0, 6, 7, $$8, $$8, false);
/*  714 */       generateBox($$0, $$4, 6, 6, 4, 6, 6, 7, $$8, $$8, false);
/*      */       
/*  716 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.EAST, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true)), 0, 6, 8, $$4);
/*  717 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true)), 6, 6, 8, $$4);
/*      */       
/*  719 */       generateBox($$0, $$4, 1, 6, 8, 5, 6, 8, $$7, $$7, false);
/*      */       
/*  721 */       placeBlock($$0, (BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 1, 7, 8, $$4);
/*  722 */       generateBox($$0, $$4, 2, 7, 8, 4, 7, 8, $$7, $$7, false);
/*  723 */       placeBlock($$0, (BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 5, 7, 8, $$4);
/*      */       
/*  725 */       placeBlock($$0, (BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 2, 8, 8, $$4);
/*  726 */       placeBlock($$0, $$7, 3, 8, 8, $$4);
/*  727 */       placeBlock($$0, (BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 4, 8, 8, $$4);
/*      */       
/*  729 */       if (!this.hasPlacedSpawner) {
/*  730 */         BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(3, 5, 5);
/*  731 */         if ($$4.isInside((Vec3i)mutableBlockPos)) {
/*  732 */           this.hasPlacedSpawner = true;
/*  733 */           $$0.setBlock((BlockPos)mutableBlockPos, Blocks.SPAWNER.defaultBlockState(), 2);
/*      */           
/*  735 */           BlockEntity $$10 = $$0.getBlockEntity((BlockPos)mutableBlockPos);
/*  736 */           if ($$10 instanceof SpawnerBlockEntity) { SpawnerBlockEntity $$11 = (SpawnerBlockEntity)$$10;
/*  737 */             $$11.setEntityId(EntityType.BLAZE, $$3); }
/*      */         
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  743 */       for (int $$12 = 0; $$12 <= 6; $$12++) {
/*  744 */         for (int $$13 = 0; $$13 <= 6; $$13++)
/*  745 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$12, -1, $$13, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleEntrance
/*      */     extends NetherBridgePiece {
/*      */     private static final int WIDTH = 13;
/*      */     private static final int HEIGHT = 14;
/*      */     private static final int DEPTH = 13;
/*      */     
/*      */     public CastleEntrance(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/*  757 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_ENTRANCE, $$0, $$2);
/*      */       
/*  759 */       setOrientation($$3);
/*      */     }
/*      */     
/*      */     public CastleEntrance(CompoundTag $$0) {
/*  763 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_ENTRANCE, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  768 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 5, 3, true);
/*      */     }
/*      */     
/*      */     public static CastleEntrance createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/*  772 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -5, -3, 0, 13, 14, 13, $$5);
/*      */       
/*  774 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/*  775 */         return null;
/*      */       }
/*      */       
/*  778 */       return new CastleEntrance($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  784 */       generateBox($$0, $$4, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  786 */       generateBox($$0, $$4, 0, 5, 0, 12, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  789 */       generateBox($$0, $$4, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  790 */       generateBox($$0, $$4, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  791 */       generateBox($$0, $$4, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  792 */       generateBox($$0, $$4, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  793 */       generateBox($$0, $$4, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  794 */       generateBox($$0, $$4, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  795 */       generateBox($$0, $$4, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  796 */       generateBox($$0, $$4, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  799 */       generateBox($$0, $$4, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  802 */       generateBox($$0, $$4, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.defaultBlockState(), Blocks.NETHER_BRICK_FENCE.defaultBlockState(), false);
/*      */       
/*  804 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*  805 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */ 
/*      */       
/*  808 */       for (int $$9 = 1; $$9 <= 11; $$9 += 2) {
/*  809 */         generateBox($$0, $$4, $$9, 10, 0, $$9, 11, 0, $$7, $$7, false);
/*  810 */         generateBox($$0, $$4, $$9, 10, 12, $$9, 11, 12, $$7, $$7, false);
/*  811 */         generateBox($$0, $$4, 0, 10, $$9, 0, 11, $$9, $$8, $$8, false);
/*  812 */         generateBox($$0, $$4, 12, 10, $$9, 12, 11, $$9, $$8, $$8, false);
/*  813 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$9, 13, 0, $$4);
/*  814 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$9, 13, 12, $$4);
/*  815 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 0, 13, $$9, $$4);
/*  816 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 12, 13, $$9, $$4);
/*  817 */         if ($$9 != 11) {
/*  818 */           placeBlock($$0, $$7, $$9 + 1, 13, 0, $$4);
/*  819 */           placeBlock($$0, $$7, $$9 + 1, 13, 12, $$4);
/*  820 */           placeBlock($$0, $$8, 0, 13, $$9 + 1, $$4);
/*  821 */           placeBlock($$0, $$8, 12, 13, $$9 + 1, $$4);
/*      */         } 
/*      */       } 
/*  824 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 0, 13, 0, $$4);
/*  825 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 0, 13, 12, $$4);
/*  826 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 12, 13, 12, $$4);
/*  827 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 12, 13, 0, $$4);
/*      */ 
/*      */       
/*  830 */       for (int $$10 = 3; $$10 <= 9; $$10 += 2) {
/*  831 */         generateBox($$0, $$4, 1, 7, $$10, 1, 8, $$10, (BlockState)$$8.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), (BlockState)$$8.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), false);
/*  832 */         generateBox($$0, $$4, 11, 7, $$10, 11, 8, $$10, (BlockState)$$8.setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), (BlockState)$$8.setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), false);
/*      */       } 
/*      */ 
/*      */       
/*  836 */       generateBox($$0, $$4, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  837 */       generateBox($$0, $$4, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  839 */       generateBox($$0, $$4, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  840 */       generateBox($$0, $$4, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  841 */       generateBox($$0, $$4, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  842 */       generateBox($$0, $$4, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  844 */       for (int $$11 = 4; $$11 <= 8; $$11++) {
/*  845 */         for (int $$12 = 0; $$12 <= 2; $$12++) {
/*  846 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$11, -1, $$12, $$4);
/*  847 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$11, -1, 12 - $$12, $$4);
/*      */         } 
/*      */       } 
/*  850 */       for (int $$13 = 0; $$13 <= 2; $$13++) {
/*  851 */         for (int $$14 = 4; $$14 <= 8; $$14++) {
/*  852 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$13, -1, $$14, $$4);
/*  853 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 12 - $$13, -1, $$14, $$4);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  858 */       generateBox($$0, $$4, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  859 */       generateBox($$0, $$4, 6, 1, 6, 6, 4, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*  860 */       placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 6, 0, 6, $$4);
/*  861 */       placeBlock($$0, Blocks.LAVA.defaultBlockState(), 6, 5, 6, $$4);
/*      */       
/*  863 */       BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(6, 5, 6);
/*  864 */       if ($$4.isInside((Vec3i)mutableBlockPos))
/*  865 */         $$0.scheduleTick((BlockPos)mutableBlockPos, (Fluid)Fluids.LAVA, 0); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleStalkRoom
/*      */     extends NetherBridgePiece {
/*      */     private static final int WIDTH = 13;
/*      */     private static final int HEIGHT = 14;
/*      */     private static final int DEPTH = 13;
/*      */     
/*      */     public CastleStalkRoom(int $$0, BoundingBox $$1, Direction $$2) {
/*  876 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_STALK_ROOM, $$0, $$1);
/*      */       
/*  878 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public CastleStalkRoom(CompoundTag $$0) {
/*  882 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_STALK_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*  887 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 5, 3, true);
/*  888 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 5, 11, true);
/*      */     }
/*      */     
/*      */     public static CastleStalkRoom createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/*  892 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -5, -3, 0, 13, 14, 13, $$4);
/*      */       
/*  894 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/*  895 */         return null;
/*      */       }
/*      */       
/*  898 */       return new CastleStalkRoom($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  904 */       generateBox($$0, $$4, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  906 */       generateBox($$0, $$4, 0, 5, 0, 12, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  909 */       generateBox($$0, $$4, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  910 */       generateBox($$0, $$4, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  911 */       generateBox($$0, $$4, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  912 */       generateBox($$0, $$4, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  913 */       generateBox($$0, $$4, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  914 */       generateBox($$0, $$4, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  915 */       generateBox($$0, $$4, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  916 */       generateBox($$0, $$4, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/*  919 */       generateBox($$0, $$4, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/*  921 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*  922 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*  923 */       BlockState $$9 = (BlockState)$$8.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true));
/*  924 */       BlockState $$10 = (BlockState)$$8.setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*      */ 
/*      */       
/*  927 */       for (int $$11 = 1; $$11 <= 11; $$11 += 2) {
/*  928 */         generateBox($$0, $$4, $$11, 10, 0, $$11, 11, 0, $$7, $$7, false);
/*  929 */         generateBox($$0, $$4, $$11, 10, 12, $$11, 11, 12, $$7, $$7, false);
/*  930 */         generateBox($$0, $$4, 0, 10, $$11, 0, 11, $$11, $$8, $$8, false);
/*  931 */         generateBox($$0, $$4, 12, 10, $$11, 12, 11, $$11, $$8, $$8, false);
/*  932 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$11, 13, 0, $$4);
/*  933 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$11, 13, 12, $$4);
/*  934 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 0, 13, $$11, $$4);
/*  935 */         placeBlock($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 12, 13, $$11, $$4);
/*  936 */         if ($$11 != 11) {
/*  937 */           placeBlock($$0, $$7, $$11 + 1, 13, 0, $$4);
/*  938 */           placeBlock($$0, $$7, $$11 + 1, 13, 12, $$4);
/*  939 */           placeBlock($$0, $$8, 0, 13, $$11 + 1, $$4);
/*  940 */           placeBlock($$0, $$8, 12, 13, $$11 + 1, $$4);
/*      */         } 
/*      */       } 
/*  943 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 0, 13, 0, $$4);
/*  944 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), 0, 13, 12, $$4);
/*  945 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 12, 13, 12, $$4);
/*  946 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), 12, 13, 0, $$4);
/*      */ 
/*      */       
/*  949 */       for (int $$12 = 3; $$12 <= 9; $$12 += 2) {
/*  950 */         generateBox($$0, $$4, 1, 7, $$12, 1, 8, $$12, $$9, $$9, false);
/*  951 */         generateBox($$0, $$4, 11, 7, $$12, 11, 8, $$12, $$10, $$10, false);
/*      */       } 
/*      */ 
/*      */       
/*  955 */       BlockState $$13 = (BlockState)Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.NORTH);
/*  956 */       for (int $$14 = 0; $$14 <= 6; $$14++) {
/*  957 */         int $$15 = $$14 + 4;
/*  958 */         for (int $$16 = 5; $$16 <= 7; $$16++) {
/*  959 */           placeBlock($$0, $$13, $$16, 5 + $$14, $$15, $$4);
/*      */         }
/*  961 */         if ($$15 >= 5 && $$15 <= 8) {
/*  962 */           generateBox($$0, $$4, 5, 5, $$15, 7, $$14 + 4, $$15, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  963 */         } else if ($$15 >= 9 && $$15 <= 10) {
/*  964 */           generateBox($$0, $$4, 5, 8, $$15, 7, $$14 + 4, $$15, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */         } 
/*  966 */         if ($$14 >= 1) {
/*  967 */           generateBox($$0, $$4, 5, 6 + $$14, $$15, 7, 9 + $$14, $$15, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */         }
/*      */       } 
/*  970 */       for (int $$17 = 5; $$17 <= 7; $$17++) {
/*  971 */         placeBlock($$0, $$13, $$17, 12, 11, $$4);
/*      */       }
/*  973 */       generateBox($$0, $$4, 5, 6, 7, 5, 7, 7, $$10, $$10, false);
/*  974 */       generateBox($$0, $$4, 7, 6, 7, 7, 7, 7, $$9, $$9, false);
/*  975 */       generateBox($$0, $$4, 5, 13, 12, 7, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/*  978 */       generateBox($$0, $$4, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  979 */       generateBox($$0, $$4, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  980 */       generateBox($$0, $$4, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  981 */       generateBox($$0, $$4, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  982 */       generateBox($$0, $$4, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  983 */       generateBox($$0, $$4, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*  984 */       BlockState $$18 = (BlockState)$$13.setValue((Property)StairBlock.FACING, (Comparable)Direction.EAST);
/*  985 */       BlockState $$19 = (BlockState)$$13.setValue((Property)StairBlock.FACING, (Comparable)Direction.WEST);
/*  986 */       placeBlock($$0, $$19, 4, 5, 2, $$4);
/*  987 */       placeBlock($$0, $$19, 4, 5, 3, $$4);
/*  988 */       placeBlock($$0, $$19, 4, 5, 9, $$4);
/*  989 */       placeBlock($$0, $$19, 4, 5, 10, $$4);
/*  990 */       placeBlock($$0, $$18, 8, 5, 2, $$4);
/*  991 */       placeBlock($$0, $$18, 8, 5, 3, $$4);
/*  992 */       placeBlock($$0, $$18, 8, 5, 9, $$4);
/*  993 */       placeBlock($$0, $$18, 8, 5, 10, $$4);
/*      */ 
/*      */       
/*  996 */       generateBox($$0, $$4, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), false);
/*  997 */       generateBox($$0, $$4, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), false);
/*  998 */       generateBox($$0, $$4, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.defaultBlockState(), Blocks.NETHER_WART.defaultBlockState(), false);
/*  999 */       generateBox($$0, $$4, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.defaultBlockState(), Blocks.NETHER_WART.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1002 */       generateBox($$0, $$4, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1003 */       generateBox($$0, $$4, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1005 */       generateBox($$0, $$4, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1006 */       generateBox($$0, $$4, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1007 */       generateBox($$0, $$4, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1008 */       generateBox($$0, $$4, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1010 */       for (int $$20 = 4; $$20 <= 8; $$20++) {
/* 1011 */         for (int $$21 = 0; $$21 <= 2; $$21++) {
/* 1012 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$20, -1, $$21, $$4);
/* 1013 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$20, -1, 12 - $$21, $$4);
/*      */         } 
/*      */       } 
/* 1016 */       for (int $$22 = 0; $$22 <= 2; $$22++) {
/* 1017 */         for (int $$23 = 4; $$23 <= 8; $$23++) {
/* 1018 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$22, -1, $$23, $$4);
/* 1019 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), 12 - $$22, -1, $$23, $$4);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleSmallCorridorPiece extends NetherBridgePiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 7;
/*      */     private static final int DEPTH = 5;
/*      */     
/*      */     public CastleSmallCorridorPiece(int $$0, BoundingBox $$1, Direction $$2) {
/* 1031 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR, $$0, $$1);
/*      */       
/* 1033 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public CastleSmallCorridorPiece(CompoundTag $$0) {
/* 1037 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1042 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 1, 0, true);
/*      */     }
/*      */     
/*      */     public static CastleSmallCorridorPiece createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/* 1046 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -1, 0, 0, 5, 7, 5, $$4);
/*      */       
/* 1048 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/* 1049 */         return null;
/*      */       }
/*      */       
/* 1052 */       return new CastleSmallCorridorPiece($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1058 */       generateBox($$0, $$4, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1060 */       generateBox($$0, $$4, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */       
/* 1062 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */ 
/*      */       
/* 1065 */       generateBox($$0, $$4, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1066 */       generateBox($$0, $$4, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1067 */       generateBox($$0, $$4, 0, 3, 1, 0, 4, 1, $$7, $$7, false);
/* 1068 */       generateBox($$0, $$4, 0, 3, 3, 0, 4, 3, $$7, $$7, false);
/* 1069 */       generateBox($$0, $$4, 4, 3, 1, 4, 4, 1, $$7, $$7, false);
/* 1070 */       generateBox($$0, $$4, 4, 3, 3, 4, 4, 3, $$7, $$7, false);
/*      */ 
/*      */       
/* 1073 */       generateBox($$0, $$4, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1076 */       for (int $$8 = 0; $$8 <= 4; $$8++) {
/* 1077 */         for (int $$9 = 0; $$9 <= 4; $$9++)
/* 1078 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$8, -1, $$9, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleSmallCorridorCrossingPiece
/*      */     extends NetherBridgePiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 7;
/*      */     private static final int DEPTH = 5;
/*      */     
/*      */     public CastleSmallCorridorCrossingPiece(int $$0, BoundingBox $$1, Direction $$2) {
/* 1090 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_CROSSING, $$0, $$1);
/*      */       
/* 1092 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public CastleSmallCorridorCrossingPiece(CompoundTag $$0) {
/* 1096 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_CROSSING, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1101 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 1, 0, true);
/* 1102 */       generateChildLeft((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, 1, true);
/* 1103 */       generateChildRight((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static CastleSmallCorridorCrossingPiece createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/* 1107 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -1, 0, 0, 5, 7, 5, $$4);
/*      */       
/* 1109 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/* 1110 */         return null;
/*      */       }
/*      */       
/* 1113 */       return new CastleSmallCorridorCrossingPiece($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1119 */       generateBox($$0, $$4, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1121 */       generateBox($$0, $$4, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1124 */       generateBox($$0, $$4, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1125 */       generateBox($$0, $$4, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1126 */       generateBox($$0, $$4, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1127 */       generateBox($$0, $$4, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1130 */       generateBox($$0, $$4, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1133 */       for (int $$7 = 0; $$7 <= 4; $$7++) {
/* 1134 */         for (int $$8 = 0; $$8 <= 4; $$8++)
/* 1135 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$7, -1, $$8, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleSmallCorridorRightTurnPiece
/*      */     extends NetherBridgePiece
/*      */   {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 7;
/*      */     private static final int DEPTH = 5;
/*      */     private boolean isNeedingChest;
/*      */     
/*      */     public CastleSmallCorridorRightTurnPiece(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/* 1149 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_RIGHT_TURN, $$0, $$2);
/*      */       
/* 1151 */       setOrientation($$3);
/*      */       
/* 1153 */       this.isNeedingChest = ($$1.nextInt(3) == 0);
/*      */     }
/*      */     
/*      */     public CastleSmallCorridorRightTurnPiece(CompoundTag $$0) {
/* 1157 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_RIGHT_TURN, $$0);
/* 1158 */       this.isNeedingChest = $$0.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 1163 */       super.addAdditionalSaveData($$0, $$1);
/*      */       
/* 1165 */       $$1.putBoolean("Chest", this.isNeedingChest);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1170 */       generateChildRight((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static CastleSmallCorridorRightTurnPiece createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 1174 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, 0, 0, 5, 7, 5, $$5);
/*      */       
/* 1176 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 1177 */         return null;
/*      */       }
/*      */       
/* 1180 */       return new CastleSmallCorridorRightTurnPiece($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1186 */       generateBox($$0, $$4, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1188 */       generateBox($$0, $$4, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */       
/* 1190 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/* 1191 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */ 
/*      */       
/* 1194 */       generateBox($$0, $$4, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1195 */       generateBox($$0, $$4, 0, 3, 1, 0, 4, 1, $$8, $$8, false);
/* 1196 */       generateBox($$0, $$4, 0, 3, 3, 0, 4, 3, $$8, $$8, false);
/*      */       
/* 1198 */       generateBox($$0, $$4, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1200 */       generateBox($$0, $$4, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1201 */       generateBox($$0, $$4, 1, 3, 4, 1, 4, 4, $$7, $$7, false);
/* 1202 */       generateBox($$0, $$4, 3, 3, 4, 3, 4, 4, $$7, $$7, false);
/*      */       
/* 1204 */       if (this.isNeedingChest && 
/* 1205 */         $$4.isInside((Vec3i)getWorldPos(1, 2, 3))) {
/* 1206 */         this.isNeedingChest = false;
/* 1207 */         createChest($$0, $$4, $$3, 1, 2, 3, BuiltInLootTables.NETHER_BRIDGE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1212 */       generateBox($$0, $$4, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1215 */       for (int $$9 = 0; $$9 <= 4; $$9++) {
/* 1216 */         for (int $$10 = 0; $$10 <= 4; $$10++)
/* 1217 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$9, -1, $$10, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleSmallCorridorLeftTurnPiece
/*      */     extends NetherBridgePiece
/*      */   {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 7;
/*      */     private static final int DEPTH = 5;
/*      */     private boolean isNeedingChest;
/*      */     
/*      */     public CastleSmallCorridorLeftTurnPiece(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/* 1231 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_LEFT_TURN, $$0, $$2);
/*      */       
/* 1233 */       setOrientation($$3);
/*      */       
/* 1235 */       this.isNeedingChest = ($$1.nextInt(3) == 0);
/*      */     }
/*      */     
/*      */     public CastleSmallCorridorLeftTurnPiece(CompoundTag $$0) {
/* 1239 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_LEFT_TURN, $$0);
/* 1240 */       this.isNeedingChest = $$0.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 1245 */       super.addAdditionalSaveData($$0, $$1);
/*      */       
/* 1247 */       $$1.putBoolean("Chest", this.isNeedingChest);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1252 */       generateChildLeft((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static CastleSmallCorridorLeftTurnPiece createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 1256 */       BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, 0, 0, 5, 7, 5, $$5);
/*      */       
/* 1258 */       if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 1259 */         return null;
/*      */       }
/*      */       
/* 1262 */       return new CastleSmallCorridorLeftTurnPiece($$6, $$1, $$7, $$5);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1268 */       generateBox($$0, $$4, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1270 */       generateBox($$0, $$4, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */       
/* 1272 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/* 1273 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */ 
/*      */       
/* 1276 */       generateBox($$0, $$4, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1277 */       generateBox($$0, $$4, 4, 3, 1, 4, 4, 1, $$8, $$8, false);
/* 1278 */       generateBox($$0, $$4, 4, 3, 3, 4, 4, 3, $$8, $$8, false);
/*      */       
/* 1280 */       generateBox($$0, $$4, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1282 */       generateBox($$0, $$4, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1283 */       generateBox($$0, $$4, 1, 3, 4, 1, 4, 4, $$7, $$7, false);
/* 1284 */       generateBox($$0, $$4, 3, 3, 4, 3, 4, 4, $$7, $$7, false);
/*      */       
/* 1286 */       if (this.isNeedingChest && 
/* 1287 */         $$4.isInside((Vec3i)getWorldPos(3, 2, 3))) {
/* 1288 */         this.isNeedingChest = false;
/* 1289 */         createChest($$0, $$4, $$3, 3, 2, 3, BuiltInLootTables.NETHER_BRIDGE);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1294 */       generateBox($$0, $$4, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1297 */       for (int $$9 = 0; $$9 <= 4; $$9++) {
/* 1298 */         for (int $$10 = 0; $$10 <= 4; $$10++)
/* 1299 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$9, -1, $$10, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleCorridorStairsPiece
/*      */     extends NetherBridgePiece {
/*      */     private static final int WIDTH = 5;
/*      */     private static final int HEIGHT = 14;
/*      */     private static final int DEPTH = 10;
/*      */     
/*      */     public CastleCorridorStairsPiece(int $$0, BoundingBox $$1, Direction $$2) {
/* 1311 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_STAIRS, $$0, $$1);
/*      */       
/* 1313 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public CastleCorridorStairsPiece(CompoundTag $$0) {
/* 1317 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_STAIRS, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1322 */       generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 1, 0, true);
/*      */     }
/*      */     
/*      */     public static CastleCorridorStairsPiece createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/* 1326 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -1, -7, 0, 5, 14, 10, $$4);
/*      */       
/* 1328 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/* 1329 */         return null;
/*      */       }
/*      */       
/* 1332 */       return new CastleCorridorStairsPiece($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1338 */       BlockState $$7 = (BlockState)Blocks.NETHER_BRICK_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.SOUTH);
/* 1339 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/*      */       
/* 1341 */       for (int $$9 = 0; $$9 <= 9; $$9++) {
/* 1342 */         int $$10 = Math.max(1, 7 - $$9);
/* 1343 */         int $$11 = Math.min(Math.max($$10 + 5, 14 - $$9), 13);
/* 1344 */         int $$12 = $$9;
/*      */ 
/*      */         
/* 1347 */         generateBox($$0, $$4, 0, 0, $$12, 4, $$10, $$12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */         
/* 1349 */         generateBox($$0, $$4, 1, $$10 + 1, $$12, 3, $$11 - 1, $$12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 1350 */         if ($$9 <= 6) {
/* 1351 */           placeBlock($$0, $$7, 1, $$10 + 1, $$12, $$4);
/* 1352 */           placeBlock($$0, $$7, 2, $$10 + 1, $$12, $$4);
/* 1353 */           placeBlock($$0, $$7, 3, $$10 + 1, $$12, $$4);
/*      */         } 
/*      */         
/* 1356 */         generateBox($$0, $$4, 0, $$11, $$12, 4, $$11, $$12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */         
/* 1358 */         generateBox($$0, $$4, 0, $$10 + 1, $$12, 0, $$11 - 1, $$12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1359 */         generateBox($$0, $$4, 4, $$10 + 1, $$12, 4, $$11 - 1, $$12, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1360 */         if (($$9 & 0x1) == 0) {
/* 1361 */           generateBox($$0, $$4, 0, $$10 + 2, $$12, 0, $$10 + 3, $$12, $$8, $$8, false);
/* 1362 */           generateBox($$0, $$4, 4, $$10 + 2, $$12, 4, $$10 + 3, $$12, $$8, $$8, false);
/*      */         } 
/*      */ 
/*      */         
/* 1366 */         for (int $$13 = 0; $$13 <= 4; $$13++)
/* 1367 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$13, -1, $$12, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CastleCorridorTBalconyPiece
/*      */     extends NetherBridgePiece {
/*      */     private static final int WIDTH = 9;
/*      */     private static final int HEIGHT = 7;
/*      */     private static final int DEPTH = 9;
/*      */     
/*      */     public CastleCorridorTBalconyPiece(int $$0, BoundingBox $$1, Direction $$2) {
/* 1379 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_T_BALCONY, $$0, $$1);
/*      */       
/* 1381 */       setOrientation($$2);
/*      */     }
/*      */     
/*      */     public CastleCorridorTBalconyPiece(CompoundTag $$0) {
/* 1385 */       super(StructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_T_BALCONY, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 1390 */       int $$3 = 1;
/*      */       
/* 1392 */       Direction $$4 = getOrientation();
/* 1393 */       if ($$4 == Direction.WEST || $$4 == Direction.NORTH) {
/* 1394 */         $$3 = 5;
/*      */       }
/*      */       
/* 1397 */       generateChildLeft((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, $$3, ($$2.nextInt(8) > 0));
/* 1398 */       generateChildRight((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 0, $$3, ($$2.nextInt(8) > 0));
/*      */     }
/*      */     
/*      */     public static CastleCorridorTBalconyPiece createPiece(StructurePieceAccessor $$0, int $$1, int $$2, int $$3, Direction $$4, int $$5) {
/* 1402 */       BoundingBox $$6 = BoundingBox.orientBox($$1, $$2, $$3, -3, 0, 0, 9, 7, 9, $$4);
/*      */       
/* 1404 */       if (!isOkBox($$6) || $$0.findCollisionPiece($$6) != null) {
/* 1405 */         return null;
/*      */       }
/*      */       
/* 1408 */       return new CastleCorridorTBalconyPiece($$5, $$6, $$4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1413 */       BlockState $$7 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/* 1414 */       BlockState $$8 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/*      */ 
/*      */       
/* 1417 */       generateBox($$0, $$4, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */       
/* 1419 */       generateBox($$0, $$4, 0, 2, 0, 8, 5, 8, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */       
/* 1421 */       generateBox($$0, $$4, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1424 */       generateBox($$0, $$4, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1425 */       generateBox($$0, $$4, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1426 */       generateBox($$0, $$4, 1, 3, 0, 1, 4, 0, $$8, $$8, false);
/* 1427 */       generateBox($$0, $$4, 7, 3, 0, 7, 4, 0, $$8, $$8, false);
/*      */ 
/*      */       
/* 1430 */       generateBox($$0, $$4, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1431 */       generateBox($$0, $$4, 1, 1, 4, 2, 2, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/* 1432 */       generateBox($$0, $$4, 6, 1, 4, 7, 2, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*      */ 
/*      */       
/* 1435 */       generateBox($$0, $$4, 1, 3, 8, 7, 3, 8, $$8, $$8, false);
/* 1436 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.EAST, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true)), 0, 3, 8, $$4);
/* 1437 */       placeBlock($$0, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.WEST, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true)), 8, 3, 8, $$4);
/* 1438 */       generateBox($$0, $$4, 0, 3, 6, 0, 3, 7, $$7, $$7, false);
/* 1439 */       generateBox($$0, $$4, 8, 3, 6, 8, 3, 7, $$7, $$7, false);
/*      */ 
/*      */       
/* 1442 */       generateBox($$0, $$4, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1443 */       generateBox($$0, $$4, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1444 */       generateBox($$0, $$4, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1445 */       generateBox($$0, $$4, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 1446 */       generateBox($$0, $$4, 1, 4, 5, 1, 5, 5, $$8, $$8, false);
/* 1447 */       generateBox($$0, $$4, 7, 4, 5, 7, 5, 5, $$8, $$8, false);
/*      */ 
/*      */       
/* 1450 */       for (int $$9 = 0; $$9 <= 5; $$9++) {
/* 1451 */         for (int $$10 = 0; $$10 <= 8; $$10++)
/* 1452 */           fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$10, -1, $$9, $$4); 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFortressPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */