/*     */ package com.mojang.realmsclient.util;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextRenderingUtils
/*     */ {
/*     */   public static class Line
/*     */   {
/*     */     public final List<TextRenderingUtils.LineSegment> segments;
/*     */     
/*     */     Line(TextRenderingUtils.LineSegment... $$0) {
/*  20 */       this(Arrays.asList($$0));
/*     */     }
/*     */     
/*     */     Line(List<TextRenderingUtils.LineSegment> $$0) {
/*  24 */       this.segments = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  29 */       return "Line{segments=" + this.segments + "}";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/*  36 */       if (this == $$0) {
/*  37 */         return true;
/*     */       }
/*  39 */       if ($$0 == null || getClass() != $$0.getClass()) {
/*  40 */         return false;
/*     */       }
/*  42 */       Line $$1 = (Line)$$0;
/*  43 */       return Objects.equals(this.segments, $$1.segments);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  48 */       return Objects.hash(new Object[] { this.segments });
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LineSegment {
/*     */     private final String fullText;
/*     */     @Nullable
/*     */     private final String linkTitle;
/*     */     @Nullable
/*     */     private final String linkUrl;
/*     */     
/*     */     private LineSegment(String $$0) {
/*  60 */       this.fullText = $$0;
/*  61 */       this.linkTitle = null;
/*  62 */       this.linkUrl = null;
/*     */     }
/*     */     
/*     */     private LineSegment(String $$0, @Nullable String $$1, @Nullable String $$2) {
/*  66 */       this.fullText = $$0;
/*  67 */       this.linkTitle = $$1;
/*  68 */       this.linkUrl = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/*  73 */       if (this == $$0) {
/*  74 */         return true;
/*     */       }
/*  76 */       if ($$0 == null || getClass() != $$0.getClass()) {
/*  77 */         return false;
/*     */       }
/*  79 */       LineSegment $$1 = (LineSegment)$$0;
/*  80 */       return (Objects.equals(this.fullText, $$1.fullText) && 
/*  81 */         Objects.equals(this.linkTitle, $$1.linkTitle) && 
/*  82 */         Objects.equals(this.linkUrl, $$1.linkUrl));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  87 */       return Objects.hash(new Object[] { this.fullText, this.linkTitle, this.linkUrl });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  92 */       return "Segment{fullText='" + this.fullText + "', linkTitle='" + this.linkTitle + "', linkUrl='" + this.linkUrl + "'}";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String renderedText() {
/* 100 */       return isLink() ? this.linkTitle : this.fullText;
/*     */     }
/*     */     
/*     */     public boolean isLink() {
/* 104 */       return (this.linkTitle != null);
/*     */     }
/*     */     
/*     */     public String getLinkUrl() {
/* 108 */       if (!isLink()) {
/* 109 */         throw new IllegalStateException("Not a link: " + this);
/*     */       }
/* 111 */       return this.linkUrl;
/*     */     }
/*     */     
/*     */     public static LineSegment link(String $$0, String $$1) {
/* 115 */       return new LineSegment(null, $$0, $$1);
/*     */     }
/*     */     
/*     */     @VisibleForTesting
/*     */     protected static LineSegment text(String $$0) {
/* 120 */       return new LineSegment($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static List<String> lineBreak(String $$0) {
/* 126 */     return Arrays.asList($$0.split("\\n"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Line> decompose(String $$0, LineSegment... $$1) {
/* 134 */     return decompose($$0, Arrays.asList($$1));
/*     */   }
/*     */   
/*     */   private static List<Line> decompose(String $$0, List<LineSegment> $$1) {
/* 138 */     List<String> $$2 = lineBreak($$0);
/* 139 */     return insertLinks($$2, $$1);
/*     */   }
/*     */   
/*     */   private static List<Line> insertLinks(List<String> $$0, List<LineSegment> $$1) {
/* 143 */     int $$2 = 0;
/* 144 */     List<Line> $$3 = Lists.newArrayList();
/* 145 */     for (String $$4 : $$0) {
/* 146 */       List<LineSegment> $$5 = Lists.newArrayList();
/* 147 */       List<String> $$6 = split($$4, "%link");
/* 148 */       for (String $$7 : $$6) {
/* 149 */         if ("%link".equals($$7)) {
/* 150 */           $$5.add($$1.get($$2++)); continue;
/*     */         } 
/* 152 */         $$5.add(LineSegment.text($$7));
/*     */       } 
/*     */       
/* 155 */       $$3.add(new Line($$5));
/*     */     } 
/* 157 */     return $$3;
/*     */   }
/*     */   
/*     */   public static List<String> split(String $$0, String $$1) {
/* 161 */     if ($$1.isEmpty()) {
/* 162 */       throw new IllegalArgumentException("Delimiter cannot be the empty string");
/*     */     }
/* 164 */     List<String> $$2 = Lists.newArrayList();
/* 165 */     int $$3 = 0; int $$4;
/* 166 */     while (($$4 = $$0.indexOf($$1, $$3)) != -1) {
/* 167 */       if ($$4 > $$3) {
/* 168 */         $$2.add($$0.substring($$3, $$4));
/*     */       }
/* 170 */       $$2.add($$1);
/* 171 */       $$3 = $$4 + $$1.length();
/*     */     } 
/* 173 */     if ($$3 < $$0.length()) {
/* 174 */       $$2.add($$0.substring($$3));
/*     */     }
/* 176 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\TextRenderingUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */