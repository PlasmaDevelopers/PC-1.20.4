/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TallFlowerBlock extends DoublePlantBlock implements BonemealableBlock {
/* 13 */   public static final MapCodec<TallFlowerBlock> CODEC = simpleCodec(TallFlowerBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<TallFlowerBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/*    */   public TallFlowerBlock(BlockBehaviour.Properties $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 36 */     popResource((Level)$$0, $$2, new ItemStack(this));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TallFlowerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */