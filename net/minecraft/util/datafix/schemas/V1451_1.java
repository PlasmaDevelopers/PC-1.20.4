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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class V1451_1
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V1451_1(int $$0, Schema $$1) {
/* 22 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 27 */     super.registerTypes($$0, $$1, $$2);
/*    */     
/* 29 */     $$0.registerType(false, References.CHUNK, () -> DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in($$0)), "TileEntities", DSL.list(DSL.or(References.BLOCK_ENTITY.in($$0), DSL.remainder())), "TileTicks", DSL.list(DSL.fields("i", References.BLOCK_NAME.in($$0))), "Sections", DSL.list(DSL.optionalFields("Palette", DSL.list(References.BLOCK_STATE.in($$0)))))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1451_1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */