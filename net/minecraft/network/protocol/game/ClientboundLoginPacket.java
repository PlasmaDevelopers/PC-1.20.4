/*    */ package net.minecraft.network.protocol.game;
/*    */ public final class ClientboundLoginPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final int playerId;
/*    */   private final boolean hardcore;
/*    */   private final Set<ResourceKey<Level>> levels;
/*    */   private final int maxPlayers;
/*    */   private final int chunkRadius;
/*    */   private final int simulationDistance;
/*    */   private final boolean reducedDebugInfo;
/*    */   private final boolean showDeathScreen;
/*    */   private final boolean doLimitedCrafting;
/*    */   private final CommonPlayerSpawnInfo commonPlayerSpawnInfo;
/*    */   
/* 14 */   public ClientboundLoginPacket(int $$0, boolean $$1, Set<ResourceKey<Level>> $$2, int $$3, int $$4, int $$5, boolean $$6, boolean $$7, boolean $$8, CommonPlayerSpawnInfo $$9) { this.playerId = $$0; this.hardcore = $$1; this.levels = $$2; this.maxPlayers = $$3; this.chunkRadius = $$4; this.simulationDistance = $$5; this.reducedDebugInfo = $$6; this.showDeathScreen = $$7; this.doLimitedCrafting = $$8; this.commonPlayerSpawnInfo = $$9; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundLoginPacket; } public int playerId() { return this.playerId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundLoginPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean hardcore() { return this.hardcore; } public Set<ResourceKey<Level>> levels() { return this.levels; } public int maxPlayers() { return this.maxPlayers; } public int chunkRadius() { return this.chunkRadius; } public int simulationDistance() { return this.simulationDistance; } public boolean reducedDebugInfo() { return this.reducedDebugInfo; } public boolean showDeathScreen() { return this.showDeathScreen; } public boolean doLimitedCrafting() { return this.doLimitedCrafting; } public CommonPlayerSpawnInfo commonPlayerSpawnInfo() { return this.commonPlayerSpawnInfo; }
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
/*    */   public ClientboundLoginPacket(FriendlyByteBuf $$0) {
/* 27 */     this($$0
/* 28 */         .readInt(), $$0
/* 29 */         .readBoolean(), (Set<ResourceKey<Level>>)$$0
/* 30 */         .readCollection(Sets::newHashSetWithExpectedSize, $$0 -> $$0.readResourceKey(Registries.DIMENSION)), $$0
/* 31 */         .readVarInt(), $$0
/* 32 */         .readVarInt(), $$0
/* 33 */         .readVarInt(), $$0
/* 34 */         .readBoolean(), $$0
/* 35 */         .readBoolean(), $$0
/* 36 */         .readBoolean(), new CommonPlayerSpawnInfo($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 43 */     $$0.writeInt(this.playerId);
/* 44 */     $$0.writeBoolean(this.hardcore);
/* 45 */     $$0.writeCollection(this.levels, FriendlyByteBuf::writeResourceKey);
/* 46 */     $$0.writeVarInt(this.maxPlayers);
/* 47 */     $$0.writeVarInt(this.chunkRadius);
/* 48 */     $$0.writeVarInt(this.simulationDistance);
/* 49 */     $$0.writeBoolean(this.reducedDebugInfo);
/* 50 */     $$0.writeBoolean(this.showDeathScreen);
/* 51 */     $$0.writeBoolean(this.doLimitedCrafting);
/* 52 */     this.commonPlayerSpawnInfo.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 57 */     $$0.handleLogin(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLoginPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */