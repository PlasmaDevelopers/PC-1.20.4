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
/*    */ public class V703
/*    */   extends Schema
/*    */ {
/*    */   public V703(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 20 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */     
/* 22 */     $$1.remove("EntityHorse");
/* 23 */     $$0.register($$1, "Horse", () -> DSL.optionalFields("ArmorItem", References.ITEM_STACK.in($$0), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     $$0.register($$1, "Donkey", () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     $$0.register($$1, "Mule", () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 38 */     $$0.register($$1, "ZombieHorse", () -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*    */ 
/*    */ 
/*    */     
/* 42 */     $$0.register($$1, "SkeletonHorse", () -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V703.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */