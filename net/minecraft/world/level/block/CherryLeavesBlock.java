/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class CherryLeavesBlock extends LeavesBlock {
/* 13 */   public static final MapCodec<CherryLeavesBlock> CODEC = simpleCodec(CherryLeavesBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<CherryLeavesBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/*    */   public CherryLeavesBlock(BlockBehaviour.Properties $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 26 */     super.animateTick($$0, $$1, $$2, $$3);
/*    */     
/* 28 */     if ($$3.nextInt(10) != 0) {
/*    */       return;
/*    */     }
/*    */     
/* 32 */     BlockPos $$4 = $$2.below();
/* 33 */     BlockState $$5 = $$1.getBlockState($$4);
/* 34 */     if (isFaceFull($$5.getCollisionShape((BlockGetter)$$1, $$4), Direction.UP)) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     ParticleUtils.spawnParticleBelow($$1, $$2, $$3, (ParticleOptions)ParticleTypes.CHERRY_LEAVES);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CherryLeavesBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */