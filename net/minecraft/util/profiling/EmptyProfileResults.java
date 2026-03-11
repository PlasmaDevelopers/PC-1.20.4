/*    */ package net.minecraft.util.profiling;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class EmptyProfileResults implements ProfileResults {
/*  8 */   public static final EmptyProfileResults EMPTY = new EmptyProfileResults();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ResultField> getTimes(String $$0) {
/* 15 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean saveResults(Path $$0) {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getStartTimeNano() {
/* 25 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStartTimeTicks() {
/* 30 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getEndTimeNano() {
/* 35 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEndTimeTicks() {
/* 40 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getProfilerResults() {
/* 45 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\EmptyProfileResults.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */