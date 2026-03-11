/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.SignBlock;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SignItem extends StandingAndWallBlockItem {
/*    */   public SignItem(Item.Properties $$0, Block $$1, Block $$2) {
/* 16 */     super($$1, $$2, $$0, Direction.DOWN);
/*    */   }
/*    */   
/*    */   public SignItem(Item.Properties $$0, Block $$1, Block $$2, Direction $$3) {
/* 20 */     super($$1, $$2, $$0, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean updateCustomBlockEntityTag(BlockPos $$0, Level $$1, @Nullable Player $$2, ItemStack $$3, BlockState $$4) {
/* 25 */     boolean $$5 = super.updateCustomBlockEntityTag($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 27 */     if (!$$1.isClientSide && !$$5 && $$2 != null) { BlockEntity blockEntity = $$1.getBlockEntity($$0); if (blockEntity instanceof SignBlockEntity) { SignBlockEntity $$6 = (SignBlockEntity)blockEntity;
/* 28 */         Block block = $$1.getBlockState($$0).getBlock(); if (block instanceof SignBlock) { SignBlock $$7 = (SignBlock)block;
/* 29 */           $$7.openTextEdit($$2, $$6, true); }
/*    */          }
/*    */        }
/* 32 */      return $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SignItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */