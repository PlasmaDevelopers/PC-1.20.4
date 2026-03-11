/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import net.minecraft.util.datafix.ComponentDataFixUtils;
/*    */ 
/*    */ public class ObjectiveDisplayNameFix extends DataFix {
/*    */   public ObjectiveDisplayNameFix(Schema $$0, boolean $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 17 */     Type<?> $$0 = getInputSchema().getType(References.OBJECTIVE);
/* 18 */     return fixTypeEverywhereTyped("ObjectiveDisplayNameFix", $$0, $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ObjectiveDisplayNameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */