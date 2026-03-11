/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class AddFlagIfNotPresentFix extends DataFix {
/*    */   private final String name;
/*    */   private final boolean flagValue;
/*    */   
/*    */   public AddFlagIfNotPresentFix(Schema $$0, DSL.TypeReference $$1, String $$2, boolean $$3) {
/* 17 */     super($$0, true);
/* 18 */     this.flagValue = $$3;
/* 19 */     this.flagKey = $$2;
/* 20 */     this.name = "AddFlagIfNotPresentFix_" + this.flagKey + "=" + this.flagValue + " for " + $$0.getVersionKey();
/* 21 */     this.typeReference = $$1;
/*    */   }
/*    */   private final String flagKey; private final DSL.TypeReference typeReference;
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 26 */     Type<?> $$0 = getInputSchema().getType(this.typeReference);
/*    */     
/* 28 */     return fixTypeEverywhereTyped(this.name, $$0, $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\AddFlagIfNotPresentFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */