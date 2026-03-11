/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class V701
/*    */   extends Schema {
/*    */   public V701(int $$0, Schema $$1) {
/* 11 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/* 15 */     $$0.register($$1, $$2, () -> V100.equipment($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 20 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */     
/* 22 */     registerMob($$0, $$1, "WitherSkeleton");
/* 23 */     registerMob($$0, $$1, "Stray");
/*    */     
/* 25 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V701.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */