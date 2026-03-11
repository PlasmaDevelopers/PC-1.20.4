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
/*    */ public class V2684
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V2684(int $$0, Schema $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 21 */     super.registerTypes($$0, $$1, $$2);
/* 22 */     $$0.registerType(false, References.GAME_EVENT_NAME, () -> DSL.constType(namespacedString()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 27 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
/* 28 */     $$0.register($$1, "minecraft:sculk_sensor", () -> DSL.optionalFields("listener", DSL.optionalFields("event", DSL.optionalFields("game_event", References.GAME_EVENT_NAME.in($$0)))));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 35 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V2684.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */