/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public abstract class ItemStackTagFix
/*    */   extends DataFix {
/*    */   private final String name;
/*    */   private final Predicate<String> idFilter;
/*    */   
/*    */   public ItemStackTagFix(Schema $$0, String $$1, Predicate<String> $$2) {
/* 22 */     super($$0, false);
/* 23 */     this.name = $$1;
/* 24 */     this.idFilter = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public final TypeRewriteRule makeRule() {
/* 29 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/*    */     
/* 31 */     OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 32 */     OpticFinder<?> $$2 = $$0.findField("tag");
/*    */     
/* 34 */     return fixTypeEverywhereTyped(this.name, $$0, $$2 -> {
/*    */           Optional<Pair<String, String>> $$3 = $$2.getOptional($$0);
/* 36 */           return ($$3.isPresent() && this.idFilter.test((String)((Pair)$$3.get()).getSecond())) ? $$2.updateTyped($$1, ()) : $$2;
/*    */         });
/*    */   }
/*    */   
/*    */   protected abstract <T> Dynamic<T> fixItemStackTag(Dynamic<T> paramDynamic);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemStackTagFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */