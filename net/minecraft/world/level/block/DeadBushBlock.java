/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class DeadBushBlock extends BushBlock {
/* 12 */   public static final MapCodec<DeadBushBlock> CODEC = simpleCodec(DeadBushBlock::new);
/*    */   protected static final float AABB_OFFSET = 6.0F;
/*    */   
/*    */   public MapCodec<DeadBushBlock> codec() {
/* 16 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 20 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
/*    */   
/*    */   protected DeadBushBlock(BlockBehaviour.Properties $$0) {
/* 23 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 28 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 33 */     return $$0.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DeadBushBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */