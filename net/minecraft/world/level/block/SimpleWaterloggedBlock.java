/*    */ package net.minecraft.world.level.block;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public interface SimpleWaterloggedBlock extends BucketPickup, LiquidBlockContainer {
/*    */   default boolean canPlaceLiquid(@Nullable Player $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, Fluid $$4) {
/* 22 */     return ($$4 == Fluids.WATER);
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 27 */     if (!((Boolean)$$2.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue() && $$3.getType() == Fluids.WATER) {
/* 28 */       if (!$$0.isClientSide()) {
/* 29 */         $$0.setBlock($$1, (BlockState)$$2.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)), 3);
/* 30 */         $$0.scheduleTick($$1, $$3.getType(), $$3.getType().getTickDelay((LevelReader)$$0));
/*    */       } 
/* 32 */       return true;
/*    */     } 
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   default ItemStack pickupBlock(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2, BlockState $$3) {
/* 39 */     if (((Boolean)$$3.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue()) {
/* 40 */       $$1.setBlock($$2, (BlockState)$$3.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)), 3);
/* 41 */       if (!$$3.canSurvive((LevelReader)$$1, $$2)) {
/* 42 */         $$1.destroyBlock($$2, true);
/*    */       }
/* 44 */       return new ItemStack((ItemLike)Items.WATER_BUCKET);
/*    */     } 
/* 46 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   default Optional<SoundEvent> getPickupSound() {
/* 51 */     return Fluids.WATER.getPickupSound();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SimpleWaterloggedBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */