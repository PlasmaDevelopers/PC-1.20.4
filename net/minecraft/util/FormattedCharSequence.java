/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.chat.Style;
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface FormattedCharSequence
/*    */ {
/*    */   public static final FormattedCharSequence EMPTY = $$0 -> true;
/*    */   
/*    */   static FormattedCharSequence codepoint(int $$0, Style $$1) {
/* 16 */     return $$2 -> $$2.accept(0, $$0, $$1);
/*    */   }
/*    */   
/*    */   static FormattedCharSequence forward(String $$0, Style $$1) {
/* 20 */     if ($$0.isEmpty()) {
/* 21 */       return EMPTY;
/*    */     }
/* 23 */     return $$2 -> StringDecomposer.iterate($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   static FormattedCharSequence forward(String $$0, Style $$1, Int2IntFunction $$2) {
/* 27 */     if ($$0.isEmpty()) {
/* 28 */       return EMPTY;
/*    */     }
/* 30 */     return $$3 -> StringDecomposer.iterate($$0, $$1, decorateOutput($$3, $$2));
/*    */   }
/*    */   
/*    */   static FormattedCharSequence backward(String $$0, Style $$1) {
/* 34 */     if ($$0.isEmpty()) {
/* 35 */       return EMPTY;
/*    */     }
/* 37 */     return $$2 -> StringDecomposer.iterateBackwards($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   static FormattedCharSequence backward(String $$0, Style $$1, Int2IntFunction $$2) {
/* 41 */     if ($$0.isEmpty()) {
/* 42 */       return EMPTY;
/*    */     }
/* 44 */     return $$3 -> StringDecomposer.iterateBackwards($$0, $$1, decorateOutput($$3, $$2));
/*    */   }
/*    */   
/*    */   static FormattedCharSink decorateOutput(FormattedCharSink $$0, Int2IntFunction $$1) {
/* 48 */     return ($$2, $$3, $$4) -> $$0.accept($$2, $$3, ((Integer)$$1.apply(Integer.valueOf($$4))).intValue());
/*    */   }
/*    */   
/*    */   static FormattedCharSequence composite() {
/* 52 */     return EMPTY;
/*    */   }
/*    */   
/*    */   static FormattedCharSequence composite(FormattedCharSequence $$0) {
/* 56 */     return $$0;
/*    */   }
/*    */   
/*    */   static FormattedCharSequence composite(FormattedCharSequence $$0, FormattedCharSequence $$1) {
/* 60 */     return fromPair($$0, $$1);
/*    */   }
/*    */   
/*    */   static FormattedCharSequence composite(FormattedCharSequence... $$0) {
/* 64 */     return fromList((List<FormattedCharSequence>)ImmutableList.copyOf((Object[])$$0));
/*    */   }
/*    */   
/*    */   static FormattedCharSequence composite(List<FormattedCharSequence> $$0) {
/* 68 */     int $$1 = $$0.size();
/* 69 */     switch ($$1) {
/*    */       case 0:
/* 71 */         return EMPTY;
/*    */       case 1:
/* 73 */         return $$0.get(0);
/*    */       case 2:
/* 75 */         return fromPair($$0.get(0), $$0.get(1));
/*    */     } 
/* 77 */     return fromList((List<FormattedCharSequence>)ImmutableList.copyOf($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   static FormattedCharSequence fromPair(FormattedCharSequence $$0, FormattedCharSequence $$1) {
/* 82 */     return $$2 -> ($$0.accept($$2) && $$1.accept($$2));
/*    */   }
/*    */   
/*    */   static FormattedCharSequence fromList(List<FormattedCharSequence> $$0) {
/* 86 */     return $$1 -> {
/*    */         for (FormattedCharSequence $$2 : $$0) {
/*    */           if (!$$2.accept($$1))
/*    */             return false; 
/*    */         } 
/*    */         return true;
/*    */       };
/*    */   }
/*    */   
/*    */   boolean accept(FormattedCharSink paramFormattedCharSink);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\FormattedCharSequence.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */