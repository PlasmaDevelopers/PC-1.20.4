/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ public final class ReportEnvironment extends Record { private final String clientVersion; @Nullable
/*    */   private final Server server;
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;
/*    */   }
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;
/*    */   }
/*    */   
/* 11 */   public ReportEnvironment(String $$0, @Nullable Server $$1) { this.clientVersion = $$0; this.server = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public String clientVersion() { return this.clientVersion; } @Nullable public Server server() { return this.server; }
/*    */    public static ReportEnvironment local() {
/* 13 */     return create(null);
/*    */   }
/*    */   
/*    */   public static ReportEnvironment thirdParty(String $$0) {
/* 17 */     return create(new Server.ThirdParty($$0));
/*    */   }
/*    */   
/*    */   public static ReportEnvironment realm(RealmsServer $$0) {
/* 21 */     return create(new Server.Realm($$0));
/*    */   }
/*    */   
/*    */   public static ReportEnvironment create(@Nullable Server $$0) {
/* 25 */     return new ReportEnvironment(getClientVersion(), $$0);
/*    */   }
/*    */   
/*    */   public AbuseReportRequest.ClientInfo clientInfo() {
/* 29 */     return new AbuseReportRequest.ClientInfo(this.clientVersion, Locale.getDefault().toLanguageTag());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AbuseReportRequest.ThirdPartyServerInfo thirdPartyServerInfo() {
/* 34 */     Server server = this.server; if (server instanceof Server.ThirdParty) { Server.ThirdParty $$0 = (Server.ThirdParty)server;
/* 35 */       return new AbuseReportRequest.ThirdPartyServerInfo($$0.ip); }
/*    */     
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AbuseReportRequest.RealmInfo realmInfo() {
/* 42 */     Server server = this.server; if (server instanceof Server.Realm) { Server.Realm $$0 = (Server.Realm)server;
/* 43 */       return new AbuseReportRequest.RealmInfo(String.valueOf($$0.realmId()), $$0.slotId()); }
/*    */     
/* 45 */     return null;
/*    */   }
/*    */   
/*    */   private static String getClientVersion() {
/* 49 */     StringBuilder $$0 = new StringBuilder();
/*    */     
/* 51 */     $$0.append("1.20.4");
/* 52 */     if (Minecraft.checkModStatus().shouldReportAsModified()) {
/* 53 */       $$0.append(" (modded)");
/*    */     }
/*    */     
/* 56 */     return $$0.toString();
/*    */   }
/*    */   public static final class ThirdParty extends Record implements Server { final String ip;
/*    */     
/* 60 */     public ThirdParty(String $$0) { this.ip = $$0; } public final String toString() { // Byte code:
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
/* 60 */       //   0	8	1	$$0	Ljava/lang/Object; } public String ip() { return this.ip; } } public static interface Server { public static final class ThirdParty extends Record implements Server { final String ip; public ThirdParty(String $$0) { this.ip = $$0; } public final String toString() { // Byte code:
/*    */         //   0: aload_0
/*    */         //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;)Ljava/lang/String;
/*    */         //   6: areturn
/*    */         // Line number table:
/*    */         //   Java source line number -> byte code offset
/*    */         //   #60	-> 0
/*    */         // Local variable table:
/*    */         //   start	length	slot	name	descriptor
/*    */         //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty; } public final int hashCode() { // Byte code:
/*    */         //   0: aload_0
/*    */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;)I
/*    */         //   6: ireturn
/*    */         // Line number table:
/*    */         //   Java source line number -> byte code offset
/*    */         //   #60	-> 0
/*    */         // Local variable table:
/*    */         //   start	length	slot	name	descriptor
/*    */         //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty; } public final boolean equals(Object $$0) { // Byte code:
/*    */         //   0: aload_0
/*    */         //   1: aload_1
/*    */         //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;Ljava/lang/Object;)Z
/*    */         //   7: ireturn
/*    */         // Line number table:
/*    */         //   Java source line number -> byte code offset
/*    */         //   #60	-> 0
/*    */         // Local variable table:
/*    */         //   start	length	slot	name	descriptor
/*    */         //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$ThirdParty;
/* 60 */         //   0	8	1	$$0	Ljava/lang/Object; } public String ip() { return this.ip; }
/*    */        }
/*    */     public static final class Realm extends Record implements Server { private final long realmId; private final int slotId;
/* 63 */       public int slotId() { return this.slotId; } public long realmId() { return this.realmId; } public final boolean equals(Object $$0) { // Byte code:
/*    */         //   0: aload_0
/*    */         //   1: aload_1
/*    */         //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;Ljava/lang/Object;)Z
/*    */         //   7: ireturn
/*    */         // Line number table:
/*    */         //   Java source line number -> byte code offset
/*    */         //   #63	-> 0
/*    */         // Local variable table:
/*    */         //   start	length	slot	name	descriptor
/*    */         //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;
/*    */         //   0	8	1	$$0	Ljava/lang/Object; } public final int hashCode() { // Byte code:
/*    */         //   0: aload_0
/*    */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;)I
/*    */         //   6: ireturn
/*    */         // Line number table:
/*    */         //   Java source line number -> byte code offset
/*    */         //   #63	-> 0
/*    */         // Local variable table:
/*    */         //   start	length	slot	name	descriptor
/*    */         //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm; } public final String toString() { // Byte code:
/*    */         //   0: aload_0
/*    */         //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;)Ljava/lang/String;
/*    */         //   6: areturn
/*    */         // Line number table:
/*    */         //   Java source line number -> byte code offset
/*    */         //   #63	-> 0
/*    */         // Local variable table:
/*    */         //   start	length	slot	name	descriptor
/* 63 */         //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm; } public Realm(long $$0, int $$1) { this.realmId = $$0; this.slotId = $$1; }
/*    */       
/* 65 */       public Realm(RealmsServer $$0) { this($$0.id, $$0.activeSlot); } } } public static final class Realm extends Record implements Server { private final long realmId; private final int slotId; public Realm(RealmsServer $$0) { this($$0.id, $$0.activeSlot); }
/*    */ 
/*    */     
/*    */     public int slotId() {
/*    */       return this.slotId;
/*    */     }
/*    */     
/*    */     public long realmId() {
/*    */       return this.realmId;
/*    */     }
/*    */     
/*    */     public final boolean equals(Object $$0) {
/*    */       // Byte code:
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
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */     }
/*    */     
/*    */     public final int hashCode() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;
/*    */     }
/*    */     
/*    */     public final String toString() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #63	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/ReportEnvironment$Server$Realm;
/*    */     }
/*    */     
/*    */     public Realm(long $$0, int $$1) {
/*    */       this.realmId = $$0;
/*    */       this.slotId = $$1;
/*    */     } }
/*    */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ReportEnvironment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */