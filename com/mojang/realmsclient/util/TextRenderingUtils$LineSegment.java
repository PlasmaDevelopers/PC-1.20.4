/*     */ package com.mojang.realmsclient.util;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineSegment
/*     */ {
/*     */   private final String fullText;
/*     */   @Nullable
/*     */   private final String linkTitle;
/*     */   @Nullable
/*     */   private final String linkUrl;
/*     */   
/*     */   private LineSegment(String $$0) {
/*  60 */     this.fullText = $$0;
/*  61 */     this.linkTitle = null;
/*  62 */     this.linkUrl = null;
/*     */   }
/*     */   
/*     */   private LineSegment(String $$0, @Nullable String $$1, @Nullable String $$2) {
/*  66 */     this.fullText = $$0;
/*  67 */     this.linkTitle = $$1;
/*  68 */     this.linkUrl = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  73 */     if (this == $$0) {
/*  74 */       return true;
/*     */     }
/*  76 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  77 */       return false;
/*     */     }
/*  79 */     LineSegment $$1 = (LineSegment)$$0;
/*  80 */     return (Objects.equals(this.fullText, $$1.fullText) && 
/*  81 */       Objects.equals(this.linkTitle, $$1.linkTitle) && 
/*  82 */       Objects.equals(this.linkUrl, $$1.linkUrl));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  87 */     return Objects.hash(new Object[] { this.fullText, this.linkTitle, this.linkUrl });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  92 */     return "Segment{fullText='" + this.fullText + "', linkTitle='" + this.linkTitle + "', linkUrl='" + this.linkUrl + "'}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String renderedText() {
/* 100 */     return isLink() ? this.linkTitle : this.fullText;
/*     */   }
/*     */   
/*     */   public boolean isLink() {
/* 104 */     return (this.linkTitle != null);
/*     */   }
/*     */   
/*     */   public String getLinkUrl() {
/* 108 */     if (!isLink()) {
/* 109 */       throw new IllegalStateException("Not a link: " + this);
/*     */     }
/* 111 */     return this.linkUrl;
/*     */   }
/*     */   
/*     */   public static LineSegment link(String $$0, String $$1) {
/* 115 */     return new LineSegment(null, $$0, $$1);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static LineSegment text(String $$0) {
/* 120 */     return new LineSegment($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\TextRenderingUtils$LineSegment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */