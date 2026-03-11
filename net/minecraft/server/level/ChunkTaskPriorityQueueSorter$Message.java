/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import java.util.function.IntSupplier;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.util.thread.ProcessorHandle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Message<T>
/*    */ {
/*    */   final Function<ProcessorHandle<Unit>, T> task;
/*    */   final long pos;
/*    */   final IntSupplier level;
/*    */   
/*    */   Message(Function<ProcessorHandle<Unit>, T> $$0, long $$1, IntSupplier $$2) {
/* 46 */     this.task = $$0;
/* 47 */     this.pos = $$1;
/* 48 */     this.level = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkTaskPriorityQueueSorter$Message.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */