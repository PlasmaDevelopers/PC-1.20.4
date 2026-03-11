/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class ItemShulkerBoxColorFix
/*    */   extends DataFix
/*    */ {
/*    */   public ItemShulkerBoxColorFix(Schema $$0, boolean $$1) {
/* 20 */     super($$0, $$1);
/*    */   }
/*    */   
/* 23 */   public static final String[] NAMES_BY_COLOR = new String[] { "minecraft:white_shulker_box", "minecraft:orange_shulker_box", "minecraft:magenta_shulker_box", "minecraft:light_blue_shulker_box", "minecraft:yellow_shulker_box", "minecraft:lime_shulker_box", "minecraft:pink_shulker_box", "minecraft:gray_shulker_box", "minecraft:silver_shulker_box", "minecraft:cyan_shulker_box", "minecraft:purple_shulker_box", "minecraft:blue_shulker_box", "minecraft:brown_shulker_box", "minecraft:green_shulker_box", "minecraft:red_shulker_box", "minecraft:black_shulker_box" };
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
/*    */   public TypeRewriteRule makeRule() {
/* 44 */     Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
/*    */     
/* 46 */     OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
/* 47 */     OpticFinder<?> $$2 = $$0.findField("tag");
/* 48 */     OpticFinder<?> $$3 = $$2.type().findField("BlockEntityTag");
/*    */     
/* 50 */     return fixTypeEverywhereTyped("ItemShulkerBoxColorFix", $$0, $$3 -> {
/*    */           Optional<Pair<String, String>> $$4 = $$3.getOptional($$0);
/*    */           if ($$4.isPresent() && Objects.equals(((Pair)$$4.get()).getSecond(), "minecraft:shulker_box")) {
/*    */             Optional<? extends Typed<?>> $$5 = $$3.getOptionalTyped($$1);
/*    */             if ($$5.isPresent()) {
/*    */               Typed<?> $$6 = $$5.get();
/*    */               Optional<? extends Typed<?>> $$7 = $$6.getOptionalTyped($$2);
/*    */               if ($$7.isPresent()) {
/*    */                 Typed<?> $$8 = $$7.get();
/*    */                 Dynamic<?> $$9 = (Dynamic)$$8.get(DSL.remainderFinder());
/*    */                 int $$10 = $$9.get("Color").asInt(0);
/*    */                 $$9.remove("Color");
/*    */                 return $$3.set($$1, $$6.set($$2, $$8.set(DSL.remainderFinder(), $$9))).set($$0, Pair.of(References.ITEM_NAME.typeName(), NAMES_BY_COLOR[$$10 % 16]));
/*    */               } 
/*    */             } 
/*    */           } 
/*    */           return $$3;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ItemShulkerBoxColorFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */