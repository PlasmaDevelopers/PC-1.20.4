/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.util.datafix.ComponentDataFixUtils;
/*    */ 
/*    */ public class ItemLoreFix extends DataFix {
/*    */   public ItemLoreFix(Schema $$0, boolean $$1) {
/* 17 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 22 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/* 23 */     OpticFinder<?> $$1 = $$0.findField("tag");
/*    */     
/* 25 */     return fixTypeEverywhereTyped("Item Lore componentize", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> Stream<Dynamic<T>> fixLoreList(Stream<Dynamic<T>> $$0) {
/* 39 */     return $$0.map(ComponentDataFixUtils::wrapLiteralStringAsComponent);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemLoreFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */