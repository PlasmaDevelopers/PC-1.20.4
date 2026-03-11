/*    */ package com.mojang.realmsclient.gui.task;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
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
/*    */ final class ComputationResult<T>
/*    */   extends Record
/*    */ {
/*    */   private final Either<T, Exception> value;
/*    */   final long time;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #47	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult<TT;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #47	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult<TT;>;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #47	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult<TT;>;
/*    */   }
/*    */   
/*    */   ComputationResult(Either<T, Exception> $$0, long $$1) {
/* 47 */     this.value = $$0; this.time = $$1; } public Either<T, Exception> value() { return this.value; } public long time() { return this.time; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\task\DataFetcher$ComputationResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */