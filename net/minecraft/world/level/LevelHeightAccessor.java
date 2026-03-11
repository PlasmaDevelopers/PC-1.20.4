/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface LevelHeightAccessor
/*    */ {
/*    */   int getHeight();
/*    */   
/*    */   int getMinBuildHeight();
/*    */   
/*    */   default int getMaxBuildHeight() {
/* 18 */     return getMinBuildHeight() + getHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   default int getSectionsCount() {
/* 23 */     return getMaxSection() - getMinSection();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default int getMinSection() {
/* 29 */     return SectionPos.blockToSectionCoord(getMinBuildHeight());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int getMaxSection() {
/* 36 */     return SectionPos.blockToSectionCoord(getMaxBuildHeight() - 1) + 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean isOutsideBuildHeight(BlockPos $$0) {
/* 41 */     return isOutsideBuildHeight($$0.getY());
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean isOutsideBuildHeight(int $$0) {
/* 46 */     return ($$0 < getMinBuildHeight() || $$0 >= getMaxBuildHeight());
/*    */   }
/*    */ 
/*    */   
/*    */   default int getSectionIndex(int $$0) {
/* 51 */     return getSectionIndexFromSectionY(SectionPos.blockToSectionCoord($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   default int getSectionIndexFromSectionY(int $$0) {
/* 56 */     return $$0 - getMinSection();
/*    */   }
/*    */ 
/*    */   
/*    */   default int getSectionYFromSectionIndex(int $$0) {
/* 61 */     return $$0 + getMinSection();
/*    */   }
/*    */   
/*    */   static LevelHeightAccessor create(final int minBuildHeight, final int height) {
/* 65 */     return new LevelHeightAccessor()
/*    */       {
/*    */         public int getHeight() {
/* 68 */           return height;
/*    */         }
/*    */ 
/*    */         
/*    */         public int getMinBuildHeight() {
/* 73 */           return minBuildHeight;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LevelHeightAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */