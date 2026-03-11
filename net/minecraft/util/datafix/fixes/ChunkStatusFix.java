/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class ChunkStatusFix extends DataFix {
/*    */   public ChunkStatusFix(Schema $$0, boolean $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 20 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 21 */     Type<?> $$1 = $$0.findFieldType("Level");
/*    */     
/* 23 */     OpticFinder<?> $$2 = DSL.fieldFinder("Level", $$1);
/*    */     
/* 25 */     return fixTypeEverywhereTyped("ChunkStatusFix", $$0, getOutputSchema().getType(References.CHUNK), $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkStatusFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */