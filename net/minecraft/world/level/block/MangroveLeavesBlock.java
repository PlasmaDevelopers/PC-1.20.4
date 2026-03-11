/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class MangroveLeavesBlock extends LeavesBlock implements BonemealableBlock {
/* 12 */   public static final MapCodec<MangroveLeavesBlock> CODEC = simpleCodec(MangroveLeavesBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<MangroveLeavesBlock> codec() {
/* 16 */     return CODEC;
/*    */   }
/*    */   
/*    */   public MangroveLeavesBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 25 */     return $$0.getBlockState($$1.below()).isAir();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 35 */     $$0.setBlock($$2.below(), MangrovePropaguleBlock.createNewHangingPropagule(), 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MangroveLeavesBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */