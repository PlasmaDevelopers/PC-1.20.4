/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class RedstoneLampBlock extends Block {
/* 16 */   public static final MapCodec<RedstoneLampBlock> CODEC = simpleCodec(RedstoneLampBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<RedstoneLampBlock> codec() {
/* 20 */     return CODEC;
/*    */   }
/*    */   
/* 23 */   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
/*    */   
/*    */   public RedstoneLampBlock(BlockBehaviour.Properties $$0) {
/* 26 */     super($$0);
/* 27 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)LIT, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 33 */     return (BlockState)defaultBlockState().setValue((Property)LIT, Boolean.valueOf($$0.getLevel().hasNeighborSignal($$0.getClickedPos())));
/*    */   }
/*    */ 
/*    */   
/*    */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 38 */     if ($$1.isClientSide) {
/*    */       return;
/*    */     }
/*    */     
/* 42 */     boolean $$6 = ((Boolean)$$0.getValue((Property)LIT)).booleanValue();
/* 43 */     if ($$6 != $$1.hasNeighborSignal($$2)) {
/* 44 */       if ($$6) {
/* 45 */         $$1.scheduleTick($$2, this, 4);
/*    */       } else {
/* 47 */         $$1.setBlock($$2, (BlockState)$$0.cycle((Property)LIT), 2);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 54 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue() && !$$1.hasNeighborSignal($$2)) {
/* 55 */       $$1.setBlock($$2, (BlockState)$$0.cycle((Property)LIT), 2);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 61 */     $$0.add(new Property[] { (Property)LIT });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RedstoneLampBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */