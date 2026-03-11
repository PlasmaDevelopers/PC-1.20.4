/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.TagNetworkSerialization;
/*    */ 
/*    */ public class ClientboundUpdateTagsPacket implements Packet<ClientCommonPacketListener> {
/*    */   private final Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> tags;
/*    */   
/*    */   public ClientboundUpdateTagsPacket(Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> $$0) {
/* 15 */     this.tags = $$0;
/*    */   }
/*    */   
/*    */   public ClientboundUpdateTagsPacket(FriendlyByteBuf $$0) {
/* 19 */     this.tags = $$0.readMap(FriendlyByteBuf::readRegistryKey, TagNetworkSerialization.NetworkPayload::read);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 27 */     $$0.writeMap(this.tags, FriendlyByteBuf::writeResourceKey, ($$0, $$1) -> $$1.write($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handle(ClientCommonPacketListener $$0) {
/* 35 */     $$0.handleUpdateTags(this);
/*    */   }
/*    */   
/*    */   public Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> getTags() {
/* 39 */     return this.tags;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientboundUpdateTagsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */