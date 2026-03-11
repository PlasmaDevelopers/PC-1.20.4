/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.datafix.fixes.References;
/*    */ 
/*    */ public class V3078
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V3078(int $$0, Schema $$1) {
/* 14 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/* 18 */     $$0.register($$1, $$2, () -> V100.equipment($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 23 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 24 */     registerMob($$0, $$1, "minecraft:frog");
/* 25 */     registerMob($$0, $$1, "minecraft:tadpole");
/* 26 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 31 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
/* 32 */     $$0.register($$1, "minecraft:sculk_shrieker", () -> DSL.optionalFields("listener", DSL.optionalFields("event", DSL.optionalFields("game_event", References.GAME_EVENT_NAME.in($$0)))));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 39 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V3078.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */