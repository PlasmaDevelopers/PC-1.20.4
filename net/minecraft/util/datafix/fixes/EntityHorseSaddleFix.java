/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class EntityHorseSaddleFix
/*    */   extends NamedEntityFix {
/*    */   public EntityHorseSaddleFix(Schema $$0, boolean $$1) {
/* 17 */     super($$0, $$1, "EntityHorseSaddleFix", References.ENTITY, "EntityHorse");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Typed<?> fix(Typed<?> $$0) {
/* 24 */     OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 25 */     Type<?> $$2 = getInputSchema().getTypeRaw(References.ITEM_STACK);
/* 26 */     OpticFinder<?> $$3 = DSL.fieldFinder("SaddleItem", $$2);
/*    */     
/* 28 */     Optional<? extends Typed<?>> $$4 = $$0.getOptionalTyped($$3);
/* 29 */     Dynamic<?> $$5 = (Dynamic)$$0.get(DSL.remainderFinder());
/* 30 */     if ($$4.isEmpty() && $$5.get("Saddle").asBoolean(false)) {
/* 31 */       Typed<?> $$6 = (Typed)$$2.pointTyped($$0.getOps()).orElseThrow(IllegalStateException::new);
/* 32 */       $$6 = $$6.set($$1, Pair.of(References.ITEM_NAME.typeName(), "minecraft:saddle"));
/*    */       
/* 34 */       Dynamic<?> $$7 = $$5.emptyMap();
/* 35 */       $$7 = $$7.set("Count", $$7.createByte((byte)1));
/* 36 */       $$7 = $$7.set("Damage", $$7.createShort((short)0));
/*    */       
/* 38 */       $$6 = $$6.set(DSL.remainderFinder(), $$7);
/* 39 */       $$5.remove("Saddle");
/*    */       
/* 41 */       return $$0.set($$3, $$6).set(DSL.remainderFinder(), $$5);
/*    */     } 
/* 43 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityHorseSaddleFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */