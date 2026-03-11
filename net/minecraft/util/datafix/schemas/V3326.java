/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class V3326
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V3326(int $$0, Schema $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 17 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 18 */     $$0.register($$1, "minecraft:sniffer", () -> V100.equipment($$0));
/* 19 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V3326.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */