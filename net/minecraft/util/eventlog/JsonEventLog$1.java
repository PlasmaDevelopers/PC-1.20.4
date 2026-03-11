/*    */ package net.minecraft.util.eventlog;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
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
/*    */ class null
/*    */   implements JsonEventLogReader<T>
/*    */ {
/*    */   private volatile long position;
/*    */   
/*    */   @Nullable
/*    */   public T next() throws IOException {
/*    */     try {
/* 64 */       JsonEventLog.this.channel.position(this.position);
/* 65 */       return (T)reader.next();
/*    */     } finally {
/* 67 */       this.position = JsonEventLog.this.channel.position();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 73 */     JsonEventLog.this.releaseReference();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\eventlog\JsonEventLog$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */