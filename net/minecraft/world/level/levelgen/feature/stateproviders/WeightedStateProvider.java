/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WeightedStateProvider extends BlockStateProvider {
/*    */   public static final Codec<WeightedStateProvider> CODEC;
/*    */   
/*    */   static {
/* 11 */     CODEC = SimpleWeightedRandomList.wrappedCodec(BlockState.CODEC).comapFlatMap(WeightedStateProvider::create, $$0 -> $$0.weightedList).fieldOf("entries").codec();
/*    */   }
/*    */   private final SimpleWeightedRandomList<BlockState> weightedList;
/*    */   private static DataResult<WeightedStateProvider> create(SimpleWeightedRandomList<BlockState> $$0) {
/* 15 */     if ($$0.isEmpty()) {
/* 16 */       return DataResult.error(() -> "WeightedStateProvider with no states");
/*    */     }
/* 18 */     return DataResult.success(new WeightedStateProvider($$0));
/*    */   }
/*    */   
/*    */   public WeightedStateProvider(SimpleWeightedRandomList<BlockState> $$0) {
/* 22 */     this.weightedList = $$0;
/*    */   }
/*    */   
/*    */   public WeightedStateProvider(SimpleWeightedRandomList.Builder<BlockState> $$0) {
/* 26 */     this($$0.build());
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateProviderType<?> type() {
/* 31 */     return BlockStateProviderType.WEIGHTED_STATE_PROVIDER;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getState(RandomSource $$0, BlockPos $$1) {
/* 36 */     return (BlockState)this.weightedList.getRandomValue($$0).orElseThrow(IllegalStateException::new);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\WeightedStateProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */