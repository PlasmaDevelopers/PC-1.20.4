/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.google.common.collect.Streams;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.protocol.game.DebugPackets;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import org.apache.commons.lang3.mutable.MutableInt;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameTestRunner
/*    */ {
/*    */   private static final int MAX_TESTS_PER_BATCH = 50;
/*    */   public static final int SPACE_BETWEEN_COLUMNS = 5;
/*    */   public static final int SPACE_BETWEEN_ROWS = 6;
/*    */   public static final int DEFAULT_TESTS_PER_ROW = 8;
/*    */   
/*    */   public static void runTest(GameTestInfo $$0, BlockPos $$1, GameTestTicker $$2) {
/* 36 */     $$2.add($$0);
/* 37 */     $$0.addListener(new ReportGameListener($$0, $$2, $$1));
/* 38 */     $$0.prepareTestStructure($$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Collection<GameTestInfo> runTestBatches(Collection<GameTestBatch> $$0, BlockPos $$1, Rotation $$2, ServerLevel $$3, GameTestTicker $$4, int $$5) {
/* 47 */     GameTestBatchRunner $$6 = new GameTestBatchRunner($$0, $$1, $$2, $$3, $$4, $$5);
/* 48 */     $$6.start();
/* 49 */     return $$6.getTestInfos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Collection<GameTestInfo> runTests(Collection<TestFunction> $$0, BlockPos $$1, Rotation $$2, ServerLevel $$3, GameTestTicker $$4, int $$5) {
/* 56 */     return runTestBatches(groupTestsIntoBatches($$0), $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   public static Collection<GameTestBatch> groupTestsIntoBatches(Collection<TestFunction> $$0) {
/* 60 */     Map<String, List<TestFunction>> $$1 = (Map<String, List<TestFunction>>)$$0.stream().collect(Collectors.groupingBy(TestFunction::getBatchName, java.util.LinkedHashMap::new, Collectors.toList()));
/*    */     
/* 62 */     return (Collection<GameTestBatch>)$$1.entrySet().stream().flatMap($$0 -> {
/*    */           String $$1 = (String)$$0.getKey();
/*    */           
/*    */           Consumer<ServerLevel> $$2 = GameTestRegistry.getBeforeBatchFunction($$1);
/*    */           Consumer<ServerLevel> $$3 = GameTestRegistry.getAfterBatchFunction($$1);
/*    */           MutableInt $$4 = new MutableInt();
/*    */           Collection<TestFunction> $$5 = (Collection<TestFunction>)$$0.getValue();
/*    */           return Streams.stream(Iterables.partition($$5, 50)).map(());
/* 70 */         }).collect(ImmutableList.toImmutableList());
/*    */   }
/*    */   
/*    */   public static void clearAllTests(ServerLevel $$0, BlockPos $$1, GameTestTicker $$2, int $$3) {
/* 74 */     $$2.clear();
/* 75 */     BlockPos $$4 = $$1.offset(-$$3, 0, -$$3);
/* 76 */     BlockPos $$5 = $$1.offset($$3, 0, $$3);
/* 77 */     BlockPos.betweenClosedStream($$4, $$5)
/* 78 */       .filter($$1 -> $$0.getBlockState($$1).is(Blocks.STRUCTURE_BLOCK))
/* 79 */       .forEach($$1 -> {
/*    */           StructureBlockEntity $$2 = (StructureBlockEntity)$$0.getBlockEntity($$1);
/*    */           BoundingBox $$3 = StructureUtils.getStructureBoundingBox($$2);
/*    */           StructureUtils.clearSpaceForStructure($$3, $$0);
/*    */         });
/*    */   }
/*    */   
/*    */   public static void clearMarkers(ServerLevel $$0) {
/* 87 */     DebugPackets.sendGameTestClearPacket($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestRunner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */