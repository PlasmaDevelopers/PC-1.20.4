/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class V2509
/*    */   extends NamespacedSchema {
/*    */   public V2509(int $$0, Schema $$1) {
/* 11 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 16 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 17 */     $$1.remove("minecraft:zombie_pigman");
/* 18 */     $$0.register($$1, "minecraft:zombified_piglin", () -> V100.equipment($$0));
/* 19 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V2509.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */