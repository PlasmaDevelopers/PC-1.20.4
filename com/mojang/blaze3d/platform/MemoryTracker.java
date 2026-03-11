/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ 
/*    */ public class MemoryTracker
/*    */ {
/*  8 */   private static final MemoryUtil.MemoryAllocator ALLOCATOR = MemoryUtil.getAllocator(false);
/*    */   
/*    */   public static ByteBuffer create(int $$0) {
/* 11 */     long $$1 = ALLOCATOR.malloc($$0);
/* 12 */     if ($$1 == 0L) {
/* 13 */       throw new OutOfMemoryError("Failed to allocate " + $$0 + " bytes");
/*    */     }
/* 15 */     return MemoryUtil.memByteBuffer($$1, $$0);
/*    */   }
/*    */   
/*    */   public static ByteBuffer resize(ByteBuffer $$0, int $$1) {
/* 19 */     long $$2 = ALLOCATOR.realloc(MemoryUtil.memAddress0($$0), $$1);
/* 20 */     if ($$2 == 0L) {
/* 21 */       throw new OutOfMemoryError("Failed to resize buffer from " + $$0.capacity() + " bytes to " + $$1 + " bytes");
/*    */     }
/* 23 */     return MemoryUtil.memByteBuffer($$2, $$1);
/*    */   }
/*    */   
/*    */   public static void free(ByteBuffer $$0) {
/* 27 */     ALLOCATOR.free(MemoryUtil.memAddress0($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\MemoryTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */