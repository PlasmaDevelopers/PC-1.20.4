/*    */ package net.minecraft.server.gui;
/*    */ 
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import javax.swing.JFrame;
/*    */ import net.minecraft.server.dedicated.DedicatedServer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends WindowAdapter
/*    */ {
/*    */   public void windowClosing(WindowEvent $$0) {
/* 62 */     if (!gui.isClosing.getAndSet(true)) {
/* 63 */       frame.setTitle("Minecraft server - shutting down!");
/* 64 */       server.halt(true);
/* 65 */       gui.runFinalizers();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\gui\MinecraftServerGui$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */