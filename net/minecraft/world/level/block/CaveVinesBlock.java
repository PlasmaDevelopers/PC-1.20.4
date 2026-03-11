/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class CaveVinesBlock extends GrowingPlantHeadBlock implements BonemealableBlock, CaveVines {
/* 20 */   public static final MapCodec<CaveVinesBlock> CODEC = simpleCodec(CaveVinesBlock::new);
/*    */   private static final float CHANCE_OF_BERRIES_ON_GROWTH = 0.11F;
/*    */   
/*    */   public MapCodec<CaveVinesBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CaveVinesBlock(BlockBehaviour.Properties $$0) {
/* 30 */     super($$0, Direction.DOWN, SHAPE, false, 0.1D);
/* 31 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0))).setValue((Property)BERRIES, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlocksToGrowWhenBonemealed(RandomSource $$0) {
/* 36 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canGrowInto(BlockState $$0) {
/* 41 */     return $$0.isAir();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Block getBodyBlock() {
/* 46 */     return Blocks.CAVE_VINES_PLANT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState updateBodyAfterConvertedFromHead(BlockState $$0, BlockState $$1) {
/* 51 */     return (BlockState)$$1.setValue((Property)BERRIES, $$0.getValue((Property)BERRIES));
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState getGrowIntoState(BlockState $$0, RandomSource $$1) {
/* 56 */     return (BlockState)super.getGrowIntoState($$0, $$1).setValue((Property)BERRIES, Boolean.valueOf(($$1.nextFloat() < 0.11F)));
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 61 */     return new ItemStack((ItemLike)Items.GLOW_BERRIES);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 66 */     return CaveVines.use((Entity)$$3, $$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 71 */     super.createBlockStateDefinition($$0);
/* 72 */     $$0.add(new Property[] { (Property)BERRIES });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 77 */     return !((Boolean)$$2.getValue((Property)BERRIES)).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 82 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 87 */     $$0.setBlock($$2, (BlockState)$$3.setValue((Property)BERRIES, Boolean.valueOf(true)), 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CaveVinesBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */