/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
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
/*    */ public interface Server
/*    */ {
/*    */   public static final class ThirdParty
/*    */     extends Record
/*    */     implements Server
/*    */   {
/*    */     final String ip;
/*    */     
/*    */     public ThirdParty(String $$0) {
/* 60 */       this.ip = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #60	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #60	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #60	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;
/* 60 */       //   0	8	1	$$0	Ljava/lang/Object; } public String ip() { return this.ip; }
/*    */      }
/*    */   public static final class Realm extends Record implements Server { private final long realmId; private final int slotId;
/* 63 */     public int slotId() { return this.slotId; } public long realmId() { return this.realmId; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;
/* 63 */       //   0	8	1	$$0	Ljava/lang/Object; } public Realm(long $$0, int $$1) { this.realmId = $$0; this.slotId = $$1; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm; }
/*    */     public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 65 */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm; } public Realm(RealmsServer $$0) { this($$0.id, $$0.activeSlot); }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ReportEnvironment$Server.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */