/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class BlockDestructionProgress implements Comparable<BlockDestructionProgress> {
/*    */   private final int id;
/*    */   private final BlockPos pos;
/*    */   private int progress;
/*    */   private int updatedRenderTick;
/*    */   
/*    */   public BlockDestructionProgress(int $$0, BlockPos $$1) {
/* 12 */     this.id = $$0;
/* 13 */     this.pos = $$1;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 17 */     return this.id;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 21 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void setProgress(int $$0) {
/* 25 */     if ($$0 > 10) {
/* 26 */       $$0 = 10;
/*    */     }
/* 28 */     this.progress = $$0;
/*    */   }
/*    */   
/*    */   public int getProgress() {
/* 32 */     return this.progress;
/*    */   }
/*    */   
/*    */   public void updateTick(int $$0) {
/* 36 */     this.updatedRenderTick = $$0;
/*    */   }
/*    */   
/*    */   public int getUpdatedRenderTick() {
/* 40 */     return this.updatedRenderTick;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 45 */     if (this == $$0) {
/* 46 */       return true;
/*    */     }
/* 48 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 49 */       return false;
/*    */     }
/* 51 */     BlockDestructionProgress $$1 = (BlockDestructionProgress)$$0;
/* 52 */     return (this.id == $$1.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 57 */     return Integer.hashCode(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(BlockDestructionProgress $$0) {
/* 62 */     if (this.progress != $$0.progress) {
/* 63 */       return Integer.compare(this.progress, $$0.progress);
/*    */     }
/* 65 */     return Integer.compare(this.id, $$0.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\BlockDestructionProgress.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */