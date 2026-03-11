/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class WeepingVinesBlock extends GrowingPlantHeadBlock {
/* 10 */   public static final MapCodec<WeepingVinesBlock> CODEC = simpleCodec(WeepingVinesBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<WeepingVinesBlock> codec() {
/* 14 */     return CODEC;
/*    */   }
/*    */   
/* 17 */   protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*    */   
/*    */   public WeepingVinesBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0, Direction.DOWN, SHAPE, false, 0.1D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlocksToGrowWhenBonemealed(RandomSource $$0) {
/* 25 */     return NetherVines.getBlocksToGrowWhenBonemealed($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Block getBodyBlock() {
/* 30 */     return Blocks.WEEPING_VINES_PLANT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canGrowInto(BlockState $$0) {
/* 35 */     return NetherVines.isValidGrowthState($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeepingVinesBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */