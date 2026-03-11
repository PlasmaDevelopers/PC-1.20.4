/*    */ package net.minecraft.network.syncher;
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
/*    */ public interface ForValueType<T>
/*    */   extends EntityDataSerializer<T>
/*    */ {
/*    */   default T copy(T $$0) {
/* 22 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataSerializer$ForValueType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */