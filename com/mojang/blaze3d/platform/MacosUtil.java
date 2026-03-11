/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import ca.weblite.objc.Client;
/*    */ import ca.weblite.objc.NSObject;
/*    */ import com.sun.jna.Pointer;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Base64;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.server.packs.resources.IoSupplier;
/*    */ import org.lwjgl.glfw.GLFWNativeCocoa;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacosUtil
/*    */ {
/*    */   private static final int NS_RESIZABLE_WINDOW_MASK = 8;
/*    */   private static final int NS_FULL_SCREEN_WINDOW_MASK = 16384;
/*    */   
/*    */   public static void exitNativeFullscreen(long $$0) {
/* 21 */     getNsWindow($$0).filter(MacosUtil::isInNativeFullscreen).ifPresent(MacosUtil::toggleNativeFullscreen);
/*    */   }
/*    */   
/*    */   public static void clearResizableBit(long $$0) {
/* 25 */     getNsWindow($$0).ifPresent($$0 -> {
/*    */           long $$1 = getStyleMask($$0);
/*    */           $$0.send("setStyleMask:", new Object[] { Long.valueOf($$1 & 0xFFFFFFFFFFFFFFF7L) });
/*    */         });
/*    */   }
/*    */   
/*    */   private static Optional<NSObject> getNsWindow(long $$0) {
/* 32 */     long $$1 = GLFWNativeCocoa.glfwGetCocoaWindow($$0);
/* 33 */     if ($$1 != 0L) {
/* 34 */       return Optional.of(new NSObject(new Pointer($$1)));
/*    */     }
/* 36 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   private static boolean isInNativeFullscreen(NSObject $$0) {
/* 40 */     return ((getStyleMask($$0) & 0x4000L) != 0L);
/*    */   }
/*    */   
/*    */   private static long getStyleMask(NSObject $$0) {
/* 44 */     return ((Long)$$0.sendRaw("styleMask", new Object[0])).longValue();
/*    */   }
/*    */   
/*    */   private static void toggleNativeFullscreen(NSObject $$0) {
/* 48 */     $$0.send("toggleFullScreen:", new Object[] { Pointer.NULL });
/*    */   }
/*    */   
/*    */   public static void loadIcon(IoSupplier<InputStream> $$0) throws IOException {
/* 52 */     InputStream $$1 = (InputStream)$$0.get(); try {
/* 53 */       String $$2 = Base64.getEncoder().encodeToString($$1.readAllBytes());
/* 54 */       Client $$3 = Client.getInstance();
/*    */       
/* 56 */       Object $$4 = $$3.sendProxy("NSData", "alloc", new Object[0]).send("initWithBase64Encoding:", new Object[] { $$2 });
/* 57 */       Object $$5 = $$3.sendProxy("NSImage", "alloc", new Object[0]).send("initWithData:", new Object[] { $$4 });
/*    */       
/* 59 */       $$3.sendProxy("NSApplication", "sharedApplication", new Object[0]).send("setApplicationIconImage:", new Object[] { $$5 });
/* 60 */       if ($$1 != null) $$1.close(); 
/*    */     } catch (Throwable throwable) {
/*    */       if ($$1 != null)
/*    */         try {
/*    */           $$1.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\MacosUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */