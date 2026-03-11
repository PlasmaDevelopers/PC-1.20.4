/*    */ package net.minecraft.core;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public interface IdMap<T>
/*    */   extends Iterable<T>
/*    */ {
/*    */   public static final int DEFAULT = -1;
/*    */   
/*    */   int getId(T paramT);
/*    */   
/*    */   @Nullable
/*    */   T byId(int paramInt);
/*    */   
/*    */   default T byIdOrThrow(int $$0) {
/* 17 */     T $$1 = byId($$0);
/* 18 */     if ($$1 == null) {
/* 19 */       throw new IllegalArgumentException("No value with id " + $$0);
/*    */     }
/* 21 */     return $$1;
/*    */   }
/*    */   
/*    */   int size();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\IdMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */