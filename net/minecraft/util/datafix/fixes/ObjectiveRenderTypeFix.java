/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class ObjectiveRenderTypeFix extends DataFix {
/*    */   public ObjectiveRenderTypeFix(Schema $$0, boolean $$1) {
/* 13 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   private static String getRenderType(String $$0) {
/* 17 */     return $$0.equals("health") ? "hearts" : "integer";
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 22 */     Type<?> $$0 = getInputSchema().getType(References.OBJECTIVE);
/* 23 */     return fixTypeEverywhereTyped("ObjectiveRenderTypeFix", $$0, $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ObjectiveRenderTypeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */