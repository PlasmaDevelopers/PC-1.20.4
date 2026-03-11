/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class V1486
/*    */   extends NamespacedSchema {
/*    */   public V1486(int $$0, Schema $$1) {
/* 11 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 16 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */     
/* 18 */     $$1.put("minecraft:cod", $$1.remove("minecraft:cod_mob"));
/* 19 */     $$1.put("minecraft:salmon", $$1.remove("minecraft:salmon_mob"));
/*    */     
/* 21 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1486.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */