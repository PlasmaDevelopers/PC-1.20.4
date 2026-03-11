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
/*    */ 
/*    */ public class V3685
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V3685(int $$0, Schema $$1) {
/* 17 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   protected static TypeTemplate abstractArrow(Schema $$0) {
/* 21 */     return DSL.optionalFields("inBlockState", References.BLOCK_STATE
/* 22 */         .in($$0), "item", References.ITEM_STACK
/* 23 */         .in($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 29 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/* 30 */     $$0.register($$1, "minecraft:trident", () -> abstractArrow($$0));
/* 31 */     $$0.register($$1, "minecraft:spectral_arrow", () -> abstractArrow($$0));
/* 32 */     $$0.register($$1, "minecraft:arrow", () -> abstractArrow($$0));
/* 33 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V3685.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */