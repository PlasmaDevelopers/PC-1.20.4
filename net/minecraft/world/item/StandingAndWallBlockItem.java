/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ 
/*    */ public class StandingAndWallBlockItem extends BlockItem {
/*    */   protected final Block wallBlock;
/*    */   private final Direction attachmentDirection;
/*    */   
/*    */   public StandingAndWallBlockItem(Block $$0, Block $$1, Item.Properties $$2, Direction $$3) {
/* 19 */     super($$0, $$2);
/* 20 */     this.wallBlock = $$1;
/* 21 */     this.attachmentDirection = $$3;
/*    */   }
/*    */   
/*    */   protected boolean canPlace(LevelReader $$0, BlockState $$1, BlockPos $$2) {
/* 25 */     return $$1.canSurvive($$0, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected BlockState getPlacementState(BlockPlaceContext $$0) {
/* 31 */     BlockState $$1 = this.wallBlock.getStateForPlacement($$0);
/*    */     
/* 33 */     BlockState $$2 = null;
/*    */     
/* 35 */     Level level = $$0.getLevel();
/* 36 */     BlockPos $$4 = $$0.getClickedPos();
/* 37 */     for (Direction $$5 : $$0.getNearestLookingDirections()) {
/* 38 */       if ($$5 != this.attachmentDirection.getOpposite()) {
/*    */ 
/*    */ 
/*    */         
/* 42 */         BlockState $$6 = ($$5 == this.attachmentDirection) ? getBlock().getStateForPlacement($$0) : $$1;
/* 43 */         if ($$6 != null && canPlace((LevelReader)level, $$6, $$4)) {
/* 44 */           $$2 = $$6;
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 49 */     return ($$2 != null && level.isUnobstructed($$2, $$4, CollisionContext.empty())) ? $$2 : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerBlocks(Map<Block, Item> $$0, Item $$1) {
/* 54 */     super.registerBlocks($$0, $$1);
/*    */     
/* 56 */     $$0.put(this.wallBlock, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\StandingAndWallBlockItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */