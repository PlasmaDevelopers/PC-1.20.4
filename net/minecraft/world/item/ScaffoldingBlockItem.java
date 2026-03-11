/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.ScaffoldingBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class ScaffoldingBlockItem extends BlockItem {
/*    */   public ScaffoldingBlockItem(Block $$0, Item.Properties $$1) {
/* 19 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockPlaceContext updatePlacementContext(BlockPlaceContext $$0) {
/* 25 */     BlockPos $$1 = $$0.getClickedPos();
/* 26 */     Level $$2 = $$0.getLevel();
/*    */     
/* 28 */     BlockState $$3 = $$2.getBlockState($$1);
/* 29 */     Block $$4 = getBlock();
/* 30 */     if ($$3.is($$4)) {
/*    */       Direction $$6;
/* 32 */       if ($$0.isSecondaryUseActive()) {
/* 33 */         Direction $$5 = $$0.isInside() ? $$0.getClickedFace().getOpposite() : $$0.getClickedFace();
/*    */       } else {
/* 35 */         $$6 = ($$0.getClickedFace() == Direction.UP) ? $$0.getHorizontalDirection() : Direction.UP;
/*    */       } 
/*    */       
/* 38 */       int $$7 = 0;
/* 39 */       BlockPos.MutableBlockPos $$8 = $$1.mutable().move($$6);
/* 40 */       while ($$7 < 7) {
/* 41 */         if (!$$2.isClientSide && !$$2.isInWorldBounds((BlockPos)$$8)) {
/*    */           
/* 43 */           Player $$9 = $$0.getPlayer();
/* 44 */           int $$10 = $$2.getMaxBuildHeight();
/* 45 */           if ($$9 instanceof ServerPlayer && $$8.getY() >= $$10) {
/* 46 */             ((ServerPlayer)$$9).sendSystemMessage((Component)Component.translatable("build.tooHigh", new Object[] { Integer.valueOf($$10 - 1) }).withStyle(ChatFormatting.RED), true);
/*    */           }
/*    */           
/*    */           break;
/*    */         } 
/* 51 */         $$3 = $$2.getBlockState((BlockPos)$$8);
/*    */         
/* 53 */         if (!$$3.is(getBlock())) {
/* 54 */           if ($$3.canBeReplaced($$0)) {
/* 55 */             return BlockPlaceContext.at($$0, (BlockPos)$$8, $$6);
/*    */           }
/*    */           
/*    */           break;
/*    */         } 
/* 60 */         $$8.move($$6);
/* 61 */         if ($$6.getAxis().isHorizontal()) {
/* 62 */           $$7++;
/*    */         }
/*    */       } 
/*    */       
/* 66 */       return null;
/*    */     } 
/*    */     
/* 69 */     if (ScaffoldingBlock.getDistance((BlockGetter)$$2, $$1) == 7) {
/* 70 */       return null;
/*    */     }
/*    */     
/* 73 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mustSurvive() {
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ScaffoldingBlockItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */