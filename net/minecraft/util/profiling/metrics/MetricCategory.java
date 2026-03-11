/*    */ package net.minecraft.util.profiling.metrics;
/*    */ 
/*    */ public enum MetricCategory {
/*  4 */   PATH_FINDING("pathfinding"),
/*  5 */   EVENT_LOOPS("event-loops"),
/*  6 */   MAIL_BOXES("mailboxes"),
/*  7 */   TICK_LOOP("ticking"),
/*  8 */   JVM("jvm"),
/*  9 */   CHUNK_RENDERING("chunk rendering"),
/* 10 */   CHUNK_RENDERING_DISPATCHING("chunk rendering dispatching"),
/* 11 */   CPU("cpu"),
/* 12 */   GPU("gpu");
/*    */   
/*    */   private final String description;
/*    */ 
/*    */   
/*    */   MetricCategory(String $$0) {
/* 18 */     this.description = $$0;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 22 */     return this.description;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\MetricCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */