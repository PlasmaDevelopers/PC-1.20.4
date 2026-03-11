/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.datafix.fixes.References;
/*    */ 
/*    */ public class V1451_4
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1451_4(int $$0, Schema $$1) {
/* 14 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 19 */     super.registerTypes($$0, $$1, $$2);
/*    */     
/* 21 */     $$0.registerType(false, References.BLOCK_NAME, () -> DSL.constType(namespacedString()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451_4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */