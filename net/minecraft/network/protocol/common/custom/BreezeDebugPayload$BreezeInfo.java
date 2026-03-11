/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BreezeInfo
/*    */   extends Record
/*    */ {
/*    */   private final UUID uuid;
/*    */   private final int id;
/*    */   private final Integer attackTarget;
/*    */   private final BlockPos jumpTarget;
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/BreezeDebugPayload$BreezeInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #29	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BreezeDebugPayload$BreezeInfo;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/BreezeDebugPayload$BreezeInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #29	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/BreezeDebugPayload$BreezeInfo;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public BreezeInfo(UUID $$0, int $$1, Integer $$2, BlockPos $$3) {
/* 29 */     this.uuid = $$0; this.id = $$1; this.attackTarget = $$2; this.jumpTarget = $$3; } public UUID uuid() { return this.uuid; } public int id() { return this.id; } public Integer attackTarget() { return this.attackTarget; } public BlockPos jumpTarget() { return this.jumpTarget; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BreezeInfo(FriendlyByteBuf $$0) {
/* 36 */     this($$0
/* 37 */         .readUUID(), $$0
/* 38 */         .readInt(), (Integer)$$0
/* 39 */         .readNullable(FriendlyByteBuf::readInt), (BlockPos)$$0
/* 40 */         .readNullable(FriendlyByteBuf::readBlockPos));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 45 */     $$0.writeUUID(this.uuid);
/* 46 */     $$0.writeInt(this.id);
/* 47 */     $$0.writeNullable(this.attackTarget, FriendlyByteBuf::writeInt);
/* 48 */     $$0.writeNullable(this.jumpTarget, FriendlyByteBuf::writeBlockPos);
/*    */   }
/*    */   
/*    */   public String generateName() {
/* 52 */     return DebugEntityNameGenerator.getEntityName(this.uuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return generateName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\BreezeDebugPayload$BreezeInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */