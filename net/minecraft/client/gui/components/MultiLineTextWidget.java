/*    */ package net.minecraft.client.gui.components;
/*    */ import java.util.Objects;
/*    */ import java.util.OptionalInt;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.util.SingleKeyCache;
/*    */ 
/*    */ public class MultiLineTextWidget extends AbstractStringWidget {
/* 12 */   private OptionalInt maxWidth = OptionalInt.empty();
/* 13 */   private OptionalInt maxRows = OptionalInt.empty();
/*    */   private final SingleKeyCache<CacheKey, MultiLineLabel> cache;
/*    */   private boolean centered = false;
/*    */   
/*    */   public MultiLineTextWidget(Component $$0, Font $$1) {
/* 18 */     this(0, 0, $$0, $$1);
/*    */   }
/*    */   
/*    */   public MultiLineTextWidget(int $$0, int $$1, Component $$2, Font $$3) {
/* 22 */     super($$0, $$1, 0, 0, $$2, $$3);
/* 23 */     this.cache = Util.singleKeyCache($$1 -> $$1.maxRows.isPresent() ? MultiLineLabel.create($$0, (FormattedText)$$1.message, $$1.maxWidth, $$1.maxRows.getAsInt()) : MultiLineLabel.create($$0, (FormattedText)$$1.message, $$1.maxWidth));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     this.active = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public MultiLineTextWidget setColor(int $$0) {
/* 35 */     super.setColor($$0);
/* 36 */     return this;
/*    */   }
/*    */   
/*    */   public MultiLineTextWidget setMaxWidth(int $$0) {
/* 40 */     this.maxWidth = OptionalInt.of($$0);
/* 41 */     return this;
/*    */   }
/*    */   
/*    */   public MultiLineTextWidget setMaxRows(int $$0) {
/* 45 */     this.maxRows = OptionalInt.of($$0);
/* 46 */     return this;
/*    */   }
/*    */   
/*    */   public MultiLineTextWidget setCentered(boolean $$0) {
/* 50 */     this.centered = $$0;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 56 */     return ((MultiLineLabel)this.cache.getValue(getFreshCacheKey())).getWidth();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 61 */     Objects.requireNonNull(getFont()); return ((MultiLineLabel)this.cache.getValue(getFreshCacheKey())).getLineCount() * 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 66 */     MultiLineLabel $$4 = (MultiLineLabel)this.cache.getValue(getFreshCacheKey());
/* 67 */     int $$5 = getX();
/* 68 */     int $$6 = getY();
/* 69 */     Objects.requireNonNull(getFont()); int $$7 = 9;
/* 70 */     int $$8 = getColor();
/* 71 */     if (this.centered) {
/* 72 */       $$4.renderCentered($$0, $$5 + getWidth() / 2, $$6, $$7, $$8);
/*    */     } else {
/* 74 */       $$4.renderLeftAligned($$0, $$5, $$6, $$7, $$8);
/*    */     } 
/*    */   }
/*    */   
/*    */   private CacheKey getFreshCacheKey() {
/* 79 */     return new CacheKey(getMessage(), this.maxWidth.orElse(2147483647), this.maxRows);
/*    */   }
/*    */   private static final class CacheKey extends Record { final Component message; final int maxWidth; final OptionalInt maxRows;
/* 82 */     CacheKey(Component $$0, int $$1, OptionalInt $$2) { this.message = $$0; this.maxWidth = $$1; this.maxRows = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/MultiLineTextWidget$CacheKey;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #82	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 82 */       //   0	7	0	this	Lnet/minecraft/client/gui/components/MultiLineTextWidget$CacheKey; } public Component message() { return this.message; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/MultiLineTextWidget$CacheKey;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #82	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/components/MultiLineTextWidget$CacheKey; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/MultiLineTextWidget$CacheKey;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #82	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/gui/components/MultiLineTextWidget$CacheKey;
/* 82 */       //   0	8	1	$$0	Ljava/lang/Object; } public int maxWidth() { return this.maxWidth; } public OptionalInt maxRows() { return this.maxRows; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\MultiLineTextWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */