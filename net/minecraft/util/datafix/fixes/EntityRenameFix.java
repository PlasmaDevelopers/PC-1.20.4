/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Locale;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public abstract class EntityRenameFix extends DataFix {
/*    */   protected final String name;
/*    */   
/*    */   public EntityRenameFix(String $$0, Schema $$1, boolean $$2) {
/* 18 */     super($$1, $$2);
/* 19 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 25 */     TaggedChoice.TaggedChoiceType<String> $$0 = getInputSchema().findChoiceType(References.ENTITY);
/* 26 */     TaggedChoice.TaggedChoiceType<String> $$1 = getOutputSchema().findChoiceType(References.ENTITY);
/*    */     
/* 28 */     return fixTypeEverywhere(this.name, (Type)$$0, (Type)$$1, $$2 -> ());
/*    */   }
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
/*    */   private <A> Typed<A> getEntity(Object $$0, DynamicOps<?> $$1, Type<A> $$2) {
/* 45 */     return new Typed($$2, $$1, $$0);
/*    */   }
/*    */   
/*    */   protected abstract Pair<String, Typed<?>> fix(String paramString, Typed<?> paramTyped);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */