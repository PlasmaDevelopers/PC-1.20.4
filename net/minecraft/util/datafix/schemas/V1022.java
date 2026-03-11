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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class V1022
/*    */   extends Schema
/*    */ {
/*    */   public V1022(int $$0, Schema $$1) {
/* 22 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 27 */     super.registerTypes($$0, $$1, $$2);
/*    */     
/* 29 */     $$0.registerType(false, References.RECIPE, () -> DSL.constType(NamespacedSchema.namespacedString()));
/* 30 */     $$0.registerType(false, References.PLAYER, () -> DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", References.ENTITY_TREE.in($$0)), "Inventory", DSL.list(References.ITEM_STACK.in($$0)), "EnderItems", DSL.list(References.ITEM_STACK.in($$0)), DSL.optionalFields("ShoulderEntityLeft", References.ENTITY_TREE.in($$0), "ShoulderEntityRight", References.ENTITY_TREE.in($$0), "recipeBook", DSL.optionalFields("recipes", DSL.list(References.RECIPE.in($$0)), "toBeDisplayed", DSL.list(References.RECIPE.in($$0))))));
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
/* 46 */     $$0.registerType(false, References.HOTBAR, () -> DSL.compoundList(DSL.list(References.ITEM_STACK.in($$0))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1022.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */