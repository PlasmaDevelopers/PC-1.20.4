/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ 
/*    */ public class ServerboundSetBeaconPacket implements Packet<ServerGamePacketListener> {
/*    */   private final Optional<MobEffect> primary;
/*    */   
/*    */   public ServerboundSetBeaconPacket(Optional<MobEffect> $$0, Optional<MobEffect> $$1) {
/* 15 */     this.primary = $$0;
/* 16 */     this.secondary = $$1;
/*    */   }
/*    */   private final Optional<MobEffect> secondary;
/*    */   public ServerboundSetBeaconPacket(FriendlyByteBuf $$0) {
/* 20 */     this.primary = $$0.readOptional($$0 -> (MobEffect)$$0.readById((IdMap)BuiltInRegistries.MOB_EFFECT));
/* 21 */     this.secondary = $$0.readOptional($$0 -> (MobEffect)$$0.readById((IdMap)BuiltInRegistries.MOB_EFFECT));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeOptional(this.primary, ($$0, $$1) -> $$0.writeId((IdMap)BuiltInRegistries.MOB_EFFECT, $$1));
/* 27 */     $$0.writeOptional(this.secondary, ($$0, $$1) -> $$0.writeId((IdMap)BuiltInRegistries.MOB_EFFECT, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 32 */     $$0.handleSetBeaconPacket(this);
/*    */   }
/*    */   
/*    */   public Optional<MobEffect> getPrimary() {
/* 36 */     return this.primary;
/*    */   }
/*    */   
/*    */   public Optional<MobEffect> getSecondary() {
/* 40 */     return this.secondary;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSetBeaconPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */