/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
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
/*    */ class null
/*    */   implements EntityTypeTest<B, T>
/*    */ {
/*    */   @Nullable
/*    */   public T tryCast(B $$0) {
/* 28 */     return cls.equals($$0.getClass()) ? (T)$$0 : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends B> getBaseClass() {
/* 33 */     return cls;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntityTypeTest$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */