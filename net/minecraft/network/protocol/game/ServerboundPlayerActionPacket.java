/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundPlayerActionPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   private final BlockPos pos;
/*    */   private final Direction direction;
/*    */   
/*    */   public ServerboundPlayerActionPacket(Action $$0, BlockPos $$1, Direction $$2, int $$3) {
/* 15 */     this.action = $$0;
/* 16 */     this.pos = $$1.immutable();
/* 17 */     this.direction = $$2;
/* 18 */     this.sequence = $$3;
/*    */   }
/*    */   private final Action action; private final int sequence;
/*    */   public ServerboundPlayerActionPacket(Action $$0, BlockPos $$1, Direction $$2) {
/* 22 */     this($$0, $$1, $$2, 0);
/*    */   }
/*    */   
/*    */   public ServerboundPlayerActionPacket(FriendlyByteBuf $$0) {
/* 26 */     this.action = (Action)$$0.readEnum(Action.class);
/* 27 */     this.pos = $$0.readBlockPos();
/* 28 */     this.direction = Direction.from3DDataValue($$0.readUnsignedByte());
/* 29 */     this.sequence = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 34 */     $$0.writeEnum(this.action);
/* 35 */     $$0.writeBlockPos(this.pos);
/* 36 */     $$0.writeByte(this.direction.get3DDataValue());
/* 37 */     $$0.writeVarInt(this.sequence);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 42 */     $$0.handlePlayerAction(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 46 */     return this.pos;
/*    */   }
/*    */   
/*    */   public Direction getDirection() {
/* 50 */     return this.direction;
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 54 */     return this.action;
/*    */   }
/*    */   
/*    */   public int getSequence() {
/* 58 */     return this.sequence;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 62 */     START_DESTROY_BLOCK,
/* 63 */     ABORT_DESTROY_BLOCK,
/* 64 */     STOP_DESTROY_BLOCK,
/* 65 */     DROP_ALL_ITEMS,
/* 66 */     DROP_ITEM,
/* 67 */     RELEASE_USE_ITEM,
/* 68 */     SWAP_ITEM_WITH_OFFHAND;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundPlayerActionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */