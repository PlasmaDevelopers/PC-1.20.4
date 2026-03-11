/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class NetherWartBlock extends BushBlock {
/* 19 */   public static final MapCodec<NetherWartBlock> CODEC = simpleCodec(NetherWartBlock::new);
/*    */   public static final int MAX_AGE = 3;
/*    */   
/*    */   public MapCodec<NetherWartBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 27 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
/*    */   
/* 29 */   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
/* 30 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), 
/* 31 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
/* 32 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D), 
/* 33 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
/*    */     };
/*    */   
/*    */   protected NetherWartBlock(BlockBehaviour.Properties $$0) {
/* 37 */     super($$0);
/* 38 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 43 */     return SHAPE_BY_AGE[((Integer)$$0.getValue((Property)AGE)).intValue()];
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 48 */     return $$0.is(Blocks.SOUL_SAND);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRandomlyTicking(BlockState $$0) {
/* 53 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() < 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 58 */     int $$4 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/* 59 */     if ($$4 < 3 && $$3.nextInt(10) == 0) {
/* 60 */       $$0 = (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$4 + 1));
/* 61 */       $$1.setBlock($$2, $$0, 2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 67 */     return new ItemStack((ItemLike)Items.NETHER_WART);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 72 */     $$0.add(new Property[] { (Property)AGE });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\NetherWartBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */