/*    */ package net.minecraft.nbt;
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
/*    */ class Cache
/*    */ {
/*    */   private static final int HIGH = 1024;
/*    */   private static final int LOW = -128;
/* 18 */   static final LongTag[] cache = new LongTag[1153];
/*    */   
/*    */   static {
/* 21 */     for (int $$0 = 0; $$0 < cache.length; $$0++)
/* 22 */       cache[$$0] = new LongTag((-128 + $$0)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\LongTag$Cache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */