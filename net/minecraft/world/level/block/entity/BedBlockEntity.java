/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.level.block.BedBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BedBlockEntity
/*    */   extends BlockEntity {
/*    */   public BedBlockEntity(BlockPos $$0, BlockState $$1) {
/* 13 */     super(BlockEntityType.BED, $$0, $$1);
/* 14 */     this.color = ((BedBlock)$$1.getBlock()).getColor();
/*    */   }
/*    */   private DyeColor color;
/*    */   public BedBlockEntity(BlockPos $$0, BlockState $$1, DyeColor $$2) {
/* 18 */     super(BlockEntityType.BED, $$0, $$1);
/* 19 */     this.color = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 24 */     return ClientboundBlockEntityDataPacket.create(this);
/*    */   }
/*    */   
/*    */   public DyeColor getColor() {
/* 28 */     return this.color;
/*    */   }
/*    */   
/*    */   public void setColor(DyeColor $$0) {
/* 32 */     this.color = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BedBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */