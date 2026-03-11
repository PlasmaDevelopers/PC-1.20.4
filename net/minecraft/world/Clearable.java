/*    */ package net.minecraft.world;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface Clearable {
/*    */   void clearContent();
/*    */   
/*    */   static void tryClear(@Nullable Object $$0) {
/*  9 */     if ($$0 instanceof Clearable)
/* 10 */       ((Clearable)$$0).clearContent(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\Clearable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */