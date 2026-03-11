/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CarvingMask
/*    */ {
/*    */   private final int minY;
/*    */   private final BitSet mask;
/*    */   private Mask additionalMask = ($$0, $$1, $$2) -> false;
/*    */   
/*    */   public CarvingMask(int $$0, int $$1) {
/* 20 */     this.minY = $$1;
/* 21 */     this.mask = new BitSet(256 * $$0);
/*    */   }
/*    */   
/*    */   public void setAdditionalMask(Mask $$0) {
/* 25 */     this.additionalMask = $$0;
/*    */   }
/*    */   
/*    */   public CarvingMask(long[] $$0, int $$1) {
/* 29 */     this.minY = $$1;
/* 30 */     this.mask = BitSet.valueOf($$0);
/*    */   }
/*    */   
/*    */   private int getIndex(int $$0, int $$1, int $$2) {
/* 34 */     return $$0 & 0xF | ($$2 & 0xF) << 4 | $$1 - this.minY << 8;
/*    */   }
/*    */   
/*    */   public void set(int $$0, int $$1, int $$2) {
/* 38 */     this.mask.set(getIndex($$0, $$1, $$2));
/*    */   }
/*    */   
/*    */   public boolean get(int $$0, int $$1, int $$2) {
/* 42 */     return (this.additionalMask.test($$0, $$1, $$2) || this.mask.get(getIndex($$0, $$1, $$2)));
/*    */   }
/*    */   
/*    */   public Stream<BlockPos> stream(ChunkPos $$0) {
/* 46 */     return this.mask.stream().mapToObj($$1 -> {
/*    */           int $$2 = $$1 & 0xF;
/*    */           int $$3 = $$1 >> 4 & 0xF;
/*    */           int $$4 = $$1 >> 8;
/*    */           return $$0.getBlockAt($$2, $$4 + this.minY, $$3);
/*    */         });
/*    */   }
/*    */   
/*    */   public long[] toArray() {
/* 55 */     return this.mask.toLongArray();
/*    */   }
/*    */   
/*    */   public static interface Mask {
/*    */     boolean test(int param1Int1, int param1Int2, int param1Int3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\CarvingMask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */