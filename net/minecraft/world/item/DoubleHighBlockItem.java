/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class DoubleHighBlockItem extends BlockItem {
/*    */   public DoubleHighBlockItem(Block $$0, Item.Properties $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean placeBlock(BlockPlaceContext $$0, BlockState $$1) {
/* 17 */     Level $$2 = $$0.getLevel();
/* 18 */     BlockPos $$3 = $$0.getClickedPos().above();
/* 19 */     BlockState $$4 = $$2.isWaterAt($$3) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
/* 20 */     $$2.setBlock($$3, $$4, 27);
/* 21 */     return super.placeBlock($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DoubleHighBlockItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */