/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class OptionsAddTextBackgroundFix extends DataFix {
/*    */   public OptionsAddTextBackgroundFix(Schema $$0, boolean $$1) {
/* 11 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 16 */     return fixTypeEverywhereTyped("OptionsAddTextBackgroundFix", getInputSchema().getType(References.OPTIONS), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private double calculateBackground(String $$0) {
/*    */     try {
/* 23 */       double $$1 = 0.9D * Double.parseDouble($$0) + 0.1D;
/* 24 */       return $$1 / 2.0D;
/* 25 */     } catch (NumberFormatException $$2) {
/* 26 */       return 0.5D;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OptionsAddTextBackgroundFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */