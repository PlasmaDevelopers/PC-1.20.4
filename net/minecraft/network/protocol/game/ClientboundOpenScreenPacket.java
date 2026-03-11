/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.inventory.MenuType;
/*    */ 
/*    */ public class ClientboundOpenScreenPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int containerId;
/*    */   
/*    */   public ClientboundOpenScreenPacket(int $$0, MenuType<?> $$1, Component $$2) {
/* 17 */     this.containerId = $$0;
/* 18 */     this.type = $$1;
/* 19 */     this.title = $$2;
/*    */   }
/*    */   private final MenuType<?> type; private final Component title;
/*    */   public ClientboundOpenScreenPacket(FriendlyByteBuf $$0) {
/* 23 */     this.containerId = $$0.readVarInt();
/* 24 */     this.type = (MenuType)$$0.readById((IdMap)BuiltInRegistries.MENU);
/* 25 */     this.title = $$0.readComponentTrusted();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeVarInt(this.containerId);
/* 31 */     $$0.writeId((IdMap)BuiltInRegistries.MENU, this.type);
/* 32 */     $$0.writeComponent(this.title);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 37 */     $$0.handleOpenScreen(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 41 */     return this.containerId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MenuType<?> getType() {
/* 46 */     return this.type;
/*    */   }
/*    */   
/*    */   public Component getTitle() {
/* 50 */     return this.title;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundOpenScreenPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */