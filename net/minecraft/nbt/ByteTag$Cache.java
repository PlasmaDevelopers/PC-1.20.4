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
/* 16 */   static final ByteTag[] cache = new ByteTag[256];
/*    */   
/*    */   static {
/* 19 */     for (int $$0 = 0; $$0 < cache.length; $$0++)
/* 20 */       cache[$$0] = new ByteTag((byte)($$0 - 128)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ByteTag$Cache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */