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
/*    */ public class CaveVinesPlantBlock extends GrowingPlantBodyBlock implements BonemealableBlock, CaveVines {
/* 20 */   public static final MapCodec<CaveVinesPlantBlock> CODEC = simpleCodec(CaveVinesPlantBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<CaveVinesPlantBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */   
/*    */   public CaveVinesPlantBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super($$0, Direction.DOWN, SHAPE, false);
/* 29 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)BERRIES, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected GrowingPlantHeadBlock getHeadBlock() {
/* 34 */     return (GrowingPlantHeadBlock)Blocks.CAVE_VINES;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState updateHeadAfterConvertedFromBody(BlockState $$0, BlockState $$1) {
/* 39 */     return (BlockState)$$1.setValue((Property)BERRIES, $$0.getValue((Property)BERRIES));
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 44 */     return new ItemStack((ItemLike)Items.GLOW_BERRIES);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 49 */     return CaveVines.use((Entity)$$3, $$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 54 */     $$0.add(new Property[] { (Property)BERRIES });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 59 */     return !((Boolean)$$2.getValue((Property)BERRIES)).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 69 */     $$0.setBlock($$2, (BlockState)$$3.setValue((Property)BERRIES, Boolean.valueOf(true)), 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CaveVinesPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */