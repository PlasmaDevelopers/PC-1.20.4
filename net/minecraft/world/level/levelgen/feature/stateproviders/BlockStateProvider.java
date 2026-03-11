/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class BlockStateProvider {
/* 11 */   public static final Codec<BlockStateProvider> CODEC = BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE.byNameCodec().dispatch(BlockStateProvider::type, BlockStateProviderType::codec);
/*    */   
/*    */   public static SimpleStateProvider simple(BlockState $$0) {
/* 14 */     return new SimpleStateProvider($$0);
/*    */   }
/*    */   
/*    */   public static SimpleStateProvider simple(Block $$0) {
/* 18 */     return new SimpleStateProvider($$0.defaultBlockState());
/*    */   }
/*    */   
/*    */   protected abstract BlockStateProviderType<?> type();
/*    */   
/*    */   public abstract BlockState getState(RandomSource paramRandomSource, BlockPos paramBlockPos);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\BlockStateProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */