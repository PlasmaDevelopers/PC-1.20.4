/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.Hook;
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
/*    */ public class V102
/*    */   extends Schema
/*    */ {
/*    */   public V102(int $$0, Schema $$1) {
/* 21 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 26 */     super.registerTypes($$0, $$1, $$2);
/*    */     
/* 28 */     $$0.registerType(true, References.ITEM_STACK, () -> DSL.hook(DSL.optionalFields("id", References.ITEM_NAME.in($$0), "tag", DSL.optionalFields("EntityTag", References.ENTITY_TREE.in($$0), "BlockEntityTag", References.BLOCK_ENTITY.in($$0), "CanDestroy", DSL.list(References.BLOCK_NAME.in($$0)), "CanPlaceOn", DSL.list(References.BLOCK_NAME.in($$0)), "Items", DSL.list(References.ITEM_STACK.in($$0)))), V99.ADD_NAMES, Hook.HookFunction.IDENTITY));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V102.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */