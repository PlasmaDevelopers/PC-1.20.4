/*    */ package net.minecraft.client.server;
/*    */ 
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public class LanServer {
/*    */   private final String motd;
/*    */   private final String address;
/*    */   private long pingTime;
/*    */   
/*    */   public LanServer(String $$0, String $$1) {
/* 11 */     this.motd = $$0;
/* 12 */     this.address = $$1;
/* 13 */     this.pingTime = Util.getMillis();
/*    */   }
/*    */   
/*    */   public String getMotd() {
/* 17 */     return this.motd;
/*    */   }
/*    */   
/*    */   public String getAddress() {
/* 21 */     return this.address;
/*    */   }
/*    */   
/*    */   public void updatePingTime() {
/* 25 */     this.pingTime = Util.getMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\server\LanServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */