/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriterionTriggerInstance;
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
/*    */ 
/*    */ 
/*    */ public interface SimpleInstance
/*    */   extends CriterionTriggerInstance
/*    */ {
/*    */   default void validate(CriterionValidator $$0) {
/* 79 */     $$0.validateEntity(player(), ".player");
/*    */   }
/*    */   
/*    */   Optional<ContextAwarePredicate> player();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\SimpleCriterionTrigger$SimpleInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */