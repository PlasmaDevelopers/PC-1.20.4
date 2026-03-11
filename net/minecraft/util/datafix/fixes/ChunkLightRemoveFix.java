/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class ChunkLightRemoveFix extends DataFix {
/*    */   public ChunkLightRemoveFix(Schema $$0, boolean $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 17 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 18 */     Type<?> $$1 = $$0.findFieldType("Level");
/*    */     
/* 20 */     OpticFinder<?> $$2 = DSL.fieldFinder("Level", $$1);
/*    */     
/* 22 */     return fixTypeEverywhereTyped("ChunkLightRemoveFix", $$0, getOutputSchema().getType(References.CHUNK), $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkLightRemoveFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */