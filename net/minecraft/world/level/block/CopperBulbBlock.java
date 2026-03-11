/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class CopperBulbBlock extends Block {
/* 16 */   public static final MapCodec<CopperBulbBlock> CODEC = simpleCodec(CopperBulbBlock::new);
/*    */ 
/*    */   
/*    */   protected MapCodec<? extends CopperBulbBlock> codec() {
/* 20 */     return CODEC;
/*    */   }
/*    */   
/* 23 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/* 24 */   public static final BooleanProperty LIT = BlockStateProperties.LIT;
/*    */   
/*    */   public CopperBulbBlock(BlockBehaviour.Properties $$0) {
/* 27 */     super($$0);
/* 28 */     registerDefaultState((BlockState)((BlockState)defaultBlockState().setValue((Property)LIT, Boolean.valueOf(false))).setValue((Property)POWERED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 33 */     if ($$3.getBlock() != $$0.getBlock() && $$1 instanceof ServerLevel) { ServerLevel $$5 = (ServerLevel)$$1;
/* 34 */       checkAndFlip($$0, $$5, $$2); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 40 */     if ($$1 instanceof ServerLevel) { ServerLevel $$6 = (ServerLevel)$$1;
/* 41 */       checkAndFlip($$0, $$6, $$2); }
/*    */   
/*    */   }
/*    */   
/*    */   public void checkAndFlip(BlockState $$0, ServerLevel $$1, BlockPos $$2) {
/* 46 */     boolean $$3 = $$1.hasNeighborSignal($$2);
/*    */     
/* 48 */     if ($$3 == ((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*    */       return;
/*    */     }
/*    */     
/* 52 */     BlockState $$4 = $$0;
/* 53 */     if (!((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 54 */       $$4 = (BlockState)$$4.cycle((Property)LIT);
/* 55 */       $$1.playSound(null, $$2, ((Boolean)$$4.getValue((Property)LIT)).booleanValue() ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF, SoundSource.BLOCKS);
/*    */     } 
/* 57 */     $$1.setBlock($$2, (BlockState)$$4.setValue((Property)POWERED, Boolean.valueOf($$3)), 3);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 62 */     $$0.add(new Property[] { (Property)LIT, (Property)POWERED });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 72 */     return ((Boolean)$$1.getBlockState($$2).getValue((Property)LIT)).booleanValue() ? 15 : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CopperBulbBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */