/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class CarpetBlock extends Block {
/* 14 */   public static final MapCodec<CarpetBlock> CODEC = simpleCodec(CarpetBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<? extends CarpetBlock> codec() {
/* 18 */     return CODEC;
/*    */   }
/*    */   
/* 21 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
/*    */   
/*    */   public CarpetBlock(BlockBehaviour.Properties $$0) {
/* 24 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 29 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 34 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 35 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/*    */     
/* 38 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 43 */     return !$$1.isEmptyBlock($$2.below());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CarpetBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */