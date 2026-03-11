/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class AbstractChestBlock<E extends BlockEntity> extends BaseEntityBlock {
/*    */   protected final Supplier<BlockEntityType<? extends E>> blockEntityType;
/*    */   
/*    */   protected AbstractChestBlock(BlockBehaviour.Properties $$0, Supplier<BlockEntityType<? extends E>> $$1) {
/* 17 */     super($$0);
/* 18 */     this.blockEntityType = $$1;
/*    */   }
/*    */   
/*    */   protected abstract MapCodec<? extends AbstractChestBlock<E>> codec();
/*    */   
/*    */   public abstract DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState paramBlockState, Level paramLevel, BlockPos paramBlockPos, boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AbstractChestBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */