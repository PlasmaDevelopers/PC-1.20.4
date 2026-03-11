/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.DispenserBlock;
/*     */ import net.minecraft.world.level.block.HorizontalDirectionalBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.DispenserBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StructurePiece
/*     */ {
/*  68 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  70 */   protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.defaultBlockState();
/*     */   protected BoundingBox boundingBox;
/*     */   @Nullable
/*     */   private Direction orientation;
/*     */   private Mirror mirror;
/*     */   private Rotation rotation;
/*     */   protected int genDepth;
/*     */   private final StructurePieceType type;
/*     */   
/*     */   protected StructurePiece(StructurePieceType $$0, int $$1, BoundingBox $$2) {
/*  80 */     this.type = $$0;
/*  81 */     this.genDepth = $$1;
/*  82 */     this.boundingBox = $$2;
/*     */   }
/*     */   
/*     */   public StructurePiece(StructurePieceType $$0, CompoundTag $$1) {
/*  86 */     this($$0, $$1
/*     */         
/*  88 */         .getInt("GD"), (BoundingBox)BoundingBox.CODEC
/*  89 */         .parse((DynamicOps)NbtOps.INSTANCE, $$1.get("BB")).resultOrPartial(LOGGER::error).orElseThrow(() -> new IllegalArgumentException("Invalid boundingbox")));
/*     */     
/*  91 */     int $$2 = $$1.getInt("O");
/*  92 */     setOrientation(($$2 == -1) ? null : Direction.from2DDataValue($$2));
/*     */   }
/*     */   
/*     */   protected static BoundingBox makeBoundingBox(int $$0, int $$1, int $$2, Direction $$3, int $$4, int $$5, int $$6) {
/*  96 */     if ($$3.getAxis() == Direction.Axis.Z) {
/*  97 */       return new BoundingBox($$0, $$1, $$2, $$0 + $$4 - 1, $$1 + $$5 - 1, $$2 + $$6 - 1);
/*     */     }
/*  99 */     return new BoundingBox($$0, $$1, $$2, $$0 + $$6 - 1, $$1 + $$5 - 1, $$2 + $$4 - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Direction getRandomHorizontalDirection(RandomSource $$0) {
/* 104 */     return Direction.Plane.HORIZONTAL.getRandomDirection($$0);
/*     */   }
/*     */   
/*     */   public final CompoundTag createTag(StructurePieceSerializationContext $$0) {
/* 108 */     CompoundTag $$1 = new CompoundTag();
/*     */     
/* 110 */     $$1.putString("id", BuiltInRegistries.STRUCTURE_PIECE.getKey(getType()).toString());
/*     */     
/* 112 */     Objects.requireNonNull(LOGGER); BoundingBox.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.boundingBox).resultOrPartial(LOGGER::error)
/* 113 */       .ifPresent($$1 -> $$0.put("BB", $$1));
/* 114 */     Direction $$2 = getOrientation();
/* 115 */     $$1.putInt("O", ($$2 == null) ? -1 : $$2.get2DDataValue());
/* 116 */     $$1.putInt("GD", this.genDepth);
/*     */     
/* 118 */     addAdditionalSaveData($$0, $$1);
/*     */     
/* 120 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBox getBoundingBox() {
/* 131 */     return this.boundingBox;
/*     */   }
/*     */   
/*     */   public int getGenDepth() {
/* 135 */     return this.genDepth;
/*     */   }
/*     */   
/*     */   public void setGenDepth(int $$0) {
/* 139 */     this.genDepth = $$0;
/*     */   }
/*     */   
/*     */   public boolean isCloseToChunk(ChunkPos $$0, int $$1) {
/* 143 */     int $$2 = $$0.getMinBlockX();
/* 144 */     int $$3 = $$0.getMinBlockZ();
/*     */     
/* 146 */     return this.boundingBox.intersects($$2 - $$1, $$3 - $$1, $$2 + 15 + $$1, $$3 + 15 + $$1);
/*     */   }
/*     */   
/*     */   public BlockPos getLocatorPosition() {
/* 150 */     return new BlockPos((Vec3i)this.boundingBox.getCenter());
/*     */   }
/*     */   
/*     */   protected BlockPos.MutableBlockPos getWorldPos(int $$0, int $$1, int $$2) {
/* 154 */     return new BlockPos.MutableBlockPos(getWorldX($$0, $$2), getWorldY($$1), getWorldZ($$0, $$2));
/*     */   }
/*     */   
/*     */   protected int getWorldX(int $$0, int $$1) {
/* 158 */     Direction $$2 = getOrientation();
/* 159 */     if ($$2 == null) {
/* 160 */       return $$0;
/*     */     }
/*     */     
/* 163 */     switch ($$2) {
/*     */       case NORTH:
/*     */       case SOUTH:
/* 166 */         return this.boundingBox.minX() + $$0;
/*     */       case WEST:
/* 168 */         return this.boundingBox.maxX() - $$1;
/*     */       case EAST:
/* 170 */         return this.boundingBox.minX() + $$1;
/*     */     } 
/* 172 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getWorldY(int $$0) {
/* 177 */     if (getOrientation() == null) {
/* 178 */       return $$0;
/*     */     }
/* 180 */     return $$0 + this.boundingBox.minY();
/*     */   }
/*     */   
/*     */   protected int getWorldZ(int $$0, int $$1) {
/* 184 */     Direction $$2 = getOrientation();
/* 185 */     if ($$2 == null) {
/* 186 */       return $$1;
/*     */     }
/*     */     
/* 189 */     switch ($$2) {
/*     */       case NORTH:
/* 191 */         return this.boundingBox.maxZ() - $$1;
/*     */       case SOUTH:
/* 193 */         return this.boundingBox.minZ() + $$1;
/*     */       case WEST:
/*     */       case EAST:
/* 196 */         return this.boundingBox.minZ() + $$0;
/*     */     } 
/* 198 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/* 202 */   private static final Set<Block> SHAPE_CHECK_BLOCKS = (Set<Block>)ImmutableSet.builder()
/*     */     
/* 204 */     .add(Blocks.NETHER_BRICK_FENCE)
/* 205 */     .add(Blocks.TORCH)
/* 206 */     .add(Blocks.WALL_TORCH)
/* 207 */     .add(Blocks.OAK_FENCE)
/* 208 */     .add(Blocks.SPRUCE_FENCE)
/* 209 */     .add(Blocks.DARK_OAK_FENCE)
/* 210 */     .add(Blocks.ACACIA_FENCE)
/* 211 */     .add(Blocks.BIRCH_FENCE)
/* 212 */     .add(Blocks.JUNGLE_FENCE)
/* 213 */     .add(Blocks.LADDER)
/* 214 */     .add(Blocks.IRON_BARS)
/* 215 */     .build();
/*     */   
/*     */   protected void placeBlock(WorldGenLevel $$0, BlockState $$1, int $$2, int $$3, int $$4, BoundingBox $$5) {
/* 218 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$2, $$3, $$4);
/*     */     
/* 220 */     if (!$$5.isInside((Vec3i)mutableBlockPos)) {
/*     */       return;
/*     */     }
/*     */     
/* 224 */     if (!canBeReplaced((LevelReader)$$0, $$2, $$3, $$4, $$5)) {
/*     */       return;
/*     */     }
/*     */     
/* 228 */     if (this.mirror != Mirror.NONE) {
/* 229 */       $$1 = $$1.mirror(this.mirror);
/*     */     }
/* 231 */     if (this.rotation != Rotation.NONE) {
/* 232 */       $$1 = $$1.rotate(this.rotation);
/*     */     }
/*     */     
/* 235 */     $$0.setBlock((BlockPos)mutableBlockPos, $$1, 2);
/* 236 */     FluidState $$7 = $$0.getFluidState((BlockPos)mutableBlockPos);
/* 237 */     if (!$$7.isEmpty()) {
/* 238 */       $$0.scheduleTick((BlockPos)mutableBlockPos, $$7.getType(), 0);
/*     */     }
/* 240 */     if (SHAPE_CHECK_BLOCKS.contains($$1.getBlock())) {
/* 241 */       $$0.getChunk((BlockPos)mutableBlockPos).markPosForPostprocessing((BlockPos)mutableBlockPos);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean canBeReplaced(LevelReader $$0, int $$1, int $$2, int $$3, BoundingBox $$4) {
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockState getBlock(BlockGetter $$0, int $$1, int $$2, int $$3, BoundingBox $$4) {
/* 262 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$1, $$2, $$3);
/* 263 */     if (!$$4.isInside((Vec3i)mutableBlockPos)) {
/* 264 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 267 */     return $$0.getBlockState((BlockPos)mutableBlockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInterior(LevelReader $$0, int $$1, int $$2, int $$3, BoundingBox $$4) {
/* 272 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$1, $$2 + 1, $$3);
/*     */     
/* 274 */     if (!$$4.isInside((Vec3i)mutableBlockPos)) {
/* 275 */       return false;
/*     */     }
/*     */     
/* 278 */     return (mutableBlockPos.getY() < $$0.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, mutableBlockPos.getX(), mutableBlockPos.getZ()));
/*     */   }
/*     */   
/*     */   protected void generateAirBox(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/* 282 */     for (int $$8 = $$3; $$8 <= $$6; $$8++) {
/* 283 */       for (int $$9 = $$2; $$9 <= $$5; $$9++) {
/* 284 */         for (int $$10 = $$4; $$10 <= $$7; $$10++) {
/* 285 */           placeBlock($$0, Blocks.AIR.defaultBlockState(), $$9, $$8, $$10, $$1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void generateBox(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, BlockState $$8, BlockState $$9, boolean $$10) {
/* 292 */     for (int $$11 = $$3; $$11 <= $$6; $$11++) {
/* 293 */       for (int $$12 = $$2; $$12 <= $$5; $$12++) {
/* 294 */         for (int $$13 = $$4; $$13 <= $$7; $$13++) {
/* 295 */           if (!$$10 || !getBlock((BlockGetter)$$0, $$12, $$11, $$13, $$1).isAir())
/*     */           {
/*     */             
/* 298 */             if ($$11 == $$3 || $$11 == $$6 || $$12 == $$2 || $$12 == $$5 || $$13 == $$4 || $$13 == $$7) {
/* 299 */               placeBlock($$0, $$8, $$12, $$11, $$13, $$1);
/*     */             } else {
/* 301 */               placeBlock($$0, $$9, $$12, $$11, $$13, $$1);
/*     */             }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void generateBox(WorldGenLevel $$0, BoundingBox $$1, BoundingBox $$2, BlockState $$3, BlockState $$4, boolean $$5) {
/* 309 */     generateBox($$0, $$1, $$2.minX(), $$2.minY(), $$2.minZ(), $$2.maxX(), $$2.maxY(), $$2.maxZ(), $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   protected void generateBox(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, RandomSource $$9, BlockSelector $$10) {
/* 313 */     for (int $$11 = $$3; $$11 <= $$6; $$11++) {
/* 314 */       for (int $$12 = $$2; $$12 <= $$5; $$12++) {
/* 315 */         for (int $$13 = $$4; $$13 <= $$7; $$13++) {
/* 316 */           if (!$$8 || !getBlock((BlockGetter)$$0, $$12, $$11, $$13, $$1).isAir()) {
/*     */ 
/*     */             
/* 319 */             $$10.next($$9, $$12, $$11, $$13, ($$11 == $$3 || $$11 == $$6 || $$12 == $$2 || $$12 == $$5 || $$13 == $$4 || $$13 == $$7));
/* 320 */             placeBlock($$0, $$10.getNext(), $$12, $$11, $$13, $$1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected void generateBox(WorldGenLevel $$0, BoundingBox $$1, BoundingBox $$2, boolean $$3, RandomSource $$4, BlockSelector $$5) {
/* 327 */     generateBox($$0, $$1, $$2.minX(), $$2.minY(), $$2.minZ(), $$2.maxX(), $$2.maxY(), $$2.maxZ(), $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   protected void generateMaybeBox(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, float $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, BlockState $$10, BlockState $$11, boolean $$12, boolean $$13) {
/* 331 */     for (int $$14 = $$5; $$14 <= $$8; $$14++) {
/* 332 */       for (int $$15 = $$4; $$15 <= $$7; $$15++) {
/* 333 */         for (int $$16 = $$6; $$16 <= $$9; $$16++) {
/* 334 */           if ($$2.nextFloat() <= $$3)
/*     */           {
/*     */             
/* 337 */             if (!$$12 || !getBlock((BlockGetter)$$0, $$15, $$14, $$16, $$1).isAir())
/*     */             {
/*     */               
/* 340 */               if (!$$13 || isInterior((LevelReader)$$0, $$15, $$14, $$16, $$1))
/*     */               {
/*     */                 
/* 343 */                 if ($$14 == $$5 || $$14 == $$8 || $$15 == $$4 || $$15 == $$7 || $$16 == $$6 || $$16 == $$9) {
/* 344 */                   placeBlock($$0, $$10, $$15, $$14, $$16, $$1);
/*     */                 } else {
/* 346 */                   placeBlock($$0, $$11, $$15, $$14, $$16, $$1);
/*     */                 }  }  }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void maybeGenerateBlock(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, float $$3, int $$4, int $$5, int $$6, BlockState $$7) {
/* 354 */     if ($$2.nextFloat() < $$3) {
/* 355 */       placeBlock($$0, $$7, $$4, $$5, $$6, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void generateUpperHalfSphere(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, BlockState $$8, boolean $$9) {
/* 360 */     float $$10 = ($$5 - $$2 + 1);
/* 361 */     float $$11 = ($$6 - $$3 + 1);
/* 362 */     float $$12 = ($$7 - $$4 + 1);
/*     */     
/* 364 */     float $$13 = $$2 + $$10 / 2.0F;
/* 365 */     float $$14 = $$4 + $$12 / 2.0F;
/*     */     
/* 367 */     for (int $$15 = $$3; $$15 <= $$6; $$15++) {
/* 368 */       float $$16 = ($$15 - $$3) / $$11;
/*     */       
/* 370 */       for (int $$17 = $$2; $$17 <= $$5; $$17++) {
/* 371 */         float $$18 = ($$17 - $$13) / $$10 * 0.5F;
/*     */         
/* 373 */         for (int $$19 = $$4; $$19 <= $$7; $$19++) {
/* 374 */           float $$20 = ($$19 - $$14) / $$12 * 0.5F;
/*     */           
/* 376 */           if (!$$9 || !getBlock((BlockGetter)$$0, $$17, $$15, $$19, $$1).isAir()) {
/*     */ 
/*     */ 
/*     */             
/* 380 */             float $$21 = $$18 * $$18 + $$16 * $$16 + $$20 * $$20;
/*     */             
/* 382 */             if ($$21 <= 1.05F)
/* 383 */               placeBlock($$0, $$8, $$17, $$15, $$19, $$1); 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void fillColumnDown(WorldGenLevel $$0, BlockState $$1, int $$2, int $$3, int $$4, BoundingBox $$5) {
/* 391 */     BlockPos.MutableBlockPos $$6 = getWorldPos($$2, $$3, $$4);
/* 392 */     if (!$$5.isInside((Vec3i)$$6)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 397 */     while (isReplaceableByStructures($$0.getBlockState((BlockPos)$$6)) && $$6.getY() > $$0.getMinBuildHeight() + 1) {
/* 398 */       $$0.setBlock((BlockPos)$$6, $$1, 2);
/* 399 */       $$6.move(Direction.DOWN);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isReplaceableByStructures(BlockState $$0) {
/* 404 */     return ($$0.isAir() || $$0.liquid() || $$0.is(Blocks.GLOW_LICHEN) || $$0.is(Blocks.SEAGRASS) || $$0.is(Blocks.TALL_SEAGRASS));
/*     */   }
/*     */   
/*     */   protected boolean createChest(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, int $$3, int $$4, int $$5, ResourceLocation $$6) {
/* 408 */     return createChest((ServerLevelAccessor)$$0, $$1, $$2, (BlockPos)getWorldPos($$3, $$4, $$5), $$6, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockState reorient(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/* 413 */     Direction $$3 = null;
/* 414 */     for (Direction $$4 : Direction.Plane.HORIZONTAL) {
/* 415 */       BlockPos $$5 = $$1.relative($$4);
/* 416 */       BlockState $$6 = $$0.getBlockState($$5);
/* 417 */       if ($$6.is(Blocks.CHEST)) {
/* 418 */         return $$2;
/*     */       }
/* 420 */       if ($$6.isSolidRender($$0, $$5)) {
/* 421 */         if ($$3 == null) {
/* 422 */           $$3 = $$4; continue;
/*     */         } 
/* 424 */         $$3 = null;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 429 */     if ($$3 != null) {
/* 430 */       return (BlockState)$$2.setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$3.getOpposite());
/*     */     }
/*     */ 
/*     */     
/* 434 */     Direction $$7 = (Direction)$$2.getValue((Property)HorizontalDirectionalBlock.FACING);
/* 435 */     BlockPos $$8 = $$1.relative($$7);
/* 436 */     if ($$0.getBlockState($$8).isSolidRender($$0, $$8)) {
/* 437 */       $$7 = $$7.getOpposite();
/* 438 */       $$8 = $$1.relative($$7);
/*     */     } 
/* 440 */     if ($$0.getBlockState($$8).isSolidRender($$0, $$8)) {
/* 441 */       $$7 = $$7.getClockWise();
/* 442 */       $$8 = $$1.relative($$7);
/*     */     } 
/* 444 */     if ($$0.getBlockState($$8).isSolidRender($$0, $$8)) {
/* 445 */       $$7 = $$7.getOpposite();
/* 446 */       $$8 = $$1.relative($$7);
/*     */     } 
/*     */     
/* 449 */     return (BlockState)$$2.setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean createChest(ServerLevelAccessor $$0, BoundingBox $$1, RandomSource $$2, BlockPos $$3, ResourceLocation $$4, @Nullable BlockState $$5) {
/* 456 */     if (!$$1.isInside((Vec3i)$$3) || $$0.getBlockState($$3).is(Blocks.CHEST)) {
/* 457 */       return false;
/*     */     }
/*     */     
/* 460 */     if ($$5 == null) {
/* 461 */       $$5 = reorient((BlockGetter)$$0, $$3, Blocks.CHEST.defaultBlockState());
/*     */     }
/* 463 */     $$0.setBlock($$3, $$5, 2);
/*     */     
/* 465 */     BlockEntity $$6 = $$0.getBlockEntity($$3);
/* 466 */     if ($$6 instanceof ChestBlockEntity) {
/* 467 */       ((ChestBlockEntity)$$6).setLootTable($$4, $$2.nextLong());
/*     */     }
/* 469 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean createDispenser(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, int $$3, int $$4, int $$5, Direction $$6, ResourceLocation $$7) {
/* 473 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$3, $$4, $$5);
/*     */     
/* 475 */     if ($$1.isInside((Vec3i)mutableBlockPos) && 
/* 476 */       !$$0.getBlockState((BlockPos)mutableBlockPos).is(Blocks.DISPENSER)) {
/* 477 */       placeBlock($$0, (BlockState)Blocks.DISPENSER.defaultBlockState().setValue((Property)DispenserBlock.FACING, (Comparable)$$6), $$3, $$4, $$5, $$1);
/*     */       
/* 479 */       BlockEntity $$9 = $$0.getBlockEntity((BlockPos)mutableBlockPos);
/* 480 */       if ($$9 instanceof DispenserBlockEntity) {
/* 481 */         ((DispenserBlockEntity)$$9).setLootTable($$7, $$2.nextLong());
/*     */       }
/* 483 */       return true;
/*     */     } 
/*     */     
/* 486 */     return false;
/*     */   }
/*     */   
/*     */   public void move(int $$0, int $$1, int $$2) {
/* 490 */     this.boundingBox.move($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static BoundingBox createBoundingBox(Stream<StructurePiece> $$0) {
/* 494 */     Objects.requireNonNull($$0.map(StructurePiece::getBoundingBox)); return BoundingBox.encapsulatingBoxes($$0.map(StructurePiece::getBoundingBox)::iterator).<Throwable>orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox without pieces"));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static StructurePiece findCollisionPiece(List<StructurePiece> $$0, BoundingBox $$1) {
/* 499 */     for (StructurePiece $$2 : $$0) {
/* 500 */       if ($$2.getBoundingBox().intersects($$1)) {
/* 501 */         return $$2;
/*     */       }
/*     */     } 
/* 504 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Direction getOrientation() {
/* 509 */     return this.orientation;
/*     */   }
/*     */   
/*     */   public void setOrientation(@Nullable Direction $$0) {
/* 513 */     this.orientation = $$0;
/* 514 */     if ($$0 == null) {
/* 515 */       this.rotation = Rotation.NONE;
/* 516 */       this.mirror = Mirror.NONE;
/*     */     } else {
/* 518 */       switch ($$0) {
/*     */         case SOUTH:
/* 520 */           this.mirror = Mirror.LEFT_RIGHT;
/* 521 */           this.rotation = Rotation.NONE;
/*     */           return;
/*     */         case WEST:
/* 524 */           this.mirror = Mirror.LEFT_RIGHT;
/* 525 */           this.rotation = Rotation.CLOCKWISE_90;
/*     */           return;
/*     */         case EAST:
/* 528 */           this.mirror = Mirror.NONE;
/* 529 */           this.rotation = Rotation.CLOCKWISE_90;
/*     */           return;
/*     */       } 
/* 532 */       this.mirror = Mirror.NONE;
/* 533 */       this.rotation = Rotation.NONE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Rotation getRotation() {
/* 540 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public Mirror getMirror() {
/* 544 */     return this.mirror;
/*     */   }
/*     */   protected abstract void addAdditionalSaveData(StructurePieceSerializationContext paramStructurePieceSerializationContext, CompoundTag paramCompoundTag);
/*     */   public StructurePieceType getType() {
/* 548 */     return this.type;
/*     */   }
/*     */   public abstract void postProcess(WorldGenLevel paramWorldGenLevel, StructureManager paramStructureManager, ChunkGenerator paramChunkGenerator, RandomSource paramRandomSource, BoundingBox paramBoundingBox, ChunkPos paramChunkPos, BlockPos paramBlockPos);
/*     */   
/* 552 */   public static abstract class BlockSelector { protected BlockState next = Blocks.AIR.defaultBlockState();
/*     */     
/*     */     public abstract void next(RandomSource param1RandomSource, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean);
/*     */     
/*     */     public BlockState getNext() {
/* 557 */       return this.next;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructurePiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */