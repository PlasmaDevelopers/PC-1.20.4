/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class GameMasterBlockItem
/*    */   extends BlockItem {
/*    */   public GameMasterBlockItem(Block $$0, Item.Properties $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected BlockState getPlacementState(BlockPlaceContext $$0) {
/* 18 */     Player $$1 = $$0.getPlayer();
/* 19 */     return ($$1 == null || $$1.canUseGameMasterBlocks()) ? super.getPlacementState($$0) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\GameMasterBlockItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */