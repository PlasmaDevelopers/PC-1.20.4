/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ClientboundRemoveMobEffectPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int entityId;
/*    */   
/*    */   public ClientboundRemoveMobEffectPacket(int $$0, MobEffect $$1) {
/* 17 */     this.entityId = $$0;
/* 18 */     this.effect = $$1;
/*    */   }
/*    */   private final MobEffect effect;
/*    */   public ClientboundRemoveMobEffectPacket(FriendlyByteBuf $$0) {
/* 22 */     this.entityId = $$0.readVarInt();
/* 23 */     this.effect = (MobEffect)$$0.readById((IdMap)BuiltInRegistries.MOB_EFFECT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 28 */     $$0.writeVarInt(this.entityId);
/* 29 */     $$0.writeId((IdMap)BuiltInRegistries.MOB_EFFECT, this.effect);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 34 */     $$0.handleRemoveMobEffect(this);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity(Level $$0) {
/* 39 */     return $$0.getEntity(this.entityId);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MobEffect getEffect() {
/* 44 */     return this.effect;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundRemoveMobEffectPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */