/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.CrashReport;
/*    */ import net.minecraft.CrashReportCategory;
/*    */ import net.minecraft.ReportedException;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*    */ import net.minecraft.world.level.chunk.PalettedContainer;
/*    */ import net.minecraft.world.level.levelgen.DebugLevelSource;
/*    */ 
/*    */ class RenderChunk
/*    */ {
/*    */   private final Map<BlockPos, BlockEntity> blockEntities;
/*    */   @Nullable
/*    */   private final List<PalettedContainer<BlockState>> sections;
/*    */   private final boolean debug;
/*    */   private final LevelChunk wrapped;
/*    */   
/*    */   RenderChunk(LevelChunk $$0) {
/* 30 */     this.wrapped = $$0;
/* 31 */     this.debug = $$0.getLevel().isDebug();
/* 32 */     this.blockEntities = (Map<BlockPos, BlockEntity>)ImmutableMap.copyOf($$0.getBlockEntities());
/* 33 */     if ($$0 instanceof net.minecraft.world.level.chunk.EmptyLevelChunk) {
/* 34 */       this.sections = null;
/*    */     } else {
/* 36 */       LevelChunkSection[] $$1 = $$0.getSections();
/* 37 */       this.sections = new ArrayList<>($$1.length);
/* 38 */       for (LevelChunkSection $$2 : $$1) {
/* 39 */         this.sections.add($$2.hasOnlyAir() ? null : $$2.getStates().copy());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 46 */     return this.blockEntities.get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getBlockState(BlockPos $$0) {
/* 51 */     int $$1 = $$0.getX();
/* 52 */     int $$2 = $$0.getY();
/* 53 */     int $$3 = $$0.getZ();
/* 54 */     if (this.debug) {
/* 55 */       BlockState $$4 = null;
/* 56 */       if ($$2 == 60) {
/* 57 */         $$4 = Blocks.BARRIER.defaultBlockState();
/*    */       }
/* 59 */       if ($$2 == 70) {
/* 60 */         $$4 = DebugLevelSource.getBlockStateFor($$1, $$3);
/*    */       }
/* 62 */       return ($$4 == null) ? Blocks.AIR.defaultBlockState() : $$4;
/*    */     } 
/*    */     
/* 65 */     if (this.sections == null) {
/* 66 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/*    */     
/*    */     try {
/* 70 */       int $$5 = this.wrapped.getSectionIndex($$2);
/* 71 */       if ($$5 >= 0 && $$5 < this.sections.size()) {
/* 72 */         PalettedContainer<BlockState> $$6 = this.sections.get($$5);
/* 73 */         if ($$6 != null) {
/* 74 */           return (BlockState)$$6.get($$1 & 0xF, $$2 & 0xF, $$3 & 0xF);
/*    */         }
/*    */       } 
/* 77 */       return Blocks.AIR.defaultBlockState();
/* 78 */     } catch (Throwable $$7) {
/* 79 */       CrashReport $$8 = CrashReport.forThrowable($$7, "Getting block state");
/* 80 */       CrashReportCategory $$9 = $$8.addCategory("Block being got");
/* 81 */       $$9.setDetail("Location", () -> CrashReportCategory.formatLocation((LevelHeightAccessor)this.wrapped, $$0, $$1, $$2));
/* 82 */       throw new ReportedException($$8);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\RenderChunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */