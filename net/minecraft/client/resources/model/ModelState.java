/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import com.mojang.math.Transformation;
/*    */ 
/*    */ public interface ModelState {
/*    */   default Transformation getRotation() {
/*  7 */     return Transformation.identity();
/*    */   }
/*    */   
/*    */   default boolean isUvLocked() {
/* 11 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\ModelState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */