/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MansionPiecePlacer
/*     */ {
/*     */   private final StructureTemplateManager structureTemplateManager;
/*     */   private final RandomSource random;
/*     */   private int startX;
/*     */   private int startY;
/*     */   
/*     */   public MansionPiecePlacer(StructureTemplateManager $$0, RandomSource $$1) {
/* 140 */     this.structureTemplateManager = $$0;
/* 141 */     this.random = $$1;
/*     */   }
/*     */   
/*     */   public void createMansion(BlockPos $$0, Rotation $$1, List<WoodlandMansionPieces.WoodlandMansionPiece> $$2, WoodlandMansionPieces.MansionGrid $$3) {
/* 145 */     WoodlandMansionPieces.PlacementData $$4 = new WoodlandMansionPieces.PlacementData();
/* 146 */     $$4.position = $$0;
/* 147 */     $$4.rotation = $$1;
/* 148 */     $$4.wallType = "wall_flat";
/*     */     
/* 150 */     WoodlandMansionPieces.PlacementData $$5 = new WoodlandMansionPieces.PlacementData();
/*     */ 
/*     */     
/* 153 */     entrance($$2, $$4);
/* 154 */     $$5.position = $$4.position.above(8);
/* 155 */     $$5.rotation = $$4.rotation;
/* 156 */     $$5.wallType = "wall_window";
/*     */     
/* 158 */     if (!$$2.isEmpty());
/*     */ 
/*     */ 
/*     */     
/* 162 */     WoodlandMansionPieces.SimpleGrid $$6 = $$3.baseGrid;
/* 163 */     WoodlandMansionPieces.SimpleGrid $$7 = $$3.thirdFloorGrid;
/*     */     
/* 165 */     this.startX = $$3.entranceX + 1;
/* 166 */     this.startY = $$3.entranceY + 1;
/* 167 */     int $$8 = $$3.entranceX + 1;
/* 168 */     int $$9 = $$3.entranceY;
/*     */     
/* 170 */     traverseOuterWalls($$2, $$4, $$6, Direction.SOUTH, this.startX, this.startY, $$8, $$9);
/* 171 */     traverseOuterWalls($$2, $$5, $$6, Direction.SOUTH, this.startX, this.startY, $$8, $$9);
/*     */ 
/*     */     
/* 174 */     WoodlandMansionPieces.PlacementData $$10 = new WoodlandMansionPieces.PlacementData();
/* 175 */     $$10.position = $$4.position.above(19);
/* 176 */     $$10.rotation = $$4.rotation;
/* 177 */     $$10.wallType = "wall_window";
/*     */     
/* 179 */     boolean $$11 = false;
/* 180 */     for (int $$12 = 0; $$12 < $$7.height && !$$11; $$12++) {
/* 181 */       for (int $$13 = $$7.width - 1; $$13 >= 0 && !$$11; $$13--) {
/* 182 */         if (WoodlandMansionPieces.MansionGrid.isHouse($$7, $$13, $$12)) {
/* 183 */           $$10.position = $$10.position.relative($$1.rotate(Direction.SOUTH), 8 + ($$12 - this.startY) * 8);
/* 184 */           $$10.position = $$10.position.relative($$1.rotate(Direction.EAST), ($$13 - this.startX) * 8);
/* 185 */           traverseWallPiece($$2, $$10);
/* 186 */           traverseOuterWalls($$2, $$10, $$7, Direction.SOUTH, $$13, $$12, $$13, $$12);
/* 187 */           $$11 = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 193 */     createRoof($$2, $$0.above(16), $$1, $$6, $$7);
/* 194 */     createRoof($$2, $$0.above(27), $$1, $$7, null);
/*     */     
/* 196 */     if (!$$2.isEmpty());
/*     */ 
/*     */ 
/*     */     
/* 200 */     WoodlandMansionPieces.FloorRoomCollection[] $$14 = new WoodlandMansionPieces.FloorRoomCollection[3];
/* 201 */     $$14[0] = new WoodlandMansionPieces.FirstFloorRoomCollection();
/* 202 */     $$14[1] = new WoodlandMansionPieces.SecondFloorRoomCollection();
/* 203 */     $$14[2] = new WoodlandMansionPieces.ThirdFloorRoomCollection();
/*     */     
/* 205 */     for (int $$15 = 0; $$15 < 3; $$15++) {
/* 206 */       BlockPos $$16 = $$0.above(8 * $$15 + (($$15 == 2) ? 3 : 0));
/* 207 */       WoodlandMansionPieces.SimpleGrid $$17 = $$3.floorRooms[$$15];
/* 208 */       WoodlandMansionPieces.SimpleGrid $$18 = ($$15 == 2) ? $$7 : $$6;
/*     */ 
/*     */       
/* 211 */       String $$19 = ($$15 == 0) ? "carpet_south_1" : "carpet_south_2";
/* 212 */       String $$20 = ($$15 == 0) ? "carpet_west_1" : "carpet_west_2";
/* 213 */       for (int $$21 = 0; $$21 < $$18.height; $$21++) {
/* 214 */         for (int $$22 = 0; $$22 < $$18.width; $$22++) {
/* 215 */           if ($$18.get($$22, $$21) == 1) {
/* 216 */             BlockPos $$23 = $$16.relative($$1.rotate(Direction.SOUTH), 8 + ($$21 - this.startY) * 8);
/* 217 */             $$23 = $$23.relative($$1.rotate(Direction.EAST), ($$22 - this.startX) * 8);
/* 218 */             $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "corridor_floor", $$23, $$1));
/*     */             
/* 220 */             if ($$18.get($$22, $$21 - 1) == 1 || ($$17.get($$22, $$21 - 1) & 0x800000) == 8388608) {
/* 221 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "carpet_north", $$23.relative($$1.rotate(Direction.EAST), 1).above(), $$1));
/*     */             }
/* 223 */             if ($$18.get($$22 + 1, $$21) == 1 || ($$17.get($$22 + 1, $$21) & 0x800000) == 8388608) {
/* 224 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "carpet_east", $$23.relative($$1.rotate(Direction.SOUTH), 1).relative($$1.rotate(Direction.EAST), 5).above(), $$1));
/*     */             }
/* 226 */             if ($$18.get($$22, $$21 + 1) == 1 || ($$17.get($$22, $$21 + 1) & 0x800000) == 8388608) {
/* 227 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$19, $$23.relative($$1.rotate(Direction.SOUTH), 5).relative($$1.rotate(Direction.WEST), 1), $$1));
/*     */             }
/* 229 */             if ($$18.get($$22 - 1, $$21) == 1 || ($$17.get($$22 - 1, $$21) & 0x800000) == 8388608) {
/* 230 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$20, $$23.relative($$1.rotate(Direction.WEST), 1).relative($$1.rotate(Direction.NORTH), 1), $$1));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 236 */       String $$24 = ($$15 == 0) ? "indoors_wall_1" : "indoors_wall_2";
/* 237 */       String $$25 = ($$15 == 0) ? "indoors_door_1" : "indoors_door_2";
/* 238 */       List<Direction> $$26 = Lists.newArrayList();
/* 239 */       for (int $$27 = 0; $$27 < $$18.height; $$27++) {
/* 240 */         for (int $$28 = 0; $$28 < $$18.width; $$28++) {
/* 241 */           boolean $$29 = ($$15 == 2 && $$18.get($$28, $$27) == 3);
/* 242 */           if ($$18.get($$28, $$27) == 2 || $$29) {
/* 243 */             int $$30 = $$17.get($$28, $$27);
/* 244 */             int $$31 = $$30 & 0xF0000;
/* 245 */             int $$32 = $$30 & 0xFFFF;
/*     */ 
/*     */             
/* 248 */             $$29 = ($$29 && ($$30 & 0x800000) == 8388608);
/*     */             
/* 250 */             $$26.clear();
/* 251 */             if (($$30 & 0x200000) == 2097152) {
/* 252 */               for (Direction $$33 : Direction.Plane.HORIZONTAL) {
/* 253 */                 if ($$18.get($$28 + $$33.getStepX(), $$27 + $$33.getStepZ()) == 1) {
/* 254 */                   $$26.add($$33);
/*     */                 }
/*     */               } 
/*     */             }
/* 258 */             Direction $$34 = null;
/* 259 */             if (!$$26.isEmpty()) {
/* 260 */               $$34 = $$26.get(this.random.nextInt($$26.size()));
/* 261 */             } else if (($$30 & 0x100000) == 1048576) {
/*     */               
/* 263 */               $$34 = Direction.UP;
/*     */             } 
/*     */             
/* 266 */             BlockPos $$35 = $$16.relative($$1.rotate(Direction.SOUTH), 8 + ($$27 - this.startY) * 8);
/* 267 */             $$35 = $$35.relative($$1.rotate(Direction.EAST), -1 + ($$28 - this.startX) * 8);
/*     */             
/* 269 */             if (WoodlandMansionPieces.MansionGrid.isHouse($$18, $$28 - 1, $$27) && !$$3.isRoomId($$18, $$28 - 1, $$27, $$15, $$32)) {
/* 270 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, ($$34 == Direction.WEST) ? $$25 : $$24, $$35, $$1));
/*     */             }
/* 272 */             if ($$18.get($$28 + 1, $$27) == 1 && !$$29) {
/* 273 */               BlockPos $$36 = $$35.relative($$1.rotate(Direction.EAST), 8);
/* 274 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, ($$34 == Direction.EAST) ? $$25 : $$24, $$36, $$1));
/*     */             } 
/* 276 */             if (WoodlandMansionPieces.MansionGrid.isHouse($$18, $$28, $$27 + 1) && !$$3.isRoomId($$18, $$28, $$27 + 1, $$15, $$32)) {
/* 277 */               BlockPos $$37 = $$35.relative($$1.rotate(Direction.SOUTH), 7);
/* 278 */               $$37 = $$37.relative($$1.rotate(Direction.EAST), 7);
/* 279 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, ($$34 == Direction.SOUTH) ? $$25 : $$24, $$37, $$1.getRotated(Rotation.CLOCKWISE_90)));
/*     */             } 
/* 281 */             if ($$18.get($$28, $$27 - 1) == 1 && !$$29) {
/* 282 */               BlockPos $$38 = $$35.relative($$1.rotate(Direction.NORTH), 1);
/* 283 */               $$38 = $$38.relative($$1.rotate(Direction.EAST), 7);
/* 284 */               $$2.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, ($$34 == Direction.NORTH) ? $$25 : $$24, $$38, $$1.getRotated(Rotation.CLOCKWISE_90)));
/*     */             } 
/*     */             
/* 287 */             if ($$31 == 65536) {
/* 288 */               addRoom1x1($$2, $$35, $$1, $$34, $$14[$$15]);
/* 289 */             } else if ($$31 == 131072 && $$34 != null) {
/*     */               
/* 291 */               Direction $$39 = $$3.get1x2RoomDirection($$18, $$28, $$27, $$15, $$32);
/* 292 */               boolean $$40 = (($$30 & 0x400000) == 4194304);
/* 293 */               addRoom1x2($$2, $$35, $$1, $$39, $$34, $$14[$$15], $$40);
/* 294 */             } else if ($$31 == 262144 && $$34 != null && $$34 != Direction.UP) {
/*     */               
/* 296 */               Direction $$41 = $$34.getClockWise();
/* 297 */               if (!$$3.isRoomId($$18, $$28 + $$41.getStepX(), $$27 + $$41.getStepZ(), $$15, $$32)) {
/* 298 */                 $$41 = $$41.getOpposite();
/*     */               }
/* 300 */               addRoom2x2($$2, $$35, $$1, $$41, $$34, $$14[$$15]);
/* 301 */             } else if ($$31 == 262144 && $$34 == Direction.UP) {
/* 302 */               addRoom2x2Secret($$2, $$35, $$1, $$14[$$15]);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void traverseOuterWalls(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, WoodlandMansionPieces.PlacementData $$1, WoodlandMansionPieces.SimpleGrid $$2, Direction $$3, int $$4, int $$5, int $$6, int $$7) {
/* 311 */     int $$8 = $$4;
/* 312 */     int $$9 = $$5;
/* 313 */     Direction $$10 = $$3;
/*     */     
/*     */     do {
/* 316 */       if (!WoodlandMansionPieces.MansionGrid.isHouse($$2, $$8 + $$3.getStepX(), $$9 + $$3.getStepZ())) {
/*     */         
/* 318 */         traverseTurn($$0, $$1);
/* 319 */         $$3 = $$3.getClockWise();
/* 320 */         if ($$8 != $$6 || $$9 != $$7 || $$10 != $$3) {
/* 321 */           traverseWallPiece($$0, $$1);
/*     */         }
/* 323 */       } else if (WoodlandMansionPieces.MansionGrid.isHouse($$2, $$8 + $$3.getStepX(), $$9 + $$3.getStepZ()) && WoodlandMansionPieces.MansionGrid.isHouse($$2, $$8 + $$3.getStepX() + $$3.getCounterClockWise().getStepX(), $$9 + $$3.getStepZ() + $$3.getCounterClockWise().getStepZ())) {
/*     */         
/* 325 */         traverseInnerTurn($$0, $$1);
/* 326 */         $$8 += $$3.getStepX();
/* 327 */         $$9 += $$3.getStepZ();
/* 328 */         $$3 = $$3.getCounterClockWise();
/*     */       } else {
/* 330 */         $$8 += $$3.getStepX();
/* 331 */         $$9 += $$3.getStepZ();
/* 332 */         if ($$8 != $$6 || $$9 != $$7 || $$10 != $$3) {
/* 333 */           traverseWallPiece($$0, $$1);
/*     */         }
/*     */       } 
/* 336 */     } while ($$8 != $$6 || $$9 != $$7 || $$10 != $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   private void createRoof(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, BlockPos $$1, Rotation $$2, WoodlandMansionPieces.SimpleGrid $$3, @Nullable WoodlandMansionPieces.SimpleGrid $$4) {
/* 341 */     for (int $$5 = 0; $$5 < $$3.height; $$5++) {
/* 342 */       for (int $$6 = 0; $$6 < $$3.width; $$6++) {
/* 343 */         BlockPos $$7 = $$1;
/* 344 */         $$7 = $$7.relative($$2.rotate(Direction.SOUTH), 8 + ($$5 - this.startY) * 8);
/* 345 */         $$7 = $$7.relative($$2.rotate(Direction.EAST), ($$6 - this.startX) * 8);
/*     */ 
/*     */         
/* 348 */         boolean $$8 = ($$4 != null && WoodlandMansionPieces.MansionGrid.isHouse($$4, $$6, $$5));
/*     */         
/* 350 */         if (WoodlandMansionPieces.MansionGrid.isHouse($$3, $$6, $$5) && !$$8) {
/* 351 */           $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof", $$7.above(3), $$2));
/*     */           
/* 353 */           if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$6 + 1, $$5)) {
/* 354 */             BlockPos $$9 = $$7.relative($$2.rotate(Direction.EAST), 6);
/* 355 */             $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_front", $$9, $$2));
/*     */           } 
/* 357 */           if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$6 - 1, $$5)) {
/* 358 */             BlockPos $$10 = $$7.relative($$2.rotate(Direction.EAST), 0);
/* 359 */             $$10 = $$10.relative($$2.rotate(Direction.SOUTH), 7);
/* 360 */             $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_front", $$10, $$2.getRotated(Rotation.CLOCKWISE_180)));
/*     */           } 
/* 362 */           if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$6, $$5 - 1)) {
/* 363 */             BlockPos $$11 = $$7.relative($$2.rotate(Direction.WEST), 1);
/* 364 */             $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_front", $$11, $$2.getRotated(Rotation.COUNTERCLOCKWISE_90)));
/*     */           } 
/* 366 */           if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$6, $$5 + 1)) {
/* 367 */             BlockPos $$12 = $$7.relative($$2.rotate(Direction.EAST), 6);
/* 368 */             $$12 = $$12.relative($$2.rotate(Direction.SOUTH), 6);
/* 369 */             $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_front", $$12, $$2.getRotated(Rotation.CLOCKWISE_90)));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     if ($$4 != null) {
/* 376 */       for (int $$13 = 0; $$13 < $$3.height; $$13++) {
/* 377 */         for (int $$14 = 0; $$14 < $$3.width; $$14++) {
/* 378 */           BlockPos $$15 = $$1;
/* 379 */           $$15 = $$15.relative($$2.rotate(Direction.SOUTH), 8 + ($$13 - this.startY) * 8);
/* 380 */           $$15 = $$15.relative($$2.rotate(Direction.EAST), ($$14 - this.startX) * 8);
/*     */ 
/*     */           
/* 383 */           boolean $$16 = WoodlandMansionPieces.MansionGrid.isHouse($$4, $$14, $$13);
/*     */           
/* 385 */           if (WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14, $$13) && $$16) {
/*     */             
/* 387 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14 + 1, $$13)) {
/* 388 */               BlockPos $$17 = $$15.relative($$2.rotate(Direction.EAST), 7);
/* 389 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall", $$17, $$2));
/*     */             } 
/* 391 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14 - 1, $$13)) {
/* 392 */               BlockPos $$18 = $$15.relative($$2.rotate(Direction.WEST), 1);
/* 393 */               $$18 = $$18.relative($$2.rotate(Direction.SOUTH), 6);
/* 394 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall", $$18, $$2.getRotated(Rotation.CLOCKWISE_180)));
/*     */             } 
/* 396 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14, $$13 - 1)) {
/* 397 */               BlockPos $$19 = $$15.relative($$2.rotate(Direction.WEST), 0);
/* 398 */               $$19 = $$19.relative($$2.rotate(Direction.NORTH), 1);
/* 399 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall", $$19, $$2.getRotated(Rotation.COUNTERCLOCKWISE_90)));
/*     */             } 
/* 401 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14, $$13 + 1)) {
/* 402 */               BlockPos $$20 = $$15.relative($$2.rotate(Direction.EAST), 6);
/* 403 */               $$20 = $$20.relative($$2.rotate(Direction.SOUTH), 7);
/* 404 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall", $$20, $$2.getRotated(Rotation.CLOCKWISE_90)));
/*     */             } 
/*     */             
/* 407 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14 + 1, $$13)) {
/* 408 */               if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14, $$13 - 1)) {
/* 409 */                 BlockPos $$21 = $$15.relative($$2.rotate(Direction.EAST), 7);
/* 410 */                 $$21 = $$21.relative($$2.rotate(Direction.NORTH), 2);
/* 411 */                 $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall_corner", $$21, $$2));
/*     */               } 
/* 413 */               if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14, $$13 + 1)) {
/* 414 */                 BlockPos $$22 = $$15.relative($$2.rotate(Direction.EAST), 8);
/* 415 */                 $$22 = $$22.relative($$2.rotate(Direction.SOUTH), 7);
/* 416 */                 $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall_corner", $$22, $$2.getRotated(Rotation.CLOCKWISE_90)));
/*     */               } 
/*     */             } 
/* 419 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14 - 1, $$13)) {
/* 420 */               if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14, $$13 - 1)) {
/* 421 */                 BlockPos $$23 = $$15.relative($$2.rotate(Direction.WEST), 2);
/* 422 */                 $$23 = $$23.relative($$2.rotate(Direction.NORTH), 1);
/* 423 */                 $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall_corner", $$23, $$2.getRotated(Rotation.COUNTERCLOCKWISE_90)));
/*     */               } 
/* 425 */               if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$14, $$13 + 1)) {
/* 426 */                 BlockPos $$24 = $$15.relative($$2.rotate(Direction.WEST), 1);
/* 427 */                 $$24 = $$24.relative($$2.rotate(Direction.SOUTH), 8);
/* 428 */                 $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "small_wall_corner", $$24, $$2.getRotated(Rotation.CLOCKWISE_180)));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 436 */     for (int $$25 = 0; $$25 < $$3.height; $$25++) {
/* 437 */       for (int $$26 = 0; $$26 < $$3.width; $$26++) {
/* 438 */         BlockPos $$27 = $$1;
/* 439 */         $$27 = $$27.relative($$2.rotate(Direction.SOUTH), 8 + ($$25 - this.startY) * 8);
/* 440 */         $$27 = $$27.relative($$2.rotate(Direction.EAST), ($$26 - this.startX) * 8);
/*     */ 
/*     */         
/* 443 */         boolean $$28 = ($$4 != null && WoodlandMansionPieces.MansionGrid.isHouse($$4, $$26, $$25));
/*     */         
/* 445 */         if (WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26, $$25) && !$$28) {
/* 446 */           if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26 + 1, $$25)) {
/* 447 */             BlockPos $$29 = $$27.relative($$2.rotate(Direction.EAST), 6);
/* 448 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26, $$25 + 1)) {
/* 449 */               BlockPos $$30 = $$29.relative($$2.rotate(Direction.SOUTH), 6);
/* 450 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_corner", $$30, $$2));
/* 451 */             } else if (WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26 + 1, $$25 + 1)) {
/* 452 */               BlockPos $$31 = $$29.relative($$2.rotate(Direction.SOUTH), 5);
/* 453 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_inner_corner", $$31, $$2));
/*     */             } 
/* 455 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26, $$25 - 1)) {
/* 456 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_corner", $$29, $$2.getRotated(Rotation.COUNTERCLOCKWISE_90)));
/* 457 */             } else if (WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26 + 1, $$25 - 1)) {
/* 458 */               BlockPos $$32 = $$27.relative($$2.rotate(Direction.EAST), 9);
/* 459 */               $$32 = $$32.relative($$2.rotate(Direction.NORTH), 2);
/* 460 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_inner_corner", $$32, $$2.getRotated(Rotation.CLOCKWISE_90)));
/*     */             } 
/*     */           } 
/* 463 */           if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26 - 1, $$25)) {
/* 464 */             BlockPos $$33 = $$27.relative($$2.rotate(Direction.EAST), 0);
/* 465 */             $$33 = $$33.relative($$2.rotate(Direction.SOUTH), 0);
/* 466 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26, $$25 + 1)) {
/* 467 */               BlockPos $$34 = $$33.relative($$2.rotate(Direction.SOUTH), 6);
/* 468 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_corner", $$34, $$2.getRotated(Rotation.CLOCKWISE_90)));
/* 469 */             } else if (WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26 - 1, $$25 + 1)) {
/* 470 */               BlockPos $$35 = $$33.relative($$2.rotate(Direction.SOUTH), 8);
/* 471 */               $$35 = $$35.relative($$2.rotate(Direction.WEST), 3);
/* 472 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_inner_corner", $$35, $$2.getRotated(Rotation.COUNTERCLOCKWISE_90)));
/*     */             } 
/* 474 */             if (!WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26, $$25 - 1)) {
/* 475 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_corner", $$33, $$2.getRotated(Rotation.CLOCKWISE_180)));
/* 476 */             } else if (WoodlandMansionPieces.MansionGrid.isHouse($$3, $$26 - 1, $$25 - 1)) {
/* 477 */               BlockPos $$36 = $$33.relative($$2.rotate(Direction.SOUTH), 1);
/* 478 */               $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "roof_inner_corner", $$36, $$2.getRotated(Rotation.CLOCKWISE_180)));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void entrance(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, WoodlandMansionPieces.PlacementData $$1) {
/* 487 */     Direction $$2 = $$1.rotation.rotate(Direction.WEST);
/* 488 */     $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "entrance", $$1.position.relative($$2, 9), $$1.rotation));
/* 489 */     $$1.position = $$1.position.relative($$1.rotation.rotate(Direction.SOUTH), 16);
/*     */   }
/*     */   
/*     */   private void traverseWallPiece(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, WoodlandMansionPieces.PlacementData $$1) {
/* 493 */     $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$1.wallType, $$1.position.relative($$1.rotation.rotate(Direction.EAST), 7), $$1.rotation));
/* 494 */     $$1.position = $$1.position.relative($$1.rotation.rotate(Direction.SOUTH), 8);
/*     */   }
/*     */   
/*     */   private void traverseTurn(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, WoodlandMansionPieces.PlacementData $$1) {
/* 498 */     $$1.position = $$1.position.relative($$1.rotation.rotate(Direction.SOUTH), -1);
/* 499 */     $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, "wall_corner", $$1.position, $$1.rotation));
/* 500 */     $$1.position = $$1.position.relative($$1.rotation.rotate(Direction.SOUTH), -7);
/* 501 */     $$1.position = $$1.position.relative($$1.rotation.rotate(Direction.WEST), -6);
/* 502 */     $$1.rotation = $$1.rotation.getRotated(Rotation.CLOCKWISE_90);
/*     */   }
/*     */   
/*     */   private void traverseInnerTurn(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, WoodlandMansionPieces.PlacementData $$1) {
/* 506 */     $$1.position = $$1.position.relative($$1.rotation.rotate(Direction.SOUTH), 6);
/* 507 */     $$1.position = $$1.position.relative($$1.rotation.rotate(Direction.EAST), 8);
/* 508 */     $$1.rotation = $$1.rotation.getRotated(Rotation.COUNTERCLOCKWISE_90);
/*     */   }
/*     */   
/*     */   private void addRoom1x1(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, BlockPos $$1, Rotation $$2, Direction $$3, WoodlandMansionPieces.FloorRoomCollection $$4) {
/* 512 */     Rotation $$5 = Rotation.NONE;
/* 513 */     String $$6 = $$4.get1x1(this.random);
/* 514 */     if ($$3 != Direction.EAST) {
/* 515 */       if ($$3 == Direction.NORTH) {
/* 516 */         $$5 = $$5.getRotated(Rotation.COUNTERCLOCKWISE_90);
/* 517 */       } else if ($$3 == Direction.WEST) {
/* 518 */         $$5 = $$5.getRotated(Rotation.CLOCKWISE_180);
/* 519 */       } else if ($$3 == Direction.SOUTH) {
/* 520 */         $$5 = $$5.getRotated(Rotation.CLOCKWISE_90);
/*     */       } else {
/*     */         
/* 523 */         $$6 = $$4.get1x1Secret(this.random);
/*     */       } 
/*     */     }
/* 526 */     BlockPos $$7 = StructureTemplate.getZeroPositionWithTransform(new BlockPos(1, 0, 0), Mirror.NONE, $$5, 7, 7);
/* 527 */     $$5 = $$5.getRotated($$2);
/* 528 */     $$7 = $$7.rotate($$2);
/* 529 */     BlockPos $$8 = $$1.offset($$7.getX(), 0, $$7.getZ());
/* 530 */     $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$6, $$8, $$5));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRoom1x2(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, BlockPos $$1, Rotation $$2, Direction $$3, Direction $$4, WoodlandMansionPieces.FloorRoomCollection $$5, boolean $$6) {
/* 537 */     if ($$4 == Direction.EAST && $$3 == Direction.SOUTH) {
/*     */ 
/*     */       
/* 540 */       BlockPos $$7 = $$1.relative($$2.rotate(Direction.EAST), 1);
/* 541 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$7, $$2));
/* 542 */     } else if ($$4 == Direction.EAST && $$3 == Direction.NORTH) {
/*     */ 
/*     */       
/* 545 */       BlockPos $$8 = $$1.relative($$2.rotate(Direction.EAST), 1);
/* 546 */       $$8 = $$8.relative($$2.rotate(Direction.SOUTH), 6);
/* 547 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$8, $$2, Mirror.LEFT_RIGHT));
/* 548 */     } else if ($$4 == Direction.WEST && $$3 == Direction.NORTH) {
/*     */ 
/*     */       
/* 551 */       BlockPos $$9 = $$1.relative($$2.rotate(Direction.EAST), 7);
/* 552 */       $$9 = $$9.relative($$2.rotate(Direction.SOUTH), 6);
/* 553 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$9, $$2.getRotated(Rotation.CLOCKWISE_180)));
/* 554 */     } else if ($$4 == Direction.WEST && $$3 == Direction.SOUTH) {
/*     */ 
/*     */       
/* 557 */       BlockPos $$10 = $$1.relative($$2.rotate(Direction.EAST), 7);
/* 558 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$10, $$2, Mirror.FRONT_BACK));
/* 559 */     } else if ($$4 == Direction.SOUTH && $$3 == Direction.EAST) {
/*     */ 
/*     */       
/* 562 */       BlockPos $$11 = $$1.relative($$2.rotate(Direction.EAST), 1);
/* 563 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$11, $$2.getRotated(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT));
/* 564 */     } else if ($$4 == Direction.SOUTH && $$3 == Direction.WEST) {
/*     */ 
/*     */       
/* 567 */       BlockPos $$12 = $$1.relative($$2.rotate(Direction.EAST), 7);
/* 568 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$12, $$2.getRotated(Rotation.CLOCKWISE_90)));
/* 569 */     } else if ($$4 == Direction.NORTH && $$3 == Direction.WEST) {
/*     */ 
/*     */       
/* 572 */       BlockPos $$13 = $$1.relative($$2.rotate(Direction.EAST), 7);
/* 573 */       $$13 = $$13.relative($$2.rotate(Direction.SOUTH), 6);
/* 574 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$13, $$2.getRotated(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK));
/* 575 */     } else if ($$4 == Direction.NORTH && $$3 == Direction.EAST) {
/*     */ 
/*     */       
/* 578 */       BlockPos $$14 = $$1.relative($$2.rotate(Direction.EAST), 1);
/* 579 */       $$14 = $$14.relative($$2.rotate(Direction.SOUTH), 6);
/* 580 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2SideEntrance(this.random, $$6), $$14, $$2.getRotated(Rotation.COUNTERCLOCKWISE_90)));
/* 581 */     } else if ($$4 == Direction.SOUTH && $$3 == Direction.NORTH) {
/*     */ 
/*     */ 
/*     */       
/* 585 */       BlockPos $$15 = $$1.relative($$2.rotate(Direction.EAST), 1);
/* 586 */       $$15 = $$15.relative($$2.rotate(Direction.NORTH), 8);
/* 587 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2FrontEntrance(this.random, $$6), $$15, $$2));
/* 588 */     } else if ($$4 == Direction.NORTH && $$3 == Direction.SOUTH) {
/*     */ 
/*     */ 
/*     */       
/* 592 */       BlockPos $$16 = $$1.relative($$2.rotate(Direction.EAST), 7);
/* 593 */       $$16 = $$16.relative($$2.rotate(Direction.SOUTH), 14);
/* 594 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2FrontEntrance(this.random, $$6), $$16, $$2.getRotated(Rotation.CLOCKWISE_180)));
/* 595 */     } else if ($$4 == Direction.WEST && $$3 == Direction.EAST) {
/*     */       
/* 597 */       BlockPos $$17 = $$1.relative($$2.rotate(Direction.EAST), 15);
/* 598 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2FrontEntrance(this.random, $$6), $$17, $$2.getRotated(Rotation.CLOCKWISE_90)));
/* 599 */     } else if ($$4 == Direction.EAST && $$3 == Direction.WEST) {
/*     */       
/* 601 */       BlockPos $$18 = $$1.relative($$2.rotate(Direction.WEST), 7);
/* 602 */       $$18 = $$18.relative($$2.rotate(Direction.SOUTH), 6);
/* 603 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2FrontEntrance(this.random, $$6), $$18, $$2.getRotated(Rotation.COUNTERCLOCKWISE_90)));
/* 604 */     } else if ($$4 == Direction.UP && $$3 == Direction.EAST) {
/*     */       
/* 606 */       BlockPos $$19 = $$1.relative($$2.rotate(Direction.EAST), 15);
/* 607 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2Secret(this.random), $$19, $$2.getRotated(Rotation.CLOCKWISE_90)));
/* 608 */     } else if ($$4 == Direction.UP && $$3 == Direction.SOUTH) {
/*     */ 
/*     */ 
/*     */       
/* 612 */       BlockPos $$20 = $$1.relative($$2.rotate(Direction.EAST), 1);
/* 613 */       $$20 = $$20.relative($$2.rotate(Direction.NORTH), 0);
/* 614 */       $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get1x2Secret(this.random), $$20, $$2));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addRoom2x2(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, BlockPos $$1, Rotation $$2, Direction $$3, Direction $$4, WoodlandMansionPieces.FloorRoomCollection $$5) {
/* 619 */     int $$6 = 0;
/* 620 */     int $$7 = 0;
/* 621 */     Rotation $$8 = $$2;
/* 622 */     Mirror $$9 = Mirror.NONE;
/*     */ 
/*     */ 
/*     */     
/* 626 */     if ($$4 == Direction.EAST && $$3 == Direction.SOUTH) {
/*     */ 
/*     */       
/* 629 */       $$6 = -7;
/* 630 */     } else if ($$4 == Direction.EAST && $$3 == Direction.NORTH) {
/*     */ 
/*     */       
/* 633 */       $$6 = -7;
/* 634 */       $$7 = 6;
/* 635 */       $$9 = Mirror.LEFT_RIGHT;
/* 636 */     } else if ($$4 == Direction.NORTH && $$3 == Direction.EAST) {
/*     */ 
/*     */ 
/*     */       
/* 640 */       $$6 = 1;
/* 641 */       $$7 = 14;
/* 642 */       $$8 = $$2.getRotated(Rotation.COUNTERCLOCKWISE_90);
/* 643 */     } else if ($$4 == Direction.NORTH && $$3 == Direction.WEST) {
/*     */ 
/*     */ 
/*     */       
/* 647 */       $$6 = 7;
/* 648 */       $$7 = 14;
/* 649 */       $$8 = $$2.getRotated(Rotation.COUNTERCLOCKWISE_90);
/* 650 */       $$9 = Mirror.LEFT_RIGHT;
/* 651 */     } else if ($$4 == Direction.SOUTH && $$3 == Direction.WEST) {
/*     */ 
/*     */ 
/*     */       
/* 655 */       $$6 = 7;
/* 656 */       $$7 = -8;
/* 657 */       $$8 = $$2.getRotated(Rotation.CLOCKWISE_90);
/* 658 */     } else if ($$4 == Direction.SOUTH && $$3 == Direction.EAST) {
/*     */ 
/*     */ 
/*     */       
/* 662 */       $$6 = 1;
/* 663 */       $$7 = -8;
/* 664 */       $$8 = $$2.getRotated(Rotation.CLOCKWISE_90);
/* 665 */       $$9 = Mirror.LEFT_RIGHT;
/* 666 */     } else if ($$4 == Direction.WEST && $$3 == Direction.NORTH) {
/*     */ 
/*     */       
/* 669 */       $$6 = 15;
/* 670 */       $$7 = 6;
/* 671 */       $$8 = $$2.getRotated(Rotation.CLOCKWISE_180);
/* 672 */     } else if ($$4 == Direction.WEST && $$3 == Direction.SOUTH) {
/*     */ 
/*     */       
/* 675 */       $$6 = 15;
/* 676 */       $$9 = Mirror.FRONT_BACK;
/*     */     } 
/*     */     
/* 679 */     BlockPos $$10 = $$1.relative($$2.rotate(Direction.EAST), $$6);
/* 680 */     $$10 = $$10.relative($$2.rotate(Direction.SOUTH), $$7);
/* 681 */     $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$5.get2x2(this.random), $$10, $$8, $$9));
/*     */   }
/*     */   
/*     */   private void addRoom2x2Secret(List<WoodlandMansionPieces.WoodlandMansionPiece> $$0, BlockPos $$1, Rotation $$2, WoodlandMansionPieces.FloorRoomCollection $$3) {
/* 685 */     BlockPos $$4 = $$1.relative($$2.rotate(Direction.EAST), 1);
/* 686 */     $$0.add(new WoodlandMansionPieces.WoodlandMansionPiece(this.structureTemplateManager, $$3.get2x2Secret(this.random), $$4, $$2, Mirror.NONE));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\WoodlandMansionPieces$MansionPiecePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */