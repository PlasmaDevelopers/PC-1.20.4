/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.DataLayer;
/*     */ import net.minecraft.world.level.chunk.LightChunk;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LightEngine<M extends DataLayerStorageMap<M>, S extends LayerLightSectionStorage<M>>
/*     */   implements LayerLightEventListener
/*     */ {
/*     */   public static final int MAX_LEVEL = 15;
/*     */   protected static final int MIN_OPACITY = 1;
/*  30 */   protected static final long PULL_LIGHT_IN_ENTRY = QueueEntry.decreaseAllDirections(1);
/*     */   
/*     */   private static final int MIN_QUEUE_SIZE = 512;
/*     */   
/*  34 */   protected static final Direction[] PROPAGATION_DIRECTIONS = Direction.values();
/*     */   
/*     */   protected final LightChunkGetter chunkSource;
/*     */   
/*     */   protected final S storage;
/*  39 */   private final LongOpenHashSet blockNodesToCheck = new LongOpenHashSet(512, 0.5F);
/*  40 */   private final LongArrayFIFOQueue decreaseQueue = new LongArrayFIFOQueue();
/*  41 */   private final LongArrayFIFOQueue increaseQueue = new LongArrayFIFOQueue();
/*     */   
/*  43 */   private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/*     */   
/*     */   private static final int CACHE_SIZE = 2;
/*  46 */   private final long[] lastChunkPos = new long[2];
/*  47 */   private final LightChunk[] lastChunk = new LightChunk[2];
/*     */   
/*     */   protected LightEngine(LightChunkGetter $$0, S $$1) {
/*  50 */     this.chunkSource = $$0;
/*  51 */     this.storage = $$1;
/*  52 */     clearChunkCache();
/*     */   }
/*     */   
/*     */   public static boolean hasDifferentLightProperties(BlockGetter $$0, BlockPos $$1, BlockState $$2, BlockState $$3) {
/*  56 */     if ($$3 == $$2) {
/*  57 */       return false;
/*     */     }
/*  59 */     return ($$3.getLightBlock($$0, $$1) != $$2.getLightBlock($$0, $$1) || $$3
/*  60 */       .getLightEmission() != $$2.getLightEmission() || $$3
/*  61 */       .useShapeForLightOcclusion() || $$2
/*  62 */       .useShapeForLightOcclusion());
/*     */   }
/*     */   
/*     */   public static int getLightBlockInto(BlockGetter $$0, BlockState $$1, BlockPos $$2, BlockState $$3, BlockPos $$4, Direction $$5, int $$6) {
/*  66 */     boolean $$7 = isEmptyShape($$1);
/*  67 */     boolean $$8 = isEmptyShape($$3);
/*     */     
/*  69 */     if ($$7 && $$8) {
/*  70 */       return $$6;
/*     */     }
/*     */     
/*  73 */     VoxelShape $$9 = $$7 ? Shapes.empty() : $$1.getOcclusionShape($$0, $$2);
/*  74 */     VoxelShape $$10 = $$8 ? Shapes.empty() : $$3.getOcclusionShape($$0, $$4);
/*     */     
/*  76 */     if (Shapes.mergedFaceOccludes($$9, $$10, $$5)) {
/*  77 */       return 16;
/*     */     }
/*     */     
/*  80 */     return $$6;
/*     */   }
/*     */   
/*     */   public static VoxelShape getOcclusionShape(BlockGetter $$0, BlockPos $$1, BlockState $$2, Direction $$3) {
/*  84 */     return isEmptyShape($$2) ? Shapes.empty() : $$2.getFaceOcclusionShape($$0, $$1, $$3);
/*     */   }
/*     */   
/*     */   protected static boolean isEmptyShape(BlockState $$0) {
/*  88 */     return (!$$0.canOcclude() || !$$0.useShapeForLightOcclusion());
/*     */   }
/*     */   
/*     */   protected BlockState getState(BlockPos $$0) {
/*  92 */     int $$1 = SectionPos.blockToSectionCoord($$0.getX());
/*  93 */     int $$2 = SectionPos.blockToSectionCoord($$0.getZ());
/*  94 */     LightChunk $$3 = getChunk($$1, $$2);
/*  95 */     if ($$3 == null)
/*     */     {
/*     */ 
/*     */       
/*  99 */       return Blocks.BEDROCK.defaultBlockState();
/*     */     }
/* 101 */     return $$3.getBlockState($$0);
/*     */   }
/*     */   
/*     */   protected int getOpacity(BlockState $$0, BlockPos $$1) {
/* 105 */     return Math.max(1, $$0.getLightBlock(this.chunkSource.getLevel(), $$1));
/*     */   }
/*     */   
/*     */   protected boolean shapeOccludes(long $$0, BlockState $$1, long $$2, BlockState $$3, Direction $$4) {
/* 109 */     VoxelShape $$5 = getOcclusionShape($$1, $$0, $$4);
/* 110 */     VoxelShape $$6 = getOcclusionShape($$3, $$2, $$4.getOpposite());
/* 111 */     return Shapes.faceShapeOccludes($$5, $$6);
/*     */   }
/*     */   
/*     */   protected VoxelShape getOcclusionShape(BlockState $$0, long $$1, Direction $$2) {
/* 115 */     return getOcclusionShape(this.chunkSource.getLevel(), (BlockPos)this.mutablePos.set($$1), $$0, $$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected LightChunk getChunk(int $$0, int $$1) {
/* 120 */     long $$2 = ChunkPos.asLong($$0, $$1);
/* 121 */     for (int $$3 = 0; $$3 < 2; $$3++) {
/* 122 */       if ($$2 == this.lastChunkPos[$$3]) {
/* 123 */         return this.lastChunk[$$3];
/*     */       }
/*     */     } 
/* 126 */     LightChunk $$4 = this.chunkSource.getChunkForLighting($$0, $$1);
/* 127 */     for (int $$5 = 1; $$5 > 0; $$5--) {
/* 128 */       this.lastChunkPos[$$5] = this.lastChunkPos[$$5 - 1];
/* 129 */       this.lastChunk[$$5] = this.lastChunk[$$5 - 1];
/*     */     } 
/* 131 */     this.lastChunkPos[0] = $$2;
/* 132 */     this.lastChunk[0] = $$4;
/* 133 */     return $$4;
/*     */   }
/*     */   
/*     */   private void clearChunkCache() {
/* 137 */     Arrays.fill(this.lastChunkPos, ChunkPos.INVALID_CHUNK_POS);
/* 138 */     Arrays.fill((Object[])this.lastChunk, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkBlock(BlockPos $$0) {
/* 143 */     this.blockNodesToCheck.add($$0.asLong());
/*     */   }
/*     */   
/*     */   public void queueSectionData(long $$0, @Nullable DataLayer $$1) {
/* 147 */     this.storage.queueSectionData($$0, $$1);
/*     */   }
/*     */   
/*     */   public void retainData(ChunkPos $$0, boolean $$1) {
/* 151 */     this.storage.retainData(SectionPos.getZeroNode($$0.x, $$0.z), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSectionStatus(SectionPos $$0, boolean $$1) {
/* 156 */     this.storage.updateSectionStatus($$0.asLong(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLightEnabled(ChunkPos $$0, boolean $$1) {
/* 161 */     this.storage.setLightEnabled(SectionPos.getZeroNode($$0.x, $$0.z), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int runLightUpdates() {
/* 166 */     LongIterator $$0 = this.blockNodesToCheck.iterator();
/* 167 */     while ($$0.hasNext()) {
/* 168 */       checkNode($$0.nextLong());
/*     */     }
/* 170 */     this.blockNodesToCheck.clear();
/* 171 */     this.blockNodesToCheck.trim(512);
/*     */     
/* 173 */     int $$1 = 0;
/* 174 */     $$1 += propagateDecreases();
/* 175 */     $$1 += propagateIncreases();
/*     */     
/* 177 */     clearChunkCache();
/*     */     
/* 179 */     this.storage.markNewInconsistencies(this);
/* 180 */     this.storage.swapSectionMap();
/*     */     
/* 182 */     return $$1;
/*     */   }
/*     */   
/*     */   private int propagateIncreases() {
/* 186 */     int $$0 = 0;
/* 187 */     while (!this.increaseQueue.isEmpty()) {
/* 188 */       long $$1 = this.increaseQueue.dequeueLong();
/* 189 */       long $$2 = this.increaseQueue.dequeueLong();
/*     */       
/* 191 */       int $$3 = this.storage.getStoredLevel($$1);
/*     */       
/* 193 */       int $$4 = QueueEntry.getFromLevel($$2);
/* 194 */       if (QueueEntry.isIncreaseFromEmission($$2) && $$3 < $$4) {
/* 195 */         this.storage.setStoredLevel($$1, $$4);
/* 196 */         $$3 = $$4;
/*     */       } 
/* 198 */       if ($$3 == $$4) {
/* 199 */         propagateIncrease($$1, $$2, $$3);
/*     */       }
/*     */       
/* 202 */       $$0++;
/*     */     } 
/* 204 */     return $$0;
/*     */   }
/*     */   
/*     */   private int propagateDecreases() {
/* 208 */     int $$0 = 0;
/* 209 */     while (!this.decreaseQueue.isEmpty()) {
/* 210 */       long $$1 = this.decreaseQueue.dequeueLong();
/* 211 */       long $$2 = this.decreaseQueue.dequeueLong();
/* 212 */       propagateDecrease($$1, $$2);
/* 213 */       $$0++;
/*     */     } 
/* 215 */     return $$0;
/*     */   }
/*     */   
/*     */   protected void enqueueDecrease(long $$0, long $$1) {
/* 219 */     this.decreaseQueue.enqueue($$0);
/* 220 */     this.decreaseQueue.enqueue($$1);
/*     */   }
/*     */   
/*     */   protected void enqueueIncrease(long $$0, long $$1) {
/* 224 */     this.increaseQueue.enqueue($$0);
/* 225 */     this.increaseQueue.enqueue($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasLightWork() {
/* 230 */     return (this.storage.hasInconsistencies() || !this.blockNodesToCheck.isEmpty() || !this.decreaseQueue.isEmpty() || !this.increaseQueue.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DataLayer getDataLayerData(SectionPos $$0) {
/* 236 */     return this.storage.getDataLayerData($$0.asLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightValue(BlockPos $$0) {
/* 241 */     return this.storage.getLightValue($$0.asLong());
/*     */   }
/*     */   
/*     */   public String getDebugData(long $$0) {
/* 245 */     return getDebugSectionType($$0).display();
/*     */   }
/*     */   
/*     */   public LayerLightSectionStorage.SectionType getDebugSectionType(long $$0) {
/* 249 */     return this.storage.getDebugSectionType($$0);
/*     */   }
/*     */   
/*     */   protected abstract void checkNode(long paramLong);
/*     */   
/*     */   protected abstract void propagateIncrease(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   protected abstract void propagateDecrease(long paramLong1, long paramLong2);
/*     */   
/*     */   public static class QueueEntry {
/*     */     private static final int FROM_LEVEL_BITS = 4;
/*     */     private static final int DIRECTION_BITS = 6;
/*     */     private static final long LEVEL_MASK = 15L;
/*     */     private static final long DIRECTIONS_MASK = 1008L;
/*     */     private static final long FLAG_FROM_EMPTY_SHAPE = 1024L;
/*     */     private static final long FLAG_INCREASE_FROM_EMISSION = 2048L;
/*     */     
/*     */     public static long decreaseSkipOneDirection(int $$0, Direction $$1) {
/* 267 */       long $$2 = withoutDirection(1008L, $$1);
/* 268 */       return withLevel($$2, $$0);
/*     */     }
/*     */     
/*     */     public static long decreaseAllDirections(int $$0) {
/* 272 */       return withLevel(1008L, $$0);
/*     */     }
/*     */     
/*     */     public static long increaseLightFromEmission(int $$0, boolean $$1) {
/* 276 */       long $$2 = 1008L;
/* 277 */       $$2 |= 0x800L;
/* 278 */       if ($$1) {
/* 279 */         $$2 |= 0x400L;
/*     */       }
/* 281 */       return withLevel($$2, $$0);
/*     */     }
/*     */     
/*     */     public static long increaseSkipOneDirection(int $$0, boolean $$1, Direction $$2) {
/* 285 */       long $$3 = withoutDirection(1008L, $$2);
/* 286 */       if ($$1) {
/* 287 */         $$3 |= 0x400L;
/*     */       }
/* 289 */       return withLevel($$3, $$0);
/*     */     }
/*     */     
/*     */     public static long increaseOnlyOneDirection(int $$0, boolean $$1, Direction $$2) {
/* 293 */       long $$3 = 0L;
/* 294 */       if ($$1) {
/* 295 */         $$3 |= 0x400L;
/*     */       }
/* 297 */       $$3 = withDirection($$3, $$2);
/* 298 */       return withLevel($$3, $$0);
/*     */     }
/*     */     
/*     */     public static long increaseSkySourceInDirections(boolean $$0, boolean $$1, boolean $$2, boolean $$3, boolean $$4) {
/* 302 */       long $$5 = withLevel(0L, 15);
/* 303 */       if ($$0) {
/* 304 */         $$5 = withDirection($$5, Direction.DOWN);
/*     */       }
/* 306 */       if ($$1) {
/* 307 */         $$5 = withDirection($$5, Direction.NORTH);
/*     */       }
/* 309 */       if ($$2) {
/* 310 */         $$5 = withDirection($$5, Direction.SOUTH);
/*     */       }
/* 312 */       if ($$3) {
/* 313 */         $$5 = withDirection($$5, Direction.WEST);
/*     */       }
/* 315 */       if ($$4) {
/* 316 */         $$5 = withDirection($$5, Direction.EAST);
/*     */       }
/* 318 */       return $$5;
/*     */     }
/*     */     
/*     */     public static int getFromLevel(long $$0) {
/* 322 */       return (int)($$0 & 0xFL);
/*     */     }
/*     */     
/*     */     public static boolean isFromEmptyShape(long $$0) {
/* 326 */       return (($$0 & 0x400L) != 0L);
/*     */     }
/*     */     
/*     */     public static boolean isIncreaseFromEmission(long $$0) {
/* 330 */       return (($$0 & 0x800L) != 0L);
/*     */     }
/*     */     
/*     */     public static boolean shouldPropagateInDirection(long $$0, Direction $$1) {
/* 334 */       return (($$0 & 1L << $$1.ordinal() + 4) != 0L);
/*     */     }
/*     */     
/*     */     private static long withLevel(long $$0, int $$1) {
/* 338 */       return $$0 & 0xFFFFFFFFFFFFFFF0L | $$1 & 0xFL;
/*     */     }
/*     */     
/*     */     private static long withDirection(long $$0, Direction $$1) {
/* 342 */       return $$0 | 1L << $$1.ordinal() + 4;
/*     */     }
/*     */     
/*     */     private static long withoutDirection(long $$0, Direction $$1) {
/* 346 */       return $$0 & (1L << $$1.ordinal() + 4 ^ 0xFFFFFFFFFFFFFFFFL);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LightEngine.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */