/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.OptionalDynamic;
/*    */ 
/*    */ public class LegacyDragonFightFix extends DataFix {
/*    */   public LegacyDragonFightFix(Schema $$0) {
/* 12 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 17 */     return fixTypeEverywhereTyped("LegacyDragonFightFix", getInputSchema().getType(References.LEVEL), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\LegacyDragonFightFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */