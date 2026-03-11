/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.util.datafix.fixes.References;
/*    */ 
/*    */ public class V1451_2
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1451_2(int $$0, Schema $$1) {
/* 14 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 19 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
/*    */     
/* 21 */     $$0.register($$1, "minecraft:piston", $$1 -> DSL.optionalFields("blockState", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */ 
/*    */     
/* 25 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451_2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */