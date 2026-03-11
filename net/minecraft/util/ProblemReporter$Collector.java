/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.ImmutableMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Collector
/*    */   implements ProblemReporter
/*    */ {
/*    */   private final Multimap<String, String> problems;
/*    */   private final Supplier<String> path;
/*    */   @Nullable
/*    */   private String pathCache;
/*    */   
/*    */   public Collector() {
/* 23 */     this((Multimap<String, String>)HashMultimap.create(), () -> "");
/*    */   }
/*    */   
/*    */   private Collector(Multimap<String, String> $$0, Supplier<String> $$1) {
/* 27 */     this.problems = $$0;
/* 28 */     this.path = $$1;
/*    */   }
/*    */   
/*    */   private String getPath() {
/* 32 */     if (this.pathCache == null) {
/* 33 */       this.pathCache = this.path.get();
/*    */     }
/* 35 */     return this.pathCache;
/*    */   }
/*    */ 
/*    */   
/*    */   public ProblemReporter forChild(String $$0) {
/* 40 */     return new Collector(this.problems, () -> getPath() + getPath());
/*    */   }
/*    */ 
/*    */   
/*    */   public void report(String $$0) {
/* 45 */     this.problems.put(getPath(), $$0);
/*    */   }
/*    */   
/*    */   public Multimap<String, String> get() {
/* 49 */     return (Multimap<String, String>)ImmutableMultimap.copyOf(this.problems);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ProblemReporter$Collector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */