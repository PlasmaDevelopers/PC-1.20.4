/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BlockEntityInfo
/*     */ {
/*     */   final int packedXZ;
/*     */   final int y;
/*     */   final BlockEntityType<?> type;
/*     */   @Nullable
/*     */   final CompoundTag tag;
/*     */   
/*     */   private BlockEntityInfo(int $$0, int $$1, BlockEntityType<?> $$2, @Nullable CompoundTag $$3) {
/* 131 */     this.packedXZ = $$0;
/* 132 */     this.y = $$1;
/* 133 */     this.type = $$2;
/* 134 */     this.tag = $$3;
/*     */   }
/*     */   
/*     */   private BlockEntityInfo(FriendlyByteBuf $$0) {
/* 138 */     this.packedXZ = $$0.readByte();
/* 139 */     this.y = $$0.readShort();
/* 140 */     this.type = (BlockEntityType)$$0.readById((IdMap)BuiltInRegistries.BLOCK_ENTITY_TYPE);
/* 141 */     this.tag = $$0.readNbt();
/*     */   }
/*     */   
/*     */   void write(FriendlyByteBuf $$0) {
/* 145 */     $$0.writeByte(this.packedXZ);
/* 146 */     $$0.writeShort(this.y);
/* 147 */     $$0.writeId((IdMap)BuiltInRegistries.BLOCK_ENTITY_TYPE, this.type);
/* 148 */     $$0.writeNbt((Tag)this.tag);
/*     */   }
/*     */   
/*     */   static BlockEntityInfo create(BlockEntity $$0) {
/* 152 */     CompoundTag $$1 = $$0.getUpdateTag();
/* 153 */     BlockPos $$2 = $$0.getBlockPos();
/* 154 */     int $$3 = SectionPos.sectionRelative($$2.getX()) << 4 | SectionPos.sectionRelative($$2.getZ());
/* 155 */     return new BlockEntityInfo($$3, $$2.getY(), $$0.getType(), $$1.isEmpty() ? null : $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLevelChunkPacketData$BlockEntityInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */