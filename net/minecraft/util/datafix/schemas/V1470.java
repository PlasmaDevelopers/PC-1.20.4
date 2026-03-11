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
/*    */ public class V1470
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1470(int $$0, Schema $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/* 19 */     $$0.register($$1, $$2, () -> V100.equipment($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 24 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */ 
/*    */     
/* 27 */     registerMob($$0, $$1, "minecraft:turtle");
/* 28 */     registerMob($$0, $$1, "minecraft:cod_mob");
/* 29 */     registerMob($$0, $$1, "minecraft:tropical_fish");
/* 30 */     registerMob($$0, $$1, "minecraft:salmon_mob");
/* 31 */     registerMob($$0, $$1, "minecraft:puffer_fish");
/* 32 */     registerMob($$0, $$1, "minecraft:phantom");
/* 33 */     registerMob($$0, $$1, "minecraft:dolphin");
/* 34 */     registerMob($$0, $$1, "minecraft:drowned");
/*    */     
/* 36 */     $$0.register($$1, "minecraft:trident", $$1 -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0), "Trident", References.ITEM_STACK.in($$0)));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1470.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */