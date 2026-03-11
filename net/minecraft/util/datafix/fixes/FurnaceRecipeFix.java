/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.datafixers.util.Unit;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FurnaceRecipeFix
/*    */   extends DataFix
/*    */ {
/*    */   public FurnaceRecipeFix(Schema $$0, boolean $$1) {
/* 29 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 34 */     return cap(getOutputSchema().getTypeRaw(References.RECIPE));
/*    */   }
/*    */   
/*    */   private <R> TypeRewriteRule cap(Type<R> $$0) {
/* 38 */     Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> $$1 = DSL.and(
/* 39 */         DSL.optional((Type)DSL.field("RecipesUsed", DSL.and((Type)DSL.compoundList($$0, DSL.intType()), DSL.remainderType()))), 
/* 40 */         DSL.remainderType());
/*    */ 
/*    */     
/* 43 */     OpticFinder<?> $$2 = DSL.namedChoice("minecraft:furnace", getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:furnace"));
/* 44 */     OpticFinder<?> $$3 = DSL.namedChoice("minecraft:blast_furnace", getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:blast_furnace"));
/* 45 */     OpticFinder<?> $$4 = DSL.namedChoice("minecraft:smoker", getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:smoker"));
/*    */     
/* 47 */     Type<?> $$5 = getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:furnace");
/* 48 */     Type<?> $$6 = getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:blast_furnace");
/* 49 */     Type<?> $$7 = getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:smoker");
/*    */     
/* 51 */     Type<?> $$8 = getInputSchema().getType(References.BLOCK_ENTITY);
/* 52 */     Type<?> $$9 = getOutputSchema().getType(References.BLOCK_ENTITY);
/* 53 */     return fixTypeEverywhereTyped("FurnaceRecipesFix", $$8, $$9, $$8 -> $$8.updateTyped($$0, $$1, ()).updateTyped($$4, $$5, ()).updateTyped($$6, $$7, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private <R> Typed<?> updateFurnaceContents(Type<R> $$0, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> $$1, Typed<?> $$2) {
/* 62 */     Dynamic<?> $$3 = (Dynamic)$$2.getOrCreate(DSL.remainderFinder());
/*    */     
/* 64 */     int $$4 = $$3.get("RecipesUsedSize").asInt(0);
/* 65 */     $$3 = $$3.remove("RecipesUsedSize");
/*    */     
/* 67 */     List<Pair<R, Integer>> $$5 = Lists.newArrayList();
/* 68 */     for (int $$6 = 0; $$6 < $$4; $$6++) {
/* 69 */       String $$7 = "RecipeLocation" + $$6;
/* 70 */       String $$8 = "RecipeAmount" + $$6;
/*    */       
/* 72 */       Optional<? extends Dynamic<?>> $$9 = $$3.get($$7).result();
/* 73 */       int $$10 = $$3.get($$8).asInt(0);
/* 74 */       if ($$10 > 0) {
/* 75 */         $$9.ifPresent($$3 -> {
/*    */               Optional<? extends Pair<R, ? extends Dynamic<?>>> $$4 = $$0.read($$3).result();
/*    */               
/*    */               $$4.ifPresent(());
/*    */             });
/*    */       }
/* 81 */       $$3 = $$3.remove($$7).remove($$8);
/*    */     } 
/*    */     
/* 84 */     return $$2.set(DSL.remainderFinder(), $$1, Pair.of(Either.left(Pair.of($$5, $$3.emptyMap())), $$3));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\FurnaceRecipeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */