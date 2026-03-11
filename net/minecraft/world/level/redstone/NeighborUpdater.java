/*    */ package net.minecraft.world.level.redstone;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.CrashReport;
/*    */ import net.minecraft.CrashReportCategory;
/*    */ import net.minecraft.ReportedException;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public interface NeighborUpdater {
/* 18 */   public static final Direction[] UPDATE_ORDER = new Direction[] { Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH };
/*    */   
/*    */   void shapeUpdate(Direction paramDirection, BlockState paramBlockState, BlockPos paramBlockPos1, BlockPos paramBlockPos2, int paramInt1, int paramInt2);
/*    */   
/*    */   void neighborChanged(BlockPos paramBlockPos1, Block paramBlock, BlockPos paramBlockPos2);
/*    */   
/*    */   void neighborChanged(BlockState paramBlockState, BlockPos paramBlockPos1, Block paramBlock, BlockPos paramBlockPos2, boolean paramBoolean);
/*    */   
/*    */   default void updateNeighborsAtExceptFromFacing(BlockPos $$0, Block $$1, @Nullable Direction $$2) {
/* 27 */     for (Direction $$3 : UPDATE_ORDER) {
/* 28 */       if ($$3 != $$2) {
/* 29 */         neighborChanged($$0.relative($$3), $$1, $$0);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   static void executeShapeUpdate(LevelAccessor $$0, Direction $$1, BlockState $$2, BlockPos $$3, BlockPos $$4, int $$5, int $$6) {
/* 35 */     BlockState $$7 = $$0.getBlockState($$3);
/* 36 */     BlockState $$8 = $$7.updateShape($$1, $$2, $$0, $$3, $$4);
/* 37 */     Block.updateOrDestroy($$7, $$8, $$0, $$3, $$5, $$6);
/*    */   }
/*    */   
/*    */   static void executeUpdate(Level $$0, BlockState $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*    */     try {
/* 42 */       $$1.neighborChanged($$0, $$2, $$3, $$4, $$5);
/* 43 */     } catch (Throwable $$6) {
/* 44 */       CrashReport $$7 = CrashReport.forThrowable($$6, "Exception while updating neighbours");
/* 45 */       CrashReportCategory $$8 = $$7.addCategory("Block being updated");
/*    */       
/* 47 */       $$8.setDetail("Source block type", () -> {
/*    */             try {
/*    */               return String.format(Locale.ROOT, "ID #%s (%s // %s)", new Object[] { BuiltInRegistries.BLOCK.getKey($$0), $$0.getDescriptionId(), $$0.getClass().getCanonicalName() });
/* 50 */             } catch (Throwable $$1) {
/*    */               return "ID #" + BuiltInRegistries.BLOCK.getKey($$0);
/*    */             } 
/*    */           });
/*    */       
/* 55 */       CrashReportCategory.populateBlockDetails($$8, (LevelHeightAccessor)$$0, $$2, $$1);
/*    */       
/* 57 */       throw new ReportedException($$7);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\redstone\NeighborUpdater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */