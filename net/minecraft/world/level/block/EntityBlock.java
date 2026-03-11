/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.gameevent.GameEventListener;
/*    */ 
/*    */ public interface EntityBlock
/*    */ {
/*    */   @Nullable
/*    */   BlockEntity newBlockEntity(BlockPos paramBlockPos, BlockState paramBlockState);
/*    */   
/*    */   @Nullable
/*    */   default <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 20 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default <T extends BlockEntity> GameEventListener getListener(ServerLevel $$0, T $$1) {
/* 25 */     if ($$1 instanceof GameEventListener.Holder) { GameEventListener.Holder<?> $$2 = (GameEventListener.Holder)$$1;
/* 26 */       return $$2.getListener(); }
/*    */ 
/*    */     
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EntityBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */