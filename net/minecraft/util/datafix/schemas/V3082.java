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
/*    */ public class V3082
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V3082(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 20 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */     
/* 22 */     $$0.register($$1, "minecraft:chest_boat", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0))));
/*    */ 
/*    */     
/* 25 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V3082.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */