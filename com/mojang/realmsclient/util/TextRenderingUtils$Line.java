/*    */ package com.mojang.realmsclient.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Line
/*    */ {
/*    */   public final List<TextRenderingUtils.LineSegment> segments;
/*    */   
/*    */   Line(TextRenderingUtils.LineSegment... $$0) {
/* 20 */     this(Arrays.asList($$0));
/*    */   }
/*    */   
/*    */   Line(List<TextRenderingUtils.LineSegment> $$0) {
/* 24 */     this.segments = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 29 */     return "Line{segments=" + this.segments + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 36 */     if (this == $$0) {
/* 37 */       return true;
/*    */     }
/* 39 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 40 */       return false;
/*    */     }
/* 42 */     Line $$1 = (Line)$$0;
/* 43 */     return Objects.equals(this.segments, $$1.segments);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     return Objects.hash(new Object[] { this.segments });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\TextRenderingUtils$Line.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */