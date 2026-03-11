/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SpawnerDataFix
/*    */   extends DataFix {
/*    */   public SpawnerDataFix(Schema $$0) {
/* 17 */     super($$0, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 22 */     Type<?> $$0 = getInputSchema().getType(References.UNTAGGED_SPAWNER);
/* 23 */     Type<?> $$1 = getOutputSchema().getType(References.UNTAGGED_SPAWNER);
/*    */     
/* 25 */     OpticFinder<?> $$2 = $$0.findField("SpawnData");
/* 26 */     Type<?> $$3 = $$1.findField("SpawnData").type();
/*    */     
/* 28 */     OpticFinder<?> $$4 = $$0.findField("SpawnPotentials");
/* 29 */     Type<?> $$5 = $$1.findField("SpawnPotentials").type();
/*    */     
/* 31 */     return fixTypeEverywhereTyped("Fix mob spawner data structure", $$0, $$1, $$4 -> $$4.updateTyped($$0, $$1, ()).updateTyped($$2, $$3, ()));
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
/*    */   private <T> Typed<T> wrapEntityToSpawnData(Type<T> $$0, Typed<?> $$1) {
/* 44 */     DynamicOps<?> $$2 = $$1.getOps();
/*    */     
/* 46 */     return new Typed($$0, $$2, Pair.of($$1.getValue(), new Dynamic($$2)));
/*    */   }
/*    */ 
/*    */   
/*    */   private <T> Typed<T> wrapSpawnPotentialsToWeightedEntries(Type<T> $$0, Typed<?> $$1) {
/* 51 */     DynamicOps<?> $$2 = $$1.getOps();
/* 52 */     List<?> $$3 = (List)$$1.getValue();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     List<?> $$4 = $$3.stream().map($$1 -> { Pair<Object, Dynamic<?>> $$2 = (Pair<Object, Dynamic<?>>)$$1; int $$3 = ((Number)((Dynamic)$$2.getSecond()).get("Weight").asNumber().result().orElse(Integer.valueOf(1))).intValue(); Dynamic<?> $$4 = new Dynamic($$0); $$4 = $$4.set("weight", $$4.createInt($$3)); Dynamic<?> $$5 = ((Dynamic)$$2.getSecond()).remove("Weight").remove("Entity"); return Pair.of(Pair.of($$2.getFirst(), $$5), $$4); }).toList();
/* 62 */     return new Typed($$0, $$2, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\SpawnerDataFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */