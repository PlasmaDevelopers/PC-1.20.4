/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.OptionalDynamic;
/*    */ 
/*    */ public class BlendingDataRemoveFromNetherEndFix extends DataFix {
/*    */   public BlendingDataRemoveFromNetherEndFix(Schema $$0) {
/* 13 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 18 */     Type<?> $$0 = getOutputSchema().getType(References.CHUNK);
/*    */     
/* 20 */     return fixTypeEverywhereTyped("BlendingDataRemoveFromNetherEndFix", $$0, $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Dynamic<?> updateChunkTag(Dynamic<?> $$0, OptionalDynamic<?> $$1) {
/* 26 */     boolean $$2 = "minecraft:overworld".equals($$1.get("dimension").asString().result().orElse(""));
/* 27 */     return $$2 ? $$0 : $$0.remove("blending_data");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlendingDataRemoveFromNetherEndFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */