/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.datafix.fixes.References;
/*    */ 
/*    */ 
/*    */ public class V1929
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1929(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 20 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 21 */     $$0.register($$1, "minecraft:wandering_trader", $$1 -> DSL.optionalFields("Inventory", DSL.list(References.ITEM_STACK.in($$0)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.ITEM_STACK.in($$0), "buyB", References.ITEM_STACK.in($$0), "sell", References.ITEM_STACK.in($$0)))), V100.equipment($$0)));
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
/* 35 */     $$0.register($$1, "minecraft:trader_llama", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), "DecorItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1929.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */