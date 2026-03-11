/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.stats.Stat;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TrappedChestBlock extends ChestBlock {
/* 19 */   public static final MapCodec<TrappedChestBlock> CODEC = simpleCodec(TrappedChestBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<TrappedChestBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */   
/*    */   public TrappedChestBlock(BlockBehaviour.Properties $$0) {
/* 27 */     super($$0, () -> BlockEntityType.TRAPPED_CHEST);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 32 */     return (BlockEntity)new TrappedChestBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Stat<ResourceLocation> getOpenChestStat() {
/* 37 */     return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSignalSource(BlockState $$0) {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 47 */     return Mth.clamp(ChestBlockEntity.getOpenCount($$1, $$2), 0, 15);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 52 */     if ($$3 == Direction.UP) {
/* 53 */       return $$0.getSignal($$1, $$2, $$3);
/*    */     }
/*    */     
/* 56 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TrappedChestBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */