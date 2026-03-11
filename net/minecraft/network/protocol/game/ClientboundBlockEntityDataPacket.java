/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ 
/*    */ public class ClientboundBlockEntityDataPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final BlockPos pos;
/*    */   
/*    */   public static ClientboundBlockEntityDataPacket create(BlockEntity $$0, Function<BlockEntity, CompoundTag> $$1) {
/* 21 */     return new ClientboundBlockEntityDataPacket($$0.getBlockPos(), $$0.getType(), $$1.apply($$0));
/*    */   } private final BlockEntityType<?> type; @Nullable
/*    */   private final CompoundTag tag;
/*    */   public static ClientboundBlockEntityDataPacket create(BlockEntity $$0) {
/* 25 */     return create($$0, BlockEntity::getUpdateTag);
/*    */   }
/*    */   
/*    */   private ClientboundBlockEntityDataPacket(BlockPos $$0, BlockEntityType<?> $$1, CompoundTag $$2) {
/* 29 */     this.pos = $$0;
/* 30 */     this.type = $$1;
/* 31 */     this.tag = $$2.isEmpty() ? null : $$2;
/*    */   }
/*    */   
/*    */   public ClientboundBlockEntityDataPacket(FriendlyByteBuf $$0) {
/* 35 */     this.pos = $$0.readBlockPos();
/* 36 */     this.type = (BlockEntityType)$$0.readById((IdMap)BuiltInRegistries.BLOCK_ENTITY_TYPE);
/* 37 */     this.tag = $$0.readNbt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 42 */     $$0.writeBlockPos(this.pos);
/* 43 */     $$0.writeId((IdMap)BuiltInRegistries.BLOCK_ENTITY_TYPE, this.type);
/* 44 */     $$0.writeNbt((Tag)this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 49 */     $$0.handleBlockEntityData(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 53 */     return this.pos;
/*    */   }
/*    */   
/*    */   public BlockEntityType<?> getType() {
/* 57 */     return this.type;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public CompoundTag getTag() {
/* 62 */     return this.tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBlockEntityDataPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */