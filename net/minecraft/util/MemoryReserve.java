/*    */ package net.minecraft.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class MemoryReserve {
/*    */   @Nullable
/*  7 */   private static byte[] reserve = null;
/*    */ 
/*    */   
/*    */   public static void allocate() {
/* 11 */     reserve = new byte[10485760];
/*    */   }
/*    */   
/*    */   public static void release() {
/* 15 */     reserve = new byte[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\MemoryReserve.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */