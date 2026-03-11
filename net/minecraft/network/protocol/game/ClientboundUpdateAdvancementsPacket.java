/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.advancements.AdvancementProgress;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class ClientboundUpdateAdvancementsPacket implements Packet<ClientGamePacketListener> {
/*    */   private final boolean reset;
/*    */   private final List<AdvancementHolder> added;
/*    */   private final Set<ResourceLocation> removed;
/*    */   private final Map<ResourceLocation, AdvancementProgress> progress;
/*    */   
/*    */   public ClientboundUpdateAdvancementsPacket(boolean $$0, Collection<AdvancementHolder> $$1, Set<ResourceLocation> $$2, Map<ResourceLocation, AdvancementProgress> $$3) {
/* 22 */     this.reset = $$0;
/* 23 */     this.added = List.copyOf($$1);
/* 24 */     this.removed = Set.copyOf($$2);
/* 25 */     this.progress = Map.copyOf($$3);
/*    */   }
/*    */   
/*    */   public ClientboundUpdateAdvancementsPacket(FriendlyByteBuf $$0) {
/* 29 */     this.reset = $$0.readBoolean();
/* 30 */     this.added = $$0.readList(AdvancementHolder::read);
/* 31 */     this.removed = (Set<ResourceLocation>)$$0.readCollection(Sets::newLinkedHashSetWithExpectedSize, FriendlyByteBuf::readResourceLocation);
/* 32 */     this.progress = $$0.readMap(FriendlyByteBuf::readResourceLocation, AdvancementProgress::fromNetwork);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 37 */     $$0.writeBoolean(this.reset);
/*    */     
/* 39 */     $$0.writeCollection(this.added, ($$0, $$1) -> $$1.write($$0));
/* 40 */     $$0.writeCollection(this.removed, FriendlyByteBuf::writeResourceLocation);
/* 41 */     $$0.writeMap(this.progress, FriendlyByteBuf::writeResourceLocation, ($$0, $$1) -> $$1.serializeToNetwork($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 46 */     $$0.handleUpdateAdvancementsPacket(this);
/*    */   }
/*    */   
/*    */   public List<AdvancementHolder> getAdded() {
/* 50 */     return this.added;
/*    */   }
/*    */   
/*    */   public Set<ResourceLocation> getRemoved() {
/* 54 */     return this.removed;
/*    */   }
/*    */   
/*    */   public Map<ResourceLocation, AdvancementProgress> getProgress() {
/* 58 */     return this.progress;
/*    */   }
/*    */   
/*    */   public boolean shouldReset() {
/* 62 */     return this.reset;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundUpdateAdvancementsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */