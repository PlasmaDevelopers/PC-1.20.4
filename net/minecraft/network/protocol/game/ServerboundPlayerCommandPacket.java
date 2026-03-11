/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class ServerboundPlayerCommandPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   private final int id;
/*    */   
/*    */   public ServerboundPlayerCommandPacket(Entity $$0, Action $$1) {
/* 13 */     this($$0, $$1, 0);
/*    */   }
/*    */   private final Action action; private final int data;
/*    */   public ServerboundPlayerCommandPacket(Entity $$0, Action $$1, int $$2) {
/* 17 */     this.id = $$0.getId();
/* 18 */     this.action = $$1;
/* 19 */     this.data = $$2;
/*    */   }
/*    */   
/*    */   public ServerboundPlayerCommandPacket(FriendlyByteBuf $$0) {
/* 23 */     this.id = $$0.readVarInt();
/* 24 */     this.action = (Action)$$0.readEnum(Action.class);
/* 25 */     this.data = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeVarInt(this.id);
/* 31 */     $$0.writeEnum(this.action);
/* 32 */     $$0.writeVarInt(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 37 */     $$0.handlePlayerCommand(this);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 41 */     return this.id;
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 45 */     return this.action;
/*    */   }
/*    */   
/*    */   public int getData() {
/* 49 */     return this.data;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 53 */     PRESS_SHIFT_KEY,
/* 54 */     RELEASE_SHIFT_KEY,
/* 55 */     STOP_SLEEPING,
/* 56 */     START_SPRINTING,
/* 57 */     STOP_SPRINTING,
/* 58 */     START_RIDING_JUMP,
/* 59 */     STOP_RIDING_JUMP,
/* 60 */     OPEN_INVENTORY,
/* 61 */     START_FALL_FLYING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundPlayerCommandPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */