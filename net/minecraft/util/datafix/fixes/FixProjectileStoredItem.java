/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public class FixProjectileStoredItem
/*    */   extends DataFix {
/*    */   private static final String EMPTY_POTION = "minecraft:empty";
/*    */   
/*    */   public FixProjectileStoredItem(Schema $$0) {
/* 21 */     super($$0, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 26 */     Type<?> $$0 = getInputSchema().getType(References.ENTITY);
/* 27 */     Type<?> $$1 = getOutputSchema().getType(References.ENTITY);
/*    */     
/* 29 */     return fixTypeEverywhereTyped("Fix AbstractArrow item type", $$0, $$1, chainAllFilters((Function<Typed<?>, Typed<?>>[])new Function[] {
/* 30 */             fixChoice("minecraft:trident", FixProjectileStoredItem::castUnchecked), 
/* 31 */             fixChoice("minecraft:arrow", FixProjectileStoredItem::fixArrow), 
/* 32 */             fixChoice("minecraft:spectral_arrow", FixProjectileStoredItem::fixSpectralArrow)
/*    */           }));
/*    */   }
/*    */   
/*    */   @SafeVarargs
/*    */   private <T> Function<Typed<?>, Typed<?>> chainAllFilters(Function<Typed<?>, Typed<?>>... $$0) {
/* 38 */     return $$1 -> {
/*    */         for (Function<Typed<?>, Typed<?>> $$2 : $$0) {
/*    */           $$1 = $$2.apply($$1);
/*    */         }
/*    */         return $$1;
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Function<Typed<?>, Typed<?>> fixChoice(String $$0, SubFixer<?> $$1) {
/* 51 */     Type<?> $$2 = getInputSchema().getChoiceType(References.ENTITY, $$0);
/* 52 */     Type<?> $$3 = getOutputSchema().getChoiceType(References.ENTITY, $$0);
/*    */     
/* 54 */     return fixChoiceCap($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   private static <T> Function<Typed<?>, Typed<?>> fixChoiceCap(String $$0, SubFixer<?> $$1, Type<?> $$2, Type<T> $$3) {
/* 58 */     OpticFinder<?> $$4 = DSL.namedChoice($$0, $$2);
/* 59 */     SubFixer<T> $$5 = (SubFixer)$$1;
/* 60 */     return $$3 -> $$3.updateTyped($$0, $$1, ());
/*    */   }
/*    */   
/*    */   private static <T> Typed<T> fixArrow(Typed<?> $$0, Type<T> $$1) {
/* 64 */     return Util.writeAndReadTypedOrThrow($$0, $$1, $$0 -> $$0.set("item", createItemStack($$0, getArrowType($$0))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static String getArrowType(Dynamic<?> $$0) {
/* 70 */     return $$0.get("Potion").asString("minecraft:empty").equals("minecraft:empty") ? "minecraft:arrow" : "minecraft:tipped_arrow";
/*    */   }
/*    */   
/*    */   private static <T> Typed<T> fixSpectralArrow(Typed<?> $$0, Type<T> $$1) {
/* 74 */     return Util.writeAndReadTypedOrThrow($$0, $$1, $$0 -> $$0.set("item", createItemStack($$0, "minecraft:spectral_arrow")));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Dynamic<?> createItemStack(Dynamic<?> $$0, String $$1) {
/* 80 */     return $$0.createMap((Map)ImmutableMap.of($$0
/* 81 */           .createString("id"), $$0.createString($$1), $$0
/* 82 */           .createString("Count"), $$0.createInt(1)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> Typed<T> castUnchecked(Typed<?> $$0, Type<T> $$1) {
/* 88 */     return new Typed($$1, $$0.getOps(), $$0.getValue());
/*    */   }
/*    */   
/*    */   private static interface SubFixer<F> {
/*    */     Typed<F> fix(Typed<?> param1Typed, Type<F> param1Type);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\FixProjectileStoredItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */