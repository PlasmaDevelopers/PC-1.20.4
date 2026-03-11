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
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityEquipmentToArmorAndHandFix
/*    */   extends DataFix
/*    */ {
/*    */   public EntityEquipmentToArmorAndHandFix(Schema $$0, boolean $$1) {
/* 31 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 36 */     return cap(getInputSchema().getTypeRaw(References.ITEM_STACK));
/*    */   }
/*    */ 
/*    */   
/*    */   private <IS> TypeRewriteRule cap(Type<IS> $$0) {
/* 41 */     Type<Pair<Either<List<IS>, Unit>, Dynamic<?>>> $$1 = DSL.and(
/* 42 */         DSL.optional((Type)DSL.field("Equipment", (Type)DSL.list($$0))), 
/* 43 */         DSL.remainderType());
/*    */ 
/*    */     
/* 46 */     Type<Pair<Either<List<IS>, Unit>, Pair<Either<List<IS>, Unit>, Dynamic<?>>>> $$2 = DSL.and(
/* 47 */         DSL.optional((Type)DSL.field("ArmorItems", (Type)DSL.list($$0))), 
/* 48 */         DSL.optional((Type)DSL.field("HandItems", (Type)DSL.list($$0))), 
/* 49 */         DSL.remainderType());
/*    */     
/* 51 */     OpticFinder<Pair<Either<List<IS>, Unit>, Dynamic<?>>> $$3 = DSL.typeFinder($$1);
/*    */     
/* 53 */     OpticFinder<List<IS>> $$4 = DSL.fieldFinder("Equipment", (Type)DSL.list($$0));
/*    */     
/* 55 */     return fixTypeEverywhereTyped("EntityEquipmentToArmorAndHandFix", getInputSchema().getType(References.ENTITY), getOutputSchema().getType(References.ENTITY), $$4 -> {
/*    */           Either<List<IS>, Unit> $$5 = Either.right(DSL.unit());
/*    */           Either<List<IS>, Unit> $$6 = Either.right(DSL.unit());
/*    */           Dynamic<?> $$7 = (Dynamic)$$4.getOrCreate(DSL.remainderFinder());
/*    */           Optional<List<IS>> $$8 = $$4.getOptional($$0);
/*    */           if ($$8.isPresent()) {
/*    */             List<IS> $$9 = $$8.get();
/*    */             IS $$10 = (IS)((Pair)$$1.read($$7.emptyMap()).result().orElseThrow(())).getFirst();
/*    */             if (!$$9.isEmpty())
/*    */               $$5 = Either.left(Lists.newArrayList(new Object[] { $$9.get(0), $$10 })); 
/*    */             if ($$9.size() > 1) {
/*    */               List<IS> $$11 = Lists.newArrayList(new Object[] { $$10, $$10, $$10, $$10 });
/*    */               for (int $$12 = 1; $$12 < Math.min($$9.size(), 5); $$12++)
/*    */                 $$11.set($$12 - 1, $$9.get($$12)); 
/*    */               $$6 = Either.left($$11);
/*    */             } 
/*    */           } 
/*    */           Dynamic<?> $$13 = $$7;
/*    */           Optional<? extends Stream<? extends Dynamic<?>>> $$14 = $$7.get("DropChances").asStreamOpt().result();
/*    */           if ($$14.isPresent()) {
/*    */             Iterator<? extends Dynamic<?>> $$15 = Stream.<Dynamic<?>>concat($$14.get(), Stream.generate(())).iterator();
/*    */             float $$16 = ((Dynamic)$$15.next()).asFloat(0.0F);
/*    */             if ($$7.get("HandDropChances").result().isEmpty()) {
/*    */               Objects.requireNonNull($$7);
/*    */               Dynamic<?> $$17 = $$7.createList(Stream.<Float>of(new Float[] { Float.valueOf($$16), Float.valueOf(0.0F) }).map($$7::createFloat));
/*    */               $$7 = $$7.set("HandDropChances", $$17);
/*    */             } 
/*    */             if ($$7.get("ArmorDropChances").result().isEmpty()) {
/*    */               Objects.requireNonNull($$7);
/*    */               Dynamic<?> $$18 = $$7.createList(Stream.<Float>of(new Float[] { Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0F)), Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0F)), Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0F)), Float.valueOf(((Dynamic)$$15.next()).asFloat(0.0F)) }).map($$7::createFloat));
/*    */               $$7 = $$7.set("ArmorDropChances", $$18);
/*    */             } 
/*    */             $$7 = $$7.remove("DropChances");
/*    */           } 
/*    */           return $$4.set($$2, $$3, Pair.of($$5, Pair.of($$6, $$7)));
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityEquipmentToArmorAndHandFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */