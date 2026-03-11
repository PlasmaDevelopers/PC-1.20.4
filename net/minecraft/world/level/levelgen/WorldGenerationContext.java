/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ 
/*    */ public class WorldGenerationContext {
/*    */   private final int minY;
/*    */   private final int height;
/*    */   
/*    */   public WorldGenerationContext(ChunkGenerator $$0, LevelHeightAccessor $$1) {
/* 11 */     this.minY = Math.max($$1.getMinBuildHeight(), $$0.getMinY());
/* 12 */     this.height = Math.min($$1.getHeight(), $$0.getGenDepth());
/*    */   }
/*    */   
/*    */   public int getMinGenY() {
/* 16 */     return this.minY;
/*    */   }
/*    */   
/*    */   public int getGenDepth() {
/* 20 */     return this.height;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\WorldGenerationContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */