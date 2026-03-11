/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Locale;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public abstract class SimplestEntityRenameFix extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   public SimplestEntityRenameFix(String $$0, Schema $$1, boolean $$2) {
/* 20 */     super($$1, $$2);
/* 21 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 27 */     TaggedChoice.TaggedChoiceType<String> $$0 = getInputSchema().findChoiceType(References.ENTITY);
/* 28 */     TaggedChoice.TaggedChoiceType<String> $$1 = getOutputSchema().findChoiceType(References.ENTITY);
/*    */     
/* 30 */     Type<Pair<String, String>> $$2 = DSL.named(References.ENTITY_NAME.typeName(), NamespacedSchema.namespacedString());
/* 31 */     if (!Objects.equals(getOutputSchema().getType(References.ENTITY_NAME), $$2)) {
/* 32 */       throw new IllegalStateException("Entity name type is not what was expected.");
/*    */     }
/*    */     
/* 35 */     return TypeRewriteRule.seq(
/* 36 */         fixTypeEverywhere(this.name, (Type)$$0, (Type)$$1, $$2 -> ()), 
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
/* 48 */         fixTypeEverywhere(this.name + " for entity name", $$2, $$0 -> ()));
/*    */   }
/*    */   
/*    */   protected abstract String rename(String paramString);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\SimplestEntityRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */