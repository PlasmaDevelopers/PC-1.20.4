/*     */ package net.minecraft.network.protocol.game;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ 
/*     */ public abstract class ServerboundMovePlayerPacket implements Packet<ServerGamePacketListener> {
/*     */   protected final double x;
/*     */   protected final double y;
/*     */   protected final double z;
/*     */   protected final float yRot;
/*     */   protected final float xRot;
/*     */   protected final boolean onGround;
/*     */   protected final boolean hasPos;
/*     */   protected final boolean hasRot;
/*     */   
/*     */   public static class PosRot extends ServerboundMovePlayerPacket {
/*     */     public PosRot(double $$0, double $$1, double $$2, float $$3, float $$4, boolean $$5) {
/*  18 */       super($$0, $$1, $$2, $$3, $$4, $$5, true, true);
/*     */     }
/*     */     
/*     */     public static PosRot read(FriendlyByteBuf $$0) {
/*  22 */       double $$1 = $$0.readDouble();
/*  23 */       double $$2 = $$0.readDouble();
/*  24 */       double $$3 = $$0.readDouble();
/*  25 */       float $$4 = $$0.readFloat();
/*  26 */       float $$5 = $$0.readFloat();
/*  27 */       boolean $$6 = ($$0.readUnsignedByte() != 0);
/*  28 */       return new PosRot($$1, $$2, $$3, $$4, $$5, $$6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  33 */       $$0.writeDouble(this.x);
/*  34 */       $$0.writeDouble(this.y);
/*  35 */       $$0.writeDouble(this.z);
/*  36 */       $$0.writeFloat(this.yRot);
/*  37 */       $$0.writeFloat(this.xRot);
/*  38 */       $$0.writeByte(this.onGround ? 1 : 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Pos extends ServerboundMovePlayerPacket {
/*     */     public Pos(double $$0, double $$1, double $$2, boolean $$3) {
/*  44 */       super($$0, $$1, $$2, 0.0F, 0.0F, $$3, true, false);
/*     */     }
/*     */     
/*     */     public static Pos read(FriendlyByteBuf $$0) {
/*  48 */       double $$1 = $$0.readDouble();
/*  49 */       double $$2 = $$0.readDouble();
/*  50 */       double $$3 = $$0.readDouble();
/*  51 */       boolean $$4 = ($$0.readUnsignedByte() != 0);
/*  52 */       return new Pos($$1, $$2, $$3, $$4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  57 */       $$0.writeDouble(this.x);
/*  58 */       $$0.writeDouble(this.y);
/*  59 */       $$0.writeDouble(this.z);
/*  60 */       $$0.writeByte(this.onGround ? 1 : 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Rot extends ServerboundMovePlayerPacket {
/*     */     public Rot(float $$0, float $$1, boolean $$2) {
/*  66 */       super(0.0D, 0.0D, 0.0D, $$0, $$1, $$2, false, true);
/*     */     }
/*     */     
/*     */     public static Rot read(FriendlyByteBuf $$0) {
/*  70 */       float $$1 = $$0.readFloat();
/*  71 */       float $$2 = $$0.readFloat();
/*  72 */       boolean $$3 = ($$0.readUnsignedByte() != 0);
/*  73 */       return new Rot($$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  78 */       $$0.writeFloat(this.yRot);
/*  79 */       $$0.writeFloat(this.xRot);
/*  80 */       $$0.writeByte(this.onGround ? 1 : 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class StatusOnly extends ServerboundMovePlayerPacket {
/*     */     public StatusOnly(boolean $$0) {
/*  86 */       super(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, $$0, false, false);
/*     */     }
/*     */     
/*     */     public static StatusOnly read(FriendlyByteBuf $$0) {
/*  90 */       boolean $$1 = ($$0.readUnsignedByte() != 0);
/*  91 */       return new StatusOnly($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  96 */       $$0.writeByte(this.onGround ? 1 : 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerboundMovePlayerPacket(double $$0, double $$1, double $$2, float $$3, float $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 102 */     this.x = $$0;
/* 103 */     this.y = $$1;
/* 104 */     this.z = $$2;
/* 105 */     this.yRot = $$3;
/* 106 */     this.xRot = $$4;
/* 107 */     this.onGround = $$5;
/* 108 */     this.hasPos = $$6;
/* 109 */     this.hasRot = $$7;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ServerGamePacketListener $$0) {
/* 114 */     $$0.handleMovePlayer(this);
/*     */   }
/*     */   
/*     */   public double getX(double $$0) {
/* 118 */     return this.hasPos ? this.x : $$0;
/*     */   }
/*     */   
/*     */   public double getY(double $$0) {
/* 122 */     return this.hasPos ? this.y : $$0;
/*     */   }
/*     */   
/*     */   public double getZ(double $$0) {
/* 126 */     return this.hasPos ? this.z : $$0;
/*     */   }
/*     */   
/*     */   public float getYRot(float $$0) {
/* 130 */     return this.hasRot ? this.yRot : $$0;
/*     */   }
/*     */   
/*     */   public float getXRot(float $$0) {
/* 134 */     return this.hasRot ? this.xRot : $$0;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/* 138 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public boolean hasPosition() {
/* 142 */     return this.hasPos;
/*     */   }
/*     */   
/*     */   public boolean hasRotation() {
/* 146 */     return this.hasRot;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundMovePlayerPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */