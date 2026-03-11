/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.UnaryOperator;
/*    */ 
/*    */ public class BlockEntityRenameFix extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   private BlockEntityRenameFix(Schema $$0, String $$1, UnaryOperator<String> $$2) {
/* 16 */     super($$0, true);
/* 17 */     this.name = $$1;
/* 18 */     this.nameChangeLookup = $$2;
/*    */   }
/*    */   private final UnaryOperator<String> nameChangeLookup;
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 23 */     TaggedChoice.TaggedChoiceType<String> $$0 = getInputSchema().findChoiceType(References.BLOCK_ENTITY);
/* 24 */     TaggedChoice.TaggedChoiceType<String> $$1 = getOutputSchema().findChoiceType(References.BLOCK_ENTITY);
/*    */     
/* 26 */     return fixTypeEverywhere(this.name, (Type)$$0, (Type)$$1, $$0 -> ());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataFix create(Schema $$0, String $$1, UnaryOperator<String> $$2) {
/* 32 */     return new BlockEntityRenameFix($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntityRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */