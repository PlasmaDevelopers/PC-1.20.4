/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ public final class GameEventDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final ResourceKey<GameEvent> type;
/*    */   private final Vec3 pos;
/*    */   
/* 10 */   public GameEventDebugPayload(ResourceKey<GameEvent> $$0, Vec3 $$1) { this.type = $$0; this.pos = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/GameEventDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GameEventDebugPayload; } public ResourceKey<GameEvent> type() { return this.type; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/GameEventDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GameEventDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/GameEventDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/GameEventDebugPayload;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public Vec3 pos() { return this.pos; }
/* 11 */    public static final ResourceLocation ID = new ResourceLocation("debug/game_event");
/*    */   
/*    */   public GameEventDebugPayload(FriendlyByteBuf $$0) {
/* 14 */     this($$0
/* 15 */         .readResourceKey(Registries.GAME_EVENT), $$0
/* 16 */         .readVec3());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 22 */     $$0.writeResourceKey(this.type);
/* 23 */     $$0.writeVec3(this.pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 28 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\GameEventDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */