/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public class ComponentCollector
/*    */ {
/* 10 */   private final List<FormattedText> parts = Lists.newArrayList();
/*    */   
/*    */   public void append(FormattedText $$0) {
/* 13 */     this.parts.add($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public FormattedText getResult() {
/* 18 */     if (this.parts.isEmpty()) {
/* 19 */       return null;
/*    */     }
/* 21 */     if (this.parts.size() == 1) {
/* 22 */       return this.parts.get(0);
/*    */     }
/* 24 */     return FormattedText.composite(this.parts);
/*    */   }
/*    */   
/*    */   public FormattedText getResultOrEmpty() {
/* 28 */     FormattedText $$0 = getResult();
/* 29 */     return ($$0 != null) ? $$0 : FormattedText.EMPTY;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 33 */     this.parts.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\ComponentCollector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */