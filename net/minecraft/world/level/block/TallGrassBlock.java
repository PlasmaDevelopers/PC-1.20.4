/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class TallGrassBlock extends BushBlock implements BonemealableBlock {
/* 15 */   public static final MapCodec<TallGrassBlock> CODEC = simpleCodec(TallGrassBlock::new);
/*    */   protected static final float AABB_OFFSET = 6.0F;
/*    */   
/*    */   public MapCodec<TallGrassBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 23 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
/*    */   
/*    */   protected TallGrassBlock(BlockBehaviour.Properties $$0) {
/* 26 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 31 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 46 */     DoublePlantBlock $$4 = $$3.is(Blocks.FERN) ? (DoublePlantBlock)Blocks.LARGE_FERN : (DoublePlantBlock)Blocks.TALL_GRASS;
/*    */     
/* 48 */     if ($$4.defaultBlockState().canSurvive((LevelReader)$$0, $$2) && $$0.isEmptyBlock($$2.above()))
/* 49 */       DoublePlantBlock.placeAt((LevelAccessor)$$0, $$4.defaultBlockState(), $$2, 2); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TallGrassBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */