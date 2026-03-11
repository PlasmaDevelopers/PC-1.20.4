/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ 
/*    */ 
/*    */ public class NbtAccounter
/*    */ {
/*    */   private static final int MAX_STACK_DEPTH = 512;
/*    */   private final long quota;
/*    */   private long usage;
/*    */   private final int maxDepth;
/*    */   private int depth;
/*    */   
/*    */   public NbtAccounter(long $$0, int $$1) {
/* 15 */     this.quota = $$0;
/* 16 */     this.maxDepth = $$1;
/*    */   }
/*    */   
/*    */   public static NbtAccounter create(long $$0) {
/* 20 */     return new NbtAccounter($$0, 512);
/*    */   }
/*    */   
/*    */   public static NbtAccounter unlimitedHeap() {
/* 24 */     return new NbtAccounter(Long.MAX_VALUE, 512);
/*    */   }
/*    */   
/*    */   public void accountBytes(long $$0, long $$1) {
/* 28 */     accountBytes($$0 * $$1);
/*    */   }
/*    */   
/*    */   public void accountBytes(long $$0) {
/* 32 */     if (this.usage + $$0 > this.quota) {
/* 33 */       throw new NbtAccounterException("Tried to read NBT tag that was too big; tried to allocate: " + this.usage + " + " + $$0 + " bytes where max allowed: " + this.quota);
/*    */     }
/* 35 */     this.usage += $$0;
/*    */   }
/*    */   
/*    */   public void pushDepth() {
/* 39 */     if (this.depth >= this.maxDepth) {
/* 40 */       throw new NbtAccounterException("Tried to read NBT tag with too high complexity, depth > " + this.maxDepth);
/*    */     }
/* 42 */     this.depth++;
/*    */   }
/*    */   
/*    */   public void popDepth() {
/* 46 */     if (this.depth <= 0) {
/* 47 */       throw new NbtAccounterException("NBT-Accounter tried to pop stack-depth at top-level");
/*    */     }
/* 49 */     this.depth--;
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   public long getUsage() {
/* 54 */     return this.usage;
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   public int getDepth() {
/* 59 */     return this.depth;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\NbtAccounter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */