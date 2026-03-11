/*    */ package net.minecraft.network.protocol.handshake;
/*    */ 
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ClientIntent
/*    */ {
/*  9 */   STATUS,
/* 10 */   LOGIN;
/*    */   
/*    */   private static final int STATUS_ID = 1;
/*    */   
/*    */   private static final int LOGIN_ID = 2;
/*    */ 
/*    */   
/*    */   public static ClientIntent byId(int $$0) {
/* 18 */     switch ($$0) { case 1:
/*    */       
/*    */       case 2:
/* 21 */        }  throw new IllegalArgumentException("Unknown connection intent: " + $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int id() {
/* 26 */     switch (this) { default: throw new IncompatibleClassChangeError();case STATUS: case LOGIN: break; }  return 
/*    */       
/* 28 */       2;
/*    */   }
/*    */ 
/*    */   
/*    */   public ConnectionProtocol protocol() {
/* 33 */     switch (this) { default: throw new IncompatibleClassChangeError();case STATUS: case LOGIN: break; }  return 
/*    */       
/* 35 */       ConnectionProtocol.LOGIN;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\handshake\ClientIntent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */