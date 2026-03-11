/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class OptionsProgrammerArtFix extends DataFix {
/*    */   public OptionsProgrammerArtFix(Schema $$0) {
/* 11 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 16 */     return fixTypeEverywhereTyped("OptionsProgrammerArtFix", getInputSchema().getType(References.OPTIONS), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private <T> Dynamic<T> fixList(Dynamic<T> $$0) {
/* 24 */     return $$0.asString().result().map($$1 -> $$0.createString($$1.replace("\"programer_art\"", "\"programmer_art\""))).orElse($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\OptionsProgrammerArtFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */