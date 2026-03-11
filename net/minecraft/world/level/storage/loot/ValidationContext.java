/*    */ package net.minecraft.world.level.storage.loot;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.ProblemReporter;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
/*    */ 
/*    */ 
/*    */ public class ValidationContext
/*    */ {
/*    */   private final ProblemReporter reporter;
/*    */   private final LootContextParamSet params;
/*    */   private final LootDataResolver resolver;
/*    */   private final Set<LootDataId<?>> visitedElements;
/*    */   
/*    */   public ValidationContext(ProblemReporter $$0, LootContextParamSet $$1, LootDataResolver $$2) {
/* 17 */     this($$0, $$1, $$2, Set.of());
/*    */   }
/*    */   
/*    */   private ValidationContext(ProblemReporter $$0, LootContextParamSet $$1, LootDataResolver $$2, Set<LootDataId<?>> $$3) {
/* 21 */     this.reporter = $$0;
/* 22 */     this.params = $$1;
/* 23 */     this.resolver = $$2;
/* 24 */     this.visitedElements = $$3;
/*    */   }
/*    */   
/*    */   public ValidationContext forChild(String $$0) {
/* 28 */     return new ValidationContext(this.reporter.forChild($$0), this.params, this.resolver, this.visitedElements);
/*    */   }
/*    */   
/*    */   public ValidationContext enterElement(String $$0, LootDataId<?> $$1) {
/* 32 */     ImmutableSet<LootDataId<?>> $$2 = ImmutableSet.builder().addAll(this.visitedElements).add($$1).build();
/* 33 */     return new ValidationContext(this.reporter.forChild($$0), this.params, this.resolver, (Set<LootDataId<?>>)$$2);
/*    */   }
/*    */   
/*    */   public boolean hasVisitedElement(LootDataId<?> $$0) {
/* 37 */     return this.visitedElements.contains($$0);
/*    */   }
/*    */   
/*    */   public void reportProblem(String $$0) {
/* 41 */     this.reporter.report($$0);
/*    */   }
/*    */   
/*    */   public void validateUser(LootContextUser $$0) {
/* 45 */     this.params.validateUser(this, $$0);
/*    */   }
/*    */   
/*    */   public LootDataResolver resolver() {
/* 49 */     return this.resolver;
/*    */   }
/*    */   
/*    */   public ValidationContext setParams(LootContextParamSet $$0) {
/* 53 */     return new ValidationContext(this.reporter, $$0, this.resolver, this.visitedElements);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\ValidationContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */