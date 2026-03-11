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
/*    */ public class V135
/*    */   extends Schema
/*    */ {
/*    */   public V135(int $$0, Schema $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 23 */     super.registerTypes($$0, $$1, $$2);
/*    */     
/* 25 */     $$0.registerType(false, References.PLAYER, () -> DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", References.ENTITY_TREE.in($$0)), "Inventory", DSL.list(References.ITEM_STACK.in($$0)), "EnderItems", DSL.list(References.ITEM_STACK.in($$0))));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     $$0.registerType(true, References.ENTITY_TREE, () -> DSL.optionalFields("Passengers", DSL.list(References.ENTITY_TREE.in($$0)), References.ENTITY.in($$0)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V135.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */