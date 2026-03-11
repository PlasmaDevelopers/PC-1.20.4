/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ 
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*    */ 
/*    */ public class NoiseProvider extends NoiseBasedStateProvider {
/*    */   public static final Codec<NoiseProvider> CODEC;
/*    */   protected final List<BlockState> states;
/*    */   
/*    */   protected static <P extends NoiseProvider> Products.P4<RecordCodecBuilder.Mu<P>, Long, NormalNoise.NoiseParameters, Float, List<BlockState>> noiseProviderCodec(RecordCodecBuilder.Instance<P> $$0) {
/* 21 */     return noiseCodec((RecordCodecBuilder.Instance)$$0).and(
/* 22 */         (App)Codec.list(BlockState.CODEC).fieldOf("states").forGetter($$0 -> $$0.states));
/*    */   }
/*    */   
/*    */   static {
/* 26 */     CODEC = RecordCodecBuilder.create($$0 -> noiseProviderCodec($$0).apply((Applicative)$$0, NoiseProvider::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public NoiseProvider(long $$0, NormalNoise.NoiseParameters $$1, float $$2, List<BlockState> $$3) {
/* 31 */     super($$0, $$1, $$2);
/* 32 */     this.states = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateProviderType<?> type() {
/* 37 */     return BlockStateProviderType.NOISE_PROVIDER;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getState(RandomSource $$0, BlockPos $$1) {
/* 42 */     return getRandomState(this.states, $$1, this.scale);
/*    */   }
/*    */   
/*    */   protected BlockState getRandomState(List<BlockState> $$0, BlockPos $$1, double $$2) {
/* 46 */     double $$3 = getNoiseValue($$1, $$2);
/* 47 */     return getRandomState($$0, $$3);
/*    */   }
/*    */   
/*    */   protected BlockState getRandomState(List<BlockState> $$0, double $$1) {
/* 51 */     double $$2 = Mth.clamp((1.0D + $$1) / 2.0D, 0.0D, 0.9999D);
/* 52 */     return $$0.get((int)($$2 * $$0.size()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\NoiseProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */