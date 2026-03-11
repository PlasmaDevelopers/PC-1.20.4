/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ 
/*    */ public class BlockStateStructureTemplateFix extends DataFix {
/*    */   public BlockStateStructureTemplateFix(Schema $$0, boolean $$1) {
/* 10 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 15 */     return fixTypeEverywhereTyped("BlockStateStructureTemplateFix", getInputSchema().getType(References.BLOCK_STATE), $$0 -> $$0.update(DSL.remainderFinder(), BlockStateData::upgradeBlockStateTag));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockStateStructureTemplateFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */