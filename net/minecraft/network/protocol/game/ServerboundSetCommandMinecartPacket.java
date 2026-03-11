/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
/*    */ import net.minecraft.world.level.BaseCommandBlock;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ServerboundSetCommandMinecartPacket implements Packet<ServerGamePacketListener> {
/*    */   private final int entity;
/*    */   private final String command;
/*    */   private final boolean trackOutput;
/*    */   
/*    */   public ServerboundSetCommandMinecartPacket(int $$0, String $$1, boolean $$2) {
/* 18 */     this.entity = $$0;
/* 19 */     this.command = $$1;
/* 20 */     this.trackOutput = $$2;
/*    */   }
/*    */   
/*    */   public ServerboundSetCommandMinecartPacket(FriendlyByteBuf $$0) {
/* 24 */     this.entity = $$0.readVarInt();
/* 25 */     this.command = $$0.readUtf();
/* 26 */     this.trackOutput = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 31 */     $$0.writeVarInt(this.entity);
/* 32 */     $$0.writeUtf(this.command);
/* 33 */     $$0.writeBoolean(this.trackOutput);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 38 */     $$0.handleSetCommandMinecart(this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BaseCommandBlock getCommandBlock(Level $$0) {
/* 43 */     Entity $$1 = $$0.getEntity(this.entity);
/* 44 */     if ($$1 instanceof MinecartCommandBlock) {
/* 45 */       return ((MinecartCommandBlock)$$1).getCommandBlock();
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 52 */     return this.command;
/*    */   }
/*    */   
/*    */   public boolean isTrackOutput() {
/* 56 */     return this.trackOutput;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSetCommandMinecartPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */