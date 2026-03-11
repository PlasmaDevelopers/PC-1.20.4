/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class FeatureFlagRemoveFix extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   public FeatureFlagRemoveFix(Schema $$0, String $$1, Set<String> $$2) {
/* 21 */     super($$0, false);
/* 22 */     this.name = $$1;
/* 23 */     this.flagsToRemove = $$2;
/*    */   }
/*    */   private final Set<String> flagsToRemove;
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 28 */     return fixTypeEverywhereTyped(this.name, getInputSchema().getType(References.LEVEL), $$0 -> $$0.update(DSL.remainderFinder(), this::fixTag));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private <T> Dynamic<T> fixTag(Dynamic<T> $$0) {
/* 34 */     List<Dynamic<T>> $$1 = (List<Dynamic<T>>)$$0.get("removed_features").asStream().collect(Collectors.toCollection(java.util.ArrayList::new));
/* 35 */     Dynamic<T> $$2 = $$0.update("enabled_features", $$2 -> {
/*    */           Objects.requireNonNull($$1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           return (Dynamic)DataFixUtils.orElse($$2.asStreamOpt().result().map(()).map($$1::createList), $$2);
/*    */         });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     if (!$$1.isEmpty()) {
/* 50 */       $$2 = $$2.set("removed_features", $$0.createList($$1.stream()));
/*    */     }
/* 52 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\FeatureFlagRemoveFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */