/*    */ package net.minecraft.advancements.critereon;
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
/*    */ public class Builder
/*    */ {
/* 25 */   private MinMaxBounds.Ints composite = MinMaxBounds.Ints.ANY;
/*    */   
/*    */   public static Builder light() {
/* 28 */     return new Builder();
/*    */   }
/*    */   
/*    */   public Builder setComposite(MinMaxBounds.Ints $$0) {
/* 32 */     this.composite = $$0;
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public LightPredicate build() {
/* 37 */     return new LightPredicate(this.composite);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\LightPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */