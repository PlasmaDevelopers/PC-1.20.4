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
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class ItemBannerColorFix
/*    */   extends DataFix {
/*    */   public ItemBannerColorFix(Schema $$0, boolean $$1) {
/* 22 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 27 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/*    */     
/* 29 */     OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 30 */     OpticFinder<?> $$2 = $$0.findField("tag");
/* 31 */     OpticFinder<?> $$3 = $$2.type().findField("BlockEntityTag");
/*    */     
/* 33 */     return fixTypeEverywhereTyped("ItemBannerColorFix", $$0, $$3 -> {
/*    */           Optional<Pair<String, String>> $$4 = $$3.getOptional($$0);
/*    */           if ($$4.isPresent() && Objects.equals(((Pair)$$4.get()).getSecond(), "minecraft:banner")) {
/*    */             Dynamic<?> $$5 = (Dynamic)$$3.get(DSL.remainderFinder());
/*    */             Optional<? extends Typed<?>> $$6 = $$3.getOptionalTyped($$1);
/*    */             if ($$6.isPresent()) {
/*    */               Typed<?> $$7 = $$6.get();
/*    */               Optional<? extends Typed<?>> $$8 = $$7.getOptionalTyped($$2);
/*    */               if ($$8.isPresent()) {
/*    */                 Typed<?> $$9 = $$8.get();
/*    */                 Dynamic<?> $$10 = (Dynamic)$$7.get(DSL.remainderFinder());
/*    */                 Dynamic<?> $$11 = (Dynamic)$$9.getOrCreate(DSL.remainderFinder());
/*    */                 if ($$11.get("Base").asNumber().result().isPresent()) {
/*    */                   $$5 = $$5.set("Damage", $$5.createShort((short)($$11.get("Base").asInt(0) & 0xF)));
/*    */                   Optional<? extends Dynamic<?>> $$12 = $$10.get("display").result();
/*    */                   if ($$12.isPresent()) {
/*    */                     Dynamic<?> $$13 = $$12.get();
/*    */                     Dynamic<?> $$14 = $$13.createMap((Map)ImmutableMap.of($$13.createString("Lore"), $$13.createList(Stream.of($$13.createString("(+NBT")))));
/*    */                     if (Objects.equals($$13, $$14))
/*    */                       return $$3.set(DSL.remainderFinder(), $$5); 
/*    */                   } 
/*    */                   $$11.remove("Base");
/*    */                   return $$3.set(DSL.remainderFinder(), $$5).set($$1, $$7.set($$2, $$9.set(DSL.remainderFinder(), $$11)));
/*    */                 } 
/*    */               } 
/*    */             } 
/*    */             return $$3.set(DSL.remainderFinder(), $$5);
/*    */           } 
/*    */           return $$3;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemBannerColorFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */