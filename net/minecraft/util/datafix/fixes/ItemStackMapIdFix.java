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
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class ItemStackMapIdFix
/*    */   extends DataFix
/*    */ {
/*    */   public ItemStackMapIdFix(Schema $$0, boolean $$1) {
/* 20 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 25 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/*    */     
/* 27 */     OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 28 */     OpticFinder<?> $$2 = $$0.findField("tag");
/*    */     
/* 30 */     return fixTypeEverywhereTyped("ItemInstanceMapIdFix", $$0, $$2 -> {
/*    */           Optional<Pair<String, String>> $$3 = $$2.getOptional($$0);
/*    */           if ($$3.isPresent() && Objects.equals(((Pair)$$3.get()).getSecond(), "minecraft:filled_map")) {
/*    */             Dynamic<?> $$4 = (Dynamic)$$2.get(DSL.remainderFinder());
/*    */             Typed<?> $$5 = $$2.getOrCreateTyped($$1);
/*    */             Dynamic<?> $$6 = (Dynamic)$$5.get(DSL.remainderFinder());
/*    */             $$6 = $$6.set("map", $$6.createInt($$4.get("Damage").asInt(0)));
/*    */             return $$2.set($$1, $$5.set(DSL.remainderFinder(), $$6));
/*    */           } 
/*    */           return $$2;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemStackMapIdFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */