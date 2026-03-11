/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import java.lang.invoke.MethodHandle;
/*    */ import java.lang.invoke.MethodHandles;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.annotation.Nullable;
/*    */ import org.lwjgl.system.Pointer;
/*    */ 
/*    */ public class DebugMemoryUntracker
/*    */ {
/*    */   static {
/* 13 */     UNTRACK = GLX.<MethodHandle>make(() -> {
/*    */           try {
/*    */             MethodHandles.Lookup $$0 = MethodHandles.lookup();
/*    */             
/*    */             Class<?> $$1 = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
/*    */             
/*    */             Method $$2 = $$1.getDeclaredMethod("untrack", new Class[] { long.class });
/*    */             
/*    */             $$2.setAccessible(true);
/*    */             
/*    */             Field $$3 = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
/*    */             
/*    */             $$3.setAccessible(true);
/*    */             
/*    */             Object $$4 = $$3.get(null);
/*    */             return $$1.isInstance($$4) ? $$0.unreflect($$2) : null;
/* 29 */           } catch (ClassNotFoundException|NoSuchMethodException|NoSuchFieldException|IllegalAccessException $$5) {
/*    */             throw new RuntimeException($$5);
/*    */           } 
/*    */         });
/*    */   } @Nullable
/*    */   private static final MethodHandle UNTRACK; public static void untrack(long $$0) {
/* 35 */     if (UNTRACK == null) {
/*    */       return;
/*    */     }
/*    */     try {
/* 39 */       UNTRACK.invoke($$0);
/* 40 */     } catch (Throwable $$1) {
/* 41 */       throw new RuntimeException($$1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void untrack(Pointer $$0) {
/* 46 */     untrack($$0.address());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\DebugMemoryUntracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */