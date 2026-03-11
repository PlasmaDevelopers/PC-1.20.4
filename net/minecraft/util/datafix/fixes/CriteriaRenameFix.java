/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.DataFix;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.datafixers.TypeRewriteRule;
/*    */ import com.mojang.datafixers.Typed;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import java.util.function.UnaryOperator;
/*    */ 
/*    */ public class CriteriaRenameFix
/*    */   extends DataFix {
/*    */   private final String name;
/*    */   
/*    */   public CriteriaRenameFix(Schema $$0, String $$1, String $$2, UnaryOperator<String> $$3) {
/* 18 */     super($$0, false);
/* 19 */     this.name = $$1;
/* 20 */     this.advancementId = $$2;
/* 21 */     this.conversions = $$3;
/*    */   }
/*    */   private final String advancementId; private final UnaryOperator<String> conversions;
/*    */   
/*    */   protected TypeRewriteRule makeRule() {
/* 26 */     return fixTypeEverywhereTyped(this.name, getInputSchema().getType(References.ADVANCEMENTS), $$0 -> $$0.update(DSL.remainderFinder(), this::fixAdvancements));
/*    */   }
/*    */   
/*    */   private Dynamic<?> fixAdvancements(Dynamic<?> $$0) {
/* 30 */     return $$0.update(this.advancementId, $$0 -> $$0.update("criteria", ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\CriteriaRenameFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */