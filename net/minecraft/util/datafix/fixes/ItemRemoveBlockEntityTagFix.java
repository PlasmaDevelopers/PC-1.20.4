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
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class ItemRemoveBlockEntityTagFix
/*    */   extends DataFix
/*    */ {
/*    */   private final Set<String> items;
/*    */   
/*    */   public ItemRemoveBlockEntityTagFix(Schema $$0, boolean $$1, Set<String> $$2) {
/* 22 */     super($$0, $$1);
/* 23 */     this.items = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 28 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/*    */     
/* 30 */     OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 31 */     OpticFinder<?> $$2 = $$0.findField("tag");
/* 32 */     OpticFinder<?> $$3 = $$2.type().findField("BlockEntityTag");
/*    */     
/* 34 */     return fixTypeEverywhereTyped("ItemRemoveBlockEntityTagFix", $$0, $$3 -> {
/*    */           Optional<Pair<String, String>> $$4 = $$3.getOptional($$0);
/*    */           if ($$4.isPresent() && this.items.contains(((Pair)$$4.get()).getSecond())) {
/*    */             Optional<? extends Typed<?>> $$5 = $$3.getOptionalTyped($$1);
/*    */             if ($$5.isPresent()) {
/*    */               Typed<?> $$6 = $$5.get();
/*    */               Optional<? extends Typed<?>> $$7 = $$6.getOptionalTyped($$2);
/*    */               if ($$7.isPresent()) {
/*    */                 Optional<? extends Dynamic<?>> $$8 = $$6.write().result();
/*    */                 Dynamic<?> $$9 = $$8.isPresent() ? $$8.get() : (Dynamic)$$6.get(DSL.remainderFinder());
/*    */                 Dynamic<?> $$10 = $$9.remove("BlockEntityTag");
/*    */                 Optional<? extends Pair<? extends Typed<?>, ?>> $$11 = $$1.type().readTyped($$10).result();
/*    */                 return $$11.isEmpty() ? $$3 : $$3.set($$1, (Typed)((Pair)$$11.get()).getFirst());
/*    */               } 
/*    */             } 
/*    */           } 
/*    */           return $$3;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemRemoveBlockEntityTagFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */