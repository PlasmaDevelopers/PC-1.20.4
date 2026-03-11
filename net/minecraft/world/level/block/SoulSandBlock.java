/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class SoulSandBlock extends Block {
/* 18 */   public static final MapCodec<SoulSandBlock> CODEC = simpleCodec(SoulSandBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SoulSandBlock> codec() {
/* 22 */     return CODEC;
/*    */   }
/*    */   
/* 25 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
/*    */   private static final int BUBBLE_COLUMN_CHECK_DELAY = 20;
/*    */   
/*    */   public SoulSandBlock(BlockBehaviour.Properties $$0) {
/* 29 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 34 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 39 */     return Shapes.block();
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 44 */     return Shapes.block();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 49 */     BubbleColumnBlock.updateColumn((LevelAccessor)$$1, $$2.above(), $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 54 */     if ($$1 == Direction.UP && $$2.is(Blocks.WATER)) {
/* 55 */       $$3.scheduleTick($$4, this, 20);
/*    */     }
/*    */     
/* 58 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 63 */     $$1.scheduleTick($$2, this, 20);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 73 */     return 0.2F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SoulSandBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */