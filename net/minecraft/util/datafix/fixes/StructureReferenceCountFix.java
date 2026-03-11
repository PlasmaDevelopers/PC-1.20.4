/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class StructureReferenceCountFix extends DataFix {
/*    */   public StructureReferenceCountFix(Schema $$0, boolean $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 17 */     Type<?> $$0 = getInputSchema().getType(References.STRUCTURE_FEATURE);
/* 18 */     return fixTypeEverywhereTyped("Structure Reference Fix", $$0, $$0 -> $$0.update(DSL.remainderFinder(), StructureReferenceCountFix::setCountToAtLeastOne));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> Dynamic<T> setCountToAtLeastOne(Dynamic<T> $$0) {
/* 24 */     return $$0.update("references", $$0 -> $$0.createInt(((Integer)$$0.asNumber().map(Number::intValue).result().filter(()).orElse(Integer.valueOf(1))).intValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\StructureReferenceCountFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */