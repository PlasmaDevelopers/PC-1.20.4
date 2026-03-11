/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface EntityTypeTest<B, T extends B> {
/*    */   static <B, T extends B> EntityTypeTest<B, T> forClass(final Class<T> cls) {
/*  7 */     return new EntityTypeTest<B, T>()
/*    */       {
/*    */         @Nullable
/*    */         public T tryCast(B $$0)
/*    */         {
/* 12 */           return cls.isInstance($$0) ? (T)$$0 : null;
/*    */         }
/*    */ 
/*    */         
/*    */         public Class<? extends B> getBaseClass() {
/* 17 */           return cls;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static <B, T extends B> EntityTypeTest<B, T> forExactClass(final Class<T> cls) {
/* 23 */     return new EntityTypeTest<B, T>()
/*    */       {
/*    */         @Nullable
/*    */         public T tryCast(B $$0)
/*    */         {
/* 28 */           return cls.equals($$0.getClass()) ? (T)$$0 : null;
/*    */         }
/*    */ 
/*    */         
/*    */         public Class<? extends B> getBaseClass() {
/* 33 */           return cls;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   T tryCast(B paramB);
/*    */   
/*    */   Class<? extends B> getBaseClass();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntityTypeTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */