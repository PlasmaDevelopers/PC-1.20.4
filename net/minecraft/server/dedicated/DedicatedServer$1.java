/*    */ package net.minecraft.server.dedicated;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
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
/*    */   extends Thread
/*    */ {
/*    */   null(String $$1) {
/* 83 */     super($$1);
/*    */   }
/*    */   public void run() {
/* 86 */     BufferedReader $$0 = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
/*    */     try {
/*    */       String $$1;
/* 89 */       while (!DedicatedServer.this.isStopped() && DedicatedServer.this.isRunning() && ($$1 = $$0.readLine()) != null) {
/* 90 */         DedicatedServer.this.handleConsoleInput($$1, DedicatedServer.this.createCommandSourceStack());
/*    */       }
/* 92 */     } catch (IOException $$2) {
/* 93 */       DedicatedServer.LOGGER.error("Exception handling console input", $$2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\DedicatedServer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */