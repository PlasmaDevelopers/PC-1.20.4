/*    */ package net.minecraft.util.datafix.schemas;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class V2832
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V2832(int $$0, Schema $$1) {
/* 32 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 37 */     super.registerTypes($$0, $$1, $$2);
/*    */     
/* 39 */     $$0.registerType(false, References.CHUNK, () -> DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in($$0)), "TileEntities", DSL.list(DSL.or(References.BLOCK_ENTITY.in($$0), DSL.remainder())), "TileTicks", DSL.list(DSL.fields("i", References.BLOCK_NAME.in($$0))), "Sections", DSL.list(DSL.optionalFields("biomes", DSL.optionalFields("palette", DSL.list(References.BIOME.in($$0))), "block_states", DSL.optionalFields("palette", DSL.list(References.BLOCK_STATE.in($$0))))), "Structures", DSL.optionalFields("Starts", DSL.compoundList(References.STRUCTURE_FEATURE.in($$0))))));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     $$0.registerType(false, References.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, () -> DSL.constType(namespacedString()));
/*    */     
/* 60 */     $$0.registerType(false, References.WORLD_GEN_SETTINGS, () -> DSL.fields("dimensions", DSL.compoundList(DSL.constType(namespacedString()), DSL.fields("generator", (TypeTemplate)DSL.taggedChoiceLazy("type", DSL.string(), (Map)ImmutableMap.of("minecraft:debug", DSL::remainder, "minecraft:flat", (), "minecraft:noise", ()))))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V2832.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */