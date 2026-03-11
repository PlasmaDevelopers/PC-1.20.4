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
/*    */ public class V2100
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V2100(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/* 19 */     $$0.register($$1, $$2, () -> V100.equipment($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 24 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 25 */     registerMob($$0, $$1, "minecraft:bee");
/* 26 */     registerMob($$0, $$1, "minecraft:bee_stinger");
/* 27 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 32 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
/*    */     
/* 34 */     $$0.register($$1, "minecraft:beehive", () -> DSL.optionalFields("Bees", DSL.list(DSL.optionalFields("EntityData", References.ENTITY_TREE.in($$0)))));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V2100.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */