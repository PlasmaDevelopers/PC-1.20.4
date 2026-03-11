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
/*    */ public class V1451
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1451(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 20 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
/*    */     
/* 22 */     $$0.register($$1, "minecraft:trapped_chest", () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0))));
/*    */ 
/*    */ 
/*    */     
/* 26 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */