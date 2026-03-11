/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.particles.BlockParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.util.ParticleUtils;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.item.FallingBlockEntity;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class FallingBlock
/*    */   extends Block
/*    */   implements Fallable {
/*    */   public FallingBlock(BlockBehaviour.Properties $$0) {
/* 24 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends FallingBlock> codec();
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 32 */     $$1.scheduleTick($$2, this, getDelayAfterPlace());
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 37 */     $$3.scheduleTick($$4, this, getDelayAfterPlace());
/*    */     
/* 39 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 44 */     if (!isFree($$1.getBlockState($$2.below())) || $$2.getY() < $$1.getMinBuildHeight()) {
/*    */       return;
/*    */     }
/*    */     
/* 48 */     FallingBlockEntity $$4 = FallingBlockEntity.fall((Level)$$1, $$2, $$0);
/* 49 */     falling($$4);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void falling(FallingBlockEntity $$0) {}
/*    */   
/*    */   protected int getDelayAfterPlace() {
/* 56 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isFree(BlockState $$0) {
/* 61 */     return ($$0.isAir() || $$0.is(BlockTags.FIRE) || $$0.liquid() || $$0.canBeReplaced());
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 66 */     if ($$3.nextInt(16) == 0) {
/* 67 */       BlockPos $$4 = $$2.below();
/*    */       
/* 69 */       if (isFree($$1.getBlockState($$4))) {
/* 70 */         ParticleUtils.spawnParticleBelow($$1, $$2, $$3, (ParticleOptions)new BlockParticleOption(ParticleTypes.FALLING_DUST, $$0));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getDustColor(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 76 */     return -16777216;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FallingBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */