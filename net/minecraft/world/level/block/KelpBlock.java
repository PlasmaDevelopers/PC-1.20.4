/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class KelpBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
/* 21 */   public static final MapCodec<KelpBlock> CODEC = simpleCodec(KelpBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<KelpBlock> codec() {
/* 25 */     return CODEC;
/*    */   }
/*    */   
/* 28 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
/*    */   private static final double GROW_PER_TICK_PROBABILITY = 0.14D;
/*    */   
/*    */   protected KelpBlock(BlockBehaviour.Properties $$0) {
/* 32 */     super($$0, Direction.UP, SHAPE, true, 0.14D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canGrowInto(BlockState $$0) {
/* 37 */     return $$0.is(Blocks.WATER);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Block getBodyBlock() {
/* 42 */     return Blocks.KELP_PLANT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canAttachTo(BlockState $$0) {
/* 47 */     return !$$0.is(Blocks.MAGMA_BLOCK);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaceLiquid(@Nullable Player $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, Fluid $$4) {
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlocksToGrowWhenBonemealed(RandomSource $$0) {
/* 62 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 68 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 69 */     if ($$1.is(FluidTags.WATER) && $$1.getAmount() == 8) {
/* 70 */       return super.getStateForPlacement($$0);
/*    */     }
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 77 */     return Fluids.WATER.getSource(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\KelpBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */