/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Arrays;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractUUIDFix
/*    */   extends DataFix
/*    */ {
/*    */   protected DSL.TypeReference typeReference;
/*    */   
/*    */   public AbstractUUIDFix(Schema $$0, DSL.TypeReference $$1) {
/* 21 */     super($$0, false);
/* 22 */     this.typeReference = $$1;
/*    */   }
/*    */   
/*    */   protected Typed<?> updateNamedChoice(Typed<?> $$0, String $$1, Function<Dynamic<?>, Dynamic<?>> $$2) {
/* 26 */     Type<?> $$3 = getInputSchema().getChoiceType(this.typeReference, $$1);
/* 27 */     Type<?> $$4 = getOutputSchema().getChoiceType(this.typeReference, $$1);
/* 28 */     return $$0.updateTyped(DSL.namedChoice($$1, $$3), $$4, $$1 -> $$1.update(DSL.remainderFinder(), $$0));
/*    */   }
/*    */   
/*    */   protected static Optional<Dynamic<?>> replaceUUIDString(Dynamic<?> $$0, String $$1, String $$2) {
/* 32 */     return createUUIDFromString($$0, $$1).map($$3 -> $$0.remove($$1).set($$2, $$3));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static Optional<Dynamic<?>> replaceUUIDMLTag(Dynamic<?> $$0, String $$1, String $$2) {
/* 38 */     return $$0.get($$1).result().flatMap(AbstractUUIDFix::createUUIDFromML).map($$3 -> $$0.remove($$1).set($$2, $$3));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static Optional<Dynamic<?>> replaceUUIDLeastMost(Dynamic<?> $$0, String $$1, String $$2) {
/* 44 */     String $$3 = $$1 + "Most";
/* 45 */     String $$4 = $$1 + "Least";
/* 46 */     return createUUIDFromLongs($$0, $$3, $$4).map($$4 -> $$0.remove($$1).remove($$2).set($$3, $$4));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static Optional<Dynamic<?>> createUUIDFromString(Dynamic<?> $$0, String $$1) {
/* 52 */     return $$0.get($$1).result().flatMap($$1 -> {
/*    */           String $$2 = $$1.asString(null);
/*    */           if ($$2 != null) {
/*    */             try {
/*    */               UUID $$3 = UUID.fromString($$2);
/*    */               return createUUIDTag($$0, $$3.getMostSignificantBits(), $$3.getLeastSignificantBits());
/* 58 */             } catch (IllegalArgumentException illegalArgumentException) {}
/*    */           }
/*    */           return Optional.empty();
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static Optional<Dynamic<?>> createUUIDFromML(Dynamic<?> $$0) {
/* 67 */     return createUUIDFromLongs($$0, "M", "L");
/*    */   }
/*    */   
/*    */   protected static Optional<Dynamic<?>> createUUIDFromLongs(Dynamic<?> $$0, String $$1, String $$2) {
/* 71 */     long $$3 = $$0.get($$1).asLong(0L);
/* 72 */     long $$4 = $$0.get($$2).asLong(0L);
/* 73 */     if ($$3 == 0L || $$4 == 0L) {
/* 74 */       return Optional.empty();
/*    */     }
/* 76 */     return createUUIDTag($$0, $$3, $$4);
/*    */   }
/*    */   
/*    */   protected static Optional<Dynamic<?>> createUUIDTag(Dynamic<?> $$0, long $$1, long $$2) {
/* 80 */     return Optional.of($$0.createIntList(Arrays.stream(new int[] { (int)($$1 >> 32L), (int)$$1, (int)($$2 >> 32L), (int)$$2 })));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\AbstractUUIDFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */