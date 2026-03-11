/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class TwistingVinesBlock extends GrowingPlantHeadBlock {
/* 10 */   public static final MapCodec<TwistingVinesBlock> CODEC = simpleCodec(TwistingVinesBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<TwistingVinesBlock> codec() {
/* 14 */     return CODEC;
/*    */   }
/*    */   
/* 17 */   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
/*    */   
/*    */   public TwistingVinesBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0, Direction.UP, SHAPE, false, 0.1D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlocksToGrowWhenBonemealed(RandomSource $$0) {
/* 25 */     return NetherVines.getBlocksToGrowWhenBonemealed($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Block getBodyBlock() {
/* 30 */     return Blocks.TWISTING_VINES_PLANT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canGrowInto(BlockState $$0) {
/* 35 */     return NetherVines.isValidGrowthState($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TwistingVinesBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */