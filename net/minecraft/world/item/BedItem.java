/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BedItem extends BlockItem {
/*    */   public BedItem(Block $$0, Item.Properties $$1) {
/*  9 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean placeBlock(BlockPlaceContext $$0, BlockState $$1) {
/* 14 */     return $$0.getLevel().setBlock($$0.getClickedPos(), $$1, 26);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BedItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */