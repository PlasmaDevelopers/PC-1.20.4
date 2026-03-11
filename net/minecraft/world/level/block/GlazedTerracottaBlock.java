/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class GlazedTerracottaBlock extends HorizontalDirectionalBlock {
/*  9 */   public static final MapCodec<GlazedTerracottaBlock> CODEC = simpleCodec(GlazedTerracottaBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<GlazedTerracottaBlock> codec() {
/* 13 */     return CODEC;
/*    */   }
/*    */   
/*    */   public GlazedTerracottaBlock(BlockBehaviour.Properties $$0) {
/* 17 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 22 */     $$0.add(new Property[] { (Property)FACING });
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 27 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\GlazedTerracottaBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */