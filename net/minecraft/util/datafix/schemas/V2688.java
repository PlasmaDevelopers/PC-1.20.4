/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.datafix.fixes.References;
/*    */ 
/*    */ public class V2688
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V2688(int $$0, Schema $$1) {
/* 14 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 19 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 20 */     $$0.register($$1, "minecraft:glow_squid", () -> V100.equipment($$0));
/*    */     
/* 22 */     $$0.register($$1, "minecraft:glow_item_frame", $$1 -> DSL.optionalFields("Item", References.ITEM_STACK.in($$0)));
/*    */ 
/*    */     
/* 25 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V2688.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */