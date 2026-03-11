/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.OpticFinder;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.TaggedChoice;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*    */ 
/*    */ public class StatsRenameFix
/*    */   extends DataFix {
/*    */   private final String name;
/*    */   private final Map<String, String> renames;
/*    */   
/*    */   public StatsRenameFix(Schema $$0, String $$1, Map<String, String> $$2) {
/* 20 */     super($$0, false);
/* 21 */     this.name = $$1;
/* 22 */     this.renames = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 27 */     return TypeRewriteRule.seq(createStatRule(), createCriteriaRule());
/*    */   }
/*    */   
/*    */   private TypeRewriteRule createCriteriaRule() {
/* 31 */     Type<?> $$0 = getOutputSchema().getType(References.OBJECTIVE);
/* 32 */     Type<?> $$1 = getInputSchema().getType(References.OBJECTIVE);
/*    */     
/* 34 */     OpticFinder<?> $$2 = $$1.findField("CriteriaType");
/* 35 */     TaggedChoice.TaggedChoiceType<?> $$3 = (TaggedChoice.TaggedChoiceType)$$2.type().findChoiceType("type", -1).orElseThrow(() -> new IllegalStateException("Can't find choice type for criteria"));
/* 36 */     Type<?> $$4 = (Type)$$3.types().get("minecraft:custom");
/* 37 */     if ($$4 == null) {
/* 38 */       throw new IllegalStateException("Failed to find custom criterion type variant");
/*    */     }
/*    */     
/* 41 */     OpticFinder<?> $$5 = DSL.namedChoice("minecraft:custom", $$4);
/* 42 */     OpticFinder<String> $$6 = DSL.fieldFinder("id", NamespacedSchema.namespacedString());
/*    */     
/* 44 */     return fixTypeEverywhereTyped(this.name, $$1, $$0, $$3 -> $$3.updateTyped($$0, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private TypeRewriteRule createStatRule() {
/* 54 */     Type<?> $$0 = getOutputSchema().getType(References.STATS);
/* 55 */     Type<?> $$1 = getInputSchema().getType(References.STATS);
/* 56 */     OpticFinder<?> $$2 = $$1.findField("stats");
/* 57 */     OpticFinder<?> $$3 = $$2.type().findField("minecraft:custom");
/* 58 */     OpticFinder<String> $$4 = NamespacedSchema.namespacedString().finder();
/* 59 */     return fixTypeEverywhereTyped(this.name, $$1, $$0, $$3 -> $$3.updateTyped($$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\StatsRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */