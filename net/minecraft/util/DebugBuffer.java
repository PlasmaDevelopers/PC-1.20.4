/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*    */ 
/*    */ public class DebugBuffer<T>
/*    */ {
/*    */   private final AtomicReferenceArray<T> data;
/*    */   private final AtomicInteger index;
/*    */   
/*    */   public DebugBuffer(int $$0) {
/* 14 */     this.data = new AtomicReferenceArray<>($$0);
/* 15 */     this.index = new AtomicInteger(0);
/*    */   }
/*    */   
/*    */   public void push(T $$0) {
/* 19 */     int $$2, $$3, $$1 = this.data.length();
/*    */ 
/*    */     
/*    */     do {
/* 23 */       $$2 = this.index.get();
/* 24 */       $$3 = ($$2 + 1) % $$1;
/* 25 */     } while (!this.index.compareAndSet($$2, $$3));
/*    */     
/* 27 */     this.data.set($$3, $$0);
/*    */   }
/*    */   
/*    */   public List<T> dump() {
/* 31 */     int $$0 = this.index.get();
/* 32 */     ImmutableList.Builder<T> $$1 = ImmutableList.builder();
/* 33 */     for (int $$2 = 0; $$2 < this.data.length(); $$2++) {
/* 34 */       int $$3 = Math.floorMod($$0 - $$2, this.data.length());
/* 35 */       T $$4 = this.data.get($$3);
/* 36 */       if ($$4 != null) {
/* 37 */         $$1.add($$4);
/*    */       }
/*    */     } 
/* 40 */     return (List<T>)$$1.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\DebugBuffer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */