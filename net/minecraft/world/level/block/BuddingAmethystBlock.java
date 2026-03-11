/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public class BuddingAmethystBlock extends AmethystBlock {
/* 13 */   public static final MapCodec<BuddingAmethystBlock> CODEC = simpleCodec(BuddingAmethystBlock::new);
/*    */   public static final int GROWTH_CHANCE = 5;
/*    */   
/*    */   public MapCodec<BuddingAmethystBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 22 */   private static final Direction[] DIRECTIONS = Direction.values();
/*    */   
/*    */   public BuddingAmethystBlock(BlockBehaviour.Properties $$0) {
/* 25 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 30 */     if ($$3.nextInt(5) != 0) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     Direction $$4 = DIRECTIONS[$$3.nextInt(DIRECTIONS.length)];
/* 35 */     BlockPos $$5 = $$2.relative($$4);
/* 36 */     BlockState $$6 = $$1.getBlockState($$5);
/* 37 */     Block $$7 = null;
/* 38 */     if (canClusterGrowAtState($$6)) {
/* 39 */       $$7 = Blocks.SMALL_AMETHYST_BUD;
/* 40 */     } else if ($$6.is(Blocks.SMALL_AMETHYST_BUD) && $$6.getValue((Property)AmethystClusterBlock.FACING) == $$4) {
/* 41 */       $$7 = Blocks.MEDIUM_AMETHYST_BUD;
/* 42 */     } else if ($$6.is(Blocks.MEDIUM_AMETHYST_BUD) && $$6.getValue((Property)AmethystClusterBlock.FACING) == $$4) {
/* 43 */       $$7 = Blocks.LARGE_AMETHYST_BUD;
/* 44 */     } else if ($$6.is(Blocks.LARGE_AMETHYST_BUD) && $$6.getValue((Property)AmethystClusterBlock.FACING) == $$4) {
/* 45 */       $$7 = Blocks.AMETHYST_CLUSTER;
/*    */     } 
/*    */     
/* 48 */     if ($$7 != null) {
/*    */ 
/*    */       
/* 51 */       BlockState $$8 = (BlockState)((BlockState)$$7.defaultBlockState().setValue((Property)AmethystClusterBlock.FACING, (Comparable)$$4)).setValue((Property)AmethystClusterBlock.WATERLOGGED, Boolean.valueOf(($$6.getFluidState().getType() == Fluids.WATER)));
/* 52 */       $$1.setBlockAndUpdate($$5, $$8);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean canClusterGrowAtState(BlockState $$0) {
/* 57 */     return ($$0.isAir() || ($$0.is(Blocks.WATER) && $$0.getFluidState().getAmount() == 8));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BuddingAmethystBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */