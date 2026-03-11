/*    */ package net.minecraft.util.random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntrusiveBase
/*    */   implements WeightedEntry
/*    */ {
/*    */   private final Weight weight;
/*    */   
/*    */   public IntrusiveBase(int $$0) {
/* 13 */     this.weight = Weight.of($$0);
/*    */   }
/*    */   
/*    */   public IntrusiveBase(Weight $$0) {
/* 17 */     this.weight = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Weight getWeight() {
/* 22 */     return this.weight;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\random\WeightedEntry$IntrusiveBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */