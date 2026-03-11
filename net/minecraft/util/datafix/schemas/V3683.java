/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.datafix.fixes.References;
/*    */ 
/*    */ public class V3683
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V3683(int $$0, Schema $$1) {
/* 14 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 19 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 20 */     $$0.register($$1, "minecraft:tnt", () -> DSL.optionalFields("block_state", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */     
/* 23 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V3683.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */