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
/*    */ public class V1928
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1928(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   protected static TypeTemplate equipment(Schema $$0) {
/* 19 */     return DSL.optionalFields("ArmorItems", 
/* 20 */         DSL.list(References.ITEM_STACK.in($$0)), "HandItems", 
/* 21 */         DSL.list(References.ITEM_STACK.in($$0)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/* 26 */     $$0.register($$1, $$2, () -> equipment($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 31 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */     
/* 33 */     $$1.remove("minecraft:illager_beast");
/* 34 */     registerMob($$0, $$1, "minecraft:ravager");
/*    */     
/* 36 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1928.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */