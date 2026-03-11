/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class NetherrackBlock extends Block implements BonemealableBlock {
/* 13 */   public static final MapCodec<NetherrackBlock> CODEC = simpleCodec(NetherrackBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<NetherrackBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/*    */   public NetherrackBlock(BlockBehaviour.Properties $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 26 */     if (!$$0.getBlockState($$1.above()).propagatesSkylightDown((BlockGetter)$$0, $$1)) {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     for (BlockPos $$3 : BlockPos.betweenClosed($$1.offset(-1, -1, -1), $$1.offset(1, 1, 1))) {
/* 31 */       if ($$0.getBlockState($$3).is(BlockTags.NYLIUM)) {
/* 32 */         return true;
/*    */       }
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 45 */     boolean $$4 = false;
/* 46 */     boolean $$5 = false;
/* 47 */     for (BlockPos $$6 : BlockPos.betweenClosed($$2.offset(-1, -1, -1), $$2.offset(1, 1, 1))) {
/* 48 */       BlockState $$7 = $$0.getBlockState($$6);
/* 49 */       if ($$7.is(Blocks.WARPED_NYLIUM)) {
/* 50 */         $$5 = true;
/*    */       }
/*    */       
/* 53 */       if ($$7.is(Blocks.CRIMSON_NYLIUM)) {
/* 54 */         $$4 = true;
/*    */       }
/*    */       
/* 57 */       if ($$5 && $$4) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 62 */     if ($$5 && $$4) {
/* 63 */       $$0.setBlock($$2, $$1.nextBoolean() ? Blocks.WARPED_NYLIUM.defaultBlockState() : Blocks.CRIMSON_NYLIUM.defaultBlockState(), 3);
/* 64 */     } else if ($$5) {
/* 65 */       $$0.setBlock($$2, Blocks.WARPED_NYLIUM.defaultBlockState(), 3);
/* 66 */     } else if ($$4) {
/* 67 */       $$0.setBlock($$2, Blocks.CRIMSON_NYLIUM.defaultBlockState(), 3);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\NetherrackBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */