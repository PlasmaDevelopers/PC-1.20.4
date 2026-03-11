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
/*    */ 
/*    */ public class V1451_3
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1451_3(int $$0, Schema $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/* 23 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*    */ 
/*    */     
/* 26 */     $$0.registerSimple($$1, "minecraft:egg");
/* 27 */     $$0.registerSimple($$1, "minecraft:ender_pearl");
/* 28 */     $$0.registerSimple($$1, "minecraft:fireball");
/* 29 */     $$0.register($$1, "minecraft:potion", $$1 -> DSL.optionalFields("Potion", References.ITEM_STACK.in($$0)));
/*    */ 
/*    */     
/* 32 */     $$0.registerSimple($$1, "minecraft:small_fireball");
/* 33 */     $$0.registerSimple($$1, "minecraft:snowball");
/* 34 */     $$0.registerSimple($$1, "minecraft:wither_skull");
/* 35 */     $$0.registerSimple($$1, "minecraft:xp_bottle");
/*    */     
/* 37 */     $$0.register($$1, "minecraft:arrow", () -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */     
/* 40 */     $$0.register($$1, "minecraft:enderman", () -> DSL.optionalFields("carriedBlockState", References.BLOCK_STATE.in($$0), V100.equipment($$0)));
/*    */ 
/*    */ 
/*    */     
/* 44 */     $$0.register($$1, "minecraft:falling_block", () -> DSL.optionalFields("BlockState", References.BLOCK_STATE.in($$0), "TileEntityData", References.BLOCK_ENTITY.in($$0)));
/*    */ 
/*    */ 
/*    */     
/* 48 */     $$0.register($$1, "minecraft:spectral_arrow", () -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */     
/* 51 */     $$0.register($$1, "minecraft:chest_minecart", () -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*    */ 
/*    */ 
/*    */     
/* 55 */     $$0.register($$1, "minecraft:commandblock_minecart", () -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */     
/* 58 */     $$0.register($$1, "minecraft:furnace_minecart", () -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */     
/* 61 */     $$0.register($$1, "minecraft:hopper_minecart", () -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*    */ 
/*    */ 
/*    */     
/* 65 */     $$0.register($$1, "minecraft:minecart", () -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */     
/* 68 */     $$0.register($$1, "minecraft:spawner_minecart", () -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), References.UNTAGGED_SPAWNER.in($$0)));
/*    */ 
/*    */ 
/*    */     
/* 72 */     $$0.register($$1, "minecraft:tnt_minecart", () -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*    */ 
/*    */ 
/*    */     
/* 76 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451_3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */