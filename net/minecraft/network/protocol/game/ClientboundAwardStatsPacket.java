/*    */ package net.minecraft.network.protocol.game;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.stats.Stat;
/*    */ import net.minecraft.stats.StatType;
/*    */ 
/*    */ public class ClientboundAwardStatsPacket implements Packet<ClientGamePacketListener> {
/*    */   private final Object2IntMap<Stat<?>> stats;
/*    */   
/*    */   public ClientboundAwardStatsPacket(Object2IntMap<Stat<?>> $$0) {
/* 17 */     this.stats = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientboundAwardStatsPacket(FriendlyByteBuf $$0) {
/* 23 */     this.stats = (Object2IntMap<Stat<?>>)$$0.readMap(it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap::new, $$1 -> { StatType<?> $$2 = (StatType)$$1.readById((IdMap)BuiltInRegistries.STAT_TYPE); return readStatCap($$0, $$2); }FriendlyByteBuf::readVarInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> Stat<T> readStatCap(FriendlyByteBuf $$0, StatType<T> $$1) {
/* 34 */     return $$1.get($$0.readById((IdMap)$$1.getRegistry()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 39 */     $$0.handleAwardStats(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 44 */     $$0.writeMap((Map)this.stats, ClientboundAwardStatsPacket::writeStatCap, FriendlyByteBuf::writeVarInt);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> void writeStatCap(FriendlyByteBuf $$0, Stat<T> $$1) {
/* 50 */     $$0.writeId((IdMap)BuiltInRegistries.STAT_TYPE, $$1.getType());
/* 51 */     $$0.writeId((IdMap)$$1.getType().getRegistry(), $$1.getValue());
/*    */   }
/*    */   
/*    */   public Map<Stat<?>, Integer> getStats() {
/* 55 */     return (Map)this.stats;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundAwardStatsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */