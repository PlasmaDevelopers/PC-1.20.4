/*    */ package net.minecraft.client.resources.language;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.ibm.icu.lang.UCharacter;
/*    */ import com.ibm.icu.text.ArabicShaping;
/*    */ import com.ibm.icu.text.Bidi;
/*    */ import com.ibm.icu.text.BidiRun;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.SubStringSource;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ 
/*    */ public class FormattedBidiReorder
/*    */ {
/*    */   public static FormattedCharSequence reorder(FormattedText $$0, boolean $$1) {
/* 16 */     SubStringSource $$2 = SubStringSource.create($$0, UCharacter::getMirror, FormattedBidiReorder::shape);
/* 17 */     Bidi $$3 = new Bidi($$2.getPlainText(), $$1 ? 127 : 126);
/* 18 */     $$3.setReorderingMode(0);
/*    */     
/* 20 */     List<FormattedCharSequence> $$4 = Lists.newArrayList();
/* 21 */     int $$5 = $$3.countRuns();
/* 22 */     for (int $$6 = 0; $$6 < $$5; $$6++) {
/* 23 */       BidiRun $$7 = $$3.getVisualRun($$6);
/* 24 */       $$4.addAll($$2.substring($$7.getStart(), $$7.getLength(), $$7.isOddRun()));
/*    */     } 
/*    */     
/* 27 */     return FormattedCharSequence.composite($$4);
/*    */   }
/*    */   
/*    */   private static String shape(String $$0) {
/*    */     try {
/* 32 */       return (new ArabicShaping(8)).shape($$0);
/* 33 */     } catch (Exception $$1) {
/* 34 */       return $$0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\language\FormattedBidiReorder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */