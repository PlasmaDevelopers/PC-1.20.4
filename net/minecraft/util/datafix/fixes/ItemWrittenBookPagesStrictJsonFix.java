/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ public class ItemWrittenBookPagesStrictJsonFix extends DataFix {
/*    */   public ItemWrittenBookPagesStrictJsonFix(Schema $$0, boolean $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   public Dynamic<?> fixTag(Dynamic<?> $$0) {
/* 22 */     return $$0.update("pages", $$1 -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return (Dynamic)DataFixUtils.orElse($$1.asStreamOpt().map(()).map($$0::createList).result(), $$0.emptyList());
/*    */         });
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
/*    */   public TypeRewriteRule makeRule() {
/* 71 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/* 72 */     OpticFinder<?> $$1 = $$0.findField("tag");
/*    */     
/* 74 */     return fixTypeEverywhereTyped("ItemWrittenBookPagesStrictJsonFix", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemWrittenBookPagesStrictJsonFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */