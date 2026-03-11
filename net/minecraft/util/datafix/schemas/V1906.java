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
/*    */ public class V1906
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1906(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 20 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
/*    */     
/* 22 */     registerInventory($$0, $$1, "minecraft:barrel");
/* 23 */     registerInventory($$0, $$1, "minecraft:smoker");
/* 24 */     registerInventory($$0, $$1, "minecraft:blast_furnace");
/*    */     
/* 26 */     $$0.register($$1, "minecraft:lectern", $$1 -> DSL.optionalFields("Book", References.ITEM_STACK.in($$0)));
/*    */ 
/*    */ 
/*    */     
/* 30 */     $$0.registerSimple($$1, "minecraft:bell");
/*    */     
/* 32 */     return $$1;
/*    */   }
/*    */   
/*    */   protected static void registerInventory(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/* 36 */     $$0.register($$1, $$2, () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1906.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */