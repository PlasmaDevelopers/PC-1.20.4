/*     */ package net.minecraft.gametest.framework;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.LongArraySet;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GameTestBatchRunner {
/*  22 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final BlockPos firstTestNorthWestCorner;
/*     */   
/*     */   final ServerLevel level;
/*     */   
/*     */   private final GameTestTicker testTicker;
/*     */   private final int testsPerRow;
/*     */   private final List<GameTestInfo> allTestInfos;
/*     */   private final List<Pair<GameTestBatch, Collection<GameTestInfo>>> batches;
/*     */   private int count;
/*     */   private AABB rowBounds;
/*     */   private final BlockPos.MutableBlockPos nextTestNorthWestCorner;
/*     */   
/*     */   public GameTestBatchRunner(Collection<GameTestBatch> $$0, BlockPos $$1, Rotation $$2, ServerLevel $$3, GameTestTicker $$4, int $$5) {
/*  37 */     this.nextTestNorthWestCorner = $$1.mutable();
/*  38 */     this.rowBounds = new AABB((BlockPos)this.nextTestNorthWestCorner);
/*  39 */     this.firstTestNorthWestCorner = $$1;
/*  40 */     this.level = $$3;
/*  41 */     this.testTicker = $$4;
/*  42 */     this.testsPerRow = $$5;
/*     */     
/*  44 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  49 */       .batches = (List<Pair<GameTestBatch, Collection<GameTestInfo>>>)$$0.stream().map($$2 -> { Collection<GameTestInfo> $$3 = (Collection<GameTestInfo>)$$2.getTestFunctions().stream().map(()).collect(ImmutableList.toImmutableList()); return Pair.of($$2, $$3); }).collect(ImmutableList.toImmutableList());
/*     */     
/*  51 */     this.allTestInfos = (List<GameTestInfo>)this.batches.stream().flatMap($$0 -> ((Collection)$$0.getSecond()).stream()).collect(ImmutableList.toImmutableList());
/*     */   }
/*     */   
/*     */   public List<GameTestInfo> getTestInfos() {
/*  55 */     return this.allTestInfos;
/*     */   }
/*     */   
/*     */   public void start() {
/*  59 */     runBatch(0);
/*     */   }
/*     */   
/*     */   void runBatch(final int batchIndex) {
/*  63 */     if (batchIndex >= this.batches.size()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  68 */     Pair<GameTestBatch, Collection<GameTestInfo>> $$1 = this.batches.get(batchIndex);
/*  69 */     final GameTestBatch currentBatch = (GameTestBatch)$$1.getFirst();
/*  70 */     Collection<GameTestInfo> $$3 = (Collection<GameTestInfo>)$$1.getSecond();
/*  71 */     Map<GameTestInfo, BlockPos> $$4 = createStructuresForBatch($$3);
/*     */     
/*  73 */     String $$5 = $$2.getName();
/*     */     
/*  75 */     LOGGER.info("Running test batch '{}' ({} tests)...", $$5, Integer.valueOf($$3.size()));
/*  76 */     $$2.runBeforeBatchFunction(this.level);
/*  77 */     final MultipleTestTracker currentBatchTracker = new MultipleTestTracker();
/*  78 */     Objects.requireNonNull($$6); $$3.forEach($$6::addTestToTrack);
/*  79 */     $$6.addListener(new GameTestListener() {
/*     */           private void testCompleted() {
/*  81 */             if (currentBatchTracker.isDone()) {
/*  82 */               currentBatch.runAfterBatchFunction(GameTestBatchRunner.this.level);
/*  83 */               LongArraySet longArraySet = new LongArraySet(GameTestBatchRunner.this.level.getForcedChunks());
/*  84 */               longArraySet.forEach($$0 -> GameTestBatchRunner.this.level.setChunkForced(ChunkPos.getX($$0), ChunkPos.getZ($$0), false));
/*  85 */               GameTestBatchRunner.this.runBatch(batchIndex + 1);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void testStructureLoaded(GameTestInfo $$0) {}
/*     */ 
/*     */           
/*     */           public void testPassed(GameTestInfo $$0) {
/*  95 */             testCompleted();
/*     */           }
/*     */ 
/*     */           
/*     */           public void testFailed(GameTestInfo $$0) {
/* 100 */             testCompleted();
/*     */           }
/*     */         });
/*     */     
/* 104 */     $$3.forEach($$1 -> {
/*     */           final BlockPos currentBatch = (BlockPos)$$0.get($$1);
/*     */           GameTestRunner.runTest($$1, $$2, this.testTicker);
/*     */         });
/*     */   }
/*     */   
/*     */   private Map<GameTestInfo, BlockPos> createStructuresForBatch(Collection<GameTestInfo> $$0) {
/* 111 */     Map<GameTestInfo, BlockPos> $$1 = Maps.newHashMap();
/*     */     
/* 113 */     for (GameTestInfo $$2 : $$0) {
/* 114 */       BlockPos $$3 = new BlockPos((Vec3i)this.nextTestNorthWestCorner);
/* 115 */       StructureBlockEntity $$4 = StructureUtils.prepareTestStructure($$2, $$3, $$2.getRotation(), this.level);
/* 116 */       AABB $$5 = StructureUtils.getStructureBounds($$4);
/* 117 */       $$2.setStructureBlockPos($$4.getBlockPos());
/* 118 */       $$1.put($$2, new BlockPos((Vec3i)this.nextTestNorthWestCorner));
/* 119 */       this.rowBounds = this.rowBounds.minmax($$5);
/*     */       
/* 121 */       this.nextTestNorthWestCorner.move((int)$$5.getXsize() + 5, 0, 0);
/*     */       
/* 123 */       if (this.count++ % this.testsPerRow == this.testsPerRow - 1) {
/*     */         
/* 125 */         this.nextTestNorthWestCorner.move(0, 0, (int)this.rowBounds.getZsize() + 6);
/* 126 */         this.nextTestNorthWestCorner.setX(this.firstTestNorthWestCorner.getX());
/* 127 */         this.rowBounds = new AABB((BlockPos)this.nextTestNorthWestCorner);
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestBatchRunner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */