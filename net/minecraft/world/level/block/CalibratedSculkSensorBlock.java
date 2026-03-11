/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*    */ 
/*    */ public class CalibratedSculkSensorBlock extends SculkSensorBlock {
/* 22 */   public static final MapCodec<CalibratedSculkSensorBlock> CODEC = simpleCodec(CalibratedSculkSensorBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<CalibratedSculkSensorBlock> codec() {
/* 26 */     return CODEC;
/*    */   }
/*    */   
/* 29 */   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
/*    */   
/*    */   public CalibratedSculkSensorBlock(BlockBehaviour.Properties $$0) {
/* 32 */     super($$0);
/* 33 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)Direction.NORTH));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 39 */     return (BlockEntity)new CalibratedSculkSensorBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 45 */     if (!$$0.isClientSide) {
/* 46 */       return createTickerHelper($$2, BlockEntityType.CALIBRATED_SCULK_SENSOR, ($$0, $$1, $$2, $$3) -> VibrationSystem.Ticker.tick($$0, $$3.getVibrationData(), $$3.getVibrationUser()));
/*    */     }
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 55 */     return (BlockState)super.getStateForPlacement($$0).setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 60 */     if ($$3 != $$0.getValue((Property)FACING)) {
/* 61 */       return super.getSignal($$0, $$1, $$2, $$3);
/*    */     }
/* 63 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 68 */     super.createBlockStateDefinition($$0);
/* 69 */     $$0.add(new Property[] { (Property)FACING });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 75 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 80 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getActiveTicks() {
/* 85 */     return 10;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CalibratedSculkSensorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */