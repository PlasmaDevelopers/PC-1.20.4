/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public class MobSpawnerEntityIdentifiersFix
/*    */   extends DataFix {
/*    */   public MobSpawnerEntityIdentifiersFix(Schema $$0, boolean $$1) {
/* 19 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   private Dynamic<?> fix(Dynamic<?> $$0) {
/* 23 */     if (!"MobSpawner".equals($$0.get("id").asString(""))) {
/* 24 */       return $$0;
/*    */     }
/*    */     
/* 27 */     Optional<String> $$1 = $$0.get("EntityId").asString().result();
/* 28 */     if ($$1.isPresent()) {
/* 29 */       Dynamic<?> $$2 = (Dynamic)DataFixUtils.orElse($$0.get("SpawnData").result(), $$0.emptyMap());
/* 30 */       $$2 = $$2.set("id", $$2.createString(((String)$$1.get()).isEmpty() ? "Pig" : $$1.get()));
/* 31 */       $$0 = $$0.set("SpawnData", $$2);
/*    */       
/* 33 */       $$0 = $$0.remove("EntityId");
/*    */     } 
/*    */     
/* 36 */     Optional<? extends Stream<? extends Dynamic<?>>> $$3 = $$0.get("SpawnPotentials").asStreamOpt().result();
/* 37 */     if ($$3.isPresent()) {
/* 38 */       $$0 = $$0.set("SpawnPotentials", $$0.createList(((Stream)$$3.get()).map($$0 -> {
/*    */                 Optional<String> $$1 = $$0.get("Type").asString().result();
/*    */                 
/*    */                 if ($$1.isPresent()) {
/*    */                   Dynamic<?> $$2 = ((Dynamic)DataFixUtils.orElse($$0.get("Properties").result(), $$0.emptyMap())).set("id", $$0.createString($$1.get()));
/*    */                   
/*    */                   return $$0.set("Entity", $$2).remove("Type").remove("Properties");
/*    */                 } 
/*    */                 
/*    */                 return $$0;
/*    */               })));
/*    */     }
/*    */     
/* 51 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 56 */     Type<?> $$0 = getOutputSchema().getType(References.UNTAGGED_SPAWNER);
/* 57 */     return fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", getInputSchema().getType(References.UNTAGGED_SPAWNER), $$0, $$1 -> {
/*    */           Dynamic<?> $$2 = (Dynamic)$$1.get(DSL.remainderFinder());
/*    */           $$2 = $$2.set("id", $$2.createString("MobSpawner"));
/*    */           DataResult<? extends Pair<? extends Typed<?>, ?>> $$3 = $$0.readTyped(fix($$2));
/*    */           return $$3.result().isEmpty() ? $$1 : (Typed)((Pair)$$3.result().get()).getFirst();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\MobSpawnerEntityIdentifiersFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */