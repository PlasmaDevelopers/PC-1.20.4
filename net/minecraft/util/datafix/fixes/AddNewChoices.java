/*    */ package net.minecraft.util.datafix.fixes;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class AddNewChoices extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   public AddNewChoices(Schema $$0, String $$1, DSL.TypeReference $$2) {
/* 16 */     super($$0, true);
/* 17 */     this.name = $$1;
/* 18 */     this.type = $$2;
/*    */   }
/*    */   private final DSL.TypeReference type;
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 23 */     TaggedChoice.TaggedChoiceType<?> $$0 = getInputSchema().findChoiceType(this.type);
/* 24 */     TaggedChoice.TaggedChoiceType<?> $$1 = getOutputSchema().findChoiceType(this.type);
/* 25 */     return cap(this.name, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final <K> TypeRewriteRule cap(String $$0, TaggedChoice.TaggedChoiceType<K> $$1, TaggedChoice.TaggedChoiceType<?> $$2) {
/* 30 */     if ($$1.getKeyType() != $$2.getKeyType()) {
/* 31 */       throw new IllegalStateException("Could not inject: key type is not the same");
/*    */     }
/* 33 */     TaggedChoice.TaggedChoiceType<K> $$3 = (TaggedChoice.TaggedChoiceType)$$2;
/* 34 */     return fixTypeEverywhere($$0, (Type)$$1, (Type)$$3, $$1 -> ());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\AddNewChoices.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */