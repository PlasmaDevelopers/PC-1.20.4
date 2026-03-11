/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class CryingObsidianBlock extends Block {
/* 12 */   public static final MapCodec<CryingObsidianBlock> CODEC = simpleCodec(CryingObsidianBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<CryingObsidianBlock> codec() {
/* 16 */     return CODEC;
/*    */   }
/*    */   
/*    */   public CryingObsidianBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 25 */     if ($$3.nextInt(5) != 0) {
/*    */       return;
/*    */     }
/*    */     
/* 29 */     Direction $$4 = Direction.getRandom($$3);
/* 30 */     if ($$4 == Direction.UP) {
/*    */       return;
/*    */     }
/* 33 */     BlockPos $$5 = $$2.relative($$4);
/* 34 */     BlockState $$6 = $$1.getBlockState($$5);
/* 35 */     if ($$0.canOcclude() && $$6.isFaceSturdy((BlockGetter)$$1, $$5, $$4.getOpposite())) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     double $$7 = ($$4.getStepX() == 0) ? $$3.nextDouble() : (0.5D + $$4.getStepX() * 0.6D);
/* 40 */     double $$8 = ($$4.getStepY() == 0) ? $$3.nextDouble() : (0.5D + $$4.getStepY() * 0.6D);
/* 41 */     double $$9 = ($$4.getStepZ() == 0) ? $$3.nextDouble() : (0.5D + $$4.getStepZ() * 0.6D);
/*    */     
/* 43 */     $$1.addParticle((ParticleOptions)ParticleTypes.DRIPPING_OBSIDIAN_TEAR, $$2.getX() + $$7, $$2.getY() + $$8, $$2.getZ() + $$9, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CryingObsidianBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */