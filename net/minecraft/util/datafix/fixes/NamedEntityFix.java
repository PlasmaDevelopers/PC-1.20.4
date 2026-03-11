/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ 
/*    */ public abstract class NamedEntityFix extends DataFix {
/*    */   private final String name;
/*    */   private final String entityName;
/*    */   private final DSL.TypeReference type;
/*    */   
/*    */   public NamedEntityFix(Schema $$0, boolean $$1, String $$2, DSL.TypeReference $$3, String $$4) {
/* 16 */     super($$0, $$1);
/* 17 */     this.name = $$2;
/* 18 */     this.type = $$3;
/* 19 */     this.entityName = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeRewriteRule makeRule() {
/* 24 */     OpticFinder<?> $$0 = DSL.namedChoice(this.entityName, getInputSchema().getChoiceType(this.type, this.entityName));
/*    */     
/* 26 */     return fixTypeEverywhereTyped(this.name, getInputSchema().getType(this.type), getOutputSchema().getType(this.type), $$1 -> $$1.updateTyped($$0, getOutputSchema().getChoiceType(this.type, this.entityName), this::fix));
/*    */   }
/*    */   
/*    */   protected abstract Typed<?> fix(Typed<?> paramTyped);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\NamedEntityFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */