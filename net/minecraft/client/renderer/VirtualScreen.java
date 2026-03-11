/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.DisplayData;
/*    */ import com.mojang.blaze3d.platform.MonitorCreator;
/*    */ import com.mojang.blaze3d.platform.ScreenManager;
/*    */ import com.mojang.blaze3d.platform.Window;
/*    */ import com.mojang.blaze3d.platform.WindowEventHandler;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public final class VirtualScreen implements AutoCloseable {
/*    */   private final Minecraft minecraft;
/*    */   private final ScreenManager screenManager;
/*    */   
/*    */   public VirtualScreen(Minecraft $$0) {
/* 16 */     this.minecraft = $$0;
/* 17 */     this.screenManager = new ScreenManager(com.mojang.blaze3d.platform.Monitor::new);
/*    */   }
/*    */   
/*    */   public Window newWindow(DisplayData $$0, @Nullable String $$1, String $$2) {
/* 21 */     return new Window((WindowEventHandler)this.minecraft, this.screenManager, $$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 26 */     this.screenManager.shutdown();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\VirtualScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */