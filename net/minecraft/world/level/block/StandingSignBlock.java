/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.block.state.properties.RotationSegment;
/*    */ import net.minecraft.world.level.block.state.properties.WoodType;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class StandingSignBlock extends SignBlock {
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WoodType.CODEC.fieldOf("wood_type").forGetter(SignBlock::type), (App)propertiesCodec()).apply((Applicative)$$0, StandingSignBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<StandingSignBlock> CODEC;
/*    */   
/*    */   public MapCodec<StandingSignBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/* 30 */   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
/*    */   
/*    */   public StandingSignBlock(WoodType $$0, BlockBehaviour.Properties $$1) {
/* 33 */     super($$0, $$1.sound($$0.soundType()));
/* 34 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)ROTATION, Integer.valueOf(0))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 39 */     return $$1.getBlockState($$2.below()).isSolid();
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 44 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 45 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)ROTATION, Integer.valueOf(RotationSegment.convertToSegment($$0.getRotation() + 180.0F)))).setValue((Property)WATERLOGGED, Boolean.valueOf(($$1.getType() == Fluids.WATER)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 50 */     if ($$1 == Direction.DOWN && !canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 51 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 53 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getYRotationDegrees(BlockState $$0) {
/* 58 */     return RotationSegment.convertToDegrees(((Integer)$$0.getValue((Property)ROTATION)).intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 63 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.rotate(((Integer)$$0.getValue((Property)ROTATION)).intValue(), 16)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 68 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.mirror(((Integer)$$0.getValue((Property)ROTATION)).intValue(), 16)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 73 */     $$0.add(new Property[] { (Property)ROTATION, (Property)WATERLOGGED });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\StandingSignBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */