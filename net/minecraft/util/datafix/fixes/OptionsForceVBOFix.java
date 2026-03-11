/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class OptionsForceVBOFix extends DataFix {
/*    */   public OptionsForceVBOFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 15 */     return fixTypeEverywhereTyped("OptionsForceVBOFix", getInputSchema().getType(References.OPTIONS), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OptionsForceVBOFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */