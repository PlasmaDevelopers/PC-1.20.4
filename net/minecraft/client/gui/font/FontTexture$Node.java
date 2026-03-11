/*     */ package net.minecraft.client.gui.font;
/*     */ 
/*     */ import com.mojang.blaze3d.font.SheetGlyphInfo;
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
/*     */ class Node
/*     */ {
/*     */   final int x;
/*     */   final int y;
/*     */   private final int width;
/*     */   private final int height;
/*     */   @Nullable
/*     */   private Node left;
/*     */   @Nullable
/*     */   private Node right;
/*     */   private boolean occupied;
/*     */   
/*     */   Node(int $$0, int $$1, int $$2, int $$3) {
/*  92 */     this.x = $$0;
/*  93 */     this.y = $$1;
/*  94 */     this.width = $$2;
/*  95 */     this.height = $$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Node insert(SheetGlyphInfo $$0) {
/* 100 */     if (this.left != null && this.right != null) {
/* 101 */       Node $$1 = this.left.insert($$0);
/* 102 */       if ($$1 == null) {
/* 103 */         $$1 = this.right.insert($$0);
/*     */       }
/* 105 */       return $$1;
/*     */     } 
/*     */     
/* 108 */     if (this.occupied) {
/* 109 */       return null;
/*     */     }
/* 111 */     int $$2 = $$0.getPixelWidth();
/* 112 */     int $$3 = $$0.getPixelHeight();
/*     */     
/* 114 */     if ($$2 > this.width || $$3 > this.height) {
/* 115 */       return null;
/*     */     }
/* 117 */     if ($$2 == this.width && $$3 == this.height) {
/* 118 */       this.occupied = true;
/* 119 */       return this;
/*     */     } 
/*     */     
/* 122 */     int $$4 = this.width - $$2;
/* 123 */     int $$5 = this.height - $$3;
/*     */     
/* 125 */     if ($$4 > $$5) {
/* 126 */       this.left = new Node(this.x, this.y, $$2, this.height);
/* 127 */       this.right = new Node(this.x + $$2 + 1, this.y, this.width - $$2 - 1, this.height);
/*     */     } else {
/* 129 */       this.left = new Node(this.x, this.y, this.width, $$3);
/* 130 */       this.right = new Node(this.x, this.y + $$3 + 1, this.width, this.height - $$3 - 1);
/*     */     } 
/* 132 */     return this.left.insert($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\FontTexture$Node.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */