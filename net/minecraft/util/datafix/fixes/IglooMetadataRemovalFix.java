/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class IglooMetadataRemovalFix extends DataFix {
/*    */   public IglooMetadataRemovalFix(Schema $$0, boolean $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 17 */     Type<?> $$0 = getInputSchema().getType(References.STRUCTURE_FEATURE);
/* 18 */     return fixTypeEverywhereTyped("IglooMetadataRemovalFix", $$0, $$0 -> $$0.update(DSL.remainderFinder(), IglooMetadataRemovalFix::fixTag));
/*    */   }
/*    */   
/*    */   private static <T> Dynamic<T> fixTag(Dynamic<T> $$0) {
/* 22 */     boolean $$1 = ((Boolean)$$0.get("Children").asStreamOpt().map($$0 -> Boolean.valueOf($$0.allMatch(IglooMetadataRemovalFix::isIglooPiece))).result().orElse(Boolean.valueOf(false))).booleanValue();
/*    */     
/* 24 */     if ($$1) {
/* 25 */       return $$0.set("id", $$0.createString("Igloo")).remove("Children");
/*    */     }
/* 27 */     return $$0.update("Children", IglooMetadataRemovalFix::removeIglooPieces);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> Dynamic<T> removeIglooPieces(Dynamic<T> $$0) {
/* 32 */     Objects.requireNonNull($$0); return $$0.asStreamOpt().map($$0 -> $$0.filter(())).map($$0::createList).result().orElse($$0);
/*    */   }
/*    */   
/*    */   private static boolean isIglooPiece(Dynamic<?> $$0) {
/* 36 */     return $$0.get("id").asString("").equals("Iglu");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\IglooMetadataRemovalFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */