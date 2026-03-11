/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class BlockNameFlatteningFix
/*    */   extends DataFix {
/*    */   public BlockNameFlatteningFix(Schema $$0, boolean $$1) {
/* 18 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 23 */     Type<?> $$0 = getInputSchema().getType(References.BLOCK_NAME);
/* 24 */     Type<?> $$1 = getOutputSchema().getType(References.BLOCK_NAME);
/*    */     
/* 26 */     Type<Pair<String, Either<Integer, String>>> $$2 = DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString()));
/* 27 */     Type<Pair<String, String>> $$3 = DSL.named(References.BLOCK_NAME.typeName(), NamespacedSchema.namespacedString());
/*    */     
/* 29 */     if (!Objects.equals($$0, $$2) || !Objects.equals($$1, $$3)) {
/* 30 */       throw new IllegalStateException("Expected and actual types don't match.");
/*    */     }
/* 32 */     return fixTypeEverywhere("BlockNameFlatteningFix", $$2, $$3, $$0 -> ());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockNameFlatteningFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */