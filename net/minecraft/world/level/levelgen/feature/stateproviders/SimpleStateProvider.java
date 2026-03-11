/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SimpleStateProvider extends BlockStateProvider {
/*    */   public static final Codec<SimpleStateProvider> CODEC;
/*    */   
/*    */   static {
/*  9 */     CODEC = BlockState.CODEC.fieldOf("state").xmap(SimpleStateProvider::new, $$0 -> $$0.state).codec();
/*    */   }
/*    */   private final BlockState state;
/*    */   
/*    */   protected SimpleStateProvider(BlockState $$0) {
/* 14 */     this.state = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateProviderType<?> type() {
/* 19 */     return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getState(RandomSource $$0, BlockPos $$1) {
/* 24 */     return this.state;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\SimpleStateProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */