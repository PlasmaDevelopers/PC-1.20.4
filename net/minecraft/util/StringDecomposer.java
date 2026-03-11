/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.Style;
/*     */ 
/*     */ public class StringDecomposer
/*     */ {
/*     */   private static final char REPLACEMENT_CHAR = '�';
/*  11 */   private static final Optional<Object> STOP_ITERATION = Optional.of(Unit.INSTANCE);
/*     */   
/*     */   private static boolean feedChar(Style $$0, FormattedCharSink $$1, int $$2, char $$3) {
/*  14 */     if (Character.isSurrogate($$3)) {
/*  15 */       return $$1.accept($$2, $$0, 65533);
/*     */     }
/*  17 */     return $$1.accept($$2, $$0, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean iterate(String $$0, Style $$1, FormattedCharSink $$2) {
/*  22 */     int $$3 = $$0.length();
/*  23 */     for (int $$4 = 0; $$4 < $$3; $$4++) {
/*  24 */       char $$5 = $$0.charAt($$4);
/*  25 */       if (Character.isHighSurrogate($$5)) {
/*  26 */         if ($$4 + 1 >= $$3) {
/*  27 */           if (!$$2.accept($$4, $$1, 65533)) {
/*  28 */             return false;
/*     */           }
/*     */           break;
/*     */         } 
/*  32 */         char $$6 = $$0.charAt($$4 + 1);
/*  33 */         if (Character.isLowSurrogate($$6)) {
/*  34 */           if (!$$2.accept($$4, $$1, Character.toCodePoint($$5, $$6))) {
/*  35 */             return false;
/*     */           }
/*  37 */           $$4++;
/*     */         }
/*  39 */         else if (!$$2.accept($$4, $$1, 65533)) {
/*  40 */           return false;
/*     */         }
/*     */       
/*  43 */       } else if (!feedChar($$1, $$2, $$4, $$5)) {
/*  44 */         return false;
/*     */       } 
/*     */     } 
/*  47 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean iterateBackwards(String $$0, Style $$1, FormattedCharSink $$2) {
/*  51 */     int $$3 = $$0.length();
/*  52 */     for (int $$4 = $$3 - 1; $$4 >= 0; $$4--) {
/*  53 */       char $$5 = $$0.charAt($$4);
/*  54 */       if (Character.isLowSurrogate($$5)) {
/*  55 */         if ($$4 - 1 < 0) {
/*  56 */           if (!$$2.accept(0, $$1, 65533)) {
/*  57 */             return false;
/*     */           }
/*     */           break;
/*     */         } 
/*  61 */         char $$6 = $$0.charAt($$4 - 1);
/*  62 */         if (Character.isHighSurrogate($$6)) {
/*  63 */           $$4--;
/*  64 */           if (!$$2.accept($$4, $$1, Character.toCodePoint($$6, $$5))) {
/*  65 */             return false;
/*     */           }
/*     */         }
/*  68 */         else if (!$$2.accept($$4, $$1, 65533)) {
/*  69 */           return false;
/*     */         }
/*     */       
/*  72 */       } else if (!feedChar($$1, $$2, $$4, $$5)) {
/*  73 */         return false;
/*     */       } 
/*     */     } 
/*  76 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean iterateFormatted(String $$0, Style $$1, FormattedCharSink $$2) {
/*  80 */     return iterateFormatted($$0, 0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static boolean iterateFormatted(String $$0, int $$1, Style $$2, FormattedCharSink $$3) {
/*  84 */     return iterateFormatted($$0, $$1, $$2, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static boolean iterateFormatted(String $$0, int $$1, Style $$2, Style $$3, FormattedCharSink $$4) {
/*  88 */     int $$5 = $$0.length();
/*  89 */     Style $$6 = $$2;
/*  90 */     for (int $$7 = $$1; $$7 < $$5; $$7++) {
/*  91 */       char $$8 = $$0.charAt($$7);
/*  92 */       if ($$8 == '§') {
/*  93 */         if ($$7 + 1 >= $$5) {
/*     */           break;
/*     */         }
/*  96 */         char $$9 = $$0.charAt($$7 + 1);
/*  97 */         ChatFormatting $$10 = ChatFormatting.getByCode($$9);
/*  98 */         if ($$10 != null) {
/*  99 */           $$6 = ($$10 == ChatFormatting.RESET) ? $$3 : $$6.applyLegacyFormat($$10);
/*     */         }
/* 101 */         $$7++;
/* 102 */       } else if (Character.isHighSurrogate($$8)) {
/* 103 */         if ($$7 + 1 >= $$5) {
/* 104 */           if (!$$4.accept($$7, $$6, 65533)) {
/* 105 */             return false;
/*     */           }
/*     */           break;
/*     */         } 
/* 109 */         char $$11 = $$0.charAt($$7 + 1);
/* 110 */         if (Character.isLowSurrogate($$11)) {
/* 111 */           if (!$$4.accept($$7, $$6, Character.toCodePoint($$8, $$11))) {
/* 112 */             return false;
/*     */           }
/* 114 */           $$7++;
/*     */         }
/* 116 */         else if (!$$4.accept($$7, $$6, 65533)) {
/* 117 */           return false;
/*     */         }
/*     */       
/* 120 */       } else if (!feedChar($$6, $$4, $$7, $$8)) {
/* 121 */         return false;
/*     */       } 
/*     */     } 
/* 124 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean iterateFormatted(FormattedText $$0, Style $$1, FormattedCharSink $$2) {
/* 128 */     return $$0.visit(($$1, $$2) -> iterateFormatted($$2, 0, $$1, $$0) ? Optional.empty() : STOP_ITERATION, $$1).isEmpty();
/*     */   }
/*     */   
/*     */   public static String filterBrokenSurrogates(String $$0) {
/* 132 */     StringBuilder $$1 = new StringBuilder();
/* 133 */     iterate($$0, Style.EMPTY, ($$1, $$2, $$3) -> {
/*     */           $$0.appendCodePoint($$3);
/*     */           return true;
/*     */         });
/* 137 */     return $$1.toString();
/*     */   }
/*     */   
/*     */   public static String getPlainText(FormattedText $$0) {
/* 141 */     StringBuilder $$1 = new StringBuilder();
/* 142 */     iterateFormatted($$0, Style.EMPTY, ($$1, $$2, $$3) -> {
/*     */           $$0.appendCodePoint($$3);
/*     */           return true;
/*     */         });
/* 146 */     return $$1.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\StringDecomposer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */