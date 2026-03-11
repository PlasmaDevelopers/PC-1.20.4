/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.datafixers.util.Unit;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class EntityRidingToPassengersFix extends DataFix {
/*    */   public EntityRidingToPassengersFix(Schema $$0, boolean $$1) {
/* 22 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 27 */     Schema $$0 = getInputSchema();
/* 28 */     Schema $$1 = getOutputSchema();
/*    */     
/* 30 */     Type<?> $$2 = $$0.getTypeRaw(References.ENTITY_TREE);
/* 31 */     Type<?> $$3 = $$1.getTypeRaw(References.ENTITY_TREE);
/* 32 */     Type<?> $$4 = $$0.getTypeRaw(References.ENTITY);
/*    */     
/* 34 */     return cap($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule cap(Schema $$0, Schema $$1, Type<OldEntityTree> $$2, Type<NewEntityTree> $$3, Type<Entity> $$4) {
/* 38 */     Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> $$5 = DSL.named(References.ENTITY_TREE.typeName(), DSL.and(
/* 39 */           DSL.optional((Type)DSL.field("Riding", $$2)), $$4));
/*    */ 
/*    */ 
/*    */     
/* 43 */     Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> $$6 = DSL.named(References.ENTITY_TREE.typeName(), DSL.and(
/* 44 */           DSL.optional((Type)DSL.field("Passengers", (Type)DSL.list($$3))), $$4));
/*    */ 
/*    */ 
/*    */     
/* 48 */     Type<?> $$7 = $$0.getType(References.ENTITY_TREE);
/* 49 */     Type<?> $$8 = $$1.getType(References.ENTITY_TREE);
/*    */     
/* 51 */     if (!Objects.equals($$7, $$5)) {
/* 52 */       throw new IllegalStateException("Old entity type is not what was expected.");
/*    */     }
/*    */     
/* 55 */     if (!$$8.equals($$6, true, true)) {
/* 56 */       throw new IllegalStateException("New entity type is not what was expected.");
/*    */     }
/*    */     
/* 59 */     OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> $$9 = DSL.typeFinder($$5);
/* 60 */     OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> $$10 = DSL.typeFinder($$6);
/* 61 */     OpticFinder<NewEntityTree> $$11 = DSL.typeFinder($$3);
/*    */     
/* 63 */     Type<?> $$12 = $$0.getType(References.PLAYER);
/* 64 */     Type<?> $$13 = $$1.getType(References.PLAYER);
/*    */     
/* 66 */     return TypeRewriteRule.seq(
/* 67 */         fixTypeEverywhere("EntityRidingToPassengerFix", $$5, $$6, $$5 -> ()), 
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
/*    */ 
/*    */         
/* 90 */         writeAndRead("player RootVehicle injecter", $$12, $$13));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityRidingToPassengersFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */