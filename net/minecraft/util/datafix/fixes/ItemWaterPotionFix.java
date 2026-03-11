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
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class ItemWaterPotionFix
/*    */   extends DataFix
/*    */ {
/*    */   public ItemWaterPotionFix(Schema $$0, boolean $$1) {
/* 19 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 24 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/*    */     
/* 26 */     OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 27 */     OpticFinder<?> $$2 = $$0.findField("tag");
/*    */     
/* 29 */     return fixTypeEverywhereTyped("ItemWaterPotionFix", $$0, $$2 -> {
/*    */           Optional<Pair<String, String>> $$3 = $$2.getOptional($$0);
/*    */           if ($$3.isPresent()) {
/*    */             String $$4 = (String)((Pair)$$3.get()).getSecond();
/*    */             if ("minecraft:potion".equals($$4) || "minecraft:splash_potion".equals($$4) || "minecraft:lingering_potion".equals($$4) || "minecraft:tipped_arrow".equals($$4)) {
/*    */               Typed<?> $$5 = $$2.getOrCreateTyped($$1);
/*    */               Dynamic<?> $$6 = (Dynamic)$$5.get(DSL.remainderFinder());
/*    */               if ($$6.get("Potion").asString().result().isEmpty())
/*    */                 $$6 = $$6.set("Potion", $$6.createString("minecraft:water")); 
/*    */               return $$2.set($$1, $$5.set(DSL.remainderFinder(), $$6));
/*    */             } 
/*    */           } 
/*    */           return $$2;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemWaterPotionFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */