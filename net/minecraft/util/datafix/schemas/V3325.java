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
/*    */ public class V3325
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V3325(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 20 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */     
/* 22 */     $$0.register($$1, "minecraft:item_display", $$1 -> DSL.optionalFields("item", References.ITEM_STACK.in($$0)));
/*    */ 
/*    */ 
/*    */     
/* 26 */     $$0.register($$1, "minecraft:block_display", $$1 -> DSL.optionalFields("block_state", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */     
/* 29 */     $$0.registerSimple($$1, "minecraft:text_display");
/*    */     
/* 31 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V3325.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */