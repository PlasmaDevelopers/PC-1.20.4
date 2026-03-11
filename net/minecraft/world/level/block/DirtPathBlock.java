/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class DirtPathBlock extends Block {
/* 18 */   public static final MapCodec<DirtPathBlock> CODEC = simpleCodec(DirtPathBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<DirtPathBlock> codec() {
/* 22 */     return CODEC;
/*    */   }
/*    */   
/* 25 */   protected static final VoxelShape SHAPE = FarmBlock.SHAPE;
/*    */   
/*    */   protected DirtPathBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 38 */     if (!defaultBlockState().canSurvive((LevelReader)$$0.getLevel(), $$0.getClickedPos())) {
/* 39 */       return Block.pushEntitiesUp(defaultBlockState(), Blocks.DIRT.defaultBlockState(), (LevelAccessor)$$0.getLevel(), $$0.getClickedPos());
/*    */     }
/* 41 */     return super.getStateForPlacement($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 46 */     if ($$1 == Direction.UP && 
/* 47 */       !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 48 */       $$3.scheduleTick($$4, this, 1);
/*    */     }
/*    */     
/* 51 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 56 */     FarmBlock.turnToDirt(null, $$0, (Level)$$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 61 */     BlockState $$3 = $$1.getBlockState($$2.above());
/* 62 */     return (!$$3.isSolid() || $$3.getBlock() instanceof FenceGateBlock);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 67 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DirtPathBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */