/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ClientboundAddEntityPacket
/*     */   implements Packet<ClientGamePacketListener>
/*     */ {
/*     */   private static final double MAGICAL_QUANTIZATION = 8000.0D;
/*     */   private static final double LIMIT = 3.9D;
/*     */   private final int id;
/*     */   private final UUID uuid;
/*     */   private final EntityType<?> type;
/*     */   private final double x;
/*     */   private final double y;
/*     */   private final double z;
/*     */   private final int xa;
/*     */   private final int ya;
/*     */   private final int za;
/*     */   private final byte xRot;
/*     */   private final byte yRot;
/*     */   private final byte yHeadRot;
/*     */   private final int data;
/*     */   
/*     */   public ClientboundAddEntityPacket(Entity $$0) {
/*  35 */     this($$0, 0);
/*     */   }
/*     */   
/*     */   public ClientboundAddEntityPacket(Entity $$0, int $$1) {
/*  39 */     this($$0.getId(), $$0.getUUID(), $$0.getX(), $$0.getY(), $$0.getZ(), $$0.getXRot(), $$0.getYRot(), $$0.getType(), $$1, $$0.getDeltaMovement(), $$0.getYHeadRot());
/*     */   }
/*     */   
/*     */   public ClientboundAddEntityPacket(Entity $$0, int $$1, BlockPos $$2) {
/*  43 */     this($$0.getId(), $$0.getUUID(), $$2.getX(), $$2.getY(), $$2.getZ(), $$0.getXRot(), $$0.getYRot(), $$0.getType(), $$1, $$0.getDeltaMovement(), $$0.getYHeadRot());
/*     */   }
/*     */   
/*     */   public ClientboundAddEntityPacket(int $$0, UUID $$1, double $$2, double $$3, double $$4, float $$5, float $$6, EntityType<?> $$7, int $$8, Vec3 $$9, double $$10) {
/*  47 */     this.id = $$0;
/*  48 */     this.uuid = $$1;
/*  49 */     this.x = $$2;
/*  50 */     this.y = $$3;
/*  51 */     this.z = $$4;
/*  52 */     this.xRot = (byte)Mth.floor($$5 * 256.0F / 360.0F);
/*  53 */     this.yRot = (byte)Mth.floor($$6 * 256.0F / 360.0F);
/*  54 */     this.yHeadRot = (byte)Mth.floor($$10 * 256.0D / 360.0D);
/*  55 */     this.type = $$7;
/*  56 */     this.data = $$8;
/*     */     
/*  58 */     this.xa = (int)(Mth.clamp($$9.x, -3.9D, 3.9D) * 8000.0D);
/*  59 */     this.ya = (int)(Mth.clamp($$9.y, -3.9D, 3.9D) * 8000.0D);
/*  60 */     this.za = (int)(Mth.clamp($$9.z, -3.9D, 3.9D) * 8000.0D);
/*     */   }
/*     */   
/*     */   public ClientboundAddEntityPacket(FriendlyByteBuf $$0) {
/*  64 */     this.id = $$0.readVarInt();
/*  65 */     this.uuid = $$0.readUUID();
/*  66 */     this.type = (EntityType)$$0.readById((IdMap)BuiltInRegistries.ENTITY_TYPE);
/*  67 */     this.x = $$0.readDouble();
/*  68 */     this.y = $$0.readDouble();
/*  69 */     this.z = $$0.readDouble();
/*  70 */     this.xRot = $$0.readByte();
/*  71 */     this.yRot = $$0.readByte();
/*  72 */     this.yHeadRot = $$0.readByte();
/*  73 */     this.data = $$0.readVarInt();
/*     */     
/*  75 */     this.xa = $$0.readShort();
/*  76 */     this.ya = $$0.readShort();
/*  77 */     this.za = $$0.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  82 */     $$0.writeVarInt(this.id);
/*  83 */     $$0.writeUUID(this.uuid);
/*  84 */     $$0.writeId((IdMap)BuiltInRegistries.ENTITY_TYPE, this.type);
/*  85 */     $$0.writeDouble(this.x);
/*  86 */     $$0.writeDouble(this.y);
/*  87 */     $$0.writeDouble(this.z);
/*  88 */     $$0.writeByte(this.xRot);
/*  89 */     $$0.writeByte(this.yRot);
/*  90 */     $$0.writeByte(this.yHeadRot);
/*  91 */     $$0.writeVarInt(this.data);
/*  92 */     $$0.writeShort(this.xa);
/*  93 */     $$0.writeShort(this.ya);
/*  94 */     $$0.writeShort(this.za);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/*  99 */     $$0.handleAddEntity(this);
/*     */   }
/*     */   
/*     */   public int getId() {
/* 103 */     return this.id;
/*     */   }
/*     */   
/*     */   public UUID getUUID() {
/* 107 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public EntityType<?> getType() {
/* 111 */     return this.type;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 115 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 119 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 123 */     return this.z;
/*     */   }
/*     */   
/*     */   public double getXa() {
/* 127 */     return this.xa / 8000.0D;
/*     */   }
/*     */   
/*     */   public double getYa() {
/* 131 */     return this.ya / 8000.0D;
/*     */   }
/*     */   
/*     */   public double getZa() {
/* 135 */     return this.za / 8000.0D;
/*     */   }
/*     */   
/*     */   public float getXRot() {
/* 139 */     return (this.xRot * 360) / 256.0F;
/*     */   }
/*     */   
/*     */   public float getYRot() {
/* 143 */     return (this.yRot * 360) / 256.0F;
/*     */   }
/*     */   
/*     */   public float getYHeadRot() {
/* 147 */     return (this.yHeadRot * 360) / 256.0F;
/*     */   }
/*     */   
/*     */   public int getData() {
/* 151 */     return this.data;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundAddEntityPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */