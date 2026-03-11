/*    */ package net.minecraft.world.level.lighting;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.DataLayer;
/*    */ 
/*    */ public interface LayerLightEventListener
/*    */   extends LightEventListener {
/*    */   @Nullable
/*    */   DataLayer getDataLayerData(SectionPos paramSectionPos);
/*    */   
/*    */   int getLightValue(BlockPos paramBlockPos);
/*    */   
/*    */   public enum DummyLightLayerEventListener
/*    */     implements LayerLightEventListener {
/* 18 */     INSTANCE;
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     public DataLayer getDataLayerData(SectionPos $$0) {
/* 23 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getLightValue(BlockPos $$0) {
/* 28 */       return 0;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void checkBlock(BlockPos $$0) {}
/*    */ 
/*    */     
/*    */     public boolean hasLightWork() {
/* 37 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public int runLightUpdates() {
/* 42 */       return 0;
/*    */     }
/*    */     
/*    */     public void updateSectionStatus(SectionPos $$0, boolean $$1) {}
/*    */     
/*    */     public void setLightEnabled(ChunkPos $$0, boolean $$1) {}
/*    */     
/*    */     public void propagateLightSources(ChunkPos $$0) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LayerLightEventListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */