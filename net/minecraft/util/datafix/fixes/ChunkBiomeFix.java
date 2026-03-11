/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Arrays;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.IntStream;
/*    */ 
/*    */ public class ChunkBiomeFix extends DataFix {
/*    */   public ChunkBiomeFix(Schema $$0, boolean $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 21 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 22 */     OpticFinder<?> $$1 = $$0.findField("Level");
/*    */     
/* 24 */     return fixTypeEverywhereTyped("Leaves fix", $$0, $$1 -> $$1.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkBiomeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */