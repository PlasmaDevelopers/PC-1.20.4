/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class V2707
/*    */   extends NamespacedSchema {
/*    */   public V2707(int $$0, Schema $$1) {
/* 11 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 16 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 17 */     registerSimple($$1, "minecraft:marker");
/* 18 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V2707.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */