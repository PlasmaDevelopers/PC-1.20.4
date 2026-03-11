/*     */ package net.minecraft.client;
/*     */ 
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.FormattedCharSink;
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
/*     */ class LineBreakFinder
/*     */   implements FormattedCharSink
/*     */ {
/*     */   private final float maxWidth;
/* 173 */   private int lineBreak = -1;
/* 174 */   private Style lineBreakStyle = Style.EMPTY;
/*     */   private boolean hadNonZeroWidthChar;
/*     */   private float width;
/* 177 */   private int lastSpace = -1;
/* 178 */   private Style lastSpaceStyle = Style.EMPTY;
/*     */   private int nextChar;
/*     */   private int offset;
/*     */   
/*     */   public LineBreakFinder(float $$0) {
/* 183 */     this.maxWidth = Math.max($$0, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean accept(int $$0, Style $$1, int $$2) {
/* 188 */     int $$3 = $$0 + this.offset;
/* 189 */     switch ($$2) {
/*     */       case 10:
/* 191 */         return finishIteration($$3, $$1);
/*     */       case 32:
/* 193 */         this.lastSpace = $$3;
/* 194 */         this.lastSpaceStyle = $$1;
/*     */         break;
/*     */     } 
/* 197 */     float $$4 = StringSplitter.this.widthProvider.getWidth($$2, $$1);
/* 198 */     this.width += $$4;
/* 199 */     if (this.hadNonZeroWidthChar && this.width > this.maxWidth) {
/* 200 */       if (this.lastSpace != -1) {
/* 201 */         return finishIteration(this.lastSpace, this.lastSpaceStyle);
/*     */       }
/* 203 */       return finishIteration($$3, $$1);
/*     */     } 
/*     */     
/* 206 */     this.hadNonZeroWidthChar |= ($$4 != 0.0F) ? 1 : 0;
/* 207 */     this.nextChar = $$3 + Character.charCount($$2);
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean finishIteration(int $$0, Style $$1) {
/* 214 */     this.lineBreak = $$0;
/* 215 */     this.lineBreakStyle = $$1;
/* 216 */     return false;
/*     */   }
/*     */   
/*     */   private boolean lineBreakFound() {
/* 220 */     return (this.lineBreak != -1);
/*     */   }
/*     */   
/*     */   public int getSplitPosition() {
/* 224 */     return lineBreakFound() ? this.lineBreak : this.nextChar;
/*     */   }
/*     */   
/*     */   public Style getSplitStyle() {
/* 228 */     return this.lineBreakStyle;
/*     */   }
/*     */   
/*     */   public void addToOffset(int $$0) {
/* 232 */     this.offset += $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\StringSplitter$LineBreakFinder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */