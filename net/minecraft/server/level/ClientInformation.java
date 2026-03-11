/*    */ package net.minecraft.server.level;public final class ClientInformation extends Record { private final String language; private final int viewDistance; private final ChatVisiblity chatVisibility;
/*    */   private final boolean chatColors;
/*    */   private final int modelCustomisation;
/*    */   private final HumanoidArm mainHand;
/*    */   private final boolean textFilteringEnabled;
/*    */   private final boolean allowsListing;
/*    */   public static final int MAX_LANGUAGE_LENGTH = 16;
/*    */   
/*  9 */   public ClientInformation(String $$0, int $$1, ChatVisiblity $$2, boolean $$3, int $$4, HumanoidArm $$5, boolean $$6, boolean $$7) { this.language = $$0; this.viewDistance = $$1; this.chatVisibility = $$2; this.chatColors = $$3; this.modelCustomisation = $$4; this.mainHand = $$5; this.textFilteringEnabled = $$6; this.allowsListing = $$7; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/level/ClientInformation;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/server/level/ClientInformation; } public String language() { return this.language; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/level/ClientInformation;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/level/ClientInformation; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/level/ClientInformation;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/level/ClientInformation;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public int viewDistance() { return this.viewDistance; } public ChatVisiblity chatVisibility() { return this.chatVisibility; } public boolean chatColors() { return this.chatColors; } public int modelCustomisation() { return this.modelCustomisation; } public HumanoidArm mainHand() { return this.mainHand; } public boolean textFilteringEnabled() { return this.textFilteringEnabled; } public boolean allowsListing() { return this.allowsListing; }
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
/*    */   public ClientInformation(FriendlyByteBuf $$0) {
/* 23 */     this($$0
/* 24 */         .readUtf(16), $$0
/* 25 */         .readByte(), (ChatVisiblity)$$0
/* 26 */         .readEnum(ChatVisiblity.class), $$0
/* 27 */         .readBoolean(), $$0
/* 28 */         .readUnsignedByte(), (HumanoidArm)$$0
/* 29 */         .readEnum(HumanoidArm.class), $$0
/* 30 */         .readBoolean(), $$0
/* 31 */         .readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 36 */     $$0.writeUtf(this.language);
/* 37 */     $$0.writeByte(this.viewDistance);
/* 38 */     $$0.writeEnum((Enum)this.chatVisibility);
/* 39 */     $$0.writeBoolean(this.chatColors);
/* 40 */     $$0.writeByte(this.modelCustomisation);
/* 41 */     $$0.writeEnum((Enum)this.mainHand);
/* 42 */     $$0.writeBoolean(this.textFilteringEnabled);
/* 43 */     $$0.writeBoolean(this.allowsListing);
/*    */   }
/*    */   
/*    */   public static ClientInformation createDefault() {
/* 47 */     return new ClientInformation("en_us", 2, ChatVisiblity.FULL, true, 0, Player.DEFAULT_MAIN_HAND, false, false);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ClientInformation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */