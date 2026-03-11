/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ 
/*     */ public class ServerboundSetStructureBlockPacket implements Packet<ServerGamePacketListener> {
/*     */   private static final int FLAG_IGNORE_ENTITIES = 1;
/*     */   private static final int FLAG_SHOW_AIR = 2;
/*     */   private static final int FLAG_SHOW_BOUNDING_BOX = 4;
/*     */   private final BlockPos pos;
/*     */   private final StructureBlockEntity.UpdateType updateType;
/*     */   private final StructureMode mode;
/*     */   private final String name;
/*     */   private final BlockPos offset;
/*     */   private final Vec3i size;
/*     */   private final Mirror mirror;
/*     */   private final Rotation rotation;
/*     */   private final String data;
/*     */   private final boolean ignoreEntities;
/*     */   private final boolean showAir;
/*     */   private final boolean showBoundingBox;
/*     */   private final float integrity;
/*     */   private final long seed;
/*     */   
/*     */   public ServerboundSetStructureBlockPacket(BlockPos $$0, StructureBlockEntity.UpdateType $$1, StructureMode $$2, String $$3, BlockPos $$4, Vec3i $$5, Mirror $$6, Rotation $$7, String $$8, boolean $$9, boolean $$10, boolean $$11, float $$12, long $$13) {
/*  34 */     this.pos = $$0;
/*  35 */     this.updateType = $$1;
/*  36 */     this.mode = $$2;
/*  37 */     this.name = $$3;
/*  38 */     this.offset = $$4;
/*  39 */     this.size = $$5;
/*  40 */     this.mirror = $$6;
/*  41 */     this.rotation = $$7;
/*  42 */     this.data = $$8;
/*  43 */     this.ignoreEntities = $$9;
/*  44 */     this.showAir = $$10;
/*  45 */     this.showBoundingBox = $$11;
/*  46 */     this.integrity = $$12;
/*  47 */     this.seed = $$13;
/*     */   }
/*     */   
/*     */   public ServerboundSetStructureBlockPacket(FriendlyByteBuf $$0) {
/*  51 */     this.pos = $$0.readBlockPos();
/*  52 */     this.updateType = (StructureBlockEntity.UpdateType)$$0.readEnum(StructureBlockEntity.UpdateType.class);
/*  53 */     this.mode = (StructureMode)$$0.readEnum(StructureMode.class);
/*  54 */     this.name = $$0.readUtf();
/*  55 */     int $$1 = 48;
/*  56 */     this.offset = new BlockPos(Mth.clamp($$0.readByte(), -48, 48), Mth.clamp($$0.readByte(), -48, 48), Mth.clamp($$0.readByte(), -48, 48));
/*  57 */     int $$2 = 48;
/*  58 */     this.size = new Vec3i(Mth.clamp($$0.readByte(), 0, 48), Mth.clamp($$0.readByte(), 0, 48), Mth.clamp($$0.readByte(), 0, 48));
/*  59 */     this.mirror = (Mirror)$$0.readEnum(Mirror.class);
/*  60 */     this.rotation = (Rotation)$$0.readEnum(Rotation.class);
/*  61 */     this.data = $$0.readUtf(128);
/*  62 */     this.integrity = Mth.clamp($$0.readFloat(), 0.0F, 1.0F);
/*  63 */     this.seed = $$0.readVarLong();
/*  64 */     int $$3 = $$0.readByte();
/*  65 */     this.ignoreEntities = (($$3 & 0x1) != 0);
/*  66 */     this.showAir = (($$3 & 0x2) != 0);
/*  67 */     this.showBoundingBox = (($$3 & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  72 */     $$0.writeBlockPos(this.pos);
/*  73 */     $$0.writeEnum((Enum)this.updateType);
/*  74 */     $$0.writeEnum((Enum)this.mode);
/*  75 */     $$0.writeUtf(this.name);
/*  76 */     $$0.writeByte(this.offset.getX());
/*  77 */     $$0.writeByte(this.offset.getY());
/*  78 */     $$0.writeByte(this.offset.getZ());
/*  79 */     $$0.writeByte(this.size.getX());
/*  80 */     $$0.writeByte(this.size.getY());
/*  81 */     $$0.writeByte(this.size.getZ());
/*  82 */     $$0.writeEnum((Enum)this.mirror);
/*  83 */     $$0.writeEnum((Enum)this.rotation);
/*  84 */     $$0.writeUtf(this.data);
/*  85 */     $$0.writeFloat(this.integrity);
/*  86 */     $$0.writeVarLong(this.seed);
/*     */     
/*  88 */     int $$1 = 0;
/*  89 */     if (this.ignoreEntities) {
/*  90 */       $$1 |= 0x1;
/*     */     }
/*  92 */     if (this.showAir) {
/*  93 */       $$1 |= 0x2;
/*     */     }
/*  95 */     if (this.showBoundingBox) {
/*  96 */       $$1 |= 0x4;
/*     */     }
/*  98 */     $$0.writeByte($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ServerGamePacketListener $$0) {
/* 103 */     $$0.handleSetStructureBlock(this);
/*     */   }
/*     */   
/*     */   public BlockPos getPos() {
/* 107 */     return this.pos;
/*     */   }
/*     */   
/*     */   public StructureBlockEntity.UpdateType getUpdateType() {
/* 111 */     return this.updateType;
/*     */   }
/*     */   
/*     */   public StructureMode getMode() {
/* 115 */     return this.mode;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 119 */     return this.name;
/*     */   }
/*     */   
/*     */   public BlockPos getOffset() {
/* 123 */     return this.offset;
/*     */   }
/*     */   
/*     */   public Vec3i getSize() {
/* 127 */     return this.size;
/*     */   }
/*     */   
/*     */   public Mirror getMirror() {
/* 131 */     return this.mirror;
/*     */   }
/*     */   
/*     */   public Rotation getRotation() {
/* 135 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public String getData() {
/* 139 */     return this.data;
/*     */   }
/*     */   
/*     */   public boolean isIgnoreEntities() {
/* 143 */     return this.ignoreEntities;
/*     */   }
/*     */   
/*     */   public boolean isShowAir() {
/* 147 */     return this.showAir;
/*     */   }
/*     */   
/*     */   public boolean isShowBoundingBox() {
/* 151 */     return this.showBoundingBox;
/*     */   }
/*     */   
/*     */   public float getIntegrity() {
/* 155 */     return this.integrity;
/*     */   }
/*     */   
/*     */   public long getSeed() {
/* 159 */     return this.seed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSetStructureBlockPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */