/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public abstract class BlockRenameFixWithJigsaw extends BlockRenameFix {
/*    */   public BlockRenameFixWithJigsaw(Schema $$0, String $$1) {
/* 16 */     super($$0, $$1);
/* 17 */     this.name = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 22 */     DSL.TypeReference $$0 = References.BLOCK_ENTITY;
/* 23 */     String $$1 = "minecraft:jigsaw";
/* 24 */     OpticFinder<?> $$2 = DSL.namedChoice("minecraft:jigsaw", getInputSchema().getChoiceType($$0, "minecraft:jigsaw"));
/*    */ 
/*    */     
/* 27 */     TypeRewriteRule $$3 = fixTypeEverywhereTyped(this.name + " for jigsaw state", getInputSchema().getType($$0), getOutputSchema().getType($$0), $$2 -> $$2.updateTyped($$0, getOutputSchema().getChoiceType($$1, "minecraft:jigsaw"), ()));
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     return TypeRewriteRule.seq(super.makeRule(), $$3);
/*    */   }
/*    */   private final String name;
/*    */   public static DataFix create(Schema $$0, String $$1, final Function<String, String> fixBlock) {
/* 49 */     return new BlockRenameFixWithJigsaw($$0, $$1)
/*    */       {
/*    */         protected String fixBlock(String $$0) {
/* 52 */           return fixBlock.apply($$0);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockRenameFixWithJigsaw.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */