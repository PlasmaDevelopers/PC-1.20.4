/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ 
/*    */ public class ServerItemCooldowns
/*    */   extends ItemCooldowns {
/*    */   public ServerItemCooldowns(ServerPlayer $$0) {
/* 10 */     this.player = $$0;
/*    */   }
/*    */   private final ServerPlayer player;
/*    */   
/*    */   protected void onCooldownStarted(Item $$0, int $$1) {
/* 15 */     super.onCooldownStarted($$0, $$1);
/* 16 */     this.player.connection.send((Packet)new ClientboundCooldownPacket($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCooldownEnded(Item $$0) {
/* 21 */     super.onCooldownEnded($$0);
/* 22 */     this.player.connection.send((Packet)new ClientboundCooldownPacket($$0, 0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ServerItemCooldowns.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */