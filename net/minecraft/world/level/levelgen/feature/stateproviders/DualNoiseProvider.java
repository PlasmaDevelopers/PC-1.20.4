/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function7;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.InclusiveRange;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*    */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*    */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*    */ 
/*    */ public class DualNoiseProvider
/*    */   extends NoiseProvider
/*    */ {
/*    */   public static final Codec<DualNoiseProvider> CODEC;
/*    */   private final InclusiveRange<Integer> variety;
/*    */   
/*    */   static {
/* 27 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)InclusiveRange.codec((Codec)Codec.INT, Integer.valueOf(1), Integer.valueOf(64)).fieldOf("variety").forGetter(()), (App)NormalNoise.NoiseParameters.DIRECT_CODEC.fieldOf("slow_noise").forGetter(()), (App)ExtraCodecs.POSITIVE_FLOAT.fieldOf("slow_scale").forGetter(())).and(noiseProviderCodec($$0)).apply((Applicative)$$0, DualNoiseProvider::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private final NormalNoise.NoiseParameters slowNoiseParameters;
/*    */ 
/*    */   
/*    */   private final float slowScale;
/*    */ 
/*    */   
/*    */   private final NormalNoise slowNoise;
/*    */ 
/*    */   
/*    */   public DualNoiseProvider(InclusiveRange<Integer> $$0, NormalNoise.NoiseParameters $$1, float $$2, long $$3, NormalNoise.NoiseParameters $$4, float $$5, List<BlockState> $$6) {
/* 42 */     super($$3, $$4, $$5, $$6);
/* 43 */     this.variety = $$0;
/* 44 */     this.slowNoiseParameters = $$1;
/* 45 */     this.slowScale = $$2;
/* 46 */     this.slowNoise = NormalNoise.create((RandomSource)new WorldgenRandom((RandomSource)new LegacyRandomSource($$3)), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateProviderType<?> type() {
/* 51 */     return BlockStateProviderType.DUAL_NOISE_PROVIDER;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState getState(RandomSource $$0, BlockPos $$1) {
/* 57 */     double $$2 = getSlowNoiseValue($$1);
/* 58 */     int $$3 = (int)Mth.clampedMap($$2, -1.0D, 1.0D, ((Integer)this.variety.minInclusive()).intValue(), (((Integer)this.variety.maxInclusive()).intValue() + 1));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     List<BlockState> $$4 = Lists.newArrayListWithCapacity($$3);
/* 64 */     for (int $$5 = 0; $$5 < $$3; $$5++)
/*    */     {
/* 66 */       $$4.add(getRandomState(this.states, getSlowNoiseValue($$1.offset($$5 * 54545, 0, $$5 * 34234))));
/*    */     }
/*    */     
/* 69 */     return getRandomState($$4, $$1, this.scale);
/*    */   }
/*    */   
/*    */   protected double getSlowNoiseValue(BlockPos $$0) {
/* 73 */     return this.slowNoise.getValue(($$0.getX() * this.slowScale), ($$0.getY() * this.slowScale), ($$0.getZ() * this.slowScale));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\DualNoiseProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */