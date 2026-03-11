/*      */ package net.minecraft.world.level.levelgen.structure.structures;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.google.common.collect.Lists;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.MobSpawnType;
/*      */ import net.minecraft.world.entity.monster.ElderGuardian;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.ServerLevelAccessor;
/*      */ import net.minecraft.world.level.StructureManager;
/*      */ import net.minecraft.world.level.WorldGenLevel;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*      */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*      */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*      */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*      */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*      */ 
/*      */ public class OceanMonumentPieces {
/*      */   protected static abstract class OceanMonumentPiece extends StructurePiece {
/*   34 */     protected static final BlockState BASE_GRAY = Blocks.PRISMARINE.defaultBlockState();
/*   35 */     protected static final BlockState BASE_LIGHT = Blocks.PRISMARINE_BRICKS.defaultBlockState();
/*   36 */     protected static final BlockState BASE_BLACK = Blocks.DARK_PRISMARINE.defaultBlockState();
/*      */     
/*   38 */     protected static final BlockState DOT_DECO_DATA = BASE_LIGHT;
/*      */     
/*   40 */     protected static final BlockState LAMP_BLOCK = Blocks.SEA_LANTERN.defaultBlockState();
/*      */     
/*      */     protected static final boolean DO_FILL = true;
/*   43 */     protected static final BlockState FILL_BLOCK = Blocks.WATER.defaultBlockState();
/*   44 */     protected static final Set<Block> FILL_KEEP = (Set<Block>)ImmutableSet.builder()
/*   45 */       .add(Blocks.ICE)
/*   46 */       .add(Blocks.PACKED_ICE)
/*   47 */       .add(Blocks.BLUE_ICE)
/*   48 */       .add(FILL_BLOCK.getBlock())
/*   49 */       .build();
/*      */     
/*      */     protected static final int GRIDROOM_WIDTH = 8;
/*      */     
/*      */     protected static final int GRIDROOM_DEPTH = 8;
/*      */     protected static final int GRIDROOM_HEIGHT = 4;
/*      */     protected static final int GRID_WIDTH = 5;
/*      */     protected static final int GRID_DEPTH = 5;
/*      */     protected static final int GRID_HEIGHT = 3;
/*      */     protected static final int GRID_FLOOR_COUNT = 25;
/*      */     protected static final int GRID_SIZE = 75;
/*   60 */     protected static final int GRIDROOM_SOURCE_INDEX = getRoomIndex(2, 0, 0);
/*   61 */     protected static final int GRIDROOM_TOP_CONNECT_INDEX = getRoomIndex(2, 2, 0);
/*   62 */     protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = getRoomIndex(0, 1, 0);
/*   63 */     protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = getRoomIndex(4, 1, 0);
/*      */     
/*      */     protected static final int LEFTWING_INDEX = 1001;
/*      */     
/*      */     protected static final int RIGHTWING_INDEX = 1002;
/*      */     protected static final int PENTHOUSE_INDEX = 1003;
/*      */     protected OceanMonumentPieces.RoomDefinition roomDefinition;
/*      */     
/*      */     protected static int getRoomIndex(int $$0, int $$1, int $$2) {
/*   72 */       return $$1 * 25 + $$2 * 5 + $$0;
/*      */     }
/*      */     
/*      */     public OceanMonumentPiece(StructurePieceType $$0, Direction $$1, int $$2, BoundingBox $$3) {
/*   76 */       super($$0, $$2, $$3);
/*   77 */       setOrientation($$1);
/*      */     }
/*      */     
/*      */     protected OceanMonumentPiece(StructurePieceType $$0, int $$1, Direction $$2, OceanMonumentPieces.RoomDefinition $$3, int $$4, int $$5, int $$6) {
/*   81 */       super($$0, $$1, makeBoundingBox($$2, $$3, $$4, $$5, $$6));
/*      */       
/*   83 */       setOrientation($$2);
/*   84 */       this.roomDefinition = $$3;
/*      */     }
/*      */     
/*      */     private static BoundingBox makeBoundingBox(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, int $$2, int $$3, int $$4) {
/*   88 */       int $$5 = $$1.index;
/*   89 */       int $$6 = $$5 % 5;
/*   90 */       int $$7 = $$5 / 5 % 5;
/*   91 */       int $$8 = $$5 / 25;
/*      */ 
/*      */ 
/*      */       
/*   95 */       BoundingBox $$9 = makeBoundingBox(0, 0, 0, $$0, $$2 * 8, $$3 * 4, $$4 * 8);
/*      */       
/*   97 */       switch ($$0)
/*      */       { case NORTH:
/*   99 */           $$9.move($$6 * 8, $$8 * 4, -($$7 + $$4) * 8 + 1);
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
/*  114 */           return $$9;case SOUTH: $$9.move($$6 * 8, $$8 * 4, $$7 * 8); return $$9;case WEST: $$9.move(-($$7 + $$4) * 8 + 1, $$8 * 4, $$6 * 8); return $$9; }  $$9.move($$7 * 8, $$8 * 4, $$6 * 8); return $$9;
/*      */     }
/*      */     
/*      */     public OceanMonumentPiece(StructurePieceType $$0, CompoundTag $$1) {
/*  118 */       super($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {}
/*      */ 
/*      */     
/*      */     protected void generateWaterBox(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/*  126 */       for (int $$8 = $$3; $$8 <= $$6; $$8++) {
/*  127 */         for (int $$9 = $$2; $$9 <= $$5; $$9++) {
/*  128 */           for (int $$10 = $$4; $$10 <= $$7; $$10++) {
/*  129 */             BlockState $$11 = getBlock((BlockGetter)$$0, $$9, $$8, $$10, $$1);
/*  130 */             if (!FILL_KEEP.contains($$11.getBlock())) {
/*  131 */               if (getWorldY($$8) >= $$0.getSeaLevel() && $$11 != FILL_BLOCK) {
/*  132 */                 placeBlock($$0, Blocks.AIR.defaultBlockState(), $$9, $$8, $$10, $$1);
/*      */               } else {
/*  134 */                 placeBlock($$0, FILL_BLOCK, $$9, $$8, $$10, $$1);
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void generateDefaultFloor(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, boolean $$4) {
/*  143 */       if ($$4) {
/*  144 */         generateBox($$0, $$1, $$2 + 0, 0, $$3 + 0, $$2 + 2, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/*  145 */         generateBox($$0, $$1, $$2 + 5, 0, $$3 + 0, $$2 + 8 - 1, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/*  146 */         generateBox($$0, $$1, $$2 + 3, 0, $$3 + 0, $$2 + 4, 0, $$3 + 2, BASE_GRAY, BASE_GRAY, false);
/*  147 */         generateBox($$0, $$1, $$2 + 3, 0, $$3 + 5, $$2 + 4, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  149 */         generateBox($$0, $$1, $$2 + 3, 0, $$3 + 2, $$2 + 4, 0, $$3 + 2, BASE_LIGHT, BASE_LIGHT, false);
/*  150 */         generateBox($$0, $$1, $$2 + 3, 0, $$3 + 5, $$2 + 4, 0, $$3 + 5, BASE_LIGHT, BASE_LIGHT, false);
/*  151 */         generateBox($$0, $$1, $$2 + 2, 0, $$3 + 3, $$2 + 2, 0, $$3 + 4, BASE_LIGHT, BASE_LIGHT, false);
/*  152 */         generateBox($$0, $$1, $$2 + 5, 0, $$3 + 3, $$2 + 5, 0, $$3 + 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */       } else {
/*  154 */         generateBox($$0, $$1, $$2 + 0, 0, $$3 + 0, $$2 + 8 - 1, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void generateBoxOnFillOnly(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, BlockState $$8) {
/*  159 */       for (int $$9 = $$3; $$9 <= $$6; $$9++) {
/*  160 */         for (int $$10 = $$2; $$10 <= $$5; $$10++) {
/*  161 */           for (int $$11 = $$4; $$11 <= $$7; $$11++) {
/*  162 */             if (getBlock((BlockGetter)$$0, $$10, $$9, $$11, $$1) == FILL_BLOCK)
/*      */             {
/*      */               
/*  165 */               placeBlock($$0, $$8, $$10, $$9, $$11, $$1); } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     protected boolean chunkIntersects(BoundingBox $$0, int $$1, int $$2, int $$3, int $$4) {
/*  172 */       int $$5 = getWorldX($$1, $$2);
/*  173 */       int $$6 = getWorldZ($$1, $$2);
/*  174 */       int $$7 = getWorldX($$3, $$4);
/*  175 */       int $$8 = getWorldZ($$3, $$4);
/*  176 */       return $$0.intersects(Math.min($$5, $$7), Math.min($$6, $$8), Math.max($$5, $$7), Math.max($$6, $$8));
/*      */     }
/*      */     
/*      */     protected void spawnElder(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4) {
/*  180 */       BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$2, $$3, $$4);
/*  181 */       if ($$1.isInside((Vec3i)mutableBlockPos)) {
/*  182 */         ElderGuardian $$6 = (ElderGuardian)EntityType.ELDER_GUARDIAN.create((Level)$$0.getLevel());
/*  183 */         if ($$6 != null) {
/*  184 */           $$6.heal($$6.getMaxHealth());
/*  185 */           $$6.moveTo(mutableBlockPos.getX() + 0.5D, mutableBlockPos.getY(), mutableBlockPos.getZ() + 0.5D, 0.0F, 0.0F);
/*  186 */           $$6.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$6.blockPosition()), MobSpawnType.STRUCTURE, null, null);
/*  187 */           $$0.addFreshEntityWithPassengers((Entity)$$6);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class MonumentBuilding
/*      */     extends OceanMonumentPiece
/*      */   {
/*      */     private static final int WIDTH = 58;
/*      */     
/*      */     private static final int HEIGHT = 22;
/*      */     
/*      */     private static final int DEPTH = 58;
/*      */     
/*      */     public static final int BIOME_RANGE_CHECK = 29;
/*      */     
/*      */     private static final int TOP_POSITION = 61;
/*      */     
/*      */     private OceanMonumentPieces.RoomDefinition sourceRoom;
/*      */     private OceanMonumentPieces.RoomDefinition coreRoom;
/*  209 */     private final List<OceanMonumentPieces.OceanMonumentPiece> childPieces = Lists.newArrayList();
/*      */     
/*      */     public MonumentBuilding(RandomSource $$0, int $$1, int $$2, Direction $$3) {
/*  212 */       super(StructurePieceType.OCEAN_MONUMENT_BUILDING, $$3, 0, makeBoundingBox($$1, 39, $$2, $$3, 58, 23, 58));
/*      */       
/*  214 */       setOrientation($$3);
/*      */       
/*  216 */       List<OceanMonumentPieces.RoomDefinition> $$4 = generateRoomGraph($$0);
/*      */       
/*  218 */       this.sourceRoom.claimed = true;
/*  219 */       this.childPieces.add(new OceanMonumentPieces.OceanMonumentEntryRoom($$3, this.sourceRoom));
/*  220 */       this.childPieces.add(new OceanMonumentPieces.OceanMonumentCoreRoom($$3, this.coreRoom));
/*      */       
/*  222 */       List<OceanMonumentPieces.MonumentRoomFitter> $$5 = Lists.newArrayList();
/*  223 */       $$5.add(new OceanMonumentPieces.FitDoubleXYRoom());
/*  224 */       $$5.add(new OceanMonumentPieces.FitDoubleYZRoom());
/*  225 */       $$5.add(new OceanMonumentPieces.FitDoubleZRoom());
/*  226 */       $$5.add(new OceanMonumentPieces.FitDoubleXRoom());
/*  227 */       $$5.add(new OceanMonumentPieces.FitDoubleYRoom());
/*  228 */       $$5.add(new OceanMonumentPieces.FitSimpleTopRoom());
/*  229 */       $$5.add(new OceanMonumentPieces.FitSimpleRoom());
/*      */       
/*  231 */       for (OceanMonumentPieces.RoomDefinition $$6 : $$4) {
/*  232 */         if (!$$6.claimed && !$$6.isSpecial())
/*      */         {
/*  234 */           for (OceanMonumentPieces.MonumentRoomFitter $$7 : $$5) {
/*  235 */             if ($$7.fits($$6)) {
/*  236 */               this.childPieces.add($$7.create($$3, $$6, $$0));
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  244 */       BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(9, 0, 22);
/*  245 */       for (OceanMonumentPieces.OceanMonumentPiece $$9 : this.childPieces) {
/*  246 */         $$9.getBoundingBox().move((Vec3i)mutableBlockPos);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  251 */       BoundingBox $$10 = BoundingBox.fromCorners((Vec3i)getWorldPos(1, 1, 1), (Vec3i)getWorldPos(23, 8, 21));
/*  252 */       BoundingBox $$11 = BoundingBox.fromCorners((Vec3i)getWorldPos(34, 1, 1), (Vec3i)getWorldPos(56, 8, 21));
/*  253 */       BoundingBox $$12 = BoundingBox.fromCorners((Vec3i)getWorldPos(22, 13, 22), (Vec3i)getWorldPos(35, 17, 35));
/*      */ 
/*      */       
/*  256 */       int $$13 = $$0.nextInt();
/*  257 */       this.childPieces.add(new OceanMonumentPieces.OceanMonumentWingRoom($$3, $$10, $$13++));
/*  258 */       this.childPieces.add(new OceanMonumentPieces.OceanMonumentWingRoom($$3, $$11, $$13++));
/*      */       
/*  260 */       this.childPieces.add(new OceanMonumentPieces.OceanMonumentPenthouse($$3, $$12));
/*      */     }
/*      */ 
/*      */     
/*      */     public MonumentBuilding(CompoundTag $$0) {
/*  265 */       super(StructurePieceType.OCEAN_MONUMENT_BUILDING, $$0);
/*      */     }
/*      */     
/*      */     private List<OceanMonumentPieces.RoomDefinition> generateRoomGraph(RandomSource $$0) {
/*  269 */       OceanMonumentPieces.RoomDefinition[] $$1 = new OceanMonumentPieces.RoomDefinition[75];
/*      */       
/*  271 */       for (int $$2 = 0; $$2 < 5; $$2++) {
/*  272 */         for (int $$3 = 0; $$3 < 4; $$3++) {
/*  273 */           int $$4 = 0;
/*  274 */           int $$5 = getRoomIndex($$2, 0, $$3);
/*  275 */           $$1[$$5] = new OceanMonumentPieces.RoomDefinition($$5);
/*      */         } 
/*      */       } 
/*  278 */       for (int $$6 = 0; $$6 < 5; $$6++) {
/*  279 */         for (int $$7 = 0; $$7 < 4; $$7++) {
/*  280 */           int $$8 = 1;
/*  281 */           int $$9 = getRoomIndex($$6, 1, $$7);
/*  282 */           $$1[$$9] = new OceanMonumentPieces.RoomDefinition($$9);
/*      */         } 
/*      */       } 
/*  285 */       for (int $$10 = 1; $$10 < 4; $$10++) {
/*  286 */         for (int $$11 = 0; $$11 < 2; $$11++) {
/*  287 */           int $$12 = 2;
/*  288 */           int $$13 = getRoomIndex($$10, 2, $$11);
/*  289 */           $$1[$$13] = new OceanMonumentPieces.RoomDefinition($$13);
/*      */         } 
/*      */       } 
/*      */       
/*  293 */       this.sourceRoom = $$1[GRIDROOM_SOURCE_INDEX];
/*      */       
/*  295 */       for (int $$14 = 0; $$14 < 5; $$14++) {
/*  296 */         for (int $$15 = 0; $$15 < 5; $$15++) {
/*  297 */           for (int $$16 = 0; $$16 < 3; $$16++) {
/*  298 */             int $$17 = getRoomIndex($$14, $$16, $$15);
/*  299 */             if ($$1[$$17] != null)
/*      */             {
/*      */               
/*  302 */               for (Direction $$18 : Direction.values()) {
/*  303 */                 int $$19 = $$14 + $$18.getStepX();
/*  304 */                 int $$20 = $$16 + $$18.getStepY();
/*  305 */                 int $$21 = $$15 + $$18.getStepZ();
/*  306 */                 if ($$19 >= 0 && $$19 < 5 && $$21 >= 0 && $$21 < 5 && $$20 >= 0 && $$20 < 3) {
/*  307 */                   int $$22 = getRoomIndex($$19, $$20, $$21);
/*  308 */                   if ($$1[$$22] != null)
/*      */                   {
/*      */                     
/*  311 */                     if ($$21 == $$15) {
/*  312 */                       $$1[$$17].setConnection($$18, $$1[$$22]);
/*      */                     } else {
/*  314 */                       $$1[$$17].setConnection($$18.getOpposite(), $$1[$$22]);
/*      */                     }  } 
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*  322 */       OceanMonumentPieces.RoomDefinition $$23 = new OceanMonumentPieces.RoomDefinition(1003);
/*  323 */       OceanMonumentPieces.RoomDefinition $$24 = new OceanMonumentPieces.RoomDefinition(1001);
/*  324 */       OceanMonumentPieces.RoomDefinition $$25 = new OceanMonumentPieces.RoomDefinition(1002);
/*  325 */       $$1[GRIDROOM_TOP_CONNECT_INDEX].setConnection(Direction.UP, $$23);
/*  326 */       $$1[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, $$24);
/*  327 */       $$1[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, $$25);
/*  328 */       $$23.claimed = true;
/*  329 */       $$24.claimed = true;
/*  330 */       $$25.claimed = true;
/*  331 */       this.sourceRoom.isSource = true;
/*      */ 
/*      */       
/*  334 */       this.coreRoom = $$1[getRoomIndex($$0.nextInt(4), 0, 2)];
/*  335 */       this.coreRoom.claimed = true;
/*  336 */       (this.coreRoom.connections[Direction.EAST.get3DDataValue()]).claimed = true;
/*  337 */       (this.coreRoom.connections[Direction.NORTH.get3DDataValue()]).claimed = true;
/*  338 */       ((this.coreRoom.connections[Direction.EAST.get3DDataValue()]).connections[Direction.NORTH.get3DDataValue()]).claimed = true;
/*  339 */       (this.coreRoom.connections[Direction.UP.get3DDataValue()]).claimed = true;
/*  340 */       ((this.coreRoom.connections[Direction.EAST.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/*  341 */       ((this.coreRoom.connections[Direction.NORTH.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/*  342 */       (((this.coreRoom.connections[Direction.EAST.get3DDataValue()]).connections[Direction.NORTH.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/*      */       
/*  344 */       ObjectArrayList<OceanMonumentPieces.RoomDefinition> $$26 = new ObjectArrayList();
/*  345 */       for (OceanMonumentPieces.RoomDefinition $$27 : $$1) {
/*  346 */         if ($$27 != null) {
/*  347 */           $$27.updateOpenings();
/*  348 */           $$26.add($$27);
/*      */         } 
/*      */       } 
/*  351 */       $$23.updateOpenings();
/*      */       
/*  353 */       Util.shuffle((List)$$26, $$0);
/*  354 */       int $$28 = 1;
/*  355 */       for (ObjectListIterator<OceanMonumentPieces.RoomDefinition> objectListIterator = $$26.iterator(); objectListIterator.hasNext(); ) { OceanMonumentPieces.RoomDefinition $$29 = objectListIterator.next();
/*      */         
/*  357 */         int $$30 = 0;
/*  358 */         int $$31 = 0;
/*  359 */         while ($$30 < 2 && $$31 < 5) {
/*  360 */           $$31++;
/*      */           
/*  362 */           int $$32 = $$0.nextInt(6);
/*  363 */           if ($$29.hasOpening[$$32]) {
/*  364 */             int $$33 = Direction.from3DDataValue($$32).getOpposite().get3DDataValue();
/*      */ 
/*      */             
/*  367 */             $$29.hasOpening[$$32] = false;
/*  368 */             ($$29.connections[$$32]).hasOpening[$$33] = false;
/*      */             
/*  370 */             if ($$29.findSource($$28++) && $$29.connections[$$32].findSource($$28++)) {
/*  371 */               $$30++;
/*      */               continue;
/*      */             } 
/*  374 */             $$29.hasOpening[$$32] = true;
/*  375 */             ($$29.connections[$$32]).hasOpening[$$33] = true;
/*      */           } 
/*      */         }  }
/*      */ 
/*      */       
/*  380 */       $$26.add($$23);
/*  381 */       $$26.add($$24);
/*  382 */       $$26.add($$25);
/*      */       
/*  384 */       return (List<OceanMonumentPieces.RoomDefinition>)$$26;
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  389 */       int $$7 = Math.max($$0.getSeaLevel(), 64) - this.boundingBox.minY();
/*      */       
/*  391 */       generateWaterBox($$0, $$4, 0, 0, 0, 58, $$7, 58);
/*      */ 
/*      */       
/*  394 */       generateWing(false, 0, $$0, $$3, $$4);
/*      */ 
/*      */       
/*  397 */       generateWing(true, 33, $$0, $$3, $$4);
/*      */ 
/*      */       
/*  400 */       generateEntranceArchs($$0, $$3, $$4);
/*      */       
/*  402 */       generateEntranceWall($$0, $$3, $$4);
/*  403 */       generateRoofPiece($$0, $$3, $$4);
/*      */       
/*  405 */       generateLowerWall($$0, $$3, $$4);
/*  406 */       generateMiddleWall($$0, $$3, $$4);
/*  407 */       generateUpperWall($$0, $$3, $$4);
/*      */ 
/*      */       
/*  410 */       for (int $$8 = 0; $$8 < 7; $$8++) {
/*  411 */         for (int $$9 = 0; $$9 < 7; ) {
/*  412 */           if ($$9 == 0 && $$8 == 3)
/*      */           {
/*  414 */             $$9 = 6;
/*      */           }
/*      */           
/*  417 */           int $$10 = $$8 * 9;
/*  418 */           int $$11 = $$9 * 9;
/*  419 */           for (int $$12 = 0; $$12 < 4; $$12++) {
/*  420 */             for (int $$13 = 0; $$13 < 4; $$13++) {
/*  421 */               placeBlock($$0, BASE_LIGHT, $$10 + $$12, 0, $$11 + $$13, $$4);
/*  422 */               fillColumnDown($$0, BASE_LIGHT, $$10 + $$12, -1, $$11 + $$13, $$4);
/*      */             } 
/*      */           } 
/*      */           
/*  426 */           if ($$8 == 0 || $$8 == 6) {
/*  427 */             $$9++; continue;
/*      */           } 
/*  429 */           $$9 += 6;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  435 */       for (int $$14 = 0; $$14 < 5; $$14++) {
/*  436 */         generateWaterBox($$0, $$4, -1 - $$14, 0 + $$14 * 2, -1 - $$14, -1 - $$14, 23, 58 + $$14);
/*  437 */         generateWaterBox($$0, $$4, 58 + $$14, 0 + $$14 * 2, -1 - $$14, 58 + $$14, 23, 58 + $$14);
/*  438 */         generateWaterBox($$0, $$4, 0 - $$14, 0 + $$14 * 2, -1 - $$14, 57 + $$14, 23, -1 - $$14);
/*  439 */         generateWaterBox($$0, $$4, 0 - $$14, 0 + $$14 * 2, 58 + $$14, 57 + $$14, 23, 58 + $$14);
/*      */       } 
/*      */       
/*  442 */       for (OceanMonumentPieces.OceanMonumentPiece $$15 : this.childPieces) {
/*  443 */         if ($$15.getBoundingBox().intersects($$4)) {
/*  444 */           $$15.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateWing(boolean $$0, int $$1, WorldGenLevel $$2, RandomSource $$3, BoundingBox $$4) {
/*  451 */       int $$5 = 24;
/*  452 */       if (chunkIntersects($$4, $$1, 0, $$1 + 23, 20)) {
/*  453 */         generateBox($$2, $$4, $$1 + 0, 0, 0, $$1 + 24, 0, 20, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  455 */         generateWaterBox($$2, $$4, $$1 + 0, 1, 0, $$1 + 24, 10, 20);
/*      */         
/*  457 */         for (int $$6 = 0; $$6 < 4; $$6++) {
/*  458 */           generateBox($$2, $$4, $$1 + $$6, $$6 + 1, $$6, $$1 + $$6, $$6 + 1, 20, BASE_LIGHT, BASE_LIGHT, false);
/*  459 */           generateBox($$2, $$4, $$1 + $$6 + 7, $$6 + 5, $$6 + 7, $$1 + $$6 + 7, $$6 + 5, 20, BASE_LIGHT, BASE_LIGHT, false);
/*  460 */           generateBox($$2, $$4, $$1 + 17 - $$6, $$6 + 5, $$6 + 7, $$1 + 17 - $$6, $$6 + 5, 20, BASE_LIGHT, BASE_LIGHT, false);
/*  461 */           generateBox($$2, $$4, $$1 + 24 - $$6, $$6 + 1, $$6, $$1 + 24 - $$6, $$6 + 1, 20, BASE_LIGHT, BASE_LIGHT, false);
/*      */           
/*  463 */           generateBox($$2, $$4, $$1 + $$6 + 1, $$6 + 1, $$6, $$1 + 23 - $$6, $$6 + 1, $$6, BASE_LIGHT, BASE_LIGHT, false);
/*  464 */           generateBox($$2, $$4, $$1 + $$6 + 8, $$6 + 5, $$6 + 7, $$1 + 16 - $$6, $$6 + 5, $$6 + 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  466 */         generateBox($$2, $$4, $$1 + 4, 4, 4, $$1 + 6, 4, 20, BASE_GRAY, BASE_GRAY, false);
/*  467 */         generateBox($$2, $$4, $$1 + 7, 4, 4, $$1 + 17, 4, 6, BASE_GRAY, BASE_GRAY, false);
/*  468 */         generateBox($$2, $$4, $$1 + 18, 4, 4, $$1 + 20, 4, 20, BASE_GRAY, BASE_GRAY, false);
/*  469 */         generateBox($$2, $$4, $$1 + 11, 8, 11, $$1 + 13, 8, 20, BASE_GRAY, BASE_GRAY, false);
/*  470 */         placeBlock($$2, DOT_DECO_DATA, $$1 + 12, 9, 12, $$4);
/*  471 */         placeBlock($$2, DOT_DECO_DATA, $$1 + 12, 9, 15, $$4);
/*  472 */         placeBlock($$2, DOT_DECO_DATA, $$1 + 12, 9, 18, $$4);
/*      */         
/*  474 */         int $$7 = $$1 + ($$0 ? 19 : 5);
/*  475 */         int $$8 = $$1 + ($$0 ? 5 : 19);
/*  476 */         for (int $$9 = 20; $$9 >= 5; $$9 -= 3) {
/*  477 */           placeBlock($$2, DOT_DECO_DATA, $$7, 5, $$9, $$4);
/*      */         }
/*  479 */         for (int $$10 = 19; $$10 >= 7; $$10 -= 3) {
/*  480 */           placeBlock($$2, DOT_DECO_DATA, $$8, 5, $$10, $$4);
/*      */         }
/*  482 */         for (int $$11 = 0; $$11 < 4; $$11++) {
/*  483 */           int $$12 = $$0 ? ($$1 + 24 - 17 - $$11 * 3) : ($$1 + 17 - $$11 * 3);
/*  484 */           placeBlock($$2, DOT_DECO_DATA, $$12, 5, 5, $$4);
/*      */         } 
/*  486 */         placeBlock($$2, DOT_DECO_DATA, $$8, 5, 5, $$4);
/*      */ 
/*      */         
/*  489 */         generateBox($$2, $$4, $$1 + 11, 1, 12, $$1 + 13, 7, 12, BASE_GRAY, BASE_GRAY, false);
/*  490 */         generateBox($$2, $$4, $$1 + 12, 1, 11, $$1 + 12, 7, 13, BASE_GRAY, BASE_GRAY, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateEntranceArchs(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/*  496 */       if (chunkIntersects($$2, 22, 5, 35, 17)) {
/*      */         
/*  498 */         generateWaterBox($$0, $$2, 25, 0, 0, 32, 8, 20);
/*      */ 
/*      */         
/*  501 */         for (int $$3 = 0; $$3 < 4; $$3++) {
/*  502 */           generateBox($$0, $$2, 24, 2, 5 + $$3 * 4, 24, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/*  503 */           generateBox($$0, $$2, 22, 4, 5 + $$3 * 4, 23, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/*  504 */           placeBlock($$0, BASE_LIGHT, 25, 5, 5 + $$3 * 4, $$2);
/*  505 */           placeBlock($$0, BASE_LIGHT, 26, 6, 5 + $$3 * 4, $$2);
/*  506 */           placeBlock($$0, LAMP_BLOCK, 26, 5, 5 + $$3 * 4, $$2);
/*      */           
/*  508 */           generateBox($$0, $$2, 33, 2, 5 + $$3 * 4, 33, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/*  509 */           generateBox($$0, $$2, 34, 4, 5 + $$3 * 4, 35, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/*  510 */           placeBlock($$0, BASE_LIGHT, 32, 5, 5 + $$3 * 4, $$2);
/*  511 */           placeBlock($$0, BASE_LIGHT, 31, 6, 5 + $$3 * 4, $$2);
/*  512 */           placeBlock($$0, LAMP_BLOCK, 31, 5, 5 + $$3 * 4, $$2);
/*      */           
/*  514 */           generateBox($$0, $$2, 27, 6, 5 + $$3 * 4, 30, 6, 5 + $$3 * 4, BASE_GRAY, BASE_GRAY, false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void generateEntranceWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/*  522 */       if (chunkIntersects($$2, 15, 20, 42, 21)) {
/*  523 */         generateBox($$0, $$2, 15, 0, 21, 42, 0, 21, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  525 */         generateWaterBox($$0, $$2, 26, 1, 21, 31, 3, 21);
/*      */ 
/*      */ 
/*      */         
/*  529 */         generateBox($$0, $$2, 21, 12, 21, 36, 12, 21, BASE_GRAY, BASE_GRAY, false);
/*  530 */         generateBox($$0, $$2, 17, 11, 21, 40, 11, 21, BASE_GRAY, BASE_GRAY, false);
/*  531 */         generateBox($$0, $$2, 16, 10, 21, 41, 10, 21, BASE_GRAY, BASE_GRAY, false);
/*  532 */         generateBox($$0, $$2, 15, 7, 21, 42, 9, 21, BASE_GRAY, BASE_GRAY, false);
/*  533 */         generateBox($$0, $$2, 16, 6, 21, 41, 6, 21, BASE_GRAY, BASE_GRAY, false);
/*  534 */         generateBox($$0, $$2, 17, 5, 21, 40, 5, 21, BASE_GRAY, BASE_GRAY, false);
/*  535 */         generateBox($$0, $$2, 21, 4, 21, 36, 4, 21, BASE_GRAY, BASE_GRAY, false);
/*  536 */         generateBox($$0, $$2, 22, 3, 21, 26, 3, 21, BASE_GRAY, BASE_GRAY, false);
/*  537 */         generateBox($$0, $$2, 31, 3, 21, 35, 3, 21, BASE_GRAY, BASE_GRAY, false);
/*  538 */         generateBox($$0, $$2, 23, 2, 21, 25, 2, 21, BASE_GRAY, BASE_GRAY, false);
/*  539 */         generateBox($$0, $$2, 32, 2, 21, 34, 2, 21, BASE_GRAY, BASE_GRAY, false);
/*      */ 
/*      */         
/*  542 */         generateBox($$0, $$2, 28, 4, 20, 29, 4, 21, BASE_LIGHT, BASE_LIGHT, false);
/*  543 */         placeBlock($$0, BASE_LIGHT, 27, 3, 21, $$2);
/*  544 */         placeBlock($$0, BASE_LIGHT, 30, 3, 21, $$2);
/*  545 */         placeBlock($$0, BASE_LIGHT, 26, 2, 21, $$2);
/*  546 */         placeBlock($$0, BASE_LIGHT, 31, 2, 21, $$2);
/*  547 */         placeBlock($$0, BASE_LIGHT, 25, 1, 21, $$2);
/*  548 */         placeBlock($$0, BASE_LIGHT, 32, 1, 21, $$2);
/*  549 */         for (int $$3 = 0; $$3 < 7; $$3++) {
/*  550 */           placeBlock($$0, BASE_BLACK, 28 - $$3, 6 + $$3, 21, $$2);
/*  551 */           placeBlock($$0, BASE_BLACK, 29 + $$3, 6 + $$3, 21, $$2);
/*      */         } 
/*  553 */         for (int $$4 = 0; $$4 < 4; $$4++) {
/*  554 */           placeBlock($$0, BASE_BLACK, 28 - $$4, 9 + $$4, 21, $$2);
/*  555 */           placeBlock($$0, BASE_BLACK, 29 + $$4, 9 + $$4, 21, $$2);
/*      */         } 
/*  557 */         placeBlock($$0, BASE_BLACK, 28, 12, 21, $$2);
/*  558 */         placeBlock($$0, BASE_BLACK, 29, 12, 21, $$2);
/*  559 */         for (int $$5 = 0; $$5 < 3; $$5++) {
/*  560 */           placeBlock($$0, BASE_BLACK, 22 - $$5 * 2, 8, 21, $$2);
/*  561 */           placeBlock($$0, BASE_BLACK, 22 - $$5 * 2, 9, 21, $$2);
/*      */           
/*  563 */           placeBlock($$0, BASE_BLACK, 35 + $$5 * 2, 8, 21, $$2);
/*  564 */           placeBlock($$0, BASE_BLACK, 35 + $$5 * 2, 9, 21, $$2);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  569 */         generateWaterBox($$0, $$2, 15, 13, 21, 42, 15, 21);
/*  570 */         generateWaterBox($$0, $$2, 15, 1, 21, 15, 6, 21);
/*  571 */         generateWaterBox($$0, $$2, 16, 1, 21, 16, 5, 21);
/*  572 */         generateWaterBox($$0, $$2, 17, 1, 21, 20, 4, 21);
/*  573 */         generateWaterBox($$0, $$2, 21, 1, 21, 21, 3, 21);
/*  574 */         generateWaterBox($$0, $$2, 22, 1, 21, 22, 2, 21);
/*  575 */         generateWaterBox($$0, $$2, 23, 1, 21, 24, 1, 21);
/*  576 */         generateWaterBox($$0, $$2, 42, 1, 21, 42, 6, 21);
/*  577 */         generateWaterBox($$0, $$2, 41, 1, 21, 41, 5, 21);
/*  578 */         generateWaterBox($$0, $$2, 37, 1, 21, 40, 4, 21);
/*  579 */         generateWaterBox($$0, $$2, 36, 1, 21, 36, 3, 21);
/*  580 */         generateWaterBox($$0, $$2, 33, 1, 21, 34, 1, 21);
/*  581 */         generateWaterBox($$0, $$2, 35, 1, 21, 35, 2, 21);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void generateRoofPiece(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/*  589 */       if (chunkIntersects($$2, 21, 21, 36, 36)) {
/*  590 */         generateBox($$0, $$2, 21, 0, 22, 36, 0, 36, BASE_GRAY, BASE_GRAY, false);
/*      */ 
/*      */ 
/*      */         
/*  594 */         generateWaterBox($$0, $$2, 21, 1, 22, 36, 23, 36);
/*      */ 
/*      */         
/*  597 */         for (int $$3 = 0; $$3 < 4; $$3++) {
/*  598 */           generateBox($$0, $$2, 21 + $$3, 13 + $$3, 21 + $$3, 36 - $$3, 13 + $$3, 21 + $$3, BASE_LIGHT, BASE_LIGHT, false);
/*  599 */           generateBox($$0, $$2, 21 + $$3, 13 + $$3, 36 - $$3, 36 - $$3, 13 + $$3, 36 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*  600 */           generateBox($$0, $$2, 21 + $$3, 13 + $$3, 22 + $$3, 21 + $$3, 13 + $$3, 35 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*  601 */           generateBox($$0, $$2, 36 - $$3, 13 + $$3, 22 + $$3, 36 - $$3, 13 + $$3, 35 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  603 */         generateBox($$0, $$2, 25, 16, 25, 32, 16, 32, BASE_GRAY, BASE_GRAY, false);
/*  604 */         generateBox($$0, $$2, 25, 17, 25, 25, 19, 25, BASE_LIGHT, BASE_LIGHT, false);
/*  605 */         generateBox($$0, $$2, 32, 17, 25, 32, 19, 25, BASE_LIGHT, BASE_LIGHT, false);
/*  606 */         generateBox($$0, $$2, 25, 17, 32, 25, 19, 32, BASE_LIGHT, BASE_LIGHT, false);
/*  607 */         generateBox($$0, $$2, 32, 17, 32, 32, 19, 32, BASE_LIGHT, BASE_LIGHT, false);
/*      */         
/*  609 */         placeBlock($$0, BASE_LIGHT, 26, 20, 26, $$2);
/*  610 */         placeBlock($$0, BASE_LIGHT, 27, 21, 27, $$2);
/*  611 */         placeBlock($$0, LAMP_BLOCK, 27, 20, 27, $$2);
/*  612 */         placeBlock($$0, BASE_LIGHT, 26, 20, 31, $$2);
/*  613 */         placeBlock($$0, BASE_LIGHT, 27, 21, 30, $$2);
/*  614 */         placeBlock($$0, LAMP_BLOCK, 27, 20, 30, $$2);
/*  615 */         placeBlock($$0, BASE_LIGHT, 31, 20, 31, $$2);
/*  616 */         placeBlock($$0, BASE_LIGHT, 30, 21, 30, $$2);
/*  617 */         placeBlock($$0, LAMP_BLOCK, 30, 20, 30, $$2);
/*  618 */         placeBlock($$0, BASE_LIGHT, 31, 20, 26, $$2);
/*  619 */         placeBlock($$0, BASE_LIGHT, 30, 21, 27, $$2);
/*  620 */         placeBlock($$0, LAMP_BLOCK, 30, 20, 27, $$2);
/*      */         
/*  622 */         generateBox($$0, $$2, 28, 21, 27, 29, 21, 27, BASE_GRAY, BASE_GRAY, false);
/*  623 */         generateBox($$0, $$2, 27, 21, 28, 27, 21, 29, BASE_GRAY, BASE_GRAY, false);
/*  624 */         generateBox($$0, $$2, 28, 21, 30, 29, 21, 30, BASE_GRAY, BASE_GRAY, false);
/*  625 */         generateBox($$0, $$2, 30, 21, 28, 30, 21, 29, BASE_GRAY, BASE_GRAY, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void generateLowerWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/*  632 */       if (chunkIntersects($$2, 0, 21, 6, 58)) {
/*  633 */         generateBox($$0, $$2, 0, 0, 21, 6, 0, 57, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  635 */         generateWaterBox($$0, $$2, 0, 1, 21, 6, 7, 57);
/*      */ 
/*      */         
/*  638 */         generateBox($$0, $$2, 4, 4, 21, 6, 4, 53, BASE_GRAY, BASE_GRAY, false);
/*  639 */         for (int $$3 = 0; $$3 < 4; $$3++) {
/*  640 */           generateBox($$0, $$2, $$3, $$3 + 1, 21, $$3, $$3 + 1, 57 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*  642 */         for (int $$4 = 23; $$4 < 53; $$4 += 3) {
/*  643 */           placeBlock($$0, DOT_DECO_DATA, 5, 5, $$4, $$2);
/*      */         }
/*  645 */         placeBlock($$0, DOT_DECO_DATA, 5, 5, 52, $$2);
/*      */         
/*  647 */         for (int $$5 = 0; $$5 < 4; $$5++) {
/*  648 */           generateBox($$0, $$2, $$5, $$5 + 1, 21, $$5, $$5 + 1, 57 - $$5, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*      */         
/*  651 */         generateBox($$0, $$2, 4, 1, 52, 6, 3, 52, BASE_GRAY, BASE_GRAY, false);
/*  652 */         generateBox($$0, $$2, 5, 1, 51, 5, 3, 53, BASE_GRAY, BASE_GRAY, false);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  657 */       if (chunkIntersects($$2, 51, 21, 58, 58)) {
/*  658 */         generateBox($$0, $$2, 51, 0, 21, 57, 0, 57, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  660 */         generateWaterBox($$0, $$2, 51, 1, 21, 57, 7, 57);
/*      */ 
/*      */         
/*  663 */         generateBox($$0, $$2, 51, 4, 21, 53, 4, 53, BASE_GRAY, BASE_GRAY, false);
/*  664 */         for (int $$6 = 0; $$6 < 4; $$6++) {
/*  665 */           generateBox($$0, $$2, 57 - $$6, $$6 + 1, 21, 57 - $$6, $$6 + 1, 57 - $$6, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*  667 */         for (int $$7 = 23; $$7 < 53; $$7 += 3) {
/*  668 */           placeBlock($$0, DOT_DECO_DATA, 52, 5, $$7, $$2);
/*      */         }
/*  670 */         placeBlock($$0, DOT_DECO_DATA, 52, 5, 52, $$2);
/*      */ 
/*      */         
/*  673 */         generateBox($$0, $$2, 51, 1, 52, 53, 3, 52, BASE_GRAY, BASE_GRAY, false);
/*  674 */         generateBox($$0, $$2, 52, 1, 51, 52, 3, 53, BASE_GRAY, BASE_GRAY, false);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  679 */       if (chunkIntersects($$2, 0, 51, 57, 57)) {
/*  680 */         generateBox($$0, $$2, 7, 0, 51, 50, 0, 57, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  682 */         generateWaterBox($$0, $$2, 7, 1, 51, 50, 10, 57);
/*      */ 
/*      */         
/*  685 */         for (int $$8 = 0; $$8 < 4; $$8++) {
/*  686 */           generateBox($$0, $$2, $$8 + 1, $$8 + 1, 57 - $$8, 56 - $$8, $$8 + 1, 57 - $$8, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void generateMiddleWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/*  694 */       if (chunkIntersects($$2, 7, 21, 13, 50)) {
/*  695 */         generateBox($$0, $$2, 7, 0, 21, 13, 0, 50, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  697 */         generateWaterBox($$0, $$2, 7, 1, 21, 13, 10, 50);
/*      */ 
/*      */         
/*  700 */         generateBox($$0, $$2, 11, 8, 21, 13, 8, 53, BASE_GRAY, BASE_GRAY, false);
/*  701 */         for (int $$3 = 0; $$3 < 4; $$3++) {
/*  702 */           generateBox($$0, $$2, $$3 + 7, $$3 + 5, 21, $$3 + 7, $$3 + 5, 54, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*  704 */         for (int $$4 = 21; $$4 <= 45; $$4 += 3) {
/*  705 */           placeBlock($$0, DOT_DECO_DATA, 12, 9, $$4, $$2);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  711 */       if (chunkIntersects($$2, 44, 21, 50, 54)) {
/*  712 */         generateBox($$0, $$2, 44, 0, 21, 50, 0, 50, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  714 */         generateWaterBox($$0, $$2, 44, 1, 21, 50, 10, 50);
/*      */ 
/*      */         
/*  717 */         generateBox($$0, $$2, 44, 8, 21, 46, 8, 53, BASE_GRAY, BASE_GRAY, false);
/*  718 */         for (int $$5 = 0; $$5 < 4; $$5++) {
/*  719 */           generateBox($$0, $$2, 50 - $$5, $$5 + 5, 21, 50 - $$5, $$5 + 5, 54, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*  721 */         for (int $$6 = 21; $$6 <= 45; $$6 += 3) {
/*  722 */           placeBlock($$0, DOT_DECO_DATA, 45, 9, $$6, $$2);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  728 */       if (chunkIntersects($$2, 8, 44, 49, 54)) {
/*  729 */         generateBox($$0, $$2, 14, 0, 44, 43, 0, 50, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  731 */         generateWaterBox($$0, $$2, 14, 1, 44, 43, 10, 50);
/*      */ 
/*      */         
/*  734 */         for (int $$7 = 12; $$7 <= 45; $$7 += 3) {
/*  735 */           placeBlock($$0, DOT_DECO_DATA, $$7, 9, 45, $$2);
/*  736 */           placeBlock($$0, DOT_DECO_DATA, $$7, 9, 52, $$2);
/*  737 */           if ($$7 == 12 || $$7 == 18 || $$7 == 24 || $$7 == 33 || $$7 == 39 || $$7 == 45) {
/*  738 */             placeBlock($$0, DOT_DECO_DATA, $$7, 9, 47, $$2);
/*  739 */             placeBlock($$0, DOT_DECO_DATA, $$7, 9, 50, $$2);
/*  740 */             placeBlock($$0, DOT_DECO_DATA, $$7, 10, 45, $$2);
/*  741 */             placeBlock($$0, DOT_DECO_DATA, $$7, 10, 46, $$2);
/*  742 */             placeBlock($$0, DOT_DECO_DATA, $$7, 10, 51, $$2);
/*  743 */             placeBlock($$0, DOT_DECO_DATA, $$7, 10, 52, $$2);
/*  744 */             placeBlock($$0, DOT_DECO_DATA, $$7, 11, 47, $$2);
/*  745 */             placeBlock($$0, DOT_DECO_DATA, $$7, 11, 50, $$2);
/*  746 */             placeBlock($$0, DOT_DECO_DATA, $$7, 12, 48, $$2);
/*  747 */             placeBlock($$0, DOT_DECO_DATA, $$7, 12, 49, $$2);
/*      */           } 
/*      */         } 
/*      */         
/*  751 */         for (int $$8 = 0; $$8 < 3; $$8++) {
/*  752 */           generateBox($$0, $$2, 8 + $$8, 5 + $$8, 54, 49 - $$8, 5 + $$8, 54, BASE_GRAY, BASE_GRAY, false);
/*      */         }
/*  754 */         generateBox($$0, $$2, 11, 8, 54, 46, 8, 54, BASE_LIGHT, BASE_LIGHT, false);
/*  755 */         generateBox($$0, $$2, 14, 8, 44, 43, 8, 53, BASE_GRAY, BASE_GRAY, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void generateUpperWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/*  762 */       if (chunkIntersects($$2, 14, 21, 20, 43)) {
/*  763 */         generateBox($$0, $$2, 14, 0, 21, 20, 0, 43, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  765 */         generateWaterBox($$0, $$2, 14, 1, 22, 20, 14, 43);
/*      */ 
/*      */         
/*  768 */         generateBox($$0, $$2, 18, 12, 22, 20, 12, 39, BASE_GRAY, BASE_GRAY, false);
/*  769 */         generateBox($$0, $$2, 18, 12, 21, 20, 12, 21, BASE_LIGHT, BASE_LIGHT, false);
/*  770 */         for (int $$3 = 0; $$3 < 4; $$3++) {
/*  771 */           generateBox($$0, $$2, $$3 + 14, $$3 + 9, 21, $$3 + 14, $$3 + 9, 43 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*  773 */         for (int $$4 = 23; $$4 <= 39; $$4 += 3) {
/*  774 */           placeBlock($$0, DOT_DECO_DATA, 19, 13, $$4, $$2);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  780 */       if (chunkIntersects($$2, 37, 21, 43, 43)) {
/*  781 */         generateBox($$0, $$2, 37, 0, 21, 43, 0, 43, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  783 */         generateWaterBox($$0, $$2, 37, 1, 22, 43, 14, 43);
/*      */ 
/*      */         
/*  786 */         generateBox($$0, $$2, 37, 12, 22, 39, 12, 39, BASE_GRAY, BASE_GRAY, false);
/*  787 */         generateBox($$0, $$2, 37, 12, 21, 39, 12, 21, BASE_LIGHT, BASE_LIGHT, false);
/*  788 */         for (int $$5 = 0; $$5 < 4; $$5++) {
/*  789 */           generateBox($$0, $$2, 43 - $$5, $$5 + 9, 21, 43 - $$5, $$5 + 9, 43 - $$5, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*  791 */         for (int $$6 = 23; $$6 <= 39; $$6 += 3) {
/*  792 */           placeBlock($$0, DOT_DECO_DATA, 38, 13, $$6, $$2);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  798 */       if (chunkIntersects($$2, 15, 37, 42, 43)) {
/*  799 */         generateBox($$0, $$2, 21, 0, 37, 36, 0, 43, BASE_GRAY, BASE_GRAY, false);
/*      */         
/*  801 */         generateWaterBox($$0, $$2, 21, 1, 37, 36, 14, 43);
/*      */ 
/*      */         
/*  804 */         generateBox($$0, $$2, 21, 12, 37, 36, 12, 39, BASE_GRAY, BASE_GRAY, false);
/*  805 */         for (int $$7 = 0; $$7 < 4; $$7++) {
/*  806 */           generateBox($$0, $$2, 15 + $$7, $$7 + 9, 43 - $$7, 42 - $$7, $$7 + 9, 43 - $$7, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/*  808 */         for (int $$8 = 21; $$8 <= 36; $$8 += 3)
/*  809 */           placeBlock($$0, DOT_DECO_DATA, $$8, 13, 38, $$2); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentEntryRoom
/*      */     extends OceanMonumentPiece {
/*      */     public OceanMonumentEntryRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/*  817 */       super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, 1, $$0, $$1, 1, 1, 1);
/*      */     }
/*      */     
/*      */     public OceanMonumentEntryRoom(CompoundTag $$0) {
/*  821 */       super(StructurePieceType.OCEAN_MONUMENT_ENTRY_ROOM, $$0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  827 */       generateBox($$0, $$4, 0, 3, 0, 2, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  828 */       generateBox($$0, $$4, 5, 3, 0, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  829 */       generateBox($$0, $$4, 0, 2, 0, 1, 2, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  830 */       generateBox($$0, $$4, 6, 2, 0, 7, 2, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  831 */       generateBox($$0, $$4, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  832 */       generateBox($$0, $$4, 7, 1, 0, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */ 
/*      */       
/*  835 */       generateBox($$0, $$4, 0, 1, 7, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */ 
/*      */       
/*  838 */       generateBox($$0, $$4, 1, 1, 0, 2, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/*  839 */       generateBox($$0, $$4, 5, 1, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/*  841 */       if (this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
/*  842 */         generateWaterBox($$0, $$4, 3, 1, 7, 4, 2, 7);
/*      */       }
/*  844 */       if (this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
/*  845 */         generateWaterBox($$0, $$4, 0, 1, 3, 1, 2, 4);
/*      */       }
/*  847 */       if (this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()])
/*  848 */         generateWaterBox($$0, $$4, 6, 1, 3, 7, 2, 4); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentSimpleRoom
/*      */     extends OceanMonumentPiece {
/*      */     private int mainDesign;
/*      */     
/*      */     public OceanMonumentSimpleRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/*  857 */       super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, 1, $$0, $$1, 1, 1, 1);
/*  858 */       this.mainDesign = $$2.nextInt(3);
/*      */     }
/*      */     
/*      */     public OceanMonumentSimpleRoom(CompoundTag $$0) {
/*  862 */       super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  867 */       if (this.roomDefinition.index / 25 > 0) {
/*  868 */         generateDefaultFloor($$0, $$4, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()]);
/*      */       }
/*  870 */       if (this.roomDefinition.connections[Direction.UP.get3DDataValue()] == null) {
/*  871 */         generateBoxOnFillOnly($$0, $$4, 1, 4, 1, 6, 4, 6, BASE_GRAY);
/*      */       }
/*      */       
/*  874 */       boolean $$7 = (this.mainDesign != 0 && $$3.nextBoolean() && !this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()] && !this.roomDefinition.hasOpening[Direction.UP.get3DDataValue()] && this.roomDefinition.countOpenings() > 1);
/*      */       
/*  876 */       if (this.mainDesign == 0) {
/*      */         
/*  878 */         generateBox($$0, $$4, 0, 1, 0, 2, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
/*  879 */         generateBox($$0, $$4, 0, 3, 0, 2, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/*  880 */         generateBox($$0, $$4, 0, 2, 0, 0, 2, 2, BASE_GRAY, BASE_GRAY, false);
/*  881 */         generateBox($$0, $$4, 1, 2, 0, 2, 2, 0, BASE_GRAY, BASE_GRAY, false);
/*  882 */         placeBlock($$0, LAMP_BLOCK, 1, 2, 1, $$4);
/*      */ 
/*      */         
/*  885 */         generateBox($$0, $$4, 5, 1, 0, 7, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
/*  886 */         generateBox($$0, $$4, 5, 3, 0, 7, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/*  887 */         generateBox($$0, $$4, 7, 2, 0, 7, 2, 2, BASE_GRAY, BASE_GRAY, false);
/*  888 */         generateBox($$0, $$4, 5, 2, 0, 6, 2, 0, BASE_GRAY, BASE_GRAY, false);
/*  889 */         placeBlock($$0, LAMP_BLOCK, 6, 2, 1, $$4);
/*      */ 
/*      */         
/*  892 */         generateBox($$0, $$4, 0, 1, 5, 2, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  893 */         generateBox($$0, $$4, 0, 3, 5, 2, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  894 */         generateBox($$0, $$4, 0, 2, 5, 0, 2, 7, BASE_GRAY, BASE_GRAY, false);
/*  895 */         generateBox($$0, $$4, 1, 2, 7, 2, 2, 7, BASE_GRAY, BASE_GRAY, false);
/*  896 */         placeBlock($$0, LAMP_BLOCK, 1, 2, 6, $$4);
/*      */ 
/*      */         
/*  899 */         generateBox($$0, $$4, 5, 1, 5, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  900 */         generateBox($$0, $$4, 5, 3, 5, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  901 */         generateBox($$0, $$4, 7, 2, 5, 7, 2, 7, BASE_GRAY, BASE_GRAY, false);
/*  902 */         generateBox($$0, $$4, 5, 2, 7, 6, 2, 7, BASE_GRAY, BASE_GRAY, false);
/*  903 */         placeBlock($$0, LAMP_BLOCK, 6, 2, 6, $$4);
/*      */         
/*  905 */         if (this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/*  906 */           generateBox($$0, $$4, 3, 3, 0, 4, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/*  908 */           generateBox($$0, $$4, 3, 3, 0, 4, 3, 1, BASE_LIGHT, BASE_LIGHT, false);
/*  909 */           generateBox($$0, $$4, 3, 2, 0, 4, 2, 0, BASE_GRAY, BASE_GRAY, false);
/*  910 */           generateBox($$0, $$4, 3, 1, 0, 4, 1, 1, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  912 */         if (this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
/*  913 */           generateBox($$0, $$4, 3, 3, 7, 4, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/*  915 */           generateBox($$0, $$4, 3, 3, 6, 4, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  916 */           generateBox($$0, $$4, 3, 2, 7, 4, 2, 7, BASE_GRAY, BASE_GRAY, false);
/*  917 */           generateBox($$0, $$4, 3, 1, 6, 4, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  919 */         if (this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
/*  920 */           generateBox($$0, $$4, 0, 3, 3, 0, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/*  922 */           generateBox($$0, $$4, 0, 3, 3, 1, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/*  923 */           generateBox($$0, $$4, 0, 2, 3, 0, 2, 4, BASE_GRAY, BASE_GRAY, false);
/*  924 */           generateBox($$0, $$4, 0, 1, 3, 1, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  926 */         if (this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()]) {
/*  927 */           generateBox($$0, $$4, 7, 3, 3, 7, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/*  929 */           generateBox($$0, $$4, 6, 3, 3, 7, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/*  930 */           generateBox($$0, $$4, 7, 2, 3, 7, 2, 4, BASE_GRAY, BASE_GRAY, false);
/*  931 */           generateBox($$0, $$4, 6, 1, 3, 7, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  933 */       } else if (this.mainDesign == 1) {
/*      */         
/*  935 */         generateBox($$0, $$4, 2, 1, 2, 2, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/*  936 */         generateBox($$0, $$4, 2, 1, 5, 2, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
/*  937 */         generateBox($$0, $$4, 5, 1, 5, 5, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
/*  938 */         generateBox($$0, $$4, 5, 1, 2, 5, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/*  939 */         placeBlock($$0, LAMP_BLOCK, 2, 2, 2, $$4);
/*  940 */         placeBlock($$0, LAMP_BLOCK, 2, 2, 5, $$4);
/*  941 */         placeBlock($$0, LAMP_BLOCK, 5, 2, 5, $$4);
/*  942 */         placeBlock($$0, LAMP_BLOCK, 5, 2, 2, $$4);
/*      */ 
/*      */         
/*  945 */         generateBox($$0, $$4, 0, 1, 0, 1, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/*  946 */         generateBox($$0, $$4, 0, 1, 1, 0, 3, 1, BASE_LIGHT, BASE_LIGHT, false);
/*  947 */         generateBox($$0, $$4, 0, 1, 7, 1, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  948 */         generateBox($$0, $$4, 0, 1, 6, 0, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/*  949 */         generateBox($$0, $$4, 6, 1, 7, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  950 */         generateBox($$0, $$4, 7, 1, 6, 7, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/*  951 */         generateBox($$0, $$4, 6, 1, 0, 7, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/*  952 */         generateBox($$0, $$4, 7, 1, 1, 7, 3, 1, BASE_LIGHT, BASE_LIGHT, false);
/*  953 */         placeBlock($$0, BASE_GRAY, 1, 2, 0, $$4);
/*  954 */         placeBlock($$0, BASE_GRAY, 0, 2, 1, $$4);
/*  955 */         placeBlock($$0, BASE_GRAY, 1, 2, 7, $$4);
/*  956 */         placeBlock($$0, BASE_GRAY, 0, 2, 6, $$4);
/*  957 */         placeBlock($$0, BASE_GRAY, 6, 2, 7, $$4);
/*  958 */         placeBlock($$0, BASE_GRAY, 7, 2, 6, $$4);
/*  959 */         placeBlock($$0, BASE_GRAY, 6, 2, 0, $$4);
/*  960 */         placeBlock($$0, BASE_GRAY, 7, 2, 1, $$4);
/*  961 */         if (!this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/*  962 */           generateBox($$0, $$4, 1, 3, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/*  963 */           generateBox($$0, $$4, 1, 2, 0, 6, 2, 0, BASE_GRAY, BASE_GRAY, false);
/*  964 */           generateBox($$0, $$4, 1, 1, 0, 6, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  966 */         if (!this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
/*  967 */           generateBox($$0, $$4, 1, 3, 7, 6, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  968 */           generateBox($$0, $$4, 1, 2, 7, 6, 2, 7, BASE_GRAY, BASE_GRAY, false);
/*  969 */           generateBox($$0, $$4, 1, 1, 7, 6, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  971 */         if (!this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
/*  972 */           generateBox($$0, $$4, 0, 3, 1, 0, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/*  973 */           generateBox($$0, $$4, 0, 2, 1, 0, 2, 6, BASE_GRAY, BASE_GRAY, false);
/*  974 */           generateBox($$0, $$4, 0, 1, 1, 0, 1, 6, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  976 */         if (!this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()]) {
/*  977 */           generateBox($$0, $$4, 7, 3, 1, 7, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/*  978 */           generateBox($$0, $$4, 7, 2, 1, 7, 2, 6, BASE_GRAY, BASE_GRAY, false);
/*  979 */           generateBox($$0, $$4, 7, 1, 1, 7, 1, 6, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } 
/*  981 */       } else if (this.mainDesign == 2) {
/*  982 */         generateBox($$0, $$4, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  983 */         generateBox($$0, $$4, 7, 1, 0, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  984 */         generateBox($$0, $$4, 1, 1, 0, 6, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
/*  985 */         generateBox($$0, $$4, 1, 1, 7, 6, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */         
/*  987 */         generateBox($$0, $$4, 0, 2, 0, 0, 2, 7, BASE_BLACK, BASE_BLACK, false);
/*  988 */         generateBox($$0, $$4, 7, 2, 0, 7, 2, 7, BASE_BLACK, BASE_BLACK, false);
/*  989 */         generateBox($$0, $$4, 1, 2, 0, 6, 2, 0, BASE_BLACK, BASE_BLACK, false);
/*  990 */         generateBox($$0, $$4, 1, 2, 7, 6, 2, 7, BASE_BLACK, BASE_BLACK, false);
/*      */         
/*  992 */         generateBox($$0, $$4, 0, 3, 0, 0, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  993 */         generateBox($$0, $$4, 7, 3, 0, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*  994 */         generateBox($$0, $$4, 1, 3, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/*  995 */         generateBox($$0, $$4, 1, 3, 7, 6, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */         
/*  997 */         generateBox($$0, $$4, 0, 1, 3, 0, 2, 4, BASE_BLACK, BASE_BLACK, false);
/*  998 */         generateBox($$0, $$4, 7, 1, 3, 7, 2, 4, BASE_BLACK, BASE_BLACK, false);
/*  999 */         generateBox($$0, $$4, 3, 1, 0, 4, 2, 0, BASE_BLACK, BASE_BLACK, false);
/* 1000 */         generateBox($$0, $$4, 3, 1, 7, 4, 2, 7, BASE_BLACK, BASE_BLACK, false);
/*      */         
/* 1002 */         if (this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1003 */           generateWaterBox($$0, $$4, 3, 1, 0, 4, 2, 0);
/*      */         }
/* 1005 */         if (this.roomDefinition.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1006 */           generateWaterBox($$0, $$4, 3, 1, 7, 4, 2, 7);
/*      */         }
/* 1008 */         if (this.roomDefinition.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1009 */           generateWaterBox($$0, $$4, 0, 1, 3, 0, 2, 4);
/*      */         }
/* 1011 */         if (this.roomDefinition.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1012 */           generateWaterBox($$0, $$4, 7, 1, 3, 7, 2, 4);
/*      */         }
/*      */       } 
/* 1015 */       if ($$7) {
/* 1016 */         generateBox($$0, $$4, 3, 1, 3, 4, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
/* 1017 */         generateBox($$0, $$4, 3, 2, 3, 4, 2, 4, BASE_GRAY, BASE_GRAY, false);
/* 1018 */         generateBox($$0, $$4, 3, 3, 3, 4, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentSimpleTopRoom extends OceanMonumentPiece {
/*      */     public OceanMonumentSimpleTopRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/* 1025 */       super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, 1, $$0, $$1, 1, 1, 1);
/*      */     }
/*      */     
/*      */     public OceanMonumentSimpleTopRoom(CompoundTag $$0) {
/* 1029 */       super(StructurePieceType.OCEAN_MONUMENT_SIMPLE_TOP_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1034 */       if (this.roomDefinition.index / 25 > 0) {
/* 1035 */         generateDefaultFloor($$0, $$4, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()]);
/*      */       }
/* 1037 */       if (this.roomDefinition.connections[Direction.UP.get3DDataValue()] == null) {
/* 1038 */         generateBoxOnFillOnly($$0, $$4, 1, 4, 1, 6, 4, 6, BASE_GRAY);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1043 */       for (int $$7 = 1; $$7 <= 6; $$7++) {
/* 1044 */         for (int $$8 = 1; $$8 <= 6; $$8++) {
/* 1045 */           if ($$3.nextInt(3) != 0) {
/* 1046 */             int $$9 = 2 + (($$3.nextInt(4) == 0) ? 0 : 1);
/* 1047 */             BlockState $$10 = Blocks.WET_SPONGE.defaultBlockState();
/* 1048 */             generateBox($$0, $$4, $$7, $$9, $$8, $$7, 3, $$8, $$10, $$10, false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1053 */       generateBox($$0, $$4, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1054 */       generateBox($$0, $$4, 7, 1, 0, 7, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1055 */       generateBox($$0, $$4, 1, 1, 0, 6, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1056 */       generateBox($$0, $$4, 1, 1, 7, 6, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1058 */       generateBox($$0, $$4, 0, 2, 0, 0, 2, 7, BASE_BLACK, BASE_BLACK, false);
/* 1059 */       generateBox($$0, $$4, 7, 2, 0, 7, 2, 7, BASE_BLACK, BASE_BLACK, false);
/* 1060 */       generateBox($$0, $$4, 1, 2, 0, 6, 2, 0, BASE_BLACK, BASE_BLACK, false);
/* 1061 */       generateBox($$0, $$4, 1, 2, 7, 6, 2, 7, BASE_BLACK, BASE_BLACK, false);
/*      */       
/* 1063 */       generateBox($$0, $$4, 0, 3, 0, 0, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1064 */       generateBox($$0, $$4, 7, 3, 0, 7, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1065 */       generateBox($$0, $$4, 1, 3, 0, 6, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1066 */       generateBox($$0, $$4, 1, 3, 7, 6, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1068 */       generateBox($$0, $$4, 0, 1, 3, 0, 2, 4, BASE_BLACK, BASE_BLACK, false);
/* 1069 */       generateBox($$0, $$4, 7, 1, 3, 7, 2, 4, BASE_BLACK, BASE_BLACK, false);
/* 1070 */       generateBox($$0, $$4, 3, 1, 0, 4, 2, 0, BASE_BLACK, BASE_BLACK, false);
/* 1071 */       generateBox($$0, $$4, 3, 1, 7, 4, 2, 7, BASE_BLACK, BASE_BLACK, false);
/*      */       
/* 1073 */       if (this.roomDefinition.hasOpening[Direction.SOUTH.get3DDataValue()])
/* 1074 */         generateWaterBox($$0, $$4, 3, 1, 0, 4, 2, 0); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentDoubleYRoom
/*      */     extends OceanMonumentPiece
/*      */   {
/*      */     public OceanMonumentDoubleYRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/* 1082 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, 1, $$0, $$1, 1, 2, 1);
/*      */     }
/*      */     
/*      */     public OceanMonumentDoubleYRoom(CompoundTag $$0) {
/* 1086 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Y_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1091 */       if (this.roomDefinition.index / 25 > 0) {
/* 1092 */         generateDefaultFloor($$0, $$4, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.get3DDataValue()]);
/*      */       }
/* 1094 */       OceanMonumentPieces.RoomDefinition $$7 = this.roomDefinition.connections[Direction.UP.get3DDataValue()];
/* 1095 */       if ($$7.connections[Direction.UP.get3DDataValue()] == null) {
/* 1096 */         generateBoxOnFillOnly($$0, $$4, 1, 8, 1, 6, 8, 6, BASE_GRAY);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1101 */       generateBox($$0, $$4, 0, 4, 0, 0, 4, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1102 */       generateBox($$0, $$4, 7, 4, 0, 7, 4, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1103 */       generateBox($$0, $$4, 1, 4, 0, 6, 4, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1104 */       generateBox($$0, $$4, 1, 4, 7, 6, 4, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1106 */       generateBox($$0, $$4, 2, 4, 1, 2, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1107 */       generateBox($$0, $$4, 1, 4, 2, 1, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1108 */       generateBox($$0, $$4, 5, 4, 1, 5, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1109 */       generateBox($$0, $$4, 6, 4, 2, 6, 4, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1110 */       generateBox($$0, $$4, 2, 4, 5, 2, 4, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1111 */       generateBox($$0, $$4, 1, 4, 5, 1, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1112 */       generateBox($$0, $$4, 5, 4, 5, 5, 4, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1113 */       generateBox($$0, $$4, 6, 4, 5, 6, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1115 */       OceanMonumentPieces.RoomDefinition $$8 = this.roomDefinition;
/* 1116 */       for (int $$9 = 1; $$9 <= 5; $$9 += 4) {
/* 1117 */         int $$10 = 0;
/* 1118 */         if ($$8.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1119 */           generateBox($$0, $$4, 2, $$9, $$10, 2, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/* 1120 */           generateBox($$0, $$4, 5, $$9, $$10, 5, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/* 1121 */           generateBox($$0, $$4, 3, $$9 + 2, $$10, 4, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/* 1123 */           generateBox($$0, $$4, 0, $$9, $$10, 7, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/* 1124 */           generateBox($$0, $$4, 0, $$9 + 1, $$10, 7, $$9 + 1, $$10, BASE_GRAY, BASE_GRAY, false);
/*      */         } 
/* 1126 */         $$10 = 7;
/* 1127 */         if ($$8.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1128 */           generateBox($$0, $$4, 2, $$9, $$10, 2, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/* 1129 */           generateBox($$0, $$4, 5, $$9, $$10, 5, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/* 1130 */           generateBox($$0, $$4, 3, $$9 + 2, $$10, 4, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/* 1132 */           generateBox($$0, $$4, 0, $$9, $$10, 7, $$9 + 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/* 1133 */           generateBox($$0, $$4, 0, $$9 + 1, $$10, 7, $$9 + 1, $$10, BASE_GRAY, BASE_GRAY, false);
/*      */         } 
/* 1135 */         int $$11 = 0;
/* 1136 */         if ($$8.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1137 */           generateBox($$0, $$4, $$11, $$9, 2, $$11, $$9 + 2, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1138 */           generateBox($$0, $$4, $$11, $$9, 5, $$11, $$9 + 2, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1139 */           generateBox($$0, $$4, $$11, $$9 + 2, 3, $$11, $$9 + 2, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/* 1141 */           generateBox($$0, $$4, $$11, $$9, 0, $$11, $$9 + 2, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1142 */           generateBox($$0, $$4, $$11, $$9 + 1, 0, $$11, $$9 + 1, 7, BASE_GRAY, BASE_GRAY, false);
/*      */         } 
/* 1144 */         $$11 = 7;
/* 1145 */         if ($$8.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1146 */           generateBox($$0, $$4, $$11, $$9, 2, $$11, $$9 + 2, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1147 */           generateBox($$0, $$4, $$11, $$9, 5, $$11, $$9 + 2, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1148 */           generateBox($$0, $$4, $$11, $$9 + 2, 3, $$11, $$9 + 2, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */         } else {
/* 1150 */           generateBox($$0, $$4, $$11, $$9, 0, $$11, $$9 + 2, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1151 */           generateBox($$0, $$4, $$11, $$9 + 1, 0, $$11, $$9 + 1, 7, BASE_GRAY, BASE_GRAY, false);
/*      */         } 
/* 1153 */         $$8 = $$7;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentDoubleXRoom
/*      */     extends OceanMonumentPiece {
/*      */     public OceanMonumentDoubleXRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/* 1161 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, 1, $$0, $$1, 2, 1, 1);
/*      */     }
/*      */     
/*      */     public OceanMonumentDoubleXRoom(CompoundTag $$0) {
/* 1165 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_X_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1170 */       OceanMonumentPieces.RoomDefinition $$7 = this.roomDefinition.connections[Direction.EAST.get3DDataValue()];
/* 1171 */       OceanMonumentPieces.RoomDefinition $$8 = this.roomDefinition;
/* 1172 */       if (this.roomDefinition.index / 25 > 0) {
/* 1173 */         generateDefaultFloor($$0, $$4, 8, 0, $$7.hasOpening[Direction.DOWN.get3DDataValue()]);
/* 1174 */         generateDefaultFloor($$0, $$4, 0, 0, $$8.hasOpening[Direction.DOWN.get3DDataValue()]);
/*      */       } 
/* 1176 */       if ($$8.connections[Direction.UP.get3DDataValue()] == null) {
/* 1177 */         generateBoxOnFillOnly($$0, $$4, 1, 4, 1, 7, 4, 6, BASE_GRAY);
/*      */       }
/* 1179 */       if ($$7.connections[Direction.UP.get3DDataValue()] == null) {
/* 1180 */         generateBoxOnFillOnly($$0, $$4, 8, 4, 1, 14, 4, 6, BASE_GRAY);
/*      */       }
/*      */ 
/*      */       
/* 1184 */       generateBox($$0, $$4, 0, 3, 0, 0, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1185 */       generateBox($$0, $$4, 15, 3, 0, 15, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1186 */       generateBox($$0, $$4, 1, 3, 0, 15, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1187 */       generateBox($$0, $$4, 1, 3, 7, 14, 3, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1188 */       generateBox($$0, $$4, 0, 2, 0, 0, 2, 7, BASE_GRAY, BASE_GRAY, false);
/* 1189 */       generateBox($$0, $$4, 15, 2, 0, 15, 2, 7, BASE_GRAY, BASE_GRAY, false);
/* 1190 */       generateBox($$0, $$4, 1, 2, 0, 15, 2, 0, BASE_GRAY, BASE_GRAY, false);
/* 1191 */       generateBox($$0, $$4, 1, 2, 7, 14, 2, 7, BASE_GRAY, BASE_GRAY, false);
/* 1192 */       generateBox($$0, $$4, 0, 1, 0, 0, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1193 */       generateBox($$0, $$4, 15, 1, 0, 15, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1194 */       generateBox($$0, $$4, 1, 1, 0, 15, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1195 */       generateBox($$0, $$4, 1, 1, 7, 14, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/*      */ 
/*      */       
/* 1198 */       generateBox($$0, $$4, 5, 1, 0, 10, 1, 4, BASE_LIGHT, BASE_LIGHT, false);
/* 1199 */       generateBox($$0, $$4, 6, 2, 0, 9, 2, 3, BASE_GRAY, BASE_GRAY, false);
/* 1200 */       generateBox($$0, $$4, 5, 3, 0, 10, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1202 */       placeBlock($$0, LAMP_BLOCK, 6, 2, 3, $$4);
/* 1203 */       placeBlock($$0, LAMP_BLOCK, 9, 2, 3, $$4);
/*      */ 
/*      */       
/* 1206 */       if ($$8.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1207 */         generateWaterBox($$0, $$4, 3, 1, 0, 4, 2, 0);
/*      */       }
/* 1209 */       if ($$8.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1210 */         generateWaterBox($$0, $$4, 3, 1, 7, 4, 2, 7);
/*      */       }
/* 1212 */       if ($$8.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1213 */         generateWaterBox($$0, $$4, 0, 1, 3, 0, 2, 4);
/*      */       }
/* 1215 */       if ($$7.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1216 */         generateWaterBox($$0, $$4, 11, 1, 0, 12, 2, 0);
/*      */       }
/* 1218 */       if ($$7.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1219 */         generateWaterBox($$0, $$4, 11, 1, 7, 12, 2, 7);
/*      */       }
/* 1221 */       if ($$7.hasOpening[Direction.EAST.get3DDataValue()])
/* 1222 */         generateWaterBox($$0, $$4, 15, 1, 3, 15, 2, 4); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentDoubleZRoom
/*      */     extends OceanMonumentPiece {
/*      */     public OceanMonumentDoubleZRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/* 1229 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, 1, $$0, $$1, 1, 1, 2);
/*      */     }
/*      */     
/*      */     public OceanMonumentDoubleZRoom(CompoundTag $$0) {
/* 1233 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_Z_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1238 */       OceanMonumentPieces.RoomDefinition $$7 = this.roomDefinition.connections[Direction.NORTH.get3DDataValue()];
/* 1239 */       OceanMonumentPieces.RoomDefinition $$8 = this.roomDefinition;
/* 1240 */       if (this.roomDefinition.index / 25 > 0) {
/* 1241 */         generateDefaultFloor($$0, $$4, 0, 8, $$7.hasOpening[Direction.DOWN.get3DDataValue()]);
/* 1242 */         generateDefaultFloor($$0, $$4, 0, 0, $$8.hasOpening[Direction.DOWN.get3DDataValue()]);
/*      */       } 
/* 1244 */       if ($$8.connections[Direction.UP.get3DDataValue()] == null) {
/* 1245 */         generateBoxOnFillOnly($$0, $$4, 1, 4, 1, 6, 4, 7, BASE_GRAY);
/*      */       }
/* 1247 */       if ($$7.connections[Direction.UP.get3DDataValue()] == null) {
/* 1248 */         generateBoxOnFillOnly($$0, $$4, 1, 4, 8, 6, 4, 14, BASE_GRAY);
/*      */       }
/*      */ 
/*      */       
/* 1252 */       generateBox($$0, $$4, 0, 3, 0, 0, 3, 15, BASE_LIGHT, BASE_LIGHT, false);
/* 1253 */       generateBox($$0, $$4, 7, 3, 0, 7, 3, 15, BASE_LIGHT, BASE_LIGHT, false);
/* 1254 */       generateBox($$0, $$4, 1, 3, 0, 7, 3, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1255 */       generateBox($$0, $$4, 1, 3, 15, 6, 3, 15, BASE_LIGHT, BASE_LIGHT, false);
/* 1256 */       generateBox($$0, $$4, 0, 2, 0, 0, 2, 15, BASE_GRAY, BASE_GRAY, false);
/* 1257 */       generateBox($$0, $$4, 7, 2, 0, 7, 2, 15, BASE_GRAY, BASE_GRAY, false);
/* 1258 */       generateBox($$0, $$4, 1, 2, 0, 7, 2, 0, BASE_GRAY, BASE_GRAY, false);
/* 1259 */       generateBox($$0, $$4, 1, 2, 15, 6, 2, 15, BASE_GRAY, BASE_GRAY, false);
/* 1260 */       generateBox($$0, $$4, 0, 1, 0, 0, 1, 15, BASE_LIGHT, BASE_LIGHT, false);
/* 1261 */       generateBox($$0, $$4, 7, 1, 0, 7, 1, 15, BASE_LIGHT, BASE_LIGHT, false);
/* 1262 */       generateBox($$0, $$4, 1, 1, 0, 7, 1, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1263 */       generateBox($$0, $$4, 1, 1, 15, 6, 1, 15, BASE_LIGHT, BASE_LIGHT, false);
/*      */ 
/*      */       
/* 1266 */       generateBox($$0, $$4, 1, 1, 1, 1, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1267 */       generateBox($$0, $$4, 6, 1, 1, 6, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1268 */       generateBox($$0, $$4, 1, 3, 1, 1, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1269 */       generateBox($$0, $$4, 6, 3, 1, 6, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1270 */       generateBox($$0, $$4, 1, 1, 13, 1, 1, 14, BASE_LIGHT, BASE_LIGHT, false);
/* 1271 */       generateBox($$0, $$4, 6, 1, 13, 6, 1, 14, BASE_LIGHT, BASE_LIGHT, false);
/* 1272 */       generateBox($$0, $$4, 1, 3, 13, 1, 3, 14, BASE_LIGHT, BASE_LIGHT, false);
/* 1273 */       generateBox($$0, $$4, 6, 3, 13, 6, 3, 14, BASE_LIGHT, BASE_LIGHT, false);
/*      */ 
/*      */       
/* 1276 */       generateBox($$0, $$4, 2, 1, 6, 2, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1277 */       generateBox($$0, $$4, 5, 1, 6, 5, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1278 */       generateBox($$0, $$4, 2, 1, 9, 2, 3, 9, BASE_LIGHT, BASE_LIGHT, false);
/* 1279 */       generateBox($$0, $$4, 5, 1, 9, 5, 3, 9, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1281 */       generateBox($$0, $$4, 3, 2, 6, 4, 2, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1282 */       generateBox($$0, $$4, 3, 2, 9, 4, 2, 9, BASE_LIGHT, BASE_LIGHT, false);
/* 1283 */       generateBox($$0, $$4, 2, 2, 7, 2, 2, 8, BASE_LIGHT, BASE_LIGHT, false);
/* 1284 */       generateBox($$0, $$4, 5, 2, 7, 5, 2, 8, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1286 */       placeBlock($$0, LAMP_BLOCK, 2, 2, 5, $$4);
/* 1287 */       placeBlock($$0, LAMP_BLOCK, 5, 2, 5, $$4);
/* 1288 */       placeBlock($$0, LAMP_BLOCK, 2, 2, 10, $$4);
/* 1289 */       placeBlock($$0, LAMP_BLOCK, 5, 2, 10, $$4);
/* 1290 */       placeBlock($$0, BASE_LIGHT, 2, 3, 5, $$4);
/* 1291 */       placeBlock($$0, BASE_LIGHT, 5, 3, 5, $$4);
/* 1292 */       placeBlock($$0, BASE_LIGHT, 2, 3, 10, $$4);
/* 1293 */       placeBlock($$0, BASE_LIGHT, 5, 3, 10, $$4);
/*      */ 
/*      */       
/* 1296 */       if ($$8.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1297 */         generateWaterBox($$0, $$4, 3, 1, 0, 4, 2, 0);
/*      */       }
/* 1299 */       if ($$8.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1300 */         generateWaterBox($$0, $$4, 7, 1, 3, 7, 2, 4);
/*      */       }
/* 1302 */       if ($$8.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1303 */         generateWaterBox($$0, $$4, 0, 1, 3, 0, 2, 4);
/*      */       }
/* 1305 */       if ($$7.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1306 */         generateWaterBox($$0, $$4, 3, 1, 15, 4, 2, 15);
/*      */       }
/* 1308 */       if ($$7.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1309 */         generateWaterBox($$0, $$4, 0, 1, 11, 0, 2, 12);
/*      */       }
/* 1311 */       if ($$7.hasOpening[Direction.EAST.get3DDataValue()])
/* 1312 */         generateWaterBox($$0, $$4, 7, 1, 11, 7, 2, 12); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentDoubleXYRoom
/*      */     extends OceanMonumentPiece {
/*      */     public OceanMonumentDoubleXYRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/* 1319 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_XY_ROOM, 1, $$0, $$1, 2, 2, 1);
/*      */     }
/*      */     
/*      */     public OceanMonumentDoubleXYRoom(CompoundTag $$0) {
/* 1323 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_XY_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1328 */       OceanMonumentPieces.RoomDefinition $$7 = this.roomDefinition.connections[Direction.EAST.get3DDataValue()];
/* 1329 */       OceanMonumentPieces.RoomDefinition $$8 = this.roomDefinition;
/* 1330 */       OceanMonumentPieces.RoomDefinition $$9 = $$8.connections[Direction.UP.get3DDataValue()];
/* 1331 */       OceanMonumentPieces.RoomDefinition $$10 = $$7.connections[Direction.UP.get3DDataValue()];
/*      */       
/* 1333 */       if (this.roomDefinition.index / 25 > 0) {
/* 1334 */         generateDefaultFloor($$0, $$4, 8, 0, $$7.hasOpening[Direction.DOWN.get3DDataValue()]);
/* 1335 */         generateDefaultFloor($$0, $$4, 0, 0, $$8.hasOpening[Direction.DOWN.get3DDataValue()]);
/*      */       } 
/* 1337 */       if ($$9.connections[Direction.UP.get3DDataValue()] == null) {
/* 1338 */         generateBoxOnFillOnly($$0, $$4, 1, 8, 1, 7, 8, 6, BASE_GRAY);
/*      */       }
/* 1340 */       if ($$10.connections[Direction.UP.get3DDataValue()] == null) {
/* 1341 */         generateBoxOnFillOnly($$0, $$4, 8, 8, 1, 14, 8, 6, BASE_GRAY);
/*      */       }
/*      */ 
/*      */       
/* 1345 */       for (int $$11 = 1; $$11 <= 7; $$11++) {
/* 1346 */         BlockState $$12 = BASE_LIGHT;
/* 1347 */         if ($$11 == 2 || $$11 == 6) {
/* 1348 */           $$12 = BASE_GRAY;
/*      */         }
/* 1350 */         generateBox($$0, $$4, 0, $$11, 0, 0, $$11, 7, $$12, $$12, false);
/* 1351 */         generateBox($$0, $$4, 15, $$11, 0, 15, $$11, 7, $$12, $$12, false);
/* 1352 */         generateBox($$0, $$4, 1, $$11, 0, 15, $$11, 0, $$12, $$12, false);
/* 1353 */         generateBox($$0, $$4, 1, $$11, 7, 14, $$11, 7, $$12, $$12, false);
/*      */       } 
/*      */ 
/*      */       
/* 1357 */       generateBox($$0, $$4, 2, 1, 3, 2, 7, 4, BASE_LIGHT, BASE_LIGHT, false);
/* 1358 */       generateBox($$0, $$4, 3, 1, 2, 4, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1359 */       generateBox($$0, $$4, 3, 1, 5, 4, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1360 */       generateBox($$0, $$4, 13, 1, 3, 13, 7, 4, BASE_LIGHT, BASE_LIGHT, false);
/* 1361 */       generateBox($$0, $$4, 11, 1, 2, 12, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1362 */       generateBox($$0, $$4, 11, 1, 5, 12, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1364 */       generateBox($$0, $$4, 5, 1, 3, 5, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/* 1365 */       generateBox($$0, $$4, 10, 1, 3, 10, 3, 4, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1367 */       generateBox($$0, $$4, 5, 7, 2, 10, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1368 */       generateBox($$0, $$4, 5, 5, 2, 5, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1369 */       generateBox($$0, $$4, 10, 5, 2, 10, 7, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1370 */       generateBox($$0, $$4, 5, 5, 5, 5, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1371 */       generateBox($$0, $$4, 10, 5, 5, 10, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1372 */       placeBlock($$0, BASE_LIGHT, 6, 6, 2, $$4);
/* 1373 */       placeBlock($$0, BASE_LIGHT, 9, 6, 2, $$4);
/* 1374 */       placeBlock($$0, BASE_LIGHT, 6, 6, 5, $$4);
/* 1375 */       placeBlock($$0, BASE_LIGHT, 9, 6, 5, $$4);
/*      */       
/* 1377 */       generateBox($$0, $$4, 5, 4, 3, 6, 4, 4, BASE_LIGHT, BASE_LIGHT, false);
/* 1378 */       generateBox($$0, $$4, 9, 4, 3, 10, 4, 4, BASE_LIGHT, BASE_LIGHT, false);
/* 1379 */       placeBlock($$0, LAMP_BLOCK, 5, 4, 2, $$4);
/* 1380 */       placeBlock($$0, LAMP_BLOCK, 5, 4, 5, $$4);
/* 1381 */       placeBlock($$0, LAMP_BLOCK, 10, 4, 2, $$4);
/* 1382 */       placeBlock($$0, LAMP_BLOCK, 10, 4, 5, $$4);
/*      */ 
/*      */       
/* 1385 */       if ($$8.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1386 */         generateWaterBox($$0, $$4, 3, 1, 0, 4, 2, 0);
/*      */       }
/* 1388 */       if ($$8.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1389 */         generateWaterBox($$0, $$4, 3, 1, 7, 4, 2, 7);
/*      */       }
/* 1391 */       if ($$8.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1392 */         generateWaterBox($$0, $$4, 0, 1, 3, 0, 2, 4);
/*      */       }
/* 1394 */       if ($$7.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1395 */         generateWaterBox($$0, $$4, 11, 1, 0, 12, 2, 0);
/*      */       }
/* 1397 */       if ($$7.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1398 */         generateWaterBox($$0, $$4, 11, 1, 7, 12, 2, 7);
/*      */       }
/* 1400 */       if ($$7.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1401 */         generateWaterBox($$0, $$4, 15, 1, 3, 15, 2, 4);
/*      */       }
/* 1403 */       if ($$9.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1404 */         generateWaterBox($$0, $$4, 3, 5, 0, 4, 6, 0);
/*      */       }
/* 1406 */       if ($$9.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1407 */         generateWaterBox($$0, $$4, 3, 5, 7, 4, 6, 7);
/*      */       }
/* 1409 */       if ($$9.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1410 */         generateWaterBox($$0, $$4, 0, 5, 3, 0, 6, 4);
/*      */       }
/* 1412 */       if ($$10.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1413 */         generateWaterBox($$0, $$4, 11, 5, 0, 12, 6, 0);
/*      */       }
/* 1415 */       if ($$10.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1416 */         generateWaterBox($$0, $$4, 11, 5, 7, 12, 6, 7);
/*      */       }
/* 1418 */       if ($$10.hasOpening[Direction.EAST.get3DDataValue()])
/* 1419 */         generateWaterBox($$0, $$4, 15, 5, 3, 15, 6, 4); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentDoubleYZRoom
/*      */     extends OceanMonumentPiece {
/*      */     public OceanMonumentDoubleYZRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/* 1426 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_YZ_ROOM, 1, $$0, $$1, 1, 2, 2);
/*      */     }
/*      */     
/*      */     public OceanMonumentDoubleYZRoom(CompoundTag $$0) {
/* 1430 */       super(StructurePieceType.OCEAN_MONUMENT_DOUBLE_YZ_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1435 */       OceanMonumentPieces.RoomDefinition $$7 = this.roomDefinition.connections[Direction.NORTH.get3DDataValue()];
/* 1436 */       OceanMonumentPieces.RoomDefinition $$8 = this.roomDefinition;
/* 1437 */       OceanMonumentPieces.RoomDefinition $$9 = $$7.connections[Direction.UP.get3DDataValue()];
/* 1438 */       OceanMonumentPieces.RoomDefinition $$10 = $$8.connections[Direction.UP.get3DDataValue()];
/* 1439 */       if (this.roomDefinition.index / 25 > 0) {
/* 1440 */         generateDefaultFloor($$0, $$4, 0, 8, $$7.hasOpening[Direction.DOWN.get3DDataValue()]);
/* 1441 */         generateDefaultFloor($$0, $$4, 0, 0, $$8.hasOpening[Direction.DOWN.get3DDataValue()]);
/*      */       } 
/* 1443 */       if ($$10.connections[Direction.UP.get3DDataValue()] == null) {
/* 1444 */         generateBoxOnFillOnly($$0, $$4, 1, 8, 1, 6, 8, 7, BASE_GRAY);
/*      */       }
/* 1446 */       if ($$9.connections[Direction.UP.get3DDataValue()] == null) {
/* 1447 */         generateBoxOnFillOnly($$0, $$4, 1, 8, 8, 6, 8, 14, BASE_GRAY);
/*      */       }
/*      */ 
/*      */       
/* 1451 */       for (int $$11 = 1; $$11 <= 7; $$11++) {
/* 1452 */         BlockState $$12 = BASE_LIGHT;
/* 1453 */         if ($$11 == 2 || $$11 == 6) {
/* 1454 */           $$12 = BASE_GRAY;
/*      */         }
/* 1456 */         generateBox($$0, $$4, 0, $$11, 0, 0, $$11, 15, $$12, $$12, false);
/* 1457 */         generateBox($$0, $$4, 7, $$11, 0, 7, $$11, 15, $$12, $$12, false);
/* 1458 */         generateBox($$0, $$4, 1, $$11, 0, 6, $$11, 0, $$12, $$12, false);
/* 1459 */         generateBox($$0, $$4, 1, $$11, 15, 6, $$11, 15, $$12, $$12, false);
/*      */       } 
/*      */ 
/*      */       
/* 1463 */       for (int $$13 = 1; $$13 <= 7; $$13++) {
/* 1464 */         BlockState $$14 = BASE_BLACK;
/* 1465 */         if ($$13 == 2 || $$13 == 6) {
/* 1466 */           $$14 = LAMP_BLOCK;
/*      */         }
/* 1468 */         generateBox($$0, $$4, 3, $$13, 7, 4, $$13, 8, $$14, $$14, false);
/*      */       } 
/*      */ 
/*      */       
/* 1472 */       if ($$8.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1473 */         generateWaterBox($$0, $$4, 3, 1, 0, 4, 2, 0);
/*      */       }
/* 1475 */       if ($$8.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1476 */         generateWaterBox($$0, $$4, 7, 1, 3, 7, 2, 4);
/*      */       }
/* 1478 */       if ($$8.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1479 */         generateWaterBox($$0, $$4, 0, 1, 3, 0, 2, 4);
/*      */       }
/* 1481 */       if ($$7.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1482 */         generateWaterBox($$0, $$4, 3, 1, 15, 4, 2, 15);
/*      */       }
/* 1484 */       if ($$7.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1485 */         generateWaterBox($$0, $$4, 0, 1, 11, 0, 2, 12);
/*      */       }
/* 1487 */       if ($$7.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1488 */         generateWaterBox($$0, $$4, 7, 1, 11, 7, 2, 12);
/*      */       }
/*      */       
/* 1491 */       if ($$10.hasOpening[Direction.SOUTH.get3DDataValue()]) {
/* 1492 */         generateWaterBox($$0, $$4, 3, 5, 0, 4, 6, 0);
/*      */       }
/* 1494 */       if ($$10.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1495 */         generateWaterBox($$0, $$4, 7, 5, 3, 7, 6, 4);
/* 1496 */         generateBox($$0, $$4, 5, 4, 2, 6, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1497 */         generateBox($$0, $$4, 6, 1, 2, 6, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1498 */         generateBox($$0, $$4, 6, 1, 5, 6, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
/*      */       } 
/* 1500 */       if ($$10.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1501 */         generateWaterBox($$0, $$4, 0, 5, 3, 0, 6, 4);
/* 1502 */         generateBox($$0, $$4, 1, 4, 2, 2, 4, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1503 */         generateBox($$0, $$4, 1, 1, 2, 1, 3, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1504 */         generateBox($$0, $$4, 1, 1, 5, 1, 3, 5, BASE_LIGHT, BASE_LIGHT, false);
/*      */       } 
/* 1506 */       if ($$9.hasOpening[Direction.NORTH.get3DDataValue()]) {
/* 1507 */         generateWaterBox($$0, $$4, 3, 5, 15, 4, 6, 15);
/*      */       }
/* 1509 */       if ($$9.hasOpening[Direction.WEST.get3DDataValue()]) {
/* 1510 */         generateWaterBox($$0, $$4, 0, 5, 11, 0, 6, 12);
/* 1511 */         generateBox($$0, $$4, 1, 4, 10, 2, 4, 13, BASE_LIGHT, BASE_LIGHT, false);
/* 1512 */         generateBox($$0, $$4, 1, 1, 10, 1, 3, 10, BASE_LIGHT, BASE_LIGHT, false);
/* 1513 */         generateBox($$0, $$4, 1, 1, 13, 1, 3, 13, BASE_LIGHT, BASE_LIGHT, false);
/*      */       } 
/* 1515 */       if ($$9.hasOpening[Direction.EAST.get3DDataValue()]) {
/* 1516 */         generateWaterBox($$0, $$4, 7, 5, 11, 7, 6, 12);
/* 1517 */         generateBox($$0, $$4, 5, 4, 10, 6, 4, 13, BASE_LIGHT, BASE_LIGHT, false);
/* 1518 */         generateBox($$0, $$4, 6, 1, 10, 6, 3, 10, BASE_LIGHT, BASE_LIGHT, false);
/* 1519 */         generateBox($$0, $$4, 6, 1, 13, 6, 3, 13, BASE_LIGHT, BASE_LIGHT, false);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentCoreRoom extends OceanMonumentPiece {
/*      */     public OceanMonumentCoreRoom(Direction $$0, OceanMonumentPieces.RoomDefinition $$1) {
/* 1526 */       super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, 1, $$0, $$1, 2, 2, 2);
/*      */     }
/*      */     
/*      */     public OceanMonumentCoreRoom(CompoundTag $$0) {
/* 1530 */       super(StructurePieceType.OCEAN_MONUMENT_CORE_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1535 */       generateBoxOnFillOnly($$0, $$4, 1, 8, 0, 14, 8, 14, BASE_GRAY);
/*      */ 
/*      */ 
/*      */       
/* 1539 */       int $$7 = 7;
/* 1540 */       BlockState $$8 = BASE_LIGHT;
/* 1541 */       generateBox($$0, $$4, 0, 7, 0, 0, 7, 15, $$8, $$8, false);
/* 1542 */       generateBox($$0, $$4, 15, 7, 0, 15, 7, 15, $$8, $$8, false);
/* 1543 */       generateBox($$0, $$4, 1, 7, 0, 15, 7, 0, $$8, $$8, false);
/* 1544 */       generateBox($$0, $$4, 1, 7, 15, 14, 7, 15, $$8, $$8, false);
/*      */       
/* 1546 */       for (int $$9 = 1; $$9 <= 6; $$9++) {
/* 1547 */         BlockState $$10 = BASE_LIGHT;
/* 1548 */         if ($$9 == 2 || $$9 == 6) {
/* 1549 */           $$10 = BASE_GRAY;
/*      */         }
/*      */         
/* 1552 */         for (int $$11 = 0; $$11 <= 15; $$11 += 15) {
/* 1553 */           generateBox($$0, $$4, $$11, $$9, 0, $$11, $$9, 1, $$10, $$10, false);
/* 1554 */           generateBox($$0, $$4, $$11, $$9, 6, $$11, $$9, 9, $$10, $$10, false);
/* 1555 */           generateBox($$0, $$4, $$11, $$9, 14, $$11, $$9, 15, $$10, $$10, false);
/*      */         } 
/* 1557 */         generateBox($$0, $$4, 1, $$9, 0, 1, $$9, 0, $$10, $$10, false);
/* 1558 */         generateBox($$0, $$4, 6, $$9, 0, 9, $$9, 0, $$10, $$10, false);
/* 1559 */         generateBox($$0, $$4, 14, $$9, 0, 14, $$9, 0, $$10, $$10, false);
/*      */         
/* 1561 */         generateBox($$0, $$4, 1, $$9, 15, 14, $$9, 15, $$10, $$10, false);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1566 */       generateBox($$0, $$4, 6, 3, 6, 9, 6, 9, BASE_BLACK, BASE_BLACK, false);
/* 1567 */       generateBox($$0, $$4, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.defaultBlockState(), Blocks.GOLD_BLOCK.defaultBlockState(), false);
/* 1568 */       for (int $$12 = 3; $$12 <= 6; $$12 += 3) {
/* 1569 */         for (int $$13 = 6; $$13 <= 9; $$13 += 3) {
/* 1570 */           placeBlock($$0, LAMP_BLOCK, $$13, $$12, 6, $$4);
/* 1571 */           placeBlock($$0, LAMP_BLOCK, $$13, $$12, 9, $$4);
/*      */         } 
/*      */       } 
/*      */       
/* 1575 */       generateBox($$0, $$4, 5, 1, 6, 5, 2, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1576 */       generateBox($$0, $$4, 5, 1, 9, 5, 2, 9, BASE_LIGHT, BASE_LIGHT, false);
/* 1577 */       generateBox($$0, $$4, 10, 1, 6, 10, 2, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1578 */       generateBox($$0, $$4, 10, 1, 9, 10, 2, 9, BASE_LIGHT, BASE_LIGHT, false);
/* 1579 */       generateBox($$0, $$4, 6, 1, 5, 6, 2, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1580 */       generateBox($$0, $$4, 9, 1, 5, 9, 2, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1581 */       generateBox($$0, $$4, 6, 1, 10, 6, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
/* 1582 */       generateBox($$0, $$4, 9, 1, 10, 9, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1584 */       generateBox($$0, $$4, 5, 2, 5, 5, 6, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1585 */       generateBox($$0, $$4, 5, 2, 10, 5, 6, 10, BASE_LIGHT, BASE_LIGHT, false);
/* 1586 */       generateBox($$0, $$4, 10, 2, 5, 10, 6, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1587 */       generateBox($$0, $$4, 10, 2, 10, 10, 6, 10, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1589 */       generateBox($$0, $$4, 5, 7, 1, 5, 7, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1590 */       generateBox($$0, $$4, 10, 7, 1, 10, 7, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1591 */       generateBox($$0, $$4, 5, 7, 9, 5, 7, 14, BASE_LIGHT, BASE_LIGHT, false);
/* 1592 */       generateBox($$0, $$4, 10, 7, 9, 10, 7, 14, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1594 */       generateBox($$0, $$4, 1, 7, 5, 6, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1595 */       generateBox($$0, $$4, 1, 7, 10, 6, 7, 10, BASE_LIGHT, BASE_LIGHT, false);
/* 1596 */       generateBox($$0, $$4, 9, 7, 5, 14, 7, 5, BASE_LIGHT, BASE_LIGHT, false);
/* 1597 */       generateBox($$0, $$4, 9, 7, 10, 14, 7, 10, BASE_LIGHT, BASE_LIGHT, false);
/*      */ 
/*      */       
/* 1600 */       generateBox($$0, $$4, 2, 1, 2, 2, 1, 3, BASE_LIGHT, BASE_LIGHT, false);
/* 1601 */       generateBox($$0, $$4, 3, 1, 2, 3, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1602 */       generateBox($$0, $$4, 13, 1, 2, 13, 1, 3, BASE_LIGHT, BASE_LIGHT, false);
/* 1603 */       generateBox($$0, $$4, 12, 1, 2, 12, 1, 2, BASE_LIGHT, BASE_LIGHT, false);
/* 1604 */       generateBox($$0, $$4, 2, 1, 12, 2, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
/* 1605 */       generateBox($$0, $$4, 3, 1, 13, 3, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
/* 1606 */       generateBox($$0, $$4, 13, 1, 12, 13, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
/* 1607 */       generateBox($$0, $$4, 12, 1, 13, 12, 1, 13, BASE_LIGHT, BASE_LIGHT, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentWingRoom extends OceanMonumentPiece {
/*      */     private int mainDesign;
/*      */     
/*      */     public OceanMonumentWingRoom(Direction $$0, BoundingBox $$1, int $$2) {
/* 1615 */       super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, $$0, 1, $$1);
/* 1616 */       this.mainDesign = $$2 & 0x1;
/*      */     }
/*      */     
/*      */     public OceanMonumentWingRoom(CompoundTag $$0) {
/* 1620 */       super(StructurePieceType.OCEAN_MONUMENT_WING_ROOM, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1625 */       if (this.mainDesign == 0) {
/* 1626 */         for (int $$7 = 0; $$7 < 4; $$7++) {
/* 1627 */           generateBox($$0, $$4, 10 - $$7, 3 - $$7, 20 - $$7, 12 + $$7, 3 - $$7, 20, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/* 1629 */         generateBox($$0, $$4, 7, 0, 6, 15, 0, 16, BASE_LIGHT, BASE_LIGHT, false);
/* 1630 */         generateBox($$0, $$4, 6, 0, 6, 6, 3, 20, BASE_LIGHT, BASE_LIGHT, false);
/* 1631 */         generateBox($$0, $$4, 16, 0, 6, 16, 3, 20, BASE_LIGHT, BASE_LIGHT, false);
/* 1632 */         generateBox($$0, $$4, 7, 1, 7, 7, 1, 20, BASE_LIGHT, BASE_LIGHT, false);
/* 1633 */         generateBox($$0, $$4, 15, 1, 7, 15, 1, 20, BASE_LIGHT, BASE_LIGHT, false);
/*      */         
/* 1635 */         generateBox($$0, $$4, 7, 1, 6, 9, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1636 */         generateBox($$0, $$4, 13, 1, 6, 15, 3, 6, BASE_LIGHT, BASE_LIGHT, false);
/* 1637 */         generateBox($$0, $$4, 8, 1, 7, 9, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1638 */         generateBox($$0, $$4, 13, 1, 7, 14, 1, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1639 */         generateBox($$0, $$4, 9, 0, 5, 13, 0, 5, BASE_LIGHT, BASE_LIGHT, false);
/*      */         
/* 1641 */         generateBox($$0, $$4, 10, 0, 7, 12, 0, 7, BASE_BLACK, BASE_BLACK, false);
/* 1642 */         generateBox($$0, $$4, 8, 0, 10, 8, 0, 12, BASE_BLACK, BASE_BLACK, false);
/* 1643 */         generateBox($$0, $$4, 14, 0, 10, 14, 0, 12, BASE_BLACK, BASE_BLACK, false);
/*      */         
/* 1645 */         for (int $$8 = 18; $$8 >= 7; $$8 -= 3) {
/* 1646 */           placeBlock($$0, LAMP_BLOCK, 6, 3, $$8, $$4);
/* 1647 */           placeBlock($$0, LAMP_BLOCK, 16, 3, $$8, $$4);
/*      */         } 
/* 1649 */         placeBlock($$0, LAMP_BLOCK, 10, 0, 10, $$4);
/* 1650 */         placeBlock($$0, LAMP_BLOCK, 12, 0, 10, $$4);
/* 1651 */         placeBlock($$0, LAMP_BLOCK, 10, 0, 12, $$4);
/* 1652 */         placeBlock($$0, LAMP_BLOCK, 12, 0, 12, $$4);
/*      */         
/* 1654 */         placeBlock($$0, LAMP_BLOCK, 8, 3, 6, $$4);
/* 1655 */         placeBlock($$0, LAMP_BLOCK, 14, 3, 6, $$4);
/*      */ 
/*      */         
/* 1658 */         placeBlock($$0, BASE_LIGHT, 4, 2, 4, $$4);
/* 1659 */         placeBlock($$0, LAMP_BLOCK, 4, 1, 4, $$4);
/* 1660 */         placeBlock($$0, BASE_LIGHT, 4, 0, 4, $$4);
/*      */         
/* 1662 */         placeBlock($$0, BASE_LIGHT, 18, 2, 4, $$4);
/* 1663 */         placeBlock($$0, LAMP_BLOCK, 18, 1, 4, $$4);
/* 1664 */         placeBlock($$0, BASE_LIGHT, 18, 0, 4, $$4);
/*      */         
/* 1666 */         placeBlock($$0, BASE_LIGHT, 4, 2, 18, $$4);
/* 1667 */         placeBlock($$0, LAMP_BLOCK, 4, 1, 18, $$4);
/* 1668 */         placeBlock($$0, BASE_LIGHT, 4, 0, 18, $$4);
/*      */         
/* 1670 */         placeBlock($$0, BASE_LIGHT, 18, 2, 18, $$4);
/* 1671 */         placeBlock($$0, LAMP_BLOCK, 18, 1, 18, $$4);
/* 1672 */         placeBlock($$0, BASE_LIGHT, 18, 0, 18, $$4);
/*      */ 
/*      */         
/* 1675 */         placeBlock($$0, BASE_LIGHT, 9, 7, 20, $$4);
/* 1676 */         placeBlock($$0, BASE_LIGHT, 13, 7, 20, $$4);
/* 1677 */         generateBox($$0, $$4, 6, 0, 21, 7, 4, 21, BASE_LIGHT, BASE_LIGHT, false);
/* 1678 */         generateBox($$0, $$4, 15, 0, 21, 16, 4, 21, BASE_LIGHT, BASE_LIGHT, false);
/*      */         
/* 1680 */         spawnElder($$0, $$4, 11, 2, 16);
/* 1681 */       } else if (this.mainDesign == 1) {
/* 1682 */         generateBox($$0, $$4, 9, 3, 18, 13, 3, 20, BASE_LIGHT, BASE_LIGHT, false);
/* 1683 */         generateBox($$0, $$4, 9, 0, 18, 9, 2, 18, BASE_LIGHT, BASE_LIGHT, false);
/* 1684 */         generateBox($$0, $$4, 13, 0, 18, 13, 2, 18, BASE_LIGHT, BASE_LIGHT, false);
/* 1685 */         int $$9 = 9;
/* 1686 */         int $$10 = 20;
/* 1687 */         int $$11 = 5;
/* 1688 */         for (int $$12 = 0; $$12 < 2; $$12++) {
/* 1689 */           placeBlock($$0, BASE_LIGHT, $$9, 6, 20, $$4);
/* 1690 */           placeBlock($$0, LAMP_BLOCK, $$9, 5, 20, $$4);
/* 1691 */           placeBlock($$0, BASE_LIGHT, $$9, 4, 20, $$4);
/* 1692 */           $$9 = 13;
/*      */         } 
/*      */         
/* 1695 */         generateBox($$0, $$4, 7, 3, 7, 15, 3, 14, BASE_LIGHT, BASE_LIGHT, false);
/* 1696 */         $$9 = 10;
/* 1697 */         for (int $$13 = 0; $$13 < 2; $$13++) {
/* 1698 */           generateBox($$0, $$4, $$9, 0, 10, $$9, 6, 10, BASE_LIGHT, BASE_LIGHT, false);
/* 1699 */           generateBox($$0, $$4, $$9, 0, 12, $$9, 6, 12, BASE_LIGHT, BASE_LIGHT, false);
/* 1700 */           placeBlock($$0, LAMP_BLOCK, $$9, 0, 10, $$4);
/* 1701 */           placeBlock($$0, LAMP_BLOCK, $$9, 0, 12, $$4);
/* 1702 */           placeBlock($$0, LAMP_BLOCK, $$9, 4, 10, $$4);
/* 1703 */           placeBlock($$0, LAMP_BLOCK, $$9, 4, 12, $$4);
/* 1704 */           $$9 = 12;
/*      */         } 
/* 1706 */         $$9 = 8;
/* 1707 */         for (int $$14 = 0; $$14 < 2; $$14++) {
/* 1708 */           generateBox($$0, $$4, $$9, 0, 7, $$9, 2, 7, BASE_LIGHT, BASE_LIGHT, false);
/* 1709 */           generateBox($$0, $$4, $$9, 0, 14, $$9, 2, 14, BASE_LIGHT, BASE_LIGHT, false);
/* 1710 */           $$9 = 14;
/*      */         } 
/* 1712 */         generateBox($$0, $$4, 8, 3, 8, 8, 3, 13, BASE_BLACK, BASE_BLACK, false);
/* 1713 */         generateBox($$0, $$4, 14, 3, 8, 14, 3, 13, BASE_BLACK, BASE_BLACK, false);
/*      */         
/* 1715 */         spawnElder($$0, $$4, 11, 5, 13);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class OceanMonumentPenthouse extends OceanMonumentPiece {
/*      */     public OceanMonumentPenthouse(Direction $$0, BoundingBox $$1) {
/* 1722 */       super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, $$0, 1, $$1);
/*      */     }
/*      */     
/*      */     public OceanMonumentPenthouse(CompoundTag $$0) {
/* 1726 */       super(StructurePieceType.OCEAN_MONUMENT_PENTHOUSE, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 1731 */       generateBox($$0, $$4, 2, -1, 2, 11, -1, 11, BASE_LIGHT, BASE_LIGHT, false);
/* 1732 */       generateBox($$0, $$4, 0, -1, 0, 1, -1, 11, BASE_GRAY, BASE_GRAY, false);
/* 1733 */       generateBox($$0, $$4, 12, -1, 0, 13, -1, 11, BASE_GRAY, BASE_GRAY, false);
/* 1734 */       generateBox($$0, $$4, 2, -1, 0, 11, -1, 1, BASE_GRAY, BASE_GRAY, false);
/* 1735 */       generateBox($$0, $$4, 2, -1, 12, 11, -1, 13, BASE_GRAY, BASE_GRAY, false);
/*      */       
/* 1737 */       generateBox($$0, $$4, 0, 0, 0, 0, 0, 13, BASE_LIGHT, BASE_LIGHT, false);
/* 1738 */       generateBox($$0, $$4, 13, 0, 0, 13, 0, 13, BASE_LIGHT, BASE_LIGHT, false);
/* 1739 */       generateBox($$0, $$4, 1, 0, 0, 12, 0, 0, BASE_LIGHT, BASE_LIGHT, false);
/* 1740 */       generateBox($$0, $$4, 1, 0, 13, 12, 0, 13, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1742 */       for (int $$7 = 2; $$7 <= 11; $$7 += 3) {
/* 1743 */         placeBlock($$0, LAMP_BLOCK, 0, 0, $$7, $$4);
/* 1744 */         placeBlock($$0, LAMP_BLOCK, 13, 0, $$7, $$4);
/* 1745 */         placeBlock($$0, LAMP_BLOCK, $$7, 0, 0, $$4);
/*      */       } 
/*      */       
/* 1748 */       generateBox($$0, $$4, 2, 0, 3, 4, 0, 9, BASE_LIGHT, BASE_LIGHT, false);
/* 1749 */       generateBox($$0, $$4, 9, 0, 3, 11, 0, 9, BASE_LIGHT, BASE_LIGHT, false);
/* 1750 */       generateBox($$0, $$4, 4, 0, 9, 9, 0, 11, BASE_LIGHT, BASE_LIGHT, false);
/* 1751 */       placeBlock($$0, BASE_LIGHT, 5, 0, 8, $$4);
/* 1752 */       placeBlock($$0, BASE_LIGHT, 8, 0, 8, $$4);
/* 1753 */       placeBlock($$0, BASE_LIGHT, 10, 0, 10, $$4);
/* 1754 */       placeBlock($$0, BASE_LIGHT, 3, 0, 10, $$4);
/* 1755 */       generateBox($$0, $$4, 3, 0, 3, 3, 0, 7, BASE_BLACK, BASE_BLACK, false);
/* 1756 */       generateBox($$0, $$4, 10, 0, 3, 10, 0, 7, BASE_BLACK, BASE_BLACK, false);
/* 1757 */       generateBox($$0, $$4, 6, 0, 10, 7, 0, 10, BASE_BLACK, BASE_BLACK, false);
/*      */       
/* 1759 */       int $$8 = 3;
/* 1760 */       for (int $$9 = 0; $$9 < 2; $$9++) {
/* 1761 */         for (int $$10 = 2; $$10 <= 8; $$10 += 3) {
/* 1762 */           generateBox($$0, $$4, $$8, 0, $$10, $$8, 2, $$10, BASE_LIGHT, BASE_LIGHT, false);
/*      */         }
/* 1764 */         $$8 = 10;
/*      */       } 
/* 1766 */       generateBox($$0, $$4, 5, 0, 10, 5, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
/* 1767 */       generateBox($$0, $$4, 8, 0, 10, 8, 2, 10, BASE_LIGHT, BASE_LIGHT, false);
/*      */       
/* 1769 */       generateBox($$0, $$4, 6, -1, 7, 7, -1, 8, BASE_BLACK, BASE_BLACK, false);
/*      */ 
/*      */       
/* 1772 */       generateWaterBox($$0, $$4, 6, -1, 3, 7, -1, 4);
/*      */       
/* 1774 */       spawnElder($$0, $$4, 6, 1, 6);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class RoomDefinition {
/*      */     final int index;
/* 1780 */     final RoomDefinition[] connections = new RoomDefinition[6];
/* 1781 */     final boolean[] hasOpening = new boolean[6];
/*      */     boolean claimed;
/*      */     boolean isSource;
/*      */     private int scanIndex;
/*      */     
/*      */     public RoomDefinition(int $$0) {
/* 1787 */       this.index = $$0;
/*      */     }
/*      */     
/*      */     public void setConnection(Direction $$0, RoomDefinition $$1) {
/* 1791 */       this.connections[$$0.get3DDataValue()] = $$1;
/* 1792 */       $$1.connections[$$0.getOpposite().get3DDataValue()] = this;
/*      */     }
/*      */     
/*      */     public void updateOpenings() {
/* 1796 */       for (int $$0 = 0; $$0 < 6; $$0++) {
/* 1797 */         this.hasOpening[$$0] = (this.connections[$$0] != null);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean findSource(int $$0) {
/* 1802 */       if (this.isSource) {
/* 1803 */         return true;
/*      */       }
/* 1805 */       this.scanIndex = $$0;
/* 1806 */       for (int $$1 = 0; $$1 < 6; $$1++) {
/* 1807 */         if (this.connections[$$1] != null && this.hasOpening[$$1] && 
/* 1808 */           (this.connections[$$1]).scanIndex != $$0 && this.connections[$$1].findSource($$0)) {
/* 1809 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 1813 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isSpecial() {
/* 1817 */       return (this.index >= 75);
/*      */     }
/*      */     
/*      */     public int countOpenings() {
/* 1821 */       int $$0 = 0;
/* 1822 */       for (int $$1 = 0; $$1 < 6; $$1++) {
/* 1823 */         if (this.hasOpening[$$1]) {
/* 1824 */           $$0++;
/*      */         }
/*      */       } 
/* 1827 */       return $$0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class FitSimpleRoom
/*      */     implements MonumentRoomFitter
/*      */   {
/*      */     public boolean fits(OceanMonumentPieces.RoomDefinition $$0) {
/* 1840 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public OceanMonumentPieces.OceanMonumentPiece create(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/* 1845 */       $$1.claimed = true;
/* 1846 */       return new OceanMonumentPieces.OceanMonumentSimpleRoom($$0, $$1, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FitSimpleTopRoom
/*      */     implements MonumentRoomFitter {
/*      */     public boolean fits(OceanMonumentPieces.RoomDefinition $$0) {
/* 1853 */       return (!$$0.hasOpening[Direction.WEST.get3DDataValue()] && !$$0.hasOpening[Direction.EAST.get3DDataValue()] && !$$0.hasOpening[Direction.NORTH.get3DDataValue()] && !$$0.hasOpening[Direction.SOUTH.get3DDataValue()] && !$$0.hasOpening[Direction.UP.get3DDataValue()]);
/*      */     }
/*      */ 
/*      */     
/*      */     public OceanMonumentPieces.OceanMonumentPiece create(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/* 1858 */       $$1.claimed = true;
/* 1859 */       return new OceanMonumentPieces.OceanMonumentSimpleTopRoom($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FitDoubleYRoom
/*      */     implements MonumentRoomFitter {
/*      */     public boolean fits(OceanMonumentPieces.RoomDefinition $$0) {
/* 1866 */       return ($$0.hasOpening[Direction.UP.get3DDataValue()] && !($$0.connections[Direction.UP.get3DDataValue()]).claimed);
/*      */     }
/*      */ 
/*      */     
/*      */     public OceanMonumentPieces.OceanMonumentPiece create(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/* 1871 */       $$1.claimed = true;
/* 1872 */       ($$1.connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 1873 */       return new OceanMonumentPieces.OceanMonumentDoubleYRoom($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FitDoubleXRoom
/*      */     implements MonumentRoomFitter {
/*      */     public boolean fits(OceanMonumentPieces.RoomDefinition $$0) {
/* 1880 */       return ($$0.hasOpening[Direction.EAST.get3DDataValue()] && !($$0.connections[Direction.EAST.get3DDataValue()]).claimed);
/*      */     }
/*      */ 
/*      */     
/*      */     public OceanMonumentPieces.OceanMonumentPiece create(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/* 1885 */       $$1.claimed = true;
/* 1886 */       ($$1.connections[Direction.EAST.get3DDataValue()]).claimed = true;
/* 1887 */       return new OceanMonumentPieces.OceanMonumentDoubleXRoom($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FitDoubleZRoom
/*      */     implements MonumentRoomFitter {
/*      */     public boolean fits(OceanMonumentPieces.RoomDefinition $$0) {
/* 1894 */       return ($$0.hasOpening[Direction.NORTH.get3DDataValue()] && !($$0.connections[Direction.NORTH.get3DDataValue()]).claimed);
/*      */     }
/*      */ 
/*      */     
/*      */     public OceanMonumentPieces.OceanMonumentPiece create(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/* 1899 */       OceanMonumentPieces.RoomDefinition $$3 = $$1;
/* 1900 */       if (!$$1.hasOpening[Direction.NORTH.get3DDataValue()] || ($$1.connections[Direction.NORTH.get3DDataValue()]).claimed) {
/* 1901 */         $$3 = $$1.connections[Direction.SOUTH.get3DDataValue()];
/*      */       }
/* 1903 */       $$3.claimed = true;
/* 1904 */       ($$3.connections[Direction.NORTH.get3DDataValue()]).claimed = true;
/* 1905 */       return new OceanMonumentPieces.OceanMonumentDoubleZRoom($$0, $$3);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FitDoubleXYRoom
/*      */     implements MonumentRoomFitter {
/*      */     public boolean fits(OceanMonumentPieces.RoomDefinition $$0) {
/* 1912 */       if ($$0.hasOpening[Direction.EAST.get3DDataValue()] && !($$0.connections[Direction.EAST.get3DDataValue()]).claimed && 
/* 1913 */         $$0.hasOpening[Direction.UP.get3DDataValue()] && !($$0.connections[Direction.UP.get3DDataValue()]).claimed) {
/* 1914 */         OceanMonumentPieces.RoomDefinition $$1 = $$0.connections[Direction.EAST.get3DDataValue()];
/*      */         
/* 1916 */         return ($$1.hasOpening[Direction.UP.get3DDataValue()] && !($$1.connections[Direction.UP.get3DDataValue()]).claimed);
/*      */       } 
/*      */       
/* 1919 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public OceanMonumentPieces.OceanMonumentPiece create(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/* 1924 */       $$1.claimed = true;
/* 1925 */       ($$1.connections[Direction.EAST.get3DDataValue()]).claimed = true;
/* 1926 */       ($$1.connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 1927 */       (($$1.connections[Direction.EAST.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 1928 */       return new OceanMonumentPieces.OceanMonumentDoubleXYRoom($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FitDoubleYZRoom
/*      */     implements MonumentRoomFitter {
/*      */     public boolean fits(OceanMonumentPieces.RoomDefinition $$0) {
/* 1935 */       if ($$0.hasOpening[Direction.NORTH.get3DDataValue()] && !($$0.connections[Direction.NORTH.get3DDataValue()]).claimed && 
/* 1936 */         $$0.hasOpening[Direction.UP.get3DDataValue()] && !($$0.connections[Direction.UP.get3DDataValue()]).claimed) {
/* 1937 */         OceanMonumentPieces.RoomDefinition $$1 = $$0.connections[Direction.NORTH.get3DDataValue()];
/*      */         
/* 1939 */         return ($$1.hasOpening[Direction.UP.get3DDataValue()] && !($$1.connections[Direction.UP.get3DDataValue()]).claimed);
/*      */       } 
/*      */       
/* 1942 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public OceanMonumentPieces.OceanMonumentPiece create(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, RandomSource $$2) {
/* 1947 */       $$1.claimed = true;
/* 1948 */       ($$1.connections[Direction.NORTH.get3DDataValue()]).claimed = true;
/* 1949 */       ($$1.connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 1950 */       (($$1.connections[Direction.NORTH.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 1951 */       return new OceanMonumentPieces.OceanMonumentDoubleYZRoom($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private static interface MonumentRoomFitter {
/*      */     boolean fits(OceanMonumentPieces.RoomDefinition param1RoomDefinition);
/*      */     
/*      */     OceanMonumentPieces.OceanMonumentPiece create(Direction param1Direction, OceanMonumentPieces.RoomDefinition param1RoomDefinition, RandomSource param1RandomSource);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanMonumentPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */