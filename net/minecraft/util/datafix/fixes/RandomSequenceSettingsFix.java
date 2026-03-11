/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class RandomSequenceSettingsFix extends DataFix {
/*    */   public RandomSequenceSettingsFix(Schema $$0) {
/* 10 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 15 */     return fixTypeEverywhereTyped("RandomSequenceSettingsFix", getInputSchema().getType(References.SAVED_DATA_RANDOM_SEQUENCES), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\RandomSequenceSettingsFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */