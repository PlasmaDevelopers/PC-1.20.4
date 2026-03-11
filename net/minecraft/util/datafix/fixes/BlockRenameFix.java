/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public abstract class BlockRenameFix
/*    */   extends DataFix {
/*    */   public BlockRenameFix(Schema $$0, String $$1) {
/* 20 */     super($$0, false);
/* 21 */     this.name = $$1;
/*    */   }
/*    */   private final String name;
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 26 */     Type<?> $$0 = getInputSchema().getType(References.BLOCK_NAME);
/* 27 */     Type<Pair<String, String>> $$1 = DSL.named(References.BLOCK_NAME.typeName(), NamespacedSchema.namespacedString());
/* 28 */     if (!Objects.equals($$0, $$1)) {
/* 29 */       throw new IllegalStateException("block type is not what was expected.");
/*    */     }
/*    */     
/* 32 */     TypeRewriteRule $$2 = fixTypeEverywhere(this.name + " for block", $$1, $$0 -> ());
/*    */     
/* 34 */     TypeRewriteRule $$3 = fixTypeEverywhereTyped(this.name + " for block_state", getInputSchema().getType(References.BLOCK_STATE), $$0 -> $$0.update(DSL.remainderFinder(), ()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     return TypeRewriteRule.seq($$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataFix create(Schema $$0, String $$1, final Function<String, String> fixBlock) {
/* 48 */     return new BlockRenameFix($$0, $$1)
/*    */       {
/*    */         protected String fixBlock(String $$0) {
/* 51 */           return fixBlock.apply($$0);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected abstract String fixBlock(String paramString);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */