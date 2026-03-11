/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.util.StringDecomposer;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.glfw.GLFW;
/*    */ import org.lwjgl.glfw.GLFWErrorCallback;
/*    */ import org.lwjgl.glfw.GLFWErrorCallbackI;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ 
/*    */ 
/*    */ public class ClipboardManager
/*    */ {
/*    */   public static final int FORMAT_UNAVAILABLE = 65545;
/* 16 */   private final ByteBuffer clipboardScratchBuffer = BufferUtils.createByteBuffer(8192);
/*    */   
/*    */   public String getClipboard(long $$0, GLFWErrorCallbackI $$1) {
/* 19 */     GLFWErrorCallback $$2 = GLFW.glfwSetErrorCallback($$1);
/* 20 */     String $$3 = GLFW.glfwGetClipboardString($$0);
/* 21 */     $$3 = ($$3 != null) ? StringDecomposer.filterBrokenSurrogates($$3) : "";
/* 22 */     GLFWErrorCallback $$4 = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)$$2);
/* 23 */     if ($$4 != null) {
/* 24 */       $$4.free();
/*    */     }
/* 26 */     return $$3;
/*    */   }
/*    */   
/*    */   private static void pushClipboard(long $$0, ByteBuffer $$1, byte[] $$2) {
/* 30 */     $$1.clear();
/* 31 */     $$1.put($$2);
/* 32 */     $$1.put((byte)0);
/* 33 */     $$1.flip();
/* 34 */     GLFW.glfwSetClipboardString($$0, $$1);
/*    */   }
/*    */   
/*    */   public void setClipboard(long $$0, String $$1) {
/* 38 */     byte[] $$2 = $$1.getBytes(Charsets.UTF_8);
/*    */     
/* 40 */     int $$3 = $$2.length + 1;
/* 41 */     if ($$3 < this.clipboardScratchBuffer.capacity()) {
/* 42 */       pushClipboard($$0, this.clipboardScratchBuffer, $$2);
/*    */     } else {
/* 44 */       ByteBuffer $$4 = MemoryUtil.memAlloc($$3);
/*    */       try {
/* 46 */         pushClipboard($$0, $$4, $$2);
/*    */       } finally {
/* 48 */         MemoryUtil.memFree($$4);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\ClipboardManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */