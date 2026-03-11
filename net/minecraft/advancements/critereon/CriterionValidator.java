/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.ProblemReporter;
/*    */ import net.minecraft.world.level.storage.loot.LootDataResolver;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*    */ 
/*    */ public class CriterionValidator
/*    */ {
/*    */   private final ProblemReporter reporter;
/*    */   private final LootDataResolver lootData;
/*    */   
/*    */   public CriterionValidator(ProblemReporter $$0, LootDataResolver $$1) {
/* 17 */     this.reporter = $$0;
/* 18 */     this.lootData = $$1;
/*    */   }
/*    */   
/*    */   public void validateEntity(Optional<ContextAwarePredicate> $$0, String $$1) {
/* 22 */     $$0.ifPresent($$1 -> validateEntity($$1, $$0));
/*    */   }
/*    */   
/*    */   public void validateEntities(List<ContextAwarePredicate> $$0, String $$1) {
/* 26 */     validate($$0, LootContextParamSets.ADVANCEMENT_ENTITY, $$1);
/*    */   }
/*    */   
/*    */   public void validateEntity(ContextAwarePredicate $$0, String $$1) {
/* 30 */     validate($$0, LootContextParamSets.ADVANCEMENT_ENTITY, $$1);
/*    */   }
/*    */   
/*    */   public void validate(ContextAwarePredicate $$0, LootContextParamSet $$1, String $$2) {
/* 34 */     $$0.validate(new ValidationContext(this.reporter.forChild($$2), $$1, this.lootData));
/*    */   }
/*    */   
/*    */   public void validate(List<ContextAwarePredicate> $$0, LootContextParamSet $$1, String $$2) {
/* 38 */     for (int $$3 = 0; $$3 < $$0.size(); $$3++) {
/* 39 */       ContextAwarePredicate $$4 = $$0.get($$3);
/* 40 */       $$4.validate(new ValidationContext(this.reporter.forChild($$2 + "[" + $$2 + "]"), $$1, this.lootData));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\CriterionValidator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */