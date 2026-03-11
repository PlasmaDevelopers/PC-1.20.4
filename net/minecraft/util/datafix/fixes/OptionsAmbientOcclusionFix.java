/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class OptionsAmbientOcclusionFix extends DataFix {
/*    */   public OptionsAmbientOcclusionFix(Schema $$0) {
/* 11 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 16 */     return fixTypeEverywhereTyped("OptionsAmbientOcclusionFix", getInputSchema().getType(References.OPTIONS), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static String updateValue(String $$0) {
/* 22 */     switch ($$0) { case "0": case "1": case "2":  }  return 
/*    */ 
/*    */       
/* 25 */       $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OptionsAmbientOcclusionFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */