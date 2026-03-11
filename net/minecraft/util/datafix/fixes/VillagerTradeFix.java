/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.List;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class VillagerTradeFix
/*    */   extends NamedEntityFix
/*    */ {
/*    */   public VillagerTradeFix(Schema $$0, boolean $$1) {
/* 18 */     super($$0, $$1, "Villager trade fix", References.ENTITY, "minecraft:villager");
/*    */   }
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 23 */     OpticFinder<?> $$1 = $$0.getType().findField("Offers");
/* 24 */     OpticFinder<?> $$2 = $$1.type().findField("Recipes");
/* 25 */     Type<?> $$3 = $$2.type();
/* 26 */     if (!($$3 instanceof List.ListType)) {
/* 27 */       throw new IllegalStateException("Recipes are expected to be a list.");
/*    */     }
/* 29 */     List.ListType<?> $$4 = (List.ListType)$$3;
/* 30 */     Type<?> $$5 = $$4.getElement();
/* 31 */     OpticFinder<?> $$6 = DSL.typeFinder($$5);
/* 32 */     OpticFinder<?> $$7 = $$5.findField("buy");
/* 33 */     OpticFinder<?> $$8 = $$5.findField("buyB");
/* 34 */     OpticFinder<?> $$9 = $$5.findField("sell");
/*    */     
/* 36 */     OpticFinder<Pair<String, String>> $$10 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 37 */     Function<Typed<?>, Typed<?>> $$11 = $$1 -> updateItemStack($$0, $$1);
/*    */     
/* 39 */     return $$0.updateTyped($$1, $$6 -> $$6.updateTyped($$0, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Typed<?> updateItemStack(OpticFinder<Pair<String, String>> $$0, Typed<?> $$1) {
/* 48 */     return $$1.update($$0, $$0 -> $$0.mapSecond(()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\VillagerTradeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */