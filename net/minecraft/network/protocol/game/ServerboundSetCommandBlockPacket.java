/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*    */ 
/*    */ public class ServerboundSetCommandBlockPacket implements Packet<ServerGamePacketListener> {
/*    */   private static final int FLAG_TRACK_OUTPUT = 1;
/*    */   private static final int FLAG_CONDITIONAL = 2;
/*    */   private static final int FLAG_AUTOMATIC = 4;
/*    */   private final BlockPos pos;
/*    */   private final String command;
/*    */   private final boolean trackOutput;
/*    */   private final boolean conditional;
/*    */   private final boolean automatic;
/*    */   private final CommandBlockEntity.Mode mode;
/*    */   
/*    */   public ServerboundSetCommandBlockPacket(BlockPos $$0, String $$1, CommandBlockEntity.Mode $$2, boolean $$3, boolean $$4, boolean $$5) {
/* 21 */     this.pos = $$0;
/* 22 */     this.command = $$1;
/* 23 */     this.trackOutput = $$3;
/* 24 */     this.conditional = $$4;
/* 25 */     this.automatic = $$5;
/* 26 */     this.mode = $$2;
/*    */   }
/*    */   
/*    */   public ServerboundSetCommandBlockPacket(FriendlyByteBuf $$0) {
/* 30 */     this.pos = $$0.readBlockPos();
/* 31 */     this.command = $$0.readUtf();
/* 32 */     this.mode = (CommandBlockEntity.Mode)$$0.readEnum(CommandBlockEntity.Mode.class);
/* 33 */     int $$1 = $$0.readByte();
/* 34 */     this.trackOutput = (($$1 & 0x1) != 0);
/* 35 */     this.conditional = (($$1 & 0x2) != 0);
/* 36 */     this.automatic = (($$1 & 0x4) != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 41 */     $$0.writeBlockPos(this.pos);
/* 42 */     $$0.writeUtf(this.command);
/* 43 */     $$0.writeEnum((Enum)this.mode);
/* 44 */     int $$1 = 0;
/* 45 */     if (this.trackOutput) {
/* 46 */       $$1 |= 0x1;
/*    */     }
/* 48 */     if (this.conditional) {
/* 49 */       $$1 |= 0x2;
/*    */     }
/* 51 */     if (this.automatic) {
/* 52 */       $$1 |= 0x4;
/*    */     }
/* 54 */     $$0.writeByte($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 59 */     $$0.handleSetCommandBlock(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 63 */     return this.pos;
/*    */   }
/*    */   
/*    */   public String getCommand() {
/* 67 */     return this.command;
/*    */   }
/*    */   
/*    */   public boolean isTrackOutput() {
/* 71 */     return this.trackOutput;
/*    */   }
/*    */   
/*    */   public boolean isConditional() {
/* 75 */     return this.conditional;
/*    */   }
/*    */   
/*    */   public boolean isAutomatic() {
/* 79 */     return this.automatic;
/*    */   }
/*    */   
/*    */   public CommandBlockEntity.Mode getMode() {
/* 83 */     return this.mode;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSetCommandBlockPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */