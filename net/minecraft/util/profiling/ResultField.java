/*    */ package net.minecraft.util.profiling;
/*    */ 
/*    */ public final class ResultField implements Comparable<ResultField> {
/*    */   public final double percentage;
/*    */   public final double globalPercentage;
/*    */   public final long count;
/*    */   public final String name;
/*    */   
/*    */   public ResultField(String $$0, double $$1, double $$2, long $$3) {
/* 10 */     this.name = $$0;
/* 11 */     this.percentage = $$1;
/* 12 */     this.globalPercentage = $$2;
/* 13 */     this.count = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(ResultField $$0) {
/* 18 */     if ($$0.percentage < this.percentage) {
/* 19 */       return -1;
/*    */     }
/* 21 */     if ($$0.percentage > this.percentage) {
/* 22 */       return 1;
/*    */     }
/* 24 */     return $$0.name.compareTo(this.name);
/*    */   }
/*    */   
/*    */   public int getColor() {
/* 28 */     return (this.name.hashCode() & 0xAAAAAA) + 4473924;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\ResultField.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */