/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class SnowyDirtBlock extends Block {
/* 15 */   public static final MapCodec<SnowyDirtBlock> CODEC = simpleCodec(SnowyDirtBlock::new);
/*    */ 
/*    */   
/*    */   protected MapCodec<? extends SnowyDirtBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */   
/* 22 */   public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
/*    */   
/*    */   protected SnowyDirtBlock(BlockBehaviour.Properties $$0) {
/* 25 */     super($$0);
/* 26 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)SNOWY, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 31 */     if ($$1 == Direction.UP) {
/* 32 */       return (BlockState)$$0.setValue((Property)SNOWY, Boolean.valueOf(isSnowySetting($$2)));
/*    */     }
/* 34 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 39 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos().above());
/* 40 */     return (BlockState)defaultBlockState().setValue((Property)SNOWY, Boolean.valueOf(isSnowySetting($$1)));
/*    */   }
/*    */   
/*    */   private static boolean isSnowySetting(BlockState $$0) {
/* 44 */     return $$0.is(BlockTags.SNOW);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 49 */     $$0.add(new Property[] { (Property)SNOWY });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SnowyDirtBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */