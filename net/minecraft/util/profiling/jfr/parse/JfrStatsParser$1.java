/*    */ package net.minecraft.util.profiling.jfr.parse;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.UncheckedIOException;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ import jdk.jfr.consumer.RecordedEvent;
/*    */ import jdk.jfr.consumer.RecordingFile;
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
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements Iterator<RecordedEvent>
/*    */ {
/*    */   public boolean hasNext() {
/* 66 */     return recordingFile.hasMoreEvents();
/*    */   }
/*    */ 
/*    */   
/*    */   public RecordedEvent next() {
/* 71 */     if (!hasNext()) {
/* 72 */       throw new NoSuchElementException();
/*    */     }
/*    */     try {
/* 75 */       return recordingFile.readEvent();
/* 76 */     } catch (IOException $$0) {
/* 77 */       throw new UncheckedIOException($$0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\parse\JfrStatsParser$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */