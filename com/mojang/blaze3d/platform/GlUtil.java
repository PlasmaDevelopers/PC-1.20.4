/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import java.nio.Buffer;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ 
/*    */ 
/*    */ public class GlUtil
/*    */ {
/*    */   public static ByteBuffer allocateMemory(int $$0) {
/* 11 */     return MemoryUtil.memAlloc($$0);
/*    */   }
/*    */   
/*    */   public static void freeMemory(Buffer $$0) {
/* 15 */     MemoryUtil.memFree($$0);
/*    */   }
/*    */   
/*    */   public static String getVendor() {
/* 19 */     return GlStateManager._getString(7936);
/*    */   }
/*    */   
/*    */   public static String getCpuInfo() {
/* 23 */     return GLX._getCpuInfo();
/*    */   }
/*    */   
/*    */   public static String getRenderer() {
/* 27 */     return GlStateManager._getString(7937);
/*    */   }
/*    */   
/*    */   public static String getOpenGLVersion() {
/* 31 */     return GlStateManager._getString(7938);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\GlUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */