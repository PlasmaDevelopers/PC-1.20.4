/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SoulFireBlock extends BaseFireBlock {
/* 12 */   public static final MapCodec<SoulFireBlock> CODEC = simpleCodec(SoulFireBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SoulFireBlock> codec() {
/* 16 */     return CODEC;
/*    */   }
/*    */   
/*    */   public SoulFireBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0, 2.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 25 */     if (canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 26 */       return defaultBlockState();
/*    */     }
/*    */     
/* 29 */     return Blocks.AIR.defaultBlockState();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 34 */     return canSurviveOnBlock($$1.getBlockState($$2.below()));
/*    */   }
/*    */   
/*    */   public static boolean canSurviveOnBlock(BlockState $$0) {
/* 38 */     return $$0.is(BlockTags.SOUL_FIRE_BASE_BLOCKS);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canBurn(BlockState $$0) {
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SoulFireBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */