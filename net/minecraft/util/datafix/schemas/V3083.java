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
/*    */ public class V3083
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V3083(int $$0, Schema $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/* 20 */     $$0.register($$1, $$2, () -> DSL.optionalFields("ArmorItems", DSL.list(References.ITEM_STACK.in($$0)), "HandItems", DSL.list(References.ITEM_STACK.in($$0)), "listener", DSL.optionalFields("event", DSL.optionalFields("game_event", References.GAME_EVENT_NAME.in($$0)))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 33 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 34 */     registerMob($$0, $$1, "minecraft:allay");
/* 35 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V3083.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */