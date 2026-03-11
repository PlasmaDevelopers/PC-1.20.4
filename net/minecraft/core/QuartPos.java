/*    */ package net.minecraft.core;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class QuartPos
/*    */ {
/*    */   public static final int BITS = 2;
/*    */   public static final int SIZE = 4;
/*    */   public static final int MASK = 3;
/*    */   private static final int SECTION_TO_QUARTS_BITS = 2;
/*    */   
/*    */   public static int fromBlock(int $$0) {
/* 14 */     return $$0 >> 2;
/*    */   }
/*    */   
/*    */   public static int quartLocal(int $$0) {
/* 18 */     return $$0 & 0x3;
/*    */   }
/*    */   
/*    */   public static int toBlock(int $$0) {
/* 22 */     return $$0 << 2;
/*    */   }
/*    */   
/*    */   public static int fromSection(int $$0) {
/* 26 */     return $$0 << 2;
/*    */   }
/*    */   
/*    */   public static int toSection(int $$0) {
/* 30 */     return $$0 >> 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\QuartPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */