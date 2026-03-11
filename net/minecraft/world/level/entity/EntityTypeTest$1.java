/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements EntityTypeTest<B, T>
/*    */ {
/*    */   @Nullable
/*    */   public T tryCast(B $$0) {
/* 12 */     return cls.isInstance($$0) ? (T)$$0 : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends B> getBaseClass() {
/* 17 */     return cls;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntityTypeTest$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */