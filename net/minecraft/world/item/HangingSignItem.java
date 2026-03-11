/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.WallHangingSignBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class HangingSignItem extends SignItem {
/*    */   public HangingSignItem(Block $$0, Block $$1, Item.Properties $$2) {
/* 12 */     super($$2, $$0, $$1, Direction.UP);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canPlace(LevelReader $$0, BlockState $$1, BlockPos $$2) {
/* 17 */     Block block = $$1.getBlock(); if (block instanceof WallHangingSignBlock) { WallHangingSignBlock $$3 = (WallHangingSignBlock)block;
/* 18 */       if (!$$3.canPlace($$1, $$0, $$2)) {
/* 19 */         return false;
/*    */       } }
/*    */     
/* 22 */     return super.canPlace($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\HangingSignItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */