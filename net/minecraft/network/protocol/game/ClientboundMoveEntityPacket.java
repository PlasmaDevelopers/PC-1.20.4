/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public abstract class ClientboundMoveEntityPacket
/*     */   implements Packet<ClientGamePacketListener>
/*     */ {
/*     */   protected final int entityId;
/*     */   protected final short xa;
/*     */   protected final short ya;
/*     */   protected final short za;
/*     */   protected final byte yRot;
/*     */   protected final byte xRot;
/*     */   protected final boolean onGround;
/*     */   protected final boolean hasRot;
/*     */   protected final boolean hasPos;
/*     */   
/*     */   public static class PosRot
/*     */     extends ClientboundMoveEntityPacket {
/*     */     public PosRot(int $$0, short $$1, short $$2, short $$3, byte $$4, byte $$5, boolean $$6) {
/*  26 */       super($$0, $$1, $$2, $$3, $$4, $$5, $$6, true, true);
/*     */     }
/*     */     
/*     */     public static PosRot read(FriendlyByteBuf $$0) {
/*  30 */       int $$1 = $$0.readVarInt();
/*  31 */       short $$2 = $$0.readShort();
/*  32 */       short $$3 = $$0.readShort();
/*  33 */       short $$4 = $$0.readShort();
/*  34 */       byte $$5 = $$0.readByte();
/*  35 */       byte $$6 = $$0.readByte();
/*  36 */       boolean $$7 = $$0.readBoolean();
/*     */       
/*  38 */       return new PosRot($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  43 */       $$0.writeVarInt(this.entityId);
/*  44 */       $$0.writeShort(this.xa);
/*  45 */       $$0.writeShort(this.ya);
/*  46 */       $$0.writeShort(this.za);
/*  47 */       $$0.writeByte(this.yRot);
/*  48 */       $$0.writeByte(this.xRot);
/*  49 */       $$0.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Pos extends ClientboundMoveEntityPacket {
/*     */     public Pos(int $$0, short $$1, short $$2, short $$3, boolean $$4) {
/*  55 */       super($$0, $$1, $$2, $$3, (byte)0, (byte)0, $$4, false, true);
/*     */     }
/*     */     
/*     */     public static Pos read(FriendlyByteBuf $$0) {
/*  59 */       int $$1 = $$0.readVarInt();
/*  60 */       short $$2 = $$0.readShort();
/*  61 */       short $$3 = $$0.readShort();
/*  62 */       short $$4 = $$0.readShort();
/*  63 */       boolean $$5 = $$0.readBoolean();
/*     */       
/*  65 */       return new Pos($$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  70 */       $$0.writeVarInt(this.entityId);
/*  71 */       $$0.writeShort(this.xa);
/*  72 */       $$0.writeShort(this.ya);
/*  73 */       $$0.writeShort(this.za);
/*  74 */       $$0.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Rot extends ClientboundMoveEntityPacket {
/*     */     public Rot(int $$0, byte $$1, byte $$2, boolean $$3) {
/*  80 */       super($$0, (short)0, (short)0, (short)0, $$1, $$2, $$3, true, false);
/*     */     }
/*     */     
/*     */     public static Rot read(FriendlyByteBuf $$0) {
/*  84 */       int $$1 = $$0.readVarInt();
/*  85 */       byte $$2 = $$0.readByte();
/*  86 */       byte $$3 = $$0.readByte();
/*  87 */       boolean $$4 = $$0.readBoolean();
/*     */       
/*  89 */       return new Rot($$1, $$2, $$3, $$4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  94 */       $$0.writeVarInt(this.entityId);
/*  95 */       $$0.writeByte(this.yRot);
/*  96 */       $$0.writeByte(this.xRot);
/*  97 */       $$0.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   protected ClientboundMoveEntityPacket(int $$0, short $$1, short $$2, short $$3, byte $$4, byte $$5, boolean $$6, boolean $$7, boolean $$8) {
/* 102 */     this.entityId = $$0;
/* 103 */     this.xa = $$1;
/* 104 */     this.ya = $$2;
/* 105 */     this.za = $$3;
/* 106 */     this.yRot = $$4;
/* 107 */     this.xRot = $$5;
/* 108 */     this.onGround = $$6;
/* 109 */     this.hasRot = $$7;
/* 110 */     this.hasPos = $$8;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/* 115 */     $$0.handleMoveEntity(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 120 */     return "Entity_" + super.toString();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntity(Level $$0) {
/* 125 */     return $$0.getEntity(this.entityId);
/*     */   }
/*     */   
/*     */   public short getXa() {
/* 129 */     return this.xa;
/*     */   }
/*     */   
/*     */   public short getYa() {
/* 133 */     return this.ya;
/*     */   }
/*     */   
/*     */   public short getZa() {
/* 137 */     return this.za;
/*     */   }
/*     */   
/*     */   public byte getyRot() {
/* 141 */     return this.yRot;
/*     */   }
/*     */   
/*     */   public byte getxRot() {
/* 145 */     return this.xRot;
/*     */   }
/*     */   
/*     */   public boolean hasRotation() {
/* 149 */     return this.hasRot;
/*     */   }
/*     */   
/*     */   public boolean hasPosition() {
/* 153 */     return this.hasPos;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/* 157 */     return this.onGround;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundMoveEntityPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */