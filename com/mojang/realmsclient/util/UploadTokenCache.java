/*    */ package com.mojang.realmsclient.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ 
/*    */ public class UploadTokenCache {
/*  7 */   private static final Long2ObjectMap<String> TOKEN_CACHE = (Long2ObjectMap<String>)new Long2ObjectOpenHashMap();
/*    */   
/*    */   public static String get(long $$0) {
/* 10 */     return (String)TOKEN_CACHE.get($$0);
/*    */   }
/*    */   
/*    */   public static void invalidate(long $$0) {
/* 14 */     TOKEN_CACHE.remove($$0);
/*    */   }
/*    */   
/*    */   public static void put(long $$0, String $$1) {
/* 18 */     TOKEN_CACHE.put($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\UploadTokenCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */