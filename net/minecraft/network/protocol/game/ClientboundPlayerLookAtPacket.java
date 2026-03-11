/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ClientboundPlayerLookAtPacket implements Packet<ClientGamePacketListener> {
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   private final int entity;
/*    */   private final EntityAnchorArgument.Anchor fromAnchor;
/*    */   private final EntityAnchorArgument.Anchor toAnchor;
/*    */   private final boolean atEntity;
/*    */   
/*    */   public ClientboundPlayerLookAtPacket(EntityAnchorArgument.Anchor $$0, double $$1, double $$2, double $$3) {
/* 22 */     this.fromAnchor = $$0;
/* 23 */     this.x = $$1;
/* 24 */     this.y = $$2;
/* 25 */     this.z = $$3;
/* 26 */     this.entity = 0;
/* 27 */     this.atEntity = false;
/* 28 */     this.toAnchor = null;
/*    */   }
/*    */   
/*    */   public ClientboundPlayerLookAtPacket(EntityAnchorArgument.Anchor $$0, Entity $$1, EntityAnchorArgument.Anchor $$2) {
/* 32 */     this.fromAnchor = $$0;
/* 33 */     this.entity = $$1.getId();
/* 34 */     this.toAnchor = $$2;
/* 35 */     Vec3 $$3 = $$2.apply($$1);
/* 36 */     this.x = $$3.x;
/* 37 */     this.y = $$3.y;
/* 38 */     this.z = $$3.z;
/* 39 */     this.atEntity = true;
/*    */   }
/*    */   
/*    */   public ClientboundPlayerLookAtPacket(FriendlyByteBuf $$0) {
/* 43 */     this.fromAnchor = (EntityAnchorArgument.Anchor)$$0.readEnum(EntityAnchorArgument.Anchor.class);
/* 44 */     this.x = $$0.readDouble();
/* 45 */     this.y = $$0.readDouble();
/* 46 */     this.z = $$0.readDouble();
/* 47 */     this.atEntity = $$0.readBoolean();
/* 48 */     if (this.atEntity) {
/* 49 */       this.entity = $$0.readVarInt();
/* 50 */       this.toAnchor = (EntityAnchorArgument.Anchor)$$0.readEnum(EntityAnchorArgument.Anchor.class);
/*    */     } else {
/* 52 */       this.entity = 0;
/* 53 */       this.toAnchor = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 59 */     $$0.writeEnum((Enum)this.fromAnchor);
/* 60 */     $$0.writeDouble(this.x);
/* 61 */     $$0.writeDouble(this.y);
/* 62 */     $$0.writeDouble(this.z);
/* 63 */     $$0.writeBoolean(this.atEntity);
/* 64 */     if (this.atEntity) {
/* 65 */       $$0.writeVarInt(this.entity);
/* 66 */       $$0.writeEnum((Enum)this.toAnchor);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 72 */     $$0.handleLookAt(this);
/*    */   }
/*    */   
/*    */   public EntityAnchorArgument.Anchor getFromAnchor() {
/* 76 */     return this.fromAnchor;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getPosition(Level $$0) {
/* 81 */     if (this.atEntity) {
/* 82 */       Entity $$1 = $$0.getEntity(this.entity);
/* 83 */       if ($$1 == null) {
/* 84 */         return new Vec3(this.x, this.y, this.z);
/*    */       }
/* 86 */       return this.toAnchor.apply($$1);
/*    */     } 
/* 88 */     return new Vec3(this.x, this.y, this.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerLookAtPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */