/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class ClientboundDamageEventPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final int entityId;
/*    */   private final int sourceTypeId;
/*    */   private final int sourceCauseId;
/*    */   private final int sourceDirectId;
/*    */   private final Optional<Vec3> sourcePosition;
/*    */   
/* 15 */   public ClientboundDamageEventPacket(int $$0, int $$1, int $$2, int $$3, Optional<Vec3> $$4) { this.entityId = $$0; this.sourceTypeId = $$1; this.sourceCauseId = $$2; this.sourceDirectId = $$3; this.sourcePosition = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundDamageEventPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 15 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundDamageEventPacket; } public int entityId() { return this.entityId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundDamageEventPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundDamageEventPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundDamageEventPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundDamageEventPacket;
/* 15 */     //   0	8	1	$$0	Ljava/lang/Object; } public int sourceTypeId() { return this.sourceTypeId; } public int sourceCauseId() { return this.sourceCauseId; } public int sourceDirectId() { return this.sourceDirectId; } public Optional<Vec3> sourcePosition() { return this.sourcePosition; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientboundDamageEventPacket(Entity $$0, DamageSource $$1) {
/* 23 */     this($$0
/* 24 */         .getId(), $$0
/* 25 */         .level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getId($$1.type()), 
/* 26 */         ($$1.getEntity() != null) ? $$1.getEntity().getId() : -1, 
/* 27 */         ($$1.getDirectEntity() != null) ? $$1.getDirectEntity().getId() : -1, 
/* 28 */         Optional.ofNullable($$1.sourcePositionRaw()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static void writeOptionalEntityId(FriendlyByteBuf $$0, int $$1) {
/* 34 */     $$0.writeVarInt($$1 + 1);
/*    */   }
/*    */   
/*    */   private static int readOptionalEntityId(FriendlyByteBuf $$0) {
/* 38 */     return $$0.readVarInt() - 1;
/*    */   }
/*    */   
/*    */   public ClientboundDamageEventPacket(FriendlyByteBuf $$0) {
/* 42 */     this($$0
/* 43 */         .readVarInt(), $$0
/* 44 */         .readVarInt(), 
/* 45 */         readOptionalEntityId($$0), 
/* 46 */         readOptionalEntityId($$0), $$0
/* 47 */         .readOptional($$0 -> new Vec3($$0.readDouble(), $$0.readDouble(), $$0.readDouble())));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 53 */     $$0.writeVarInt(this.entityId);
/* 54 */     $$0.writeVarInt(this.sourceTypeId);
/* 55 */     writeOptionalEntityId($$0, this.sourceCauseId);
/* 56 */     writeOptionalEntityId($$0, this.sourceDirectId);
/* 57 */     $$0.writeOptional(this.sourcePosition, ($$0, $$1) -> {
/*    */           $$0.writeDouble($$1.x());
/*    */           $$0.writeDouble($$1.y());
/*    */           $$0.writeDouble($$1.z());
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 66 */     $$0.handleDamageEvent(this);
/*    */   }
/*    */   
/*    */   public DamageSource getSource(Level $$0) {
/* 70 */     Holder<DamageType> $$1 = $$0.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolder(this.sourceTypeId).get();
/* 71 */     if (this.sourcePosition.isPresent()) {
/* 72 */       return new DamageSource($$1, this.sourcePosition.get());
/*    */     }
/* 74 */     Entity $$2 = $$0.getEntity(this.sourceCauseId);
/* 75 */     Entity $$3 = $$0.getEntity(this.sourceDirectId);
/* 76 */     return new DamageSource($$1, $$3, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundDamageEventPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */