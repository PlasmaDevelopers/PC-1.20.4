/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ContainerSynchronizer;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements ContainerSynchronizer
/*     */ {
/*     */   public void sendInitialData(AbstractContainerMenu $$0, NonNullList<ItemStack> $$1, ItemStack $$2, int[] $$3) {
/* 225 */     ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetContentPacket($$0.containerId, $$0.incrementStateId(), $$1, $$2));
/* 226 */     for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/* 227 */       broadcastDataValue($$0, $$4, $$3[$$4]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendSlotChange(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/* 233 */     ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetSlotPacket($$0.containerId, $$0.incrementStateId(), $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendCarriedChange(AbstractContainerMenu $$0, ItemStack $$1) {
/* 238 */     ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetSlotPacket(-1, $$0.incrementStateId(), -1, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendDataChange(AbstractContainerMenu $$0, int $$1, int $$2) {
/* 243 */     broadcastDataValue($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void broadcastDataValue(AbstractContainerMenu $$0, int $$1, int $$2) {
/* 247 */     ServerPlayer.this.connection.send((Packet)new ClientboundContainerSetDataPacket($$0.containerId, $$1, $$2));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ServerPlayer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */