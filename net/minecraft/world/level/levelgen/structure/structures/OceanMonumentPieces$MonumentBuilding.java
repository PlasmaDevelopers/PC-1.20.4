/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MonumentBuilding
/*     */   extends OceanMonumentPieces.OceanMonumentPiece
/*     */ {
/*     */   private static final int WIDTH = 58;
/*     */   private static final int HEIGHT = 22;
/*     */   private static final int DEPTH = 58;
/*     */   public static final int BIOME_RANGE_CHECK = 29;
/*     */   private static final int TOP_POSITION = 61;
/*     */   private OceanMonumentPieces.RoomDefinition sourceRoom;
/*     */   private OceanMonumentPieces.RoomDefinition coreRoom;
/* 209 */   private final List<OceanMonumentPieces.OceanMonumentPiece> childPieces = Lists.newArrayList();
/*     */   
/*     */   public MonumentBuilding(RandomSource $$0, int $$1, int $$2, Direction $$3) {
/* 212 */     super(StructurePieceType.OCEAN_MONUMENT_BUILDING, $$3, 0, makeBoundingBox($$1, 39, $$2, $$3, 58, 23, 58));
/*     */     
/* 214 */     setOrientation($$3);
/*     */     
/* 216 */     List<OceanMonumentPieces.RoomDefinition> $$4 = generateRoomGraph($$0);
/*     */     
/* 218 */     this.sourceRoom.claimed = true;
/* 219 */     this.childPieces.add(new OceanMonumentPieces.OceanMonumentEntryRoom($$3, this.sourceRoom));
/* 220 */     this.childPieces.add(new OceanMonumentPieces.OceanMonumentCoreRoom($$3, this.coreRoom));
/*     */     
/* 222 */     List<OceanMonumentPieces.MonumentRoomFitter> $$5 = Lists.newArrayList();
/* 223 */     $$5.add(new OceanMonumentPieces.FitDoubleXYRoom());
/* 224 */     $$5.add(new OceanMonumentPieces.FitDoubleYZRoom());
/* 225 */     $$5.add(new OceanMonumentPieces.FitDoubleZRoom());
/* 226 */     $$5.add(new OceanMonumentPieces.FitDoubleXRoom());
/* 227 */     $$5.add(new OceanMonumentPieces.FitDoubleYRoom());
/* 228 */     $$5.add(new OceanMonumentPieces.FitSimpleTopRoom());
/* 229 */     $$5.add(new OceanMonumentPieces.FitSimpleRoom());
/*     */     
/* 231 */     for (OceanMonumentPieces.RoomDefinition $$6 : $$4) {
/* 232 */       if (!$$6.claimed && !$$6.isSpecial())
/*     */       {
/* 234 */         for (OceanMonumentPieces.MonumentRoomFitter $$7 : $$5) {
/* 235 */           if ($$7.fits($$6)) {
/* 236 */             this.childPieces.add($$7.create($$3, $$6, $$0));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 244 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(9, 0, 22);
/* 245 */     for (OceanMonumentPieces.OceanMonumentPiece $$9 : this.childPieces) {
/* 246 */       $$9.getBoundingBox().move((Vec3i)mutableBlockPos);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 251 */     BoundingBox $$10 = BoundingBox.fromCorners((Vec3i)getWorldPos(1, 1, 1), (Vec3i)getWorldPos(23, 8, 21));
/* 252 */     BoundingBox $$11 = BoundingBox.fromCorners((Vec3i)getWorldPos(34, 1, 1), (Vec3i)getWorldPos(56, 8, 21));
/* 253 */     BoundingBox $$12 = BoundingBox.fromCorners((Vec3i)getWorldPos(22, 13, 22), (Vec3i)getWorldPos(35, 17, 35));
/*     */ 
/*     */     
/* 256 */     int $$13 = $$0.nextInt();
/* 257 */     this.childPieces.add(new OceanMonumentPieces.OceanMonumentWingRoom($$3, $$10, $$13++));
/* 258 */     this.childPieces.add(new OceanMonumentPieces.OceanMonumentWingRoom($$3, $$11, $$13++));
/*     */     
/* 260 */     this.childPieces.add(new OceanMonumentPieces.OceanMonumentPenthouse($$3, $$12));
/*     */   }
/*     */ 
/*     */   
/*     */   public MonumentBuilding(CompoundTag $$0) {
/* 265 */     super(StructurePieceType.OCEAN_MONUMENT_BUILDING, $$0);
/*     */   }
/*     */   
/*     */   private List<OceanMonumentPieces.RoomDefinition> generateRoomGraph(RandomSource $$0) {
/* 269 */     OceanMonumentPieces.RoomDefinition[] $$1 = new OceanMonumentPieces.RoomDefinition[75];
/*     */     
/* 271 */     for (int $$2 = 0; $$2 < 5; $$2++) {
/* 272 */       for (int $$3 = 0; $$3 < 4; $$3++) {
/* 273 */         int $$4 = 0;
/* 274 */         int $$5 = getRoomIndex($$2, 0, $$3);
/* 275 */         $$1[$$5] = new OceanMonumentPieces.RoomDefinition($$5);
/*     */       } 
/*     */     } 
/* 278 */     for (int $$6 = 0; $$6 < 5; $$6++) {
/* 279 */       for (int $$7 = 0; $$7 < 4; $$7++) {
/* 280 */         int $$8 = 1;
/* 281 */         int $$9 = getRoomIndex($$6, 1, $$7);
/* 282 */         $$1[$$9] = new OceanMonumentPieces.RoomDefinition($$9);
/*     */       } 
/*     */     } 
/* 285 */     for (int $$10 = 1; $$10 < 4; $$10++) {
/* 286 */       for (int $$11 = 0; $$11 < 2; $$11++) {
/* 287 */         int $$12 = 2;
/* 288 */         int $$13 = getRoomIndex($$10, 2, $$11);
/* 289 */         $$1[$$13] = new OceanMonumentPieces.RoomDefinition($$13);
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     this.sourceRoom = $$1[GRIDROOM_SOURCE_INDEX];
/*     */     
/* 295 */     for (int $$14 = 0; $$14 < 5; $$14++) {
/* 296 */       for (int $$15 = 0; $$15 < 5; $$15++) {
/* 297 */         for (int $$16 = 0; $$16 < 3; $$16++) {
/* 298 */           int $$17 = getRoomIndex($$14, $$16, $$15);
/* 299 */           if ($$1[$$17] != null)
/*     */           {
/*     */             
/* 302 */             for (Direction $$18 : Direction.values()) {
/* 303 */               int $$19 = $$14 + $$18.getStepX();
/* 304 */               int $$20 = $$16 + $$18.getStepY();
/* 305 */               int $$21 = $$15 + $$18.getStepZ();
/* 306 */               if ($$19 >= 0 && $$19 < 5 && $$21 >= 0 && $$21 < 5 && $$20 >= 0 && $$20 < 3) {
/* 307 */                 int $$22 = getRoomIndex($$19, $$20, $$21);
/* 308 */                 if ($$1[$$22] != null)
/*     */                 {
/*     */                   
/* 311 */                   if ($$21 == $$15) {
/* 312 */                     $$1[$$17].setConnection($$18, $$1[$$22]);
/*     */                   } else {
/* 314 */                     $$1[$$17].setConnection($$18.getOpposite(), $$1[$$22]);
/*     */                   }  } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 322 */     OceanMonumentPieces.RoomDefinition $$23 = new OceanMonumentPieces.RoomDefinition(1003);
/* 323 */     OceanMonumentPieces.RoomDefinition $$24 = new OceanMonumentPieces.RoomDefinition(1001);
/* 324 */     OceanMonumentPieces.RoomDefinition $$25 = new OceanMonumentPieces.RoomDefinition(1002);
/* 325 */     $$1[GRIDROOM_TOP_CONNECT_INDEX].setConnection(Direction.UP, $$23);
/* 326 */     $$1[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, $$24);
/* 327 */     $$1[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, $$25);
/* 328 */     $$23.claimed = true;
/* 329 */     $$24.claimed = true;
/* 330 */     $$25.claimed = true;
/* 331 */     this.sourceRoom.isSource = true;
/*     */ 
/*     */     
/* 334 */     this.coreRoom = $$1[getRoomIndex($$0.nextInt(4), 0, 2)];
/* 335 */     this.coreRoom.claimed = true;
/* 336 */     (this.coreRoom.connections[Direction.EAST.get3DDataValue()]).claimed = true;
/* 337 */     (this.coreRoom.connections[Direction.NORTH.get3DDataValue()]).claimed = true;
/* 338 */     ((this.coreRoom.connections[Direction.EAST.get3DDataValue()]).connections[Direction.NORTH.get3DDataValue()]).claimed = true;
/* 339 */     (this.coreRoom.connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 340 */     ((this.coreRoom.connections[Direction.EAST.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 341 */     ((this.coreRoom.connections[Direction.NORTH.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/* 342 */     (((this.coreRoom.connections[Direction.EAST.get3DDataValue()]).connections[Direction.NORTH.get3DDataValue()]).connections[Direction.UP.get3DDataValue()]).claimed = true;
/*     */     
/* 344 */     ObjectArrayList<OceanMonumentPieces.RoomDefinition> $$26 = new ObjectArrayList();
/* 345 */     for (OceanMonumentPieces.RoomDefinition $$27 : $$1) {
/* 346 */       if ($$27 != null) {
/* 347 */         $$27.updateOpenings();
/* 348 */         $$26.add($$27);
/*     */       } 
/*     */     } 
/* 351 */     $$23.updateOpenings();
/*     */     
/* 353 */     Util.shuffle((List)$$26, $$0);
/* 354 */     int $$28 = 1;
/* 355 */     for (ObjectListIterator<OceanMonumentPieces.RoomDefinition> objectListIterator = $$26.iterator(); objectListIterator.hasNext(); ) { OceanMonumentPieces.RoomDefinition $$29 = objectListIterator.next();
/*     */       
/* 357 */       int $$30 = 0;
/* 358 */       int $$31 = 0;
/* 359 */       while ($$30 < 2 && $$31 < 5) {
/* 360 */         $$31++;
/*     */         
/* 362 */         int $$32 = $$0.nextInt(6);
/* 363 */         if ($$29.hasOpening[$$32]) {
/* 364 */           int $$33 = Direction.from3DDataValue($$32).getOpposite().get3DDataValue();
/*     */ 
/*     */           
/* 367 */           $$29.hasOpening[$$32] = false;
/* 368 */           ($$29.connections[$$32]).hasOpening[$$33] = false;
/*     */           
/* 370 */           if ($$29.findSource($$28++) && $$29.connections[$$32].findSource($$28++)) {
/* 371 */             $$30++;
/*     */             continue;
/*     */           } 
/* 374 */           $$29.hasOpening[$$32] = true;
/* 375 */           ($$29.connections[$$32]).hasOpening[$$33] = true;
/*     */         } 
/*     */       }  }
/*     */ 
/*     */     
/* 380 */     $$26.add($$23);
/* 381 */     $$26.add($$24);
/* 382 */     $$26.add($$25);
/*     */     
/* 384 */     return (List<OceanMonumentPieces.RoomDefinition>)$$26;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 389 */     int $$7 = Math.max($$0.getSeaLevel(), 64) - this.boundingBox.minY();
/*     */     
/* 391 */     generateWaterBox($$0, $$4, 0, 0, 0, 58, $$7, 58);
/*     */ 
/*     */     
/* 394 */     generateWing(false, 0, $$0, $$3, $$4);
/*     */ 
/*     */     
/* 397 */     generateWing(true, 33, $$0, $$3, $$4);
/*     */ 
/*     */     
/* 400 */     generateEntranceArchs($$0, $$3, $$4);
/*     */     
/* 402 */     generateEntranceWall($$0, $$3, $$4);
/* 403 */     generateRoofPiece($$0, $$3, $$4);
/*     */     
/* 405 */     generateLowerWall($$0, $$3, $$4);
/* 406 */     generateMiddleWall($$0, $$3, $$4);
/* 407 */     generateUpperWall($$0, $$3, $$4);
/*     */ 
/*     */     
/* 410 */     for (int $$8 = 0; $$8 < 7; $$8++) {
/* 411 */       for (int $$9 = 0; $$9 < 7; ) {
/* 412 */         if ($$9 == 0 && $$8 == 3)
/*     */         {
/* 414 */           $$9 = 6;
/*     */         }
/*     */         
/* 417 */         int $$10 = $$8 * 9;
/* 418 */         int $$11 = $$9 * 9;
/* 419 */         for (int $$12 = 0; $$12 < 4; $$12++) {
/* 420 */           for (int $$13 = 0; $$13 < 4; $$13++) {
/* 421 */             placeBlock($$0, BASE_LIGHT, $$10 + $$12, 0, $$11 + $$13, $$4);
/* 422 */             fillColumnDown($$0, BASE_LIGHT, $$10 + $$12, -1, $$11 + $$13, $$4);
/*     */           } 
/*     */         } 
/*     */         
/* 426 */         if ($$8 == 0 || $$8 == 6) {
/* 427 */           $$9++; continue;
/*     */         } 
/* 429 */         $$9 += 6;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 435 */     for (int $$14 = 0; $$14 < 5; $$14++) {
/* 436 */       generateWaterBox($$0, $$4, -1 - $$14, 0 + $$14 * 2, -1 - $$14, -1 - $$14, 23, 58 + $$14);
/* 437 */       generateWaterBox($$0, $$4, 58 + $$14, 0 + $$14 * 2, -1 - $$14, 58 + $$14, 23, 58 + $$14);
/* 438 */       generateWaterBox($$0, $$4, 0 - $$14, 0 + $$14 * 2, -1 - $$14, 57 + $$14, 23, -1 - $$14);
/* 439 */       generateWaterBox($$0, $$4, 0 - $$14, 0 + $$14 * 2, 58 + $$14, 57 + $$14, 23, 58 + $$14);
/*     */     } 
/*     */     
/* 442 */     for (OceanMonumentPieces.OceanMonumentPiece $$15 : this.childPieces) {
/* 443 */       if ($$15.getBoundingBox().intersects($$4)) {
/* 444 */         $$15.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateWing(boolean $$0, int $$1, WorldGenLevel $$2, RandomSource $$3, BoundingBox $$4) {
/* 451 */     int $$5 = 24;
/* 452 */     if (chunkIntersects($$4, $$1, 0, $$1 + 23, 20)) {
/* 453 */       generateBox($$2, $$4, $$1 + 0, 0, 0, $$1 + 24, 0, 20, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 455 */       generateWaterBox($$2, $$4, $$1 + 0, 1, 0, $$1 + 24, 10, 20);
/*     */       
/* 457 */       for (int $$6 = 0; $$6 < 4; $$6++) {
/* 458 */         generateBox($$2, $$4, $$1 + $$6, $$6 + 1, $$6, $$1 + $$6, $$6 + 1, 20, BASE_LIGHT, BASE_LIGHT, false);
/* 459 */         generateBox($$2, $$4, $$1 + $$6 + 7, $$6 + 5, $$6 + 7, $$1 + $$6 + 7, $$6 + 5, 20, BASE_LIGHT, BASE_LIGHT, false);
/* 460 */         generateBox($$2, $$4, $$1 + 17 - $$6, $$6 + 5, $$6 + 7, $$1 + 17 - $$6, $$6 + 5, 20, BASE_LIGHT, BASE_LIGHT, false);
/* 461 */         generateBox($$2, $$4, $$1 + 24 - $$6, $$6 + 1, $$6, $$1 + 24 - $$6, $$6 + 1, 20, BASE_LIGHT, BASE_LIGHT, false);
/*     */         
/* 463 */         generateBox($$2, $$4, $$1 + $$6 + 1, $$6 + 1, $$6, $$1 + 23 - $$6, $$6 + 1, $$6, BASE_LIGHT, BASE_LIGHT, false);
/* 464 */         generateBox($$2, $$4, $$1 + $$6 + 8, $$6 + 5, $$6 + 7, $$1 + 16 - $$6, $$6 + 5, $$6 + 7, BASE_LIGHT, BASE_LIGHT, false);
/*     */       } 
/* 466 */       generateBox($$2, $$4, $$1 + 4, 4, 4, $$1 + 6, 4, 20, BASE_GRAY, BASE_GRAY, false);
/* 467 */       generateBox($$2, $$4, $$1 + 7, 4, 4, $$1 + 17, 4, 6, BASE_GRAY, BASE_GRAY, false);
/* 468 */       generateBox($$2, $$4, $$1 + 18, 4, 4, $$1 + 20, 4, 20, BASE_GRAY, BASE_GRAY, false);
/* 469 */       generateBox($$2, $$4, $$1 + 11, 8, 11, $$1 + 13, 8, 20, BASE_GRAY, BASE_GRAY, false);
/* 470 */       placeBlock($$2, DOT_DECO_DATA, $$1 + 12, 9, 12, $$4);
/* 471 */       placeBlock($$2, DOT_DECO_DATA, $$1 + 12, 9, 15, $$4);
/* 472 */       placeBlock($$2, DOT_DECO_DATA, $$1 + 12, 9, 18, $$4);
/*     */       
/* 474 */       int $$7 = $$1 + ($$0 ? 19 : 5);
/* 475 */       int $$8 = $$1 + ($$0 ? 5 : 19);
/* 476 */       for (int $$9 = 20; $$9 >= 5; $$9 -= 3) {
/* 477 */         placeBlock($$2, DOT_DECO_DATA, $$7, 5, $$9, $$4);
/*     */       }
/* 479 */       for (int $$10 = 19; $$10 >= 7; $$10 -= 3) {
/* 480 */         placeBlock($$2, DOT_DECO_DATA, $$8, 5, $$10, $$4);
/*     */       }
/* 482 */       for (int $$11 = 0; $$11 < 4; $$11++) {
/* 483 */         int $$12 = $$0 ? ($$1 + 24 - 17 - $$11 * 3) : ($$1 + 17 - $$11 * 3);
/* 484 */         placeBlock($$2, DOT_DECO_DATA, $$12, 5, 5, $$4);
/*     */       } 
/* 486 */       placeBlock($$2, DOT_DECO_DATA, $$8, 5, 5, $$4);
/*     */ 
/*     */       
/* 489 */       generateBox($$2, $$4, $$1 + 11, 1, 12, $$1 + 13, 7, 12, BASE_GRAY, BASE_GRAY, false);
/* 490 */       generateBox($$2, $$4, $$1 + 12, 1, 11, $$1 + 12, 7, 13, BASE_GRAY, BASE_GRAY, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateEntranceArchs(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/* 496 */     if (chunkIntersects($$2, 22, 5, 35, 17)) {
/*     */       
/* 498 */       generateWaterBox($$0, $$2, 25, 0, 0, 32, 8, 20);
/*     */ 
/*     */       
/* 501 */       for (int $$3 = 0; $$3 < 4; $$3++) {
/* 502 */         generateBox($$0, $$2, 24, 2, 5 + $$3 * 4, 24, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/* 503 */         generateBox($$0, $$2, 22, 4, 5 + $$3 * 4, 23, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/* 504 */         placeBlock($$0, BASE_LIGHT, 25, 5, 5 + $$3 * 4, $$2);
/* 505 */         placeBlock($$0, BASE_LIGHT, 26, 6, 5 + $$3 * 4, $$2);
/* 506 */         placeBlock($$0, LAMP_BLOCK, 26, 5, 5 + $$3 * 4, $$2);
/*     */         
/* 508 */         generateBox($$0, $$2, 33, 2, 5 + $$3 * 4, 33, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/* 509 */         generateBox($$0, $$2, 34, 4, 5 + $$3 * 4, 35, 4, 5 + $$3 * 4, BASE_LIGHT, BASE_LIGHT, false);
/* 510 */         placeBlock($$0, BASE_LIGHT, 32, 5, 5 + $$3 * 4, $$2);
/* 511 */         placeBlock($$0, BASE_LIGHT, 31, 6, 5 + $$3 * 4, $$2);
/* 512 */         placeBlock($$0, LAMP_BLOCK, 31, 5, 5 + $$3 * 4, $$2);
/*     */         
/* 514 */         generateBox($$0, $$2, 27, 6, 5 + $$3 * 4, 30, 6, 5 + $$3 * 4, BASE_GRAY, BASE_GRAY, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateEntranceWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/* 522 */     if (chunkIntersects($$2, 15, 20, 42, 21)) {
/* 523 */       generateBox($$0, $$2, 15, 0, 21, 42, 0, 21, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 525 */       generateWaterBox($$0, $$2, 26, 1, 21, 31, 3, 21);
/*     */ 
/*     */ 
/*     */       
/* 529 */       generateBox($$0, $$2, 21, 12, 21, 36, 12, 21, BASE_GRAY, BASE_GRAY, false);
/* 530 */       generateBox($$0, $$2, 17, 11, 21, 40, 11, 21, BASE_GRAY, BASE_GRAY, false);
/* 531 */       generateBox($$0, $$2, 16, 10, 21, 41, 10, 21, BASE_GRAY, BASE_GRAY, false);
/* 532 */       generateBox($$0, $$2, 15, 7, 21, 42, 9, 21, BASE_GRAY, BASE_GRAY, false);
/* 533 */       generateBox($$0, $$2, 16, 6, 21, 41, 6, 21, BASE_GRAY, BASE_GRAY, false);
/* 534 */       generateBox($$0, $$2, 17, 5, 21, 40, 5, 21, BASE_GRAY, BASE_GRAY, false);
/* 535 */       generateBox($$0, $$2, 21, 4, 21, 36, 4, 21, BASE_GRAY, BASE_GRAY, false);
/* 536 */       generateBox($$0, $$2, 22, 3, 21, 26, 3, 21, BASE_GRAY, BASE_GRAY, false);
/* 537 */       generateBox($$0, $$2, 31, 3, 21, 35, 3, 21, BASE_GRAY, BASE_GRAY, false);
/* 538 */       generateBox($$0, $$2, 23, 2, 21, 25, 2, 21, BASE_GRAY, BASE_GRAY, false);
/* 539 */       generateBox($$0, $$2, 32, 2, 21, 34, 2, 21, BASE_GRAY, BASE_GRAY, false);
/*     */ 
/*     */       
/* 542 */       generateBox($$0, $$2, 28, 4, 20, 29, 4, 21, BASE_LIGHT, BASE_LIGHT, false);
/* 543 */       placeBlock($$0, BASE_LIGHT, 27, 3, 21, $$2);
/* 544 */       placeBlock($$0, BASE_LIGHT, 30, 3, 21, $$2);
/* 545 */       placeBlock($$0, BASE_LIGHT, 26, 2, 21, $$2);
/* 546 */       placeBlock($$0, BASE_LIGHT, 31, 2, 21, $$2);
/* 547 */       placeBlock($$0, BASE_LIGHT, 25, 1, 21, $$2);
/* 548 */       placeBlock($$0, BASE_LIGHT, 32, 1, 21, $$2);
/* 549 */       for (int $$3 = 0; $$3 < 7; $$3++) {
/* 550 */         placeBlock($$0, BASE_BLACK, 28 - $$3, 6 + $$3, 21, $$2);
/* 551 */         placeBlock($$0, BASE_BLACK, 29 + $$3, 6 + $$3, 21, $$2);
/*     */       } 
/* 553 */       for (int $$4 = 0; $$4 < 4; $$4++) {
/* 554 */         placeBlock($$0, BASE_BLACK, 28 - $$4, 9 + $$4, 21, $$2);
/* 555 */         placeBlock($$0, BASE_BLACK, 29 + $$4, 9 + $$4, 21, $$2);
/*     */       } 
/* 557 */       placeBlock($$0, BASE_BLACK, 28, 12, 21, $$2);
/* 558 */       placeBlock($$0, BASE_BLACK, 29, 12, 21, $$2);
/* 559 */       for (int $$5 = 0; $$5 < 3; $$5++) {
/* 560 */         placeBlock($$0, BASE_BLACK, 22 - $$5 * 2, 8, 21, $$2);
/* 561 */         placeBlock($$0, BASE_BLACK, 22 - $$5 * 2, 9, 21, $$2);
/*     */         
/* 563 */         placeBlock($$0, BASE_BLACK, 35 + $$5 * 2, 8, 21, $$2);
/* 564 */         placeBlock($$0, BASE_BLACK, 35 + $$5 * 2, 9, 21, $$2);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 569 */       generateWaterBox($$0, $$2, 15, 13, 21, 42, 15, 21);
/* 570 */       generateWaterBox($$0, $$2, 15, 1, 21, 15, 6, 21);
/* 571 */       generateWaterBox($$0, $$2, 16, 1, 21, 16, 5, 21);
/* 572 */       generateWaterBox($$0, $$2, 17, 1, 21, 20, 4, 21);
/* 573 */       generateWaterBox($$0, $$2, 21, 1, 21, 21, 3, 21);
/* 574 */       generateWaterBox($$0, $$2, 22, 1, 21, 22, 2, 21);
/* 575 */       generateWaterBox($$0, $$2, 23, 1, 21, 24, 1, 21);
/* 576 */       generateWaterBox($$0, $$2, 42, 1, 21, 42, 6, 21);
/* 577 */       generateWaterBox($$0, $$2, 41, 1, 21, 41, 5, 21);
/* 578 */       generateWaterBox($$0, $$2, 37, 1, 21, 40, 4, 21);
/* 579 */       generateWaterBox($$0, $$2, 36, 1, 21, 36, 3, 21);
/* 580 */       generateWaterBox($$0, $$2, 33, 1, 21, 34, 1, 21);
/* 581 */       generateWaterBox($$0, $$2, 35, 1, 21, 35, 2, 21);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateRoofPiece(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/* 589 */     if (chunkIntersects($$2, 21, 21, 36, 36)) {
/* 590 */       generateBox($$0, $$2, 21, 0, 22, 36, 0, 36, BASE_GRAY, BASE_GRAY, false);
/*     */ 
/*     */ 
/*     */       
/* 594 */       generateWaterBox($$0, $$2, 21, 1, 22, 36, 23, 36);
/*     */ 
/*     */       
/* 597 */       for (int $$3 = 0; $$3 < 4; $$3++) {
/* 598 */         generateBox($$0, $$2, 21 + $$3, 13 + $$3, 21 + $$3, 36 - $$3, 13 + $$3, 21 + $$3, BASE_LIGHT, BASE_LIGHT, false);
/* 599 */         generateBox($$0, $$2, 21 + $$3, 13 + $$3, 36 - $$3, 36 - $$3, 13 + $$3, 36 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/* 600 */         generateBox($$0, $$2, 21 + $$3, 13 + $$3, 22 + $$3, 21 + $$3, 13 + $$3, 35 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/* 601 */         generateBox($$0, $$2, 36 - $$3, 13 + $$3, 22 + $$3, 36 - $$3, 13 + $$3, 35 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*     */       } 
/* 603 */       generateBox($$0, $$2, 25, 16, 25, 32, 16, 32, BASE_GRAY, BASE_GRAY, false);
/* 604 */       generateBox($$0, $$2, 25, 17, 25, 25, 19, 25, BASE_LIGHT, BASE_LIGHT, false);
/* 605 */       generateBox($$0, $$2, 32, 17, 25, 32, 19, 25, BASE_LIGHT, BASE_LIGHT, false);
/* 606 */       generateBox($$0, $$2, 25, 17, 32, 25, 19, 32, BASE_LIGHT, BASE_LIGHT, false);
/* 607 */       generateBox($$0, $$2, 32, 17, 32, 32, 19, 32, BASE_LIGHT, BASE_LIGHT, false);
/*     */       
/* 609 */       placeBlock($$0, BASE_LIGHT, 26, 20, 26, $$2);
/* 610 */       placeBlock($$0, BASE_LIGHT, 27, 21, 27, $$2);
/* 611 */       placeBlock($$0, LAMP_BLOCK, 27, 20, 27, $$2);
/* 612 */       placeBlock($$0, BASE_LIGHT, 26, 20, 31, $$2);
/* 613 */       placeBlock($$0, BASE_LIGHT, 27, 21, 30, $$2);
/* 614 */       placeBlock($$0, LAMP_BLOCK, 27, 20, 30, $$2);
/* 615 */       placeBlock($$0, BASE_LIGHT, 31, 20, 31, $$2);
/* 616 */       placeBlock($$0, BASE_LIGHT, 30, 21, 30, $$2);
/* 617 */       placeBlock($$0, LAMP_BLOCK, 30, 20, 30, $$2);
/* 618 */       placeBlock($$0, BASE_LIGHT, 31, 20, 26, $$2);
/* 619 */       placeBlock($$0, BASE_LIGHT, 30, 21, 27, $$2);
/* 620 */       placeBlock($$0, LAMP_BLOCK, 30, 20, 27, $$2);
/*     */       
/* 622 */       generateBox($$0, $$2, 28, 21, 27, 29, 21, 27, BASE_GRAY, BASE_GRAY, false);
/* 623 */       generateBox($$0, $$2, 27, 21, 28, 27, 21, 29, BASE_GRAY, BASE_GRAY, false);
/* 624 */       generateBox($$0, $$2, 28, 21, 30, 29, 21, 30, BASE_GRAY, BASE_GRAY, false);
/* 625 */       generateBox($$0, $$2, 30, 21, 28, 30, 21, 29, BASE_GRAY, BASE_GRAY, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateLowerWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/* 632 */     if (chunkIntersects($$2, 0, 21, 6, 58)) {
/* 633 */       generateBox($$0, $$2, 0, 0, 21, 6, 0, 57, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 635 */       generateWaterBox($$0, $$2, 0, 1, 21, 6, 7, 57);
/*     */ 
/*     */       
/* 638 */       generateBox($$0, $$2, 4, 4, 21, 6, 4, 53, BASE_GRAY, BASE_GRAY, false);
/* 639 */       for (int $$3 = 0; $$3 < 4; $$3++) {
/* 640 */         generateBox($$0, $$2, $$3, $$3 + 1, 21, $$3, $$3 + 1, 57 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/* 642 */       for (int $$4 = 23; $$4 < 53; $$4 += 3) {
/* 643 */         placeBlock($$0, DOT_DECO_DATA, 5, 5, $$4, $$2);
/*     */       }
/* 645 */       placeBlock($$0, DOT_DECO_DATA, 5, 5, 52, $$2);
/*     */       
/* 647 */       for (int $$5 = 0; $$5 < 4; $$5++) {
/* 648 */         generateBox($$0, $$2, $$5, $$5 + 1, 21, $$5, $$5 + 1, 57 - $$5, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/*     */       
/* 651 */       generateBox($$0, $$2, 4, 1, 52, 6, 3, 52, BASE_GRAY, BASE_GRAY, false);
/* 652 */       generateBox($$0, $$2, 5, 1, 51, 5, 3, 53, BASE_GRAY, BASE_GRAY, false);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 657 */     if (chunkIntersects($$2, 51, 21, 58, 58)) {
/* 658 */       generateBox($$0, $$2, 51, 0, 21, 57, 0, 57, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 660 */       generateWaterBox($$0, $$2, 51, 1, 21, 57, 7, 57);
/*     */ 
/*     */       
/* 663 */       generateBox($$0, $$2, 51, 4, 21, 53, 4, 53, BASE_GRAY, BASE_GRAY, false);
/* 664 */       for (int $$6 = 0; $$6 < 4; $$6++) {
/* 665 */         generateBox($$0, $$2, 57 - $$6, $$6 + 1, 21, 57 - $$6, $$6 + 1, 57 - $$6, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/* 667 */       for (int $$7 = 23; $$7 < 53; $$7 += 3) {
/* 668 */         placeBlock($$0, DOT_DECO_DATA, 52, 5, $$7, $$2);
/*     */       }
/* 670 */       placeBlock($$0, DOT_DECO_DATA, 52, 5, 52, $$2);
/*     */ 
/*     */       
/* 673 */       generateBox($$0, $$2, 51, 1, 52, 53, 3, 52, BASE_GRAY, BASE_GRAY, false);
/* 674 */       generateBox($$0, $$2, 52, 1, 51, 52, 3, 53, BASE_GRAY, BASE_GRAY, false);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 679 */     if (chunkIntersects($$2, 0, 51, 57, 57)) {
/* 680 */       generateBox($$0, $$2, 7, 0, 51, 50, 0, 57, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 682 */       generateWaterBox($$0, $$2, 7, 1, 51, 50, 10, 57);
/*     */ 
/*     */       
/* 685 */       for (int $$8 = 0; $$8 < 4; $$8++) {
/* 686 */         generateBox($$0, $$2, $$8 + 1, $$8 + 1, 57 - $$8, 56 - $$8, $$8 + 1, 57 - $$8, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateMiddleWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/* 694 */     if (chunkIntersects($$2, 7, 21, 13, 50)) {
/* 695 */       generateBox($$0, $$2, 7, 0, 21, 13, 0, 50, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 697 */       generateWaterBox($$0, $$2, 7, 1, 21, 13, 10, 50);
/*     */ 
/*     */       
/* 700 */       generateBox($$0, $$2, 11, 8, 21, 13, 8, 53, BASE_GRAY, BASE_GRAY, false);
/* 701 */       for (int $$3 = 0; $$3 < 4; $$3++) {
/* 702 */         generateBox($$0, $$2, $$3 + 7, $$3 + 5, 21, $$3 + 7, $$3 + 5, 54, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/* 704 */       for (int $$4 = 21; $$4 <= 45; $$4 += 3) {
/* 705 */         placeBlock($$0, DOT_DECO_DATA, 12, 9, $$4, $$2);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 711 */     if (chunkIntersects($$2, 44, 21, 50, 54)) {
/* 712 */       generateBox($$0, $$2, 44, 0, 21, 50, 0, 50, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 714 */       generateWaterBox($$0, $$2, 44, 1, 21, 50, 10, 50);
/*     */ 
/*     */       
/* 717 */       generateBox($$0, $$2, 44, 8, 21, 46, 8, 53, BASE_GRAY, BASE_GRAY, false);
/* 718 */       for (int $$5 = 0; $$5 < 4; $$5++) {
/* 719 */         generateBox($$0, $$2, 50 - $$5, $$5 + 5, 21, 50 - $$5, $$5 + 5, 54, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/* 721 */       for (int $$6 = 21; $$6 <= 45; $$6 += 3) {
/* 722 */         placeBlock($$0, DOT_DECO_DATA, 45, 9, $$6, $$2);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 728 */     if (chunkIntersects($$2, 8, 44, 49, 54)) {
/* 729 */       generateBox($$0, $$2, 14, 0, 44, 43, 0, 50, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 731 */       generateWaterBox($$0, $$2, 14, 1, 44, 43, 10, 50);
/*     */ 
/*     */       
/* 734 */       for (int $$7 = 12; $$7 <= 45; $$7 += 3) {
/* 735 */         placeBlock($$0, DOT_DECO_DATA, $$7, 9, 45, $$2);
/* 736 */         placeBlock($$0, DOT_DECO_DATA, $$7, 9, 52, $$2);
/* 737 */         if ($$7 == 12 || $$7 == 18 || $$7 == 24 || $$7 == 33 || $$7 == 39 || $$7 == 45) {
/* 738 */           placeBlock($$0, DOT_DECO_DATA, $$7, 9, 47, $$2);
/* 739 */           placeBlock($$0, DOT_DECO_DATA, $$7, 9, 50, $$2);
/* 740 */           placeBlock($$0, DOT_DECO_DATA, $$7, 10, 45, $$2);
/* 741 */           placeBlock($$0, DOT_DECO_DATA, $$7, 10, 46, $$2);
/* 742 */           placeBlock($$0, DOT_DECO_DATA, $$7, 10, 51, $$2);
/* 743 */           placeBlock($$0, DOT_DECO_DATA, $$7, 10, 52, $$2);
/* 744 */           placeBlock($$0, DOT_DECO_DATA, $$7, 11, 47, $$2);
/* 745 */           placeBlock($$0, DOT_DECO_DATA, $$7, 11, 50, $$2);
/* 746 */           placeBlock($$0, DOT_DECO_DATA, $$7, 12, 48, $$2);
/* 747 */           placeBlock($$0, DOT_DECO_DATA, $$7, 12, 49, $$2);
/*     */         } 
/*     */       } 
/*     */       
/* 751 */       for (int $$8 = 0; $$8 < 3; $$8++) {
/* 752 */         generateBox($$0, $$2, 8 + $$8, 5 + $$8, 54, 49 - $$8, 5 + $$8, 54, BASE_GRAY, BASE_GRAY, false);
/*     */       }
/* 754 */       generateBox($$0, $$2, 11, 8, 54, 46, 8, 54, BASE_LIGHT, BASE_LIGHT, false);
/* 755 */       generateBox($$0, $$2, 14, 8, 44, 43, 8, 53, BASE_GRAY, BASE_GRAY, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateUpperWall(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2) {
/* 762 */     if (chunkIntersects($$2, 14, 21, 20, 43)) {
/* 763 */       generateBox($$0, $$2, 14, 0, 21, 20, 0, 43, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 765 */       generateWaterBox($$0, $$2, 14, 1, 22, 20, 14, 43);
/*     */ 
/*     */       
/* 768 */       generateBox($$0, $$2, 18, 12, 22, 20, 12, 39, BASE_GRAY, BASE_GRAY, false);
/* 769 */       generateBox($$0, $$2, 18, 12, 21, 20, 12, 21, BASE_LIGHT, BASE_LIGHT, false);
/* 770 */       for (int $$3 = 0; $$3 < 4; $$3++) {
/* 771 */         generateBox($$0, $$2, $$3 + 14, $$3 + 9, 21, $$3 + 14, $$3 + 9, 43 - $$3, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/* 773 */       for (int $$4 = 23; $$4 <= 39; $$4 += 3) {
/* 774 */         placeBlock($$0, DOT_DECO_DATA, 19, 13, $$4, $$2);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 780 */     if (chunkIntersects($$2, 37, 21, 43, 43)) {
/* 781 */       generateBox($$0, $$2, 37, 0, 21, 43, 0, 43, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 783 */       generateWaterBox($$0, $$2, 37, 1, 22, 43, 14, 43);
/*     */ 
/*     */       
/* 786 */       generateBox($$0, $$2, 37, 12, 22, 39, 12, 39, BASE_GRAY, BASE_GRAY, false);
/* 787 */       generateBox($$0, $$2, 37, 12, 21, 39, 12, 21, BASE_LIGHT, BASE_LIGHT, false);
/* 788 */       for (int $$5 = 0; $$5 < 4; $$5++) {
/* 789 */         generateBox($$0, $$2, 43 - $$5, $$5 + 9, 21, 43 - $$5, $$5 + 9, 43 - $$5, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/* 791 */       for (int $$6 = 23; $$6 <= 39; $$6 += 3) {
/* 792 */         placeBlock($$0, DOT_DECO_DATA, 38, 13, $$6, $$2);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 798 */     if (chunkIntersects($$2, 15, 37, 42, 43)) {
/* 799 */       generateBox($$0, $$2, 21, 0, 37, 36, 0, 43, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 801 */       generateWaterBox($$0, $$2, 21, 1, 37, 36, 14, 43);
/*     */ 
/*     */       
/* 804 */       generateBox($$0, $$2, 21, 12, 37, 36, 12, 39, BASE_GRAY, BASE_GRAY, false);
/* 805 */       for (int $$7 = 0; $$7 < 4; $$7++) {
/* 806 */         generateBox($$0, $$2, 15 + $$7, $$7 + 9, 43 - $$7, 42 - $$7, $$7 + 9, 43 - $$7, BASE_LIGHT, BASE_LIGHT, false);
/*     */       }
/* 808 */       for (int $$8 = 21; $$8 <= 36; $$8 += 3)
/* 809 */         placeBlock($$0, DOT_DECO_DATA, $$8, 13, 38, $$2); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanMonumentPieces$MonumentBuilding.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */