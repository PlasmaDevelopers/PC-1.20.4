/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ 
/*    */ public final class GameEventListenerDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final PositionSource listenerPos;
/*    */   private final int listenerRange;
/*    */   
/*  8 */   public GameEventListenerDebugPayload(PositionSource $$0, int $$1) { this.listenerPos = $$0; this.listenerRange = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/GameEventListenerDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GameEventListenerDebugPayload; } public PositionSource listenerPos() { return this.listenerPos; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/GameEventListenerDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GameEventListenerDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/GameEventListenerDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/GameEventListenerDebugPayload;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public int listenerRange() { return this.listenerRange; }
/*  9 */    public static final ResourceLocation ID = new ResourceLocation("debug/game_event_listeners");
/*    */   
/*    */   public GameEventListenerDebugPayload(FriendlyByteBuf $$0) {
/* 12 */     this(
/* 13 */         PositionSourceType.fromNetwork($$0), $$0
/* 14 */         .readVarInt());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     PositionSourceType.toNetwork(this.listenerPos, $$0);
/* 21 */     $$0.writeVarInt(this.listenerRange);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 26 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\GameEventListenerDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */