/*     */ package com.mojang.blaze3d.vertex;
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
/*     */ public enum Mode
/*     */ {
/* 143 */   LINES(4, 2, 2, false),
/* 144 */   LINE_STRIP(5, 2, 1, true),
/* 145 */   DEBUG_LINES(1, 2, 2, false),
/* 146 */   DEBUG_LINE_STRIP(3, 2, 1, true),
/* 147 */   TRIANGLES(4, 3, 3, false),
/* 148 */   TRIANGLE_STRIP(5, 3, 1, true),
/* 149 */   TRIANGLE_FAN(6, 3, 1, true),
/* 150 */   QUADS(4, 4, 4, false);
/*     */   
/*     */   public final int asGLMode;
/*     */   public final int primitiveLength;
/*     */   public final int primitiveStride;
/*     */   public final boolean connectedPrimitives;
/*     */   
/*     */   Mode(int $$0, int $$1, int $$2, boolean $$3) {
/* 158 */     this.asGLMode = $$0;
/* 159 */     this.primitiveLength = $$1;
/* 160 */     this.primitiveStride = $$2;
/* 161 */     this.connectedPrimitives = $$3;
/*     */   }
/*     */   
/*     */   public int indexCount(int $$0) {
/*     */     int $$1, $$2;
/* 166 */     switch (VertexFormat.null.$SwitchMap$com$mojang$blaze3d$vertex$VertexFormat$Mode[ordinal()])
/*     */     { case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/* 173 */         $$1 = $$0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 183 */         return $$1;case 7: case 8: $$2 = $$0 / 4 * 6; return $$2; }  int $$3 = 0; return $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexFormat$Mode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */