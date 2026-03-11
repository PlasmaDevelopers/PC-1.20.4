/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ 
/*    */ public class ChunkDeleteLightFix extends DataFix {
/*    */   public ChunkDeleteLightFix(Schema $$0) {
/* 12 */     super($$0, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 17 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 18 */     OpticFinder<?> $$1 = $$0.findField("sections");
/*    */     
/* 20 */     return fixTypeEverywhereTyped("ChunkDeleteLightFix for " + getOutputSchema().getVersionKey(), $$0, $$1 -> {
/*    */           $$1 = $$1.update(DSL.remainderFinder(), ());
/*    */           return $$1.updateTyped($$0, ());
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkDeleteLightFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */