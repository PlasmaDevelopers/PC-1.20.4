/*    */ package net.minecraft.network.protocol.game;
/*    */ public final class CommonPlayerSpawnInfo extends Record {
/*    */   private final ResourceKey<DimensionType> dimensionType;
/*    */   private final ResourceKey<Level> dimension;
/*    */   private final long seed;
/*    */   private final GameType gameType;
/*    */   @Nullable
/*    */   private final GameType previousGameType;
/*    */   private final boolean isDebug;
/*    */   private final boolean isFlat;
/*    */   private final Optional<GlobalPos> lastDeathLocation;
/*    */   private final int portalCooldown;
/*    */   
/* 14 */   public CommonPlayerSpawnInfo(ResourceKey<DimensionType> $$0, ResourceKey<Level> $$1, long $$2, GameType $$3, @Nullable GameType $$4, boolean $$5, boolean $$6, Optional<GlobalPos> $$7, int $$8) { this.dimensionType = $$0; this.dimension = $$1; this.seed = $$2; this.gameType = $$3; this.previousGameType = $$4; this.isDebug = $$5; this.isFlat = $$6; this.lastDeathLocation = $$7; this.portalCooldown = $$8; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/CommonPlayerSpawnInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/CommonPlayerSpawnInfo; } public ResourceKey<DimensionType> dimensionType() { return this.dimensionType; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/CommonPlayerSpawnInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/CommonPlayerSpawnInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/CommonPlayerSpawnInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/CommonPlayerSpawnInfo;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public ResourceKey<Level> dimension() { return this.dimension; } public long seed() { return this.seed; } public GameType gameType() { return this.gameType; } @Nullable public GameType previousGameType() { return this.previousGameType; } public boolean isDebug() { return this.isDebug; } public boolean isFlat() { return this.isFlat; } public Optional<GlobalPos> lastDeathLocation() { return this.lastDeathLocation; } public int portalCooldown() { return this.portalCooldown; }
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
/*    */   public CommonPlayerSpawnInfo(FriendlyByteBuf $$0) {
/* 26 */     this($$0
/* 27 */         .readResourceKey(Registries.DIMENSION_TYPE), $$0
/* 28 */         .readResourceKey(Registries.DIMENSION), $$0
/* 29 */         .readLong(), 
/* 30 */         GameType.byId($$0.readByte()), 
/* 31 */         GameType.byNullableId($$0.readByte()), $$0
/* 32 */         .readBoolean(), $$0
/* 33 */         .readBoolean(), $$0
/* 34 */         .readOptional(FriendlyByteBuf::readGlobalPos), $$0
/* 35 */         .readVarInt());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 40 */     $$0.writeResourceKey(this.dimensionType);
/* 41 */     $$0.writeResourceKey(this.dimension);
/* 42 */     $$0.writeLong(this.seed);
/* 43 */     $$0.writeByte(this.gameType.getId());
/* 44 */     $$0.writeByte(GameType.getNullableId(this.previousGameType));
/* 45 */     $$0.writeBoolean(this.isDebug);
/* 46 */     $$0.writeBoolean(this.isFlat);
/* 47 */     $$0.writeOptional(this.lastDeathLocation, FriendlyByteBuf::writeGlobalPos);
/* 48 */     $$0.writeVarInt(this.portalCooldown);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\CommonPlayerSpawnInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */