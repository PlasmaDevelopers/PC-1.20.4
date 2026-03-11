/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.LongArrayTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ 
/*     */ public class ClientboundLevelChunkPacketData
/*     */ {
/*     */   private static final int TWO_MEGABYTES = 2097152;
/*     */   private final CompoundTag heightmaps;
/*     */   
/*     */   public ClientboundLevelChunkPacketData(LevelChunk $$0) {
/*  30 */     this.heightmaps = new CompoundTag();
/*  31 */     for (Map.Entry<Heightmap.Types, Heightmap> $$1 : (Iterable<Map.Entry<Heightmap.Types, Heightmap>>)$$0.getHeightmaps()) {
/*  32 */       if (!((Heightmap.Types)$$1.getKey()).sendToClient()) {
/*     */         continue;
/*     */       }
/*  35 */       this.heightmaps.put(((Heightmap.Types)$$1.getKey()).getSerializationKey(), (Tag)new LongArrayTag(((Heightmap)$$1.getValue()).getRawData()));
/*     */     } 
/*     */ 
/*     */     
/*  39 */     this.buffer = new byte[calculateChunkSize($$0)];
/*  40 */     extractChunkData(new FriendlyByteBuf(getWriteBuffer()), $$0);
/*     */     
/*  42 */     this.blockEntitiesData = Lists.newArrayList();
/*  43 */     for (Map.Entry<BlockPos, BlockEntity> $$2 : (Iterable<Map.Entry<BlockPos, BlockEntity>>)$$0.getBlockEntities().entrySet())
/*  44 */       this.blockEntitiesData.add(BlockEntityInfo.create($$2.getValue())); 
/*     */   }
/*     */   private final byte[] buffer; private final List<BlockEntityInfo> blockEntitiesData;
/*     */   
/*     */   public ClientboundLevelChunkPacketData(FriendlyByteBuf $$0, int $$1, int $$2) {
/*  49 */     this.heightmaps = $$0.readNbt();
/*  50 */     if (this.heightmaps == null) {
/*  51 */       throw new RuntimeException("Can't read heightmap in packet for [" + $$1 + ", " + $$2 + "]");
/*     */     }
/*     */     
/*  54 */     int $$3 = $$0.readVarInt();
/*  55 */     if ($$3 > 2097152) {
/*  56 */       throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
/*     */     }
/*     */     
/*  59 */     this.buffer = new byte[$$3];
/*  60 */     $$0.readBytes(this.buffer);
/*     */     
/*  62 */     this.blockEntitiesData = $$0.readList(BlockEntityInfo::new);
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  66 */     $$0.writeNbt((Tag)this.heightmaps);
/*  67 */     $$0.writeVarInt(this.buffer.length);
/*  68 */     $$0.writeBytes(this.buffer);
/*     */     
/*  70 */     $$0.writeCollection(this.blockEntitiesData, ($$0, $$1) -> $$1.write($$0));
/*     */   }
/*     */   
/*     */   private static int calculateChunkSize(LevelChunk $$0) {
/*  74 */     int $$1 = 0;
/*     */     
/*  76 */     for (LevelChunkSection $$2 : $$0.getSections()) {
/*  77 */       $$1 += $$2.getSerializedSize();
/*     */     }
/*     */     
/*  80 */     return $$1;
/*     */   }
/*     */   
/*     */   private ByteBuf getWriteBuffer() {
/*  84 */     ByteBuf $$0 = Unpooled.wrappedBuffer(this.buffer);
/*  85 */     $$0.writerIndex(0);
/*  86 */     return $$0;
/*     */   }
/*     */   
/*     */   public static void extractChunkData(FriendlyByteBuf $$0, LevelChunk $$1) {
/*  90 */     for (LevelChunkSection $$2 : $$1.getSections()) {
/*  91 */       $$2.write($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public Consumer<BlockEntityTagOutput> getBlockEntitiesTagsConsumer(int $$0, int $$1) {
/*  96 */     return $$2 -> getBlockEntitiesTags($$2, $$0, $$1);
/*     */   }
/*     */   
/*     */   private void getBlockEntitiesTags(BlockEntityTagOutput $$0, int $$1, int $$2) {
/* 100 */     int $$3 = 16 * $$1;
/* 101 */     int $$4 = 16 * $$2;
/* 102 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 103 */     for (BlockEntityInfo $$6 : this.blockEntitiesData) {
/* 104 */       int $$7 = $$3 + SectionPos.sectionRelative($$6.packedXZ >> 4);
/* 105 */       int $$8 = $$4 + SectionPos.sectionRelative($$6.packedXZ);
/* 106 */       $$5.set($$7, $$6.y, $$8);
/* 107 */       $$0.accept((BlockPos)$$5, $$6.type, $$6.tag);
/*     */     } 
/*     */   }
/*     */   public FriendlyByteBuf getReadBuffer() {
/* 111 */     return new FriendlyByteBuf(Unpooled.wrappedBuffer(this.buffer));
/*     */   }
/*     */   
/*     */   public CompoundTag getHeightmaps() {
/* 115 */     return this.heightmaps;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class BlockEntityInfo
/*     */   {
/*     */     final int packedXZ;
/*     */     
/*     */     final int y;
/*     */     
/*     */     final BlockEntityType<?> type;
/*     */     
/*     */     @Nullable
/*     */     final CompoundTag tag;
/*     */     
/*     */     private BlockEntityInfo(int $$0, int $$1, BlockEntityType<?> $$2, @Nullable CompoundTag $$3) {
/* 131 */       this.packedXZ = $$0;
/* 132 */       this.y = $$1;
/* 133 */       this.type = $$2;
/* 134 */       this.tag = $$3;
/*     */     }
/*     */     
/*     */     private BlockEntityInfo(FriendlyByteBuf $$0) {
/* 138 */       this.packedXZ = $$0.readByte();
/* 139 */       this.y = $$0.readShort();
/* 140 */       this.type = (BlockEntityType)$$0.readById((IdMap)BuiltInRegistries.BLOCK_ENTITY_TYPE);
/* 141 */       this.tag = $$0.readNbt();
/*     */     }
/*     */     
/*     */     void write(FriendlyByteBuf $$0) {
/* 145 */       $$0.writeByte(this.packedXZ);
/* 146 */       $$0.writeShort(this.y);
/* 147 */       $$0.writeId((IdMap)BuiltInRegistries.BLOCK_ENTITY_TYPE, this.type);
/* 148 */       $$0.writeNbt((Tag)this.tag);
/*     */     }
/*     */     
/*     */     static BlockEntityInfo create(BlockEntity $$0) {
/* 152 */       CompoundTag $$1 = $$0.getUpdateTag();
/* 153 */       BlockPos $$2 = $$0.getBlockPos();
/* 154 */       int $$3 = SectionPos.sectionRelative($$2.getX()) << 4 | SectionPos.sectionRelative($$2.getZ());
/* 155 */       return new BlockEntityInfo($$3, $$2.getY(), $$0.getType(), $$1.isEmpty() ? null : $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockEntityTagOutput {
/*     */     void accept(BlockPos param1BlockPos, BlockEntityType<?> param1BlockEntityType, @Nullable CompoundTag param1CompoundTag);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLevelChunkPacketData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */