/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Lists;
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.UnaryOperator;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ import net.minecraft.util.StringDecomposer;
/*    */ 
/*    */ public class SubStringSource
/*    */ {
/*    */   private final String plainText;
/*    */   private final List<Style> charStyles;
/*    */   private final Int2IntFunction reverseCharModifier;
/*    */   
/*    */   private SubStringSource(String $$0, List<Style> $$1, Int2IntFunction $$2) {
/* 19 */     this.plainText = $$0;
/* 20 */     this.charStyles = (List<Style>)ImmutableList.copyOf($$1);
/* 21 */     this.reverseCharModifier = $$2;
/*    */   }
/*    */   
/*    */   public String getPlainText() {
/* 25 */     return this.plainText;
/*    */   }
/*    */   
/*    */   public List<FormattedCharSequence> substring(int $$0, int $$1, boolean $$2) {
/* 29 */     if ($$1 == 0) {
/* 30 */       return (List<FormattedCharSequence>)ImmutableList.of();
/*    */     }
/*    */     
/* 33 */     List<FormattedCharSequence> $$3 = Lists.newArrayList();
/*    */     
/* 35 */     Style $$4 = this.charStyles.get($$0);
/* 36 */     int $$5 = $$0;
/* 37 */     for (int $$6 = 1; $$6 < $$1; $$6++) {
/* 38 */       int $$7 = $$0 + $$6;
/* 39 */       Style $$8 = this.charStyles.get($$7);
/* 40 */       if (!$$8.equals($$4)) {
/* 41 */         String $$9 = this.plainText.substring($$5, $$7);
/* 42 */         $$3.add($$2 ? FormattedCharSequence.backward($$9, $$4, this.reverseCharModifier) : FormattedCharSequence.forward($$9, $$4));
/* 43 */         $$4 = $$8;
/* 44 */         $$5 = $$7;
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     if ($$5 < $$0 + $$1) {
/* 49 */       String $$10 = this.plainText.substring($$5, $$0 + $$1);
/* 50 */       $$3.add($$2 ? FormattedCharSequence.backward($$10, $$4, this.reverseCharModifier) : FormattedCharSequence.forward($$10, $$4));
/*    */     } 
/*    */     
/* 53 */     return $$2 ? Lists.reverse($$3) : $$3;
/*    */   }
/*    */   
/*    */   public static SubStringSource create(FormattedText $$0) {
/* 57 */     return create($$0, $$0 -> $$0, $$0 -> $$0);
/*    */   }
/*    */   
/*    */   public static SubStringSource create(FormattedText $$0, Int2IntFunction $$1, UnaryOperator<String> $$2) {
/* 61 */     StringBuilder $$3 = new StringBuilder();
/* 62 */     List<Style> $$4 = Lists.newArrayList();
/*    */     
/* 64 */     $$0.visit(($$2, $$3) -> { StringDecomposer.iterateFormatted($$3, $$2, ()); return Optional.empty(); }Style.EMPTY);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 76 */     return new SubStringSource($$2.apply($$3.toString()), $$4, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SubStringSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */