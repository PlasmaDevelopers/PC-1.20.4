/*     */ package net.minecraft.client;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.FormattedCharSink;
/*     */ import net.minecraft.util.StringDecomposer;
/*     */ import org.apache.commons.lang3.mutable.MutableFloat;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringSplitter
/*     */ {
/*     */   final WidthProvider widthProvider;
/*     */   
/*     */   public StringSplitter(WidthProvider $$0) {
/*  29 */     this.widthProvider = $$0;
/*     */   }
/*     */   
/*     */   public float stringWidth(@Nullable String $$0) {
/*  33 */     if ($$0 == null) {
/*  34 */       return 0.0F;
/*     */     }
/*     */     
/*  37 */     MutableFloat $$1 = new MutableFloat();
/*  38 */     StringDecomposer.iterateFormatted($$0, Style.EMPTY, ($$1, $$2, $$3) -> {
/*     */           $$0.add(this.widthProvider.getWidth($$3, $$2));
/*     */           return true;
/*     */         });
/*  42 */     return $$1.floatValue();
/*     */   }
/*     */   
/*     */   public float stringWidth(FormattedText $$0) {
/*  46 */     MutableFloat $$1 = new MutableFloat();
/*  47 */     StringDecomposer.iterateFormatted($$0, Style.EMPTY, ($$1, $$2, $$3) -> {
/*     */           $$0.add(this.widthProvider.getWidth($$3, $$2));
/*     */           return true;
/*     */         });
/*  51 */     return $$1.floatValue();
/*     */   }
/*     */   
/*     */   public float stringWidth(FormattedCharSequence $$0) {
/*  55 */     MutableFloat $$1 = new MutableFloat();
/*  56 */     $$0.accept(($$1, $$2, $$3) -> {
/*     */           $$0.add(this.widthProvider.getWidth($$3, $$2));
/*     */           return true;
/*     */         });
/*  60 */     return $$1.floatValue();
/*     */   }
/*     */   
/*     */   private class WidthLimitedCharSink implements FormattedCharSink {
/*     */     private float maxWidth;
/*     */     private int position;
/*     */     
/*     */     public WidthLimitedCharSink(float $$0) {
/*  68 */       this.maxWidth = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean accept(int $$0, Style $$1, int $$2) {
/*  73 */       this.maxWidth -= StringSplitter.this.widthProvider.getWidth($$2, $$1);
/*  74 */       if (this.maxWidth >= 0.0F) {
/*  75 */         this.position = $$0 + Character.charCount($$2);
/*  76 */         return true;
/*     */       } 
/*  78 */       return false;
/*     */     }
/*     */     
/*     */     public int getPosition() {
/*  82 */       return this.position;
/*     */     }
/*     */     
/*     */     public void resetPosition() {
/*  86 */       this.position = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public int plainIndexAtWidth(String $$0, int $$1, Style $$2) {
/*  91 */     WidthLimitedCharSink $$3 = new WidthLimitedCharSink($$1);
/*  92 */     StringDecomposer.iterate($$0, $$2, $$3);
/*  93 */     return $$3.getPosition();
/*     */   }
/*     */   
/*     */   public String plainHeadByWidth(String $$0, int $$1, Style $$2) {
/*  97 */     return $$0.substring(0, plainIndexAtWidth($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public String plainTailByWidth(String $$0, int $$1, Style $$2) {
/* 101 */     MutableFloat $$3 = new MutableFloat();
/* 102 */     MutableInt $$4 = new MutableInt($$0.length());
/* 103 */     StringDecomposer.iterateBackwards($$0, $$2, ($$3, $$4, $$5) -> {
/*     */           float $$6 = $$0.addAndGet(this.widthProvider.getWidth($$5, $$4));
/*     */           
/*     */           if ($$6 > $$1) {
/*     */             return false;
/*     */           }
/*     */           
/*     */           $$2.setValue($$3);
/*     */           return true;
/*     */         });
/* 113 */     return $$0.substring($$4.intValue());
/*     */   }
/*     */   
/*     */   public int formattedIndexByWidth(String $$0, int $$1, Style $$2) {
/* 117 */     WidthLimitedCharSink $$3 = new WidthLimitedCharSink($$1);
/* 118 */     StringDecomposer.iterateFormatted($$0, $$2, $$3);
/* 119 */     return $$3.getPosition();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Style componentStyleAtWidth(FormattedText $$0, int $$1) {
/* 124 */     WidthLimitedCharSink $$2 = new WidthLimitedCharSink($$1);
/* 125 */     return $$0.visit(($$1, $$2) -> StringDecomposer.iterateFormatted($$2, $$1, $$0) ? Optional.empty() : Optional.<Style>of($$1), Style.EMPTY).orElse(null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Style componentStyleAtWidth(FormattedCharSequence $$0, int $$1) {
/* 130 */     WidthLimitedCharSink $$2 = new WidthLimitedCharSink($$1);
/* 131 */     MutableObject<Style> $$3 = new MutableObject();
/* 132 */     $$0.accept(($$2, $$3, $$4) -> {
/*     */           if (!$$0.accept($$2, $$3, $$4)) {
/*     */             $$1.setValue($$3);
/*     */             return false;
/*     */           } 
/*     */           return true;
/*     */         });
/* 139 */     return (Style)$$3.getValue();
/*     */   }
/*     */   
/*     */   public String formattedHeadByWidth(String $$0, int $$1, Style $$2) {
/* 143 */     return $$0.substring(0, formattedIndexByWidth($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public FormattedText headByWidth(FormattedText $$0, int $$1, Style $$2) {
/* 147 */     final WidthLimitedCharSink output = new WidthLimitedCharSink($$1);
/*     */     
/* 149 */     return $$0.visit(new FormattedText.StyledContentConsumer<FormattedText>() {
/* 150 */           private final ComponentCollector collector = new ComponentCollector();
/*     */ 
/*     */           
/*     */           public Optional<FormattedText> accept(Style $$0, String $$1) {
/* 154 */             output.resetPosition();
/* 155 */             if (!StringDecomposer.iterateFormatted($$1, $$0, output)) {
/* 156 */               String $$2 = $$1.substring(0, output.getPosition());
/* 157 */               if (!$$2.isEmpty()) {
/* 158 */                 this.collector.append(FormattedText.of($$2, $$0));
/*     */               }
/* 160 */               return Optional.of(this.collector.getResultOrEmpty());
/*     */             } 
/* 162 */             if (!$$1.isEmpty()) {
/* 163 */               this.collector.append(FormattedText.of($$1, $$0));
/*     */             }
/* 165 */             return Optional.empty();
/*     */           }
/* 167 */         }$$2).orElse($$0);
/*     */   }
/*     */   
/*     */   private class LineBreakFinder
/*     */     implements FormattedCharSink {
/*     */     private final float maxWidth;
/* 173 */     private int lineBreak = -1;
/* 174 */     private Style lineBreakStyle = Style.EMPTY;
/*     */     private boolean hadNonZeroWidthChar;
/*     */     private float width;
/* 177 */     private int lastSpace = -1;
/* 178 */     private Style lastSpaceStyle = Style.EMPTY;
/*     */     private int nextChar;
/*     */     private int offset;
/*     */     
/*     */     public LineBreakFinder(float $$0) {
/* 183 */       this.maxWidth = Math.max($$0, 1.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean accept(int $$0, Style $$1, int $$2) {
/* 188 */       int $$3 = $$0 + this.offset;
/* 189 */       switch ($$2) {
/*     */         case 10:
/* 191 */           return finishIteration($$3, $$1);
/*     */         case 32:
/* 193 */           this.lastSpace = $$3;
/* 194 */           this.lastSpaceStyle = $$1;
/*     */           break;
/*     */       } 
/* 197 */       float $$4 = StringSplitter.this.widthProvider.getWidth($$2, $$1);
/* 198 */       this.width += $$4;
/* 199 */       if (this.hadNonZeroWidthChar && this.width > this.maxWidth) {
/* 200 */         if (this.lastSpace != -1) {
/* 201 */           return finishIteration(this.lastSpace, this.lastSpaceStyle);
/*     */         }
/* 203 */         return finishIteration($$3, $$1);
/*     */       } 
/*     */       
/* 206 */       this.hadNonZeroWidthChar |= ($$4 != 0.0F) ? 1 : 0;
/* 207 */       this.nextChar = $$3 + Character.charCount($$2);
/* 208 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean finishIteration(int $$0, Style $$1) {
/* 214 */       this.lineBreak = $$0;
/* 215 */       this.lineBreakStyle = $$1;
/* 216 */       return false;
/*     */     }
/*     */     
/*     */     private boolean lineBreakFound() {
/* 220 */       return (this.lineBreak != -1);
/*     */     }
/*     */     
/*     */     public int getSplitPosition() {
/* 224 */       return lineBreakFound() ? this.lineBreak : this.nextChar;
/*     */     }
/*     */     
/*     */     public Style getSplitStyle() {
/* 228 */       return this.lineBreakStyle;
/*     */     }
/*     */     
/*     */     public void addToOffset(int $$0) {
/* 232 */       this.offset += $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   public int findLineBreak(String $$0, int $$1, Style $$2) {
/* 237 */     LineBreakFinder $$3 = new LineBreakFinder($$1);
/* 238 */     StringDecomposer.iterateFormatted($$0, $$2, $$3);
/* 239 */     return $$3.getSplitPosition();
/*     */   }
/*     */   
/*     */   public static int getWordPosition(String $$0, int $$1, int $$2, boolean $$3) {
/* 243 */     int $$4 = $$2;
/* 244 */     boolean $$5 = ($$1 < 0);
/* 245 */     int $$6 = Math.abs($$1);
/*     */     
/* 247 */     for (int $$7 = 0; $$7 < $$6; $$7++) {
/* 248 */       if ($$5) {
/* 249 */         while ($$3 && $$4 > 0 && ($$0.charAt($$4 - 1) == ' ' || $$0.charAt($$4 - 1) == '\n')) {
/* 250 */           $$4--;
/*     */         }
/* 252 */         while ($$4 > 0 && $$0.charAt($$4 - 1) != ' ' && $$0.charAt($$4 - 1) != '\n') {
/* 253 */           $$4--;
/*     */         }
/*     */       } else {
/* 256 */         int $$8 = $$0.length();
/*     */         
/* 258 */         int $$9 = $$0.indexOf(' ', $$4);
/* 259 */         int $$10 = $$0.indexOf('\n', $$4);
/* 260 */         if ($$9 == -1 && $$10 == -1) {
/* 261 */           $$4 = -1;
/* 262 */         } else if ($$9 != -1 && $$10 != -1) {
/* 263 */           $$4 = Math.min($$9, $$10);
/* 264 */         } else if ($$9 != -1) {
/* 265 */           $$4 = $$9;
/*     */         } else {
/* 267 */           $$4 = $$10;
/*     */         } 
/*     */         
/* 270 */         if ($$4 == -1) {
/* 271 */           $$4 = $$8;
/*     */         } else {
/* 273 */           while ($$3 && $$4 < $$8 && ($$0.charAt($$4) == ' ' || $$0.charAt($$4) == '\n')) {
/* 274 */             $$4++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     return $$4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void splitLines(String $$0, int $$1, Style $$2, boolean $$3, LinePosConsumer $$4) {
/* 289 */     int $$5 = 0;
/* 290 */     int $$6 = $$0.length();
/* 291 */     Style $$7 = $$2;
/* 292 */     while ($$5 < $$6) {
/* 293 */       LineBreakFinder $$8 = new LineBreakFinder($$1);
/* 294 */       boolean $$9 = StringDecomposer.iterateFormatted($$0, $$5, $$7, $$2, $$8);
/* 295 */       if ($$9) {
/* 296 */         $$4.accept($$7, $$5, $$6);
/*     */         break;
/*     */       } 
/* 299 */       int $$10 = $$8.getSplitPosition();
/* 300 */       char $$11 = $$0.charAt($$10);
/*     */       
/* 302 */       int $$12 = ($$11 == '\n' || $$11 == ' ') ? ($$10 + 1) : $$10;
/* 303 */       $$4.accept($$7, $$5, $$3 ? $$12 : $$10);
/* 304 */       $$5 = $$12;
/* 305 */       $$7 = $$8.getSplitStyle();
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<FormattedText> splitLines(String $$0, int $$1, Style $$2) {
/* 310 */     List<FormattedText> $$3 = Lists.newArrayList();
/* 311 */     splitLines($$0, $$1, $$2, false, ($$2, $$3, $$4) -> $$0.add(FormattedText.of($$1.substring($$3, $$4), $$2)));
/* 312 */     return $$3;
/*     */   }
/*     */   
/*     */   private static class LineComponent implements FormattedText {
/*     */     final String contents;
/*     */     final Style style;
/*     */     
/*     */     public LineComponent(String $$0, Style $$1) {
/* 320 */       this.contents = $$0;
/* 321 */       this.style = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 326 */       return $$0.accept(this.contents);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 331 */       return $$0.accept(this.style.applyTo($$1), this.contents);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FlatComponents {
/*     */     final List<StringSplitter.LineComponent> parts;
/*     */     private String flatParts;
/*     */     
/*     */     public FlatComponents(List<StringSplitter.LineComponent> $$0) {
/* 340 */       this.parts = $$0;
/* 341 */       this.flatParts = $$0.stream().map($$0 -> $$0.contents).collect(Collectors.joining());
/*     */     }
/*     */     
/*     */     public char charAt(int $$0) {
/* 345 */       return this.flatParts.charAt($$0);
/*     */     }
/*     */     
/*     */     public FormattedText splitAt(int $$0, int $$1, Style $$2) {
/* 349 */       ComponentCollector $$3 = new ComponentCollector();
/* 350 */       ListIterator<StringSplitter.LineComponent> $$4 = this.parts.listIterator();
/* 351 */       int $$5 = $$0;
/* 352 */       boolean $$6 = false;
/* 353 */       while ($$4.hasNext()) {
/* 354 */         StringSplitter.LineComponent $$7 = $$4.next();
/* 355 */         String $$8 = $$7.contents;
/* 356 */         int $$9 = $$8.length();
/*     */         
/* 358 */         if (!$$6) {
/* 359 */           if ($$5 > $$9) {
/* 360 */             $$3.append($$7);
/* 361 */             $$4.remove();
/* 362 */             $$5 -= $$9;
/*     */           } else {
/* 364 */             String $$10 = $$8.substring(0, $$5);
/* 365 */             if (!$$10.isEmpty()) {
/* 366 */               $$3.append(FormattedText.of($$10, $$7.style));
/*     */             }
/* 368 */             $$5 += $$1;
/* 369 */             $$6 = true;
/*     */           } 
/*     */         }
/*     */         
/* 373 */         if ($$6) {
/* 374 */           if ($$5 > $$9) {
/* 375 */             $$4.remove();
/* 376 */             $$5 -= $$9; continue;
/*     */           } 
/* 378 */           String $$11 = $$8.substring($$5);
/* 379 */           if ($$11.isEmpty()) {
/* 380 */             $$4.remove(); break;
/*     */           } 
/* 382 */           $$4.set(new StringSplitter.LineComponent($$11, $$2));
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 389 */       this.flatParts = this.flatParts.substring($$0 + $$1);
/* 390 */       return $$3.getResultOrEmpty();
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public FormattedText getRemainder() {
/* 395 */       ComponentCollector $$0 = new ComponentCollector();
/* 396 */       Objects.requireNonNull($$0); this.parts.forEach($$0::append);
/* 397 */       this.parts.clear();
/* 398 */       return $$0.getResult();
/*     */     }
/*     */   }
/*     */   
/*     */   public List<FormattedText> splitLines(FormattedText $$0, int $$1, Style $$2) {
/* 403 */     List<FormattedText> $$3 = Lists.newArrayList();
/* 404 */     splitLines($$0, $$1, $$2, ($$1, $$2) -> $$0.add($$1));
/* 405 */     return $$3;
/*     */   }
/*     */   
/*     */   public List<FormattedText> splitLines(FormattedText $$0, int $$1, Style $$2, FormattedText $$3) {
/* 409 */     List<FormattedText> $$4 = Lists.newArrayList();
/* 410 */     splitLines($$0, $$1, $$2, ($$2, $$3) -> $$0.add($$3.booleanValue() ? FormattedText.composite(new FormattedText[] { $$1, $$2 }) : $$2));
/* 411 */     return $$4;
/*     */   }
/*     */   
/*     */   public void splitLines(FormattedText $$0, int $$1, Style $$2, BiConsumer<FormattedText, Boolean> $$3) {
/* 415 */     List<LineComponent> $$4 = Lists.newArrayList();
/*     */     
/* 417 */     $$0.visit(($$1, $$2) -> { if (!$$2.isEmpty()) $$0.add(new LineComponent($$2, $$1));  return Optional.empty(); }$$2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     FlatComponents $$5 = new FlatComponents($$4);
/* 425 */     boolean $$6 = true;
/* 426 */     boolean $$7 = false;
/* 427 */     boolean $$8 = false;
/* 428 */     while ($$6) {
/* 429 */       $$6 = false;
/* 430 */       LineBreakFinder $$9 = new LineBreakFinder($$1);
/* 431 */       for (LineComponent $$10 : $$5.parts) {
/* 432 */         boolean $$11 = StringDecomposer.iterateFormatted($$10.contents, 0, $$10.style, $$2, $$9);
/* 433 */         if (!$$11) {
/*     */           
/* 435 */           int $$12 = $$9.getSplitPosition();
/* 436 */           Style $$13 = $$9.getSplitStyle();
/*     */           
/* 438 */           char $$14 = $$5.charAt($$12);
/*     */           
/* 440 */           boolean $$15 = ($$14 == '\n');
/* 441 */           boolean $$16 = ($$15 || $$14 == ' ');
/* 442 */           $$7 = $$15;
/* 443 */           FormattedText $$17 = $$5.splitAt($$12, $$16 ? 1 : 0, $$13);
/* 444 */           $$3.accept($$17, Boolean.valueOf($$8));
/* 445 */           $$8 = !$$15;
/* 446 */           $$6 = true;
/*     */           break;
/*     */         } 
/* 449 */         $$9.addToOffset($$10.contents.length());
/*     */       } 
/*     */     } 
/*     */     
/* 453 */     FormattedText $$18 = $$5.getRemainder();
/* 454 */     if ($$18 != null) {
/* 455 */       $$3.accept($$18, Boolean.valueOf($$8));
/* 456 */     } else if ($$7) {
/* 457 */       $$3.accept(FormattedText.EMPTY, Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface WidthProvider {
/*     */     float getWidth(int param1Int, Style param1Style);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface LinePosConsumer {
/*     */     void accept(Style param1Style, int param1Int1, int param1Int2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\StringSplitter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */