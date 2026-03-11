/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.vehicle.MinecartChest;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.FenceBlock;
/*     */ import net.minecraft.world.level.block.RailBlock;
/*     */ import net.minecraft.world.level.block.WallTorchBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
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
/*     */ public class MineShaftCorridor
/*     */   extends MineshaftPieces.MineShaftPiece
/*     */ {
/*     */   private final boolean hasRails;
/*     */   private final boolean spiderCorridor;
/*     */   private boolean hasPlacedSpider;
/*     */   private final int numSections;
/*     */   
/*     */   public MineShaftCorridor(CompoundTag $$0) {
/* 320 */     super(StructurePieceType.MINE_SHAFT_CORRIDOR, $$0);
/*     */     
/* 322 */     this.hasRails = $$0.getBoolean("hr");
/* 323 */     this.spiderCorridor = $$0.getBoolean("sc");
/* 324 */     this.hasPlacedSpider = $$0.getBoolean("hps");
/* 325 */     this.numSections = $$0.getInt("Num");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 330 */     super.addAdditionalSaveData($$0, $$1);
/* 331 */     $$1.putBoolean("hr", this.hasRails);
/* 332 */     $$1.putBoolean("sc", this.spiderCorridor);
/* 333 */     $$1.putBoolean("hps", this.hasPlacedSpider);
/* 334 */     $$1.putInt("Num", this.numSections);
/*     */   }
/*     */   
/*     */   public MineShaftCorridor(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3, MineshaftStructure.Type $$4) {
/* 338 */     super(StructurePieceType.MINE_SHAFT_CORRIDOR, $$0, $$4, $$2);
/* 339 */     setOrientation($$3);
/* 340 */     this.hasRails = ($$1.nextInt(3) == 0);
/* 341 */     this.spiderCorridor = (!this.hasRails && $$1.nextInt(23) == 0);
/*     */     
/* 343 */     if (getOrientation().getAxis() == Direction.Axis.Z) {
/* 344 */       this.numSections = $$2.getZSpan() / 5;
/*     */     } else {
/* 346 */       this.numSections = $$2.getXSpan() / 5;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BoundingBox findCorridorSize(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5) {
/* 352 */     int $$6 = $$1.nextInt(3) + 2;
/* 353 */     while ($$6 > 0) {
/*     */       BoundingBox $$8, $$9, $$10, $$11;
/* 355 */       int $$7 = $$6 * 5;
/*     */       
/* 357 */       switch (MineshaftPieces.null.$SwitchMap$net$minecraft$core$Direction[$$5.ordinal()]) {
/*     */         
/*     */         default:
/* 360 */           $$8 = new BoundingBox(0, 0, -($$7 - 1), 2, 2, 0);
/*     */           break;
/*     */         case 2:
/* 363 */           $$9 = new BoundingBox(0, 0, 0, 2, 2, $$7 - 1);
/*     */           break;
/*     */         case 3:
/* 366 */           $$10 = new BoundingBox(-($$7 - 1), 0, 0, 0, 2, 2);
/*     */           break;
/*     */         case 4:
/* 369 */           $$11 = new BoundingBox(0, 0, 0, $$7 - 1, 2, 2);
/*     */           break;
/*     */       } 
/*     */       
/* 373 */       $$11.move($$2, $$3, $$4);
/*     */       
/* 375 */       if ($$0.findCollisionPiece($$11) != null) {
/* 376 */         $$6--; continue;
/*     */       } 
/* 378 */       return $$11;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 383 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 388 */     int $$3 = getGenDepth();
/* 389 */     int $$4 = $$2.nextInt(4);
/* 390 */     Direction $$5 = getOrientation();
/* 391 */     if ($$5 != null) {
/* 392 */       switch (MineshaftPieces.null.$SwitchMap$net$minecraft$core$Direction[$$5.ordinal()]) {
/*     */         
/*     */         default:
/* 395 */           if ($$4 <= 1) {
/* 396 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX(), this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.minZ() - 1, $$5, $$3); break;
/* 397 */           }  if ($$4 == 2) {
/* 398 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.minZ(), Direction.WEST, $$3); break;
/*     */           } 
/* 400 */           MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.minZ(), Direction.EAST, $$3);
/*     */           break;
/*     */         
/*     */         case 2:
/* 404 */           if ($$4 <= 1) {
/* 405 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX(), this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.maxZ() + 1, $$5, $$3); break;
/* 406 */           }  if ($$4 == 2) {
/* 407 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.maxZ() - 3, Direction.WEST, $$3); break;
/*     */           } 
/* 409 */           MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.maxZ() - 3, Direction.EAST, $$3);
/*     */           break;
/*     */         
/*     */         case 3:
/* 413 */           if ($$4 <= 1) {
/* 414 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.minZ(), $$5, $$3); break;
/* 415 */           }  if ($$4 == 2) {
/* 416 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX(), this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.minZ() - 1, Direction.NORTH, $$3); break;
/*     */           } 
/* 418 */           MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX(), this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.maxZ() + 1, Direction.SOUTH, $$3);
/*     */           break;
/*     */         
/*     */         case 4:
/* 422 */           if ($$4 <= 1) {
/* 423 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.minZ(), $$5, $$3); break;
/* 424 */           }  if ($$4 == 2) {
/* 425 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() - 3, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.minZ() - 1, Direction.NORTH, $$3); break;
/*     */           } 
/* 427 */           MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() - 3, this.boundingBox.minY() - 1 + $$2.nextInt(3), this.boundingBox.maxZ() + 1, Direction.SOUTH, $$3);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 434 */     if ($$3 < 8) {
/* 435 */       if ($$5 == Direction.NORTH || $$5 == Direction.SOUTH) {
/* 436 */         for (int $$6 = this.boundingBox.minZ() + 3; $$6 + 3 <= this.boundingBox.maxZ(); $$6 += 5) {
/* 437 */           int $$7 = $$2.nextInt(5);
/* 438 */           if ($$7 == 0) {
/* 439 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY(), $$6, Direction.WEST, $$3 + 1);
/* 440 */           } else if ($$7 == 1) {
/* 441 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY(), $$6, Direction.EAST, $$3 + 1);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 445 */         for (int $$8 = this.boundingBox.minX() + 3; $$8 + 3 <= this.boundingBox.maxX(); $$8 += 5) {
/* 446 */           int $$9 = $$2.nextInt(5);
/* 447 */           if ($$9 == 0) {
/* 448 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, $$8, this.boundingBox.minY(), this.boundingBox.minZ() - 1, Direction.NORTH, $$3 + 1);
/* 449 */           } else if ($$9 == 1) {
/* 450 */             MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, $$8, this.boundingBox.minY(), this.boundingBox.maxZ() + 1, Direction.SOUTH, $$3 + 1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean createChest(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, int $$3, int $$4, int $$5, ResourceLocation $$6) {
/* 459 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$3, $$4, $$5);
/* 460 */     if ($$1.isInside((Vec3i)mutableBlockPos) && 
/* 461 */       $$0.getBlockState((BlockPos)mutableBlockPos).isAir() && !$$0.getBlockState(mutableBlockPos.below()).isAir()) {
/* 462 */       BlockState $$8 = (BlockState)Blocks.RAIL.defaultBlockState().setValue((Property)RailBlock.SHAPE, $$2.nextBoolean() ? (Comparable)RailShape.NORTH_SOUTH : (Comparable)RailShape.EAST_WEST);
/* 463 */       placeBlock($$0, $$8, $$3, $$4, $$5, $$1);
/* 464 */       MinecartChest $$9 = new MinecartChest((Level)$$0.getLevel(), mutableBlockPos.getX() + 0.5D, mutableBlockPos.getY() + 0.5D, mutableBlockPos.getZ() + 0.5D);
/* 465 */       $$9.setLootTable($$6, $$2.nextLong());
/* 466 */       $$0.addFreshEntity((Entity)$$9);
/* 467 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 471 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 476 */     if (isInInvalidLocation((LevelAccessor)$$0, $$4)) {
/*     */       return;
/*     */     }
/*     */     
/* 480 */     int $$7 = 0;
/* 481 */     int $$8 = 2;
/* 482 */     int $$9 = 0;
/* 483 */     int $$10 = 2;
/* 484 */     int $$11 = this.numSections * 5 - 1;
/*     */     
/* 486 */     BlockState $$12 = this.type.getPlanksState();
/*     */ 
/*     */     
/* 489 */     generateBox($$0, $$4, 0, 0, 0, 2, 1, $$11, CAVE_AIR, CAVE_AIR, false);
/* 490 */     generateMaybeBox($$0, $$4, $$3, 0.8F, 0, 2, 0, 2, 2, $$11, CAVE_AIR, CAVE_AIR, false, false);
/*     */     
/* 492 */     if (this.spiderCorridor) {
/* 493 */       generateMaybeBox($$0, $$4, $$3, 0.6F, 0, 0, 0, 2, 1, $$11, Blocks.COBWEB.defaultBlockState(), CAVE_AIR, false, true);
/*     */     }
/*     */ 
/*     */     
/* 497 */     for (int $$13 = 0; $$13 < this.numSections; $$13++) {
/* 498 */       int $$14 = 2 + $$13 * 5;
/*     */       
/* 500 */       placeSupport($$0, $$4, 0, 0, $$14, 2, 2, $$3);
/*     */       
/* 502 */       maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 0, 2, $$14 - 1);
/* 503 */       maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 2, 2, $$14 - 1);
/* 504 */       maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 0, 2, $$14 + 1);
/* 505 */       maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 2, 2, $$14 + 1);
/* 506 */       maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 0, 2, $$14 - 2);
/* 507 */       maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 2, 2, $$14 - 2);
/* 508 */       maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 0, 2, $$14 + 2);
/* 509 */       maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 2, 2, $$14 + 2);
/*     */       
/* 511 */       if ($$3.nextInt(100) == 0) {
/* 512 */         createChest($$0, $$4, $$3, 2, 0, $$14 - 1, BuiltInLootTables.ABANDONED_MINESHAFT);
/*     */       }
/* 514 */       if ($$3.nextInt(100) == 0) {
/* 515 */         createChest($$0, $$4, $$3, 0, 0, $$14 + 1, BuiltInLootTables.ABANDONED_MINESHAFT);
/*     */       }
/* 517 */       if (this.spiderCorridor && !this.hasPlacedSpider) {
/* 518 */         int $$15 = 1;
/* 519 */         int $$16 = $$14 - 1 + $$3.nextInt(3);
/* 520 */         BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(1, 0, $$16);
/*     */         
/* 522 */         if ($$4.isInside((Vec3i)mutableBlockPos) && isInterior((LevelReader)$$0, 1, 0, $$16, $$4)) {
/* 523 */           this.hasPlacedSpider = true;
/* 524 */           $$0.setBlock((BlockPos)mutableBlockPos, Blocks.SPAWNER.defaultBlockState(), 2);
/*     */           
/* 526 */           BlockEntity $$18 = $$0.getBlockEntity((BlockPos)mutableBlockPos);
/* 527 */           if ($$18 instanceof SpawnerBlockEntity) { SpawnerBlockEntity $$19 = (SpawnerBlockEntity)$$18;
/* 528 */             $$19.setEntityId(EntityType.CAVE_SPIDER, $$3); }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 535 */     for (int $$20 = 0; $$20 <= 2; $$20++) {
/* 536 */       for (int $$21 = 0; $$21 <= $$11; $$21++) {
/* 537 */         setPlanksBlock($$0, $$4, $$12, $$20, -1, $$21);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 542 */     int $$22 = 2;
/* 543 */     placeDoubleLowerOrUpperSupport($$0, $$4, 0, -1, 2);
/* 544 */     if (this.numSections > 1) {
/* 545 */       int $$23 = $$11 - 2;
/* 546 */       placeDoubleLowerOrUpperSupport($$0, $$4, 0, -1, $$23);
/*     */     } 
/*     */     
/* 549 */     if (this.hasRails) {
/* 550 */       BlockState $$24 = (BlockState)Blocks.RAIL.defaultBlockState().setValue((Property)RailBlock.SHAPE, (Comparable)RailShape.NORTH_SOUTH);
/* 551 */       for (int $$25 = 0; $$25 <= $$11; $$25++) {
/* 552 */         BlockState $$26 = getBlock((BlockGetter)$$0, 1, -1, $$25, $$4);
/* 553 */         if (!$$26.isAir() && $$26.isSolidRender((BlockGetter)$$0, (BlockPos)getWorldPos(1, -1, $$25))) {
/* 554 */           float $$27 = isInterior((LevelReader)$$0, 1, 0, $$25, $$4) ? 0.7F : 0.9F;
/* 555 */           maybeGenerateBlock($$0, $$4, $$3, $$27, 1, 0, $$25, $$24);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeDoubleLowerOrUpperSupport(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4) {
/* 562 */     BlockState $$5 = this.type.getWoodState();
/* 563 */     BlockState $$6 = this.type.getPlanksState();
/* 564 */     if (getBlock((BlockGetter)$$0, $$2, $$3, $$4, $$1).is($$6.getBlock())) {
/* 565 */       fillPillarDownOrChainUp($$0, $$5, $$2, $$3, $$4, $$1);
/*     */     }
/* 567 */     if (getBlock((BlockGetter)$$0, $$2 + 2, $$3, $$4, $$1).is($$6.getBlock())) {
/* 568 */       fillPillarDownOrChainUp($$0, $$5, $$2 + 2, $$3, $$4, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fillColumnDown(WorldGenLevel $$0, BlockState $$1, int $$2, int $$3, int $$4, BoundingBox $$5) {
/* 574 */     BlockPos.MutableBlockPos $$6 = getWorldPos($$2, $$3, $$4);
/* 575 */     if (!$$5.isInside((Vec3i)$$6)) {
/*     */       return;
/*     */     }
/*     */     
/* 579 */     int $$7 = $$6.getY();
/*     */ 
/*     */     
/* 582 */     while (isReplaceableByStructures($$0.getBlockState((BlockPos)$$6)) && $$6.getY() > $$0.getMinBuildHeight() + 1) {
/* 583 */       $$6.move(Direction.DOWN);
/*     */     }
/* 585 */     if (!canPlaceColumnOnTopOf((LevelReader)$$0, (BlockPos)$$6, $$0.getBlockState((BlockPos)$$6))) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 590 */     while ($$6.getY() < $$7) {
/* 591 */       $$6.move(Direction.UP);
/* 592 */       $$0.setBlock((BlockPos)$$6, $$1, 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fillPillarDownOrChainUp(WorldGenLevel $$0, BlockState $$1, int $$2, int $$3, int $$4, BoundingBox $$5) {
/* 598 */     BlockPos.MutableBlockPos $$6 = getWorldPos($$2, $$3, $$4);
/* 599 */     if (!$$5.isInside((Vec3i)$$6)) {
/*     */       return;
/*     */     }
/*     */     
/* 603 */     int $$7 = $$6.getY();
/*     */ 
/*     */     
/* 606 */     int $$8 = 1;
/*     */     
/* 608 */     boolean $$9 = true;
/* 609 */     boolean $$10 = true;
/* 610 */     while ($$9 || $$10) {
/* 611 */       if ($$9) {
/* 612 */         $$6.setY($$7 - $$8);
/* 613 */         BlockState $$11 = $$0.getBlockState((BlockPos)$$6);
/* 614 */         boolean $$12 = (isReplaceableByStructures($$11) && !$$11.is(Blocks.LAVA));
/* 615 */         if (!$$12 && canPlaceColumnOnTopOf((LevelReader)$$0, (BlockPos)$$6, $$11)) {
/* 616 */           fillColumnBetween($$0, $$1, $$6, $$7 - $$8 + 1, $$7);
/*     */           return;
/*     */         } 
/* 619 */         $$9 = ($$8 <= 20 && $$12 && $$6.getY() > $$0.getMinBuildHeight() + 1);
/*     */       } 
/*     */       
/* 622 */       if ($$10) {
/* 623 */         $$6.setY($$7 + $$8);
/* 624 */         BlockState $$13 = $$0.getBlockState((BlockPos)$$6);
/* 625 */         boolean $$14 = isReplaceableByStructures($$13);
/* 626 */         if (!$$14 && canHangChainBelow((LevelReader)$$0, (BlockPos)$$6, $$13)) {
/*     */           
/* 628 */           $$0.setBlock((BlockPos)$$6.setY($$7 + 1), this.type.getFenceState(), 2);
/* 629 */           fillColumnBetween($$0, Blocks.CHAIN.defaultBlockState(), $$6, $$7 + 2, $$7 + $$8);
/*     */           return;
/*     */         } 
/* 632 */         $$10 = ($$8 <= 50 && $$14 && $$6.getY() < $$0.getMaxBuildHeight() - 1);
/*     */       } 
/*     */       
/* 635 */       $$8++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void fillColumnBetween(WorldGenLevel $$0, BlockState $$1, BlockPos.MutableBlockPos $$2, int $$3, int $$4) {
/* 640 */     for (int $$5 = $$3; $$5 < $$4; $$5++) {
/* 641 */       $$0.setBlock((BlockPos)$$2.setY($$5), $$1, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean canPlaceColumnOnTopOf(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 646 */     return $$2.isFaceSturdy((BlockGetter)$$0, $$1, Direction.UP);
/*     */   }
/*     */   
/*     */   private boolean canHangChainBelow(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 650 */     return (Block.canSupportCenter($$0, $$1, Direction.DOWN) && !($$2.getBlock() instanceof net.minecraft.world.level.block.FallingBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeSupport(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, RandomSource $$7) {
/* 655 */     if (!isSupportingBox((BlockGetter)$$0, $$1, $$2, $$6, $$5, $$4)) {
/*     */       return;
/*     */     }
/*     */     
/* 659 */     BlockState $$8 = this.type.getPlanksState();
/* 660 */     BlockState $$9 = this.type.getFenceState();
/*     */     
/* 662 */     generateBox($$0, $$1, $$2, $$3, $$4, $$2, $$5 - 1, $$4, (BlockState)$$9.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), CAVE_AIR, false);
/* 663 */     generateBox($$0, $$1, $$6, $$3, $$4, $$6, $$5 - 1, $$4, (BlockState)$$9.setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), CAVE_AIR, false);
/* 664 */     if ($$7.nextInt(4) == 0) {
/* 665 */       generateBox($$0, $$1, $$2, $$5, $$4, $$2, $$5, $$4, $$8, CAVE_AIR, false);
/* 666 */       generateBox($$0, $$1, $$6, $$5, $$4, $$6, $$5, $$4, $$8, CAVE_AIR, false);
/*     */     } else {
/* 668 */       generateBox($$0, $$1, $$2, $$5, $$4, $$6, $$5, $$4, $$8, CAVE_AIR, false);
/* 669 */       maybeGenerateBlock($$0, $$1, $$7, 0.05F, $$2 + 1, $$5, $$4 - 1, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.SOUTH));
/* 670 */       maybeGenerateBlock($$0, $$1, $$7, 0.05F, $$2 + 1, $$5, $$4 + 1, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.NORTH));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void maybePlaceCobWeb(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, float $$3, int $$4, int $$5, int $$6) {
/* 675 */     if (isInterior((LevelReader)$$0, $$4, $$5, $$6, $$1) && $$2.nextFloat() < $$3 && hasSturdyNeighbours($$0, $$1, $$4, $$5, $$6, 2)) {
/* 676 */       placeBlock($$0, Blocks.COBWEB.defaultBlockState(), $$4, $$5, $$6, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean hasSturdyNeighbours(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5) {
/* 681 */     BlockPos.MutableBlockPos $$6 = getWorldPos($$2, $$3, $$4);
/* 682 */     int $$7 = 0;
/* 683 */     for (Direction $$8 : Direction.values()) {
/* 684 */       $$6.move($$8);
/*     */       
/* 686 */       $$7++;
/* 687 */       if ($$1.isInside((Vec3i)$$6) && $$0.getBlockState((BlockPos)$$6).isFaceSturdy((BlockGetter)$$0, (BlockPos)$$6, $$8.getOpposite()) && $$7 >= $$5) {
/* 688 */         return true;
/*     */       }
/*     */       
/* 691 */       $$6.move($$8.getOpposite());
/*     */     } 
/* 693 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\MineshaftPieces$MineShaftCorridor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */