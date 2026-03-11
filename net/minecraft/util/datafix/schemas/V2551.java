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
/*    */ public class V2551
/*    */   extends NamespacedSchema
/*    */ {
/*    */   public V2551(int $$0, Schema $$1) {
/* 25 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 30 */     super.registerTypes($$0, $$1, $$2);
/*    */     
/* 32 */     $$0.registerType(false, References.WORLD_GEN_SETTINGS, () -> DSL.fields("dimensions", DSL.compoundList(DSL.constType(namespacedString()), DSL.fields("generator", (TypeTemplate)DSL.taggedChoiceLazy("type", DSL.string(), (Map)ImmutableMap.of("minecraft:debug", DSL::remainder, "minecraft:flat", (), "minecraft:noise", ()))))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V2551.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */